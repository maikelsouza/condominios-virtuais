package br.com.condominiosvirtuais.entity;

import java.util.Date;

import br.com.condominiosvirtuais.util.AplicacaoUtil;

/**
 * Entidade que representa uma reserva de um ambiente. Exemplo: Reserva de um salão de festa.
 * @author Maikel Joel de Souza
 * @since 23/02/2013
 */
public class Reserva {
	
	private Integer id;
	
	private Condomino condomino;
	
	private Ambiente ambiente;
	
	private Date data;

	private String situacao;	
	
	private String motivoReprovacao;
	
	
	public Reserva() {
		this.ambiente = new Ambiente();
		this.condomino = new Condomino();
		this.motivoReprovacao = "";
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Ambiente getAmbiente() {
		return ambiente;
	}

	public void setAmbiente(Ambiente ambiente) {
		this.ambiente = ambiente;
	}

	public Condomino getCondomino() {
		return condomino;
	}

	public void setCondomino(Condomino condomino) {
		this.condomino = condomino;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public String getSituacao() {
		return this.situacao;
	}	

	public String getMotivoReprovacao() {
		return motivoReprovacao;
	}

	public void setMotivoReprovacao(String motivoReprovacao) {
		this.motivoReprovacao = motivoReprovacao;
	}

	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}
	
	/* Existe três possibilidade de situação: Pedente, Aprovada ou Reprovada.
	 * Para que seja possíve exibir essas três possibilidades em um único campo e em momentos diferentes, foi
	 * gravado a string no banco. 
	 */
	public String getSituacaoI18n() {
		return AplicacaoUtil.i18n(this.situacao);
	}

}
