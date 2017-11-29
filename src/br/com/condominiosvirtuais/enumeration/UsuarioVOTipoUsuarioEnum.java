package br.com.condominiosvirtuais.enumeration;

public enum UsuarioVOTipoUsuarioEnum {
	
	CONDOMINO(1),
	
	FUNCIONARIO(2);				
	
	private Integer tipoUsuario = null;	
	
	UsuarioVOTipoUsuarioEnum(Integer tipoUsuario){
		this.tipoUsuario = tipoUsuario;
	}

	public Integer getTipoUsuario() {
		return tipoUsuario;
	}	

}
