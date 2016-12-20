package br.com.condominiosvirtuais.service;

import java.sql.SQLException;
import java.util.List;

import br.com.condominiosvirtuais.entity.MeioPagamento;

public interface MeioPagamentoService {
	
	public abstract List<MeioPagamento> buscarTodos()  throws SQLException, Exception;

}
