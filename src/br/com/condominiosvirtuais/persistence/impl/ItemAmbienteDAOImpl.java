package br.com.condominiosvirtuais.persistence.impl;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import br.com.condominiosvirtuais.entity.Ambiente;
import br.com.condominiosvirtuais.entity.ItemAmbiente;
import br.com.condominiosvirtuais.persistence.ItemAmbienteDAO;
import br.com.condominiosvirtuais.util.SQLUtil;

public class ItemAmbienteDAOImpl implements ItemAmbienteDAO, Serializable {
	
	private static final long serialVersionUID = 1L;

	private static Logger logger = Logger.getLogger(ItemAmbienteDAOImpl.class);
	
	private static final String ITEM_AMBIENTE = "ITEM_AMBIENTE";
	
	private static final String ID = "ID";
	
	private static final String NOME = "NOME";
	
	private static final String QUANTIDADE = "QUANTIDADE";
	
	private static final String ID_AMBIENTE = "ID_AMBIENTE";
	
	
	public List<ItemAmbiente> buscarPorAmbiente(Ambiente ambiente) throws SQLException, Exception {
		StringBuffer query = new StringBuffer();
		query.append("SELECT * FROM ");
		query.append(ITEM_AMBIENTE);
		query.append(" WHERE ");
		query.append(ID_AMBIENTE);
		query.append(" = ?");
		query.append(" ORDER BY ");
		query.append(NOME);
		Connection con = Conexao.getConexao();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		List<ItemAmbiente> listaItemAmbiente = new ArrayList<ItemAmbiente>();
		ItemAmbiente itemAmbiente = null;
		try {
			preparedStatement = con.prepareStatement(query.toString());
			preparedStatement.setInt(1,ambiente.getId());
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()){
				itemAmbiente = new ItemAmbiente();				
				itemAmbiente.setId(resultSet.getInt(ID));
				itemAmbiente.setNome(resultSet.getString(NOME));
				itemAmbiente.setQuantidade(resultSet.getInt(QUANTIDADE));
				itemAmbiente.setIdAmbiente(resultSet.getInt(ID_AMBIENTE));				
				listaItemAmbiente.add(itemAmbiente);
			}
		}  catch (SQLException e) {		
			throw e;
		} catch (Exception e) {		
			throw e;	
		}finally{
			try {
				preparedStatement.close();
				con.close();				
			} catch (SQLException e) {
				logger.error("erro sqlstate "+e.getSQLState(), e);	
			}
		}	
		return listaItemAmbiente;
	}


	public void salvar(ItemAmbiente itemAmbiente) throws SQLException, Exception {
		StringBuffer query = new StringBuffer();
		query.append("INSERT INTO "); 
		query.append(ITEM_AMBIENTE);
		query.append("("); 
		query.append(NOME); 
		query.append(",");
		query.append(QUANTIDADE); 
		query.append(",");
		query.append(ID_AMBIENTE);		 
		query.append(") ");
		query.append("VALUES(?,?,?)");
		PreparedStatement statement = null;
		Connection con = Conexao.getConexao();
		try {			
			statement = con.prepareStatement(query.toString());
			statement.setString(1,itemAmbiente.getNome());
			statement.setInt(2,itemAmbiente.getQuantidade());
			statement.setInt(3,itemAmbiente.getIdAmbiente());			
			statement.executeUpdate();			
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
	}


	
	public void excluir(ItemAmbiente itemAmbiente) throws SQLException, Exception {
		StringBuffer query = new StringBuffer();
		query.append("DELETE FROM ");
		query.append(ITEM_AMBIENTE);
		query.append(" WHERE ");		
		query.append(ID);
		query.append(" = ?");
		PreparedStatement statement = null;
		Connection con = Conexao.getConexao();
		try {
			statement = con.prepareStatement(query.toString());
			statement.setInt(1,itemAmbiente.getId());					
			statement.executeUpdate();
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
	}
	
	public void excluir(ItemAmbiente itemAmbiente, Connection con) throws SQLException, Exception {
		StringBuffer query = new StringBuffer();
		query.append("DELETE FROM ");
		query.append(ITEM_AMBIENTE);
		query.append(" WHERE ");		
		query.append(ID);
		query.append(" = ?");
		PreparedStatement statement = null;		
		try {
			statement = con.prepareStatement(query.toString());			
			SQLUtil.setValorPpreparedStatement(statement,1, itemAmbiente.getId(), java.sql.Types.INTEGER);								
			statement.executeUpdate();
		} catch (SQLException e) {
			con.rollback();
			throw e;
		} catch (Exception e) {
			con.rollback();		
			throw e;	
	    }
	}	

	public void atualizar(ItemAmbiente itemAmbiente) throws SQLException, Exception {
		StringBuffer query = new StringBuffer();
		query.append("UPDATE "); 
		query.append(ITEM_AMBIENTE);
		query.append(" SET ");
		query.append(NOME);
		query.append("= ?," );
		query.append(QUANTIDADE);
		query.append("= ?" );		
		query.append(" WHERE " );
		query.append(ID);
		query.append(" = ?");		
		PreparedStatement statement = null;
		Connection con = Conexao.getConexao();		
		try {			
			statement = con.prepareStatement(query.toString());
			statement.setString(1,itemAmbiente.getNome());	
			statement.setInt(2,itemAmbiente.getQuantidade());			
			statement.setInt(3,itemAmbiente.getId());
			statement.executeUpdate();
		} catch (SQLException e) {		
			throw e;
		} catch (Exception e) {		
			throw e;
		}finally{
			try {
				con.close();				
				statement .close();
			} catch (SQLException e) {
				logger.error("erro sqlstate "+e.getSQLState(), e);
			}
		}
		
	}

}
