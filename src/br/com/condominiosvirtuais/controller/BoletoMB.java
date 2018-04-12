package br.com.condominiosvirtuais.controller;


import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Instance;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.log4j.Logger;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.condominiosvirtuais.entity.Beneficiario;
import br.com.condominiosvirtuais.entity.Boleto;
import br.com.condominiosvirtuais.entity.ContaBancaria;
import br.com.condominiosvirtuais.entity.Erro;
import br.com.condominiosvirtuais.entity.PreCadastroBoleto;
import br.com.condominiosvirtuais.enumeration.ArquivoExtensaoEnum;
import br.com.condominiosvirtuais.enumeration.ConfiguracaoAplicacaoEnum;
import br.com.condominiosvirtuais.enumeration.MimeTypeEnum;
import br.com.condominiosvirtuais.exception.BusinessException;
import br.com.condominiosvirtuais.service.BeneficiarioService;
import br.com.condominiosvirtuais.service.BoletoService;
import br.com.condominiosvirtuais.service.CondominoService;
import br.com.condominiosvirtuais.service.ConfiguracaoAplicacaoService;
import br.com.condominiosvirtuais.service.ContaBancariaService;
import br.com.condominiosvirtuais.service.PreCadastroBoletoService;
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
	
	@Inject
	private PreCadastroBoletoService preCadastroBoletoService;
	
	@Inject
	private ConfiguracaoAplicacaoService configuracaoAplicacaoService;
	
	private List<CondominoVO> listaCondominoVO = null; 
	
	private List<SelectItem> listaSICondominios;
	
	private List<SelectItem> listaSIContasBancarias;
	
	private List<SelectItem> listaSIBeneficiarios;
	
	private List<SelectItem> listaSIPagadores;
	
	private List<SelectItem> listaSITitulos;
	
	private List<SelectItem> listaSIPago;

	private List<SelectItem> listaSIPreCadastroBoleto;
	
	private ListDataModel<Boleto> listaBoletos;
	
	private ListDataModel<Boleto> listaMeusBoletos;
	
	private ListDataModel<Erro> listaErros;
	
	private List<Beneficiario> listaBeneficiarios;
	
	private List<ContaBancaria> listaContaBancaria;
	
	private List<PreCadastroBoleto> listaPreCadastroBoleto;
	
	private Boleto boleto;	
	
	private Date dataVencimentoDe;
	
	private Date dataVencimentoAte;
	
	private Integer tipoPagoBoleto = -1;
	
	private Integer idPreCadastroBoleto;

	private List<Erro> listaErro = null;
	
	
	
	public BoletoMB(){
		this.boleto = new Boleto();
		this.boleto.setEmissao(new Date());
	}
	
	@PostConstruct
	public void iniciarBoletoMB(){
		this.listaSICondominios = this.condominioMB.get().buscarListaCondominiosAtivos();
		this.listaErros = new ListDataModel<Erro>();
		this.popularTitulos();
		this.popularPago();
	}
	
	public String gerarBoleto(){		
		try {
			if(this.boleto.getVencimento().before(this.boleto.getEmissao())){
				ManagedBeanUtil.setMensagemErro("msg.boleto.vencimentoMenorEmissao");
				return null;
			}else{
				Integer idCondomino = this.boleto.getCondominoVO().getId(); 
				this.listaErro = new ArrayList<Erro>();
				this.boleto.setPago(Boolean.FALSE);
				this.popularBeneficiario();
				this.popularContasBancaria();
				
				if(idCondomino == -1){
					if (this.listaSIPagadores.isEmpty()){
						ManagedBeanUtil.setMensagemErro("msg.boleto.semPagadorCadastrado");
						return null;
					}
					for (SelectItem pagador : this.listaSIPagadores) {
						this.boleto.getCondominoVO().setId((Integer) pagador.getValue());
						this.popularPagador();
						if(this.validarDadosBoleto()){
							this.criarBoleto();
							this.boletoService.salvar(this.boleto);
						}						
					}					
				}else{
					this.popularPagador();
					if (this.validarDadosBoleto()){
						this.criarBoleto();
						this.boletoService.salvar(this.boleto);
					}									
				}
				if(this.dataVencimentoDe == null || this.dataVencimentoAte == null){
					this.popularPesquisaDataVencimento();
				}	
				this.listaErros = new ListDataModel<Erro>(this.listaErro);
				if (idCondomino == -1){
					if (this.listaErros.getRowCount() == 0){
						ManagedBeanUtil.setMensagemInfo("msg.boleto.geradoSucesso");
					}else if (this.listaErros.getRowCount() == this.listaSIPagadores.size()){
						ManagedBeanUtil.setMensagemErro("msg.boleto.naoGeradoSucesso");
						return null;
					}else{
						ManagedBeanUtil.setMensagemWarn("msg.boleto.geradoSucessoMasComErro");
						return null;
					}
				}else{
					if (this.listaErros.getRowCount() == 0){
						ManagedBeanUtil.setMensagemInfo("msg.boleto.geradoSucesso");
					}else{
						ManagedBeanUtil.setMensagemErro("msg.boleto.naoGeradoSucesso");
						return null;
					}					
				}
				this.pesquisar();
			}
		}catch (SQLException e) {
			logger.error("erro sqlstate "+e.getSQLState(), e);	
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
			return null;
		} catch (BusinessException e) {
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao",e.getCause() != null ? e.getCause().getMessage() : "");
			logger.error("", e);			
			return null;
		} catch (Exception e) {
			logger.error("", e);
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
			return null;
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
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage());			
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
		this.idPreCadastroBoleto = -1;
		this.listaBoletos = new ListDataModel<Boleto>();
	}
	
	public void limparFiltroMeusBoletos(){
		this.boleto = new Boleto();
		this.listaMeusBoletos = new ListDataModel<Boleto>();
		this.tipoPagoBoleto = -1;
		this.idPreCadastroBoleto = -1;
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
			this.popularBoleto();
			this.popularListaPreCadastroBoleto();
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
	
	
	
	public void baixarBoleto(ActionEvent actionEvent) throws IOException{
		this.boleto = this.listaBoletos.getRowData();
		Response response = ClientBuilder
				.newClient().target("https://sandbox.boletocloud.com/api/v1/boletos").path("/"+this.boleto.getToken())
				.register(HttpAuthenticationFeature.basic(configuracaoAplicacaoService.getConfiguracoes().get(ConfiguracaoAplicacaoEnum.TOKEN_BOLETO_CLOUD.getChave()),"token"))
				.request(MediaType.WILDCARD).get();
		
		StringBuffer nomeArquivo = new StringBuffer(boleto.getCondominoVO().getNomeCondomino());
		nomeArquivo.append(".");
		nomeArquivo.append(ArquivoExtensaoEnum.PDF.getExtensao());
	
		InputStream is = response.readEntity(InputStream.class);
		HttpServletResponse responseServlet = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
		
		responseServlet.setContentType(MimeTypeEnum.PDF.getMimeType());		
		responseServlet.setHeader("Content-Disposition", "attachment; filename="+nomeArquivo.toString());
		PrintWriter printWriter = responseServlet.getWriter();
		   
		int bit = 256;
		while(bit >= 0){
			 bit = is.read();
			 printWriter.write(bit);
		}
		
		printWriter.flush();
		printWriter.close();
		is.close();
		responseServlet.flushBuffer();
		FacesContext.getCurrentInstance().responseComplete();		
	}
	
	private Boolean validarDadosBoleto(){
		Boolean boletoValido = Boolean.TRUE;
		Erro erro = null;		
		String cpf = AplicacaoUtil.formatarCpf(this.boleto.getCondominoVO().getCpfCondomino());
		if (!AplicacaoUtil.validaExpressaoRegular("(^\\d{3}\\.\\d{3}\\.\\d{3}\\-\\d{2}$)", cpf)){
			erro = new Erro();
			erro.setMensagemErro(AplicacaoUtil.i18n("msg.boleto.boletoNaoGeradoCpfPagadorInvalido"));
			erro.setVariavelMensagemErro(this.boleto.getCondominoVO().getNomeCondomino());			
			erro.setNomeBloco(this.boleto.getCondominoVO().getNomeBloco());
			erro.setNumeroUnidade(this.boleto.getCondominoVO().getNumeroUnidade());
			this.listaErro.add(erro);			
			boletoValido = Boolean.FALSE;
		}if(cpf.equals("000.000.000-00") || cpf.equals("111.111.111-11") || cpf.equals("222.222.222-22") || cpf.equals("333.333.333-33")
			 ||	cpf.equals("444.444.444-44") || cpf.equals("555.555.555-55") || cpf.equals("666.666.666-66") || cpf.equals("777.777.777-77")
			 || cpf.equals("888.888.888-88") || cpf.equals("999.999.999-99")){
			erro = new Erro();
			erro.setMensagemErro(AplicacaoUtil.i18n("msg.boleto.boletoNaoGeradoCpfPagadorInvalido"));
			erro.setVariavelMensagemErro(this.boleto.getCondominoVO().getNomeCondomino());			
			erro.setNomeBloco(this.boleto.getCondominoVO().getNomeBloco());
			erro.setNumeroUnidade(this.boleto.getCondominoVO().getNumeroUnidade());
			this.listaErro.add(erro);
			boletoValido = Boolean.FALSE;
		}
		return boletoValido;
	}
		
		
	private void criarBoleto() throws JsonProcessingException, IOException, BusinessException, Exception{
		
		Form formBoleto = new Form();
		formBoleto.param("boleto.conta.token",this.boleto.getContaBancaria().getToken());
		System.out.println("Banco: " + this.boleto.getContaBancaria().getConfiguracaoPadraoContaBancaria().getTipoTitulo().getSigla());
		System.out.println("Token: " + this.boleto.getContaBancaria().getToken());

		formBoleto.param("boleto.emissao", AplicacaoUtil.formatarData(AplicacaoUtil.i18n("formatoDataBoletoCloud"), this.boleto.getEmissao()));
		formBoleto.param("boleto.vencimento", AplicacaoUtil.formatarData(AplicacaoUtil.i18n("formatoDataBoletoCloud"), this.boleto.getVencimento()));
		formBoleto.param("boleto.documento",this.boleto.getDocumento());
		formBoleto.param("boleto.titulo",this.boleto.getTitulo());
		formBoleto.param("boleto.valor",AplicacaoUtil.formatarMoeda(AplicacaoUtil.i18n("formatoMoedaBoletoCloud"), this.boleto.getValor()).replace(",", "."));
		formBoleto.param("boleto.pagador.nome",this.boleto.getCondominoVO().getNomeCondomino());
		formBoleto.param("boleto.pagador.cprf",AplicacaoUtil.formatarCpf(this.boleto.getCondominoVO().getCpfCondomino()));
		// Endereço do pagador é o mesmo que o do beneficiário. Com excecao do complemento
		formBoleto.param("boleto.pagador.endereco.cep",AplicacaoUtil.formatarCep(this.boleto.getBeneficiario().getEndereco().getCep()));
		formBoleto.param("boleto.pagador.endereco.uf",this.boleto.getBeneficiario().getEndereco().getUf());
		formBoleto.param("boleto.pagador.endereco.localidade",this.boleto.getBeneficiario().getEndereco().getCidade());
		formBoleto.param("boleto.pagador.endereco.bairro",this.boleto.getBeneficiario().getEndereco().getBairro());
		formBoleto.param("boleto.pagador.endereco.logradouro",this.boleto.getBeneficiario().getEndereco().getEndereco());
		formBoleto.param("boleto.pagador.endereco.numero",String.valueOf(this.boleto.getBeneficiario().getEndereco().getNumero()));
		formBoleto.param("boleto.pagador.endereco.complemento", this.boleto.getCondominoVO().getNomeBloco() + " Unidade " + this.boleto.getCondominoVO().getNumeroUnidade());
		formBoleto.param("boleto.instrucao",this.boleto.getInstrucao1());
		formBoleto.param("boleto.instrucao",this.boleto.getInstrucao2());
		formBoleto.param("boleto.instrucao",this.boleto.getInstrucao3());
		

		
		
		Response response = ClientBuilder.newClient().target("https://sandbox.boletocloud.com/api/v1").path("/boletos")
				.register(HttpAuthenticationFeature.basic(configuracaoAplicacaoService.getConfiguracoes().get(ConfiguracaoAplicacaoEnum.TOKEN_BOLETO_CLOUD.getChave()),"token"))
				.request(MediaType.WILDCARD).post(Entity.form(formBoleto));
		

		
		if (response.getStatus() != Status.CREATED.getStatusCode()){			
			String entityResponse = response.readEntity(String.class);
			JsonNode jsonNode = new ObjectMapper().readTree(entityResponse);
			String reason = "\nstatus: " + response.getStatus();		
			if (response.getStatus() == Status.BAD_REQUEST.getStatusCode()){
				Iterator<JsonNode> causas = jsonNode.get("erro").get("causas").elements();
				while(causas.hasNext()){
					JsonNode causa = causas.next();
					reason+="\nCódigo: "+causa.get("codigo").asText() + "\n" +"Mensagem: "+causa.get("mensagem").asText()+"\n";
				}
				throw new BusinessException("msg.erro.executarOperacao", new Exception(reason));
			}
			
			if (response.getStatus() == Status.CONFLICT.getStatusCode()){
				Iterator<JsonNode> causas = jsonNode.get("erro").get("causas").elements();
				while(causas.hasNext()){
					JsonNode causa = causas.next();
					reason+="\nCódigo: "+causa.get("codigo").asText() + "\n" +"Mensagem: "+causa.get("mensagem").asText()+"\n";
				}
				throw new BusinessException("msg.boleto.boletoNumeroRepetido", new Exception(reason),this.boleto.getNumero());				
			}
			
			if (response.getStatus() == Status.UNAUTHORIZED.getStatusCode()){
				Iterator<JsonNode> causas = jsonNode.get("erro").get("causas").elements();
				while(causas.hasNext()){
					JsonNode causa = causas.next();
					reason+="\nCódigo: "+causa.get("codigo").asText() + "\n" +"Mensagem: "+causa.get("mensagem").asText()+"\n";
				}
				throw new BusinessException("msg.erro.executarOperacao", new Exception(reason));
			}
			
			if(response.getStatus() == Status.INTERNAL_SERVER_ERROR.getStatusCode()){
				Iterator<JsonNode> causas = jsonNode.get("erro").get("causas").elements();
				while(causas.hasNext()){
					JsonNode causa = causas.next();
					reason+="\nCódigo: "+causa.get("codigo").asText() + "\n" +"Mensagem: "+causa.get("mensagem").asText()+"\n";
				}
				throw new BusinessException("msg.erro.executarOperacao", new Exception(reason));
			}
		}else if (response.getStatus() == Status.CREATED.getStatusCode()){
			this.boleto.setNumero(String.valueOf(response.getHeaders().get("X-BoletoCloud-NIB-Nosso-Numero").get(0)));
			this.boleto.setToken(String.valueOf(response.getHeaders().get("X-BoletoCloud-Token").get(0)));
		}
	}
	
	
	private void popularBoleto() throws SQLException, Exception{
		PreCadastroBoleto preCadastroBoleto = this.preCadastroBoletoService.buscarPorIdCondominioPrincipal(this.boleto.getIdCondominio());
		if(preCadastroBoleto != null){
			this.idPreCadastroBoleto = preCadastroBoleto.getId();
			this.boleto.getBeneficiario().setId(preCadastroBoleto.getBeneficiario().getId());
			this.boleto.getContaBancaria().setId(preCadastroBoleto.getContaBancaria().getId());
			this.boleto.setInstrucao1(preCadastroBoleto.getInstrucao1());
			this.boleto.setInstrucao2(preCadastroBoleto.getInstrucao2());
			this.boleto.setInstrucao3(preCadastroBoleto.getInstrucao3());
			
			Date dataVencimento = new Date();
			Calendar vencimentoCalendar = GregorianCalendar.getInstance();
			vencimentoCalendar.setTime(dataVencimento);
			
			Integer ultimoDiaMesAtual = vencimentoCalendar.getActualMaximum(Calendar.DAY_OF_MONTH);
			if(preCadastroBoleto.getDiaMesVencimento() > ultimoDiaMesAtual){
				vencimentoCalendar.set(Calendar.DAY_OF_MONTH,ultimoDiaMesAtual);			
			}else{
				vencimentoCalendar.set(Calendar.DAY_OF_MONTH,preCadastroBoleto.getDiaMesVencimento());
			}
			this.boleto.setVencimento(vencimentoCalendar.getTime());			
		}else{
			this.idPreCadastroBoleto = -1;
			this.boleto.setTitulo(null);
			this.boleto.setInstrucao1(null);
			this.boleto.setInstrucao2(null);
			this.boleto.setInstrucao3(null);
			this.boleto.setVencimento(null);
		}
	}
	
	public void popularBoletoComPreCadastroBoleto() throws SQLException, Exception{
		PreCadastroBoleto preCadastroBoleto = null;
		Boolean encontrou = Boolean.FALSE;
		Iterator<PreCadastroBoleto> iteratorPreCadastroBoleto = this.listaPreCadastroBoleto.iterator();
		while (!encontrou || iteratorPreCadastroBoleto.hasNext()) {
			preCadastroBoleto = iteratorPreCadastroBoleto.next();
			if (this.idPreCadastroBoleto.intValue() == preCadastroBoleto.getId().intValue()){
				encontrou = Boolean.TRUE;
				this.boleto.getBeneficiario().setId(preCadastroBoleto.getBeneficiario().getId());
				this.boleto.getContaBancaria().setId(preCadastroBoleto.getContaBancaria().getId());
				this.boleto.setInstrucao1(preCadastroBoleto.getInstrucao1());
				this.boleto.setInstrucao2(preCadastroBoleto.getInstrucao2());
				this.boleto.setInstrucao3(preCadastroBoleto.getInstrucao3());
				
				Date dataVencimento = new Date();
				Calendar vencimentoCalendar = GregorianCalendar.getInstance();
				vencimentoCalendar.setTime(dataVencimento);
				
				Integer ultimoDiaMesAtual = vencimentoCalendar.getActualMaximum(Calendar.DAY_OF_MONTH);
				if(preCadastroBoleto.getDiaMesVencimento() > ultimoDiaMesAtual){
					vencimentoCalendar.set(Calendar.DAY_OF_MONTH,ultimoDiaMesAtual);			
				}else{
					vencimentoCalendar.set(Calendar.DAY_OF_MONTH,preCadastroBoleto.getDiaMesVencimento());
				}
				this.boleto.setVencimento(vencimentoCalendar.getTime());			
			}
		}
	}
	
	private void popularListaPreCadastroBoleto() throws SQLException, Exception{
		this.listaPreCadastroBoleto = this.preCadastroBoletoService.buscarPorIdCondominio(this.boleto.getIdCondominio());
		this.listaSIPreCadastroBoleto = new ArrayList<SelectItem>();
		for (PreCadastroBoleto preCadastroBoleto : listaPreCadastroBoleto) {
			this.listaSIPreCadastroBoleto.add(new SelectItem(preCadastroBoleto.getId(), preCadastroBoleto.getNome()));
		}
	}
	
	private void popularPagador() throws SQLException, Exception{
		Boolean encontrou = Boolean.FALSE;
		Iterator<CondominoVO> iteratorCondominioVO = this.listaCondominoVO.iterator();
		while (iteratorCondominioVO.hasNext() || !encontrou) {
			CondominoVO condominoVO = iteratorCondominioVO.next();
			if(condominoVO.getId().equals(this.boleto.getCondominoVO().getId())){
				this.boleto.setCondominoVO(condominoVO);
				encontrou = Boolean.TRUE;
			}
		}
	}
	
	private void popularBeneficiarios() throws SQLException, BusinessException, Exception{		
		this.listaBeneficiarios = this.beneficiarioService.buscarPorIdCondominioESituacao(this.boleto.getIdCondominio(),Boolean.TRUE);
		this.listaSIBeneficiarios = new ArrayList<SelectItem>();
		for (Beneficiario beneficiario : this.listaBeneficiarios) {
			this.listaSIBeneficiarios.add(new SelectItem(beneficiario.getId(), beneficiario.getNome()));
		}
	}
	
	private void popularBeneficiario() throws CloneNotSupportedException{
		Boolean encontrou = Boolean.FALSE;
		Iterator<Beneficiario> iteratorBeneficiario = this.listaBeneficiarios.iterator();
		while (iteratorBeneficiario.hasNext() && encontrou == Boolean.FALSE) {
			Beneficiario beneficiario = iteratorBeneficiario.next();
			if(this.boleto.getBeneficiario().getId().equals(beneficiario.getId())){
				this.boleto.setBeneficiario(beneficiario.clone());			
				encontrou = Boolean.TRUE;
			}
		}		
	}
	
	private void popularContasBancaria() throws CloneNotSupportedException{
		Boolean encontrou = Boolean.FALSE;
		Iterator<ContaBancaria> iteratorContaBancaria = this.listaContaBancaria.iterator();
		while (iteratorContaBancaria.hasNext() && encontrou == Boolean.FALSE) {
			ContaBancaria contaBancaria = iteratorContaBancaria.next();
			if(this.boleto.getContaBancaria().getId().equals(contaBancaria.getId())){
				this.boleto.setContaBancaria(contaBancaria.clone());
				encontrou = Boolean.TRUE;
			}
		}		
	}
	
	private void popularContasBancarias() throws SQLException, Exception{
		this.listaContaBancaria = this.contaBancariaService.buscarPorIdCondominioESituacao(this.boleto.getIdCondominio(),Boolean.TRUE);
		this.listaSIContasBancarias = new ArrayList<SelectItem>();
		for (ContaBancaria contaBancaria : this.listaContaBancaria) {
			this.listaSIContasBancarias.add(new SelectItem(contaBancaria.getId(), contaBancaria.getBanco().getNome()));
		}			
		
	}
	
	public void buscarPagador() throws SQLException, Exception{		
		this.listaCondominoVO = condominoService.buscarListaCondominosVOPorIdCondominioEPagadorSemImagem(this.boleto.getIdCondominio());
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

	public List<SelectItem> getListaSIPreCadastroBoleto() {
		return listaSIPreCadastroBoleto;
	}

	public void setListaSIPreCadastroBoleto(List<SelectItem> listaSIPreCadastroBoleto) {
		this.listaSIPreCadastroBoleto = listaSIPreCadastroBoleto;
	}

	public Integer getIdPreCadastroBoleto() {
		return idPreCadastroBoleto;
	}

	public void setIdPreCadastroBoleto(Integer idPreCadastroBoleto) {
		this.idPreCadastroBoleto = idPreCadastroBoleto;
	}

	public ListDataModel<Erro> getListaErros() {
		return listaErros;
	}

	public void setListaErros(ListDataModel<Erro> listaErros) {
		this.listaErros = listaErros;
	}	
	
	

}
