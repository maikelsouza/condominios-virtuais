package br.com.condominiosvirtuais.enumeration;

public enum LogAcaoEnum {
	
	EXCLUIR_RESERVA("EXCLUIR_RESERVA");
	
	private String acao = null;	
	
	LogAcaoEnum(String acao){
		this.acao = acao;
	}

	public String getAcao() {
		return acao;
	}	
	 

}
