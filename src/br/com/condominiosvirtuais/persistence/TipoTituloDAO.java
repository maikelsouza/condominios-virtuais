package br.com.condominiosvirtuais.persistence;

import java.sql.SQLException;
import java.util.List;

import br.com.condominiosvirtuais.entity.TipoTitulo;

public interface TipoTituloDAO {

	
	public abstract List<TipoTitulo> buscarPorSituacao(Boolean situacao) throws SQLException, Exception;
}
