package br.com.condominiosvirtuais.controller;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.log4j.Logger;

import br.com.condominiosvirtuais.thread.ClassificadosThread;
import br.com.condominiosvirtuais.thread.EmailThread;
import br.com.condominiosvirtuais.util.AplicacaoUtil;

@Named @ApplicationScoped
public class PrincipalMB  implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private static Logger logger = Logger.getLogger(PrincipalMB.class);	 
	
	private String caminhoAplicacao = null; 
	
	@Inject
	private CondominioMB condominioMB = null;
	
	@Inject
	private BlocoMB blocoMB = null;
	
	@Inject
	private UnidadeMB unidadeMB = null;
	
	@Inject
	private AmbienteMB ambienteMB = null;
	
	@Inject
	private ItemAmbienteMB itemAmbienteMB;
	
	@Inject
	private ReservaMB reservaMB;
	
	@Inject
	private MensagemMB mensagemMB;
	
	@Inject
	private EnqueteMB enqueteMB;
	
	@Inject
	private TelefonesUteisMB telefonesUteisMB;
	
	@Inject
	private VeiculoMB veiculoMB;
	
	@Inject
	private EmailThread emailThread;
	
	@Inject
	private ClassificadosThread classificadosThread;
	
	@Inject
	private AgendamentoMB agendamentoMB;
	
	@Inject
	private DespesasMB despesasMB;
	
	@Inject
	private ChamadoMB chamadoMB;
	
	@Inject
	private VisitanteMB visitanteMB;
	
	@Inject
	private ObraMB obraMB;
	
	@PostConstruct
	public void init() {
		logger.info("Inicializa aplicação");
		this.iniciaThreads();
    }
	
	public String principal(){
		this.finalizaSessoes();	
		return "principal";
	}
	              
	public String listarDocumento(){
		this.finalizaSessoes();
		return "listarDocumento";
	}
	
	public String anexarDocumento(){
		this.finalizaSessoes();
		return "anexarDocumento";
	}
	
	public String anexarImagem(){
		this.finalizaSessoes();
		return "anexarImagem";
	}
	
	public String listarCondominio(){
		this.finalizaSessoes();
		return "listarCondominio"; 
	}
	
	public String listarImagem(){
		this.finalizaSessoes();
		return "listarImagem"; 
	}
	
	public String listarBloco(){
		this.finalizaSessoes();
		return "listarBloco";		        
	}
	
	public String listarUnidade(){
		this.finalizaSessoes();
		return "listarUnidade";		        
	}
	
	public String listarCondomino(){
		this.finalizaSessoes();
		return "listarCondomino";		        
	}
	
	public String listarVeiculo(){
		this.finalizaSessoes();
		return "listarVeiculo";		        
	}
	
	public String listarAmbienteCondominio(){
		this.finalizaSessoes();
		return "listarAmbienteCondominio";
	}
	              
	public String listarAmbienteBloco(){
		this.finalizaSessoes();
		return "listarAmbienteBloco";
	}
	
	public String listarFuncionarioCondominio(){
		this.finalizaSessoes();
		return "listarFuncionarioCondominio";
	}
	              
	public String listarFuncionarioBloco(){
		this.finalizaSessoes();
		return "listarFuncionarioBloco";
	}
	
	public String cadastroListarReserva(){
		this.finalizaSessoes();
		return "cadastroListarReserva";
	}
	
	public String cadastrarReserva(){
		this.finalizaSessoes();
		return "cadastrarReserva";
	}
	
	public String aprovarReserva(){
		this.finalizaSessoes();
		return "aprovarReserva";
	}
	
	public String listarReserva(){
		this.finalizaSessoes();
		return "listarReserva";		        
	}
	
	public String faleComSindico(){
		this.finalizaSessoes();
		return "faleComSindico";
	}
	
	public String escreverMensagem(){
		this.finalizaSessoes();
		return "escreverMensagem";
	}
	
	public String mensagensRecebidas(){
		this.finalizaSessoes();
		return "mensagensRecebidas"; 
	}
	
	public String mensagensEnviadas(){
		this.finalizaSessoes();
		return "mensagensEnviadas"; 
	}
	
	public String listarEnquete(){
		this.finalizaSessoes();
		return "listarEnquete";
	}
	
	public String cadastrarEnquete(){
		this.finalizaSessoes();
		return "cadastrarEnquete"; 
	}
	
	public String votarEnquete(){
		this.finalizaSessoes();
		return "votarEnquete"; 
	}
	
	public String listarClassificados(){
		return "listarClassificados"; 
	}
	
	public String cadastrarClassificados(){
		return "cadastrarClassificados"; 
	}
	
	public String listarTelefonesUteis(){
		this.finalizaSessoes();
		return "listarTelefonesUteis"; 
	}
	
	public String cadastrarTelefonesUteis(){
		this.finalizaSessoes();
		return "cadastrarTelefonesUteis"; 
	}
	
	public String meuPainel(){
		this.finalizaSessoes();
		return "meuPainel"; 
	}
	
	public String solicitarAgendamento(){
		return "solicitarAgendamento";
	}
	
	public String listarAgendamento(){
		return "listarAgendamento";
	}
	
	public String aprovarAgendamento(){
		return "aprovarAgendamento";
	}
	
	public String cadastrarDespesasCondominio(){
		return "cadastrarDespesasCondominio";
	}
	
	public String cadastrarDespesasCondomino(){
		return "cadastrarDespesasCondomino";
	}
	
	public String cadastrarDespesasTodosCondominos(){
		return "cadastrarDespesasTodosCondominos";
	}
	
	public String listarDespesasCondominio(){
		return "listarDespesasCondominio";
	}
	
	public String cadastrarChamado(){
		return "cadastrarChamado";
	}
	
	public String listarChamado(){
		return "listarChamado";
	}
	
	public String cadastrarVisitante(){
		return "cadastrarVisitante";
	}
	
	public String listarVisitante(){
		return "listarVisitante";
	}
	
	public String cadastrarObra(){
		return "cadastrarObra";
	}
	
	public String listarObra(){
		return "listarObra";
	}
	

	private void iniciaThreads(){				
		emailThread.start();
		logger.info("Start"+ EmailThread.class);
// TODO: Thread de classificados com problema no i18n.		
		//classificadosThread.start();
		//logger.info("Start "+ ClassificadosThread.class);
		this.caminhoAplicacao = AplicacaoUtil.getCaminhoAplicacao();		
	}
	
	private void finalizaSessoes(){
		this.condominioMB.fechaSessao();
		this.blocoMB.fechaSessao();
		this.unidadeMB.fechaSessao();
		this.ambienteMB.fechaSessao();
		this.itemAmbienteMB.fechaSessao();		
		this.ambienteMB.fechaSessao();
		this.reservaMB.fechaSessao();
		this.mensagemMB.fechaSessao();
		this.enqueteMB.fechaSessao();
		this.veiculoMB.fechaSessao();
	}

	public CondominioMB getCondominioMB() {
		return condominioMB;
	}

	public void setCondominioMB(CondominioMB condominioMB) {
		this.condominioMB = condominioMB;
	}

	public BlocoMB getBlocoMB() {
		return blocoMB;
	}

	public void setBlocoMB(BlocoMB blocoMB) {
		this.blocoMB = blocoMB;
	}

	public UnidadeMB getUnidadeMB() {
		return unidadeMB;
	}

	public void setUnidadeMB(UnidadeMB unidadeMB) {
		this.unidadeMB = unidadeMB;
	}	

	public AmbienteMB getAmbienteMB() {
		return ambienteMB;
	}

	public void setAmbienteMB(AmbienteMB ambienteMB) {
		this.ambienteMB = ambienteMB;
	}	

	public ItemAmbienteMB getItemAmbienteMB() {
		return itemAmbienteMB;
	}

	public void setItemAmbienteMB(ItemAmbienteMB itemAmbienteMB) {
		this.itemAmbienteMB = itemAmbienteMB;
	}	

	public ReservaMB getReservaMB() {
		return reservaMB;
	}

	public void setReservaMB(ReservaMB reservaMB) {
		this.reservaMB = reservaMB;
	}	

	public MensagemMB getMensagemMB() {
		return mensagemMB;
	}

	public void setMensagemMB(MensagemMB mensagemMB) {
		this.mensagemMB = mensagemMB;
	}	

	public EnqueteMB getEnqueteMB() {
		return enqueteMB;
	}

	public void setEnqueteMB(EnqueteMB enqueteMB) {
		this.enqueteMB = enqueteMB;
	}	

	public TelefonesUteisMB getTelefonesUteisMB() {
		return telefonesUteisMB;
	}

	public void setTelefonesUteisMB(TelefonesUteisMB telefonesUteisMB) {
		this.telefonesUteisMB = telefonesUteisMB;
	}

	public EmailThread getEmailThread() {
		return emailThread;
	}

	public void setEmailThread(EmailThread emailThread) {
		this.emailThread = emailThread;
	}

	public AgendamentoMB getAgendamentoMB() {
		return agendamentoMB;
	}

	public void setAgendamentoMB(AgendamentoMB agendamentoMB) {
		this.agendamentoMB = agendamentoMB;
	}	

	public String getCaminhoAplicacao() {
		return caminhoAplicacao;
	}

	public DespesasMB getDespesasMB() {
		return despesasMB;
	}

	public void setDespesasMB(DespesasMB despesasMB) {
		this.despesasMB = despesasMB;
	}

	public ChamadoMB getChamadoMB() {
		return chamadoMB;
	}

	public void setChamadoMB(ChamadoMB chamadoMB) {
		this.chamadoMB = chamadoMB;
	}

	public VeiculoMB getVeiculoMB() {
		return veiculoMB;
	}

	public void setVeiculoMB(VeiculoMB veiculoMB) {
		this.veiculoMB = veiculoMB;
	}

	public VisitanteMB getVisitanteMB() {
		return visitanteMB;
	}

	public void setVisitanteMB(VisitanteMB visitanteMB) {
		this.visitanteMB = visitanteMB;
	}

	public ObraMB getObraMB() {
		return obraMB;
	}

	public void setObraMB(ObraMB obraMB) {
		this.obraMB = obraMB;
	}
	
	
}
