package br.com.condominiosvirtuais.service.impl;

import java.io.Serializable;
import java.sql.SQLException;

import javax.inject.Inject;

import br.com.condominiosvirtuais.entity.ContaBancaria;
import br.com.condominiosvirtuais.persistence.ContaBancariaDAO;
import br.com.condominiosvirtuais.service.ContaBancariaService;

public class ContaBancariaServiceImpl implements ContaBancariaService, Serializable {
	

	private static final long serialVersionUID = 1L;
	
	@Inject
	private ContaBancariaDAO contaBancariaDAO;

	@Override
	public void salvar(ContaBancaria contaBancaria) throws SQLException, Exception {
		this.contaBancariaDAO.salvar(contaBancaria);
		
	}

}
