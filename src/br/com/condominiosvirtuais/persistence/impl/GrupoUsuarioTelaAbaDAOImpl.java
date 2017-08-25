package br.com.condominiosvirtuais.persistence.impl;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import br.com.condominiosvirtuais.entity.GrupoUsuarioTelaAba;
import br.com.condominiosvirtuais.persistence.GrupoUsuarioTelaAbaDAO;
import br.com.condominiosvirtuais.util.SQLUtil;

public class GrupoUsuarioTelaAbaDAOImpl implements GrupoUsuarioTelaAbaDAO, Serializable {

	private static final long serialVersionUID = 1L;
	
	private static Logger logger = Logger.getLogger(GrupoUsuarioTelaAbaDAOImpl.class);
	
	private static final String  GRUPO_USUARIO_TELA_ABA = "GRUPO_USUARIO_TELA_ABA";	
	
	private static final String ID = "ID";	
	
	private static final String ID_GRUPO_USUARIO = "ID_GRUPO_USUARIO";
	
	private static final String ID_TELA = "ID_TELA";
	
	private static final String ID_ABA = "ID_ABA";
	
	
	

	@Override
	public void salvar(GrupoUsuarioTelaAba grupoUsuarioTelaAba, Connection con) throws SQLException, Exception {
		StringBuffer query = new StringBuffer();
		query.append("INSERT INTO "); 
		query.append(GRUPO_USUARIO_TELA_ABA);
		query.append("("); 
		query.append(ID_TELA); 
		query.append(",");		
		query.append(ID_ABA); 	
		query.append(",");
		query.append(ID_GRUPO_USUARIO);
		query.append(") ");
		query.append("VALUES(?,?,?)");
		PreparedStatement statement = null;
		try {			
			statement = con.prepareStatement(query.toString());
			SQLUtil.setValorPpreparedStatement(statement, 1, grupoUsuarioTelaAba.getIdTela(), java.sql.Types.INTEGER);			
			SQLUtil.setValorPpreparedStatement(statement, 2, grupoUsuarioTelaAba.getIdAba(), java.sql.Types.INTEGER);
			SQLUtil.setValorPpreparedStatement(statement, 3, grupoUsuarioTelaAba.getIdGrupoUsuario(), java.sql.Types.INTEGER);			
			statement.executeUpdate();
		}catch (SQLException e) {
			con.rollback();
			throw e;
		} catch (Exception e) {
			con.rollback();
			throw e; 
		}		
		
	}	

	@Override
	public List<GrupoUsuarioTelaAba> buscarPorIdGrupoUsuarioEIdTela(Integer idGrupoUsuario, Integer idTela, Connection con) throws SQLException, Exception {
		StringBuffer query = new StringBuffer();
		query.append("SELECT * FROM ");
		query.append(GRUPO_USUARIO_TELA_ABA);
		query.append(" WHERE ");
		query.append(ID_GRUPO_USUARIO);
		query.append(" = ?");
		query.append(" AND ");
		query.append(ID_TELA);
		query.append(" = ?");
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		GrupoUsuarioTelaAba telaAba = null;	
		List<GrupoUsuarioTelaAba> listaTelaAba = new ArrayList<GrupoUsuarioTelaAba>();
		try {
			statement = con.prepareStatement(query.toString());
			SQLUtil.setValorPpreparedStatement(statement, 1,idGrupoUsuario, java.sql.Types.INTEGER);
			SQLUtil.setValorPpreparedStatement(statement, 2,idTela, java.sql.Types.INTEGER);
			resultSet = statement.executeQuery();
			while(resultSet.next()){
				telaAba = new GrupoUsuarioTelaAba();
				telaAba.setId((Integer) SQLUtil.getValorResultSet(resultSet, ID, java.sql.Types.INTEGER));
				telaAba.setIdGrupoUsuario((Integer) SQLUtil.getValorResultSet(resultSet, ID_GRUPO_USUARIO, java.sql.Types.INTEGER));
				telaAba.setIdAba((Integer) SQLUtil.getValorResultSet(resultSet, ID_ABA, java.sql.Types.INTEGER));
				telaAba.setIdTela((Integer) SQLUtil.getValorResultSet(resultSet, ID_TELA, java.sql.Types.INTEGER));				
				listaTelaAba.add(telaAba);
			}
		}catch (SQLException e) {
			con.rollback();
			throw e;
		}catch (Exception e) {		
			con.rollback();
			throw e;
		}
		return listaTelaAba;
	}
	
	@Override
	public List<GrupoUsuarioTelaAba> buscarPorIdTela(Integer idTela, Connection con) throws SQLException, Exception {
		StringBuffer query = new StringBuffer();
		query.append("SELECT * FROM ");
		query.append(GRUPO_USUARIO_TELA_ABA);
		query.append(" WHERE ");
		query.append(ID_TELA);
		query.append(" = ?");
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		GrupoUsuarioTelaAba telaAba = null;	
		List<GrupoUsuarioTelaAba> listaTelaAba = new ArrayList<GrupoUsuarioTelaAba>();
		try {
			statement = con.prepareStatement(query.toString());
			SQLUtil.setValorPpreparedStatement(statement, 1,idTela, java.sql.Types.INTEGER);
			resultSet = statement.executeQuery();
			while(resultSet.next()){
				telaAba = new GrupoUsuarioTelaAba();
				telaAba.setId((Integer) SQLUtil.getValorResultSet(resultSet, ID, java.sql.Types.INTEGER));
				telaAba.setIdGrupoUsuario((Integer) SQLUtil.getValorResultSet(resultSet, ID_GRUPO_USUARIO, java.sql.Types.INTEGER));
				telaAba.setIdAba((Integer) SQLUtil.getValorResultSet(resultSet, ID_ABA, java.sql.Types.INTEGER));
				telaAba.setIdTela((Integer) SQLUtil.getValorResultSet(resultSet, ID_TELA, java.sql.Types.INTEGER));				
				listaTelaAba.add(telaAba);
			}
		}catch (SQLException e) {
			con.rollback();
			throw e;
		}catch (Exception e) {		
			con.rollback();
			throw e;
		}
		return listaTelaAba;
	}

	@Override
	public void excluirPorIdGrupoUsuario(Integer idGrupoUsuario, Connection con) throws SQLException, Exception {
		StringBuffer query = new StringBuffer();
		PreparedStatement statement = null;
		try {
			query.append("DELETE FROM ");
			query.append(GRUPO_USUARIO_TELA_ABA);
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

}
