package br.com.condominiosvirtuais.controller;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIInput;
import javax.faces.event.ActionEvent;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.log4j.Logger;

import br.com.condominiosvirtuais.entity.Condominio;
import br.com.condominiosvirtuais.entity.TelefonesUteis;
import br.com.condominiosvirtuais.service.TelefonesUteisService;
import br.com.condominiosvirtuais.util.ManagedBeanUtil;

@Named @SessionScoped
public class TelefonesUteisMB implements IConversationScopeMB, Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Inject
	private Conversation conversation;

	private static Logger logger = Logger.getLogger(TelefonesUteisMB.class);
		
	@Inject
	private TelefonesUteisService telefonesUteisService;
	
	private ListDataModel<TelefonesUteis> listaTelefonesUteis;
	
	private Condominio condominio;
	
	private TelefonesUteis telefonesUteis;
	
	@Inject
	private CondominioMB condominioMB = null;
	
	private List<SelectItem> listaSICondominios = null;
	
	private UIInput componenteNomeTelefonesUteis;
	
	private UIInput componenteSiteTelefonesUteis;
	
	private UIInput componenteDescricaoTelefonesUteis;
	
	private UIInput componenteTelefone1TelefonesUteis;
	
	private UIInput componenteTelefone2TelefonesUteis;
	
	private UIInput componenteEmailTelefonesUteis;	
	

	@PostConstruct
	public void iniciarTelefonesUteisMB(){
		this.condominio = new Condominio();		
		this.listaSICondominios = this.condominioMB.buscarListaCondominiosAtivos();
		this.telefonesUteis = new TelefonesUteis();
	}


	
	public void pesquisar(ActionEvent event){ 	
		try {
			this.abreSessao();
			this.listaTelefonesUteis = new ListDataModel<TelefonesUteis>(this.telefonesUteisService.buscarPorCondominio(this.condominio));
			if (this.listaTelefonesUteis.getRowCount() == 0){
				ManagedBeanUtil.setMensagemInfo("msg.telefonesUteis.semTelefonesUteis");
			}
		} catch (SQLException e) {
			logger.error("erro sqlstate "+e.getSQLState(), e);	
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");				
		} catch (Exception e) {
			logger.error("", e);
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		}
	}
	
	public String cancelarCadastroTelefonesUteis(){	
		this.telefonesUteis = new TelefonesUteis();
		this.limpaFormTelefonesUties();
		this.pesquisar(null);
		return "cancelar";
	}	
	
	public void limparFiltroTelefonesUteis(ActionEvent event){
		this.telefonesUteis = new TelefonesUteis();
		this.listaTelefonesUteis = new ListDataModel<TelefonesUteis>();
		this.limpaFormTelefonesUties();
	}
	
	public String editarTelefonesUteis(){
		this.telefonesUteis = this.listaTelefonesUteis.getRowData();
		return "editar";
	}
	
	public String excluirTelefonesUteis(){	
		try {
			this.telefonesUteisService.excluir(this.telefonesUteis);
			this.telefonesUteis = new TelefonesUteis();
			this.pesquisar(null);
			ManagedBeanUtil.setMensagemInfo("msg.telefonesUteis.excluirSucesso");
			return "excluir";
		} catch (SQLException e) {
			logger.error("erro sqlstate "+e.getSQLState(), e);	
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");	
			return null;
		} catch (Exception e) {
			logger.error("", e);
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
			return null;
		}
	}
	
	public String atualizarTelefonesUteis(){
		this.AdicionaHttpOuHttps();
		try {
			if (this.telefonesUteis.getEmail() != null && this.telefonesUteis.getEmail().trim() != ""  && !ManagedBeanUtil.validaEmail(this.telefonesUteis.getEmail())){
				ManagedBeanUtil.setMensagemErro("msg.telefonesUteis.formatoEmailInvalido");
				return null;
			}
			this.telefonesUteisService.atualizar(this.telefonesUteis);
			this.pesquisar(null);
			ManagedBeanUtil.setMensagemInfo("msg.telefonesUteis.atualizarSucesso");
		} catch (SQLException e) {
			logger.error("erro sqlstate "+e.getSQLState(), e);	
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");	
		} catch (Exception e) {
			logger.error("", e);
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		}
		return "atualizar";
	}
	
	public String cadastrarTelefonesUteis(){
		this.telefonesUteis = new TelefonesUteis();	
		this.limpaFormTelefonesUties();
 		return "cadastrar";
	}
	
	public String salvarTelefonesUteis(){
		this.AdicionaHttpOuHttps();
		try {
			if (this.telefonesUteis.getEmail() != null && this.telefonesUteis.getEmail().trim() != "" && !ManagedBeanUtil.validaEmail(this.telefonesUteis.getEmail())){
				ManagedBeanUtil.setMensagemErro("msg.telefonesUteis.formatoEmailInvalido");
				return null;
			}
			this.telefonesUteisService.salvar(this.telefonesUteis);
			this.pesquisar(null);
			ManagedBeanUtil.setMensagemInfo("msg.telefonesUteis.salvoSucesso");
		} catch (SQLException e) {
			logger.error("erro sqlstate "+e.getSQLState(), e);	
			ManagedBeanUtil.setMensagemErro(e.getMessage() != null ? e.getMessage() : "msg.erro.executarOperacao");	
		} catch (Exception e) {
			logger.error("", e);
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		}
		return "salvar";
	}
	
//	public void finalizaScope(){
//		ManagedBeanUtil.fechaSessao(conversation);
//	}

	private void AdicionaHttpOuHttps() {
		if (!this.telefonesUteis.getSite().trim().isEmpty()){
			if(!this.telefonesUteis.getSite().startsWith("http://") && !this.telefonesUteis.getSite().startsWith("https://")){
				String url = "http://" + this.telefonesUteis.getSite();
				this.telefonesUteis.setSite(url);
			}			
		}
	}
	
	
	private void limpaFormTelefonesUties(){
		ManagedBeanUtil.cleanSubmittedValues(this.componenteNomeTelefonesUteis);
		ManagedBeanUtil.cleanSubmittedValues(this.componenteSiteTelefonesUteis);
		ManagedBeanUtil.cleanSubmittedValues(this.componenteDescricaoTelefonesUteis);
		ManagedBeanUtil.cleanSubmittedValues(this.componenteTelefone1TelefonesUteis);
		ManagedBeanUtil.cleanSubmittedValues(this.componenteTelefone2TelefonesUteis);
		ManagedBeanUtil.cleanSubmittedValues(this.componenteEmailTelefonesUteis);
	}

	public ListDataModel<TelefonesUteis> getListaTelefonesUteis() {
		return listaTelefonesUteis;
	}

	public void setListaTelefonesUteis(ListDataModel<TelefonesUteis> listaTelefonesUteis) {
		this.listaTelefonesUteis = listaTelefonesUteis;
	}

	public Condominio getCondominio() {
		return condominio;
	}

	public void setCondominio(Condominio condominio) {
		this.condominio = condominio;
	}

	public List<SelectItem> getListaSICondominios() {
		return listaSICondominios;
	}

	public void setListaSICondominios(List<SelectItem> listaSICondominios) {
		this.listaSICondominios = listaSICondominios;
	}

	public TelefonesUteis getTelefonesUteis() {
		return telefonesUteis;
	}
	
	public void setTelefonesUteis(TelefonesUteis telefonesUteis) {
		this.telefonesUteis = telefonesUteis;
	}	


	public UIInput getComponenteNomeTelefonesUteis() {
		return componenteNomeTelefonesUteis;
	}

	public void setComponenteNomeTelefonesUteis(UIInput componenteNomeTelefonesUteis) {
		this.componenteNomeTelefonesUteis = componenteNomeTelefonesUteis;
	}

	public UIInput getComponenteSiteTelefonesUteis() {
		return componenteSiteTelefonesUteis;
	}

	public void setComponenteSiteTelefonesUteis(UIInput componenteSiteTelefonesUteis) {
		this.componenteSiteTelefonesUteis = componenteSiteTelefonesUteis;
	}

	public UIInput getComponenteDescricaoTelefonesUteis() {
		return componenteDescricaoTelefonesUteis;
	}

	public void setComponenteDescricaoTelefonesUteis(
			UIInput componenteDescricaoTelefonesUteis) {
		this.componenteDescricaoTelefonesUteis = componenteDescricaoTelefonesUteis;
	}

	public UIInput getComponenteTelefone1TelefonesUteis() {
		return componenteTelefone1TelefonesUteis;
	}

	public void setComponenteTelefone1TelefonesUteis(
			UIInput componenteTelefone1TelefonesUteis) {
		this.componenteTelefone1TelefonesUteis = componenteTelefone1TelefonesUteis;
	}

	public UIInput getComponenteTelefone2TelefonesUteis() {
		return componenteTelefone2TelefonesUteis;
	}

	public void setComponenteTelefone2TelefonesUteis(
			UIInput componenteTelefone2TelefonesUteis) {
		this.componenteTelefone2TelefonesUteis = componenteTelefone2TelefonesUteis;
	}

	public UIInput getComponenteEmailTelefonesUteis() {
		return componenteEmailTelefonesUteis;
	}

	public void setComponenteEmailTelefonesUteis(
			UIInput componenteEmailTelefonesUteis) {
		this.componenteEmailTelefonesUteis = componenteEmailTelefonesUteis;
	}

	@Override
	public void abreSessao() {
		ManagedBeanUtil.abreSessao(conversation);
		
	}

	@Override
	public void fechaSessao() {
		ManagedBeanUtil.fechaSessao(conversation);		
	}

	public TelefonesUteisService getTelefonesUteisService() {
		return telefonesUteisService;
	}

	public void setTelefonesUteisService(TelefonesUteisService telefonesUteisService) {
		this.telefonesUteisService = telefonesUteisService;
	}




	public CondominioMB getCondominioMB() {
		return condominioMB;
	}




	public void setCondominioMB(CondominioMB condominioMB) {
		this.condominioMB = condominioMB;
	}




	public Conversation getConversation() {		
		return conversation;
	}
	public void setConversation(Conversation conversation) {
		this.conversation = conversation;		
	}	

}
