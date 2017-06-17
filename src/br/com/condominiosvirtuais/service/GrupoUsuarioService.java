package br.com.condominiosvirtuais.service;

import java.sql.SQLException;
import java.util.List;

import br.com.condominiosvirtuais.entity.GrupoUsuario;

public interface GrupoUsuarioService {
	
	public abstract GrupoUsuario buscarPorIdUsuario(Integer idUsuario) throws SQLException, Exception;
	
	public abstract List<GrupoUsuario> buscarPorIdCondominioESituacao(Integer idCondominio, Boolean situacao) throws SQLException, Exception;
	
	public abstract List<GrupoUsuario> buscarPorIdCondominio(Integer idCondominio) throws SQLException, Exception;

}
