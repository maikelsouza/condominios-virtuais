package br.com.condominiosvirtuais.entity;

import java.util.Date;

/**
 * Entidade que representa um anúncio nos classificados do condomínio.
 * @author Maikel Joel de Souza
 * @since 12/02/2014
 */
public class Classificados {
	
	private Integer id;
	
	private String titulo;
	
	private String descricao;
	
	private Long telefone1;
	
	private Long telefone2;
	
	private Double valor;
	
	private Date dataExpira;
	
	private Arquivo imagem;
	
	private Integer idUsuario;
	
	private Integer idCondominio;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Long getTelefone1() {
		return telefone1;
	}

	public void setTelefone1(Long telefone1) {
		this.telefone1 = telefone1;
	}

	public Long getTelefone2() {
		return telefone2;
	}

	public void setTelefone2(Long telefone2) {
		this.telefone2 = telefone2;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	public Date getDataExpira() {
		return dataExpira;
	}

	public void setDataExpira(Date dataExpira) {
		this.dataExpira = dataExpira;
	}

	public Arquivo getImagem() {
		return imagem;
	}

	public void setImagem(Arquivo imagem) {
		this.imagem = imagem;
	}
	
	public Integer getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Integer idUsuario) {
		this.idUsuario = idUsuario;
	}

	public Integer getIdCondominio() {
		return idCondominio;
	}

	public void setIdCondominio(Integer idCondominio) {
		this.idCondominio = idCondominio;
	}

}
