package br.com.condominiosvirtuais.persistence;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import br.com.condominiosvirtuais.entity.Despesa;

public interface DespesaDAO {
	
	public abstract void salvar(Despesa despesa) throws SQLException, Exception;
	
	public abstract List<Despesa> buscarPorDataDeEDataAteEIdCondominio(Date dataDe, Date dataAte, Integer idCondominio) throws SQLException, Exception;
	
	public abstract void atualizar(Despesa despesa) throws SQLException, Exception;
	
	public abstract void excluir(Despesa despesa) throws SQLException, Exception;
	

}
