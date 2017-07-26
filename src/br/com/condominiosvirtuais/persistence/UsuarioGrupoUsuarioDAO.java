package br.com.condominiosvirtuais.persistence;

import java.sql.SQLException;
import java.util.List;

import br.com.condominiosvirtuais.entity.UsuarioGrupoUsuario;

public interface UsuarioGrupoUsuarioDAO {
	
	
	public abstract List<UsuarioGrupoUsuario> buscarPorIdUsuario(Integer idUsuario) throws SQLException, Exception;

}
