package br.com.condominiosvirtuais.enumeration;

public enum VisitanteTipoDocumentoEnum {
	
    CPF(1),
	
	RG(2);		
	
	private Integer tipoDocumento = null;	
	
	VisitanteTipoDocumentoEnum(Integer tipoDocumento){
		this.tipoDocumento = tipoDocumento;
	}

	public Integer getTipoDocumento() {
		return tipoDocumento;
	}	


}
