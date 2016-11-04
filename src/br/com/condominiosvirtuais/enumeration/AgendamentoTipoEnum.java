package br.com.condominiosvirtuais.enumeration;

public enum AgendamentoTipoEnum {
	
	ENTRADA("agendamentoMudanša.tipo.0"),
	
	SAIDA("agendamentoMudanša.tipo.1");
	
	
	private String tipo = null;	
	
	AgendamentoTipoEnum(String tipo){
		this.tipo = tipo;
	}

	public String getTipo() {
		return tipo;
	}

}

