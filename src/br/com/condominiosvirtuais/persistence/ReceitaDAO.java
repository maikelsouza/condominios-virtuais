package br.com.condominiosvirtuais.persistence;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import br.com.condominiosvirtuais.entity.Receita;

public interface ReceitaDAO {
	
	public abstract void salvar(Receita receita) throws SQLException, Exception;
	
	public abstract List<Receita> buscarPorDataDeEDataAteEIdCondominio(Date dataDe, Date dataAte, Integer idCondominio) throws SQLException, Exception;
	
	public abstract void atualizar(Receita receita) throws SQLException, Exception;
	
	public abstract void excluir(Receita receita) throws SQLException, Exception;
	
	
	
	

}
