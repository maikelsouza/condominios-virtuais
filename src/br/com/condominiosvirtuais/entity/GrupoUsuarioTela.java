package br.com.condominiosvirtuais.entity;


/**
 * Entidade que representa uma associação entre um grupo de usuário e uma tela. Usado para verificar quais telas um </br> 
 * determinado grupo de usuário tem acesso. 
 * @author Maikel Joel de Souza
 * @since 31/05/2017
 */
public class GrupoUsuarioTela {
	
	private Integer id;
	
	private Integer idGrupoUsuario;
	
	private Integer idTela;
	
	private String acao;

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

	public String getAcao() {
		return acao;
	}

	public void setAcao(String acao) {
		this.acao = acao;
	}

	public Integer getIdTela() {
		return idTela;
	}

	public void setIdTela(Integer idTela) {
		this.idTela = idTela;
	}
	
	

}
