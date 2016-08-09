package br.com.condominiosvirtuais.enumeration;

public enum UsuarioEnum {
	
	INATIVO(0),
	
	ATIVO(1);				
	
	private Integer situacao = null;	
	
	UsuarioEnum(Integer situacao){
		this.situacao = situacao;
	}

	public Integer getSituacao() {
		return situacao;
	}	

}
