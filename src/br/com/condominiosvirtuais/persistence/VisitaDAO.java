package br.com.condominiosvirtuais.persistence;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import br.com.condominiosvirtuais.entity.Visita;

public interface VisitaDAO {
	
	public abstract void salvarVisitaESalvarPrestadorServico(Visita visita, Connection con) throws SQLException, Exception;
	
	public abstract void salvarVisitaEAtualizarPrestadorServico(Visita visita, Connection con) throws SQLException, Exception;
	
	public abstract void salvarSomenteVisita(Visita visita, Connection con) throws SQLException, Exception;	
	
	public abstract List<Visita> buscarPorIdCondominio(Integer idCondominio) throws SQLException, Exception;
	
	public abstract List<Visita> buscarPorIdVisitante(Integer idVisitante) throws SQLException, Exception;
	
	public abstract List<Integer> buscarIdsVisitantesAgrupadosOndePeriodoEIdCondominio(Date de, Date ate, Integer idCondominio) throws SQLException, Exception;
	
	public abstract List<Visita> buscarPorPeriodoEIdVisitante(Date de, Date ate, Integer idVisitante) throws SQLException, Exception;
	
}
