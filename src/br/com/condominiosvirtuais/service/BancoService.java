package br.com.condominiosvirtuais.service;

import java.sql.SQLException;
import java.util.List;

import br.com.condominiosvirtuais.entity.Banco;

public interface BancoService {

	public abstract List<Banco> buscarTodosPorSituacao(Boolean situacao)  throws SQLException, Exception;
	
	public abstract List<Banco> buscarTodos()  throws SQLException, Exception;
}
