package br.com.condominiosvirtuais.entity;

/**
 * Entidade que representa qual(ais) condom�nio(s) esse usu�rio est� associado. � poss�vel que um usu�rio esteja associado a mais de um <br>
 * condom�nio. Exemplo: S�ndico Profissional. E tamb�m o caso do administrado da aplica��o, pois para cada novo condom�nio esse estar� associado.  <br>
 * Esse controle se faz necess�rio para poder exibir o(s) condom�nio(s) que o usu�iro logodo pode ver.
 * @author Maikel Joel de Souza
 * @since 20/02/2013
 */
public class UsuarioCondominio {
	
	private Integer id;
	
	private Integer idUsuario;
	
	private Integer idCondominio;

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

	public Integer getIdCondominio() {
		return idCondominio;
	}

	public void setIdCondominio(Integer idCondominio) {
		this.idCondominio = idCondominio;
	}	

}
