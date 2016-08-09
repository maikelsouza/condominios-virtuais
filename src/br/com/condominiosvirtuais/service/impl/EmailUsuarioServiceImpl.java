package br.com.condominiosvirtuais.service.impl;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

import javax.inject.Inject;

import br.com.condominiosvirtuais.entity.EmailUsuario;
import br.com.condominiosvirtuais.entity.Usuario;
import br.com.condominiosvirtuais.persistence.impl.EmailUsuarioDAOImpl;
import br.com.condominiosvirtuais.service.EmailUsuarioService;

public class EmailUsuarioServiceImpl implements EmailUsuarioService, Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject	
	private EmailUsuarioDAOImpl emailUsuarioDAO = null;
	
	
	public EmailUsuario buscarEmail(String emailUsuario) throws SQLException, Exception {
		return this.emailUsuarioDAO.buscarEmail(emailUsuario);
	}

	public List<EmailUsuario> buscarPorUsuario(Usuario usuario) throws SQLException, Exception {
		return this.emailUsuarioDAO.buscarPorUsuario(usuario);
	}

	public EmailUsuarioDAOImpl getEmailUsuarioDAO() {
		return emailUsuarioDAO;
	}

	public void setEmailUsuarioDAO(EmailUsuarioDAOImpl emailUsuarioDAO) {
		this.emailUsuarioDAO = emailUsuarioDAO;
	}

}
