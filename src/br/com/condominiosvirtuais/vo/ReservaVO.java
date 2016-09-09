package br.com.condominiosvirtuais.vo;

import java.util.Date;

public class ReservaVO {
	
	private Integer id;
	
	private Integer idAmbiente;
	
	private String ambiente;
	
	private String condominio;
	
	private String bloco;
	
	private Integer unidade;
	
	private Integer idCondomino;
	
	private String condomino;
	
	private Date data;
	
	private String situacao;
	
	private String motivoReprovacao;
	
	private String motivoSuspensao;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	

	public Integer getIdAmbiente() {
		return idAmbiente;
	}

	public void setIdAmbiente(Integer idAmbiente) {
		this.idAmbiente = idAmbiente;
	}

	public String getAmbiente() {
		return ambiente;
	}

	public void setAmbiente(String ambiente) {
		this.ambiente = ambiente;
	}

	public String getCondominio() {
		return condominio;
	}

	public void setCondominio(String condominio) {
		this.condominio = condominio;
	}

	public String getBloco() {
		return bloco;
	}

	public void setBloco(String bloco) {
		this.bloco = bloco;
	}

	public Integer getUnidade() {
		return unidade;
	}

	public void setUnidade(Integer unidade) {
		this.unidade = unidade;
	}	

	public Integer getIdCondomino() {
		return idCondomino;
	}

	public void setIdCondomino(Integer idCondomino) {
		this.idCondomino = idCondomino;
	}

	public String getCondomino() {
		return condomino;
	}

	public void setCondomino(String condomino) {
		this.condomino = condomino;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public String getSituacao() {
		return situacao;
	}

	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}

	public String getMotivoReprovacao() {
		return motivoReprovacao;
	}

	public void setMotivoReprovacao(String motivoReprovacao) {
		this.motivoReprovacao = motivoReprovacao;
	}

	public String getMotivoSuspensao() {
		return motivoSuspensao;
	}

	public void setMotivoSuspensao(String motivoSuspensao) {
		this.motivoSuspensao = motivoSuspensao;
	}
	

}
