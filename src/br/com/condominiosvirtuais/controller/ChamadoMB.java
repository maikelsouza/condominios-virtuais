package br.com.condominiosvirtuais.controller;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Instance;
import javax.faces.event.ActionEvent;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.log4j.Logger;

import br.com.condominiosvirtuais.entity.Bloco;
import br.com.condominiosvirtuais.entity.Chamado;
import br.com.condominiosvirtuais.entity.Condominio;
import br.com.condominiosvirtuais.entity.Condomino;
import br.com.condominiosvirtuais.entity.TipoChamado;
import br.com.condominiosvirtuais.entity.Unidade;
import br.com.condominiosvirtuais.enumeration.ChamadoStatusEnum;
import br.com.condominiosvirtuais.service.impl.BlocoServiceImpl;
import br.com.condominiosvirtuais.service.impl.ChamadoServiceImpl;
import br.com.condominiosvirtuais.service.impl.CondominoServiceImpl;
import br.com.condominiosvirtuais.service.impl.FuncionarioServiceImpl;
import br.com.condominiosvirtuais.service.impl.TipoChamadoServiceImpl;
import br.com.condominiosvirtuais.service.impl.UnidadeServiceImpl;
import br.com.condominiosvirtuais.util.AplicacaoUtil;
import br.com.condominiosvirtuais.util.ManagedBeanUtil;
import br.com.condominiosvirtuais.vo.ChamadoVO;

@Named @SessionScoped
public class ChamadoMB  implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private static Logger logger = Logger.getLogger(ChamadoMB.class);
	
	@Inject
	private Instance<CondominioMB> condominioMB = null;
	
	@Inject
	private TipoChamadoServiceImpl tipoChamadoService;
	
	@Inject
	private ChamadoServiceImpl chamadoService;
	
	@Inject
	private UnidadeServiceImpl unidadeService;
	
	@Inject
	private BlocoServiceImpl blocoService;
	
	@Inject
	private CondominoServiceImpl condominoService;	
	
	@Inject
	private FuncionarioServiceImpl funcionarioService;
	
	private List<SelectItem> listaSITipoChamado = new ArrayList<SelectItem>();
	
	private List<SelectItem> listaSICondominios = new ArrayList<SelectItem>();
	
	private List<SelectItem> listaSIStatus;
	
	private ListDataModel<ChamadoVO> listaChamadosVOs = null;
	
	private ListDataModel<ChamadoVO> listaMeusChamadosVOs = null;
	
	private Chamado chamado;
	
	private ChamadoVO chamadoVO;
	
	private Condominio condominio;
	
	private Boolean exibirCampoOutros;
	
	
	
	@PostConstruct
	public void iniciarChamadoMB(){
		try {
			this.chamado = new Chamado();			
			this.condominio = new Condominio();
			this.exibirCampoOutros = Boolean.FALSE;			
			this.popularListaSiTipoChamado();
			this.popularListaSiStatus();	
			this.pesquisarMeusChamado();
		} catch (Exception e) {
			logger.error("", e);
			ManagedBeanUtil.setMensagemErro("msg.erro.executarOperacao");
		}
	}
	
	public void criarChamado(){
		try {
			this.chamado.setStatus(ChamadoStatusEnum.ABERTO.getStatus());
			this.chamado.setUsuario(AplicacaoUtil.getUsuarioAutenticado());
			this.chamado.setDataAbertura(new Date());
			this.chamadoService.salvar(this.chamado);
			this.chamado = new Chamado();
			this.exibirCampoOutros = Boolean.FALSE;
			this.pesquisarMeusChamado();
			ManagedBeanUtil.setMensagemInfo("msg.chamado.criadoSucesso");
		} catch (SQLException e) {
			logger.error("erro sqlstate "+e.getSQLState(), e);	
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		} catch (Exception e) {
			logger.error("", e);	
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		}
	}
	
	public void pesquisarChamado(){
		List<Chamado> listaChamados = null;
		this.listaChamadosVOs = new ListDataModel<ChamadoVO>();
		try {
			// Situação onde foi selecionados todos os chamados independente do status
			if (this.chamado.getStatus() == -1){
				listaChamados = this.chamadoService.buscarPorCondominio(this.chamado.getCondominio());
			}else{
				listaChamados = this.chamadoService.buscarPorCondominioEStatus(this.chamado.getCondominio(), this.chamado.getStatus());
			}
			if(listaChamados.isEmpty()){				
				ManagedBeanUtil.setMensagemInfo("msg.chamado.semChamado");
			}else{
				this.popularListaChamadosVOs(listaChamados,Boolean.FALSE);				
			}
		} catch (SQLException e) {
			logger.error("erro sqlstate "+e.getSQLState(), e);	
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		} catch (Exception e) {
			logger.error("", e);	
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		}
	}
	
	public void pesquisarMeusChamado(){
		List<Chamado> listaChamados = null;
		this.listaMeusChamadosVOs = new ListDataModel<ChamadoVO>();
		try {
			listaChamados = this.chamadoService.buscarPorUsuario(AplicacaoUtil.getUsuarioAutenticado().getId());
			this.popularListaChamadosVOs(listaChamados,Boolean.TRUE);				
			
		} catch (SQLException e) {
			logger.error("erro sqlstate "+e.getSQLState(), e);	
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		} catch (Exception e) {
			logger.error("", e);	
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		}
	}
	
	public List<SelectItem> getListaSICondominios() {
		this.listaSICondominios = this.condominioMB.get().buscarListaCondominiosAtivos();
		return listaSICondominios;
		
	}
	
	public void exibirOpcaoOutros(){		
		this.exibirCampoOutros = Boolean.FALSE;	
		if(this.chamado.getTipoChamado().getId() == this.listaSITipoChamado.get(this.listaSITipoChamado.size()-1).getValue()){
			this.exibirCampoOutros = Boolean.TRUE;
		}
	}
	
	public void excluirChamado(ActionEvent actionEvent){
		try {
			this.chamadoVO = this.listaMeusChamadosVOs.getRowData();
			this.chamadoService.excluir(chamadoVO.getId());		
			this.pesquisarMeusChamado();
			ManagedBeanUtil.setMensagemInfo("msg.chamado.excluirSucesso");
		} catch (SQLException e) {
			logger.error("erro sqlstate "+e.getSQLState(), e);	
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");	
		} catch (Exception e) {
			logger.error("", e);	
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		}		
	}
	
	public String cancelarAbrirChamado(){
		return "cancelar";
	}
	
	public String cancelarDarAndamentoChamado(){
		return "cancelar";
	}
	
	
	public void limparAbrirChamado(ActionEvent event){
		this.chamado = new Chamado();
		this.exibirCampoOutros = Boolean.FALSE;
	}
	
	public void limparFiltroChamado(ActionEvent event){
		try {
			this.chamado = new Chamado();
			this.listaChamadosVOs = new ListDataModel<ChamadoVO>();
			this.popularListaSiStatus();
		} catch (Exception e) {
			logger.error("", e);	
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		}
		
	}
	
	
	
	public String darAndamentoChamado(){
		try {
			this.chamadoVO = this.listaChamadosVOs.getRowData();		
			if(chamadoVO.getComentario().equals("null")){
				chamadoVO.setComentario("");
			}			
			this.chamadoService.atualizarStatus(this.chamadoVO.getId(), ChamadoStatusEnum.EM_ANDAMENTO.getStatus());
			this.chamadoVO.setStatus(this.buscarNomeStatus(ChamadoStatusEnum.EM_ANDAMENTO.getStatus()));			
		} catch (Exception e) {
			logger.error("", e);	
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		}
		return "darAndamento";		
	}
	
	public String fecharChamado(){
		try {			
			chamadoVO.setDataFechamento(new Date());
			Chamado chamado = new Chamado();
			chamado.setDataFechamento(chamadoVO.getDataFechamento());
			chamado.setComentario(this.chamadoVO.getComentario());
			chamado.setId(this.chamadoVO.getId());
			chamado.setNome(this.chamadoVO.getNome());
			chamado.getUsuario().setId(chamadoVO.getIdUsuario());
			this.chamadoService.fecharChamado(chamado);
			this.pesquisarChamado();			
			ManagedBeanUtil.setMensagemInfo("msg.chamado.fechar");
		} catch (Exception e) {
			logger.error("", e);	
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		}
		return "fechar";
	}
	public Boolean getExibirCampoOutros() {
		return exibirCampoOutros;
	}

	public void setExibirCampoOutros(Boolean exibirCampoOutros) {
		this.exibirCampoOutros = exibirCampoOutros;
	}

	private void popularListaSiTipoChamado() throws SQLException, Exception{
		List<TipoChamado> listaTipoChamado = null;
		listaTipoChamado = this.tipoChamadoService.buscarTodos();
		for (TipoChamado tipoChamado : listaTipoChamado) {			
			this.listaSITipoChamado.add(new SelectItem(tipoChamado.getId(), tipoChamado.getNomeI18n()));
		}
	}
	
	private void popularListaSiStatus() throws SQLException, Exception{
		this.listaSIStatus = new ArrayList<SelectItem>();
		this.listaSIStatus.add(new SelectItem(ChamadoStatusEnum.ABERTO.getStatus(), AplicacaoUtil.i18n("chamado.status.1")));
		this.listaSIStatus.add(new SelectItem(ChamadoStatusEnum.EM_ANDAMENTO.getStatus(), AplicacaoUtil.i18n("chamado.status.2")));
		this.listaSIStatus.add(new SelectItem(ChamadoStatusEnum.FECHADO.getStatus(), AplicacaoUtil.i18n("chamado.status.0")));
		this.listaSIStatus.add(new SelectItem(-1, AplicacaoUtil.i18n("todos")));
	}
	
	private void popularListaChamadosVOs(List<Chamado> listaChamado, Boolean meuChamado) throws SQLException, Exception{
		List<ChamadoVO> listaDeChamadosLocalVOs = new ArrayList<ChamadoVO>();
		ChamadoVO chamadoVO = null;
		for (Chamado chamado : listaChamado) {
			chamadoVO = new ChamadoVO();
			chamadoVO.setId(chamado.getId());
			chamadoVO.setNome(chamado.getNome());
			chamadoVO.setIdUsuario(chamado.getUsuario().getId());
			chamadoVO.setNomeUsuario(chamado.getUsuario().getNome());
			chamadoVO.setOutros(chamado.getOutros());
			chamadoVO.setDescricao(chamado.getDescricao());
			chamadoVO.setDataAbertura(chamado.getDataAbertura());
			chamadoVO.setDataFechamento(chamado.getDataFechamento());
			chamadoVO.setTipoChamado(chamado.getTipoChamado().getNomeI18n());
			chamadoVO.setComentario(chamado.getComentario());
			chamadoVO.setNomeBloco("-");
			chamadoVO.setNumeroUnidade("-");
			chamadoVO.setStatus(this.buscarNomeStatus(chamado.getStatus()));
			Condomino condomino = this.condominoService.buscarPorId(chamado.getUsuario().getId());
			if(condomino != null){				
				Unidade unidade = this.unidadeService.buscarPorId(condomino.getIdUnidade());
				chamadoVO.setNumeroUnidade(String.valueOf((unidade.getNumero())));
				Bloco bloco = this.blocoService.buscarPorId(unidade.getIdBloco());
				chamadoVO.setNomeBloco(bloco.getNome());
				chamadoVO.setNome(chamado.getNome());
			} else if (this.funcionarioService.buscarPorId(chamado.getUsuario().getId()) != null){
				chamadoVO.setNomeUsuario(chamado.getUsuario().getNome() + " - " + AplicacaoUtil.i18n("funcionario"));
			} else{
				chamadoVO.setNomeUsuario(chamado.getUsuario().getNome() + " - Admin" );
			}
			listaDeChamadosLocalVOs.add(chamadoVO);
			if(meuChamado == Boolean.TRUE){
				this.listaMeusChamadosVOs = new ListDataModel<ChamadoVO>(listaDeChamadosLocalVOs);
			}else{
				this.listaChamadosVOs = new ListDataModel<ChamadoVO>(listaDeChamadosLocalVOs);				
			}
		}
	}	
	
	private String buscarNomeStatus(Integer status){
		String nomeStatus = "";
		if(ChamadoStatusEnum.ABERTO.getStatus() == status){
			nomeStatus = AplicacaoUtil.i18n("chamado.status.1");
		}else if (ChamadoStatusEnum.EM_ANDAMENTO.getStatus() == status){
			nomeStatus = AplicacaoUtil.i18n("chamado.status.2");
		}else if (ChamadoStatusEnum.FECHADO.getStatus() == status){
			nomeStatus = AplicacaoUtil.i18n("chamado.status.0");
		}else{
			nomeStatus = AplicacaoUtil.i18n("todos");
		}	
		return nomeStatus;
	}

	public List<SelectItem> getListaSITipoChamado() {
		return listaSITipoChamado;
	}

	public void setListaSITipoChamado(List<SelectItem> listaSITipoChamado) {
		this.listaSITipoChamado = listaSITipoChamado;
	}

	public Chamado getChamado() {
		return chamado;
	}

	public void setChamado(Chamado chamado) {
		this.chamado = chamado;
	}

	public Condominio getCondominio() {
		return condominio;
	}

	public void setCondominio(Condominio condominio) {
		this.condominio = condominio;
	}

	public void setListaSICondominios(List<SelectItem> listaSICondominios) {
		this.listaSICondominios = listaSICondominios;
	}

	public List<SelectItem> getListaSIStatus() {
		return listaSIStatus;
	}

	public void setListaSIStatus(List<SelectItem> listaSIStatus) {
		this.listaSIStatus = listaSIStatus;
	}

	public ListDataModel<ChamadoVO> getListaChamadosVOs() {
		return listaChamadosVOs;
	}

	public void setListaChamadosVOs(ListDataModel<ChamadoVO> listaChamadosVOs) {
		this.listaChamadosVOs = listaChamadosVOs;
	}

	public ChamadoVO getChamadoVO() {
		return chamadoVO;
	}

	public void setChamadoVO(ChamadoVO chamadoVO) {
		this.chamadoVO = chamadoVO;
	}

	public ListDataModel<ChamadoVO> getListaMeusChamadosVOs() {
		return listaMeusChamadosVOs;
	}

	public void setListaMeusChamadosVOs(ListDataModel<ChamadoVO> listaMeusChamadosVOs) {
		this.listaMeusChamadosVOs = listaMeusChamadosVOs;
	}	
	

}
