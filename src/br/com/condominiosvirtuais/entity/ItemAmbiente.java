package br.com.condominiosvirtuais.entity;

/**
 * Entidade que representea um item de um ambiente cadastrado no condôminio.
 * Exemplo: Ambiente: Salão de Festa. Item Ambiente: Prato, garfo, faca e etc
 * @author Maikel Joel de Souza
 * @since 23/02/2013
 */
public class ItemAmbiente {
	
	private Integer id;
	
	private String nome;
	
	private Integer quantidade;
	
	private Integer idAmbiente;
	
	
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

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

	public Integer getIdAmbiente() {
		return idAmbiente;
	}

	public void setIdAmbiente(Integer idAmbiente) {
		this.idAmbiente = idAmbiente;
	}

}
