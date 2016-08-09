package br.com.condominiosvirtuais.controller;

import java.io.Serializable;

import org.richfaces.component.SortOrder;

/**
 * MB Criado para ordenar (asc e desc) os itens da lista de condomínios. Página: formListaChamado.xhtml
 * @author Maikel Joel de Souza
 * @since 27/07/2016
 *    
 */
public class OrdenaChamadoMB implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private SortOrder nomeBloco = SortOrder.unsorted;
	
	private SortOrder numeroUnidade = SortOrder.unsorted;
	
	private SortOrder nomeUsuario = SortOrder.unsorted;
	
	private SortOrder nomeChamado = SortOrder.unsorted;		

	private SortOrder tipoChamado = SortOrder.unsorted;
	
	private SortOrder statusChamado = SortOrder.unsorted;
	
	private SortOrder descricaoChamado = SortOrder.unsorted;
	
	private SortOrder comentarioChamado = SortOrder.unsorted;
	
	private SortOrder dataAberturaChamado = SortOrder.unsorted;
	
	private SortOrder dataFechamentoChamado = SortOrder.unsorted;
	
	
	

	public void ordenarPorNomeBloco(){			
		this.setNumeroUnidade(SortOrder.unsorted);
		this.setNomeChamado(SortOrder.unsorted);
		this.setTipoChamado(SortOrder.unsorted);
		this.setStatusChamado(SortOrder.unsorted);
		this.setDescricaoChamado(SortOrder.unsorted);
		this.setDataAberturaChamado(SortOrder.unsorted);
		this.setDataFechamentoChamado(SortOrder.unsorted);
		this.setNomeUsuario(SortOrder.unsorted);
		this.setComentarioChamado(SortOrder.unsorted);
		if (this.nomeBloco.equals(SortOrder.ascending)) {
			 this.setNomeBloco(SortOrder.descending);
		} else {
			 this.setNomeBloco(SortOrder.ascending);
		}
	}
	
	public void ordenarPorNumeroUnidade(){			
		this.setNomeBloco(SortOrder.unsorted);
		this.setNomeChamado(SortOrder.unsorted);
		this.setTipoChamado(SortOrder.unsorted);
		this.setStatusChamado(SortOrder.unsorted);
		this.setDescricaoChamado(SortOrder.unsorted);
		this.setDataAberturaChamado(SortOrder.unsorted);
		this.setDataFechamentoChamado(SortOrder.unsorted);
		this.setNomeUsuario(SortOrder.unsorted);
		this.setComentarioChamado(SortOrder.unsorted);
		if (this.numeroUnidade.equals(SortOrder.ascending)) {
			this.setNumeroUnidade(SortOrder.descending);
		} else {
			this.setNumeroUnidade(SortOrder.ascending);
		}
	}
	
	public void ordenarPorNomeUsuario(){			
		this.setNomeBloco(SortOrder.unsorted);
		this.setNomeChamado(SortOrder.unsorted);
		this.setTipoChamado(SortOrder.unsorted);
		this.setNumeroUnidade(SortOrder.unsorted);
		this.setStatusChamado(SortOrder.unsorted);
		this.setDescricaoChamado(SortOrder.unsorted);
		this.setDataAberturaChamado(SortOrder.unsorted);
		this.setDataFechamentoChamado(SortOrder.unsorted);
		this.setComentarioChamado(SortOrder.unsorted);
		if (this.nomeUsuario.equals(SortOrder.ascending)) {
			this.setNomeUsuario(SortOrder.descending);
		} else {
			this.setNomeUsuario(SortOrder.ascending);
		}
	}
	
	public void ordenarPorNomeChamado(){			
		this.setNomeBloco(SortOrder.unsorted);
		this.setNumeroUnidade(SortOrder.unsorted);
		this.setTipoChamado(SortOrder.unsorted);
		this.setStatusChamado(SortOrder.unsorted);
		this.setDescricaoChamado(SortOrder.unsorted);
		this.setDataAberturaChamado(SortOrder.unsorted);
		this.setDataFechamentoChamado(SortOrder.unsorted);
		this.setNomeUsuario(SortOrder.unsorted);
		this.setComentarioChamado(SortOrder.unsorted);
		if (this.nomeChamado.equals(SortOrder.ascending)) {
			this.setNomeChamado(SortOrder.descending);
		} else {
			this.setNomeChamado(SortOrder.ascending);
		}
	}
	
	public void ordenarPorTipoChamado(){			
		this.setNomeBloco(SortOrder.unsorted);
		this.setNumeroUnidade(SortOrder.unsorted);
		this.setNomeChamado(SortOrder.unsorted);
		this.setStatusChamado(SortOrder.unsorted);
		this.setDescricaoChamado(SortOrder.unsorted);
		this.setDataAberturaChamado(SortOrder.unsorted);
		this.setDataFechamentoChamado(SortOrder.unsorted);
		this.setNomeUsuario(SortOrder.unsorted);
		this.setComentarioChamado(SortOrder.unsorted);
		if (this.tipoChamado.equals(SortOrder.ascending)) {
			this.setTipoChamado(SortOrder.descending);
		} else {
			this.setTipoChamado(SortOrder.ascending);
		}
	}
	
	public void ordenarPorStatusChamado(){			
		this.setNomeBloco(SortOrder.unsorted);
		this.setNumeroUnidade(SortOrder.unsorted);
		this.setNomeChamado(SortOrder.unsorted);
		this.setTipoChamado(SortOrder.unsorted);
		this.setDescricaoChamado(SortOrder.unsorted);
		this.setDataAberturaChamado(SortOrder.unsorted);
		this.setDataFechamentoChamado(SortOrder.unsorted);
		this.setNomeUsuario(SortOrder.unsorted);
		this.setComentarioChamado(SortOrder.unsorted);
		if (this.statusChamado.equals(SortOrder.ascending)) {
			this.setStatusChamado(SortOrder.descending);
		} else {
			this.setStatusChamado(SortOrder.ascending);
		}
	}
	
	public void ordenarPorDescricaoChamado(){			
		this.setNomeBloco(SortOrder.unsorted);
		this.setNumeroUnidade(SortOrder.unsorted);
		this.setNomeChamado(SortOrder.unsorted);
		this.setTipoChamado(SortOrder.unsorted);
		this.setStatusChamado(SortOrder.unsorted);
		this.setDataAberturaChamado(SortOrder.unsorted);
		this.setDataFechamentoChamado(SortOrder.unsorted);
		this.setNomeUsuario(SortOrder.unsorted);
		this.setComentarioChamado(SortOrder.unsorted);
		if (this.descricaoChamado.equals(SortOrder.ascending)) {
			this.setDescricaoChamado(SortOrder.descending);
		} else {
			this.setDescricaoChamado(SortOrder.ascending);
		}
	}
	
	public void ordenarPorDataAberturaChamado(){			
		this.setNomeBloco(SortOrder.unsorted);
		this.setNumeroUnidade(SortOrder.unsorted);
		this.setNomeChamado(SortOrder.unsorted);
		this.setTipoChamado(SortOrder.unsorted);
		this.setStatusChamado(SortOrder.unsorted);
		this.setDescricaoChamado(SortOrder.unsorted);
		this.setDataFechamentoChamado(SortOrder.unsorted);
		this.setNomeUsuario(SortOrder.unsorted);
		this.setComentarioChamado(SortOrder.unsorted);
		if (this.dataAberturaChamado.equals(SortOrder.ascending)) {
			this.setDataAberturaChamado(SortOrder.descending);
		} else {
			this.setDataAberturaChamado(SortOrder.ascending);
		}
	}
	
	public void ordenarPorDataFechamentoChamado(){			
		this.setNomeBloco(SortOrder.unsorted);
		this.setNumeroUnidade(SortOrder.unsorted);
		this.setNomeChamado(SortOrder.unsorted);
		this.setTipoChamado(SortOrder.unsorted);
		this.setStatusChamado(SortOrder.unsorted);
		this.setDescricaoChamado(SortOrder.unsorted);
		this.setDataFechamentoChamado(SortOrder.unsorted);
		this.setNomeUsuario(SortOrder.unsorted);
		this.setComentarioChamado(SortOrder.unsorted);
		if (this.dataFechamentoChamado.equals(SortOrder.ascending)) {
			this.setDataFechamentoChamado(SortOrder.descending);
		} else {
			this.setDataFechamentoChamado(SortOrder.ascending);
		}
	}
	
	public void ordenarPorComentarioChamado(){			
		this.setNomeBloco(SortOrder.unsorted);
		this.setNumeroUnidade(SortOrder.unsorted);
		this.setNomeChamado(SortOrder.unsorted);
		this.setTipoChamado(SortOrder.unsorted);
		this.setStatusChamado(SortOrder.unsorted);
		this.setDescricaoChamado(SortOrder.unsorted);
		this.setDataFechamentoChamado(SortOrder.unsorted);
		this.setNomeUsuario(SortOrder.unsorted);
		this.setDataFechamentoChamado(SortOrder.unsorted);
		if (this.comentarioChamado.equals(SortOrder.ascending)) {
			this.setComentarioChamado(SortOrder.descending);
		} else {
			this.setComentarioChamado(SortOrder.ascending);
		}
	}

	public SortOrder getNomeBloco() {
		return nomeBloco;
	}

	public void setNomeBloco(SortOrder nomeBloco) {
		this.nomeBloco = nomeBloco;
	}

	public SortOrder getNumeroUnidade() {
		return numeroUnidade;
	}

	public void setNumeroUnidade(SortOrder numeroUnidade) {
		this.numeroUnidade = numeroUnidade;
	}

	public SortOrder getNomeChamado() {
		return nomeChamado;
	}

	public void setNomeChamado(SortOrder nomeChamado) {
		this.nomeChamado = nomeChamado;
	}

	public SortOrder getTipoChamado() {
		return tipoChamado;
	}

	public void setTipoChamado(SortOrder tipoChamado) {
		this.tipoChamado = tipoChamado;
	}
	
	public SortOrder getStatusChamado() {
		return statusChamado;
	}
	
	public void setStatusChamado(SortOrder statusChamado) {
		this.statusChamado = statusChamado;
	}

	public SortOrder getDescricaoChamado() {
		return descricaoChamado;
	}

	public void setDescricaoChamado(SortOrder descricaoChamado) {
		this.descricaoChamado = descricaoChamado;
	}

	public SortOrder getDataAberturaChamado() {
		return dataAberturaChamado;
	}

	public void setDataAberturaChamado(SortOrder dataAberturaChamado) {
		this.dataAberturaChamado = dataAberturaChamado;
	}

	public SortOrder getDataFechamentoChamado() {
		return dataFechamentoChamado;
	}

	public void setDataFechamentoChamado(SortOrder dataFechamentoChamado) {
		this.dataFechamentoChamado = dataFechamentoChamado;
	}
	
	public SortOrder getNomeUsuario() {
		return nomeUsuario;
	}

	public void setNomeUsuario(SortOrder nomeUsuario) {
		this.nomeUsuario = nomeUsuario;
	}

	public SortOrder getComentarioChamado() {
		return comentarioChamado;
	}

	public void setComentarioChamado(SortOrder comentarioChamado) {
		this.comentarioChamado = comentarioChamado;
	}
	

}
