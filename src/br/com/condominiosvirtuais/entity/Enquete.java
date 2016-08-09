package br.com.condominiosvirtuais.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * Entidade que representas uma enquete realizada em um condomínio. <br> 
 * Exemplo: Qual tema deve ser tratado na reunião do dia X. Contratação novos funcionários, reforma parquinho e etc.
 * @author Maikel J. Souza
 * @since 12/12/2013
 */
public class Enquete {
	
	private Integer id;
	
	private String pergunta;
	
	private Date dataFim;	
	
	private Date dataCriacao;	
	
	private List<AlternativaEnquete> listaAlternativaEnquetes = new ArrayList<AlternativaEnquete>();
	
	private Integer idCondominio;
	

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPergunta() {
		return pergunta;
	}

	public void setPergunta(String pergunta) {
		this.pergunta = pergunta;
	}

	public Date getDataFim() {
		return dataFim;
	}

	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}	

	public Date getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(Date dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	public List<AlternativaEnquete> getListaAlternativaEnquetes() {
		return listaAlternativaEnquetes;
	}

	public void setListaAlternativaEnquetes(
			List<AlternativaEnquete> listaAlternativaEnquetes) {
		this.listaAlternativaEnquetes = listaAlternativaEnquetes;
	}

	public Integer getIdCondominio() {
		return idCondominio;
	}

	public void setIdCondominio(Integer idCondominio) {
		this.idCondominio = idCondominio;
	}

}
