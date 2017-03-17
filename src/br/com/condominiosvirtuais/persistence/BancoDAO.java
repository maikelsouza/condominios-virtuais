package br.com.condominiosvirtuais.persistence;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import br.com.condominiosvirtuais.entity.Banco;

public interface BancoDAO {
	
	public abstract List<Banco> buscarTodosPorSituacao(Boolean situacao)  throws SQLException, Exception;
	
	public abstract Banco buscarPorId(Integer idBanco, Connection con)  throws SQLException, Exception;
	
	public abstract List<Banco> buscarTodos() throws SQLException, Exception;

}
