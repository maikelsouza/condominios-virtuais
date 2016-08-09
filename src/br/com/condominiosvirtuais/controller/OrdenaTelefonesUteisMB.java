package br.com.condominiosvirtuais.controller;

import java.io.Serializable;

import org.richfaces.component.SortOrder;

public class OrdenaTelefonesUteisMB implements Serializable{
	
	
	private static final long serialVersionUID = 1L;

	private SortOrder nomeTelefonesUteis = SortOrder.unsorted;
	
	private SortOrder siteTelefonesUteis = SortOrder.unsorted;
	
	private SortOrder descricaoTelefonesUteis = SortOrder.unsorted;
	
	private SortOrder telefone1TelefonesUteis = SortOrder.unsorted;
	
	private SortOrder telefone2TelefonesUteis = SortOrder.unsorted;
	
	private SortOrder emailTelefonesUteis = SortOrder.unsorted;
	

	public void ordenarPorNome(){		
		this.setSiteTelefonesUteis(SortOrder.unsorted);
		this.setDescricaoTelefonesUteis(SortOrder.unsorted);
		this.setTelefone1TelefonesUteis(SortOrder.unsorted);
		this.setTelefone2TelefonesUteis(SortOrder.unsorted);
		this.setEmailTelefonesUteis(SortOrder.unsorted);
		if (this.nomeTelefonesUteis.equals(SortOrder.ascending)) {
			 this.setNomeTelefonesUteis(SortOrder.descending);
		} else {
			 this.setNomeTelefonesUteis(SortOrder.ascending);
		}
	}	

	public void ordenarPorSite(){
		this.setNomeTelefonesUteis(SortOrder.unsorted);
		this.setDescricaoTelefonesUteis(SortOrder.unsorted);
		this.setTelefone1TelefonesUteis(SortOrder.unsorted);
		this.setTelefone2TelefonesUteis(SortOrder.unsorted);
		this.setEmailTelefonesUteis(SortOrder.unsorted);
		if (this.siteTelefonesUteis.equals(SortOrder.ascending)) {
			 this.setSiteTelefonesUteis(SortOrder.descending);
		} else {
			 this.setSiteTelefonesUteis(SortOrder.ascending);
		}
	}
	

	public void ordenarPorDescricao(){
		this.setNomeTelefonesUteis(SortOrder.unsorted);
		this.setSiteTelefonesUteis(SortOrder.unsorted);	
		this.setTelefone1TelefonesUteis(SortOrder.unsorted);
		this.setTelefone2TelefonesUteis(SortOrder.unsorted);
		this.setEmailTelefonesUteis(SortOrder.unsorted);
		if (this.descricaoTelefonesUteis.equals(SortOrder.ascending)) {
			 this.setDescricaoTelefonesUteis(SortOrder.descending);
		} else {
			 this.setDescricaoTelefonesUteis(SortOrder.ascending);
		}
	}
	
	public void ordenarPorTelefone1(){
		this.setNomeTelefonesUteis(SortOrder.unsorted);
		this.setSiteTelefonesUteis(SortOrder.unsorted);
		this.setDescricaoTelefonesUteis(SortOrder.unsorted);
		this.setTelefone2TelefonesUteis(SortOrder.unsorted);
		this.setEmailTelefonesUteis(SortOrder.unsorted);
		if (this.telefone1TelefonesUteis.equals(SortOrder.ascending)) {
			 this.setTelefone1TelefonesUteis(SortOrder.descending);
		} else {
			 this.setTelefone1TelefonesUteis(SortOrder.ascending);
		}
	}
	
	public void ordenarPorTelefone2(){
		this.setNomeTelefonesUteis(SortOrder.unsorted);
		this.setSiteTelefonesUteis(SortOrder.unsorted);	
		this.setTelefone1TelefonesUteis(SortOrder.unsorted);
		this.setDescricaoTelefonesUteis(SortOrder.unsorted);
		this.setEmailTelefonesUteis(SortOrder.unsorted);
		if (this.telefone2TelefonesUteis.equals(SortOrder.ascending)) {
			 this.setTelefone2TelefonesUteis(SortOrder.descending);
		} else {
			 this.setTelefone2TelefonesUteis(SortOrder.ascending);
		}
	}
	
	public void ordenarPorEmail(){
		this.setNomeTelefonesUteis(SortOrder.unsorted);
		this.setSiteTelefonesUteis(SortOrder.unsorted);	
		this.setTelefone1TelefonesUteis(SortOrder.unsorted);
		this.setTelefone2TelefonesUteis(SortOrder.unsorted);
		this.setDescricaoTelefonesUteis(SortOrder.unsorted);
		if (this.emailTelefonesUteis.equals(SortOrder.ascending)) {
			 this.setEmailTelefonesUteis(SortOrder.descending);
		} else {
			 this.setEmailTelefonesUteis(SortOrder.ascending);
		}
	}
	
	
	public SortOrder getNomeTelefonesUteis() {
		return nomeTelefonesUteis;
	}

	public void setNomeTelefonesUteis(SortOrder nomeTelefonesUteis) {
		this.nomeTelefonesUteis = nomeTelefonesUteis;
	}

	public SortOrder getSiteTelefonesUteis() {
		return siteTelefonesUteis;
	}

	public void setSiteTelefonesUteis(SortOrder siteTelefonesUteis) {
		this.siteTelefonesUteis = siteTelefonesUteis;
	}

	public SortOrder getDescricaoTelefonesUteis() {
		return descricaoTelefonesUteis;
	}

	public void setDescricaoTelefonesUteis(SortOrder descricaoTelefonesUteis) {
		this.descricaoTelefonesUteis = descricaoTelefonesUteis;
	}

	public SortOrder getTelefone1TelefonesUteis() {
		return telefone1TelefonesUteis;
	}

	public void setTelefone1TelefonesUteis(SortOrder telefone1TelefonesUteis) {
		this.telefone1TelefonesUteis = telefone1TelefonesUteis;
	}

	public SortOrder getTelefone2TelefonesUteis() {
		return telefone2TelefonesUteis;
	}

	public void setTelefone2TelefonesUteis(SortOrder telefone2TelefonesUteis) {
		this.telefone2TelefonesUteis = telefone2TelefonesUteis;
	}

	public SortOrder getEmailTelefonesUteis() {
		return emailTelefonesUteis;
	}

	public void setEmailTelefonesUteis(SortOrder emailTelefonesUteis) {
		this.emailTelefonesUteis = emailTelefonesUteis;
	}

}