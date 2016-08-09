package br.com.condominiosvirtuais.service;

import java.sql.SQLException;
import java.util.List;

import br.com.condominiosvirtuais.entity.EmailUsuario;
import br.com.condominiosvirtuais.entity.Usuario;

public interface EmailUsuarioService {
	
	public abstract EmailUsuario buscarEmail(String emailUsuario) throws SQLException, Exception;
	
	public abstract List<EmailUsuario> buscarPorUsuario(Usuario usuario) throws SQLException, Exception;

}
