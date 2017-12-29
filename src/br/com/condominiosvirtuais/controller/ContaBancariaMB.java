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

import br.com.condominiosvirtuais.entity.Banco;
import br.com.condominiosvirtuais.entity.ContaBancaria;
import br.com.condominiosvirtuais.entity.TipoTitulo;
import br.com.condominiosvirtuais.enumeration.BancoSituacaoEnum;
import br.com.condominiosvirtuais.enumeration.ContaBancariaSituacaoEnum;
import br.com.condominiosvirtuais.enumeration.TipoTituloSituacaoEnum;
import br.com.condominiosvirtuais.exception.BusinessException;
import br.com.condominiosvirtuais.service.BancoService;
import br.com.condominiosvirtuais.service.CondominioService;
import br.com.condominiosvirtuais.service.ContaBancariaService;
import br.com.condominiosvirtuais.service.TipoTituloService;
import br.com.condominiosvirtuais.util.AplicacaoUtil;
import br.com.condominiosvirtuais.util.ManagedBeanUtil;

@Named @SessionScoped
public class ContaBancariaMB implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private static Logger logger = Logger.getLogger(ContaBancariaMB.class);
	
	@Inject
	private BancoService bancoService;
	
	@Inject
	private Instance<CondominioMB> condominioMB; 
	
	@Inject
	private ContaBancariaService contaBancariaService;
	
	@Inject
	private CondominioService condominioService;
	
	@Inject
	private TipoTituloService tipoTituloService;
	
	private List<SelectItem> listaSIBancos = null;
	
	private List<SelectItem> listaSISituacao = null;
	
	private List<SelectItem> listaSICondominios = null;
	
	private List<SelectItem> listaSITipoTitulo = null;	
	
	private ContaBancaria contaBancaria = null;
	
	private ListDataModel<ContaBancaria> listaContasBancarias;
	
	private String nomeCondominio;
	
	private Integer situacaoContaBancaria = -1;
	
	
	
	public ContaBancariaMB(){
		this.contaBancaria = new ContaBancaria();		
	}
	
	@PostConstruct
	public void iniciarContaBancariaMB(){
		try {
			this.listaSICondominios = this.condominioMB.get().buscarListaCondominiosAtivos();
			this.popularListaSiBancos();
			this.popularListaSiSituacao();		
			this.popularListaSiTipoTitulo();
		} catch (SQLException e) {
			logger.error("erro sqlstate "+e.getSQLState(), e);	
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		} catch (Exception e) {
			logger.error("", e);	
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		}
	}
	
	public String salvar(){
		try {			
			this.contaBancaria.setSituacao(Boolean.TRUE);
			this.contaBancariaService.salvar(this.contaBancaria);
			this.contaBancaria = new ContaBancaria();			
			this.pesquisar();			
			ManagedBeanUtil.setMensagemInfo("msg.contaBancaria.salvaSucesso");
		} catch (SQLException e) {
			logger.error("erro sqlstate "+e.getSQLState(), e);	
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		} catch (Exception e) {
			logger.error("", e);	
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		}
		
		return "salvar";
	}
	
	public void pesquisar(){
		try {
			if(this.situacaoContaBancaria == -1){
				this.listaContasBancarias = new ListDataModel<ContaBancaria>(this.contaBancariaService.buscarPorIdCondominio(this.contaBancaria.getIdCondominio()));				
			}else if (this.situacaoContaBancaria == ContaBancariaSituacaoEnum.ATIVA.getSituacao()) {
				this.listaContasBancarias = new ListDataModel<ContaBancaria>(this.contaBancariaService.buscarPorIdCondominioESituacao(this.contaBancaria.getIdCondominio(),Boolean.TRUE));
			}else{
				this.listaContasBancarias = new ListDataModel<ContaBancaria>(this.contaBancariaService.buscarPorIdCondominioESituacao(this.contaBancaria.getIdCondominio(),Boolean.FALSE));				
			}
			if (this.listaContasBancarias.getRowCount() == 0){
				ManagedBeanUtil.setMensagemInfo("msg.contaBancaria.semContasBancarias");
			}			
		} catch (SQLException e) {
			logger.error("erro sqlstate "+e.getSQLState(), e);	
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		} catch (Exception e) {
			logger.error("", e);	
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		}		
	}
	
	public String visualizarContaBancaria(){
		this.contaBancaria = this.listaContasBancarias.getRowData();
		try {
			this.nomeCondominio = this.condominioService.buscarPorId(this.contaBancaria.getIdCondominio()).getNome();
		} catch (SQLException e) {
			logger.error("erro sqlstate "+e.getSQLState(), e);	
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		} catch (Exception e) {
			logger.error("", e);	
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		}		
		return "visualizar";
	}
	
	public String atualizar(){
		try {
			this.contaBancaria.setSituacao(this.situacaoContaBancaria == ContaBancariaSituacaoEnum.ATIVA.getSituacao() ? Boolean.TRUE : Boolean.FALSE);		
			this.contaBancariaService.atualizar(this.contaBancaria);
			this.pesquisar();
			ManagedBeanUtil.setMensagemInfo("msg.contaBancaria.atualizarSucesso");
		} catch (SQLException e) {
			logger.error("erro sqlstate "+e.getSQLState(), e);	
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		} catch (Exception e) {
			logger.error("", e);	
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		}
		
		return "atualizar";
	}
	
	public String excluir(){
		try {
			this.contaBancariaService.excluir(this.contaBancaria);
			this.pesquisar();
			ManagedBeanUtil.setMensagemInfo("msg.contaBancaria.excluirSucesso");
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
	
	public String editaContaBancaria(){
		this.contaBancaria = this.listaContasBancarias.getRowData();
		this.situacaoContaBancaria = this.contaBancaria.getSituacao() == Boolean.TRUE ? ContaBancariaSituacaoEnum.ATIVA.getSituacao() : ContaBancariaSituacaoEnum.INATIVA.getSituacao(); 
		return "editar";
	}
	
	public String cancelarContaBancaria(){
		return "cancelar";
	}
	
	public String voltarContaBancaria(){
		return "voltar";
	}
	
	public String cancelarSalvarContaBancaria(){
		return "cancelar";
	}
	
	public void limparSalvarContaBancaria(){
		this.contaBancaria = new ContaBancaria();
	}
	
	public String cadastroContaBancaria(){
		return "cadastrar";
	}
	
	public void limparFiltroContaBancaria(){
		this.contaBancaria = new ContaBancaria();
		this.listaContasBancarias = new ListDataModel<ContaBancaria>();
	}
	

	private void popularListaSiBancos() throws SQLException, Exception{
		this.listaSIBancos = new ArrayList<SelectItem>();
		List<Banco> listaBanco = this.bancoService.buscarTodosPorSituacao(BancoSituacaoEnum.ATIVO.getSituacao());		
		for (Banco banco : listaBanco) {
			this.listaSIBancos.add(new SelectItem(banco.getId(), banco.getNome()));
			
		}
	}
	
	private void popularListaSiSituacao() throws SQLException, Exception{
		this.listaSISituacao = new ArrayList<SelectItem>();
		this.listaSISituacao.add(new SelectItem(ContaBancariaSituacaoEnum.ATIVA.getSituacao(), AplicacaoUtil.i18n("contaBancaria.situacao.1")));
		this.listaSISituacao.add(new SelectItem(ContaBancariaSituacaoEnum.INATIVA.getSituacao(), AplicacaoUtil.i18n("contaBancaria.situacao.0")));
	}
	
	private void popularListaSiTipoTitulo() throws SQLException, Exception{
		this.listaSITipoTitulo = new ArrayList<SelectItem>();		
		List<TipoTitulo> listaTipoTitulo = tipoTituloService.buscarPorSituacao(TipoTituloSituacaoEnum.ATIVO.getSituacao());
		for (TipoTitulo tipoTitulo : listaTipoTitulo) {
			this.listaSITipoTitulo.add(new SelectItem(tipoTitulo.getId(), tipoTitulo.getSigla() + " - " +tipoTitulo.getNome()));
		}
		
	}
	
	

	public List<SelectItem> getListaSIBancos() {
		return listaSIBancos;
	}

	public void setListaSIBancos(List<SelectItem> listaSIBancos) {
		this.listaSIBancos = listaSIBancos;
	}

	public List<SelectItem> getListaSICondominios() {
		return listaSICondominios;
	}

	public void setListaSICondominios(List<SelectItem> listaSICondominios) {
		this.listaSICondominios = listaSICondominios;
	}

	public ContaBancaria getContaBancaria() {
		return contaBancaria;
	}

	public void setContaBancaria(ContaBancaria contaBancaria) {
		this.contaBancaria = contaBancaria;
	}

	public ListDataModel<ContaBancaria> getListaContasBancarias() {
		return listaContasBancarias;
	}

	public void setListaContasBancarias(ListDataModel<ContaBancaria> listaContasBancarias) {
		this.listaContasBancarias = listaContasBancarias;
	}

	public String getNomeCondominio() {
		return nomeCondominio;
	}

	public void setNomeCondominio(String nomeCondominio) {
		this.nomeCondominio = nomeCondominio;
	}

	public List<SelectItem> getListaSISituacao() {
		return listaSISituacao;
	}

	public void setListaSISituacao(List<SelectItem> listaSISituacao) {
		this.listaSISituacao = listaSISituacao;
	}

	public Integer getSituacaoContaBancaria() {
		return situacaoContaBancaria;
	}

	public void setSituacaoContaBancaria(Integer situacaoContaBancaria) {
		this.situacaoContaBancaria = situacaoContaBancaria;
	}

	public List<SelectItem> getListaSITipoTitulo() {
		return listaSITipoTitulo;
	}

	public void setListaSITipoTitulo(List<SelectItem> listaSITipoTitulo) {
		this.listaSITipoTitulo = listaSITipoTitulo;
	}
	
	

}
