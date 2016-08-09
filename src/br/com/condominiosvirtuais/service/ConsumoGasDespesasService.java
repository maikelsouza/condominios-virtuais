package br.com.condominiosvirtuais.service;

import java.sql.SQLException;

import br.com.condominiosvirtuais.entity.ConsumoGasDespesas;

public interface ConsumoGasDespesasService {	
	
	public abstract ConsumoGasDespesas buscarPorIdDespesa(Integer idDespesas) throws SQLException, Exception;
	
}
