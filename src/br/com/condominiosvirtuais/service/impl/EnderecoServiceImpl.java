package br.com.condominiosvirtuais.service.impl;

import java.io.Serializable;
import java.sql.SQLException;

import javax.inject.Inject;

import br.com.condominiosvirtuais.entity.Endereco;
import br.com.condominiosvirtuais.persistence.impl.EnderecoDAOImpl;
import br.com.condominiosvirtuais.service.EnderecoService;

public class EnderecoServiceImpl implements EnderecoService, Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Inject
	private EnderecoDAOImpl enderecoDAO = null;
	
	public Endereco buscaPorIdCondominio(Integer idCondominio) throws SQLException, Exception{
		return this.enderecoDAO.buscarEnderecoPorIdCondominio(idCondominio);
	}

	public EnderecoDAOImpl getEnderecoDAO() {
		return enderecoDAO;
	}

	public void setEnderecoDAO(EnderecoDAOImpl enderecoDAO) {
		this.enderecoDAO = enderecoDAO;
	}

}
