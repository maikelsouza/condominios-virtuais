package br.com.condominiosvirtuais.service.impl;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import br.com.condominiosvirtuais.entity.Boleto;
import br.com.condominiosvirtuais.persistence.BoletoDAO;
import br.com.condominiosvirtuais.service.BoletoService;

public class BoletoServiceImpl implements BoletoService, Serializable {
	
	
	private static final long serialVersionUID = 1L;
	
	@Inject
	private BoletoDAO BoletoDAO; 

	@Override
	public void salvar(Boleto boleto) throws SQLException, Exception {
		this.BoletoDAO.salvar(boleto);		
	}

	@Override
	public void excluirPorId(Integer idBoleto) throws SQLException, Exception {
		this.BoletoDAO.excluirPorId(idBoleto);		
	}

	
	@Override
	public List<Boleto> buscarPorIdCondominioEDataVencimento(Integer idCondominio, Date dataVencimentoDe,
			Date dataVencimentoAte) throws SQLException, Exception {
		return this.BoletoDAO.buscarPorIdCondominioEDataVencimento(idCondominio, dataVencimentoDe, dataVencimentoAte);
		
	}

}
