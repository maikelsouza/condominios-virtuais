 package br.com.condominiosvirtuais.entity;

import java.util.Date;

public class Visita {
	
	private Integer id;
	
	private Date dataInicio;
	
	private Date dataFim;	
	
	private Boolean prestarServico;	
	
	private PrestadorServico prestadorServico;
	
	private Integer idCondominio;
	
	private Integer idBloco;
	
	private Integer idUnidade;
	
	private Integer idCondomino;
	
	private Integer idVisitante;	
	
	public Visita(){
		this.prestadorServico = new PrestadorServico();
	}	

	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}

	public Date getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}

	public Date getDataFim() {
		return dataFim;
	}

	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}	

	public Boolean getPrestarServico() {
		return prestarServico;
	}

	public void setPrestarServico(Boolean prestarServico) {
		this.prestarServico = prestarServico;
	}

	public PrestadorServico getPrestadorServico() {
		return prestadorServico;
	}

	public void setPrestadorServico(PrestadorServico prestadorServico) {
		this.prestadorServico = prestadorServico;
	}

	public Integer getIdVisitante() {
		return idVisitante;
	}

	public void setIdVisitante(Integer idVisitante) {
		this.idVisitante = idVisitante;
	}

	public Integer getIdCondominio() {
		return idCondominio;
	}

	public void setIdCondominio(Integer idCondominio) {
		this.idCondominio = idCondominio;
	}

	public Integer getIdBloco() {
		return idBloco;
	}

	public void setIdBloco(Integer idBloco) {
		this.idBloco = idBloco;
	}

	public Integer getIdUnidade() {
		return idUnidade;
	}

	public void setIdUnidade(Integer idUnidade) {
		this.idUnidade = idUnidade;
	}

	public Integer getIdCondomino() {
		return idCondomino;
	}

	public void setIdCondomino(Integer idCondomino) {
		this.idCondomino = idCondomino;
	}	

}
