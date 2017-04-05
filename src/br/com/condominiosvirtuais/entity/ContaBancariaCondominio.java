package br.com.condominiosvirtuais.entity;

public class ContaBancariaCondominio {
	
	private Integer id;
	
	private Integer idContaBancaria;
	
	private Integer idCondominio;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getIdContaBancaria() {
		return idContaBancaria;
	}

	public void setIdContaBancaria(Integer idContaBancaria) {
		this.idContaBancaria = idContaBancaria;
	}

	public Integer getIdCondominio() {
		return idCondominio;
	}

	public void setIdCondominio(Integer idCondominio) {
		this.idCondominio = idCondominio;
	}

}
