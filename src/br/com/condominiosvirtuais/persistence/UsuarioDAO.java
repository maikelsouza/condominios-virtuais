package br.com.condominiosvirtuais.persistence;

import java.sql.Connection;
import java.sql.SQLException;

import br.com.condominiosvirtuais.entity.Usuario;

public interface UsuarioDAO {
	
	public abstract void buscarEPopularUsuarioPeloId(Usuario usuario, Connection con ) throws SQLException, Exception;
	
	public abstract void salvarDataHoraLogin(Usuario usuario) throws SQLException, Exception;
	
	public abstract void salvarDataHoraLogout(Usuario usuario) throws SQLException, Exception;
	
	public abstract Usuario buscarPorId(Integer idUsuario, Connection con) throws SQLException, Exception;

}
