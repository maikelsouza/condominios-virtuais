package br.com.condominiosvirtuais.persistence.impl;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import br.com.condominiosvirtuais.entity.Email;
import br.com.condominiosvirtuais.persistence.EmailDAO;
import br.com.condominiosvirtuais.util.SQLUtil;

public class EmailDAOImpl implements EmailDAO, Serializable {
	
	private static final long serialVersionUID = 1L;

	private static Logger logger = Logger.getLogger(EmailDAOImpl.class);
	
    private static final String EMAIL = "EMAIL";
	
	private static final String ID = "ID";
	
	private static final String PARA = "PARA";
	
	private static final String DE = "DE";
	
	private static final String CC = "CC";
	
	private static final String CO = "CO";
	
	private static final String ASSUNTO = "ASSUNTO";
	
	private static final String MENSAGEM = "MENSAGEM";
	

	public void salvar(Email email) throws SQLException, Exception {
		PreparedStatement statement = null;
		Connection con = null;		
		try {
			con = Conexao.getConexao();
			StringBuffer query = new StringBuffer();
			query.append("INSERT INTO "); 
			query.append(EMAIL); 
			query.append("(");
			query.append(DE); 
			query.append(",");
			query.append(PARA); 
			query.append(",");
			query.append(CC); 
			query.append(",");			
			query.append(CO); 
			query.append(",");			
			query.append(ASSUNTO);
			query.append(",");	
			query.append(MENSAGEM);
			query.append(") ");
			query.append("VALUES(?,?,?,?,?,?)");
			statement = con.prepareStatement(query.toString());			
			SQLUtil.setValorPpreparedStatement(statement, 1, email.getDe(), java.sql.Types.VARCHAR);
			SQLUtil.setValorPpreparedStatement(statement, 2, email.getPara(), java.sql.Types.VARCHAR);
			SQLUtil.setValorPpreparedStatement(statement, 3, email.getCc(), java.sql.Types.VARCHAR);
			SQLUtil.setValorPpreparedStatement(statement, 4, email.getCo(), java.sql.Types.VARCHAR);
			SQLUtil.setValorPpreparedStatement(statement, 5, email.getAssunto(), java.sql.Types.VARCHAR);
			SQLUtil.setValorPpreparedStatement(statement, 6, email.getMensagem(), java.sql.Types.VARCHAR);
			statement.execute();
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

	public void excluir(Email email) throws SQLException, Exception {
		StringBuffer query = new StringBuffer();
		Connection con = Conexao.getConexao();
		PreparedStatement statement = null;
		try {			
			query.append("DELETE FROM ");
			query.append(EMAIL);
			query.append(" WHERE ");		
			query.append(ID);
			query.append(" = ?");
			statement = con.prepareStatement(query.toString());
			SQLUtil.setValorPpreparedStatement(statement, 1, email.getId(), java.sql.Types.INTEGER);			
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

	public List<Email> busca(Integer limite) throws SQLException, Exception {
		StringBuffer query = new StringBuffer();
		Connection con = Conexao.getConexao();
		PreparedStatement statement = null;
		List<Email> listaEmail = null;
		try {			
			query.append("SELECT * FROM ");
			query.append(EMAIL);
			query.append(" LIMIT ");
			query.append(limite);
			statement = con.prepareStatement(query.toString());
			PreparedStatement preparedStatement = null;
			ResultSet resultSet = null;
			listaEmail = new ArrayList<Email>();
			Email email = null;						
				preparedStatement = con.prepareStatement(query.toString());
				resultSet = preparedStatement.executeQuery();
				while(resultSet.next()){				
					email = new Email();				
					email.setId((Integer) SQLUtil.getValorResultSet(resultSet, ID, java.sql.Types.INTEGER));
					email.setDe((String) SQLUtil.getValorResultSet(resultSet, DE, java.sql.Types.VARCHAR));
					email.setPara((String) SQLUtil.getValorResultSet(resultSet, PARA, java.sql.Types.VARCHAR));
					email.setCc((String) SQLUtil.getValorResultSet(resultSet, CC, java.sql.Types.VARCHAR));
					email.setCo((String) SQLUtil.getValorResultSet(resultSet, CO, java.sql.Types.VARCHAR));
					email.setAssunto((String) SQLUtil.getValorResultSet(resultSet, ASSUNTO, java.sql.Types.VARCHAR));
					email.setMensagem((String) SQLUtil.getValorResultSet(resultSet, MENSAGEM, java.sql.Types.VARCHAR));
					listaEmail.add(email);
				}
			} catch (NumberFormatException e) {
				throw e;
			} catch (SQLException e) {
				throw e;
			} finally{
				try {
					statement.close();
					con.close();
				} catch (SQLException e) {
					logger.error("erro sqlstate "+e.getSQLState(), e);
				}
			}	
		return listaEmail;
	}
}
