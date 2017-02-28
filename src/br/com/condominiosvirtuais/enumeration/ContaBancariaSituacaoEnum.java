package br.com.condominiosvirtuais.enumeration;

public enum ContaBancariaSituacaoEnum {
	
	ATIVA(1),
	
	INATIVA(0);	
	
	private Integer situacao = null;	
	
	ContaBancariaSituacaoEnum(Integer situacao){
		this.situacao = situacao;
	}

	public Integer getSituacao() {
		return situacao;
	}

}

