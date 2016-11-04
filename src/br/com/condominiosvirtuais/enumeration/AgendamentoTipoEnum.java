package br.com.condominiosvirtuais.enumeration;

public enum AgendamentoTipoEnum {
	
	ENTRADA("agendamentoMudan�a.tipo.0"),
	
	SAIDA("agendamentoMudan�a.tipo.1");
	
	
	private String tipo = null;	
	
	AgendamentoTipoEnum(String tipo){
		this.tipo = tipo;
	}

	public String getTipo() {
		return tipo;
	}

}

