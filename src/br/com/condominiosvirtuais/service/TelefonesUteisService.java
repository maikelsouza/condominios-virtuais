package br.com.condominiosvirtuais.service;

import java.sql.SQLException;
import java.util.List;

import br.com.condominiosvirtuais.entity.Condominio;
import br.com.condominiosvirtuais.entity.TelefonesUteis;

public interface TelefonesUteisService {
	
	public abstract List<TelefonesUteis> buscarPorCondominio(Condominio condominio) throws SQLException, Exception;
	
	public abstract void salvar(TelefonesUteis linksUteis) throws SQLException, Exception;
	
	public abstract void excluir(TelefonesUteis linksUteis) throws SQLException, Exception;
	
	public abstract void atualizar(TelefonesUteis linksUteis) throws SQLException, Exception;

}
