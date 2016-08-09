package br.com.condominiosvirtuais.persistence;

import java.sql.Connection;
import java.sql.SQLException;

import br.com.condominiosvirtuais.entity.VotoEnquete;

public interface VotoEnqueteDAO {
	
	public abstract void salvar(VotoEnquete votoEnquete,  Connection con) throws SQLException, Exception;
	
	public abstract void excluirPorIdEnquete(Integer idEnquete,  Connection con) throws SQLException, Exception;

}
