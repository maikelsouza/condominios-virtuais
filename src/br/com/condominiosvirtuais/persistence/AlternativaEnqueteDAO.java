package br.com.condominiosvirtuais.persistence;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import br.com.condominiosvirtuais.entity.AlternativaEnquete;
import br.com.condominiosvirtuais.exception.BusinessException;

public interface AlternativaEnqueteDAO {
	
	public abstract void salvar(AlternativaEnquete alternativaEnquete, Connection con) throws SQLException, Exception;
	
	public abstract void excluir(AlternativaEnquete alternativaEnquete, Connection con) throws SQLException, Exception;
	
	public abstract List<AlternativaEnquete> buscarPorIdEnquete(Integer idEnquete,  Connection con) throws SQLException, Exception;
	
	public abstract List<AlternativaEnquete> buscarPorIdEnquete(Integer idEnquete) throws SQLException, Exception;
	
	public abstract void votar(AlternativaEnquete alternativaEnquete) throws SQLException, BusinessException, Exception;
	

}
