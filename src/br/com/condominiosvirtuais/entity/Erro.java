package br.com.condominiosvirtuais.entity;

import java.io.Serializable;

public class Erro implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private Integer id;
	
	private String mensagemErro;
	
	private String nomeBloco;
	
	private Integer numeroUnidade;
	
	private String variavelMensagemErro;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getMensagemErro() {
		return mensagemErro;
	}

	public void setMensagemErro(String mensagem) {
		this.mensagemErro = mensagem;
	}

	public String getVariavelMensagemErro() {
		return variavelMensagemErro;
	}

	public void setVariavelMensagemErro(String variavelMensagemErro) {
		this.variavelMensagemErro = variavelMensagemErro;
	}	

	public String getNomeBloco() {
		return nomeBloco;
	}

	public void setNomeBloco(String nomeBloco) {
		this.nomeBloco = nomeBloco;
	}

	public Integer getNumeroUnidade() {
		return numeroUnidade;
	}

	public void setNumeroUnidade(Integer numeroUnidade) {
		this.numeroUnidade = numeroUnidade;
	}
	
	
	
	

}