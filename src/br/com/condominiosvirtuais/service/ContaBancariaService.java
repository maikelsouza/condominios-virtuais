package br.com.condominiosvirtuais.service;

import java.sql.SQLException;
import java.util.List;

import br.com.condominiosvirtuais.entity.ContaBancaria;

public interface ContaBancariaService {
	
	public abstract void salvar(ContaBancaria contaBancaria) throws SQLException, Exception;
	
	public abstract void atualizar(ContaBancaria contaBancaria) throws SQLException, Exception;
	
	public abstract void excluir(ContaBancaria contaBancaria) throws SQLException, Exception;
	
	public abstract List<ContaBancaria> buscarPorIdCondominio(Integer idCondominio) throws SQLException, Exception;

}
