package br.com.condominiosvirtuais.entity;

public class TelaComponente {
	
	private Integer id;
	
	private Integer idTela;
	
	private Integer idComponente;
	
	private String acao;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getIdTela() {
		return idTela;
	}

	public void setIdTela(Integer idTela) {
		this.idTela = idTela;
	}

	public Integer getIdComponente() {
		return idComponente;
	}

	public void setIdComponente(Integer idComponente) {
		this.idComponente = idComponente;
	}

	public String getAcao() {
		return acao;
	}

	public void setAcao(String acao) {
		this.acao = acao;
	}	

}
