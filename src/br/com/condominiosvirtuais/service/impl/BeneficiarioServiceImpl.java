package br.com.condominiosvirtuais.service.impl;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

import javax.inject.Inject;

import br.com.condominiosvirtuais.entity.Beneficiario;
import br.com.condominiosvirtuais.exception.BusinessException;
import br.com.condominiosvirtuais.persistence.BeneficiarioDAO;
import br.com.condominiosvirtuais.service.BeneficiarioService;

public class BeneficiarioServiceImpl implements BeneficiarioService, Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private BeneficiarioDAO beneficiarioDAO;

	@Override
	public void salvar(Beneficiario beneficiario) throws SQLException, BusinessException, Exception {
		this.beneficiarioDAO.salvar(beneficiario);		
	}

	@Override
	public List<Beneficiario> buscarPorIdCondominio(Integer idCondominio) throws SQLException, BusinessException, Exception {
		return this.beneficiarioDAO.buscarPorIdCondominio(idCondominio);
	}

	@Override
	public void atualizar(Beneficiario beneficiario) throws SQLException, BusinessException, Exception {
		this.beneficiarioDAO.atualizar(beneficiario);		
	}

	@Override
	public void excluir(Beneficiario beneficiario) throws SQLException, BusinessException, Exception {
		this.beneficiarioDAO.excluir(beneficiario);
		
	}

	@Override
	public List<Beneficiario> buscarPorIdCondominioESituacao(Integer idCondominio, Boolean situacao) throws SQLException, BusinessException, Exception {	
		return this.beneficiarioDAO.buscarPorIdCondominioESituacao(idCondominio, situacao);
	}
	
	

}
