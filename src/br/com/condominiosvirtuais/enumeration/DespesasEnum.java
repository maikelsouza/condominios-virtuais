package br.com.condominiosvirtuais.enumeration;

public enum DespesasEnum {
	
	ENERGIA(0),
	
	AGUA(1),
	
	FUNDO_DE_RESERVA(2),
	
	COTA_CAPITAL(3),
	
	FAXINA(4),
	
	GAS(5),
	
	RATEIO_CONDOMINIO(6),
	
	RATEIO_SINDICO(7);
	
	private Integer despesa = null;	
	
	DespesasEnum(Integer despesa){
		this.despesa = despesa;
	}

	public Integer getDespesa() {
		return despesa;
	}	

}
