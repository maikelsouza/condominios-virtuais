package br.com.condominiosvirtuais.enumeration;

public enum ReceitaDespesaMeioPagamentoEnum {
	
	DINHEIRO("receitaDespesa.meioPagamento.0"),
	
	BANCO("receitaDespesa.meioPagamento.1");
	
	private String meioPagamento = null;	
	
	ReceitaDespesaMeioPagamentoEnum(String meioPagamento){
		this.meioPagamento = meioPagamento;
	}

	public String getMeioPagamento() {
		return meioPagamento;
	}

}
