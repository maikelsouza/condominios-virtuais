package br.com.condominiosvirtuais.entity;

public class ConsumoGasDespesas {
	
	private Integer id;
	
	private Double consumoMesAtual;
	
	private Integer idDespesas;	

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

	public Integer getIdDespesas() {
		return idDespesas;
	}

	public void setIdDespesas(Integer idDespesas) {
		this.idDespesas = idDespesas;
	}		

}
