package br.com.condominiosvirtuais.persistence.impl;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import br.com.condominiosvirtuais.entity.Endereco;
import br.com.condominiosvirtuais.persistence.EnderecoDAO;

public class EnderecoDAOImpl implements EnderecoDAO, Serializable {
	
	private static final long serialVersionUID = 1L;

	private static Logger logger = Logger.getLogger(EnderecoDAOImpl.class);
	
	private static final String ENDERECO = "ENDERECO";
    	
	private static final String ID = "ID";	
	
	private static final String NUMERO = "NUMERO";
	
	private static final String COMPLEMENTO = "COMPLEMENTO";
	
	private static final String CEP = "CEP";
	
	private static final String BAIRRO = "BAIRRO";
	
	private static final String CIDADE = "CIDADE";
	
	private static final String UF = "UF";
	
	private static final String PAIS = "PAIS";
	
	private static final String ID_CONDOMINIO = "ID_CONDOMINIO";

	
	public void salvarEndereco(Endereco endereco, Connection con) throws SQLException, Exception{
		StringBuffer query = new StringBuffer();
		query.append("INSERT INTO "); 
		query.append(ENDERECO); 
		query.append("("); 
		query.append(ENDERECO); query.append(",");
		query.append(NUMERO); query.append(",");
		query.append(COMPLEMENTO); query.append(",");
		query.append(CEP); query.append(",");
		query.append(BAIRRO); query.append(",");
		query.append(CIDADE); query.append(",");
		query.append(UF); query.append(",");
		query.append(PAIS); query.append(",");
		query.append(ID_CONDOMINIO); 
		query.append(") ");
		query.append("VALUES(?,?,?,?,?,?,?,?,?)");		
		PreparedStatement statement = null;
		try {
			statement = con.prepareStatement(query.toString());			
			statement.setString(1, endereco.getEndereco());
			statement.setInt(2,endereco.getNumero());
			statement.setString(3,endereco.getComplemento());
			statement.setInt(4,endereco.getCep());
			statement.setString(5,endereco.getBairro());
			statement.setString(6,endereco.getCidade());
			statement.setString(7,endereco.getUf());
			statement.setString(8,endereco.getPais());
			statement.setInt(9,endereco.getIdCondominio());			
			statement.execute();
		} catch (SQLException e) {					
			con.rollback();
			throw e;
		} catch (Exception e) {					
			con.rollback();
			throw e;
		}	
	}
	
	public Endereco buscarEnderecoPorIdCondominio(Integer idCondominio) throws SQLException, Exception{
		StringBuffer query = new StringBuffer();
		query.append("SELECT * FROM ");
		query.append(ENDERECO);
		query.append(" WHERE ");
		query.append(ID_CONDOMINIO);
		query.append(" = ?");		
		Connection con = Conexao.getConexao();
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		Endereco endereco = null;
		try {
			statement = con.prepareStatement(query.toString());
			statement.setInt(1, idCondominio);
			resultSet = statement.executeQuery();
			while(resultSet.next()){
				endereco = new Endereco();
				endereco.setId(resultSet.getInt(ID));
				endereco.setEndereco(resultSet.getString(ENDERECO));
				endereco.setNumero(resultSet.getInt(NUMERO));
				endereco.setComplemento(resultSet.getString(COMPLEMENTO));
				endereco.setCep(resultSet.getInt(CEP));
				endereco.setBairro(resultSet.getString(BAIRRO));
				endereco.setCidade(resultSet.getString(CIDADE));
				endereco.setUf(resultSet.getString(UF));
				endereco.setPais(resultSet.getString(PAIS));
				endereco.setIdCondominio(resultSet.getInt(ID_CONDOMINIO));
			}
		} catch (SQLException e) {					
			throw e;
		} catch (Exception e) {					
			throw e;
		}finally{
			try {
				statement.close();
				con.close();				
			} catch (SQLException e) {
				logger.error("erro sqlstate "+e.getSQLState(), e);
			}
		}	
		return endereco;
	}
	

	public void atualizarEndereco(Endereco endereco,  Connection con) throws SQLException, Exception{
		StringBuffer query = new StringBuffer();
		query.append("UPDATE ");
		query.append(ENDERECO);
		query.append(" SET ");
		query.append(ENDERECO);
		query.append("= ?, ");
		query.append(NUMERO);
		query.append("= ?, ");
		query.append(COMPLEMENTO);
		query.append("= ?, "); 
		query.append(CEP);
		query.append("= ?, "); 
		query.append(BAIRRO);
		query.append("= ?, "); 
		query.append(CIDADE);
		query.append("= ?, "); 
		query.append(UF);
		query.append("= ?, "); 
		query.append(PAIS);
		query.append("= ? "); 
		query.append("WHERE ");
		query.append(ID_CONDOMINIO);
		query.append("= ?");
		PreparedStatement statement = null;
		try {
			statement = con.prepareStatement(query.toString());			
			statement.setString(1, endereco.getEndereco());
			statement.setInt(2,endereco.getNumero());
			statement.setString(3,endereco.getComplemento());
			statement.setInt(4,endereco.getCep());
			statement.setString(5,endereco.getBairro());
			statement.setString(6,endereco.getCidade());
			statement.setString(7,endereco.getUf());
			statement.setString(8,endereco.getPais());
			statement.setInt(9,endereco.getIdCondominio());			
			statement.executeUpdate();
		} catch (SQLException e) {					
			con.rollback();
			throw e;
		} catch (Exception e) {					
			con.rollback();
			throw e;
		}	
	}
	
	public void excluirEndereco(Endereco endereco,  Connection con) throws SQLException, Exception{
		StringBuffer query = new StringBuffer();
		query.append("DELETE FROM ");
		query.append(ENDERECO);
		query.append(" WHERE ");
		query.append(ID);
		query.append("= ?");
		PreparedStatement statement = null;
		try {
			statement = con.prepareStatement(query.toString());
			statement.setInt(1, endereco.getId());
			statement.executeUpdate();
		} catch (SQLException e) {					
			con.rollback();
			throw e;
		} catch (Exception e) {					
			con.rollback();
			throw e;
		}		
		
	}
}
