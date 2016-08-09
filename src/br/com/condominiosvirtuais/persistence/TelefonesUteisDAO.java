package br.com.condominiosvirtuais.persistence;

import java.sql.SQLException;
import java.util.List;

import br.com.condominiosvirtuais.entity.Condominio;
import br.com.condominiosvirtuais.entity.TelefonesUteis;

public interface TelefonesUteisDAO {
	
	public abstract List<TelefonesUteis> buscarPorCondominio(Condominio condominio) throws SQLException, Exception;
	
	public abstract void salvar(TelefonesUteis telefonesUteis) throws SQLException, Exception;
	
	public abstract void excluir(TelefonesUteis telefonesUteis) throws SQLException, Exception;
	
	public abstract void atualizar(TelefonesUteis telefonesUteis) throws SQLException, Exception;

}
