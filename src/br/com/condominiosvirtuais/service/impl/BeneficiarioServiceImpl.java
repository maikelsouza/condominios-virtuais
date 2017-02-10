package br.com.condominiosvirtuais.service.impl;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

import br.com.condominiosvirtuais.entity.Beneficiario;
import br.com.condominiosvirtuais.exception.BusinessException;
import br.com.condominiosvirtuais.persistence.BeneficiarioDAO;
import br.com.condominiosvirtuais.service.BeneficiarioService;

public class BeneficiarioServiceImpl implements BeneficiarioService, Serializable {

	private static final long serialVersionUID = 1L;
	
	private BeneficiarioDAO beneficiarioDAO;

	@Override
	public void salvar(Beneficiario beneficiario) throws SQLException, BusinessException, Exception {
		this.beneficiarioDAO.salvar(beneficiario);		
	}

	@Override
	public List<Beneficiario> buscarPorIdCondominio(Integer idCondominio) throws SQLException, BusinessException, Exception {
		return this.beneficiarioDAO.buscarPorIdCondominio(idCondominio);
	}
	
	

}
