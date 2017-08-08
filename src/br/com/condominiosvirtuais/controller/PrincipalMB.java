package br.com.condominiosvirtuais.controller;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.log4j.Logger;

import br.com.condominiosvirtuais.thread.EmailThread;
import br.com.condominiosvirtuais.util.AplicacaoUtil;

@Named @ApplicationScoped
public class PrincipalMB  implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private static Logger logger = Logger.getLogger(PrincipalMB.class);	 
	
	private String caminhoAplicacao = null; 
	
	private String charset = null;
	
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
		return "principal";
	}
	              
	public String listarDocumento(){	
		return "listarDocumento";
	}
	
	public String anexarDocumento(){	
		return "anexarDocumento";
	}
	
	public String anexarImagem(){		
		return "anexarImagem";
	}
	
	public String listarCondominio(){		
		return "listarCondominio"; 
	}
	
	public String listarImagem(){		
		return "listarImagem"; 
	}
	
	public String listarBloco(){	
		return "listarBloco";		        
	}
	
	public String listarUnidade(){		
		return "listarUnidade";		        
	}
	
	public String listarCondomino(){		
		return "listarCondomino";		        
	}
	
	public String listarVeiculo(){		
		return "listarVeiculo";		        
	}
	
	public String listarAmbienteCondominio(){		
		return "listarAmbienteCondominio";
	}
	              
	public String listarAmbienteBloco(){		
		return "listarAmbienteBloco";
	}
	
	public String listarFuncionarioCondominio(){		
		return "listarFuncionarioCondominio";
	}
	              
	public String listarFuncionarioBloco(){		
		return "listarFuncionarioBloco";
	}
	
	public String cadastroListarReserva(){
		return "cadastroListarReserva";
	}
	
	public String cadastrarReserva(){		
		return "cadastrarReserva";
	}
	
	public String aprovarReserva(){		
		return "aprovarReserva";
	}
	
	public String listarReserva(){		
		return "listarReserva";		        
	}
	
	public String faleComSindico(){		
		return "faleComSindico";
	}
	
	public String escreverMensagem(){		
		return "escreverMensagem";
	}
	
	public String mensagensRecebidas(){		
		return "mensagensRecebidas"; 
	}
	
	public String mensagensEnviadas(){		
		return "mensagensEnviadas"; 
	}
	
	public String listarEnquete(){		
		return "listarEnquete";
	}
	
	public String cadastrarEnquete(){		
		return "cadastrarEnquete"; 
	}
	
	public String votarEnquete(){		
		return "votarEnquete"; 
	}
	
	public String listarClassificados(){
		return "listarClassificados"; 
	}
	
	public String cadastrarClassificados(){
		return "cadastrarClassificados"; 
	}
	
	public String listarTelefonesUteis(){		
		return "listarTelefonesUteis"; 
	}
	
	public String cadastrarTelefonesUteis(){		
		return "cadastrarTelefonesUteis"; 
	}
	
	public String meuPainel(){		
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
	
	public String cadastrarReceitaDespesa(){
		return "cadastrarReceitaDespesa";
	}
	
	public String listarReceitaDespesa(){
		return "listarReceitaDespesa";
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
	
	public String cadastrarSindicoProfissional(){
		return "cadastrarSindicoProfissional";
	}
	
	public String listarSindicoProfissional(){
		return "listarSindicoProfissional";
	}
	
	public String cadastrarContador(){
		return "cadastrarContador";
	}
	
	public String listarContador(){
		return "listarContador";
	}
	
	public String cadastrarContaBancaria(){
		return "cadastrarContaBancaria";
	}	
	
	public String listarContaBancaria(){
		return "listarContaBancaria";
	}
	
	public String cadastrarBeneficiario(){
		return "cadastrarBeneficiario";
	}
	
	public String listarBeneficiario(){
		return "listarBeneficiario";		        
	}
	
	public String gerarBoleto(){
		return "gerarBoleto";		        
	}
	
	public String listarBoleto(){
		return "listarBoleto";		        
	}
	
	public String meusBoletos(){
		return "meusBoletos";		        
	}
	
	public String cadastroPreCadastroBoleto(){
		return "cadastroPreCadastroBoleto";		        
	}
	
	public String listaPreCadastroBoleto(){
		return "listarPreCadastroBoleto";		        
	}
	
	public String listarGrupoUsuario(){
		return "listarGrupoUsuario";
	}
	
	

	private void iniciaThreads(){				
		emailThread.start();
		logger.info("Start"+ EmailThread.class);
// TODO: Thread de classificados com problema no i18n.		
		//classificadosThread.start();
		//logger.info("Start "+ ClassificadosThread.class);
		this.caminhoAplicacao = AplicacaoUtil.getCaminhoAplicacao();		
		this.charset = AplicacaoUtil.i18n("charset");
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

	public String getCharset() {
		return charset;
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
