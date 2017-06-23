package br.com.condominiosvirtuais.entity;

import br.com.condominiosvirtuais.util.AplicacaoUtil;

/**
 * Entidade que representa um m�dulo do sistema. Usado para agrupar as telas.
 * 
 * Obs.: Essa entidade ser� cadastradas pela administrados do sistema e n�o pelo usu�rio
 * @author Maikel Joel de Souza
 * @since 22/06/2017
 */
public class Modulo {
	
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
	
	public String getNomeI18n() {
		return AplicacaoUtil.i18n(this.getNome());
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}
	
	public String getDescricaoI18n() {
		return AplicacaoUtil.i18n(this.getDescricao());
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}	

}
