package br.com.condominiosvirtuais.persistence;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import br.com.condominiosvirtuais.entity.Condominio;
import br.com.condominiosvirtuais.entity.Usuario;

public interface CondominoDAO {
	
	public abstract List<Integer> buscarListaIdsCondominosPorIdUnidade(Integer idUnidade) throws SQLException, Exception;
	
	public abstract Usuario buscarSindicoGeralPorCondominio(Condominio condominio, Connection con) throws SQLException, Exception;

}
