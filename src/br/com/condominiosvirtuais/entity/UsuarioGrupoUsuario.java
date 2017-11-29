package br.com.condominiosvirtuais.entity;

public class UsuarioGrupoUsuario {
	
	private Integer id;
	
	private Integer idUsuario;
	
	private Integer idGrupoUsuario;

	
	public UsuarioGrupoUsuario(Integer id, Integer idUsuario, Integer idGrupoUsuario){
		this.id = id;
		this.idUsuario = idUsuario;		
		this.idGrupoUsuario = idGrupoUsuario;
	}
	
	public UsuarioGrupoUsuario(){}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Integer idUsuario) {
		this.idUsuario = idUsuario;
	}

	public Integer getIdGrupoUsuario() {
		return idGrupoUsuario;
	}

	public void setIdGrupoUsuario(Integer idGrupoUsuario) {
		this.idGrupoUsuario = idGrupoUsuario;
	}
	
	

}
