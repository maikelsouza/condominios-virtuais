package br.com.condominiosvirtuais.entity;

/**
 * Interfaçe que tipifica o conjunto de bloco. Exemplo: Um conjunto de blocos que está associada a um ambiente. <br>
 * Para esse caso a entidade ambiente deve implementar essa interfaçe.
 * Usado para fazer a distinção nas consultas na tabela {@link ConjuntoBloco} 
 * @author Maikel Joel de Souza
 * @since 23/02/2013
 */
public interface TipoConjuntoBloco {
	
	public abstract Integer getIdConjuntoBloco();

	public abstract  void setIdConjuntoBloco(Integer idConjuntoBloco);

}
