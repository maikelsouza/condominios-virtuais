package br.com.condominiosvirtuais.controller;

import java.io.Serializable;

import org.richfaces.component.SortOrder;

/**
 * MB Criado para ordenar (asc e desc) os itens da lista de Boletos. Página: formListaBoleto.xhtml
 * @author Maikel Joel de Souza
 * @since 18/02/2017  
 */
public class OrdenaBoletoMB implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private SortOrder blocoPagadorBoleto = SortOrder.unsorted;
	
	private SortOrder unidadePagadorBoleto = SortOrder.unsorted;
	
	private SortOrder nomePagadorBoleto = SortOrder.unsorted;
	
	private SortOrder vencimentoBoleto = SortOrder.unsorted;
	
	private SortOrder emissaoBoleto = SortOrder.unsorted;
	
	private SortOrder pagoBoleto = SortOrder.unsorted;
	
	private SortOrder valorBoleto = SortOrder.unsorted;		
	
	
	
	
	public void ordenarPorBlocoPagador(){
		this.setUnidadePagadorBoleto(SortOrder.unsorted);			
		this.setNomePagadorBoleto(SortOrder.unsorted);			
		this.setVencimentoBoleto(SortOrder.unsorted);			
		this.setValorBoleto(SortOrder.unsorted);
		this.setPagoBoleto(SortOrder.unsorted);
		this.setEmissaoBoleto(SortOrder.unsorted);
		if (this.nomePagadorBoleto.equals(SortOrder.ascending)) {
			 this.setNomePagadorBoleto(SortOrder.descending);
		} else {
			 this.setNomePagadorBoleto(SortOrder.ascending);
		}
	}
	
	public void ordenarPorUnidadePagador(){
		this.setBlocoPagadorBoleto(SortOrder.unsorted);			
		this.setNomePagadorBoleto(SortOrder.unsorted);			
		this.setVencimentoBoleto(SortOrder.unsorted);			
		this.setValorBoleto(SortOrder.unsorted);
		this.setEmissaoBoleto(SortOrder.unsorted);
		if (this.unidadePagadorBoleto.equals(SortOrder.ascending)) {
			 this.setUnidadePagadorBoleto(SortOrder.descending);
		} else {
			 this.setUnidadePagadorBoleto(SortOrder.ascending);
		}
	}	
	
	public void ordenarPorNomePagador(){
		this.setBlocoPagadorBoleto(SortOrder.unsorted);			
		this.setUnidadePagadorBoleto(SortOrder.unsorted);		
		this.setVencimentoBoleto(SortOrder.unsorted);			
		this.setValorBoleto(SortOrder.unsorted);
		this.setPagoBoleto(SortOrder.unsorted);
		this.setEmissaoBoleto(SortOrder.unsorted);
		if (this.nomePagadorBoleto.equals(SortOrder.ascending)) {
			this.setNomePagadorBoleto(SortOrder.descending);
		} else {
			this.setNomePagadorBoleto(SortOrder.ascending);
		}
	}	
	public void ordenarPorVencimento(){
		this.setBlocoPagadorBoleto(SortOrder.unsorted);			
		this.setUnidadePagadorBoleto(SortOrder.unsorted);		
		this.setNomePagadorBoleto(SortOrder.unsorted);				
		this.setValorBoleto(SortOrder.unsorted);
		this.setPagoBoleto(SortOrder.unsorted);
		this.setEmissaoBoleto(SortOrder.unsorted);
		if (this.vencimentoBoleto.equals(SortOrder.ascending)) {
			this.setVencimentoBoleto(SortOrder.descending);
		} else {
			this.setVencimentoBoleto(SortOrder.ascending);
		}
	}	
	public void ordenarPorValor(){
		this.setBlocoPagadorBoleto(SortOrder.unsorted);			
		this.setUnidadePagadorBoleto(SortOrder.unsorted);		
		this.setNomePagadorBoleto(SortOrder.unsorted);				
		this.setVencimentoBoleto(SortOrder.unsorted);
		this.setPagoBoleto(SortOrder.unsorted);
		this.setEmissaoBoleto(SortOrder.unsorted);
		if (this.valorBoleto.equals(SortOrder.ascending)) {
			this.setValorBoleto(SortOrder.descending);
		} else {
			this.setValorBoleto(SortOrder.ascending);
		}
	}
	
	public void ordenarPorPago(){
		this.setBlocoPagadorBoleto(SortOrder.unsorted);			
		this.setUnidadePagadorBoleto(SortOrder.unsorted);		
		this.setNomePagadorBoleto(SortOrder.unsorted);				
		this.setVencimentoBoleto(SortOrder.unsorted);
		this.setValorBoleto(SortOrder.unsorted);
		this.setEmissaoBoleto(SortOrder.unsorted);
		if (this.pagoBoleto.equals(SortOrder.ascending)) {
			this.setPagoBoleto(SortOrder.descending);
		} else {
			this.setPagoBoleto(SortOrder.ascending);
		}
	}
	
	public void ordenarPorEmissao(){
		this.setBlocoPagadorBoleto(SortOrder.unsorted);			
		this.setUnidadePagadorBoleto(SortOrder.unsorted);		
		this.setNomePagadorBoleto(SortOrder.unsorted);				
		this.setVencimentoBoleto(SortOrder.unsorted);
		this.setValorBoleto(SortOrder.unsorted);
		this.setPagoBoleto(SortOrder.unsorted);
		if (this.emissaoBoleto.equals(SortOrder.ascending)) {
			this.setEmissaoBoleto(SortOrder.descending);
		} else {
			this.setEmissaoBoleto(SortOrder.ascending);
		}
	}

	public SortOrder getBlocoPagadorBoleto() {
		return blocoPagadorBoleto;
	}

	public void setBlocoPagadorBoleto(SortOrder blocoPagadorBoleto) {
		this.blocoPagadorBoleto = blocoPagadorBoleto;
	}

	public SortOrder getUnidadePagadorBoleto() {
		return unidadePagadorBoleto;
	}

	public void setUnidadePagadorBoleto(SortOrder unidadePagadorBoleto) {
		this.unidadePagadorBoleto = unidadePagadorBoleto;
	}

	public SortOrder getNomePagadorBoleto() {
		return nomePagadorBoleto;
	}

	public void setNomePagadorBoleto(SortOrder nomePagadorBoleto) {
		this.nomePagadorBoleto = nomePagadorBoleto;
	}

	public SortOrder getVencimentoBoleto() {
		return vencimentoBoleto;
	}

	public void setVencimentoBoleto(SortOrder vencimentoBoleto) {
		this.vencimentoBoleto = vencimentoBoleto;
	}

	public SortOrder getValorBoleto() {
		return valorBoleto;
	}

	public void setValorBoleto(SortOrder valorBoleto) {
		this.valorBoleto = valorBoleto;
	}

	public SortOrder getPagoBoleto() {
		return pagoBoleto;
	}

	public void setPagoBoleto(SortOrder pagoBoleto) {
		this.pagoBoleto = pagoBoleto;
	}

	public SortOrder getEmissaoBoleto() {
		return emissaoBoleto;
	}

	public void setEmissaoBoleto(SortOrder emissaoBoleto) {
		this.emissaoBoleto = emissaoBoleto;
	}		
	

}
