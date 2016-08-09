package br.com.condominiosvirtuais.entity;

/**
 * Entidade que representa uma associação entre o voto da enquete e a unidade que votou. <br/>
 * @author Maikel Joel de Souza
 * @since 22/12/2013
 */
public class VotoEnquete {
	
	private Integer idEnquete;
	
	private Integer idUnidade;

	public Integer getIdEnquete() {
		return idEnquete;
	}

	public void setIdEnquete(Integer idEnquete) {
		this.idEnquete = idEnquete;
	}

	public Integer getIdUnidade() {
		return idUnidade;
	}

	public void setIdUnidade(Integer idUnidade) {
		this.idUnidade = idUnidade;
	}

}
