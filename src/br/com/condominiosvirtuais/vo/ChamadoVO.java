package br.com.condominiosvirtuais.vo;

import java.util.Date;

public class ChamadoVO {
	
	private Integer id;
	
	private String nomeBloco;
	
	private String numeroUnidade;
	
	private String nome;
	
	private String descricao;
	
	private String outros;
	
	private String status;
	
	private String nomeUsuario;
	
	private Integer idUsuario;
	
	private String comentario;
	
	private String tipoChamado;
	
	private Date dataAbertura;
	
	private Date dataFechamento;

	public String getNomeBloco() {
		return nomeBloco;
	}

	public void setNomeBloco(String nomeBloco) {
		this.nomeBloco = nomeBloco;
	}

	public String getNumeroUnidade() {
		return numeroUnidade;
	}

	public void setNumeroUnidade(String numeroUnidade) {
		this.numeroUnidade = numeroUnidade;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getOutros() {
		return outros;
	}

	public void setOutros(String outros) {
		this.outros = outros;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getNomeUsuario() {
		return nomeUsuario;
	}

	public void setNomeUsuario(String nomeUsuario) {
		this.nomeUsuario = nomeUsuario;
	}

	public Date getDataAbertura() {
		return dataAbertura;
	}

	public void setDataAbertura(Date dataAbertura) {
		this.dataAbertura = dataAbertura;
	}

	public Date getDataFechamento() {
		return dataFechamento;
	}

	public void setDataFechamento(Date dataFechamento) {
		this.dataFechamento = dataFechamento;
	}

	public String getTipoChamado() {
		return tipoChamado;
	}

	public void setTipoChamado(String tipoChamado) {
		this.tipoChamado = tipoChamado;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	public Integer getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Integer idUsuario) {
		this.idUsuario = idUsuario;
	}		

}
