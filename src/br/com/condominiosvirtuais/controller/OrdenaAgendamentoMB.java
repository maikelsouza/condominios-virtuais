package br.com.condominiosvirtuais.controller;

import java.io.Serializable;

import org.richfaces.component.SortOrder;

/**
 * MB Criado para ordenar (asc e desc) os itens da lista de agendamentos. Páginas: formAprovaAgendamento.xhtml e formListaAgendamento.xhtml
 * @author Maikel Joel de Souza
 * @since 09/04/2016  
 */
public class OrdenaAgendamentoMB implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private SortOrder bloco = SortOrder.unsorted;
	
	private SortOrder unidade = SortOrder.unsorted;
	
	private SortOrder data = SortOrder.unsorted;
	
	private SortOrder horaInicial = SortOrder.unsorted;
	
	private SortOrder horaFinal = SortOrder.unsorted;
	
	private SortOrder situacao = SortOrder.unsorted;
	
	private SortOrder motivoReprovacao = SortOrder.unsorted;
	
	private SortOrder condomino = SortOrder.unsorted;
		
	public void ordenarPorBloco(){
		this.setUnidade(SortOrder.unsorted);
		this.setData(SortOrder.unsorted);
		this.setHoraInicial(SortOrder.unsorted);
		this.setHoraFinal(SortOrder.unsorted);
		this.setSituacao(SortOrder.unsorted);
		this.setMotivoReprovacao(SortOrder.unsorted);
		this.setCondomino(SortOrder.unsorted);
		if (this.bloco.equals(SortOrder.ascending)) {
			 this.setBloco(SortOrder.descending);
		} else {
			 this.setBloco(SortOrder.ascending);
		}
	}

	public void ordenarPorUnidade(){
		this.setBloco(SortOrder.unsorted);
		this.setData(SortOrder.unsorted);
		this.setHoraInicial(SortOrder.unsorted);
		this.setHoraFinal(SortOrder.unsorted);
		this.setSituacao(SortOrder.unsorted);
		this.setMotivoReprovacao(SortOrder.unsorted);
		this.setCondomino(SortOrder.unsorted);
		if (this.unidade.equals(SortOrder.ascending)) {
			 this.setUnidade(SortOrder.descending);
		} else {
			 this.setUnidade(SortOrder.ascending);
		}
	}
	
	public void ordenarPorData(){
		this.setBloco(SortOrder.unsorted);
		this.setUnidade(SortOrder.unsorted);
		this.setHoraInicial(SortOrder.unsorted);
		this.setHoraFinal(SortOrder.unsorted);
		this.setSituacao(SortOrder.unsorted);
		this.setMotivoReprovacao(SortOrder.unsorted);
		this.setCondomino(SortOrder.unsorted);
		if (this.data.equals(SortOrder.ascending)) {
			 this.setData(SortOrder.descending);
		} else {
			 this.setData(SortOrder.ascending);
		}
	}
	
	public void ordenarPorHoraInicial(){
		this.setBloco(SortOrder.unsorted);
		this.setUnidade(SortOrder.unsorted);		
		this.setData(SortOrder.descending);
		this.setHoraFinal(SortOrder.unsorted);
		this.setSituacao(SortOrder.unsorted);
		this.setCondomino(SortOrder.unsorted);
		this.setMotivoReprovacao(SortOrder.unsorted);
		if (this.horaInicial.equals(SortOrder.ascending)) {
			 this.setHoraInicial(SortOrder.descending);
		} else {
			 this.setHoraInicial(SortOrder.ascending);
		}
	}
	
	public void ordenarPorHoraFinal(){
		this.setBloco(SortOrder.unsorted);
		this.setUnidade(SortOrder.unsorted);		
		this.setData(SortOrder.descending);
		this.setHoraInicial(SortOrder.unsorted);
		this.setSituacao(SortOrder.unsorted);
		this.setCondomino(SortOrder.unsorted);
		this.setMotivoReprovacao(SortOrder.unsorted);
		if (this.horaFinal.equals(SortOrder.ascending)) {
			 this.setHoraFinal(SortOrder.descending);
		} else {
			 this.setHoraFinal(SortOrder.ascending);
		}
	}
	
	public void ordenarPorSituacao(){
		this.setBloco(SortOrder.unsorted);
		this.setUnidade(SortOrder.unsorted);		
		this.setData(SortOrder.descending);
		this.setHoraInicial(SortOrder.unsorted);
		this.setHoraFinal(SortOrder.unsorted);		
		this.setMotivoReprovacao(SortOrder.unsorted);
		this.setCondomino(SortOrder.unsorted);
		if (this.situacao.equals(SortOrder.ascending)) {
			 this.setSituacao(SortOrder.descending);
		} else {
			 this.setSituacao(SortOrder.ascending);
		}
	}
	
	public void ordenarPorMotivoReprovacao(){
		this.setBloco(SortOrder.unsorted);
		this.setUnidade(SortOrder.unsorted);		
		this.setData(SortOrder.descending);
		this.setHoraInicial(SortOrder.unsorted);
		this.setHoraFinal(SortOrder.unsorted);		
		this.setSituacao(SortOrder.unsorted);
		this.setCondomino(SortOrder.unsorted);
		if (this.motivoReprovacao.equals(SortOrder.ascending)) {
			 this.setMotivoReprovacao(SortOrder.descending);
		} else {
			 this.setMotivoReprovacao(SortOrder.ascending);
		}
	}
	
	public void ordenarPorCondomino(){
		this.setBloco(SortOrder.unsorted);
		this.setUnidade(SortOrder.unsorted);
		this.setData(SortOrder.unsorted);
		this.setHoraInicial(SortOrder.unsorted);
		this.setHoraFinal(SortOrder.unsorted);
		this.setSituacao(SortOrder.unsorted);
		this.setMotivoReprovacao(SortOrder.unsorted);
		if (this.condomino.equals(SortOrder.ascending)) {
			 this.setCondomino(SortOrder.descending);
		} else {
			 this.setCondomino(SortOrder.ascending);
		}
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

	public SortOrder getData() {
		return data;
	}

	public void setData(SortOrder data) {
		this.data = data;
	}

	public SortOrder getHoraInicial() {
		return horaInicial;
	}

	public void setHoraInicial(SortOrder horaInicial) {
		this.horaInicial = horaInicial;
	}

	public SortOrder getHoraFinal() {
		return horaFinal;
	}

	public void setHoraFinal(SortOrder horaFinal) {
		this.horaFinal = horaFinal;
	}

	public SortOrder getSituacao() {
		return situacao;
	}

	public void setSituacao(SortOrder situacao) {
		this.situacao = situacao;
	}

	public SortOrder getMotivoReprovacao() {
		return motivoReprovacao;
	}

	public void setMotivoReprovacao(SortOrder motivoReprovacao) {
		this.motivoReprovacao = motivoReprovacao;
	}

	public SortOrder getCondomino() {
		return condomino;
	}

	public void setCondomino(SortOrder condomino) {
		this.condomino = condomino;
	}
	

}
