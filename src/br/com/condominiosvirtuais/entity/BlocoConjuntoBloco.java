package br.com.condominiosvirtuais.entity;

/**
 * Entidade que representa a associação de um bloco com um conjunto de bloco. <br>
 * Exemplo: Os blocos 1 e 2 fazem parte do conjunto de bloco A, logo existiram dois registro de BlocoConjuntoBloco, <br>
 * para cada bloco.
 * @author Maikel Joel de Souza
 * @since 20/02/2013
 */
public class BlocoConjuntoBloco {
	
	private Integer id;
	
	private Bloco bloco;
	
	private ConjuntoBloco conjuntoBloco;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Bloco getBloco() {
		return bloco;
	}

	public void setBloco(Bloco bloco) {
		this.bloco = bloco;
	}

	public ConjuntoBloco getConjuntoBloco() {
		return conjuntoBloco;
	}

	public void setConjuntoBloco(ConjuntoBloco conjuntoBloco) {
		this.conjuntoBloco = conjuntoBloco;
	}
	

}
