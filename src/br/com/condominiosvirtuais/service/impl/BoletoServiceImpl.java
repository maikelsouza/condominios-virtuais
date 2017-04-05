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
	private BoletoDAO boletoDAO; 

	@Override
	public void salvar(Boleto boleto) throws SQLException, Exception {
		this.boletoDAO.salvar(boleto);		
	}

	@Override
	public void excluirPorId(Integer idBoleto) throws SQLException, Exception {
		this.boletoDAO.excluirPorId(idBoleto);		
	}

	
	@Override
	public List<Boleto> buscarPorIdCondominioEDataVencimento(Integer idCondominio, Date dataVencimentoDe,
			Date dataVencimentoAte) throws SQLException, Exception {
		return this.boletoDAO.buscarPorIdCondominioEDataVencimento(idCondominio, dataVencimentoDe, dataVencimentoAte);
		
	}

	@Override
	public void atualizarStatusPagamento(Boleto boleto) throws SQLException, Exception {
		this.boletoDAO.atualizarStatusPagamento(boleto);		
	}

	@Override
	public List<Boleto> buscarPorIdCondominioEPagoEDataVencimento(Integer idCondominio, Boolean pago,
			Date dataVencimentoDe, Date dataVencimentoAte) throws SQLException, Exception {	
		return this.boletoDAO.buscarPorIdCondominioEPagoEDataVencimento(idCondominio, pago, dataVencimentoDe, dataVencimentoAte);
	}

	@Override
	public List<Boleto> buscarPorIdPagador(Integer idPagador) throws SQLException, Exception {		
		return this.boletoDAO.buscarPorIdPagador(idPagador);
	}

	@Override
	public List<Boleto> buscarPorIdPagadorEPago(Integer idPagador, Boolean pago) throws SQLException, Exception {
		return this.boletoDAO.buscarPorIdPagadorEPago(idPagador, pago);
	}

}
