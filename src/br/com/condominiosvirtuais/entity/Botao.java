package br.com.condominiosvirtuais.entity;

import br.com.condominiosvirtuais.util.AplicacaoUtil;

/**
 * Entidade que representa um bot�o de uma determinada tela. Usado para verificar se ser� ou n�o exibido essa aba para
 * um grupo de usu�rio
 * Obs.: Essa entidade � usada para recupetar os bot�es que s�o cadastradas pela administrados do sistema e n�o pelo usu�rio
 * @author Maikel Joel de Souza
 * @since 22/06/2017
 */
public class Botao implements Comparable<Botao> {	

	private Integer id;
	
	private String nome;
	
	private String descricao;
	
	private String idBotao;
	
	private Integer idTela;
	
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
	
	public String getNomeI18n() {
		return AplicacaoUtil.i18n(this.getNome());
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	public String getDescricaoI18n() {
		return AplicacaoUtil.i18n(this.getDescricao());
	}

	public String getIdBotao() {
		return idBotao;
	}

	public void setIdBotao(String idBotao) {
		this.idBotao = idBotao;
	}

	public Integer getIdTela() {
		return idTela;
	}

	public void setIdTela(Integer idTela) {
		this.idTela = idTela;
	}

	@Override
	public int compareTo(Botao botao) {
		return this.getNomeI18n().compareTo(botao.getNomeI18n());
	}

}
