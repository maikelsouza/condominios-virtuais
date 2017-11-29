package br.com.condominiosvirtuais.enumeration;

public enum GrupoUsuarioSituacaoEnum {
	
	INATIVO(Boolean.FALSE),
	
	ATIVO(Boolean.TRUE);				
	
	private Boolean situacao = null;	
	
	GrupoUsuarioSituacaoEnum(Boolean situacao){
		this.situacao = situacao;
	}

	public Boolean getSituacao() {
		return situacao;
	}	

}
