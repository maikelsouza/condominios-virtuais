package br.com.condominiosvirtuais.enumeration;

public enum TipoConjuntoBlocoEnum {
	
	AMBIENTE(1),
	
	FUNCIONARIO(2);		
	
	private Integer conjuntoBloco = null;
	
	TipoConjuntoBlocoEnum(Integer conjuntoBloco){
		this.conjuntoBloco = conjuntoBloco;
	}

	public Integer getConjuntoBloco() {
		return conjuntoBloco;
	}

	public void setConjuntoBloco(Integer conjuntoBloco) {
		this.conjuntoBloco = conjuntoBloco;
	}
	
	

}
