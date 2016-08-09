package br.com.condominiosvirtuais.entity;


/**
 * Entidade quer representa o endere�o de e-mail de um usu�rio.
 * @author Maikel Joel de Souza
 * @since 21/02/2013
 */
public class EmailUsuario {
	
	private Integer id;
	
	private String email;
	
	// Utilizado para identificar na lista de email, qual � o e-mail principal que ser� o login do usu�rio.
	private Boolean principal;
	
	private Integer idUsuario;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Boolean getPrincipal() {
		return principal;
	}

	public void setPrincipal(Boolean principal) {
		this.principal = principal;
	}	

	public Integer getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Integer idUsuario) {
		this.idUsuario = idUsuario;
	}
}
