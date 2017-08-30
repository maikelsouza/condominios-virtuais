package br.com.condominiosvirtuais.vo;

public class ComponenteVO implements Comparable<ComponenteVO>{
	
	private Integer id;
	
	private Integer idComponente;
	
	private String nomeI18nComponente;
	
	private String descricaoI18nComponente;
	
	private Boolean checada;
	
	private String tipoI18nComponente;
	
	private Integer idTela;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getIdComponente() {
		return idComponente;
	}

	public void setIdComponente(Integer idComponente) {
		this.idComponente = idComponente;
	}

	public String getNomeI18nComponente() {
		return nomeI18nComponente;
	}

	public void setNomeI18nComponente(String nomeI18nComponente) {
		this.nomeI18nComponente = nomeI18nComponente;
	}

	public String getDescricaoI18nComponente() {
		return descricaoI18nComponente;
	}

	public void setDescricaoI18nComponente(String descricaoI18nComponente) {
		this.descricaoI18nComponente = descricaoI18nComponente;
	}

	public Boolean getChecada() {
		return checada;
	}

	public void setChecada(Boolean checada) {
		this.checada = checada;
	}		

	public String getTipoI18nComponente() {
		return tipoI18nComponente;
	}	

	public void setTipoI18nComponente(String tipoI18nComponente) {
		this.tipoI18nComponente = tipoI18nComponente;
	}
	
	

	public Integer getIdTela() {
		return idTela;
	}

	public void setIdTela(Integer idTela) {
		this.idTela = idTela;
	}

	@Override
	public int compareTo(ComponenteVO componenteVO) {
		return this.getNomeI18nComponente().compareTo(componenteVO.getNomeI18nComponente());
	}
	

}
