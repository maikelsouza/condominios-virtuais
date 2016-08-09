package br.com.condominiosvirtuais.controller;

import java.io.Serializable;

import org.richfaces.component.SortOrder;

/**
 * MB Criado para ordenar (asc e desc) os itens da lista de despesas. Página: formListaDespesasCondominio.xhtml
 * @author Maikel Joel de Souza
 * @since 01/06/2016 
 */
public class OrdenaDespesasMB implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private SortOrder descricaoDespesas = SortOrder.unsorted;
	
	private SortOrder valorDespesas = SortOrder.unsorted;
	
	private SortOrder mesAnoReferenciaDespesas = SortOrder.unsorted;
	
	private SortOrder tipoDespesas = SortOrder.unsorted;
	
	

	public void ordenarPorDescricao(){
		this.setMesAnoReferenciaDespesas(SortOrder.unsorted);
		this.setTipoDespesas(SortOrder.unsorted);
		this.setValorDespesas(SortOrder.unsorted);
		if (this.descricaoDespesas.equals(SortOrder.ascending)) {
			this.setDescricaoDespesas(SortOrder.descending);			 
		} else {
			this.setDescricaoDespesas(SortOrder.ascending);			 
		}
	}	
	
	public void ordenarPorValor(){
		this.setMesAnoReferenciaDespesas(SortOrder.unsorted);
		this.setTipoDespesas(SortOrder.unsorted);
		this.setDescricaoDespesas(SortOrder.unsorted);
		if (this.valorDespesas.equals(SortOrder.ascending)) {
			 this.setValorDespesas(SortOrder.descending);
		} else {
			this.setValorDespesas(SortOrder.ascending);
		}
	}
	
	public void ordenarPorAnoMesReferencia(){
		this.setTipoDespesas(SortOrder.unsorted);
		this.setDescricaoDespesas(SortOrder.unsorted);
		this.setValorDespesas(SortOrder.unsorted);
		if (this.mesAnoReferenciaDespesas.equals(SortOrder.ascending)) {
			 this.setMesAnoReferenciaDespesas(SortOrder.descending);
		} else {
			this.setMesAnoReferenciaDespesas(SortOrder.ascending);			 
		}
	}
	
	public void ordenarPorTipo(){
		this.setMesAnoReferenciaDespesas(SortOrder.unsorted);
		this.setDescricaoDespesas(SortOrder.unsorted);
		this.setValorDespesas(SortOrder.unsorted);
		if (this.tipoDespesas.equals(SortOrder.ascending)) {
			this.setTipoDespesas(SortOrder.descending);			 
		} else {
			this.setTipoDespesas(SortOrder.ascending);			 
		}
	}

	public SortOrder getDescricaoDespesas() {
		return descricaoDespesas;
	}

	public void setDescricaoDespesas(SortOrder descricaoDespesas) {
		this.descricaoDespesas = descricaoDespesas;
	}

	public SortOrder getValorDespesas() {
		return valorDespesas;
	}

	public void setValorDespesas(SortOrder valorDespesas) {
		this.valorDespesas = valorDespesas;
	}

	public SortOrder getMesAnoReferenciaDespesas() {
		return mesAnoReferenciaDespesas;
	}

	public void setMesAnoReferenciaDespesas(SortOrder mesAnoReferenciaDespesas) {
		this.mesAnoReferenciaDespesas = mesAnoReferenciaDespesas;
	}

	public SortOrder getTipoDespesas() {
		return tipoDespesas;
	}

	public void setTipoDespesas(SortOrder tipoDespesas) {
		this.tipoDespesas = tipoDespesas;
	}

	
}
