package br.com.condominiosvirtuais.enumeration;

public enum GrupoUsuarioTipoUsuarioEnum {
	
	CONDOMINO(1),
	
	FUNCIONARIO(2),
	
	SINDICO(3),
	
	CONTADOR(4),
	
	SINDICO_PROFISSIONAL(5),
	
	ESCRITORIO_CONTABILIDADE(6);
	
	private Integer tipoUsuario = null;	
	
	GrupoUsuarioTipoUsuarioEnum(Integer tipoUsuario){
		this.tipoUsuario = tipoUsuario;
	}

	public Integer getTipoUsuario() {
		return tipoUsuario;
	}	

}
