package br.com.condominiosvirtuais.enumeration;

public enum UsuarioSexoEnum {
	
	MASCULINO(0),
	
	FEMININO(1);
	
	
	private Integer sexo = null;	
	
	UsuarioSexoEnum(Integer sexo){
		this.sexo = sexo;
	}

	public Integer getSexo() {
		return sexo;
	}	

}
