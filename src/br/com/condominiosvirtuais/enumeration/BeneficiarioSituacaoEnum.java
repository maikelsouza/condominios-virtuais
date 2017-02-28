package br.com.condominiosvirtuais.enumeration;

public enum BeneficiarioSituacaoEnum {
	
	ATIVO(1),
	
	INATIVO(0);	
	
	private Integer situacao = null;	
	
	BeneficiarioSituacaoEnum(Integer situacao){
		this.situacao = situacao;
	}

	public Integer getSituacao() {
		return situacao;
	}

}

