package br.com.condominiosvirtuais.service;

import java.sql.SQLException;
import java.util.List;

import br.com.condominiosvirtuais.entity.TipoChamado;

public interface TipoChamadoService {
	
	public abstract List<TipoChamado> buscarTodos() throws SQLException, Exception;

}
