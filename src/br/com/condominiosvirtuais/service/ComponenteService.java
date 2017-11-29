package br.com.condominiosvirtuais.service;

import java.sql.SQLException;
import java.util.List;

import br.com.condominiosvirtuais.entity.Componente;

public interface ComponenteService {
	
	public abstract List<Componente> buscarPorIdTela(Integer idTela) throws SQLException, Exception;

}
