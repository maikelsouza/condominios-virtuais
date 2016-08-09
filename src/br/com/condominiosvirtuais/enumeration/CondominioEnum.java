package br.com.condominiosvirtuais.enumeration;

public enum CondominioEnum {
	
	INATIVO(0),
	
	ATIVO(1);				
	
	private Integer situacao = null;	
	
	CondominioEnum(Integer situacao){
		this.situacao = situacao;
	}

	public Integer getSituacao() {
		return situacao;
	}	

}
