package br.com.condominiosvirtuais.service.impl;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import br.com.condominiosvirtuais.entity.Visita;
import br.com.condominiosvirtuais.persistence.impl.VisitaDAOImpl;
import br.com.condominiosvirtuais.service.VisitaService;

public class VisitaServiceImpl implements VisitaService, Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private VisitaDAOImpl visitaDAO;

	@Override
	public List<Visita> buscarPorIdCondominio(Integer idCondominio) throws SQLException, Exception {		
		return this.visitaDAO.buscarPorIdCondominio(idCondominio);
	}

	@Override
	public List<Integer> buscarIdsVisitantesAgrupadosOndePeriodoEIdCondominio(Date de, Date ate, Integer idCondominio) throws SQLException, Exception {		
		return this.visitaDAO.buscarIdsVisitantesAgrupadosOndePeriodoEIdCondominio(de, ate, idCondominio);
	}

	@Override
	public List<Visita> buscarPorPeriodoEIdVisitante(Date de, Date ate, Integer idVisitante)
			throws SQLException, Exception {
		return this.visitaDAO.buscarPorPeriodoEIdVisitante(de, ate, idVisitante);
	}
}
