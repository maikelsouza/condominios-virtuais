package br.com.condominiosvirtuais.controller;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Instance;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.log4j.Logger;

import br.com.condominiosvirtuais.entity.Despesa;
import br.com.condominiosvirtuais.entity.MeioPagamento;
import br.com.condominiosvirtuais.entity.Receita;
import br.com.condominiosvirtuais.enumeration.MeioPagamentoNomeEnum;
import br.com.condominiosvirtuais.service.DespesaService;
import br.com.condominiosvirtuais.service.MeioPagamentoService;
import br.com.condominiosvirtuais.service.ReceitaService;
import br.com.condominiosvirtuais.util.AplicacaoUtil;
import br.com.condominiosvirtuais.util.ManagedBeanUtil;
import br.com.condominiosvirtuais.vo.ReceitaDespesaVO;
import br.com.condominiosvirtuais.vo.ReservaVO;

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
	
	private static final Integer ANO_INICIAL = 2015;
	
	private List<SelectItem> listaSIMesReferencia = new ArrayList<SelectItem>();
	
	private List<SelectItem> listaSIAnoReferencia = new ArrayList<SelectItem>();
	
	private List<SelectItem> listaSICondominios = new ArrayList<SelectItem>();
	
	private List<SelectItem> listaSIMeioPagamento = new ArrayList<SelectItem>();
	
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
	
	private String numeroDocumento;
	
	private Integer idDespesa;
	
	private Integer idReceita;
			
	
	@PostConstruct
	public void iniciaReceitaDespesasMB(){	
		ManagedBeanUtil.popularSIMeses(listaSIMesReferencia);
		ManagedBeanUtil.popularSIAnos(listaSIAnoReferencia,ANO_INICIAL,Boolean.FALSE);			
		this.popularMeioPagamento();
		this.meioPagamento = new MeioPagamento();
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
				
				if (this.listaReceita.getRowCount() == 0 && this.listaDespesa.getRowCount() == 0){
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
	
	private void calcularTotal(List<Receita> listaReceita, List<Despesa> listaDespesa){
		List<ReceitaDespesaVO> listaTotaisReceitasDespesasLocal = new ArrayList<ReceitaDespesaVO>();
		Double totalSicoob = 0.0;
		Double totalDinheiro = 0.0;
		Double totalReceitaSicoobDinheiro = 0.0;
		ReceitaDespesaVO receitaDespesaVO = null;
		
		if(!listaReceita.isEmpty() || !listaDespesa.isEmpty()){
			
			for (Receita receitaLocal : listaReceita) {
				if(receitaLocal.getMeioPagamento().getNome().equals(MeioPagamentoNomeEnum.DINHEIRO.getNome())){
					totalDinheiro+= receitaLocal.getValor();
				}else{
					totalSicoob+= receitaLocal.getValor();
				}
			}				
			
			receitaDespesaVO = new ReceitaDespesaVO();;
			receitaDespesaVO.setDescricao(AplicacaoUtil.i18n("msg.receitaDespesa.receitaTotalSicoob"));
			receitaDespesaVO.setValor(totalSicoob);
			listaTotaisReceitasDespesasLocal.add(receitaDespesaVO);
			receitaDespesaVO = new ReceitaDespesaVO();
			receitaDespesaVO.setDescricao(AplicacaoUtil.i18n("msg.receitaDespesa.receitaTotalDinheiro"));
			receitaDespesaVO.setValor(totalDinheiro);
			listaTotaisReceitasDespesasLocal.add(receitaDespesaVO);
			receitaDespesaVO = new ReceitaDespesaVO();
			receitaDespesaVO.setDescricao(AplicacaoUtil.i18n("msg.receitaDespesa.receitaTotalSicobobDinheiro"));
			totalReceitaSicoobDinheiro = totalSicoob+totalDinheiro;
			receitaDespesaVO.setValor(totalReceitaSicoobDinheiro);
			listaTotaisReceitasDespesasLocal.add(receitaDespesaVO);
				
			totalSicoob = 0.0;
			totalDinheiro = 0.0;
			for (Despesa despesaLocal : listaDespesa) {
				if(despesaLocal.getMeioPagamento().getNome().equals(MeioPagamentoNomeEnum.DINHEIRO.getNome())){
					totalDinheiro+= despesaLocal.getValor();
				}else{
					totalSicoob+= despesaLocal.getValor();
				}
			}
			receitaDespesaVO = new ReceitaDespesaVO();
			receitaDespesaVO.setDescricao(AplicacaoUtil.i18n("msg.receitaDespesa.despesaTotalSicoob"));
			receitaDespesaVO.setValor(totalSicoob);		
			listaTotaisReceitasDespesasLocal.add(receitaDespesaVO);
			
			receitaDespesaVO = new ReceitaDespesaVO();
			receitaDespesaVO.setDescricao(AplicacaoUtil.i18n("msg.receitaDespesa.despesaTotalDinheiro"));
			receitaDespesaVO.setValor(totalDinheiro);
			listaTotaisReceitasDespesasLocal.add(receitaDespesaVO);
			
			receitaDespesaVO = new ReceitaDespesaVO();
			receitaDespesaVO.setDescricao(AplicacaoUtil.i18n("msg.receitaDespesa.despesaTotalSicobobDinheiro"));
			receitaDespesaVO.setValor(totalSicoob+totalDinheiro);
			listaTotaisReceitasDespesasLocal.add(receitaDespesaVO);
			
			receitaDespesaVO = new ReceitaDespesaVO();
			receitaDespesaVO.setDescricao(AplicacaoUtil.i18n("msg.receitaDespesa.receitaMenosDespesa"));
			receitaDespesaVO.setValor(totalReceitaSicoobDinheiro - (totalSicoob+totalDinheiro));
			listaTotaisReceitasDespesasLocal.add(receitaDespesaVO);		
			
			
		}
		
		
		this.listaTotaisReceitasDespesas = new ListDataModel<ReceitaDespesaVO>(listaTotaisReceitasDespesasLocal);
	}

	
	public String cancelarCadastroReceitaDespesa(){
		return "cancelar";
	}
	
	public String editarDespesa(){
		this.despesa = listaDespesa.getRowData();
		this.idDespesa = this.despesa.getId();
		this.descricao = this.despesa.getDescricao();
		this.data = this.despesa.getData();
		this.valor = this.despesa.getValor();
		this.numeroDocumento = this.despesa.getNumeroDocumento();
		this.meioPagamento = new MeioPagamento();
		this.meioPagamento.setId(this.getMeioPagamento().getId());	
		this.observacao = this.despesa.getObservacao();
		return "editar";
	}
	
	public String editarReceita(){
		this.receita = listaReceita.getRowData();
		this.idReceita = this.receita.getId();
		this.descricao = this.receita.getDescricao();
		this.data = this.receita.getData();
		this.valor = this.receita.getValor();
		this.numeroDocumento = this.receita.getNumeroDocumento();
		this.meioPagamento = new MeioPagamento();
		this.meioPagamento.setId(this.getMeioPagamento().getId());	
		this.observacao = this.receita.getObservacao();
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
	}
	
	private void limparTelaCadastroReceitaDespesa(){
		this.despesa = new Despesa();
		this.receita = new Receita();
		this.meioPagamento = new MeioPagamento();
		this.valor = null;
		this.descricao = null;
		this.idCondominio = null;
		this.observacao = null;
		this.data = null;
		this.numeroDocumento = null;
	}	
	
	private void popularMeioPagamento(){
		try {
			List<MeioPagamento> listaMeioPagamento = null;
			listaMeioPagamento = this.meioPagamentoService.buscarTodos();
			this.listaSIMeioPagamento = new ArrayList<SelectItem>();
			for (MeioPagamento meioPagamento : listaMeioPagamento) {
				this.listaSIMeioPagamento.add(new SelectItem(meioPagamento.getId(),meioPagamento.getNomeI18n()));	
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
	
	
	

}
