package br.com.condominiosvirtuais.entity;

import java.util.Date;

public class Chamado {
	
	private Integer id;
	
	private String nome;
	
	private String descricao;
	
	private String comentario;
	
	private String outros;
	
	private Integer status;
	
	private Date dataAbertura;
	
	private Date dataFechamento;
	
	private TipoChamado tipoChamado;
	
	private Condominio condominio;
	
	private Usuario usuario;
	
	
	public Chamado(){
		this.tipoChamado = new TipoChamado();
		this.condominio = new Condominio();
		this.usuario = new Usuario();
		
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

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}	

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	public String getOutros() {
		return outros;
	}

	public void setOutros(String outros) {
		this.outros = outros;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public TipoChamado getTipoChamado() {
		return tipoChamado;
	}

	public void setTipoChamado(TipoChamado tipoChamado) {
		this.tipoChamado = tipoChamado;
	}

	public Condominio getCondominio() {
		return condominio;
	}

	public void setCondominio(Condominio condominio) {
		this.condominio = condominio;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
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
	
}
