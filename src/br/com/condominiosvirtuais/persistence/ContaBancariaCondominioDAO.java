package br.com.condominiosvirtuais.persistence;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import br.com.condominiosvirtuais.entity.ContaBancariaCondominio;

public interface ContaBancariaCondominioDAO {
	
	public abstract void salvar(ContaBancariaCondominio contaBancariaCondominio, Connection con) throws SQLException, Exception;
	
	public abstract List<ContaBancariaCondominio> buscarPorIdCondominio(Integer idCondominio, Connection con) throws SQLException, Exception;
	
	public abstract void atualizarPorIdContaBancaria(ContaBancariaCondominio contaBancariaCondominio , Connection con) throws SQLException, Exception;

}
