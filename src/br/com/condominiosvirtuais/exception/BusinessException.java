package br.com.condominiosvirtuais.exception;

public class BusinessException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public BusinessException(String mensagem, Throwable e){
		super(mensagem,e);
	}
	
	public BusinessException(String mensagem){
		super(mensagem);
	}

}
