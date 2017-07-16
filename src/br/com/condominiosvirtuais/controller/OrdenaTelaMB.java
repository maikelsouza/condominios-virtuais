package br.com.condominiosvirtuais.controller;

import java.io.Serializable;

import org.richfaces.component.SortOrder;

/**
 * MB Criado para ordenar (asc e desc) as telas do sistema. Página: formCadastroGrupoUsuario.xhtml
 * @author Maikel Joel de Souza
 * @since 11/07/2017  
 */
public class OrdenaTelaMB implements Serializable {

	private static final long serialVersionUID = 1L;

	private SortOrder nomeTela = SortOrder.unsorted;
	
	private SortOrder descricaoTela = SortOrder.unsorted;
	
		
	public void ordenarPorNome(){
		this.setDescricaoTela(SortOrder.unsorted);
		if (this.nomeTela.equals(SortOrder.ascending)) {
			 this.setNomeTela(SortOrder.descending);
		} else {
			 this.setNomeTela(SortOrder.ascending);
		}
	}
	
	public void ordenarPorDescricao(){
		this.setNomeTela(SortOrder.unsorted);
		if (this.descricaoTela.equals(SortOrder.ascending)) {
			 this.setDescricaoTela(SortOrder.descending);
		} else {
			 this.setDescricaoTela(SortOrder.ascending);
		}
	}
	
	
	

	public SortOrder getNomeTela() {
		return nomeTela;
	}

	public void setNomeTela(SortOrder nomeTela) {
		this.nomeTela = nomeTela;
	}

	public SortOrder getDescricaoTela() {
		return descricaoTela;
	}

	public void setDescricaoTela(SortOrder descricaoTela) {
		this.descricaoTela = descricaoTela;
	}
	
	

}
