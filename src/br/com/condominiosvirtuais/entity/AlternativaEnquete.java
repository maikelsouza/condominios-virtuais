package br.com.condominiosvirtuais.entity;

public class AlternativaEnquete {
	
	private Integer id;
	
	private String alternativa;
	
	private Integer voto;
	
	private Double porcentagem;	
	
	private Integer idEnquete;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAlternativa() {
		return alternativa;
	}

	public void setAlternativa(String alternativa) {
		this.alternativa = alternativa;
	}

	public Integer getVoto() {
		return voto;
	}

	public void setVoto(Integer voto) {
		this.voto = voto;
	}	

	public Double getPorcentagem() {
		return porcentagem;
	}

	public void setPorcentagem(Double porcentagem) {
		this.porcentagem = porcentagem;
	}

	public Integer getIdEnquete() {
		return idEnquete;
	}

	public void setIdEnquete(Integer idEnquete) {
		this.idEnquete = idEnquete;
	}

}
