package br.com.condominiosvirtuais.persistence;

import java.sql.SQLException;
import java.util.List;

import br.com.condominiosvirtuais.entity.Modulo;

public interface ModuloDAO {

	public abstract Modulo buscarPorId(Integer id) throws SQLException, Exception;
	
	public abstract List<Modulo>  buscarPorIds(List<Integer> listaIdsModulo) throws SQLException, Exception;
}
