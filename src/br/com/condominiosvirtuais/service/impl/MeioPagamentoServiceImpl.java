package br.com.condominiosvirtuais.service.impl;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import br.com.condominiosvirtuais.entity.MeioPagamento;
import br.com.condominiosvirtuais.persistence.MeioPagamentoDAO;
import br.com.condominiosvirtuais.service.MeioPagamentoService;

public class MeioPagamentoServiceImpl implements MeioPagamentoService, Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private MeioPagamentoDAO meioPagamentoDAO;

	@Override
	public List<MeioPagamento> buscarTodos() throws SQLException, Exception {
		List<MeioPagamento> listaMeioPagamento = this.meioPagamentoDAO.buscarTodos();
		Collections.sort(listaMeioPagamento);		
		return listaMeioPagamento;
	}

}