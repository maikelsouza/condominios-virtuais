package br.com.condominiosvirtuais.entity;

public class Beneficiario implements Cloneable{
	
	private Integer id;
	
	private String nome;
	
	private String razaoSocial;
	
	private String NomeFantasia;
	
	private Long cprf;	
	
	private Integer idCondominio;
	
	private Boolean situacao;
	
	private Integer tipoDocumento;	
	
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

	public String getRazaoSocial() {
		return razaoSocial;
	}

	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}

	public String getNomeFantasia() {
		return NomeFantasia;
	}

	public void setNomeFantasia(String nomeFantasia) {
		NomeFantasia = nomeFantasia;
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

	public Boolean getSituacao() {
		return situacao;
	}

	public void setSituacao(Boolean situacao) {
		this.situacao = situacao;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}
	
	public Integer getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(Integer tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public Beneficiario clone() throws CloneNotSupportedException{
        return (Beneficiario) super.clone();
	}
	

}
