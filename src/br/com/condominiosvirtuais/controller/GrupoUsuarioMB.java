package br.com.condominiosvirtuais.controller;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Instance;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.log4j.Logger;

import br.com.condominiosvirtuais.entity.GrupoUsuario;
import br.com.condominiosvirtuais.enumeration.AtributoSessaoEnum;
import br.com.condominiosvirtuais.enumeration.GrupoUsuarioSituacaoEnum;
import br.com.condominiosvirtuais.exception.BusinessException;
import br.com.condominiosvirtuais.service.GrupoUsuarioService;
import br.com.condominiosvirtuais.service.TelaService;
import br.com.condominiosvirtuais.util.AplicacaoUtil;
import br.com.condominiosvirtuais.util.ManagedBeanUtil;

@Named @SessionScoped
public class GrupoUsuarioMB implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private static Logger logger = Logger.getLogger(GrupoUsuarioMB.class);
	
	@Inject
	private TelaService telaService;	

	@Inject
	private GrupoUsuarioService grupoUsuarioService;
	
	@Inject
	private Instance<CondominioMB> condominioMB = null;
		
	private GrupoUsuario grupoUsuario;
		
	private List<SelectItem> listaSICondominios;
	
	private List<SelectItem> listaSISituacao;
	
	private Integer situacao;	
	
	private ListDataModel<GrupoUsuario> listaGruposUsuarios = null;
	
		
	@PostConstruct
	public void inciarGrupoUsuarioMB(){
		this.grupoUsuario = new GrupoUsuario();
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
	
	public List<SelectItem> getListaSICondominios() {
		this.listaSICondominios = this.condominioMB.get().buscarListaCondominiosAtivos();
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

	private void popularSituacao(){
		this.listaSISituacao = new ArrayList<SelectItem>();
		this.listaSISituacao.add(new SelectItem(-1, AplicacaoUtil.i18n("grupoUsuario.situacao.todos")));
		this.listaSISituacao.add(new SelectItem(1, AplicacaoUtil.i18n("grupoUsuario.situacao.1")));
		this.listaSISituacao.add(new SelectItem(0, AplicacaoUtil.i18n("grupoUsuario.situacao.0")));
	}

}
