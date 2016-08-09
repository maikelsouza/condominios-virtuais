package br.com.condominiosvirtuais.persistence;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import br.com.condominiosvirtuais.entity.BlocoConjuntoBloco;
import br.com.condominiosvirtuais.entity.ConjuntoBloco;

public interface BlocoConjuntoBlocoDAO {	
	
	public abstract void salvar(BlocoConjuntoBloco blocoConjuntoBloco, Connection con) throws SQLException, Exception;
	
	public abstract List<BlocoConjuntoBloco> buscarPorIdBloco(Integer idBloco, Connection con)throws SQLException, Exception;
	
	public abstract List<BlocoConjuntoBloco> buscarPorIdConjuntoBloco(Integer idConjuntoBloco, Connection con) throws SQLException, Exception;
	
	public abstract void excluir(BlocoConjuntoBloco blocoConjuntoBloco, Connection con) throws SQLException, Exception;
	
	public abstract void excluirPorConjuntoBloco(ConjuntoBloco conjuntoBloco, Connection con) throws SQLException, Exception;
	
	

}
