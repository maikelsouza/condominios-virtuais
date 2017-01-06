package br.com.condominiosvirtuais.persistence;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import br.com.condominiosvirtuais.entity.MeioPagamento;

public interface MeioPagamentoDAO {
	
	public abstract MeioPagamento buscaPorId(Integer id, Connection con) throws SQLException, Exception;
	
	public abstract List<MeioPagamento> buscarTodos()  throws SQLException, Exception;	

}