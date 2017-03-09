package br.com.condominiosvirtuais.exception;

public class BusinessException extends Exception {

	private static final long serialVersionUID = 1L;
	
	private String variavelNaoI18n;
	
	public BusinessException(String mensagem, Throwable e){
		super(mensagem,e);
	}	
	
	public BusinessException(String mensagem, Throwable e, String variavelNaoI18n){
		super(mensagem,e);
		this.variavelNaoI18n = variavelNaoI18n;
	}	
	
	public BusinessException(String mensagem){
		super(mensagem);
	}

	public String getVariavelNaoI18n() {
		return variavelNaoI18n;
	}

	public void setVariavelNaoI18n(String variavelNaoI18n) {
		this.variavelNaoI18n = variavelNaoI18n;
	}
	
	

}
