package br.com.condominiosvirtuais.thread;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.mail.MessagingException;

import org.apache.log4j.Logger;

import br.com.condominiosvirtuais.controller.PrincipalMB;
import br.com.condominiosvirtuais.entity.Email;
import br.com.condominiosvirtuais.enumeration.ConfiguracaoAplicacaoEnum;
import br.com.condominiosvirtuais.service.ConfiguracaoAplicacaoService;
import br.com.condominiosvirtuais.service.EmailService;

/**
 * @author Maikel Joel de Souza
 * Thread criada para buscar os e-mail's que estão na tabela de e-mail {@link Email}, enviar e apagar. <br>
 * Inicialmente essa thread foi configurada para rodar a cada 5 minutos.
 * @since 12/02/2014
 */

public class EmailThread extends Thread {

	private static Logger logger = Logger.getLogger(EmailThread.class);	
	
	@Inject 
	private EmailService emailService = null;
	
	@Inject
	PrincipalMB principalMB;
	
	
	
	@Inject
	private ConfiguracaoAplicacaoService configuracaoAplicacaoService = null;
	
	
	@Override
	public void run() {
		try {
			while(true){
				Thread.sleep(5*60*1000); // Executa a cada 5 minutos - Rever esse calculo
				logger.info("Inicio do processo para envio de email em : " + new Date());
				List<Email> listaEmail = this.emailService.buscar(Integer.parseInt(this.configuracaoAplicacaoService.getConfiguracoes().
			    get(ConfiguracaoAplicacaoEnum.QTD_ENVIO_EMAIL_LOTE.getChave())));
				for (Email email : listaEmail) {
					this.emailService.enviarHTML(email, principalMB.getCaminhoAplicacao(), principalMB.getCharset());
					this.emailService.excluir(email);					 
				 }
				logger.info("Fim do processo para envio de email em : " + new Date());
			}
	    }catch (InterruptedException e) {
	    	logger.error("Erro referente ao sleep da thread.", e);							
		} catch (MessagingException e) {
			logger.error("Não foi possível enviar os email's", e);
		} catch (NumberFormatException e) {
			logger.error("", e);
		} catch (SQLException e) {
			logger.error("Não foi possível escluir os email's. erro sqlstate "+e.getSQLState(), e);
		} catch (Exception e) {
			logger.error("", e);
		}
	}
}
