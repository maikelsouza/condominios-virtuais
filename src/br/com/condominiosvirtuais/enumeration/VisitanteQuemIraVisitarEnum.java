package br.com.condominiosvirtuais.enumeration;

public enum VisitanteQuemIraVisitarEnum {
	
    MORADOR(1),
	
	CONDOMINIO(2);	
	
	
	private Integer quemIraVisitar = null;	
	
	VisitanteQuemIraVisitarEnum(Integer quemIraVisitar){
		this.quemIraVisitar = quemIraVisitar;
	}

	public Integer getQuemIraVisitar() {
		return quemIraVisitar;
	}	

}
