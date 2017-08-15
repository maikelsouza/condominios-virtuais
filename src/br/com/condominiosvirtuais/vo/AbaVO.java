package br.com.condominiosvirtuais.vo;

public class AbaVO implements Comparable<AbaVO>{
	
	private Integer id;
	
	private Integer idAba;
	
	private String nomeI18nAba;
	
	private String descricaoI18nAba;
	
	private Boolean checada;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getIdAba() {
		return idAba;
	}

	public void setIdAba(Integer idAba) {
		this.idAba = idAba;
	}

	public String getNomeI18nAba() {
		return nomeI18nAba;
	}

	public void setNomeI18nAba(String nomeI18nAba) {
		this.nomeI18nAba = nomeI18nAba;
	}

	public String getDescricaoI18nAba() {
		return descricaoI18nAba;
	}

	public void setDescricaoI18nAba(String descricaoI18nAba) {
		this.descricaoI18nAba = descricaoI18nAba;
	}

	public Boolean getChecada() {
		return checada;
	}

	public void setChecada(Boolean checada) {
		this.checada = checada;
	}	
	

	@Override
	public int compareTo(AbaVO abaVO) {
		return this.getNomeI18nAba().compareTo(abaVO.getNomeI18nAba());
	}
	

}
