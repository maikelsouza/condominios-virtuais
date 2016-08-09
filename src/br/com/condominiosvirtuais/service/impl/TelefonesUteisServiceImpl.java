package br.com.condominiosvirtuais.service.impl;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

import javax.inject.Inject;

import br.com.condominiosvirtuais.entity.Condominio;
import br.com.condominiosvirtuais.entity.TelefonesUteis;
import br.com.condominiosvirtuais.persistence.TelefonesUteisDAO;
import br.com.condominiosvirtuais.service.TelefonesUteisService;

public class TelefonesUteisServiceImpl implements TelefonesUteisService, Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Inject
	private TelefonesUteisDAO telefonesUteisDAO;
	
	
	@Override
	public List<TelefonesUteis> buscarPorCondominio(Condominio condominio) throws SQLException, Exception {
		return this.telefonesUteisDAO.buscarPorCondominio(condominio);
	}

	@Override
	public void salvar(TelefonesUteis telefonesUteis) throws SQLException, Exception {
		this.telefonesUteisDAO.salvar(telefonesUteis);		
	}

	@Override
	public void excluir(TelefonesUteis telefonesUteis) throws SQLException, Exception {
		this.telefonesUteisDAO.excluir(telefonesUteis); 
	}

	@Override
	public void atualizar(TelefonesUteis telefonesUteis) throws SQLException, Exception {
		this.telefonesUteisDAO.atualizar(telefonesUteis);		
	}

	public TelefonesUteisDAO getTelefonesUteisDAO() {
		return telefonesUteisDAO;
	}

	public void setTelefonesUteisDAO(TelefonesUteisDAO telefonesUteisDAO) {
		this.telefonesUteisDAO = telefonesUteisDAO;
	}
}
