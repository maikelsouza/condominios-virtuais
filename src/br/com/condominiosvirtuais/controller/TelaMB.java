package br.com.condominiosvirtuais.controller;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.faces.model.ListDataModel;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.log4j.Logger;

import br.com.condominiosvirtuais.entity.Aba;
import br.com.condominiosvirtuais.entity.Componente;
import br.com.condominiosvirtuais.entity.GrupoUsuario;
import br.com.condominiosvirtuais.entity.Tela;
import br.com.condominiosvirtuais.enumeration.AtributoSessaoEnum;
import br.com.condominiosvirtuais.persistence.ModuloDAO;
import br.com.condominiosvirtuais.service.AbaService;
import br.com.condominiosvirtuais.service.ComponenteService;
import br.com.condominiosvirtuais.util.ManagedBeanUtil;
import br.com.condominiosvirtuais.vo.TelaVO;

@Named @SessionScoped
public class TelaMB implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private static Logger logger = Logger.getLogger(TelaMB.class);
	
	private Tela tela;
	
	private TelaVO telaVO;
	
	private GrupoUsuario grupoUsuario;
	
	private ListDataModel<TelaVO> listaTelaVO;
	
	@Inject
	private ModuloDAO moduloDAO;
		
	public void inicializaTelaMB() throws SQLException, Exception{
		this.grupoUsuario = (GrupoUsuario) ManagedBeanUtil.getSession(Boolean.FALSE).getAttribute(AtributoSessaoEnum.GRUPO_USUARIO.getAtributo());
		this.populaTelaVO();
				
	}
	
	public String visualizarTelaAba(){		
		try {
			this.telaVO = (TelaVO) this.listaTelaVO.getRowData();
			ManagedBeanUtil.getSession(Boolean.TRUE).setAttribute(AtributoSessaoEnum.TELA.getAtributo(),this.telaVO);
			if(this.telaVO.getListaAbasTela().isEmpty()){
				ManagedBeanUtil.setMensagemInfo("msg.tela.semAbas");
			}
		} catch (Exception e) {
			logger.error("", e);
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		}
		return "visualizar";
	}
	
	public String visualizarTelaComponente(){		
		try {
			this.telaVO = (TelaVO) this.listaTelaVO.getRowData();
			ManagedBeanUtil.getSession(Boolean.TRUE).setAttribute(AtributoSessaoEnum.TELA.getAtributo(),this.telaVO);
			if(this.telaVO.getListaComponentesTela().isEmpty()){
				ManagedBeanUtil.setMensagemInfo("msg.tela.semComponentes");
			}
		} catch (Exception e) {
			logger.error("", e);
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		}
		return "visualizar";
	}
	
	private void populaTelaVO() throws SQLException, Exception{
		TelaVO telaVO = null;List<TelaVO> listaTelaVO = new ArrayList<TelaVO>();
		for (Tela tela : this.grupoUsuario.getListaTela()) {
			telaVO = new TelaVO();
			telaVO.setIdTela(tela.getId());
			telaVO.setDescricaoI18nTela(tela.getDescricaoI18n());
			telaVO.setNomeI18nTela(tela.getNomeI18n());
			telaVO.setNomeI18nModulo(this.moduloDAO.buscarPorId(tela.getIdModulo()).getNomeI18n());
			telaVO.setListaAbasTela(new ArrayList<Aba>(tela.getListaAbas()));
			telaVO.setListaComponentesTela(new ArrayList<Componente>(tela.getListaComponentes()));
			listaTelaVO.add(telaVO);
		}
		Collections.sort(listaTelaVO);
		this.listaTelaVO = new ListDataModel<TelaVO>(listaTelaVO);
	}
	
	public String voltarVisualizarGrupoUsuarioTela(){
		return "voltar";
	}
	
	public String cancelarVisualizarGrupoUsuarioTela(){
		return "cancelar";
	}

	public Tela getTela() {
		return tela;
	}

	public void setTela(Tela tela) {
		this.tela = tela;
	}

	public GrupoUsuario getGrupoUsuario() {
		return grupoUsuario;
	}

	public void setGrupoUsuario(GrupoUsuario grupoUsuario) {
		this.grupoUsuario = grupoUsuario;
	}

	public ListDataModel<TelaVO> getListaTelaVO() {
		return listaTelaVO;
	}

	public void setListaTelaVO(ListDataModel<TelaVO> listaTelaVO) {
		this.listaTelaVO = listaTelaVO;
	}	
	

}
