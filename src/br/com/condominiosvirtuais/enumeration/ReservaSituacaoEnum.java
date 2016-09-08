package br.com.condominiosvirtuais.enumeration;

public enum ReservaSituacaoEnum{
	
	REPROVADA("reserva.situacao.0"),
	
	APROVADA("reserva.situacao.1"),
	
	PENDENTE("reserva.situacao.2"),	
	
	SUSPENSA("reserva.situacao.3");	
	
	private String situacao = null;	
	
	ReservaSituacaoEnum(String situacao){
		this.situacao = situacao;
	}

	public String getSituacao() {
		return situacao;
	}
	

	
}
