package br.com.condominiosvirtuais.entity;

public class SindicoProfissional extends Usuario {
	
	private Long telefoneComercial;
	
	private Long telefoneCelular1;
	
	private Long telefoneCelular2;
	
	private Long telefoneCelular3;
	
	private String site;
	
	public Long getTelefoneComercial() {
		return telefoneComercial;
	}

	public void setTelefoneComercial(Long telefoneComercial) {
		this.telefoneComercial = telefoneComercial;
	}

	public Long getTelefoneCelular1() {
		return telefoneCelular1;
	}

	public void setTelefoneCelular1(Long telefoneCelular1) {
		this.telefoneCelular1 = telefoneCelular1;
	}

	public Long getTelefoneCelular2() {
		return telefoneCelular2;
	}

	public void setTelefoneCelular2(Long telefoneCelular2) {
		this.telefoneCelular2 = telefoneCelular2;
	}

	public Long getTelefoneCelular3() {
		return telefoneCelular3;
	}

	public void setTelefoneCelular3(Long telefoneCelular3) {
		this.telefoneCelular3 = telefoneCelular3;
	}

	public String getSite() {
		return site;
	}

	public void setSite(String site) {
		this.site = site;
	}
	
	
}
