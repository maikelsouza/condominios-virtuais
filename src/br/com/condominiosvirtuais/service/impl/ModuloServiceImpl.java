package br.com.condominiosvirtuais.service.impl;

import java.io.Serializable;
import java.sql.SQLException;

import javax.inject.Inject;

import br.com.condominiosvirtuais.entity.Modulo;
import br.com.condominiosvirtuais.persistence.ModuloDAO;
import br.com.condominiosvirtuais.service.ModuloService;

public class ModuloServiceImpl implements ModuloService, Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private ModuloDAO moduloDAO;

	@Override
	public Modulo buscarPorId(Integer id) throws SQLException, Exception {
		return this.moduloDAO.buscarPorId(id);
	}
	
	

}
