package br.com.condominiosvirtuais.controller;

import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.NavigationCase;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import br.com.condominiosvirtuais.entity.Condominio;
import br.com.condominiosvirtuais.entity.Email;
import br.com.condominiosvirtuais.entity.GrupoUsuario;
import br.com.condominiosvirtuais.entity.Usuario;
import br.com.condominiosvirtuais.enumeration.AtributoSessaoEnum;
import br.com.condominiosvirtuais.enumeration.UsuarioSituacaoEnum;
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
			if (this.usuario.getSituacao() == UsuarioSituacaoEnum.INATIVO.getSituacao()){
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
	 * Método de desloga o usuário, direcionando ele para a tela de login.
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
	 * Médodo que verifica se o usuário logado pertence a um dos grupos informados 
	 * @param idGrupo = Recebe uma lista de id's de grupos usuários, separados por vírgula.
	 * @return Boolean = Informa se o usuário logado pertence a um dos grupos informados
	 * TODO: Código comentado em 28/09/2017. Apagar em 180 dias 
	 * 
	 */	
//	public Boolean usuarioLogadoPertenceUmDosGrupos(String idGrupoUsuario){
//		Boolean usuarioLogadoPertenceUmDosGrupos = Boolean.FALSE;
//		StringTokenizer tokenizer = new StringTokenizer(idGrupoUsuario, ",");
//		while (tokenizer.hasMoreTokens() && !usuarioLogadoPertenceUmDosGrupos){
//			if(this.usuario.getGrupoUsuario().getId() == Integer.parseInt(tokenizer.nextToken())){
//				usuarioLogadoPertenceUmDosGrupos = Boolean.TRUE;
//			}			   
//		}		
//		return usuarioLogadoPertenceUmDosGrupos;
//	}
	
	public Boolean temAcesso(String... actions){	
		Boolean encontrou = Boolean.FALSE;
		for (String action : actions) {
			Map<String,Set<NavigationCase>> navigationCases = ManagedBeanUtil.getMapNavigationCase();
			Collection<Set<NavigationCase>> collectionNavigationCase = navigationCases.values();
			Iterator<Set<NavigationCase>> iteratorCollectionNavigationCase = collectionNavigationCase.iterator();
			Set<NavigationCase> setNavigationCase = null;
			Iterator<NavigationCase> iteratorNavigationCase = null;
			Iterator<GrupoUsuario> iteratorGrupoUsuario = null;
			NavigationCase navigationCase = null;
			GrupoUsuario grupoUsuario = null;
			// Flag criada para garantir que não ir executar o final do primeiro e segundo while caso a view não esteja na lista de telas que os grupos de usuários tem acesso
			Boolean continuarProcurandoNesseForm = Boolean.TRUE;
			while (iteratorCollectionNavigationCase.hasNext() && continuarProcurandoNesseForm) {
				setNavigationCase = iteratorCollectionNavigationCase.next();
				iteratorNavigationCase = setNavigationCase.iterator();
				while (iteratorNavigationCase.hasNext() && continuarProcurandoNesseForm) {
					navigationCase = iteratorNavigationCase.next();					
					if(action.toString().equals(navigationCase.getFromAction())){
						iteratorGrupoUsuario = this.usuario.getListaGrupoUsuario().iterator();
						while (iteratorGrupoUsuario.hasNext()) {
							grupoUsuario = iteratorGrupoUsuario.next();
							encontrou = grupoUsuario.existeTela(navigationCase.getToViewId(FacesContext.getCurrentInstance()));
							if(encontrou){
								return encontrou;
							}
						}
						continuarProcurandoNesseForm = Boolean.FALSE;
					}
				}
			}
		}
		return encontrou;
	}
	
	public Boolean temAcessoComponente(String... actions){
		Iterator<GrupoUsuario> iteratorGrupoUsuario = null;
		GrupoUsuario grupoUsuario = null;
		for (String action : actions) {
			iteratorGrupoUsuario = this.usuario.getListaGrupoUsuario().iterator();
			while (iteratorGrupoUsuario.hasNext()) {
				grupoUsuario = iteratorGrupoUsuario.next();
				if(grupoUsuario.existeComponente(action)){
					return Boolean.TRUE;	
				}
			}
		}
		return Boolean.FALSE;
	}
	
	public Boolean temAcessoAba(String... actions){
		Iterator<GrupoUsuario> iteratorGrupoUsuario = null;
		GrupoUsuario grupoUsuario = null;
		for (String action : actions) {
			iteratorGrupoUsuario = this.usuario.getListaGrupoUsuario().iterator();
			while (iteratorGrupoUsuario.hasNext()) {
				grupoUsuario = iteratorGrupoUsuario.next();
				if(grupoUsuario.existeAba(action)){
					return Boolean.TRUE;	
				}
			}
		}
		return Boolean.FALSE;
	}
	
	/**
	 * FIXME: Método criado para não exibir o menu financeiro.
	 * Esse deve ser apagado após mudar o layout do menu
	 * OBS: Regra chumbada no código. Deve ser repensada para deixar configurável
	 * TODO: Código comentado em 28/09/2017. Apagar em 180 dias
	 */	
//	public Boolean exibeMenuFinanceiro(){
//		Boolean exibeMenuFianceiro = Boolean.TRUE;
//		if (this.usuario.getId() != 280 &&  this.usuario.getId() != 1) { // Caso não seja o usuário Ivan, então deve aplicar a regra abaixo
//			for (Condominio condominio : this.usuario.getListaCondominio()) {
//				if (condominio.getId() == 20){ // Caso seja o condomínio SANTA ANA, então não deve exibir
//					exibeMenuFianceiro = Boolean.FALSE;
//				}
//			}			
//		}
//		return exibeMenuFianceiro;
//	}    
	
	
	/**
	 * FIXME: Método criado para atender o condomínio Quinta do Horto (id 19)
	 * Esse deverá ser removido após criar os grupos de usuários e suas autorizações.
	 * @return True: - Exibe - False: Não exibe 
	 * TODO: Código comentado em 28/09/2017. Apagar em 180 dias
	 */
//	public Boolean exibeMenuArquivoAdvogadoQuintaDoHorto(){
//		Boolean exibeMenuArquivo = Boolean.FALSE;
//		if(this.usuario.getId() == 367){
//			exibeMenuArquivo = Boolean.TRUE;
//		}		
//		return exibeMenuArquivo;
//	}
	
	/**
	 * FIXME: Método criado para atender o condomínio Quinta do Horto (id 19)
	 * Esse deverá ser removido após criar os grupos de usuários e suas autorizações.
	 * @return True: - Exibe - False: Não exibe 
	 * TODO: Código comentado em 28/09/2017. Apagar em 180 dias
	 */
//	public Boolean exibeMenuEscreverMensagemQuintaDoHorto(){
//		Boolean exibeMenuEscreverMensagem = Boolean.TRUE;
//		for (Condominio condominio : this.usuario.getListaCondominio()) {
//			if (condominio.getId() == 19){ // Caso seja o condomínio Quinta do Horto, então não deve exibir a opção de escrever mensagens para os condôminos
//				exibeMenuEscreverMensagem = Boolean.FALSE;
//				// id usuário síndico  José de Oliveira Júnior
//				if(this.usuario.getId() == 236 || this.usuario.getId() == 1){
//					exibeMenuEscreverMensagem = Boolean.TRUE;
//				}
//			}			
//		}
//		return exibeMenuEscreverMensagem;
//	}
	
	/**
	 * FIXME: Método criado para atender o condomínio Quinta do Horto (id 19)
	 * Esse deverá ser removido após criar os grupos de usuários e suas autorizações.
	 * @return True: - Exibe - False: Não exibe 
	 * TODO: Código comentado em 28/09/2017. Apagar em 180 dias
	 */
//	public Boolean exibeMenuArquivoContadorQuintaDoHorto(){
//		Boolean exibeMenuArquivo = Boolean.FALSE;
//		if(this.usuario.getId() == 365){
//			exibeMenuArquivo = Boolean.TRUE;
//		}		
//		return exibeMenuArquivo;
//	}
	
	/**
	 * FIXME: Método criado para atender o condomínio Quinta do Horto (id 19)
	 * Esse deverá ser removido após criar os grupos de usuários e suas autorizações.
	 * @return True: - Exibe - False: Não exibe 
	 */

	public Boolean exibeMenuEscreverMensagemQuintaDoHorto(){
		Boolean exibeMenuEscreverMensagem = Boolean.TRUE;
		for (Condominio condominio : this.usuario.getListaCondominio()) {
			if (condominio.getId() == 19){ // Caso seja o condomínio Quinta do Horto, então não deve exibir a opção de escrever mensagens para os condôminos
				exibeMenuEscreverMensagem = Boolean.FALSE;
				// id usuário síndico  José de Oliveira Júnior
				if(this.usuario.getId() == 236 || this.usuario.getId() == 1){
					exibeMenuEscreverMensagem = Boolean.TRUE;
				}
			}			
		}
		return exibeMenuEscreverMensagem;
	}
	
	/**
	 * FIXME: Método criado para atender o condomínio Quinta do Horto (id 19)
	 * Esse deverá ser removido após criar os grupos de usuários e suas autorizações.
	 * @return True: - Exibe - False: Não exibe 
	 */
	public Boolean exibeMenuArquivoContadorQuintaDoHorto(){
		Boolean exibeMenuArquivo = Boolean.FALSE;
		if(this.usuario.getId() == 365){
			exibeMenuArquivo = Boolean.TRUE;
		}		
		return exibeMenuArquivo;
	}
	
	/**
	 * FIXME: Método criado para atender o condomínio Quinta do Horto (id 19)
	 * Esse deverá ser removido após criar os grupos de usuários e suas autorizações.
	 * @return True: - Exibe - False: Não exibe 
	 */
	public Boolean exibeMenuListaReservaQuintaDoHorto(){
		Boolean exibeMenuArquivo = Boolean.FALSE;
		if(this.usuario.getId() == 368 || this.usuario.getId() == 369 || this.usuario.getId() == 425 || this.usuario.getId() == 469 || this.usuario.getId() == 514){
			exibeMenuArquivo = Boolean.TRUE;
		}		
		return exibeMenuArquivo;
	}


	
	public Boolean sindicoGeralAssociado(){
		Boolean sindicoGeralAssociado = Boolean.TRUE;
		// Regra válida somente para usuário não admin
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
		// Caso contenha a string branco, então pega o primeiro nome do usuário (sendo o branco como delimitador)
		if(this.getUsuario().getNome().contains(" ")){
			primeiroNomeUsuario = this.getUsuario().getNome().substring(0, this.getUsuario().getNome().indexOf(" "));
		}else{
			primeiroNomeUsuario = this.getUsuario().getNome();
		}
		// Caracteriza o período da manhã 
		if(dataAtual.after(calendarManhaDia.getTime()) && dataAtual.before(calendarMeioDia.getTime())){
			msg = AplicacaoUtil.i18n("bomDia") +", " + primeiroNomeUsuario;
		// Caracteriza o período da tarde
		}else if (dataAtual.after(calendarMeioDia.getTime()) && dataAtual.before(calendarFimDia.getTime())){
			msg = AplicacaoUtil.i18n("boaTarde") +", " + primeiroNomeUsuario;
		// Caracteriza o período da noite
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
