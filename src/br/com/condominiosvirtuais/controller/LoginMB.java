package br.com.condominiosvirtuais.controller;

import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import br.com.condominiosvirtuais.entity.Condominio;
import br.com.condominiosvirtuais.entity.Email;
import br.com.condominiosvirtuais.entity.Usuario;
import br.com.condominiosvirtuais.enumeration.AtributoSessaoEnum;
import br.com.condominiosvirtuais.enumeration.UsuarioEnum;
import br.com.condominiosvirtuais.exception.BusinessException;
import br.com.condominiosvirtuais.service.EmailService;
import br.com.condominiosvirtuais.service.UsuarioService;
import br.com.condominiosvirtuais.util.AplicacaoUtil;
import br.com.condominiosvirtuais.util.ManagedBeanUtil;
import br.com.condominiosvirtuais.util.MensagensEmailUtil;

@Named @SessionScoped
public class LoginMB implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private static Logger logger = Logger.getLogger(LoginMB.class);	
	
	private Usuario usuario;
	
	private String email;
	
	private String senha;
	
	private Boolean autenticado;
	
	@Inject
	private UsuarioService usuarioService = null;
	
	@Inject 
	private EmailService emailService;
	
	
	public String login() throws NoSuchAlgorithmException{
		this.usuario = null;
		this.autenticado = Boolean.FALSE;		
		List<String> condominiosSemSindicoGeral = new ArrayList<String>();
		try {
			this.usuario = usuarioService.buscarPorEmailPrincipal(this.email);		
			if(this.usuario != null && usuario.getSenha().equals(AplicacaoUtil.gerarHashMD5(this.senha))){
				ManagedBeanUtil.getSession(Boolean.TRUE).setAttribute(AtributoSessaoEnum.AUTENTICADO.getAtributo(), this.usuario);				
				this.autenticado = Boolean.TRUE;
			}else{
				ManagedBeanUtil.setMensagemErro("msg.usuario.email.senha.incorretos");	
				return null;
			}				
			if (this.usuario.getSituacao() == UsuarioEnum.INATIVO.getSituacao()){
				ManagedBeanUtil.setMensagemErro("msg.usuario.situacao.inativo");
				return null;
			}			
			for (Condominio condominio : this.usuario.getListaCondominio()) {
				if(condominio.getSindicoGeral() == null){
					condominiosSemSindicoGeral.add(condominio.getNome());	
				}
			}
			for (String mensagem: condominiosSemSindicoGeral){
				ManagedBeanUtil.setMensagemWarn("msg.condominio.semSindicoGeral"," : "+mensagem);	
			}
			this.salvarDataHoraLogin();			
		}catch (SQLException e) {
			logger.error("erro sqlstate "+e.getSQLState(), e);	
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
			return null;
		} catch (BusinessException e) {				
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage());
			return null;
		} catch (Exception e) {
			logger.error("", e);
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
			return null;
		}
		return "logar";
	}
	
	/**
	 * M�todo de desloga o usu�rio, direcionando ele para a tela de login.
	 */
	public String logout() {
		try {
			HttpSession session = ManagedBeanUtil.getSession(Boolean.FALSE);
			session.removeAttribute(AtributoSessaoEnum.AUTENTICADO.getAtributo());		
			session.invalidate();
			this.autenticado = Boolean.FALSE;
			this.salvarDataHoraLogout();
		}catch (SQLException e) {
			logger.error("erro sqlstate "+e.getSQLState(), e);	
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
			return null;
		} catch (BusinessException e) {				
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage());
			return null;
		} catch (Exception e) {
			logger.error("", e);
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
			return null;
		}
		return "logout";
	 }
	
	/**
	 * M�dodo que verifica se o usu�rio logado pertence a um dos grupos informados 
	 * @param idGrupo = Recebe uma lista de id's de grupos usu�rios, separados por v�rgula.
	 * @return Boolean = Informa se o usu�rio logado pertence a um dos grupos informados
	 */
	public Boolean usuarioLogadoPertenceUmDosGrupos(String idGrupoUsuario){
		Boolean usuarioLogadoPertenceUmDosGrupos = Boolean.FALSE;
		StringTokenizer tokenizer = new StringTokenizer(idGrupoUsuario, ",");
		while (tokenizer.hasMoreTokens() && !usuarioLogadoPertenceUmDosGrupos){
			if(this.usuario.getGrupoUsuario().getId() == Integer.parseInt(tokenizer.nextToken())){
				usuarioLogadoPertenceUmDosGrupos = Boolean.TRUE;
			}			   
		}		
		return usuarioLogadoPertenceUmDosGrupos;
	}    
	
	/**
	 * FIXME: M�todo criado para n�o exibir o menu financeiro.
	 * Esse deve ser apagado ap�s mudar o layout do menu
	 */
// C�digo comentado em 28/12/2016. Apagar em 180 dias	
//	public Boolean exibeMenuFinanceiro(){
//		Boolean exibeMenuFianceiro = Boolean.FALSE;		
//		for (Condominio condominio : this.usuario.getListaCondominio()) {
//			if (condominio.getId() == 22 || condominio.getId() == 23){
//				exibeMenuFianceiro = Boolean.TRUE;
//			}
//		}
//		return exibeMenuFianceiro;
//	}    
	
	
	public Boolean sindicoGeralAssociado(){
		Boolean sindicoGeralAssociado = Boolean.TRUE;
//		// Regra v�lida somente para usu�rio n�o admin
		for (Condominio condominio : this.usuario.getListaCondominio()) {
			if(condominio.getSindicoGeral() == null){
				sindicoGeralAssociado  = Boolean.FALSE;
			}
		}
		return sindicoGeralAssociado;
	}
	
	public String gerarSenha(){
		String logar = null;
		try {
			Usuario usuario = null;
			Email email = null;
			usuario = this.usuarioService.buscarPorEmailPrincipal(this.email);
			if(usuario == null){
				ManagedBeanUtil.setMensagemErro("msg.usuario.email.inexistente");
			}else{				
				String alfabeto = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";		
				String novaSenha = "";
				for (int i = 0; i < 3; i++) {
					int j = (int) (Math.random() * alfabeto.length());
					int k = 1 + (int) (Math.random() * 10);
					novaSenha+=alfabeto.charAt(j);
					novaSenha+=String.valueOf(k);			
				}
				usuario.setSenha(novaSenha);
				this.usuarioService.atualizarSenha(usuario);
				email = new Email();
				email.setPara(usuario.getEmail().getEmail());
				email.setAssunto(AplicacaoUtil.i18n("msg.usuarioNovaSenhaGerada.assunto"));
//				Object[] parametros = new Object[2];
//				parametros[0] = usuario.getNome().toUpperCase();
//				parametros[1] = senha;
				email.setMensagem(MensagensEmailUtil.novaSenhaGerada(usuario, novaSenha));
				this.emailService.salvar(email);
				logar = "logar";
				ManagedBeanUtil.setMensagemInfo("msg.usuario.gerarsenha.sucesso");				
				}	
		} catch (SQLException e) {
			logger.error("erro sqlstate "+e.getSQLState(), e);	
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
			return null;
		} catch (BusinessException e) {				
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage());
			return null;
		} catch (Exception e) {
			logger.error("", e);
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
			return null;
		}
		return logar;
	}
	
	public String getMensagemBoasVindas() {
		String msg = null;
		String primeiroNomeUsuario = null;
		Date dataAtual = new Date();
		Calendar calendarManhaDia = Calendar.getInstance();
		calendarManhaDia.setTime(new Date());
		calendarManhaDia.set(Calendar.HOUR_OF_DAY, 06);
		calendarManhaDia.set(Calendar.MINUTE, 00);
		Calendar calendarMeioDia = Calendar.getInstance();
		calendarMeioDia.setTime(new Date());
		calendarMeioDia.set(Calendar.HOUR_OF_DAY, 12);
		calendarMeioDia.set(Calendar.MINUTE, 00);
		Calendar calendarFimDia = Calendar.getInstance();
		calendarFimDia.setTime(new Date());
		calendarFimDia.set(Calendar.HOUR_OF_DAY, 18);
		calendarFimDia.set(Calendar.MINUTE, 00);	
		// Caso contenha a string branco, ent�o pega o primeiro nome do usu�rio (sendo o branco como delimitador)
		if(this.getUsuario().getNome().contains(" ")){
			primeiroNomeUsuario = this.getUsuario().getNome().substring(0, this.getUsuario().getNome().indexOf(" "));
		}else{
			primeiroNomeUsuario = this.getUsuario().getNome();
		}
		// Caracteriza o per�odo da manh� 
		if(dataAtual.after(calendarManhaDia.getTime()) && dataAtual.before(calendarMeioDia.getTime())){
			msg = AplicacaoUtil.i18n("bomDia") +", " + primeiroNomeUsuario;
		// Caracteriza o per�odo da tarde
		}else if (dataAtual.after(calendarMeioDia.getTime()) && dataAtual.before(calendarFimDia.getTime())){
			msg = AplicacaoUtil.i18n("boaTarde") +", " + primeiroNomeUsuario;
		// Caracteriza o per�odo da noite
		}else{
			msg = AplicacaoUtil.i18n("boaNoite") +", "  + primeiroNomeUsuario;
		}		
		return msg;
	}
	
	private void salvarDataHoraLogin() throws SQLException, Exception{
		this.usuario.setUltimoLogin(new Date());
		this.usuarioService.salvarDataHoraLogin(this.usuario);
	}
	
	private void salvarDataHoraLogout() throws SQLException, Exception{
		this.usuario.setUltimoLogout(new Date());
		this.usuarioService.salvarDataHoraLogout(usuario);
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	public Boolean getAutenticado() {
		return autenticado;
	}

	public void setAutenticado(Boolean autenticado) {
		this.autenticado = autenticado;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}	
	
	public String esqueciMinhaSenha(){	
		return "esqueciMinhaSenha";
	}
	
	public String pesquisaCondominio(){	
		return "pesquisaCondominio";
	}
	
	public String cancelarEsqueciMinhaSenha(){
		return "cancelar";
	}	

	public UsuarioService getUsuarioService() {
		return usuarioService;
	}

	public void setUsuarioService(UsuarioService usuarioService) {
		this.usuarioService = usuarioService;
	}

	public EmailService getEmailService() {
		return emailService;
	}

	public void setEmailService(EmailService emailService) {
		this.emailService = emailService;
	}

}
