package br.com.condominiosvirtuais.reports;

import javax.faces.model.ListDataModel;

import br.com.condominiosvirtuais.entity.Despesas;

/**
 * Classe que irá representar o relatório de despesas de um condomínio, ou seja, <br>
 * as despesas do condomínio mais as despesas de cada unidade, bem como os seus somatórios
 * @author Maikel Joel de Souza
 * @since 03/06/2016
 */
public class RelatorioDespesas {
	                                       
	private ListDataModel<DespesasUnidade> listaDespesasUnidades;	
	                                
	private ListDataModel<Despesas> listaDespesasCondominio;
	
	private Double totalDespesasCondominio = 0.0;
	
	private Double totalDespesasTodasUnidades = 0.0;
	

	public ListDataModel<DespesasUnidade> getListaDespesasUnidades() {
		return listaDespesasUnidades;
	}

	public void setListaDespesasUnidades(ListDataModel<DespesasUnidade> listaDespesasUnidades) {
		this.listaDespesasUnidades = listaDespesasUnidades;
	}

	public ListDataModel<Despesas> getListaDespesasCondominio() {
		return listaDespesasCondominio;
	}

	public void setListaDespesasCondominio(ListDataModel<Despesas> listaDespesasCondominio) {
		this.listaDespesasCondominio = listaDespesasCondominio;
	}

	public Double getTotalDespesasCondominio() {
		this.totalDespesasCondominio = 0.0;
		for (Despesas despesas : listaDespesasCondominio) {
			this.totalDespesasCondominio+= despesas.getValor(); 
		}
		return totalDespesasCondominio;
	}

	public void setTotalDespesasCondominio(Double totalDespesasCondominio) {
		this.totalDespesasCondominio = totalDespesasCondominio;
	}

	public Double getTotalDespesasTodasUnidades() {
		this.totalDespesasTodasUnidades = 0.0;
		for (DespesasUnidade despesasUnidade : listaDespesasUnidades) {
			this.totalDespesasTodasUnidades+= despesasUnidade.getTotalDespesasUnidade();
		}
		return totalDespesasTodasUnidades;
	}

	public void setTotalDespesasTodasUnidades(Double totalDespesasTodasUnidades) {
		this.totalDespesasTodasUnidades = totalDespesasTodasUnidades;
	}	
	
	

}
