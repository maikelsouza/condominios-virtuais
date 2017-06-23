package br.com.condominiosvirtuais.controller;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.faces.model.ListDataModel;
import javax.inject.Named;

import org.apache.log4j.Logger;

import br.com.condominiosvirtuais.entity.Aba;
import br.com.condominiosvirtuais.entity.GrupoUsuario;
import br.com.condominiosvirtuais.entity.Tela;
import br.com.condominiosvirtuais.enumeration.AtributoSessaoEnum;
import br.com.condominiosvirtuais.util.ManagedBeanUtil;

@Named @SessionScoped
public class AbaMB implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private static Logger logger = Logger.getLogger(AbaMB.class);
	
	private Tela tela;
	
	private ListDataModel<Aba> listaAba;
	
	
	public void inicializaAbaMB(){
		this.tela = (Tela) ManagedBeanUtil.getSession(Boolean.FALSE).getAttribute(AtributoSessaoEnum.TELA.getAtributo());
		this.listaAba = new ListDataModel<Aba>(this.tela.getListaAbas());		
	}

	public String voltarVisualizarAbaTela(){
		return "voltar";
	}
	
	public String cancelarVisualizarAbaTela(){
		return "cancelar";
	}

	public Tela getTela() {
		return tela;
	}

	public void setTela(Tela tela) {
		this.tela = tela;
	}

	public ListDataModel<Aba> getListaAba() {
		return listaAba;
	}

	public void setListaAba(ListDataModel<Aba> listaAba) {
		this.listaAba = listaAba;
	}


}
