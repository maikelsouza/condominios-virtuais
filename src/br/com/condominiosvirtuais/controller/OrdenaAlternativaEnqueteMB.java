package br.com.condominiosvirtuais.controller;

import java.io.Serializable;

import org.richfaces.component.SortOrder;

/**
 * MB Criado para ordenar (asc e desc) os itens da lista de alternativas enquete. Página: formVisualzarEnquete.xhtml
 * @author Maikel Joel de Souza
 * @since 20/12/2013  
 */
public class OrdenaAlternativaEnqueteMB implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private SortOrder alternativaAlternativaEnquete = SortOrder.unsorted;
	
	private SortOrder porcentagemAlternativaEnquete = SortOrder.unsorted;

	private SortOrder votoAlternativaEnquete = SortOrder.descending;
	
	public void ordenarPorAlternativaEnquete(){
		this.setVotoAlternativaEnquete(SortOrder.unsorted);
		this.setPorcentagemAlternativaEnquete(SortOrder.unsorted);
		if (this.alternativaAlternativaEnquete.equals(SortOrder.ascending)) {
			 this.setAlternativaAlternativaEnquete(SortOrder.descending);
		} else {
			 this.setAlternativaAlternativaEnquete(SortOrder.ascending);
		}
	}
	
	public void ordenarPorVotoEnquete(){
		this.setAlternativaAlternativaEnquete(SortOrder.unsorted);
		this.setPorcentagemAlternativaEnquete(SortOrder.unsorted);
		if (this.votoAlternativaEnquete.equals(SortOrder.ascending)) {
			 this.setVotoAlternativaEnquete(SortOrder.descending);
		} else {
			this.setVotoAlternativaEnquete(SortOrder.ascending);
		}
	}
	
	public void ordenarPorPorcentagemEnquete(){
		this.setAlternativaAlternativaEnquete(SortOrder.unsorted);
		this.setVotoAlternativaEnquete(SortOrder.unsorted);
		if (this.porcentagemAlternativaEnquete.equals(SortOrder.ascending)) {
			this.setPorcentagemAlternativaEnquete(SortOrder.descending);
		} else {
			this.setPorcentagemAlternativaEnquete(SortOrder.ascending);			
		}
	}

	public SortOrder getAlternativaAlternativaEnquete() {
		return alternativaAlternativaEnquete;
	}

	public void setAlternativaAlternativaEnquete(
			SortOrder alternativaAlternativaEnquete) {
		this.alternativaAlternativaEnquete = alternativaAlternativaEnquete;
	}

	public SortOrder getVotoAlternativaEnquete() {
		return votoAlternativaEnquete;
	}

	public void setVotoAlternativaEnquete(SortOrder votoAlternativaEnquete) {
		this.votoAlternativaEnquete = votoAlternativaEnquete;
	}

	public SortOrder getPorcentagemAlternativaEnquete() {
		return porcentagemAlternativaEnquete;
	}
	
	public void setPorcentagemAlternativaEnquete(
			SortOrder porcentagemAlternativaEnquete) {
		this.porcentagemAlternativaEnquete = porcentagemAlternativaEnquete;
	}	

}
