package br.com.condominiosvirtuais.persistence.impl;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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
	
	private static final String ID_ABA = "ID_COMPONENTE";
	

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
		// TODO Auto-generated method stub

	}

}
