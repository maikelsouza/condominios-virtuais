package br.com.condominiosvirtuais.converter;

public class BeanConversor implements Comparable<BeanConversor> {
	
	private String atributo;
	
	private Integer posicaoInicial;
	
	private Integer posicaoFinal;
	
	private Object conteudo;

	public String getAtributo() {
		return atributo;
	}

	public void setAtributo(String atributo) {
		this.atributo = atributo;
	}

	public Integer getPosicaoInicial() {
		return posicaoInicial;
	}

	public void setPosicaoInicial(Integer posicaoInicial) {
		this.posicaoInicial = posicaoInicial;
	}

	public Integer getPosicaoFinal() {
		return posicaoFinal;
	}

	public void setPosicaoFinal(Integer posicaoFinal) {
		this.posicaoFinal = posicaoFinal;
	}

	public Object getConteudo() {				
		return this.conteudo;			
	}

	public void setConteudo(Object conteudo) {
		this.conteudo = conteudo;
	}

	@Override
	public int compareTo(BeanConversor beanConversor) {		
		return this.posicaoInicial.compareTo(beanConversor.getPosicaoInicial());
	}

}
