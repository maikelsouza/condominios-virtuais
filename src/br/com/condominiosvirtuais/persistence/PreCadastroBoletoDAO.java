package br.com.condominiosvirtuais.persistence;

import java.sql.SQLException;
import java.util.List;

import br.com.condominiosvirtuais.entity.PreCadastroBoleto;
import br.com.condominiosvirtuais.exception.BusinessException;

public interface PreCadastroBoletoDAO {
	
	public abstract void salvar(PreCadastroBoleto preCadastroBoleto) throws SQLException, BusinessException, Exception;
	
	public abstract void excluirPorId(Integer idPreCadastroBoleto) throws SQLException, Exception;
	
	public abstract void atualizar(PreCadastroBoleto preCadastroBoleto) throws SQLException, BusinessException, Exception;
	
	public abstract void atualizarPrincipal(Integer id, Boolean principal) throws SQLException, BusinessException, Exception;
	
	public abstract List<PreCadastroBoleto> buscarPorIdCondominio(Integer idCondominio) throws SQLException, Exception;
	
	public abstract List<PreCadastroBoleto> buscarPorIdCondominioEPrincipal(Integer idCondominio, Boolean principal) throws SQLException, Exception;
	

}
