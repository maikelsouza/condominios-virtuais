package br.com.condominiosvirtuais.vo;

import java.util.Date;

import br.com.condominiosvirtuais.entity.Usuario;


public class MensagemEnviadaVO {
	
	private Integer id;
	
	private Usuario usuarioRemetente;
	
	private String dadosUsuariosDestinatarios;
	
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

	public String getDadosUsuariosDestinatarios() {
		return dadosUsuariosDestinatarios;
	}

	public void setDadosUsuariosDestinatarios(String dadosUsuariosDestinatarios) {
		this.dadosUsuariosDestinatarios = dadosUsuariosDestinatarios;
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
