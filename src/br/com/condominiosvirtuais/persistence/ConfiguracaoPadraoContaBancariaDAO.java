package br.com.condominiosvirtuais.persistence;

import java.sql.Connection;
import java.sql.SQLException;

import br.com.condominiosvirtuais.entity.ConfiguracaoPadraoContaBancaria;
import br.com.condominiosvirtuais.exception.BusinessException;

public interface ConfiguracaoPadraoContaBancariaDAO {
	
	public abstract void salvar(ConfiguracaoPadraoContaBancaria configuracaoPadraoContaBancaria, Connection con) throws SQLException, BusinessException, Exception;
	
	public abstract ConfiguracaoPadraoContaBancaria buscarPorIdContaBancaria(Integer idContaBancaria, Connection con) throws SQLException, BusinessException, Exception;

}
