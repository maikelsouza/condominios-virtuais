package br.com.condominiosvirtuais.enumeration;

public enum AgendamentoTipoEnum {
	
	ENTRADA("agendamentoMudança.tipo.0"),
	
	SAIDA("agendamentoMudança.tipo.1");
	
	
	private String tipo = null;	
	
	AgendamentoTipoEnum(String tipo){
		this.tipo = tipo;
	}

	public String getTipo() {
		return tipo;
	}

}

