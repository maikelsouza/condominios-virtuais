package br.com.condominiosvirtuais.controller;

import java.io.Serializable;

import org.richfaces.component.SortOrder;

public class OrdenaContadorMB implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private SortOrder nomeContador = SortOrder.unsorted;
	
	private SortOrder emailContador = SortOrder.unsorted;
	
	private SortOrder situacaoContador = SortOrder.unsorted;
	
	private SortOrder sexoContador = SortOrder.unsorted;
	
	private SortOrder dataNascimentoContador = SortOrder.unsorted;
	
	
	public void ordenarPorNomeContador(){
		this.setEmailContador(SortOrder.unsorted);
		this.setSituacaoContador(SortOrder.unsorted);
		this.setSexoContador(SortOrder.unsorted);
		this.setDataNascimentoContador(SortOrder.unsorted);
		if (this.nomeContador.equals(SortOrder.ascending)) {
			 this.setNomeContador(SortOrder.descending);
		} else {
			 this.setNomeContador(SortOrder.ascending);
		}
	}
	
	public void ordenarPorEmailContador(){
		this.setNomeContador(SortOrder.unsorted);
		this.setSituacaoContador(SortOrder.unsorted);
		this.setSexoContador(SortOrder.unsorted);
		this.setDataNascimentoContador(SortOrder.unsorted);
		if (this.emailContador.equals(SortOrder.ascending)) {
			 this.setEmailContador(SortOrder.descending);
		} else {
			 this.setEmailContador(SortOrder.ascending);
		}
	}
	
	public void ordenarPorSituacaoContador(){
		this.setNomeContador(SortOrder.unsorted);
		this.setEmailContador(SortOrder.unsorted);
		this.setSexoContador(SortOrder.unsorted);
		this.setDataNascimentoContador(SortOrder.unsorted);		
		if (this.situacaoContador.equals(SortOrder.ascending)) {
			this.setSituacaoContador(SortOrder.descending);
		} else {
			this.setSituacaoContador(SortOrder.ascending);
		}
	}
	
	public void ordenarPorSexoContador(){
		this.setNomeContador(SortOrder.unsorted);
		this.setEmailContador(SortOrder.unsorted);
		this.setDataNascimentoContador(SortOrder.unsorted);	
		this.setSituacaoContador(SortOrder.unsorted);
		if (this.sexoContador.equals(SortOrder.ascending)) {
			this.setSexoContador(SortOrder.descending);
		} else {
			this.setSexoContador(SortOrder.ascending);
		}
	}
	
	public void ordenarPorDataNascimentoContador(){
		this.setNomeContador(SortOrder.unsorted);
		this.setEmailContador(SortOrder.unsorted);
		this.setSexoContador(SortOrder.unsorted);
		this.setSituacaoContador(SortOrder.unsorted);
		if (this.dataNascimentoContador.equals(SortOrder.ascending)) {
			this.setDataNascimentoContador(SortOrder.descending);
		} else {
			this.setDataNascimentoContador(SortOrder.ascending);
		}
	}

	public SortOrder getNomeContador() {
		return nomeContador;
	}

	public void setNomeContador(SortOrder nomeContador) {
		this.nomeContador = nomeContador;
	}

	public SortOrder getEmailContador() {
		return emailContador;
	}

	public void setEmailContador(SortOrder emailContador) {
		this.emailContador = emailContador;
	}

	public SortOrder getSituacaoContador() {
		return situacaoContador;
	}

	public void setSituacaoContador(SortOrder situacaoContador) {
		this.situacaoContador = situacaoContador;
	}

	public SortOrder getSexoContador() {
		return sexoContador;
	}

	public void setSexoContador(SortOrder sexoContador) {
		this.sexoContador = sexoContador;
	}

	public SortOrder getDataNascimentoContador() {
		return dataNascimentoContador;
	}

	public void setDataNascimentoContador(SortOrder dataNascimentoContador) {
		this.dataNascimentoContador = dataNascimentoContador;
	}
	
	

	
}
