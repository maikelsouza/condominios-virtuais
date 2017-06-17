package br.com.condominiosvirtuais.persistence.impl;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.apache.log4j.Logger;

import br.com.condominiosvirtuais.entity.GrupoUsuarioTela;
import br.com.condominiosvirtuais.persistence.GrupoUsuarioTelaDAO;
import br.com.condominiosvirtuais.persistence.TelaAbaTelaDAO;
import br.com.condominiosvirtuais.util.SQLUtil;

public class GrupoUsuarioTelaDAOImpl implements GrupoUsuarioTelaDAO, Serializable {

	private static final long serialVersionUID = 1L;
	
	private static Logger logger = Logger.getLogger(GrupoUsuarioTelaDAOImpl.class);	
	
	private static final String GRUPO_USUARIO_TELA = "GRUPO_USUARIO_TELA";
	
	private static final String ID = "ID";
	
	private static final String ACAO = "ACAO";
	
	private static final String ID_GRUPO_USUARIO = "ID_GRUPO_USUARIO";
	
	private static final String ID_TELA = "ID_TELA";
	
	@Inject
	private TelaAbaTelaDAO telaAbaTelaDAO; 
	

	@Override
	public List<GrupoUsuarioTela> buscarPorIdGrupoUsuario(Integer idGrupoUsuario) throws SQLException, Exception {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public List<GrupoUsuarioTela> buscarPorIdGrupoUsuario(Integer idGrupoUsuario, Connection con) throws SQLException, Exception {
		StringBuffer query = new StringBuffer();
		query.append("SELECT * FROM ");
		query.append(GRUPO_USUARIO_TELA);
		query.append(" WHERE ");
		query.append(ID_GRUPO_USUARIO);
		query.append(" = ?");
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		GrupoUsuarioTela grupoUsuarioTela = null;	
		List<GrupoUsuarioTela> listaGrupoUsuarioTela = new ArrayList<GrupoUsuarioTela>();
		try {
			statement = con.prepareStatement(query.toString());
			SQLUtil.setValorPpreparedStatement(statement, 1,idGrupoUsuario, java.sql.Types.INTEGER);
			resultSet = statement.executeQuery();
			while(resultSet.next()){
				grupoUsuarioTela = new GrupoUsuarioTela();
				grupoUsuarioTela.setId((Integer) SQLUtil.getValorResultSet(resultSet, ID, java.sql.Types.INTEGER));
				grupoUsuarioTela.setIdGrupoUsuario((Integer) SQLUtil.getValorResultSet(resultSet, ID_GRUPO_USUARIO, java.sql.Types.INTEGER));
				grupoUsuarioTela.setIdTela((Integer) SQLUtil.getValorResultSet(resultSet, ID_TELA, java.sql.Types.INTEGER));
				grupoUsuarioTela.setAcao(String.valueOf(SQLUtil.getValorResultSet(resultSet, ACAO, java.sql.Types.VARCHAR)));
				listaGrupoUsuarioTela.add(grupoUsuarioTela);
			}
		}catch (SQLException e) {
			con.rollback();
			throw e;
		}catch (Exception e) {		
			con.rollback();
			throw e;
		}	
		return listaGrupoUsuarioTela;
	}

	@Override
	public void salvar(GrupoUsuarioTela grupoUsuarioTela) throws SQLException, Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void excluir(Integer idGrupoUsuarioTela) throws SQLException, Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void excluirPorIdGrupoUsuario(Integer idGrupoUsuario, Connection con) throws SQLException, Exception {
		StringBuffer query = new StringBuffer();
		PreparedStatement statement = null;
		try {
			List<GrupoUsuarioTela> listaGrupoUsuarioTela = this.buscarPorIdGrupoUsuario(idGrupoUsuario,con);
			for (GrupoUsuarioTela grupoUsuarioTela : listaGrupoUsuarioTela) {
				this.telaAbaTelaDAO.excluirPorIdTela(grupoUsuarioTela.getIdTela(), con);
			}
			query.append("DELETE FROM ");
			query.append(GRUPO_USUARIO_TELA);
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
