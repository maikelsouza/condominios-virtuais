package br.com.condominiosvirtuais.enumeration;

public enum CondominoPagadorEnum {
	
	SIM(1),
	
	NAO(0);	
	
	private Integer pagador = null;	
	
	CondominoPagadorEnum(Integer pagador){
		this.pagador = pagador;
	}

	public Integer getPagador() {
		return pagador;
	}

}

