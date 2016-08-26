package br.com.condominiosvirtuais.enumeration;

public enum VisitantePrestarServicoEnum {
	
 	NAO(Boolean.FALSE),
	
	SIM(Boolean.TRUE);		
	
	private Boolean prestarServico = null;	
	
	VisitantePrestarServicoEnum(Boolean prestarServico){
		this.prestarServico = prestarServico;
	}

	public Boolean getPrestarServico() {
		return prestarServico;
	}	

}
