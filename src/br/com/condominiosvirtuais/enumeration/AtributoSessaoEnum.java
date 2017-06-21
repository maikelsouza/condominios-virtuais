package br.com.condominiosvirtuais.enumeration;

public enum AtributoSessaoEnum {
	
	CONDOMINIO("condominio"),
	
	AUTENTICADO("autenticado"),
	
	GRUPO_USUARIO("grupoUsuario"),
	
	TELA("tela");
	
	
	private String atributo = null;	
	
	AtributoSessaoEnum(String atributo){
		this.atributo = atributo;
	}

	public String getAtributo() {
		return atributo;
	}	

}
