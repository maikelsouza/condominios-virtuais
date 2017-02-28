package br.com.condominiosvirtuais.service.impl;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

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

	@Override
	public List<ContaBancaria> buscarPorIdCondominio(Integer idCondominio) throws SQLException, Exception {
		return this.contaBancariaDAO.buscarPorIdCondominio(idCondominio);
		
	}

	@Override
	public void atualizar(ContaBancaria contaBancaria) throws SQLException, Exception {
		this.contaBancariaDAO.atualizar(contaBancaria);		
	}

	@Override
	public void excluir(ContaBancaria contaBancaria) throws SQLException, Exception {
		this.contaBancariaDAO.excluir(contaBancaria);		
	}

	@Override
	public List<ContaBancaria> buscarPorIdCondominioESituacao(Integer idCondominio, Boolean situacao)
			throws SQLException, Exception {		
		return this.contaBancariaDAO.buscarPorIdCondominioESituacao(idCondominio, situacao);
	}

}
