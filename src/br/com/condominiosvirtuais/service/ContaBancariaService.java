package br.com.condominiosvirtuais.service;

import java.sql.SQLException;

import br.com.condominiosvirtuais.entity.ContaBancaria;

public interface ContaBancariaService {
	
	public abstract void salvar(ContaBancaria contaBancaria) throws SQLException, Exception;

}
