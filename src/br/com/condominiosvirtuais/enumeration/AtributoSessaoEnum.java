package br.com.condominiosvirtuais.enumeration;

public enum AtributoSessaoEnum {
	
	CONDOMINIO("condominio"),
	
	AUTENTICADO("autenticado"),
	
	GRUPO_USUARIO("grupoUsuario"),
	
	TELA("tela"),
	
	TELA_VO("telaVO");
	
	
	private String atributo = null;	
	
	AtributoSessaoEnum(String atributo){
		this.atributo = atributo;
	}

	public String getAtributo() {
		return atributo;
	}	

}
