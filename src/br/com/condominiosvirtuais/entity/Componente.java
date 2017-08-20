package br.com.condominiosvirtuais.entity;

import br.com.condominiosvirtuais.enumeration.ComponenteTipoEnum;
import br.com.condominiosvirtuais.util.AplicacaoUtil;


/**
 * Entidade que representa um compoente da tela, por exemplo: Um botão, um link e etc. O atributo tipo {@link ComponenteTipoEnum} identifica 
 * qual componente.  
 * @author Maikel Joel de Souza 
 * @since 24/06/2017
 */
public class Componente implements Comparable<Componente>{

	private Integer id;
	
	private String nome;
	
	private String descricao;
	
	private String idComponente;
	
	private Integer idTela;
	
	private Integer idAba;
	
	private String tipo;

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

	public String getIdComponente() {
		return idComponente;
	}

	public void setIdComponente(String idComponente) {
		this.idComponente = idComponente;
	}

	public Integer getIdTela() {
		return idTela;
	}

	public void setIdTela(Integer idTela) {
		this.idTela = idTela;
	}

	public Integer getIdAba() {
		return idAba;
	}

	public void setIdAba(Integer idAba) {
		this.idAba = idAba;
	}

	public String getTipoI18n() {
		return AplicacaoUtil.i18n(this.getTipo());
	}
	
	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	@Override
	public int compareTo(Componente componente) {
		return this.getNomeI18n().compareTo(componente.getNomeI18n());
	}
	
	
}
