package br.com.condominiosvirtuais.persistence;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import br.com.condominiosvirtuais.entity.Condominio;
import br.com.condominiosvirtuais.entity.GestorCondominio;

public interface GestorCondominioDAO {
	
	
	public abstract void salvarGestorCondominioBloco(GestorCondominio gestorCondominio, Connection con) throws SQLException, Exception;
	
	public abstract void salvarGestorCondominioSindicoProfissional(GestorCondominio gestorCondominio, Connection con) throws SQLException, Exception;
	
	public abstract List<GestorCondominio> buscarListaGestoresCondominioPorCondominio(Condominio condominio) throws SQLException, Exception;
	
}
