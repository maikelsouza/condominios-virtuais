package br.com.condominiosvirtuais.service;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import br.com.condominiosvirtuais.entity.Despesa;

public interface DespesaService {
	
	public abstract void salvarDespesa(Despesa despesa) throws SQLException, Exception;
	
	public abstract List<Despesa> pesquisarPorMesAnoReferenciaEIdCondominio(Date mesAnoReferencia, Integer idCondominio) throws SQLException, Exception;

}
