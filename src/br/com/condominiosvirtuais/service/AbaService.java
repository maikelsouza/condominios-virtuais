package br.com.condominiosvirtuais.service;

import java.sql.SQLException;
import java.util.List;

import br.com.condominiosvirtuais.entity.Aba;

public interface AbaService {

	
	public abstract List<Aba> buscarPorIdTela(Integer idTela) throws SQLException, Exception;
	
}
