package br.com.condominiosvirtuais.controller;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIInput;
import javax.faces.component.UISelectOne;
import javax.faces.event.ActionEvent;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.log4j.Logger;

import br.com.condominiosvirtuais.entity.Ambiente;
import br.com.condominiosvirtuais.entity.Bloco;
import br.com.condominiosvirtuais.entity.BlocoConjuntoBloco;
import br.com.condominiosvirtuais.entity.Condominio;
import br.com.condominiosvirtuais.entity.ConjuntoBloco;
import br.com.condominiosvirtuais.entity.TipoConjuntoBloco;
import br.com.condominiosvirtuais.enumeration.TipoConjuntoBlocoEnum;
import br.com.condominiosvirtuais.exception.BusinessException;
import br.com.condominiosvirtuais.service.AmbienteService;
import br.com.condominiosvirtuais.service.BlocoService;
import br.com.condominiosvirtuais.service.ConjuntoBlocoService;
import br.com.condominiosvirtuais.util.ManagedBeanUtil;

@Named @SessionScoped
public class AmbienteMB implements IConversationScopeMB, Serializable{
	
	private static final long serialVersionUID = 1L;

	private static Logger logger = Logger.getLogger(AmbienteMB.class);
	
	private Condominio condominio = null;
	
	@Inject
	private Conversation conversation;
	
	private Bloco bloco = null; 	
	
	private Ambiente ambiente = null;
	
	@Inject
	private BlocoMB blocoMB = null;
	
	@Inject
	private CondominioMB condominioMB = null;
	
	@Inject
	private AmbienteService ambienteService = null;
	
	@Inject
	private BlocoService blocoService = null;
	
	@Inject
	private ConjuntoBlocoService conjuntoBlocoService = null;
	
	private ListDataModel<Ambiente> listaAmbientes = new ListDataModel<Ambiente>();	
	
	private List<SelectItem> listaSICondominios = new ArrayList<SelectItem>();
	
	private List<SelectItem> listaSIBlocos = new ArrayList<SelectItem>();
	
	private List<Bloco> listaBlocos = new ArrayList<Bloco>();
	
	private List<Bloco> listaAmbienteBlocos = new ArrayList<Bloco>();
	
	private List<String> listaNomeBlocos = new ArrayList<String>();	
	
	private UIInput componenteNomeAmbiente;
	
	private UISelectOne componenteCondominioAmbiente;
	
		
	public AmbienteMB(){
		this.ambiente = new Ambiente();
		this.condominio = new Condominio();
		this.bloco = new Bloco();		
	}
	
	public void pesquisarAmbienteCondominio(ActionEvent event){
		try {			
			this.popularNomeCondominido();
			this.listaAmbientes = new ListDataModel<Ambiente>(this.ambienteService.buscarPorCondominioENomeAmbiente(this.condominio, this.ambiente.getNome()));
			if (this.listaAmbientes.getRowCount() == 0){
				ManagedBeanUtil.setMensagemInfo("msg.ambienteCondominio.semAmbientes");
			}
		} catch (SQLException e) {
			logger.error("erro sqlstate "+e.getSQLState(), e);	
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");				
		} catch (Exception e) {
			logger.error("", e);
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		}
	}
	
	public void pesquisarAmbienteBloco(ActionEvent event){
		try {			
			this.popularNomeCondominido();
			this.listaAmbientes = new ListDataModel<Ambiente>(this.ambienteService.buscarPorBlocoENomeAmbiente(this.bloco, this.ambiente.getNome()));
			if (this.listaAmbientes.getRowCount() == 0){
				ManagedBeanUtil.setMensagemInfo("msg.ambienteBloco.semAmbientes");
			}
		} catch (SQLException e) {
			logger.error("erro sqlstate "+e.getSQLState(), e);	
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");				
		} catch (Exception e) {
			logger.error("", e);
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		}
	}
	
	public String salvarAmbienteCondominio(){
		try {
			this.ambiente.setIdCondominio(this.condominio.getId());		
			this.ambienteService.salvar(this.ambiente);
			this.ambiente = new Ambiente();		
			this.pesquisarAmbienteCondominio(null);
			ManagedBeanUtil.setMensagemInfo("msg.ambienteCondominio.salvaSucesso");
		} catch (SQLException e) {
			logger.error("erro sqlstate "+e.getSQLState(), e);	
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");				
		} catch (Exception e) {
			logger.error("", e);
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		}
		return "salvar";	
	}
	
	public String salvarAmbienteBloco(){	
		try {
			// Inicialmente, entende-se que os blocos selecinados não fazem parte de um conjunto de blocos existentes.
			Boolean todosBlocosMesmoConjunto = Boolean.FALSE;
			Integer quantidadeBlocosIguais = 0;
			Integer indiceListaConjuntoBloco = -1;
			this.conjuntoBlocoService.configuraTipoConjuntoBloco(TipoConjuntoBlocoEnum.AMBIENTE.getConjuntoBloco());
			List<ConjuntoBloco> listaConjuntoBloco = this.conjuntoBlocoService.buscarPorTipo(TipoConjuntoBlocoEnum.AMBIENTE.getConjuntoBloco());
			Iterator<ConjuntoBloco> iteratorConjuntoBloco = listaConjuntoBloco.iterator();
			while(iteratorConjuntoBloco.hasNext() && !todosBlocosMesmoConjunto){
				ConjuntoBloco conjuntoBloco = iteratorConjuntoBloco.next();
				indiceListaConjuntoBloco++;
				// Testa somente os conjunto de blocos que tem a mesma quantidade de blocos.
				if (conjuntoBloco.getListaListaBlocoConjuntoBlocos().size() == this.listaAmbienteBlocos.size()){
					quantidadeBlocosIguais = 0;
					for (BlocoConjuntoBloco blocoBase: conjuntoBloco.getListaListaBlocoConjuntoBlocos()) {
						for (Bloco blocoTela : this.listaAmbienteBlocos) {
							if(blocoBase.getBloco().getId() == blocoTela.getId()){
								quantidadeBlocosIguais++;
							}
						}
					}				
					if(quantidadeBlocosIguais == this.listaAmbienteBlocos.size()){
						todosBlocosMesmoConjunto = Boolean.TRUE;
					}
				}
			}
			// Bloco criado para ser utilizado na pesquisa, que será realizada após a criação do ambiente.		
			this.bloco = new Bloco();
			// Caso sejam todos os blocos do mesmo conjunto, então eu salvo esse ambiente associando ele a esse conjunto
			if(todosBlocosMesmoConjunto){
				this.ambiente.setIdConjuntoBloco(listaConjuntoBloco.get(indiceListaConjuntoBloco).getId());
				// Pega-se o primeiro bloco da lista de conjunto de blocos para pesquisar.
				this.bloco.setId(listaConjuntoBloco.get(indiceListaConjuntoBloco).getListaListaBlocoConjuntoBlocos().get(0).getBloco().getId());
				this.ambienteService.salvar(this.ambiente);				
			}else{
				List<TipoConjuntoBloco> listaAmbientes = new ArrayList<TipoConjuntoBloco>();
				listaAmbientes.add(this.ambiente);
				ConjuntoBloco conjuntoBloco = new ConjuntoBloco();
				conjuntoBloco.setTipo(TipoConjuntoBlocoEnum.AMBIENTE.getConjuntoBloco());
				conjuntoBloco.setListaTipoConjuntoBlocos(listaAmbientes);
				List<BlocoConjuntoBloco> listaBlocoConjuntoBloco = new ArrayList<BlocoConjuntoBloco>();
				BlocoConjuntoBloco blocoConjuntoBloco = null;
				// Pega-se o primeiro bloco da lista de conjunto bloco para pesquisar.
				this.bloco.setId(this.listaAmbienteBlocos.get(0).getId());
				for (Bloco bloco : this.listaAmbienteBlocos) {
					blocoConjuntoBloco = new BlocoConjuntoBloco();
					blocoConjuntoBloco.setConjuntoBloco(conjuntoBloco);
					blocoConjuntoBloco.setBloco(bloco);
					listaBlocoConjuntoBloco.add(blocoConjuntoBloco);
				}
				conjuntoBloco.setListaListaBlocoConjuntoBlocos(listaBlocoConjuntoBloco);
				this.conjuntoBlocoService.salvar(conjuntoBloco);		
			}		
			this.ambiente = new Ambiente();
			// Seta o nome com vazio, pois esse será utilizado na consulta. "startsWith".   
			this.ambiente.setNome("");
			this.pesquisarAmbienteBloco(null);
			ManagedBeanUtil.setMensagemInfo("msg.ambienteBloco.salvaSucesso");
		} catch (SQLException e) {
			logger.error("erro sqlstate "+e.getSQLState(), e);	
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");				
		} catch (Exception e) {
			logger.error("", e);
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		}	 
		return "salvar";			
	}
	
	public String cadastroAmbienteCondominio(){
		this.ambiente = new Ambiente();
		this.condominio = new Condominio();
		return "cadastrar";
	}
	
	public String cadastroAmbienteBloco(){
		this.ambiente = new Ambiente();
		this.condominio = new Condominio();
		this.bloco = new Bloco();
		this.listaSIBlocos = new ArrayList<SelectItem>();
		this.listaBlocos = new ArrayList<Bloco>();
		this.listaAmbienteBlocos = new ArrayList<Bloco>();
		return "cadastrar";
	}
	
	public String cadastroItemAmbiente(){
		ManagedBeanUtil.getSession(true).setAttribute("idAmbiente",this.ambiente.getId());
		ManagedBeanUtil.getSession(true).setAttribute("idCondominio",this.ambiente.getIdCondominio());		
		return "cadastrar";
	}
	
	public String visualizarAmbienteCondominio(){
		this.ambiente = this.listaAmbientes.getRowData();		
		return "visualizar";
	}
	
	public String visualizarAmbienteBloco(){
		ConjuntoBloco conjuntoBloco;
		try {
			this.ambiente = this.listaAmbientes.getRowData();
			this.listaNomeBlocos = new ArrayList<String>();
			this.conjuntoBlocoService.configuraTipoConjuntoBloco(TipoConjuntoBlocoEnum.AMBIENTE.getConjuntoBloco());
			conjuntoBloco = this.conjuntoBlocoService.buscarPorId(this.ambiente.getIdConjuntoBloco());
			for (BlocoConjuntoBloco blocoConjuntoBloco : conjuntoBloco.getListaListaBlocoConjuntoBlocos()) {
				this.listaNomeBlocos.add(blocoConjuntoBloco.getBloco().getNome());
			}
		} catch (SQLException e) {
			logger.error("erro sqlstate "+e.getSQLState(), e);	
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");				
		} catch (Exception e) {
			logger.error("", e);
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		}
		return "visualizar";	    
	}
	
	public String voltarVizualizarAmbienteCondominio(){		
		return "voltar";
	}
	
	public String voltarVizualizarAmbienteBloco(){		
		return "voltar";
	}
	
	public void limparFiltroAmbienteCondominio (ActionEvent actionEvent){
		this.listaAmbientes = new ListDataModel<Ambiente>();
		this.ambiente = new Ambiente();
		ManagedBeanUtil.cleanSubmittedValues(this.componenteNomeAmbiente);
	}
	
	public void limparFiltroAmbienteBloco (ActionEvent actionEvent){
		this.listaAmbientes = new ListDataModel<Ambiente>();
		this.ambiente = new Ambiente();		
		this.condominio = new  Condominio();
		this.listaSIBlocos = new ArrayList<SelectItem>();
		ManagedBeanUtil.cleanSubmittedValues(this.componenteNomeAmbiente);
		ManagedBeanUtil.cleanSubmittedValues(this.componenteCondominioAmbiente);
	}
	
	public String editarAmbienteCondominio(){
		this.ambiente = this.listaAmbientes.getRowData();
		return "editar";
	}
	
	public String editarAmbienteBloco(){
		this.ambiente = this.listaAmbientes.getRowData();
		this.popularAmbienteListaBlocos();
		return "editar";
	}
	              
	public String cancelarCadastroAmbienteCondominio(){
		this.listaAmbientes = new ListDataModel<Ambiente>();
		this.ambiente = new Ambiente();		
		this.condominio = new Condominio();		
		return "cancelar";
	}	
	
	public String cancelarCadastroAmbienteBloco(){
		this.listaAmbientes = new ListDataModel<Ambiente>();
		this.ambiente = new Ambiente();		
		this.condominio = new Condominio();
		this.listaBlocos = new ArrayList<Bloco>();		     
		this.listaAmbienteBlocos = new ArrayList<Bloco>();
		return "cancelar";
	}	
	 
	public String excluirAmbienteCondominio(){	
		try {
			this.ambienteService.excluir(this.ambiente);
			this.ambiente = new Ambiente();
			this.pesquisarAmbienteCondominio(null);
			ManagedBeanUtil.setMensagemInfo("msg.ambienteCondominio.excluirSucesso");
			return "excluir";
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
	}
	
	public String excluirAmbienteBloco(){	
		try {
			List<Ambiente> listaAmbiente = this.ambienteService.buscarPorIdConjuntoBloco(this.ambiente.getIdConjuntoBloco());						
			// Caso exista somente um ambiente associado a esse conjunto de blocos, exclui-se também o conjunto de blocos
			if(listaAmbiente.size() == 1){
				this.conjuntoBlocoService.configuraTipoConjuntoBloco(TipoConjuntoBlocoEnum.AMBIENTE.getConjuntoBloco());
				ConjuntoBloco conjuntoBloco = this.conjuntoBlocoService.buscarPorId(this.ambiente.getIdConjuntoBloco());
				this.conjuntoBlocoService.excluir(conjuntoBloco);
			}else{
				this.ambienteService.excluir(this.ambiente);
			}
			this.ambiente = new Ambiente();
			// Seta o nome com vazio, pois esse será utilizado na consulta. "startsWith".   
			this.ambiente.setNome("");
			this.pesquisarAmbienteBloco(null);
			ManagedBeanUtil.setMensagemInfo("msg.ambienteBloco.excluirSucesso");
			return "excluir";
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
	}
	
	public String atualizarAmbienteCondominio(){
		try {
			this.ambienteService.atualizar(this.ambiente);
			this.ambiente = new Ambiente();
			this.pesquisarAmbienteCondominio(null);
			ManagedBeanUtil.setMensagemInfo("msg.ambienteCondominio.atualizadoSucesso");
		} catch (SQLException e) {
			logger.error("erro sqlstate "+e.getSQLState(), e);	
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
			return null;
		} catch (Exception e) {
			logger.error("", e);
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
			return null;
		}
		return "atualizar";		
	}
	
	public String atualizarAmbienteBloco(){		
		// Inicialmente, entende-se que os blocos selecinados não fazem parte de um conjunto de blocos existentes.
		try {
			Boolean todosBlocosMesmoConjunto = Boolean.FALSE;
			Integer quantidadeBlocosIguais = 0;
			this.conjuntoBlocoService.configuraTipoConjuntoBloco(TipoConjuntoBlocoEnum.AMBIENTE.getConjuntoBloco());
			ConjuntoBloco conjuntoBloco;
			conjuntoBloco = this.conjuntoBlocoService.buscarPorId(this.ambiente.getIdConjuntoBloco());
		// Testa somente os conjunto de blocos que tem a mesma quantidade de blocos.
		if (conjuntoBloco.getListaListaBlocoConjuntoBlocos().size() == this.listaAmbienteBlocos.size()){
			quantidadeBlocosIguais = 0;
			for (BlocoConjuntoBloco blocoBase: conjuntoBloco.getListaListaBlocoConjuntoBlocos()) {
				for (Bloco blocoTela : this.listaAmbienteBlocos) {
					if(blocoBase.getBloco().getId() == blocoTela.getId()){
						quantidadeBlocosIguais++;
					}
				}
			}				
			if(quantidadeBlocosIguais == this.listaAmbienteBlocos.size()){
				todosBlocosMesmoConjunto = Boolean.TRUE;
			}		
		}
		List<Condominio> listaCondominio = new ArrayList<Condominio>();
		listaCondominio.add(this.condominio);		
		// Caso sejam todos os blocos do mesmo conjunto, então atualizo esse funcionário associando ele a esse conjunto
		if(todosBlocosMesmoConjunto){			
			this.ambienteService.atualizar(this.ambiente);		 	
		}else{
			List<TipoConjuntoBloco> listaFuncionarios = new ArrayList<TipoConjuntoBloco>();
			List<BlocoConjuntoBloco> listaBlocoConjuntoBloco = new ArrayList<BlocoConjuntoBloco>();
			listaFuncionarios.add(this.ambiente);
			conjuntoBloco.setListaTipoConjuntoBlocos(listaFuncionarios);			
			BlocoConjuntoBloco blocoConjuntoBloco = null;
			for (Bloco bloco : this.listaAmbienteBlocos) {
				blocoConjuntoBloco = new BlocoConjuntoBloco();
				blocoConjuntoBloco.setConjuntoBloco(conjuntoBloco);
				blocoConjuntoBloco.setBloco(bloco);
				listaBlocoConjuntoBloco.add(blocoConjuntoBloco);
			}
			conjuntoBloco.setListaListaBlocoConjuntoBlocos(listaBlocoConjuntoBloco);
			this.conjuntoBlocoService.atualizar(conjuntoBloco);			
			}	
		this.ambiente = new Ambiente();
		// Seta o nome com vazio, pois esse será utilizado na consulta. "startsWith".   
		this.ambiente.setNome("");
		this.pesquisarAmbienteBloco(null);
		ManagedBeanUtil.setMensagemInfo("msg.ambienteBloco.atualizadoSucesso");
		} catch (SQLException e) {
			logger.error("erro sqlstate "+e.getSQLState(), e);	
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		} catch (Exception e) {
			logger.error("", e);
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		}	
		return "atualizar";
	}
	
	public void popularListaSIBlocos(){		
		this.listaSIBlocos  = this.blocoMB.buscarListaBlocosPorCondominio(this.condominio);	
	}
	
	public void popularListaBlocos(){		
		try {
			this.listaBlocos = this.blocoService.buscarPorCondominioENome(this.condominio,null);
		} catch (SQLException e) {
			logger.error("erro sqlstate "+e.getSQLState(), e);	
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		} catch (Exception e) {
			logger.error("", e);
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		}
	}
	
	public void popularAmbienteListaBlocos(){
		try {
			this.listaBlocos = this.blocoService.buscarPorCondominioENome(this.condominio,null);
			this.listaAmbienteBlocos = new ArrayList<Bloco>();
			this.conjuntoBlocoService.configuraTipoConjuntoBloco(TipoConjuntoBlocoEnum.AMBIENTE.getConjuntoBloco());
			ConjuntoBloco conjuntoBloco = this.conjuntoBlocoService.buscarPorId(this.ambiente.getIdConjuntoBloco());
			if(conjuntoBloco != null){
				for (BlocoConjuntoBloco blocoConjuntoBloco : conjuntoBloco.getListaListaBlocoConjuntoBlocos()) {
					this.listaAmbienteBlocos.add(blocoConjuntoBloco.getBloco());			
				}
			}	
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
		//ManagedBeanUtil.abreSessao(conversation);		
	}

	@Override
	public void fechaSessao() {
		//ManagedBeanUtil.fechaSessao(conversation);		
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

	public Ambiente getAmbiente() {		
		return ambiente;
	}

	public void setAmbiente(Ambiente ambiente) {
		this.ambiente = ambiente;
	}	

	public ListDataModel<Ambiente> getListaAmbientes() {
		return listaAmbientes;
	}

	public void setListaAmbientes(ListDataModel<Ambiente> listaAmbientes) {
		this.listaAmbientes = listaAmbientes;
	}

	public List<SelectItem> getListaSICondominios() {
		this.listaSICondominios = this.condominioMB.buscarListaCondominiosAtivos();	
		return listaSICondominios;
	}	

	public UISelectOne getComponenteCondominioAmbiente() {
		return componenteCondominioAmbiente;
	}

	public void setComponenteCondominioAmbiente(
			UISelectOne componenteCondominioAmbiente) {
		this.componenteCondominioAmbiente = componenteCondominioAmbiente;
	}

	public void setListaSICondominios(List<SelectItem> listaSICondominios) {
		this.listaSICondominios = listaSICondominios;
	}

	public List<SelectItem> getListaSIBlocos() {
		return listaSIBlocos;
	}

	public void setListaSIBlocos(List<SelectItem> listaSIBlocos) {
		this.listaSIBlocos = listaSIBlocos;
	}

	public List<Bloco> getListaBlocos() {
		return listaBlocos;
	}

	public void setListaBlocos(List<Bloco> listaBlocos) {
		this.listaBlocos = listaBlocos;
	}

	public List<Bloco> getListaAmbienteBlocos() {
		return listaAmbienteBlocos;
	}

	public void setListaAmbienteBlocos(List<Bloco> listaAmbienteBlocos) {
		this.listaAmbienteBlocos = listaAmbienteBlocos;
	}

	public List<String> getListaNomeBlocos() {
		return listaNomeBlocos;
	}

	public CondominioMB getCondominioMB() {
		return condominioMB;
	}

	public void setCondominioMB(CondominioMB condominioMB) {
		this.condominioMB = condominioMB;
	}

	public void setListaNomeBlocos(List<String> listaNomeBlocos) {
		this.listaNomeBlocos = listaNomeBlocos;
	}

	public BlocoMB getBlocoMB() {
		return blocoMB;
	}

	public void setBlocoMB(BlocoMB blocoMB) {
		this.blocoMB = blocoMB;
	}

	public AmbienteService getAmbienteService() {
		return ambienteService;
	}

	public void setAmbienteService(AmbienteService ambienteService) {
		this.ambienteService = ambienteService;
	}

	public BlocoService getBlocoService() {
		return blocoService;
	}

	public void setBlocoService(BlocoService blocoService) {
		this.blocoService = blocoService;
	}

	public ConjuntoBlocoService getConjuntoBlocoService() {
		return conjuntoBlocoService;
	}

	public void setConjuntoBlocoService(ConjuntoBlocoService conjuntoBlocoService) {
		this.conjuntoBlocoService = conjuntoBlocoService;
	}

	public UIInput getComponenteNomeAmbiente() {
		return componenteNomeAmbiente;
	}

	public void setComponenteNomeAmbiente(UIInput componenteNomeAmbiente) {
		this.componenteNomeAmbiente = componenteNomeAmbiente;
	}

}
