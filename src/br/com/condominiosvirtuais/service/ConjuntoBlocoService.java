package br.com.condominiosvirtuais.service;

import java.sql.SQLException;
import java.util.List;

import br.com.condominiosvirtuais.entity.ConjuntoBloco;
import br.com.condominiosvirtuais.exception.BusinessException;

public interface ConjuntoBlocoService {
	
	public abstract void configuraTipoConjuntoBloco(Integer tipoConjuntoBloco);
	
	public abstract void salvar(ConjuntoBloco conjuntoBloco) throws SQLException, Exception;
	
	public abstract ConjuntoBloco buscarPorId(Integer id) throws SQLException, Exception;
	
	public abstract List<ConjuntoBloco> buscarPorTipo(Integer tipo) throws SQLException, Exception;	
	
	public abstract void excluir(ConjuntoBloco conjuntoBloco) throws SQLException, BusinessException, Exception;
	
	public abstract void atualizar(ConjuntoBloco conjuntoBloco) throws SQLException, Exception;	

}
