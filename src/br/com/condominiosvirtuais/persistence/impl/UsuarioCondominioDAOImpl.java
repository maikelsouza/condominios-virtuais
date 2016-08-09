package br.com.condominiosvirtuais.persistence.impl;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import br.com.condominiosvirtuais.entity.Usuario;
import br.com.condominiosvirtuais.entity.UsuarioCondominio;
import br.com.condominiosvirtuais.persistence.UsuarioCondominioDAO;

public class UsuarioCondominioDAOImpl implements UsuarioCondominioDAO, Serializable {
	
	private static final long serialVersionUID = 1L;

	private static Logger logger = Logger.getLogger(UsuarioCondominioDAOImpl.class);
	
	private static final String USUARIO_CONDOMINIO = "USUARIO_CONDOMINIO";

	private static final String ID = "ID";
	
	private static final String ID_USUARIO = "ID_USUARIO";
	
	private static final String ID_CONDOMINIO = "ID_CONDOMINIO";
	
	public List<UsuarioCondominio> buscarListaPorUsuario(Usuario usuario) throws SQLException, Exception{		
		StringBuffer query = new StringBuffer();
		query.append("SELECT * FROM ");
		query.append(USUARIO_CONDOMINIO);
		query.append(" WHERE ");
		query.append(ID_USUARIO);
		query.append(" = ? ");		
		query.append(";");
		Connection con = Conexao.getConexao();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		List<UsuarioCondominio> listaUsuarioCondominio = new ArrayList<UsuarioCondominio>();
		UsuarioCondominio usuarioCondominio = null;
		try {
			preparedStatement = con.prepareStatement(query.toString());
			preparedStatement.setInt(1, usuario.getId());
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()){
				usuarioCondominio = new UsuarioCondominio();
				usuarioCondominio.setId(resultSet.getInt(ID));
				usuarioCondominio.setIdCondominio(resultSet.getInt(ID_CONDOMINIO));
				usuarioCondominio.setIdUsuario(resultSet.getInt(ID_USUARIO));				
				listaUsuarioCondominio.add(usuarioCondominio);
			}
		} catch (SQLException e) {			
			throw e;
		} catch (Exception e) {					
			throw e;	
		}finally{
			try{	
				preparedStatement.close();
				con.close();
			}catch (SQLException e){
				logger.error("erro sqlstate "+e.getSQLState(), e);
			}
		}			
		return listaUsuarioCondominio;
	}
	
	public List<UsuarioCondominio> buscarListaPorUsuario(Usuario usuario, Connection con) throws SQLException, Exception{		
		StringBuffer query = new StringBuffer();
		query.append("SELECT * FROM ");
		query.append(USUARIO_CONDOMINIO);
		query.append(" WHERE ");
		query.append(ID_USUARIO);
		query.append(" = ? ");		
		query.append(";");		
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		List<UsuarioCondominio> listaUsuarioCondominio = new ArrayList<UsuarioCondominio>();
		UsuarioCondominio usuarioCondominio = null;
		try {
			preparedStatement = con.prepareStatement(query.toString());
			preparedStatement.setInt(1, usuario.getId());
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()){
				usuarioCondominio = new UsuarioCondominio();
				usuarioCondominio.setId(resultSet.getInt(ID));
				usuarioCondominio.setIdCondominio(resultSet.getInt(ID_CONDOMINIO));
				usuarioCondominio.setIdUsuario(resultSet.getInt(ID_USUARIO));				
				listaUsuarioCondominio.add(usuarioCondominio);
			}
		} catch (SQLException e) {
			con.rollback();
			throw e;
		} catch (Exception e) {
			con.rollback();
			throw e;
		}
		return listaUsuarioCondominio;
	}
	
	
	public void salvarUsuarioCondominio(UsuarioCondominio usuarioCondominio, Connection con) throws SQLException, Exception{
		StringBuffer query = new StringBuffer();
		query.append("INSERT INTO "); 
		query.append(USUARIO_CONDOMINIO);
		query.append("("); 
		query.append(ID_USUARIO); 
		query.append(",");
		query.append(ID_CONDOMINIO);	
		query.append(") ");
		query.append("VALUES(?,?)");		
		PreparedStatement statement = null;
		try {			
			statement = con.prepareStatement(query.toString());
			statement.setInt(1,usuarioCondominio.getIdUsuario());
			statement.setInt(2,usuarioCondominio.getIdCondominio());						
			statement.executeUpdate();			
		} catch (SQLException e) {
			con.rollback();
			throw e;
		} catch (Exception e) {
			con.rollback();
			throw e;
		}	
	}
	
	public void excluirPorUsuario(Usuario usuario, Connection con) throws SQLException, Exception{
		StringBuffer query = new StringBuffer();		
		PreparedStatement statement = null;
		try {			
			query.append("DELETE FROM ");
			query.append(USUARIO_CONDOMINIO);
			query.append(" WHERE ");		
			query.append(ID_USUARIO);
			query.append(" = ?");
			statement = con.prepareStatement(query.toString());
			statement.setInt(1, usuario.getId());
			statement.executeUpdate();
		} catch (SQLException e) {
			con.rollback();
			throw e;
		} catch (Exception e) {
			con.rollback();
			throw e;
		}	
	}
	
	public void atualizarPorId(UsuarioCondominio usuarioCondominio, Connection con) throws SQLException, Exception{
		StringBuffer query = new StringBuffer();		
		PreparedStatement statement = null;
		try {		
			query.append("UPDATE ");
			query.append(USUARIO_CONDOMINIO);
			query.append(" SET ");
			query.append(ID_CONDOMINIO); 
			query.append(" = ?,");
			query.append(ID_USUARIO); 
			query.append(" = ?");
			query.append("WHERE ");					
			query.append(ID);
			query.append(" = ?");
			statement = con.prepareStatement(query.toString());
			statement.setInt(1, usuarioCondominio.getIdCondominio());
			statement.setInt(2, usuarioCondominio.getIdUsuario());
			statement.setInt(3, usuarioCondominio.getId());
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