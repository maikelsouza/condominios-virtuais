package br.com.condominiosvirtuais.entity;

/**
 * Entidade criada para representar um funcionário do condomínio. Exemplo: Zelador, Faxineira e etc
 * @author Maikel Joel de Souza
 * @since 23/02/2012
 */
public class Funcionario extends Usuario implements TipoConjuntoBloco{
	
	private String funcao;
	
	private Long telefoneCelular;
	
	private Long telefoneResidencial;
	
	private Integer idCondominio;
	
	private Integer idConjuntoBloco;

	public String getFuncao() {
		return funcao;
	}

	public void setFuncao(String funcao) {
		this.funcao = funcao;
	}		

	public Long getTelefoneCelular() {
		return telefoneCelular;
	}

	public void setTelefoneCelular(Long telefoneCelular) {
		this.telefoneCelular = telefoneCelular;
	}

	public Long getTelefoneResidencial() {
		return telefoneResidencial;
	}

	public void setTelefoneResidencial(Long telefoneResidencial) {
		this.telefoneResidencial = telefoneResidencial;
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
