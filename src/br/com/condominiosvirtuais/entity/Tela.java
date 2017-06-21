package br.com.condominiosvirtuais.entity;

import java.util.List;

import br.com.condominiosvirtuais.util.AplicacaoUtil;

/**
 * Entidade que representa uma tela da aplica��o. Ser� cadastrado todas as telas para que possa ser listadas e adicionadas <\br>
 * em um grupo de usu�rio espec�fico
 * Obs.: Essa entidade � usada para recupetar as telas que s�o cadastradas pela administrados do sistema e n�o pelo usu�rio 
 * @author Maikel Joel de Souza 
 * @since 31/05/2017
 */
public class Tela implements Comparable<Tela>{
	
	private Integer id;
	
	private String nome;
	
	private String descricao;
	
	private String nomeArquivo;	
	
	private List<Aba> listaAbas;

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

	public String getNomeArquivo() {
		return nomeArquivo;
	}

	public void setNomeArquivo(String nomeArquivo) {
		this.nomeArquivo = nomeArquivo;
	}

	public List<Aba> getListaAbas() {		
		return listaAbas;
	}

	public void setListaAbas(List<Aba> listaAbas) {
		this.listaAbas = listaAbas;
	}
	
	public String getNomeI18n(){
		return AplicacaoUtil.i18n(this.getNome());
	}

	@Override
	public int compareTo(Tela tela) {
		return this.getNomeI18n().compareTo(tela.getNomeI18n());
	}
	

}
