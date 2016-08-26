package br.com.condominiosvirtuais.controller;

import java.io.Serializable;

import org.richfaces.component.SortOrder;

public class OrdenaVisitaMB implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private SortOrder dataVisita = SortOrder.unsorted;
	
	private SortOrder condominioVisita = SortOrder.unsorted;
	
	private SortOrder blocoVisita = SortOrder.unsorted;
	
	private SortOrder unidadeVisita = SortOrder.unsorted;
	
	private SortOrder condominoVisita = SortOrder.unsorted;
	
	
	public void ordenarPorDataVisita(){
		this.setCondominioVisita(SortOrder.unsorted);
		this.setBlocoVisita(SortOrder.unsorted);
		this.setUnidadeVisita(SortOrder.unsorted);
		this.setCondominoVisita(SortOrder.unsorted);				
		if (dataVisita.equals(SortOrder.ascending)) {
			 this.setDataVisita(SortOrder.descending);
		} else {
			 this.setDataVisita(SortOrder.ascending);
		}
	}
	
	public void ordenarPorCondominioVisita(){		
		this.setBlocoVisita(SortOrder.unsorted);
		this.setUnidadeVisita(SortOrder.unsorted);
		this.setCondominoVisita(SortOrder.unsorted);
		this.setDataVisita(SortOrder.unsorted);
		if (condominioVisita.equals(SortOrder.ascending)) {
			this.setCondominioVisita(SortOrder.descending);
		} else {
			this.setCondominioVisita(SortOrder.ascending);
		}
	}
	
	public void ordenarPorBlocoVisita(){		
		this.setCondominioVisita(SortOrder.unsorted);
		this.setUnidadeVisita(SortOrder.unsorted);
		this.setCondominoVisita(SortOrder.unsorted);
		this.setDataVisita(SortOrder.unsorted);
		if (blocoVisita.equals(SortOrder.ascending)) {
			this.setBlocoVisita(SortOrder.descending);
		} else {
			this.setBlocoVisita(SortOrder.ascending);
		}
	}
	
	public void ordenarPorUnidadeVisita(){		
		this.setDataVisita(SortOrder.unsorted);
		this.setCondominioVisita(SortOrder.unsorted);
		this.setBlocoVisita(SortOrder.unsorted);
		this.setCondominoVisita(SortOrder.unsorted);
		if (unidadeVisita.equals(SortOrder.ascending)) {
			this.setUnidadeVisita(SortOrder.descending);
		} else {
			this.setUnidadeVisita(SortOrder.ascending);
		}
	}
	
	public void ordenarPorCondominoVisita(){	
		this.setDataVisita(SortOrder.unsorted);
		this.setCondominioVisita(SortOrder.unsorted);
		this.setUnidadeVisita(SortOrder.unsorted);
		this.setBlocoVisita(SortOrder.unsorted);
		if (condominoVisita.equals(SortOrder.ascending)) {
			this.setCondominoVisita(SortOrder.descending);
		} else {
			this.setCondominoVisita(SortOrder.ascending);
		}
	}

	public SortOrder getDataVisita() {
		return dataVisita;
	}

	public void setDataVisita(SortOrder dataVisita) {
		this.dataVisita = dataVisita;
	}

	public SortOrder getCondominioVisita() {
		return condominioVisita;
	}

	public void setCondominioVisita(SortOrder condominioVisita) {
		this.condominioVisita = condominioVisita;
	}

	public SortOrder getBlocoVisita() {
		return blocoVisita;
	}

	public void setBlocoVisita(SortOrder blocoVisita) {
		this.blocoVisita = blocoVisita;
	}

	public SortOrder getUnidadeVisita() {
		return unidadeVisita;
	}

	public void setUnidadeVisita(SortOrder unidadeVisita) {
		this.unidadeVisita = unidadeVisita;
	}

	public SortOrder getCondominoVisita() {
		return condominoVisita;
	}

	public void setCondominoVisita(SortOrder condominoVisita) {
		this.condominoVisita = condominoVisita;
	}	

}
