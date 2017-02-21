package br.com.condominiosvirtuais.service.impl;

import java.sql.SQLException;
import java.util.List;

import javax.inject.Inject;

import br.com.condominiosvirtuais.entity.PreCadastroBoleto;
import br.com.condominiosvirtuais.persistence.PreCadastroBoletoDAO;
import br.com.condominiosvirtuais.service.PreCadastroBoletoService;

public class PreCadastroBoletoServiceImpl implements PreCadastroBoletoService {
	
	@Inject
	private PreCadastroBoletoDAO preCadastroBoletoDAO; 

	@Override
	public void salvar(PreCadastroBoleto preCadastroBoleto) throws SQLException, Exception {
		this.preCadastroBoletoDAO.salvar(preCadastroBoleto);		
	}

	@Override
	public void excluirPorId(Integer idPreCadastroBoleto) throws SQLException, Exception {
		this.preCadastroBoletoDAO.excluirPorId(idPreCadastroBoleto);
		
	}

	@Override
	public void atualizar(PreCadastroBoleto preCadastroBoleto) throws SQLException, Exception {
		this.preCadastroBoletoDAO.atualizar(preCadastroBoleto);		
	}

	@Override
	public List<PreCadastroBoleto> buscarPorIdCondominio(Integer idCondominio) throws SQLException, Exception {		
		return this.preCadastroBoletoDAO.buscarPorIdCondominio(idCondominio);
	}

}
