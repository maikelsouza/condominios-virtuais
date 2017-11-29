package br.com.condominiosvirtuais.controller;

import java.io.Serializable;

import org.richfaces.component.SortOrder;

/**
 * MB Criado para ordenar (asc e desc) as abas de uma tela do sistema. Página: formListaTelaAba.xhtml
 * @author Maikel Joel de Souza
 * @since 16/07/2017  
 */
public class OrdenaAbaMB implements Serializable {

	private static final long serialVersionUID = 1L;

	private SortOrder nomeAba = SortOrder.unsorted;
	
	private SortOrder descricaoAba= SortOrder.unsorted;
	
		
	public void ordenarPorNome(){
		this.setDescricaoAba(SortOrder.unsorted);
		if (this.nomeAba.equals(SortOrder.ascending)) {
			 this.setNomeAba(SortOrder.descending);
		} else {
			 this.setNomeAba(SortOrder.ascending);
		}
	}
	
	public void ordenarPorDescricao(){
		this.setNomeAba(SortOrder.unsorted);
		if (this.descricaoAba.equals(SortOrder.ascending)) {
			 this.setDescricaoAba(SortOrder.descending);
		} else {
			 this.setDescricaoAba(SortOrder.ascending);
		}
	}
	
	
	

	public SortOrder getNomeAba() {
		return nomeAba;
	}

	public void setNomeAba(SortOrder nomeAba) {
		this.nomeAba = nomeAba;
	}

	public SortOrder getDescricaoAba() {
		return descricaoAba;
	}

	public void setDescricaoAba(SortOrder descricaoAba) {
		this.descricaoAba = descricaoAba;
	}
	
	

}
