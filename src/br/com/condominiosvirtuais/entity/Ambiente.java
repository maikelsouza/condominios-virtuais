package br.com.condominiosvirtuais.entity;

import java.util.List;

/**
 * Entidade que representa um ambiente de um condomínio ou de um conjunto de blocos. Exemplo: Salão de Festa
 * @author Maikel Joel de Souza
 * @since 20/02/2013
 */
public class Ambiente implements TipoConjuntoBloco {
	
	private Integer id;
	
	private String nome;
	
	private Integer idCondominio;
	
	private Integer idConjuntoBloco;	
	
	private List<ItemAmbiente> listaItensAmbiente;
		
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
	
	public List<ItemAmbiente> getListaItensAmbiente() {
		return listaItensAmbiente;
	}
	
	public void setListaItensAmbiente(List<ItemAmbiente> listaItensAmbiente) {
		this.listaItensAmbiente = listaItensAmbiente;
	}
	
	public Integer getIdCondominio() {
		return idCondominio;
	}

	public void setIdCondominio(Integer idCondominio) {
		this.idCondominio = idCondominio;
	}

	public Integer getIdConjuntoBloco() {
		return idConjuntoBloco;
	}

	public void setIdConjuntoBloco(Integer idConjuntoBloco) {
		this.idConjuntoBloco = idConjuntoBloco;
	}

}
