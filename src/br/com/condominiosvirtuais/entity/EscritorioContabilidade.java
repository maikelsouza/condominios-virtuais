package br.com.condominiosvirtuais.entity;

import java.util.List;

public class EscritorioContabilidade {
	
	private Integer id;
	
	private String nome;
	
	private String site;
	
	private Integer situacao;
	
	private Long cnpj;
	
	private Long telefoneComercial;
	
	private Long telefoneCelular;
	
	private List<Contador> listaContador = null;
	
	private List<Condominio> listaCondominio = null;
	
	

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSite() {
		return site;
	}

	public void setSite(String site) {
		this.site = site;
	}

	public Integer getSituacao() {
		return situacao;
	}

	public void setSituacao(Integer situacao) {
		this.situacao = situacao;
	}

	public Long getCnpj() {
		return cnpj;
	}

	public void setCnpj(Long cnpj) {
		this.cnpj = cnpj;
	}

	public Long getTelefoneComercial() {
		return telefoneComercial;
	}

	public void setTelefoneComercial(Long telefoneComercial) {
		this.telefoneComercial = telefoneComercial;
	}

	public Long getTelefoneCelular() {
		return telefoneCelular;
	}

	public void setTelefoneCelular(Long telefoneCelular) {
		this.telefoneCelular = telefoneCelular;
	}

	public List<Contador> getListaContador() {
		return listaContador;
	}

	public void setListaContador(List<Contador> listaContador) {
		this.listaContador = listaContador;
	}

	public List<Condominio> getListaCondominio() {
		return listaCondominio;
	}

	public void setListaCondominio(List<Condominio> listaCondominio) {
		this.listaCondominio = listaCondominio;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}	
	

}
