package br.com.condominiosvirtuais.controller;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.event.ActionEvent;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.log4j.Logger;

import br.com.condominiosvirtuais.entity.Bloco;
import br.com.condominiosvirtuais.entity.Condominio;
import br.com.condominiosvirtuais.entity.Condomino;
import br.com.condominiosvirtuais.entity.Funcionario;
import br.com.condominiosvirtuais.entity.MensagemEnviada;
import br.com.condominiosvirtuais.entity.MensagemRecebida;
import br.com.condominiosvirtuais.entity.Unidade;
import br.com.condominiosvirtuais.entity.Usuario;
import br.com.condominiosvirtuais.enumeration.CondominioSituacaoEnum;
import br.com.condominiosvirtuais.enumeration.UsuarioSituacaoEnum;
import br.com.condominiosvirtuais.exception.BusinessException;
import br.com.condominiosvirtuais.service.BlocoService;
import br.com.condominiosvirtuais.service.CondominioService;
import br.com.condominiosvirtuais.service.CondominoService;
import br.com.condominiosvirtuais.service.FuncionarioService;
import br.com.condominiosvirtuais.service.MensagemEnviadaService;
import br.com.condominiosvirtuais.service.MensagemRecebidaService;
import br.com.condominiosvirtuais.service.UnidadeService;
import br.com.condominiosvirtuais.util.AplicacaoUtil;
import br.com.condominiosvirtuais.util.ManagedBeanUtil;
import br.com.condominiosvirtuais.vo.BlocoVO;
import br.com.condominiosvirtuais.vo.MensagemEnviadaVO;
import br.com.condominiosvirtuais.vo.MensagemRecebidaVO;

@Named @SessionScoped
public class MensagemMB implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private static Logger logger = Logger.getLogger(MensagemMB.class);
	
	private static final String ID_TAB_MENSAGEM_CONDOMINIOS = "idTabMensagemCondominios";
	
	private static final String ID_TAB_MENSAGEM_FUNCIONARIOS = "idTabMensagemFuncionarios";
	
	@Inject
	private CondominioService condominioService = null;
	
	@Inject
	private BlocoService blocoService = null;
	
	@Inject
	private UnidadeService unidadeService = null;
	
	@Inject
	private CondominoService condominoService = null;
	
	@Inject
	private FuncionarioService funcionarioService = null;
	
	@Inject
	private MensagemRecebidaService mensagemRecebidaService = null;
	
	@Inject
	private MensagemEnviadaService mensagemEnviadaService = null;
	
	@Inject
	private CondominioMB condominioMB;
	
	@Inject
	private BlocoMB blocoMB = null;	 
	
	private String tabSelecionada;
	
	private List<SelectItem> listaSICondominio;
	
	private List<SelectItem> listaSIBlocos = new ArrayList<SelectItem>();
	
	private List<SelectItem> listaSICondominos = new ArrayList<SelectItem>();
	
	private List<SelectItem> listaSIFuncionarios;
	
	private List<Condominio> listaCondominios;
	
	private List<Condominio> listaCondominiosSelecionados;
		
	private List<BlocoVO> listaBlocoVO;
	
	private List<BlocoVO> listaBlocoVOSelecionados;
	
	private ListDataModel<MensagemRecebidaVO> listaDeMensagemRecebidasVO = null;
	
	private ListDataModel<MensagemEnviada> listaDeMensagemEnviadas = null;
	
	private Condominio condominioCondomino;
	
	private Condominio condominioFuncionario;
	
	private Condominio condominioFaleComSindico;
	
	private Bloco bloco;
	
	private Condomino condomino;
	
	private Funcionario funcionario;
	
	private MensagemRecebida mensagemRecebidaCondomino;
	
	private MensagemRecebida mensagemRecebidaFuncionario;
	
	private MensagemRecebida mensagemRecebidaFaleComSindico;
	
	private MensagemRecebidaVO mensagemRecebidaVO;
	
	private MensagemEnviada mensagemEnviada;
	
	private MensagemEnviadaVO mensagemEnviadaVO;
	
	private Boolean enviarParaMimTambemCondomino = null;
	
	private Boolean enviarParaMimTambemFuncionario = null;
	
	private Boolean enviarParaMimTambemFaleComSindico = null;
	
	private Usuario usuarioAutenticado;
		
	public MensagemMB(){}
	
	
	@PostConstruct
	public void inciarMensagemMB(){
		this.condominioCondomino = new Condominio();
		this.condominioFuncionario = new Condominio();
		this.condominioFaleComSindico = new Condominio();
		this.bloco = new Bloco();
		this.condomino = new Condomino();
		this.funcionario = new Funcionario();
		this.mensagemRecebidaCondomino = new MensagemRecebida();
		this.mensagemRecebidaFuncionario = new MensagemRecebida();
		this.mensagemRecebidaFaleComSindico = new MensagemRecebida();
		this.usuarioAutenticado = AplicacaoUtil.getUsuarioAutenticado();
		this.enviarParaMimTambemCondomino = Boolean.FALSE;
		this.enviarParaMimTambemFuncionario = Boolean.FALSE;
		this.enviarParaMimTambemFaleComSindico = Boolean.FALSE;
		this.carregarCondominios();
		this.setTabSelecionada(ID_TAB_MENSAGEM_CONDOMINIOS);
	}
	
	public MensagemMB(MensagemRecebida mensagem) {		
		this.mensagemRecebidaCondomino = mensagem;		
	}
	
	public String enviarMensagemFaleComSindico(){
		try {
			List<MensagemRecebida> listaMensagemRecebida = new ArrayList<MensagemRecebida>();
			this.condominioFaleComSindico = condominioService.buscarPorId(this.condominioFaleComSindico.getId());
			this.mensagemRecebidaFaleComSindico.setData(new Date());
			this.mensagemRecebidaFaleComSindico.setVisualizada(Boolean.FALSE);
			this.mensagemRecebidaFaleComSindico.setUsuarioRemetente(AplicacaoUtil.getUsuarioAutenticado());
			this.mensagemRecebidaFaleComSindico.setUsuarioDestinatario(this.condominioFaleComSindico.getSindicoGeral());
			listaMensagemRecebida.add(this.mensagemRecebidaFaleComSindico);
			// Condição que contempla o cenário onde o usuário informou se pode ou não enviar a msg para ele também.
			if(this.enviarParaMimTambemFaleComSindico){
				MensagemRecebida mensagemParaMim = new MensagemRecebida();
				mensagemParaMim.setData(new Date());
				mensagemParaMim.setVisualizada(Boolean.FALSE);
				mensagemParaMim.setUsuarioRemetente(AplicacaoUtil.getUsuarioAutenticado());
				mensagemParaMim.setUsuarioDestinatario(AplicacaoUtil.getUsuarioAutenticado());
				mensagemParaMim.setAssunto(this.mensagemRecebidaFaleComSindico.getAssunto());
				mensagemParaMim.setTexto(this.mensagemRecebidaFaleComSindico.getTexto());
				listaMensagemRecebida.add(mensagemParaMim);				
			}
			this.mensagemRecebidaService.enviarListaMensagemRecebida(listaMensagemRecebida);						
			ManagedBeanUtil.setMensagemInfo("msg.mensagem.enviadaSucesso");
		} catch (SQLException e) {
			logger.error("erro sqlstate "+e.getSQLState(), e);	
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");				
		} catch (Exception e) {
			logger.error("", e);
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		}
		this.limparMensagemFaleComSindico(null);
		return "enviar";
	}

	
	
	public String enviarMensagemCondominos(){
		try{
			if(this.validaMensagemCondominos()){
				// Caso tenha sido selecionado um bloco, mas não existe condôminos para esse bloco então não envia a msg.
				if(this.bloco.getId() != 0 && this.listaSICondominos.isEmpty()){
					throw new BusinessException("msg.mensagem.MensagemNaoEnviadaCondominosNaoCadastrados");
				}			
				List<Unidade> listaUnidade = null;
				String mensagemCondominosNaoCadastrados = "";
				List<Condomino> listaCondominos = new ArrayList<Condomino>();;
				MensagemRecebida mensagem = null;
				List<MensagemRecebida> listaMensagemRecebida = new ArrayList<MensagemRecebida>();			
				// Condição que contempla o caso onde envia para todos os condôminos.
				if(this.bloco.getId() == 0 && this.condomino.getId() == 0){
					this.condominioService.popularCondominos(this.condominioCondomino);
					for (Bloco bloco : this.condominioCondomino.getListaBlocos()) {
						listaUnidade = this.unidadeService.buscarListaUnidadesPorBloco(bloco);
						for (Unidade unidade : listaUnidade) {
							for (Condomino condomino : this.condominoService.buscarPorUnidade(unidade)) {
								listaCondominos.add(condomino);
							}
							
						}			
					}
					// Condição que contempla o caso onde envia para todos os condôminos de um determinado bloco	
				}else if (this.condomino.getId() == 0){
					listaUnidade = this.unidadeService.buscarListaUnidadesPorBloco(this.bloco);
					for (Unidade unidade : listaUnidade) {				
						for (Condomino condomino : this.condominoService.buscarPorUnidade(unidade)) {
							listaCondominos.add(condomino);
						}					
					}
					// Caso onde envia para um único condômino	
				}else{
					this.condomino = this.condominoService.buscarPorId(this.condomino.getId());
					listaCondominos.add(this.condomino);				
				}			
				for (Condomino condomino : listaCondominos) {
					mensagem = new MensagemRecebida();
					mensagem.setAssunto(this.mensagemRecebidaCondomino.getAssunto());
					mensagem.setTexto(this.mensagemRecebidaCondomino.getTexto());
					mensagem.setData(new Date());
					mensagem.setVisualizada(Boolean.FALSE);
					mensagem.setUsuarioRemetente(AplicacaoUtil.getUsuarioAutenticado());
					mensagem.setUsuarioDestinatario(condomino);
					// Condição que contempla o cenário onde o usuário informou se pode ou não enviar a msg para ele também.
					if (!condomino.getId().equals(mensagem.getUsuarioRemetente().getId())){
						listaMensagemRecebida.add(mensagem);
					}
				}
				
				
				if(this.enviarParaMimTambemCondomino){
					MensagemRecebida mensagemParaMim = new MensagemRecebida();
					mensagemParaMim.setAssunto(this.mensagemRecebidaCondomino.getAssunto());
					mensagemParaMim.setTexto(this.mensagemRecebidaCondomino.getTexto());
					mensagemParaMim.setData(new Date());
					mensagemParaMim.setVisualizada(Boolean.FALSE);
					mensagemParaMim.setUsuarioRemetente(AplicacaoUtil.getUsuarioAutenticado());
					mensagemParaMim.setUsuarioDestinatario(AplicacaoUtil.getUsuarioAutenticado());
					listaMensagemRecebida.add(mensagemParaMim);
					
				}
				if (listaMensagemRecebida.size() > 0){
					this.mensagemRecebidaService.enviarListaMensagemRecebida(listaMensagemRecebida);
					ManagedBeanUtil.setMensagemInfo("msg.mensagem.enviadaSucesso");				
				}
				//Exibe msg worn caso não tenha enviado msg para ninguém ou somente para o próprio remetente.
				if (listaMensagemRecebida.size() == 0 || listaMensagemRecebida.size() == 1 && this.enviarParaMimTambemCondomino){
					// Remove a última vírgula e acrescenta o ponto e vírgula
					mensagemCondominosNaoCadastrados = mensagemCondominosNaoCadastrados.substring(0,mensagemCondominosNaoCadastrados.length()-2) + ";"; 
					ManagedBeanUtil.setMensagemWarn("msg.mensagem.NaoEnviadaCondominos",mensagemCondominosNaoCadastrados);
				}
				this.limparMensagemCondomino(null);
				return "enviar";
			}
		 } catch (SQLException e) {
			logger.error("erro sqlstate "+e.getSQLState(), e);	
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		 }catch (BusinessException e) {				
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage());
			return null;
		 }catch (Exception e) {
			logger.error("", e);
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		}		
		return null;
	}
	
	public String enviarMensagemFuncionarios(){
		try{
			if(this.validaMensagemFuncionarios()){
				// Caso tenha sido selecionado um bloco, mas não existe condôminos para esse bloco então não envia a msg.
				if(this.listaSIFuncionarios.isEmpty()){
					throw new BusinessException("msg.mensagem.MensagemNaoEnviadaFuncionariosNaoCadastrados");
				}			
				
				String mensagemFuncionariosNaoCadastrados = "";
				List<MensagemRecebida> listaMensagemRecebida = new ArrayList<MensagemRecebida>();			
				List<Funcionario> listaFuncionarios = new ArrayList<Funcionario>();
				
				// Condição que contempla o caso onde envia para todos
				if(this.funcionario.getId().equals(0)){
					listaFuncionarios = this.funcionarioService.buscarPorCondominioESituacaoSemImagem(this.condominioFuncionario.getId(), UsuarioSituacaoEnum.ATIVO.getSituacao());
				}else{
					listaFuncionarios.add(this.funcionarioService.buscarPorId(this.funcionario.getId()));
				}
				
				MensagemRecebida mensagem = null;		
				for (Funcionario funcionario : listaFuncionarios) {
					mensagem = new MensagemRecebida();
					mensagem.setAssunto(this.mensagemRecebidaFuncionario.getAssunto());
					mensagem.setTexto(this.mensagemRecebidaFuncionario.getTexto());
					mensagem.setData(new Date());
					mensagem.setVisualizada(Boolean.FALSE);
					mensagem.setUsuarioRemetente(AplicacaoUtil.getUsuarioAutenticado());
					mensagem.setUsuarioDestinatario(funcionario);
					// Condição que contempla o cenário onde o usuário informou se pode ou não enviar a msg para ele também.
					if (funcionario.getId() != mensagem.getUsuarioRemetente().getId()){
						listaMensagemRecebida.add(mensagem);
					}
				}
				
				
				if(this.enviarParaMimTambemFuncionario){
					MensagemRecebida mensagemParaMim = new MensagemRecebida();
					mensagemParaMim.setAssunto(this.mensagemRecebidaFuncionario.getAssunto());
					mensagemParaMim.setTexto(this.mensagemRecebidaFuncionario.getTexto());
					mensagemParaMim.setData(new Date());
					mensagemParaMim.setVisualizada(Boolean.FALSE);
					mensagemParaMim.setUsuarioRemetente(AplicacaoUtil.getUsuarioAutenticado());
					mensagemParaMim.setUsuarioDestinatario(AplicacaoUtil.getUsuarioAutenticado());
					listaMensagemRecebida.add(mensagemParaMim);
					
				}
				if (listaMensagemRecebida.size() > 0){
					this.mensagemRecebidaService.enviarListaMensagemRecebida(listaMensagemRecebida);
					ManagedBeanUtil.setMensagemInfo("msg.mensagem.enviadaSucesso");				
				}
				//Exibe msg worn caso não tenha enviado msg para ninguém ou somente para o próprio remetente.
				if (listaMensagemRecebida.size() == 0 || listaMensagemRecebida.size() == 1 && this.enviarParaMimTambemFuncionario){
					// Remove a última vírgula e acrescenta o ponto e vírgula
					mensagemFuncionariosNaoCadastrados = mensagemFuncionariosNaoCadastrados.substring(0,mensagemFuncionariosNaoCadastrados.length()-2) + ";"; 
					ManagedBeanUtil.setMensagemWarn("msg.mensagem.NaoEnviadaFuncionarios",mensagemFuncionariosNaoCadastrados);
				}
				this.limparMensagemFuncionario(null);
				return "enviar";
			}
		} catch (SQLException e) {
			logger.error("erro sqlstate "+e.getSQLState(), e);	
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		}catch (BusinessException e) {				
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage());
			return null;
		}catch (Exception e) {
			logger.error("", e);
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		}		
		return null;
	}
	
	public void limparMensagemFaleComSindico(ActionEvent event){
		this.mensagemRecebidaFaleComSindico = new MensagemRecebida();
		this.condominioFaleComSindico = new Condominio();
		this.enviarParaMimTambemFaleComSindico = Boolean.FALSE;
	}	
	
	public void limparMensagemCondomino(ActionEvent event){
		this.mensagemRecebidaCondomino = new MensagemRecebida();
		this.condominioCondomino = new Condominio();
		this.bloco = new Bloco();
		this.condomino = new Condomino();
		this.listaSIBlocos = new ArrayList<SelectItem>();
		this.listaSICondominos = new ArrayList<SelectItem>();
		this.enviarParaMimTambemCondomino = Boolean.FALSE;
		this.setTabSelecionada(ID_TAB_MENSAGEM_CONDOMINIOS);
	}	
	
	public void limparMensagemFuncionario(ActionEvent event){
		this.mensagemRecebidaFuncionario = new MensagemRecebida();
		this.condominioFuncionario = new Condominio();
		this.funcionario = new Funcionario();
		this.listaSIFuncionarios = new ArrayList<SelectItem>();
		this.enviarParaMimTambemFuncionario = Boolean.FALSE;
		this.setTabSelecionada(ID_TAB_MENSAGEM_FUNCIONARIOS);
	}	

	public String voltarListarMensagemRecebida(){
		return "listarMensagemRecebida";
	}
	
	public String voltarListarMensagemEnviadas(){
		return "listarMensagemEnviada";
	}
	
	public void carregarCondominios(){		
		try {
			this.listaCondominios = this.condominioService.buscarTodos();
			this.removeCondominioInativo();			
			this.listaSICondominio = condominioMB.buscarListaCondominiosAtivos();
		} catch (SQLException e) {
			logger.error("erro sqlstate "+e.getSQLState(), e);	
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");				
		} catch (Exception e) {
			logger.error("", e);
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		}
	}
	
	public void carregarBlocos(){		
		try {
			List<Bloco> listaBlocos = null;
			BlocoVO blocoVO = null;
			this.listaBlocoVO = new ArrayList<BlocoVO>();
			for (Condominio condominio : this.listaCondominiosSelecionados) {
				listaBlocos = this.blocoService.buscarPorCondominioENome(condominio,null);			
				for (Bloco bloco : listaBlocos) {
					blocoVO = new BlocoVO();					
					blocoVO.setIdCondominio(condominio.getId());
				    blocoVO.setNomeCondominio(condominio.getNome());
				    blocoVO.setIdBloco(bloco.getId());
					blocoVO.setNomeBloco(bloco.getNome());
					this.listaBlocoVO.add(blocoVO);
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
	
	public void popularListaMensagensRecebidas(){
		try {
			this.listaDeMensagemRecebidasVO = new ListDataModel<MensagemRecebidaVO>(this.mensagemRecebidaService.buscarPorUsuarioDestinatario(AplicacaoUtil.getUsuarioAutenticado()));			
			if (this.listaDeMensagemRecebidasVO.getRowCount() == 0){
				ManagedBeanUtil.setMensagemInfo("msg.mensagemRecebida.semMensagensRecebidas");
			}		
		} catch (SQLException e) {
			logger.error("erro sqlstate "+e.getSQLState(), e);	
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");				
		} catch (Exception e) {
			logger.error("", e);
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		}
	}
	
	public void popularListaMensagensEnviadas(){
		try {			
			this.listaDeMensagemEnviadas = new ListDataModel<MensagemEnviada>(this.mensagemEnviadaService.buscarPorUsuarioRemetente(AplicacaoUtil.getUsuarioAutenticado()));
			if (this.listaDeMensagemEnviadas.getRowCount() == 0){
				ManagedBeanUtil.setMensagemInfo("msg.mensagemEnviada.semMensagensEnviadas");
			}		
		} catch (SQLException e) {
			logger.error("erro sqlstate "+e.getSQLState(), e);	
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");				
		} catch (Exception e) {
			logger.error("", e);
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		}
	}
	
	
	public String verMensagemRecebida(){
		this.mensagemRecebidaVO = this.listaDeMensagemRecebidasVO.getRowData();
		try {
			MensagemRecebida mensagemRecebida = new MensagemRecebida();
			mensagemRecebida.setId(this.mensagemRecebidaVO.getId());
			this.mensagemRecebidaService.visualizar(mensagemRecebida);
		} catch (SQLException e) {
			logger.error("erro sqlstate "+e.getSQLState(), e);	
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");				
		} catch (Exception e) {
			logger.error("", e);
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		}
		return "verMensagemRecebida";
	}
	
	public String verMensagemEnviada(){
		this.mensagemEnviada = this.listaDeMensagemEnviadas.getRowData();
		Condomino condomino = null;
		Unidade unidade = null;
		Bloco bloco = null;
		Funcionario funcionario = null;
		this.mensagemEnviadaVO = new MensagemEnviadaVO();
		this.mensagemEnviadaVO.setId(this.mensagemEnviada.getId());
		this.mensagemEnviadaVO.setUsuarioRemetente(this.mensagemEnviada.getUsuarioRemetente());
		this.mensagemEnviadaVO.setAssunto(this.mensagemEnviada.getAssunto());
		this.mensagemEnviadaVO.setData(this.mensagemEnviada.getData());
		this.mensagemEnviadaVO.setTexto(this.mensagemEnviada.getTexto());
		this.mensagemEnviadaVO.setDadosUsuariosDestinatarios("");
		try {			
			for (Usuario usuario : this.mensagemEnviada.getListaUsuariosDestinatarios()) {
				condomino = this.condominoService.buscarPorId(usuario.getId());
				if (condomino != null){
					unidade = this.unidadeService.buscarPorId(condomino.getIdUnidade());
					bloco = this.blocoService.buscarPorId(unidade.getIdBloco());
					this.mensagemEnviadaVO.setDadosUsuariosDestinatarios(this.mensagemEnviadaVO.getDadosUsuariosDestinatarios()+bloco.getNome()+" - "+unidade.getNumero()+" - "+condomino.getNome()+"; ");
				}else{  
					funcionario = this.funcionarioService.buscarPorId(usuario.getId());					
					if (funcionario != null){
						this.mensagemEnviadaVO.setDadosUsuariosDestinatarios(this.mensagemEnviadaVO.getDadosUsuariosDestinatarios()+funcionario.getFuncao()+" - "+funcionario.getNome()+"; ");	
					}else{
						this.mensagemEnviadaVO.setDadosUsuariosDestinatarios(this.mensagemEnviadaVO.getDadosUsuariosDestinatarios()+usuario.getNome()+"; ");
					}
					
				}
			}
		} catch (SQLException e) {
			logger.error("erro sqlstate "+e.getSQLState(), e);	
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");				
		} catch (Exception e) {
			logger.error("", e);
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		}
		return "verMensagemEnviada";
	}
				  
	public String excluirMensagemRecebida(){		
		try {
			MensagemRecebida mensagemRecebida = new MensagemRecebida();
			mensagemRecebida.setId(this.mensagemRecebidaVO.getId());
			this.mensagemRecebidaService.excluir(mensagemRecebida);
			ManagedBeanUtil.setMensagemInfo("msg.mensagem.excluirSucesso");
		} catch (SQLException e) {
			logger.error("erro sqlstate "+e.getSQLState(), e);	
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");				
		} catch (Exception e) {
			logger.error("", e);
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		}
		return "excluir";
	}
	
	public void excluirMensagemEnviada(ActionEvent actionEvent){		
		try {
			this.mensagemEnviadaService.excluir(this.listaDeMensagemEnviadas.getRowData());
			ManagedBeanUtil.setMensagemInfo("msg.mensagem.excluirSucesso");
		} catch (SQLException e) {
			logger.error("erro sqlstate "+e.getSQLState(), e);	
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");				
		} catch (Exception e) {
			logger.error("", e);
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		}
	}
	
	private void removeCondominioInativo(){
		List<Condominio> listaRemoverCondominios = new ArrayList<Condominio>();
		for (Condominio condominio : this.listaCondominios) {
			if(condominio.getSituacao() == CondominioSituacaoEnum.INATIVO.getSituacao()){
				listaRemoverCondominios.add(condominio);				
			}
		}
		this.listaCondominios.removeAll(listaRemoverCondominios);
	}
	
	public void popularListaBlocos(){
		this.listaSICondominos = new ArrayList<SelectItem>();
		List<SelectItem> listaSIBlocos = null;
		// Se opção é Selecione um condomínio, então limpa os blocos
		if(this.condominioCondomino.getId() == null){
			listaSIBlocos = new ArrayList<SelectItem>();	
		}else{
			listaSIBlocos = this.blocoMB.buscarListaBlocosPorCondominio(this.condominioCondomino);
			listaSIBlocos.add(0,new SelectItem(0, AplicacaoUtil.i18n("todos")));
// TODO: Código comentado em 25/09/2017. Apagar em 180 dias			
// FIXME: Colocar essa regra no grupo de usuário do quinta do horto			
//			Usuario usuarioLogado = AplicacaoUtil.getUsuarioAutenticado();
//			List<Condominio> listaCondominio = usuarioLogado.getListaCondominio();
//			for (Condominio condominio : listaCondominio) {
//				// FIXME - Regra temporária. Se for do condomínio Residencial Quinta do Horto e não for o síndico, então remove a opção TODOS 
//				if(condominio.getId() == 19 && usuarioLogado.getIdGrupoUsuario() != 4 && usuarioLogado.getIdGrupoUsuario() != 1){
//					listaSIBlocos.remove(0);
//				}
//			}
			this.popularListaCondominos();
		}
		this.setListaSIBlocos(listaSIBlocos);	
	}
	
	public void popularListaFuncionarios(){
		Integer situacaoAtivo = 1;
		List<Funcionario> listaFuncionarios;
		this.listaSIFuncionarios = new ArrayList<SelectItem>();
		
		try {
			listaFuncionarios = this.funcionarioService.buscarPorCondominioESituacaoSemImagem(this.condominioFuncionario.getId(),situacaoAtivo);
			if(!listaFuncionarios.isEmpty()){
				listaSIFuncionarios.add(0,new SelectItem(0, AplicacaoUtil.i18n("todos")));
			}
			for (int i = 0; i < listaFuncionarios.size(); i++) {
				Funcionario funcionario = listaFuncionarios.get(i);
				this.listaSIFuncionarios.add(new SelectItem(funcionario.getId(), funcionario.getNome()));					
			}
		} catch (SQLException e) {
			logger.error("erro sqlstate "+e.getSQLState(), e);	
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");				
		} catch (Exception e) {
			logger.error("", e);
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		}	
	}
	
	public void popularListaCondominos(){	
		this.listaSICondominos = new ArrayList<SelectItem>();	
		List<Bloco> listBlocos = null;
		try {
			if(this.bloco.getId() != null){
				// Caso onde selecionou todos os blocos
				if(this.bloco.getId() == 0){
					this.condominioService.popularCondominos(this.condominioCondomino);
					listBlocos = this.condominioCondomino.getListaBlocos();
				}else{
					// O Bloco que vem da página não contém o nome, somente o id, por isso buscar ele na base.
					this.bloco = this.blocoService.buscarPorId(this.bloco.getId());
					this.blocoService.popularBloco(this.bloco);
					listBlocos = new ArrayList<Bloco>();
					listBlocos.add(this.bloco);
				}	
				this.listaSICondominos.add(0,new SelectItem(0, AplicacaoUtil.i18n("todos")));
				for (Bloco bloco : listBlocos) {
					for (Unidade unidade : bloco.getListaUnidade()) {
						for (Condomino condomino : unidade.getListaCondominos()) {
							// Condição que garante que não vai aparecer o usuário logado na lista dos destinatários.
							if(AplicacaoUtil.getUsuarioAutenticado().getId() != condomino.getId())
								this.listaSICondominos.add(new SelectItem(condomino.getId(), bloco.getNome() + " - " + unidade.getNumero() + " - " + condomino.getNome()));
						}	
					}
				}
// TODO: Código comentado em 25/09/2017. Apagar em 180 dias			
// FIXME: Colocar essa regra no grupo de usuário do quinta do horto				
//				Usuario usuarioLogado = AplicacaoUtil.getUsuarioAutenticado();
//				List<Condominio> listaCondominio = usuarioLogado.getListaCondominio();
//				for (Condominio condominio : listaCondominio) {
//					// FIXME - Regra temporária. Se for do condomínio Residencial Quinta do Horto e não for o síndico, então remove a opção TODOS 
//					if(condominio.getId() == 19 && usuarioLogado.getIdGrupoUsuario() != 4 && usuarioLogado.getIdGrupoUsuario() != 1){
//						listaSICondominos.remove(0);
//					}
//				}				
			}
		} catch (SQLException e) {
			logger.error("erro sqlstate "+e.getSQLState(), e);	
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");				
		} catch (Exception e) {
			logger.error("", e);
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		}	
	}
	
	private Boolean validaMensagemCondominos() {
		this.setTabSelecionada(ID_TAB_MENSAGEM_CONDOMINIOS);
		Integer quantidadeErros = 0; 
		if(this.condominioCondomino.getId() == null){
			ManagedBeanUtil.setMensagemErro("msg.mensagem.condominos.condominioRequerido");
			quantidadeErros++;			
		}
		if(this.bloco.getId() == null){
			ManagedBeanUtil.setMensagemErro("msg.mensagem.condominos.blocoRequerido");
			quantidadeErros++;			
		}

		if(this.condomino.getId() == null){
			ManagedBeanUtil.setMensagemErro("msg.mensagem.condominos.condominoRequerido");
			quantidadeErros++;		
		}		
		// Regra de valicação tamanho de campos
		if(this.mensagemRecebidaCondomino.getAssunto().trim().length() < 1 || this.mensagemRecebidaCondomino.getAssunto().trim().length() > 50){
			ManagedBeanUtil.setMensagemErro("msg.mensagem.assuntoTamanho");
			quantidadeErros++;		
		}
		// Regra formato de email
		if (this.mensagemRecebidaCondomino.getTexto().trim().length() < 1){
			ManagedBeanUtil.setMensagemErro("msg.mensagem.textoRequerido");
			quantidadeErros++;
		}		
		return quantidadeErros == 0 ? Boolean.TRUE : Boolean.FALSE;
	}
	
	
	private Boolean validaMensagemFuncionarios() {
		this.setTabSelecionada(ID_TAB_MENSAGEM_FUNCIONARIOS);
		Integer quantidadeErros = 0; 
		if(this.condominioFuncionario.getId() == null){
			ManagedBeanUtil.setMensagemErro("msg.mensagem.funcionarios.condominioRequerido");
			quantidadeErros++;			
		}		
		
		if(this.funcionario.getId() == null){
			ManagedBeanUtil.setMensagemErro("msg.mensagem.funcionarios.funcionarioRequerido");
			quantidadeErros++;		
		}		
		// Regra de valicação tamanho de campos
		if(this.mensagemRecebidaFuncionario.getAssunto().trim().length() < 1 || this.mensagemRecebidaFuncionario.getAssunto().trim().length() > 50){
			ManagedBeanUtil.setMensagemErro("msg.mensagem.assuntoTamanho");
			quantidadeErros++;		
		}
		
		if (this.mensagemRecebidaFuncionario.getTexto().trim().length() < 1){
			ManagedBeanUtil.setMensagemErro("msg.mensagem.textoRequerido");
			quantidadeErros++;
		}		
		return quantidadeErros == 0 ? Boolean.TRUE : Boolean.FALSE;
	}

// TODO: Código comentado em 21/08/2017. Apagar em 180 dias	
//	public Boolean ehSindicoOuAdmin(){
//		Boolean ehSindicoOuAdmin = Boolean.FALSE;
//		Usuario usuario = AplicacaoUtil.getUsuarioAutenticado();
//		if(usuario.getId().equals(1) || usuario.getId().equals(236) || usuario.getId().equals(295)){
//			ehSindicoOuAdmin = Boolean.TRUE;
//		}		
//		return ehSindicoOuAdmin;
//	}
	

	public List<BlocoVO> getListaBlocoVO() {
		return listaBlocoVO;
	}

	public void setListaBlocoVO(List<BlocoVO> listaBlocoVO) {
		this.listaBlocoVO = listaBlocoVO;
	}

	public List<BlocoVO> getListaBlocoVOSelecionados() {
		return listaBlocoVOSelecionados;
	}

	public void setListaBlocoVOSelecionados(List<BlocoVO> listaBlocoVOSelecionados) {
		this.listaBlocoVOSelecionados = listaBlocoVOSelecionados;
	}

	public Condominio getCondominioCondomino() {
		return condominioCondomino;
	}

	public void setCondominioCondomino(Condominio condominioCondomino) {
		this.condominioCondomino = condominioCondomino;
	}	

	public Condominio getCondominioFuncionario() {
		return condominioFuncionario;
	}

	public void setCondominioFuncionario(Condominio condominioFuncionario) {
		this.condominioFuncionario = condominioFuncionario;
	}

	public Bloco getBloco() {
		return bloco;
	}

	public void setBloco(Bloco bloco) {
		this.bloco = bloco;
	}

	public Condomino getCondomino() {
		return condomino;
	}

	public void setCondomino(Condomino condomino) {
		this.condomino = condomino;
	}

	public CondominioService getCondominioService() {
		return condominioService;
	}

	public void setCondominioService(CondominioService condominioService) {
		this.condominioService = condominioService;
	}

	public UnidadeService getUnidadeService() {
		return unidadeService;
	}

	public void setUnidadeService(UnidadeService unidadeService) {
		this.unidadeService = unidadeService;
	}

	public CondominoService getCondominoService() {
		return condominoService;
	}

	public void setCondominoService(CondominoService condominoService) {
		this.condominoService = condominoService;
	}

	public FuncionarioService getFuncionarioService() {
		return funcionarioService;
	}

	public void setFuncionarioService(FuncionarioService funcionarioService) {
		this.funcionarioService = funcionarioService;
	}

	public MensagemRecebidaService getMensagemRecebidaService() {
		return mensagemRecebidaService;
	}

	public void setMensagemRecebidaService(
			MensagemRecebidaService mensagemRecebidaService) {
		this.mensagemRecebidaService = mensagemRecebidaService;
	}

	public MensagemEnviadaService getMensagemEnviadaService() {
		return mensagemEnviadaService;
	}

	public void setMensagemEnviadaService(
			MensagemEnviadaService mensagemEnviadaService) {
		this.mensagemEnviadaService = mensagemEnviadaService;
	}

	public List<Condominio> getListaCondominiosSelecionados() {
		return listaCondominiosSelecionados;
	}

	public void setListaCondominiosSelecionados(
			List<Condominio> listaCondominiosSelecionados) {
		this.listaCondominiosSelecionados = listaCondominiosSelecionados;
	}

	public ListDataModel<MensagemRecebidaVO> getListaDeMensagem() {
		return listaDeMensagemRecebidasVO;
	}

	public void setListaDeMensagem(ListDataModel<MensagemRecebidaVO> listaDeMensagem) {
		this.listaDeMensagemRecebidasVO = listaDeMensagem;
	}

	public MensagemRecebida getMensagemRecebidaCondomino() {
		return mensagemRecebidaCondomino;
	}

	public void setMensagemRecebidaCondomino(MensagemRecebida mensagemRecebidaCondomino) {
		this.mensagemRecebidaCondomino = mensagemRecebidaCondomino;
	}		

 	public MensagemRecebida getMensagemRecebidaFuncionario() {
		return mensagemRecebidaFuncionario;
	}

	public void setMensagemRecebidaFuncionario(MensagemRecebida mensagemRecebidaFuncionario) {
		this.mensagemRecebidaFuncionario = mensagemRecebidaFuncionario;
	}

	public MensagemEnviadaVO getMensagemEnviadaVO() {
		return mensagemEnviadaVO;
	}

	public void setMensagemEnviadaVO(MensagemEnviadaVO mensagemEnviadaVO) {
		this.mensagemEnviadaVO = mensagemEnviadaVO;
	}

	public ListDataModel<MensagemEnviada> getListaDeMensagemEnviadas() {
		return listaDeMensagemEnviadas;
	}

	public void setListaDeMensagemEnviadas(
			ListDataModel<MensagemEnviada> listaDeMensagemEnviadas) {
		this.listaDeMensagemEnviadas = listaDeMensagemEnviadas;
	}

	public List<SelectItem> getListaSICondominio() {
		return listaSICondominio;
	}

	public void setListaSICondominio(List<SelectItem> listaSICondominio) {
		this.listaSICondominio = listaSICondominio;
	}

	public List<Condominio> getListaCondominios() {
		return listaCondominios;
	}

	public void setListaCondominios(List<Condominio> listaCondominios) {
		this.listaCondominios = listaCondominios;
	}	

	public Usuario getUsuarioAutenticado() {
		return usuarioAutenticado;
	}

	public void setUsuarioAutenticado(Usuario usuarioAutenticado) {
		this.usuarioAutenticado = usuarioAutenticado;
	}		

	public MensagemEnviada getMensagemEnviada() {
		return mensagemEnviada;
	}

	public void setMensagemEnviada(MensagemEnviada mensagemEnviada) {
		this.mensagemEnviada = mensagemEnviada;
	}	

	public MensagemRecebidaVO getMensagemRecebidaVO() {
		return mensagemRecebidaVO;
	}

	public void setMensagemRecebidaVO(MensagemRecebidaVO mensagemRecebidaVO) {
		this.mensagemRecebidaVO = mensagemRecebidaVO;
	}

	public Boolean getEnviarParaMimTambemCondomino() {
		return enviarParaMimTambemCondomino;
	}

	public void setEnviarParaMimTambemCondomino(Boolean enviarParaMimTambemCondomino) {
		this.enviarParaMimTambemCondomino = enviarParaMimTambemCondomino;
	}	

	public void setEnviarParaMimTambemFuncionario(Boolean enviarParaMimTambemFuncionario) {
		this.enviarParaMimTambemFuncionario = enviarParaMimTambemFuncionario;
	}

	public Boolean getEnviarParaMimTambemFuncionario() {
		return enviarParaMimTambemFuncionario;
	}

	public ListDataModel<MensagemRecebidaVO> getListaDeMensagemRecebidasVO() {
		return listaDeMensagemRecebidasVO;
	}

	public void setListaDeMensagemRecebidasVO(
			ListDataModel<MensagemRecebidaVO> listaDeMensagemRecebidasVO) {
		this.listaDeMensagemRecebidasVO = listaDeMensagemRecebidasVO;
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

	public List<SelectItem> getListaSIFuncionarios() {
		return listaSIFuncionarios;
	}

	public void setListaSIFuncionarios(List<SelectItem> listaSIFuncionarios) {
		this.listaSIFuncionarios = listaSIFuncionarios;
	}

	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}

	public String getTabSelecionada() {
		return tabSelecionada;
	}

	public void setTabSelecionada(String tabSelecionada) {
		this.tabSelecionada = tabSelecionada;
	}


	public Condominio getCondominioFaleComSindico() {
		return condominioFaleComSindico;
	}


	public void setCondominioFaleComSindico(Condominio condominioFaleComSindico) {
		this.condominioFaleComSindico = condominioFaleComSindico;
	}


	public MensagemRecebida getMensagemRecebidaFaleComSindico() {
		return mensagemRecebidaFaleComSindico;
	}


	public void setMensagemRecebidaFaleComSindico(MensagemRecebida mensagemRecebidaFaleComSindico) {
		this.mensagemRecebidaFaleComSindico = mensagemRecebidaFaleComSindico;
	}


	public Boolean getEnviarParaMimTambemFaleComSindico() {
		return enviarParaMimTambemFaleComSindico;
	}


	public void setEnviarParaMimTambemFaleComSindico(Boolean enviarParaMimTambemFaleComSindico) {
		this.enviarParaMimTambemFaleComSindico = enviarParaMimTambemFaleComSindico;
	}	
	
}