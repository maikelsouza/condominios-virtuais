package br.com.condominiosvirtuais.controller;

import java.io.Serializable;

import org.richfaces.component.SortOrder;

/**
 * MB Criado para ordenar (asc e desc) os itens da lista de despesas. Página: formListaReceitaDespesa.xhtml
 * @author Maikel Joel de Souza
 * @since 09/12/2016    
 */
public class OrdenaDespesaMB implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private SortOrder nomeDespesa = SortOrder.unsorted;
	
	private SortOrder valorDespesa = SortOrder.unsorted;	
	

	public void ordenarPorNomeDespesa(){			
		this.setValorDespesa(SortOrder.unsorted);		
		if (this.nomeDespesa.equals(SortOrder.ascending)) {
			 this.setNomeDespesa(SortOrder.descending);
		} else {
			 this.setNomeDespesa(SortOrder.ascending);
		}
	}
	
	public void ordenarPorValorDespesa(){			
		this.setNomeDespesa(SortOrder.unsorted);
		if (this.valorDespesa.equals(SortOrder.ascending)) {
			 this.setValorDespesa(SortOrder.descending);
		} else {
			 this.setValorDespesa(SortOrder.ascending);
		}
	}

	public SortOrder getNomeDespesa() {
		return nomeDespesa;
	}

	public void setNomeDespesa(SortOrder nomeDespesa) {
		this.nomeDespesa = nomeDespesa;
	}

	public SortOrder getValorDespesa() {
		return valorDespesa;
	}

	public void setValorDespesa(SortOrder valorDespesa) {
		this.valorDespesa = valorDespesa;
	}
		

}
