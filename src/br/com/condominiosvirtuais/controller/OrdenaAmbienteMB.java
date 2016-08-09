package br.com.condominiosvirtuais.controller;

import java.io.Serializable;

import org.richfaces.component.SortOrder;

/**
 * MB Criado para ordenar (asc e desc) os itens da lista de ambientes. Página: formListaAmbienteCondominio.xhtml
 * @author Maikel Joel de Souza
 * @since 02/06/2012  
 */
public class OrdenaAmbienteMB implements Serializable{
	
	
	private static final long serialVersionUID = 1L;
	
	private SortOrder nomeAmbiente = SortOrder.unsorted;		
	
	public void ordenarPorNomeAmbiente(){		
		if (nomeAmbiente.equals(SortOrder.ascending)) {
			 this.setNomeAmbiente(SortOrder.descending);
		} else {
			 this.setNomeAmbiente(SortOrder.ascending);
		}
	}	

	public SortOrder getNomeAmbiente() {
		return nomeAmbiente;
	}

	public void setNomeAmbiente(SortOrder nomeAmbiente) {
		this.nomeAmbiente = nomeAmbiente;
	}	

}