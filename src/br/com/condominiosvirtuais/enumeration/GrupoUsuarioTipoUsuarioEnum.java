package br.com.condominiosvirtuais.enumeration;

public enum GrupoUsuarioTipoUsuarioEnum {
	
	ADMINISTRADOR(0),
	
	CONDOMINO(1),
	
	FUNCIONARIO(2),
	
	SINDICO(3),
	
	SINDICO_PROFISSIONAL(4),
	
	ESCRITORIO_CONTABILIDADE(5),
	
	ADMINISTRADORA(6);
	
	private Integer tipoUsuario = null;	
	
	GrupoUsuarioTipoUsuarioEnum(Integer tipoUsuario){
		this.tipoUsuario = tipoUsuario;
	}

	public Integer getTipoUsuario() {
		return tipoUsuario;
	}	

}
