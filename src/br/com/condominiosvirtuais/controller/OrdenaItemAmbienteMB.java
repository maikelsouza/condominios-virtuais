package br.com.condominiosvirtuais.controller;

import java.io.Serializable;

import org.richfaces.component.SortOrder;

public class OrdenaItemAmbienteMB implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private SortOrder nomeItemAmbiente = SortOrder.unsorted;
	
	private SortOrder quantidadeItemAmbiente = SortOrder.unsorted;	
	
	public void ordenarPorNome(){
		this.setQuantidadeItemAmbiente(SortOrder.unsorted);		
		if (this.nomeItemAmbiente.equals(SortOrder.ascending)) {
			 this.setNomeItemAmbiente(SortOrder.descending);
		} else {
			 this.setNomeItemAmbiente(SortOrder.ascending);
		}
	}
	
	public void ordenarPorQuantidade(){
		this.setNomeItemAmbiente(SortOrder.unsorted);		
		if (this.quantidadeItemAmbiente.equals(SortOrder.ascending)) {
			 this.setQuantidadeItemAmbiente(SortOrder.descending);
		} else {
			 this.setQuantidadeItemAmbiente(SortOrder.ascending);
		}
	}

	public SortOrder getNomeItemAmbiente() {
		return nomeItemAmbiente;
	}

	public void setNomeItemAmbiente(SortOrder nomeItemAmbiente) {
		this.nomeItemAmbiente = nomeItemAmbiente;
	}

	public SortOrder getQuantidadeItemAmbiente() {
		return quantidadeItemAmbiente;
	}

	public void setQuantidadeItemAmbiente(SortOrder quantidadeItemAmbiente) {
		this.quantidadeItemAmbiente = quantidadeItemAmbiente;
	}

}
