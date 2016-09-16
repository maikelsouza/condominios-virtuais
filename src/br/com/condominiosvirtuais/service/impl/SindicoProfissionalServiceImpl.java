package br.com.condominiosvirtuais.service.impl;

import java.io.Serializable;
import java.sql.SQLException;

import javax.inject.Inject;

import org.apache.log4j.Logger;

import br.com.condominiosvirtuais.entity.SindicoProfissional;
import br.com.condominiosvirtuais.persistence.impl.SindicoProfissionalDAOImpl;
import br.com.condominiosvirtuais.service.SindicoProfissionalService;

public class SindicoProfissionalServiceImpl implements SindicoProfissionalService, Serializable {

	private static final long serialVersionUID = 1L;
	
	private static Logger logger = Logger.getLogger(SindicoProfissionalServiceImpl.class);
	
	@Inject
	private SindicoProfissionalDAOImpl sindicoProfissionalDAO;

	@Override
	public void salvar(SindicoProfissional sindicoProfissional) throws SQLException, Exception {
		// TODO Auto-generated method stub
		
	}

}
