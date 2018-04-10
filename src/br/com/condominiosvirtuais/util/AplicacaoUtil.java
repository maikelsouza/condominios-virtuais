package br.com.condominiosvirtuais.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import br.com.condominiosvirtuais.entity.Usuario;
import br.com.condominiosvirtuais.enumeration.AtributoSessaoEnum;

/**
 * Classe utilitária criada para ser utilizada por toda a aplicação (todas as camadas) *  
 * @author Maikel Joel de Souza 
 */
public abstract class AplicacaoUtil {
	
	/**
	 * Método que recupera o usuário que está autenticado na aplicação.
	 * 
	 * @return Usuario
	 */
	public static Usuario getUsuarioAutenticado() {  
		 FacesContext context = FacesContext.getCurrentInstance();
		 HttpServletRequest request =  (HttpServletRequest) context.getExternalContext().getRequest();
		 HttpSession session = request.getSession(false);
	     return (Usuario) session.getAttribute(AtributoSessaoEnum.AUTENTICADO.getAtributo());  
	     
	 } 
	
	public static String gerarHashMD5(String string) throws NoSuchAlgorithmException{
		MessageDigest md = MessageDigest.getInstance("MD5");
		md.update(string.getBytes());
		byte[] hashMd5 = md.digest();
		StringBuilder s = new StringBuilder();
		for (int i = 0; i < hashMd5.length; i++) {
	       int parteAlta = ((hashMd5[i] >> 4) & 0xf) << 4;
	       int parteBaixa = hashMd5[i] & 0xf;
	       if (parteAlta == 0) s.append('0');
	       s.append(Integer.toHexString(parteAlta | parteBaixa));
		}	   
		return s.toString();
	}	

	public static String i18n(String chave){
		FacesContext context = FacesContext.getCurrentInstance();
		Locale locale = context.getViewRoot().getLocale();
		ResourceBundle bundle = ResourceBundle.getBundle(FacesContext.getCurrentInstance().getApplication().getMessageBundle(), locale);
		return bundle.getString(chave);		
	}
	
	public static String i18n(String chave, Object[] parametros) throws InstantiationException, IllegalAccessException{
		FacesContext context = FacesContext.getCurrentInstance();
		Locale locale = context.getViewRoot().getLocale();
		ResourceBundle bundle = ResourceBundle.getBundle(FacesContext.getCurrentInstance().getApplication().getMessageBundle(), locale);		
		return MessageFormat.format(bundle.getString(chave), parametros);		
	}
	
	public static Boolean validaExpressaoRegular(String padrao, String valor){
		Pattern pattern = Pattern.compile(padrao);
		Matcher matcher = pattern.matcher(valor);
		return matcher.find();
	}
	
	public static String getCaminhoAplicacao(){
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);  
		String path = session.getServletContext().getRealPath("/");
		return path;
	}
	
	public static String formatarData(String formatoData, Date data){		
		SimpleDateFormat spd = new SimpleDateFormat(formatoData);
		return spd.format(data);
	}
	
	public static String formatarMoeda(String formatoMoeda, Double valor){		
		 DecimalFormat df = new DecimalFormat(formatoMoeda);
		return df.format(valor);
	}
	
	public static String formatarCep(Integer cep){		
		String cepString = String.valueOf(cep);
		String cepStringRetorno = cepString.substring(0, 5) + "-" + cepString.substring(5, cepString.length());
		return cepStringRetorno;
	}
	
	public static String formatarCnpj(Long cnpj){		
		String cnpjString = String.valueOf(cnpj);
		String cnpjStringRetorno = cnpjString.substring(0, 2) + "." + cnpjString.substring(2, 5) + "." + cnpjString.substring(5,8) + "/" + cnpjString.substring(8,12) + "-" + cnpjString.substring(12,cnpjString.length());
		return cnpjStringRetorno;
	}
	
	public static String formatarCpf(Long cpf){
		String cpfString = cpf != null ? String.valueOf(cpf) : "0";
		cpfString = AplicacaoUtil.completaZerosAEsquerta(cpfString, 11);
		String cpfStringRetorno = cpfString.substring(0, 3) + "." + cpfString.substring(3, 6) + "." + cpfString.substring(6,9) + "-" + cpfString.substring(9,cpfString.length());	
		return cpfStringRetorno;
	}
	
	public static String completaZerosAEsquerta(String numero, Integer tamanho){
		Integer tamanhoMenosTamanhoNumero = tamanho - numero.length();
		String zeros = "";
		for (int i = 0; i < tamanhoMenosTamanhoNumero; i++) {
			zeros +="0";
		}
		return zeros+numero;
	}
	
	public static Locale getLocale(){
		FacesContext context = FacesContext.getCurrentInstance();
		return context.getViewRoot().getLocale();
	}
	

}
