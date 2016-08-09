package br.com.condominiosvirtuais.enumeration;

public enum DespesasCalculaRateioPadraoEnum {
	
	SIM(Boolean.TRUE),
	
	NAO(Boolean.FALSE);	
		
	private Boolean rateioPadrao = null;	
	
	DespesasCalculaRateioPadraoEnum(Boolean rateioPadrao){
		this.rateioPadrao = rateioPadrao;
	}

	public Boolean getRateioPadrao() {
		return rateioPadrao;
	}	

}
