package br.com.condominiosvirtuais.persistence;

import java.sql.SQLException;

import br.com.condominiosvirtuais.entity.TarifaGas;

public interface TarifaGasDAO {	
	
	public abstract TarifaGas buscarPorIdCondominio(Integer idCondominio) throws SQLException, Exception;

}
