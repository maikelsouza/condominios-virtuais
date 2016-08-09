package br.com.condominiosvirtuais.service.impl;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

import javax.inject.Inject;

import br.com.condominiosvirtuais.entity.TipoChamado;
import br.com.condominiosvirtuais.persistence.impl.TipoChamadoDAOImpl;
import br.com.condominiosvirtuais.service.TipoChamadoService;

public class TipoChamadoServiceImpl implements TipoChamadoService, Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private TipoChamadoDAOImpl tipoChamadoDAO;

	@Override
	public List<TipoChamado> buscarTodos() throws SQLException, Exception {
		return tipoChamadoDAO.buscarTodos();
	}

}
