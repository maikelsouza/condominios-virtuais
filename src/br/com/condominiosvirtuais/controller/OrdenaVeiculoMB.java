package br.com.condominiosvirtuais.controller;

import java.io.Serializable;

import org.richfaces.component.SortOrder;

/**
 * MB Criado para ordenar (asc e desc) os itens da lista veículos. Página:
 * formMeuPainel.xhtml.
 * 
 * @author Maikel Joel de Souza
 * @since 14/01/2014
 */

public class OrdenaVeiculoMB implements Serializable{

	private static final long serialVersionUID = 1L;

	private SortOrder nomeVeiculo = SortOrder.unsorted;
	
	private SortOrder placaVeiculo = SortOrder.unsorted;
	
	private SortOrder tipoVeiculo = SortOrder.unsorted;
	
	private SortOrder garagemVeiculo = SortOrder.unsorted;
	
	private SortOrder nomeCondominoVeiculo= SortOrder.unsorted;
	
	private SortOrder blocoCondominoVeiculo = SortOrder.unsorted;
	
	private SortOrder unidadeCondominoVeiculo= SortOrder.unsorted;


	public void ordenarPorNomeVeiculo() {
		this.setPlacaVeiculo(SortOrder.unsorted);
		this.setTipoVeiculo(SortOrder.unsorted);
		this.setGaragemVeiculo(SortOrder.unsorted);		
		this.setNomeCondominoVeiculo(SortOrder.unsorted);
		this.setUnidadeCondominoVeiculo(SortOrder.unsorted);
		this.setBlocoCondominoVeiculo(SortOrder.unsorted);
		if (this.nomeVeiculo.equals(SortOrder.ascending)) {
			this.setNomeVeiculo(SortOrder.descending);
		} else {
			this.setNomeVeiculo(SortOrder.ascending);
		}
	}
	
	public void ordenarPorPlacaVeiculo() {
		this.setNomeVeiculo(SortOrder.unsorted);
		this.setTipoVeiculo(SortOrder.unsorted);
		this.setGaragemVeiculo(SortOrder.unsorted);	
		this.setNomeCondominoVeiculo(SortOrder.unsorted);
		this.setUnidadeCondominoVeiculo(SortOrder.unsorted);
		this.setBlocoCondominoVeiculo(SortOrder.unsorted);
		if (this.placaVeiculo.equals(SortOrder.ascending)) {
			this.setPlacaVeiculo(SortOrder.descending);
		} else {
			this.setPlacaVeiculo(SortOrder.ascending);
		}
	}
	
	public void ordenarPorTipoVeiculo() {
		this.setPlacaVeiculo(SortOrder.unsorted);
		this.setNomeVeiculo(SortOrder.unsorted);
		this.setGaragemVeiculo(SortOrder.unsorted);
		this.setNomeCondominoVeiculo(SortOrder.unsorted);
		this.setUnidadeCondominoVeiculo(SortOrder.unsorted);
		this.setBlocoCondominoVeiculo(SortOrder.unsorted);
		if (this.tipoVeiculo.equals(SortOrder.ascending)) {
			this.setTipoVeiculo(SortOrder.descending);
		} else {
			this.setTipoVeiculo(SortOrder.ascending);
		}
	}
	
	public void ordenarPorGaragemVeiculo() {
		this.setPlacaVeiculo(SortOrder.unsorted);
		this.setTipoVeiculo(SortOrder.unsorted);
		this.setNomeVeiculo(SortOrder.unsorted);
		this.setNomeCondominoVeiculo(SortOrder.unsorted);
		this.setUnidadeCondominoVeiculo(SortOrder.unsorted);
		this.setBlocoCondominoVeiculo(SortOrder.unsorted);
		if (this.garagemVeiculo.equals(SortOrder.ascending)) {
			this.setGaragemVeiculo(SortOrder.descending);
		} else {
			this.setGaragemVeiculo(SortOrder.ascending);
		}
	}
	
	public void ordenarPorNomeCondominoVeiculo() {
		this.setPlacaVeiculo(SortOrder.unsorted);
		this.setTipoVeiculo(SortOrder.unsorted);
		this.setNomeVeiculo(SortOrder.unsorted);
		this.setGaragemVeiculo(SortOrder.unsorted);
		this.setUnidadeCondominoVeiculo(SortOrder.unsorted);
		this.setBlocoCondominoVeiculo(SortOrder.unsorted);
		if (this.nomeCondominoVeiculo.equals(SortOrder.ascending)) {
			this.setNomeCondominoVeiculo(SortOrder.descending);
		} else {
			this.setNomeCondominoVeiculo(SortOrder.ascending);
		}
	}

	public void ordenarPorUnidadeCondominoVeiculo() {
		this.setPlacaVeiculo(SortOrder.unsorted);
		this.setTipoVeiculo(SortOrder.unsorted);
		this.setNomeVeiculo(SortOrder.unsorted);
		this.setGaragemVeiculo(SortOrder.unsorted);
		this.setNomeCondominoVeiculo(SortOrder.unsorted);
		this.setBlocoCondominoVeiculo(SortOrder.unsorted);
		if (this.unidadeCondominoVeiculo.equals(SortOrder.ascending)) {
			this.setUnidadeCondominoVeiculo(SortOrder.descending);
		} else {
			this.setUnidadeCondominoVeiculo(SortOrder.ascending);
		}
	}

	public void ordenarPorBlocoCondominoVeiculo() {
		this.setPlacaVeiculo(SortOrder.unsorted);
		this.setTipoVeiculo(SortOrder.unsorted);
		this.setNomeVeiculo(SortOrder.unsorted);
		this.setGaragemVeiculo(SortOrder.unsorted);
		this.setNomeCondominoVeiculo(SortOrder.unsorted);
		this.setUnidadeCondominoVeiculo(SortOrder.unsorted);
		if (this.blocoCondominoVeiculo.equals(SortOrder.ascending)) {
			this.setBlocoCondominoVeiculo(SortOrder.descending);
		} else {
			this.setBlocoCondominoVeiculo(SortOrder.ascending);
		}
	}
	
	public SortOrder getNomeVeiculo() {
		return this.nomeVeiculo;
	}

	public void setNomeVeiculo(SortOrder nomeVeiculo) {
		this.nomeVeiculo = nomeVeiculo;
	}

	public SortOrder getPlacaVeiculo() {
		return placaVeiculo;
	}

	public void setPlacaVeiculo(SortOrder placaVeiculo) {
		this.placaVeiculo = placaVeiculo;
	}

	public SortOrder getTipoVeiculo() {
		return tipoVeiculo;
	}

	public void setTipoVeiculo(SortOrder tipoVeiculo) {
		this.tipoVeiculo = tipoVeiculo;
	}

	public SortOrder getGaragemVeiculo() {
		return garagemVeiculo;
	}

	public void setGaragemVeiculo(SortOrder garagemVeiculo) {
		this.garagemVeiculo = garagemVeiculo;
	}

	public SortOrder getNomeCondominoVeiculo() {
		return nomeCondominoVeiculo;
	}

	public void setNomeCondominoVeiculo(SortOrder nomeCondominoVeiculo) {
		this.nomeCondominoVeiculo = nomeCondominoVeiculo;
	}	
	

	public SortOrder getBlocoCondominoVeiculo() {
		return blocoCondominoVeiculo;
	}

	public SortOrder getUnidadeCondominoVeiculo() {
		return unidadeCondominoVeiculo;
	}

	public void setBlocoCondominoVeiculo(SortOrder blocoCondominoVeiculo) {
		this.blocoCondominoVeiculo = blocoCondominoVeiculo;
	}

	public void setUnidadeCondominoVeiculo(SortOrder unidadeCondominoVeiculo) {
		this.unidadeCondominoVeiculo = unidadeCondominoVeiculo;
	}	

}