package br.com.condominiosvirtuais.entity;

import java.util.Date;
import java.util.List;

public class MensagemEnviada {
	
	private Integer id;
	
	private Usuario usuarioRemetente;
	
	private List<Usuario> listaUsuariosDestinatarios;
	
	private Date data;
	
	private String assunto;
	
	private String texto;

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

	public List<Usuario> getListaUsuariosDestinatarios() {
		return listaUsuariosDestinatarios;
	}

	public void setListaUsuariosDestinatarios(
			List<Usuario> listaUsuariosDestinatarios) {
		this.listaUsuariosDestinatarios = listaUsuariosDestinatarios;
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
