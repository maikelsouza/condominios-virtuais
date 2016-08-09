package br.com.condominiosvirtuais.enumeration;

public enum AgendamentoEnum {
	
    REPROVADA("agendamentoMudança.situacao.0"),
	
	APROVADA("agendamentoMudança.situacao.1"),
	
	PENDENTE("agendamentoMudança.situacao.2");	
	
	private String situacao = null;	
	
	AgendamentoEnum(String situacao){
		this.situacao = situacao;
	}

	public String getSituacao() {
		return situacao;
	}

}
