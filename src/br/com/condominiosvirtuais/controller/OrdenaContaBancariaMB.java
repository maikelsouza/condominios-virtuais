package br.com.condominiosvirtuais.controller;

import java.io.Serializable;

import org.richfaces.component.SortOrder;

/**
 * MB Criado para ordenar (asc e desc) os itens da lista de Contas Bancárias. Página: formListaContaBancaria.xhtml
 * @author Maikel Joel de Souza
 * @since 06/02/2017  
 */
public class OrdenaContaBancariaMB implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private SortOrder numeroContaBancaria= SortOrder.unsorted;
	
	private SortOrder agenciaContaBancaria = SortOrder.unsorted;
	
	private SortOrder carteiraContaBancaria = SortOrder.unsorted;
	
	private SortOrder bancoContaBancaria = SortOrder.unsorted;
	
	private SortOrder situacaoContaBancaria = SortOrder.unsorted;
	
	
	public void ordenarPorNumero(){
		this.setAgenciaContaBancaria(SortOrder.unsorted);	
		this.setCarteiraContaBancaria(SortOrder.unsorted);
		this.setBancoContaBancaria(SortOrder.unsorted);
		this.setSituacaoContaBancaria(SortOrder.unsorted);
		if (this.numeroContaBancaria.equals(SortOrder.ascending)) {
			 this.setNumeroContaBancaria(SortOrder.descending);
		} else {
			 this.setNumeroContaBancaria(SortOrder.ascending);
		}
	}
	
	public void ordenarPorAgencia(){
		this.setNumeroContaBancaria(SortOrder.unsorted);	
		this.setCarteiraContaBancaria(SortOrder.unsorted);
		this.setBancoContaBancaria(SortOrder.unsorted);
		this.setSituacaoContaBancaria(SortOrder.unsorted);
		if (this.agenciaContaBancaria.equals(SortOrder.ascending)) {
			 this.setAgenciaContaBancaria(SortOrder.descending);
		} else {
			 this.setAgenciaContaBancaria(SortOrder.ascending);
		}
	}
	
	public void ordenarPorCarteira(){
		this.setNumeroContaBancaria(SortOrder.unsorted);	
		this.setAgenciaContaBancaria(SortOrder.unsorted);
		this.setBancoContaBancaria(SortOrder.unsorted);
		this.setSituacaoContaBancaria(SortOrder.unsorted);
		if (this.carteiraContaBancaria.equals(SortOrder.ascending)) {
			 this.setCarteiraContaBancaria(SortOrder.descending);
		} else {
			 this.setCarteiraContaBancaria(SortOrder.ascending);
		}
	}
	
	public void ordenarPorBanco(){
		this.setNumeroContaBancaria(SortOrder.unsorted);	
		this.setAgenciaContaBancaria(SortOrder.unsorted);
		this.setCarteiraContaBancaria(SortOrder.unsorted);
		this.setSituacaoContaBancaria(SortOrder.unsorted);
		if (this.bancoContaBancaria.equals(SortOrder.ascending)) {
			 this.setBancoContaBancaria(SortOrder.descending);
		} else {
			 this.setBancoContaBancaria(SortOrder.ascending);
		}
	}
	
	public void ordenarPorSituacao(){
		this.setAgenciaContaBancaria(SortOrder.unsorted);	
		this.setCarteiraContaBancaria(SortOrder.unsorted);
		this.setBancoContaBancaria(SortOrder.unsorted);
		this.setNumeroContaBancaria(SortOrder.unsorted);
		if (this.situacaoContaBancaria.equals(SortOrder.ascending)) {
			 this.setSituacaoContaBancaria(SortOrder.descending);
		} else {
			 this.setSituacaoContaBancaria(SortOrder.ascending);
		}
	}

	public SortOrder getNumeroContaBancaria() {
		return numeroContaBancaria;
	}

	public void setNumeroContaBancaria(SortOrder numeroContaBancaria) {
		this.numeroContaBancaria = numeroContaBancaria;
	}

	public SortOrder getAgenciaContaBancaria() {
		return agenciaContaBancaria;
	}

	public void setAgenciaContaBancaria(SortOrder agenciaContaBancaria) {
		this.agenciaContaBancaria = agenciaContaBancaria;
	}

	public SortOrder getCarteiraContaBancaria() {
		return carteiraContaBancaria;
	}

	public void setCarteiraContaBancaria(SortOrder carteiraContaBancaria) {
		this.carteiraContaBancaria = carteiraContaBancaria;
	}

	public SortOrder getBancoContaBancaria() {
		return bancoContaBancaria;
	}

	public void setBancoContaBancaria(SortOrder bancoContaBancaria) {
		this.bancoContaBancaria = bancoContaBancaria;
	}

	public SortOrder getSituacaoContaBancaria() {
		return situacaoContaBancaria;
	}

	public void setSituacaoContaBancaria(SortOrder situacaoContaBancaria) {
		this.situacaoContaBancaria = situacaoContaBancaria;
	}
	

}
