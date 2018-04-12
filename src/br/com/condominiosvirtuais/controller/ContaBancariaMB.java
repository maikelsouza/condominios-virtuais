package br.com.condominiosvirtuais.controller;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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
import br.com.condominiosvirtuais.util.CalculoUtil;
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
	
	private String multaConfiguracaoPadraoContaBancaria = "0,0000";
	
	private String jurosAoDiaConfiguracaoPadraoContaBancaria = "0,0000";
	
	private String descontoConfiguracaoPadraoContaBancaria = "0,0000";
	
	
	

	@PostConstruct
	public void iniciarContaBancariaMB(){
		try {
			this.contaBancaria = new ContaBancaria();
			this.listaSICondominios = this.condominioMB.get().buscarListaCondominiosAtivos();
			this.popularListaSiBancos();
			this.popularListaSiSituacao();		
			this.popularListaSiTipoTitulo();
			this.zerarCamposPercentuais();
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
			this.setarValoresPercentuaisNoBean();			
			this.contaBancariaService.salvar(this.contaBancaria);
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
			this.popularSiglaNomeTipoTitulo();
			this.setarValoresPercentuaisNaString();
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
			this.setarValoresPercentuaisNoBean();
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
		this.setarValoresPercentuaisNaString();
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
		this.contaBancaria = new ContaBancaria();
		this.zerarCamposPercentuais();
		return "cadastrar";
	}
	
	public void limparFiltroContaBancaria(){
		this.contaBancaria = new ContaBancaria();
		this.zerarCamposPercentuais();
		this.listaContasBancarias = new ListDataModel<ContaBancaria>();
	}
	
	public void zerarCamposPercentuais(){
		this.multaConfiguracaoPadraoContaBancaria = "0,0000";
		this.jurosAoDiaConfiguracaoPadraoContaBancaria = "0,0000";
		this.descontoConfiguracaoPadraoContaBancaria = "0,0000";
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
		List<TipoTitulo> listaTipoTitulo = tipoTituloService.buscarPorSituacao(TipoTituloSituacaoEnum.ATIVO.getSituacao(), AplicacaoUtil.getLocale().toString());
		for (TipoTitulo tipoTitulo : listaTipoTitulo) {
			this.listaSITipoTitulo.add(new SelectItem(tipoTitulo.getId(), tipoTitulo.getSigla() + " - " +tipoTitulo.getNome()));
		}		
	}
	
		
	
	private void popularSiglaNomeTipoTitulo(){
		Boolean encontrou = Boolean.FALSE;
		SelectItem selectItem = null;
		Integer index = 0;
		while(!encontrou){
			selectItem  = this.listaSITipoTitulo.get(index++);
			if(this.contaBancaria.getConfiguracaoPadraoContaBancaria().getTipoTitulo().getId().intValue() == ((Integer) selectItem.getValue()).intValue()){
				this.contaBancaria.getConfiguracaoPadraoContaBancaria().setSiglaMaisNomeTipoTitulo(selectItem.getLabel());
				encontrou = Boolean.TRUE;
			}
		}		
	}
	
	private void setarValoresPercentuaisNoBean(){
		this.contaBancaria.getConfiguracaoPadraoContaBancaria().setMulta(CalculoUtil.setScala(this.multaConfiguracaoPadraoContaBancaria.replace(',', '.'), 4));
		this.contaBancaria.getConfiguracaoPadraoContaBancaria().setJurosAoDia(CalculoUtil.setScala(this.jurosAoDiaConfiguracaoPadraoContaBancaria.replace(',', '.'), 4));
		this.contaBancaria.getConfiguracaoPadraoContaBancaria().setDesconto(CalculoUtil.setScala(this.descontoConfiguracaoPadraoContaBancaria.replace(',', '.'), 4));
	}
	
	private void setarValoresPercentuaisNaString(){
		this.multaConfiguracaoPadraoContaBancaria = String.valueOf(this.contaBancaria.getConfiguracaoPadraoContaBancaria().getMulta()).replace('.',','); 
		this.jurosAoDiaConfiguracaoPadraoContaBancaria = String.valueOf(this.contaBancaria.getConfiguracaoPadraoContaBancaria().getJurosAoDia()).replace('.',','); 
		this.descontoConfiguracaoPadraoContaBancaria = String.valueOf(this.contaBancaria.getConfiguracaoPadraoContaBancaria().getDesconto()).replace('.',',');
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

	public String getMultaConfiguracaoPadraoContaBancaria() {
		return multaConfiguracaoPadraoContaBancaria;
	}

	public void setMultaConfiguracaoPadraoContaBancaria(String multaConfiguracaoPadraoContaBancaria) {
		this.multaConfiguracaoPadraoContaBancaria = multaConfiguracaoPadraoContaBancaria;
	}

	public String getJurosAoDiaConfiguracaoPadraoContaBancaria() {
		return jurosAoDiaConfiguracaoPadraoContaBancaria;
	}

	public void setJurosAoDiaConfiguracaoPadraoContaBancaria(String jurosAoDiaConfiguracaoPadraoContaBancaria) {
		this.jurosAoDiaConfiguracaoPadraoContaBancaria = jurosAoDiaConfiguracaoPadraoContaBancaria;
	}

	public String getDescontoConfiguracaoPadraoContaBancaria() {
		return descontoConfiguracaoPadraoContaBancaria;
	}

	public void setDescontoConfiguracaoPadraoContaBancaria(String descontoConfiguracaoPadraoContaBancaria) {
		this.descontoConfiguracaoPadraoContaBancaria = descontoConfiguracaoPadraoContaBancaria;
	}		

}
