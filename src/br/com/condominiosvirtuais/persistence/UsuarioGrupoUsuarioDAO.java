package br.com.condominiosvirtuais.persistence;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import br.com.condominiosvirtuais.entity.UsuarioGrupoUsuario;

public interface UsuarioGrupoUsuarioDAO {
	
	
	public abstract List<UsuarioGrupoUsuario> buscarPorIdUsuario(Integer idUsuario) throws SQLException, Exception;
		
	public abstract void excluirPorIdGrupoUsuario(Integer idGrupoUsuario, Connection con) throws SQLException, Exception;
	
	public abstract List<UsuarioGrupoUsuario> buscarPorIdGrupoUsuario(Integer idGrupoUsuario, Integer idUsuario, Connection con) throws SQLException, Exception;

}
