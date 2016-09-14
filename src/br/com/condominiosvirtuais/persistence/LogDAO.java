package br.com.condominiosvirtuais.persistence;

import java.sql.Connection;
import java.sql.SQLException;

import br.com.condominiosvirtuais.entity.Log;

public interface LogDAO {
	
	public abstract void salvarReserva(Log log, Connection con) throws SQLException, Exception;

}
