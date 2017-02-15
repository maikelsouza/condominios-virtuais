package br.com.condominiosvirtuais.controller;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Instance;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.log4j.Logger;

import br.com.condominiosvirtuais.entity.Beneficiario;
import br.com.condominiosvirtuais.entity.Boleto;
import br.com.condominiosvirtuais.entity.ContaBancaria;
import br.com.condominiosvirtuais.exception.BusinessException;
import br.com.condominiosvirtuais.service.BeneficiarioService;
import br.com.condominiosvirtuais.service.ContaBancariaService;
import br.com.condominiosvirtuais.util.ManagedBeanUtil;

@Named @SessionScoped
public class BoletoMB  implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private static Logger logger = Logger.getLogger(BoletoMB.class);
	
	@Inject
	private ContaBancariaService contaBancariaService;
	
	@Inject
	private BeneficiarioService beneficiarioService;
	
	@Inject
	private Instance<CondominioMB> condominioMB; 	
	
	private List<SelectItem> listaSICondominios;
	
	private List<SelectItem> listaSIContasBancarias;
	
	private List<SelectItem> listaSIBeneficiarios;
	
	private List<SelectItem> listaSIPagadores;
	
	private Boleto boleto;
	
	public BoletoMB(){
		this.boleto = new Boleto();
		this.boleto.setEmissao(new Date());
	}
	
	@PostConstruct
	public void iniciarBoletoMB(){
		this.listaSICondominios = this.condominioMB.get().buscarListaCondominiosAtivos();
	}
	
	
	
	public void buscarBeneficiarios(){
		try {
			List<Beneficiario> listaBeneficiarios = this.beneficiarioService.buscarPorIdCondominio(this.boleto.getIdCondominio());
			for (Beneficiario beneficiario : listaBeneficiarios) {
				this.listaSIBeneficiarios.add(new SelectItem(beneficiario.getId(), beneficiario.getNome()));
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
	
	public void buscarContasBancarias(){
		try {
			List<ContaBancaria> listaContaBancaria = this.contaBancariaService.buscarPorIdCondominio(this.boleto.getIdCondominio());
			for (ContaBancaria contaBancaria : listaContaBancaria) {
				this.listaSIBeneficiarios.add(new SelectItem(contaBancaria.getId(), contaBancaria.getAgencia() + " " + contaBancaria.getNumero()));
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
	
	public void buscarPagador(){
		
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

	public List<SelectItem> getListaSIPagadores() {
		return listaSIPagadores;
	}

	public void setListaSIPagadores(List<SelectItem> listaSIPagadores) {
		this.listaSIPagadores = listaSIPagadores;
	}
	
	
	
	

}
