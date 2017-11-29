package br.com.condominiosvirtuais.enumeration;

public enum GrupoUsuarioPadraoEnum {
	
	NAO(Boolean.FALSE),
	
	SIM(Boolean.TRUE);				
	
	private Boolean padrao = null;	
	
	GrupoUsuarioPadraoEnum(Boolean padrao){
		this.padrao = padrao;
	}

	public Boolean getPadrao() {
		return padrao;
	}	

}
