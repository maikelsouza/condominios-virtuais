package br.com.condominiosvirtuais.listener;
        
import javax.faces.application.NavigationHandler;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.servlet.http.HttpSession;

import br.com.condominiosvirtuais.entity.Usuario;
import br.com.condominiosvirtuais.enumeration.PathTelasAplicacaoEnum;

             
public class AutorizacaoListener implements PhaseListener {

	
	private static final long serialVersionUID = 1L;
	
	private String paginaAnterior;

	
	public void afterPhase(PhaseEvent event) {
		String loginPage = "loginPage";
		String paginaSemAcesso = "paginaSemAcesso";
		String esqueciMinhaSenhaPage = "esqueciMinhaSenha";
		String pesquisaCondominioPage = "pesquisaCondominio";
		String cadastroCondominoPrimeiraVezPage = "cadastroCondominoPrimeiraVez";
		FacesContext facesContext = event.getFacesContext();
		String paginaCorrente = facesContext.getViewRoot().getViewId();	
		paginaAnterior = paginaCorrente;
		HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
		NavigationHandler nh = facesContext.getApplication().getNavigationHandler();
		if(paginaCorrente.equals(PathTelasAplicacaoEnum.FORM_ESQUECI_MINHA_SENHA.getPathTelas())){
			nh.handleNavigation(facesContext, null, esqueciMinhaSenhaPage);	
		}else if(paginaCorrente.equals(PathTelasAplicacaoEnum.FORM_PESQUISA_CONDOMINIO.getPathTelas())){
			nh.handleNavigation(facesContext, null, pesquisaCondominioPage);	
		}else if(paginaCorrente.equals(PathTelasAplicacaoEnum.FORM_CADASTRO_CONDOMINIO_PRIMEIRA_VEZ.getPathTelas())){
			nh.handleNavigation(facesContext, null, cadastroCondominoPrimeiraVezPage);		
		}else{
			Usuario usuario = (Usuario) session.getAttribute("autenticado");
			// Situação onde o usuário não está autenticado.		
			if(usuario == null){			
				nh.handleNavigation(facesContext, null, loginPage);			
			// Situação onde o usuário não tem acesso a página que selecionada	
			}else if(!usuario.getGrupoUsuario().temAcesso(paginaCorrente)){ 
				nh.handleNavigation(facesContext, null, paginaSemAcesso);	
			}
		}
	}

	@Override
	public void beforePhase(PhaseEvent arg0){} 

	
	public PhaseId getPhaseId() {		
		return PhaseId.RESTORE_VIEW;
	}
	
//	private a(String paginaCorrente){
//		Map<String,Set<NavigationCase>> navigationCases = ManagedBeanUtil.getMapNavigationCase();
//		Collection<Set<NavigationCase>> collectionNavigationCase = navigationCases.values();
//		Iterator<Set<NavigationCase>> iteratorCollectionNavigationCase = collectionNavigationCase.iterator();
//		Set<NavigationCase> setNavigationCase = null;
//		Iterator<NavigationCase> iteratorNavigationCase = null;
//		Iterator<GrupoUsuario> iteratorGrupoUsuario = null;
//		NavigationCase navigationCase = null;
//		GrupoUsuario grupoUsuario = null;
//		// Flag criada para garantir que não ir executar o final do primeiro e segundo while caso a view não esteja na lista de telas que os grupos de usuários tem acesso
//		Boolean continuarProcurandoNesseForm = Boolean.TRUE;
//		while (iteratorCollectionNavigationCase.hasNext() && continuarProcurandoNesseForm) {
//			setNavigationCase = iteratorCollectionNavigationCase.next();
//			iteratorNavigationCase = setNavigationCase.iterator();
//			while (iteratorNavigationCase.hasNext() && continuarProcurandoNesseForm) {
//				navigationCase = iteratorNavigationCase.next();
//				if(paginaCorrente.equals(navigationCase.getToViewId(FacesContext.getCurrentInstance()))){
//					if()
//					
//					while (iteratorGrupoUsuario.hasNext()) {
//						grupoUsuario = iteratorGrupoUsuario.next();
//						encontrou = grupoUsuario.temAcesso2(navigationCase.getToViewId(FacesContext.getCurrentInstance()));
//						if(encontrou){
//							return encontrou;
//						}
//					}
//					continuarProcurandoNesseForm = Boolean.FALSE;
//				}
//			}
//		}
//	}
//	}
}
