package br.com.condominiosvirtuais.enumeration;

public enum TipoTituloSituacaoEnum {
	
	INATIVO(Boolean.FALSE),
	
	ATIVO(Boolean.TRUE);				
	
	private Boolean situacao = null;	
	
	TipoTituloSituacaoEnum(Boolean situacao){
		this.situacao = situacao;
	}

	public Boolean getSituacao() {
		return situacao;
	}	

}
