package br.com.condominiosvirtuais.entity;


/**
 * Entidade que representa uma associação entre um grupo de usuário uma tela e uma aba. Usado para verificar quais abras de uma tela um </br> 
 * determinado grupo de usuário tem acesso. 
 * @author Maikel Joel de Souza
 * @since 05/06/2017
 */
public class GrupoUsuarioTelaAba {
	
	private Integer id;
	
	private Integer idGrupoUsuario;
	
	private Integer idTela;
	
	private Integer idAba;

	public Integer getId() {
		return id;
	}	

	public Integer getIdGrupoUsuario() {
		return idGrupoUsuario;
	}
	
	public void setIdGrupoUsuario(Integer idGrupoUsuario) {
		this.idGrupoUsuario = idGrupoUsuario;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getIdTela() {
		return idTela;
	}

	public void setIdTela(Integer idTela) {
		this.idTela = idTela;
	}

	public Integer getIdAba() {
		return idAba;
	}

	public void setIdAba(Integer idAba) {
		this.idAba = idAba;
	}	

}
