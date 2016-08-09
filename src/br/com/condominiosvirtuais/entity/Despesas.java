package br.com.condominiosvirtuais.entity;

import java.util.Date;

import br.com.condominiosvirtuais.util.AplicacaoUtil;

/**
 * Entidade que representa um despesa de um condomínio ou condômino.
 * @author Maikel Joel de Souza
 * @since 23/05/2016
 */
public class Despesas {

	private Integer id;	
	
	private String descricao;
	
	private Double valor;
	
	private Date mesAnoReferencia;
	
	private Integer tipo;
	
	private Condomino condomino;
	
	private Condominio condominio;
	
	private Unidade unidade;
	
	private ConsumoGasDespesas consumoGasDespesas;
	
	
	public Despesas(){
		this.condominio = new Condominio();
		this.condomino = new Condomino();
		this.unidade = new Unidade();
		this.consumoGasDespesas = new ConsumoGasDespesas();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	public Date getMesAnoReferencia() {
		return mesAnoReferencia;
	}

	public void setMesAnoReferencia(Date mesAnoReferencia) {
		this.mesAnoReferencia = mesAnoReferencia;
	}	

	public Integer getTipo() {
		return tipo;
	}

	public void setTipo(Integer tipo) {
		this.tipo = tipo;
	}

	public Condomino getCondomino() {
		return condomino;
	}

	public void setCondomino(Condomino condomino) {
		this.condomino = condomino;
	}

	public Condominio getCondominio() {
		return condominio;
	}

	public void setCondominio(Condominio condominio) {
		this.condominio = condominio;
	}

	public Unidade getUnidade() {
		return unidade;
	}

	public void setUnidade(Unidade unidade) {
		this.unidade = unidade;
	}
	
	public ConsumoGasDespesas getConsumoGasDespesas() {
		return consumoGasDespesas;
	}

	public void setConsumoGasDespesas(ConsumoGasDespesas consumoGasDespesas) {
		this.consumoGasDespesas = consumoGasDespesas;
	}

	/* Atualmente existem seis tipos de despesas (@Link DespesasEnum)
	 * Para que seja possíve exibir essas possibilidades em um único campo e em momentos diferentes, foi
	 * gravado a string no banco. 
	 */
	public String getTipoI18n(){		
		return AplicacaoUtil.i18n("despesas.tipo.label."+this.tipo); // Concatena a chave com o tipo que é um número começando com zero
	}	
	
}
