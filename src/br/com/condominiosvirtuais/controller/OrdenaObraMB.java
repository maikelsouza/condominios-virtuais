package br.com.condominiosvirtuais.controller;

import java.io.Serializable;

import org.richfaces.component.SortOrder;

public class OrdenaObraMB implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private SortOrder nomeObra = SortOrder.unsorted;
	
	private SortOrder tipoObra = SortOrder.unsorted;	
	
	private SortOrder situacaoObra = SortOrder.unsorted;
	
	private SortOrder dataInicioObra = SortOrder.unsorted;
	
	private SortOrder dataFimObra = SortOrder.unsorted;
	
	private SortOrder valorObra = SortOrder.unsorted;
	
	private SortOrder descricaoObra = SortOrder.unsorted;
	
	public void ordenarPorNomeObra() {
		this.setDataFimObra(SortOrder.unsorted);
		this.setDataInicioObra(SortOrder.unsorted);
		this.setTipoObra(SortOrder.unsorted);
		this.setSituacaoObra(SortOrder.unsorted);
		this.setDescricaoObra(SortOrder.unsorted);
		this.setValorObra(SortOrder.unsorted);
		if (this.nomeObra.equals(SortOrder.ascending)) {
			this.setNomeObra(SortOrder.descending);
		} else {
			this.setNomeObra(SortOrder.ascending);
		}
	}
	
	public void ordenarPorTipoObra() {
		this.setNomeObra(SortOrder.unsorted);
		this.setDataFimObra(SortOrder.unsorted);
		this.setDataInicioObra(SortOrder.unsorted);		
		this.setSituacaoObra(SortOrder.unsorted);
		this.setDescricaoObra(SortOrder.unsorted);
		this.setValorObra(SortOrder.unsorted);
		if (this.tipoObra.equals(SortOrder.ascending)) {
			this.setTipoObra(SortOrder.descending);
		} else {
			this.setTipoObra(SortOrder.ascending);
		}
	}
	
	public void ordenarPorSituacaoObra() {
		this.setNomeObra(SortOrder.unsorted);
		this.setDataFimObra(SortOrder.unsorted);
		this.setDataInicioObra(SortOrder.unsorted);			
		this.setTipoObra(SortOrder.unsorted);
		this.setDescricaoObra(SortOrder.unsorted);
		this.setValorObra(SortOrder.unsorted);
		if (this.situacaoObra.equals(SortOrder.ascending)) {
			this.setSituacaoObra(SortOrder.descending);
		} else {
			this.setSituacaoObra(SortOrder.ascending);
		}
	}
	
	public void ordenarPorDataInicioObra() {
		this.setNomeObra(SortOrder.unsorted);
		this.setDataFimObra(SortOrder.unsorted);
		this.setTipoObra(SortOrder.unsorted);
		this.setSituacaoObra(SortOrder.unsorted);
		this.setDescricaoObra(SortOrder.unsorted);
		this.setValorObra(SortOrder.unsorted);
		if (this.dataInicioObra.equals(SortOrder.ascending)) {
			this.setDataInicioObra(SortOrder.descending);
		} else {
			this.setDataInicioObra(SortOrder.ascending);
		}
	}
	
	public void ordenarPorDataFimObra() {
		this.setNomeObra(SortOrder.unsorted);
		this.setDataInicioObra(SortOrder.unsorted);
		this.setTipoObra(SortOrder.unsorted);
		this.setSituacaoObra(SortOrder.unsorted);
		this.setValorObra(SortOrder.unsorted);
		this.setDescricaoObra(SortOrder.unsorted);
		if (this.dataFimObra.equals(SortOrder.ascending)) {
			this.setDataFimObra(SortOrder.descending);
		} else {
			this.setDataFimObra(SortOrder.ascending);
		}
	}
	
	public void ordenarPorValorObra() {
		this.setNomeObra(SortOrder.unsorted);
		this.setDataInicioObra(SortOrder.unsorted);
		this.setDataFimObra(SortOrder.unsorted);
		this.setTipoObra(SortOrder.unsorted);		
		this.setSituacaoObra(SortOrder.unsorted);
		this.setDescricaoObra(SortOrder.unsorted);
		if (this.valorObra.equals(SortOrder.ascending)) {
			this.setValorObra(SortOrder.descending);
		} else {
			this.setValorObra(SortOrder.ascending);
		}
	}
	
	public void ordenarPorDescricaoObra() {
		this.setNomeObra(SortOrder.unsorted);
		this.setDataInicioObra(SortOrder.unsorted);
		this.setDataFimObra(SortOrder.unsorted);
		this.setTipoObra(SortOrder.unsorted);		
		this.setSituacaoObra(SortOrder.unsorted);
		this.setValorObra(SortOrder.unsorted);
		if (this.descricaoObra.equals(SortOrder.ascending)) {
			this.setDescricaoObra(SortOrder.descending);
		} else {
			this.setDescricaoObra(SortOrder.ascending);
		}
	}

	public SortOrder getNomeObra() {
		return nomeObra;
	}

	public void setNomeObra(SortOrder nomeObra) {
		this.nomeObra = nomeObra;
	}

	public SortOrder getTipoObra() {
		return tipoObra;
	}

	public void setTipoObra(SortOrder tipoObra) {
		this.tipoObra = tipoObra;
	}

	public SortOrder getSituacaoObra() {
		return situacaoObra;
	}

	public void setSituacaoObra(SortOrder situacaoObra) {
		this.situacaoObra = situacaoObra;
	}

	public SortOrder getDataInicioObra() {
		return dataInicioObra;
	}

	public void setDataInicioObra(SortOrder dataInicioObra) {
		this.dataInicioObra = dataInicioObra;
	}

	public SortOrder getDataFimObra() {
		return dataFimObra;
	}

	public void setDataFimObra(SortOrder dataFimObra) {
		this.dataFimObra = dataFimObra;
	}

	public SortOrder getValorObra() {
		return valorObra;
	}

	public void setValorObra(SortOrder valorObra) {
		this.valorObra = valorObra;
	}

	public SortOrder getDescricaoObra() {
		return descricaoObra;
	}

	public void setDescricaoObra(SortOrder descricaoObra) {
		this.descricaoObra = descricaoObra;
	}	

}
