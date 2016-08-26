package br.com.condominiosvirtuais.service;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import br.com.condominiosvirtuais.entity.Visitante;

public interface VisitanteService {
	
	// Os métodos de salvar e atualizar, sempre salvam uma visita.
	
	public abstract void salvarSomenteVisitante(Visitante visitante) throws SQLException, Exception;
	
	public abstract void atualizarSomenteVisitante(Visitante visitante) throws SQLException, Exception;
	
	public abstract void salvarVisitanteESalvarPrestadorServico(Visitante visitante) throws SQLException, Exception;
	
	public abstract void salvarVisitanteEAtualizarPrestadorServico(Visitante visitante) throws SQLException, Exception;
	
	public abstract void atualizarVisitanteESalvarPrestadorServico(Visitante visitante) throws SQLException, Exception;
	
	public abstract void atualizarVisitanteEAtualizarPrestadorServico(Visitante visitante) throws SQLException, Exception;	
	
	public abstract List<Visitante> buscaPorNomeEIdCondominio(String nome, Integer idCondominio) throws SQLException, Exception;
	
	public abstract List<Visitante> buscarPorPeriodoEIdCondominio(Date de, Date ate, Integer idCondominio) throws SQLException, Exception;


}
