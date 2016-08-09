package br.com.condominiosvirtuais.controller;

import java.io.Serializable;

import org.richfaces.component.SortOrder;

public class OrdenaCondominoMB implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private SortOrder blocoCondomino = SortOrder.unsorted;
	
	private SortOrder unidadeCondomino = SortOrder.unsorted;	
	
	private SortOrder nomeCondomino = SortOrder.unsorted;
	
	public void ordenarPorBloco(){
		this.setUnidadeCondomino(SortOrder.unsorted);
		this.setNomeCondomino(SortOrder.unsorted);
		if (this.blocoCondomino.equals(SortOrder.ascending)) {
			 this.setBlocoCondomino(SortOrder.descending);
		} else {
			 this.setBlocoCondomino(SortOrder.ascending);
		}
	}
	
	public void ordenarPorUnidade(){
		this.setBlocoCondomino(SortOrder.unsorted);
		this.setNomeCondomino(SortOrder.unsorted);
		if (this.unidadeCondomino.equals(SortOrder.ascending)) {
			 this.setUnidadeCondomino(SortOrder.descending);
		} else {
			 this.setUnidadeCondomino(SortOrder.ascending);
		}
	}
	
	public void ordenarPorNome(){
		this.setBlocoCondomino(SortOrder.unsorted);
		this.setUnidadeCondomino(SortOrder.unsorted);
		if (this.nomeCondomino.equals(SortOrder.ascending)) {
			this.setNomeCondomino(SortOrder.descending);
		} else {
			this.setNomeCondomino(SortOrder.ascending);
		}
	}

	public SortOrder getBlocoCondomino() {
		return blocoCondomino;
	}

	public void setBlocoCondomino(SortOrder blocoCondomino) {
		this.blocoCondomino = blocoCondomino;
	}

	public SortOrder getUnidadeCondomino() {
		return unidadeCondomino;
	}

	public void setUnidadeCondomino(SortOrder unidadeCondomino) {
		this.unidadeCondomino = unidadeCondomino;
	}

	public SortOrder getNomeCondomino() {
		return nomeCondomino;
	}

	public void setNomeCondomino(SortOrder nomeCondomino) {
		this.nomeCondomino = nomeCondomino;
	}

}
