package br.com.condominiosvirtuais.persistence;

import java.sql.SQLException;

import br.com.condominiosvirtuais.entity.Condominio;

public interface CondominioDAO {
	
	public abstract Condominio buscarPorCodigo(Integer Codigo) throws SQLException, Exception;
	
	public abstract void popularBlocoEUnidadeDoCondominio(Condominio condominio) throws SQLException, Exception;

}
