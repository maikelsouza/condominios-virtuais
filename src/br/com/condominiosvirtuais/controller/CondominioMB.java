package br.com.condominiosvirtuais.controller;
        

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIInput;
import javax.faces.component.UISelectItem;
import javax.faces.component.UISelectItems;
import javax.faces.event.ActionEvent;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.log4j.Logger;

import br.com.condominiosvirtuais.entity.Condominio;
import br.com.condominiosvirtuais.entity.Condomino;
import br.com.condominiosvirtuais.entity.Usuario;
import br.com.condominiosvirtuais.enumeration.AtributoSessaoEnum;
import br.com.condominiosvirtuais.enumeration.CondominioSituacaoEnum;
import br.com.condominiosvirtuais.service.CondominioService;
import br.com.condominiosvirtuais.service.CondominoService;
import br.com.condominiosvirtuais.service.UsuarioService;
import br.com.condominiosvirtuais.util.AplicacaoUtil;
import br.com.condominiosvirtuais.util.ManagedBeanUtil;
import br.com.condominiosvirtuais.vo.CondominoVO;

/**
 * MB Criado para realização do CRUD de um condomíno. Página: formListaCondominio.xhtml
 * @author Maikel Joel de Souza
 * @since 08/10/2011
 * UC 02 - Cadastrar Condomínio
 */

@Named @SessionScoped
public class CondominioMB implements IConversationScopeMB, Serializable{	

	private static final long serialVersionUID = 1L;

	private static Logger logger = Logger.getLogger(CondominioMB.class);
	
	@Inject
	private Conversation conversation;
	
	private Condominio condominio = null;		
	
	private CondominoVO condominoVO = null;	
	
	private String nomeSindicoGeral  = "";
	
	private Integer idSindicoGeral = null;
	
	private Integer idSubSindicoGeral = 0;
	
	private String nomeSubSindicoGeral  = "";	

	@Inject
	private CondominioService condominioService;
	
	@Inject
	private CondominoService condominoService = null;	
	
	@Inject
	private UsuarioService usuarioService = null;
		
	private ListDataModel<Condominio> listaDeCondominios = new ListDataModel<Condominio>();	
		
	private List<CondominoVO> listaDeCondominosVOs = null;	
	
	private List<CondominoVO> listaDeConselheirosVOs = null;
	
	private Boolean condominioPossuiCondominos;
	
	private UIInput componenteNomeCondominio;
	
	private UISelectItems componenteItensSituacaoCondominio;
	
	private UISelectItem componenteItemSituacaoCondominio;
	
	private UISelectItems componenteItensCondominio;
	
	private UIInput componenteEnderecoCondominio;
	
	private UIInput componenteEnderecoNumeroCondominio;
	
	private UIInput componenteEnderecoComplementoCondominio;
	
	private UIInput componenteEnderecoCepCondominio;
	
	private UIInput componenteEnderecoBairroCondominio;
	
	private UIInput componenteEnderecoCidadeCondominio;
	
	private UIInput componenteEnderecoUfCondominio;
	
	private UIInput componenteEnderecoPaisCondominio;	
	
	private UIInput componenteCnpjCondominio;	
	
	private UIInput componenteTelefoneFixoCondominio;	
	
	private UIInput componenteTelefoneCelularCondominio;	
	
	
	@PostConstruct
	public void iniciarCondominioMB(){
		this.condominio = new Condominio();
		this.condominio.setSituacao(CondominioSituacaoEnum.ATIVO.getSituacao());
	}
	
	public void pesquisar(ActionEvent event){
		try {			
			this.listaDeCondominios = new ListDataModel<Condominio>(this.condominioService.buscarPorNomeESituacao(this.condominio));
			if (this.listaDeCondominios.getRowCount() == 0){
				ManagedBeanUtil.setMensagemInfo("msg.condominio.semCondominios");
			}		
		} catch (SQLException e) {
			logger.error("erro sqlstate "+e.getSQLState(), e);	
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");				
		} catch (Exception e) {
			logger.error("", e);
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		}
	}
	
	public String pesquisarPorCodigo(){
		String retorno = null;
		try {				
			this.condominio = this.condominioService.buscarPorCodigo(this.condominio.getCodigo());
			if (this.condominio == null){
				ManagedBeanUtil.setMensagemInfo("msg.condominio.condominioCodigoNaoEncontrado");
			}else{
				ManagedBeanUtil.getSession(true).setAttribute(AtributoSessaoEnum.CONDOMINIO.getAtributo(), this.condominio);
				retorno = "cadastroCondominoPrimeiraVez";
			}
		} catch (SQLException e) {
			logger.error("erro sqlstate "+e.getSQLState(), e);	
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");				
		} catch (Exception e) {
			logger.error("", e);
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		}
		return retorno;
	}
	
	
	public void limparFiltroCondominio(ActionEvent event){		
		this.listaDeCondominios = new ListDataModel<Condominio>();
		this.limpaFormListaCondominio();
		this.fechaSessao();
	}
	
		
	public String cancelarEditaCondominio(){	
		return "cancelar";
	}
	
	public String cancelarCadastroCondominio(){		
		this.condominio = new Condominio();
		this.condominio.setSituacao(CondominioSituacaoEnum.ATIVO.getSituacao());
		return "cancelar";
	}	
	
	public String cancelarPesquisaCondominio(){
		return "cancelar";
	}
	
	public String voltarVizualizarCondominio(){
		return "voltar";
	}
	
	public String visualizarCondominio(){
		try {
			this.carregarDadosCondominio();
		} catch (SQLException e) {
			logger.error("erro sqlstate "+e.getSQLState(), e);	
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");	
			return null;
		} catch (Exception e) {
			logger.error("", e);
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
			return null;
		}
		return "visualizar";
	}
	
	public String cadastroCondominio(){
		this.limpaFormCadastroCondominio();		
		this.condominio.setSituacao(1);
		return "cadastrar";
	}
	
	public String editarCondominio(){
		try {
			this.carregarDadosCondominio();
		} catch (SQLException e) {
			logger.error("erro sqlstate "+e.getSQLState(), e);	
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");	
			return null;
		} catch (Exception e) {
			logger.error("", e);
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
			return null;
		}
		return "editar";
	}	
	
	public List<SelectItem> getListaSituacoes(){
		List<SelectItem> listaSituacoes = new ArrayList<SelectItem>();
		listaSituacoes.add(new SelectItem(CondominioSituacaoEnum.INATIVO.getSituacao(), AplicacaoUtil.i18n("condominio.situacao.itemLabel.0")));
		listaSituacoes.add(new SelectItem(CondominioSituacaoEnum.ATIVO.getSituacao(), AplicacaoUtil.i18n("condominio.situacao.itemLabel.1")));
		return listaSituacoes;
	}
	
	public void limparCadastroCondominio(ActionEvent event){
		this.limpaFormCadastroCondominio();
	}
	
	public String salvarCondominio(){		
		try {
			this.condominio.setSituacao(CondominioSituacaoEnum.ATIVO.getSituacao());
			this.condominioService.salvar(this.condominio);
			this.condominio = new Condominio();
			this.condominio.setSituacao(CondominioSituacaoEnum.ATIVO.getSituacao());
			this.pesquisar(null);
			this.limpaFormCadastroCondominio();
			ManagedBeanUtil.setMensagemInfo("msg.condominio.salvoSucesso");			
		} catch (SQLException e) {
			logger.error("erro sqlstate "+e.getSQLState(), e);	
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");	
		} catch (Exception e) {
			logger.error("", e);
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		}
		return "salvar";
	}	
	
	public String atualizarCondominio(){
		try {
			List<Condomino> listaConselheiro = new ArrayList<Condomino>();
			for (CondominoVO condominoVO : this.listaDeConselheirosVOs) {
				Condomino conselheiro = new Condomino();
				conselheiro.setId(condominoVO.getId());						
				listaConselheiro.add(conselheiro);
			}		
			this.condominio.setListaConselheiros(listaConselheiro);		
			// Condição que contempla o caso onde o condomínio foi criado, mas ainda não existe um síndico geral 
			if(this.condominio.getSindicoGeral() == null){
				Condomino sindicoGeral = new Condomino();
				sindicoGeral.setId(this.nomeSindicoGeral.trim().isEmpty() ? -1 : this.idSindicoGeral);			
				this.condominio.setSindicoGeral(sindicoGeral);
			}else{ // Caso já exista um síndico geral, esse trecho de código modifica o síndico geral
				this.condominio.getSindicoGeral().setId(this.nomeSindicoGeral.trim().isEmpty() ? -1 : this.idSindicoGeral);			
			}		
			
			if(this.idSubSindicoGeral != 0){
				Condomino subSindicoGeral = new Condomino();
				// Condição que contempla o caso onde o usuário apagou o nome do subsindico. -1 é igual a branco.
				subSindicoGeral.setId(this.nomeSubSindicoGeral.trim().isEmpty() ? -1 : this.idSubSindicoGeral);
				this.condominio.setSubSindicoGeral(subSindicoGeral);								
			}
			
			/*Os ifs abaixo são para garantir que um condômino não possa assumir mais de um papel (Ex.: síndico geral e subsíndico geral),
			 * pois a aplicação não suporta um usuário associado a mais de um grupo de usuário. */
			if(this.condominio.getSubSindicoGeral() != null && this.condominio.getSindicoGeral().getId() == this.condominio.getSubSindicoGeral().getId()){
				ManagedBeanUtil.setMensagemErro("msg.condominio.sindicoGeralESubSindicoGeralIgual");
				return null;
			}
			
			for (Condomino conselheiro : this.condominio.getListaConselheiros()) {
				if(conselheiro.getId() == this.condominio.getSindicoGeral().getId()){
					ManagedBeanUtil.setMensagemErro("msg.condominio.sindicoGeralEConselheiroIgual");
					return null;
				}
				if(condominio.getSubSindicoGeral() != null && conselheiro.getId() == this.condominio.getSubSindicoGeral().getId()){
					ManagedBeanUtil.setMensagemErro("msg.condominio.subSindicoGeralEConselheiroIgual");
					return null;
				}
			}			
			this.condominioService.atualizar(this.condominio);
			this.pesquisar(null);
			ManagedBeanUtil.setMensagemInfo("msg.condominio.atualizadoSucesso");
		} catch (SQLException e) {
			logger.error("erro sqlstate "+e.getSQLState(), e);	
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");	
		} catch (Exception e) {
			logger.error("", e);
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		}
		return "atualizar";		
	}
	
	
	
	public List<CondominoVO> buscarListaCondominoVO(Object nomeCondomino){	
		List<CondominoVO> listaCondominoVO = null;
		try {
			listaCondominoVO = this.condominoService.buscarListaCondominosVOPorNomeCondominoECondominio(nomeCondomino.toString(), this.condominio);
		} catch (SQLException e) {
			logger.error("erro sqlstate "+e.getSQLState(), e);	
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");	
		} catch (Exception e) {
			logger.error("", e);
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		}
		return listaCondominoVO;
	}	
	
	/**
	 * Método que encontra e id o síndico geral selecionado.	
	 */
	public void popularIdSindicoGeral() {
		  this.idSindicoGeral = Integer.parseInt(this.nomeSindicoGeral);
		  Boolean encontrou = Boolean.FALSE;
		  Integer i = 0;
		  while(!encontrou){		  
			CondominoVO condominoVO = this.listaDeCondominosVOs.get(i++);
			if (condominoVO.getId().equals(this.idSindicoGeral)){
				this.nomeSindicoGeral = condominoVO .getNomeCondomino();
				encontrou = Boolean.TRUE;
			}
		}	
	}
	
	/**
	 * Método que encontra e id o subsíndico geral selecionado.	
	 */
	public void popularIdSubSindicoGeral() {
		  this.idSubSindicoGeral = Integer.parseInt(this.nomeSubSindicoGeral);
		  Boolean encontrou = Boolean.FALSE;
		  Integer i = 0;
		  while(!encontrou){		  
			CondominoVO condominoVO = this.listaDeCondominosVOs.get(i++);
			if (condominoVO.getId().equals(this.idSubSindicoGeral)){
				this.nomeSubSindicoGeral = condominoVO .getNomeCondomino();
				encontrou = Boolean.TRUE;
			}
		}	
	}
	
	public List<SelectItem> buscarListaCondominiosAtivos() {
		List<SelectItem> listaSICondominios = null;		
		try {
			listaSICondominios = new ArrayList<SelectItem>();
			List<Condominio> listaCondominios = null;
			listaCondominios = this.condominioService.buscarTodos();
			for (int i = 0; i < listaCondominios.size(); i++) {
				Condominio condominio = listaCondominios.get(i);
				if(condominio.getSituacao() == CondominioSituacaoEnum.ATIVO.getSituacao()){
					listaSICondominios.add(new SelectItem(condominio.getId(), condominio.getNome()));					
				}
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
	
	private void carregarDadosCondominio() throws  SQLException, Exception{
		this.condominio = this.listaDeCondominios.getRowData();
		this.condominioService.associarEndereco(condominio);		
		Usuario sindicoGeral = this.usuarioService.buscarSindicoGeralPorCondominio(this.condominio);
		this.listaDeCondominosVOs = condominoService.buscarListaCondominosVOPorCondominioBlocoEUnidade(this.condominio,null,null);
		this.condominioPossuiCondominos = this.listaDeCondominosVOs.size() == 0 ? Boolean.FALSE: Boolean.TRUE;
		List<Condomino> listaDeConselheirosLocal = this.condominoService.buscarConselheirosPorCondominio(this.condominio);
		this.listaDeConselheirosVOs = new ArrayList<CondominoVO>();		
		for (Condomino conselheiro : listaDeConselheirosLocal) {
			for (int j = 0; j < this.listaDeCondominosVOs.size(); j++) {
				this.condominoVO  = this.listaDeCondominosVOs.get(j);
				if(conselheiro.getId() == this.condominoVO.getId()){					
					this.listaDeConselheirosVOs.add(this.condominoVO);
				}				
			}			
		}		
		if(sindicoGeral != null){
			this.idSindicoGeral  = sindicoGeral.getId();
			this.nomeSindicoGeral = sindicoGeral.getNome();
		}
		Condomino subSindicoGeral = this.condominoService.buscarSubSindicoGeralPorCondominio(this.condominio);
		if(subSindicoGeral != null){
			this.idSubSindicoGeral = subSindicoGeral.getId(); 
			this.nomeSubSindicoGeral = subSindicoGeral.getNome();			
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

	
	private void limpaFormListaCondominio(){
		this.condominio = new Condominio();
		this.condominio.setSituacao(CondominioSituacaoEnum.ATIVO.getSituacao());
		ManagedBeanUtil.cleanSubmittedValues(this.componenteNomeCondominio);
		
	}
	
	private void limpaFormCadastroCondominio(){
		this.condominio = new Condominio();
		ManagedBeanUtil.cleanSubmittedValues(this.componenteNomeCondominio);		
		ManagedBeanUtil.cleanSubmittedValues(this.componenteEnderecoCondominio);
		ManagedBeanUtil.cleanSubmittedValues(this.componenteEnderecoNumeroCondominio);
		ManagedBeanUtil.cleanSubmittedValues(this.componenteEnderecoComplementoCondominio);
		ManagedBeanUtil.cleanSubmittedValues(this.componenteEnderecoCepCondominio);
		ManagedBeanUtil.cleanSubmittedValues(this.componenteEnderecoBairroCondominio);
		ManagedBeanUtil.cleanSubmittedValues(this.componenteEnderecoCidadeCondominio);
		ManagedBeanUtil.cleanSubmittedValues(this.componenteEnderecoUfCondominio);
		ManagedBeanUtil.cleanSubmittedValues(this.componenteEnderecoPaisCondominio);
		ManagedBeanUtil.cleanSubmittedValues(this.componenteCnpjCondominio);
		ManagedBeanUtil.cleanSubmittedValues(this.componenteTelefoneCelularCondominio);
		ManagedBeanUtil.cleanSubmittedValues(this.componenteTelefoneFixoCondominio);
	}

	public Condominio getCondominio() {
		return condominio;
	}

	public void setCondominio(Condominio condominio) {
		this.condominio = condominio;
	}

	public CondominoVO getCondominoVO() {
		return condominoVO;
	}

	public void setCondominoVO(CondominoVO condominoVO) {
		this.condominoVO = condominoVO;
	}
	
	public String getNomeSindicoGeral() {
		return nomeSindicoGeral;
	}

	public void setNomeSindicoGeral(String nomeSindicoGeral) {
		this.nomeSindicoGeral = nomeSindicoGeral;
	}

	public Integer getIdSindicoGeral() {
		return idSindicoGeral;
	}

	public void setIdSindicoGeral(Integer idSindicoGeral) {
		this.idSindicoGeral = idSindicoGeral;
	}	

	public Integer getIdSubSindicoGeral() {
		return idSubSindicoGeral;
	}

	public void setIdSubSindicoGeral(Integer idSubSindicoGeral) {
		this.idSubSindicoGeral = idSubSindicoGeral;
	}

	public String getNomeSubSindicoGeral() {
		return nomeSubSindicoGeral;
	}

	public void setNomeSubSindicoGeral(String nomeSubSindicoGeral) {
		this.nomeSubSindicoGeral = nomeSubSindicoGeral;
	}

	public ListDataModel<Condominio> getListaDeCondominios() {
		return listaDeCondominios;
	}

	public void setListaDeCondominios(ListDataModel<Condominio> listaDeCondominios) {
		this.listaDeCondominios = listaDeCondominios;
	}

	public List<CondominoVO> getListaDeCondominosVOs() {
		return listaDeCondominosVOs;
	}

	public void setListaDeCondominosVOs(List<CondominoVO> listaDeCondominosVOs) {
		this.listaDeCondominosVOs = listaDeCondominosVOs;
	}

	public List<CondominoVO> getListaDeConselheirosVOs() {
		return listaDeConselheirosVOs;
	}

	public void setListaDeConselheirosVOs(List<CondominoVO> listaDeConselheirosVOs) {
		this.listaDeConselheirosVOs = listaDeConselheirosVOs;
	}	

	public CondominioService getCondominioService() {
		return condominioService;
	}

	public void setCondominioService(CondominioService condominioService) {
		this.condominioService = condominioService;
	}

	public CondominoService getCondominoService() {
		return condominoService;
	}

	public void setCondominoService(CondominoService condominoService) {
		this.condominoService = condominoService;
	}

	public Conversation getConversation() {
		return conversation;
	}

	public void setConversation(Conversation conversation) {
		this.conversation = conversation;
	}	

	public Boolean getCondominioPossuiCondominos() {
		return condominioPossuiCondominos;
	}

	public void setCondominioPossuiCondominos(Boolean condominioPossuiCondominos) {
		this.condominioPossuiCondominos = condominioPossuiCondominos;
	}

	public UIInput getComponenteNomeCondominio() {
		return componenteNomeCondominio;
	}

	public void setComponenteNomeCondominio(UIInput componenteNomeCondominio) {
		this.componenteNomeCondominio = componenteNomeCondominio;
	}

	public UISelectItems getComponenteItensSituacaoCondominio() {
		return componenteItensSituacaoCondominio;
	}

	public void setComponenteItensSituacaoCondominio(
			UISelectItems componenteItensSituacaoCondominio) {
		this.componenteItensSituacaoCondominio = componenteItensSituacaoCondominio;
	}

	public UISelectItem getComponenteItemSituacaoCondominio() {
		return componenteItemSituacaoCondominio;
	}

	public void setComponenteItemSituacaoCondominio(
			UISelectItem componenteItemSituacaoCondominio) {
		this.componenteItemSituacaoCondominio = componenteItemSituacaoCondominio;
	}

	public UISelectItems getComponenteItensCondominio() {
		return componenteItensCondominio;
	}

	public void setComponenteItensCondominio(UISelectItems componenteItensCondominio) {
		this.componenteItensCondominio = componenteItensCondominio;
	}

	public UIInput getComponenteEnderecoCondominio() {
		return componenteEnderecoCondominio;
	}

	public void setComponenteEnderecoCondominio(UIInput componenteEnderecoCondominio) {
		this.componenteEnderecoCondominio = componenteEnderecoCondominio;
	}

	public UIInput getComponenteEnderecoNumeroCondominio() {
		return componenteEnderecoNumeroCondominio;
	}

	public void setComponenteEnderecoNumeroCondominio(
			UIInput componenteEnderecoNumeroCondominio) {
		this.componenteEnderecoNumeroCondominio = componenteEnderecoNumeroCondominio;
	}

	public UIInput getComponenteEnderecoComplementoCondominio() {
		return componenteEnderecoComplementoCondominio;
	}

	public void setComponenteEnderecoComplementoCondominio(
			UIInput componenteEnderecoComplementoCondominio) {
		this.componenteEnderecoComplementoCondominio = componenteEnderecoComplementoCondominio;
	}

	public UIInput getComponenteEnderecoCepCondominio() {
		return componenteEnderecoCepCondominio;
	}

	public void setComponenteEnderecoCepCondominio(
			UIInput componenteEnderecoCepCondominio) {
		this.componenteEnderecoCepCondominio = componenteEnderecoCepCondominio;
	}

	public UIInput getComponenteEnderecoBairroCondominio() {
		return componenteEnderecoBairroCondominio;
	}

	public void setComponenteEnderecoBairroCondominio(
			UIInput componenteEnderecoBairroCondominio) {
		this.componenteEnderecoBairroCondominio = componenteEnderecoBairroCondominio;
	}

	public UIInput getComponenteEnderecoCidadeCondominio() {
		return componenteEnderecoCidadeCondominio;
	}

	public void setComponenteEnderecoCidadeCondominio(
			UIInput componenteEnderecoCidadeCondominio) {
		this.componenteEnderecoCidadeCondominio = componenteEnderecoCidadeCondominio;
	}

	public UIInput getComponenteEnderecoUfCondominio() {
		return componenteEnderecoUfCondominio;
	}

	public void setComponenteEnderecoUfCondominio(
			UIInput componenteEnderecoUfCondominio) {
		this.componenteEnderecoUfCondominio = componenteEnderecoUfCondominio;
	}

	public UIInput getComponenteEnderecoPaisCondominio() {
		return componenteEnderecoPaisCondominio;
	}

	public void setComponenteEnderecoPaisCondominio(
			UIInput componenteEnderecoPaisCondominio) {
		this.componenteEnderecoPaisCondominio = componenteEnderecoPaisCondominio;
	}

	public UIInput getComponenteCnpjCondominio() {
		return componenteCnpjCondominio;
	}

	public void setComponenteCnpjCondominio(UIInput componenteCnpjCondominio) {
		this.componenteCnpjCondominio = componenteCnpjCondominio;
	}

	public UIInput getComponenteTelefoneFixoCondominio() {
		return componenteTelefoneFixoCondominio;
	}

	public void setComponenteTelefoneFixoCondominio(
			UIInput componenteTelefoneFixoCondominio) {
		this.componenteTelefoneFixoCondominio = componenteTelefoneFixoCondominio;
	}

	public UIInput getComponenteTelefoneCelularCondominio() {
		return componenteTelefoneCelularCondominio;
	}

	public void setComponenteTelefoneCelularCondominio(
			UIInput componenteTelefoneCelularCondominio) {
		this.componenteTelefoneCelularCondominio = componenteTelefoneCelularCondominio;
	}		
	
}