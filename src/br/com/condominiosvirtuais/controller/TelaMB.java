package br.com.condominiosvirtuais.controller;

import java.io.Serializable;
import java.sql.SQLException;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.model.ListDataModel;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.log4j.Logger;

import br.com.condominiosvirtuais.entity.GrupoUsuario;
import br.com.condominiosvirtuais.entity.Tela;
import br.com.condominiosvirtuais.enumeration.AtributoSessaoEnum;
import br.com.condominiosvirtuais.service.AbaService;
import br.com.condominiosvirtuais.util.ManagedBeanUtil;

@Named @SessionScoped
public class TelaMB implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private static Logger logger = Logger.getLogger(TelaMB.class);
	
	private Tela tela;
	
	private GrupoUsuario grupoUsuario;
	
	private ListDataModel<Tela> listaTela;
	
	@Inject
	private AbaService abaService;
	
	
	public void inicializaTelaMB(){
		this.grupoUsuario = (GrupoUsuario) ManagedBeanUtil.getSession(Boolean.FALSE).getAttribute(AtributoSessaoEnum.GRUPO_USUARIO.getAtributo());
		this.listaTela = new ListDataModel<Tela>(this.grupoUsuario.getListaTela());		
	}
	
	public String visualizarAbaTela() throws SQLException, Exception{		
		this.tela = (Tela) this.listaTela .getRowData();
		this.tela.setListaAbas(this.abaService.buscarPorIdTela(this.tela.getId()));
		ManagedBeanUtil.getSession(Boolean.TRUE).setAttribute(AtributoSessaoEnum.TELA.getAtributo(),this.tela);
		return "visualizar";
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


	public ListDataModel<Tela> getListaTela() {
		return listaTela;
	}


	public void setListaTela(ListDataModel<Tela> listaTela) {
		this.listaTela = listaTela;
	}	
	

}
