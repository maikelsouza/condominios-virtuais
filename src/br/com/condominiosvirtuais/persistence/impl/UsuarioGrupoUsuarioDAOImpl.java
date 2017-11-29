package br.com.condominiosvirtuais.persistence.impl;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import br.com.condominiosvirtuais.entity.UsuarioGrupoUsuario;
import br.com.condominiosvirtuais.persistence.UsuarioGrupoUsuarioDAO;
import br.com.condominiosvirtuais.util.SQLUtil;

public class UsuarioGrupoUsuarioDAOImpl implements UsuarioGrupoUsuarioDAO, Serializable {

	private static final long serialVersionUID = 1L;
	
	private static Logger logger = Logger.getLogger(UsuarioGrupoUsuarioDAOImpl.class);
	
	private static final String USUARIO_GRUPO_USUARIO = "USUARIO_GRUPO_USUARIO";
	
	private static final String ID = "ID";
	
	private static final String ID_USUARIO = "ID_USUARIO";
	
	private static final String ID_GRUPO_USUARIO = "ID_GRUPO_USUARIO";

	@Override
	public List<UsuarioGrupoUsuario> buscarPorIdUsuario(Integer idUsuario) throws SQLException, Exception {
		StringBuffer query = new StringBuffer();
		query.append("SELECT * FROM ");
		query.append(USUARIO_GRUPO_USUARIO);
		query.append(" WHERE ");
		query.append(ID_USUARIO);
		query.append(" = ?");
		query.append(";");		
		Connection con = Conexao.getConexao();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;		
		List<UsuarioGrupoUsuario> listaUsuarioGrupoUsuarios = new ArrayList<UsuarioGrupoUsuario>();
		UsuarioGrupoUsuario usuarioGrupoUsuario = null;
		try {
			preparedStatement = con.prepareStatement(query.toString());
			SQLUtil.setValorPpreparedStatement(preparedStatement, 1, idUsuario, java.sql.Types.INTEGER);	
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()){
				usuarioGrupoUsuario = new UsuarioGrupoUsuario();
				usuarioGrupoUsuario.setId((Integer) SQLUtil.getValorResultSet(resultSet, ID, java.sql.Types.INTEGER));
				usuarioGrupoUsuario.setIdUsuario((Integer) SQLUtil.getValorResultSet(resultSet, ID_USUARIO, java.sql.Types.INTEGER));
				usuarioGrupoUsuario.setIdGrupoUsuario((Integer) SQLUtil.getValorResultSet(resultSet, ID_GRUPO_USUARIO, java.sql.Types.INTEGER));
				listaUsuarioGrupoUsuarios.add(usuarioGrupoUsuario);
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
		return listaUsuarioGrupoUsuarios;
	}
	
	@Override
	public List<UsuarioGrupoUsuario> buscarPorIdUsuario(Integer idUsuario, Connection con) throws SQLException, Exception {
		StringBuffer query = new StringBuffer();
		query.append("SELECT * FROM ");
		query.append(USUARIO_GRUPO_USUARIO);
		query.append(" WHERE ");
		query.append(ID_USUARIO);
		query.append(" = ?");
		query.append(";");
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;		
		List<UsuarioGrupoUsuario> listaUsuarioGrupoUsuarios = new ArrayList<UsuarioGrupoUsuario>();
		UsuarioGrupoUsuario usuarioGrupoUsuario = null;
		try {
			preparedStatement = con.prepareStatement(query.toString());
			SQLUtil.setValorPpreparedStatement(preparedStatement, 1, idUsuario, java.sql.Types.INTEGER);	
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()){
				usuarioGrupoUsuario = new UsuarioGrupoUsuario();
				usuarioGrupoUsuario.setId((Integer) SQLUtil.getValorResultSet(resultSet, ID, java.sql.Types.INTEGER));
				usuarioGrupoUsuario.setIdUsuario((Integer) SQLUtil.getValorResultSet(resultSet, ID_USUARIO, java.sql.Types.INTEGER));
				usuarioGrupoUsuario.setIdGrupoUsuario((Integer) SQLUtil.getValorResultSet(resultSet, ID_GRUPO_USUARIO, java.sql.Types.INTEGER));
				listaUsuarioGrupoUsuarios.add(usuarioGrupoUsuario);
			}
		} catch (SQLException e) {
			throw e;
		} catch (Exception e) {
			throw e;
		}				
		return listaUsuarioGrupoUsuarios;
	}

	@Override
	public void excluirPorIdGrupoUsuario(Integer idGrupoUsuario, Connection con) throws SQLException, Exception {
		StringBuffer query = new StringBuffer();
		PreparedStatement statement = null;
		try {
			query.append("DELETE FROM ");
			query.append(USUARIO_GRUPO_USUARIO);
			query.append(" WHERE ");		
			query.append(ID_GRUPO_USUARIO);
			query.append(" = ?");
			statement = con.prepareStatement(query.toString());
			SQLUtil.setValorPpreparedStatement(statement, 1, idGrupoUsuario, java.sql.Types.INTEGER);
			statement.executeUpdate();
		} catch (SQLException e) {
			con.rollback();
			throw e;
		}catch (Exception e) {
			con.rollback();
			throw e;
		}
		
	}
	
	@Override
	public void excluirPorId(Integer id, Connection con) throws SQLException, Exception {
		StringBuffer query = new StringBuffer();
		PreparedStatement statement = null;
		try {
			query.append("DELETE FROM ");
			query.append(USUARIO_GRUPO_USUARIO);
			query.append(" WHERE ");		
			query.append(ID);
			query.append(" = ?");
			statement = con.prepareStatement(query.toString());
			SQLUtil.setValorPpreparedStatement(statement, 1, id, java.sql.Types.INTEGER);
			statement.executeUpdate();
		} catch (SQLException e) {
			con.rollback();
			throw e;
		}catch (Exception e) {
			con.rollback();
			throw e;
		}
		
	}
	
	@Override
	public void excluirPorIdGrupoUsuarioEIdUsuario(Integer idGrupoUsuario, Integer idUsuario, Connection con) throws SQLException, Exception {
		StringBuffer query = new StringBuffer();
		PreparedStatement statement = null;
		try {
			query.append("DELETE FROM ");
			query.append(USUARIO_GRUPO_USUARIO);
			query.append(" WHERE ");		
			query.append(ID_GRUPO_USUARIO);
			query.append(" = ?");
			query.append(" AND ");
			query.append(ID_USUARIO);
			query.append(" = ?");
			statement = con.prepareStatement(query.toString());
			SQLUtil.setValorPpreparedStatement(statement, 1, idGrupoUsuario, java.sql.Types.INTEGER);
			SQLUtil.setValorPpreparedStatement(statement, 2, idUsuario, java.sql.Types.INTEGER);
			statement.executeUpdate();
		} catch (SQLException e) {
			con.rollback();
			throw e;
		}catch (Exception e) {
			con.rollback();
			throw e;
		}
		
	}

	@Override
	public UsuarioGrupoUsuario buscarPorIdGrupoUsuarioEIdUsuario(Integer idGrupoUsuario, Integer idUsuario, Connection con) throws SQLException, Exception {
		StringBuffer query = new StringBuffer();
		query.append("SELECT * FROM ");
		query.append(USUARIO_GRUPO_USUARIO);
		query.append(" WHERE ");
		query.append(ID_GRUPO_USUARIO);
		query.append(" = ?");
		query.append(" AND ");
		query.append(ID_USUARIO);
		query.append(" = ?");
		query.append(";");
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		UsuarioGrupoUsuario usuarioGrupoUsuario =  new UsuarioGrupoUsuario();;
		try {
			preparedStatement = con.prepareStatement(query.toString());
			SQLUtil.setValorPpreparedStatement(preparedStatement, 1, idGrupoUsuario, java.sql.Types.INTEGER);	
			SQLUtil.setValorPpreparedStatement(preparedStatement, 2, idUsuario, java.sql.Types.INTEGER);	
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()){				
				usuarioGrupoUsuario.setId((Integer) SQLUtil.getValorResultSet(resultSet, ID, java.sql.Types.INTEGER));
				usuarioGrupoUsuario.setIdUsuario((Integer) SQLUtil.getValorResultSet(resultSet, ID_USUARIO, java.sql.Types.INTEGER));
				usuarioGrupoUsuario.setIdGrupoUsuario((Integer) SQLUtil.getValorResultSet(resultSet, ID_GRUPO_USUARIO, java.sql.Types.INTEGER));
			}
		} catch (SQLException e) {
			throw e;
		} catch (Exception e) {
			throw e;
		}				
		return usuarioGrupoUsuario;
	}

	@Override
	public void associar(List<UsuarioGrupoUsuario> listaUsuarioGrupoUsuario, List<UsuarioGrupoUsuario> listaUsuarioGrupoUsuarioOriginalAssociados) throws SQLException, Exception {
		Connection con = Conexao.getConexao();
		PreparedStatement statement = null;
		try {			
			con.setAutoCommit(Boolean.FALSE);
			for (UsuarioGrupoUsuario usuarioGrupoUsuario : listaUsuarioGrupoUsuarioOriginalAssociados) {
				this.excluirPorIdGrupoUsuarioEIdUsuario(usuarioGrupoUsuario.getIdGrupoUsuario(), usuarioGrupoUsuario.getIdUsuario(), con);
			}		
			StringBuffer query = new StringBuffer();
			query.append("INSERT INTO "); 
			query.append(USUARIO_GRUPO_USUARIO);
			query.append("(");
			query.append(ID_USUARIO); 
			query.append(",");
			query.append(ID_GRUPO_USUARIO); 
			query.append(") ");
			query.append("VALUES(?,?)");		
			for (UsuarioGrupoUsuario usuarioGrupoUsuario : listaUsuarioGrupoUsuario) {
				statement = con.prepareStatement(query.toString());
				SQLUtil.setValorPpreparedStatement(statement, 1, usuarioGrupoUsuario.getIdUsuario(), java.sql.Types.INTEGER);
				SQLUtil.setValorPpreparedStatement(statement, 2, usuarioGrupoUsuario.getIdGrupoUsuario(), java.sql.Types.INTEGER);
				statement.execute();
			}
			con.commit();
		} catch (SQLException e) {			
			throw e;
		}catch (Exception e) {
			throw e;		
		}finally{ 
			try {
				SQLUtil.closeStatement(statement);
				SQLUtil.closeConnection(con);				
			} catch (SQLException e) {
				logger.error("erro sqlstate "+e.getSQLState(), e);				
			}
		}
	}
	
	@Override
	public void desassociar(List<UsuarioGrupoUsuario> listaUsuarioGrupoUsuario) throws SQLException, Exception {
		Connection con = Conexao.getConexao();
		PreparedStatement statement = null;
		try {			
			con.setAutoCommit(Boolean.FALSE);
			for (UsuarioGrupoUsuario usuarioGrupoUsuario : listaUsuarioGrupoUsuario) {
				this.excluirPorIdGrupoUsuarioEIdUsuario(usuarioGrupoUsuario.getIdGrupoUsuario(), usuarioGrupoUsuario.getIdUsuario(), con);
			}con.commit();
		} catch (SQLException e) {			
			throw e;
		}catch (Exception e) {
			throw e;		
		}finally{ 
			try {
				SQLUtil.closeStatement(statement);
				SQLUtil.closeConnection(con);				
			} catch (SQLException e) {
				logger.error("erro sqlstate "+e.getSQLState(), e);				
			}
		}
	}

	@Override
	public void salvar(UsuarioGrupoUsuario usuarioGrupoUsuario, Connection con) throws SQLException, Exception {
		StringBuffer query = new StringBuffer();
		try {	
			query.append("INSERT INTO "); 
			query.append(USUARIO_GRUPO_USUARIO);
			query.append("(");
			query.append(ID_USUARIO); 
			query.append(",");
			query.append(ID_GRUPO_USUARIO); 
			query.append(") ");
			query.append("VALUES(?,?)");	
			PreparedStatement preparedStatement = null;
			preparedStatement = con.prepareStatement(query.toString());
			SQLUtil.setValorPpreparedStatement(preparedStatement, 1, usuarioGrupoUsuario.getIdUsuario(), java.sql.Types.INTEGER);
			SQLUtil.setValorPpreparedStatement(preparedStatement, 2, usuarioGrupoUsuario.getIdGrupoUsuario(), java.sql.Types.INTEGER);
			preparedStatement.execute();
		} catch (SQLException e) {		
			con.rollback();
			throw e;
		}catch (Exception e) {
			con.rollback();
			throw e;		
		}
	}
	
	@Override
	public void atualizar(UsuarioGrupoUsuario usuarioGrupoUsuario, Connection con) throws SQLException, Exception {
		StringBuffer query = new StringBuffer();
		try {	
			query.append("UPDATE ");
			query.append(USUARIO_GRUPO_USUARIO);
			query.append(" SET ");
			query.append(ID_USUARIO); 
			query.append(" = ?,");
			query.append(ID_GRUPO_USUARIO); 
			query.append(" = ? ");
			query.append("WHERE ");					
			query.append(ID);
			query.append(" = ?");
			PreparedStatement preparedStatement = null;
			preparedStatement = con.prepareStatement(query.toString());
			SQLUtil.setValorPpreparedStatement(preparedStatement, 1, usuarioGrupoUsuario.getIdUsuario(), java.sql.Types.INTEGER);
			SQLUtil.setValorPpreparedStatement(preparedStatement, 2, usuarioGrupoUsuario.getIdGrupoUsuario(), java.sql.Types.INTEGER);
			SQLUtil.setValorPpreparedStatement(preparedStatement, 3, usuarioGrupoUsuario.getId(), java.sql.Types.INTEGER);
			preparedStatement.execute();
		} catch (SQLException e) {		
			con.rollback();
			throw e;
		}catch (Exception e) {
			con.rollback();
			throw e;		
		}
	}
	
	

}
