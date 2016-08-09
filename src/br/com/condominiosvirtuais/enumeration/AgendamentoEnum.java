package br.com.condominiosvirtuais.enumeration;

public enum AgendamentoEnum {
	
    REPROVADA("agendamentoMudan�a.situacao.0"),
	
	APROVADA("agendamentoMudan�a.situacao.1"),
	
	PENDENTE("agendamentoMudan�a.situacao.2");	
	
	private String situacao = null;	
	
	AgendamentoEnum(String situacao){
		this.situacao = situacao;
	}

	public String getSituacao() {
		return situacao;
	}

}
