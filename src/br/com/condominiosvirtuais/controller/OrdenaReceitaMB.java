package br.com.condominiosvirtuais.controller;

import java.io.Serializable;

import org.richfaces.component.SortOrder;

/**
 * MB Criado para ordenar (asc e desc) os itens da lista de receitas. Página: formListaReceitaDespesa.xhtml
 * @author Maikel Joel de Souza
 * @since 09/12/2016    
 */
public class OrdenaReceitaMB implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private SortOrder descricaoReceita = SortOrder.unsorted;
	
	private SortOrder valorReceita = SortOrder.unsorted;
	
	private SortOrder dataReceita = SortOrder.unsorted;
	
	private SortOrder numeroDocumentoReceita = SortOrder.unsorted;
	
	private SortOrder meioPagamentoReceita = SortOrder.unsorted;
	
	private SortOrder observacaoReceita = SortOrder.unsorted;	
	
	

	public void ordenarPorDescricaoReceita(){			
		this.setValorReceita(SortOrder.unsorted);
		this.setDataReceita(SortOrder.unsorted);
		this.setNumeroDocumentoReceita(SortOrder.unsorted);
		this.setMeioPagamentoReceita(SortOrder.unsorted);
		this.setObservacaoReceita(SortOrder.unsorted);
		if (this.descricaoReceita.equals(SortOrder.ascending)) {
			 this.setDescricaoReceita(SortOrder.descending);
		} else {
			 this.setDescricaoReceita(SortOrder.ascending);
		}
	}
	
	public void ordenarPorValorReceita(){			
		this.setDescricaoReceita(SortOrder.unsorted);
		this.setDataReceita(SortOrder.unsorted);
		this.setNumeroDocumentoReceita(SortOrder.unsorted);
		this.setMeioPagamentoReceita(SortOrder.unsorted);
		this.setObservacaoReceita(SortOrder.unsorted);
		if (this.valorReceita.equals(SortOrder.ascending)) {
			 this.setValorReceita(SortOrder.descending);
		} else {
			 this.setValorReceita(SortOrder.ascending);
		}
	}
	
	public void ordenarPorDataReceita(){			
		this.setDescricaoReceita(SortOrder.unsorted);
		this.setValorReceita(SortOrder.unsorted);
		this.setNumeroDocumentoReceita(SortOrder.unsorted);
		this.setMeioPagamentoReceita(SortOrder.unsorted);
		this.setObservacaoReceita(SortOrder.unsorted);
		if (this.dataReceita.equals(SortOrder.ascending)) {
			 this.setDataReceita(SortOrder.descending);
		} else {
			 this.setDataReceita(SortOrder.ascending);
		}
	}
	
	public void ordenarPorNumeroDocumentoReceita(){			
		this.setDescricaoReceita(SortOrder.unsorted);
		this.setValorReceita(SortOrder.unsorted);
		this.setDataReceita(SortOrder.unsorted);
		this.setMeioPagamentoReceita(SortOrder.unsorted);
		this.setObservacaoReceita(SortOrder.unsorted);
		if (this.numeroDocumentoReceita.equals(SortOrder.ascending)) {
			 this.setNumeroDocumentoReceita(SortOrder.descending);
		} else {
			 this.setNumeroDocumentoReceita(SortOrder.ascending);
		}
	}	
	
	public void ordenarPorMeioPagamentoReceita(){			
		this.setDescricaoReceita(SortOrder.unsorted);
		this.setValorReceita(SortOrder.unsorted);
		this.setNumeroDocumentoReceita(SortOrder.unsorted);
		this.setDataReceita(SortOrder.unsorted);
		this.setObservacaoReceita(SortOrder.unsorted);
		if (this.meioPagamentoReceita.equals(SortOrder.ascending)) {
			 this.setMeioPagamentoReceita(SortOrder.descending);
		} else {
			 this.setMeioPagamentoReceita(SortOrder.ascending);
		}
	}
	
	public void ordenarPorObservacaoReceita(){			
		this.setDescricaoReceita(SortOrder.unsorted);
		this.setValorReceita(SortOrder.unsorted);
		this.setNumeroDocumentoReceita(SortOrder.unsorted);
		this.setMeioPagamentoReceita(SortOrder.unsorted);
		this.setDataReceita(SortOrder.unsorted);
		if (this.observacaoReceita.equals(SortOrder.ascending)) {
			 this.setObservacaoReceita(SortOrder.descending);
		} else {
			 this.setObservacaoReceita(SortOrder.ascending);
		}
	}

	public SortOrder getDescricaoReceita() {
		return descricaoReceita;
	}

	public void setDescricaoReceita(SortOrder descricaoReceita) {
		this.descricaoReceita = descricaoReceita;
	}

	public SortOrder getValorReceita() {
		return valorReceita;
	}

	public void setValorReceita(SortOrder valorReceita) {
		this.valorReceita = valorReceita;
	}

	public SortOrder getDataReceita() {
		return dataReceita;
	}

	public void setDataReceita(SortOrder dataReceita) {
		this.dataReceita = dataReceita;
	}

	public SortOrder getNumeroDocumentoReceita() {
		return numeroDocumentoReceita;
	}

	public void setNumeroDocumentoReceita(SortOrder numeroDocumentoReceita) {
		this.numeroDocumentoReceita = numeroDocumentoReceita;
	}

	public SortOrder getMeioPagamentoReceita() {
		return meioPagamentoReceita;
	}

	public void setMeioPagamentoReceita(SortOrder meioPagamentoReceita) {
		this.meioPagamentoReceita = meioPagamentoReceita;
	}

	public SortOrder getObservacaoReceita() {
		return observacaoReceita;
	}

	public void setObservacaoReceita(SortOrder observacaoReceita) {
		this.observacaoReceita = observacaoReceita;
	}	
		

}
