package br.com.condominiosvirtuais.service.impl;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

import javax.inject.Inject;

import br.com.condominiosvirtuais.entity.PreCadastroBoleto;
import br.com.condominiosvirtuais.persistence.PreCadastroBoletoDAO;
import br.com.condominiosvirtuais.service.PreCadastroBoletoService;

public class PreCadastroBoletoServiceImpl implements PreCadastroBoletoService, Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Inject
	private PreCadastroBoletoDAO preCadastroBoletoDAO; 

	@Override
	public void salvar(PreCadastroBoleto preCadastroBoleto) throws SQLException, Exception {
		Boolean principal = Boolean.FALSE;
		List<PreCadastroBoleto> listaPreCadastroBoleto = this.buscarPorIdCondominio(preCadastroBoleto.getIdCondominio());
		if(listaPreCadastroBoleto.isEmpty()){
			principal = Boolean.TRUE;
		}
		preCadastroBoleto.setPrincipal(principal);
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

	@Override
	public PreCadastroBoleto buscarPorIdCondominioPrincipal(Integer idCondominio)
			throws SQLException, Exception {		
		List<PreCadastroBoleto> listaPreCadastroBoleto = this.preCadastroBoletoDAO.buscarPorIdCondominioEPrincipal(idCondominio, Boolean.TRUE);
		return !listaPreCadastroBoleto.isEmpty() ? listaPreCadastroBoleto.get(0) : null;  
	}

	@Override
	public void tornarPrincipal(PreCadastroBoleto preCadastroBoleto) throws SQLException, Exception {
		List<PreCadastroBoleto> listaPrecadastroBoleto = 
				this.preCadastroBoletoDAO.buscarPorIdCondominio(preCadastroBoleto.getIdCondominio());
		for (PreCadastroBoleto preCadastroBoletoBanco : listaPrecadastroBoleto) {
			if(preCadastroBoleto.getId().intValue() == preCadastroBoletoBanco.getId().intValue()){
				this.preCadastroBoletoDAO.atualizarPrincipal(preCadastroBoleto.getId(), Boolean.TRUE);
			}else if(preCadastroBoletoBanco.getPrincipal()){
				this.preCadastroBoletoDAO.atualizarPrincipal(preCadastroBoletoBanco.getId(), Boolean.FALSE);
			}
		}
		
	}

}
