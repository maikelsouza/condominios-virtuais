package br.com.condominiosvirtuais.persistence;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import br.com.condominiosvirtuais.entity.TelaComponente;

public interface TelaComponenteDAO {	
	
	public abstract List<TelaComponente> buscarPorIdTela(Integer idTela, Connection con) throws SQLException, Exception;

}
