package br.com.condominiosvirtuais.entity;

/**
 * Entidade que representa a configuração das tarifas que serão cobradas com a {@link Despesas} do gas
 * @author Maikel Joel de Souza
 * @since 10/06/2016
 */
public class TarifaGas {

	private Integer id;
	
	private Double fatorConversao;
	
	private Double valorCobrado;
	
	private Condominio condominio;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Double getFatorConversao() {
		return fatorConversao;
	}

	public void setFatorConversao(Double fatorConversao) {
		this.fatorConversao = fatorConversao;
	}

	public Double getValorCobrado() {
		return valorCobrado;
	}

	public void setValorCobrado(Double valorCobrado) {
		this.valorCobrado = valorCobrado;
	}

	public Condominio getCondominio() {
		return condominio;
	}

	public void setCondominio(Condominio condominio) {
		this.condominio = condominio;
	}
	
	
	
}
