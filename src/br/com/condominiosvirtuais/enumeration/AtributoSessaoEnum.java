package br.com.condominiosvirtuais.enumeration;

public enum AtributoSessaoEnum {
	
	CONDOMINIO("condominio"),
	
	ID_CONDOMINIO("idCondominio"),
	
	AUTENTICADO("autenticado"),
	
	GRUPO_USUARIO("grupoUsuario"),
	
	TELA("tela"),
	
	TELA_VO("telaVO"),
	
	CHECADO("checado");	
	
	
	private String atributo = null;	
	
	AtributoSessaoEnum(String atributo){
		this.atributo = atributo;
	}

	public String getAtributo() {
		return atributo;
	}	

}
