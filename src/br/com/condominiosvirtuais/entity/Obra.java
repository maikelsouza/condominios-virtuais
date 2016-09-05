package br.com.condominiosvirtuais.entity;

import java.util.Date;

import br.com.condominiosvirtuais.util.AplicacaoUtil;

public class Obra {
	
	private Integer id;
	
	private String nome;
	
	private Integer tipo;
	
	private Integer situacao;
	
	private Date dataInicio;
	
	private Date dataFim;
	
	private String descricao;
	
	private ResponsavelObra responsavelObra;
	
	private Integer idCondominio;
	
	public Obra(){
		this.responsavelObra = new ResponsavelObra();
	}

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

	public Integer getTipo() {
		return tipo;
	}

	public void setTipo(Integer tipo) {
		this.tipo = tipo;
	}

	public Integer getSituacao() {
		return situacao;
	}

	public void setSituacao(Integer situacao) {
		this.situacao = situacao;
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

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public ResponsavelObra getResponsavelObra() {
		return responsavelObra;
	}

	public void setResponsavelObra(ResponsavelObra responsavelObra) {
		this.responsavelObra = responsavelObra;
	}

	public Integer getIdCondominio() {
		return idCondominio;
	}

	public void setIdCondominio(Integer idCondominio) {
		this.idCondominio = idCondominio;
	}
	
	public String getTipoI18n(){
		return AplicacaoUtil.i18n("obra.tipo."+this.tipo);
	}
	
	public String getSituacaoI18n(){
		return AplicacaoUtil.i18n("obra.situacao."+this.situacao);
	}

}
