package br.com.condominiosvirtuais.controller;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.log4j.Logger;

import br.com.condominiosvirtuais.entity.Condominio;
import br.com.condominiosvirtuais.entity.SindicoProfissional;
import br.com.condominiosvirtuais.enumeration.UsuarioSexoEnum;
import br.com.condominiosvirtuais.exception.BusinessException;
import br.com.condominiosvirtuais.service.impl.CondominioServiceImpl;
import br.com.condominiosvirtuais.service.impl.SindicoProfissionalServiceImpl;
import br.com.condominiosvirtuais.util.AplicacaoUtil;
import br.com.condominiosvirtuais.util.ManagedBeanUtil;

@SessionScoped @Named
public class SindicoProfissionalMB implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private static Logger logger = Logger.getLogger(SindicoProfissionalMB.class);
	
	@Inject
	private SindicoProfissionalServiceImpl sindicoProfissionalService;
	
	@Inject
	private CondominioServiceImpl condominioService;
	
	private SindicoProfissional sindicoProfissional;	
	
	private List<SelectItem> listaSISexo;
	
	private List<SelectItem> listaSICondominio;
	
	private List<SelectItem> listaSIDias = new ArrayList<SelectItem>();
	
	private List<SelectItem> listaSIMeses = new ArrayList<SelectItem>();
	
	private List<SelectItem> listaSIAnos = new ArrayList<SelectItem>();	

	
	
	@PostConstruct
	public void iniciarSindicoProfissionalMB(){
		this.popularListaSISexo();
		this.popularListaSICondominio();
		this.sindicoProfissional = new SindicoProfissional();
		ManagedBeanUtil.popularSIDias(this.listaSIDias);
		ManagedBeanUtil.popularSIMeses(this.listaSIMeses);
		ManagedBeanUtil.popularSIAnos(this.listaSIAnos);
	}	
	
	
	
	private void popularListaSISexo(){
		this.listaSISexo = new ArrayList<SelectItem>();
		this.listaSISexo.add(new SelectItem(UsuarioSexoEnum.MASCULINO.getSexo(), AplicacaoUtil.i18n("sindicoProfissional.sexo.0")));
		this.listaSISexo.add(new SelectItem(UsuarioSexoEnum.FEMININO.getSexo(), AplicacaoUtil.i18n("sindicoProfissional.sexo.1")));
	}
	
	private void popularListaSICondominio(){
		List<Condominio> listaDeCondominios;
		this.listaSICondominio =  new ArrayList<SelectItem>();  
		try {
			listaDeCondominios = this.condominioService.buscarTodos();
			for (Condominio condominio : listaDeCondominios) {
				this.listaSICondominio.add(new SelectItem(condominio.getId(), condominio.getNome()));				
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
	
	public SindicoProfissional getSindicoProfissional() {
		return sindicoProfissional;
	}

	public void setSindicoProfissional(SindicoProfissional sindicoProfissional) {
		this.sindicoProfissional = sindicoProfissional;
	}

	public List<SelectItem> getListaSISexo() {
		return listaSISexo;
	}

	public void setListaSISexo(List<SelectItem> listaSISexo) {
		this.listaSISexo = listaSISexo;
	}

	public List<SelectItem> getListaSICondominio() {
		return listaSICondominio;
	}

	public void setListaSICondominio(List<SelectItem> listaSICondominio) {
		this.listaSICondominio = listaSICondominio;
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
	
	

}
