package br.com.condominiosvirtuais.enumeration;

public enum AtributoSessaoEnum {
	
	CONDOMINIO("condominio"),
	
	AUTENTICADO("autenticado");
	
	
	private String atributo = null;	
	
	AtributoSessaoEnum(String atributo){
		this.atributo = atributo;
	}

	public String getAtributo() {
		return atributo;
	}	

}
