package br.com.condominiosvirtuais.persistence;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import br.com.condominiosvirtuais.entity.Aba;

public interface AbaDAO {
	
	public abstract List<Aba> buscarPorIdTela(Integer idTela, Connection con) throws SQLException, Exception;
	
	public abstract List<Aba> buscarPorIdTela(Integer idTela) throws SQLException, Exception;

}
