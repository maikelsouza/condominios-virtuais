package br.com.condominiosvirtuais.enumeration;

public enum VeiculoTipoEnum {
	
	CARRO(1),
	
	MOTO(2);		
		
	private Integer tipo = null;	
	
	VeiculoTipoEnum(Integer tipo){
		this.tipo = tipo;	
	}	

	public Integer getTipo() {
		return tipo;
	}	
}