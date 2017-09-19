package br.com.condominiosvirtuais.enumeration;

public enum GrupoUsuarioTipoUsuarioEnum {
	
	CONDOMINO(1),
	
	FUNCIONARIO(2);				
	
	private Integer tipoUsuario = null;	
	
	GrupoUsuarioTipoUsuarioEnum(Integer tipoUsuario){
		this.tipoUsuario = tipoUsuario;
	}

	public Integer getTipoUsuario() {
		return tipoUsuario;
	}	

}
