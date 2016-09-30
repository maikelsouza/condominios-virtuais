package br.com.condominiosvirtuais.controller;

import java.io.Serializable;

import org.richfaces.component.SortOrder;

public class OrdenaSindicoProfissionalMB implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private SortOrder nomeSindicoProfissional = SortOrder.unsorted;
	
	private SortOrder emailSindicoProfissional = SortOrder.unsorted;
	
	private SortOrder siteSindicoProfissional = SortOrder.unsorted;
	
	private SortOrder telefoneComercialSindicoProfissional = SortOrder.unsorted;
	
	private SortOrder telefoneCelular1SindicoProfissional = SortOrder.unsorted;
	
	
	public void ordenarPorNomeSindicoProfissional(){
		this.setEmailSindicoProfissional(SortOrder.unsorted);
		this.setSiteSindicoProfissional(SortOrder.unsorted);
		this.setTelefoneComercialSindicoProfissional(SortOrder.unsorted);
		this.setTelefoneCelular1SindicoProfissional(SortOrder.unsorted);
		if (this.nomeSindicoProfissional.equals(SortOrder.ascending)) {
			 this.setNomeSindicoProfissional(SortOrder.descending);
		} else {
			 this.setNomeSindicoProfissional(SortOrder.ascending);
		}
	}
	
	public void ordenarPorEmailSindicoProfissional(){
		this.setNomeSindicoProfissional(SortOrder.unsorted);
		this.setSiteSindicoProfissional(SortOrder.unsorted);
		this.setTelefoneComercialSindicoProfissional(SortOrder.unsorted);
		this.setTelefoneCelular1SindicoProfissional(SortOrder.unsorted);
		if (this.emailSindicoProfissional.equals(SortOrder.ascending)) {
			 this.setEmailSindicoProfissional(SortOrder.descending);
		} else {
			 this.setEmailSindicoProfissional(SortOrder.ascending);
		}
	}
	
	public void ordenarPorSiteSindicoProfissional(){
		this.setNomeSindicoProfissional(SortOrder.unsorted);
		this.setEmailSindicoProfissional(SortOrder.unsorted);
		this.setTelefoneComercialSindicoProfissional(SortOrder.unsorted);
		this.setTelefoneCelular1SindicoProfissional(SortOrder.unsorted);
		if (this.siteSindicoProfissional.equals(SortOrder.ascending)) {
			this.setSiteSindicoProfissional(SortOrder.descending);
		} else {
			this.setSiteSindicoProfissional(SortOrder.ascending);
		}
	}
	
	public void ordenarPorTelefoneComercialSindicoProfissional(){
		this.setNomeSindicoProfissional(SortOrder.unsorted);
		this.setEmailSindicoProfissional(SortOrder.unsorted);
		this.setSiteSindicoProfissional(SortOrder.unsorted);
		this.setTelefoneCelular1SindicoProfissional(SortOrder.unsorted);
		if (this.telefoneComercialSindicoProfissional.equals(SortOrder.ascending)) {
			this.setTelefoneComercialSindicoProfissional(SortOrder.descending);
		} else {
			this.setTelefoneComercialSindicoProfissional(SortOrder.ascending);
		}
	}
	
	public void ordenarPorTelefoneCelular1SindicoProfissional(){
		this.setNomeSindicoProfissional(SortOrder.unsorted);
		this.setEmailSindicoProfissional(SortOrder.unsorted);
		this.setSiteSindicoProfissional(SortOrder.unsorted);
		this.setTelefoneComercialSindicoProfissional(SortOrder.unsorted);
		if (this.telefoneCelular1SindicoProfissional.equals(SortOrder.ascending)) {
			this.setTelefoneCelular1SindicoProfissional(SortOrder.descending);
		} else {
			this.setTelefoneCelular1SindicoProfissional(SortOrder.ascending);
		}
	}

	public SortOrder getNomeSindicoProfissional() {
		return nomeSindicoProfissional;
	}

	public void setNomeSindicoProfissional(SortOrder nomeSindicoProfissional) {
		this.nomeSindicoProfissional = nomeSindicoProfissional;
	}

	public SortOrder getEmailSindicoProfissional() {
		return emailSindicoProfissional;
	}

	public void setEmailSindicoProfissional(SortOrder emailSindicoProfissional) {
		this.emailSindicoProfissional = emailSindicoProfissional;
	}

	public SortOrder getSiteSindicoProfissional() {
		return siteSindicoProfissional;
	}

	public void setSiteSindicoProfissional(SortOrder siteSindicoProfissional) {
		this.siteSindicoProfissional = siteSindicoProfissional;
	}

	public SortOrder getTelefoneComercialSindicoProfissional() {
		return telefoneComercialSindicoProfissional;
	}

	public void setTelefoneComercialSindicoProfissional(SortOrder telefoneComercialSindicoProfissional) {
		this.telefoneComercialSindicoProfissional = telefoneComercialSindicoProfissional;
	}

	public SortOrder getTelefoneCelular1SindicoProfissional() {
		return telefoneCelular1SindicoProfissional;
	}

	public void setTelefoneCelular1SindicoProfissional(SortOrder telefoneCelular1SindicoProfissional) {
		this.telefoneCelular1SindicoProfissional = telefoneCelular1SindicoProfissional;
	}	

}
