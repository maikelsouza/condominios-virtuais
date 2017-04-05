package br.com.condominiosvirtuais.service;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import br.com.condominiosvirtuais.entity.Boleto;

public interface BoletoService {
	
	public abstract void salvar(Boleto boleto) throws SQLException, Exception;
	
	public abstract void excluirPorId(Integer idBoleto) throws SQLException, Exception;
	
	public abstract List<Boleto> buscarPorIdCondominioEDataVencimento(Integer idCondominio, Date dataVencimentoDe, Date dataVencimentoAte) throws SQLException, Exception;
	
	public abstract List<Boleto> buscarPorIdCondominioEPagoEDataVencimento(Integer idCondominio, Boolean pago, Date dataVencimentoDe, Date dataVencimentoAte) throws SQLException, Exception;
	
	public abstract void atualizarStatusPagamento(Boleto boleto) throws SQLException, Exception;
	
	public abstract List<Boleto> buscarPorIdPagador(Integer idPagador) throws SQLException, Exception;
	
	public abstract List<Boleto> buscarPorIdPagadorEPago(Integer idPagador, Boolean pago) throws SQLException, Exception;


}
