package br.com.condominiosvirtuais.entity;

import java.util.List;


/**
 * Entidade que representa um bloco de um condomínio. Exemplo Bloco 1
 * @author Maikel Joel de Souza
 * @since 20/02/2013
 */
public class Bloco implements Comparable<Bloco>{
	
	private Integer id;
	
	private String nome;
	
	private Condomino sindico;
	
	private Condomino subSindico;
	
	private List<Condomino> listaConselheiros;
	
	private List<Unidade> listaUnidade;
	
	private List<Ambiente> listaAmbientes;
	
	private Integer idCondominio;
	
	private Integer idConjuntoBloco;
	
	
	public Bloco(){
		this.sindico = new Condomino();
		this.subSindico = new Condomino();
	}
	
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

	public Condomino getSindico() {
		return sindico;
	}

	public void setSindico(Condomino sindico) {
		this.sindico = sindico;
	}

	public Condomino getSubSindico() {
		return subSindico;
	}

	public void setSubSindico(Condomino subSindico) {
		this.subSindico = subSindico;
	}

	public List<Condomino> getListaConselheiros() {
		return listaConselheiros;
	}

	public void setListaConselheiros(List<Condomino> listaConselheiros) {
		this.listaConselheiros = listaConselheiros;
	}

	public List<Unidade> getListaUnidade() {
		return listaUnidade;
	}

	public void setListaUnidade(List<Unidade> listaUnidade) {
		this.listaUnidade = listaUnidade;
	}

	public List<Ambiente> getListaAmbientes() {
		return listaAmbientes;
	}

	public void setListaAmbientes(List<Ambiente> listaAmbientes) {
		this.listaAmbientes = listaAmbientes;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((idCondominio == null) ? 0 : idCondominio.hashCode());
		result = prime * result
				+ ((idConjuntoBloco == null) ? 0 : idConjuntoBloco.hashCode());
		result = prime * result
				+ ((listaAmbientes == null) ? 0 : listaAmbientes.hashCode());
		result = prime
				* result
				+ ((listaConselheiros == null) ? 0 : listaConselheiros
						.hashCode());
		result = prime * result
				+ ((listaUnidade == null) ? 0 : listaUnidade.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Bloco other = (Bloco) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (idCondominio == null) {
			if (other.idCondominio != null)
				return false;
		} else if (!idCondominio.equals(other.idCondominio))
			return false;
		if (idConjuntoBloco == null) {
			if (other.idConjuntoBloco != null)
				return false;
		} else if (!idConjuntoBloco.equals(other.idConjuntoBloco))
			return false;
		if (listaAmbientes == null) {
			if (other.listaAmbientes != null)
				return false;
		} else if (!listaAmbientes.equals(other.listaAmbientes))
			return false;
		if (listaConselheiros == null) {
			if (other.listaConselheiros != null)
				return false;
		} else if (!listaConselheiros.equals(other.listaConselheiros))
			return false;
		if (listaUnidade == null) {
			if (other.listaUnidade != null)
				return false;
		} else if (!listaUnidade.equals(other.listaUnidade))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		return true;
	}

	@Override
	public int compareTo(Bloco arg0) {
		
//		int a = this.nome.compareTo(arg0.getNome());
//	
//		return 0; 

		return this.nome.compareTo(arg0.getNome());
	}
	
}
