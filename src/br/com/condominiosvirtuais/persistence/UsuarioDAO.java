package br.com.condominiosvirtuais.persistence;

import java.sql.Connection;
import java.sql.SQLException;

import br.com.condominiosvirtuais.entity.Condominio;
import br.com.condominiosvirtuais.entity.Usuario;

public interface UsuarioDAO {
	
	public abstract void buscarEPopularUsuarioPeloId(Usuario usuario, Connection con ) throws SQLException, Exception;
	
	public abstract void salvarDataHoraLogin(Usuario usuario) throws SQLException, Exception;
	
	public abstract void salvarDataHoraLogout(Usuario usuario) throws SQLException, Exception;
	
	public abstract Usuario buscarPorId(Integer idUsuario, Connection con) throws SQLException, Exception;
	
	public abstract Usuario buscarSindicoGeralPorCondominio(Condominio condominio, Connection con) throws SQLException, Exception;
	
	public abstract Usuario buscarSindicoGeralPorCondominio(Condominio condominio) throws SQLException, Exception;
	
	public abstract Boolean buscarPorIdESituacaoEPopularUsuarioPeloId(Usuario usuario, Connection con ) throws SQLException, Exception;

}
