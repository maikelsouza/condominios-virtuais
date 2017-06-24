package br.com.condominiosvirtuais.enumeration;

public enum ComponenteTipoEnum {
	
    BOTAO(0),
	
	LINK(1);	
	
	private Integer tipo = null;	
	
	ComponenteTipoEnum(Integer tipo){
		this.tipo = tipo;
	}

	public Integer getTipo() {
		return tipo;
	}

}
