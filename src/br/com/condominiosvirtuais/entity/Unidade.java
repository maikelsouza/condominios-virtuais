package br.com.condominiosvirtuais.entity;

import java.util.List;

/**
 * Entidade que representa uma unidade. Exemplo: Apartamento
 * @author Maikel Joel de Souza
 * @since 23/02/2013
 */
public class Unidade implements Comparable<Unidade> {
	
	private Integer id;
	
	private Integer tipo;
	
	private Integer numero;
	
	private Integer idBloco;
	
	private List<Condomino> listaCondominos;
	
	private List<Garagem> listaGaragem;
	
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getTipo() {
		return tipo;
	}

	public void setTipo(Integer tipo) {
		this.tipo = tipo;
	}

	public Integer getNumero() {
		return numero;
	}

	public void setNumero(Integer numero) {
		this.numero = numero;
	}

	public List<Condomino> getListaCondominos() {
		return listaCondominos;
	}

	public void setListaCondominos(List<Condomino> listaCondominos) {
		this.listaCondominos = listaCondominos;
	}

	public Integer getIdBloco() {
		return idBloco;
	}

	public void setIdBloco(Integer idBloco) {
		this.idBloco = idBloco;
	}

	public List<Garagem> getListaGaragem() {
		return listaGaragem;
	}

	public void setListaGaragem(List<Garagem> listaGaragem) {
		this.listaGaragem = listaGaragem;
	}

	@Override
	public int compareTo(Unidade arg0) {
		if (this.numero < arg0.getNumero()) {
            return -1;
        }
        if (this.numero > arg0.getNumero()) {
            return 1;
        }
		return 0; 
	}
	
	 @Override
	    public boolean equals(Object obj) {
	        if (!(obj instanceof Unidade)) {
	            return false;
	        }
	        final Unidade other = (Unidade) obj;
	        return this.getId().equals(other.getId());
	    }
}
