package br.com.condominiosvirtuais.controller;

import java.io.Serializable;

import org.richfaces.component.SortOrder;

/**
 * MB Criado para ordenar (asc e desc) os itens da lista de Beneficiário. Página: formListaBeneficiario.xhtml
 * @author Maikel Joel de Souza
 * @since 13/02/2017  
 */
public class OrdenaBeneficiarioMB implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private SortOrder nomeBeneficiario = SortOrder.unsorted;
	
	private SortOrder cprfBeneficiario = SortOrder.unsorted;
	
	private SortOrder situacaoBeneficiario = SortOrder.unsorted;
	
	
	
	public void ordenarPorNome(){
		this.setCprfBeneficiario(SortOrder.unsorted);
		this.setSituacaoBeneficiario(SortOrder.unsorted);
		if (this.nomeBeneficiario.equals(SortOrder.ascending)) {
			 this.setNomeBeneficiario(SortOrder.descending);
		} else {
			 this.setNomeBeneficiario(SortOrder.ascending);
		}
	}
	
	public void ordenarPorCprf(){
		this.setNomeBeneficiario(SortOrder.unsorted);
		this.setSituacaoBeneficiario(SortOrder.unsorted);
		if (this.cprfBeneficiario.equals(SortOrder.ascending)) {
			 this.setCprfBeneficiario(SortOrder.descending);
		} else {
			 this.setCprfBeneficiario(SortOrder.ascending);
		}
	}
	
	public void ordenarPorSituacao(){
		this.setNomeBeneficiario(SortOrder.unsorted);
		this.setCprfBeneficiario(SortOrder.unsorted);
		if (this.situacaoBeneficiario.equals(SortOrder.ascending)) {
			this.setSituacaoBeneficiario(SortOrder.descending);
		} else {
			this.setSituacaoBeneficiario(SortOrder.ascending);
		}
	}	

	public SortOrder getNomeBeneficiario() {
		return nomeBeneficiario;
	}

	public void setNomeBeneficiario(SortOrder nomeBeneficiario) {
		this.nomeBeneficiario = nomeBeneficiario;
	}

	public SortOrder getCprfBeneficiario() {
		return cprfBeneficiario;
	}

	public void setCprfBeneficiario(SortOrder cprfBeneficiario) {
		this.cprfBeneficiario = cprfBeneficiario;
	}

	public SortOrder getSituacaoBeneficiario() {
		return situacaoBeneficiario;
	}

	public void setSituacaoBeneficiario(SortOrder situacaoBeneficiario) {
		this.situacaoBeneficiario = situacaoBeneficiario;
	}
	
	

}
