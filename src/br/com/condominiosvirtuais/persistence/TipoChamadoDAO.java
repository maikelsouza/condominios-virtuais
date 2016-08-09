package br.com.condominiosvirtuais.persistence;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import br.com.condominiosvirtuais.entity.TipoChamado;

public interface TipoChamadoDAO {
	
	
	public abstract TipoChamado buscarPorId(Integer idTipoChamado, Connection con) throws SQLException, Exception;
	
	public abstract List<TipoChamado> buscarTodos() throws SQLException, Exception;
	
	public abstract Integer maxId(Connection con) throws SQLException, Exception;

}
