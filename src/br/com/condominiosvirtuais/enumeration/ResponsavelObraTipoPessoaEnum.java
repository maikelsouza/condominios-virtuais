package br.com.condominiosvirtuais.enumeration;

public enum ResponsavelObraTipoPessoaEnum {
	
	PESSOA_FISICA(0),
	
	PESSOA_JURIDICA(1);	
		
	
	private Integer tipoPessoa = null;	
	
	ResponsavelObraTipoPessoaEnum(Integer tipoPessoa){
		this.tipoPessoa = tipoPessoa;
	}

	public Integer getTipoPessoa() {
		return tipoPessoa;
	}

}
