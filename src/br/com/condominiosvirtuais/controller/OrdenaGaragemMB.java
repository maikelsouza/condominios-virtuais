package br.com.condominiosvirtuais.controller;

import java.io.Serializable;

import org.richfaces.component.SortOrder;

/**
 * MB Criado para ordenar (asc e desc) os itens da lista garagens. Página:
 * formCadastroGaragem.xhtml.xhtml
 * 
 * @author Maikel Joel de Souza
 * @since 31/12/2013
 */
public class OrdenaGaragemMB implements Serializable{

	
	private static final long serialVersionUID = 1L;
	
	private SortOrder numeroGaragem = SortOrder.unsorted;

	public void ordenarPorNumeroGaragem() {		
		if (numeroGaragem.equals(SortOrder.ascending)) {
			this.setNumeroGaragem(SortOrder.descending);
		} else {
			this.setNumeroGaragem(SortOrder.ascending);
		}
	}

	public SortOrder getNumeroGaragem() {
		return numeroGaragem;
	}

	public void setNumeroGaragem(SortOrder numeroGaragem) {
		this.numeroGaragem = numeroGaragem;
	}		

}