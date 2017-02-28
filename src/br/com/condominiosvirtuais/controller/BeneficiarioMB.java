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

import br.com.condominiosvirtuais.entity.Beneficiario;
import br.com.condominiosvirtuais.enumeration.BeneficiarioSituacaoEnum;
import br.com.condominiosvirtuais.exception.BusinessException;
import br.com.condominiosvirtuais.service.BeneficiarioService;
import br.com.condominiosvirtuais.service.CondominioService;
import br.com.condominiosvirtuais.util.AplicacaoUtil;
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
	
	private Integer situacaoBeneficiario;
	
	private List<SelectItem> listaSISituacao = null;
	
	public BeneficiarioMB(){
		this.beneficiario = new Beneficiario();
	}
	
	@PostConstruct
	public void iniciarBeneficiarioMB(){
		try {
			this.listaSICondominios = this.condominioMB.get().buscarListaCondominiosAtivos();
			this.popularListaSISituacao();		
		} catch (Exception e) {
			logger.error("", e);	
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		}
	}
	
	public String salvarBeneficiario(){
		try {
			this.beneficiario.setSituacao(Boolean.TRUE);
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
			this.beneficiario.setSituacao(this.situacaoBeneficiario == BeneficiarioSituacaoEnum.ATIVO.getSituacao() ? Boolean.TRUE : Boolean.FALSE);
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
			if(this.situacaoBeneficiario == -1){
				this.listaBeneficiarios = new ListDataModel<Beneficiario>(this.beneficiarioService.buscarPorIdCondominio(this.beneficiario.getIdCondominio()));				
			}else if (this.situacaoBeneficiario == BeneficiarioSituacaoEnum.ATIVO.getSituacao()) {
				this.listaBeneficiarios = new ListDataModel<Beneficiario>(this.beneficiarioService.buscarPorIdCondominioESituacao(this.beneficiario.getIdCondominio(),Boolean.TRUE));
			}else{
				this.listaBeneficiarios = new ListDataModel<Beneficiario>(this.beneficiarioService.buscarPorIdCondominioESituacao(this.beneficiario.getIdCondominio(),Boolean.FALSE));				
			}
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
	
	public String excluirBeneficiario(){
		try {
			this.beneficiarioService.excluir(this.beneficiario);
			this.pesquisar();
			ManagedBeanUtil.setMensagemInfo("msg.beneficiario.excluirSucesso");
		} catch (SQLException e) {
			logger.error("erro sqlstate "+e.getSQLState(), e);	
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		} catch (BusinessException e) {				
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage());
			return null;			
		} catch (Exception e) {
			logger.error("", e);	
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		}
		return "excluir";
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
	
	private void popularListaSISituacao() throws SQLException, Exception{
		this.listaSISituacao = new ArrayList<SelectItem>();
		this.listaSISituacao.add(new SelectItem(BeneficiarioSituacaoEnum.ATIVO.getSituacao(), AplicacaoUtil.i18n("beneficiario.situacao.1")));
		this.listaSISituacao.add(new SelectItem(BeneficiarioSituacaoEnum.INATIVO.getSituacao(), AplicacaoUtil.i18n("beneficiario.situacao.0")));
	}
	
	public String cadastroBeneficiario(){
		return "cadastrar";
	}	
	              
	public String editaBeneficiario(){
		this.beneficiario = this.listaBeneficiarios.getRowData();
		this.situacaoBeneficiario = this.beneficiario.getSituacao() == Boolean.TRUE ? BeneficiarioSituacaoEnum.ATIVO.getSituacao() : BeneficiarioSituacaoEnum.INATIVO.getSituacao();
		return "editar";
	}
	
	public String cancelarBeneficiario(){
		return "cancelar";
	}	
	
	public void limparBeneficiario(){
		this.beneficiario = new Beneficiario();
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

	public Integer getSituacaoBeneficiario() {
		return situacaoBeneficiario;
	}

	public void setSituacaoBeneficiario(Integer situacaoBeneficiario) {
		this.situacaoBeneficiario = situacaoBeneficiario;
	}

	public List<SelectItem> getListaSISituacao() {
		return listaSISituacao;
	}

	public void setListaSISituacao(List<SelectItem> listaSISituacao) {
		this.listaSISituacao = listaSISituacao;
	}	
	
	
	

}
