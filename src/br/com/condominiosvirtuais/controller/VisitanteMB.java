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

import br.com.condominiosvirtuais.entity.Bloco;
import br.com.condominiosvirtuais.entity.Condominio;
import br.com.condominiosvirtuais.entity.Condomino;
import br.com.condominiosvirtuais.entity.PrestadorServico;
import br.com.condominiosvirtuais.entity.Unidade;
import br.com.condominiosvirtuais.entity.Visita;
import br.com.condominiosvirtuais.entity.Visitante;
import br.com.condominiosvirtuais.enumeration.VisitantePrestarServicoEnum;
import br.com.condominiosvirtuais.enumeration.VisitanteQuemIraVisitarEnum;
import br.com.condominiosvirtuais.enumeration.VisitanteTipoDocumentoEnum;
import br.com.condominiosvirtuais.exception.BusinessException;
import br.com.condominiosvirtuais.service.impl.BlocoServiceImpl;
import br.com.condominiosvirtuais.service.impl.CondominioServiceImpl;
import br.com.condominiosvirtuais.service.impl.CondominoServiceImpl;
import br.com.condominiosvirtuais.service.impl.PrestadorServicoServiceImpl;
import br.com.condominiosvirtuais.service.impl.UnidadeServiceImpl;
import br.com.condominiosvirtuais.service.impl.VisitanteServiceImpl;
import br.com.condominiosvirtuais.util.AplicacaoUtil;
import br.com.condominiosvirtuais.util.ManagedBeanUtil;
import br.com.condominiosvirtuais.vo.VisitaVO;

@Named @SessionScoped
public class VisitanteMB implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private static Logger logger = Logger.getLogger(VisitanteMB.class);
	
	private Visitante visitante;	
	
	private List<SelectItem> listaSIQuemIraVisitar;
	
	private List<SelectItem> listaSITipoDocumento;
	
	private List<SelectItem> listaSIPrestadorServico;
	
	private List<SelectItem> listaSICondominios;
	
	private List<SelectItem> listaSIBlocos;	
	
	private List<SelectItem> listaSIUnidades;
	
	private List<SelectItem> listaSICondominos;	
	
	private List<Visitante> listaVisitante;
	
	private List<PrestadorServico> listaPrestadorServico;
	
	private ListDataModel<Visitante> listaVisitanteDataModel;
	
	private ListDataModel<VisitaVO> listaVisitaDataModel;
	
	@Inject
	private Instance<CondominioMB> condominioMB = null;
	
	@Inject
	private Instance<BlocoMB> blocoMB;
	
	@Inject
	private Instance<UnidadeMB> unidadeMB;

	@Inject
	private Instance<CondominoMB> condominoMB;
	
	@Inject
	private VisitanteServiceImpl visitanteService;
	
	@Inject
	private CondominioServiceImpl condominioService;
	
	@Inject
	private BlocoServiceImpl blocoService;
	
	@Inject
	private UnidadeServiceImpl unidadeService;
	
	@Inject
	private CondominoServiceImpl condominoService;
	
	@Inject
	private PrestadorServicoServiceImpl prestadorServicoService;
	
	private Integer quemIraVisitar;	
	
	private Boolean painelLocalVisita;
	
	private Boolean exibeCampoRg;
	
	private Integer tipoDocumento;
	
	private Boolean painelPrestadorServico;
	
	private Boolean desabilitaCampoNomeVisitante;
	
	private String nomeVisitante;
	
	private Integer idNomeVisitante = null;
	
	private String nomePrestadorServico;
	
	private Integer idNomePrestadorServico = null;
	
	private Date dataDe;
	
	private Date dataAte;
	
	private PrestadorServico prestadorServico;
	
	private Integer idCondominio;
	
	

	@PostConstruct
	public void iniciarVisitanteMB(){
		this.popularListaSIQuemIraVisitar();
		this.popularListaSITipoDocumento();
		this.popularListaSIPrestadorServico();
		this.inicializarTela();
	}	
	
	public String salvarVisitante(){
		try {
			if(this.visitante.getVisita().getPrestarServico() == VisitantePrestarServicoEnum.SIM.getPrestarServico() && 
					!this.visitante.getVisita().getPrestadorServico().getEmail().trim().equals("") ){
				if (!ManagedBeanUtil.validaEmail(this.visitante.getVisita().getPrestadorServico().getEmail())){
					ManagedBeanUtil.setMensagemErro("msg.visitante.visita.prestadorServico.formatoEmailInvalido");
					return null;
				}
			}
			this.visitante.setNome(this.nomeVisitante);			
			this.visitante.getVisita().setDataInicio(new Date());
			this.visitante.getVisita().getPrestadorServico().setNome(this.nomePrestadorServico);
			if (this.visitante.getVisita().getPrestarServico() == VisitantePrestarServicoEnum.SIM.getPrestarServico()){
				// Situação onde salva um novo visitante e também um novo prestador de serviço
				if(this.idNomeVisitante == null && this.idNomePrestadorServico == null){
					this.visitanteService.salvarVisitanteESalvarPrestadorServico(this.visitante);
				}else if(this.idNomeVisitante == null && this.idNomePrestadorServico != null){ // Situação onde salva um novo visitante e atualiza um prestador de serviço
					this.visitante.getVisita().getPrestadorServico().setId(this.idNomePrestadorServico);
					this.visitanteService.salvarVisitanteEAtualizarPrestadorServico(this.visitante);
				}else if(this.idNomeVisitante != null && this.idNomePrestadorServico == null){ // Situação onde atualiza um visitante e salva um novo prestador de serviço
					this.visitante.setId(this.idNomeVisitante);
					this.visitanteService.atualizarVisitanteESalvarPrestadorServico(this.visitante);
				}else{ // Situação onde atualiza um visitante e atualiza um  prestador de serviço
					this.visitante.setId(this.idNomeVisitante);
					this.visitante.getVisita().getPrestadorServico().setId(this.idNomePrestadorServico);
					this.visitanteService.atualizarVisitanteEAtualizarPrestadorServico(this.visitante);
				}				
			}else{
				// Situação onde salva um novo visitante
				if(this.idNomeVisitante == null){
					this.visitanteService.salvarSomenteVisitante(this.visitante);
				}else{ // Situação onde atualiza um novo visitante
					this.visitante.setId(this.idNomeVisitante);
					this.visitanteService.atualizarSomenteVisitante(this.visitante);
				}
			}			
			this.inicializarTela();
			ManagedBeanUtil.setMensagemInfo("msg.visitante.salvoSucesso");
		} catch (SQLException e) {
			logger.error("erro sqlstate "+e.getSQLState(), e);	
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");			
		} catch (BusinessException e) {				
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage());			
		} catch (Exception e) {
			logger.error("", e);
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");			
		}
		return null;
	}
	
	public String pesquisarVisitantes(){
		try {
			if(this.dataDe.after(dataAte)){
				ManagedBeanUtil.setMensagemErro("msg.visitante.dataDeMaiorDataAte");
				return null;
			}
			this.listaVisitanteDataModel = new ListDataModel<Visitante>(this.visitanteService.buscarPorPeriodoEIdCondominio(this.dataDe, this.dataAte, this.idCondominio));
			if(this.listaVisitanteDataModel.getRowCount() == 0){
				ManagedBeanUtil.setMensagemInfo("msg.visitante.semVisitante");
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
		return null;
	}
	
	public String visualizarVisitas(){
		Visitante visitante = this.listaVisitanteDataModel.getRowData();
		List<VisitaVO> listaVisitaVO = new ArrayList<VisitaVO>();
		VisitaVO visitaVO = null;
		Bloco bloco = null;
		Unidade unidade = null;
		Condomino condomino = null;
			try {
				for (Visita visita : visitante.getListaVisita()) {
					visitaVO = new VisitaVO();
					visitaVO.setId(visita.getId());
					visitaVO.setDataInicio(visita.getDataInicio());
					visitaVO.setDataFim(visita.getDataFim());
					visitaVO.setPrestarServico(visita.getPrestarServico());
					visitaVO.setPrestadorServico(visita.getPrestadorServico());
					visitaVO.setNomeCondominio(this.condominioService.buscarPorId(visita.getIdCondominio()).getNome());
					bloco = this.blocoService.buscarPorId(visita.getIdBloco()); 
					visitaVO.setNomeBloco(bloco != null ? bloco.getNome() : null);
					unidade = this.unidadeService.buscarPorId(visita.getIdUnidade());
					visitaVO.setNumeroUnidade(unidade != null ? unidade.getNumero() : null);
					condomino = this.condominoService.buscarPorId(visita.getIdCondomino());
					visitaVO.setNomeCondomino(condomino != null ? condomino.getNome() : null);
					listaVisitaVO.add(visitaVO);
				}
				this.listaVisitaDataModel = new ListDataModel<VisitaVO>(listaVisitaVO);
			} catch (SQLException e) {
				logger.error("erro sqlstate "+e.getSQLState(), e);	
				ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");			
			} catch (BusinessException e) {				
				ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage());			
			} catch (Exception e) {
				logger.error("", e);
				ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");			
			}
		return "visualizarVisitas";		        
	}
	
	public List<Visitante> buscarListaVisitante(Object nomeVisitante){		
		try {
			if(this.visitante.getVisita().getIdCondominio() != null){
				this.listaVisitante = this.visitanteService.buscaPorNomeEIdCondominio(nomeVisitante.toString(), this.visitante.getVisita().getIdCondominio());
			}	
		} catch (SQLException e) {
			logger.error("erro sqlstate "+e.getSQLState(), e);	
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");	
		} catch (Exception e) {
			logger.error("", e);
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		}
		return listaVisitante;
	}
	
	public List<PrestadorServico> buscarListaPrestadorServico(Object nomePrestadorServico){
		try {
			this.listaPrestadorServico = this.prestadorServicoService.buscarPorNome(nomePrestadorServico.toString());			
		} catch (SQLException e) {
			logger.error("erro sqlstate "+e.getSQLState(), e);	
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");	
		} catch (Exception e) {
			logger.error("", e);
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		}
		return this.listaPrestadorServico;
	}	
	
	public String cancelarCadastroVisitante(){
		return "cancelar";
	}
	
	public String cancelarFiltroPesquisaVisitante(){
		return "cancelar";
	}
	
	public void limparCadastroVisitante(){
		this.inicializarTela();
	}
	
	public String voltarFiltroVisita(){
		return "voltar";
	}
	
	public String cancelarFiltroVisita(){
		return "cancelar";
	}
	
	public String visualizarPrestadorServico(){
		VisitaVO visitaVO = this.listaVisitaDataModel.getRowData();
		this.prestadorServico = visitaVO.getPrestadorServico();
		return "visualizaPrestadorServico";
	}
	
	public String voltarVisualizarPrestadorServico(){
		return "voltar";
	}
	
	public String cancelarVisualizarPrestadorServico(){
		return "cancelar";
	}
	
	
	public void limparFiltroPesquisaVisitante(){
		this.visitante = new Visitante();
		this.dataDe = null;
		this.dataAte = null;
		this.idCondominio = null;
		this.listaVisitanteDataModel = new ListDataModel<Visitante>();
	}
	
	public void exibirLocalVisita(){
		this.painelLocalVisita = Boolean.FALSE;
		if (this.quemIraVisitar == VisitanteQuemIraVisitarEnum.MORADOR.getQuemIraVisitar()){
			this.painelLocalVisita = Boolean.TRUE;
		}
	}
	
	public void exibirCampoRG(){
		this.exibeCampoRg = Boolean.FALSE;
		if(this.tipoDocumento == VisitanteTipoDocumentoEnum.RG.getTipoDocumento()){
			this.exibeCampoRg = Boolean.TRUE;
		}
	}
	
	public void exibirPainelPrestadorServico(){
		this.painelPrestadorServico = Boolean.FALSE;
		if(this.visitante.getVisita().getPrestarServico() == VisitantePrestarServicoEnum.SIM.getPrestarServico()){
			this.painelPrestadorServico = Boolean.TRUE;
		}
	}	
	
	public void popularListaSIBlocos(){
		this.listaSIUnidades = new ArrayList<SelectItem>();
		this.listaSICondominos = new ArrayList<SelectItem>();
		this.desabilitaCampoNomeVisitante = Boolean.FALSE;
		this.nomeVisitante = "";
		this.idNomeVisitante = null;
		this.visitante.setCpf(null);
		this.visitante.setRg(null);
		this.visitante.setTelefone(null);
		Condominio condominio = new Condominio();
		condominio.setId(this.visitante.getVisita().getIdCondominio());
		this.setListaSIBlocos(this.blocoMB.get().buscarListaBlocosPorCondominio(condominio));	
	}
	
	public void popularListaSIUnidades(){
		this.listaSICondominos = new ArrayList<SelectItem>();
		Bloco bloco = new Bloco();
		bloco.setId(this.visitante.getVisita().getIdBloco());
		this.setListaSIUnidades(this.unidadeMB.get().buscarListaSIUnidadesPorBloco(bloco));	
	}
	
	public void popularListaSICondominos(){
		Unidade unidade = new Unidade();
		unidade.setId(this.visitante.getVisita().getIdUnidade());
		this.setListaSICondominos(this.condominoMB.get().buscarListaCondominoPorUnidade(unidade));
	}
	
	public List<SelectItem> getListaSICondominios() {
		this.listaSICondominios = this.condominioMB.get().buscarListaCondominiosAtivos();
		return listaSICondominios;		
	}	
	
	/**
	 * Método que encontra o id do nome do visitante selecionado	
	 */
	public void popularIdNomeVisitante() {		
		  Boolean encontrou = Boolean.FALSE;
		  this.idNomeVisitante = null;
		  if(ManagedBeanUtil.verificaSeExisteSomenteNumeros(this.nomeVisitante)){
			  this.idNomeVisitante = Integer.parseInt(this.nomeVisitante);
			  Integer i = 0;
			  while(!encontrou){
				  Visitante visitante = this.listaVisitante.get(i++);			
				  if (visitante.getId().equals(this.idNomeVisitante)){
					  this.nomeVisitante = visitante.getNome();
					  this.visitante.setCpf(visitante.getCpf());
					  this.visitante.setRg(visitante.getRg());
					  this.visitante.setTelefone(visitante.getTelefone());				
					  encontrou = Boolean.TRUE;
				  }
			  }
		  }else{
			  this.visitante.setCpf(null);
			  this.visitante.setTelefone(null);
			  this.visitante.setRg(null);
		  }
	  }
	
	/**
	 * Método que encontra o id do nome do prestador de serviço selecionado	
	 */
	public void popularIdNomePrestadorServico() {	
		Boolean encontrou = Boolean.FALSE;
		this.idNomePrestadorServico = null;
		if(ManagedBeanUtil.verificaSeExisteSomenteNumeros(this.nomePrestadorServico)){
			this.idNomePrestadorServico = Integer.parseInt(this.nomePrestadorServico);
			Integer i = 0;
			while(!encontrou){
				PrestadorServico prestadorServico = this.listaPrestadorServico.get(i++);			
				if (prestadorServico.getId().equals(this.idNomePrestadorServico)){
					this.nomePrestadorServico = prestadorServico.getNome();
					this.visitante.getVisita().getPrestadorServico().setCnpj(prestadorServico.getCnpj());
					this.visitante.getVisita().getPrestadorServico().setEmail(prestadorServico.getEmail());
					this.visitante.getVisita().getPrestadorServico().setTelefone(prestadorServico.getTelefone());
					encontrou = Boolean.TRUE;
				}
			}			
		}else{
			this.visitante.getVisita().getPrestadorServico().setCnpj(null);
			this.visitante.getVisita().getPrestadorServico().setEmail(null);
			this.visitante.getVisita().getPrestadorServico().setTelefone(null);
		}
	}
	

	public Boolean getDesabilitaCampoNomeVisitante() {
		return desabilitaCampoNomeVisitante;
	}

	public void setDesabilitaCampoNomeVisitante(Boolean desabilitaCampoNomeVisitante) {		
		this.desabilitaCampoNomeVisitante = desabilitaCampoNomeVisitante;
	}

	public List<SelectItem> getListaSIBlocos() {
		return listaSIBlocos;
	}

	public void setListaSIBlocos(List<SelectItem> listaSIBlocos) {
		this.listaSIBlocos = listaSIBlocos;
	}	

	public List<SelectItem> getListaSIUnidades() {
		return listaSIUnidades;
	}

	public void setListaSIUnidades(List<SelectItem> listaSIUnidades) {
		this.listaSIUnidades = listaSIUnidades;
	}

	public void setListaSICondominios(List<SelectItem> listaSICondominios) {
		this.listaSICondominios = listaSICondominios;
	}	

	public List<SelectItem> getListaSICondominos() {
		return listaSICondominos;
	}

	public void setListaSICondominos(List<SelectItem> listaSICondominos) {
		this.listaSICondominos = listaSICondominos;
	}

	public Visitante getVisitante() {
		return visitante;
	}

	public void setVisitante(Visitante visitante) {
		this.visitante = visitante;
	}

	public List<SelectItem> getListaSIQuemIraVisitar() {
		return listaSIQuemIraVisitar;
	}

	public void setListaSIQuemIraVisitar(List<SelectItem> listaSIQuemIraVisitar) {
		this.listaSIQuemIraVisitar = listaSIQuemIraVisitar;
	}

	public Integer getQuemIraVisitar() {
		return quemIraVisitar;
	}

	public void setQuemIraVisitar(Integer quemIraVisitar) {
		this.quemIraVisitar = quemIraVisitar;
	}	
	
	public Boolean getPainelLocalVisita() {
		return painelLocalVisita;
	}

	public void setPainelLocalVisita(Boolean painelLocalVisita) {
		this.painelLocalVisita = painelLocalVisita;
	}	

	public Integer getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(Integer tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}	

	public List<SelectItem> getListaSITipoDocumento() {
		return listaSITipoDocumento;
	}

	public void setListaSITipoDocumento(List<SelectItem> listaSITipoDocumento) {
		this.listaSITipoDocumento = listaSITipoDocumento;
	}

	public Boolean getExibeCampoRg() {
		return exibeCampoRg;
	}

	public void setExibeCampoRg(Boolean exibeCampoRg) {
		this.exibeCampoRg = exibeCampoRg;
	}	

	public List<SelectItem> getListaSIPrestadorServico() {
		return listaSIPrestadorServico;
	}

	public void setListaSIPrestadorServico(List<SelectItem> listaSIPrestadorServico) {
		this.listaSIPrestadorServico = listaSIPrestadorServico;
	}	

	public Boolean getPainelPrestadorServico() {
		return painelPrestadorServico;
	}

	public void setPainelPrestadorServico(Boolean painelPrestadorServico) {
		this.painelPrestadorServico = painelPrestadorServico;
	}		

	public String getNomeVisitante() {
		return nomeVisitante;
	}

	public void setNomeVisitante(String nomeVisitante) {
		this.nomeVisitante = nomeVisitante;
	}

	public Integer getIdNomeVisitante() {
		return idNomeVisitante;
	}

	public void setIdNomeVisitante(Integer idNomeVisitante) {
		this.idNomeVisitante = idNomeVisitante;
	}	

	public String getNomePrestadorServico() {
		return nomePrestadorServico;
	}

	public void setNomePrestadorServico(String nomePrestadorServico) {
		this.nomePrestadorServico = nomePrestadorServico;
	}

	public Integer getIdNomePrestadorServico() {
		return idNomePrestadorServico;
	}

	public void setIdNomePrestadorServico(Integer idNomePrestadorServico) {
		this.idNomePrestadorServico = idNomePrestadorServico;
	}	

	public ListDataModel<Visitante> getListaVisitanteDataModel() {
		return listaVisitanteDataModel;
	}

	public void setListaVisitanteDataModel(ListDataModel<Visitante> listaVisitanteDataModel) {
		this.listaVisitanteDataModel = listaVisitanteDataModel;
	}

	public Date getDataDe() {
		return dataDe;
	}

	public void setDataDe(Date dataDe) {
		this.dataDe = dataDe;
	}

	public Date getDataAte() {
		return dataAte;
	}

	public void setDataAte(Date dataAte) {
		this.dataAte = dataAte;
	}	

	public ListDataModel<VisitaVO> getListaVisitaDataModel() {
		return listaVisitaDataModel;
	}

	public void setListaVisitaDataModel(ListDataModel<VisitaVO> listaVisitaDataModel) {
		this.listaVisitaDataModel = listaVisitaDataModel;
	}	

	public PrestadorServico getPrestadorServico() {
		return prestadorServico;
	}

	public void setPrestadorServico(PrestadorServico prestadorServico) {
		this.prestadorServico = prestadorServico;
	}	

	public Integer getIdCondominio() {
		return idCondominio;
	}

	public void setIdCondominio(Integer idCondominio) {
		this.idCondominio = idCondominio;
	}

	private void popularListaSIQuemIraVisitar(){
		this.listaSIQuemIraVisitar = new ArrayList<SelectItem>();
		this.listaSIQuemIraVisitar.add(new SelectItem(VisitanteQuemIraVisitarEnum.MORADOR.getQuemIraVisitar(), AplicacaoUtil.i18n("visitante.quemIraVisitar.1")));
		this.listaSIQuemIraVisitar.add(new SelectItem(VisitanteQuemIraVisitarEnum.CONDOMINIO.getQuemIraVisitar(), AplicacaoUtil.i18n("visitante.quemIraVisitar.2")));
	}
	
	private void popularListaSITipoDocumento(){
		this.listaSITipoDocumento = new ArrayList<SelectItem>();
		this.listaSITipoDocumento.add(new SelectItem(VisitanteTipoDocumentoEnum.CPF.getTipoDocumento(), AplicacaoUtil.i18n("visitante.cpf")));
		this.listaSITipoDocumento.add(new SelectItem(VisitanteTipoDocumentoEnum.RG.getTipoDocumento(), AplicacaoUtil.i18n("visitante.rg")));
	}
	
	private void popularListaSIPrestadorServico(){
		this.listaSIPrestadorServico = new ArrayList<SelectItem>();
		this.listaSIPrestadorServico.add(new SelectItem(VisitantePrestarServicoEnum.SIM.getPrestarServico(), AplicacaoUtil.i18n("visitante.visita.prestadorServico.1")));
		this.listaSIPrestadorServico.add(new SelectItem(VisitantePrestarServicoEnum.NAO.getPrestarServico(), AplicacaoUtil.i18n("visitante.visita.prestadorServico.0")));
	}
	
	private void inicializarTela(){
		this.quemIraVisitar = VisitanteQuemIraVisitarEnum.CONDOMINIO.getQuemIraVisitar();		
		this.visitante = new Visitante();		
		this.visitante.getVisita().setPrestarServico(VisitantePrestarServicoEnum.NAO.getPrestarServico());
		this.painelLocalVisita = Boolean.FALSE;
		this.exibeCampoRg = Boolean.FALSE;
		this.painelPrestadorServico = Boolean.FALSE;
		this.tipoDocumento = VisitanteTipoDocumentoEnum.CPF.getTipoDocumento();
		this.nomeVisitante = "";
		this.desabilitaCampoNomeVisitante = Boolean.TRUE;
		this.idNomeVisitante = null;
		this.idNomePrestadorServico = null;
		this.nomePrestadorServico = "";
	}

}
