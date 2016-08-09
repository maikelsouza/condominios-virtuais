package br.com.condominiosvirtuais.controller;

import java.io.Serializable;

import org.richfaces.component.SortOrder;

public class OrdenaUnidadeMB implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private SortOrder numeroUnidade = SortOrder.unsorted;

	private SortOrder tipoUnidade = SortOrder.unsorted;
	
	private SortOrder blocoUnidade = SortOrder.unsorted;
	
	public void ordenarPorNumero(){
		this.setTipoUnidade(SortOrder.unsorted);
		this.setBlocoUnidade(SortOrder.unsorted);
		if (this.numeroUnidade.equals(SortOrder.ascending)) {
			 this.setNumeroUnidade(SortOrder.descending);
		} else {
			 this.setNumeroUnidade(SortOrder.ascending);
		}
	}
	
	public void ordenarPorTipo(){
		this.setNumeroUnidade(SortOrder.unsorted);
		this.setBlocoUnidade(SortOrder.unsorted);
		if (this.tipoUnidade.equals(SortOrder.ascending)) {
			 this.setTipoUnidade(SortOrder.descending);
		} else {
			 this.setTipoUnidade(SortOrder.ascending);
		}
	}
	
	public void ordenarPorBloco(){
		this.setNumeroUnidade(SortOrder.unsorted);
		this.setTipoUnidade(SortOrder.unsorted);
		if (this.blocoUnidade.equals(SortOrder.ascending)) {
			this.setBlocoUnidade(SortOrder.descending);
		} else {
			this.setBlocoUnidade(SortOrder.ascending);
		}
	}

	public SortOrder getNumeroUnidade() {
		return numeroUnidade;
	}

	public void setNumeroUnidade(SortOrder numeroUnidade) {
		this.numeroUnidade = numeroUnidade;
	}

	public SortOrder getTipoUnidade() {
		return tipoUnidade;
	}

	public void setTipoUnidade(SortOrder tipoUnidade) {
		this.tipoUnidade = tipoUnidade;
	}

	public SortOrder getBlocoUnidade() {
		return blocoUnidade;
	}

	public void setBlocoUnidade(SortOrder blocoUnidade) {
		this.blocoUnidade = blocoUnidade;
	}	
}
