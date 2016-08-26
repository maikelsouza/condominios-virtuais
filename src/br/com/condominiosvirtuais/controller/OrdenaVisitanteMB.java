package br.com.condominiosvirtuais.controller;

import java.io.Serializable;

import org.richfaces.component.SortOrder;

public class OrdenaVisitanteMB implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private SortOrder nomeVisitante = SortOrder.unsorted;
	
	private SortOrder cpfVisitante = SortOrder.unsorted;
	
	private SortOrder rgVisitante = SortOrder.unsorted;
	
	private SortOrder telefoneVisitante = SortOrder.unsorted;
	
	public void ordenarPorNomeVisitante(){
		this.setCpfVisitante(SortOrder.unsorted);
		this.setRgVisitante(SortOrder.unsorted);
		this.setTelefoneVisitante(SortOrder.unsorted);		
		if (nomeVisitante.equals(SortOrder.ascending)) {
			 this.setNomeVisitante(SortOrder.descending);
		} else {
			 this.setNomeVisitante(SortOrder.ascending);
		}
	}
	
	public void ordenarPorCpfVisitante(){
		this.setRgVisitante(SortOrder.unsorted);
		this.setTelefoneVisitante(SortOrder.unsorted);
		this.setNomeVisitante(SortOrder.unsorted);
		if (cpfVisitante.equals(SortOrder.ascending)) {
			 this.setCpfVisitante(SortOrder.descending);
		} else {
			 this.setCpfVisitante(SortOrder.ascending);
		}
	}
	
	
	public void ordenarPorRgVisitante(){
		this.setTelefoneVisitante(SortOrder.unsorted);
		this.setCpfVisitante(SortOrder.unsorted);
		this.setNomeVisitante(SortOrder.unsorted);
		if (rgVisitante.equals(SortOrder.ascending)) {
			 this.setRgVisitante(SortOrder.descending);
		} else {
			 this.setRgVisitante(SortOrder.ascending);
		}
	}
	
	public void ordenarPorTelefoneVisitante(){
		this.setCpfVisitante(SortOrder.unsorted);
		this.setNomeVisitante(SortOrder.unsorted);
		this.setRgVisitante(SortOrder.unsorted);
		if (telefoneVisitante.equals(SortOrder.ascending)) {
			 this.setTelefoneVisitante(SortOrder.descending);
		} else {
			 this.setTelefoneVisitante(SortOrder.ascending);
		}
	}

	public SortOrder getNomeVisitante() {
		return nomeVisitante;
	}

	public void setNomeVisitante(SortOrder nomeVisitante) {
		this.nomeVisitante = nomeVisitante;
	}

	public SortOrder getCpfVisitante() {
		return cpfVisitante;
	}

	public void setCpfVisitante(SortOrder cpfVisitante) {
		this.cpfVisitante = cpfVisitante;
	}

	public SortOrder getRgVisitante() {
		return rgVisitante;
	}

	public void setRgVisitante(SortOrder rgVisitante) {
		this.rgVisitante = rgVisitante;
	}

	public SortOrder getTelefoneVisitante() {
		return telefoneVisitante;
	}

	public void setTelefoneVisitante(SortOrder telefoneVisitante) {
		this.telefoneVisitante = telefoneVisitante;
	}	

}
