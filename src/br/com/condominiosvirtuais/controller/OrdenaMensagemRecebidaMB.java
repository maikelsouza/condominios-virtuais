package br.com.condominiosvirtuais.controller;

import java.io.Serializable;

import org.richfaces.component.SortOrder;

public class OrdenaMensagemRecebidaMB implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private SortOrder dataMensagemRecebida = SortOrder.unsorted;
	
	private SortOrder remetenteMensagemRecebida = SortOrder.unsorted;	
	
	private SortOrder assuntoMensagemRecebida = SortOrder.unsorted;
	
	private SortOrder visualizadaMensagemRecebida = SortOrder.unsorted;
	
	private SortOrder blocoRemetenteMensagemRecebida = SortOrder.unsorted;
	
	private SortOrder unidadeRemetenteMensagemRecebida = SortOrder.unsorted;
	
	
	public void ordenarDataMensagemRecebida(){
		this.setRemetenteMensagemRecebida(SortOrder.unsorted);
		this.setAssuntoMensagemRecebida(SortOrder.unsorted);
		this.setVisualizadaMensagemRecebida(SortOrder.unsorted);
		this.setBlocoRemetenteMensagemRecebida(SortOrder.unsorted);
		this.setUnidadeRemetenteMensagemRecebida(SortOrder.unsorted);
		if (this.dataMensagemRecebida.equals(SortOrder.ascending)) {
			 this.setDataMensagemRecebida(SortOrder.descending);
		} else {
			 this.setDataMensagemRecebida(SortOrder.ascending);
		}
	}
	
	public void ordenarRemetenteMensagemRecebida(){
		this.setDataMensagemRecebida(SortOrder.unsorted);
		this.setAssuntoMensagemRecebida(SortOrder.unsorted);
		this.setVisualizadaMensagemRecebida(SortOrder.unsorted);
		this.setBlocoRemetenteMensagemRecebida(SortOrder.unsorted);
		this.setUnidadeRemetenteMensagemRecebida(SortOrder.unsorted);
		if (this.remetenteMensagemRecebida.equals(SortOrder.ascending)) {
			 this.setRemetenteMensagemRecebida(SortOrder.descending);
		} else {
			 this.setRemetenteMensagemRecebida(SortOrder.ascending);
		}
	}
	
	public void ordenarAssuntoMensagemRecebida(){
		this.setDataMensagemRecebida(SortOrder.unsorted);
		this.setRemetenteMensagemRecebida(SortOrder.unsorted);
		this.setVisualizadaMensagemRecebida(SortOrder.unsorted);
		this.setBlocoRemetenteMensagemRecebida(SortOrder.unsorted);
		this.setUnidadeRemetenteMensagemRecebida(SortOrder.unsorted);
		if (this.assuntoMensagemRecebida.equals(SortOrder.ascending)) {
			 this.setAssuntoMensagemRecebida(SortOrder.descending);
		} else {
			 this.setAssuntoMensagemRecebida(SortOrder.ascending);
		}
	}
	
	public void ordenarVisualizadaMensagemRecebida(){
		this.setAssuntoMensagemRecebida(SortOrder.unsorted);
		this.setRemetenteMensagemRecebida(SortOrder.unsorted);
		this.setDataMensagemRecebida(SortOrder.unsorted);
		this.setBlocoRemetenteMensagemRecebida(SortOrder.unsorted);
		this.setUnidadeRemetenteMensagemRecebida(SortOrder.unsorted);
		if (this.visualizadaMensagemRecebida.equals(SortOrder.ascending)) {
			 this.setVisualizadaMensagemRecebida(SortOrder.descending);
		} else {
			 this.setVisualizadaMensagemRecebida(SortOrder.ascending);
		}
	}
	
	public void ordenarBlocoRemetenteMensagemRecebida(){
		this.setAssuntoMensagemRecebida(SortOrder.unsorted);
		this.setRemetenteMensagemRecebida(SortOrder.unsorted);
		this.setDataMensagemRecebida(SortOrder.unsorted);		
		this.setUnidadeRemetenteMensagemRecebida(SortOrder.unsorted);
		this.setVisualizadaMensagemRecebida(SortOrder.unsorted);
		if (this.blocoRemetenteMensagemRecebida.equals(SortOrder.ascending)) {
			this.setBlocoRemetenteMensagemRecebida(SortOrder.descending);
		} else {
			this.setBlocoRemetenteMensagemRecebida(SortOrder.ascending);
		}
	}	
	
	public void ordenarUnidadeRemetenteMensagemRecebida(){
		this.setAssuntoMensagemRecebida(SortOrder.unsorted);
		this.setRemetenteMensagemRecebida(SortOrder.unsorted);
		this.setDataMensagemRecebida(SortOrder.unsorted);
		this.setBlocoRemetenteMensagemRecebida(SortOrder.unsorted);
		this.setVisualizadaMensagemRecebida(SortOrder.unsorted);
		if (this.unidadeRemetenteMensagemRecebida.equals(SortOrder.ascending)) {
			 this.setUnidadeRemetenteMensagemRecebida(SortOrder.descending);
		} else {
			 this.setUnidadeRemetenteMensagemRecebida(SortOrder.ascending);
		}
	}

	public SortOrder getDataMensagemRecebida() {
		return dataMensagemRecebida;
	}

	public void setDataMensagemRecebida(SortOrder dataMensagemRecebida) {
		this.dataMensagemRecebida = dataMensagemRecebida;
	}

	public SortOrder getRemetenteMensagemRecebida() {
		return remetenteMensagemRecebida;
	}

	public void setRemetenteMensagemRecebida(SortOrder remetenteMensagemRecebida) {
		this.remetenteMensagemRecebida = remetenteMensagemRecebida;
	}

	public SortOrder getAssuntoMensagemRecebida() {
		return assuntoMensagemRecebida;
	}

	public void setAssuntoMensagemRecebida(SortOrder assuntoMensagemRecebida) {
		this.assuntoMensagemRecebida = assuntoMensagemRecebida;
	}

	public SortOrder getVisualizadaMensagemRecebida() {
		return visualizadaMensagemRecebida;
	}

	public void setVisualizadaMensagemRecebida(SortOrder visualizadaMensagemRecebida) {
		this.visualizadaMensagemRecebida = visualizadaMensagemRecebida;
	}

	public SortOrder getBlocoRemetenteMensagemRecebida() {
		return blocoRemetenteMensagemRecebida;
	}

	public void setBlocoRemetenteMensagemRecebida(
			SortOrder blocoDestinatarioMensagemRecebida) {
		this.blocoRemetenteMensagemRecebida = blocoDestinatarioMensagemRecebida;
	}

	public SortOrder getUnidadeRemetenteMensagemRecebida() {
		return unidadeRemetenteMensagemRecebida;
	}

	public void setUnidadeRemetenteMensagemRecebida(
			SortOrder unidadeDestinatarioMensagemRecebida) {
		this.unidadeRemetenteMensagemRecebida = unidadeDestinatarioMensagemRecebida;
	}
	
}