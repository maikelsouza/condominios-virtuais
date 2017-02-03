package br.com.condominiosvirtuais.controller;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Instance;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.log4j.Logger;

import br.com.condominiosvirtuais.entity.Banco;
import br.com.condominiosvirtuais.entity.ContaBancaria;
import br.com.condominiosvirtuais.service.BancoService;
import br.com.condominiosvirtuais.service.ContaBancariaService;
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
	
	private List<SelectItem> listaSIBancos = null;
	
	private List<SelectItem> listaSICondominios = null;	
	
	private ContaBancaria contaBancaria = null;
	
	
	
	@PostConstruct
	public void iniciarContaBancariaMB(){
		try {
			this.contaBancaria = new ContaBancaria();
			this.listaSICondominios = this.condominioMB.get().buscarListaCondominiosAtivos();
			this.popularListaSiBancos();
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
			this.contaBancariaService.salvar(this.contaBancaria);
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
	

	private void popularListaSiBancos() throws SQLException, Exception{
		this.listaSIBancos = new ArrayList<SelectItem>();
		List<Banco> listaBanco = this.bancoService.buscarTodos();		
		for (Banco banco : listaBanco) {
			this.listaSIBancos.add(new SelectItem(banco.getId(), banco.getNome()));
			
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
	
	
	
	
	
	
	
	
	
	
	

}
