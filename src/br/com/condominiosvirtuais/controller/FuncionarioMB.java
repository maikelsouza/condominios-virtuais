package br.com.condominiosvirtuais.controller;

import java.io.OutputStream;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIInput;
import javax.faces.event.ActionEvent;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.log4j.Logger;

import br.com.condominiosvirtuais.entity.Bloco;
import br.com.condominiosvirtuais.entity.BlocoConjuntoBloco;
import br.com.condominiosvirtuais.entity.Condominio;
import br.com.condominiosvirtuais.entity.ConjuntoBloco;
import br.com.condominiosvirtuais.entity.Funcionario;
import br.com.condominiosvirtuais.entity.GrupoUsuario;
import br.com.condominiosvirtuais.entity.TipoConjuntoBloco;
import br.com.condominiosvirtuais.enumeration.GrupoUsuarioPadraoEnum;
import br.com.condominiosvirtuais.enumeration.GrupoUsuarioSituacaoEnum;
import br.com.condominiosvirtuais.enumeration.GrupoUsuarioTipoUsuarioEnum;
import br.com.condominiosvirtuais.enumeration.TipoConjuntoBlocoEnum;
import br.com.condominiosvirtuais.enumeration.UsuarioSituacaoEnum;
import br.com.condominiosvirtuais.exception.BusinessException;
import br.com.condominiosvirtuais.service.BlocoService;
import br.com.condominiosvirtuais.service.ConjuntoBlocoService;
import br.com.condominiosvirtuais.service.FuncionarioService;
import br.com.condominiosvirtuais.service.GrupoUsuarioService;
import br.com.condominiosvirtuais.util.AplicacaoUtil;
import br.com.condominiosvirtuais.util.ManagedBeanUtil;

@Named @SessionScoped
public class FuncionarioMB implements  Serializable {
	
	private static final long serialVersionUID = 1L;

	private static Logger logger = Logger.getLogger(FuncionarioMB.class);
	
	private Condominio condominio = null;
	
	private Bloco bloco = null; 	
	
	private Funcionario funcionario = null;
	
	@Inject
	private CondominioMB condominioMB = null;
	
	@Inject
	private BlocoMB blocoMB = null;
	
	private ListDataModel<Funcionario> listaFuncionariosCondominio = new ListDataModel<Funcionario>();
	
	private ListDataModel<Funcionario> listaFuncionariosBloco = new ListDataModel<Funcionario>();
	
	private List<SelectItem> listaSICondominios = new ArrayList<SelectItem>();
	
	private List<SelectItem> listaSIBlocos = new ArrayList<SelectItem>();
	
	private List<Bloco> listaBlocos = new ArrayList<Bloco>();
	
	private List<Bloco> listaFuncionarioBlocos = new ArrayList<Bloco>();
	
	@Inject
	private FuncionarioService funcionarioService  = null;
	
	@Inject
	private ConjuntoBlocoService conjuntoBlocoService = null;
	
	@Inject
	private GrupoUsuarioService grupoUsuarioService; 
	
	@Inject
	private BlocoService blocoService = null;
	
	private List<SelectItem> listaSIDias = new ArrayList<SelectItem>();
	
	private List<SelectItem> listaSIMeses = new ArrayList<SelectItem>();
	
	private List<SelectItem> listaSIAnos = new ArrayList<SelectItem>();
	
	private List<String> listaNomeBlocos = new ArrayList<String>();
	
	private UIInput componenteNomeFuncionario;
	
	private UIInput componenteFuncaoFuncionario;
	
	private UIInput componenteEmailFuncionario;
	
	private UIInput componenteTelefoneResidencialFuncionario;
	
	private UIInput componenteTelefoneCelularFuncionario;
	
	private UIInput componenteSenhaFuncionario;
	
	private UIInput componenteConfirmarSenhaFuncionario;
	
	
	@PostConstruct
	public void iniciarFuncionarioMB(){
		this.funcionario = new Funcionario();
		this.condominio = new Condominio();
		this.bloco = new Bloco();
		ManagedBeanUtil.popularSIDias(this.listaSIDias);
		ManagedBeanUtil.popularSIMeses(this.listaSIMeses);
		ManagedBeanUtil.popularSIAnos(this.listaSIAnos);
	}
	
	public void pesquisarFuncionarioCondominio(ActionEvent event){
		try {			
			this.popularNomeCondominido();
			this.listaFuncionariosCondominio = new ListDataModel<Funcionario>(this.funcionarioService.buscarPorCondominioENomeFuncionario(this.condominio,this.funcionario.getNome()));
			if (this.listaFuncionariosCondominio.getRowCount() == 0){
				ManagedBeanUtil.setMensagemInfo("msg.funcionarioBloco.semFuncionarios");
			}
		} catch (SQLException e) {
			logger.error("erro sqlstate "+e.getSQLState(), e);	
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");				
		} catch (Exception e) {
			logger.error("", e);
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		}
	}
	
	public void pesquisarFuncionarioBloco(ActionEvent event){
		try {			
			this.popularNomeCondominido();
			this.listaFuncionariosBloco = new ListDataModel<Funcionario>(this.funcionarioService.buscarPorBlocoENomeFuncionario(this.bloco,this.funcionario.getNome()));
			if (this.listaFuncionariosBloco.getRowCount() == 0){
				ManagedBeanUtil.setMensagemInfo("msg.funcionarioBloco.semFuncionarios");
			}
		} catch (SQLException e) {
			logger.error("erro sqlstate "+e.getSQLState(), e);	
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");				
		} catch (Exception e) {
			logger.error("", e);
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		}
	}
	
	public void popularListaSIBlocos(){		
		this.listaSIBlocos  = this.blocoMB.buscarListaBlocosPorCondominio(this.condominio);	
	}
	
	public void popularListaBlocos(){		
		try {
			this.listaBlocos = this.blocoService.buscarPorCondominioENome(this.condominio,null);
			this.listaFuncionarioBlocos = new ArrayList<Bloco>();			
			this.conjuntoBlocoService.configuraTipoConjuntoBloco(TipoConjuntoBlocoEnum.FUNCIONARIO.getConjuntoBloco());
			ConjuntoBloco conjuntoBloco = this.conjuntoBlocoService.buscarPorId(this.funcionario.getIdConjuntoBloco());
			if(conjuntoBloco != null){
				for (BlocoConjuntoBloco blocoConjuntoBloco : conjuntoBloco.getListaListaBlocoConjuntoBlocos()) {
					this.listaFuncionarioBlocos.add(blocoConjuntoBloco.getBloco());			
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
	
	public String visualizarFuncionarioCondominio(){
		this.funcionario = this.listaFuncionariosCondominio.getRowData();		
		return "visualizar";
	}
	
	public String visualizarFuncionarioBloco() {
		try {
			this.funcionario = this.listaFuncionariosBloco.getRowData();
			this.listaNomeBlocos = new ArrayList<String>(); 
			this.conjuntoBlocoService.configuraTipoConjuntoBloco(TipoConjuntoBlocoEnum.FUNCIONARIO.getConjuntoBloco());
			ConjuntoBloco conjuntoBloco = this.conjuntoBlocoService.buscarPorId(this.funcionario.getIdConjuntoBloco());
			for (BlocoConjuntoBloco blocoConjuntoBloco : conjuntoBloco.getListaListaBlocoConjuntoBlocos()) {
				this.listaNomeBlocos.add(blocoConjuntoBloco.getBloco().getNome());
			}
		} catch (SQLException e) {
			logger.error("erro sqlstate "+e.getSQLState(), e);	
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");				
		} catch (Exception e) {
			logger.error("", e);
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		}
		return "visualizar";
	}
	
	public String editarFuncionarioCondominio(){
		this.funcionario = this.listaFuncionariosCondominio.getRowData();
		return "editar";
	}
	
	public List<SelectItem> getListaSituacoes(){
		List<SelectItem> listaSituacoes = new ArrayList<SelectItem>();
		listaSituacoes.add(new SelectItem(UsuarioSituacaoEnum.INATIVO.getSituacao(), AplicacaoUtil.i18n("funcionario.situacao.itemLabel.0")));
		listaSituacoes.add(new SelectItem(UsuarioSituacaoEnum.ATIVO.getSituacao(), AplicacaoUtil.i18n("funcionario.situacao.itemLabel.1")));
		return listaSituacoes;
	}
	
	public String cadastroFuncionarioCondominio(){
		this.funcionario = new Funcionario();		
		return "cadastrar";
	}	
	        
	public String cadastroFuncionarioBloco(){
		this.funcionario = new Funcionario();
		this.condominio = new Condominio();
		this.bloco = new Bloco();
		this.listaSIBlocos = new ArrayList<SelectItem>();
		return "cadastrar";
	}
	
	public String salvarFuncionarioCondominio(){
		try {
			this.funcionario.setIdCondominio(this.condominio.getId());		
			if(!this.funcionario.getSenha().equals(this.funcionario.getConfirmarSenha())){
				ManagedBeanUtil.setMensagemErro("msg.funcionario.senhasNaoCorrespondem");
				return null;
			}
			if (!ManagedBeanUtil.validaEmail(this.funcionario.getEmail().getEmail())){
				ManagedBeanUtil.setMensagemErro("msg.condomino.formatoEmailInvalido");
				return null;
			}
			List<Condominio> listaCondominio = new ArrayList<Condominio>();
			listaCondominio.add(this.condominio);
			this.funcionario.setListaCondominio(listaCondominio);
			this.popularGrupoUsuarioFuncionario();
			this.funcionario.getEmail().setPrincipal(Boolean.TRUE);
			this.funcionarioService.salvar(this.funcionario);
			this.funcionario = new Funcionario();
			// Seta vazio, pois quando pesquisado via xhtml esse atributo vem vazio.			
			this.funcionario.setNome("");
			this.pesquisarFuncionarioCondominio(null);
			ManagedBeanUtil.setMensagemInfo("msg.funcionarioCondominio.salvaSucesso");
		} catch (SQLException e) {
			logger.error("erro sqlstate "+e.getSQLState(), e);	
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");	
			return null;
		}catch (BusinessException e) {				
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage());			
			return null;	
		} catch (Exception e) {
			logger.error("", e);
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
			return null;
		}
		return "salvar";	
	}
	
	public String salvarFuncionarioBloco(){
		try {
			if(!this.funcionario.getSenha().equals(this.funcionario.getConfirmarSenha())){
				ManagedBeanUtil.setMensagemErro("msg.funcionario.senhasNaoCorrespondem");
				return null;
			}
			if (!ManagedBeanUtil.validaEmail(this.funcionario.getEmail().getEmail())){
				ManagedBeanUtil.setMensagemErro("msg.condomino.formatoEmailInvalido");
				return null;
			}
			// Inicialmente, entende-se que os blocos selecinados não fazem parte de um conjunto de blocos existentes.
			Boolean todosBlocosMesmoConjunto = Boolean.FALSE;
			Integer quantidadeBlocosIguais = 0;
			this.conjuntoBlocoService.configuraTipoConjuntoBloco(TipoConjuntoBlocoEnum.FUNCIONARIO.getConjuntoBloco());
			List<ConjuntoBloco> listaConjuntoBloco = this.conjuntoBlocoService.buscarPorTipo(TipoConjuntoBlocoEnum.FUNCIONARIO.getConjuntoBloco());
			Iterator<ConjuntoBloco> iteratorConjuntoBloco = listaConjuntoBloco.iterator();
			while(iteratorConjuntoBloco.hasNext() && !todosBlocosMesmoConjunto){
				ConjuntoBloco conjuntoBloco = iteratorConjuntoBloco.next();
				// Testa somente os conjunto de blocos que tem a mesma quantidade de blocos.
				if (conjuntoBloco.getListaListaBlocoConjuntoBlocos().size() == this.listaFuncionarioBlocos.size()){
					quantidadeBlocosIguais = 0;
					for (BlocoConjuntoBloco blocoBase: conjuntoBloco.getListaListaBlocoConjuntoBlocos()) {
						for (Bloco blocoTela : this.listaFuncionarioBlocos) {
							if(blocoBase.getBloco().getId() == blocoTela.getId()){
								quantidadeBlocosIguais++;
							}
						}
					}				
					if(quantidadeBlocosIguais == this.listaFuncionarioBlocos.size()){
						todosBlocosMesmoConjunto = Boolean.TRUE;
					}
				}
			}
			// Bloco criado para ser utilizado na pesquisa, que será realizada após a criação do funcionário.		
			this.bloco = new Bloco();
			List<Condominio> listaCondominio = new ArrayList<Condominio>();
			listaCondominio.add(this.condominio);
			this.funcionario.setListaCondominio(listaCondominio);
			this.popularGrupoUsuarioFuncionario();
			this.funcionario.getEmail().setPrincipal(Boolean.TRUE);
			// Caso sejam todos os blocos do mesmo conjunto, então eu salvo esse funcionário associando ele a esse conjunto
			if(todosBlocosMesmoConjunto){
				this.funcionario.setIdConjuntoBloco(listaConjuntoBloco.get(0).getId());
				// Pega-se o primeiro bloco da lista de conjunto de blocos para pesquisar.
				this.bloco.setId(listaConjuntoBloco.get(0).getListaListaBlocoConjuntoBlocos().get(0).getBloco().getId());
				this.funcionarioService.salvar(this.funcionario);
			}else{
				List<TipoConjuntoBloco> listaFuncionarios = new ArrayList<TipoConjuntoBloco>();
				listaFuncionarios.add(this.funcionario);
				ConjuntoBloco conjuntoBloco = new ConjuntoBloco();
				conjuntoBloco.setTipo(TipoConjuntoBlocoEnum.FUNCIONARIO.getConjuntoBloco());
				conjuntoBloco.setListaTipoConjuntoBlocos(listaFuncionarios);
				List<BlocoConjuntoBloco> listaBlocoConjuntoBloco = new ArrayList<BlocoConjuntoBloco>();
				BlocoConjuntoBloco blocoConjuntoBloco = null;
				// Pega-se o primeiro bloco da lista de conjunto bloco para pesquisar.
				this.bloco.setId(this.listaFuncionarioBlocos.get(0).getId());
				for (Bloco bloco : this.listaFuncionarioBlocos) {
					blocoConjuntoBloco = new BlocoConjuntoBloco();
					blocoConjuntoBloco.setConjuntoBloco(conjuntoBloco);
					blocoConjuntoBloco.setBloco(bloco);
					listaBlocoConjuntoBloco.add(blocoConjuntoBloco);
				}
				conjuntoBloco.setListaListaBlocoConjuntoBlocos(listaBlocoConjuntoBloco);
				this.conjuntoBlocoService.salvar(conjuntoBloco);
			}
			this.funcionario = new Funcionario();
			// Seta vazio, pois quando pesquisado via xhtml esse atributo vem vazio.			
			this.funcionario.setNome("");
			this.pesquisarFuncionarioBloco(null);
			ManagedBeanUtil.setMensagemInfo("msg.funcionarioBloco.salvaSucesso");
		} catch (SQLException e) {
			logger.error("erro sqlstate "+e.getSQLState(), e);	
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");	
			return null;
		} catch (BusinessException e) {				
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage());			
			return null;
		} catch (Exception e) {
			logger.error("", e);
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
			return null;
		}
		return "salvar";	
	}
	
	public void limparFiltroFuncionarioCondominio (ActionEvent event){
		this.listaFuncionariosCondominio = new ListDataModel<Funcionario>();
		this.funcionario = new Funcionario();
		this.limpaFormCadastroFuncionario();
	}
	
	public void limparFiltroFuncionarioBloco (ActionEvent event){
		this.listaFuncionariosBloco = new ListDataModel<Funcionario>();
		this.funcionario = new Funcionario();
		this.limpaFormCadastroFuncionario();
	}
	
	public String cancelarCadastroFuncionarioCondominio(){
		this.listaFuncionariosCondominio = new ListDataModel<Funcionario>();
		this.funcionario = new Funcionario();
		ManagedBeanUtil.cleanSubmittedValues(this.componenteNomeFuncionario);
		this.condominio = new Condominio();		
		return "cancelar";
	}
	
	public String cancelarCadastroFuncionarioBloco(){
		this.listaFuncionariosCondominio = new ListDataModel<Funcionario>();
		this.listaSICondominios = new ArrayList<SelectItem>();
		this.listaSIBlocos = new ArrayList<SelectItem>();
		this.funcionario = new Funcionario();
		this.limpaFormCadastroFuncionario();
		return "cancelar";
	}
	
	public String excluirFuncionarioCondominio(){	
		try {
			this.funcionarioService.excluir(this.funcionario);
			this.funcionario = new Funcionario();
			// Seta vazio, pois quando pesquisado via xhtml esse atributo vem vazio.
			this.funcionario.setNome("");
			this.pesquisarFuncionarioCondominio(null);
			ManagedBeanUtil.setMensagemInfo("msg.funcionarioCondominio.excluirSucesso");
			return "excluir";
		} catch (SQLException e) {
			logger.error("erro sqlstate "+e.getSQLState(), e);	
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");	
			return null;
		} catch (Exception e) {
			logger.error("", e);
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
			return null;
		}
	}
	
	public String excluirFuncionarioBloco(){	
		try {			
			List<Funcionario> listaFuncionario = this.funcionarioService.buscarPorIdConjuntoBloco(this.funcionario.getIdConjuntoBloco());			
			// Caso exista somente um funcionário associado a esse conjunto de blocos, exclui-se também o conjunto de blocos
			if(listaFuncionario.size() == 1){
				this.conjuntoBlocoService.configuraTipoConjuntoBloco(TipoConjuntoBlocoEnum.FUNCIONARIO.getConjuntoBloco());
				ConjuntoBloco conjuntoBloco = this.conjuntoBlocoService.buscarPorId(this.funcionario.getIdConjuntoBloco());
				this.conjuntoBlocoService.excluir(conjuntoBloco);
			}else{
				this.funcionarioService.excluir(this.funcionario);
			}
			this.funcionario = new Funcionario();
			// Seta vazio, pois quando pesquisado via xhtml esse atributo vem vazio.			
			this.funcionario.setNome("");
			this.pesquisarFuncionarioBloco(null);
			ManagedBeanUtil.setMensagemInfo("msg.funcionarioBloco.excluirSucesso");
			return "excluir";
		} catch (SQLException e) {
			logger.error("erro sqlstate "+e.getSQLState(), e);	
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");	
			return null;
		} catch (Exception e) {
			logger.error("", e);
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
			return null;
		}
	}
	
	public String atualizarFuncionarioCondominio(){
		try {
			this.funcionario.setIdCondominio(this.condominio.getId());
			if (!ManagedBeanUtil.validaEmail(this.funcionario.getEmail().getEmail())){
				ManagedBeanUtil.setMensagemErro("msg.condomino.formatoEmailInvalido");
				return null;
			}
			this.funcionarioService.atualizar(this.funcionario);
			this.funcionario = new Funcionario();
			// Seta vazio, pois quando pesquisado via xhtml esse atributo vem vazio.
			this.funcionario.setNome("");
			this.pesquisarFuncionarioCondominio(null);
			ManagedBeanUtil.setMensagemInfo("msg.funcionarioCondominio.atualizadoSucesso");
		} catch (SQLException e) {
			logger.error("erro sqlstate "+e.getSQLState(), e);	
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");	
		} catch (Exception e) {
			logger.error("", e);
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		}
		return "atualizar";		
	}
	
	public String atualizarFuncionarioBloco(){
		try {
			if (!ManagedBeanUtil.validaEmail(this.funcionario.getEmail().getEmail())){
				ManagedBeanUtil.setMensagemErro("msg.funcionario.formatoEmailInvalido");
				return null;
			}
			// Inicialmente, entende-se que os blocos selecinados não fazem parte de um conjunto de blocos existentes.
			Boolean todosBlocosMesmoConjunto = Boolean.FALSE;
			Integer quantidadeBlocosIguais = 0;
			this.conjuntoBlocoService.configuraTipoConjuntoBloco(TipoConjuntoBlocoEnum.FUNCIONARIO.getConjuntoBloco());
			ConjuntoBloco conjuntoBloco = this.conjuntoBlocoService.buscarPorId(this.funcionario.getIdConjuntoBloco());		
			// Testa somente os conjunto de blocos que tem a mesma quantidade de blocos.
			if (conjuntoBloco.getListaListaBlocoConjuntoBlocos().size() == this.listaFuncionarioBlocos.size()){
				quantidadeBlocosIguais = 0;
				for (BlocoConjuntoBloco blocoBase: conjuntoBloco.getListaListaBlocoConjuntoBlocos()) {
					for (Bloco blocoTela : this.listaFuncionarioBlocos) {
						if(blocoBase.getBloco().getId() == blocoTela.getId()){
							quantidadeBlocosIguais++;
						}
					}
				}				
				if(quantidadeBlocosIguais == this.listaFuncionarioBlocos.size()){
					todosBlocosMesmoConjunto = Boolean.TRUE;
				}		
			}
			List<Condominio> listaCondominio = new ArrayList<Condominio>();
			listaCondominio.add(this.condominio);
			this.funcionario.setListaCondominio(listaCondominio);
			this.popularGrupoUsuarioFuncionario();
			// Caso sejam todos os blocos do mesmo conjunto, então atualizo esse funcionário associando ele a esse conjunto
			if(todosBlocosMesmoConjunto){
				this.funcionarioService.atualizar(this.funcionario);		 	
			}else{
				List<TipoConjuntoBloco> listaFuncionarios = new ArrayList<TipoConjuntoBloco>();
				List<BlocoConjuntoBloco> listaBlocoConjuntoBloco = new ArrayList<BlocoConjuntoBloco>();
				listaFuncionarios.add(this.funcionario);
				conjuntoBloco.setListaTipoConjuntoBlocos(listaFuncionarios);			
				BlocoConjuntoBloco blocoConjuntoBloco = null;
				for (Bloco bloco : this.listaFuncionarioBlocos) {
					blocoConjuntoBloco = new BlocoConjuntoBloco();
					blocoConjuntoBloco.setConjuntoBloco(conjuntoBloco);
					blocoConjuntoBloco.setBloco(bloco);
					listaBlocoConjuntoBloco.add(blocoConjuntoBloco);
				}
				conjuntoBloco.setListaListaBlocoConjuntoBlocos(listaBlocoConjuntoBloco);
				this.conjuntoBlocoService.atualizar(conjuntoBloco);			
				}
			this.funcionario = new Funcionario();
			// Seta vazio, pois quando pesquisado via xhtml esse atributo vem vazio.			
			this.funcionario.setNome("");
			this.pesquisarFuncionarioBloco(null);
			ManagedBeanUtil.setMensagemInfo("msg.funcionarioBloco.atualizadoSucesso");
		} catch (SQLException e) {
			logger.error("erro sqlstate "+e.getSQLState(), e);	
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");	
		} catch (Exception e) {
			logger.error("", e);
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		}
		return "atualizar";
	}
	
	public String editarFuncionarioBloco(){
		this.funcionario = this.listaFuncionariosBloco.getRowData();
		this.popularListaBlocos();
		return "editar";
	}
	
	 public void getImagem(OutputStream out, Object data)  {
		try{
			if(this.funcionario.getImagem() != null){			  
				out.write(this.funcionario.getImagem().getDadosArquivo());			  
			}else{
				byte[] sendBuf = ManagedBeanUtil.popularImagemNaoDisponivel();			  
				out.write(sendBuf);
			}
			out.close();
		} catch (Exception e) {
			logger.error("", e);
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		}
	}	 
	 
	public String cancelarCadastrarDespesasCondominio(){
		return "cancelar";
	}

	public String voltarVisualizarFuncionarioCondominio(){		
		return "voltar";
	}
	
	public String voltarVisualizarFuncionarioBloco(){		          
		return "voltar";
	}
	
	private void popularGrupoUsuarioFuncionario() throws SQLException, Exception{
		List<GrupoUsuario> listaGrupoUsuario = new ArrayList<GrupoUsuario>();
		listaGrupoUsuario.addAll(this.grupoUsuarioService.buscarPorIdCondominioEPadraoETipoUsuarioESituacao(this.condominio.getId(),
				GrupoUsuarioPadraoEnum.SIM.getPadrao(), GrupoUsuarioTipoUsuarioEnum.FUNCIONARIO.getTipoUsuario(),GrupoUsuarioSituacaoEnum.ATIVO.getSituacao()));
		if(listaGrupoUsuario.isEmpty()){
			listaGrupoUsuario.add(this.grupoUsuarioService.buscarPorPadraoETipoUsuarioESituacao(GrupoUsuarioPadraoEnum.SIM.getPadrao(), 
					GrupoUsuarioTipoUsuarioEnum.FUNCIONARIO.getTipoUsuario(), GrupoUsuarioSituacaoEnum.ATIVO.getSituacao()));			
		}
		this.funcionario.setListaGrupoUsuario(listaGrupoUsuario);
			
	}
	
	public Condominio getCondominio() {
		return condominio;
	}

	public void setCondominio(Condominio condominio) {
		this.condominio = condominio;
	}

	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}

	public ListDataModel<Funcionario> getListaFuncionariosCondominio() {
		return listaFuncionariosCondominio;
	}

	public void setListaFuncionariosCondominio(ListDataModel<Funcionario> listaFuncionariosCondominio) {
		this.listaFuncionariosCondominio = listaFuncionariosCondominio;
	}

	public ListDataModel<Funcionario> getListaFuncionariosBloco() {
		return listaFuncionariosBloco;
	}

	public void setListaFuncionariosBloco(
			ListDataModel<Funcionario> listaFuncionariosBloco) {
		this.listaFuncionariosBloco = listaFuncionariosBloco;
	}

	public BlocoMB getBlocoMB() {
		return blocoMB;
	}

	public void setBlocoMB(BlocoMB blocoMB) {
		this.blocoMB = blocoMB;
	}

	public FuncionarioService getFuncionarioService() {
		return funcionarioService;
	}

	public void setFuncionarioService(FuncionarioService funcionarioService) {
		this.funcionarioService = funcionarioService;
	}

	public ConjuntoBlocoService getConjuntoBlocoService() {
		return conjuntoBlocoService;
	}

	public void setConjuntoBlocoService(ConjuntoBlocoService conjuntoBlocoService) {
		this.conjuntoBlocoService = conjuntoBlocoService;
	}

	public BlocoService getBlocoService() {
		return blocoService;
	}

	public void setBlocoService(BlocoService blocoService) {
		this.blocoService = blocoService;
	}

	public List<SelectItem> getListaSICondominios() {
		this.listaSICondominios = this.condominioMB.buscarListaCondominiosAtivos();
		return listaSICondominios;
	}

	public void setListaSICondominios(List<SelectItem> listaSICondominios) {
		this.listaSICondominios = listaSICondominios;
	}
	
	public List<SelectItem> getListaSIDias() {
		return listaSIDias;
	}

	public void setListaSIDias(List<SelectItem> listaSIDias) {
		this.listaSIDias = listaSIDias;
	}

	public List<SelectItem> getListaSIMeses() {
		return listaSIMeses;
	}

	public void setListaSIMeses(List<SelectItem> listaSIMeses) {
		this.listaSIMeses = listaSIMeses;
	}

	public List<SelectItem> getListaSIAnos() {
		return listaSIAnos;
	}

	public void setListaSIAnos(List<SelectItem> listaSIAnos) {
		this.listaSIAnos = listaSIAnos;
	}
	
	public List<SelectItem> getListaSIBlocos() {
		return listaSIBlocos;
	}

	public void setListaSIBlocos(List<SelectItem> listaSIBlocos) {
		this.listaSIBlocos = listaSIBlocos;
	}	

	public Bloco getBloco() {
		return bloco;
	}

	public void setBloco(Bloco bloco) {
		this.bloco = bloco;
	}	

	public List<Bloco> getListaBlocos() {
		return listaBlocos;
	}

	public void setListaBlocos(List<Bloco> listaBlocos) {
		this.listaBlocos = listaBlocos;
	}	

	public List<Bloco> getListaFuncionarioBlocos() {
		return listaFuncionarioBlocos;
	}

	public void setListaFuncionarioBlocos(List<Bloco> listaFuncionarioBlocos) {
		this.listaFuncionarioBlocos = listaFuncionarioBlocos;
	}	

	public List<String> getListaNomeBlocos() {
		return listaNomeBlocos;
	}

	public void setListaNomeBlocos(List<String> listaNomeBlocos) {
		this.listaNomeBlocos = listaNomeBlocos;
	}		

	public CondominioMB getCondominioMB() {
		return condominioMB;
	}

	public void setCondominioMB(CondominioMB condominioMB) {
		this.condominioMB = condominioMB;
	}	

	public UIInput getComponenteNomeFuncionario() {
		return componenteNomeFuncionario;
	}

	public void setComponenteNomeFuncionario(UIInput componenteNomeFuncionario) {
		this.componenteNomeFuncionario = componenteNomeFuncionario;
	}	

	public UIInput getComponenteFuncaoFuncionario() {
		return componenteFuncaoFuncionario;
	}

	public void setComponenteFuncaoFuncionario(UIInput componenteFuncaoFuncionario) {
		this.componenteFuncaoFuncionario = componenteFuncaoFuncionario;
	}

	public UIInput getComponenteEmailFuncionario() {
		return componenteEmailFuncionario;
	}

	public void setComponenteEmailFuncionario(UIInput componenteEmailFuncionario) {
		this.componenteEmailFuncionario = componenteEmailFuncionario;
	}

	public UIInput getComponenteTelefoneResidencialFuncionario() {
		return componenteTelefoneResidencialFuncionario;
	}

	public void setComponenteTelefoneResidencialFuncionario(
			UIInput componenteTelefoneResidencialFuncionario) {
		this.componenteTelefoneResidencialFuncionario = componenteTelefoneResidencialFuncionario;
	}

	public UIInput getComponenteTelefoneCelularFuncionario() {
		return componenteTelefoneCelularFuncionario;
	}

	public void setComponenteTelefoneCelularFuncionario(
			UIInput componenteTelefoneCelularFuncionario) {
		this.componenteTelefoneCelularFuncionario = componenteTelefoneCelularFuncionario;
	}

	public UIInput getComponenteSenhaFuncionario() {
		return componenteSenhaFuncionario;
	}

	public void setComponenteSenhaFuncionario(UIInput componenteSenhaFuncionario) {
		this.componenteSenhaFuncionario = componenteSenhaFuncionario;
	}

	public UIInput getComponenteConfirmarSenhaFuncionario() {
		return componenteConfirmarSenhaFuncionario;
	}

	public void setComponenteConfirmarSenhaFuncionario(
			UIInput componenteConfirmarSenhaFuncionario) {
		this.componenteConfirmarSenhaFuncionario = componenteConfirmarSenhaFuncionario;
	}

	private void popularNomeCondominido(){
		Boolean encontrou = Boolean.FALSE;
		Integer i = 0;
		while(!encontrou){
			SelectItem selectItem = this.getListaSICondominios().get(i++);
			if(selectItem.getValue() == this.getCondominio().getId()){
				this.getCondominio().setNome(selectItem.getLabel());
				encontrou = Boolean.TRUE;
			}
		}
	}
	
	private void limpaFormCadastroFuncionario(){		
		ManagedBeanUtil.cleanSubmittedValues(this.componenteNomeFuncionario);		
		ManagedBeanUtil.cleanSubmittedValues(this.componenteFuncaoFuncionario);
		ManagedBeanUtil.cleanSubmittedValues(this.componenteEmailFuncionario);
		ManagedBeanUtil.cleanSubmittedValues(this.componenteTelefoneCelularFuncionario);
		ManagedBeanUtil.cleanSubmittedValues(this.componenteTelefoneResidencialFuncionario);
		ManagedBeanUtil.cleanSubmittedValues(this.componenteSenhaFuncionario);
		ManagedBeanUtil.cleanSubmittedValues(this.componenteConfirmarSenhaFuncionario);		
	}

}