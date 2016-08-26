package br.com.condominiosvirtuais.service;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import br.com.condominiosvirtuais.entity.Visita;

public interface VisitaService {
	
	public abstract List<Visita> buscarPorIdCondominio(Integer idCondominio) throws SQLException, Exception;
	
	public abstract List<Integer> buscarIdsVisitantesAgrupadosOndePeriodoEIdCondominio(Date de, Date ate, Integer idCondominio) throws SQLException, Exception;
	
	public abstract List<Visita> buscarPorPeriodoEIdVisitante(Date de, Date ate, Integer idVisitante) throws SQLException, Exception;


}
