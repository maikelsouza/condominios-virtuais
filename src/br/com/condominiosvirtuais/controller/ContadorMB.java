package br.com.condominiosvirtuais.controller;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.log4j.Logger;

import br.com.condominiosvirtuais.entity.Contador;
import br.com.condominiosvirtuais.entity.EscritorioContabilidade;
import br.com.condominiosvirtuais.entity.GrupoUsuario;
import br.com.condominiosvirtuais.enumeration.EscritorioContabilidadeSituacaoEnum;
import br.com.condominiosvirtuais.enumeration.GrupoUsuarioPadraoEnum;
import br.com.condominiosvirtuais.enumeration.GrupoUsuarioSituacaoEnum;
import br.com.condominiosvirtuais.enumeration.GrupoUsuarioTipoUsuarioEnum;
import br.com.condominiosvirtuais.enumeration.UsuarioSexoEnum;
import br.com.condominiosvirtuais.enumeration.UsuarioSituacaoEnum;
import br.com.condominiosvirtuais.exception.BusinessException;
import br.com.condominiosvirtuais.service.ContadorService;
import br.com.condominiosvirtuais.service.EscritorioContabilidadeService;
import br.com.condominiosvirtuais.service.GrupoUsuarioService;
import br.com.condominiosvirtuais.util.AplicacaoUtil;
import br.com.condominiosvirtuais.util.ManagedBeanUtil;

@Named @SessionScoped
public class ContadorMB implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private static Logger logger = Logger.getLogger(ContadorMB.class);
	
	private Contador contador;
	
	private List<SelectItem> listaSIDias = new ArrayList<SelectItem>();
	
	private List<SelectItem> listaSIMeses = new ArrayList<SelectItem>();
	
	private List<SelectItem> listaSIAnos = new ArrayList<SelectItem>();
	
	private ListDataModel<Contador> listaContadorDataModel = null;		                                
	
	private List<SelectItem> listaSISexo;
	
	private List<SelectItem> listaSISituacao;
	
	private List<SelectItem> listaSIEscritorioContabilidade;
	
	@Inject
	private GrupoUsuarioService grupoUsuarioService; 
	
	@Inject
	private EscritorioContabilidadeService escritorioContabilidadeService;
	
	@Inject
	private ContadorService contadorService; 

	
	@PostConstruct
	public void iniciarContadorMB(){
		this.contador = new Contador();
		this.popularListaSISexo();
		this.popularListaSISituacao();
		this.popularListaEscritoriosContabilidadeAtivos();
		ManagedBeanUtil.popularSIDias(this.listaSIDias);
		ManagedBeanUtil.popularSIMeses(this.listaSIMeses);
		ManagedBeanUtil.popularSIAnos(this.listaSIAnos);		
	}	
	
	
	public void popularListaEscritoriosContabilidadeAtivos(){
		try {
			List<EscritorioContabilidade> listaDeEscritoriosContabilidadeAtivos = null; 
			listaDeEscritoriosContabilidadeAtivos = this.escritorioContabilidadeService.buscarPorSituacao(EscritorioContabilidadeSituacaoEnum.ATIVO.getSituacao());
			this.listaSIEscritorioContabilidade = new ArrayList<SelectItem>();
			for (EscritorioContabilidade escritorioContabilidade : listaDeEscritoriosContabilidadeAtivos) {
				this.listaSIEscritorioContabilidade.add(new SelectItem(escritorioContabilidade.getId(), escritorioContabilidade.getNome()));				
			}
		} catch (SQLException e) {
			logger.error("erro sqlstate "+e.getSQLState(), e);	
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");				
		} catch (Exception e) {
			logger.error("", e);
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		}	
	}
	
	public String salvar(){
		try {
			this.contador.setSituacao(UsuarioSituacaoEnum.ATIVO.getSituacao());
			this.popularGrupoUsuarioEscritorioContabilidade();
			this.contador.getEmail().setPrincipal(Boolean.TRUE);		
			if(!this.contador.getSenha().equals(this.contador.getConfirmarSenha())){
				ManagedBeanUtil.setMensagemErro("msg.contador.senhasNaoCorrespondem");
				return null;
			}
			if (!ManagedBeanUtil.validaEmail(this.contador.getEmail().getEmail())){
				ManagedBeanUtil.setMensagemErro("msg.contador.formatoEmailInvalido");
				return null;
			}
			this.contadorService.salvar(this.contador);
			ManagedBeanUtil.setMensagemInfo("msg.contador.salvoSucesso");
		} catch (SQLException e) {
			logger.error("erro sqlstate "+e.getSQLState(), e);	
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");	
		} catch (BusinessException e) {				
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage());
			return null;		
		} catch (Exception e) {
			logger.error("", e);
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		}	
		return null;		
	}
	
	public String atualizarContador(){		
		try {			
			if (!ManagedBeanUtil.validaEmail(this.contador.getEmail().getEmail())){
				ManagedBeanUtil.setMensagemErro("msg.contador.formatoEmailInvalido");
				return null;
			}
			this.contadorService.atualizar(this.contador);
			this.pesquisar();
			ManagedBeanUtil.setMensagemInfo("msg.contador.atualizadoSucesso");			
		} catch (SQLException e) {
			logger.error("erro sqlstate "+e.getSQLState(), e);	
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		} catch (BusinessException e) {				
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage());
			return null;	
		} catch (Exception e) {
			logger.error("", e);
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		}	
		return "atualizar";	
	}
	
	public String editarContador(){
		this.contador = this.listaContadorDataModel.getRowData();
		try {			
			if (!ManagedBeanUtil.validaEmail(this.contador.getEmail().getEmail())){
				ManagedBeanUtil.setMensagemErro("msg.contador.formatoEmailInvalido");
				return null;
			}
			this.contadorService.atualizar(this.contador);
			ManagedBeanUtil.setMensagemInfo("msg.contador.atualizadoSucesso");
		} catch (SQLException e) {
			logger.error("erro sqlstate "+e.getSQLState(), e);	
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");				
		} catch (Exception e) {
			logger.error("", e);
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		}	
		return "editar";		
	}
	
	public String voltarAtualizarContador(){		
		return "voltar";
	}
	
	public String pesquisar(){
		try {                         
			this.listaContadorDataModel = new ListDataModel<Contador>(this.contadorService.buscarPorIdEscritorioContabilidadeESituacao(this.contador.getIdEscritorioContabilidade(), this.contador.getSituacao()));
			if(this.listaContadorDataModel.getRowCount() == 0){
				ManagedBeanUtil.setMensagemInfo("msg.contador.semContador");
			}			
		} catch (SQLException e) {
			logger.error("erro sqlstate "+e.getSQLState(), e);	
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		} catch (BusinessException e) {				
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage());
			return null;		
		} catch (Exception e) {
			logger.error("", e);
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		}
		return null;
	}
	
	
	public void limparCadastroContador(){
		this.contador = new Contador();
	}
	
	public String cancelarCadastroContador(){
		return "cancelar";
	}
	
	public String cancelarFiltroContador(){
		return "cancelar";
	}
	
	
	private void popularListaSISexo(){
		this.listaSISexo = new ArrayList<SelectItem>();
		this.listaSISexo.add(new SelectItem(UsuarioSexoEnum.MASCULINO.getSexo(), AplicacaoUtil.i18n("contador.sexo.0")));
		this.listaSISexo.add(new SelectItem(UsuarioSexoEnum.FEMININO.getSexo(), AplicacaoUtil.i18n("contador.sexo.1")));
	}
	
	private void popularListaSISituacao(){
		this.listaSISituacao = new ArrayList<SelectItem>();
		this.listaSISituacao.add(new SelectItem(UsuarioSituacaoEnum.ATIVO.getSituacao(), AplicacaoUtil.i18n("contador.situacao.1")));
		this.listaSISituacao.add(new SelectItem(UsuarioSituacaoEnum.INATIVO.getSituacao(), AplicacaoUtil.i18n("contador.situacao.0")));
	}
	
	private void popularGrupoUsuarioEscritorioContabilidade() throws SQLException, Exception{
		List<GrupoUsuario> listaGrupoUsuario = new ArrayList<GrupoUsuario>();
		listaGrupoUsuario.addAll(this.grupoUsuarioService.buscarPorIdEscritorioDeContabilidadeEPadraoETipoUsuarioESituacao(this.contador.getIdEscritorioContabilidade(),
				GrupoUsuarioPadraoEnum.SIM.getPadrao(), GrupoUsuarioTipoUsuarioEnum.ESCRITORIO_CONTABILIDADE.getTipoUsuario(),GrupoUsuarioSituacaoEnum.ATIVO.getSituacao()));
		if(listaGrupoUsuario.isEmpty()){
			listaGrupoUsuario.add(this.grupoUsuarioService.buscarPorPadraoETipoUsuarioESituacao(GrupoUsuarioPadraoEnum.SIM.getPadrao(), GrupoUsuarioTipoUsuarioEnum.CONDOMINO.getTipoUsuario(),GrupoUsuarioSituacaoEnum.ATIVO.getSituacao()));			
		}
		this.contador.setListaGrupoUsuario(listaGrupoUsuario);
			
	}
	
	public Contador getContador() {
		return contador;
	}

	public void setContador(Contador contador) {
		this.contador = contador;
	}

	public List<SelectItem> getListaSIEscritorioContabilidade() {
		return listaSIEscritorioContabilidade;
	}


	public void setListaSIEscritorioContabilidade(List<SelectItem> listaSIEscritorioContabilidade) {
		this.listaSIEscritorioContabilidade = listaSIEscritorioContabilidade;
	}

	public List<SelectItem> getListaSIDias() {
		return listaSIDias;
	}

	public void setListaSIDias(List<SelectItem> listaSIDias) {
		this.listaSIDias = listaSIDias;
	}

	public List<SelectItem> getListaSIMeses() {
		return listaSIMeses;
	}

	public void setListaSIMeses(List<SelectItem> listaSIMeses) {
		this.listaSIMeses = listaSIMeses;
	}

	public List<SelectItem> getListaSIAnos() {
		return listaSIAnos;
	}

	public void setListaSIAnos(List<SelectItem> listaSIAnos) {
		this.listaSIAnos = listaSIAnos;
	}

	public List<SelectItem> getListaSISexo() {
		return listaSISexo;
	}

	public void setListaSISexo(List<SelectItem> listaSISexo) {
		this.listaSISexo = listaSISexo;
	}

	public List<SelectItem> getListaSISituacao() {
		return listaSISituacao;
	}

	public void setListaSISituacao(List<SelectItem> listaSISituacao) {
		this.listaSISituacao = listaSISituacao;
	}

	public ListDataModel<Contador> getListaContadorDataModel() {		
		return listaContadorDataModel;
	}

	public void setListaContadorDataModel(ListDataModel<Contador> listaContadorDataModel) {
		this.listaContadorDataModel = listaContadorDataModel;
	}	
	

}
