package br.com.condominiosvirtuais.enumeration;

public enum MeioPagamentoNomeEnum {
	
    DINHEIRO("meioPagamento.dinheiro"),
	
	SICOOB("meioPagamento.bancoSicoob");	
		
	
	private String nome = null;	
	
	MeioPagamentoNomeEnum(String nome){
		this.nome = nome;
	}

	public String getNome() {
		return nome;
	}

}
