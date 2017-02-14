package br.com.condominiosvirtuais.persistence.impl;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import br.com.condominiosvirtuais.entity.Endereco;
import br.com.condominiosvirtuais.persistence.EnderecoDAO;
import br.com.condominiosvirtuais.util.SQLUtil;

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
	
	private static final String ID_BENEFICIARIO = "ID_BENEFICIARIO";

	
	public void salvarEndereco(Endereco endereco, Connection con) throws SQLException, Exception{
		StringBuffer query = new StringBuffer();
		query.append("INSERT INTO "); 
		query.append(ENDERECO); 
		query.append("("); 
		query.append(ENDERECO); 
		query.append(",");
		query.append(NUMERO); 
		query.append(",");
		query.append(COMPLEMENTO); 
		query.append(",");
		query.append(CEP); 
		query.append(",");
		query.append(BAIRRO); 
		query.append(",");
		query.append(CIDADE); 
		query.append(",");
		query.append(UF); 
		query.append(",");
		query.append(PAIS); 
		query.append(",");
		query.append(ID_CONDOMINIO);
		query.append(",");
		query.append(ID_BENEFICIARIO);
		query.append(") ");
		query.append("VALUES(?,?,?,?,?,?,?,?,?,?)");		
		PreparedStatement statement = null;
		try {
			statement = con.prepareStatement(query.toString());		
			SQLUtil.setValorPpreparedStatement(statement, 1, endereco.getEndereco(),java.sql.Types.VARCHAR);
			SQLUtil.setValorPpreparedStatement(statement, 2, endereco.getNumero(), java.sql.Types.INTEGER);
			SQLUtil.setValorPpreparedStatement(statement, 3, endereco.getComplemento(),java.sql.Types.VARCHAR);
			SQLUtil.setValorPpreparedStatement(statement, 4, endereco.getCep(), java.sql.Types.INTEGER);
			SQLUtil.setValorPpreparedStatement(statement, 5, endereco.getBairro(),java.sql.Types.VARCHAR);
			SQLUtil.setValorPpreparedStatement(statement, 6, endereco.getCidade(),java.sql.Types.VARCHAR);
			SQLUtil.setValorPpreparedStatement(statement, 7, endereco.getUf(),java.sql.Types.VARCHAR);
			SQLUtil.setValorPpreparedStatement(statement, 8, endereco.getPais(),java.sql.Types.VARCHAR);
			SQLUtil.setValorPpreparedStatement(statement, 9, endereco.getIdCondominio(),java.sql.Types.INTEGER);
			SQLUtil.setValorPpreparedStatement(statement, 10, endereco.getIdBeneficiario(),java.sql.Types.INTEGER);			
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
	
	public Endereco buscarEnderecoPorIdBeneficiario(Integer idBeneficiario, Connection con) throws SQLException, Exception{
		StringBuffer query = new StringBuffer();
		query.append("SELECT * FROM ");
		query.append(ENDERECO);
		query.append(" WHERE ");
		query.append(ID_BENEFICIARIO);
		query.append(" = ?");
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		Endereco endereco = null;
		try {
			statement = con.prepareStatement(query.toString());
			SQLUtil.setValorPpreparedStatement(statement, 1, idBeneficiario, java.sql.Types.INTEGER);
			resultSet = statement.executeQuery();
			while(resultSet.next()){
				endereco = new Endereco();
				endereco.setId((Integer) SQLUtil.getValorResultSet(resultSet, ID, java.sql.Types.INTEGER));							
				endereco.setEndereco(String.valueOf(SQLUtil.getValorResultSet(resultSet, ENDERECO, java.sql.Types.VARCHAR)));
				endereco.setNumero((Integer) SQLUtil.getValorResultSet(resultSet, NUMERO, java.sql.Types.INTEGER));
				endereco.setComplemento(String.valueOf(SQLUtil.getValorResultSet(resultSet, COMPLEMENTO, java.sql.Types.VARCHAR)));
				endereco.setCep((Integer) SQLUtil.getValorResultSet(resultSet, CEP, java.sql.Types.INTEGER));
				endereco.setBairro(String.valueOf(SQLUtil.getValorResultSet(resultSet, BAIRRO, java.sql.Types.VARCHAR)));
				endereco.setCidade(String.valueOf(SQLUtil.getValorResultSet(resultSet, CIDADE, java.sql.Types.VARCHAR)));
				endereco.setUf(String.valueOf(SQLUtil.getValorResultSet(resultSet, UF, java.sql.Types.VARCHAR)));
				endereco.setPais(String.valueOf(SQLUtil.getValorResultSet(resultSet, PAIS, java.sql.Types.VARCHAR)));
				endereco.setIdCondominio((Integer) SQLUtil.getValorResultSet(resultSet, ID_CONDOMINIO, java.sql.Types.INTEGER));
				endereco.setIdBeneficiario((Integer) SQLUtil.getValorResultSet(resultSet, ID_BENEFICIARIO, java.sql.Types.INTEGER));
			}
		} catch (SQLException e) {
			con.rollback();
			throw e;
		} catch (Exception e) {
			con.rollback();
			throw e;		
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
		query.append("= ?, "); 
		query.append(ID_BENEFICIARIO);
		query.append("= ? "); 
		query.append("WHERE ");
		query.append(ID_CONDOMINIO);
		query.append("= ?");
		PreparedStatement statement = null;
		try {
			statement = con.prepareStatement(query.toString());	
			SQLUtil.setValorPpreparedStatement(statement, 1, endereco.getEndereco(),java.sql.Types.VARCHAR);
			SQLUtil.setValorPpreparedStatement(statement, 2, endereco.getNumero(), java.sql.Types.INTEGER);
			SQLUtil.setValorPpreparedStatement(statement, 3, endereco.getComplemento(),java.sql.Types.VARCHAR);
			SQLUtil.setValorPpreparedStatement(statement, 4, endereco.getCep(), java.sql.Types.INTEGER);
			SQLUtil.setValorPpreparedStatement(statement, 5, endereco.getBairro(),java.sql.Types.VARCHAR);
			SQLUtil.setValorPpreparedStatement(statement, 6, endereco.getCidade(),java.sql.Types.VARCHAR);
			SQLUtil.setValorPpreparedStatement(statement, 7, endereco.getUf(),java.sql.Types.VARCHAR);
			SQLUtil.setValorPpreparedStatement(statement, 8, endereco.getPais(),java.sql.Types.VARCHAR);
			SQLUtil.setValorPpreparedStatement(statement, 9, endereco.getIdBeneficiario(),java.sql.Types.INTEGER);
			SQLUtil.setValorPpreparedStatement(statement, 10, endereco.getIdCondominio(),java.sql.Types.INTEGER);			
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

	@Override
	public void atualizarEnderecoPorId(Endereco endereco, Connection con) throws SQLException, Exception {
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
		query.append("= ?, "); 
		query.append(ID_BENEFICIARIO);
		query.append("= ?, "); 
		query.append(ID_CONDOMINIO);
		query.append("= ?");
		query.append(" WHERE ");
		query.append(ID);
		query.append("= ?");
		PreparedStatement statement = null;
		try {
			statement = con.prepareStatement(query.toString());	
			SQLUtil.setValorPpreparedStatement(statement, 1, endereco.getEndereco(),java.sql.Types.VARCHAR);
			SQLUtil.setValorPpreparedStatement(statement, 2, endereco.getNumero(), java.sql.Types.INTEGER);
			SQLUtil.setValorPpreparedStatement(statement, 3, endereco.getComplemento(),java.sql.Types.VARCHAR);
			SQLUtil.setValorPpreparedStatement(statement, 4, endereco.getCep(), java.sql.Types.INTEGER);
			SQLUtil.setValorPpreparedStatement(statement, 5, endereco.getBairro(),java.sql.Types.VARCHAR);
			SQLUtil.setValorPpreparedStatement(statement, 6, endereco.getCidade(),java.sql.Types.VARCHAR);
			SQLUtil.setValorPpreparedStatement(statement, 7, endereco.getUf(),java.sql.Types.VARCHAR);
			SQLUtil.setValorPpreparedStatement(statement, 8, endereco.getPais(),java.sql.Types.VARCHAR);
			SQLUtil.setValorPpreparedStatement(statement, 9, endereco.getIdBeneficiario(),java.sql.Types.INTEGER);
			SQLUtil.setValorPpreparedStatement(statement, 10, endereco.getIdCondominio(),java.sql.Types.INTEGER);
			SQLUtil.setValorPpreparedStatement(statement, 11, endereco.getId(),java.sql.Types.INTEGER);
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
