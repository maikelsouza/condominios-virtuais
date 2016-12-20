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
	
	private SortOrder descricaoDespesa = SortOrder.unsorted;
	
	private SortOrder valorDespesa = SortOrder.unsorted;
	
	private SortOrder dataDespesa = SortOrder.unsorted;
	
	private SortOrder numeroDocumentoDespesa = SortOrder.unsorted;
	
	private SortOrder meioPagamentoDespesa = SortOrder.unsorted;
	
	private SortOrder observacaoDespesa = SortOrder.unsorted;	
	
	

	public void ordenarPorDescricaoDespesa(){			
		this.setValorDespesa(SortOrder.unsorted);
		this.setDataDespesa(SortOrder.unsorted);
		this.setNumeroDocumentoDespesa(SortOrder.unsorted);
		this.setMeioPagamentoDespesa(SortOrder.unsorted);
		this.setObservacaoDespesa(SortOrder.unsorted);
		if (this.descricaoDespesa.equals(SortOrder.ascending)) {
			 this.setDescricaoDespesa(SortOrder.descending);
		} else {
			 this.setDescricaoDespesa(SortOrder.ascending);
		}
	}
	
	public void ordenarPorValorDespesa(){			
		this.setDescricaoDespesa(SortOrder.unsorted);
		this.setDataDespesa(SortOrder.unsorted);
		this.setNumeroDocumentoDespesa(SortOrder.unsorted);
		this.setMeioPagamentoDespesa(SortOrder.unsorted);
		this.setObservacaoDespesa(SortOrder.unsorted);
		if (this.valorDespesa.equals(SortOrder.ascending)) {
			 this.setValorDespesa(SortOrder.descending);
		} else {
			 this.setValorDespesa(SortOrder.ascending);
		}
	}
	
	public void ordenarPorDataDespesa(){			
		this.setDescricaoDespesa(SortOrder.unsorted);
		this.setValorDespesa(SortOrder.unsorted);
		this.setNumeroDocumentoDespesa(SortOrder.unsorted);
		this.setMeioPagamentoDespesa(SortOrder.unsorted);
		this.setObservacaoDespesa(SortOrder.unsorted);
		if (this.dataDespesa.equals(SortOrder.ascending)) {
			 this.setDataDespesa(SortOrder.descending);
		} else {
			 this.setDataDespesa(SortOrder.ascending);
		}
	}
	
	public void ordenarPorNumeroDocumentoDespesa(){			
		this.setDescricaoDespesa(SortOrder.unsorted);
		this.setValorDespesa(SortOrder.unsorted);
		this.setDataDespesa(SortOrder.unsorted);
		this.setMeioPagamentoDespesa(SortOrder.unsorted);
		this.setObservacaoDespesa(SortOrder.unsorted);
		if (this.numeroDocumentoDespesa.equals(SortOrder.ascending)) {
			 this.setNumeroDocumentoDespesa(SortOrder.descending);
		} else {
			 this.setNumeroDocumentoDespesa(SortOrder.ascending);
		}
	}	
	
	public void ordenarPorMeioPagamentoDespesa(){			
		this.setDescricaoDespesa(SortOrder.unsorted);
		this.setValorDespesa(SortOrder.unsorted);
		this.setNumeroDocumentoDespesa(SortOrder.unsorted);
		this.setDataDespesa(SortOrder.unsorted);
		this.setObservacaoDespesa(SortOrder.unsorted);
		if (this.meioPagamentoDespesa.equals(SortOrder.ascending)) {
			 this.setMeioPagamentoDespesa(SortOrder.descending);
		} else {
			 this.setMeioPagamentoDespesa(SortOrder.ascending);
		}
	}
	
	public void ordenarPorObservacaoDespesa(){			
		this.setDescricaoDespesa(SortOrder.unsorted);
		this.setValorDespesa(SortOrder.unsorted);
		this.setNumeroDocumentoDespesa(SortOrder.unsorted);
		this.setMeioPagamentoDespesa(SortOrder.unsorted);
		this.setDataDespesa(SortOrder.unsorted);
		if (this.observacaoDespesa.equals(SortOrder.ascending)) {
			 this.setObservacaoDespesa(SortOrder.descending);
		} else {
			 this.setObservacaoDespesa(SortOrder.ascending);
		}
	}

	public SortOrder getDescricaoDespesa() {
		return descricaoDespesa;
	}

	public void setDescricaoDespesa(SortOrder descricaoDespesa) {
		this.descricaoDespesa = descricaoDespesa;
	}

	public SortOrder getValorDespesa() {
		return valorDespesa;
	}

	public void setValorDespesa(SortOrder valorDespesa) {
		this.valorDespesa = valorDespesa;
	}

	public SortOrder getDataDespesa() {
		return dataDespesa;
	}

	public void setDataDespesa(SortOrder dataDespesa) {
		this.dataDespesa = dataDespesa;
	}

	public SortOrder getNumeroDocumentoDespesa() {
		return numeroDocumentoDespesa;
	}

	public void setNumeroDocumentoDespesa(SortOrder numeroDocumentoDespesa) {
		this.numeroDocumentoDespesa = numeroDocumentoDespesa;
	}

	public SortOrder getMeioPagamentoDespesa() {
		return meioPagamentoDespesa;
	}

	public void setMeioPagamentoDespesa(SortOrder meioPagamentoDespesa) {
		this.meioPagamentoDespesa = meioPagamentoDespesa;
	}

	public SortOrder getObservacaoDespesa() {
		return observacaoDespesa;
	}

	public void setObservacaoDespesa(SortOrder observacaoDespesa) {
		this.observacaoDespesa = observacaoDespesa;
	}	

}
