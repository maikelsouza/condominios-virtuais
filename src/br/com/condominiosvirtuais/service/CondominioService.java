package br.com.condominiosvirtuais.service;

import java.sql.SQLException;
import java.util.List;

import br.com.condominiosvirtuais.entity.Condominio;
import br.com.condominiosvirtuais.entity.Condomino;

public interface CondominioService {
	
	public abstract void salvar(Condominio condominio) throws SQLException, Exception;
	
	public abstract void excluir(Condominio condominio) throws SQLException, Exception;
	
	public abstract void atualizar(Condominio condominio) throws SQLException, Exception;		
	                 
	public abstract  List<Condominio> buscarTodos() throws SQLException, Exception;
	
	public abstract List<Condominio> buscarPorNomeESituacao(Condominio condominio) throws SQLException, Exception;	
	
	public abstract Condominio buscarPorId(Integer idCondominio) throws SQLException, Exception;
	
	public abstract void associarEndereco(Condominio condominio) throws SQLException, Exception;	
		
	public abstract  void popularPorNomeCondominos(String nomeCondominos, Condominio condominio) throws SQLException, Exception;
		
	public abstract void popularCondominos(Condominio condominio) throws SQLException, Exception;
	
	// TODO: Repensar a modelagem: Hoje esse método preve que um condômino está associado a somente um condomínio
	public abstract Condominio buscarPorCondomino(Condomino condomino) throws SQLException, Exception;
	
	public abstract Condominio buscarPorCodigo(Integer codigo) throws SQLException, Exception;
	
	public abstract void popularBlocoEUnidadeDoCondominio(Condominio condominio) throws SQLException, Exception;
	
	public abstract List<Condominio> buscarPorSituacaoESemGestores(Integer situacao) throws SQLException, Exception;

}