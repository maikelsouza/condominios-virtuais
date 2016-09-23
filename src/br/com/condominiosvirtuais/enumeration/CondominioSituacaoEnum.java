package br.com.condominiosvirtuais.enumeration;

public enum CondominioSituacaoEnum {
	
	INATIVO(0),
	
	ATIVO(1);				
	
	private Integer situacao = null;	
	
	CondominioSituacaoEnum(Integer situacao){
		this.situacao = situacao;
	}

	public Integer getSituacao() {
		return situacao;
	}	

}
