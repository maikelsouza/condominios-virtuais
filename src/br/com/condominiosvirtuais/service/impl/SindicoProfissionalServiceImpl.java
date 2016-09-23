package br.com.condominiosvirtuais.service.impl;

import java.io.Serializable;
import java.sql.SQLException;

import javax.inject.Inject;

import br.com.condominiosvirtuais.entity.SindicoProfissional;
import br.com.condominiosvirtuais.persistence.impl.SindicoProfissionalDAOImpl;
import br.com.condominiosvirtuais.service.SindicoProfissionalService;

public class SindicoProfissionalServiceImpl implements SindicoProfissionalService, Serializable {

	private static final long serialVersionUID = 1L;
	
	
	@Inject
	private SindicoProfissionalDAOImpl sindicoProfissionalDAO;

	@Override
	public void salvar(SindicoProfissional sindicoProfissional) throws SQLException, Exception {
		this.sindicoProfissionalDAO.salvar(sindicoProfissional);
		
	}

}
