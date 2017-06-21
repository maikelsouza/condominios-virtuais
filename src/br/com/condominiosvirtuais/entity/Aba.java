package br.com.condominiosvirtuais.entity;

import br.com.condominiosvirtuais.util.AplicacaoUtil;

/**
 * Entidade que representa uma aba de uma determinada tela. Usado para verificar se ser� ou n�o exibido essa aba para
 * um grupo de usu�rio
 * Obs.: Essa entidade � usada para recupetar as abas que s�o cadastradas pela administrados do sistema e n�o pelo usu�rio
 * @author Maikel Joel de Souza
 * @since 31/05/2017
 */
public class Aba implements Comparable<Aba>{
	
	private Integer id;
	
	private String nome;
	
	private String descricao;
	
	private String idAba;
	
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

	public String getDescricao() {
		return descricao;
	}
	
	public String getDescricaoI18n() {
		return AplicacaoUtil.i18n(this.getDescricao());
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getIdAba() {
		return idAba;
	}

	public void setIdAba(String idAba) {
		this.idAba = idAba;
	}

	public Integer getIdTela() {
		return idTela;
	}

	public void setIdTela(Integer idTela) {
		this.idTela = idTela;
	}	
	
	public String getNomeI18n(){
		return AplicacaoUtil.i18n(this.getNome());
	}

	@Override
	public int compareTo(Aba aba) {
		return this.getNomeI18n().compareTo(aba.getNomeI18n());
	}
	

}
