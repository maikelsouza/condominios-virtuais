package br.com.condominiosvirtuais.controller;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Instance;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.log4j.Logger;

import br.com.condominiosvirtuais.entity.Obra;
import br.com.condominiosvirtuais.entity.ResponsavelObra;
import br.com.condominiosvirtuais.enumeration.ObraSituacaoEnum;
import br.com.condominiosvirtuais.enumeration.ObraTipoEnum;
import br.com.condominiosvirtuais.enumeration.ResponsavelObraTipoPessoaEnum;
import br.com.condominiosvirtuais.exception.BusinessException;
import br.com.condominiosvirtuais.service.impl.ObraServiceImpl;
import br.com.condominiosvirtuais.service.impl.ResponsavelObraServiceImpl;
import br.com.condominiosvirtuais.util.AplicacaoUtil;
import br.com.condominiosvirtuais.util.ManagedBeanUtil;

@Named @SessionScoped
public class ObraMB implements Serializable {
	
	
	private static final long serialVersionUID = 1L;

	private static Logger logger = Logger.getLogger(ObraMB.class);
	
	@Inject
	private Instance<CondominioMB> condominioMB = null;
	
	@Inject
	private ObraServiceImpl obraService;
	
	@Inject
	private ResponsavelObraServiceImpl responsavelObraService;
	
	private List<SelectItem> listaSICondominios;
	
	private List<SelectItem> listaSITipo;
	
	private List<SelectItem> listaSISituacao;
	
	private List<SelectItem> listaSITipoPessoa;
	
	private ListDataModel<Obra> listaObraDataModel = null;
	
	private List<ResponsavelObra> listaResponsavelObra;
	
	private Obra obra;
	
	private String nomeResponsavelObra;
	
	private Integer idNomeResponsavelObra;
	
	private String razaoSocialResponsavelObra;
	
	private Integer idRazaoSocialResponsavelObra;
	
	private Date dataInicioDe;
	
	private Date dataInicioAte;
	
	private Integer tipoPessoa;
	
	private Integer idCondominio;
	
	private Boolean exibeDadosPessoaFisica;
	
	@PostConstruct
	public void carregarObra(){
		this.inicializarTela();
		this.populaSituacaoObra();
		this.populaTipoObra();		
		this.populaTipoPessoaResponsavelObra();
	}
	
	public String salvarObra(){
		try {
			if(this.validaTelaCadastro() == Boolean.FALSE){
				return null;
			}
			this.obra.getResponsavelObra().setNome(this.nomeResponsavelObra);
			this.obra.getResponsavelObra().setRazaoSocial(this.razaoSocialResponsavelObra);
			// Situação onde o responsável da obra não está cadastrado no sistema 
			if(this.idNomeResponsavelObra != null || this.idRazaoSocialResponsavelObra != null){
				this.obraService.salvarObraEAtualizarResponsavel(this.obra);
			}else{
				this.obraService.salvarObraESalvaResponsavelObra(this.obra);				
			}
			this.dataInicioDe = this.obra.getDataInicio();
			this.dataInicioAte = this.obra.getDataFim();
			this.idCondominio = this.obra.getIdCondominio();
			this.inicializarTela();
			this.pesquisarObra();
			ManagedBeanUtil.setMensagemInfo("msg.obra.salvaSucesso");
		} catch (SQLException e) {
			logger.error("erro sqlstate "+e.getSQLState(), e);	
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");			
		} catch (BusinessException e) {				
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage());			
		} catch (Exception e) {
			logger.error("", e);
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");			
		}	
		return "salvar";
	}
	
	public String excluirObra(){		
		try {
			this.obraService.excluir(this.obra.getId());
			this.pesquisarObra();
			ManagedBeanUtil.setMensagemInfo("msg.obra.excluidaSucesso");
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
	
	public String atualizarObra(){		
		try {
			if(this.validaTelaCadastro() == Boolean.FALSE){
				return null;
			}
			if(this.obra.getResponsavelObra().getTipoPessoa() == ResponsavelObraTipoPessoaEnum.PESSOA_FISICA.getTipoPessoa()){
				this.obra.getResponsavelObra().setNome(this.nomeResponsavelObra);
				this.obra.getResponsavelObra().setRazaoSocial(null);				
			}else{
				this.obra.getResponsavelObra().setNome(null);
				this.obra.getResponsavelObra().setRazaoSocial(this.razaoSocialResponsavelObra);
			}
			// Situação onde o responsável da obra não está cadastrado no sistema 
			if(this.idNomeResponsavelObra != null || this.idRazaoSocialResponsavelObra != null){
				this.obraService.atualizarObraEAtualizarResponsavelObra(this.obra);
			}else{
				this.obraService.atualizarObraESalvarResponsavelObra(this.obra);				
			}		
			this.pesquisarObra();
			ManagedBeanUtil.setMensagemInfo("msg.obra.atualizadaSucesso");
		} catch (SQLException e) {
			logger.error("erro sqlstate "+e.getSQLState(), e);	
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");			
		} catch (BusinessException e) {				
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage());			
		} catch (Exception e) {
			logger.error("", e);
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");			
		}
		return "atualizar";
	}
	
	public String voltarEditarObra(){
		return "voltar";
	}
	
	public String voltarVisualizarObra(){
		return "voltar";
	}	
	              
	public String cancelarFiltroPesquisaObra(){
		return "cancelar";
	}
	
	public String cancelarVisualizarObra(){
		return "cancelar";
	}
	             
	public String cancelarCadastroObra(){
		return "cancelar";
	}
	
	public String limparCadastroObra(){
		this.inicializarTela();
		return null;
	}
	
	public void limparFiltroPesquisaObra(){
		this.obra = new Obra();
		this.dataInicioDe = null;
		this.dataInicioAte = null;
		this.listaObraDataModel = new ListDataModel<Obra>();
	}
	
	
	public String editarObra(){
		this.obra = (Obra) this.listaObraDataModel.getRowData();		
		this.nomeResponsavelObra = null;
		this.razaoSocialResponsavelObra = null;
		this.idNomeResponsavelObra = this.obra.getResponsavelObra().getId();
		this.exibeDadosPessoaFisica =  Boolean.FALSE;
		if(this.obra.getResponsavelObra().getTipoPessoa() == ResponsavelObraTipoPessoaEnum.PESSOA_FISICA.getTipoPessoa()){
			this.exibeDadosPessoaFisica = Boolean.TRUE;
			this.nomeResponsavelObra = this.obra.getResponsavelObra().getNome();
			this.tipoPessoa = ResponsavelObraTipoPessoaEnum.PESSOA_FISICA.getTipoPessoa();
		}else{
			this.razaoSocialResponsavelObra = this.obra.getResponsavelObra().getRazaoSocial();
			this.tipoPessoa = ResponsavelObraTipoPessoaEnum.PESSOA_JURIDICA.getTipoPessoa();
		}
		return "editar";
	}
	
	public List<ResponsavelObra> buscarListaResponsavelObra(Object nomeResponsavelObra){
		try {
			this.listaResponsavelObra = this.responsavelObraService.buscarPorNomeETipoPessoa(nomeResponsavelObra.toString(), this.tipoPessoa);						
		} catch (SQLException e) {
			logger.error("erro sqlstate "+e.getSQLState(), e);	
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");	
		} catch (Exception e) {
			logger.error("", e);
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		}
		return this.listaResponsavelObra;
	}	
	
	public void pesquisarObra(){
		try {
			this.listaObraDataModel = new ListDataModel<Obra>(this.obraService.buscarPorCondominioEPeriodoDeAte(this.idCondominio, this.dataInicioDe, this.dataInicioAte));
			if(this.listaObraDataModel .getRowCount() == 0){
				ManagedBeanUtil.setMensagemInfo("msg.obra.semObra");
			}
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
	
	/**
	 * Método que encontra o id do nome do visitante selecionado	
	 */
	public void popularIdNomeResponsavelObra() {		
		  Boolean encontrou = Boolean.FALSE;
		  this.idNomeResponsavelObra = null;
		  if(ManagedBeanUtil.verificaSeExisteSomenteNumeros(this.nomeResponsavelObra)){
			  this.idNomeResponsavelObra = Integer.parseInt(this.nomeResponsavelObra);
			  Integer i = 0;
			  while(!encontrou){
				  ResponsavelObra responsavelObra = this.listaResponsavelObra.get(i++);			
				  if (responsavelObra.getId().equals(this.idNomeResponsavelObra)){
					  this.nomeResponsavelObra = responsavelObra.getNome();
					  this.obra.getResponsavelObra().setEmail(responsavelObra.getEmail());
					  this.obra.getResponsavelObra().setCPF(responsavelObra.getCPF());
					  this.obra.getResponsavelObra().setSite(responsavelObra.getSite());
					  this.obra.getResponsavelObra().setTelefone(responsavelObra.getTelefone());
					  this.obra.getResponsavelObra().setId(responsavelObra.getId());
					  encontrou = Boolean.TRUE;
				  }
			  }
		  }else{
			  this.obra.getResponsavelObra().setEmail(null);
			  this.obra.getResponsavelObra().setCNPJ(null);
			  this.obra.getResponsavelObra().setCPF(null);
			  this.obra.getResponsavelObra().setSite(null);
			  this.obra.getResponsavelObra().setTelefone(null);
			  this.obra.getResponsavelObra().setId(null);
		  }
	  }
	
	/**
	 * Método que encontra o id do nome do visitante selecionado	
	 */
	public void popularIdRazaoSocialResponsavelObra() {		
		  Boolean encontrou = Boolean.FALSE;
		  this.idRazaoSocialResponsavelObra = null;
		  if(ManagedBeanUtil.verificaSeExisteSomenteNumeros(this.razaoSocialResponsavelObra)){
			  this.idRazaoSocialResponsavelObra = Integer.parseInt(this.razaoSocialResponsavelObra);
			  Integer i = 0;
			  while(!encontrou){
				  ResponsavelObra responsavelObra = this.listaResponsavelObra.get(i++);			
				  if (responsavelObra.getId().equals(this.idRazaoSocialResponsavelObra)){
					  this.razaoSocialResponsavelObra = responsavelObra.getRazaoSocial();
					  this.obra.getResponsavelObra().setEmail(responsavelObra.getEmail());
					  this.obra.getResponsavelObra().setCNPJ(responsavelObra.getCNPJ());
					  this.obra.getResponsavelObra().setSite(responsavelObra.getSite());
					  this.obra.getResponsavelObra().setTelefone(responsavelObra.getTelefone());
					  this.obra.getResponsavelObra().setId(responsavelObra.getId());
					  encontrou = Boolean.TRUE;
				  }
			  }
		  }else{
			  this.obra.getResponsavelObra().setEmail(null);
			  this.obra.getResponsavelObra().setCNPJ(null);
			  this.obra.getResponsavelObra().setCPF(null);
			  this.obra.getResponsavelObra().setSite(null);
			  this.obra.getResponsavelObra().setTelefone(null);
			  this.obra.getResponsavelObra().setId(null);			  
		  }
	  }
	
	public String visualizarObra(){
		this.obra = (Obra) this.listaObraDataModel.getRowData();		
		return "visualizar";		
	}
	
	private void populaTipoObra(){
		this.listaSITipo = new ArrayList<SelectItem>();
		this.listaSITipo.add(new SelectItem(ObraTipoEnum.URGENTE.getTipo(), AplicacaoUtil.i18n("obra.tipo.0")));
		this.listaSITipo.add(new SelectItem(ObraTipoEnum.PLANEJADA.getTipo(), AplicacaoUtil.i18n("obra.tipo.1")));
	}
	
	private void populaSituacaoObra(){
		this.listaSISituacao = new ArrayList<SelectItem>();
		this.listaSISituacao.add(new SelectItem(ObraSituacaoEnum.EM_ANDAMENTO.getSituacao(), AplicacaoUtil.i18n("obra.situacao.0")));
		this.listaSISituacao.add(new SelectItem(ObraSituacaoEnum.CONCLUIDA.getSituacao(), AplicacaoUtil.i18n("obra.situacao.1")));
	}
	
	private void populaTipoPessoaResponsavelObra(){
		this.listaSITipoPessoa = new ArrayList<SelectItem>();
		this.listaSITipoPessoa.add(new SelectItem(ResponsavelObraTipoPessoaEnum.PESSOA_FISICA.getTipoPessoa(), AplicacaoUtil.i18n("obra.responsavelObra.tipoPessoa.0")));
		this.listaSITipoPessoa.add(new SelectItem(ResponsavelObraTipoPessoaEnum.PESSOA_JURIDICA.getTipoPessoa(), AplicacaoUtil.i18n("obra.responsavelObra.tipoPessoa.1")));
	}
	
	public void inicializarTela(){
		this.obra = new Obra();
		this.nomeResponsavelObra = null;
		this.razaoSocialResponsavelObra = null;
		this.exibeDadosPessoaFisica = Boolean.TRUE;
		this.tipoPessoa = ResponsavelObraTipoPessoaEnum.PESSOA_FISICA.getTipoPessoa();
	}
	
	public void inicializaPessoa(){
		if(this.obra.getResponsavelObra().getTipoPessoa() == null){
			this.tipoPessoa = ResponsavelObraTipoPessoaEnum.PESSOA_FISICA.getTipoPessoa();
		}else{
			this.tipoPessoa = this.obra.getResponsavelObra().getTipoPessoa();
		}
	}
	
	private Boolean validaTelaCadastro(){
		Boolean telaValidada = Boolean.TRUE;
		if(!this.obra.getResponsavelObra().getEmail().trim().equals("") && !ManagedBeanUtil.validaEmail(this.obra.getResponsavelObra().getEmail()) ){
			ManagedBeanUtil.setMensagemErro("msg.obra.responsavelObra.email.formatoEmailInvalido");
			telaValidada = Boolean.FALSE;
		}
		if(this.obra.getDataFim() != null && this.obra.getDataInicio().after(this.obra.getDataFim())){
			ManagedBeanUtil.setMensagemErro("msg.obra.dataInicioMaiorDataFim");
			telaValidada = Boolean.FALSE;
		}
		return telaValidada;
	}
	
	public void exibirDadosPessoaFisica(){
		this.exibeDadosPessoaFisica =  Boolean.FALSE;
		if(this.obra.getResponsavelObra().getTipoPessoa() == ResponsavelObraTipoPessoaEnum.PESSOA_FISICA.getTipoPessoa()){
			this.exibeDadosPessoaFisica = Boolean.TRUE;
		}
		this.tipoPessoa = this.obra.getResponsavelObra().getTipoPessoa();
		this.obra.getResponsavelObra().setEmail(null);
		this.obra.getResponsavelObra().setCNPJ(null);
		this.obra.getResponsavelObra().setCPF(null);
		this.obra.getResponsavelObra().setSite(null);
		this.obra.getResponsavelObra().setTelefone(null);
		this.obra.getResponsavelObra().setId(null);
		this.nomeResponsavelObra = null;
		this.razaoSocialResponsavelObra = null;
		this.idNomeResponsavelObra = null;
		this.idRazaoSocialResponsavelObra = null;
	}

	public List<SelectItem> getListaSICondominios() {
		this.listaSICondominios = this.condominioMB.get().buscarListaCondominiosAtivos();
		return listaSICondominios;
	}

	public void setListaSICondominios(List<SelectItem> listaSICondominios) {		
		this.listaSICondominios = listaSICondominios;
	}

	public List<SelectItem> getListaSITipo() {
		return listaSITipo;
	}

	public void setListaSITipo(List<SelectItem> listaSITipo) {
		this.listaSITipo = listaSITipo;
	}

	public Obra getObra() {
		return obra;
	}

	public void setObra(Obra obra) {
		this.obra = obra;
	}

	public List<SelectItem> getListaSISituacao() {
		return listaSISituacao;
	}

	public void setListaSISituacao(List<SelectItem> listaSISituacao) {
		this.listaSISituacao = listaSISituacao;
	}

	public String getNomeResponsavelObra() {
		return nomeResponsavelObra;
	}

	public void setNomeResponsavelObra(String nomeResponsavelObra) {
		this.nomeResponsavelObra = nomeResponsavelObra;
	}

	public Integer getIdNomeResponsavelObra() {
		return idNomeResponsavelObra;
	}

	public void setIdNomeResponsavelObra(Integer idNomeResponsavelObra) {
		this.idNomeResponsavelObra = idNomeResponsavelObra;
	}

	public ListDataModel<Obra> getListaObraDataModel() {
		return listaObraDataModel;
	}

	public void setListaObraDataModel(ListDataModel<Obra> listaObraDataModel) {
		this.listaObraDataModel = listaObraDataModel;
	}

	public Date getDataInicioDe() {
		return dataInicioDe;
	}

	public void setDataInicioDe(Date dataInicioDe) {
		this.dataInicioDe = dataInicioDe;
	}

	public Date getDataInicioAte() {
		return dataInicioAte;
	}

	public void setDataInicioAte(Date dataInicioAte) {
		this.dataInicioAte = dataInicioAte;
	}

	public List<SelectItem> getListaSITipoPessoa() {
		return listaSITipoPessoa;
	}

	public void setListaSITipoPessoa(List<SelectItem> listaSITipoPessoa) {
		this.listaSITipoPessoa = listaSITipoPessoa;
	}

	public String getRazaoSocialResponsavelObra() {
		return razaoSocialResponsavelObra;
	}

	public void setRazaoSocialResponsavelObra(String razaoSocialResponsavelObra) {
		this.razaoSocialResponsavelObra = razaoSocialResponsavelObra;
	}

	public Integer getIdRazaoSocialResponsavelObra() {
		return idRazaoSocialResponsavelObra;
	}

	public void setIdRazaoSocialResponsavelObra(Integer idRazaoSocialResponsavelObra) {
		this.idRazaoSocialResponsavelObra = idRazaoSocialResponsavelObra;
	}

	public Integer getTipoPessoa() {		
		return tipoPessoa;
	}

	public void setTipoPessoa(Integer tipoPessoa) {
		this.tipoPessoa = tipoPessoa;
	}

	public Boolean getExibeDadosPessoaFisica() {		
		return exibeDadosPessoaFisica;
	}

	public void setExibeDadosPessoaFisica(Boolean exibeDadosPessoaFisica) {
		this.exibeDadosPessoaFisica = exibeDadosPessoaFisica;
	}

	public Integer getIdCondominio() {
		return idCondominio;
	}

	public void setIdCondominio(Integer idCondominio) {
		this.idCondominio = idCondominio;
	}	

}
