package br.com.condominiosvirtuais.entity;

import br.com.condominiosvirtuais.util.AplicacaoUtil;

public class TipoChamado {
	
	private Integer id;
	
	private String nome;
	
	private String descricao;

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

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}	
	
	/* As opções estão no arquivo Messages_pt_BR.properties. A chave será gravada no banco.
	 */
	public String getNomeI18n() {
		return  AplicacaoUtil.i18n(this.nome);
	}

}
