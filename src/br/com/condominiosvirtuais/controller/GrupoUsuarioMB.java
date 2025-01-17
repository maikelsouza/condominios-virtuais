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
import br.com.condominiosvirtuais.entity.Componente;
import br.com.condominiosvirtuais.entity.Condominio;
import br.com.condominiosvirtuais.entity.GrupoUsuario;
import br.com.condominiosvirtuais.entity.Tela;
import br.com.condominiosvirtuais.enumeration.AtributoSessaoEnum;
import br.com.condominiosvirtuais.enumeration.GrupoUsuarioPadraoEnum;
import br.com.condominiosvirtuais.enumeration.GrupoUsuarioSituacaoEnum;
import br.com.condominiosvirtuais.enumeration.GrupoUsuarioTipoUsuarioEnum;
import br.com.condominiosvirtuais.exception.BusinessException;
import br.com.condominiosvirtuais.service.AbaService;
import br.com.condominiosvirtuais.service.ComponenteService;
import br.com.condominiosvirtuais.service.GrupoUsuarioService;
import br.com.condominiosvirtuais.service.TelaService;
import br.com.condominiosvirtuais.util.AplicacaoUtil;
import br.com.condominiosvirtuais.util.ManagedBeanUtil;
import br.com.condominiosvirtuais.vo.AbaVO;
import br.com.condominiosvirtuais.vo.ComponenteVO;
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
	private ComponenteService componenteService;	

	@Inject
	private GrupoUsuarioService grupoUsuarioService;
	
	@Inject
	private Instance<CondominioMB> condominioMB = null;
	
	@Inject
	private TelaMB telaMB;
	
	@Inject
	private UsuarioMB usuarioMB;
		
	private GrupoUsuario grupoUsuario;
		
	private List<SelectItem> listaSICondominios;
	
	private List<SelectItem> listaSISituacao;
	
	private Integer situacao;	
	
	private ListDataModel<GrupoUsuario> listaGruposUsuarios = null;
	
	private ListDataModel<TelaVO> listaTelaVO = null;
	
	private Condominio condominio = null; 
	
	private TelaVO telaVO = null;
	
	private Boolean checadoTodos = null;
	
	private String tabSelecionada;
	
	private List<SelectItem> listaSIPadrao;
	
	private List<SelectItem> listaSITipoUsuario;
	
	private List<GrupoUsuario> listaGrupoUsuarioTipoSindicoCondominoEFuncionario = new ArrayList<GrupoUsuario>();
	
	private Integer idCondominio;

	
	public void iniciarTelaVO (){
		this.telaVO = (TelaVO) ManagedBeanUtil.getSession(Boolean.FALSE).getAttribute(AtributoSessaoEnum.TELA_VO.getAtributo());
	}
	
		
	@PostConstruct
	public void inciarGrupoUsuarioMB(){
		this.grupoUsuario = new GrupoUsuario();
		this.grupoUsuario.setSituacao(GrupoUsuarioSituacaoEnum.ATIVO.getSituacao());
		this.condominio = new Condominio();
		this.listaSICondominios = this.condominioMB.get().buscarListaCondominiosAtivos();
		this.checadoTodos = Boolean.FALSE;
		this.situacao = -1;
		this.popularSituacao();
		this.popularPadrao();
		this.popularTipoUsuario();
	}	
	
	public void pesquisaGrupoUsuario(){
		// Caso tenha selecionado todos
		List<GrupoUsuario> listaGrupoUsuario = null;			
		List<Integer> listaTipoUsuarioSindicoCondominoEFuncionario = new ArrayList<Integer>();
		// TODO: C�digo comentado em 28/10/2017. Apagar em 90 dias
		//listaTipoUsuarioSindicoCondominoEFuncionario.add(GrupoUsuarioTipoUsuarioEnum.SINDICO.getTipoUsuario());
		listaTipoUsuarioSindicoCondominoEFuncionario.add(GrupoUsuarioTipoUsuarioEnum.CONDOMINO.getTipoUsuario());
		listaTipoUsuarioSindicoCondominoEFuncionario.add(GrupoUsuarioTipoUsuarioEnum.FUNCIONARIO.getTipoUsuario());
		try {
			if(this.situacao == -1){
				listaGrupoUsuario = this.grupoUsuarioService.buscarPorIdCondominio(this.idCondominio);
				this.listaGrupoUsuarioTipoSindicoCondominoEFuncionario = this.grupoUsuarioService.buscarGruposFixosTipoUsuarioSindicoCondominoEFuncionarioEPadrao(listaTipoUsuarioSindicoCondominoEFuncionario,GrupoUsuarioPadraoEnum.SIM.getPadrao());
			}else{
				listaGrupoUsuario = this.grupoUsuarioService.buscarPorIdCondominioESituacao(this.idCondominio,
						this.situacao == 1 ? GrupoUsuarioSituacaoEnum.ATIVO.getSituacao() : GrupoUsuarioSituacaoEnum.INATIVO.getSituacao());
				this.listaGrupoUsuarioTipoSindicoCondominoEFuncionario = this.grupoUsuarioService.buscarGruposFixosTipoUsuarioSindicoCondominoEFuncionarioEPadraoESituacao(listaTipoUsuarioSindicoCondominoEFuncionario,
						GrupoUsuarioPadraoEnum.SIM.getPadrao(), this.situacao == 1 ? GrupoUsuarioSituacaoEnum.ATIVO.getSituacao() : GrupoUsuarioSituacaoEnum.INATIVO.getSituacao());
			}
			listaGrupoUsuario.addAll(this.listaGrupoUsuarioTipoSindicoCondominoEFuncionario);
			this.listaGruposUsuarios = new ListDataModel<GrupoUsuario>(listaGrupoUsuario);
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
			this.grupoUsuario = new GrupoUsuario();
			this.grupoUsuario.setPadrao(Boolean.FALSE);
			this.grupoUsuario.setSituacao(GrupoUsuarioSituacaoEnum.ATIVO.getSituacao());
		} catch (SQLException e) {
			logger.error("erro sqlstate "+e.getSQLState(), e);	
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		} catch (Exception e) {
			logger.error("", e);
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		}
		return "cadastrar";

	}
	
// TODO: C�digo comentado em 28/10/2017. Apagar em 90 dias	
//	public void habilitarTipoUsuario(){
//		if(this.grupoUsuario.getPadrao() == Boolean.FALSE){
//			this.grupoUsuario.setTipoUsuario(null);
//		}
//		
//	}
	
	public String atualizarGrupoUsuario(){
		try {
			Iterator<TelaVO> iteratorTelaVO = this.listaTelaVO.iterator();
			Iterator<AbaVO> iteratorAbaVO = null;
			List<AbaVO> listaAbaVORemover = null;
			List<ComponenteVO> listaComponenteVORemover = null;
			Iterator<ComponenteVO> iteratorComponenteVO = null;
			AbaVO abaVO = null;
			ComponenteVO componenteVO = null;
			List<TelaVO> listaTelaVO = new ArrayList<TelaVO>();
			while (iteratorTelaVO.hasNext()) {
				TelaVO telaVO = iteratorTelaVO.next();
				if(telaVO.getChecada() != null && telaVO.getChecada()){
					listaTelaVO.add(telaVO);
					iteratorAbaVO = telaVO.getListaAbasVOTela().iterator();
					listaAbaVORemover = new ArrayList<AbaVO>();
					while (iteratorAbaVO.hasNext()) {
						abaVO = (AbaVO) iteratorAbaVO.next();
						if(abaVO != null && abaVO.getChecada() != null && !abaVO.getChecada()){
							listaAbaVORemover.add(abaVO);
						}						
					}
					for (AbaVO abaVORemover : listaAbaVORemover) {
						telaVO.getListaAbasVOTela().remove(abaVORemover);
					}
					iteratorComponenteVO = telaVO.getListaComponentesVOTela().iterator();
					listaComponenteVORemover = new ArrayList<ComponenteVO>();
					while (iteratorComponenteVO.hasNext()) {
						componenteVO = (ComponenteVO) iteratorComponenteVO.next();
						if(componenteVO != null && componenteVO.getChecada() != null && !componenteVO.getChecada()){
							listaComponenteVORemover.add(componenteVO);							
						}
						for (ComponenteVO componenteVORemover : listaComponenteVORemover) {
							telaVO.getListaComponentesVOTela().remove(componenteVORemover);
						}
					}
				}
			}
			this.grupoUsuario.setListaTelaAcesso(listaTelaVO);
			this.grupoUsuario.setSituacao(this.situacao == 1 ? Boolean.TRUE : Boolean.FALSE);			
			this.grupoUsuarioService.atualizar(this.grupoUsuario);
			this.pesquisaGrupoUsuario();
			ManagedBeanUtil.setMensagemInfo("msg.grupoUsuario.atualizadoSucesso");
		} catch (SQLException e) {
			logger.error("erro sqlstate "+e.getSQLState(), e);	
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		} catch (Exception e) {
			logger.error("", e);
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		}
		return "atualizar";
	}
	
	public String editarGrupoUsuario(){
		try {
			this.grupoUsuario = (GrupoUsuario) this.listaGruposUsuarios.getRowData();
			this.situacao = this.grupoUsuario.getSituacao() ? 1 : 0;
			this.grupoUsuario.setListaTela(this.telaService.buscarPorIdGrupoUsuario(this.grupoUsuario.getId()));
			this.populaTelaVOEditar();
		} catch (SQLException e) {
			logger.error("erro sqlstate "+e.getSQLState(), e);	
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		} catch (Exception e) {
			logger.error("", e);
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		}
		return "editar";
	}
	
	public void limparFiltroPesquisaGrupoUsuario(){
		try {
			this.grupoUsuario = new GrupoUsuario();
			this.situacao = -1;
			this.checadoTodos = Boolean.FALSE;
			this.listaGruposUsuarios = new ListDataModel<GrupoUsuario>();
			this.checarTodosCheckbox();
		} catch (SQLException e) {
			logger.error("erro sqlstate "+e.getSQLState(), e);
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		} catch (Exception e) {				
			logger.error("", e);
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage());	
		}
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
			List<Aba> listaAbasTela = this.abaService.buscarPorIdTela(telaVO.getIdTela());
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
	
	public String listarGrupoUsuarioTelaComponente() {		
		try {
			telaVO = (TelaVO) this.listaTelaVO.getRowData();			
			List<Componente> listaComponentesTela = componenteService.buscarPorIdTela(telaVO.getIdTela());
			telaVO.setListaComponentesTela(listaComponentesTela);
			ManagedBeanUtil.getSession(Boolean.TRUE).setAttribute(AtributoSessaoEnum.TELA_VO.getAtributo(),telaVO);
			this.telaMB.populaComponenteVOTemporaria();
		 } catch (SQLException e) {
				logger.error("erro sqlstate "+e.getSQLState(), e);	
				ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		}catch (BusinessException e) {				
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage());
			return null;
		}catch (Exception e) {
			logger.error("", e);
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		}		
		return "listar";
	}	
	
	public String editarGrupoUsuarioTelaAba(){
		try {
			this.telaVO = (TelaVO) this.listaTelaVO.getRowData();
			ManagedBeanUtil.getSession(Boolean.FALSE).setAttribute(AtributoSessaoEnum.TELA_VO.getAtributo(),telaVO);
			this.telaMB.populaAbaVOTemporaria();
		 } catch (SQLException e) {
				logger.error("erro sqlstate "+e.getSQLState(), e);	
				ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		}catch (BusinessException e) {				
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage());
			return null;
		}catch (Exception e) {
			logger.error("", e);
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		}		
		return "editar";
	}
	
	public String editarGrupoUsuarioTelaComponente(){
		try {
			this.telaVO = (TelaVO) this.listaTelaVO.getRowData();
			ManagedBeanUtil.getSession(Boolean.FALSE).setAttribute(AtributoSessaoEnum.TELA_VO.getAtributo(),telaVO);
			this.telaMB.populaComponenteVOTemporaria();
		} catch (SQLException e) {
			logger.error("erro sqlstate "+e.getSQLState(), e);	
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		}catch (BusinessException e) {				
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage());
			return null;
		}catch (Exception e) {
			logger.error("", e);
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		}			
		return "editar";
	}
	
	public void checarAbaVOComponenteVO(){
		this.telaVO = (TelaVO) this.listaTelaVO.getRowData();
		Boolean checada = this.telaVO.getChecada();
		for (AbaVO abaVO : this.telaVO.getListaAbasVOTela()) {
			abaVO.setChecada(checada);
		}
		for (ComponenteVO componenteVO : this.telaVO.getListaComponentesVOTela()) {
			componenteVO.setChecada(checada);
		}		
	}
	
	public String associarUsuarioCondominioGrupoUsuario(){
		 
		this.grupoUsuario = (GrupoUsuario) this.listaGruposUsuarios.getRowData();
		ManagedBeanUtil.getSession(Boolean.FALSE).setAttribute(AtributoSessaoEnum.ID_CONDOMINIO.getAtributo(),this.idCondominio);
		ManagedBeanUtil.getSession(Boolean.FALSE).setAttribute(AtributoSessaoEnum.GRUPO_USUARIO.getAtributo(),this.grupoUsuario);		
		this.usuarioMB.buscarUsuariosAssociadosOuNao();
		return "associarUsuarioCondominio";		
	}
	
	public String salvarGrupoUsuario(){
		try {
			Iterator<TelaVO> iteratorTelaVO = this.listaTelaVO.iterator();
			Iterator<AbaVO> iteratorAbaVO = null;
			Iterator<ComponenteVO> iteratorComponenteVO = null;
			AbaVO abaVO = null;
			ComponenteVO componenteVO = null;
			List<TelaVO> listaTelaVO = new ArrayList<TelaVO>();
			while (iteratorTelaVO.hasNext()) {
				TelaVO telaVO = iteratorTelaVO.next();
				if(telaVO.getChecada() != null && telaVO.getChecada()){
					listaTelaVO.add(telaVO);
					iteratorAbaVO = telaVO.getListaAbasVOTela().iterator();
					while (iteratorAbaVO.hasNext()) {
						abaVO = (AbaVO) iteratorAbaVO.next();
						if(abaVO != null && !abaVO.getChecada()){
							telaVO.getListaAbasVOTela().remove(abaVO);
						}
					}
					iteratorComponenteVO = telaVO.getListaComponentesVOTela().iterator();
					while (iteratorComponenteVO.hasNext()) {
						componenteVO = (ComponenteVO) iteratorComponenteVO.next();
						if(componenteVO != null && !componenteVO.getChecada()){
							telaVO.getListaComponentesVOTela().remove(componenteVO);
						}
					}
				}
			}
			this.grupoUsuario.setListaTelaAcesso(listaTelaVO);
			this.grupoUsuario.setSituacao(GrupoUsuarioSituacaoEnum.ATIVO.getSituacao());
			this.grupoUsuarioService.salvar(this.grupoUsuario);
			this.listaTelaVO = new ListDataModel<TelaVO>();			
			this.pesquisaGrupoUsuario();
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
	
	public String cancelarCadastroGrupoUsuario(){
		return "cancelar";
	}
	
	public String cancelarEditaGrupoUsuario(){
		return "cancelar";
	}
	
	public void excluirGrupoUsuario(){
		this.grupoUsuario = (GrupoUsuario) this.listaGruposUsuarios.getRowData();
		
		try {
			this.grupoUsuarioService.excluir(this.grupoUsuario.getId());
			this.pesquisaGrupoUsuario();
			ManagedBeanUtil.setMensagemInfo("msg.grupoUsuario.excluidoSucesso");
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
	
	private void populaTelaVOEditar() throws SQLException, Exception{		
		TelaVO telaVO = null;
		List<TelaVO> listaTelaVO = new ArrayList<TelaVO>();		
		List<Tela> listaTelas = this.telaService.buscarTodas();
		for (Tela tela : listaTelas) {
			telaVO = new TelaVO();
			telaVO.setIdTela(tela.getId());
			telaVO.setDescricaoI18nTela(tela.getDescricaoI18n());
			telaVO.setNomeI18nTela(tela.getNomeI18n());
			telaVO.setListaAbasVOTela(this.popularAbaVOEditar(tela));
			telaVO.setListaAbasTela(this.abaService.buscarPorIdTela(telaVO.getIdTela()));
			telaVO.setListaComponentesVOTela(this.popularComponenteVOEditar(tela));	
			telaVO.setListaComponentesTela(this.componenteService.buscarPorIdTela(telaVO.getIdTela()));
			telaVO.setChecada(this.essaTelaEstaChecada(tela));
			listaTelaVO.add(telaVO);
		}
		this.listaTelaVO = new ListDataModel<TelaVO>(listaTelaVO);
	}
	
	private Boolean essaTelaEstaChecada(Tela tela){		
		Iterator<Tela> iteratorTela = this.grupoUsuario.getListaTela().iterator();		
		while (iteratorTela.hasNext()) {
			Tela telaChecada = (Tela) iteratorTela.next();
			if(tela.getId() == telaChecada.getId()){
				return Boolean.TRUE;
			}			
		}
		return Boolean.FALSE;
	}	
	
	private Boolean essaAbaEstaChecada(AbaVO abaVO){		
		Iterator<Tela> iteratorTela = this.grupoUsuario.getListaTela().iterator();
		Iterator<Aba> iteratorAba = null;
		Aba abaChecada = null;
		while (iteratorTela.hasNext()) {
			Tela telaChecada = (Tela) iteratorTela.next();
			iteratorAba = telaChecada.getListaAbas().iterator();
			while (iteratorAba.hasNext()) {
				abaChecada = iteratorAba.next(); 
				if(abaVO.getIdAba() == abaChecada.getId()){
					return Boolean.TRUE;
				}
			}
		}
		return Boolean.FALSE;
	}
	
	private Boolean esseComponenteEstaChecado(ComponenteVO componenteVO){		
		Iterator<Tela> iteratorTela = this.grupoUsuario.getListaTela().iterator();
		Iterator<Componente> iteratorComponente = null;
		Componente componenteChecado = null;
		while (iteratorTela.hasNext()) {
			Tela telaChecada = (Tela) iteratorTela.next();
			iteratorComponente = telaChecada.getListaComponentes().iterator();
			while (iteratorComponente.hasNext()) {
				componenteChecado = iteratorComponente.next(); 
				if(componenteVO.getIdComponente() == componenteChecado.getId()){
					return Boolean.TRUE;
				}
			}
		}
		return Boolean.FALSE;
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
			telaVO.setListaComponentesVOTela(this.popularComponenteVO(tela));
			listaTelaVO.add(telaVO);
		}
		this.listaTelaVO = new ListDataModel<TelaVO>(listaTelaVO);
	}

// TODO: C�digo comentado em 12/10/2017. Apagar em 180 dias
//	private void populaEditarTelaVO() throws SQLException, Exception{
//		TelaVO telaVO = null;
//		List<TelaVO> listaTelaVO = new ArrayList<TelaVO>();
//		List<Tela> listaTelas = this.telaService.buscarTodas();
//		for (Tela tela : listaTelas) {
//			telaVO = new TelaVO();
//			telaVO.setIdTela(tela.getId());
//			telaVO.setDescricaoI18nTela(tela.getDescricaoI18n());
//			telaVO.setNomeI18nTela(tela.getNomeI18n());
//			telaVO.setListaAbasVOTela(this.popularAbaVOEditar(tela));
//			telaVO.setListaComponentesVOTela(this.popularComponenteVO(tela));
//			listaTelaVO.add(telaVO);
//		}
//		this.listaTelaVO = new ListDataModel<TelaVO>(listaTelaVO);
//	}
	
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
	
	private List<AbaVO> popularAbaVOEditar(Tela tela){
		List<AbaVO> listaAbaVO = new ArrayList<AbaVO>();
		AbaVO abaVO = null;
		for (Aba aba : tela.getListaAbas()) {
			abaVO = new AbaVO();
			abaVO.setIdAba(aba.getId());
			abaVO.setNomeI18nAba(aba.getNomeI18n());
			abaVO.setDescricaoI18nAba(aba.getDescricaoI18n());
			abaVO.setChecada(this.essaAbaEstaChecada(abaVO));
			listaAbaVO.add(abaVO);
		}
		return listaAbaVO;
	}
	
	private List<ComponenteVO> popularComponenteVO(Tela tela){
		List<ComponenteVO> listaComponenteVO = new ArrayList<ComponenteVO>();
		ComponenteVO componenteVO = null;
		for (Componente componente : tela.getListaComponentes()) {
			componenteVO = new ComponenteVO();
			componenteVO.setIdTela(componente.getIdTela());
			componenteVO.setIdComponente(componente.getId());
			componenteVO.setNomeI18nComponente(componente.getNomeI18n());
			componenteVO.setDescricaoI18nComponente(componente.getDescricaoI18n());
			componenteVO.setTipoI18nComponente(componente.getTipoI18n());			
			listaComponenteVO.add(componenteVO);
		}
		return listaComponenteVO;
	}
	
	private List<ComponenteVO> popularComponenteVOEditar(Tela tela){
		List<ComponenteVO> listaComponenteVO = new ArrayList<ComponenteVO>();
		ComponenteVO componenteVO = null;
		for (Componente componente : tela.getListaComponentes()) {
			componenteVO = new ComponenteVO();
			componenteVO.setIdTela(componente.getIdTela());
			componenteVO.setIdComponente(componente.getId());
			componenteVO.setNomeI18nComponente(componente.getNomeI18n());
			componenteVO.setDescricaoI18nComponente(componente.getDescricaoI18n());
			componenteVO.setTipoI18nComponente(componente.getTipoI18n());
			componenteVO.setChecada(this.esseComponenteEstaChecado(componenteVO));
			listaComponenteVO.add(componenteVO);
		}
		return listaComponenteVO;
	}
	
	
	public void checarTodosCheckbox() throws SQLException, Exception{
		Iterator<TelaVO> iteratorTelaVO = listaTelaVO.iterator();
		Iterator<AbaVO> iteratorAbaVO = null;
		Iterator<ComponenteVO> iteratorComponenteVO = null;
		TelaVO telaVO = null;
		AbaVO abaVO = null; 
		ComponenteVO componenteVO = null; 
		while (iteratorTelaVO.hasNext()) {
			telaVO = iteratorTelaVO.next();
			telaVO.setChecada(this.checadoTodos);
			iteratorAbaVO = telaVO.getListaAbasVOTela().iterator();
			while (iteratorAbaVO.hasNext()) {
				abaVO = iteratorAbaVO.next();
				abaVO.setChecada(this.checadoTodos);
			}
			iteratorComponenteVO = telaVO.getListaComponentesVOTela().iterator();
			while (iteratorComponenteVO.hasNext()) {
				componenteVO = iteratorComponenteVO.next();
				componenteVO.setChecada(this.checadoTodos);
			}
			if(!telaVO.getListaAbasVOTela().isEmpty() && !telaVO.getListaComponentesVOTela().isEmpty()){
				ManagedBeanUtil.getSession(Boolean.FALSE).setAttribute(AtributoSessaoEnum.TELA_VO.getAtributo(),telaVO);
				ManagedBeanUtil.getSession(Boolean.FALSE).setAttribute(AtributoSessaoEnum.CHECADO.getAtributo(),this.checadoTodos);
				this.telaMB.checarTodosCheckbox();
			}	
		}		
	}
	
	public Boolean verificarGrupoCondominoOuSindicoOuFuncionario(){
		this.grupoUsuario = (GrupoUsuario) this.listaGruposUsuarios.getRowData();
		for (GrupoUsuario grupoUsuario : this.listaGrupoUsuarioTipoSindicoCondominoEFuncionario) {
			if(grupoUsuario.getId().intValue() == this.grupoUsuario.getId().intValue()){
				return Boolean.TRUE;
			}			
		}
		return Boolean.FALSE;
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

	public String getTabSelecionada() {
		return tabSelecionada;
	}

	public void setTabSelecionada(String tabSelecionada) {
		this.tabSelecionada = tabSelecionada;
	}	

	public List<SelectItem> getListaSIPadrao() {
		return listaSIPadrao;
	}
	
	public void setListaSIPadrao(List<SelectItem> listaSIPadrao) {
		this.listaSIPadrao = listaSIPadrao;
	}

	public List<SelectItem> getListaSITipoUsuario() {
		return listaSITipoUsuario;
	}

	public void setListaSITipoUsuario(List<SelectItem> listaSITipoUsuario) {
		this.listaSITipoUsuario = listaSITipoUsuario;
	}		

	public Integer getIdCondominio() {
		return idCondominio;
	}

	public void setIdCondominio(Integer idCondominio) {
		this.idCondominio = idCondominio;
	}

	private void popularSituacao(){
		this.listaSISituacao = new ArrayList<SelectItem>();
		this.listaSISituacao.add(new SelectItem(-1, AplicacaoUtil.i18n("grupoUsuario.situacao.todos")));
		this.listaSISituacao.add(new SelectItem(1, AplicacaoUtil.i18n("grupoUsuario.situacao.1")));
		this.listaSISituacao.add(new SelectItem(0, AplicacaoUtil.i18n("grupoUsuario.situacao.0")));
	}
	
	private void popularPadrao(){
		this.listaSIPadrao = new ArrayList<SelectItem>();
		this.listaSIPadrao.add(new SelectItem(Boolean.FALSE, AplicacaoUtil.i18n("grupoUsuario.padrao.0")));
		this.listaSIPadrao.add(new SelectItem(Boolean.TRUE, AplicacaoUtil.i18n("grupoUsuario.padrao.1")));
	}
	
	private void popularTipoUsuario(){
		this.listaSITipoUsuario = new ArrayList<SelectItem>();
		this.listaSITipoUsuario.add(new SelectItem(1, AplicacaoUtil.i18n("grupoUsuario.tipoUsuario.1")));
		this.listaSITipoUsuario.add(new SelectItem(2, AplicacaoUtil.i18n("grupoUsuario.tipoUsuario.2")));
	}

}
