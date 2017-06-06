package br.com.condominiosvirtuais.persistence.impl;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;

import br.com.condominiosvirtuais.entity.GrupoUsuarioTela;
import br.com.condominiosvirtuais.persistence.GrupoUsuarioTelaDAO;

public class GrupoUsuarioTelaDAOImpl implements GrupoUsuarioTelaDAO, Serializable {

	private static final long serialVersionUID = 1L;
	
	private static Logger logger = Logger.getLogger(GrupoUsuarioTelaDAOImpl.class);	
	
	private static final String GRUPO_USUARIO_TELA = "GRUPO_USUARIO_TELA";
	
	private static final String ID = "ID";
	
	private static final String ACAO = "ACAO";
	
	private static final String ID_GRUPO_USUARIO = "ID_GRUPO_USUARIO";
	
	private static final String ID_TELA = "ID_TELA";

	@Override
	public List<GrupoUsuarioTela> buscarPorIdGrupoUsuario(Integer idGrupoUsuario) throws SQLException, Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void salvar(GrupoUsuarioTela grupoUsuarioTela) throws SQLException, Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void excluir(Integer idGrupoUsuarioTela) throws SQLException, Exception {
		// TODO Auto-generated method stub
		
	}
	
	
	

}
