package br.com.condominiosvirtuais.persistence;

import java.sql.SQLException;

import br.com.condominiosvirtuais.entity.GrupoUsuario;

public interface GrupoUsuarioDAO {
	
	public abstract GrupoUsuario buscarPorIdUsuario(Integer idUsuario) throws SQLException, Exception;
	
	public abstract void excluir(Integer idGrupoUsuario) throws SQLException, Exception;

}
