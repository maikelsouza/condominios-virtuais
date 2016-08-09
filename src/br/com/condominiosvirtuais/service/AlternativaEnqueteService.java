package br.com.condominiosvirtuais.service;

import java.sql.SQLException;
import java.util.List;

import br.com.condominiosvirtuais.entity.AlternativaEnquete;

public interface AlternativaEnqueteService {
	
	public abstract List<AlternativaEnquete> buscarPorIdEnquete(Integer idEnquete) throws SQLException, Exception;
	
	public abstract void votar(AlternativaEnquete alternativaEnquete) throws SQLException, Exception;
	
	

}
