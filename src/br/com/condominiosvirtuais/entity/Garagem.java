package br.com.condominiosvirtuais.entity;

/**
 * Entidade que representa uma garagem de um condomínio associado a uma unidade.
 * @author Maikel Joel de Souza
 * @since 12/02/2014
 */
public class Garagem {
	
	private Integer id;
	
	private Integer tamanho;
	
	// O campo número é do tipo string para poder aceitar algo do tipo: B6, ou seja, vaga de número 6 do bloco B
	private String numero;
	
	private Integer idUnidade;	

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getTamanho() {
		return tamanho;
	}

	public void setTamanho(Integer tamanho) {
		this.tamanho = tamanho;
	}

	public Integer getIdUnidade() {
		return idUnidade;
	}

	public void setIdUnidade(Integer idUnidade) {
		this.idUnidade = idUnidade;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}	
}