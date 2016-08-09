package br.com.condominiosvirtuais.vo;

import java.util.Date;

import br.com.condominiosvirtuais.entity.Bloco;
import br.com.condominiosvirtuais.entity.Unidade;
import br.com.condominiosvirtuais.entity.Usuario;

public class MensagemRecebidaVO {
	
	
private Integer id;
	
	private Usuario usuarioRemetente;
	
	private Usuario usuarioDestinatario;
	
	private Bloco blocoRemetente;
	
	private Unidade unidadeRemetente;
	
	private Boolean visualizada;
	
	private Date data;
	
	private String assunto;
	
	private String texto;
	
	public MensagemRecebidaVO() {
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

	public Bloco getBlocoRemetente() {
		return blocoRemetente;
	}

	public void setBlocoRemetente(Bloco blocoRemetente) {
		this.blocoRemetente = blocoRemetente;
	}

	public Unidade getUnidadeRemetente() {
		return unidadeRemetente;
	}

	public void setUnidadeRemetente(Unidade unidadeRemetente) {
		this.unidadeRemetente = unidadeRemetente;
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
