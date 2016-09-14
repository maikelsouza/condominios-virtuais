package br.com.condominiosvirtuais.persistence;

import java.sql.Connection;
import java.sql.SQLException;

import br.com.condominiosvirtuais.entity.LogReserva;

public interface LogReservaDAO {

	
	public abstract void salvar(LogReserva logReserva, Connection con) throws SQLException, Exception;
}
