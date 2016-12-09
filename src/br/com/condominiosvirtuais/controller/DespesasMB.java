package br.com.condominiosvirtuais.controller;

import java.io.Serializable;
import java.sql.SQLException;
import java.text.DecimalFormat;
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
import br.com.condominiosvirtuais.entity.Despesas;
import br.com.condominiosvirtuais.entity.Unidade;
import br.com.condominiosvirtuais.enumeration.DespesasCalculaRateioPadraoEnum;
import br.com.condominiosvirtuais.enumeration.DespesasEnum;
import br.com.condominiosvirtuais.exception.BusinessException;
import br.com.condominiosvirtuais.service.impl.DespesasServiceImpl;
import br.com.condominiosvirtuais.util.AplicacaoUtil;
import br.com.condominiosvirtuais.util.ManagedBeanUtil;

@Named @SessionScoped 
public class DespesasMB implements Serializable {
	         
	private static final long serialVersionUID = 1L;
	
	private static Logger logger = Logger.getLogger(DespesasMB.class);
	
	private Despesas despesas = null;	
	
	@Inject
	private DespesasServiceImpl despesasService;	
	
	@Inject
	private Instance<CondominioMB> condominioMB = null;
	
	@Inject
	private Instance<RelatorioDespesasMB> relatorioDespesasMB = null;
	
	@Inject
	private Instance<BlocoMB> blocoMB = null;
	
	@Inject
	private Instance<UnidadeMB> unidadeMB = null;
	
	
	private Bloco bloco = null;
	
	private Double totalDespesasCondominio = null;	
	
	private List<SelectItem> listaSIMesReferencia = new ArrayList<SelectItem>();
	
	private List<SelectItem> listaSIAnoReferencia = new ArrayList<SelectItem>();		
	
	private List<SelectItem> listaSICondominios = new ArrayList<SelectItem>();
	
	private List<SelectItem> listaSITipos = new ArrayList<SelectItem>();
	
	private List<SelectItem> listaSIMenosGasTipos = new ArrayList<SelectItem>();
	
	private List<SelectItem> listaSIUnidades = new ArrayList<SelectItem>();	
	
	private List<SelectItem> listaSIBlocos = new ArrayList<SelectItem>();
	
	private List<SelectItem> listaSICalculaRateioPadrao = new ArrayList<SelectItem>();
	
	private ListDataModel<Despesas> listaDespesasCondominio = new ListDataModel<Despesas>();
	
	private ListDataModel<Despesas> listaDespesasUnidadeCondomino = new ListDataModel<Despesas>();
	
	private ListDataModel<Unidade> listaDespesasUnidade = new ListDataModel<Unidade>();
	
	private Boolean calculaRateioPadrao;
	
	private Boolean exibirCalculaRateioPadrao;
	
	private Boolean exibirCampoValor;
	
	private Boolean exibirCampoConsumoGasMesAtual;
	
	private Boolean exibirCampoLeituraGasMesAtual;
	
	private Integer numeroColunasPanelGrip;	

	private Integer mesReferencia;
	
	private Integer anoReferencia;
	
	private static final Integer ANO_INICIAL = 2010;
	
	private Integer anoAtual  = null;
	
	private Double consumoGasMesAtual;
	
	private Double leituraGasMesAtual;
	               
	
	
	@PostConstruct
	public void iniciarDespesasMB(){		
		this.despesas = new Despesas();
		this.bloco = new Bloco();		
		this.exibirCalculaRateioPadrao = Boolean.FALSE;
		this.calculaRateioPadrao = Boolean.FALSE;
		this.exibirCampoValor = Boolean.TRUE;
		this.exibirCampoConsumoGasMesAtual = Boolean.FALSE;
		this.numeroColunasPanelGrip = 2;
		ManagedBeanUtil.popularSIMeses(listaSIMesReferencia);
		ManagedBeanUtil.popularSIAnos(listaSIAnoReferencia,ANO_INICIAL,Boolean.FALSE);		
		this.popularSITiposDespesas();
		this.popularSIDespesaUnica();
		this.popularSITiposDespesasMenosGas();
		Calendar dataAtual = GregorianCalendar.getInstance();
		dataAtual.setTime(new Date());
		this.anoAtual = dataAtual.get(Calendar.YEAR);
	}
	
	public void cadastrarDespesasCondominio(){
		try {
			Calendar mesAnoReferencia = GregorianCalendar.getInstance();
			mesAnoReferencia.set(Calendar.DAY_OF_MONTH,1);
			mesAnoReferencia.set(Calendar.MONTH,this.getMesReferencia());
			mesAnoReferencia.set(Calendar.YEAR,this.getAnoReferencia());
			this.despesas.setMesAnoReferencia(mesAnoReferencia.getTime());
			this.despesasService.salvarDespesasCondominio(this.despesas);
			this.despesas = new Despesas();
			this.mesReferencia = 0;
			this.anoReferencia = this.anoAtual;
			ManagedBeanUtil.setMensagemInfo("msg.despesas.despesaCondominioSalvaSucesso");			
		} catch (SQLException e) {
			logger.error("erro sqlstate "+e.getSQLState(), e);	
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		} catch (Exception e) {
			logger.error("", e);
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		}			
	}
	
	public void cadastrarDespesasCondomino(){
		try {
			String mensagemDeErro = "";
			Calendar mesAnoReferencia = GregorianCalendar.getInstance();
			mesAnoReferencia.set(Calendar.DAY_OF_MONTH,1);
			mesAnoReferencia.set(Calendar.MONTH,this.getMesReferencia());
			mesAnoReferencia.set(Calendar.YEAR,this.getAnoReferencia());
			this.despesas.setMesAnoReferencia(mesAnoReferencia.getTime());
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
					this.despesasService.salvarDespesasGasCondomino(this.despesas);			
				}
			}else{
				this.despesasService.salvarDespesasCondomino(this.despesas);	
			}		
			if (!mensagemDeErro.isEmpty()){
				ManagedBeanUtil.setMensagemErro(mensagemDeErro);
			}else{
				this.despesas = new Despesas();
				this.bloco = new Bloco();
				this.mesReferencia = 0;
				this.anoReferencia = this.anoAtual;
				this.exibirCampoConsumoGasMesAtual = Boolean.FALSE;				
				ManagedBeanUtil.setMensagemInfo("msg.despesas.despesaCondominoSalvaSucesso");
			}
		} catch (SQLException e) {
			logger.error("erro sqlstate "+e.getSQLState(), e);	
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		} catch (Exception e) {
			logger.error("", e);
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		}			
	}
	
	public void cadastrarDespesasUnicaCondominos(){
		try {
			Double totalDespesasCondominio = 0.0;
			Calendar mesAnoReferencia = GregorianCalendar.getInstance();
			mesAnoReferencia.set(Calendar.DAY_OF_MONTH,1);
			mesAnoReferencia.set(Calendar.MONTH,this.getMesReferencia());
			mesAnoReferencia.set(Calendar.YEAR,this.getAnoReferencia());
			this.despesas.setMesAnoReferencia(mesAnoReferencia.getTime());
			if(this.calculaRateioPadrao == Boolean.TRUE){
				this.relatorioDespesasMB.get().popularTotalDespesasCondominio(this.despesas.getCondominio().getId(), this.despesas.getMesAnoReferencia());
				totalDespesasCondominio = this.relatorioDespesasMB.get().getRelatorioDespesasUnidades().getTotalDespesasCondominio();				
				this.despesasService.salvarDespesasUnicaRateioPadraoCondominos(this.calcularRateioPadrao(totalDespesasCondominio));
			}else{				
				this.despesasService.salvarDespesasUnicaCondominos(this.despesas);
			}
			this.despesas = new Despesas();
			this.mesReferencia = 0;
			this.anoReferencia = this.anoAtual;
			ManagedBeanUtil.setMensagemInfo("msg.despesas.despesaCondominoSalvaSucesso");			
		} catch (SQLException e) {
			logger.error("erro sqlstate "+e.getSQLState(), e);	
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		} catch (Exception e) {
			logger.error("", e);
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		}			
	}
	

	
	public void excluirDespesa(ActionEvent actionEvent){
		try {
			Despesas despesas = (Despesas) this.listaDespesasCondominio.getRowData();
			this.despesasService.excluir(despesas);
		//	this.pesquisarDespesasCondominio();
			ManagedBeanUtil.setMensagemInfo("msg.despesas.excluirSucesso");		
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
	


	
	public void limparCadastrarDespesasCondominio(ActionEvent actionEvent){
		this.despesas = new Despesas();
		this.mesReferencia = 0;
		this.anoReferencia = this.anoAtual;	
		this.listaSICondominios.clear();
	}
	            
    public void limparEditarDespesaCondominio(ActionEvent actionEvent){
		this.despesas = new Despesas();
		this.mesReferencia = 0;
		this.anoReferencia = this.anoAtual;	
		this.listaSICondominios.clear();
	}
	
	public void limparCadastrarDespesasCondomino(ActionEvent actionEvent){
		this.despesas = new Despesas();
		this.mesReferencia = 0;
		this.anoReferencia = this.anoAtual;			
		this.listaSIBlocos.clear();
		this.listaSIUnidades.clear();
	}
	
	public void popularListaBlocos(){
		this.listaSIBlocos = this.blocoMB.get().buscarListaBlocosPorCondominio(this.despesas.getCondominio());			
		this.listaSIUnidades.clear();
	}
	
	public void popularListaUnidades(){
		this.listaSIUnidades = this.unidadeMB.get().buscarListaSIUnidadesPorBloco(this.bloco);		
	}
	              
	public String cancelarCadastrarDespesasCondominio(){
		return "cancelar";
	}	
	
	public String cancelarCadastrarDespesasCondomino(){
		return "cancelar";
	}	
	              
	public String cancelarEditarDespesaCondominio(){
		return "cancelar";
	}	
	
	public String cancelarCadastrarDespesasTodosCondominos(){
		return "cancelar";
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
	
	public List<SelectItem> getListaSIMenosGasTipos() {
		return listaSIMenosGasTipos;
	}

	public void setListaSIMenosGasTipos(List<SelectItem> listaSIMenosGasTipos) {
		this.listaSIMenosGasTipos = listaSIMenosGasTipos;
	}

	public Boolean getExibirCalculaRateioPadrao() {
		return exibirCalculaRateioPadrao;
	}	

	public List<SelectItem> popularSIDespesaUnica(){
		this.listaSICalculaRateioPadrao.add(new SelectItem(DespesasCalculaRateioPadraoEnum.SIM.getRateioPadrao(), AplicacaoUtil.i18n("despesas.calculaRateioPadrao.label.1")));
		this.listaSICalculaRateioPadrao.add(new SelectItem(DespesasCalculaRateioPadraoEnum.NAO.getRateioPadrao(), AplicacaoUtil.i18n("despesas.calculaRateioPadrao.label.0")));		
		return this.listaSICalculaRateioPadrao;
	}
	
	public void exibirCalculoRateioPadrao(){
		this.numeroColunasPanelGrip = 2;
		this.exibirCalculaRateioPadrao = Boolean.FALSE;
		this.exibirCampoValor = Boolean.TRUE;
		this.calculaRateioPadrao = Boolean.FALSE;
		if(this.despesas.getTipo() == DespesasEnum.RATEIO_CONDOMINIO.getDespesa()){
			this.exibirCalculaRateioPadrao = Boolean.TRUE;
			if (this.calculaRateioPadrao == Boolean.TRUE){
				this.numeroColunasPanelGrip = 2;	
				this.exibirCampoValor = Boolean.FALSE;
			}else{
				this.numeroColunasPanelGrip = 3;
			}			
		}	
	}
	
	public void exibirCampoValor(){
		this.exibirCampoValor = Boolean.TRUE;
		this.numeroColunasPanelGrip = 3;
		if(this.calculaRateioPadrao != Boolean.FALSE){
			this.exibirCampoValor = Boolean.FALSE;
			this.numeroColunasPanelGrip = 2;
		}		
	}
	
	public void exibirCampoConsumoGasMesAtual(){
		System.out.println("Unidade: " +this.despesas.getUnidade().getId());
		System.out.println("mes: " +this.mesReferencia);
		System.out.println("ano: " +this.anoReferencia);
		System.out.println("Condominio: " +this.despesas.getCondominio().getId());
		System.out.println("Bloco: " +this.bloco.getId());
		this.exibirCampoConsumoGasMesAtual = Boolean.FALSE;	
		if(this.despesas.getTipo() == DespesasEnum.GAS.getDespesa()){
			this.exibirCampoConsumoGasMesAtual = Boolean.TRUE;
		}		
	}


	public List<SelectItem> getListaSICondominios() {
		this.listaSICondominios = this.condominioMB.get().buscarListaCondominiosAtivos();
		return listaSICondominios;
	}
	
   // FIXME: Quando essa regra for configurada essa deve estar num service  	
	private List<Despesas> calcularRateioPadrao(Double totalDespesasCondominio){		
		Double rateioPorMetroQuadrado = totalDespesasCondominio / 770.43;
		DecimalFormat formato = new DecimalFormat("#.0000");
		Double valor = 0.0;
		rateioPorMetroQuadrado = Double.valueOf(formato.format(rateioPorMetroQuadrado).replace(',','.'));
		formato = new DecimalFormat("#.00");
		Double unidade01 = 137.23; Double unidade101 = 101.57;
		Double unidade102 = 108.23; Double unidade103 = 107.54;
		Double unidade201 = 100.97; Double unidade202 = 107.33;
		Double unidade203 = 107.55;
		Despesas despesasUnidade = null;
		List<Double> metroQuadrado = new ArrayList<Double>();
		metroQuadrado.add(unidade01); metroQuadrado.add(unidade101); metroQuadrado.add(unidade102);
		metroQuadrado.add(unidade103); metroQuadrado.add(unidade201); metroQuadrado.add(unidade202);
		metroQuadrado.add(unidade203);		
		List<Despesas> listaDespesas = new ArrayList<>();
		for (int i = 0; i < 7; i++) {
			despesasUnidade = new Despesas();	
			despesasUnidade.setDescricao(this.despesas.getDescricao());
			despesasUnidade.setCondominio(this.despesas.getCondominio());
			despesasUnidade.setMesAnoReferencia(this.getDespesas().getMesAnoReferencia());
			despesasUnidade.setTipo(this.despesas.getTipo());
			valor = Double.valueOf(formato.format(metroQuadrado.get(i)*rateioPorMetroQuadrado).replace(',', '.'));
			despesasUnidade.setValor(valor);
			listaDespesas.add(despesasUnidade);
		}
		
		return listaDespesas;		
	}
	
	// FIXME: Quando essa regra for configurada essa deve estar num service  	
		private void recalcularRateioPadrao(Double totalDespesasCondominio, List<Despesas> listaDespesas, Despesas despesas ){		
			Double rateioPorMetroQuadrado = totalDespesasCondominio / 770.43;
			DecimalFormat formato = new DecimalFormat("#.0000");
			Double valor = 0.0;
			rateioPorMetroQuadrado = Double.valueOf(formato.format(rateioPorMetroQuadrado).replace(',','.'));
			formato = new DecimalFormat("#.00");
			Double unidade01 = 137.23; Double unidade101 = 101.57;
			Double unidade102 = 108.23; Double unidade103 = 107.54;
			Double unidade201 = 100.97; Double unidade202 = 107.33;
			Double unidade203 = 107.55;
			Despesas despesasUnidade = null;
			List<Double> metroQuadrado = new ArrayList<Double>();
			metroQuadrado.add(unidade01); metroQuadrado.add(unidade101); metroQuadrado.add(unidade102);
			metroQuadrado.add(unidade103); metroQuadrado.add(unidade201); metroQuadrado.add(unidade202);
			metroQuadrado.add(unidade203);
			for (int i = 0; i < 7; i++) {
				despesasUnidade = new Despesas();	
				despesasUnidade.setDescricao(despesas.getDescricao());
				despesasUnidade.setCondominio(despesas.getCondominio());
				despesasUnidade.setMesAnoReferencia(this.getDespesas().getMesAnoReferencia());
				despesasUnidade.setTipo(despesas.getTipo());
				valor = Double.valueOf(formato.format(metroQuadrado.get(i)*rateioPorMetroQuadrado).replace(',', '.'));
				despesasUnidade.setValor(valor);
				listaDespesas.add(despesasUnidade);
			}
					
		}

	public Despesas getDespesas() {
		return despesas;
	}
	
	public void setDespesas(Despesas despesas) {
		this.despesas = despesas;
	}	

	public List<SelectItem> getListaSIMesReferencia() {
		return listaSIMesReferencia;
	}

	public List<SelectItem> getListaSIAnoReferencia() {
		return listaSIAnoReferencia;
	}

	public void setListaSICondominios(List<SelectItem> listaSICondominios) {
		this.listaSICondominios = listaSICondominios;
	}

	public List<SelectItem> getListaSITipos() {
		return listaSITipos;
	}

	public void setListaSITipos(List<SelectItem> listaSITipos) {
		this.listaSITipos = listaSITipos;
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

	public Bloco getBloco() {
		return bloco;
	}

	public void setBloco(Bloco bloco) {
		this.bloco = bloco;
	}	

	public Double getTotalDespesasCondominio() {
		return totalDespesasCondominio;
	}

	public void setTotalDespesasCondominio(Double totalDespesasCondominio) {
		this.totalDespesasCondominio = totalDespesasCondominio;
	}

	public List<SelectItem> getListaSIUnidades() {
		return listaSIUnidades;
	}

	public void setListaSIUnidades(List<SelectItem> listaSIUnidades) {
		this.listaSIUnidades = listaSIUnidades;
	}

	public ListDataModel<Despesas> getListaDespesasCondominio() {		       
		return listaDespesasCondominio;
	}

	public void setListaDespesasCondominio(ListDataModel<Despesas> listaDespesasCondominio) {
		this.listaDespesasCondominio = listaDespesasCondominio;
	}

	public ListDataModel<Despesas> getListaDespesasUnidadeCondomino() {
		return listaDespesasUnidadeCondomino;
	}

	public void setListaDespesasUnidadeCondomino(ListDataModel<Despesas> listaDespesasUnidadeCondomino) {
		this.listaDespesasUnidadeCondomino = listaDespesasUnidadeCondomino;
	}

	public ListDataModel<Unidade> getListaDespesasUnidade() {
		return listaDespesasUnidade;
	}

	public void setListaDespesasUnidade(ListDataModel<Unidade> listaDespesasUnidade) {
		this.listaDespesasUnidade = listaDespesasUnidade;
	}

	public Boolean getCalculaRateioPadrao() {
		return calculaRateioPadrao;
	}

	public void setCalculaRateioPadrao(Boolean calculaRateioPadrao) {
		this.calculaRateioPadrao = calculaRateioPadrao;
	}

	public List<SelectItem> getListaSICalculaRateioPadrao() {
		return listaSICalculaRateioPadrao;
	}

	public void setListaSICalculaRateioPadrao(List<SelectItem> listaSICalculaRateioPadrao) {
		this.listaSICalculaRateioPadrao = listaSICalculaRateioPadrao;
	}

	public Boolean getExibirRalculaRateioPadrao() {
		return exibirCalculaRateioPadrao;
	}

	public void setExibirRalculaRateioPadrao(Boolean exibirRalculaRateioPadrao) {
		this.exibirCalculaRateioPadrao = exibirRalculaRateioPadrao;
	}

	public Boolean getExibirCampoValor() {
		return exibirCampoValor;
	}

	public void setExibirCampoValor(Boolean exibirCampoValor) {
		this.exibirCampoValor = exibirCampoValor;
	}

	public Integer getNumeroColunasPanelGrip() {
		return numeroColunasPanelGrip;
	}

	public void setNumeroColunasPanelGrip(Integer numeroColunasPanelGrip) {
		this.numeroColunasPanelGrip = numeroColunasPanelGrip;
	}
	
	public List<SelectItem> getListaSIBlocos() {
		return listaSIBlocos;
	}

	public void setListaSIBlocos(List<SelectItem> listaSIBlocos) {
		this.listaSIBlocos = listaSIBlocos;
	}

	public Boolean getExibirCampoConsumoGasMesAtual() {
		return exibirCampoConsumoGasMesAtual;
	}

	public void setExibirCampoConsumoGasMesAtual(Boolean exibirCampoConsumoGasMesAtual) {
		this.exibirCampoConsumoGasMesAtual = exibirCampoConsumoGasMesAtual;
	}

	public Double getConsumoGasMesAtual() {
		return consumoGasMesAtual;
	}

	public void setConsumoGasMesAtual(Double consumoGasMesAtual) {
		this.consumoGasMesAtual = consumoGasMesAtual;
	}

	public Boolean getExibirCampoLeituraGasMesAtual() {
		return exibirCampoLeituraGasMesAtual;
	}

	public void setExibirCampoLeituraGasMesAtual(Boolean exibirCampoLeituraGasMesAtual) {
		this.exibirCampoLeituraGasMesAtual = exibirCampoLeituraGasMesAtual;
	}

	public Double getLeituraGasMesAtual() {
		return leituraGasMesAtual;
	}

	public void setLeituraGasMesAtual(Double leituraGasMesAtual) {
		this.leituraGasMesAtual = leituraGasMesAtual;
	}
	
	
	

}
