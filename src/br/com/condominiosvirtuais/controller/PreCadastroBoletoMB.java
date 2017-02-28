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
import br.com.condominiosvirtuais.entity.ContaBancaria;
import br.com.condominiosvirtuais.entity.PreCadastroBoleto;
import br.com.condominiosvirtuais.exception.BusinessException;
import br.com.condominiosvirtuais.service.BeneficiarioService;
import br.com.condominiosvirtuais.service.ContaBancariaService;
import br.com.condominiosvirtuais.service.PreCadastroBoletoService;
import br.com.condominiosvirtuais.util.ManagedBeanUtil;

@Named @SessionScoped
public class PreCadastroBoletoMB implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private static Logger logger = Logger.getLogger(PreCadastroBoletoMB.class);
	
	private PreCadastroBoleto preCadastroBoleto;
	
	private List<SelectItem> listaSICondominios;
	
	private List<SelectItem> listaSIContasBancarias;
	
	private List<SelectItem> listaSIBeneficiarios;
	
	private List<SelectItem> listaSIDiaMesVencimento;
	
	private List<SelectItem> listaSITitulos;	
	
	private ListDataModel<PreCadastroBoleto> listaPreCadastroBoletos;
	
	@Inject
	private Instance<CondominioMB> condominioMB; 	
	
	@Inject
	private ContaBancariaService contaBancariaService;
	
	@Inject
	private BeneficiarioService beneficiarioService;

	@Inject
	private PreCadastroBoletoService preCadastroBoletoService;                         
	
	
	public PreCadastroBoletoMB(){
		this.preCadastroBoleto = new PreCadastroBoleto();
	}
	
	@PostConstruct
	public void iniciarPreCadastroBoletoMB(){
		this.listaSICondominios = this.condominioMB.get().buscarListaCondominiosAtivos();
		this.popularDiaMesVencimento();
		this.popularTitulos();		
	}
	
	public String salvar(){
		try{			
			this.preCadastroBoletoService.salvar(this.preCadastroBoleto);
			this.pesquisar();
			ManagedBeanUtil.setMensagemInfo("msg.preCadastroBoleto.salvoSucesso");
		} catch (SQLException e) {
			logger.error("erro sqlstate "+e.getSQLState(), e);	
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		} catch (BusinessException e) {
			logger.error("", e);
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		} catch (Exception e) {
			logger.error("", e);
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		}
		return "salvar";
	}
	
	public String atualizar(){
		try{
			this.preCadastroBoletoService.atualizar(this.preCadastroBoleto);
			this.pesquisar();
			ManagedBeanUtil.setMensagemInfo("msg.preCadastroBoleto.atualizadoSucesso");
		} catch (SQLException e) {
			logger.error("erro sqlstate "+e.getSQLState(), e);	
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		} catch (BusinessException e) {
			logger.error("", e);
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		} catch (Exception e) {
			logger.error("", e);
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		}
		return "atualizar";
	}
	
	public String excluir(){
		try{
			this.preCadastroBoletoService.excluirPorId(this.preCadastroBoleto.getId());
			this.pesquisar();
			ManagedBeanUtil.setMensagemInfo("msg.preCadastroBoleto.excluidoSucesso");
		} catch (SQLException e) {
			logger.error("erro sqlstate "+e.getSQLState(), e);	
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		} catch (BusinessException e) {
			logger.error("", e);
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		} catch (Exception e) {
			logger.error("", e);
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		}
		return "excluir";
	}
	
	public void pesquisar(){
		try{
			this.listaPreCadastroBoletos = new ListDataModel<PreCadastroBoleto>(this.preCadastroBoletoService.buscarPorIdCondominio(this.preCadastroBoleto.getIdCondominio()));
			if (this.listaPreCadastroBoletos.getRowCount() == 0){
				 ManagedBeanUtil.setMensagemInfo("msg.preCadastroBoleto.semPreCadastroBoletos");
			}
		} catch (SQLException e) {
			logger.error("erro sqlstate "+e.getSQLState(), e);	
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		} catch (BusinessException e) {
			logger.error("", e);
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		} catch (Exception e) {
			logger.error("", e);
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		}
	}
	
	public void popularBeneficariosEContasBancarias(){
		try{
			this.popularBeneficiarios();
			this.popularContasBancarias();
		} catch (SQLException e) {
			logger.error("erro sqlstate "+e.getSQLState(), e);	
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		} catch (BusinessException e) {
			logger.error("", e);
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		} catch (Exception e) {
			logger.error("", e);
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		}
	}
	
	public String cadastroPreCadastroBoleto(){
		return "cadastrar";
	}
	
	public String cancelarCadastroPreCadastroBoleto(){
		return "cancelar";
	}
	
	public String cancelarEditaPreCadastroBoleto(){
		return "cancelar";
	}
	
	public void limparFiltroPreCadastroBoleto(){
		this.preCadastroBoleto = new PreCadastroBoleto();
		this.listaPreCadastroBoletos = new ListDataModel<PreCadastroBoleto>();		
	}
	
	public void limparCadastroPreCadastroBoleto(){
		this.preCadastroBoleto = new PreCadastroBoleto();
	}
	
	public String editarPreCadastroBoleto(){
		this.preCadastroBoleto = this.listaPreCadastroBoletos.getRowData();
		this.popularBeneficariosEContasBancarias();
		return "editar";
	}
	
	public String voltarListaPreCadastroBoleto(){
		return "voltar";
	}
	
	public String visualizarPreCadastroBoleto(){
		this.preCadastroBoleto = this.listaPreCadastroBoletos.getRowData();
		return "visualizar";
	}
	private void popularDiaMesVencimento(){
		this.listaSIDiaMesVencimento = new ArrayList<SelectItem>();
		for (int i = 1; i < 32; i++) {
			this.listaSIDiaMesVencimento.add(new SelectItem(i, String.valueOf(i)));	
		}
	}
	
	private void popularBeneficiarios() throws SQLException, BusinessException, Exception{		
		List<Beneficiario> listaBeneficiarios = this.beneficiarioService.buscarPorIdCondominioESituacao(this.preCadastroBoleto.getIdCondominio(),Boolean.TRUE);
		this.listaSIBeneficiarios = new ArrayList<SelectItem>();
		for (Beneficiario beneficiario : listaBeneficiarios) {
			this.listaSIBeneficiarios.add(new SelectItem(beneficiario.getId(), beneficiario.getNome()));
		}			
		
	}
	
	private void popularContasBancarias() throws SQLException, Exception{
		List<ContaBancaria> listaContaBancaria = this.contaBancariaService.buscarPorIdCondominioESituacao(this.preCadastroBoleto.getIdCondominio(),Boolean.TRUE);
		this.listaSIContasBancarias = new ArrayList<SelectItem>();
		for (ContaBancaria contaBancaria : listaContaBancaria) {
			this.listaSIContasBancarias.add(new SelectItem(contaBancaria.getId(), contaBancaria.getAgencia() + " " + contaBancaria.getNumero()));
		}			
		
	}
	
	private void popularTitulos(){
		String[] titulos = {"AP", "CC", "CH", "CPR", "DAE", "DAM", "DAU", "DD", "DM", "DMI", "DR", "DS", "DSI", "EC", "FAT", "LC", "ME", "NCC", "NCE", "NCI", "NCR", "ND", "NF", "NP", "NPR", "NS", "O", "PC", "RC", "TM", "TS", "W"};
		this.listaSITitulos = new ArrayList<SelectItem>();
		for (String string : titulos) {
			this.listaSITitulos.add(new SelectItem(string, string));			
		}
	}
	
	public PreCadastroBoleto getPreCadastroBoleto() {
		return preCadastroBoleto;
	}

	public void setPreCadastroBoleto(PreCadastroBoleto preCadastroBoleto) {
		this.preCadastroBoleto = preCadastroBoleto;
	}

	public List<SelectItem> getListaSICondominios() {
		return listaSICondominios;
	}

	public void setListaSICondominios(List<SelectItem> listaSICondominios) {
		this.listaSICondominios = listaSICondominios;
	}

	public List<SelectItem> getListaSIContasBancarias() {
		return listaSIContasBancarias;
	}

	public void setListaSIContasBancarias(List<SelectItem> listaSIContasBancarias) {
		this.listaSIContasBancarias = listaSIContasBancarias;
	}

	public List<SelectItem> getListaSIBeneficiarios() {
		return listaSIBeneficiarios;
	}

	public void setListaSIBeneficiarios(List<SelectItem> listaSIBeneficiarios) {
		this.listaSIBeneficiarios = listaSIBeneficiarios;
	}

	public List<SelectItem> getListaSIDiaMesVencimento() {
		return listaSIDiaMesVencimento;
	}

	public void setListaSIDiaMesVencimento(List<SelectItem> listaSIDiaMesVencimento) {
		this.listaSIDiaMesVencimento = listaSIDiaMesVencimento;
	}
	
	public List<SelectItem> getListaSITitulos() {
		return listaSITitulos;
	}

	public void setListaSITitulos(List<SelectItem> listaSITitulos) {
		this.listaSITitulos = listaSITitulos;
	}

	public ListDataModel<PreCadastroBoleto> getListaPreCadastroBoletos() {
		return listaPreCadastroBoletos;
	}

	public void setListaPreCadastroBoletos(ListDataModel<PreCadastroBoleto> listaPreCadastroBoletos) {
		this.listaPreCadastroBoletos = listaPreCadastroBoletos;
	}
	
	

}
