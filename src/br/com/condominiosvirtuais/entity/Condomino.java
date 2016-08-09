package br.com.condominiosvirtuais.entity;


/**
 * Entidade que representa um condômino, independente do papel que possa assumir. (síndico, sub-Síndico e etc)
 * @author Maikel Joel de Souza
 * @since 20/02/2013
 */
public class Condomino extends Usuario {
	
	private Boolean proprietario;
	
	private Long telefoneResidencial;
	
	private Long telefoneCelular;	
	
	private Long telefoneComercial = null;
	
	private Integer idUnidade;

		
	public Boolean getProprietario() {
		return proprietario;
	}

	public void setProprietario(Boolean proprietario) {
		this.proprietario = proprietario;
	}	
	
	public Long getTelefoneResidencial() {
		return telefoneResidencial;
	}

	public void setTelefoneResidencial(Long telefoneResidencial) {
		this.telefoneResidencial = telefoneResidencial;
	}

	public Long getTelefoneCelular() {
		return telefoneCelular;
	}

	public void setTelefoneCelular(Long telefoneCelular) {
		this.telefoneCelular = telefoneCelular;
	}

	public Long getTelefoneComercial() {
		return telefoneComercial;
	}

	public void setTelefoneComercial(Long telefoneComercial) {
		this.telefoneComercial = telefoneComercial;
	}

	public Integer getIdUnidade() {
		return idUnidade;
	}

	public void setIdUnidade(Integer idUnidade) {
		this.idUnidade = idUnidade;
	}
	
	
}
