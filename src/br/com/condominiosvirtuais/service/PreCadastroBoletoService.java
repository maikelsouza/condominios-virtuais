package br.com.condominiosvirtuais.service;

import java.sql.SQLException;
import java.util.List;

import br.com.condominiosvirtuais.entity.PreCadastroBoleto;

public interface PreCadastroBoletoService {
	
	public abstract void salvar(PreCadastroBoleto preCadastroBoleto) throws SQLException, Exception;
	
	public abstract void excluirPorId(Integer idPreCadastroBoleto) throws SQLException, Exception;
	
	public abstract void atualizar(PreCadastroBoleto preCadastroBoleto) throws SQLException, Exception;
	
	public abstract List<PreCadastroBoleto> buscarPorIdCondominio(Integer idCondominio) throws SQLException, Exception;
	
	public abstract PreCadastroBoleto buscarPorIdCondominioPrincipal(Integer idCondominio) throws SQLException, Exception;
	
	public abstract void tornarPrincipal(PreCadastroBoleto preCadastroBoleto) throws SQLException, Exception;

}
