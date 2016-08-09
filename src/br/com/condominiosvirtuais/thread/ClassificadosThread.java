package br.com.condominiosvirtuais.thread;

import java.sql.SQLException;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.inject.Inject;

import org.apache.log4j.Logger;

import br.com.condominiosvirtuais.entity.Classificados;
import br.com.condominiosvirtuais.entity.Email;
import br.com.condominiosvirtuais.entity.Usuario;
import br.com.condominiosvirtuais.service.ClassificadosService;
import br.com.condominiosvirtuais.service.EmailService;
import br.com.condominiosvirtuais.service.UsuarioService;
import br.com.condominiosvirtuais.util.AplicacaoUtil;
import br.com.condominiosvirtuais.util.MensagensEmailUtil;


/**
 * @author Maikel Joel de Souza
 * Thread criada para excluir automaticamente os an�ncios ap�s a data "DataExpira".
 * Essa thread tamb�m envia um email para o anunciante informando que o an�cio ir� expira na data X.
 */
public class ClassificadosThread extends Thread {
	
	private static Logger logger = Logger.getLogger(ClassificadosThread.class);
	
	@Inject
	private ClassificadosService classificadosService;
	
	@Inject 
	private EmailService emailService;
	
	@Inject
	private UsuarioService usuarioService;
	
	private static final String MESSAGES = "Messages_";
	
	
	@Override
	public void run() {	
		try {
			List<Classificados> listaClassificadosMaiorIgualExpira = null;
			List<Classificados> listaClassificadosExpira = null;
			Email email = null;
			Usuario usuario = null;
			Date hoje = null; 
			while(true){ // FIXME: MODIFICAR O sleep para 24h
				Thread.sleep(60*1000); // Executa a cada 24 horas
				logger.info("Inicio do processo exclus�o do an�ncio e aviso que ir� expirar em : " + new Date());
				hoje = new Date();
				listaClassificadosMaiorIgualExpira = this.classificadosService.buscarPorMaiorIgualDataExpira(hoje);
// TODO: Rever essa regra de neg�cio. Talvez n�o seja necess�rio excluir o an�ncio.				
//				for (Classificados classificados : listaClassificadosMaiorIgualExpira) {
//					this.classificadosService.excluir(classificados);
//				}
				Calendar CalendarHoje = Calendar.getInstance();
				CalendarHoje.setTime(hoje);
				// Configurando cinco dias para frente para poder avisar que o an�ncio ir� expirar. 
				CalendarHoje.add(Calendar.DATE, 5);
				listaClassificadosExpira = this.classificadosService.buscarPorDataExpira(CalendarHoje.getTime());
				for (Classificados classificados : listaClassificadosExpira) {
					usuario = this.usuarioService.buscarPorId(classificados.getIdUsuario());
					email = new Email();		
					// FIXME: REMOVER ANTER DE COLOCAR EM PRODU��O			  
					email.setPara("maikel.souza@gmail.com");
				//	email.setPara(usuario.getEmail().getEmail());
					//	email.setPara(usuario.getEmail().getEmail());
// N�o foi poss�vel obter o facesContaxt no contexto de uma thread, logo foi necessario criar uma solu��o paliativa para i18n.
// Se voc� est� tendo problema com a i18n, por conta do sistema est� sendo usados por usu�rios n�o brasileiros, ent�o parab�ns, o projeto deu muito certo!!!					
				//	Locale locale = Locale.getDefault();
				//	ResourceBundle bundle = ResourceBundle.getBundle(MESSAGES+locale, locale);					
					email.setAssunto("teste");
// TODO: C�digo comentado em 05/08/2016. Apagar em 90 dias					
//					Object[] parametros = new Object[3];
//					parametros[0] = usuario.getNome().toUpperCase();
//					parametros[1] = classificados.getTitulo().toUpperCase();
//					SimpleDateFormat formato = new SimpleDateFormat(bundle.getString("formatoData"));  
//					parametros[2] = formato.format(classificados.getDataExpira());					
				//	email.setMensagem(MensagensEmailUtil.expiraClassificados(classificados, usuario.getNome()));
				//	this.emailService.salvar(email);
					logger.info("Fim do processo exclus�o do an�ncio e aviso que ir� expirar em : " + new Date());
				}
			}
	    }catch (InterruptedException e) {
	    	logger.error("", e);										
		}catch (SQLException e) {
			logger.error("erro sqlstate "+e.getSQLState(), e);
		} catch (Exception e){
			logger.error("", e);				
		}
	}

}
