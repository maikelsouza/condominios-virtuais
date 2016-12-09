package br.com.condominiosvirtuais.controller;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Instance;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.log4j.Logger;

import br.com.condominiosvirtuais.entity.Ambiente;
import br.com.condominiosvirtuais.entity.Despesa;
import br.com.condominiosvirtuais.entity.Receita;
import br.com.condominiosvirtuais.service.DespesaService;
import br.com.condominiosvirtuais.service.ReceitaService;
import br.com.condominiosvirtuais.util.ManagedBeanUtil;

@Named @SessionScoped 
public class ReceitaDespesaMB implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private static Logger logger = Logger.getLogger(ReceitaDespesaMB.class);
	
	@Inject
	private ReceitaService receitaService;
	
	@Inject
	private DespesaService despesaService;
	
	@Inject
	private Instance<CondominioMB> condominioMB = null;
	
	private static final Integer ANO_INICIAL = 2015;
	
	private List<SelectItem> listaSIMesReferencia = new ArrayList<SelectItem>();
	
	private List<SelectItem> listaSIAnoReferencia = new ArrayList<SelectItem>();
	
	private List<SelectItem> listaSICondominios = new ArrayList<SelectItem>();
	
	private Receita receita;
	
	private Despesa despesa;
	
	private Boolean ehReceita;
	
	private String nome;
	
	private Double valor;
	
	private Integer mesReferencia;
	
	private Integer anoReferencia;
	
	private Integer idCondominio;
	
	private ListDataModel<Receita> listarReceita = null;
	
	private ListDataModel<Despesa> listarDespesa = null;
	
	
	
	
	
	@PostConstruct
	public void iniciaReceitaDespesasMB(){	
		ManagedBeanUtil.popularSIMeses(listaSIMesReferencia);
		ManagedBeanUtil.popularSIAnos(listaSIAnoReferencia,ANO_INICIAL,Boolean.FALSE);				
	}
	
	public String salvarReceitaDespesa(){		
		try {
			Calendar mesAnoReferencia = GregorianCalendar.getInstance();
			mesAnoReferencia.set(Calendar.DAY_OF_MONTH,1);
			mesAnoReferencia.set(Calendar.MONTH,this.getMesReferencia());
			mesAnoReferencia.set(Calendar.YEAR,this.getAnoReferencia());
			if(this.ehReceita){
				this.receita = new Receita();
				this.receita.setValor(this.valor);
				this.receita.setNome(this.nome);
				this.receita.setMesAnoReferencia(mesAnoReferencia.getTime());
				this.receita.setIdCondominio(this.idCondominio);
				this.receitaService.salvarReceita(this.receita);
			}else{
				this.despesa = new Despesa();
				this.despesa.setValor(this.valor);
				this.despesa.setNome(this.nome);
				this.despesa.setMesAnoReferencia(mesAnoReferencia.getTime());
				this.despesa.setIdCondominio(this.idCondominio);
				this.despesaService.salvarDespesa(this.despesa);
				this.despesa = new Despesa();
			}	
			this.limparTelaCadastroReceitaDespesa();
			ManagedBeanUtil.setMensagemInfo("msg.receitaDespesa.salvoSucesso");			
		} catch (SQLException e) {
			logger.error("erro sqlstate "+e.getSQLState(), e);	
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");	
		} catch (Exception e) {
			logger.error("", e);
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		}
		return "salvar";
	}	

	
	public void pesquisarReceitaDespesa(){		
		try {
			Calendar mesAnoReferencia = GregorianCalendar.getInstance();
			mesAnoReferencia.set(Calendar.DAY_OF_MONTH,1);
			mesAnoReferencia.set(Calendar.MONTH,this.getMesReferencia());
			mesAnoReferencia.set(Calendar.YEAR,this.getAnoReferencia());
			this.listarReceita = new ListDataModel<Receita>(this.receitaService.pesquisarPorMesAnoReferenciaEIdCondominio(mesAnoReferencia.getTime(), idCondominio));
			this.listarDespesa = new ListDataModel<Despesa>(this.despesaService.pesquisarPorMesAnoReferenciaEIdCondominio(mesAnoReferencia.getTime(), idCondominio));
			if (this.listarReceita.getRowCount() == 0 && this.listarDespesa.getRowCount() == 0){
				ManagedBeanUtil.setMensagemInfo("msg.receitaDespesa.semReceitaDespesa");
			}			
		} catch (SQLException e) {
			logger.error("erro sqlstate "+e.getSQLState(), e);	
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");	
		} catch (Exception e) {
			logger.error("", e);
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		}		
	}	

	
	public String cancelarCadastroReceitaDespesa(){
		return "cancelar";
	}
	
	public void limparCadastroReceitaDespesa(){
		this.limparTelaCadastroReceitaDespesa();
	}


	
	private void limparTelaCadastroReceitaDespesa(){
		this.despesa = new Despesa();
		this.receita = new Receita();
		this.valor = null;
		this.nome = null;
		this.mesReferencia = null;
		this.idCondominio = null;
	}

	public Receita getReceita() {
		return receita;
	}
	
	public void setReceita(Receita receita) {
		this.receita = receita;
	}
	
	public Despesa getDespesa() {
		return despesa;
	}
	
	public void setDespesa(Despesa despesa) {
		this.despesa = despesa;
	}

	public List<SelectItem> getListaSIMesReferencia() {
		return listaSIMesReferencia;
	}

	public void setListaSIMesReferencia(List<SelectItem> listaSIMesReferencia) {
		this.listaSIMesReferencia = listaSIMesReferencia;
	}

	public List<SelectItem> getListaSIAnoReferencia() {
		return listaSIAnoReferencia;
	}

	public void setListaSIAnoReferencia(List<SelectItem> listaSIAnoReferencia) {
		this.listaSIAnoReferencia = listaSIAnoReferencia;
	}

	public Boolean getEhReceita() {
		return ehReceita;
	}

	public void setEhReceita(Boolean ehReceita) {
		this.ehReceita = ehReceita;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	public Integer getMesReferencia() {
		return mesReferencia;
	}

	public void setMesReferencia(Integer mesReferencia) {
		this.mesReferencia = mesReferencia;
	}

	public Integer getAnoReferencia() {
		return anoReferencia;
	}

	public void setAnoReferencia(Integer anoReferencia) {
		this.anoReferencia = anoReferencia;
	}	
	
	
	public Integer getIdCondominio() {
		return idCondominio;
	}

	public void setIdCondominio(Integer idCondominio) {
		this.idCondominio = idCondominio;
	}

	public List<SelectItem> getListaSICondominios() {
		this.listaSICondominios = this.condominioMB.get().buscarListaCondominiosAtivos();
		return listaSICondominios;
	}

	public ListDataModel<Receita> getListarReceita() {
		return listarReceita;
	}

	public void setListarReceita(ListDataModel<Receita> listarReceita) {
		this.listarReceita = listarReceita;
	}

	public ListDataModel<Despesa> getListarDespesa() {
		return listarDespesa;
	}

	public void setListarDespesa(ListDataModel<Despesa> listarDespesa) {
		this.listarDespesa = listarDespesa;
	}	

}
