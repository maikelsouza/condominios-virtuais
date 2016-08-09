package br.com.condominiosvirtuais.persistence;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import br.com.condominiosvirtuais.entity.TipoConjuntoBloco;
import br.com.condominiosvirtuais.exception.BusinessException;

public interface TipoConjuntoBlocoDAO {
	
	public abstract List<TipoConjuntoBloco> buscarPorIdConjuntoBloco(Integer idConjuntoBloco, Connection con) throws SQLException, Exception;
		
	public abstract void salvar(TipoConjuntoBloco tipoConjuntoBloco) throws SQLException, Exception;
	
	public abstract void salvar(TipoConjuntoBloco tipoConjuntoBloco, Connection con) throws SQLException, Exception;	

	public abstract void atualizar(TipoConjuntoBloco tipoConjuntoBloco, Connection con) throws SQLException, Exception;
	
	public abstract void excluir(TipoConjuntoBloco tipoConjuntoBloco, Connection con) throws SQLException, BusinessException, Exception;
}
