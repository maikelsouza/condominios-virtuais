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
import br.com.condominiosvirtuais.enumeration.CondominioSituacaoEnum;
import br.com.condominiosvirtuais.enumeration.TipoGrupoUsuarioEnum;
import br.com.condominiosvirtuais.enumeration.UsuarioEnum;
import br.com.condominiosvirtuais.enumeration.UsuarioSexoEnum;
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
	
	private List<SelectItem> listaSIDias = new ArrayList<SelectItem>();
	
	private List<SelectItem> listaSIMeses = new ArrayList<SelectItem>();
	
	private List<SelectItem> listaSIAnos = new ArrayList<SelectItem>();	
	
	private List<Condominio> listaDeCondominios;
	
	private List<Condominio> listaDeCondominiosAssociados;
	

	
	
	@PostConstruct
	public void iniciarSindicoProfissionalMB(){
		this.popularListaSISexo();
		this.sindicoProfissional = new SindicoProfissional();
		this.listaDeCondominiosAssociados = new ArrayList<Condominio>();
		ManagedBeanUtil.popularSIDias(this.listaSIDias);
		ManagedBeanUtil.popularSIMeses(this.listaSIMeses);
		ManagedBeanUtil.popularSIAnos(this.listaSIAnos);		
	}	
	
	public String salvar (){
		this.sindicoProfissional.setSituacao(UsuarioEnum.ATIVO.getSituacao());
		this.sindicoProfissional.setIdGrupoUsuario(TipoGrupoUsuarioEnum.SINDICO_PROFISSIONAL.getGrupoUsuario());
		this.sindicoProfissional.getEmail().setPrincipal(Boolean.TRUE);
		this.sindicoProfissional.setListaCondominio(this.listaDeCondominiosAssociados);
		try {
			if(!this.sindicoProfissional.getSenha().equals(this.sindicoProfissional.getConfirmarSenha())){
				ManagedBeanUtil.setMensagemErro("msg.sindicoProfissional.senhasNaoCorrespondem");
				return null;
			}
			if (!ManagedBeanUtil.validaEmail(this.sindicoProfissional.getEmail().getEmail())){
				ManagedBeanUtil.setMensagemErro("msg.sindicoProfissional.formatoEmailInvalido");
				return null;
			}
			this.sindicoProfissionalService.salvar(sindicoProfissional);
			ManagedBeanUtil.setMensagemInfo("msg.sindicoProfissional.salvoSucesso");
		} catch (SQLException e) {
			logger.error("erro sqlstate "+e.getSQLState(), e);	
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");				
		} catch (Exception e) {
			logger.error("", e);
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		}
		return null;
	}
	
	public String cancelarCadastroSindicoProfissional(){
		return "cancelar";
	}
	
	public void limparCadastroSindicoProfissional(){
		this.sindicoProfissional = new SindicoProfissional();
		this.listaDeCondominiosAssociados.clear();
	}
	
	
	
	private void popularListaSISexo(){
		this.listaSISexo = new ArrayList<SelectItem>();
		this.listaSISexo.add(new SelectItem(UsuarioSexoEnum.MASCULINO.getSexo(), AplicacaoUtil.i18n("sindicoProfissional.sexo.0")));
		this.listaSISexo.add(new SelectItem(UsuarioSexoEnum.FEMININO.getSexo(), AplicacaoUtil.i18n("sindicoProfissional.sexo.1")));
	}
	
	public void popularListaCondominiosAtivosESemGestores(){
		try {
			this.listaDeCondominios = this.condominioService.buscarPorSituacaoESemGestores(CondominioSituacaoEnum.ATIVO.getSituacao());
		} catch (SQLException e) {
			logger.error("erro sqlstate "+e.getSQLState(), e);	
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");				
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

	public List<Condominio> getListaDeCondominios() {
		return listaDeCondominios;
	}

	public void setListaDeCondominios(List<Condominio> listaDeCondominios) {
		this.listaDeCondominios = listaDeCondominios;
	}

	public List<Condominio> getListaDeCondominiosAssociados() {
		return listaDeCondominiosAssociados;
	}

	public void setListaDeCondominiosAssociados(List<Condominio> listaDeCondominiosAssociados) {
		this.listaDeCondominiosAssociados = listaDeCondominiosAssociados;
	}
	

}
