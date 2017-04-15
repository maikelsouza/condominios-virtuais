package br.com.condominiosvirtuais.controller;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UISelectOne;
import javax.faces.event.ActionEvent;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.log4j.Logger;
import org.richfaces.component.UIAutocomplete;
import org.richfaces.component.UICalendar;

import br.com.condominiosvirtuais.entity.Ambiente;
import br.com.condominiosvirtuais.entity.Bloco;
import br.com.condominiosvirtuais.entity.Condominio;
import br.com.condominiosvirtuais.entity.Condomino;
import br.com.condominiosvirtuais.entity.Reserva;
import br.com.condominiosvirtuais.entity.Unidade;
import br.com.condominiosvirtuais.enumeration.ReservaSituacaoEnum;
import br.com.condominiosvirtuais.exception.BusinessException;
import br.com.condominiosvirtuais.service.AmbienteService;
import br.com.condominiosvirtuais.service.BlocoService;
import br.com.condominiosvirtuais.service.CondominioService;
import br.com.condominiosvirtuais.service.CondominoService;
import br.com.condominiosvirtuais.service.ReservaService;
import br.com.condominiosvirtuais.service.UnidadeService;
import br.com.condominiosvirtuais.util.AplicacaoUtil;
import br.com.condominiosvirtuais.util.ManagedBeanUtil;
import br.com.condominiosvirtuais.vo.CondominoVO;
import br.com.condominiosvirtuais.vo.ReservaVO;

@Named @SessionScoped
public class ReservaMB implements IConversationScopeMB, Serializable{
	
	private static final long serialVersionUID = 1L;

	private static Logger logger = Logger.getLogger(ReservaMB.class);
	
	@Inject
	private Conversation conversation;
	
	private Reserva reserva = null;
	
	private Ambiente ambiente = null;
	
	private Bloco bloco;
	
	private Condomino condomino = null;

	private Condominio condominio = null;
	
	private Integer idCondomino = null;
	
	private String nomeCondomino  = "";

	private ListDataModel<Reserva> listaMinhasReservas = null;
	
	private ListDataModel<ReservaVO> listaDeReservasVOs = null;
	
	private ListDataModel<ReservaVO> listaReservasVOs = null;
	
	private List<SelectItem> listaSICondominios = new ArrayList<SelectItem>();
	
	private List<SelectItem> listaSIBlocos = new ArrayList<SelectItem>();
	
	private List<SelectItem> listaSICondominos = new ArrayList<SelectItem>();	
	
	private List<SelectItem> listaSIMeusAmbientes = null;
	
	private List<SelectItem> listaSISituacoes = null;
	
	private List<SelectItem> listaSIAmbientes = null;
	
	private List<CondominoVO> listaDeCondominosVOs = null;	
	
	@Inject
	private ReservaService reservaService = null;
	
	@Inject
	private AmbienteService ambienteService = null;
	
	@Inject
	private CondominoService condominoService = null;
	
	@Inject
	private UnidadeService unidadeService = null;
	
	@Inject
	private BlocoService blocoService = null;
	
	@Inject
	private CondominioService condominioService = null;	
	
	@Inject
	private CondominioMB condominioMB = null;
	
	@Inject
	private CondominoMB condominoMB = null;
	
	@Inject
	private BlocoMB blocoMB = null;	 
	
	private Boolean campoCondominoDesabilitado = null;
	
	private Integer idAmbiente = null;
	
	private ReservaVO reservaVO;
	
	private UICalendar componenteDataReserva = null;
	
	private UIAutocomplete componenteNomeCondomino;
	
	private UISelectOne componenteCondominio;	
	
	private static final String TODAS = "todas";
	
	
	public ReservaMB(){
		this.reserva = new Reserva();
		this.condominio = new Condominio();
		this.bloco = new Bloco();
		this.condomino = new Condomino();
	}
	
	@PostConstruct
	public void carregarListaSICondomimios() throws SQLException, Exception{
		//this.carregarMeusAmbientes();
		//this.habilitaCamboCondomino();
		this.popularSituacaoes();
	}
	
	public String salvarReserva(){
		try{							
			this.reserva.getCondomino().setId(this.condomino.getId());			
			this.reserva.setSituacao(ReservaSituacaoEnum.PENDENTE.getSituacao());
			this.condominio = this.condominioService.buscarPorCondomino(this.condomino);
			this.populaNomeMeusAmbiente();
			this.reservaService.solicitar(this.reserva,this.condominio.getSindicoGeral().getEmail().getEmail(),this.condominio.getSindicoGeral().getNome() );
			this.popularListaMinhasReservas();
			this.reserva = new Reserva();
			ManagedBeanUtil.setMensagemInfo("msg.reserva.salvaSucesso");		
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
		return null;
	}
	
	public String salvarReservaPeloFuncionario(){
		try{							
			this.condomino = condominoService.buscarPorId(this.condomino.getId());
			this.reserva.getCondomino().setId(this.condomino.getId());			
			this.reserva.setSituacao(ReservaSituacaoEnum.PENDENTE.getSituacao());
			this.condominio = this.condominioService.buscarPorCondomino(this.condomino);
			this.populaNomeAmbiente();
			this.reservaService.solicitar(this.reserva,this.condominio.getSindicoGeral().getEmail().getEmail(),this.condominio.getSindicoGeral().getNome() );
			this.popularListaMinhasReservas();
			ManagedBeanUtil.setMensagemInfo("msg.reserva.salvaSucesso");		
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
		return null;
	}
	
	public List<SelectItem> getListaAmbientes() {
		return this.listaSIAmbientes;
	}
	
	public List<SelectItem> getListaMeusAmbientes() {
		return this.listaSIMeusAmbientes;
	}
	
	public String editarReserva(){		
		this.reserva = this.listaMinhasReservas.getRowData();
		// Necessário para exibir o campo data
		this.idAmbiente = this.reserva.getAmbiente().getId();
	//	if (this.componenteDataReserva != null) this.componenteDataReserva.setRDisabled(false);
		return "editar";
	}
	
	public void aprovarReserva(ActionEvent event){
		ReservaVO reservaVO = this.listaDeReservasVOs.getRowData();
		try {
			Reserva reservaLocal = new Reserva();
			reservaLocal.setId(reservaVO.getId());
			Ambiente ambiente = new Ambiente();
			ambiente.setId(reservaVO.getIdAmbiente());
			ambiente.setNome(reservaVO.getAmbiente());
			reservaLocal.setAmbiente(ambiente);
			Condomino condomino = this.condominoService.buscarPorId(reservaVO.getIdCondomino());
			condomino.setId(reservaVO.getIdCondomino());
			reservaLocal.setCondomino(condomino);
			reservaLocal.setData(reservaVO.getData());
			reservaLocal.setSituacao(ReservaSituacaoEnum.APROVADA.getSituacao());		
			this.reservaService.aprovar(reservaLocal);
			this.buscarListaAprovarReserva();
			ManagedBeanUtil.setMensagemInfo("msg.reserva.aprovadaSucesso");
		} catch (SQLException e) {
			logger.error("erro sqlstate "+e.getSQLState(), e);	
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		} catch (Exception e) {
			logger.error("", e);
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		}
	}
	
	public void reprovarReserva(ActionEvent event){
		try {
			ReservaVO reservaVO = this.listaDeReservasVOs.getRowData();
			if(reservaVO.getMotivoReprovacao().trim().equals("")){
				ManagedBeanUtil.setMensagemErro("msg.reserva.motivoReprovacaoObrigatorio");
			}else{
				Reserva reservaLocal = new Reserva();
				reservaLocal.setId(reservaVO.getId());
				Ambiente ambiente = new Ambiente();
				ambiente.setId(reservaVO.getIdAmbiente());
				ambiente.setNome(reservaVO.getAmbiente());
				reservaLocal.setAmbiente(ambiente);
				Condomino condomino = this.condominoService.buscarPorId(reservaVO.getIdCondomino());
				condomino.setId(reservaVO.getIdCondomino());
				reservaLocal.setCondomino(condomino);
				reservaLocal.setData(reservaVO.getData());				
				reservaLocal.setMotivoReprovacao(reservaVO.getMotivoReprovacao());			
				reservaLocal.setSituacao(ReservaSituacaoEnum.REPROVADA.getSituacao());
				this.reservaService.reprovar(reservaLocal);
				this.buscarListaAprovarReserva();
				ManagedBeanUtil.setMensagemInfo("msg.reserva.reprovadaSucesso");				
			}
		} catch (SQLException e) {
			logger.error("erro sqlstate "+e.getSQLState(), e);	
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		} catch (Exception e) {
			logger.error("", e);
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		}		
	}
	
	public void suspenderReserva(ActionEvent event){
		try {
			ReservaVO reservaVO = this.listaDeReservasVOs.getRowData();
			if(reservaVO.getMotivoSuspensao().trim().equals("")){
				ManagedBeanUtil.setMensagemErro("msg.reserva.motivoSuspensaoObrigatorio");
			}else{
				Reserva reservaLocal = new Reserva();
				reservaLocal.setId(reservaVO.getId());
				Ambiente ambiente = new Ambiente();
				ambiente.setId(reservaVO.getIdAmbiente());
				ambiente.setNome(reservaVO.getAmbiente());
				reservaLocal.setAmbiente(ambiente);
				Condomino condomino = this.condominoService.buscarPorId(reservaVO.getIdCondomino());
				condomino.setId(reservaVO.getIdCondomino());
				reservaLocal.setCondomino(condomino);
				reservaLocal.setData(reservaVO.getData());				
				reservaLocal.setMotivoSuspensao(reservaVO.getMotivoSuspensao());			
				reservaLocal.setSituacao(ReservaSituacaoEnum.SUSPENSA.getSituacao());
				this.reservaService.suspender(reservaLocal);
				this.buscarListaAprovarReserva();
				ManagedBeanUtil.setMensagemInfo("msg.reserva.suspensaoSucesso");				
			}
		} catch (SQLException e) {
			logger.error("erro sqlstate "+e.getSQLState(), e);	
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		} catch (Exception e) {
			logger.error("", e);
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		}		
	}
	
	public String atualizarReserva(){
		try {
			this.reservaService.atualizar(this.reserva);
			this.reserva = new Reserva();
			this.idAmbiente = null;
			this.listaSICondominios = new ArrayList<SelectItem>();
			ManagedBeanUtil.setMensagemInfo("msg.reserva.atualizadoSucesso");
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
	
	public String excluirReserva(){
		try {                                
			Reserva reserva = (Reserva) this.listaMinhasReservas.getRowData();
			this.reservaService.excluir(reserva);
			ManagedBeanUtil.setMensagemInfo("msg.reserva.excluirSucesso");
		} catch (SQLException e) {
			logger.error("erro sqlstate "+e.getSQLState(), e);	
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		} catch (Exception e) {
			logger.error("", e);
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		}
		return "excluir";
	}
	
	public String voltarListaReserva(){				
		return "voltar";
	}
	
	public void limparReserva(ActionEvent event){				
		this.carregarMeusAmbientes();
		this.reserva = new Reserva();			
	}

	
	public void limparCadastroReserva(ActionEvent event){
		this.reserva = new Reserva();				
		this.idAmbiente = null;
		this.nomeCondomino = "";
		this.condominio = new Condominio();
		this.campoCondominoDesabilitado = Boolean.TRUE;
		ManagedBeanUtil.cleanSubmittedValues(this.componenteCondominio);
	}
	
	public List<CondominoVO> buscarListaCondominoVO(Object nomeCondomino){		
		List<CondominoVO> listaCondominoVO = null;
		try {
			listaCondominoVO = this.condominoService.buscarListaCondominosVOPorNomeCondominoECondominio(nomeCondomino.toString(), this.condominio);
		} catch (SQLException e) {
			logger.error("erro sqlstate "+e.getSQLState(), e);	
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		} catch (Exception e) {
			logger.error("", e);
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		}	
		return listaCondominoVO;
	}
	
	public void buscarListaAprovarReserva() {
		List<Reserva> listaDeReservaLocol = null;
		List<ReservaVO> listaDeReservaLocolVOs = new ArrayList<ReservaVO>();
		List<String> listaSituacoes = new ArrayList<String>();
		listaSituacoes.add(ReservaSituacaoEnum.PENDENTE.getSituacao());
		listaSituacoes.add(ReservaSituacaoEnum.APROVADA.getSituacao());
		listaSituacoes.add(ReservaSituacaoEnum.SUSPENSA.getSituacao());
		Unidade unidade = null;
		Bloco bloco = null;
		ReservaVO reservaVO = null;
		try {			
			listaDeReservaLocol = this.reservaService.buscarPorCondominioESituacoesEAteData(this.condominio, listaSituacoes, new Date());
			for (Reserva reserva : listaDeReservaLocol) {
				reservaVO = new ReservaVO();
				reservaVO.setId(reserva.getId());
				reservaVO.setCondominio(this.condominio.getNome());
				reservaVO.setData(reserva.getData());
				reservaVO.setSituacao(reserva.getSituacaoI18n());
				reservaVO.setIdAmbiente(reserva.getAmbiente().getId());
				reservaVO.setAmbiente(reserva.getAmbiente().getNome());
				reservaVO.setIdCondomino(reserva.getCondomino().getId());
				reservaVO.setCondomino(reserva.getCondomino().getNome());
				unidade = this.unidadeService.buscarPorId(reserva.getCondomino().getIdUnidade());
				reservaVO.setUnidade(unidade.getNumero());
				bloco = this.blocoService.buscarPorId(unidade.getIdBloco());
				reservaVO.setBloco(bloco.getNome());
				listaDeReservaLocolVOs.add(reservaVO);			
			}
			this.listaDeReservasVOs = new ListDataModel<ReservaVO>(listaDeReservaLocolVOs);		
			if (this.listaDeReservasVOs.getRowCount() == 0){
				ManagedBeanUtil.setMensagemInfo("msg.reserva.semReservas");
			}
		} catch (SQLException e) {
			logger.error("erro sqlstate "+e.getSQLState(), e);	
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		} catch (Exception e) {
			logger.error("", e);
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		}	
	}	
	
	/**
	 * Método que encontra e id o condomino selecionado.	
	 * @throws Exception 
	 */
	public void popularIdCondomino()  {
		  this.idCondomino = Integer.parseInt(this.nomeCondomino);
		  Boolean encontrou = Boolean.FALSE;
		  Integer i = 0;
		  while(!encontrou){		  
			CondominoVO condominoVO = this.listaDeCondominosVOs.get(i++);
			if (condominoVO.getId().equals(this.idCondomino)){
				this.nomeCondomino= condominoVO .getNomeCondomino();
				this.popularListaSIAmbientes();
				encontrou = Boolean.TRUE;
			}
		}		  
	}
	
	public void habilitaCamboCondomino(){		
		this.campoCondominoDesabilitado = Boolean.TRUE;	
		this.nomeCondomino = "";
		this.listaSIAmbientes = new ArrayList<SelectItem>();
		if(this.condominio.getId() != null){
			this.campoCondominoDesabilitado = Boolean.FALSE;
			this.listaDeCondominosVOs = condominoMB.buscarListaCondominosVOPorCondominio(this.condominio);	
		}		
	}
	
	public void popularListaSIAmbientes() {
		try {
			this.listaSIAmbientes = new ArrayList<SelectItem>();			
			this.condomino = condominoService.buscarPorId(this.idCondomino);
			List<Ambiente> listaAmbiente = this.ambienteService.buscarPorCondomino(this.condomino);		
			for (Ambiente ambiente : listaAmbiente) {
				this.listaSIAmbientes.add(new SelectItem(ambiente.getId(), ambiente.getNome()));
			}
		} catch (SQLException e) {
			logger.error("erro sqlstate "+e.getSQLState(), e);	
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		} catch (Exception e) {
			logger.error("", e);
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		}
		
	}	
		
	public void popularListaMinhasReservas(){				
		try {
			this.listaMinhasReservas = new ListDataModel<Reserva>(this.reservaService.buscarPorCondomino(this.condomino));
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
	
	public void popularListaReservas(){		
		List<Reserva> listaDeReservaLocol = null;
		List<ReservaVO> listaDeReservaLocolVOs = new ArrayList<ReservaVO>();
		Unidade unidade = null;
		Bloco bloco = null;
		ReservaVO reservaVO = null;
		try {
			if(this.reserva.getSituacao().equals(TODAS)){
				listaDeReservaLocol = this.reservaService.buscarPorCondominio(this.condominio);				
			}else{
				listaDeReservaLocol = this.reservaService.buscarPorCondominioETipo(condominio, this.reserva.getSituacao());
			}
			for (Reserva reserva : listaDeReservaLocol) {
				reservaVO = new ReservaVO();
				reservaVO.setId(reserva.getId());
				reservaVO.setCondominio(this.condominio.getNome());
				reservaVO.setData(reserva.getData());
				reservaVO.setSituacao(reserva.getSituacaoI18n());
				reservaVO.setIdAmbiente(reserva.getAmbiente().getId());
				reservaVO.setAmbiente(reserva.getAmbiente().getNome());
				reservaVO.setIdCondomino(reserva.getCondomino().getId());
				reservaVO.setCondomino(reserva.getCondomino().getNome());
				unidade = this.unidadeService.buscarPorId(reserva.getCondomino().getIdUnidade());
				reservaVO.setUnidade(unidade.getNumero());
				bloco = this.blocoService.buscarPorId(unidade.getIdBloco());
				reservaVO.setBloco(bloco.getNome());
				listaDeReservaLocolVOs.add(reservaVO);			
			}
			this.listaReservasVOs = new ListDataModel<ReservaVO>(listaDeReservaLocolVOs);		
			if (this.listaReservasVOs.getRowCount() == 0){
				ManagedBeanUtil.setMensagemInfo("msg.reserva.semReservas");
			}
		} catch (SQLException e) {
			logger.error("erro sqlstate "+e.getSQLState(), e);	
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		} catch (Exception e) {
			logger.error("", e);
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		}
	}
	
	public void carregarMeusAmbientes(){		
		try {			
			this.condomino  = condominoService.buscarPorId(AplicacaoUtil.getUsuarioAutenticado().getId());			
			List<Ambiente> listaAmbiente = this.ambienteService.buscarPorCondomino(this.condomino);
			this.listaSIMeusAmbientes = new ArrayList<SelectItem>();
			for (Ambiente ambiente : listaAmbiente) {
				this.listaSIMeusAmbientes.add(new SelectItem(ambiente.getId(), ambiente.getNome()));
			}	
			this.popularListaMinhasReservas();
		} catch (SQLException e) {
			logger.error("erro sqlstate "+e.getSQLState(), e);	
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		} catch (Exception e) {
			logger.error("", e);
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		}
	}
	
	public void limparListaReservas(ActionEvent actionEvent){
		this.listaReservasVOs = new ListDataModel<ReservaVO>();
		this.reserva = new Reserva();		
		this.populaNomeMeusAmbiente();
	}
	
	public void limparListaAprovarReservas(ActionEvent actionEvent){
		this.listaDeReservasVOs = new ListDataModel<ReservaVO>();		
		this.condominio = new Condominio();
	}
	

	public void populaIdAmbiente(){	
		this.idAmbiente = null;
		this.reserva.setData(null);
		if(this.reserva.getAmbiente().getId() != -1){
			this.idAmbiente = this.reserva.getAmbiente().getId();	
			// Necessário setar o idAmbiente na sessão, pois ao abrir o calendário para selecionar uma data para reserva, 
			// não deve ser possível selecionar uma data que esteja com uma reserva pendente ou aprovada (validação feita em CalendarioReservaMB) 
			ManagedBeanUtil.getSession(true).setAttribute("idAmbiente",this.idAmbiente);
		}
	}
	
	public void popularListaBlocos() {
		this.limparListasAmbienteBlocoCondominoEData();
		try {
			List<SelectItem> listaSIBlocos = this.blocoMB.buscarListaBlocosPorCondominio(this.condominio);
			this.setListaSIBlocos(listaSIBlocos);
			List<Ambiente> listaAmbiente = this.ambienteService.buscarPorCondominio(this.condominio);		
			for (Ambiente ambiente : listaAmbiente) {
				this.listaSIAmbientes.add(new SelectItem(ambiente.getId(), ambiente.getNome()));
			}
		} catch (SQLException e) {
			logger.error("erro sqlstate "+e.getSQLState(), e);	
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		} catch (Exception e) {
			logger.error("", e);
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		}
	}
	
	
	public void popularListaCondominos() throws SQLException, Exception{
		this.listaSICondominos = new ArrayList<SelectItem>();
		try {
			if (this.bloco.getId() != -1){
				// O Bloco que vem da página não contém o nome, somente o id, por isso buscar ele na base.
				this.bloco = this.blocoService.buscarPorId(this.bloco.getId());
				this.blocoService.popularBloco(this.bloco);
				for (Unidade unidade : bloco.getListaUnidade()) {
					for (Condomino condomino : unidade.getListaCondominos()) {
						this.listaSICondominos.add(new SelectItem(condomino.getId(), bloco.getNome() + " - " + unidade.getNumero() + " - " + condomino.getNome()));
					}	
				}			
			}
		}catch (SQLException e) {
			logger.error("erro sqlstate "+e.getSQLState(), e);	
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		} catch (Exception e) {
			logger.error("", e);
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		}
	}
	
	private void limparListasAmbienteBlocoCondominoEData() {
		this.listaSIAmbientes = new ArrayList<SelectItem>();
		this.listaSIBlocos = new ArrayList<SelectItem>();
		this.listaSICondominos = new ArrayList<SelectItem>();
		this.reserva = new Reserva();
		this.bloco = new Bloco();
		this.idAmbiente = null;
	}
	
	private void populaNomeAmbiente(){
		Boolean encontrou = Boolean.FALSE;
		Integer indiceListaSIAmbiente = 0;
		while (!encontrou){
			if(this.listaSIAmbientes.get(indiceListaSIAmbiente).getValue() ==  this.reserva.getAmbiente().getId()){
				this.reserva.getAmbiente().setNome(this.listaSIAmbientes.get(indiceListaSIAmbiente).getLabel());
				encontrou = Boolean.TRUE;
			}
			indiceListaSIAmbiente++;
		}		
	}
	
	private void populaNomeMeusAmbiente(){
		Boolean encontrou = Boolean.FALSE;
		Integer indiceListaSIAmbiente = 0;
		while (!encontrou){
			if(this.listaSIMeusAmbientes.get(indiceListaSIAmbiente).getValue() ==  this.reserva.getAmbiente().getId()){
				this.reserva.getAmbiente().setNome(this.listaSIMeusAmbientes.get(indiceListaSIAmbiente).getLabel());
				encontrou = Boolean.TRUE;
			}
			indiceListaSIAmbiente++;
		}		
	}
	
	private void popularSituacaoes(){
		this.listaSISituacoes = new ArrayList<SelectItem>();
		this.listaSISituacoes.add(new SelectItem(ReservaSituacaoEnum.APROVADA.getSituacao(), AplicacaoUtil.i18n("reserva.situacao.1")));
		this.listaSISituacoes.add(new SelectItem(ReservaSituacaoEnum.PENDENTE.getSituacao(), AplicacaoUtil.i18n("reserva.situacao.2")));
		this.listaSISituacoes.add(new SelectItem(ReservaSituacaoEnum.REPROVADA.getSituacao(), AplicacaoUtil.i18n("reserva.situacao.0")));
		this.listaSISituacoes.add(new SelectItem(ReservaSituacaoEnum.SUSPENSA.getSituacao(), AplicacaoUtil.i18n("reserva.situacao.3")));
		this.listaSISituacoes.add(new SelectItem(TODAS, AplicacaoUtil.i18n(TODAS)));
		
	}
	
	@Override
	public void abreSessao() {
		ManagedBeanUtil.abreSessao(conversation);	
	}

	@Override
	public void fechaSessao() {
		ManagedBeanUtil.fechaSessao(conversation);		
	}	
	
	public Conversation getConversation() {
		return conversation;
	}

	public void setConversation(Conversation conversation) {
		this.conversation = conversation;
	}

	public ListDataModel<Reserva> getListaMinhasReservas() {
		return listaMinhasReservas;
	}

	public void setListaMinhasReservas(ListDataModel<Reserva> listaDeReservas) {
		this.listaMinhasReservas = listaDeReservas;
	}		

	public void setListaSICondominios(List<SelectItem> listaSICondominios) {
		this.listaSICondominios = listaSICondominios;
	}

	public List<SelectItem> getListaSICondominios() {
		this.listaSICondominios =  this.condominioMB.buscarListaCondominiosAtivos();
		return listaSICondominios;
	}

	public Reserva getReserva() {
		return reserva;
	}

	public void setReserva(Reserva reserva) {
		this.reserva = reserva;
	}

	public Ambiente getAmbiente() {
		return ambiente;
	}

	public void setAmbiente(Ambiente ambiente) {
		this.ambiente = ambiente;
	}

	public Condominio getCondominio() {
		return condominio;
	}

	public void setCondominio(Condominio condominio) {
		this.condominio = condominio;
	}

	public Integer getIdCondomino() {
		return idCondomino;
	}

	public void setIdCondomino(Integer idCondomino) {
		this.idCondomino = idCondomino;
	}

	public String getNomeCondomino() {
		return nomeCondomino;
	}

	public void setNomeCondomino(String nomeCondomino) {
		this.nomeCondomino = nomeCondomino;
	}

	public Boolean getCampoCondominoDesabilitado() {
		return campoCondominoDesabilitado;
	}

	public void setCampoCondominoDesabilitado(Boolean campoCondominoDesabilitado) {
		this.campoCondominoDesabilitado = campoCondominoDesabilitado;
	}		
	
	public ReservaService getReservaService() {
		return reservaService;
	}

	public void setReservaService(ReservaService reservaService) {
		this.reservaService = reservaService;
	}

	public AmbienteService getAmbienteService() {
		return ambienteService;
	}

	public void setAmbienteService(AmbienteService ambienteService) {
		this.ambienteService = ambienteService;
	}

	public CondominoService getCondominoService() {
		return condominoService;
	}

	public void setCondominoService(CondominoService condominoService) {
		this.condominoService = condominoService;
	}

	public UnidadeService getUnidadeService() {
		return unidadeService;
	}

	public void setUnidadeService(UnidadeService unidadeService) {
		this.unidadeService = unidadeService;
	}

	public BlocoService getBlocoService() {
		return blocoService;
	}

	public void setBlocoService(BlocoService blocoService) {
		this.blocoService = blocoService;
	}

	public CondominioService getCondominioService() {
		return condominioService;
	}

	public void setCondominioService(CondominioService condominioService) {
		this.condominioService = condominioService;
	}

	public CondominioMB getCondominioMB() {
		return condominioMB;
	}

	public void setCondominioMB(CondominioMB condominioMB) {
		this.condominioMB = condominioMB;
	}

	public CondominoMB getCondominoMB() {
		return condominoMB;
	}

	public void setCondominoMB(CondominoMB condominoMB) {
		this.condominoMB = condominoMB;
	}

	public UISelectOne getComponenteCondominio() {
		return componenteCondominio;
	}

	public void setComponenteCondominio(UISelectOne componenteCondominio) {
		this.componenteCondominio = componenteCondominio;
	}

	public ListDataModel<ReservaVO> getListaDeReservasVOs() {
		return listaDeReservasVOs;
	}

	public void setListaDeReservasVOs(ListDataModel<ReservaVO> listaDeReservasVOs) {
		this.listaDeReservasVOs = listaDeReservasVOs;
	}

	public Integer getIdAmbiente() {
		return idAmbiente;
	}

	public void setIdAmbiente(Integer idAmbiente) {
		this.idAmbiente = idAmbiente;
	}	

	public UICalendar getComponenteDataReserva() {
		return componenteDataReserva;
	}

	public ListDataModel<ReservaVO> getListaReservasVOs() {
		return listaReservasVOs;
	}

	public void setListaReservasVOs(ListDataModel<ReservaVO> listaReservasVOs) {
		this.listaReservasVOs = listaReservasVOs;
	}

	public void setComponenteDataReserva(UICalendar componenteDataReserva) {
		this.componenteDataReserva = componenteDataReserva;
	}

	public UIAutocomplete getComponenteNomeCondomino() {
		return componenteNomeCondomino;
	}

	public void setComponenteNomeCondomino(UIAutocomplete componenteNomeCondomino) {
		this.componenteNomeCondomino = componenteNomeCondomino;
	}	

	public ReservaVO getReservaVO() {
		return reservaVO;
	}
	public void setReservaVO(ReservaVO reservaVO) {
		this.reservaVO = reservaVO;
	}

	public List<SelectItem> getListaSISituacoes() {
		return listaSISituacoes;
	}

	public void setListaSISituacoes(List<SelectItem> listaSISituacoes) {
		this.listaSISituacoes = listaSISituacoes;
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

	public List<SelectItem> getListaSICondominos() {
		return listaSICondominos;
	}

	public void setListaSICondominos(List<SelectItem> listaSICondominos) {
		this.listaSICondominos = listaSICondominos;
	}	
	
	public Condomino getCondomino() {
		return condomino;
	}

	public void setCondomino(Condomino condomino) {
		this.condomino = condomino;
	}
	
	
}