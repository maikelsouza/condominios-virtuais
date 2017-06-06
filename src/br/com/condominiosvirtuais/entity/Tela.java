package br.com.condominiosvirtuais.entity;

import java.util.List;

/**
 * Entidade que representa uma tela da aplicação. Será cadastrado todas as telas para que possa ser listadas e adicionadas <\br>
 * em um grupo de usuário específico
 * @author Maikel Joel de Souza
 * @since 31/05/2017
 */
public class Tela {
	
	private Integer id;
	
	private String nome;
	
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
	
	

}
