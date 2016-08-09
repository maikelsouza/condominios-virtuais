package br.com.condominiosvirtuais.enumeration;

public enum ReservaEnum{
	
	REPROVADA("reserva.situacao.0"),
	
	APROVADA("reserva.situacao.1"),
	
	PENDENTE("reserva.situacao.2");	
	
	private String situacao = null;	
	
	ReservaEnum(String situacao){
		this.situacao = situacao;
	}

	public String getSituacao() {
		return situacao;
	}
	

	
}
