package br.com.condominiosvirtuais.service;

import java.sql.SQLException;
import java.util.List;

import br.com.condominiosvirtuais.entity.Bloco;
import br.com.condominiosvirtuais.entity.Condominio;
import br.com.condominiosvirtuais.exception.BusinessException;

public interface BlocoService {
	
	public abstract List<Bloco> buscarPorCondominioENome(Condominio condominio, String nomeBloco) throws SQLException, Exception;		
		
	public abstract void salvar(Bloco bloco) throws SQLException, BusinessException, Exception;
	
	public abstract void salvarEmLote(List<Bloco> listaBlocos) throws SQLException, Exception;
	
	public abstract void atualizar(Bloco bloco) throws SQLException, BusinessException, Exception;
	
	public abstract void excluir(Bloco bloco) throws SQLException, BusinessException, Exception;	
	
	public abstract void popularPorNomeCondominos(String nomeCondominos, Bloco bloco) throws SQLException, Exception;		
	
	public abstract Bloco buscarPorId(Integer idBloco) throws SQLException, Exception;	
	
	public abstract void popularBloco(Bloco bloco) throws SQLException, Exception; 
		
}
