package br.com.condominiosvirtuais.vo;

public class UnidadeVO {
	
	private Integer id;
	
	private Integer idCondominio;
	
	private Integer idBloco;
	
	private Integer tipoUnidade;
	
	private Integer numeroUnidade;
	
	private String nomeBloco;
	
	private String nomeCondominio;	
	

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}	

	public Integer getIdCondominio() {
		return idCondominio;
	}

	public void setIdCondominio(Integer idCondominio) {
		this.idCondominio = idCondominio;
	}

	public Integer getIdBloco() {
		return idBloco;
	}

	public void setIdBloco(Integer idBloco) {
		this.idBloco = idBloco;
	}

	public Integer getTipoUnidade() {
		return tipoUnidade;
	}

	public void setTipoUnidade(Integer tipoUnidade) {
		this.tipoUnidade = tipoUnidade;
	}

	public Integer getNumeroUnidade() {
		return numeroUnidade;
	}

	public void setNumeroUnidade(Integer numeroUnidade) {
		this.numeroUnidade = numeroUnidade;
	}

	public String getNomeBloco() {
		return nomeBloco;
	}

	public void setNomeBloco(String nomeBloco) {
		this.nomeBloco = nomeBloco;
	}

	public String getNomeCondominio() {
		return nomeCondominio;
	}

	public void setNomeCondominio(String nomeCondominio) {
		this.nomeCondominio = nomeCondominio;
	}

}
