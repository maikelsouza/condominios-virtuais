package br.com.condominiosvirtuais.enumeration;

public enum ObraSituacaoEnum {
	
	EM_ANDAMENTO(0),
	
	CONCLUIDA(1);	
		
	
	private Integer situacao = null;	
	
	ObraSituacaoEnum(Integer situacao){
		this.situacao = situacao;
	}

	public Integer getSituacao() {
		return situacao;
	}

}
