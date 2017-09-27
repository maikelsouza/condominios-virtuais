package br.com.condominiosvirtuais.service.impl;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import br.com.condominiosvirtuais.entity.Condominio;
import br.com.condominiosvirtuais.entity.EmailUsuario;
import br.com.condominiosvirtuais.entity.Usuario;
import br.com.condominiosvirtuais.entity.UsuarioCondominio;
import br.com.condominiosvirtuais.persistence.UsuarioCondominioDAO;
import br.com.condominiosvirtuais.persistence.impl.UsuarioDAOImpl;
import br.com.condominiosvirtuais.service.CondominioService;
import br.com.condominiosvirtuais.service.EmailUsuarioService;
import br.com.condominiosvirtuais.service.GrupoUsuarioService;
import br.com.condominiosvirtuais.service.UsuarioService;

public class UsuarioServiceImpl implements UsuarioService, Serializable {
	
	private static final long serialVersionUID = 1L;

	@Inject
	private EmailUsuarioService emailUsuarioService = null;
	
	@Inject
	private GrupoUsuarioService grupoUsuarioService; 
	
	@Inject
	private CondominioService condominioService = null;
	
	@Inject
	private UsuarioDAOImpl usuarioDAO = null;
	
	@Inject
	private UsuarioCondominioDAO  usuarioCondominioDAO = null;
	
	
	/**
	 * Método que busca um usuário (com lista de e-mails mais grupo de usuário) com base no seu e-mail principal.
	 * 
	 * @param String: Representa o e-mail principal do usuário.
	 * @return Usuario: Retona null caso o e-mail principal não exita. 
	 * @throws Exception 
	 */
	public Usuario buscarPorEmailPrincipal(String emailPrincipal) throws SQLException, Exception {
		Usuario usuario = null;
		List<Condominio> listaCondominio = null;
		EmailUsuario email = this.emailUsuarioService.buscarEmail(emailPrincipal);
		// Caso não exita o e-mail principal, significa que também não exite um usuário, logo retorna null
//TODO: Inserir o algoritmo abaixo no método "usuarioDAO.buscarPorId(Integer integer);"	
		if(email != null){
			listaCondominio = new ArrayList<Condominio>();
			usuario = this.usuarioDAO.buscarPorId(email.getIdUsuario());
			usuario.setListaEmail(this.emailUsuarioService.buscarPorUsuario(usuario));
//TODO: Código comentado em 27/09/2017. Apagar em 180 dias			
//			usuario.setListaGrupoUsuario(this.grupoUsuarioService.buscarPorIdUsuarioESituacao(usuario.getId(), 
//					GrupoUsuarioSituacaoEnum.ATIVO.getSituacao()));			
			for (UsuarioCondominio usuarioCondominio : this.usuarioCondominioDAO.buscarListaPorUsuario(usuario)) {
				Condominio condominio = this.condominioService.buscarPorId(usuarioCondominio.getIdCondominio());
				listaCondominio.add(condominio);	
			}
			usuario.setListaCondominio(listaCondominio);			 	
		}
		return usuario;
	}
	
	public void atualizar(Usuario usuario) throws SQLException, Exception {
		this.usuarioDAO.atualizarUsuario(usuario);		
	}
	
	public void atualizarSenha(Usuario usuario) throws SQLException, Exception {
		this.usuarioDAO.atualizarSenha(usuario);		
	}

	@Override
	public Usuario buscarPorId(Integer idUsuario) throws SQLException, Exception {		
		return this.usuarioDAO.buscarPorId(idUsuario);
	}

	public CondominioService getCondominioService() {
		return condominioService;
	}

	public void setCondominioService(CondominioService condominioService) {
		this.condominioService = condominioService;
	}

	public EmailUsuarioService getEmailUsuarioService() {
		return emailUsuarioService;
	}

	public void setEmailUsuarioService(EmailUsuarioService emailUsuarioService) {
		this.emailUsuarioService = emailUsuarioService;
	}

	public GrupoUsuarioService getGrupoUsuarioService() {
		return grupoUsuarioService;
	}

	public void setGrupoUsuarioService(GrupoUsuarioService grupoUsuarioService) {
		this.grupoUsuarioService = grupoUsuarioService;
	}

	public UsuarioDAOImpl getUsuarioDAO() {
		return usuarioDAO;
	}

	public void setUsuarioDAO(UsuarioDAOImpl usuarioDAO) {
		this.usuarioDAO = usuarioDAO;
	}

	public UsuarioCondominioDAO getUsuarioCondominioDAO() {
		return usuarioCondominioDAO;
	}

	public void setUsuarioCondominioDAO(UsuarioCondominioDAO usuarioCondominioDAO) {
		this.usuarioCondominioDAO = usuarioCondominioDAO;
	}

	public void salvarDataHoraLogin(Usuario usuario) throws SQLException, Exception {
		this.usuarioDAO.salvarDataHoraLogin(usuario);
	}
	
	public void salvarDataHoraLogout(Usuario usuario) throws SQLException, Exception {
		this.usuarioDAO.salvarDataHoraLogout(usuario);
	}

	@Override
	public Usuario buscarSindicoGeralPorCondominio(Condominio condominio) throws SQLException, Exception {
		return this.usuarioDAO.buscarSindicoGeralPorCondominio(condominio);
	}

	@Override
	public void associar(List<Usuario> listaDeUsuarios, Integer idGrupoUsuario) throws SQLException, Exception {
		this.usuarioDAO.associarUsuariosGrupoUsuario(listaDeUsuarios, idGrupoUsuario);
		
	}

}
