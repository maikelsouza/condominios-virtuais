package br.com.condominiosvirtuais.persistence;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import br.com.condominiosvirtuais.entity.Receita;

public interface ReceitaDAO {
	
	public abstract void salvarReceita(Receita receita) throws SQLException, Exception;
	
	public abstract List<Receita> pesquisarPorMesAnoReferenciaEIdCondominio(Date mesAnoReferencia, Integer idCondominio) throws SQLException, Exception;

}
