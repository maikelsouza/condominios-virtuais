package br.com.condominiosvirtuais.controller;

import java.io.Serializable;

import org.richfaces.component.SortOrder;

/**
 * MB Criado para ordenar (asc e desc) os grupos de usuários. Páginas: formListaGrupoUsuario.xhtml
 * @author Maikel Joel de Souza
 * @since 08/06/2017  
 */
public class OrdenaGrupoUsuarioMB implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private SortOrder nomeGrupoUsuario = SortOrder.unsorted;
	
	private SortOrder descricaoGrupoUsuario = SortOrder.unsorted;
	
	private SortOrder situacaoGrupoUsuario = SortOrder.unsorted;
		
		
	public void ordenarPorNomeGrupoUsuario(){		
		this.setDescricaoGrupoUsuario(SortOrder.unsorted);
		this.setSituacaoGrupoUsuario(SortOrder.unsorted);
		if (this.nomeGrupoUsuario.equals(SortOrder.ascending)) {
			 this.setNomeGrupoUsuario(SortOrder.descending);
		} else {
			 this.setNomeGrupoUsuario(SortOrder.ascending);
		}
	}
	
	public void ordenarPorDescricaoGrupoUsuario(){		
		this.setNomeGrupoUsuario(SortOrder.unsorted);
		this.setSituacaoGrupoUsuario(SortOrder.unsorted);
		if (this.descricaoGrupoUsuario.equals(SortOrder.ascending)) {
			 this.setDescricaoGrupoUsuario(SortOrder.descending);
		} else {
			 this.setDescricaoGrupoUsuario(SortOrder.ascending);
		}
	}
	
	public void ordenarPorSituacaoGrupoUsuario(){
		this.setNomeGrupoUsuario(SortOrder.unsorted);
		this.setDescricaoGrupoUsuario(SortOrder.unsorted);
		if (this.situacaoGrupoUsuario.equals(SortOrder.ascending)) {
			 this.setSituacaoGrupoUsuario(SortOrder.descending);
		} else {
			 this.setSituacaoGrupoUsuario(SortOrder.ascending);
		}	
	}

	public SortOrder getNomeGrupoUsuario() {
		return nomeGrupoUsuario;
	}

	public void setNomeGrupoUsuario(SortOrder nomeGrupoUsuario) {
		this.nomeGrupoUsuario = nomeGrupoUsuario;
	}

	public SortOrder getDescricaoGrupoUsuario() {
		return descricaoGrupoUsuario;
	}

	public void setDescricaoGrupoUsuario(SortOrder descricaoGrupoUsuario) {
		this.descricaoGrupoUsuario = descricaoGrupoUsuario;
	}

	public SortOrder getSituacaoGrupoUsuario() {
		return situacaoGrupoUsuario;
	}

	public void setSituacaoGrupoUsuario(SortOrder situacaoGrupoUsuario) {
		this.situacaoGrupoUsuario = situacaoGrupoUsuario;
	}
		

}
