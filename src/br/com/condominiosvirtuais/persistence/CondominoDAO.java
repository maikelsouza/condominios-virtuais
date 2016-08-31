package br.com.condominiosvirtuais.persistence;

import java.sql.SQLException;
import java.util.List;

public interface CondominoDAO {
	
	public abstract List<Integer> buscarListaIdsCondominosPorIdUnidade(Integer idUnidade) throws SQLException, Exception;

}
