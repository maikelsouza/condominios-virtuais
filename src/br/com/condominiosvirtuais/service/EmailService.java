package br.com.condominiosvirtuais.service;

import java.io.File;
import java.sql.SQLException;
import java.util.List;

import javax.mail.MessagingException;

import br.com.condominiosvirtuais.entity.Email;

public interface EmailService {
	
	public abstract void salvar(Email email) throws SQLException, Exception;	
		
	public abstract void excluir(Email email) throws SQLException, Exception;
	
	public abstract void enviar(Email email) throws MessagingException;
	
	public abstract void enviarHTML(Email email, String enderecoLogo, String charset) throws MessagingException;
	
	public abstract void enviarSESAmazon(Email email) throws MessagingException;	
	
	public abstract List<Email> buscar(Integer limite) throws SQLException, Exception;
	
	public abstract void set(File file) throws SQLException, Exception;
	
	


}
