package br.com.condominiosvirtuais.controller;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.log4j.Logger;

import br.com.condominiosvirtuais.entity.Agendamento;
import br.com.condominiosvirtuais.entity.Bloco;
import br.com.condominiosvirtuais.entity.Condominio;
import br.com.condominiosvirtuais.entity.Condomino;
import br.com.condominiosvirtuais.entity.GrupoUsuario;
import br.com.condominiosvirtuais.entity.SindicoProfissional;
import br.com.condominiosvirtuais.entity.Unidade;
import br.com.condominiosvirtuais.entity.Usuario;
import br.com.condominiosvirtuais.enumeration.AgendamentoSituacaoEnum;
import br.com.condominiosvirtuais.enumeration.AgendamentoTipoEnum;
import br.com.condominiosvirtuais.enumeration.GrupoUsuarioTipoUsuarioEnum;
import br.com.condominiosvirtuais.service.AgendamentoService;
import br.com.condominiosvirtuais.service.BlocoService;
import br.com.condominiosvirtuais.service.CondominioService;
import br.com.condominiosvirtuais.service.CondominoService;
import br.com.condominiosvirtuais.service.SindicoProfissionalService;
import br.com.condominiosvirtuais.service.UnidadeService;
import br.com.condominiosvirtuais.util.AplicacaoUtil;
import br.com.condominiosvirtuais.util.ManagedBeanUtil;
import br.com.condominiosvirtuais.vo.AgendamentoVO;

@Named  @SessionScoped
public class AgendamentoMB implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private static Logger logger = Logger.getLogger(AgendamentoMB.class);
	
	private static final String TODAS = "TODAS";
	
	private static final String TODOS = "TODOS";
	
	private ListDataModel<AgendamentoVO> listaDeAgendamentoVOs = null;
	
	private ListDataModel<AgendamentoVO> listaDeAgendamentoPendentesVOs = null;
	
	private ListDataModel<Agendamento> listaMeusAgendamentos = null;
	
	private Agendamento agendamento;	                    
	
	private Condominio condominio;
	
	private Condomino condomino;
	
	private List<SelectItem> listaSIHoraInicial = null;
	
	private List<SelectItem> listaSIHoraFinal = null;
	 
	private List<SelectItem> listaSICondominios  = new ArrayList<SelectItem>();
	
	private List<SelectItem> listaSITipo  = new ArrayList<SelectItem>();
	
	private List<SelectItem> listaSISituacaoAgendamento = null;
	
	private Integer diaSemana = 1;	
	
	@Inject
	private AgendamentoService agendamentoService;
	
	@Inject
	private CondominioService condominioService;
	
	@Inject
	private CondominoService condominoService;
	
	@Inject
	private SindicoProfissionalService sindicoProfissionalService;
	
	@Inject
	private UnidadeService unidadeService;
	
	@Inject
	private BlocoService blocoService;
		
	@Inject
	private CondominioMB condominioMB = null;
	
	
	@PostConstruct
	public void populaHoraInicialEFinal(){
		this.condominio = new Condominio();
		this.agendamento = new Agendamento();
		if (diaSemana == 1){
			this.popularHoraInicialEFinalDiaSemana();
		}else{
			this.popularHoraInicialEFinalSabado();
		}
		try {
			if(this.tipoUsuarioEhSindicoProfissional(AplicacaoUtil.getUsuarioAutenticado())){
				this.descobrirCondominioSindicoProfissional();
			}else{
				this.descobrirCondominio();
				this.popularListaMeusAgendamentos();
			}
			this.populaSituacao();
			this.popularTipo();
		} catch (SQLException e) {
			logger.error("erro sqlstate "+e.getSQLState(), e);	
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		} catch (Exception e) {
			logger.error("", e);
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		}	
	}
	
	private Boolean tipoUsuarioEhSindicoProfissional(Usuario usuario){
		for (GrupoUsuario grupoUsuario : usuario.getListaGrupoUsuario()) {
			if(grupoUsuario.getTipoUsuario() == GrupoUsuarioTipoUsuarioEnum.SINDICO_PROFISSIONAL.getTipoUsuario()){
				return Boolean.TRUE;
			}
		}
		return Boolean.FALSE;
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
			this.agendamento.setSituacao(AgendamentoSituacaoEnum.PENDENTE.getSituacao());
			this.agendamento.setUnidade(this.unidadeService.buscarPorId(this.condomino.getIdUnidade()));
			this.agendamento.setBloco(this.blocoService.buscarPorId(this.agendamento.getUnidade().getIdBloco()));
			this.agendamento.setCondomino(this.condomino);
			// FIXME: Regra temporária. Essa deve ser criada para um modelo genérico
			if(this.condominio.getId() == 8){
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(new Date());
				calendar.add(Calendar.HOUR_OF_DAY, 48);
				if(this.agendamento.getData().before(calendar.getTime())){	
					FacesMessage facesMessage = new FacesMessage("O período de agendamento deve ser superior a 48 horas");
					facesMessage.setSeverity(FacesMessage.SEVERITY_ERROR);
					FacesContext context  = FacesContext.getCurrentInstance();
					context.addMessage(null, facesMessage);
					return null;
				}
				// Regra para não deixar um condômino fazer agendamento de mudança para o mesmo bloco no mesmo dia. 
				List<Agendamento> listaAgendamento = this.agendamentoService.buscarPorCondominioEData(this.condominio, this.agendamento.getData());
				if(!listaAgendamento.isEmpty()){
					Condomino condomino = this.condominoService.buscarPorId(AplicacaoUtil.getUsuarioAutenticado().getId());
					Unidade unidade = this.unidadeService.buscarPorId(condomino.getIdUnidade());
					Bloco bloco = this.blocoService.buscarPorId(unidade.getIdBloco());					
					for (Agendamento agendamento : listaAgendamento) {
						if(agendamento.getBloco().getId().intValue() == bloco.getId().intValue()){
							FacesMessage facesMessage = new FacesMessage("Já existe um agendamento para a "+bloco.getNome()+" nesse dia.");
							facesMessage.setSeverity(FacesMessage.SEVERITY_ERROR);
							FacesContext context  = FacesContext.getCurrentInstance();
							context.addMessage(null, facesMessage);
							return null;
						}
					}
				}
			}			
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
			listaDeAgendamentoLocal = this.agendamentoService.buscarPorCondominioESituacao(this.condominio, AgendamentoSituacaoEnum.PENDENTE.getSituacao());
			for (Agendamento agendamento : listaDeAgendamentoLocal) {				
				agendamentoVO = new AgendamentoVO();
				agendamentoVO.setId(agendamento.getId());				
				agendamentoVO.setData(agendamento.getData());
				agendamentoVO.setHoraInicial(agendamento.getHoraInicial());
				agendamentoVO.setHoraFinal(agendamento.getHoraFinal());
				agendamentoVO.setSituacao(agendamento.getSituacaoI18n());
				agendamentoVO.setTipo(agendamento.getTipoI18n());
				agendamentoVO.setUnidade(agendamento.getUnidade());				
				agendamentoVO.setBloco(agendamento.getBloco());
				agendamentoVO.setCondomino(agendamento.getCondomino());
				listaDeAgendamentoLocalVOs.add(agendamentoVO);			
			}
			this.listaDeAgendamentoPendentesVOs = new ListDataModel<AgendamentoVO>(listaDeAgendamentoLocalVOs);		
			if (this.listaDeAgendamentoPendentesVOs.getRowCount() == 0){
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
		this.listaDeAgendamentoPendentesVOs = new ListDataModel<AgendamentoVO>();
	}
	
	public void popularListaAgendamentos(){		
		List<Agendamento> listaDeAgendamentoLocal = null;
		List<AgendamentoVO> listaDeAgendamentoLocalVOs = new ArrayList<AgendamentoVO>();		
		AgendamentoVO agendamentoVO = null;
		try {
			if (this.agendamento.getSituacao().equals(TODAS)){
				if(this.agendamento.getTipo().equals(TODOS)){
					listaDeAgendamentoLocal = this.agendamentoService.buscarPorCondominio(this.condominio);
				}else{
					listaDeAgendamentoLocal = this.agendamentoService.buscarPorCondominioETipo(this.condominio, this.agendamento.getTipo());
				}
			}else{
				if(this.agendamento.getTipo().equals(TODOS)){
					listaDeAgendamentoLocal = this.agendamentoService.buscarPorCondominioESituacao(this.condominio, this.agendamento.getSituacao());
				}else{
					listaDeAgendamentoLocal = this.agendamentoService.buscarPorCondominioESituacaoETipo(this.condominio, this.agendamento.getSituacao(),this.agendamento.getTipo());
				}
			}
			for (Agendamento agendamento : listaDeAgendamentoLocal) {
				agendamentoVO = new AgendamentoVO();
				agendamentoVO.setId(agendamento.getId());				
				agendamentoVO.setData(agendamento.getData());
				agendamentoVO.setHoraInicial(agendamento.getHoraInicial());
				agendamentoVO.setHoraFinal(agendamento.getHoraFinal());
				agendamentoVO.setSituacao(agendamento.getSituacaoI18n());
				agendamentoVO.setTipo(agendamento.getTipoI18n());
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
		AgendamentoVO agendamentoVO = this.listaDeAgendamentoPendentesVOs.getRowData();
		try {
			Agendamento agendamentoLocal = new Agendamento();
			agendamentoLocal.setId(agendamentoVO.getId());		
			agendamentoLocal.setTipo(agendamentoVO.getTipo());
			agendamentoLocal.setSituacao(AgendamentoSituacaoEnum.APROVADA.getSituacao());
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
		AgendamentoVO agendamentoVO = this.listaDeAgendamentoPendentesVOs.getRowData();		
		try {
			if(agendamentoVO.getMotivoReprovacao().trim().equals("")){
				ManagedBeanUtil.setMensagemErro("msg.agendamentoMudança.motivoReprovacaoObrigatorio");
			}else{
				Agendamento agendamentoLocal = new Agendamento();
				agendamentoLocal.setId(agendamentoVO.getId());	
				agendamentoLocal.setTipo(agendamentoVO.getTipo());
				agendamentoLocal.setSituacao(AgendamentoSituacaoEnum.REPROVADA.getSituacao());	
				agendamentoLocal.setBloco(agendamentoVO.getBloco());
				agendamentoLocal.setUnidade(agendamentoVO.getUnidade());
				agendamentoLocal.setCondomino(agendamentoVO.getCondomino());
				agendamentoLocal.setData(agendamentoVO.getData());
				agendamentoLocal.setHoraInicial(agendamentoVO.getHoraInicial());
				agendamentoLocal.setHoraFinal(agendamentoVO.getHoraFinal());
				agendamentoLocal.setMotivoReprovacao(agendamentoVO.getMotivoReprovacao());
				this.agendamentoService.reprovar(agendamentoLocal);			
				this.buscarListaAprovarAgendamento();
				ManagedBeanUtil.setMensagemInfo("msg.agendamentoMudança.reprovadaSucesso");
			}
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
	
	private void descobrirCondominioSindicoProfissional() throws SQLException, Exception{
		Condominio condominioLocal = null;
		Boolean encontrou = Boolean.FALSE;
		Integer idSindicoProfissional = 0;
		SindicoProfissional sindicoProfissional = this.sindicoProfissionalService.buscarPorId(AplicacaoUtil.getUsuarioAutenticado().getId());
		while(encontrou == Boolean.FALSE && this.condominio.getId() != null){
			condominioLocal = sindicoProfissional.getListaCondominio().get(idSindicoProfissional++);
			if(condominioLocal.getId().intValue() == this.condominio.getId().intValue()){
				this.condominio = condominioLocal;
				encontrou = Boolean.TRUE;
			}			
		}
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
	
	private void popularTipo(){
		this.listaSITipo = new ArrayList<SelectItem>();
		this.listaSITipo.add(new SelectItem(AgendamentoTipoEnum.ENTRADA.getTipo(), AplicacaoUtil.i18n(AgendamentoTipoEnum.ENTRADA.getTipo())));
		this.listaSITipo.add(new SelectItem(AgendamentoTipoEnum.SAIDA.getTipo(), AplicacaoUtil.i18n(AgendamentoTipoEnum.SAIDA.getTipo())));
	}
	
	
	private void populaSituacao(){
		this.listaSISituacaoAgendamento = new ArrayList<SelectItem>();
		this.listaSISituacaoAgendamento.add(new SelectItem(AgendamentoSituacaoEnum.APROVADA.getSituacao(), AplicacaoUtil.i18n(AgendamentoSituacaoEnum.APROVADA.getSituacao())));
		this.listaSISituacaoAgendamento.add(new SelectItem(AgendamentoSituacaoEnum.PENDENTE.getSituacao(), AplicacaoUtil.i18n(AgendamentoSituacaoEnum.PENDENTE.getSituacao())));
		this.listaSISituacaoAgendamento.add(new SelectItem(AgendamentoSituacaoEnum.REPROVADA.getSituacao(), AplicacaoUtil.i18n(AgendamentoSituacaoEnum.REPROVADA.getSituacao())));
		this.listaSISituacaoAgendamento.add(new SelectItem(TODAS,AplicacaoUtil.i18n("todas")));
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

	public List<SelectItem> getListaSISituacaoAgendamento() {
		return listaSISituacaoAgendamento;
	}

	public void setListaSISituacaoAgendamento(List<SelectItem> listaSISituacaoAgendamento) {
		this.listaSISituacaoAgendamento = listaSISituacaoAgendamento;
	}

	public ListDataModel<AgendamentoVO> getListaDeAgendamentoPendentesVOs() {
		return listaDeAgendamentoPendentesVOs;
	}

	public void setListaDeAgendamentoPendentesVOs(ListDataModel<AgendamentoVO> listaDeAgendamentoPendentesVOs) {
		this.listaDeAgendamentoPendentesVOs = listaDeAgendamentoPendentesVOs;
	}
	
	public List<SelectItem> getListaSITipo() {
		return listaSITipo;
	}

	public void setListaSITipo(List<SelectItem> listaSITipo) {
		this.listaSITipo = listaSITipo;
	}
	

}
