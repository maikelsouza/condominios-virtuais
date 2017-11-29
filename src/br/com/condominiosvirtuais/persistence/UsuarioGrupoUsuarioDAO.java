package br.com.condominiosvirtuais.persistence;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import br.com.condominiosvirtuais.entity.UsuarioGrupoUsuario;

public interface UsuarioGrupoUsuarioDAO {
	
	
	public abstract List<UsuarioGrupoUsuario> buscarPorIdUsuario(Integer idUsuario) throws SQLException, Exception;
		
	public abstract void excluirPorIdGrupoUsuario(Integer idGrupoUsuario, Connection con) throws SQLException, Exception;
	
	public abstract UsuarioGrupoUsuario buscarPorIdGrupoUsuarioEIdUsuario(Integer idGrupoUsuario, Integer idUsuario, Connection con) throws SQLException, Exception;
	
	public abstract void associar(List<UsuarioGrupoUsuario> listaUsuariosAssociados, List<UsuarioGrupoUsuario> listaOriginalUsuariosAssociados) throws SQLException, Exception;
	
	public abstract List<UsuarioGrupoUsuario> buscarPorIdUsuario(Integer idUsuario, Connection con) throws SQLException, Exception;
	
	public abstract void desassociar(List<UsuarioGrupoUsuario> listaUsuarioGrupoUsuario) throws SQLException, Exception;
	
	public abstract void excluirPorIdGrupoUsuarioEIdUsuario(Integer idGrupoUsuario, Integer idUsuario, Connection con) throws SQLException, Exception;
	
	public abstract void salvar(UsuarioGrupoUsuario usuarioGrupoUsuario, Connection con) throws SQLException, Exception;
	
	public abstract void atualizar(UsuarioGrupoUsuario usuarioGrupoUsuario, Connection con) throws SQLException, Exception;
	
	public abstract void excluirPorId(Integer id, Connection con) throws SQLException, Exception;

}
