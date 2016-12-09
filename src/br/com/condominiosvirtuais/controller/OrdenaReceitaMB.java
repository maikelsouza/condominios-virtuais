package br.com.condominiosvirtuais.controller;

import java.io.Serializable;

import org.richfaces.component.SortOrder;

/**
 * MB Criado para ordenar (asc e desc) os itens da lista de receitas. Página: formListaReceitaDespesa.xhtml
 * @author Maikel Joel de Souza
 * @since 09/12/2016    
 */
public class OrdenaReceitaMB implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private SortOrder nomeReceita = SortOrder.unsorted;
	
	private SortOrder valorReceita = SortOrder.unsorted;	
	

	public void ordenarPorNomeReceita(){			
		this.setValorReceita(SortOrder.unsorted);		
		if (this.nomeReceita.equals(SortOrder.ascending)) {
			 this.setNomeReceita(SortOrder.descending);
		} else {
			 this.setNomeReceita(SortOrder.ascending);
		}
	}
	
	public void ordenarPorValorReceita(){			
		this.setNomeReceita(SortOrder.unsorted);
		if (this.valorReceita.equals(SortOrder.ascending)) {
			 this.setValorReceita(SortOrder.descending);
		} else {
			 this.setValorReceita(SortOrder.ascending);
		}
	}

	public SortOrder getNomeReceita() {
		return nomeReceita;
	}

	public void setNomeReceita(SortOrder nomeReceita) {
		this.nomeReceita = nomeReceita;
	}

	public SortOrder getValorReceita() {
		return valorReceita;
	}

	public void setValorReceita(SortOrder valorReceita) {
		this.valorReceita = valorReceita;
	}
	
	
	
	
		

}
