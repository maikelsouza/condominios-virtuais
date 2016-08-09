package br.com.condominiosvirtuais.persistence;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import br.com.condominiosvirtuais.entity.Usuario;
import br.com.condominiosvirtuais.entity.UsuarioCondominio;

public interface UsuarioCondominioDAO {
	
	public abstract void salvarUsuarioCondominio(UsuarioCondominio usuarioCondominio, Connection con) throws SQLException, Exception;
	
	public abstract List<UsuarioCondominio> buscarListaPorUsuario(Usuario usuario) throws SQLException, Exception;
	
	public abstract List<UsuarioCondominio> buscarListaPorUsuario(Usuario usuario, Connection con) throws SQLException, Exception;
	
	public abstract void excluirPorUsuario(Usuario usuario, Connection con) throws SQLException, Exception;
	
	public abstract void atualizarPorId(UsuarioCondominio usuarioCondominio, Connection con) throws SQLException, Exception;

}
