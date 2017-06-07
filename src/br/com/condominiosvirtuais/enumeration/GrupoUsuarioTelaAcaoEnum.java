package br.com.condominiosvirtuais.enumeration;

public enum GrupoUsuarioTelaAcaoEnum {
	
	VISUALIZAR("");				
	
	private String acao = null;	
	
	GrupoUsuarioTelaAcaoEnum(String acao){
		this.acao = acao;
	}

	public String getAcao() {
		return acao;
	}	

}
