package br.com.condominiosvirtuais.controller;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIInput;
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
public class MensagemMB implements IConversationScopeMB, Serializable {
	
	private static final long serialVersionUID = 1L;

	private static Logger logger = Logger.getLogger(MensagemMB.class);
	
	@Inject
	private Conversation conversation;
	
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
	
	private List<SelectItem> listaSICondominio;
	
	private List<SelectItem> listaSIBlocos = new ArrayList<SelectItem>();
	
	private List<SelectItem> listaSICondominos = new ArrayList<SelectItem>();
	
	private List<Condominio> listaCondominios;
	
	private List<Condominio> listaCondominiosSelecionados;
		
	private List<BlocoVO> listaBlocoVO;
	
	private List<BlocoVO> listaBlocoVOSelecionados;
	
	private ListDataModel<MensagemRecebidaVO> listaDeMensagemRecebidasVO = null;
	
	private ListDataModel<MensagemEnviada> listaDeMensagemEnviadas = null;
	
	private Condominio condominio;
	
	private Bloco bloco;
	
	private Condomino condomino;
	
	private MensagemRecebida mensagemRecebida;
	
	private MensagemRecebidaVO mensagemRecebidaVO;
	
	private MensagemEnviada mensagemEnviada;
	
	private MensagemEnviadaVO mensagemEnviadaVO;
	
	private Boolean enviarParaMimTambem = null;
	
	private UIInput componenteAssuntoMensagem;
	
	private UIInput componenteTextoMensagem;
	
	private Usuario usuarioAutenticado;
		
	public MensagemMB(){}
	
	
	@PostConstruct
	public void inciarMensagemMB(){
		this.condominio = new Condominio();
		this.bloco = new Bloco();
		this.condomino = new Condomino();
		this.mensagemRecebida = new MensagemRecebida();
		this.usuarioAutenticado = AplicacaoUtil.getUsuarioAutenticado();
		this.enviarParaMimTambem = Boolean.FALSE;
		this.carregarCondominios();		
	}
	
	public MensagemMB(MensagemRecebida mensagem) {		
		this.mensagemRecebida = mensagem;		
	}
	
	public String enviarMensagem(){
		try {
			List<MensagemRecebida> listaMensagemRecebida = new ArrayList<MensagemRecebida>();
			this.condominio = condominioService.buscarPorId(this.condominio.getId());
			this.mensagemRecebida.setData(new Date());
			this.mensagemRecebida.setVisualizada(Boolean.FALSE);
			this.mensagemRecebida.setUsuarioRemetente(AplicacaoUtil.getUsuarioAutenticado());
			this.mensagemRecebida.setUsuarioDestinatario(this.condominio.getSindicoGeral());
			listaMensagemRecebida.add(this.mensagemRecebida);
			// Condição que contempla o cenário onde o usuário informou se pode ou não enviar a msg para ele também.
			if(this.enviarParaMimTambem){
				MensagemRecebida mensagemParaMim = new MensagemRecebida();
				mensagemParaMim.setData(new Date());
				mensagemParaMim.setVisualizada(Boolean.FALSE);
				mensagemParaMim.setUsuarioRemetente(AplicacaoUtil.getUsuarioAutenticado());
				mensagemParaMim.setUsuarioDestinatario(AplicacaoUtil.getUsuarioAutenticado());
				mensagemParaMim.setAssunto(this.mensagemRecebida.getAssunto());
				mensagemParaMim.setTexto(this.mensagemRecebida.getTexto());
				listaMensagemRecebida.add(mensagemParaMim);				
			}
//			  File file = new File(AplicacaoUtil.getCaminhoAplicacao()+EnderecoImagemEnum.URL_FOTO_NAO_DISPONIVEL.getEnderecoImagem());
//				emailService.set(file);
//				List<Email> listaEmail = this.emailService.buscar(Integer.parseInt(this.configuracaoAplicacaoService.getConfiguracoes().
//					    get(ConfiguracaoAplicacaoEnum.QTD_ENVIO_EMAIL_LOTE.getChave())));
////						for (Email email : listaEmail) {
//			// FIXME: REMOVER ANTER DE COLOCAR EM PRODUÇÃO			    	
//							email.setPara("maikel.souza@gmail.com");
//							//this.emailService.enviar(email);
//							this.emailService.enviarHTML(email);
//							this.emailService.excluir(email);					 
//						 }
			//	emailService.enviar(email);
			this.mensagemRecebidaService.enviarListaMensagemRecebida(listaMensagemRecebida);						
			ManagedBeanUtil.setMensagemInfo("msg.mensagem.enviadaSucesso");
		} catch (SQLException e) {
			logger.error("erro sqlstate "+e.getSQLState(), e);	
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");				
		} catch (Exception e) {
			logger.error("", e);
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		}
		return "enviar";
	}

	
	
	public String enviarMensagemBloco(){
		try{
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
				this.condominioService.popularCondominos(this.condominio);
				for (Bloco bloco : this.condominio.getListaBlocos()) {
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
				mensagem.setAssunto(this.mensagemRecebida.getAssunto());
				mensagem.setTexto(this.mensagemRecebida.getTexto());
				mensagem.setData(new Date());
				mensagem.setVisualizada(Boolean.FALSE);
				mensagem.setUsuarioRemetente(AplicacaoUtil.getUsuarioAutenticado());
				mensagem.setUsuarioDestinatario(condomino);
				// Condição que contempla o cenário onde o usuário informou se pode ou não enviar a msg para ele também.
				if (condomino.getId() != mensagem.getUsuarioRemetente().getId()){
					listaMensagemRecebida.add(mensagem);
				}
			}
			
			
			if(this.enviarParaMimTambem){
				MensagemRecebida mensagemParaMim = new MensagemRecebida();
				mensagemParaMim.setAssunto(this.mensagemRecebida.getAssunto());
				mensagemParaMim.setTexto(this.mensagemRecebida.getTexto());
				mensagemParaMim.setData(new Date());
				mensagemParaMim.setVisualizada(Boolean.FALSE);
				mensagemParaMim.setUsuarioRemetente(AplicacaoUtil.getUsuarioAutenticado());
				mensagemParaMim.setUsuarioDestinatario(AplicacaoUtil.getUsuarioAutenticado());
				listaMensagemRecebida.add(mensagemParaMim);
								
			}
			
//			File file = new File(AplicacaoUtil.getCaminhoAplicacao()+EnderecoImagemEnum.URL_FOTO_NAO_DISPONIVEL.getEnderecoImagem());
//			emailService.set(file);
//			List<Email> listaEmail = this.emailService.buscar(Integer.parseInt(this.configuracaoAplicacaoService.getConfiguracoes().
//				    get(ConfiguracaoAplicacaoEnum.QTD_ENVIO_EMAIL_LOTE.getChave())));
//					for (Email email : listaEmail) {
//		// FIXME: REMOVER ANTER DE COLOCAR EM PRODUÇÃO			    	
//						email.setPara("maikel.souza@gmail.com");
//						//this.emailService.enviar(email);
//						this.emailService.enviarHTML(email);
//						this.emailService.excluir(email);					 
//					 }
//			
			
			if (listaMensagemRecebida.size() > 0){
				this.mensagemRecebidaService.enviarListaMensagemRecebida(listaMensagemRecebida);
				ManagedBeanUtil.setMensagemInfo("msg.mensagem.enviadaSucesso");				
			}
			//Exibe msg worn caso não tenha enviado msg para ninguém ou somente para o próprio remetente.
			if (listaMensagemRecebida.size() == 0 || listaMensagemRecebida.size() == 1 && this.enviarParaMimTambem){
				// Remove a última vírgula e acrescenta o ponto e vírgula
				mensagemCondominosNaoCadastrados = mensagemCondominosNaoCadastrados.substring(0,mensagemCondominosNaoCadastrados.length()-2) + ";"; 
				ManagedBeanUtil.setMensagemWarn("msg.mensagem.NaoEnviadaCondominos",mensagemCondominosNaoCadastrados);
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
		return "enviar";
	}
	
	public void limparMensagem(ActionEvent event){
		this.mensagemRecebida = new MensagemRecebida();
		ManagedBeanUtil.cleanSubmittedValues(this.componenteAssuntoMensagem);
		ManagedBeanUtil.cleanSubmittedValues(this.componenteTextoMensagem);
		this.listaCondominiosSelecionados = new ArrayList<Condominio>();
		this.listaBlocoVOSelecionados = new ArrayList<BlocoVO>();
		this.listaBlocoVO = new ArrayList<BlocoVO>();		
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
			this.abreSessao();
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
			this.abreSessao();
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
	

	@Override
	public void abreSessao() {
		ManagedBeanUtil.abreSessao(conversation);		
	}

	@Override
	public void fechaSessao() {
		ManagedBeanUtil.fechaSessao(conversation);		
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
		List<SelectItem> listaSIBlocos = this.blocoMB.buscarListaBlocosPorCondominio(this.condominio);
		listaSIBlocos.add(0,new SelectItem(0, AplicacaoUtil.i18n("todos")));
		Usuario usuarioLogado = AplicacaoUtil.getUsuarioAutenticado();
		List<Condominio> listaCondominio = usuarioLogado.getListaCondominio();
		for (Condominio condominio : listaCondominio) {
			// FIXME - Regra temporária. Se for do condomínio Residencial Quinta do Horto e não for o síndico, então remove a opção TODOS 
			if(condominio.getId() == 19 && usuarioLogado.getIdGrupoUsuario() != 4 && usuarioLogado.getIdGrupoUsuario() != 1){
				listaSIBlocos.remove(0);
			}
		}
		this.setListaSIBlocos(listaSIBlocos);	
	}
	
	public void popularListaCondominos(){	
		this.listaSICondominos = new ArrayList<SelectItem>();	
		List<Bloco> listBlocos = null;
		// Caso onde selecionou todos os blocos
		try {
			if(this.bloco.getId() == 0){
				this.condominioService.popularCondominos(this.condominio);
				listBlocos = this.condominio.getListaBlocos();
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
			Usuario usuarioLogado = AplicacaoUtil.getUsuarioAutenticado();
			List<Condominio> listaCondominio = usuarioLogado.getListaCondominio();
			for (Condominio condominio : listaCondominio) {
				// FIXME - Regra temporária. Se for do condomínio Residencial Quinta do Horto e não for o síndico, então remove a opção TODOS 
				if(condominio.getId() == 19 && usuarioLogado.getIdGrupoUsuario() != 4 && usuarioLogado.getIdGrupoUsuario() != 1){
					listaSICondominos.remove(0);
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
	
	
	
	public Conversation getConversation() {
		return conversation;
	}

	public void setConversation(Conversation conversation) {
		this.conversation = conversation;
	}

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

	public Condominio getCondominio() {
		return condominio;
	}

	public void setCondominio(Condominio condominio) {
		this.condominio = condominio;
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

	public MensagemRecebida getMensagem() {
		return mensagemRecebida;
	}

	public void setMensagem(MensagemRecebida mensagem) {
		this.mensagemRecebida = mensagem;
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

	public Boolean getEnviarParaMimTambem() {
		return enviarParaMimTambem;
	}

	public void setEnviarParaMimTambem(Boolean enviarParaMimTambem) {
		this.enviarParaMimTambem = enviarParaMimTambem;
	}

	public ListDataModel<MensagemRecebidaVO> getListaDeMensagemRecebidasVO() {
		return listaDeMensagemRecebidasVO;
	}

	public void setListaDeMensagemRecebidasVO(
			ListDataModel<MensagemRecebidaVO> listaDeMensagemRecebidasVO) {
		this.listaDeMensagemRecebidasVO = listaDeMensagemRecebidasVO;
	}

	public UIInput getComponenteAssuntoMensagem() {
		return componenteAssuntoMensagem;
	}

	public void setComponenteAssuntoMensagem(UIInput componenteAssuntoMensagem) {
		this.componenteAssuntoMensagem = componenteAssuntoMensagem;
	}

	public UIInput getComponenteTextoMensagem() {
		return componenteTextoMensagem;
	}

	public void setComponenteTextoMensagem(UIInput componenteTextoMensagem) {
		this.componenteTextoMensagem = componenteTextoMensagem;
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
	
}