package br.com.condominiosvirtuais.controller;

import java.io.Serializable;

import org.richfaces.component.SortOrder;

public class OrdenaMensagemEnviadaMB implements Serializable{
	
	
	private static final long serialVersionUID = 1L;

	private SortOrder dataMensagemEnviada = SortOrder.unsorted;
	
	private SortOrder assuntoMensagemEnviada = SortOrder.unsorted;
	
	
	public void ordenarDataMensagemEnviada(){
		this.setAssuntoMensagemEnviada(SortOrder.unsorted);
		if (this.dataMensagemEnviada.equals(SortOrder.ascending)) {
			 this.setDataMensagemEnviada(SortOrder.descending);
		} else {
			 this.setDataMensagemEnviada(SortOrder.ascending);
		}
	}
	
	public void ordenarAssuntoMensagemEnviada(){
		this.setDataMensagemEnviada(SortOrder.unsorted);
		if (this.assuntoMensagemEnviada.equals(SortOrder.ascending)) {
			 this.setAssuntoMensagemEnviada(SortOrder.descending);
		} else {
			 this.setAssuntoMensagemEnviada(SortOrder.ascending);
		}
	}	

	public SortOrder getDataMensagemEnviada() {
		return dataMensagemEnviada;
	}

	public void setDataMensagemEnviada(SortOrder dataMensagemEnviada) {
		this.dataMensagemEnviada = dataMensagemEnviada;
	}

	public SortOrder getAssuntoMensagemEnviada() {
		return assuntoMensagemEnviada;
	}

	public void setAssuntoMensagemEnviada(SortOrder assuntoMensagemEnviada) {
		this.assuntoMensagemEnviada= assuntoMensagemEnviada;
	}
	
}