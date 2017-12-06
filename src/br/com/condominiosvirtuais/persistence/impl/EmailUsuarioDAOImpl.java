package br.com.condominiosvirtuais.persistence.impl;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import br.com.condominiosvirtuais.entity.EmailUsuario;
import br.com.condominiosvirtuais.entity.Usuario;
import br.com.condominiosvirtuais.exception.BusinessException;
import br.com.condominiosvirtuais.persistence.EmailUsuarioDAO;
import br.com.condominiosvirtuais.util.SQLUtil;

public class EmailUsuarioDAOImpl implements EmailUsuarioDAO, Serializable{
	
	private static final long serialVersionUID = 1L;

	private static Logger logger = Logger.getLogger(EmailUsuarioDAOImpl.class);
	
	private static final String EMAIL_USUARIO = "EMAIL_USUARIO";

	private static final String ID = "ID";
	
	private static final String EMAIL = "EMAIL";
	
	private static final String PRINCIPAL = "PRINCIPAL";
	
	private static final String ID_USUARIO = "ID_USUARIO";
	
	// Constraint referente a integridade do email único, ou seja, o e-mail é único.
	private static final String UQ_EMAIL_USUARIO_EMAIL = "UQ_EMAIL_USUARIO_EMAIL";
	
	// Constraint referente a integridade do e-mail principal com o usuário, ou seja, não é possível que um usuário tenha mais de um e-mail principal.
	private static final String UQ_EMAIL_USUARIO_PRINCIPAL_ID_USUARIO = "UQ_EMAIL_USUARIO_PRINCIPAL_ID_USUARIO";
	
	// Constraint referente a integridade do e-mail com o usuário, ou seja, um email usuário deve estar associado a um usuário.
	private static final String FK_EMAIL_USUARIO_ID_USUARIO_USUARIO_ID = "FK_EMAIL_USUARIO_ID_USUARIO_USUARIO_ID";
		
	
	public EmailUsuario buscarEmailPrincipalPorUsuario(Usuario usuario, Connection con) throws SQLException, Exception{
		StringBuffer query = new StringBuffer();
		query.append("SELECT * FROM ");
		query.append(EMAIL_USUARIO);
		query.append(" WHERE ");
		query.append(ID_USUARIO);
		query.append(" = ? ");
		query.append("AND ");
		query.append(PRINCIPAL);
		query.append("= ?");
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		EmailUsuario email = null;
		try {
			preparedStatement = con.prepareStatement(query.toString());
			preparedStatement.setInt(1, usuario.getId());
			preparedStatement.setBoolean(2, Boolean.TRUE);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()){
				email = new EmailUsuario();
				email.setId(resultSet.getInt(ID));
				email.setEmail(resultSet.getString(EMAIL));
				email.setPrincipal(resultSet.getBoolean(PRINCIPAL));
				email.setIdUsuario(resultSet.getInt(ID_USUARIO));
			}
		} catch (SQLException e) {
			con.rollback();
			throw e;
		} catch (Exception e) {
			con.rollback();
			throw e;
		}	
		return email;
	}
	
	public EmailUsuario buscarEmailPrincipalPorUsuario(Usuario usuario) throws SQLException, Exception{
		StringBuffer query = new StringBuffer();
		query.append("SELECT * FROM ");
		query.append(EMAIL_USUARIO);
		query.append(" WHERE ");
		query.append(ID_USUARIO);
		query.append(" = ? ");
		query.append("AND ");
		query.append(PRINCIPAL);
		query.append("= ?");		
		Connection con =C3P0DataSource.getInstance().getConnection();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		EmailUsuario email = null;
		try {
			preparedStatement = con.prepareStatement(query.toString());
			preparedStatement.setInt(1, usuario.getId());
			preparedStatement.setBoolean(2, Boolean.TRUE);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()){
				email = new EmailUsuario();
				email.setId(resultSet.getInt(ID));
				email.setEmail(resultSet.getString(EMAIL));
				email.setPrincipal(resultSet.getBoolean(PRINCIPAL));
				email.setIdUsuario(resultSet.getInt(ID_USUARIO));
			}
		} catch (SQLException e) {
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
		return email;
	}
	
	
	public EmailUsuario buscarEmail(String emailUsuario) throws SQLException, Exception{
		StringBuffer query = new StringBuffer();
		query.append("SELECT * FROM ");
		query.append(EMAIL_USUARIO);
		query.append(" WHERE ");
		query.append(EMAIL);
		query.append(" = ? ");				
		Connection con =C3P0DataSource.getInstance().getConnection();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		EmailUsuario email = null;
		try {
			preparedStatement = con.prepareStatement(query.toString());
			preparedStatement.setString(1, emailUsuario);			
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()){
				email = new EmailUsuario();
				email.setId(resultSet.getInt(ID));
				email.setEmail(resultSet.getString(EMAIL));
				email.setPrincipal(resultSet.getBoolean(PRINCIPAL));
				email.setIdUsuario(resultSet.getInt(ID_USUARIO));
			}
		} catch (SQLException e) {
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
		return email;
	}
	
	public List<EmailUsuario> buscarPorUsuario(Usuario usuario) throws SQLException, Exception{
		StringBuffer query = new StringBuffer();
		query.append("SELECT * FROM ");
		query.append(EMAIL_USUARIO);
		query.append(" WHERE ");
		query.append(ID_USUARIO);
		query.append(" = ? ");
		query.append(";");		
		Connection con =C3P0DataSource.getInstance().getConnection();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		List<EmailUsuario> listaEmail = new ArrayList<EmailUsuario>();
		EmailUsuario email = null;
		try {
			preparedStatement = con.prepareStatement(query.toString());
			preparedStatement.setInt(1, usuario.getId());	
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()){
				email = new EmailUsuario();
				email.setId(resultSet.getInt(ID));
				email.setEmail(resultSet.getString(EMAIL));
				email.setPrincipal(resultSet.getBoolean(PRINCIPAL));
				email.setIdUsuario(resultSet.getInt(ID_USUARIO));
				listaEmail.add(email);
			}
		} catch (SQLException e) {
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
		return listaEmail;		
	}
	
	public void salvar(EmailUsuario emailUsuario, Connection con) throws SQLException, BusinessException, Exception{		
		PreparedStatement statement = null;		
		try {				
			StringBuffer query = new StringBuffer();
			query.append("INSERT INTO "); 
			query.append(EMAIL_USUARIO); 
			query.append("(");
			query.append(EMAIL); 
			query.append(",");
			query.append(PRINCIPAL); 
			query.append(",");
			query.append(ID_USUARIO);
			query.append(") ");
			query.append("VALUES(?,?,?)");
			statement = con.prepareStatement(query.toString());
			SQLUtil.setValorPpreparedStatement(statement, 1, emailUsuario.getEmail(), java.sql.Types.VARCHAR);
			SQLUtil.setValorPpreparedStatement(statement, 2, emailUsuario.getPrincipal(), java.sql.Types.BOOLEAN);
			SQLUtil.setValorPpreparedStatement(statement, 3, emailUsuario.getIdUsuario(), java.sql.Types.INTEGER);
			statement.execute();			
		} catch (SQLException e) {
			con.rollback();
			if (e.getMessage().contains(UQ_EMAIL_USUARIO_EMAIL)){
				throw new BusinessException("msg.condomino.emailUnico");
			}else if (e.getMessage().contains(UQ_EMAIL_USUARIO_PRINCIPAL_ID_USUARIO)){
				throw new BusinessException("msg.condomino.emailPrincipalUnicoUsuario");
			}else{
				throw e;
			}			
		} catch (Exception e){
			con.rollback();
			throw e;
		}
	}
	 
	public void atualizar(EmailUsuario emailUsuario,  Connection con) throws SQLException, BusinessException, Exception{
		StringBuffer query = new StringBuffer();
		query.append("UPDATE ");
		query.append(EMAIL_USUARIO);
		query.append(" SET ");
		query.append(EMAIL);
		query.append("= ?, ");
		query.append(ID_USUARIO);
		query.append("= ?, ");
		query.append(PRINCIPAL);
		query.append("= ? ");
		query.append("WHERE ");
		query.append(ID);
		query.append("= ?");
		PreparedStatement statement = null;
		try {
			statement = con.prepareStatement(query.toString());			
			SQLUtil.setValorPpreparedStatement(statement, 1, emailUsuario.getEmail(), java.sql.Types.VARCHAR);
			SQLUtil.setValorPpreparedStatement(statement, 2, emailUsuario.getIdUsuario(), java.sql.Types.INTEGER);
			SQLUtil.setValorPpreparedStatement(statement, 3, emailUsuario.getPrincipal(), java.sql.Types.BOOLEAN);
			SQLUtil.setValorPpreparedStatement(statement, 4, emailUsuario.getId(), java.sql.Types.INTEGER);
			statement.executeUpdate();
		} catch (SQLException e) {
			con.rollback();
			if (e.getMessage().contains(UQ_EMAIL_USUARIO_EMAIL)){
				throw new BusinessException("msg.condomino.emailUnico");
			}else if (e.getMessage().contains(UQ_EMAIL_USUARIO_PRINCIPAL_ID_USUARIO)){
				throw new BusinessException("msg.condomino.emailPrincipalUnicoUsuario");
			}else{
				throw e;
			}
		} catch (Exception e){
			con.rollback();
			throw e;
		}
	}
	
	public void excluir(EmailUsuario emailUsuario,  Connection con) throws SQLException, BusinessException, Exception{
		StringBuffer query = new StringBuffer();
		query.append("DELETE FROM ");
		query.append(EMAIL_USUARIO);
		query.append(" WHERE ");
		query.append(ID);
		query.append("= ?");
		PreparedStatement statement = null;
		try {			
			statement = con.prepareStatement(query.toString());
			SQLUtil.setValorPpreparedStatement(statement, 1, emailUsuario.getId(), java.sql.Types.INTEGER);
			statement.executeUpdate();
		} catch (SQLException e) {
			con.rollback();
			if (e.getMessage().contains(FK_EMAIL_USUARIO_ID_USUARIO_USUARIO_ID)){
				throw new BusinessException("msg.condomino.excluirEmailUsuarioAssociado");
			}else{
				throw e;
			}
		} catch (Exception e) {
			con.rollback();
			throw e;
		}			
	}
}
