package br.com.condominiosvirtuais.controller;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Instance;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.log4j.Logger;

import br.com.condominiosvirtuais.entity.Aba;
import br.com.condominiosvirtuais.entity.Condominio;
import br.com.condominiosvirtuais.entity.GrupoUsuario;
import br.com.condominiosvirtuais.entity.Tela;
import br.com.condominiosvirtuais.enumeration.AtributoSessaoEnum;
import br.com.condominiosvirtuais.enumeration.GrupoUsuarioSituacaoEnum;
import br.com.condominiosvirtuais.exception.BusinessException;
import br.com.condominiosvirtuais.service.AbaService;
import br.com.condominiosvirtuais.service.GrupoUsuarioService;
import br.com.condominiosvirtuais.service.TelaService;
import br.com.condominiosvirtuais.util.AplicacaoUtil;
import br.com.condominiosvirtuais.util.ManagedBeanUtil;
import br.com.condominiosvirtuais.vo.AbaVO;
import br.com.condominiosvirtuais.vo.TelaVO;

@Named @SessionScoped
public class GrupoUsuarioMB implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private static Logger logger = Logger.getLogger(GrupoUsuarioMB.class);
	
	@Inject
	private TelaService telaService;
	
	@Inject
	private AbaService abaService;	

	@Inject
	private GrupoUsuarioService grupoUsuarioService;
	
	@Inject
	private Instance<CondominioMB> condominioMB = null;
	
	@Inject
	private TelaMB telaMB;
		
	private GrupoUsuario grupoUsuario;
		
	private List<SelectItem> listaSICondominios;
	
	private List<SelectItem> listaSISituacao;
	
	private Integer situacao;	
	
	private ListDataModel<GrupoUsuario> listaGruposUsuarios = null;
	
	private ListDataModel<TelaVO> listaTelaVO = null;
	
	private Condominio condominio = null; 
	
	private TelaVO telaVO = null;
	
	private Boolean checadoTodos = null;
	

	
	public void iniciarTelaVO (){
		this.telaVO = (TelaVO) ManagedBeanUtil.getSession(Boolean.FALSE).getAttribute(AtributoSessaoEnum.TELA_VO.getAtributo());
	}
	
		
	@PostConstruct
	public void inciarGrupoUsuarioMB(){
		this.grupoUsuario = new GrupoUsuario();
		this.condominio = new Condominio();
		this.listaSICondominios = this.condominioMB.get().buscarListaCondominiosAtivos();
		this.checadoTodos = Boolean.FALSE;
		this.popularSituacao();
	}	
	
	public void pesquisaGrupoUsuario(){
		// Caso tenha selecionado todos
		try {
			if(this.situacao == -1){
				this.listaGruposUsuarios = new ListDataModel<GrupoUsuario>(this.grupoUsuarioService.buscarPorIdCondominio(this.grupoUsuario.getIdCondominio()));
			}else{
				this.listaGruposUsuarios = new ListDataModel<GrupoUsuario>(this.grupoUsuarioService.buscarPorIdCondominioESituacao(this.grupoUsuario.getIdCondominio(),
				this.situacao == 1 ? GrupoUsuarioSituacaoEnum.ATIVO.getSituacao() : GrupoUsuarioSituacaoEnum.INATIVO.getSituacao()));
			}
			if(this.listaGruposUsuarios.getRowCount() == 0){
				ManagedBeanUtil.setMensagemInfo("msg.grupoUsuario.semGrupoUsuario");
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
	
	public String cadastroGrupoUsuario(){
		try {
			this.populaTelaVO();
		} catch (SQLException e) {
			logger.error("erro sqlstate "+e.getSQLState(), e);	
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		} catch (Exception e) {
			logger.error("", e);
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		}
		return "cadastrar";
	}
	
	public void limparFiltroPesquisaGrupoUsuario(){
		this.grupoUsuario = new GrupoUsuario();
		this.situacao = -1;
		this.listaGruposUsuarios = new ListDataModel<GrupoUsuario>();
	}
	              
	public String visualizarGrupoUsuarioTela() {		
		try {
			this.grupoUsuario = (GrupoUsuario) this.listaGruposUsuarios.getRowData();
			this.grupoUsuario.setListaTela(this.telaService.buscarPorIdGrupoUsuario(this.grupoUsuario.getId()));
			ManagedBeanUtil.getSession(Boolean.TRUE).setAttribute(AtributoSessaoEnum.GRUPO_USUARIO.getAtributo(),this.grupoUsuario);
			if(this.grupoUsuario.getListaTela().isEmpty()){
				ManagedBeanUtil.setMensagemInfo("msg.grupoUsuario.semTelas");
			}
		} catch (SQLException e) {
			logger.error("erro sqlstate "+e.getSQLState(), e);
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		} catch (Exception e) {				
			logger.error("", e);
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage());	
		}
		return "visualizar";
	}
	
	public String listarGrupoUsuarioTelaAba() {		
		try {
			telaVO = (TelaVO) this.listaTelaVO.getRowData();
			List<Aba> listaAbasTela = abaService.buscarPorIdTela(telaVO.getIdTela());
			telaVO.setListaAbasTela(listaAbasTela);
			ManagedBeanUtil.getSession(Boolean.TRUE).setAttribute(AtributoSessaoEnum.TELA_VO.getAtributo(),telaVO);
			this.telaMB.populaAbaVOTemporaria();
		} catch (SQLException e) {
			logger.error("erro sqlstate "+e.getSQLState(), e);
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		} catch (Exception e) {				
			logger.error("", e);
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage());	
		}
		return "listar";
	}	
	
	public String salvarGrupoUsuario(){
		try {
			Iterator<TelaVO> iteratorTelaVO = this.listaTelaVO.iterator();
			Iterator<AbaVO> iteratorAbaVO = null;
			AbaVO abaVO = null;
			List<TelaVO> listaTelaVO = new ArrayList<TelaVO>();
			while (iteratorTelaVO.hasNext()) {
				TelaVO telaVO = iteratorTelaVO.next();
				if(telaVO.getChecada()){
					listaTelaVO.add(telaVO);
					iteratorAbaVO = telaVO.getListaAbasVOTela().iterator();
					while (iteratorAbaVO.hasNext()) {
						abaVO = (AbaVO) iteratorAbaVO.next();
						if(!abaVO.getChecada()){
							telaVO.getListaAbasVOTela().remove(abaVO);
						}
					}
				}
			}
			this.grupoUsuario.setListaTelaAcesso(listaTelaVO);
			this.grupoUsuario.setSituacao(GrupoUsuarioSituacaoEnum.ATIVO.getSituacao());
			this.grupoUsuarioService.salvar(this.grupoUsuario);
			this.listaTelaVO = new ListDataModel<TelaVO>();
			this.grupoUsuario = new GrupoUsuario();
			ManagedBeanUtil.setMensagemInfo("msg.grupoUsuario.salvoSucesso");
		} catch (SQLException e) {
			logger.error("erro sqlstate "+e.getSQLState(), e);
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		} catch (Exception e) {				
			logger.error("", e);
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage());	
		}
		return "salvar";
	}
	
	
	private void populaTelaVO() throws SQLException, Exception{
		TelaVO telaVO = null;
		List<TelaVO> listaTelaVO = new ArrayList<TelaVO>();
		List<Tela> listaTelas = this.telaService.buscarTodas();
		for (Tela tela : listaTelas) {
			telaVO = new TelaVO();
			telaVO.setIdTela(tela.getId());
			telaVO.setDescricaoI18nTela(tela.getDescricaoI18n());
			telaVO.setNomeI18nTela(tela.getNomeI18n());
			telaVO.setListaAbasVOTela(this.popularAbaVO(tela));
		//	telaVO.setListaComponentesTela(new ArrayList<Componente>(tela.getListaComponentes()));
			listaTelaVO.add(telaVO);
		}
		this.listaTelaVO = new ListDataModel<TelaVO>(listaTelaVO);
	}
	
	private List<AbaVO> popularAbaVO(Tela tela){
		List<AbaVO> listaAbaVO = new ArrayList<AbaVO>();
		AbaVO abaVO = null;
		for (Aba aba : tela.getListaAbas()) {
			abaVO = new AbaVO();
			abaVO.setIdAba(aba.getId());
			abaVO.setNomeI18nAba(aba.getNomeI18n());
			abaVO.setDescricaoI18nAba(aba.getDescricaoI18n());
			listaAbaVO.add(abaVO);
		}
		return listaAbaVO;
	}
	
	
	public void checarTodosCheckbox() throws SQLException, Exception{
		Iterator<TelaVO> iteratorTelaVO = listaTelaVO.iterator();
		Iterator<AbaVO> iteratorAbaVO = null;
		TelaVO telaVO = null;
		AbaVO abaVO = null;
		while (iteratorTelaVO.hasNext()) {
			telaVO = iteratorTelaVO.next();
			telaVO.setChecada(this.checadoTodos);
			iteratorAbaVO = telaVO.getListaAbasVOTela().iterator();
			if(!telaVO.getListaAbasVOTela().isEmpty()){
				ManagedBeanUtil.getSession(Boolean.TRUE).setAttribute(AtributoSessaoEnum.TELA_VO.getAtributo(),telaVO);
				
			}	
			while (iteratorAbaVO.hasNext()) {
				abaVO = iteratorAbaVO.next();
				abaVO.setChecada(this.checadoTodos);
			}
		}		
	}
	
	public List<SelectItem> getListaSICondominios() {		
		return listaSICondominios;
	}
	
	public void setListaSICondominios(List<SelectItem> listaSICondominios) {
		this.listaSICondominios = listaSICondominios;
	}
	
	public List<SelectItem> getListaSISituacao() {
		return listaSISituacao;
	}

	public void setListaSISituacao(List<SelectItem> listaSISituacao) {
		this.listaSISituacao = listaSISituacao;
	}

	public Integer getSituacao() {
		return situacao;
	}

	public void setSituacao(Integer situacao) {
		this.situacao = situacao;
	}

	public GrupoUsuario getGrupoUsuario() {
		return grupoUsuario;
	}

	public void setGrupoUsuario(GrupoUsuario grupoUsuario) {
		this.grupoUsuario = grupoUsuario;
	}	

	public ListDataModel<GrupoUsuario> getListaGruposUsuarios() {
		return listaGruposUsuarios;
	}

	public void setListaGruposUsuarios(ListDataModel<GrupoUsuario> listaGruposUsuarios) {
		this.listaGruposUsuarios = listaGruposUsuarios;
	}
	
	public List<SelectItem> getListaCondominios() {		
		
		return this.listaSICondominios;
	}		

	public Condominio getCondominio() {
		return condominio;
	}

	public void setCondominio(Condominio condominio) {
		this.condominio = condominio;
	}	

	public ListDataModel<TelaVO> getListaTelaVO() {
		return listaTelaVO;
	}

	public void setListaTelaVO(ListDataModel<TelaVO> listaTelaVO) {
		this.listaTelaVO = listaTelaVO;
	}	

	public Boolean getChecadoTodos() {
		return checadoTodos;
	}

	public void setChecadoTodos(Boolean checadoTodos) {
		this.checadoTodos = checadoTodos;
	}


	private void popularSituacao(){
		this.listaSISituacao = new ArrayList<SelectItem>();
		this.listaSISituacao.add(new SelectItem(-1, AplicacaoUtil.i18n("grupoUsuario.situacao.todos")));
		this.listaSISituacao.add(new SelectItem(1, AplicacaoUtil.i18n("grupoUsuario.situacao.1")));
		this.listaSISituacao.add(new SelectItem(0, AplicacaoUtil.i18n("grupoUsuario.situacao.0")));
	}

}
