package br.com.condominiosvirtuais.service.impl;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import br.com.condominiosvirtuais.persistence.ConfiguracaoAplicacaoDAO;
import br.com.condominiosvirtuais.service.ConfiguracaoAplicacaoService;

public class ConfiguracaoAplicacaoServiceImpl implements
		ConfiguracaoAplicacaoService, Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private ConfiguracaoAplicacaoDAO configuracaoAplicacaoDAO = null;
	
	private Map<String, String> mapConfiguracaoAplicacao = null;
	
	
	@PostConstruct
	public void buscarTodasConfiguracoes() throws SQLException, Exception{
		this.mapConfiguracaoAplicacao = this.configuracaoAplicacaoDAO.buscarTodas();
	}

	public Map<String, String> getConfiguracoes() {		
		return this.mapConfiguracaoAplicacao;
	}

	public ConfiguracaoAplicacaoDAO getConfiguracaoAplicacaoDAO() {
		return configuracaoAplicacaoDAO;
	}

	public void setConfiguracaoAplicacaoDAO(
			ConfiguracaoAplicacaoDAO configuracaoAplicacaoDAO) {
		this.configuracaoAplicacaoDAO = configuracaoAplicacaoDAO;
	}

}
