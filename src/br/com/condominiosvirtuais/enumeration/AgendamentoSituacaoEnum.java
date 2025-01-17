package br.com.condominiosvirtuais.enumeration;

public enum AgendamentoSituacaoEnum {
	
    REPROVADA("agendamentoMudanša.situacao.0"),
	
	APROVADA("agendamentoMudanša.situacao.1"),
	
	PENDENTE("agendamentoMudanša.situacao.2");	
	
	private String situacao = null;	
	
	AgendamentoSituacaoEnum(String situacao){
		this.situacao = situacao;
	}

	public String getSituacao() {
		return situacao;
	}

}
