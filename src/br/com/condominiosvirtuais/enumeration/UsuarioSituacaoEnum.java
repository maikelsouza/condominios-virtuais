package br.com.condominiosvirtuais.enumeration;

public enum UsuarioSituacaoEnum {
	
	INATIVO(0),
	
	ATIVO(1);				
	
	private Integer situacao = null;	
	
	UsuarioSituacaoEnum(Integer situacao){
		this.situacao = situacao;
	}

	public Integer getSituacao() {
		return situacao;
	}	

}
