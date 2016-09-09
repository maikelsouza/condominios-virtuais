package br.com.condominiosvirtuais.controller;

import java.io.Serializable;

import org.richfaces.component.SortOrder;

/**
 * MB Criado para ordenar (asc e desc) os itens da lista de reserva. Página:
 * formListaReserva.xhtml
 * 
 * @author Maikel Joel de Souza
 * @since 28/10/2012
 */
public class OrdenaReservaMB implements Serializable {

	
	private static final long serialVersionUID = 1L;

	private SortOrder nomeAmbiente = SortOrder.unsorted;

	private SortOrder dataReserva = SortOrder.unsorted;

	private SortOrder situacao = SortOrder.unsorted;
	
	private SortOrder motivoReprovacao = SortOrder.unsorted;
	
	private SortOrder motivoSuspensao = SortOrder.unsorted;
	
	private SortOrder bloco = SortOrder.unsorted;
	
	private SortOrder unidade = SortOrder.unsorted;
	
	private SortOrder condomino = SortOrder.unsorted;
	

	public void ordenarPorNomeAmbiente() {
		this.setDataReserva(SortOrder.unsorted);
		this.setSituacao(SortOrder.unsorted);
		this.setBloco(SortOrder.unsorted);
		this.setUnidade(SortOrder.unsorted);
		this.setCondomino(SortOrder.unsorted);		
		this.setMotivoReprovacao(SortOrder.unsorted);
		this.setMotivoSuspensao(SortOrder.unsorted);
		if (nomeAmbiente.equals(SortOrder.ascending)) {
			this.setNomeAmbiente(SortOrder.descending);
		} else {
			this.setNomeAmbiente(SortOrder.ascending);
		}
	}

	public void ordenarPorDataReserva() {
		this.setNomeAmbiente(SortOrder.unsorted);
		this.setSituacao(SortOrder.unsorted);
		this.setBloco(SortOrder.unsorted);
		this.setUnidade(SortOrder.unsorted);
		this.setCondomino(SortOrder.unsorted);		
		this.setMotivoReprovacao(SortOrder.unsorted);
		this.setMotivoSuspensao(SortOrder.unsorted);
		if (dataReserva.equals(SortOrder.ascending)) {
			this.setDataReserva(SortOrder.descending);
		} else {
			this.setDataReserva(SortOrder.ascending);
		}
	}

	public void ordenarPorSituacao() {
		this.setNomeAmbiente(SortOrder.unsorted);
		this.setDataReserva(SortOrder.unsorted);
		this.setBloco(SortOrder.unsorted);
		this.setUnidade(SortOrder.unsorted);
		this.setCondomino(SortOrder.unsorted);	
		this.setMotivoReprovacao(SortOrder.unsorted);
		this.setMotivoSuspensao(SortOrder.unsorted);
		if (situacao.equals(SortOrder.ascending)) {
			this.setSituacao(SortOrder.descending);
		} else {
			this.setSituacao(SortOrder.ascending);
		}
	}
	
	public void ordenarPorBloco() {
		this.setNomeAmbiente(SortOrder.unsorted);
		this.setDataReserva(SortOrder.unsorted);
		this.setSituacao(SortOrder.unsorted);
		this.setUnidade(SortOrder.unsorted);
		this.setCondomino(SortOrder.unsorted);	
		this.setMotivoReprovacao(SortOrder.unsorted);
		this.setMotivoSuspensao(SortOrder.unsorted);
		if (bloco.equals(SortOrder.ascending)) {
			this.setBloco(SortOrder.descending);
		} else {
			this.setBloco(SortOrder.ascending);
		}
	}
	
	public void ordenarPorUnidade() {
		this.setNomeAmbiente(SortOrder.unsorted);
		this.setDataReserva(SortOrder.unsorted);
		this.setSituacao(SortOrder.unsorted);
		this.setBloco(SortOrder.unsorted);
		this.setCondomino(SortOrder.unsorted);
		this.setMotivoReprovacao(SortOrder.unsorted);
		this.setMotivoSuspensao(SortOrder.unsorted);
		if (unidade.equals(SortOrder.ascending)) {
			this.setUnidade(SortOrder.descending);
		} else {
			this.setUnidade(SortOrder.ascending);
		}
	}
	
	public void ordenarPorCondomino() {
		this.setNomeAmbiente(SortOrder.unsorted);
		this.setDataReserva(SortOrder.unsorted);
		this.setSituacao(SortOrder.unsorted);
		this.setBloco(SortOrder.unsorted);
		this.setUnidade(SortOrder.unsorted);
		this.setMotivoReprovacao(SortOrder.unsorted);
		this.setMotivoSuspensao(SortOrder.unsorted);
		if (condomino.equals(SortOrder.ascending)) {
			this.setCondomino(SortOrder.descending);
		} else {
			this.setCondomino(SortOrder.ascending);
		}
	}
	
	public void ordenarPorMotivoReprovacao() {
		this.setNomeAmbiente(SortOrder.unsorted);
		this.setDataReserva(SortOrder.unsorted);
		this.setSituacao(SortOrder.unsorted);
		this.setBloco(SortOrder.unsorted);
		this.setUnidade(SortOrder.unsorted);
		this.setMotivoSuspensao(SortOrder.unsorted);
		this.setMotivoReprovacao(SortOrder.unsorted);
		if (motivoReprovacao.equals(SortOrder.ascending)) {
			this.setMotivoReprovacao(SortOrder.descending);
		} else {
			this.setMotivoReprovacao(SortOrder.ascending);
		}
	}
	
	public void ordenarPorMotivoSuspensao() {
		this.setNomeAmbiente(SortOrder.unsorted);
		this.setDataReserva(SortOrder.unsorted);
		this.setSituacao(SortOrder.unsorted);
		this.setBloco(SortOrder.unsorted);
		this.setUnidade(SortOrder.unsorted);
		this.setMotivoReprovacao(SortOrder.unsorted);
		if (motivoSuspensao.equals(SortOrder.ascending)) {
			this.setMotivoSuspensao(SortOrder.descending);
		} else {
			this.setMotivoSuspensao(SortOrder.ascending);
		}
	}

	public SortOrder getNomeAmbiente() {
		return nomeAmbiente;
	}

	public void setNomeAmbiente(SortOrder nomeAmbiente) {
		this.nomeAmbiente = nomeAmbiente;
	}

	public SortOrder getDataReserva() {
		return dataReserva;
	}

	public void setDataReserva(SortOrder dataReserva) {
		this.dataReserva = dataReserva;
	}

	public SortOrder getSituacao() {
		return situacao;
	}

	public void setSituacao(SortOrder situacao) {
		this.situacao = situacao;
	}

	public SortOrder getBloco() {
		return bloco;
	}

	public void setBloco(SortOrder bloco) {
		this.bloco = bloco;
	}

	public SortOrder getUnidade() {
		return unidade;
	}

	public void setUnidade(SortOrder unidade) {
		this.unidade = unidade;
	}

	public SortOrder getCondomino() {
		return condomino;
	}

	public void setCondomino(SortOrder condomino) {
		this.condomino = condomino;
	}

	public SortOrder getMotivoReprovacao() {
		return motivoReprovacao;
	}

	public void setMotivoReprovacao(SortOrder motivoReprovacao) {
		this.motivoReprovacao = motivoReprovacao;
	}

	public SortOrder getMotivoSuspensao() {
		return motivoSuspensao;
	}

	public void setMotivoSuspensao(SortOrder motivoSuspensao) {
		this.motivoSuspensao = motivoSuspensao;
	}
	
	

}