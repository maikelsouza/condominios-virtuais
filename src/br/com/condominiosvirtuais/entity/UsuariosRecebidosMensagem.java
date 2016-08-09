package br.com.condominiosvirtuais.entity;

/**
 * Entidade que representa os usuários que receberam uma determinada mensagem, ou seja, a lista de usuário para o qual <br>
 * foi enviada uma determinada mensagem. 
 * @author Maikel Joel de Souza
 * @since 26/03/2014
 */
public class UsuariosRecebidosMensagem {
	
	private Integer id;
	
	private Integer idUsuario;
	
	private Integer idMensagemEnviada;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Integer idUsuario) {
		this.idUsuario = idUsuario;
	}

	public Integer getIdMensagemEnviada() {
		return idMensagemEnviada;
	}

	public void setIdMensagemEnviada(Integer idMensagemEnviada) {
		this.idMensagemEnviada = idMensagemEnviada;
	}
	
	

}
