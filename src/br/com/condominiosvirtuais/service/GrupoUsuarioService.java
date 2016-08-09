package br.com.condominiosvirtuais.service;

import java.sql.SQLException;

import br.com.condominiosvirtuais.entity.GrupoUsuario;

public interface GrupoUsuarioService {
	
	public abstract GrupoUsuario buscarPorIdUsuario(Integer idUsuario) throws SQLException, Exception;

}
