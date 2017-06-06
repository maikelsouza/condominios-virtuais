package br.com.condominiosvirtuais.entity;


/**
 * Entidade que representa uma aba de uma determinada tela. Usado para verificar se será ou não exibido essa aba para
 * um grupo de usuário
 * @author Maikel Joel de Souza
 * @since 31/05/2017
 */
public class Aba {
	
	private Integer id;
	
	private String nome;
	
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
	

}
