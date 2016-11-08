package br.com.condominiosvirtuais.service.impl;

import java.io.File;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;
import java.util.StringTokenizer;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.inject.Inject;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import br.com.condominiosvirtuais.entity.Email;
import br.com.condominiosvirtuais.enumeration.ConfiguracaoAplicacaoEnum;
import br.com.condominiosvirtuais.persistence.EmailDAO;
import br.com.condominiosvirtuais.service.ConfiguracaoAplicacaoService;
import br.com.condominiosvirtuais.service.EmailService;


public class EmailServiceImpl implements EmailService, Serializable {
	
	private static final long serialVersionUID = 1L;

	@Inject
	private ConfiguracaoAplicacaoService configuracaoAplicacaoService = null;
	
	@Inject
	private EmailDAO emailDAO = null;


	public void salvar(Email email) throws SQLException, Exception {
		this.emailDAO.salvar(email);
	}

	public void excluir(Email email) throws SQLException, Exception {
		this.emailDAO.excluir(email);
	}
	
	public void enviar(Email email) throws MessagingException {
		
		Properties propertie = new Properties();  
		propertie.put(ConfiguracaoAplicacaoEnum.MAIL_SMTP_AUTH.getChave(),
				this.configuracaoAplicacaoService.getConfiguracoes().get(ConfiguracaoAplicacaoEnum.MAIL_SMTP_AUTH.getChave()));  	
		propertie.put(ConfiguracaoAplicacaoEnum.MAIL_SMTP_PORT.getChave(),
				this.configuracaoAplicacaoService.getConfiguracoes().get(ConfiguracaoAplicacaoEnum.MAIL_SMTP_PORT.getChave()));  
		Session session = Session.getInstance(propertie);
		Message msg = new MimeMessage(session);
		InternetAddress enderecoDe = null;
		if (email.getDe() == null){ // Situação onde não foi expecificado um remetente, logo o remetente é a aplicação.
			enderecoDe = new InternetAddress(this.configuracaoAplicacaoService.getConfiguracoes().get(ConfiguracaoAplicacaoEnum.USUARIO_EMAIL.getChave()));			
		}else{
			enderecoDe = new InternetAddress(email.getDe());
		}
		msg.setFrom(enderecoDe);		
		StringTokenizer parser = new StringTokenizer(email.getPara(),";");
		InternetAddress[] enderecoPara = new InternetAddress[parser.countTokens()];
		Integer i = 0;
	    while (parser.hasMoreTokens()) {
	    	enderecoPara[i++] = new InternetAddress(parser.nextToken().trim());	    	
	    }		
		msg.setRecipients(Message.RecipientType.TO, enderecoPara);
		if(email.getCc() != null){			
			parser = new StringTokenizer(email.getCc(),";");
			InternetAddress[] enderecoComCopia = new InternetAddress[parser.countTokens()];
			i = 0;
		    while (parser.hasMoreTokens()) {
		    	enderecoComCopia[i++] = new InternetAddress(parser.nextToken().trim());	    	
		    }			
			msg.setRecipients(Message.RecipientType.CC, enderecoComCopia);
		}
		if(email.getCo() != null){			
			parser = new StringTokenizer(email.getCo(),";");
			InternetAddress[] enderecoComCopiaOculta = new InternetAddress[parser.countTokens()];
			i = 0;
		    while (parser.hasMoreTokens()) {
		    	enderecoComCopiaOculta[i++] = new InternetAddress(parser.nextToken().trim());	    	
		    }			
			msg.setRecipients(Message.RecipientType.BCC, enderecoComCopiaOculta);
		}
		
		msg.setSubject(email.getAssunto());		
		msg.setText(email.getMensagem());
		
		Transport tr = session.getTransport(this.configuracaoAplicacaoService.getConfiguracoes().get(
				ConfiguracaoAplicacaoEnum.PROTOCOLO_EMAIL_SECURITY.getChave()));
		tr.connect(this.configuracaoAplicacaoService.getConfiguracoes().get(
				ConfiguracaoAplicacaoEnum.HOST_EMAIL.getChave()), 
				this.configuracaoAplicacaoService.getConfiguracoes().get(
						ConfiguracaoAplicacaoEnum.USUARIO_EMAIL.getChave()), 
						this.configuracaoAplicacaoService.getConfiguracoes().get(
								ConfiguracaoAplicacaoEnum.SENHA_EMAIL.getChave()));
		tr.sendMessage(msg, msg.getAllRecipients());		
		tr.close();		
	}
	
 public void enviarHTML(Email email, String endereçoLogo) throws MessagingException {
	 
		 Multipart multipart = new MimeMultipart("related");
		 
	     BodyPart messageBodyPart = new MimeBodyPart();     
	     messageBodyPart.setContent(email.getMensagem(), "text/html");
	     multipart.addBodyPart(messageBodyPart);
	      
	     messageBodyPart = new MimeBodyPart();
	     DataSource fds = new FileDataSource (endereçoLogo+File.separator+"imagens"+File.separator+"logo_cabecalho.png");	     
	     messageBodyPart.setDataHandler(new DataHandler(fds));
	     messageBodyPart.setFileName("logo_cabecalho.png");
	     messageBodyPart.setDisposition(MimeBodyPart.INLINE);
	     messageBodyPart.setHeader("Content-ID","<logo>");
	     multipart.addBodyPart(messageBodyPart);
	     
	     
	     messageBodyPart = new MimeBodyPart();
	     fds = new FileDataSource (endereçoLogo+File.separator+"imagens"+File.separator+"google_plus.png");	    
	     messageBodyPart.setDataHandler(new DataHandler(fds));
	     messageBodyPart.setFileName("google_plus.png");
	     messageBodyPart.setHeader("Content-ID","<googlePlus>");   
	     messageBodyPart.setDisposition(MimeBodyPart.INLINE);
	     multipart.addBodyPart(messageBodyPart);
	     
	     messageBodyPart = new MimeBodyPart();
	     fds = new FileDataSource (endereçoLogo+File.separator+"imagens"+File.separator+"linkedin.png");	     
	     messageBodyPart.setDataHandler(new DataHandler(fds));
	     messageBodyPart.setFileName("linkedin.png");
	     messageBodyPart.setHeader("Content-ID","<linkedin>");
	     messageBodyPart.setDisposition(MimeBodyPart.INLINE);
	     multipart.addBodyPart(messageBodyPart);
	     
	     messageBodyPart = new MimeBodyPart();
	     fds = new FileDataSource (endereçoLogo+File.separator+"imagens"+File.separator+"facebookMenor.png");	     
	     messageBodyPart.setDataHandler(new DataHandler(fds));
	     messageBodyPart.setFileName("facebookMenor.png");
	     messageBodyPart.setDisposition(MimeBodyPart.INLINE);
	     messageBodyPart.setHeader("Content-ID","<facebook>");
	     multipart.addBodyPart(messageBodyPart);
	     
		Properties propertie = new Properties();  
		propertie.put(ConfiguracaoAplicacaoEnum.MAIL_SMTP_AUTH.getChave(),
				this.configuracaoAplicacaoService.getConfiguracoes().get(ConfiguracaoAplicacaoEnum.MAIL_SMTP_AUTH.getChave()));  	
		propertie.put(ConfiguracaoAplicacaoEnum.MAIL_SMTP_PORT.getChave(),
				this.configuracaoAplicacaoService.getConfiguracoes().get(ConfiguracaoAplicacaoEnum.MAIL_SMTP_PORT.getChave()));  
		Session session = Session.getInstance(propertie);
		
		Message msg = new MimeMessage(session);
		InternetAddress enderecoDe = null;
		if (email.getDe() == null){ // Situação onde não foi expecificado um remetente, logo o remetente é a aplicação.
			enderecoDe = new InternetAddress(this.configuracaoAplicacaoService.getConfiguracoes().get(ConfiguracaoAplicacaoEnum.USUARIO_EMAIL.getChave()));			
		}else{
			enderecoDe = new InternetAddress(email.getDe());
		}
		msg.setFrom(enderecoDe);		
		StringTokenizer parser = new StringTokenizer(email.getPara(),";");
		InternetAddress[] enderecoPara = new InternetAddress[parser.countTokens()];
		Integer i = 0;
	    while (parser.hasMoreTokens()) {
	    	enderecoPara[i++] = new InternetAddress(parser.nextToken().trim());	    	
	    }		
		msg.setRecipients(Message.RecipientType.TO, enderecoPara);
		if(email.getCc() != null){			
			parser = new StringTokenizer(email.getCc(),";");
			InternetAddress[] enderecoComCopia = new InternetAddress[parser.countTokens()];
			i = 0;
		    while (parser.hasMoreTokens()) {
		    	enderecoComCopia[i++] = new InternetAddress(parser.nextToken().trim());	    	
		    }			
			msg.setRecipients(Message.RecipientType.CC, enderecoComCopia);
		}
		if(email.getCo() != null){			
			parser = new StringTokenizer(email.getCo(),";");
			InternetAddress[] enderecoComCopiaOculta = new InternetAddress[parser.countTokens()];
			i = 0;
		    while (parser.hasMoreTokens()) {
		    	enderecoComCopiaOculta[i++] = new InternetAddress(parser.nextToken().trim());	    	
		    }			
			msg.setRecipients(Message.RecipientType.BCC, enderecoComCopiaOculta);
		}
		msg.setReplyTo(new InternetAddress[]{ new InternetAddress("Não Responda <nao-responda@condominiosvirtuais.com.br>")});		
		msg.setSubject(email.getAssunto());	
		msg.setContent(multipart);
		
		Transport tr = session.getTransport(this.configuracaoAplicacaoService.getConfiguracoes().get(
				ConfiguracaoAplicacaoEnum.PROTOCOLO_EMAIL_SECURITY.getChave()));
		tr.connect(this.configuracaoAplicacaoService.getConfiguracoes().get(
				ConfiguracaoAplicacaoEnum.HOST_EMAIL.getChave()), 
				this.configuracaoAplicacaoService.getConfiguracoes().get(
						ConfiguracaoAplicacaoEnum.USUARIO_EMAIL.getChave()), 
						this.configuracaoAplicacaoService.getConfiguracoes().get(
								ConfiguracaoAplicacaoEnum.SENHA_EMAIL.getChave()));
		tr.sendMessage(msg, msg.getAllRecipients());
		tr.close();
	}
	
	
	@Override
	public void enviarSESAmazon(Email email) throws MessagingException {
		Properties propertie = new Properties(); 		
		propertie.put(ConfiguracaoAplicacaoEnum.MAIL_SMTP_AUTH.getChave(),
				this.configuracaoAplicacaoService.getConfiguracoes().get(ConfiguracaoAplicacaoEnum.MAIL_SMTP_AUTH.getChave()));  	
		propertie.put(ConfiguracaoAplicacaoEnum.MAIL_SMTP_PORT.getChave(),25);
		//propertie.put("host_email","email-smtp.us-east-1.amazonaws.com");
		Session session = Session.getInstance(propertie);
		Message msg = new MimeMessage(session);
		InternetAddress enderecoDe = null;
		if (email.getDe() == null){ // Situação onde não foi expecificado um remetente, logo o remetente é a aplicação.
			enderecoDe = new InternetAddress(this.configuracaoAplicacaoService.getConfiguracoes().get(ConfiguracaoAplicacaoEnum.USUARIO_EMAIL.getChave()));			
		}else{
			enderecoDe = new InternetAddress(email.getDe());
		}
		msg.setFrom(enderecoDe);		
		StringTokenizer parser = new StringTokenizer(email.getPara(),";");
		InternetAddress[] enderecoPara = new InternetAddress[parser.countTokens()];
		Integer i = 0;
	    while (parser.hasMoreTokens()) {
	    	enderecoPara[i++] = new InternetAddress(parser.nextToken().trim());	    	
	    }		
		msg.setRecipients(Message.RecipientType.TO, enderecoPara);
		if(email.getCc() != null){			
			parser = new StringTokenizer(email.getCc(),";");
			InternetAddress[] enderecoComCopia = new InternetAddress[parser.countTokens()];
			i = 0;
		    while (parser.hasMoreTokens()) {
		    	enderecoComCopia[i++] = new InternetAddress(parser.nextToken().trim());	    	
		    }			
			msg.setRecipients(Message.RecipientType.CC, enderecoComCopia);
		}
		if(email.getCo() != null){			
			parser = new StringTokenizer(email.getCo(),";");
			InternetAddress[] enderecoComCopiaOculta = new InternetAddress[parser.countTokens()];
			i = 0;
		    while (parser.hasMoreTokens()) {
		    	enderecoComCopiaOculta[i++] = new InternetAddress(parser.nextToken().trim());	    	
		    }			
			msg.setRecipients(Message.RecipientType.BCC, enderecoComCopiaOculta);
		}
		
		msg.setSubject(email.getAssunto());		
		msg.setText(email.getMensagem());
		Transport tr = session.getTransport(this.configuracaoAplicacaoService.getConfiguracoes().get(
				ConfiguracaoAplicacaoEnum.PROTOCOLO_EMAIL_SECURITY.getChave()));
		
		tr.connect("email-smtp.us-east-1.amazonaws.com", 
				"AKIAINAF6C67ONXFIXKA", 
						"AmiF24/9ye5xKQ0SGUyrGFlQGXQ5yH9teUQD65IlrTx9");
		tr.sendMessage(msg, msg.getAllRecipients());		
		tr.close();		

	}

	@Override
	public List<Email> buscar(Integer limite) throws SQLException, Exception {		
		return this.emailDAO.busca(limite);
	}

	public ConfiguracaoAplicacaoService getConfiguracaoAplicacaoService() {
		return configuracaoAplicacaoService;
	}

	public void setConfiguracaoAplicacaoService(
			ConfiguracaoAplicacaoService configuracaoAplicacaoService) {
		this.configuracaoAplicacaoService = configuracaoAplicacaoService;
	}

	public EmailDAO getEmailDAO() {
		return emailDAO;
	}

	public void setEmailDAO(EmailDAO emailDAO) {
		this.emailDAO = emailDAO;
	}

	@Override
	public void set(File file) throws SQLException, Exception {
//		this.file = file;
		
	}

}
