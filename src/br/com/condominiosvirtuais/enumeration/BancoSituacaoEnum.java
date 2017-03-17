package br.com.condominiosvirtuais.enumeration;

public enum BancoSituacaoEnum {
	
	ATIVO(Boolean.TRUE),
	
	INATIVO(Boolean.FALSE);	
	
	private Boolean situacao = null;	
	
	BancoSituacaoEnum(Boolean situacao){
		this.situacao = situacao;
	}

	public Boolean getSituacao() {
		return situacao;
	}

}

