package br.com.condominiosvirtuais.service;

import java.sql.SQLException;

import br.com.condominiosvirtuais.entity.TarifaGas;

public interface TarifaGasService {
	
	public abstract TarifaGas buscarPorIdCondominio(Integer idCondominio) throws SQLException, Exception;

}
