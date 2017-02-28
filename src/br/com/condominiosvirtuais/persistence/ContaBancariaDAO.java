package br.com.condominiosvirtuais.persistence;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import br.com.condominiosvirtuais.entity.ContaBancaria;
import br.com.condominiosvirtuais.exception.BusinessException;

public interface ContaBancariaDAO {
	
	public abstract void salvar(ContaBancaria contaBancaria) throws SQLException, Exception;
	
	public abstract List<ContaBancaria> buscarPorIdCondominio(Integer idCondominio) throws SQLException, Exception;
	
	public abstract List<ContaBancaria> buscarPorIdCondominioESituacao(Integer idCondominio, Boolean situacao) throws SQLException, Exception;
	
	public abstract void atualizar(ContaBancaria contaBancaria) throws SQLException, Exception;
	
	public abstract void excluir(ContaBancaria contaBancaria) throws SQLException, BusinessException, Exception;
	
	public abstract ContaBancaria buscarPorId(Integer idContaBancaria, Connection con) throws SQLException, Exception;

}
