package br.com.condominiosvirtuais.entity;

public class Beneficiario {
	
	private Integer id;
	
	private String nome;
	
	private Long cprf;	
	
	private Integer idCondominio;
	
	private Endereco endereco;
	
	public Beneficiario(){
		this.endereco = new Endereco();
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

	public Integer getIdCondominio() {
		return idCondominio;
	}

	public void setIdCondominio(Integer idCondominio) {
		this.idCondominio = idCondominio;
	}

	public Long getCprf() {
		return cprf;
	}

	public void setCprf(Long cprf) {
		this.cprf = cprf;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}
	
	

}
