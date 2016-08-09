package br.com.condominiosvirtuais.controller;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.enterprise.context.Conversation;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIInput;
import javax.faces.event.ActionEvent;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.log4j.Logger;
import org.richfaces.component.UICalendar;

import br.com.condominiosvirtuais.entity.AlternativaEnquete;
import br.com.condominiosvirtuais.entity.Condominio;
import br.com.condominiosvirtuais.entity.Enquete;
import br.com.condominiosvirtuais.exception.BusinessException;
import br.com.condominiosvirtuais.service.AlternativaEnqueteService;
import br.com.condominiosvirtuais.service.CondominioService;
import br.com.condominiosvirtuais.service.EnqueteService;
import br.com.condominiosvirtuais.util.ManagedBeanUtil;

@Named @SessionScoped
public class EnqueteMB implements IConversationScopeMB, Serializable{
	
	private static final long serialVersionUID = 1L;

	private static Logger logger = Logger.getLogger(EnqueteMB.class);
	
	@Inject
	private Conversation conversation;
	
	private Condominio condominio = null;
	
	@Inject
	private CondominioMB condominioMB = null;
	
	private List<SelectItem> listaCondominios = null;
	
	private List<SelectItem> listaSIAlternativaEnquetes = null;
	
	private List<AlternativaEnquete> listaAlternativaEnquetes = null;
	
	private List<SelectItem> listaSIEnquetes = null;	
	
	private ListDataModel<Enquete> listaEnquetes;
	
	@Inject
	private EnqueteService enqueteService;
	
	@Inject
	private CondominioService condominioService;	
	
	@Inject
	private AlternativaEnqueteService alternativaEnqueteService;
	
	private Enquete enquete;
	
	private AlternativaEnquete alternativaEnquete;
	
	private UIInput componentePerguntaEnquete;
	
	private UICalendar componenteDataFimEnquete;
	
	private UIInput componenteAlternativa1Enquete;
	
	private UIInput componenteAlternativa2Enquete;
	
	private UIInput componenteAlternativa3Enquete;
	
	private UIInput componenteAlternativa4Enquete;
	
	private UIInput componenteAlternativa5Enquete;

	
	public EnqueteMB(){
		this.criarPopularEnquete();
		this.condominio = new Condominio();
		this.alternativaEnquete = new AlternativaEnquete();		
	}		
	
	public String salvarEnquete(){
		try {
			this.enqueteService.salvar(this.enquete);
			this.enqueteService.enviarEmailCondominosEnqueteCriada(this.enquete);
			this.enquete.setPergunta("");
			this.limpaComponentes();
			this.pesquisar(null);
			ManagedBeanUtil.setMensagemInfo("msg.enquete.salvarSucessoAvisoEmailEnviado");
		} catch (SQLException e) {
			logger.error("erro sqlstate "+e.getSQLState(), e);	
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");				
		} catch (Exception e) {
			logger.error("", e);
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		}		
		return "salvar";				
	}
	
	public void limparFiltroEnquete(ActionEvent event){
		this.criarPopularEnquete();
		this.listaEnquetes = new ListDataModel<Enquete>();
		this.limpaComponentes();
	}
	
	public String cancelarCadastroEnquete(){
		this.criarPopularEnquete();
		this.listaEnquetes = new ListDataModel<Enquete>();
		this.limpaComponentes();
		return "cancelar";
	}
	
	public String cadastroEnquete(){
		this.criarPopularEnquete();		
		this.limpaComponentes();
		return "cadastrar"; 
	}
	
	public void excluirEnquete(ActionEvent actionEvent){
		try {			
			Enquete enquete = (Enquete) this.listaEnquetes.getRowData();
			this.enqueteService.excluir(enquete);		
			this.pesquisar(null);
			ManagedBeanUtil.setMensagemInfo("msg.enquete.excluirSucesso");		
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
	
	
	
	public String votarEnquete(){
		try {
			this.popularIdEnqueteEVoto();
			this.alternativaEnqueteService.votar(this.alternativaEnquete);	
			this.pesquisar(null);
			ManagedBeanUtil.setMensagemInfo("msg.enquete.votarSucesso");
		} catch (SQLException e) {
			logger.error("erro sqlstate "+e.getSQLState(), e);	
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
			return null;
		} catch (BusinessException e) {				
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage());		
		} catch (Exception e) {
			logger.error("", e);
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
			return null;
		}		
		return "listar";
	}
	
	public void popularListaAlternativaEnquetes(){	
		try{
			this.listaSIAlternativaEnquetes = new ArrayList<SelectItem>();	
			this.listaAlternativaEnquetes = this.alternativaEnqueteService.buscarPorIdEnquete(this.enquete.getId());		
			AlternativaEnquete alternativaEnquete = null;
			for (int i = 0; i < this.listaAlternativaEnquetes.size(); i++) {			
				alternativaEnquete = this.listaAlternativaEnquetes.get(i);
				this.listaSIAlternativaEnquetes.add(new SelectItem(alternativaEnquete.getId(), alternativaEnquete.getAlternativa()));
			}		
		} catch (SQLException e) {
			logger.error("erro sqlstate "+e.getSQLState(), e);	
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		} catch (Exception e) {
			logger.error("", e);
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		}			
	}
	
	public void pesquisar(ActionEvent actionEvent){
		try {			
			if(this.enquete.getPergunta() != null && this.enquete.getPergunta().trim() != ""){
				this.listaEnquetes = new ListDataModel<Enquete>(this.enqueteService.buscarPorIdCondominioEPergunta(this.enquete.getIdCondominio(),  this.enquete.getPergunta()));
			}else{
				this.listaEnquetes = new ListDataModel<Enquete>(this.enqueteService.buscarPorIdCondominio(this.enquete.getIdCondominio()));
			}
			if (this.listaEnquetes.getRowCount() == 0){
				ManagedBeanUtil.setMensagemInfo("msg.enquete.semEnquetes");
			}				
		} catch (SQLException e) {
			logger.error("erro sqlstate "+e.getSQLState(), e);	
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		} catch (Exception e) {
			logger.error("", e);
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		}		
	}
	
	public String visualizarEnquete(){
		try {
			this.enquete = this.listaEnquetes.getRowData();
			Double somatorioVotos = 0.0;	
			Double porcentagem = 0.00;
			// Calcula porcentagem
			for (AlternativaEnquete alternativaEnquete : this.enquete.getListaAlternativaEnquetes()) {
				somatorioVotos+= alternativaEnquete.getVoto();
			}
			for (AlternativaEnquete alternativaEnquete : this.enquete.getListaAlternativaEnquetes()) {
				if(somatorioVotos > 0){
					porcentagem =  (((double)alternativaEnquete.getVoto()) / (somatorioVotos));				
				}
				alternativaEnquete.setPorcentagem(porcentagem);			
			}		
			this.condominio = this.condominioService.buscarPorId(this.enquete.getIdCondominio());
		} catch (SQLException e) {
			logger.error("erro sqlstate "+e.getSQLState(), e);	
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		} catch (Exception e) {
			logger.error("", e);
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		}	
		return "visualizar";
	}
	
	public void popularListaEnquetes(){
		List<Enquete> listaEnquete;
		this.listaSIEnquetes = new ArrayList<SelectItem>();		
		try {
			listaEnquete = this.enqueteService.buscarPorIdCondominioNaoFinalizou(this.enquete.getIdCondominio());
			Enquete enquete = null;
			for (int i = 0; i < listaEnquete.size(); i++) {			
				enquete = listaEnquete.get(i);
				this.listaSIEnquetes.add(new SelectItem(enquete.getId(), enquete.getPergunta()));
			}
			if (listaEnquete.size() == 0){			
				ManagedBeanUtil.setMensagemInfo("msg.enquete.semEnquetes");
			}	
		} catch (SQLException e) {
			logger.error("erro sqlstate "+e.getSQLState(), e);	
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		} catch (Exception e) {
			logger.error("", e);
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		}
	}	

	public String voltarVizualizarEnquete(){
		return "voltar";
	}
	
	@Override
	public void abreSessao() {
		ManagedBeanUtil.abreSessao(conversation);		
	}

	@Override
	public void fechaSessao() {
		ManagedBeanUtil.fechaSessao(conversation);		
	}	
	private void criarPopularEnquete(){
		this.enquete = new Enquete();
		this.enquete.setDataCriacao(new Date());
		// Inicializa as cinco alternativas
		AlternativaEnquete alternativaEnquete = null;
		for (int i = 0; i < 5; i++) {
			alternativaEnquete = new AlternativaEnquete();
			alternativaEnquete.setVoto(0);
			alternativaEnquete.setPorcentagem(0.0);
			this.enquete.getListaAlternativaEnquetes().add(alternativaEnquete);
		}
	}
	
	private void popularIdEnqueteEVoto(){
		Boolean encontrou = Boolean.FALSE;
		Integer contador = 0;
		AlternativaEnquete alternativaEnquete;
		while(!encontrou){
			alternativaEnquete = this.listaAlternativaEnquetes.get(contador++);
			if(alternativaEnquete.getId() == this.alternativaEnquete.getId()){
				this.alternativaEnquete.setIdEnquete(alternativaEnquete.getIdEnquete());
				this.alternativaEnquete.setVoto(alternativaEnquete.getVoto());
				encontrou = Boolean.TRUE;
			}
		}
	}
	
	private void limpaComponentes() {
		ManagedBeanUtil.cleanSubmittedValues(this.componentePerguntaEnquete);	
// TODO: Quando habilita a opção de limpar a data, aparece um bug que deixa o calendario desabilidado. rever esse bug.		
	//	ManagedBeanUtil.cleanSubmittedValues(this.componenteDataFimEnquete);	
		ManagedBeanUtil.cleanSubmittedValues(this.componenteAlternativa1Enquete);	
		ManagedBeanUtil.cleanSubmittedValues(this.componenteAlternativa2Enquete);	
		ManagedBeanUtil.cleanSubmittedValues(this.componenteAlternativa3Enquete);	
		ManagedBeanUtil.cleanSubmittedValues(this.componenteAlternativa4Enquete);
		ManagedBeanUtil.cleanSubmittedValues(this.componenteAlternativa5Enquete);
	}

	public Enquete getEnquete() {
		return enquete;
	}

	public void setEnquete(Enquete enquete) {
		this.enquete = enquete;
	}	
	
	public AlternativaEnquete getAlternativaEnquete() {
		return alternativaEnquete;
	}

	public void setAlternativaEnquete(AlternativaEnquete alternativaEnquete) {
		this.alternativaEnquete = alternativaEnquete;
	}

	public Condominio getCondominio() {
		return condominio;
	}

	public void setCondominio(Condominio condominio) {
		this.condominio = condominio;
	}	

	public Conversation getConversation() {
		return conversation;
	}

	public void setConversation(Conversation conversation) {
		this.conversation = conversation;
	}

	public EnqueteService getEnqueteService() {
		return enqueteService;
	}

	public void setEnqueteService(EnqueteService enqueteService) {
		this.enqueteService = enqueteService;
	}

	public CondominioService getCondominioService() {
		return condominioService;
	}

	public void setCondominioService(CondominioService condominioService) {
		this.condominioService = condominioService;
	}

	public AlternativaEnqueteService getAlternativaEnqueteService() {
		return alternativaEnqueteService;
	}

	public void setAlternativaEnqueteService(
			AlternativaEnqueteService alternativaEnqueteService) {
		this.alternativaEnqueteService = alternativaEnqueteService;
	}

	public List<SelectItem> getListaCondominios() {
		this.listaCondominios = this.condominioMB.buscarListaCondominiosAtivos();
		return listaCondominios;
	}

	public void setListaCondominios(List<SelectItem> listaCondominios) {		
		this.listaCondominios = listaCondominios;
	}

	public ListDataModel<Enquete> getListaEnquetes() {
		return listaEnquetes;
	}

	public void setListaEnquetes(ListDataModel<Enquete> listaEnquetes) {
		this.listaEnquetes = listaEnquetes;
	}	
	
	public List<SelectItem> getListaSIEnquetes() {
		return listaSIEnquetes;
	}

	public void setListaSIEnquetes(List<SelectItem> listaSIEnquetes) {
		this.listaSIEnquetes = listaSIEnquetes;
	}	

	public List<SelectItem> getListaSIAlternativaEnquetes() {
		return listaSIAlternativaEnquetes;
	}

	public void setListaSIAlternativaEnquetes(
			List<SelectItem> listaSIAlternativaEnquetes) {
		this.listaSIAlternativaEnquetes = listaSIAlternativaEnquetes;
	}	

	public CondominioMB getCondominioMB() {
		return condominioMB;
	}

	public void setCondominioMB(CondominioMB condominioMB) {
		this.condominioMB = condominioMB;
	}

	public UIInput getComponentePerguntaEnquete() {
		return componentePerguntaEnquete;
	}

	public void setComponentePerguntaEnquete(UIInput componentePerguntaEnquete) {
		this.componentePerguntaEnquete = componentePerguntaEnquete;
	}

	public UICalendar getComponenteDataFimEnquete() {
		return componenteDataFimEnquete;
	}

	public void setComponenteDataFimEnquete(UICalendar componenteDataFimEnquete) {
		this.componenteDataFimEnquete = componenteDataFimEnquete;
	}

	public UIInput getComponenteAlternativa1Enquete() {
		return componenteAlternativa1Enquete;
	}

	public void setComponenteAlternativa1Enquete(
			UIInput componenteAlternativa1Enquete) {
		this.componenteAlternativa1Enquete = componenteAlternativa1Enquete;
	}

	public UIInput getComponenteAlternativa2Enquete() {
		return componenteAlternativa2Enquete;
	}

	public void setComponenteAlternativa2Enquete(
			UIInput componenteAlternativa2Enquete) {
		this.componenteAlternativa2Enquete = componenteAlternativa2Enquete;
	}

	public UIInput getComponenteAlternativa3Enquete() {
		return componenteAlternativa3Enquete;
	}

	public void setComponenteAlternativa3Enquete(
			UIInput componenteAlternativa3Enquete) {
		this.componenteAlternativa3Enquete = componenteAlternativa3Enquete;
	}

	public UIInput getComponenteAlternativa4Enquete() {
		return componenteAlternativa4Enquete;
	}

	public void setComponenteAlternativa4Enquete(
			UIInput componenteAlternativa4Enquete) {
		this.componenteAlternativa4Enquete = componenteAlternativa4Enquete;
	}

	public UIInput getComponenteAlternativa5Enquete() {
		return componenteAlternativa5Enquete;
	}

	public void setComponenteAlternativa5Enquete(
			UIInput componenteAlternativa5Enquete) {
		this.componenteAlternativa5Enquete = componenteAlternativa5Enquete;
	}
	
}
