package br.com.condominiosvirtuais.enumeration;

public enum ComponenteTipoEnum {
	
    BOTAO("componente.tipo.0"),
	
	LINK("componente.tipo.1");	
	
	private String tipo = null;	
	
	ComponenteTipoEnum(String tipo){
		this.tipo = tipo;
	}

	public String getTipo() {
		return tipo;
	}

}
