package br.com.condominiosvirtuais.reports;

import javax.faces.model.ListDataModel;

import br.com.condominiosvirtuais.entity.Despesas;
import br.com.condominiosvirtuais.entity.Unidade;

/**
 * Classe que represas as despesas de uma únidade bem como o seu total 
 * @author Maikel Joel de Souza
 * @since 03/06/2016
 */
public class DespesasUnidade {
	
	private ListDataModel<Despesas> listaDespesas;
	
	private Unidade unidade;
	
	private Double totalDespesasUnidade = 0.0;

	public ListDataModel<Despesas> getListaDespesas() {
		return listaDespesas;
	}

	public void setListaDespesas(ListDataModel<Despesas> listaDespesas) {
		this.listaDespesas = listaDespesas;
	}

	public Unidade getUnidade() {
		return unidade;
	}

	public void setUnidade(Unidade unidade) {
		this.unidade = unidade;
	}

	public void setTotalDespesasUnidade(Double totalDespesasUnidade) {
		this.totalDespesasUnidade = totalDespesasUnidade;
	}
	
	public Double getTotalDespesasUnidade() {
		this.totalDespesasUnidade = 0.0;
		for (Despesas despesas : listaDespesas) {
			totalDespesasUnidade+= despesas.getValor();
		}
		return totalDespesasUnidade;
	}

	
	
	
	
	
	

}
