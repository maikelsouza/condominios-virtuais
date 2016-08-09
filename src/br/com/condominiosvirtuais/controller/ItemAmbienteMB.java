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

import br.com.condominiosvirtuais.entity.Ambiente;
import br.com.condominiosvirtuais.entity.ItemAmbiente;
import br.com.condominiosvirtuais.service.ItemAmbienteService;
import br.com.condominiosvirtuais.util.ManagedBeanUtil;

@Named @SessionScoped
public class ItemAmbienteMB implements IConversationScopeMB, Serializable{	
	
	private static final long serialVersionUID = 1L;

	private static Logger logger = Logger.getLogger(ItemAmbienteMB.class);
	
	@Inject
	private Conversation conversation;
	
	private Ambiente ambiente = null;
	
	private ItemAmbiente itemAmbiente = null;
	
	private ListDataModel<ItemAmbiente> listaItemAmbiente = null;
	
	@Inject
	private ItemAmbienteService itemAmbienteService = null;
	
	// Atributo que identifica se o item do ambiente é de um ambiente de um condomíno ou conjunto de blocos.
	private Boolean itemAmbienteEhCondominio;
	
	private UIInput componenteNomeItemAmbiente;
	
	private UIInput componenteQuantidadeItemAmbiente;
	
	
	public ItemAmbienteMB(){
		this.ambiente = new Ambiente();
		this.itemAmbiente = new ItemAmbiente();
	}
	
	
	public String salvarItemAmbiente(){		
		try {
			this.itemAmbiente.setIdAmbiente(this.ambiente.getId());
			this.itemAmbienteService.salvar(this.itemAmbiente);
			this.popularListaItemAmbiente();
			this.itemAmbiente = new ItemAmbiente();
			ManagedBeanUtil.setMensagemInfo("msg.itemAmbiente.salvaSucesso");
		} catch (SQLException e) {
			logger.error("erro sqlstate "+e.getSQLState(), e);	
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");				
		} catch (Exception e) {
			logger.error("", e);
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		}
		return null;	
	}
	
	public String editarItemAmbiente(){	
		this.itemAmbiente = this.listaItemAmbiente.getRowData();		
		return "editar";	
	}
	
	public String excluirItemAmbiente(){	
		try {
			this.itemAmbienteService.excluir(this.itemAmbiente);
			this.itemAmbiente = new ItemAmbiente();
			ManagedBeanUtil.setMensagemInfo("msg.itemAmbiente.excluirSucesso");
		} catch (SQLException e) {
			logger.error("erro sqlstate "+e.getSQLState(), e);	
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");				
		} catch (Exception e) {
			logger.error("", e);
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		}
		return "excluir";	
	}
	
	public String atualizarItemAmbiente(){		
		try {
			this.itemAmbienteService.atualizar(this.itemAmbiente);
			this.itemAmbiente = new ItemAmbiente();
			ManagedBeanUtil.setMensagemInfo("msg.itemAmbiente.atualizadoSucesso");
		} catch (SQLException e) {
			logger.error("erro sqlstate "+e.getSQLState(), e);	
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");				
		} catch (Exception e) {
			logger.error("", e);
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		}
		return "atualizar";
	}
	
	public String cancelarEditarItemAmbiente(){
		this.itemAmbiente = new ItemAmbiente();
		return "cancelar";
	}
	
	public String voltarCadastroItemAmbiente(){		
		// Caso o ambiente possua id do condomínio, então os itens do ambiente são referente a um ambiente de um condomínio e não de um conjunto de blocos.
		this.itemAmbienteEhCondominio = this.ambiente.getIdCondominio() != null ? Boolean.TRUE : Boolean.FALSE; 
		return "voltar";
	}
	
		
	public void limparItemAmbiente(ActionEvent event){
		this.limpaFormCadastroItemAmbiente();
	}
	
	public void popularListaItemAmbiente(){		
		try {
			this.abreSessao();
			this.ambiente.setId((Integer) ManagedBeanUtil.getSession(false).getAttribute("idAmbiente"));
			this.ambiente.setIdCondominio((Integer) ManagedBeanUtil.getSession(false).getAttribute("idCondominio"));
			this.listaItemAmbiente = new ListDataModel<ItemAmbiente>(this.itemAmbienteService.buscarPorAmbiente(this.ambiente));
		} catch (SQLException e) {
			logger.error("erro sqlstate "+e.getSQLState(), e);	
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");				
		} catch (Exception e) {
			logger.error("", e);
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		}	
	}
	
	@Override
	public void abreSessao() {
		ManagedBeanUtil.abreSessao(conversation);
	}

	@Override
	public void fechaSessao() {
		ManagedBeanUtil.fechaSessao(conversation);		
	}	
	
	private void limpaFormCadastroItemAmbiente(){
		this.itemAmbiente = new ItemAmbiente();
		ManagedBeanUtil.cleanSubmittedValues(this.componenteNomeItemAmbiente);		
		ManagedBeanUtil.cleanSubmittedValues(this.componenteQuantidadeItemAmbiente);		
	}

	public ListDataModel<ItemAmbiente> getListaItemAmbiente() {
		return listaItemAmbiente;
	}

	public void setListaItemAmbiente(ListDataModel<ItemAmbiente> listaItemAmbiente) {
		this.listaItemAmbiente = listaItemAmbiente;
	}

	public ItemAmbiente getItemAmbiente() {
		return itemAmbiente;
	}

	public void setItemAmbiente(ItemAmbiente itemAmbiente) {
		this.itemAmbiente = itemAmbiente;
	}

	public Boolean getItemAmbienteEhCondominio() {
		return itemAmbienteEhCondominio;
	}

	public void setItemAmbienteEhCondominio(Boolean itemAmbienteEhCondominio) {
		this.itemAmbienteEhCondominio = itemAmbienteEhCondominio;
	}	

	public Conversation getConversation() {
		return conversation;
	}


	public void setConversation(Conversation conversation) {
		this.conversation = conversation;
	}


	public ItemAmbienteService getItemAmbienteService() {
		return itemAmbienteService;
	}


	public void setItemAmbienteService(ItemAmbienteService itemAmbienteService) {
		this.itemAmbienteService = itemAmbienteService;
	}


	public UIInput getComponenteNomeItemAmbiente() {
		return componenteNomeItemAmbiente;
	}

	public void setComponenteNomeItemAmbiente(UIInput componenteNomeItemAmbiente) {
		this.componenteNomeItemAmbiente = componenteNomeItemAmbiente;
	}

	public UIInput getComponenteQuantidadeItemAmbiente() {
		return componenteQuantidadeItemAmbiente;
	}

	public void setComponenteQuantidadeItemAmbiente(
			UIInput componenteQuantidadeItemAmbiente) {
		this.componenteQuantidadeItemAmbiente = componenteQuantidadeItemAmbiente;
	}	

}
