package br.com.condominiosvirtuais.controller;

import java.io.OutputStream;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Instance;
import javax.faces.context.FacesContext;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import br.com.condominiosvirtuais.entity.Banco;
import br.com.condominiosvirtuais.entity.Condominio;
import br.com.condominiosvirtuais.entity.Despesa;
import br.com.condominiosvirtuais.entity.MeioPagamento;
import br.com.condominiosvirtuais.entity.Receita;
import br.com.condominiosvirtuais.enumeration.ReceitaDespesaMeioPagamentoEnum;
import br.com.condominiosvirtuais.service.BancoService;
import br.com.condominiosvirtuais.service.CondominioService;
import br.com.condominiosvirtuais.service.DespesaService;
import br.com.condominiosvirtuais.service.MeioPagamentoService;
import br.com.condominiosvirtuais.service.ReceitaService;
import br.com.condominiosvirtuais.util.AplicacaoUtil;
import br.com.condominiosvirtuais.util.ManagedBeanUtil;
import br.com.condominiosvirtuais.vo.ReceitaDespesaVO;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.format.Alignment;
import jxl.format.Colour;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.NumberFormat;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

@Named @SessionScoped 
public class ReceitaDespesaMB implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private static Logger logger = Logger.getLogger(ReceitaDespesaMB.class);
	
	@Inject
	private ReceitaService receitaService;
	
	@Inject
	private DespesaService despesaService;
	
	@Inject
	private MeioPagamentoService meioPagamentoService;
	
	@Inject
	private Instance<CondominioMB> condominioMB = null;
	
	@Inject
	private CondominioService condominioService;
	
	@Inject
	private BancoService bancoService;
	
	private static final Integer ANO_INICIAL = 2015;
	
	private List<SelectItem> listaSIMesReferencia = new ArrayList<SelectItem>();
	
	private List<SelectItem> listaSIAnoReferencia = new ArrayList<SelectItem>();
	
	private List<SelectItem> listaSICondominios = new ArrayList<SelectItem>();
	
	private List<SelectItem> listaSIMeioPagamento = new ArrayList<SelectItem>();
	
	private List<SelectItem> listaSIBanco = new ArrayList<SelectItem>();
	
	private List<MeioPagamento> listaMeioPagamento = null;
	
	private Receita receita;
	
	private Despesa despesa;
	
	private Boolean ehReceita;
	
	private String descricao;
	
	private String observacao;
	
	private Double valor;
	
	private Integer mesReferencia;
	
	private Integer anoReferencia;
	
	private Integer idCondominio;
	
	private ListDataModel<Receita> listaReceita = null;
	
	private ListDataModel<Despesa> listaDespesa = null;
	
	private ListDataModel<ReceitaDespesaVO> listaTotaisReceitasDespesas = null;
	
	private Date dataDe;
	
	private Date dataAte;
	
	private Date data;
	
	private MeioPagamento meioPagamento;
	
	private Banco banco;
	
	private String numeroDocumento;
	
	private Integer idDespesa;
	
	private Integer idReceita;
	
	private Boolean exibeBotaoExcel = null;
	
	private Boolean bancoRequerido = null;
			
	
	@PostConstruct
	public void iniciaReceitaDespesasMB(){	
		ManagedBeanUtil.popularSIMeses(listaSIMesReferencia);
		ManagedBeanUtil.popularSIAnos(listaSIAnoReferencia,ANO_INICIAL,Boolean.FALSE);			
		this.popularMeioPagamento();
		this.meioPagamento = new MeioPagamento();
		this.banco = new Banco();
		this.exibeBotaoExcel = Boolean.FALSE;
		this.bancoRequerido = Boolean.FALSE;
	}
	
	public String salvarReceitaDespesa(){		
		try {
			
			if(this.ehReceita){
				this.receita = new Receita();
				this.receita.setValor(this.valor);
				this.receita.setDescricao(this.descricao);
				this.receita.setData(this.data);
				this.receita.setObservacao(this.observacao);
				this.receita.setIdCondominio(this.idCondominio);
				this.receita.setMeioPagamento(this.meioPagamento);
				this.receita.setNumeroDocumento(this.numeroDocumento);
				this.receita.setBanco(this.banco);
				this.receitaService.salvar(this.receita);
			}else{
				this.despesa = new Despesa();
				this.despesa.setValor(this.valor);
				this.despesa.setDescricao(this.descricao);
				this.despesa.setData(this.data);
				this.despesa.setObservacao(this.observacao);
				this.despesa.setIdCondominio(this.idCondominio);
				this.despesa.setMeioPagamento(this.meioPagamento);
				this.despesa.setNumeroDocumento(this.numeroDocumento);
				this.despesa.setBanco(this.banco);
				this.despesaService.salvar(this.despesa);
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
	
	public String atualizarDespesa(){		
		try {
			this.despesa = new Despesa();
			this.despesa.setId(this.idDespesa);
			this.despesa.setValor(this.valor);
			this.despesa.setDescricao(this.descricao);
			this.despesa.setData(this.data);
			this.despesa.setObservacao(this.observacao);
			this.despesa.setIdCondominio(this.idCondominio);
			this.despesa.setMeioPagamento(this.meioPagamento);
			this.despesa.setBanco(this.banco);
			this.despesa.setNumeroDocumento(this.numeroDocumento);
			this.despesaService.atualizar(this.despesa);
			this.despesa = new Despesa();
			ManagedBeanUtil.setMensagemInfo("msg.receitaDespesa.despesaAtualizadaSucesso");
			this.pesquisarReceitaDespesa();
		} catch (SQLException e) {
			logger.error("erro sqlstate "+e.getSQLState(), e);	
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");	
		} catch (Exception e) {
			logger.error("", e);
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		}
		return "atualizar";
	}	
	
	public String atualizarReceita(){		
		try {
			this.receita = new Receita();
			this.receita.setId(this.idReceita);
			this.receita.setValor(this.valor);
			this.receita.setDescricao(this.descricao);
			this.receita.setData(this.data);
			this.receita.setObservacao(this.observacao);
			this.receita.setIdCondominio(this.idCondominio);
			this.receita.setMeioPagamento(this.meioPagamento);		
			this.receita.setBanco(this.banco);
			this.receita.setNumeroDocumento(this.numeroDocumento);
			this.receitaService.atualizar(this.receita);
			this.receita = new Receita();
			ManagedBeanUtil.setMensagemInfo("msg.receitaDespesa.despesaAtualizadaSucesso");
			this.pesquisarReceitaDespesa();
		} catch (SQLException e) {
			logger.error("erro sqlstate "+e.getSQLState(), e);	
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");	
		} catch (Exception e) {
			logger.error("", e);
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		}
		return "atualizar";
	}	


	
	public void pesquisarReceitaDespesa(){	
		List<Receita> listaReceitaLocal = null;
		List<Despesa> listaDespesaLocal = null;				
		try {			
			if(this.dataDe.after(this.dataAte)){
				ManagedBeanUtil.setMensagemErro("msg.receitaDespesa.dataDeMaiorDataAte");
			}else{
				listaReceitaLocal = this.receitaService.buscarPorDataDeEDataAteEIdCondominio(this.dataDe, this.dataAte, this.idCondominio);
				listaDespesaLocal = this.despesaService.buscarPorDataDeEDataAteEIdCondominio(this.dataDe, this.dataAte, this.idCondominio);
				this.calcularTotal(listaReceitaLocal, listaDespesaLocal);
				this.listaReceita = new ListDataModel<Receita>(listaReceitaLocal);
				this.listaDespesa = new ListDataModel<Despesa>(listaDespesaLocal);
				this.exibeBotaoExcel = Boolean.TRUE;
				if (this.listaReceita.getRowCount() == 0 && this.listaDespesa.getRowCount() == 0){
					this.exibeBotaoExcel = Boolean.FALSE;
					ManagedBeanUtil.setMensagemInfo("msg.receitaDespesa.semReceitaDespesa");
				}
			}

		} catch (SQLException e) {
			logger.error("erro sqlstate "+e.getSQLState(), e);	
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");	
		} catch (Exception e) {
			logger.error("", e);
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		}		
	}		

	
// TODO: Código comentado em 23/02/2017. Apagar em 180 dias 	
//	@SuppressWarnings("rawtypes")
//	private void calcularTotal(List<Receita> listaReceita, List<Despesa> listaDespesa) throws SQLException, Exception{
//		List<ReceitaDespesaVO> listaTotaisReceitasDespesasLocal = new ArrayList<ReceitaDespesaVO>();
//		List<MeioPagamento> listaMeioPagamento = this.meioPagamentoService.buscarTodos();		
//		Map<String, Double> mapMeioPagamentoValor = new HashMap<String, Double>();
//		Double totalGeralReceita = 0.0;
//		Double totalGeralDespesa = 0.0;
//		Double total = 0.0;
//		
//		ReceitaDespesaVO receitaDespesaVO = null;
//		
//		for (MeioPagamento meioPagamento : listaMeioPagamento) {
//			mapMeioPagamentoValor.put(meioPagamento.getNomeI18n(), 0.0);
//		}
//		
//		if(!listaReceita.isEmpty() || !listaDespesa.isEmpty()){			
//			for (Receita receitaLocal : listaReceita) {				
//				for (MeioPagamento meioPagamento : listaMeioPagamento) {
//					if(receitaLocal.getMeioPagamento().getId().equals(meioPagamento.getId())){
//						total = mapMeioPagamentoValor.get(receitaLocal.getMeioPagamento().getNomeI18n()).doubleValue() + receitaLocal.getValor();
//						mapMeioPagamentoValor.put(receitaLocal.getMeioPagamento().getNome(), total);
//					}
//				}
//			}
//			
//			Iterator<Entry<String, Double>> entries = mapMeioPagamentoValor.entrySet().iterator();
//			while (entries.hasNext()) {
//			    Map.Entry entry = (Map.Entry) entries.next();
//			    receitaDespesaVO = new ReceitaDespesaVO();
//			    if (!((Double) entry.getValue()).equals(0.0)){
//			    	receitaDespesaVO.setDescricao(AplicacaoUtil.i18n("msg.receitaDespesa.receita")+" " +(String) entry.getKey());
//			    	receitaDespesaVO.setValor((Double) entry.getValue());
//			    	totalGeralReceita += (Double) entry.getValue();
//			    	listaTotaisReceitasDespesasLocal.add(receitaDespesaVO);
//			    }
//			}			
//			receitaDespesaVO = new ReceitaDespesaVO();
//			receitaDespesaVO.setDescricao(AplicacaoUtil.i18n("msg.receitaDespesa.totalReceita"));			
//			receitaDespesaVO.setValor(totalGeralReceita);
//			listaTotaisReceitasDespesasLocal.add(receitaDespesaVO);
//						
//			mapMeioPagamentoValor = new HashMap<String, Double>();		
//			for (MeioPagamento meioPagamento : listaMeioPagamento) {
//				mapMeioPagamentoValor.put(meioPagamento.getNomeI18n(), 0.0);
//			}
//				
//			total = 0.0;
//			for (Despesa despesaLocal : listaDespesa) {				
//				for (MeioPagamento meioPagamento : listaMeioPagamento) {
//					if(despesaLocal.getMeioPagamento().getId().equals(meioPagamento.getId())){
//						total = mapMeioPagamentoValor.get(despesaLocal.getMeioPagamento().getNomeI18n()).doubleValue() + despesaLocal.getValor();
//						mapMeioPagamentoValor.put(despesaLocal.getMeioPagamento().getNome(), total);
//					}
//				}
//			}		
//			
//			entries = mapMeioPagamentoValor.entrySet().iterator();
//			while (entries.hasNext()) {
//			    Map.Entry entry = (Map.Entry) entries.next();
//			    if (!((Double) entry.getValue()).equals(0.0)){
//			    	receitaDespesaVO = new ReceitaDespesaVO();
//			    	receitaDespesaVO.setDescricao(AplicacaoUtil.i18n("msg.receitaDespesa.despesa")+" " +(String) entry.getKey());
//			    	receitaDespesaVO.setValor((Double) entry.getValue());
//			    	totalGeralDespesa += (Double) entry.getValue();
//			    	listaTotaisReceitasDespesasLocal.add(receitaDespesaVO);
//			    }
//			}
//			
//			receitaDespesaVO = new ReceitaDespesaVO();
//			receitaDespesaVO.setDescricao(AplicacaoUtil.i18n("msg.receitaDespesa.totalDespesa"));		
//			receitaDespesaVO.setValor(totalGeralDespesa);
//			listaTotaisReceitasDespesasLocal.add(receitaDespesaVO);
//			
//			receitaDespesaVO = new ReceitaDespesaVO();
//			receitaDespesaVO.setDescricao(AplicacaoUtil.i18n("msg.receitaDespesa.receitaMenosDespesa"));
//			receitaDespesaVO.setValor(totalGeralReceita - totalGeralDespesa);
//			listaTotaisReceitasDespesasLocal.add(receitaDespesaVO);
//		}
//		this.listaTotaisReceitasDespesas = new ListDataModel<ReceitaDespesaVO>(listaTotaisReceitasDespesasLocal);
//	}
	
	@SuppressWarnings("rawtypes")
	private void calcularTotal(List<Receita> listaReceita, List<Despesa> listaDespesa) throws SQLException, Exception{
		List<ReceitaDespesaVO> listaTotaisReceitasDespesasLocal = new ArrayList<ReceitaDespesaVO>();
		List<Banco> listaBanco = this.bancoService.buscarTodos();				
		Map<String, Double> mapMeioPagamentoValor = new HashMap<String, Double>();
		Double totalGeralReceita = 0.0;
		Double totalGeralDespesa = 0.0;
		Double total = 0.0;
		
		ReceitaDespesaVO receitaDespesaVO = null;
		
		for (Banco banco : listaBanco) {
			mapMeioPagamentoValor.put(banco.getNome(), 0.0);
		}
		mapMeioPagamentoValor.put(AplicacaoUtil.i18n("receitaDespesa.meioPagamento.0"), 0.0);
		
		if(!listaReceita.isEmpty() || !listaDespesa.isEmpty()){			
			for (Receita receitaLocal : listaReceita) {		
				if(receitaLocal.getMeioPagamento().getNome().equals(ReceitaDespesaMeioPagamentoEnum.BANCO.getMeioPagamento())){
					for (Banco banco : listaBanco) {
						if(receitaLocal.getBanco().getId().equals(banco.getId())){
							total = mapMeioPagamentoValor.get(receitaLocal.getBanco().getNome()).doubleValue() + receitaLocal.getValor();
							mapMeioPagamentoValor.put(receitaLocal.getBanco().getNome(), total);
						}
					}					
				}else{
					total = mapMeioPagamentoValor.get(AplicacaoUtil.i18n("receitaDespesa.meioPagamento.0")).doubleValue() + receitaLocal.getValor();
					mapMeioPagamentoValor.put(AplicacaoUtil.i18n("receitaDespesa.meioPagamento.0"), total);
				}
			}
			
			Iterator<Entry<String, Double>> entries = mapMeioPagamentoValor.entrySet().iterator();
			while (entries.hasNext()) {
				Map.Entry entry = (Map.Entry) entries.next();
				receitaDespesaVO = new ReceitaDespesaVO();
				if (!((Double) entry.getValue()).equals(0.0)){
					receitaDespesaVO.setDescricao(AplicacaoUtil.i18n("msg.receitaDespesa.receita")+" " +(String) entry.getKey());
					receitaDespesaVO.setValor((Double) entry.getValue());
					totalGeralReceita += (Double) entry.getValue();
					listaTotaisReceitasDespesasLocal.add(receitaDespesaVO);
				}
			}			
			receitaDespesaVO = new ReceitaDespesaVO();
			receitaDespesaVO.setDescricao(AplicacaoUtil.i18n("msg.receitaDespesa.totalReceita"));			
			receitaDespesaVO.setValor(totalGeralReceita);
			listaTotaisReceitasDespesasLocal.add(receitaDespesaVO);
			
			mapMeioPagamentoValor = new HashMap<String, Double>();		
			for (Banco banco : listaBanco) {
				mapMeioPagamentoValor.put(banco.getNome(), 0.0);
			}
			mapMeioPagamentoValor.put(AplicacaoUtil.i18n("receitaDespesa.meioPagamento.0"), 0.0);
			
			total = 0.0;
			for (Despesa despesaLocal : listaDespesa) {		
				if(despesaLocal.getMeioPagamento().getNome().equals(ReceitaDespesaMeioPagamentoEnum.BANCO.getMeioPagamento())){
					for (Banco banco : listaBanco) {
						if(despesaLocal.getBanco().getId().equals(banco.getId())){
							total = mapMeioPagamentoValor.get(despesaLocal.getBanco().getNome()).doubleValue() + despesaLocal.getValor();
							mapMeioPagamentoValor.put(despesaLocal.getBanco().getNome(), total);
						}
					}					
				}else{
					total = mapMeioPagamentoValor.get(AplicacaoUtil.i18n("receitaDespesa.meioPagamento.0")).doubleValue() + despesaLocal.getValor();
					mapMeioPagamentoValor.put("Dinheiro", total);
				}
			}		
			
			entries = mapMeioPagamentoValor.entrySet().iterator();
			while (entries.hasNext()) {
				Map.Entry entry = (Map.Entry) entries.next();
				if (!((Double) entry.getValue()).equals(0.0)){
					receitaDespesaVO = new ReceitaDespesaVO();
					receitaDespesaVO.setDescricao(AplicacaoUtil.i18n("msg.receitaDespesa.despesa")+" " +(String) entry.getKey());
					receitaDespesaVO.setValor((Double) entry.getValue());
					totalGeralDespesa += (Double) entry.getValue();
					listaTotaisReceitasDespesasLocal.add(receitaDespesaVO);
				}
			}
			
			receitaDespesaVO = new ReceitaDespesaVO();
			receitaDespesaVO.setDescricao(AplicacaoUtil.i18n("msg.receitaDespesa.totalDespesa"));		
			receitaDespesaVO.setValor(totalGeralDespesa);
			listaTotaisReceitasDespesasLocal.add(receitaDespesaVO);
			
			receitaDespesaVO = new ReceitaDespesaVO();
			receitaDespesaVO.setDescricao(AplicacaoUtil.i18n("msg.receitaDespesa.receitaMenosDespesa"));
			receitaDespesaVO.setValor(totalGeralReceita - totalGeralDespesa);
			listaTotaisReceitasDespesasLocal.add(receitaDespesaVO);
		}
		this.listaTotaisReceitasDespesas = new ListDataModel<ReceitaDespesaVO>(listaTotaisReceitasDespesasLocal);
	}

	
	public void gerarExcel() throws SQLException, Exception{
		
		// TODO: Código comentado em 27/12/2016. Apagar em 180 dias
		// String filename = "d:\\entrada.xls";
		// WritableWorkbook workbook =  Workbook.createWorkbook(new File(filename), ws);
		
		List<Integer> listaTamanhoLinhasColunaZero = new ArrayList<>();
		List<Integer> listaTamanhoLinhasColunaUm = new ArrayList<>();
		List<Integer> listaTamanhoLinhasColunaDois = new ArrayList<>();
		List<Integer> listaTamanhoLinhasColunaTres = new ArrayList<>();
		List<Integer> listaTamanhoLinhasColunaQuatro = new ArrayList<>();
		List<Integer> listaTamanhoLinhasColunaCinco = new ArrayList<>();
		Integer tamanhoExcedenteBoldArial12 = 3;
		
		
		Condominio condominio = this.condominioService.buscarPorId(this.idCondominio);
		String nomeRelatorio = condominio.getNome()+ "_"+AplicacaoUtil.formatarData(AplicacaoUtil.i18n("formatoDataUndescore"),this.dataDe)+"_"+AplicacaoUtil.formatarData(AplicacaoUtil.i18n("formatoDataUndescore"),this.dataAte)+".xls";
				
		
		WorkbookSettings ws = new WorkbookSettings();
		ws.setLocale(new Locale(AplicacaoUtil.i18n("localePt"), AplicacaoUtil.i18n("localeBr")));
		NumberFormat numberFormatMoeda = new NumberFormat(AplicacaoUtil.i18n("moeda")+" "+AplicacaoUtil.i18n("formatoMoeda"),NumberFormat.COMPLEX_FORMAT); 

		HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
		response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment; filename="+nomeRelatorio);
        
        OutputStream output = response.getOutputStream();
        WritableWorkbook workbook =  Workbook.createWorkbook(output);
		WritableSheet abaFolha1 = workbook.createSheet(AplicacaoUtil.i18n("folha1"), 0);
		
		 
		WritableFont wFormatarTitulo = new WritableFont(WritableFont.ARIAL,  12, WritableFont.BOLD);
		WritableFont wFormatarSubTitulo = new WritableFont(WritableFont.ARIAL,  12, WritableFont.BOLD);		 
		WritableFont wFormatarTituloColuna = new WritableFont(WritableFont.ARIAL,  10, WritableFont.BOLD);
		WritableFont wFormatarLinhaValor = new WritableFont(WritableFont.ARIAL,10, WritableFont.BOLD);
		wFormatarLinhaValor.setColour(Colour.RED);
		WritableFont wFormatarLinha = new WritableFont(WritableFont.ARIAL, 10);
		WritableCellFormat cfTitulo = new WritableCellFormat(wFormatarTitulo);
		WritableCellFormat cfTituloColuna = new WritableCellFormat(wFormatarTituloColuna);
		WritableCellFormat cfLinha = new WritableCellFormat(wFormatarLinha);
		WritableCellFormat cfLinhaValor = new WritableCellFormat(wFormatarLinha);
		WritableCellFormat cfMoeda = new WritableCellFormat(numberFormatMoeda);
		WritableCellFormat cfMoedaNegativo = new WritableCellFormat(wFormatarLinhaValor,numberFormatMoeda);
		 
		cfLinhaValor.setAlignment(Alignment.CENTRE);
		cfTitulo.setWrap(true);		 
		StringBuffer sbTitulo = new StringBuffer();
		sbTitulo.append(AplicacaoUtil.i18n("receitasEDespesas"));
		sbTitulo.append("  -  ");
		sbTitulo.append(condominioService.buscarPorId(this.idCondominio).getNome());
		sbTitulo.append("  -  ");
		sbTitulo.append(AplicacaoUtil.formatarData(AplicacaoUtil.i18n("formatoData"),this.dataDe));
		sbTitulo.append("  ");
		sbTitulo.append(AplicacaoUtil.i18n("ate"));
		sbTitulo.append("  ");
		sbTitulo.append(AplicacaoUtil.formatarData(AplicacaoUtil.i18n("formatoData"),this.dataAte));
		Label labelTitulo = new Label(0,0,sbTitulo.toString(),cfTitulo);
		abaFolha1.mergeCells(0, 0, 5, 0);
		cfTitulo.setAlignment(Alignment.CENTRE);
		abaFolha1.addCell(labelTitulo);		 
		
		WritableCellFormat cfcListaReceitaDespesa = new WritableCellFormat(wFormatarSubTitulo);
		Label labelListReceitaDespesa = new Label(0,2,AplicacaoUtil.i18n("listarReceita"),cfcListaReceitaDespesa);		 
		abaFolha1.mergeCells(0, 2, 3, 2);
		abaFolha1.addCell(labelListReceitaDespesa);		 
		
		Label labelDescricao = new Label(0,3,AplicacaoUtil.i18n("receitaDespesa.descricao"),cfTituloColuna);		 
		abaFolha1.addCell(labelDescricao);
		listaTamanhoLinhasColunaZero.add(AplicacaoUtil.i18n("receitaDespesa.descricao").length()+tamanhoExcedenteBoldArial12);
		
		Label labelData = new Label(1,3,AplicacaoUtil.i18n("receitaDespesa.data"),cfTituloColuna);		 
		abaFolha1.addCell(labelData);
		listaTamanhoLinhasColunaUm.add(AplicacaoUtil.i18n("receitaDespesa.data").length()+tamanhoExcedenteBoldArial12);		 
		
		Label labelValor = new Label(2,3,AplicacaoUtil.i18n("receitaDespesa.valor"),cfTituloColuna);		 
		abaFolha1.addCell(labelValor);
		listaTamanhoLinhasColunaDois.add(AplicacaoUtil.i18n("receitaDespesa.valor").length()+tamanhoExcedenteBoldArial12);
		
		Label lableNumeroDocumento = new Label(3,3,AplicacaoUtil.i18n("receitaDespesa.numeroDocumento"),cfTituloColuna);		 
		abaFolha1.addCell(lableNumeroDocumento);
		listaTamanhoLinhasColunaTres.add(AplicacaoUtil.i18n("receitaDespesa.numeroDocumento").length()+tamanhoExcedenteBoldArial12);		 
		
		Label labelMeioPagamento = new Label(4,3,AplicacaoUtil.i18n("receitaDespesa.meioPagamento"),cfTituloColuna);		 
		abaFolha1.addCell(labelMeioPagamento);
		listaTamanhoLinhasColunaQuatro.add(AplicacaoUtil.i18n("receitaDespesa.meioPagamento").length()+tamanhoExcedenteBoldArial12);
		
		Label labelObservacao = new Label(5,3,AplicacaoUtil.i18n("receitaDespesa.observacao"),cfTituloColuna);
		listaTamanhoLinhasColunaCinco.add(AplicacaoUtil.i18n("receitaDespesa.observacao").length()+tamanhoExcedenteBoldArial12);
		abaFolha1.addCell(labelObservacao);
		
		Label label = null;		 
		Iterator<Receita>  iteratorReceita = this.listaReceita.iterator();
		int totalReceita = this.listaReceita.getRowCount();
		int ultimaLinhaReceita = 0;
		for (int linha = 4; linha < totalReceita + 4; linha++) {
			Receita receita = (Receita) iteratorReceita.next();
			label = new Label(0,linha,receita.getDescricao(),cfLinha);		 
			listaTamanhoLinhasColunaZero.add(receita.getDescricao().length());
			abaFolha1.addCell(label);
			label = new Label(1,linha,AplicacaoUtil.formatarData(AplicacaoUtil.i18n("formatoData"),receita.getData()),cfLinha);
			listaTamanhoLinhasColunaUm.add(AplicacaoUtil.formatarData(AplicacaoUtil.i18n("formatoData"),receita.getData()).length());
			abaFolha1.addCell(label);
			Number number = new Number(2, linha, receita.getValor(),cfMoeda);				 
			listaTamanhoLinhasColunaDois.add((AplicacaoUtil.i18n("moeda")+" "+AplicacaoUtil.i18n("formatoMoeda")).length());			
			abaFolha1.addCell(number);
			label = new Label(3,linha,String.valueOf(receita.getNumeroDocumento()),cfLinha);	
			listaTamanhoLinhasColunaTres.add(receita.getNumeroDocumento().length());
			abaFolha1.addCell(label);
			label = new Label(4,linha,String.valueOf(receita.getMeioPagamento().getNomeI18n()),cfLinha);
			listaTamanhoLinhasColunaQuatro.add(receita.getMeioPagamento().getNomeI18n().length());
			abaFolha1.addCell(label);
			label = new Label(5,linha,receita.getObservacao(),cfLinha);		
			listaTamanhoLinhasColunaCinco.add(receita.getObservacao().length());
			abaFolha1.addCell(label);
			ultimaLinhaReceita = linha;
		 }				 
			 
		 int linhaSubTituloDespesa = ultimaLinhaReceita + 2;
		 labelListReceitaDespesa = new Label(0,linhaSubTituloDespesa,AplicacaoUtil.i18n("listarDespesa"),cfcListaReceitaDespesa);
		 abaFolha1.mergeCells(0, linhaSubTituloDespesa, 3, linhaSubTituloDespesa);			 
		 abaFolha1.addCell(labelListReceitaDespesa);
		 
		 int linhaTituloColuna = linhaSubTituloDespesa + 1;
		 labelDescricao = new Label(0,linhaTituloColuna,AplicacaoUtil.i18n("receitaDespesa.descricao"),cfTituloColuna);	
		 listaTamanhoLinhasColunaZero.add(AplicacaoUtil.i18n("receitaDespesa.descricao").length()+tamanhoExcedenteBoldArial12);
		 abaFolha1.addCell(labelDescricao);
		 
		 labelData = new Label(1,linhaTituloColuna,AplicacaoUtil.i18n("receitaDespesa.data"),cfTituloColuna);	
		 listaTamanhoLinhasColunaUm.add(AplicacaoUtil.i18n("formatoData").length()+tamanhoExcedenteBoldArial12);
		 abaFolha1.addCell(labelData);
		 
		 labelValor = new Label(2,linhaTituloColuna,AplicacaoUtil.i18n("receitaDespesa.valor"),cfTituloColuna);		 
		 listaTamanhoLinhasColunaDois.add(AplicacaoUtil.i18n("receitaDespesa.valor").length()+tamanhoExcedenteBoldArial12);
		 abaFolha1.addCell(labelValor);
		 
		 lableNumeroDocumento = new Label(3,linhaTituloColuna,AplicacaoUtil.i18n("receitaDespesa.numeroDocumento"),cfTituloColuna);
		 listaTamanhoLinhasColunaTres.add(AplicacaoUtil.i18n("receitaDespesa.numeroDocumento").length()+tamanhoExcedenteBoldArial12);
		 abaFolha1.addCell(lableNumeroDocumento);
		 
		 labelMeioPagamento = new Label(4,linhaTituloColuna,AplicacaoUtil.i18n("receitaDespesa.meioPagamento"),cfTituloColuna);		
		 listaTamanhoLinhasColunaQuatro.add(AplicacaoUtil.i18n("receitaDespesa.meioPagamento").length()+tamanhoExcedenteBoldArial12);
		 abaFolha1.addCell(labelMeioPagamento);
		 
		 labelObservacao = new Label(5,linhaTituloColuna,AplicacaoUtil.i18n("receitaDespesa.observacao"),cfTituloColuna);	
		 listaTamanhoLinhasColunaCinco.add(AplicacaoUtil.i18n("receitaDespesa.observacao").length()+tamanhoExcedenteBoldArial12);
		 abaFolha1.addCell(labelObservacao);
		 
		 int linhaDespesa = linhaTituloColuna + 1;
		 Iterator<Despesa>  iteratorDespesa = this.listaDespesa.iterator();
		 int totalDespesa = this.listaDespesa.getRowCount();
		 int ultimaLinhaDespesa = 0;
		 for (int linha = linhaDespesa; linha < totalDespesa + linhaDespesa; linha++) {
			 Despesa despesa = (Despesa) iteratorDespesa.next();
			 label = new Label(0,linha, despesa.getDescricao(),cfLinha);	
			 listaTamanhoLinhasColunaZero.add(despesa.getDescricao().length());
			 abaFolha1.addCell(label);
			 label = new Label(1,linha,AplicacaoUtil.formatarData(AplicacaoUtil.i18n("formatoData"),despesa.getData()),cfLinha);	
			 listaTamanhoLinhasColunaZero.add(AplicacaoUtil.formatarData(AplicacaoUtil.i18n("formatoData"),despesa.getData()).length());
			 abaFolha1.addCell(label);
			 Number number = new Number(2, linha, despesa.getValor(),cfMoeda);
			 listaTamanhoLinhasColunaDois.add((AplicacaoUtil.i18n("moeda")+" "+AplicacaoUtil.i18n("formatoMoeda")).length());			 
			 abaFolha1.addCell(number);
			 label = new Label(3,linha,String.valueOf(despesa.getNumeroDocumento()),cfLinha);
			 listaTamanhoLinhasColunaTres.add(despesa.getNumeroDocumento().length());
			 abaFolha1.addCell(label);
			 label = new Label(4,linha,String.valueOf(despesa.getMeioPagamento().getNomeI18n()),cfLinha);	
			 listaTamanhoLinhasColunaQuatro.add(despesa.getMeioPagamento().getNomeI18n().length());
			 abaFolha1.addCell(label);
			 label = new Label(5,linha,despesa.getObservacao(),cfLinha);	
			 listaTamanhoLinhasColunaCinco.add(despesa.getObservacao().length());
			 abaFolha1.addCell(label);
			 ultimaLinhaDespesa = linha;
		 }
		 
		 int linhaSubTituloTotalReceitaEDespesa = ultimaLinhaDespesa + 2;
		 labelListReceitaDespesa = new Label(0,linhaSubTituloTotalReceitaEDespesa,AplicacaoUtil.i18n("totaisReceitaEDespesa"),cfcListaReceitaDespesa);
		 abaFolha1.mergeCells(0, linhaSubTituloTotalReceitaEDespesa, 3, linhaSubTituloTotalReceitaEDespesa);			 
		 abaFolha1.addCell(labelListReceitaDespesa);
		 
		 Iterator<ReceitaDespesaVO> iteratorReceitaDespesaVO =  listaTotaisReceitasDespesas.iterator();
		 int linhaReceitaEDespesa = linhaSubTituloTotalReceitaEDespesa +1;
		 while (iteratorReceitaDespesaVO.hasNext()) {
			 ReceitaDespesaVO receitaDespesaVO = iteratorReceitaDespesaVO.next();
			 label = new Label(0,linhaReceitaEDespesa, receitaDespesaVO.getDescricao(),cfLinha);		
			 listaTamanhoLinhasColunaZero.add(receitaDespesaVO.getDescricao().length());
			 abaFolha1.addCell(label);
			 Number number = null;
			 if(receitaDespesaVO.getValor() < 0){ // Formata com valor vermelho
				 number = new Number(1, linhaReceitaEDespesa, receitaDespesaVO.getValor(),cfMoedaNegativo);				 
			 }else{
				 number = new Number(1, linhaReceitaEDespesa, receitaDespesaVO.getValor(),cfMoeda);
			 }
			 listaTamanhoLinhasColunaDois.add((AplicacaoUtil.i18n("moeda")+" "+AplicacaoUtil.i18n("formatoMoeda")).length());
			 
			 abaFolha1.addCell(number);
			 linhaReceitaEDespesa++;
			
		}
		 abaFolha1.setColumnView(0, Collections.max(listaTamanhoLinhasColunaZero));
		 abaFolha1.setColumnView(1, Collections.max(listaTamanhoLinhasColunaUm));
		 abaFolha1.setColumnView(2, Collections.max(listaTamanhoLinhasColunaDois));
		 abaFolha1.setColumnView(3, Collections.max(listaTamanhoLinhasColunaTres));
		 abaFolha1.setColumnView(4, Collections.max(listaTamanhoLinhasColunaQuatro));
		 abaFolha1.setColumnView(5, Collections.max(listaTamanhoLinhasColunaCinco));
		 abaFolha1.setRowView(0, 300);
		 response.flushBuffer();
		 workbook.write();
		 FacesContext.getCurrentInstance().responseComplete();
		 workbook.close();
	}
	
	
	public String cadastroReceitaDespesa(){
		this.limparCadastroReceitaDespesa();
		return "cadastrar"; 
	}

	
	public String cancelarCadastroReceitaDespesa(){
		return "cancelar";
	}
	
	public String editarDespesa(){
		try {
			this.bancoRequerido = Boolean.FALSE;
			this.despesa = listaDespesa.getRowData();
			this.idDespesa = this.despesa.getId();
			this.descricao = this.despesa.getDescricao();
			this.data = this.despesa.getData();
			this.valor = this.despesa.getValor();		
			this.numeroDocumento = this.despesa.getNumeroDocumento();
			this.meioPagamento = this.despesa.getMeioPagamento();		
			this.banco.setId(this.despesa.getBanco() != null ? this.despesa.getBanco().getId() : null );
			this.observacao = this.despesa.getObservacao();	
			this.listaSIBanco = new ArrayList<SelectItem>();	
			if(this.meioPagamento.getNome().equals(ReceitaDespesaMeioPagamentoEnum.BANCO.getMeioPagamento())){				
				this.bancoRequerido = Boolean.TRUE;
				List<Banco> listaBanco = null;
				listaBanco = this.bancoService.buscarTodos();
				for (Banco banco : listaBanco) {
					this.listaSIBanco.add(new SelectItem(banco.getId(),banco.getNome()));	
				}
			}	
		} catch (SQLException e) {
			logger.error("erro sqlstate "+e.getSQLState(), e);	
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");	
		} catch (Exception e) {
			logger.error("", e);
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		}		
		return "editar";
	}
	
	public String editarReceita() throws SQLException, Exception{
		try{
			this.bancoRequerido = Boolean.FALSE;
			this.receita = listaReceita.getRowData();
			this.idReceita = this.receita.getId();
			this.descricao = this.receita.getDescricao();
			this.data = this.receita.getData();
			this.valor = this.receita.getValor();		
			this.numeroDocumento = this.receita.getNumeroDocumento();
			this.meioPagamento = this.receita.getMeioPagamento();
			this.observacao = this.receita.getObservacao();
			this.banco.setId(this.receita.getBanco() != null ? this.receita.getBanco().getId() : null );		
			this.listaSIBanco = new ArrayList<SelectItem>();	
			if(this.meioPagamento.getNome().equals(ReceitaDespesaMeioPagamentoEnum.BANCO.getMeioPagamento())){				
				this.bancoRequerido = Boolean.TRUE;
				List<Banco> listaBanco = null;
				listaBanco = this.bancoService.buscarTodos();			
				for (Banco banco : listaBanco) {
					this.listaSIBanco.add(new SelectItem(banco.getId(),banco.getNome()));	
				}
			}
		} catch (SQLException e) {
			logger.error("erro sqlstate "+e.getSQLState(), e);	
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");	
		} catch (Exception e) {
			logger.error("", e);
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		}
// Código comentado em 24/02/2017. Apagar em 90 dias		
//this.banco = new Banco();
//		if(this.meioPagamento.getNome().equals(ReceitaDespesaMeioPagamentoEnum.BANCO.getMeioPagamento())){
//			this.popularBanco();			
//			this.banco.setId(this.receita.getBanco().getId());			
//		}		
		return "editar";
	}
	
	public String excluirDespesa(){
		try {
			this.despesaService.excluir(this.despesa);
			ManagedBeanUtil.setMensagemInfo("msg.receitaDespesa.despesaExcluidaSucesso");
			this.pesquisarReceitaDespesa();
		} catch (SQLException e) {
			logger.error("erro sqlstate "+e.getSQLState(), e);	
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");	
		} catch (Exception e) {
			logger.error("", e);
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		}		
		return "excluir";
	}
	              
	public String excluirReceita(){		
		try {
			this.receitaService.excluir(this.receita);
			ManagedBeanUtil.setMensagemInfo("msg.receitaDespesa.receitaExcluidaSucesso");
			this.pesquisarReceitaDespesa();
		} catch (SQLException e) {
			logger.error("erro sqlstate "+e.getSQLState(), e);	
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");	
		} catch (Exception e) {
			logger.error("", e);
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		}		
		return "excluir";
	}
	
	public String voltarListaReceitaDespesa(){
		return "voltar";
	}	
	
	public void limparCadastroReceitaDespesa(){
		this.limparTelaCadastroReceitaDespesa();
	}
	
	public void LimparPesquisarReceitaDespesa(){
		this.limparTelaCadastroReceitaDespesa();
		this.listaDespesa = new ListDataModel<Despesa>();
		this.listaReceita = new ListDataModel<Receita>();
		this.listaTotaisReceitasDespesas = new ListDataModel<ReceitaDespesaVO>();
		this.exibeBotaoExcel = Boolean.FALSE;
		this.dataDe = null;
		this.dataAte = null;
		this.idCondominio = null;
	}
	
	public void popularBanco(){
		try {
			this.bancoRequerido = Boolean.FALSE;			
			this.listaSIBanco = new ArrayList<SelectItem>();
			this.banco = new Banco();
			if(this.meioPagamento.getNome().equals(ReceitaDespesaMeioPagamentoEnum.BANCO.getMeioPagamento())){				
				this.bancoRequerido = Boolean.TRUE;
				List<Banco> listaBanco = null;
				listaBanco = this.bancoService.buscarTodos();			
				for (Banco banco : listaBanco) {
					this.listaSIBanco.add(new SelectItem(banco.getId(),banco.getNome()));	
				}
			}
		} catch (SQLException e) {
			logger.error("erro sqlstate "+e.getSQLState(), e);	
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");	
		} catch (Exception e) {
			logger.error("", e);
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		}		
	}
	
	private void limparTelaCadastroReceitaDespesa(){
		this.bancoRequerido = Boolean.FALSE;
		this.despesa = new Despesa();
		this.receita = new Receita();
		this.meioPagamento = new MeioPagamento();
		this.valor = null;
		this.descricao = null;
		this.idCondominio = null;
		this.observacao = null;
		this.data = null;
		this.numeroDocumento = null;
		this.listaSIBanco = new ArrayList<SelectItem>();	
	}	
	
	private void popularMeioPagamento(){
		try {
			this.listaMeioPagamento = this.meioPagamentoService.buscarTodos();
			this.listaSIMeioPagamento = new ArrayList<SelectItem>();
			for (MeioPagamento meioPagamento : listaMeioPagamento) {
				this.listaSIMeioPagamento.add(new SelectItem((MeioPagamento)meioPagamento,meioPagamento.getNomeI18n()));	
			}			
		} catch (SQLException e) {
			logger.error("erro sqlstate "+e.getSQLState(), e);	
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");	
		} catch (Exception e) {
			logger.error("", e);
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		}		
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
		return descricao;
	}

	public void setNome(String nome) {
		this.descricao = nome;
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

	public ListDataModel<Receita> getListaReceita() {
		return listaReceita;
	}

	public void setListaReceita(ListDataModel<Receita> listaReceita) {
		this.listaReceita = listaReceita;
	}

	public ListDataModel<Despesa> getListaDespesa() {
		return listaDespesa;
	}

	public void setListaDespesa(ListDataModel<Despesa> listaDespesa) {
		this.listaDespesa = listaDespesa;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public Date getDataDe() {
		return dataDe;
	}

	public void setDataDe(Date dataDe) {
		this.dataDe = dataDe;
	}

	public Date getDataAte() {
		return dataAte;
	}

	public void setDataAte(Date dataAte) {
		this.dataAte = dataAte;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public MeioPagamento getMeioPagamento() {
		return meioPagamento;
	}

	public void setMeioPagamento(MeioPagamento meioPagamento) {
		this.meioPagamento = meioPagamento;
	}

	public String getNumeroDocumento() {
		return numeroDocumento;
	}

	public void setNumeroDocumento(String numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}

	public List<SelectItem> getListaSIMeioPagamento() {
		return listaSIMeioPagamento;
	}

	public void setListaSIMeioPagamento(List<SelectItem> listaSIMeioPagamento) {
		this.listaSIMeioPagamento = listaSIMeioPagamento;
	}

	public ListDataModel<ReceitaDespesaVO> getListaTotaisReceitasDespesas() {
		return listaTotaisReceitasDespesas;
	}

	public void setListaTotaisReceitasDespesas(ListDataModel<ReceitaDespesaVO> listaTotaisReceitasDespesas) {
		this.listaTotaisReceitasDespesas = listaTotaisReceitasDespesas;
	}

	public Boolean getExibeBotaoExcel() {
		return exibeBotaoExcel;
	}

	public void setExibeBotaoExcel(Boolean exibeBotaoExcel) {
		this.exibeBotaoExcel = exibeBotaoExcel;
	}

	public List<SelectItem> getListaSIBanco() {
		return listaSIBanco;
	}

	public void setListaSIBanco(List<SelectItem> listaSIBanco) {
		this.listaSIBanco = listaSIBanco;
	}

	public Boolean getBancoRequerido() {
		return bancoRequerido;
	}

	public void setBancoRequerido(Boolean bancoRequerido) {
		this.bancoRequerido = bancoRequerido;
	}

	public Banco getBanco() {
		return banco;
	}

	public void setBanco(Banco banco) {
		this.banco = banco;
	}	
	

}
