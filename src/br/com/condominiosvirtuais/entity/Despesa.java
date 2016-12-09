package br.com.condominiosvirtuais.entity;

import java.util.Date;

/**
 * Entidade que representa uma receita de um condomínio.
 * @author Maikel Joel de Souza
 * @since 08/12/2016
 */
public class Despesa {
	
	private Integer id;
	
	private String nome;
	
	private Double valor;
	
	private Date mesAnoReferencia;
	
	private Integer idCondominio;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}	

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	public Date getMesAnoReferencia() {
		return mesAnoReferencia;
	}

	public void setMesAnoReferencia(Date mesAnoReferencia) {
		this.mesAnoReferencia = mesAnoReferencia;
	}

	public Integer getIdCondominio() {
		return idCondominio;
	}

	public void setIdCondominio(Integer idCondominio) {
		this.idCondominio = idCondominio;
	}
	

}
