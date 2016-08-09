package br.com.condominiosvirtuais.entity;

import java.util.List;

/**
 * Entidade que representa um conjunto de bloco. Utilizado para agrupar os blocos onde podem ter <br>
 * um ambiente associado. Por exemplo.
 * @author Maikel Joel de Souza
 * @since 20/02/2013
 */
public class ConjuntoBloco {
	
	private Integer id;
	
    /** Identifica o tipo de conjunto de bloco {@link TipoConjuntoBloco} */ 
	private Integer tipo;
	
	private List<BlocoConjuntoBloco> listaBlocoConjuntoBloco;
	
	// Lista de objetos que podem estar associados a um conjunto de blocos. Ex.: Ambiente, Funcionário.
	private List<TipoConjuntoBloco> listaTipoConjuntoBloco;

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

	public List<BlocoConjuntoBloco> getListaListaBlocoConjuntoBlocos() {
		return listaBlocoConjuntoBloco;
	}

	public void setListaListaBlocoConjuntoBlocos(List<BlocoConjuntoBloco> listaBlocoConjuntoBloco) {
		this.listaBlocoConjuntoBloco = listaBlocoConjuntoBloco;
	}

	public List<TipoConjuntoBloco> getListaTipoConjuntoBlocos() {
		return listaTipoConjuntoBloco;
	}

	public void setListaTipoConjuntoBlocos(List<TipoConjuntoBloco> listaTipoConjuntoBlocos) {
		this.listaTipoConjuntoBloco = listaTipoConjuntoBlocos;
	}
	

}
