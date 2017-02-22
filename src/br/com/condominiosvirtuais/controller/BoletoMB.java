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
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.log4j.Logger;

import br.com.condominiosvirtuais.entity.Beneficiario;
import br.com.condominiosvirtuais.entity.Boleto;
import br.com.condominiosvirtuais.entity.ContaBancaria;
import br.com.condominiosvirtuais.exception.BusinessException;
import br.com.condominiosvirtuais.service.BeneficiarioService;
import br.com.condominiosvirtuais.service.BoletoService;
import br.com.condominiosvirtuais.service.CondominoService;
import br.com.condominiosvirtuais.service.ContaBancariaService;
import br.com.condominiosvirtuais.util.AplicacaoUtil;
import br.com.condominiosvirtuais.util.ManagedBeanUtil;
import br.com.condominiosvirtuais.vo.CondominoVO;

@Named @SessionScoped
public class BoletoMB  implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private static Logger logger = Logger.getLogger(BoletoMB.class);
	
	@Inject
	private ContaBancariaService contaBancariaService;
	
	@Inject
	private BeneficiarioService beneficiarioService;
	
	@Inject
	private CondominoService condominoService;
	
	@Inject
	private BoletoService boletoService;
	
	@Inject
	private Instance<CondominioMB> condominioMB; 	
	
	private List<SelectItem> listaSICondominios;
	
	private List<SelectItem> listaSIContasBancarias;
	
	private List<SelectItem> listaSIBeneficiarios;
	
	private List<SelectItem> listaSIPagadores;
	
	private List<SelectItem> listaSITitulos;
	
	private List<SelectItem> listaSIPago;
	
	private ListDataModel<Boleto> listaBoletos;
	
	private ListDataModel<Boleto> listaMeusBoletos;
	
	private Boleto boleto;	
	
	private Date dataVencimentoDe;
	
	private Date dataVencimentoAte;
	
	private Integer tipoPagoBoleto;
	
	
	
	public BoletoMB(){
		this.boleto = new Boleto();
		this.boleto.setEmissao(new Date());
	}
	
	@PostConstruct
	public void iniciarBoletoMB(){
		this.listaSICondominios = this.condominioMB.get().buscarListaCondominiosAtivos();
		this.popularTitulos();
		this.popularPago();
	}
	
	public String gerarBoleto(){
		try {
			if(this.boleto.getVencimento().before(this.boleto.getEmissao())){
				ManagedBeanUtil.setMensagemErro("msg.boleto.vencimentoMenorEmissao");
				return null;
			}else{
				this.boleto.setPago(Boolean.FALSE);
				this.boletoService.salvar(this.boleto);
				if(this.dataVencimentoDe == null || this.dataVencimentoAte == null){
					this.popularPesquisaDataVencimento();
				}				 
				this.pesquisar();				
				ManagedBeanUtil.setMensagemInfo("msg.boleto.geradoSucesso");				
			}
		}catch (SQLException e) {
			logger.error("erro sqlstate "+e.getSQLState(), e);	
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		} catch (BusinessException e) {
			logger.error("", e);
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		} catch (Exception e) {
			logger.error("", e);
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		}		
		return "gerar";
	}
	
	public void pagarBoleto(){		
		this.boleto = this.listaBoletos.getRowData();
		this.boleto.setPago(Boolean.TRUE);
		try {
			this.boletoService.atualizarStatusPagamento(this.boleto);
			this.pesquisar();
			ManagedBeanUtil.setMensagemInfo("msg.boleto.boletoPago");
		}catch (SQLException e) {
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
	
	public void deixarEmAberto(){
		this.boleto = this.listaBoletos.getRowData();
		this.boleto.setPago(Boolean.FALSE);
		try {
			this.boletoService.atualizarStatusPagamento(this.boleto);
			this.pesquisar();
			ManagedBeanUtil.setMensagemInfo("msg.boleto.boletoEmAberto");
		}catch (SQLException e) {
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
	
	public void pesquisar(){
		try {
			if(this.tipoPagoBoleto == -1){
				this.listaBoletos = new ListDataModel<Boleto>(this.boletoService.buscarPorIdCondominioEDataVencimento(this.boleto.getIdCondominio(), this.dataVencimentoDe, this.dataVencimentoAte));				
			}else if (this.tipoPagoBoleto == 1) {
				this.listaBoletos = new ListDataModel<Boleto>(this.boletoService.buscarPorIdCondominioEPagoEDataVencimento(this.boleto.getIdCondominio(), Boolean.TRUE,this.dataVencimentoDe, this.dataVencimentoAte));
			}else{
				this.listaBoletos = new ListDataModel<Boleto>(this.boletoService.buscarPorIdCondominioEPagoEDataVencimento(this.boleto.getIdCondominio(), Boolean.FALSE,this.dataVencimentoDe, this.dataVencimentoAte));
			}
			if (this.listaBoletos.getRowCount() == 0){
				 ManagedBeanUtil.setMensagemInfo("msg.boleto.semBoletos");
			}
		}catch (SQLException e) {
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
	
	public void pesquisarMeusBoletos(){
		try {
			Integer idUsuarioAutenticado = AplicacaoUtil.getUsuarioAutenticado().getId();
			if(this.tipoPagoBoleto == -1){				
				this.listaMeusBoletos = new ListDataModel<Boleto>(this.boletoService.buscarPorIdPagador(idUsuarioAutenticado));				
			}else if (this.tipoPagoBoleto == 1) {
				this.listaMeusBoletos= new ListDataModel<Boleto>(this.boletoService.buscarPorIdPagadorEPago(idUsuarioAutenticado, Boolean.TRUE));
			}else{
				this.listaMeusBoletos= new ListDataModel<Boleto>(this.boletoService.buscarPorIdPagadorEPago(idUsuarioAutenticado, Boolean.FALSE));
			}
			if (this.listaMeusBoletos.getRowCount() == 0){
				 ManagedBeanUtil.setMensagemInfo("msg.boleto.semBoletos");
			}
		}catch (SQLException e) {
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
	
	public void excluir(){
		this.boleto = this.listaBoletos.getRowData();
		try {
			this.boletoService.excluirPorId(this.boleto.getId());
			this.pesquisar();
			 ManagedBeanUtil.setMensagemInfo("msg.boleto.excluidoSucesso");
		}catch (SQLException e) {
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
	
	public void limparGerarBoleto(){
		this.boleto = new Boleto();		
	}
	
	public void limparFiltroBoleto(){
		this.boleto = new Boleto();	
		this.dataVencimentoDe = null;
		this.dataVencimentoAte = null;
		this.tipoPagoBoleto = -1;
		this.listaBoletos = new ListDataModel<Boleto>();
	}
	
	public void limparFiltroMeusBoletos(){
		this.boleto = new Boleto();
		this.listaMeusBoletos = new ListDataModel<Boleto>();
		this.tipoPagoBoleto = -1;
	}
	
	public String geraBoleto(){
		return "gera";
	}	
	
	public String cancelarGerarBoleto(){
		return "cancelar";
	}
	
	
	
	public void popularBeneficariosEContasBancarias(){
		try{
			this.popularBeneficiarios();
			this.popularContasBancarias();			
			this.buscarPagador();
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
	
	
	private void popularBeneficiarios() throws SQLException, BusinessException, Exception{		
		List<Beneficiario> listaBeneficiarios = this.beneficiarioService.buscarPorIdCondominio(this.boleto.getIdCondominio());
		this.listaSIBeneficiarios = new ArrayList<SelectItem>();
		for (Beneficiario beneficiario : listaBeneficiarios) {
			this.listaSIBeneficiarios.add(new SelectItem(beneficiario.getId(), beneficiario.getNome()));
		}			
		
	}
	
	private void popularContasBancarias() throws SQLException, Exception{
		List<ContaBancaria> listaContaBancaria = this.contaBancariaService.buscarPorIdCondominio(this.boleto.getIdCondominio());
		this.listaSIContasBancarias = new ArrayList<SelectItem>();
		for (ContaBancaria contaBancaria : listaContaBancaria) {
			this.listaSIContasBancarias.add(new SelectItem(contaBancaria.getId(), contaBancaria.getAgencia() + " " + contaBancaria.getNumero()));
		}			
		
	}
	
	public void buscarPagador() throws SQLException, Exception{		
		List<CondominoVO> listaCondominoVO = condominoService.buscarListaCondominosVOPorIdCondominioEPagadorSemImagem(this.boleto.getIdCondominio());
		this.listaSIPagadores = new ArrayList<SelectItem>();
		for (CondominoVO condominoVO : listaCondominoVO) {
			this.listaSIPagadores.add(new SelectItem(condominoVO.getIdCondomino(), condominoVO.getNomeBloco() + " " + condominoVO.getNumeroUnidade() + " " + condominoVO.getNomeCondomino(),"MAIKEL"));
		}	
	}
	
	private void popularTitulos(){
		String[] titulos = {"AP", "CC", "CH", "CPR", "DAE", "DAM", "DAU", "DD", "DM", "DMI", "DR", "DS", "DSI", "EC", "FAT", "LC", "ME", "NCC", "NCE", "NCI", "NCR", "ND", "NF", "NP", "NPR", "NS", "O", "PC", "RC", "TM", "TS", "W"};
		this.listaSITitulos = new ArrayList<SelectItem>();
		for (String string : titulos) {
			this.listaSITitulos.add(new SelectItem(string, string));			
		}
	}
	
	private void popularPago(){		
		this.listaSIPago = new ArrayList<SelectItem>();
		this.listaSIPago.add(new SelectItem(1, AplicacaoUtil.i18n("boleto.pago.1")));			
		this.listaSIPago.add(new SelectItem(0, AplicacaoUtil.i18n("boleto.pago.0")));
	}
	
	
	private void popularPesquisaDataVencimento(){
		Calendar vencimentoDataDe = GregorianCalendar.getInstance();
		Calendar vencimentoDataAte = GregorianCalendar.getInstance();
		Calendar dataVencimetoBoleto = new GregorianCalendar();
		dataVencimetoBoleto.setTime(this.boleto.getVencimento());
		vencimentoDataDe.set(Calendar.DAY_OF_MONTH,1);
		vencimentoDataDe.set(Calendar.MONTH,dataVencimetoBoleto.get(Calendar.MONTH));
		vencimentoDataDe.set(Calendar.YEAR,dataVencimetoBoleto.get(Calendar.YEAR));
		vencimentoDataAte.set(Calendar.DAY_OF_MONTH,dataVencimetoBoleto.getActualMaximum(Calendar.DAY_OF_MONTH));
		vencimentoDataAte.set(Calendar.MONTH,dataVencimetoBoleto.get(Calendar.MONTH));
		vencimentoDataAte.set(Calendar.YEAR,dataVencimetoBoleto.get(Calendar.YEAR));
		this.dataVencimentoDe = vencimentoDataDe.getTime();
		this.dataVencimentoAte = vencimentoDataAte.getTime();		
	}
	
	public List<SelectItem> getListaSICondominios() {
		return listaSICondominios;
	}

	public List<SelectItem> getListaSITitulos() {
		return listaSITitulos;
	}

	public void setListaSITitulos(List<SelectItem> listaSITitulos) {
		this.listaSITitulos = listaSITitulos;
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

	public Date getDataVencimentoDe() {
		return dataVencimentoDe;
	}

	public void setDataVencimentoDe(Date dataVencimentoDe) {
		this.dataVencimentoDe = dataVencimentoDe;
	}

	public Date getDataVencimentoAte() {
		return dataVencimentoAte;
	}

	public void setDataVencimentoAte(Date dataVencimentoAte) {
		this.dataVencimentoAte = dataVencimentoAte;
	}

	public Boleto getBoleto() {
		return boleto;
	}

	public void setBoleto(Boleto boleto) {
		this.boleto = boleto;
	}

	public ListDataModel<Boleto> getListaBoletos() {
		return listaBoletos;
	}

	public void setListaBoletos(ListDataModel<Boleto> listaBoletos) {
		this.listaBoletos = listaBoletos;
	}

	public List<SelectItem> getListaSIPago() {
		return listaSIPago;
	}

	public void setListaSIPago(List<SelectItem> listaSIPago) {
		this.listaSIPago = listaSIPago;
	}

	public Integer getTipoPagoBoleto() {
		return tipoPagoBoleto;
	}

	public void setTipoPagoBoleto(Integer tipoPagoBoleto) {
		this.tipoPagoBoleto = tipoPagoBoleto;
	}

	public ListDataModel<Boleto> getListaMeusBoletos() {
		return listaMeusBoletos;
	}

	public void setListaMeusBoletos(ListDataModel<Boleto> listaMeusBoletos) {
		this.listaMeusBoletos = listaMeusBoletos;
	}	
	

}
