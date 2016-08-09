package br.com.condominiosvirtuais.entity;

/**
 * Entidade que representa qual(ais) condomínio(s) esse usuário está associado. É possível que um usuário esteja associado a mais de um <br>
 * condomínio. Exemplo: Síndico Profissional. E também o caso do administrado da aplicação, pois para cada novo condomínio esse estará associado.  <br>
 * Esse controle se faz necessário para poder exibir o(s) condomínio(s) que o usuáiro logodo pode ver.
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
