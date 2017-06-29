package br.com.condominiosvirtuais.controller;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.faces.model.ListDataModel;
import javax.inject.Named;

import org.apache.log4j.Logger;

import br.com.condominiosvirtuais.entity.Aba;
import br.com.condominiosvirtuais.enumeration.AtributoSessaoEnum;
import br.com.condominiosvirtuais.util.ManagedBeanUtil;
import br.com.condominiosvirtuais.vo.TelaVO;

@Named @SessionScoped
public class AbaMB implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private static Logger logger = Logger.getLogger(AbaMB.class);
	
	private TelaVO telaVO;
	
	private ListDataModel<Aba> listaAba;
	
	
	public void inicializaAbaMB(){
		this.telaVO = (TelaVO) ManagedBeanUtil.getSession(Boolean.FALSE).getAttribute(AtributoSessaoEnum.TELA.getAtributo());
		this.listaAba = new ListDataModel<Aba>(this.telaVO.getListaAbasTela());		
	}

	public String voltarVisualizarTelaAba(){
		return "voltar";
	}
	
	public String cancelarVisualizarTelaAba(){
		return "cancelar";
	}	

	public TelaVO getTelaVO() {
		return telaVO;
	}

	public void setTelaVO(TelaVO telaVO) {
		this.telaVO = telaVO;
	}

	public ListDataModel<Aba> getListaAba() {
		return listaAba;
	}

	public void setListaAba(ListDataModel<Aba> listaAba) {
		this.listaAba = listaAba;
	}


}
