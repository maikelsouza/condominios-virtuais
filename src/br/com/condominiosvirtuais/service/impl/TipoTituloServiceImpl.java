package br.com.condominiosvirtuais.service.impl;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

import javax.inject.Inject;

import br.com.condominiosvirtuais.entity.TipoTitulo;
import br.com.condominiosvirtuais.persistence.TipoTituloDAO;
import br.com.condominiosvirtuais.service.TipoTituloService;

public class TipoTituloServiceImpl implements TipoTituloService, Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Inject
	private TipoTituloDAO tipoTituloDAO;

	@Override
	public List<TipoTitulo> buscarPorSituacao(Boolean situacao, String locale) throws SQLException, Exception {
		return tipoTituloDAO.buscarPorSituacao(situacao, locale);
	}

}
