package br.com.condominiosvirtuais.controller;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.event.ActionEvent;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.log4j.Logger;

import br.com.condominiosvirtuais.entity.Agendamento;
import br.com.condominiosvirtuais.entity.Condominio;
import br.com.condominiosvirtuais.entity.Condomino;
import br.com.condominiosvirtuais.enumeration.AgendamentoEnum;
import br.com.condominiosvirtuais.service.AgendamentoService;
import br.com.condominiosvirtuais.service.BlocoService;
import br.com.condominiosvirtuais.service.CondominioService;
import br.com.condominiosvirtuais.service.CondominoService;
import br.com.condominiosvirtuais.service.UnidadeService;
import br.com.condominiosvirtuais.util.AplicacaoUtil;
import br.com.condominiosvirtuais.util.ManagedBeanUtil;
import br.com.condominiosvirtuais.vo.AgendamentoVO;

@Named  @SessionScoped
public class AgendamentoMB implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private static Logger logger = Logger.getLogger(AgendamentoMB.class);
	
	private ListDataModel<AgendamentoVO> listaDeAgendamentoVOs = null;
	
	private ListDataModel<Agendamento> listaMeusAgendamentos = null;
	
	private Agendamento agendamento;	                    
	
	private Condominio condominio;
	
	private Condomino condomino;
	
	private List<SelectItem> listaSIHoraInicial = null;
	
	private List<SelectItem> listaSIHoraFinal = null;
	 
	private List<SelectItem> listaSICondominios  = new ArrayList<SelectItem>();
	
	private Integer diaSemana = 1;
	
	@Inject
	private AgendamentoService agendamentoService;
	
	@Inject
	private CondominioService condominioService;
	
	@Inject
	private CondominoService condominoService;
	
	@Inject
	private UnidadeService unidadeService;
	
	@Inject
	private BlocoService blocoService;
		
	@Inject
	private CondominioMB condominioMB = null;
	
	
	@PostConstruct
	public void populaHoraInicialEFinal(){		 
		this.agendamento = new Agendamento();
		if (diaSemana == 1){
			this.popularHoraInicialEFinalDiaSemana();
		}else{
			this.popularHoraInicialEFinalSabado();
		}
		try {
			this.descobrirCondominio();
			this.popularListaMeusAgendamentos();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	public String solicitarAgendamento() {
		if(!this.validaHoraFinalMaiorQueHoraInicial()){
			ManagedBeanUtil.setMensagemErro("msg.agendamentoMudança.horaInicial.maior.horaFinal");
			return null;
		}
		if(!this.validaSabado()){
			ManagedBeanUtil.setMensagemErro("msg.agendamentoMudança.diaFinalDeSemanaDiferenteSabado");
			return null;
		}
		if(!this.validaDiaSemana()){
			ManagedBeanUtil.setMensagemErro("msg.agendamentoMudança.naoEhDiaDeSemana");
			return null;
		}
		try {
			this.agendamento.setSituacao(AgendamentoEnum.PENDENTE.getSituacao());
			this.agendamento.setUnidade(this.unidadeService.buscarPorId(this.condomino.getIdUnidade()));
			this.agendamento.setBloco(this.blocoService.buscarPorId(this.agendamento.getUnidade().getIdBloco()));
			this.agendamento.setCondomino(this.condomino);
			this.agendamentoService.solicitarAgendamento(this.agendamento,this.condominio.getSindicoGeral().getNome(),this.condominio.getSindicoGeral().getEmail().getEmail());
			this.agendamento = new Agendamento();	
			this.popularListaMeusAgendamentos();
			ManagedBeanUtil.setMensagemInfo("msg.agendamentoMudança.solicitacaoAgendamento");
		} catch (Exception e) {
			logger.error("", e);	
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		}
		return null;
	}
	
	public void buscarListaAprovarAgendamento() {
		List<Agendamento> listaDeAgendamentoLocal = null;
		List<AgendamentoVO> listaDeAgendamentoLocalVOs = new ArrayList<AgendamentoVO>();
		AgendamentoVO agendamentoVO = null;
		try {
			listaDeAgendamentoLocal = this.agendamentoService.buscarPorCondominioETipo(this.condominio, AgendamentoEnum.PENDENTE.getSituacao());
			for (Agendamento agendamento : listaDeAgendamentoLocal) {				
				agendamentoVO = new AgendamentoVO();
				agendamentoVO.setId(agendamento.getId());				
				agendamentoVO.setData(agendamento.getData());
				agendamentoVO.setHoraInicial(agendamento.getHoraInicial());
				agendamentoVO.setHoraFinal(agendamento.getHoraFinal());
				agendamentoVO.setSituacao(agendamento.getSituacaoI18n());
				agendamentoVO.setUnidade(agendamento.getUnidade());				
				agendamentoVO.setBloco(agendamento.getBloco());
				agendamentoVO.setCondomino(agendamento.getCondomino());
				listaDeAgendamentoLocalVOs.add(agendamentoVO);			
			}
			this.listaDeAgendamentoVOs = new ListDataModel<AgendamentoVO>(listaDeAgendamentoLocalVOs);		
			if (this.listaDeAgendamentoVOs.getRowCount() == 0){
				ManagedBeanUtil.setMensagemInfo("msg.agendamentoMudança.semAgendamento");
			}
		} catch (SQLException e) {
			logger.error("erro sqlstate "+e.getSQLState(), e);	
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		} catch (Exception e) {
			logger.error("", e);
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		}	
	}	
	
	
	public void popularListaMeusAgendamentos(){				
		try {			
			this.listaMeusAgendamentos =  new ListDataModel<Agendamento> (this.agendamentoService.buscarPorCondomino(this.condomino));	
		} catch (NumberFormatException e) {
			logger.error("", e);
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		} catch (SQLException e) {
			logger.error("erro sqlstate "+e.getSQLState(), e);	
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		} catch (Exception e) {
			logger.error("", e);
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		}
	}
	
	public String limparCadastroAgendamento() {
		this.agendamento = new Agendamento();
		this.listaMeusAgendamentos =  new ListDataModel<Agendamento>();
		return null;
	}
	
	public void limparListarAgendamento(ActionEvent event) {
		this.agendamento = new Agendamento();
		this.listaDeAgendamentoVOs = new ListDataModel<AgendamentoVO>();
	}
	
	public void popularListaAgendamentos(){		
		List<Agendamento> listaDeAgendamentoLocal = null;
		List<AgendamentoVO> listaDeAgendamentoLocalVOs = new ArrayList<AgendamentoVO>();		
		AgendamentoVO agendamentoVO = null;
		try {			
			listaDeAgendamentoLocal = this.agendamentoService.buscarPorCondominio(this.condominio);
			for (Agendamento agendamento : listaDeAgendamentoLocal) {
				agendamentoVO = new AgendamentoVO();
				agendamentoVO.setId(agendamento.getId());				
				agendamentoVO.setData(agendamento.getData());
				agendamentoVO.setHoraInicial(agendamento.getHoraInicial());
				agendamentoVO.setHoraFinal(agendamento.getHoraFinal());
				agendamentoVO.setSituacao(agendamento.getSituacaoI18n());
				agendamentoVO.setUnidade(agendamento.getUnidade());				
				agendamentoVO.setBloco(agendamento.getBloco());
				agendamentoVO.setCondomino(agendamento.getCondomino());
				agendamentoVO.setMotivoReprovacao(agendamento.getMotivoReprovacao());
				listaDeAgendamentoLocalVOs.add(agendamentoVO);			
			}
			this.listaDeAgendamentoVOs = new ListDataModel<AgendamentoVO>(listaDeAgendamentoLocalVOs);		
			if (this.listaDeAgendamentoVOs.getRowCount() == 0){
				ManagedBeanUtil.setMensagemInfo("msg.agendamentoMudança.semAgendamento");
			}
		} catch (SQLException e) {
			logger.error("erro sqlstate "+e.getSQLState(), e);	
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		} catch (Exception e) {
			logger.error("", e);
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		}
	}
	
	public void aprovarAgendamento(ActionEvent event){		
		AgendamentoVO agendamentoVO = this.listaDeAgendamentoVOs.getRowData();
		try {
			Agendamento agendamentoLocal = new Agendamento();
			agendamentoLocal.setId(agendamentoVO.getId());			
			agendamentoLocal.setSituacao(AgendamentoEnum.APROVADA.getSituacao());
			agendamentoLocal.setBloco(agendamentoVO.getBloco());
			agendamentoLocal.setUnidade(agendamentoVO.getUnidade());
			agendamentoLocal.setCondomino(agendamentoVO.getCondomino());
			agendamentoLocal.setData(agendamentoVO.getData());
			agendamentoLocal.setHoraInicial(agendamentoVO.getHoraInicial());
			agendamentoLocal.setHoraFinal(agendamentoVO.getHoraFinal());
			this.agendamentoService.aprovar(agendamentoLocal);			
			this.buscarListaAprovarAgendamento();
			ManagedBeanUtil.setMensagemInfo("msg.agendamentoMudança.aprovadaSucesso");
		} catch (SQLException e) {
			logger.error("erro sqlstate "+e.getSQLState(), e);	
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		} catch (Exception e) {
			logger.error("", e);
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		}
	}
	
	public void reprovarAgendamento(ActionEvent event){		
		AgendamentoVO agendamentoVO = this.listaDeAgendamentoVOs.getRowData();
		try {
			Agendamento agendamentoLocal = new Agendamento();
			agendamentoLocal.setId(agendamentoVO.getId());			
			agendamentoLocal.setSituacao(AgendamentoEnum.REPROVADA.getSituacao());	
			agendamentoLocal.setBloco(agendamentoVO.getBloco());
			agendamentoLocal.setUnidade(agendamentoVO.getUnidade());
			agendamentoLocal.setCondomino(agendamentoVO.getCondomino());
			agendamentoLocal.setData(agendamentoVO.getData());
			agendamentoLocal.setHoraInicial(agendamentoVO.getHoraInicial());
			agendamentoLocal.setHoraFinal(agendamentoVO.getHoraFinal());
			agendamentoLocal.setMotivoReprovacao(agendamentoVO.getMotivoReprovacao());
			agendamentoService.reprovar(agendamentoLocal);			
			this.buscarListaAprovarAgendamento();
			ManagedBeanUtil.setMensagemInfo("msg.agendamentoMudança.reprovadaSucesso");
		} catch (SQLException e) {
			logger.error("erro sqlstate "+e.getSQLState(), e);	
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		} catch (Exception e) {
			logger.error("", e);
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		}
	}
	
	
	private void popularHoraInicialEFinalDiaSemana(){
		this.listaSIHoraInicial = new ArrayList<SelectItem>();	
		listaSIHoraInicial.add(new SelectItem("08", "08:00"));
		listaSIHoraInicial.add(new SelectItem("09", "09:00"));
		listaSIHoraInicial.add(new SelectItem("10", "10:00"));
		listaSIHoraInicial.add(new SelectItem("11", "11:00"));		
		listaSIHoraInicial.add(new SelectItem("13", "13:00"));
		listaSIHoraInicial.add(new SelectItem("14", "14:00"));
		listaSIHoraInicial.add(new SelectItem("15", "15:00"));
		listaSIHoraInicial.add(new SelectItem("16", "16:00"));		
		
		this.listaSIHoraFinal = new ArrayList<SelectItem>();	
		listaSIHoraFinal.add(new SelectItem("09", "09:00"));
		listaSIHoraFinal.add(new SelectItem("10", "10:00"));
		listaSIHoraFinal.add(new SelectItem("11", "11:00"));
		listaSIHoraFinal.add(new SelectItem("12", "12:00"));		
		listaSIHoraFinal.add(new SelectItem("14", "14:00"));
		listaSIHoraFinal.add(new SelectItem("15", "15:00"));
		listaSIHoraFinal.add(new SelectItem("16", "16:00"));
		listaSIHoraFinal.add(new SelectItem("17", "17:00"));
	}
	
	private void popularHoraInicialEFinalSabado(){
		this.listaSIHoraInicial = new ArrayList<SelectItem>();	
		listaSIHoraInicial.add(new SelectItem("09", "09:00"));
		listaSIHoraInicial.add(new SelectItem("10", "10:00"));
		listaSIHoraInicial.add(new SelectItem("11", "11:00"));
		listaSIHoraInicial.add(new SelectItem("12", "12:00"));
		
		this.listaSIHoraFinal = new ArrayList<SelectItem>();	
		listaSIHoraFinal.add(new SelectItem("10", "10:00"));
		listaSIHoraFinal.add(new SelectItem("11", "11:00"));
		listaSIHoraFinal.add(new SelectItem("12", "12:00"));
		listaSIHoraFinal.add(new SelectItem("13", "13:00"));		
	}
	
	private void descobrirCondominio() throws SQLException, Exception{
		this.condomino = this.condominoService.buscarPorId(AplicacaoUtil.getUsuarioAutenticado().getId());
		this.condominio = this.condominioService.buscarPorCondomino(this.condomino);
	}
	
	private Boolean validaHoraFinalMaiorQueHoraInicial(){
		Boolean maior = Boolean.TRUE;
		Integer horaInicial = Integer.parseInt(this.agendamento.getHoraInicial().substring(0, 2));
		Integer horaFinal = Integer.parseInt(this.agendamento.getHoraFinal().substring(0, 2));
		if(horaInicial > horaFinal) {
			maior = Boolean.FALSE;
		}		
		return maior;
	}
	
	private Boolean validaSabado(){
		Boolean ehSabado = Boolean.TRUE;
		// Se escolheu sábado, então 
		if (this.diaSemana == 0){
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(this.agendamento.getData()); 
			if (calendar.get(Calendar.DAY_OF_WEEK) != 7){
				ehSabado = Boolean.FALSE;
			}			
		}
		return ehSabado;
	}
	
	private Boolean validaDiaSemana(){
		Boolean ehDiaSemana = Boolean.TRUE;
		// Se escolheu sábado, então 
		if (this.diaSemana == 1){
			Calendar callendar = Calendar.getInstance();
			callendar.setTime(this.agendamento.getData()); 
			if (callendar.get(Calendar.DAY_OF_WEEK) == 7 || callendar.get(Calendar.DAY_OF_WEEK) == 1){
				ehDiaSemana = Boolean.FALSE;
			}			
		}
		return ehDiaSemana;
		
	}

	public Agendamento getAgendamento() {
		return agendamento;
	}

	public void setAgendamento(Agendamento agendamento) {
		this.agendamento = agendamento;
	}


	public List<SelectItem> getListaSIHoraInicial() {
		return listaSIHoraInicial;
	}


	public void setListaSIHoraInicial(List<SelectItem> listaSIHoraInicial) {
		this.listaSIHoraInicial = listaSIHoraInicial;
	}


	public List<SelectItem> getListaSIHoraFinal() {
		return listaSIHoraFinal;
	}


	public void setListaSIHoraFinal(List<SelectItem> listaSIHoraFinal) {
		this.listaSIHoraFinal = listaSIHoraFinal;
	}


	public Integer getDiaSemana() {
		return diaSemana;
	}


	public void setDiaSemana(Integer diaSemana) {
		this.diaSemana = diaSemana;
	}

	public Condominio getCondominio() {
		return condominio;
	}

	public void setCondominio(Condominio condominio) {
		this.condominio = condominio;
	}

	public List<SelectItem> getListaSICondominios() {
		this.listaSICondominios =  this.condominioMB.buscarListaCondominiosAtivos();
		return listaSICondominios;
	}

	public void setListaSICondominios(List<SelectItem> listaSICondominios) {
		this.listaSICondominios = listaSICondominios;
	}

	public ListDataModel<AgendamentoVO> getListaDeAgendamentoVOs() {
		return listaDeAgendamentoVOs;
	}

	public void setListaDeAgendamentoVOs(ListDataModel<AgendamentoVO> listaDeAgendamentoVOs) {
		this.listaDeAgendamentoVOs = listaDeAgendamentoVOs;
	}

	public ListDataModel<Agendamento> getListaMeusAgendamentos() {
		return listaMeusAgendamentos;
	}
	
	public void setListaMeusAgendamentos(ListDataModel<Agendamento> listaMeusAgendamentos) {
		this.listaMeusAgendamentos = listaMeusAgendamentos;
	}		
	

}
