package br.com.condominiosvirtuais.service;

import java.sql.SQLException;

import br.com.condominiosvirtuais.entity.Modulo;

public interface ModuloService {
	
	public abstract Modulo buscarPorId(Integer id) throws SQLException, Exception;

}
