package br.com.condominiosvirtuais.persistence;

import java.sql.SQLException;
import java.util.List;

import br.com.condominiosvirtuais.entity.PreCadastroBoleto;

public interface PreCadastroBoletoDAO {
	
	public abstract void salvar(PreCadastroBoleto preCadastroBoleto) throws SQLException, Exception;
	
	public abstract void excluirPorId(Integer idPreCadastroBoleto) throws SQLException, Exception;
	
	public abstract void atualizar(PreCadastroBoleto preCadastroBoleto) throws SQLException, Exception;
	
	public abstract List<PreCadastroBoleto> buscarPorIdCondominio(Integer idCondominio) throws SQLException, Exception;
	

}
