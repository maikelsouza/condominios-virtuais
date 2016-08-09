package br.com.condominiosvirtuais.entity;

import java.util.Date;

/**
 * Entidade que representa uma mensagem recebida da aplica��o. Exemplo: Um cond�nino deseja enviar uma mensagem, via aplica��o, <br>
 * para o S�ndico. 
 * @author Maikel Joel de Souza
 * @since 23/02/2013
 */
public class MensagemRecebida {
	
	private Integer id;
	
	private Usuario usuarioRemetente;
	
	private Usuario usuarioDestinatario;
	
	private Boolean visualizada;
	
	private Date data;
	
	private String assunto;
	
	private String texto;
	

	public MensagemRecebida() {
		this.usuarioRemetente = new Usuario();
		this.usuarioDestinatario = new Usuario();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}	

	public Usuario getUsuarioRemetente() {
		return usuarioRemetente;
	}

	public void setUsuarioRemetente(Usuario usuarioRemetente) {
		this.usuarioRemetente = usuarioRemetente;
	}

	public Usuario getUsuarioDestinatario() {
		return usuarioDestinatario;
	}

	public void setUsuarioDestinatario(Usuario usuarioDestinatario) {
		this.usuarioDestinatario = usuarioDestinatario;
	}

	public Boolean getVisualizada() {
		return visualizada;
	}

	public void setVisualizada(Boolean visualizada) {
		this.visualizada = visualizada;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public String getAssunto() {
		return assunto;
	}

	public void setAssunto(String assunto) {
		this.assunto = assunto;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

}
