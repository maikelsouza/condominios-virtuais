package br.com.condominiosvirtuais.controller;

import java.io.Serializable;

import org.richfaces.component.SortOrder;

public class OrdenaArquivoMB implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private SortOrder nomeArquivo = SortOrder.unsorted;	
	
	public void ordenarPorNome(){
		if (this.nomeArquivo.equals(SortOrder.ascending)) {
			 this.setNomeArquivo(SortOrder.descending);
		} else {
			 this.setNomeArquivo(SortOrder.ascending);
		}
	}

	public void setNomeArquivo(SortOrder nomeArquivo) {
			this.nomeArquivo = nomeArquivo;
	}

	public SortOrder getNomeArquivo() {
		return nomeArquivo;
	}		
	
}
