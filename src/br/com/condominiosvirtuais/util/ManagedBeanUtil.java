package br.com.condominiosvirtuais.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.enterprise.context.Conversation;
import javax.faces.application.ConfigurableNavigationHandler;
import javax.faces.application.FacesMessage;
import javax.faces.application.NavigationCase;
import javax.faces.component.EditableValueHolder;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import br.com.condominiosvirtuais.enumeration.EnderecoImagemEnum;

/**
 * Classe utilit�ria criada para centralizar os m�todos que s�o uteis para a camada de apresenta��o
 * @author Maikel Joel de Souza
 */
public abstract class ManagedBeanUtil {
	
	private static Logger logger = Logger.getLogger(ManagedBeanUtil.class);
	
	
	/**
	 * M�todo que exibe uma mensagem, no formato de informa��o, na tela. <br> 
	 * Esse m�todo utiliza i18n
	 * @param chave - Chave para i18n
	 */
	public static void setMensagemInfo(String chave){
		FacesContext context  = null;
		FacesMessage facesMessage = null;
		ResourceBundle bundle = null;
		try{
			context = FacesContext.getCurrentInstance();
			Locale locale = context.getViewRoot().getLocale();		
			bundle = ResourceBundle.getBundle(FacesContext.getCurrentInstance().getApplication().getMessageBundle(), locale);
			facesMessage = new FacesMessage(bundle.getString(chave));
		} catch (MissingResourceException e) {
			logger.error("N�o foi poss�vel encontrar a chave \"" + e.getKey() + "\"", e);
			chave = "msg.info.msgInfoNaoEncontrada";
			facesMessage = new FacesMessage(bundle.getString(chave));			
		} catch (Exception e) {
			logger.error("", e);
		}finally{
			facesMessage.setSeverity(FacesMessage.SEVERITY_INFO);
			context.addMessage(null, facesMessage);
		}
	}
	
	/**
	 * M�todo que exibe uma mensagem, no formato de erro, na tela. <br> 
	 * Esse m�todo utiliza i18n
	 * @param chave - Chave para i18n
	 * @param msg - mensagen (n�o i18).
	 */
	public static void setMensagemErro(String chave, String msg){
		FacesContext context  = null;
		FacesMessage facesMessage = null;
		ResourceBundle bundle = null;
		try{
			context = FacesContext.getCurrentInstance();
			Locale locale = context.getViewRoot().getLocale();
			bundle = ResourceBundle.getBundle(FacesContext.getCurrentInstance().getApplication().getMessageBundle(), locale);
			facesMessage = new FacesMessage(bundle.getString(chave)+msg);
		} catch (MissingResourceException e) {
			logger.error("N�o foi poss�vel encontrar a chave \"" + e.getKey() + "\"", e);
			chave = "msg.erro.executarOperacao";
			facesMessage = new FacesMessage(bundle.getString(chave));			
		} catch (Exception e) {
			logger.error("", e);
		}finally{
			facesMessage.setSeverity(FacesMessage.SEVERITY_ERROR);
			context.addMessage(null, facesMessage);
		}
	}
	
	/**
	 * M�todo que exibe uma mensagem, no formato de erro, na tela. <br> 
	 * Esse m�todo utiliza i18n
	 * @param chave - Chave para i18n
	 */
	public static void setMensagemErro(String chave){
		FacesContext context  = null;
		FacesMessage facesMessage = null;
		ResourceBundle bundle = null;
		try{
			context = FacesContext.getCurrentInstance();
			Locale locale = context.getViewRoot().getLocale();
			bundle = ResourceBundle.getBundle(FacesContext.getCurrentInstance().getApplication().getMessageBundle(), locale);
			facesMessage = new FacesMessage(bundle.getString(chave));
		} catch (MissingResourceException e) {
			logger.error("N�o foi poss�vel encontrar a chave \"" + e.getKey() + "\"", e);
			chave = "msg.erro.executarOperacao";
			facesMessage = new FacesMessage(bundle.getString(chave));			
		} catch (Exception e) {
			logger.error("", e);
		}finally{
			facesMessage.setSeverity(FacesMessage.SEVERITY_ERROR);
			context.addMessage(null, facesMessage);
		}
	}
		
	/**
	 * M�todo que exibe uma mensagem, no formato de warn, na tela. <br> 
	 * Esse m�todo utiliza i18n
	 * @param chave - Chave para i18n
	 */
	public static void setMensagemWarn(String chave){
		FacesContext context  = null;
		FacesMessage facesMessage = null;
		ResourceBundle bundle = null;
		try{
			context = FacesContext.getCurrentInstance();
			Locale locale = context.getViewRoot().getLocale();		
			bundle = ResourceBundle.getBundle(FacesContext.getCurrentInstance().getApplication().getMessageBundle(), locale);
			facesMessage = new FacesMessage(bundle.getString(chave));
		} catch (MissingResourceException e) {
			logger.error("N�o foi poss�vel encontrar a chave \"" + e.getKey() + "\"", e);
			chave = "msg.warn.msgWarnNaoEncontrada";
			facesMessage = new FacesMessage(bundle.getString(chave));			
		} catch (Exception e) {
			logger.error("", e);
		}finally{
			facesMessage.setSeverity(FacesMessage.SEVERITY_WARN);
			context.addMessage(null, facesMessage);
		}
	}
	
	/**
	 * M�todo que exibe uma mensagem (i18n concatenado com msg n�o i18n), no formato de warn, na tela. <br> 
	 * Esse m�todo utiliza i18n
	 * @param chave - Chave para i18n
	 * @param msg - mensagen (n�o i18).
	 */
	public static void setMensagemWarn(String chave, String msg){
		FacesContext context  = null;
		FacesMessage facesMessage = null;
		ResourceBundle bundle = null;
		try{
			context = FacesContext.getCurrentInstance();
			Locale locale = context.getViewRoot().getLocale();		
			bundle = ResourceBundle.getBundle(FacesContext.getCurrentInstance().getApplication().getMessageBundle(), locale);
			facesMessage = new FacesMessage(bundle.getString(chave)+msg);			
		} catch (MissingResourceException e) {
			logger.error("N�o foi poss�vel encontrar a chave \"" + e.getKey() + "\"", e);
			chave = "msg.warn.msgWarnNaoEncontrada";
			facesMessage = new FacesMessage(bundle.getString(chave));			
		} catch (Exception e) {
			logger.error("", e);	
		}finally{
			facesMessage.setSeverity(FacesMessage.SEVERITY_WARN);
			context.addMessage(null, facesMessage);
		}
	}
	
	public static void setMensagemErro(Exception e){
		FacesContext context = FacesContext.getCurrentInstance();
		Locale locale = context.getViewRoot().getLocale();
		ResourceBundle bundle = ResourceBundle.getBundle(FacesContext.getCurrentInstance().getApplication().getMessageBundle(), locale);
		FacesMessage facesMessage = new FacesMessage(bundle.getString(e.getLocalizedMessage()));
		facesMessage.setSeverity(FacesMessage.SEVERITY_ERROR);
		context.addMessage(null, facesMessage);
	}
	
	
	public static void limpaMensagens(){
		FacesContext context = FacesContext.getCurrentInstance();
		Iterator<FacesMessage> facesMessage =  context.getMessages();
		while(facesMessage.hasNext()){
			facesMessage.remove();
		}		
	}
	
	/**
	 * 	
	 * @param novaSeNaoExiste - True - Cria uma nova sess�o caso n�o exista.
	 * @return HttpSession
	 */
	 public static HttpSession getSession(Boolean novaSeNaoExiste) {  
		 FacesContext context = FacesContext.getCurrentInstance();
		 HttpServletRequest request =  (HttpServletRequest) context.getExternalContext().getRequest();
		 HttpSession session = request.getSession(novaSeNaoExiste);
	     return session;  
	 }
	 
	 /**
	  *Limpa os dados dos componentes de edi��o e de seus filhos,
	  * recursivamente. Checa se o componente � inst�ncia de EditableValueHolder e 'reseta' suas propriedades.
	  * <p>
	  * 	Quando este m�todo, por algum motivo, n�o funcionar, parta para ignor�ncia e limpe o componente assim:
	  * <p><blockquote><pre>
	  * 	component.getChildren().clear()
	  * </pre></blockquote>	 
	  * Adaptado de Rafael Pontes: {@link https://github.com/rponte/jsf-loja-project/blob/master/src/br/com/triadworks/loja/util/FacesUtils.java}
	  */
	 public static void cleanSubmittedValues(UIComponent component){
		 if(component != null){
			 if (component instanceof EditableValueHolder) {
				 EditableValueHolder evh = (EditableValueHolder) component;
				 evh.setSubmittedValue(null);
				 evh.setValue(null);
				 evh.setLocalValueSet(false);
				 evh.setValid(true);
			 }
			 if(component.getChildCount()>0){
				 for (UIComponent child : component.getChildren()) {
					 cleanSubmittedValues(child);
				 }
			 }			 
		 }
	 }
	 
	 /**
	  * M�todo criado para popular um array de byte, com a imagem - Imagem N�o Dispon�vel
	  * @return Array de byte referente a imagem.
	  * @throws Exception 
	  * @throws FileNotFoundException
	  * @throws IOException
	  */
	 public static byte[] popularImagemNaoDisponivel() throws Exception {
		 byte[] sendBuf = null;
		 try{
			 File file = new File(AplicacaoUtil.getCaminhoAplicacao()+EnderecoImagemEnum.URL_FOTO_NAO_DISPONIVEL.getEnderecoImagem());			    
			 int len = (int) file.length();    
			 sendBuf = new byte[len];  
			 FileInputStream inFile  = null;
			 inFile = new FileInputStream(file);           
			 inFile.read(sendBuf, 0, len);
		 } catch (FileNotFoundException e) {
			 logger.error("", e);		
			 throw new Exception("msg.erro.executarOperacao",e);
		 } catch (IOException e) {
			 logger.error("", e);		
			 throw new Exception("msg.erro.executarOperacao",e);
		 } catch (Exception e) {
			 logger.error("", e);		
			 throw new Exception("msg.erro.executarOperacao",e);
	     }
		 return sendBuf;
	 }
	 
	 
	 /**
	  * Popula a lista de SI com a quantidade de dias de um m�s, ou seja, 1 at� 31.
	  * @param listaSIDias - Lista que ser� populada
	  */
	 public static void popularSIDias(List<SelectItem> listaSIDias){		
		for (int i = 1; i <= 31; i++) {
			listaSIDias.add(new SelectItem(i, String.valueOf(i)));		
		}
	 }
	
	 /**
	  * Popula a lista de SI com a quantidade de meses que tem um ano, ou seja, 1 at� 12.
	  * Obs.: Janeiro come�a com zero.
	  * @param listaSIMeses - Lista que ser� populada
	  */
	public static void popularSIMeses(List<SelectItem> listaSIMeses){		
		for (int i = 1; i <=12 ; i++) {
			listaSIMeses.add(new SelectItem(i-1, String.valueOf(i)));		
		}
	}
	
	/**
	 * Popula a quantidade de anos iniciando em 1930 at� o ano atual. 
	 * @param listaSIAnos - Lista que ser� populada
	 */
	public static void popularSIAnos(List<SelectItem> listaSIAnos){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		Integer anoAtual = calendar.get(Calendar.YEAR);
		for (int i = 1930; i <= anoAtual; i++) {
			listaSIAnos.add(new SelectItem(i, String.valueOf(i)));	
		}
	}
	
	/**
	 * Popula a quantidade de anos iniciando com o ano passado como par�metro at� o ano atual. 
	 * @param listaSIAnos - Lista que ser� populada
	 * @param anoInicial - Ano de in�cio da lista
	 * @param crescente - ordena��o da lista. (crescente = TRUE, decrescente == FALSE)
	 */
	public static void popularSIAnos(List<SelectItem> listaSIAnos, Integer anoInicial, Boolean crescente){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());		
		Integer anoAtual = calendar.get(Calendar.YEAR);
		if(crescente == Boolean.TRUE){
			for (int i = anoInicial; i <= anoAtual; i++) {
				listaSIAnos.add(new SelectItem(i, String.valueOf(i)));	
			}			
		}else{
			for (int i = anoAtual; i >= anoInicial; i--) {
				listaSIAnos.add(new SelectItem(i, String.valueOf(i)));	
			}
		}
	}
	
	/**
	 * M�todo que recebe um endere�o de email e verificar se ele est� v�lido
	 * @param email
	 * @return padr�o v�lido (true) ou inv�lido (false)
	 * 
	 * Observa��o: N�o foi poss�vel realizar a valida��o diretamente no xhtml. Sempre mostrava como padr�o inv�lido. 
	 */
	public static Boolean validaEmail(String email) {
		Boolean emailValido = Boolean.TRUE;
	    Pattern p = Pattern.compile("^[\\w-]+(\\.[\\w-]+)*@([\\w-]+\\.)+[a-zA-Z]{2,7}$");
	    Matcher m = p.matcher(email); 
	    if (!m.find()){
	    	emailValido = Boolean.FALSE;
	    }
	    return emailValido;  
	 }
	
	/**
	 * M�todo que recebe uma string e verifica se existe somente n�mero nela
	 * @param string
	 * @return somente n�mero (true) ou existeLetra (false)
	 *  
	 */
	public static Boolean verificaSeExisteSomenteNumeros(String string) {
		Boolean somenteNumero = Boolean.TRUE;
	    Pattern p = Pattern.compile("^[0-9]+$");
	    Matcher m = p.matcher(string); 
	    if (!m.find()){
	    	somenteNumero = Boolean.FALSE;
	    }
	    return somenteNumero;  
	 }
	
	
	public static Map<String,Set<NavigationCase>> getMapNavigationCase(){
		FacesContext context = FacesContext.getCurrentInstance();
		ConfigurableNavigationHandler navigationHandler = (ConfigurableNavigationHandler) context.getApplication().getNavigationHandler();
		return navigationHandler.getNavigationCases();
	}
	
	
	public static void abreSessao(Conversation conversation){		
//		if(conversation.isTransient()){
//			conversation.setTimeout(60000);
//			conversation.begin();
//		}
	}

	public static void fechaSessao(Conversation conversation){
//		if(conversation!=null && !conversation.isTransient()){
//			conversation.end();		
//		}
	}	
	
	 
}