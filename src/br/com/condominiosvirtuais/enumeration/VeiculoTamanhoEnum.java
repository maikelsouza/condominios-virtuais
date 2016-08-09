package br.com.condominiosvirtuais.enumeration;

public enum VeiculoTamanhoEnum{
	
	PEQUENO(0),
	
	MEDIO(1),	
		
	GRANDE(2);
	
	private Integer tamanho = null;	
	
	VeiculoTamanhoEnum(Integer tamanho){
		this.tamanho = tamanho;	
	}	
	
	public Integer getTamanho() {
		return tamanho;
	}
	
}