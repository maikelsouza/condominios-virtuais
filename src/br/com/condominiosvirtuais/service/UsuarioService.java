package br.com.condominiosvirtuais.service;

import java.sql.SQLException;
import java.util.List;

import br.com.condominiosvirtuais.entity.Condominio;
import br.com.condominiosvirtuais.entity.Usuario;

public interface UsuarioService {
	
	public abstract Usuario buscarPorEmailPrincipal(String emailPrincipal) throws SQLException, Exception;
	
	public abstract void atualizar(Usuario usuario) throws SQLException, Exception;
	
	public abstract void atualizarSenha(Usuario usuario) throws SQLException, Exception;
	
	public abstract Usuario buscarPorId(Integer idUsuario) throws SQLException, Exception;
	
	public abstract void salvarDataHoraLogin(Usuario usuario) throws SQLException, Exception;
	
	public abstract void salvarDataHoraLogout(Usuario usuario) throws SQLException, Exception;	
	
	public abstract Usuario buscarSindicoGeralPorCondominio(Condominio condominio) throws SQLException, Exception;
	
	public abstract void associar(List<Usuario> listaDeUsuarios, Integer idGrupoUsuario) throws SQLException, Exception;

}
