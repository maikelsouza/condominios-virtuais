package br.com.condominiosvirtuais.service.impl;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

import javax.inject.Inject;

import br.com.condominiosvirtuais.entity.ResponsavelObra;
import br.com.condominiosvirtuais.persistence.impl.ResponsavelObraDAOImpl;
import br.com.condominiosvirtuais.service.ResponsavelObraService;

public class ResponsavelObraServiceImpl implements ResponsavelObraService, Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private ResponsavelObraDAOImpl responsavelObraDAO;

	@Override
	public List<ResponsavelObra> buscarPorNome(String nome) throws SQLException, Exception {		
		return this.responsavelObraDAO.buscarPorNome(nome);
	}

}
