package br.com.condominiosvirtuais.persistence;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import br.com.condominiosvirtuais.entity.TipoTitulo;

public interface TipoTituloDAO {

	
	public abstract List<TipoTitulo> buscarPorSituacao(Boolean situacao, String locale) throws SQLException, Exception;
	
	public abstract TipoTitulo buscarPorId(Integer id, Connection con) throws SQLException, Exception;
}
