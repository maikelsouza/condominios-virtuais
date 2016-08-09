package br.com.condominiosvirtuais.controller;

import java.io.Serializable;

import org.richfaces.component.SortOrder;

/**
 * MB Criado para ordenar (asc e desc) os itens da lista de blocos. Página: formListaBlocos.xhtml
 * @author Maikel Joel de Souza
 * @since 08/11/2011
 * UC 02 - Cadastrar Condomínio 
 */
public class OrdenaBlocoMB implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private SortOrder nomeBloco = SortOrder.unsorted;
	
	private SortOrder nomeSindico = SortOrder.unsorted;
	
	private SortOrder nomeSubsindico = SortOrder.unsorted;
	
	public void ordenarPorNomeBloco(){
		this.setNomeSindico(SortOrder.unsorted);
		this.setNomeSubsindico(SortOrder.unsorted);	
		if (nomeBloco.equals(SortOrder.ascending)) {
			 this.setNomeBloco(SortOrder.descending);
		} else {
			 this.setNomeBloco(SortOrder.ascending);
		}
	}
	
	public void ordenarPorNomeSindico(){
		this.setNomeBloco(SortOrder.unsorted);	
		this.setNomeSubsindico(SortOrder.unsorted);
		if (nomeSindico.equals(SortOrder.ascending)) {
			 this.setNomeSindico(SortOrder.descending);
		} else {
			 this.setNomeSindico(SortOrder.ascending);
		}
	}
	
	public void ordenarPorNomeSubsindico(){
		this.setNomeBloco(SortOrder.unsorted);	
		this.setNomeSindico(SortOrder.unsorted);
		if (nomeSubsindico.equals(SortOrder.ascending)) {
			 this.setNomeSubsindico(SortOrder.descending);
		} else {
			 this.setNomeSubsindico(SortOrder.ascending);
		}
	}

	public SortOrder getNomeBloco() {
		return nomeBloco;
	}

	public void setNomeBloco(SortOrder nomeBloco) {
		this.nomeBloco = nomeBloco;
	}

	public SortOrder getNomeSindico() {
		return nomeSindico;
	}

	public void setNomeSindico(SortOrder nomeSindico) {
		this.nomeSindico = nomeSindico;
	}

	public SortOrder getNomeSubsindico() {
		return nomeSubsindico;
	}

	public void setNomeSubsindico(SortOrder nomeSubsindico) {
		this.nomeSubsindico = nomeSubsindico;
	}	

}
