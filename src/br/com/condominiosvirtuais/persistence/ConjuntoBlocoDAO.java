package br.com.condominiosvirtuais.persistence;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import br.com.condominiosvirtuais.entity.ConjuntoBloco;
import br.com.condominiosvirtuais.exception.BusinessException;

public interface ConjuntoBlocoDAO {
	
	public abstract void salvar(ConjuntoBloco conjuntoBloco) throws SQLException,  Exception;
	                              
	public abstract ConjuntoBloco buscarPorId(Integer idConjuntoBloco, Connection con) throws SQLException, Exception;
		
	public abstract ConjuntoBloco buscarPorId(Integer idConjuntoBloco) throws SQLException, Exception;
	
	public abstract List<ConjuntoBloco> buscarPorTipo(Integer tipo) throws SQLException, Exception;
	
	public abstract void excluir(ConjuntoBloco conjuntoBloco) throws SQLException, BusinessException, Exception;
	
	public abstract void atualizar(ConjuntoBloco conjuntoBloco) throws SQLException, Exception;
	
	public abstract ConjuntoBloco buscarPorIdETipo(Integer idConjuntoBloco, Integer tipo, Connection con) throws SQLException, Exception;

}
