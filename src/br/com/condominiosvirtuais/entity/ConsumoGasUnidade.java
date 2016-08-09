package br.com.condominiosvirtuais.entity;

import java.util.Date;

public class ConsumoGasUnidade {
	
	private Integer id;
	
	private Double consumoMesAtual;
	
	private Date mesAnoReferencia;
	
	private Unidade unidade;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Double getConsumoMesAtual() {
		return consumoMesAtual;
	}

	public void setConsumoMesAtual(Double consumoMesAtual) {
		this.consumoMesAtual = consumoMesAtual;
	}

	public Date getMesAnoReferencia() {
		return mesAnoReferencia;
	}

	public void setMesAnoReferencia(Date mesAnoReferencia) {
		this.mesAnoReferencia = mesAnoReferencia;
	}

	public Unidade getUnidade() {
		return unidade;
	}

	public void setUnidade(Unidade unidade) {
		this.unidade = unidade;
	}
	
	

}
