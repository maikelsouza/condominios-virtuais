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
import br.com.condominiosvirtuais.service.BancoService;
import br.com.condominiosvirtuais.service.CondominioService;
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
	
	@Inject
	private CondominioService condominioService;
	
	private List<SelectItem> listaSIBancos = null;
	
	private List<SelectItem> listaSICondominios = null;	
	
	private ContaBancaria contaBancaria = null;
	
	private ListDataModel<ContaBancaria> listaContasBancarias;
	
	private String nomeCondominio;
	
	
	
	public ContaBancariaMB(){
		this.contaBancaria = new ContaBancaria();		
	}
	
	@PostConstruct
	public void iniciarContaBancariaMB(){
		try {
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
			this.listaContasBancarias = new ListDataModel<ContaBancaria>(this.contaBancariaService.buscarPorIdCondominio(this.contaBancaria.getIdCondominio()));
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
		
		return "salvar";
	}
	
	public String excluir(){
		try {
			this.contaBancariaService.excluir(this.contaBancaria);
			this.pesquisar();
			ManagedBeanUtil.setMensagemInfo("msg.contaBancaria.excluirSucesso");
		} catch (SQLException e) {
			logger.error("erro sqlstate "+e.getSQLState(), e);	
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		} catch (Exception e) {
			logger.error("", e);	
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		}
		
		return "salvar";
	}
	
	public String editaContaBancaria(){
		this.contaBancaria = this.listaContasBancarias.getRowData();
		return "editar";
	}
	
	public String voltarVisualizarContaBancaria(){
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
	

}
