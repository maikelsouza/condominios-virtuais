package br.com.condominiosvirtuais.service.impl;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import br.com.condominiosvirtuais.entity.Visita;
import br.com.condominiosvirtuais.entity.Visitante;
import br.com.condominiosvirtuais.persistence.impl.VisitanteDAOImpl;
import br.com.condominiosvirtuais.service.VisitanteService;

public class VisitanteServiceImpl implements VisitanteService, Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private VisitanteDAOImpl visitanteDAO;
	
	@Inject
	private VisitaServiceImpl visitanteService;	
	
	@Inject
	private VisitaServiceImpl visitaService;

	

	@Override
	public List<Visitante> buscaPorNomeEIdCondominio(String nome, Integer idCondominio) throws SQLException, Exception {
		List<Visitante> listaVisitantes = null;
		List<Visita> listaVisitas =  this.visitanteService.buscarPorIdCondominio(idCondominio);
		List<Integer> listaIds = new ArrayList<Integer>();
		for (Visita visita : listaVisitas) {
			listaIds.add(visita.getIdVisitante());
		}
		if(!listaIds.isEmpty()){
			listaVisitantes = this.visitanteDAO.buscaPorIdsENome(listaIds, nome);			
		}
		return listaVisitantes;
	}	

	@Override
	public void salvarVisitanteESalvarPrestadorServico(Visitante visitante) throws SQLException, Exception {
		this.visitanteDAO.salvarVisitanteESalvarPrestadorServico(visitante);
		
	}

	@Override
	public void salvarVisitanteEAtualizarPrestadorServico(Visitante visitante) throws SQLException, Exception {
		this.visitanteDAO.salvarVisitanteEAtualizarPrestadorServico(visitante);		
	}

	@Override
	public void atualizarVisitanteESalvarPrestadorServico(Visitante visitante) throws SQLException, Exception {
		this.visitanteDAO.atualizarVisitanteESalvarPrestadorServico(visitante);		
	}

	@Override
	public void atualizarVisitanteEAtualizarPrestadorServico(Visitante visitante) throws SQLException, Exception {
		this.visitanteDAO.atualizarVisitanteEAtualizarPrestadorServico(visitante);
		
	}

	@Override
	public void salvarSomenteVisitante(Visitante visitante) throws SQLException, Exception {
		this.visitanteDAO.salvarSomenteVisitante(visitante);
		
	}

	@Override
	public void atualizarSomenteVisitante(Visitante visitante) throws SQLException, Exception {
		this.visitanteDAO.atualizarSomenteVisitante(visitante);
		
	}

	@Override
	public List<Visitante> buscarPorPeriodoEIdCondominio(Date de, Date ate, Integer idCondominio) throws SQLException, Exception {
		List<Visitante> listaVisitantes = new ArrayList<Visitante>();
		Visitante visitante = null;
		List<Integer> listaIdVisitantes = this.visitaService.buscarIdsVisitantesAgrupadosOndePeriodoEIdCondominio(de, ate, idCondominio);
		for (Integer id : listaIdVisitantes) {
			visitante = this.visitanteDAO.buscaPorId(id);
			visitante.setListaVisita(this.visitaService.buscarPorPeriodoEIdVisitante(de, ate, visitante.getId()));
			listaVisitantes.add(visitante);
		}
		return listaVisitantes;
	}

}
