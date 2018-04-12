package br.com.condominiosvirtuais.entity;

/**
 * Entidade que representa um tipo de título que tem uma Conta Bancaria 
 * {@link ContaBancaria} e {@link ConfiguracaoPadraoContaBancaria}
 * 
 * @author Maikel Joel de Souza
 * @since 11/04/2018
 */
public class TipoTitulo {
	
	private Integer id;
	
	private String sigla;
	
	private String nome;
	
	private Boolean situacao;	
	
	// Atributo criado para cadatrar os tipos de título de acordo com o pais.
	private String locale;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getSigla() {
		return sigla;
	}

	public void setSigla(String sigla) {
		this.sigla = sigla;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Boolean getSituacao() {
		return situacao;
	}

	public void setSituacao(Boolean situacao) {
		this.situacao = situacao;
	}

	public String getLocale() {
		return locale;
	}

	public void setLocale(String locale) {
		this.locale = locale;
	}

}
