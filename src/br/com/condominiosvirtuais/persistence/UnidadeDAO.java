package br.com.condominiosvirtuais.persistence;

import java.sql.Connection;
import java.sql.SQLException;

import br.com.condominiosvirtuais.entity.Unidade;

public interface UnidadeDAO {
	
	public abstract Unidade buscarPorId(Integer id, Connection con) throws SQLException, Exception;

}
