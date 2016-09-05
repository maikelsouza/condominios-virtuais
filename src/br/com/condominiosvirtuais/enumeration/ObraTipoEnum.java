package br.com.condominiosvirtuais.enumeration;

public enum ObraTipoEnum {
	
	URGENTE(0),
	
	PLANEJADA(1);	
		
	
	private Integer tipo = null;	
	
	ObraTipoEnum(Integer tipo){
		this.tipo = tipo;
	}

	public Integer getTipo() {
		return tipo;
	}

}
