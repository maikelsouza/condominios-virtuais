package br.com.condominiosvirtuais.controller;

import java.io.Serializable;

import org.richfaces.component.SortOrder;

/**
 * MB Criado para ordenar (asc e desc) os componentes de uma tela ou aba do sistema. Página: formListaTelaComponente.xhtml
 * @author Maikel Joel de Souza
 * @since 12/08/2017  
 */
public class OrdenaComponenteMB implements Serializable {

	private static final long serialVersionUID = 1L;

	private SortOrder nomeComponente = SortOrder.unsorted;
	
	private SortOrder descricaoComponente = SortOrder.unsorted;
	
	private SortOrder tipoComponente = SortOrder.unsorted;
	
		
	public void ordenarPorNome(){
		this.setDescricaoComponente(SortOrder.unsorted);
		this.setTipoComponente(SortOrder.unsorted);
		if (this.nomeComponente.equals(SortOrder.ascending)) {
			 this.setNomeComponente(SortOrder.descending);
		} else {
			 this.setNomeComponente(SortOrder.ascending);
		}
	}
	
	public void ordenarPorDescricao(){
		this.setNomeComponente(SortOrder.unsorted);
		this.setTipoComponente(SortOrder.unsorted);
		if (this.descricaoComponente.equals(SortOrder.ascending)) {
			 this.setDescricaoComponente(SortOrder.descending);
		} else {
			 this.setDescricaoComponente(SortOrder.ascending);
		}
	}
	
	public void ordenarPorTipo(){
		this.setNomeComponente(SortOrder.unsorted);
		this.setDescricaoComponente(SortOrder.unsorted);
		if (this.tipoComponente.equals(SortOrder.ascending)) {
			this.setTipoComponente(SortOrder.descending);
		} else {
			this.setTipoComponente(SortOrder.ascending);
		}
	}

	public SortOrder getNomeComponente() {
		return nomeComponente;
	}

	public void setNomeComponente(SortOrder nomeComponente) {
		this.nomeComponente = nomeComponente;
	}

	public SortOrder getDescricaoComponente() {
		return descricaoComponente;
	}

	public void setDescricaoComponente(SortOrder descricaoComponente) {
		this.descricaoComponente = descricaoComponente;
	}

	public SortOrder getTipoComponente() {
		return tipoComponente;
	}

	public void setTipoComponente(SortOrder tipoComponente) {
		this.tipoComponente = tipoComponente;
	}	
	

}
