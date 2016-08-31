package br.com.condominiosvirtuais.enumeration;

public enum AgendamentoSituacaoEnum {
	
    REPROVADA("agendamentoMudança.situacao.0"),
	
	APROVADA("agendamentoMudança.situacao.1"),
	
	PENDENTE("agendamentoMudança.situacao.2");	
	
	private String situacao = null;	
	
	AgendamentoSituacaoEnum(String situacao){
		this.situacao = situacao;
	}

	public String getSituacao() {
		return situacao;
	}

}
