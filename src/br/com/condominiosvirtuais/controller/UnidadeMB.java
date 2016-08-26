package br.com.condominiosvirtuais.controller;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
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

import br.com.condominiosvirtuais.entity.Bloco;
import br.com.condominiosvirtuais.entity.Condominio;
import br.com.condominiosvirtuais.entity.Unidade;
import br.com.condominiosvirtuais.enumeration.UnidadeEnum;
import br.com.condominiosvirtuais.exception.BusinessException;
import br.com.condominiosvirtuais.service.CondominioService;
import br.com.condominiosvirtuais.service.UnidadeService;
import br.com.condominiosvirtuais.util.AplicacaoUtil;
import br.com.condominiosvirtuais.util.ManagedBeanUtil;
import br.com.condominiosvirtuais.vo.UnidadeVO;

@Named @SessionScoped
public class UnidadeMB implements IConversationScopeMB, Serializable {
	
	private static final long serialVersionUID = 1L;

	private static Logger logger = Logger.getLogger(UnidadeMB.class);
	
	@Inject
	private Conversation conversation;
	
	private Unidade unidade = null;
	
	private UnidadeVO unidadeVO = null;	
	
	private Bloco bloco = null;
	
	private Condominio condominio = null;
	
	@Inject
	private CondominioMB condominioMB = null;
	
	@Inject
	private BlocoMB blocoMB = null;	
	
	private Integer cadastroEmLote = 0;
	
	private Integer sequenciaInicial = null;
	
	private Integer sequenciaFinal = null;	
	
	private Integer quantidadeAndares = null;
	
	@Inject
	private UnidadeService unidadeService = null;	
	
	private List<SelectItem> listaSIBlocos = new ArrayList<SelectItem>();	
	
	private List<SelectItem> listaSICondominios = null;
	
	private ListDataModel<UnidadeVO> listaUnidadesVO = null;	 
	
	private List<SelectItem> listaSITipos = new ArrayList<SelectItem>();	
	
	private UIInput componenteNumeroUnidade;	
	
	@Inject
	private CondominioService condominioService = null;	
			
	public UnidadeMB() {		
		this.unidadeVO = new UnidadeVO();
		this.unidade = new Unidade();
		this.unidade.setTipo(UnidadeEnum.AMBOS.getTipo());
		this.bloco = new Bloco();
		this.condominio = new Condominio();		
	}	
	
	public void pesquisar(ActionEvent event){		
		this.popularNomeCondominido();		
		this.popularNomeBloco();
		try {
			this.listaUnidadesVO = new ListDataModel<UnidadeVO>(this.unidadeService.buscarListaUnidadesPorCondominioTipoEBloco(this.condominio, 
					this.unidade.getTipo(), this.bloco));
			if (this.listaUnidadesVO.getRowCount() == 0){
				ManagedBeanUtil.setMensagemInfo("msg.unidade.semUnidades");
			}
		} catch (SQLException e) {
			logger.error("erro sqlstate "+e.getSQLState(), e);	
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");				
		} catch (Exception e) {
			logger.error("", e);
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		}
	}
	
	public String limparFiltroUnidade(){
		this.listaUnidadesVO = new ListDataModel<UnidadeVO>();
		this.condominio = new Condominio();
		this.bloco = new Bloco();
		this.unidade = new Unidade();
		this.setCadastroEmLote(0);
		return null;
	}

	public void popularListaBlocos(){
		this.setListaSIBlocos(this.blocoMB.buscarListaBlocosPorCondominio(this.condominio));	
	}
	
	public void popularListaBlocosEditar(){		
		this.condominio.setId(this.unidadeVO.getIdCondominio());
		this.setListaSIBlocos(this.blocoMB.buscarListaBlocosPorCondominio(this.condominio));	
	}
	
	public List<SelectItem> getListaSITipos(){		
		this.listaSITipos = new ArrayList<SelectItem>();
		this.listaSITipos.add(new SelectItem(UnidadeEnum.APARTAMENTO.getTipo(), AplicacaoUtil.i18n("unidade.tipo.label.0")));
		this.listaSITipos.add(new SelectItem(UnidadeEnum.LOJA.getTipo(), AplicacaoUtil.i18n("unidade.tipo.label.1")));		
		return this.listaSITipos;
	}
	
	public String salvarUnidade(){
		try{
			if(this.getCadastroEmLote() == 1){
// TODO: 		Código comentado, pois será desenvolvido na segunda versão. Data: 16/02/2012. Estudar a possibilidade de levar esse algoritmo para o business
//				// Condição que valida, no caso de um cadastro em lote, se o valor da sequencia inicial é menor que a sequencia final
//				if(this.getSequenciaInicial() >= this.getSequenciaFinal()){
//					ManagedBeanUtil.setMensagemErro("msg.unidade.sequenciaIniciaDiferenteFinal");
//					return null;
//				}
//				List<Unidade> listaUnidades = new ArrayList<Unidade>();
//				Integer sequenciaInicialPorAndar = this.getSequenciaInicial();
//				Integer sequenciaFinalPorAndar = this.getSequenciaFinal();
//				for (int i = 0; i < this.quantidadeAndares; i++) {
//					for (int j = sequenciaInicialPorAndar; j <= sequenciaFinalPorAndar; j++) {
//						Unidade unidade = new Unidade();
//						unidade.setNumero(i); 
//						unidade.setTipo(this.unidade.getTipo());
//						unidade.setIdBloco(this.getBloco().getId());
//						listaUnidades.add(unidade);
//					}					
//				}
//				this.unidadeBusiness.salvarUnidadeEmLote(listaUnidades);	
			}else{				
				this.unidade.setIdBloco(this.bloco.getId());
				this.unidadeService.salvarUnidade(this.unidade);
				this.pesquisar(null);				
				ManagedBeanUtil.setMensagemInfo("msg.unidade.salvaSucesso");
			}
			//this.condominio = new Condominio();
			//this.bloco = new Bloco();
			//this.unidade = new Unidade();
		//	ManagedBeanUtil.cleanSubmittedValues(this.componenteNumeroUnidade);
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
		return "salvar";
	}	
	
	public String cancelarCadastroUnidade(){
		this.listaUnidadesVO = new ListDataModel<UnidadeVO>();
		this.unidade = new Unidade();
		this.condominio = new Condominio();
		this.bloco = new Bloco();
		ManagedBeanUtil.cleanSubmittedValues(this.componenteNumeroUnidade);
		return "cancelar";
	}
	
	public List<SelectItem>buscarListaSIUnidadesPorBloco(Bloco bloco){
		List<SelectItem> listaSIUnidade = new ArrayList<SelectItem>();		
		List<Unidade> listaUnidades = null;
		try {
			listaUnidades = this.unidadeService.buscarListaUnidadesPorBloco(bloco);
			for (int i = 0; i < listaUnidades.size(); i++) {
				Unidade unidade = listaUnidades.get(i);
				listaSIUnidade.add(new SelectItem(unidade.getId(), unidade.getNumero().toString()));
			}		
		} catch (SQLException e) {
			logger.error("erro sqlstate "+e.getSQLState(), e);	
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");				
		} catch (Exception e) {
			logger.error("", e);
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		}
		return listaSIUnidade;
	}
	
	public String cadastroGaragem(){
		ManagedBeanUtil.getSession(true).setAttribute("idUnidade",this.unidadeVO.getId());
// TODO: CÓDIGO COMENTADO EM 18/02/2016. APAGAR EM 90 DIAS			
	//	ManagedBeanUtil.getSession(true).setAttribute("cid",this.conversation.getId());
		return "cadastrar";
	}
	
	public Unidade getUnidade() {
		return unidade;
	}

	public void setUnidade(Unidade unidade) {
		this.unidade = unidade;
	}

	public ListDataModel<UnidadeVO> getListaUnidadesVO() {
		return listaUnidadesVO;
	}

	public void setListaUnidadesVO(ListDataModel<UnidadeVO> listaUnidadesVO) {
		this.listaUnidadesVO = listaUnidadesVO;
	}
	
	public String visualizarUnidade(){
		this.unidadeVO = this.listaUnidadesVO.getRowData();		
		return "visualizar";		
	}
	
	public String voltarVizualizarUnidade(){
		return "voltar";
	}
	
	public String excluirUnidade(){
		try{
			this.unidade.setId(this.unidadeVO.getId());
			this.unidade.setTipo(this.unidadeVO.getTipoUnidade());
			this.unidadeService.excluirUnidade(this.unidade);		
			this.pesquisar(null);			
			ManagedBeanUtil.setMensagemInfo("msg.unidade.excluirSucesso");
		} catch (SQLException e) {
			logger.error("erro sqlstate "+e.getSQLState(), e);	
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		} catch (BusinessException e) {				
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage());
		} catch (Exception e) {
			logger.error("", e);
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		}
		
		return "excluir";
	}
	
	public String editarUnidade(){
		this.unidadeVO = this.listaUnidadesVO.getRowData();		
		return "editar";
	}
	
	public String cadastroUnidade(){		
		this.unidade = new Unidade();
		this.condominio = new Condominio();
		ManagedBeanUtil.cleanSubmittedValues(this.componenteNumeroUnidade);
		this.listaSICondominios = this.condominioMB.buscarListaCondominiosAtivos();;
		this.listaSIBlocos = new ArrayList<SelectItem>();		
		return "cadastrar"; 
	}
	
	public String atualizarUnidade(){
		try{
			this.unidade = new Unidade();
			this.unidade.setId(this.unidadeVO.getId());
			this.unidade.setNumero(this.unidadeVO.getNumeroUnidade());
			this.unidade.setTipo(this.unidadeVO.getTipoUnidade());
			this.unidade.setIdBloco(this.unidadeVO.getIdBloco());
			this.unidadeService.atualizarUnidade(this.unidade);
			this.pesquisar(null);
			ManagedBeanUtil.setMensagemInfo("msg.unidade.atualizadoSucesso");
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
	
	@Override 
	public void abreSessao() {
		ManagedBeanUtil.abreSessao(conversation);		
	}

	@Override
	public void fechaSessao() {
		ManagedBeanUtil.fechaSessao(conversation);
	}
	
	public List<SelectItem> getListaSIBlocos() {
		return listaSIBlocos;
	}

	public void setListaSIBlocos(List<SelectItem> listaSIBlocos) {
		this.listaSIBlocos = listaSIBlocos;
	}

	public Integer getCadastroEmLote() {
		return cadastroEmLote;
	}

	public void setCadastroEmLote(Integer cadastroEmLote) {
		this.cadastroEmLote = cadastroEmLote;
	}

	public Integer getSequenciaInicial() {
		return sequenciaInicial;
	}

	public void setSequenciaInicial(Integer sequenciaInicial) {
		this.sequenciaInicial = sequenciaInicial;
	}

	public Integer getSequenciaFinal() {
		return sequenciaFinal;
	}

	public void setSequenciaFinal(Integer sequenciaFinal) {
		this.sequenciaFinal = sequenciaFinal;
	}

	public Condominio getCondominio() {
		return condominio;
	}

	public void setCondominio(Condominio condominio) {
		this.condominio = condominio;
	}

	public Bloco getBloco() {
		return bloco;
	}

	public void setBloco(Bloco bloco) {
		this.bloco = bloco;
	}

	public List<SelectItem> getListaSICondominios() {		
		this.listaSICondominios = this.condominioMB.buscarListaCondominiosAtivos();
		return listaSICondominios;
	}
	
	public UnidadeVO getUnidadeVO() {
		return unidadeVO;
	}

	public void setUnidadeVO(UnidadeVO unidadeVO) {
		this.unidadeVO = unidadeVO;
	}	
	
	public Integer getQuantidadeAndares() {
		return quantidadeAndares;
	}

	public void setQuantidadeAndares(Integer quantidadeAndares) {
		this.quantidadeAndares = quantidadeAndares;
	}	
		
	public CondominioMB getCondominioMB() {
		return condominioMB;
	}

	public void setCondominioMB(CondominioMB condominioMB) {
		this.condominioMB = condominioMB;
	}	
	
	public BlocoMB getBlocoMB() {
		return blocoMB;
	}

	public void setBlocoMB(BlocoMB blocoMB) {
		this.blocoMB = blocoMB;
	}	

	public UnidadeService getUnidadeService() {
		return unidadeService;
	}

	public void setUnidadeService(UnidadeService unidadeService) {
		this.unidadeService = unidadeService;
	}

	public CondominioService getCondominioService() {
		return condominioService;
	}

	public void setCondominioService(CondominioService condominioService) {
		this.condominioService = condominioService;
	}

	public Conversation getConversation() {
		return conversation;
	}

	public void setConversation(Conversation conversation) {
		this.conversation = conversation;
	}

	public UIInput getComponenteNumeroUnidade() {
		return componenteNumeroUnidade;
	}

	public void setComponenteNumeroUnidade(UIInput componenteNumeroUnidade) {
		this.componenteNumeroUnidade = componenteNumeroUnidade;
	}

	private void popularNomeCondominido(){
		Boolean encontrou = Boolean.FALSE;
		Integer i = 0;
		while(!encontrou){
			SelectItem selectItem = this.getListaSICondominios().get(i++);
			if(selectItem.getValue() == this.getCondominio().getId()){
				this.getCondominio().setNome(selectItem.getLabel());
				encontrou = Boolean.TRUE;
			}
		}
	}
	
	private void popularNomeBloco(){
		Boolean encontrou = Boolean.FALSE;
		Integer i = 0;
		while(!encontrou && i < this.getListaSIBlocos().size()){
			SelectItem selectItem = this.getListaSIBlocos().get(i++);
			if(selectItem.getValue() == this.getBloco().getId()){
				this.getBloco().setNome(selectItem.getLabel());
				encontrou = Boolean.TRUE;
			}
		}
	}
	
	
	public List<SelectItem> buscarListaCondominios() {
		List<SelectItem> listaSICondominios = null;		
		try {
			listaSICondominios = new ArrayList<SelectItem>();
			List<Condominio> listaCondominios = null;
			listaCondominios = this.condominioService.buscarTodos();
			for (int i = 0; i < listaCondominios.size(); i++) {
				Condominio condominio = listaCondominios.get(i);
				listaSICondominios.add(new SelectItem(condominio.getId(), condominio.getNome()));
			}		
		} catch (SQLException e) {
			logger.error("erro sqlstate "+e.getSQLState(), e);	
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");	
		} catch (Exception e) {
			logger.error("", e);
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		}
		return listaSICondominios;
	}

}
