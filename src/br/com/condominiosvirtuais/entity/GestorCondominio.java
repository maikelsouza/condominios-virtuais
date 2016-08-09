package br.com.condominiosvirtuais.entity;

/**
 * Entidade que identifica qual(ais) gestor(es) um condom�nio ou bloco possui. 
 * Exemplo de utiliza��o: Tela de edi��o de um condom�nio, onde s�o identificados quais os gestores existem nesse condom�nio.
 * @author Maikel Joel de Souza
 * @since 21/02/2013
 */
public class GestorCondominio {
	
	private Integer id;
	
	private Integer idCondominio;
	
	private Integer idBloco;
	
	private Integer idCondomino;
	
	private Integer tipoCondomino;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getIdCondominio() {
		return idCondominio;
	}

	public void setIdCondominio(Integer idCondominio) {
		this.idCondominio = idCondominio;
	}

	public Integer getIdBloco() {
		return idBloco;
	}

	public void setIdBloco(Integer idBloco) {
		this.idBloco = idBloco;
	}

	public Integer getIdCondomino() {
		return idCondomino;
	}

	public void setIdCondomino(Integer idCondomino) {
		this.idCondomino = idCondomino;
	}

	public Integer getTipoCondomino() {
		return tipoCondomino;
	}

	public void setTipoCondomino(Integer tipoCondomino) {
		this.tipoCondomino = tipoCondomino;
	}	
	
}
