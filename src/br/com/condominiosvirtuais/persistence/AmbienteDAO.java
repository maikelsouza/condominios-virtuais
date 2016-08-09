package br.com.condominiosvirtuais.persistence;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import br.com.condominiosvirtuais.entity.Ambiente;
import br.com.condominiosvirtuais.entity.Bloco;
import br.com.condominiosvirtuais.entity.Condominio;
import br.com.condominiosvirtuais.entity.Condomino;
import br.com.condominiosvirtuais.exception.BusinessException;

public interface AmbienteDAO extends TipoConjuntoBlocoDAO {	
	
	public abstract void excluir(Ambiente ambiente) throws SQLException, BusinessException, Exception;
	
	public abstract void atualizar(Ambiente ambiente) throws  SQLException, Exception;
	
	public abstract Ambiente buscarPorId(Integer idAmbiente) throws SQLException, Exception;
	
	public abstract Ambiente buscarPorId(Integer idAmbiente, Connection con) throws SQLException, Exception;
	
	public abstract List<Ambiente> buscarPorCondominioENomeAmbiente(Condominio condominio, String nomeAmbiente) throws SQLException, Exception;	
	
	public abstract List<Ambiente> buscarPorBlocoENomeAmbiente(Bloco bloco, String nomeAmbiente) throws SQLException, Exception;	
	
	public abstract List<Ambiente> buscarPorIdConjuntoBloco(Integer idConjuntoBloco) throws SQLException, Exception;
	
	public abstract List<Ambiente> buscarPorCondomino(Condomino condomino) throws SQLException, Exception;
	
	public abstract List<Ambiente> buscarPorCondominio(Condominio condominio, Connection con) throws SQLException, Exception;	
	

}
