package br.com.condominiosvirtuais.persistence.impl;

import java.io.Serializable;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import br.com.condominiosvirtuais.entity.SindicoProfissional;
import br.com.condominiosvirtuais.persistence.SindicoProfissionalDAO;

public class SindicoProfissionalDAOImpl implements SindicoProfissionalDAO, Serializable {

	private static final long serialVersionUID = 1L;
	
	private static Logger logger = Logger.getLogger(SindicoProfissionalDAOImpl.class);

	@Override
	public void salvar(SindicoProfissional sindicoProfissional) throws SQLException, Exception {
		// TODO Auto-generated method stub
		
	}

}
