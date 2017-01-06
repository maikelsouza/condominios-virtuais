package br.com.condominiosvirtuais.enumeration;

public enum EscritorioContabilidadeSituacaoEnum {
	
	INATIVO(0),
	
	ATIVO(1);				
	
	private Integer situacao = null;	
	
	EscritorioContabilidadeSituacaoEnum(Integer situacao){
		this.situacao = situacao;
	}

	public Integer getSituacao() {
		return situacao;
	}	

}
