package br.com.condominiosvirtuais.persistence;

import java.sql.SQLException;
import java.util.List;

import br.com.condominiosvirtuais.entity.Visitante;
import br.com.condominiosvirtuais.exception.BusinessException;

public interface VisitanteDAO {	
	
	public abstract void salvarVisitanteESalvarPrestadorServico(Visitante visitante) throws SQLException, BusinessException, Exception;
	
	public abstract void salvarVisitanteEAtualizarPrestadorServico(Visitante visitante) throws SQLException, BusinessException, Exception;
	
	public abstract void atualizarVisitanteESalvarPrestadorServico(Visitante visitante) throws SQLException, BusinessException, Exception;
	
	public abstract void atualizarVisitanteEAtualizarPrestadorServico(Visitante visitante) throws SQLException, BusinessException, Exception;
	
	public abstract void salvarSomenteVisitante(Visitante visitante) throws SQLException, BusinessException, Exception;
	
	public abstract void atualizarSomenteVisitante(Visitante visitante) throws SQLException, BusinessException, Exception;
	
	public abstract List<Visitante> buscaPorIdsENome(List<Integer> listaIds, String nome) throws SQLException, Exception;
	
	public abstract Visitante buscaPorId(Integer id) throws SQLException, Exception;
	

}