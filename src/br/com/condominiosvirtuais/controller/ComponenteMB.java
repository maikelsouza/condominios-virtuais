package br.com.condominiosvirtuais.controller;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.faces.model.ListDataModel;
import javax.inject.Named;

import org.apache.log4j.Logger;

import br.com.condominiosvirtuais.entity.Componente;
import br.com.condominiosvirtuais.enumeration.AtributoSessaoEnum;
import br.com.condominiosvirtuais.util.ManagedBeanUtil;
import br.com.condominiosvirtuais.vo.TelaVO;

@Named @SessionScoped
public class ComponenteMB implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private static Logger logger = Logger.getLogger(ComponenteMB.class);
	
	private TelaVO telaVO;
	
	private ListDataModel<Componente> listaComponente;
	
	
	public void inicializaComponenteMB(){
		this.telaVO = (TelaVO) ManagedBeanUtil.getSession(Boolean.FALSE).getAttribute(AtributoSessaoEnum.TELA_VO.getAtributo());
		this.listaComponente = new ListDataModel<Componente>(this.telaVO.getListaComponentesTela());		
	}
	
	public String voltarVisualizarTelaComponente(){
		return "voltar";
	}
	
	public String cancelarVisualizarTelaComponente(){
		return "cancelar";
	}	

	public TelaVO getTelaVO() {
		return telaVO;
	}

	public void setTelaVO(TelaVO telaVO) {
		this.telaVO = telaVO;
	}

	public ListDataModel<Componente> getListaComponente() {
		return listaComponente;
	}

	public void setListaComponente(ListDataModel<Componente> listaComponente) {
		this.listaComponente = listaComponente;
	}
	
	

}
