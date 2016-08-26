package br.com.condominiosvirtuais.vo;

import java.util.Date;

import br.com.condominiosvirtuais.entity.PrestadorServico;

public class VisitaVO {
	
	private Integer id;
	
	private Date dataInicio;
	
	private Date dataFim;	
	
	private Boolean prestarServico;	
	
	private String nomeCondominio;
	
	private String nomeBloco;
	
	private Integer numeroUnidade;
	
	private String nomeCondomino;
	
	private PrestadorServico prestadorServico;

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

	public String getNomeCondominio() {
		return nomeCondominio;
	}

	public void setNomeCondominio(String nomeCondominio) {
		this.nomeCondominio = nomeCondominio;
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

	public String getNomeCondomino() {
		return nomeCondomino;
	}

	public void setNomeCondomino(String nomeCondomino) {
		this.nomeCondomino = nomeCondomino;
	}

	public PrestadorServico getPrestadorServico() {
		return prestadorServico;
	}

	public void setPrestadorServico(PrestadorServico prestadorServico) {
		this.prestadorServico = prestadorServico;
	}

}
