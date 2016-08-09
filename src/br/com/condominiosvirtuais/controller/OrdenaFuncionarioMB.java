package br.com.condominiosvirtuais.controller;

import java.io.Serializable;

import org.richfaces.component.SortOrder;

/**
 * MB Criado para ordenar (asc e desc) os itens da lista de funcionários. Página: formListaFuncionarioCondominio.xhtml
 * @author Maikel Joel de Souza
 * @since 25/08/2012  
 */
public class OrdenaFuncionarioMB implements Serializable{
	
	
	private static final long serialVersionUID = 1L;

	private SortOrder nomeFuncionario = SortOrder.unsorted;
	
	private SortOrder funcaoFuncionario = SortOrder.unsorted;
	
	
	public void ordenarPorNomeFuncionario(){		
		this.setFuncaoFuncionario(SortOrder.unsorted);		
		if (nomeFuncionario.equals(SortOrder.ascending)) {
			 this.setNomeFuncionario(SortOrder.descending);
		} else {
			 this.setNomeFuncionario(SortOrder.ascending);
		}
	}
	
	public void ordenarPorFuncaoFuncionario(){		
		this.setNomeFuncionario(SortOrder.unsorted);		
		if (funcaoFuncionario.equals(SortOrder.ascending)) {
			 this.setFuncaoFuncionario(SortOrder.descending);
		} else {
			 this.setFuncaoFuncionario(SortOrder.ascending);
		}
	}

	public SortOrder getNomeFuncionario() {
		return nomeFuncionario;
	}

	public void setNomeFuncionario(SortOrder nomeFuncionario) {
		this.nomeFuncionario = nomeFuncionario;
	}

	public SortOrder getFuncaoFuncionario() {
		return funcaoFuncionario;
	}

	public void setFuncaoFuncionario(SortOrder funcaoFuncionario) {
		this.funcaoFuncionario = funcaoFuncionario;
	}	

}