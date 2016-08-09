package br.com.condominiosvirtuais.service.impl;

import java.io.Serializable;
import java.sql.SQLException;

import javax.inject.Inject;

import br.com.condominiosvirtuais.entity.GrupoUsuario;
import br.com.condominiosvirtuais.persistence.GrupoUsuarioDAO;
import br.com.condominiosvirtuais.service.GrupoUsuarioService;

public class GrupoUsuarioServiceImpl implements GrupoUsuarioService, Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Inject
	private GrupoUsuarioDAO grupoUsuarioDAO = null;
	
	public GrupoUsuario buscarPorIdUsuario(Integer idUsuario) throws SQLException, Exception {		
		return this.grupoUsuarioDAO.buscarPorIdUsuario(idUsuario);
	}

	public GrupoUsuarioDAO getGrupoUsuarioDAO() {
		return grupoUsuarioDAO;
	}

	public void setGrupoUsuarioDAO(GrupoUsuarioDAO grupoUsuarioDAO) {
		this.grupoUsuarioDAO = grupoUsuarioDAO;
	}	

}
