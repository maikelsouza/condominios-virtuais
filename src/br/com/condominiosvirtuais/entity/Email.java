package br.com.condominiosvirtuais.entity;

/**
 * Entidade que representa o e-mail propriamente dito. Usada para agrupar informações necessárias para <br>
 * o envio de um e-mail.
 * @author Maikel Joel de Souza
 * @since 20/02/2013
 */
public class Email{
	
	private Integer id;
	
	private String para;
	
	private String de;
	
	private String cc;
	
	private String co;
	
	private String assunto;
	
	private String mensagem;
	
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPara() {
		return para;
	}

	public void setPara(String para) {
		this.para = para;
	}

	public String getDe() {
		return de;
	}

	public void setDe(String de) {
		this.de = de;
	}

	public String getCc() {
		return cc;
	}

	public void setCc(String cc) {
		this.cc = cc;
	}

	public String getCo() {
		return co;
	}

	public void setCo(String co) {
		this.co = co;
	}

	public String getAssunto() {
		return assunto;
	}

	public void setAssunto(String assunto) {
		this.assunto = assunto;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

}
