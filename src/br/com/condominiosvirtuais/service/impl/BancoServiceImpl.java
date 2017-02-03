package br.com.condominiosvirtuais.service.impl;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

import javax.inject.Inject;

import br.com.condominiosvirtuais.entity.Banco;
import br.com.condominiosvirtuais.persistence.BancoDAO;
import br.com.condominiosvirtuais.service.BancoService;

public class BancoServiceImpl implements BancoService, Serializable  {
	
	
	private static final long serialVersionUID = 1L;
	
	@Inject
	private BancoDAO bancoDAO;

	@Override
	public List<Banco> buscarTodos() throws SQLException, Exception {
		return bancoDAO.buscarTodos();
	}

}
