package br.com.condominiosvirtuais.entity;


/**
 * Entidade que representa uma associação entre um grupo de usuário uma tela e um componente. Usado para verificar quais abras de uma tela um </br> 
 * determinado grupo de usuário tem acesso. 
 * @author Maikel Joel de Souza
 * @since 24/08/2017
 */
public class GrupoUsuarioTelaComponente {
	
	private Integer id;
	
	private Integer idGrupoUsuario;
	
	private Integer idTela;
	
	private Integer idComponete;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getIdGrupoUsuario() {
		return idGrupoUsuario;
	}

	public void setIdGrupoUsuario(Integer idGrupoUsuario) {
		this.idGrupoUsuario = idGrupoUsuario;
	}

	public Integer getIdTela() {
		return idTela;
	}

	public void setIdTela(Integer idTela) {
		this.idTela = idTela;
	}

	public Integer getIdComponete() {
		return idComponete;
	}

	public void setIdComponete(Integer idComponete) {
		this.idComponete = idComponete;
	}
	

}
