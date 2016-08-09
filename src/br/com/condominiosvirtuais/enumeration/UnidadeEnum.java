package br.com.condominiosvirtuais.enumeration;

public enum UnidadeEnum {
	
	APARTAMENTO(0),
	
	LOJA(1),
	
	AMBOS(2);
	
	private Integer tipo = null;	
	
	UnidadeEnum(Integer tipo){
		this.tipo = tipo;
	}

	public Integer getTipo() {
		return tipo;
	}	

}
