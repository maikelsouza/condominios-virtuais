package br.com.condominiosvirtuais.enumeration;

public enum BeneficiarioTipoDocumentoEnum {
	
	CPF(1),
	
	CNPJ(0);	
	
	private Integer tipoDocumento = null;	
	
	BeneficiarioTipoDocumentoEnum(Integer tipoDocumento){
		this.tipoDocumento = tipoDocumento;
	}

	public Integer getSituacao() {
		return tipoDocumento;
	}

}

