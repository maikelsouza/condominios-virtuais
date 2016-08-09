package br.com.condominiosvirtuais.enumeration;

public enum ChamadoStatusEnum {
	
	ABERTO(1),	
	
	FECHADO(0),
	
	EM_ANDAMENTO(2);
	
	private Integer status = null;	
	

	ChamadoStatusEnum(Integer status){
		this.status = status;
	}
	
	public Integer getStatus() {
		return status;
	}
	
	
}
