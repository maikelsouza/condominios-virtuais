package br.com.condominiosvirtuais.controller;

import java.io.Serializable;

import org.richfaces.component.SortOrder;

/**
 * MB Criado para ordenar (asc e desc) os itens da lista de Boletos. Página: formListaPreCadastroBoleto.xhtml
 * @author Maikel Joel de Souza
 * @since 21/02/2017  
 */
public class OrdenaPreCadastroBoletoMB implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private SortOrder nomeBanco = SortOrder.unsorted;
	
	private SortOrder nomeBeneficiario = SortOrder.unsorted;
	
	private SortOrder diaMesVencimento = SortOrder.unsorted;
	
	private SortOrder principal = SortOrder.unsorted;
	
	
	
	public void ordenarPorNomeBanco(){
		this.setNomeBeneficiario(SortOrder.unsorted);			
		this.setDiaMesVencimento(SortOrder.unsorted);
		this.setPrincipal(SortOrder.unsorted);
		if (this.nomeBanco.equals(SortOrder.ascending)) {
			 this.setNomeBanco(SortOrder.descending);
		} else {
			 this.setNomeBanco(SortOrder.ascending);
		}
	}
	
	public void ordenarPorNomeBeneficiario(){
		this.setNomeBanco(SortOrder.unsorted);			
		this.setDiaMesVencimento(SortOrder.unsorted);
		this.setPrincipal(SortOrder.unsorted);
		if (this.nomeBeneficiario.equals(SortOrder.ascending)) {
			 this.setNomeBeneficiario(SortOrder.descending);
		} else {
			 this.setNomeBeneficiario(SortOrder.ascending);
		}
	}	
	
	public void ordenarPorDiaMesVencimento(){		
		this.setNomeBanco(SortOrder.unsorted);
		this.setNomeBeneficiario(SortOrder.unsorted);
		this.setPrincipal(SortOrder.unsorted);
		if (this.diaMesVencimento.equals(SortOrder.ascending)) {
			this.setDiaMesVencimento(SortOrder.descending);
		} else {
			this.setDiaMesVencimento(SortOrder.ascending);
		}
	}
	
	public void ordenarPorPrincipal(){		
		this.setNomeBanco(SortOrder.unsorted);
		this.setNomeBeneficiario(SortOrder.unsorted);
		this.setDiaMesVencimento(SortOrder.unsorted);
		if (this.principal.equals(SortOrder.ascending)) {
			this.setPrincipal(SortOrder.descending);
		} else {
			this.setPrincipal(SortOrder.ascending);
		}
	}

	public SortOrder getNomeBanco() {
		return nomeBanco;
	}

	public void setNomeBanco(SortOrder nomeBanco) {
		this.nomeBanco = nomeBanco;
	}

	public SortOrder getNomeBeneficiario() {
		return nomeBeneficiario;
	}

	public void setNomeBeneficiario(SortOrder nomeBeneficiario) {
		this.nomeBeneficiario = nomeBeneficiario;
	}

	public SortOrder getDiaMesVencimento() {
		return diaMesVencimento;
	}

	public void setDiaMesVencimento(SortOrder diaMesVencimento) {
		this.diaMesVencimento = diaMesVencimento;
	}

	public SortOrder getPrincipal() {
		return principal;
	}

	public void setPrincipal(SortOrder principal) {
		this.principal = principal;
	}	

}
