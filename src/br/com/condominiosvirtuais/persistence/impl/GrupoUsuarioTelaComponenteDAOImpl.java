package br.com.condominiosvirtuais.persistence.impl;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import br.com.condominiosvirtuais.entity.GrupoUsuarioTelaComponente;
import br.com.condominiosvirtuais.persistence.GrupoUsuarioTelaComponenteDAO;
import br.com.condominiosvirtuais.util.SQLUtil;

public class GrupoUsuarioTelaComponenteDAOImpl implements GrupoUsuarioTelaComponenteDAO, Serializable {

	
	private static final long serialVersionUID = 1L;
	
	private static Logger logger = Logger.getLogger(GrupoUsuarioTelaComponenteDAOImpl.class);
	
	private static final String  GRUPO_USUARIO_TELA_COMPONENTE = "GRUPO_USUARIO_TELA_COMPONENTE";	
	
	private static final String ID = "ID";	
	
	private static final String ID_GRUPO_USUARIO = "ID_GRUPO_USUARIO";
	
	private static final String ID_TELA = "ID_TELA";
	
	private static final String ID_COMPONENTE = "ID_COMPONENTE";
	

	@Override
	public void excluirPorIdGrupoUsuario(Integer idGrupoUsuario, Connection con) throws SQLException, Exception {
		StringBuffer query = new StringBuffer();
		PreparedStatement statement = null;
		try {
			query.append("DELETE FROM ");
			query.append(GRUPO_USUARIO_TELA_COMPONENTE);
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
	public void salvar(GrupoUsuarioTelaComponente grupoUsuarioTelaComponente, Connection con)
			throws SQLException, Exception {
		StringBuffer query = new StringBuffer();
		query.append("INSERT INTO "); 
		query.append(GRUPO_USUARIO_TELA_COMPONENTE);
		query.append("("); 
		query.append(ID_TELA); 
		query.append(",");		
		query.append(ID_COMPONENTE); 	
		query.append(",");
		query.append(ID_GRUPO_USUARIO);
		query.append(") ");
		query.append("VALUES(?,?,?)");
		PreparedStatement statement = null;
		try {			
			statement = con.prepareStatement(query.toString());
			SQLUtil.setValorPpreparedStatement(statement, 1, grupoUsuarioTelaComponente.getIdTela(), java.sql.Types.INTEGER);			
			SQLUtil.setValorPpreparedStatement(statement, 2, grupoUsuarioTelaComponente.getIdComponente(), java.sql.Types.INTEGER);
			SQLUtil.setValorPpreparedStatement(statement, 3, grupoUsuarioTelaComponente.getIdGrupoUsuario(), java.sql.Types.INTEGER);			
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
	public List<GrupoUsuarioTelaComponente> buscarPorIdGrupoUsuarioEIdTela(Integer idGrupoUsuario, Integer idTela,
			Connection con) throws SQLException, Exception {
		StringBuffer query = new StringBuffer();
		query.append("SELECT * FROM ");
		query.append(GRUPO_USUARIO_TELA_COMPONENTE);
		query.append(" WHERE ");
		query.append(ID_GRUPO_USUARIO);
		query.append(" = ?");
		query.append(" AND ");
		query.append(ID_TELA);
		query.append(" = ?");
		PreparedStatement statement = null;
		ResultSet resultSet = null;		
		GrupoUsuarioTelaComponente grupoUsuarioTelaComponente = null;
		List<GrupoUsuarioTelaComponente> listaTelaComponente = new ArrayList<GrupoUsuarioTelaComponente>();
		try {
			statement = con.prepareStatement(query.toString());
			SQLUtil.setValorPpreparedStatement(statement, 1,idGrupoUsuario, java.sql.Types.INTEGER);
			SQLUtil.setValorPpreparedStatement(statement, 2,idTela, java.sql.Types.INTEGER);
			resultSet = statement.executeQuery();
			while(resultSet.next()){
				grupoUsuarioTelaComponente = new GrupoUsuarioTelaComponente();
				grupoUsuarioTelaComponente.setId((Integer) SQLUtil.getValorResultSet(resultSet, ID, java.sql.Types.INTEGER));
				grupoUsuarioTelaComponente.setIdGrupoUsuario((Integer) SQLUtil.getValorResultSet(resultSet, ID_GRUPO_USUARIO, java.sql.Types.INTEGER));
				grupoUsuarioTelaComponente.setIdComponente((Integer) SQLUtil.getValorResultSet(resultSet, ID_COMPONENTE, java.sql.Types.INTEGER));
				grupoUsuarioTelaComponente.setIdTela((Integer) SQLUtil.getValorResultSet(resultSet, ID_TELA, java.sql.Types.INTEGER));				
				listaTelaComponente.add(grupoUsuarioTelaComponente);
			}
		}catch (SQLException e) {
			con.rollback();
			throw e;
		}catch (Exception e) {		
			con.rollback();
			throw e;
		}
		return listaTelaComponente;
	}

}
