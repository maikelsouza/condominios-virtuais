package br.com.condominiosvirtuais.persistence;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import br.com.condominiosvirtuais.entity.Unidade;

public interface UnidadeDAO {
	
	public abstract Unidade buscarPorId(Integer id, Connection con) throws SQLException, Exception;
	
	public abstract List<Integer> buscarListaIdsUnidadesPorIdBloco(Integer idBloco) throws SQLException, Exception;

}
