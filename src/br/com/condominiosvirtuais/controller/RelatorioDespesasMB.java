package br.com.condominiosvirtuais.controller;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Instance;
import javax.faces.event.ActionEvent;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.log4j.Logger;

import br.com.condominiosvirtuais.entity.Bloco;
import br.com.condominiosvirtuais.entity.Condominio;
import br.com.condominiosvirtuais.entity.Despesas;
import br.com.condominiosvirtuais.enumeration.DespesasEnum;
import br.com.condominiosvirtuais.exception.BusinessException;
import br.com.condominiosvirtuais.reports.DespesasUnidade;
import br.com.condominiosvirtuais.reports.RelatorioDespesas;
import br.com.condominiosvirtuais.service.impl.DespesasServiceImpl;
import br.com.condominiosvirtuais.util.AplicacaoUtil;
import br.com.condominiosvirtuais.util.ManagedBeanUtil;

@Named @SessionScoped
public class RelatorioDespesasMB implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private static Logger logger = Logger.getLogger(RelatorioDespesasMB.class);
	
	private RelatorioDespesas relatorioDespesasUnidades = null;	
	
	private List<SelectItem> listaSIMesReferencia = new ArrayList<SelectItem>();
	
	private List<SelectItem> listaSIAnoReferencia = new ArrayList<SelectItem>();
	
	private List<SelectItem> listaSICondominios = new ArrayList<SelectItem>();
	
	private List<SelectItem> listaSIMenosGasTipos = new ArrayList<SelectItem>();
 
	private List<SelectItem> listaSITipos = new ArrayList<SelectItem>();
	
	private List<SelectItem> listaSIUnidades = new ArrayList<SelectItem>();	
	
	private List<SelectItem> listaSIBlocos = new ArrayList<SelectItem>();
	
	@Inject
	private DespesasServiceImpl despesasService;
	
	@Inject
	private Instance<CondominioMB> condominioMB = null;
	
	@Inject
	private Instance<UnidadeMB> unidadeMB = null;
	
	@Inject
	private Instance<BlocoMB> blocoMB = null;
	
	private Integer mesReferencia;
	
	private Integer anoReferencia;
	
	private Integer idCondominio;	
	
	private static final Integer ANO_INICIAL = 2010;
	
	private Despesas despesas = null;
	
	private Integer anoAtual  = null;
	
	private Bloco bloco = null;
	
	private Boolean exibirCampoValor;
	
	private Boolean exibirCampoConsumoGasMesAtual;
	
	
	
	@PostConstruct
	public void relatorioDespesasMB(){
		ManagedBeanUtil.popularSIMeses(listaSIMesReferencia);
		ManagedBeanUtil.popularSIAnos(listaSIAnoReferencia,ANO_INICIAL,Boolean.FALSE);
		this.popularSITiposDespesas();
		this.popularSITiposDespesasMenosGas();
		Calendar dataAtual = GregorianCalendar.getInstance();
		dataAtual.setTime(new Date());
		this.anoAtual = dataAtual.get(Calendar.YEAR);		
		this.exibirCampoConsumoGasMesAtual = Boolean.FALSE;
		this.exibirCampoValor = Boolean.TRUE;
		this.bloco = new Bloco();
		// FIXME: Como essa tela foi feita para o residencial das orquideas, então foi chumbado esse id. No entanto esse deve ser retirado para possibilitar todos os outros condomínios 
		this.bloco.setId(24);
	}
	
	
	public void pesquisarDespesasCondominio(){
		this.relatorioDespesasUnidades = new RelatorioDespesas();
		Calendar mesAnoReferencia = GregorianCalendar.getInstance();
		mesAnoReferencia.set(Calendar.DAY_OF_MONTH,1);
		mesAnoReferencia.set(Calendar.MONTH,this.getMesReferencia());
		mesAnoReferencia.set(Calendar.YEAR,this.getAnoReferencia());
		try 
		{	     
			this.relatorioDespesasUnidades.setListaDespesasCondominio(new ListDataModel<Despesas>(this.despesasService.buscarPorIdCondominioEMesAnoReferencia(this.idCondominio, mesAnoReferencia.getTime())));
			this.relatorioDespesasUnidades.setListaDespesasUnidades(new ListDataModel<DespesasUnidade>(this.despesasService.buscarDespesaUnidadePorIdCondominioEMesAnoReferencia(this.idCondominio, mesAnoReferencia.getTime())));
			if (this.relatorioDespesasUnidades.getListaDespesasCondominio().getRowCount() == 0 && 
					this.relatorioDespesasUnidades.getListaDespesasUnidades().getRowCount() == 0){
					ManagedBeanUtil.setMensagemInfo("msg.relatorioDespesas.semRelatorioDespesas");
			}		
		} catch (SQLException e) {
			logger.error("erro sqlstate "+e.getSQLState(), e);	
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		} catch (Exception e) {
			logger.error("", e);
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		}		
	}
	
	public void popularTotalDespesasCondominio(Integer idCondominio, Date mesAnoReferencia){
		try {
			this.relatorioDespesasUnidades = new RelatorioDespesas();
			this.relatorioDespesasUnidades.setListaDespesasCondominio(new ListDataModel<Despesas>(this.despesasService.buscarPorIdCondominioEMesAnoReferencia(idCondominio, mesAnoReferencia)));
		} catch (SQLException e) {
			logger.error("erro sqlstate "+e.getSQLState(), e);	
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		} catch (Exception e) {
			logger.error("", e);
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		}		
	}
	
	public String editarDespesaCondominio(){
		this.despesas = (Despesas) this.relatorioDespesasUnidades.getListaDespesasCondominio().getRowData();
		Calendar mesAnoReferencia = GregorianCalendar.getInstance();
		mesAnoReferencia.setTime(this.despesas.getMesAnoReferencia());
		this.mesReferencia = mesAnoReferencia.get(Calendar.MONTH);
		this.anoReferencia = mesAnoReferencia.get(Calendar.YEAR);	
		this.exibirCampoConsumoGasMesAtual = Boolean.FALSE;
		return "editar";
	}
	
	public String editarDespesaCondomino(){	
		this.despesas = (Despesas) this.relatorioDespesasUnidades.getListaDespesasUnidades().getRowData().getListaDespesas().getRowData();
		Calendar mesAnoReferencia = GregorianCalendar.getInstance();
		mesAnoReferencia.setTime(this.despesas.getMesAnoReferencia());
		this.mesReferencia = mesAnoReferencia.get(Calendar.MONTH);
		this.anoReferencia = mesAnoReferencia.get(Calendar.YEAR);
		this.exibirCampoConsumoGasMesAtual = Boolean.FALSE;
		this.exibirCampoValor = Boolean.TRUE;
		if(this.despesas.getTipo() == DespesasEnum.GAS.getDespesa()){
			this.exibirCampoConsumoGasMesAtual = Boolean.TRUE;			
		}	
		this.popularListaBlocos();
		this.popularListaUnidades();
		return "editar";
	}
	
	public String atualizarDespesaCondominio(){
		try {			
			Calendar mesAnoReferencia = GregorianCalendar.getInstance();
			mesAnoReferencia.set(Calendar.DAY_OF_MONTH,1);
			mesAnoReferencia.set(Calendar.MONTH,this.getMesReferencia());
			mesAnoReferencia.set(Calendar.YEAR,this.getAnoReferencia());
			this.despesas.setMesAnoReferencia(mesAnoReferencia.getTime());
			this.despesas.getCondominio().setId(this.idCondominio);	
			this.despesasService.atualizarDespesasCondominio(this.despesas);
			this.pesquisarDespesasCondominio();
			ManagedBeanUtil.setMensagemInfo("msg.relatorioDespesas.atualizadoSucesso");			
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
		return "atualizar";		
	}
	
	public String atualizarDespesaCondomino(){
		try {
			String mensagemDeErro = "";
			Calendar mesAnoReferencia = GregorianCalendar.getInstance();
			mesAnoReferencia.set(Calendar.DAY_OF_MONTH,1);
			mesAnoReferencia.set(Calendar.MONTH,this.getMesReferencia());
			mesAnoReferencia.set(Calendar.YEAR,this.getAnoReferencia());
			this.despesas.setMesAnoReferencia(mesAnoReferencia.getTime());	
			this.despesas.getCondominio().setId(this.idCondominio);	
			Despesas despesasGasMesAnterior = null;
			if (this.despesas.getTipo() == DespesasEnum.GAS.getDespesa()){
				mesAnoReferencia.add(Calendar.MONTH, -1);
				despesasGasMesAnterior = this.despesasService.buscarPorIdUnidadeEMesAnoReferenciaETipo(despesas.getUnidade().getId(), 
						mesAnoReferencia.getTime(), despesas.getTipo());
				if(despesasGasMesAnterior == null){
					mensagemDeErro = "msg.despesas.despesaCondominoConsumoMesAnteriorInexistente";
				}else if (this.despesas.getConsumoGasDespesas().getConsumoMesAtual() < 0.0){
					mensagemDeErro = "msg.despesas.despesaCondominoConsumoNegativo";			
				}else{
					this.despesasService.atualizarDespesasGasCondomino(this.despesas);			
				}
			}else{	
				this.despesasService.atualizarDespesasCondomino(this.despesas);			
			}
			this.pesquisarDespesasCondominio();
			if (!mensagemDeErro.isEmpty()){
				ManagedBeanUtil.setMensagemErro(mensagemDeErro);
			}else{
				ManagedBeanUtil.setMensagemInfo("msg.relatorioDespesas.atualizadoSucesso");
			}
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
		return "atualizar";		
	}	
	
	public void excluirDespesa(ActionEvent actionEvent){
		try {
			this.despesas = (Despesas) this.relatorioDespesasUnidades.getListaDespesasCondominio().getRowData();
			this.despesasService.excluir(despesas);
			this.pesquisarDespesasCondominio();
			ManagedBeanUtil.setMensagemInfo("msg.relatorioDespesas.excluirSucesso");		
		} catch (SQLException e) {
			logger.error("erro sqlstate "+e.getSQLState(), e);	
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		} catch (BusinessException e) {				
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage());	
		} catch (Exception e) {
			logger.error("", e);
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		}		
	}
	
	public void excluirDespesaCondomino(ActionEvent actionEvent){
		try {
			this.despesas = (Despesas) this.relatorioDespesasUnidades.getListaDespesasUnidades().getRowData().getListaDespesas().getRowData();
			this.despesasService.excluir(despesas);
			this.pesquisarDespesasCondominio();
			ManagedBeanUtil.setMensagemInfo("msg.relatorioDespesas.excluirSucesso");		
		} catch (SQLException e) {
			logger.error("erro sqlstate "+e.getSQLState(), e);	
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		} catch (BusinessException e) {				
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage());	
		} catch (Exception e) {
			logger.error("", e);
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		}		
	}
	
	public List<SelectItem> popularSITiposDespesas(){		
		this.listaSITipos.add(new SelectItem(DespesasEnum.ENERGIA.getDespesa(), AplicacaoUtil.i18n("despesas.tipo.label.0")));
		this.listaSITipos.add(new SelectItem(DespesasEnum.AGUA.getDespesa(), AplicacaoUtil.i18n("despesas.tipo.label.1")));
		this.listaSITipos.add(new SelectItem(DespesasEnum.FUNDO_DE_RESERVA.getDespesa(), AplicacaoUtil.i18n("despesas.tipo.label.2")));
		this.listaSITipos.add(new SelectItem(DespesasEnum.COTA_CAPITAL.getDespesa(), AplicacaoUtil.i18n("despesas.tipo.label.3")));
		this.listaSITipos.add(new SelectItem(DespesasEnum.FAXINA.getDespesa(), AplicacaoUtil.i18n("despesas.tipo.label.4")));
		this.listaSITipos.add(new SelectItem(DespesasEnum.GAS.getDespesa(), AplicacaoUtil.i18n("despesas.tipo.label.5")));
		this.listaSITipos.add(new SelectItem(DespesasEnum.RATEIO_CONDOMINIO.getDespesa(), AplicacaoUtil.i18n("despesas.tipo.label.6")));
		this.listaSITipos.add(new SelectItem(DespesasEnum.RATEIO_SINDICO.getDespesa(), AplicacaoUtil.i18n("despesas.tipo.label.7")));		
		return this.listaSITipos;
	}
	
	public List<SelectItem> popularSITiposDespesasMenosGas(){		
		this.listaSIMenosGasTipos.add(new SelectItem(DespesasEnum.ENERGIA.getDespesa(), AplicacaoUtil.i18n("despesas.tipo.label.0")));
		this.listaSIMenosGasTipos.add(new SelectItem(DespesasEnum.AGUA.getDespesa(), AplicacaoUtil.i18n("despesas.tipo.label.1")));
		this.listaSIMenosGasTipos.add(new SelectItem(DespesasEnum.FUNDO_DE_RESERVA.getDespesa(), AplicacaoUtil.i18n("despesas.tipo.label.2")));
		this.listaSIMenosGasTipos.add(new SelectItem(DespesasEnum.COTA_CAPITAL.getDespesa(), AplicacaoUtil.i18n("despesas.tipo.label.3")));
		this.listaSIMenosGasTipos.add(new SelectItem(DespesasEnum.FAXINA.getDespesa(), AplicacaoUtil.i18n("despesas.tipo.label.4")));		
		this.listaSIMenosGasTipos.add(new SelectItem(DespesasEnum.RATEIO_CONDOMINIO.getDespesa(), AplicacaoUtil.i18n("despesas.tipo.label.6")));
		this.listaSIMenosGasTipos.add(new SelectItem(DespesasEnum.RATEIO_SINDICO.getDespesa(), AplicacaoUtil.i18n("despesas.tipo.label.7")));		
		return this.listaSIMenosGasTipos;
	}
	
	public void limparFiltroDespesasCondominio(ActionEvent actionEvent){
		this.idCondominio = 0;
		this.mesReferencia = 0;
		this.anoReferencia = this.anoAtual;	
		this.relatorioDespesasUnidades = new RelatorioDespesas();
	}
	
	public void exibirCampoConsumoGasMesAtual(){
		this.exibirCampoConsumoGasMesAtual = Boolean.FALSE;	
		if(this.despesas.getTipo() == DespesasEnum.GAS.getDespesa()){
			this.exibirCampoConsumoGasMesAtual = Boolean.TRUE;
		}		
	}
	
	public void popularListaUnidades(){
		this.listaSIUnidades = this.unidadeMB.get().buscarListaSIUnidadesPorBloco(this.bloco);		
	}
	
	public void popularListaBlocos(){
		Condominio condominio = new Condominio();
		condominio.setId(this.idCondominio);
		this.listaSIBlocos = this.blocoMB.get().buscarListaBlocosPorCondominio(condominio);			
		this.listaSIUnidades.clear();
	}	
	
	public String cancelarEditarDespesaCondominio(){
		return "cancelar";
	}	
	
	public String cancelarEditarDespesaCondomino(){
		return "cancelar";
	}	

	public RelatorioDespesas getRelatorioDespesasUnidades() {
		return relatorioDespesasUnidades;
	}

	public void setRelatorioDespesas(RelatorioDespesas relatorioDespesas) {
		this.relatorioDespesasUnidades = relatorioDespesas;
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

	public List<SelectItem> getListaSICondominios() {
		this.listaSICondominios = this.condominioMB.get().buscarListaCondominiosAtivos();
		return listaSICondominios;
	}

	public void setListaSICondominios(List<SelectItem> listaSICondominios) {
		this.listaSICondominios = listaSICondominios;
	}

	public Despesas getDespesas() {		
		return despesas;
	}

	public void setDespesas(Despesas despesas) {
		this.despesas = despesas;
	}

	public List<SelectItem> getListaSITipos() {
		return listaSITipos;
	}

	public void setListaSITipos(List<SelectItem> listaSITipos) {
		this.listaSITipos = listaSITipos;
	}

	public Boolean getExibirCampoConsumoGasMesAtual() {
		return exibirCampoConsumoGasMesAtual;
	}

	public void setExibirCampoConsumoGasMesAtual(Boolean exibirCampoConsumoGasMesAtual) {
		this.exibirCampoConsumoGasMesAtual = exibirCampoConsumoGasMesAtual;
	}

	public List<SelectItem> getListaSIUnidades() {
		return listaSIUnidades;
	}

	public void setListaSIUnidades(List<SelectItem> listaSIUnidades) {
		this.listaSIUnidades = listaSIUnidades;
	}

	public Bloco getBloco() {
		return bloco;
	}

	public void setBloco(Bloco bloco) {
		this.bloco = bloco;
	}

	public List<SelectItem> getListaSIBlocos() {
		return listaSIBlocos;
	}

	public void setListaSIBlocos(List<SelectItem> listaSIBlocos) {
		this.listaSIBlocos = listaSIBlocos;
	}

	public List<SelectItem> getListaSIMenosGasTipos() {
		return listaSIMenosGasTipos;
	}

	public void setListaSIMenosGasTipos(List<SelectItem> listaSIMenosGasTipos) {
		this.listaSIMenosGasTipos = listaSIMenosGasTipos;
	}	
	
	
}
