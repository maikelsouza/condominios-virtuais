package br.com.condominiosvirtuais.entity;


/**
 * Entidade que representa uma associação entre uma tela e uma aba. Usado para verificar quais abras de uma tela um </br> 
 * determinado grupo de usuário tem acesso. 
 * @author Maikel Joel de Souza
 * @since 05/06/2017
 */
public class TelaAbaTela {
	
	private Integer id;
	
	private Integer idTela;
	
	private Integer idAbaTela;
	
	private String  acao;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getIdTela() {
		return idTela;
	}

	public void setIdTela(Integer idTela) {
		this.idTela = idTela;
	}

	public Integer getIdAbaTela() {
		return idAbaTela;
	}

	public void setIdAbaTela(Integer idAbaTela) {
		this.idAbaTela = idAbaTela;
	}

	public String getAcao() {
		return acao;
	}

	public void setAcao(String acao) {
		this.acao = acao;
	}
	
	

}
