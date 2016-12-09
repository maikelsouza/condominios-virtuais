package br.com.condominiosvirtuais.persistence;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import br.com.condominiosvirtuais.entity.Despesa;

public interface DespesaDAO {
	
	public abstract void salvarDespesa(Despesa despesa) throws SQLException, Exception;
	
	public abstract List<Despesa> pesquisarPorMesAnoReferenciaEIdCondominio(Date mesAnoReferencia, Integer idCondominio) throws SQLException, Exception;
	

}
