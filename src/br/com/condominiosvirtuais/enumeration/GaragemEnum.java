package br.com.condominiosvirtuais.enumeration;

public enum GaragemEnum {
	
	PEQUENO(0),
	
	MEDIO(1),
	
	GRANDE(2);
	
	private Integer tamanho = null;	
	
	GaragemEnum(Integer tamanho){
		this.tamanho = tamanho;
	}

	public Integer getTamanho() {
		return tamanho;
	}	

}
