package br.com.condominiosvirtuais.enumeration;

public enum GrupoUsuarioAtivoEnum {
	
	INATIVO(Boolean.FALSE),
	
	ATIVO(Boolean.TRUE);				
	
	private Boolean ativo = null;	
	
	GrupoUsuarioAtivoEnum(Boolean ativo){
		this.ativo = ativo;
	}

	public Boolean getAtivo() {
		return ativo;
	}	

}
