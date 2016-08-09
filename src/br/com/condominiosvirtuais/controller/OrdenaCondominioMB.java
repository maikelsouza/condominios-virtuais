package br.com.condominiosvirtuais.controller;

import java.io.Serializable;

import org.richfaces.component.SortOrder;

/**
 * MB Criado para ordenar (asc e desc) os itens da lista de condomínios. Página: formListaCondominio.xhtml
 * @author Maikel Joel de Souza
 * @since 12/10/2011
 * UC 02 - Cadastrar Condomínio 
 */
public class OrdenaCondominioMB implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private SortOrder nomeCondominio = SortOrder.unsorted;
	
	private SortOrder nomeSindicoGeral = SortOrder.unsorted;
	
	private SortOrder situacaoCondominio = SortOrder.unsorted;
	
	

	public void ordenarPorNome(){
		this.setNomeSindicoGeral(SortOrder.unsorted);	
		this.setSituacaoCondominio(SortOrder.unsorted);
		if (nomeCondominio.equals(SortOrder.ascending)) {
			 this.setNomeCondominio(SortOrder.descending);
		} else {
			 this.setNomeCondominio(SortOrder.ascending);
		}
	}
	
	public void ordenarPorNomeSindicoGeral(){
		this.setNomeCondominio(SortOrder.unsorted);	
		this.setSituacaoCondominio(SortOrder.unsorted);
		if (nomeSindicoGeral.equals(SortOrder.ascending)) {
			 this.setNomeSindicoGeral(SortOrder.descending);
		} else {
			 this.setNomeSindicoGeral(SortOrder.ascending);
		}
	}
	
	public void ordenarPorSituacaoCondominio(){
		this.setNomeCondominio(SortOrder.unsorted);	
		this.setNomeSindicoGeral(SortOrder.unsorted);
		if (situacaoCondominio.equals(SortOrder.ascending)) {
			 this.setSituacaoCondominio(SortOrder.descending);
		} else {
			 this.setSituacaoCondominio(SortOrder.ascending);
		}
	}

	public SortOrder getNomeCondominio() {
		return nomeCondominio;
	}

	public void setNomeCondominio(SortOrder nomeCondominio) {
		this.nomeCondominio = nomeCondominio;
	}

	public SortOrder getNomeSindicoGeral() {
		return nomeSindicoGeral;
	}

	public void setNomeSindicoGeral(SortOrder nomeSindicoGeral) {
		this.nomeSindicoGeral = nomeSindicoGeral;
	}
	
	public SortOrder getSituacaoCondominio() {
		return situacaoCondominio;
	}

	public void setSituacaoCondominio(SortOrder situacaoCondominio) {
		this.situacaoCondominio = situacaoCondominio;
	}

}
