package br.com.condominiosvirtuais.entity;

public class ContaBancaria implements Cloneable {
	
	private Integer id;
	
	private String numero;
	
	private String agencia;
	
	private String carteira;
	
	private Banco banco;
	
	private Integer idCondominio;
	
	private Boolean situacao;
	
	private String descricao;
	
	private String token;
	
	private ConfiguracaoPadraoContaBancaria configuracaoPadraoContaBancaria;
	
	public ContaBancaria(){
		this.banco = new Banco();
		this.configuracaoPadraoContaBancaria = new ConfiguracaoPadraoContaBancaria();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getAgencia() {
		return agencia;
	}

	public void setAgencia(String agencia) {
		this.agencia = agencia;
	}

	public Banco getBanco() {
		return banco;
	}

	public void setBanco(Banco banco) {
		this.banco = banco;
	}
	

	public String getCarteira() {
		return carteira;
	}

	public void setCarteira(String carteira) {
		this.carteira = carteira;
	}

	public Integer getIdCondominio() {
		return idCondominio;
	}

	public void setIdCondominio(Integer idCondominio) {
		this.idCondominio = idCondominio;
	}

	public Boolean getSituacao() {
		return situacao;
	}

	public void setSituacao(Boolean situacao) {
		this.situacao = situacao;
	}
	
	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}		
	
	public ConfiguracaoPadraoContaBancaria getConfiguracaoPadraoContaBancaria() {
		return configuracaoPadraoContaBancaria;
	}

	public void setConfiguracaoPadraoContaBancaria(ConfiguracaoPadraoContaBancaria configuracaoPadraoContaBancaria) {
		this.configuracaoPadraoContaBancaria = configuracaoPadraoContaBancaria;
	}

	public ContaBancaria clone() throws CloneNotSupportedException{
        return (ContaBancaria) super.clone();
	}	

}
