package br.com.condominiosvirtuais.entity;

import br.com.condominiosvirtuais.util.AplicacaoUtil;

public class MeioPagamento implements Comparable<MeioPagamento> {
	
	private Integer id;
	
	private String nome;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	/* As opções estão no arquivo Messages_pt_BR.properties. A chave será gravada no banco.
	 */
	public String getNomeI18n() {
		return  AplicacaoUtil.i18n(this.nome);
	}

	@Override
	public int compareTo(MeioPagamento meioPagamento) {
		return  meioPagamento.getNome().compareTo(getNomeI18n());       
	}
	

}
