package br.com.condominiosvirtuais.persistence;

import java.sql.Connection;
import java.sql.SQLException;

import br.com.condominiosvirtuais.entity.GestorCondominio;

public interface GestorCondominioDAO {
	
	
	public abstract void salvarGestorCondominioBloco(GestorCondominio gestorCondominio, Connection con) throws SQLException, Exception;
}
