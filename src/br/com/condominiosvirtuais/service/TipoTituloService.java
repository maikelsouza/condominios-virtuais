package br.com.condominiosvirtuais.service;

import java.sql.SQLException;
import java.util.List;

import br.com.condominiosvirtuais.entity.TipoTitulo;

public interface TipoTituloService {
	
	
	public abstract List<TipoTitulo> buscarPorSituacao(Boolean situacao, String locale) throws SQLException, Exception;
	

}
