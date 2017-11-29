package br.com.condominiosvirtuais.persistence;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import br.com.condominiosvirtuais.entity.Bloco;

public interface BlocoDAO {
	
	public abstract List<Bloco> buscarPorIdCondominio(Integer idCondominio, Connection con) throws SQLException, Exception;
	
	public abstract List<Integer> buscarListaIdsBlocosPorIdCondominio(Integer idCondominio) throws SQLException, Exception;
	
	public abstract void popularBloco(Bloco bloco) throws SQLException, Exception;
	
	public abstract Bloco buscarPorId(Integer idBloco, Connection con) throws SQLException, Exception;
	
	public abstract List<Bloco> buscarListaBlocosPoIdCondominio(Integer idCondominio) throws SQLException, Exception;

}
