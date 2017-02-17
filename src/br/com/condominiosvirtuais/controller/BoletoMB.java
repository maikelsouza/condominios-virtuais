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

import br.com.condominiosvirtuais.entity.Beneficiario;
import br.com.condominiosvirtuais.entity.Boleto;
import br.com.condominiosvirtuais.entity.ContaBancaria;
import br.com.condominiosvirtuais.exception.BusinessException;
import br.com.condominiosvirtuais.service.BeneficiarioService;
import br.com.condominiosvirtuais.service.BoletoService;
import br.com.condominiosvirtuais.service.CondominoService;
import br.com.condominiosvirtuais.service.ContaBancariaService;
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
	
	private ListDataModel<Boleto> listaBoletos;
	
	private Boleto boleto;	
	
	private Date dataVencimentoDe;
	
	private Date dataVencimentoAte;
	
	
	
	public BoletoMB(){
		this.boleto = new Boleto();
		this.boleto.setEmissao(new Date());
	}
	
	@PostConstruct
	public void iniciarBoletoMB(){
		this.listaSICondominios = this.condominioMB.get().buscarListaCondominiosAtivos();
		this.popularTitulos();
	}
	
	public String salvar(){
		try {
			if(this.boleto.getVencimento().before(this.boleto.getEmissao())){
				ManagedBeanUtil.setMensagemErro("msg.boleto.vencimentoMenorEmissao");
			}
			this.boletoService.salvar(this.boleto);
			ManagedBeanUtil.setMensagemInfo("msg.boleto.salvoSucesso");
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
		return "salvar";
	}
	
	public void pesquisar(){
		try {
			 this.listaBoletos = new ListDataModel<Boleto>(this.boletoService.buscarPorIdCondominioEDataVencimento(this.boleto.getIdCondominio(), this.dataVencimentoDe, this.dataVencimentoAte));
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
	
	public void popularContasBancarias() throws SQLException, Exception{
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
	

}
