package br.com.condominiosvirtuais.controller;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.faces.model.ListDataModel;
import javax.inject.Named;

import org.apache.log4j.Logger;

import br.com.condominiosvirtuais.entity.Componente;
import br.com.condominiosvirtuais.entity.Tela;
import br.com.condominiosvirtuais.enumeration.AtributoSessaoEnum;
import br.com.condominiosvirtuais.util.ManagedBeanUtil;

@Named @SessionScoped
public class ComponenteMB implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private static Logger logger = Logger.getLogger(ComponenteMB.class);
	
	private Tela tela;
	
	private ListDataModel<Componente> listaComponente;
	
	
	public void inicializaComponenteMB(){
		this.tela = (Tela) ManagedBeanUtil.getSession(Boolean.FALSE).getAttribute(AtributoSessaoEnum.TELA.getAtributo());
		this.listaComponente = new ListDataModel<Componente>(this.tela.getListaComponente());		
	}

}
