package br.com.condominiosvirtuais.persistence;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import br.com.condominiosvirtuais.entity.Componente;

public interface ComponenteDAO {
	
	public abstract List<Componente> buscarPorIdTela(Integer idTela) throws SQLException, Exception;
	
	public abstract List<Componente> buscarPorIdTela(Integer idTela, Connection con) throws SQLException, Exception; 
	
	public abstract List<Componente> buscarPoridGrupoUsuarioEIdTela(Integer idGrupoUsuario, Integer idTela, Connection con) throws SQLException, Exception;

}
