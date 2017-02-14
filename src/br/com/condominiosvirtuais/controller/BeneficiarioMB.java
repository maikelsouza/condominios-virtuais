package br.com.condominiosvirtuais.controller;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Instance;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.log4j.Logger;

import br.com.condominiosvirtuais.entity.Beneficiario;
import br.com.condominiosvirtuais.service.BeneficiarioService;
import br.com.condominiosvirtuais.service.CondominioService;
import br.com.condominiosvirtuais.util.ManagedBeanUtil;

@Named @SessionScoped
public class BeneficiarioMB implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private static Logger logger = Logger.getLogger(BeneficiarioMB.class);
	
	@Inject
	private BeneficiarioService beneficiarioService;
	
	@Inject
	private Instance<CondominioService> condominioService;
	
	private Beneficiario beneficiario;
	
	private List<SelectItem> listaSICondominios;
	
	private ListDataModel<Beneficiario> listaBeneficiarios = null;	
	
	@Inject
	private Instance<CondominioMB> condominioMB; 
	
	private String nomeCondominio;
	
	public BeneficiarioMB(){
		this.beneficiario = new Beneficiario();
	}
	
	@PostConstruct
	public void iniciarBeneficiarioMB(){
		try {
			this.listaSICondominios = this.condominioMB.get().buscarListaCondominiosAtivos();
		} catch (Exception e) {
			logger.error("", e);	
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		}
	}
	
	public String salvarBeneficiario(){
		try {
			this.beneficiarioService.salvar(this.beneficiario);
			this.pesquisar();			
			ManagedBeanUtil.setMensagemInfo("msg.beneficiario.salvaSucesso");
		} catch (SQLException e) {
			logger.error("erro sqlstate "+e.getSQLState(), e);	
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		} catch (Exception e) {
			logger.error("", e);	
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		}		
		return "salvar";
	}
	
	public String atualizarBeneficiario(){
		try {
			this.beneficiarioService.atualizar(this.beneficiario);
			this.pesquisar();			
			ManagedBeanUtil.setMensagemInfo("msg.beneficiario.atualizarSucesso");
		} catch (SQLException e) {
			logger.error("erro sqlstate "+e.getSQLState(), e);	
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		} catch (Exception e) {
			logger.error("", e);	
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		}		
		return "atualizar";
	}
	
	public void pesquisar(){
		try {
			this.listaBeneficiarios = new ListDataModel<Beneficiario>(this.beneficiarioService.buscarPorIdCondominio(this.beneficiario.getIdCondominio()));
			if(this.listaBeneficiarios.getRowCount() == 0){
				ManagedBeanUtil.setMensagemInfo("msg.beneficiario.semBeneficiarios");				
			}
		} catch (SQLException e) {
			logger.error("erro sqlstate "+e.getSQLState(), e);	
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		} catch (Exception e) {
			logger.error("", e);	
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		}
	}
	
	public String voltaListaBeneficiario(){
		return "voltar";
	}
	
	public String visualizaBeneficiario(){
		this.beneficiario = this.listaBeneficiarios.getRowData();
	try{	
		this.nomeCondominio = this.condominioService.get().buscarPorId(this.beneficiario.getIdCondominio()).getNome();
	} catch (SQLException e) {
		logger.error("erro sqlstate "+e.getSQLState(), e);	
		ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
	} catch (Exception e) {
		logger.error("", e);	
		ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
	}		
		return "visualizar";
	}
	
	public String cadastroBeneficiario(){
		return "cadastrar";
	}	
	              
	public String editaBeneficiario(){
		this.beneficiario = this.listaBeneficiarios.getRowData();
		return "editar";
	}
	

	public ListDataModel<Beneficiario> getListaBeneficiarios() {
		return listaBeneficiarios;
	}

	public void setListaBeneficiarios(ListDataModel<Beneficiario> listaBeneficiarios) {
		this.listaBeneficiarios = listaBeneficiarios;
	}

	public List<SelectItem> getListaSICondominios() {
		return listaSICondominios;
	}

	public void setListaSICondominios(List<SelectItem> listaSICondominios) {
		this.listaSICondominios = listaSICondominios;
	}

	public Beneficiario getBeneficiario() {
		return beneficiario;
	}

	public void setBeneficiario(Beneficiario beneficiario) {
		this.beneficiario = beneficiario;
	}

	public String getNomeCondominio() {
		return nomeCondominio;
	}

	public void setNomeCondominio(String nomeCondominio) {
		this.nomeCondominio = nomeCondominio;
	}	
	

}
