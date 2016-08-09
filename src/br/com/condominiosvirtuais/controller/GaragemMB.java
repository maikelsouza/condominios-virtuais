package br.com.condominiosvirtuais.controller;

import java.io.Serializable;
import java.sql.SQLException;

import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIInput;
import javax.faces.event.ActionEvent;
import javax.faces.model.ListDataModel;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.log4j.Logger;

import br.com.condominiosvirtuais.entity.Garagem;
import br.com.condominiosvirtuais.enumeration.GaragemEnum;
import br.com.condominiosvirtuais.exception.BusinessException;
import br.com.condominiosvirtuais.service.GaragemService;
import br.com.condominiosvirtuais.util.ManagedBeanUtil;

@Named @SessionScoped
public class GaragemMB implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private static Logger logger = Logger.getLogger(GaragemMB.class);
	
	private Garagem garagem;	
	
	@Inject
	private GaragemService garagemService;
	
	@Inject
	private Conversation conversation;
	
	private ListDataModel<Garagem> listaGaragem = null;
	
	private UIInput componenteNumeroGaragem;

	
	public GaragemMB(){		
		this.garagem = new Garagem();	
	}
	
	public void popularListaGaragem(){		
		try {
			this.listaGaragem = new ListDataModel<Garagem>(this.garagemService.buscarPorIdUnidade((Integer) ManagedBeanUtil.getSession(false).getAttribute("idUnidade")));
		} catch (SQLException e) {
			logger.error("erro sqlstate "+e.getSQLState(), e);	
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");				
		} catch (Exception e) {
			logger.error("", e);
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		}
	}
	
	public void salvarGaragem(ActionEvent event){			
		this.garagem.setTamanho(GaragemEnum.MEDIO.getTamanho());
		this.garagem.setIdUnidade((Integer) ManagedBeanUtil.getSession(false).getAttribute("idUnidade"));
		try {
			this.garagemService.salvar(this.garagem);
			ManagedBeanUtil.setMensagemInfo("msg.garagem.salvaSucesso");	
		} catch (SQLException e) {
			logger.error("erro sqlstate "+e.getSQLState(), e);	
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		} catch (BusinessException e) {				
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage());				
		} catch (Exception e) {
			logger.error("", e);
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		}
	}
	
	public String atualizarGaragem(){
		try {
			this.garagemService.atualizar(this.garagem);
			ManagedBeanUtil.setMensagemInfo("msg.garagem.atualizadoSucesso");
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
		return "atualizar";
	}
	
	public String excluirGaragem(){
		try {
			this.garagemService.excluir(this.garagem);		
			ManagedBeanUtil.setMensagemInfo("msg.garagem.excluidaSucesso");
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
		return "excluir";
	}
	
	public String cancelarEditarGaragem(){
		this.garagem = new Garagem();
		return "cancelar";
	}
	
	public void limparGaragem(ActionEvent event){			
		this.garagem = new Garagem();
		ManagedBeanUtil.cleanSubmittedValues(this.componenteNumeroGaragem);		
	}
	
	public String voltarCadastroGaragem(){		 
		return "voltar";
	}
	
	public String editarGaragem(){
		this.garagem = this.listaGaragem.getRowData();
		return "editar";
	}

	public ListDataModel<Garagem> getListaGaragem() {
		return listaGaragem;
	}

	public void setListaGaragem(ListDataModel<Garagem> listaGaragem) {
		this.listaGaragem = listaGaragem;
	}

	public Garagem getGaragem() {
		return garagem;
	}

	public void setGaragem(Garagem garagem) {
		this.garagem = garagem;
	}	

	public GaragemService getGaragemService() {
		return garagemService;
	}

	public void setGaragemService(GaragemService garagemService) {
		this.garagemService = garagemService;
	}

	public Conversation getConversation() {
		return conversation;
	}

	public void setConversation(Conversation conversation) {
		this.conversation = conversation;
	}

	public UIInput getComponenteNumeroGaragem() {
		return componenteNumeroGaragem;
	}

	public void setComponenteNumeroGaragem(UIInput componenteNumeroGaragem) {
		this.componenteNumeroGaragem = componenteNumeroGaragem;
	}
	

}
