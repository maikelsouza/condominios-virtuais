package br.com.condominiosvirtuais.service.impl;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

import javax.inject.Inject;

import br.com.condominiosvirtuais.entity.AlternativaEnquete;
import br.com.condominiosvirtuais.persistence.AlternativaEnqueteDAO;
import br.com.condominiosvirtuais.service.AlternativaEnqueteService;

public class AlternativaEnqueteServiceImpl implements AlternativaEnqueteService, Serializable {

	
	private static final long serialVersionUID = 1L;
	
	@Inject
	private AlternativaEnqueteDAO alternativaEnqueteDAO;
	
	
	@Override
	public List<AlternativaEnquete> buscarPorIdEnquete(Integer idEnquete) throws SQLException, Exception {	
		return this.alternativaEnqueteDAO.buscarPorIdEnquete(idEnquete);
	}
	
	@Override
	public void votar(AlternativaEnquete alternativaEnquete) throws SQLException, Exception {
		this.alternativaEnqueteDAO.votar(alternativaEnquete);		
	}

	public AlternativaEnqueteDAO getAlternativaEnqueteDAO() {
		return alternativaEnqueteDAO;
	}

	public void setAlternativaEnqueteDAO(AlternativaEnqueteDAO alternativaEnqueteDAO) {
		this.alternativaEnqueteDAO = alternativaEnqueteDAO;
	}

}
