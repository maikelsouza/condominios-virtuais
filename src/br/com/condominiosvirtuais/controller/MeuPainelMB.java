package br.com.condominiosvirtuais.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.event.ActionEvent;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.log4j.Logger;
import org.richfaces.event.FileUploadEvent;
import org.richfaces.model.UploadedFile;

import br.com.condominiosvirtuais.entity.Arquivo;
import br.com.condominiosvirtuais.entity.Bloco;
import br.com.condominiosvirtuais.entity.Condominio;
import br.com.condominiosvirtuais.entity.Condomino;
import br.com.condominiosvirtuais.entity.Contador;
import br.com.condominiosvirtuais.entity.Funcionario;
import br.com.condominiosvirtuais.entity.Garagem;
import br.com.condominiosvirtuais.entity.GrupoUsuario;
import br.com.condominiosvirtuais.entity.SindicoProfissional;
import br.com.condominiosvirtuais.entity.Unidade;
import br.com.condominiosvirtuais.entity.Usuario;
import br.com.condominiosvirtuais.entity.Veiculo;
import br.com.condominiosvirtuais.enumeration.GaragemEnum;
import br.com.condominiosvirtuais.enumeration.GrupoUsuarioPadraoEnum;
import br.com.condominiosvirtuais.enumeration.GrupoUsuarioSituacaoEnum;
import br.com.condominiosvirtuais.enumeration.GrupoUsuarioTipoUsuarioEnum;
import br.com.condominiosvirtuais.enumeration.UsuarioSituacaoEnum;
import br.com.condominiosvirtuais.enumeration.VeiculoTipoEnum;
import br.com.condominiosvirtuais.exception.BusinessException;
import br.com.condominiosvirtuais.service.ArquivoService;
import br.com.condominiosvirtuais.service.BlocoService;
import br.com.condominiosvirtuais.service.CondominioService;
import br.com.condominiosvirtuais.service.CondominoService;
import br.com.condominiosvirtuais.service.ContadorService;
import br.com.condominiosvirtuais.service.FuncionarioService;
import br.com.condominiosvirtuais.service.GaragemService;
import br.com.condominiosvirtuais.service.GrupoUsuarioService;
import br.com.condominiosvirtuais.service.SindicoProfissionalService;
import br.com.condominiosvirtuais.service.UnidadeService;
import br.com.condominiosvirtuais.service.UsuarioService;
import br.com.condominiosvirtuais.service.VeiculoService;
import br.com.condominiosvirtuais.util.AplicacaoUtil;
import br.com.condominiosvirtuais.util.ManagedBeanUtil;

@Named @SessionScoped
public class MeuPainelMB implements  Serializable{
	
	private static final long serialVersionUID = 1L;

	private static Logger logger = Logger.getLogger(MeuPainelMB.class);
		
	private static final String ID_TAB_DADOS_PESSOAIS = "idTabDadosPessoais";
	
	private static final String ID_TAB_ALTERAR_SENHA = "idTabAlterarSenha";
	
	private static final String ID_TAB_ALTERAR_IMAGEM_CONDOMINO = "idTabAlterarImagemCondomino";
	
	private static final String ID_TAB_ALTERAR_IMAGEM_FUNCIONARIO = "idTabAlterarImagemFuncionario";
	
	private static final String ID_TAB_NOVO_CONDOMINO = "idTabNovoCondomino";
	
	private static final String ID_TAB_DADOS_PESSOAIS_FUNCIONARIO = "idTabDadosPessoaisFuncionario";
	
	private static final String ID_TAB_DADOS_PESSOAIS_SINDICO_PROFISSIONAL = "idTabDadosPessoaisSindicoProfissional";
	
	private static final String ID_TAB_ALTERAR_SENHA_SINDICO_PROFISSIONAL = "idTabAlterarSenhaSindicoProfissional";	
	
	private static final String ID_TAB_ALTERAR_IMAGEM_SINDICO_PROFISSIONAL = "idTabAlterarImagemSindicoProfissional";
	
	private static final String ID_TAB_DADOS_PESSOAIS_CONTADOR = "idTabDadosPessoaisContador";
	
	private static final String ID_TAB_ALTERAR_SENHA_CONTADOR = "idTabAlterarSenhaContador";	
	
	private static final String ID_TAB_ALTERAR_SENHA_FUNCIONARIO = "idTabAlterarSenhaFuncionario";	
	
	private static final String ID_TAB_NOVA_GARAGEM = "idTabNovaGaragem";	
	
	private static final String ID_TAB_NOVO_VEICULO = "idTabNovoVeiculo";	
	
	private Condomino condomino;
	
	private Condomino novoCondomino = new Condomino();
	
	private Garagem garagem;
	
	private Veiculo veiculo;
	
	private Funcionario funcionario;
	
	private SindicoProfissional sindicoProfissional;
	
	private Contador contador;
	
	@Inject
	private UsuarioService usuarioService;
	
	@Inject
	private CondominoService condominoService;
	
	@Inject
	private CondominioService condominioService;
	
	@Inject
	private BlocoService blocoService;
	
	@Inject
	private UnidadeService unidadeService;
	
	@Inject
	private FuncionarioService funcionarioService;
	
	@Inject
	private ArquivoService arquivoService;
	
	@Inject
	private GaragemService garagemService;
	
	@Inject
	private VeiculoService veiculoService;
	
	@Inject
	private SindicoProfissionalService sindicoProfissionalService;
	
	@Inject
	private ContadorService contadorService;
	
	@Inject
	private GrupoUsuarioService grupoUsuarioService = null;
	
	private String senhaAtualCondomino;
	
	private String senhaCondomino;
	
	private String confirmarSenhaCondomino;
	
	private String senhaAtualFuncionario;
	
	private String senhaFuncionario;
	
	private String confirmarSenhaFuncionario;
	
	private String senhaAtualSindicoProfissional;
	
	private String senhaSindicoProfissional;
	
	private String senhaAtualContador;
	
	private String senhaContador;	
	
	private String confirmarSenhaContador;
	
	private String confirmarSenhaSindicoProfissional;
	
	private String telefoneResidencialNovoCondomino = "0";
	
	private String telefoneCelularNovoCondomino = "0";
	
	private String telefoneComercialNovoCondomino = "0";
	
	private String telefoneResidencialCondomino;
	
	private String telefoneCelularCondomino;
	
	private String telefoneComercialCondomino;	
	
	private String telefoneCelularFuncionario;
	
	private String telefoneComercialSindicoProfissional;
	
	private String telefoneCelular1SindicoProfissional;
	
	private String telefoneCelular2SindicoProfissional;
	
	private String telefoneCelular3SindicoProfissional;
	
	private String telefoneResidencialFuncionario = "0";	
	
	private String tabSelecionada;
	
	private String tabSelecionadaFuncionario;
	
	private List<SelectItem> listaSIDias = new ArrayList<SelectItem>();
	
	private List<SelectItem> listaSIMeses = new ArrayList<SelectItem>();
	
	private List<SelectItem> listaSIAnos = new ArrayList<SelectItem>();	
	
	private ArrayList<Arquivo> arquivos = new ArrayList<Arquivo>();
	
	private ListDataModel<Garagem> listaDMGaragem = null;
	
	private ListDataModel<Veiculo> listaDMVeiculo = null;
	
	private List<Garagem> listaGaragem = null;
	
	private List<SelectItem> listaSIGaragem = null;
	
	private Boolean renderedCadastroNovaGaragem = Boolean.TRUE;		
	
	private Boolean renderedCadastroNovoVeiculo = Boolean.TRUE;	
		

	@PostConstruct
	public void iniciarMeuPainelMB(){
		this.popularMeuPainel();
	}
	
	
	
	public void popularMeuPainel(){	
		try{			
			Usuario usuario = AplicacaoUtil.getUsuarioAutenticado();
			Arquivo arquivo = null;
			if(this.validaTiposUsuariosGruposUsuarios(usuario.getListaGrupoUsuario(), GrupoUsuarioTipoUsuarioEnum.CONDOMINO.getTipoUsuario(),
					GrupoUsuarioTipoUsuarioEnum.SINDICO.getTipoUsuario())){
// TODO: C�digo comentado em 21/08/2017. Apagar em 180 dias				
//			if(usuario.getIdGrupoUsuario().equals(TipoGrupoUsuarioEnum.CONDOMINO.getGrupoUsuario()) || 
//					usuario.getIdGrupoUsuario().equals(TipoGrupoUsuarioEnum.SINDICO.getGrupoUsuario())){
				this.condomino = this.condominoService.buscarPorId(usuario.getId());			
				this.telefoneCelularCondomino = String.valueOf(this.condomino.getTelefoneCelular() == null ? "" : this.condomino.getTelefoneCelular());
				this.telefoneComercialCondomino = String.valueOf(this.condomino.getTelefoneComercial()  == null ? "" : this.condomino.getTelefoneComercial());
				this.telefoneResidencialCondomino = String.valueOf(this.condomino.getTelefoneResidencial() == null ? "" : this.condomino.getTelefoneResidencial());
				this.arquivos.clear();			
				this.setTabSelecionada(ID_TAB_DADOS_PESSOAIS);
				if(this.condomino.getImagem() == null){
					arquivo = new Arquivo();
					arquivo.setId(-1);
					arquivo.setDadosArquivo(ManagedBeanUtil.popularImagemNaoDisponivel());
					this.arquivos.add(arquivo);
				}else{
					this.arquivos.add(this.condomino.getImagem());
				}
				ManagedBeanUtil.popularSIDias(this.listaSIDias);
				ManagedBeanUtil.popularSIMeses(this.listaSIMeses);
				ManagedBeanUtil.popularSIAnos(this.listaSIAnos);
				this.popularDMGaragem();
				this.garagem = new Garagem();
				this.veiculo = new Veiculo();						
				this.popularSIGaragem();
				this.popularDMVeiculo();
			}
			if(this.validaTiposUsuariosGruposUsuarios(usuario.getListaGrupoUsuario(), GrupoUsuarioTipoUsuarioEnum.FUNCIONARIO.getTipoUsuario())){
// TODO: C�digo comentado em 21/08/2017. Apagar em 180 dias				
//			if(usuario.getIdGrupoUsuario().equals(TipoGrupoUsuarioEnum.FUNCIONARIO.getGrupoUsuario())){
				this.funcionario = this.funcionarioService.buscarPorId(usuario.getId());
				this.setTabSelecionada(ID_TAB_DADOS_PESSOAIS_FUNCIONARIO);
				this.telefoneCelularFuncionario = String.valueOf(this.funcionario.getTelefoneCelular());
				this.telefoneResidencialFuncionario = String.valueOf(this.funcionario.getTelefoneResidencial());
				if(this.funcionario.getImagem() == null){
					arquivo = new Arquivo();
					arquivo.setId(-1);
					arquivo.setDadosArquivo(ManagedBeanUtil.popularImagemNaoDisponivel());
					this.arquivos.add(arquivo);
				}else{
					this.arquivos.add(this.funcionario.getImagem());
				}				
				ManagedBeanUtil.popularSIDias(this.listaSIDias);
				ManagedBeanUtil.popularSIMeses(this.listaSIMeses);
				ManagedBeanUtil.popularSIAnos(this.listaSIAnos);
			}if(this.validaTiposUsuariosGruposUsuarios(usuario.getListaGrupoUsuario(), GrupoUsuarioTipoUsuarioEnum.SINDICO_PROFISSIONAL.getTipoUsuario())){
// TODO: C�digo comentado em 21/08/2017. Apagar em 180 dias			
//			}if(usuario.getIdGrupoUsuario().equals(TipoGrupoUsuarioEnum.SINDICO_PROFISSIONAL.getGrupoUsuario())){
				this.sindicoProfissional = this.sindicoProfissionalService.buscarPorId(usuario.getId());
				this.setTabSelecionada(ID_TAB_DADOS_PESSOAIS_SINDICO_PROFISSIONAL);
				this.telefoneComercialSindicoProfissional = String.valueOf(this.sindicoProfissional.getTelefoneComercial() == null ? "" : this.sindicoProfissional.getTelefoneComercial());
				this.telefoneCelular1SindicoProfissional = String.valueOf(this.sindicoProfissional.getTelefoneCelular1()  == null ? "" : this.sindicoProfissional.getTelefoneCelular1());
				this.telefoneCelular2SindicoProfissional = String.valueOf(this.sindicoProfissional.getTelefoneCelular2() == null ? "" : this.sindicoProfissional.getTelefoneCelular2());
				this.telefoneCelular3SindicoProfissional = String.valueOf(this.sindicoProfissional.getTelefoneCelular3() == null ? "" : this.sindicoProfissional.getTelefoneCelular3());
				ManagedBeanUtil.popularSIDias(this.listaSIDias);
				ManagedBeanUtil.popularSIMeses(this.listaSIMeses);
				ManagedBeanUtil.popularSIAnos(this.listaSIAnos);
// TODO: C�digo comentado em 21/08/2017. Apagar em 180 dias
				// O Usu�rio contador deve fazer parte do grupo escritorio de contabilidade. Num futuro dever� ser criados subgruos para os contadores.
			}if(this.validaTiposUsuariosGruposUsuarios(usuario.getListaGrupoUsuario(), GrupoUsuarioTipoUsuarioEnum.ESCRITORIO_CONTABILIDADE.getTipoUsuario())){			
			//}if(usuario.getIdGrupoUsuario().equals(TipoGrupoUsuarioEnum.ESCRITORIO_CONTABILIDADE.getGrupoUsuario())){	
				this.contador = this.contadorService.buscarPorId(usuario.getId());
				this.setTabSelecionada(ID_TAB_DADOS_PESSOAIS_CONTADOR);
				ManagedBeanUtil.popularSIDias(this.listaSIDias);
				ManagedBeanUtil.popularSIMeses(this.listaSIMeses);
				ManagedBeanUtil.popularSIAnos(this.listaSIAnos);
			}
		} catch (SQLException e) {
			logger.error("erro sqlstate "+e.getSQLState(), e);	
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");				
		} catch (Exception e) {
			logger.error("", e);
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		}			
	}
	
	
	
	public String atualizarDadosPessoais(){
		this.setTabSelecionada(ID_TAB_DADOS_PESSOAIS);
		try {
			if(this.validaDadosPessoais()){
				this.condomino.setTelefoneCelular(this.telefoneCelularCondomino == "" ? null : Long.valueOf(this.telefoneCelularCondomino));
				this.condomino.setTelefoneComercial(this.telefoneComercialCondomino == "" ? null : Long.valueOf(this.telefoneComercialCondomino));
				this.condomino.setTelefoneResidencial(this.telefoneResidencialCondomino == "" ? null : Long.valueOf(this.telefoneResidencialCondomino));
				this.condominoService.atualizar(this.condomino);
				ManagedBeanUtil.setMensagemInfo("msg.meuPainel.condomino.atualizadoSucesso");				
			}
		} catch (SQLException e) {
			logger.error("erro sqlstate "+e.getSQLState(), e);	
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		} catch (Exception e) {
			logger.error("", e);
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		}		
		return null;
	}
	
	public String atualizarDadosPessoaisSindicoProfissional(){
		this.setTabSelecionada(ID_TAB_DADOS_PESSOAIS_SINDICO_PROFISSIONAL);
		try {
			if(this.validaDadosPessoaisSindicoProfissional()){
				this.sindicoProfissional.setTelefoneComercial(this.telefoneComercialSindicoProfissional == "" ? null : Long.valueOf(this.telefoneComercialSindicoProfissional));
				this.sindicoProfissional.setTelefoneCelular1(this.telefoneCelular1SindicoProfissional == "" ? null : Long.valueOf(this.telefoneCelular1SindicoProfissional));
				this.sindicoProfissional.setTelefoneCelular2(this.telefoneCelular2SindicoProfissional == "" ? null : Long.valueOf(this.telefoneCelular2SindicoProfissional));
				this.sindicoProfissional.setTelefoneCelular3(this.telefoneCelular3SindicoProfissional == "" ? null : Long.valueOf(this.telefoneCelular3SindicoProfissional));
				this.sindicoProfissionalService.atualizar(sindicoProfissional);
				ManagedBeanUtil.setMensagemInfo("msg.meuPainel.sindicoProfissional.atualizadoSucesso");				
			}
		} catch (SQLException e) {
			logger.error("erro sqlstate "+e.getSQLState(), e);	
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		} catch (Exception e) {
			logger.error("", e);
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		}		
		return null;
	}
	
	public String atualizarDadosPessoaisContador(){
		this.setTabSelecionada(ID_TAB_DADOS_PESSOAIS_CONTADOR);
		try {
			if(this.validaDadosContador()){
				this.contadorService.atualizar(this.contador);
				ManagedBeanUtil.setMensagemInfo("msg.meuPainel.contador.atualizadoSucesso");				
			}
		} catch (SQLException e) {
			logger.error("erro sqlstate "+e.getSQLState(), e);	
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		} catch (Exception e) {
			logger.error("", e);
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		}		
		return null;
	}
	
	public String atualizarDadosPessoaisFuncionario(){	
		this.setTabSelecionada(ID_TAB_DADOS_PESSOAIS_FUNCIONARIO);		
		try {
			if(this.validaDadosPessoaisFuncionario()){
				if (!this.telefoneCelularFuncionario.trim().equals("")){
					this.funcionario.setTelefoneCelular(Long.valueOf(this.telefoneCelularFuncionario));
				}
				if (!this.telefoneResidencialFuncionario.trim().equals("")){
					this.funcionario.setTelefoneResidencial(Long.valueOf(this.telefoneResidencialFuncionario));
				}				
				this.funcionarioService.atualizar(this.funcionario);
				ManagedBeanUtil.setMensagemInfo("msg.meuPainel.funcionario.atualizadoSucesso");				
			}
		} catch (SQLException e) {
			logger.error("erro sqlstate "+e.getSQLState(), e);	
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		} catch (Exception e) {
			logger.error("", e);
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		}		
		return null;
	}

	public String atualizarSenha(){		
		try {			
			this.setTabSelecionada(ID_TAB_ALTERAR_SENHA);						
			if(this.validaAtualizarSenha()){				
				this.condomino.setSenha(this.senhaCondomino);				
				this.usuarioService.atualizarSenha(this.condomino);
				ManagedBeanUtil.setMensagemInfo("msg.meuPainel.alterarSenha.novaSenhaAtualizada");				
			}
		} catch (SQLException e) {
			logger.error("erro sqlstate "+e.getSQLState(), e);	
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		} catch (Exception e) {
			logger.error("", e);
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		}		
		return null;
	}
	
	public String atualizarSenhaContador(){		
		try {			
			this.setTabSelecionada(ID_TAB_ALTERAR_SENHA_CONTADOR);						
			if(this.validaAtualizarSenhaContador()){								
				this.contador.setSenha(this.senhaContador);				
				this.usuarioService.atualizarSenha(this.contador);
				ManagedBeanUtil.setMensagemInfo("msg.meuPainel.alterarSenha.novaSenhaAtualizada");				
			}
		} catch (SQLException e) {
			logger.error("erro sqlstate "+e.getSQLState(), e);	
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		} catch (Exception e) {
			logger.error("", e);
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		}		
		return null;
	}
	
	public String atualizarSenhaFuncionario(){	
		this.setTabSelecionada(ID_TAB_ALTERAR_SENHA_FUNCIONARIO);
		try {			
			if(this.validaAtualizarSenhaFuncionario()){
				this.funcionario.setSenha(this.senhaFuncionario);				
				this.funcionarioService.atualizarSenha(this.funcionario);
				ManagedBeanUtil.setMensagemInfo("msg.meuPainel.alterarSenhaFuncionario.novaSenhaAtualizada");				
			}
		} catch (SQLException e) {
			logger.error("erro sqlstate "+e.getSQLState(), e);	
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		} catch (Exception e) {
			logger.error("", e);
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		}		
		return null;
	}
	
	public String atualizarImagemCondomino(){	
		try {			
			this.setTabSelecionada(ID_TAB_ALTERAR_IMAGEM_CONDOMINO);
			if(this.validaImagemCondomino()){
				if(this.condomino.getImagem().getId() != -1){					
					this.arquivoService.atualizarArquivoCondomino(this.condomino.getImagem());				
				}else{
					this.arquivoService.salvarArquivoCondomino(this.condomino.getImagem());	
				}
				ManagedBeanUtil.setMensagemInfo("msg.meuPainel.alterarImagemCondomino.imagemAtualizadaSucesso");
			}			
		} catch (SQLException e) {
			logger.error("erro sqlstate "+e.getSQLState(), e);	
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		} catch (Exception e) {
			logger.error("", e);
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		}		
		return null;
	}
	
	public String atualizarImagemSindicoProfissional(){	
		try {			
			this.setTabSelecionada(ID_TAB_ALTERAR_IMAGEM_SINDICO_PROFISSIONAL);
			if(this.validaImagemSindicoProfissional()){
				if(this.sindicoProfissional.getImagem().getId() != -1){					
					this.arquivoService.atualizarArquivoCondomino(this.sindicoProfissional.getImagem());				
				}else{
					this.arquivoService.salvarArquivoCondomino(this.sindicoProfissional.getImagem());	
				}
				ManagedBeanUtil.setMensagemInfo("msg.meuPainel.alterarImagemSindicoProfissional.imagemAtualizadaSucesso");
			}			
		} catch (SQLException e) {
			logger.error("erro sqlstate "+e.getSQLState(), e);	
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		} catch (Exception e) {
			logger.error("", e);
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		}		
		return null;
	}
	
	
	public String atualizarSenhaSindicoProfissional(){	
		this.setTabSelecionada(ID_TAB_ALTERAR_SENHA_SINDICO_PROFISSIONAL);
		try {			
			if(this.validaAtualizarSenhaSindicoProfissional()){
				this.sindicoProfissional.setSenha(this.senhaSindicoProfissional);
				this.sindicoProfissionalService.atualizarSenha(this.sindicoProfissional);
				ManagedBeanUtil.setMensagemInfo("msg.meuPainel.alterarSenhaSindicoProfissional.novaSenhaAtualizada");				
			}
		} catch (SQLException e) {
			logger.error("erro sqlstate "+e.getSQLState(), e);	
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		} catch (Exception e) {
			logger.error("", e);
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		}		
		return null;
	}
	
	public String atualizarImagemFuncionario(){	
		try {			
			this.setTabSelecionada(ID_TAB_ALTERAR_IMAGEM_FUNCIONARIO);
			if(this.validaImagemFuncionario()){
				// Condi��o que verifica se o funcionario est� associado a um condom�nio ou a um conjunto de bloco.
				if(this.funcionario.getIdCondominio() != null){
					if(this.funcionario.getImagem().getId() != -1){
						this.arquivoService.atualizarArquivoFuncionarioCondominio(this.funcionario.getImagem());						
					}else{
						this.arquivoService.salvarArquivoFuncionarioCondominio(this.funcionario.getImagem());		
					}
				}else{
					if(this.funcionario.getImagem().getId() != -1){
						this.arquivoService.atualizarArquivoFuncionarioConjuntoBloco(this.funcionario.getImagem());
					}else{
						this.arquivoService.salvarArquivoFuncionarioConjuntoBloco(this.funcionario.getImagem());		
					}
				}
				ManagedBeanUtil.setMensagemInfo("msg.meuPainel.alterarImagemFuncionario.imagemAtualizadaSucesso");
			}			
		} catch (SQLException e) {
			logger.error("erro sqlstate "+e.getSQLState(), e);	
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		} catch (Exception e) {
			logger.error("", e);
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		}			
		return null;
	}	
	
	public void salvarNovoCondomino(ActionEvent event){
		try {			
			this.setTabSelecionada(ID_TAB_NOVO_CONDOMINO);						
			if(this.validaNovoCondomino()){
				this.novoCondomino.setIdUnidade(this.condomino.getIdUnidade());
				this.novoCondomino.setTelefoneCelular(Long.parseLong(this.telefoneCelularNovoCondomino));
				this.novoCondomino.setTelefoneComercial(Long.parseLong(this.telefoneComercialNovoCondomino));
				this.novoCondomino.setTelefoneResidencial(Long.parseLong(this.telefoneResidencialNovoCondomino));				
				//this.novoCondomino.setIdGrupoUsuario(TipoGrupoUsuarioEnum.CONDOMINO.getGrupoUsuario());
				this.novoCondomino.getEmail().setPrincipal(Boolean.TRUE);				
				this.novoCondomino.setSituacao(UsuarioSituacaoEnum.ATIVO.getSituacao());
				this.popularGrupoUsuarioCondomino();
				Unidade unidade = this.unidadeService.buscarPorId(this.novoCondomino.getIdUnidade());
				Bloco bloco = this.blocoService.buscarPorId(unidade.getIdBloco());				
				Condominio condominio = this.condominioService.buscarPorId(bloco.getIdCondominio());
				List<Condominio> listaCondominio = new ArrayList<Condominio>();
				listaCondominio.add(condominio);
				this.novoCondomino.setListaCondominio(listaCondominio);		
				this.condominoService.salvar(this.novoCondomino);
				ManagedBeanUtil.setMensagemInfo("msg.meuPainel.novoCondomino.salvoSucesso");				
			}
		} catch (SQLException e) {
			logger.error("erro sqlstate "+e.getSQLState(), e);	
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		} catch (Exception e) {
			logger.error("", e);
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		}		
	}
	
	public void salvarGaragem(ActionEvent event){
		try {			
			this.setTabSelecionada(ID_TAB_NOVA_GARAGEM);	
			if(validaDadosNovaGaragem()){
				this.garagem.setTamanho(GaragemEnum.MEDIO.getTamanho());		
				this.garagem.setIdUnidade(this.condomino.getIdUnidade());			
				this.garagemService.salvar(this.garagem);
				this.popularDMGaragem();
				this.garagem = new Garagem();
				this.popularSIGaragem();
				ManagedBeanUtil.setMensagemInfo("msg.garagem.salvaSucesso");				
			}
		} catch (SQLException e) {
			logger.error("erro sqlstate "+e.getSQLState(), e);	
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		} catch (Exception e) {
			logger.error("", e);
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		}		
	}
	
	public void salvarVeiculo(ActionEvent event){
		try {			
			this.setTabSelecionada(ID_TAB_NOVO_VEICULO);	
			if(validaDadosNovoVeiculo()){
				this.veiculo.setTamanho(GaragemEnum.MEDIO.getTamanho());
				this.veiculo.setCondomino(this.condomino);
				this.veiculoService.salvar(this.veiculo);
				this.veiculo = new Veiculo();
				this.popularDMVeiculo();
				ManagedBeanUtil.setMensagemInfo("msg.meuPainel.veiculo.salvoSucesso");				
			}		 
		} catch (SQLException e) {
			logger.error("erro sqlstate "+e.getSQLState(), e);	
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		} catch (Exception e) {
			logger.error("", e);
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		}		
	}
	
	public void editarGaragem(ActionEvent event){
		this.setTabSelecionada(ID_TAB_NOVA_GARAGEM);	
		this.garagem = this.listaDMGaragem.getRowData();
		this.renderedCadastroNovaGaragem = Boolean.FALSE;		
	}
	
	public void editarVeiculo(ActionEvent event){
		this.setTabSelecionada(ID_TAB_NOVO_VEICULO);	
		this.veiculo = this.listaDMVeiculo.getRowData();
		this.renderedCadastroNovoVeiculo = Boolean.FALSE;		
	}

	
	public void atualizarGaragem(ActionEvent event){
		try {
			this.setTabSelecionada(ID_TAB_NOVA_GARAGEM);	
			if(validaDadosNovaGaragem()){
				this.garagem.setTamanho(GaragemEnum.MEDIO.getTamanho());		
				this.garagem.setIdUnidade(this.condomino.getIdUnidade());			
				this.garagemService.atualizar(this.garagem);
				this.popularDMGaragem();
				this.renderedCadastroNovaGaragem = Boolean.TRUE;	
				this.garagem = new Garagem();
				this.popularSIGaragem();
				ManagedBeanUtil.setMensagemInfo("msg.garagem.atualizadaSucesso");				
			}
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
	
	public void atualizarVeiculo(ActionEvent event){
		try {
			this.setTabSelecionada(ID_TAB_NOVO_VEICULO);	
			if(validaDadosNovoVeiculo()){
				this.veiculo.setTamanho(GaragemEnum.MEDIO.getTamanho());
				this.veiculo.setCondomino(this.condomino);
				this.veiculoService.atualizar(this.veiculo);
				this.popularDMVeiculo();
				this.renderedCadastroNovoVeiculo = Boolean.TRUE;	
				this.veiculo = new Veiculo();				
				ManagedBeanUtil.setMensagemInfo("msg.meuPainel.veiculo.atualizadoSucesso");				
			}
		} catch (SQLException e) {
			logger.error("erro sqlstate "+e.getSQLState(), e);	
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		} catch (Exception e) {
			logger.error("", e);
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		}		
	}
	
	public String excluirGaragem(){
		this.setTabSelecionada(ID_TAB_NOVA_GARAGEM);
		try {
			this.garagemService.excluir(this.garagem);
			this.popularDMGaragem();
			this.popularSIGaragem();
			this.renderedCadastroNovaGaragem = Boolean.TRUE;	
			this.garagem = new Garagem();
			ManagedBeanUtil.setMensagemInfo("msg.garagem.excluidaSucesso");
		} catch (SQLException e) {
			logger.error("erro sqlstate "+e.getSQLState(), e);	
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		} catch (BusinessException e) {				
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage());	
		} catch (Exception e) {
			logger.error("", e);
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		}		
		return null;
	}
	
	public void cancelarEditarGaragem(ActionEvent event){
		this.setTabSelecionada(ID_TAB_NOVA_GARAGEM);
		this.garagem = new Garagem();
		this.renderedCadastroNovaGaragem = Boolean.TRUE;		
	}
	
	public void cancelarEditarVeiculo(ActionEvent event){
		this.setTabSelecionada(ID_TAB_NOVO_VEICULO);
		this.veiculo = new Veiculo();
		this.renderedCadastroNovoVeiculo = Boolean.TRUE;		
	}
	
	public String excluirVeiculo(){
		this.setTabSelecionada(ID_TAB_NOVO_VEICULO);
		try {
			this.veiculoService.excluir(this.veiculo);
			this.popularDMVeiculo();
			this.renderedCadastroNovoVeiculo = Boolean.TRUE;	
			this.veiculo = new Veiculo();
			ManagedBeanUtil.setMensagemInfo("msg.meuPainel.veiculo.excluirSucesso");
		} catch (SQLException e) {
			logger.error("erro sqlstate "+e.getSQLState(), e);	
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		} catch (Exception e) {
			logger.error("", e);
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		}		
		return null;
	}
	
	
	 public void paintCondomino(OutputStream stream, Object object)  {
	    try {
	    	this.condomino.setImagem(this.arquivos.get(0));
			stream.write(this.arquivos.get(0).getDadosArquivo());
			stream.close();		
		} catch (IOException e) {
			logger.error("", e);
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		} catch (Exception e) {
			logger.error("", e);
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		}		
	}
	 
	public void paintFuncionario(OutputStream stream, Object object)   {
		try {
			this.funcionario.setImagem(this.arquivos.get(0));
			stream.write(this.arquivos.get(0).getDadosArquivo());
			stream.close();
		} catch (IOException e) {
			logger.error("", e);
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		} catch (Exception e) {
			logger.error("", e);
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		}
	}
	
	public void listenerSindicoProfissional(FileUploadEvent event)  {
		 UploadedFile item = event.getUploadedFile();
		 Arquivo arquivo =  new Arquivo();		 
		 arquivo.setId(this.sindicoProfissional.getImagem() != null ? this.sindicoProfissional.getImagem().getId() : null);
		 arquivo.setIdUsuario(this.sindicoProfissional.getId());
		 arquivo.setDadosArquivo(item.getData());
		 arquivo.setNome(event.getUploadedFile().getName());
		 arquivo.setMimeType(event.getUploadedFile().getContentType());
		 this.arquivos.clear();
	     this.arquivos.add(arquivo);	
	}
	
	public void paintSindicoProfissional(OutputStream stream, Object object)   {
		try {
			this.sindicoProfissional.setImagem(this.arquivos.get(0));
			stream.write(this.arquivos.get(0).getDadosArquivo());
			stream.close();
		} catch (IOException e) {
			logger.error("", e);
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		} catch (Exception e) {
			logger.error("", e);
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		}
	}
	
	public void listenerFuncionario(FileUploadEvent event)  {
		 UploadedFile item = event.getUploadedFile();
		 Arquivo arquivo =  new Arquivo();		 
		 arquivo.setId(this.funcionario.getImagem() != null ? this.funcionario.getImagem().getId() : null);
		 arquivo.setIdUsuario(this.funcionario.getId());
		 arquivo.setDadosArquivo(item.getData());
		 arquivo.setNome(event.getUploadedFile().getName());
		 arquivo.setMimeType(event.getUploadedFile().getContentType());
		 this.arquivos.clear();
	     this.arquivos.add(arquivo);	
	} 
	 
	public void listenerCondomino(FileUploadEvent event) {
		 UploadedFile item = event.getUploadedFile();
		 Arquivo arquivo =  new Arquivo();		 
		 arquivo.setId(this.condomino.getImagem() != null ? this.condomino.getImagem().getId() : null);
		 arquivo.setIdUsuario(this.condomino.getId());
		 arquivo.setDadosArquivo(item.getData());
		 arquivo.setNome(event.getUploadedFile().getName());
		 arquivo.setMimeType(event.getUploadedFile().getContentType());
		 this.arquivos.clear();
	     this.arquivos.add(arquivo);	
	} 
		
	public List<SelectItem> getListaSexo(){
		List<SelectItem> listaSexo = new ArrayList<SelectItem>();
		listaSexo.add(new SelectItem(0, AplicacaoUtil.i18n("condomino.sexo.0")));
		listaSexo.add(new SelectItem(1, AplicacaoUtil.i18n("condomino.sexo.1")));
		return listaSexo;
	}
	
	public List<SelectItem> getListaTipoVeiculo(){
		List<SelectItem> listaSituacoes = new ArrayList<SelectItem>();
		listaSituacoes.add(new SelectItem(VeiculoTipoEnum.CARRO.getTipo(), AplicacaoUtil.i18n("meuPainel.veiculo.tipo.1")));
		listaSituacoes.add(new SelectItem(VeiculoTipoEnum.MOTO.getTipo(), AplicacaoUtil.i18n("meuPainel.veiculo.tipo.2")));
		return listaSituacoes;
	}
	
	 
	 public long getTimeStamp() {
	     return System.currentTimeMillis();
	 }
	 
	 public int getSize() {
        if (getArquivos().size() > 0) {
            return getArquivos().size();
        } else {
            return 0;
        }
	 }
	 
	 
	 private void popularGrupoUsuarioCondomino() throws SQLException, Exception{
			List<GrupoUsuario> listaGrupoUsuario = new ArrayList<GrupoUsuario>();
			listaGrupoUsuario.addAll(this.grupoUsuarioService.buscarPorIdCondominioEPadraoETipoUsuarioESituacao(this.novoCondomino.getId(),
					GrupoUsuarioPadraoEnum.SIM.getPadrao(), GrupoUsuarioTipoUsuarioEnum.CONDOMINO.getTipoUsuario(),GrupoUsuarioSituacaoEnum.ATIVO.getSituacao()));
			if(listaGrupoUsuario.isEmpty()){
				listaGrupoUsuario.add(this.grupoUsuarioService.buscarPorPadraoETipoUsuarioESituacao(GrupoUsuarioPadraoEnum.SIM.getPadrao(), GrupoUsuarioTipoUsuarioEnum.CONDOMINO.getTipoUsuario(),GrupoUsuarioSituacaoEnum.ATIVO.getSituacao()));			
			}
			this.novoCondomino.setListaGrupoUsuario(listaGrupoUsuario);
				
		}
	 
	public UsuarioService getUsuarioService() {
		return usuarioService;
	}

	public void setUsuarioService(UsuarioService usuarioService) {
		this.usuarioService = usuarioService;
	}

	public CondominoService getCondominoService() {
		return condominoService;
	}

	public void setCondominoService(CondominoService condominoService) {
		this.condominoService = condominoService;
	}

	public CondominioService getCondominioService() {
		return condominioService;
	}

	public void setCondominioService(CondominioService condominioService) {
		this.condominioService = condominioService;
	}

	public BlocoService getBlocoService() {
		return blocoService;
	}

	public void setBlocoService(BlocoService blocoService) {
		this.blocoService = blocoService;
	}

	public UnidadeService getUnidadeService() {
		return unidadeService;
	}

	public void setUnidadeService(UnidadeService unidadeService) {
		this.unidadeService = unidadeService;
	}

	public FuncionarioService getFuncionarioService() {
		return funcionarioService;
	}

	public void setFuncionarioService(FuncionarioService funcionarioService) {
		this.funcionarioService = funcionarioService;
	}

	public ArquivoService getArquivoService() {
		return arquivoService;
	}

	public void setArquivoService(ArquivoService arquivoService) {
		this.arquivoService = arquivoService;
	}

	public GaragemService getGaragemService() {
		return garagemService;
	}

	public void setGaragemService(GaragemService garagemService) {
		this.garagemService = garagemService;
	}

	public VeiculoService getVeiculoService() {
		return veiculoService;
	}

	public void setVeiculoService(VeiculoService veiculoService) {
		this.veiculoService = veiculoService;
	}

	public List<Garagem> getListaGaragem() {
		return listaGaragem;
	}

	public void setListaGaragem(List<Garagem> listaGaragem) {
		this.listaGaragem = listaGaragem;
	}

	public Condomino getCondomino() {
		return condomino;
	}

	public void setCondomino(Condomino condomino) {
		this.condomino = condomino;
	}

	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}	

	public String getSenhaAtualCondomino() {
		return senhaAtualCondomino;
	}

	public void setSenhaAtualCondomino(String senhaAtualCondomino) {
		this.senhaAtualCondomino = senhaAtualCondomino;
	}

	public Condomino getNovoCondomino() {
		return novoCondomino;
	}

	public void setNovoCondomino(Condomino novoCondomino) {
		this.novoCondomino = novoCondomino;
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

	public String getTabSelecionada() {		
		return tabSelecionada;
	}

	public void setTabSelecionada(String tabSelecionada) {		
		this.tabSelecionada = tabSelecionada;
	}	

	public String getSenhaCondomino() {
		return senhaCondomino;
	}

	public void setSenhaCondomino(String senhaCondomino) {
		this.senhaCondomino = senhaCondomino;
	}

	public String getConfirmarSenhaCondomino() {
		return confirmarSenhaCondomino;
	}

	public void setConfirmarSenhaCondomino(String confirmarSenhaCondomino) {
		this.confirmarSenhaCondomino = confirmarSenhaCondomino;
	}	

	public String getTelefoneResidencialNovoCondomino() {
		return telefoneResidencialNovoCondomino;
	}

	public void setTelefoneResidencialNovoCondomino(
			String telefoneResidencialNovoCondomino) {
		this.telefoneResidencialNovoCondomino = telefoneResidencialNovoCondomino;
	}

	public String getTelefoneCelularNovoCondomino() {
		return telefoneCelularNovoCondomino;
	}

	public void setTelefoneCelularNovoCondomino(String telefoneCelularNovoCondomino) {
		this.telefoneCelularNovoCondomino = telefoneCelularNovoCondomino;
	}

	public String getTelefoneComercialNovoCondomino() {
		return telefoneComercialNovoCondomino;
	}

	public void setTelefoneComercialNovoCondomino(
			String telefoneComercialNovoCondomino) {
		this.telefoneComercialNovoCondomino = telefoneComercialNovoCondomino;
	}	

	public String getTelefoneResidencialCondomino() {
		return telefoneResidencialCondomino;
	}

	public void setTelefoneResidencialCondomino(String telefoneResidencialCondomino) {
		this.telefoneResidencialCondomino = telefoneResidencialCondomino;
	}

	public String getTelefoneCelularCondomino() {
		return telefoneCelularCondomino;
	}

	public void setTelefoneCelularCondomino(String telefoneCelularCondomino) {
		this.telefoneCelularCondomino = telefoneCelularCondomino;
	}

	public String getTelefoneComercialCondomino() {
		return telefoneComercialCondomino;
	}

	public void setTelefoneComercialCondomino(String telefoneComercialCondomino) {
		this.telefoneComercialCondomino = telefoneComercialCondomino;
	}	

	public String getTabSelecionadaFuncionario() {
		return tabSelecionadaFuncionario;
	}

	public void setTabSelecionadaFuncionario(String tabSelecionadaFuncionario) {
		this.tabSelecionadaFuncionario = tabSelecionadaFuncionario;
	}	

	public String getTelefoneCelularFuncionario() {
		return telefoneCelularFuncionario;
	}

	public void setTelefoneCelularFuncionario(String telefoneCelularFuncionario) {
		this.telefoneCelularFuncionario = telefoneCelularFuncionario;
	}

	public String getTelefoneResidencialFuncionario() {
		return telefoneResidencialFuncionario;
	}

	public void setTelefoneResidencialFuncionario(
			String telefoneResidencialFuncionario) {
		this.telefoneResidencialFuncionario = telefoneResidencialFuncionario;
	}	

	public String getSenhaAtualFuncionario() {
		return senhaAtualFuncionario;
	}

	public void setSenhaAtualFuncionario(String senhaAtualFuncionario) {
		this.senhaAtualFuncionario = senhaAtualFuncionario;
	}

	public String getSenhaFuncionario() {
		return senhaFuncionario;
	}

	public void setSenhaFuncionario(String senhaFuncionario) {
		this.senhaFuncionario = senhaFuncionario;
	}

	public String getConfirmarSenhaFuncionario() {
		return confirmarSenhaFuncionario;
	}

	public void setConfirmarSenhaFuncionario(String confirmarSenhaFuncionario) {
		this.confirmarSenhaFuncionario = confirmarSenhaFuncionario;
	}
	
	public ArrayList<Arquivo> getArquivos() {		
		 return arquivos;
	 }
	
	public void setArquivos(ArrayList<Arquivo> arquivos) {
		 this.arquivos = arquivos;
	}	
	
	public Garagem getGaragem() {
		return garagem;
	}

	public void setGaragem(Garagem garagem) {
		this.garagem = garagem;
	}	

	public ListDataModel<Garagem> getListaDMGaragem() {
		return listaDMGaragem;
	}

	public void setListaDMGaragem(ListDataModel<Garagem> listaDMGaragem) {
		this.listaDMGaragem = listaDMGaragem;
	}
	
	public Boolean getRenderedCadastroNovaGaragem(){
		return this.renderedCadastroNovaGaragem;
	}
	
	public void setRenderedCadastroNovaGaragem(Boolean renderedCadastroNovaGaragem) {
		this.renderedCadastroNovaGaragem = renderedCadastroNovaGaragem;
	}

	public Veiculo getVeiculo() {
		return veiculo;
	}

	public void setVeiculo(Veiculo veiculo) {
		this.veiculo = veiculo;
	}	

	public List<SelectItem> getListaSIGaragem() {
		return listaSIGaragem;
	}

	public void setListaSIGaragem(List<SelectItem> listaSIGaragem) {
		this.listaSIGaragem = listaSIGaragem;
	}	

	public ListDataModel<Veiculo> getListaDMVeiculo() {
		return listaDMVeiculo;
	}

	public void setListaDMVeiculo(ListDataModel<Veiculo> listaDMVeiculo) {
		this.listaDMVeiculo = listaDMVeiculo;
	}

	public Boolean getRenderedCadastroNovoVeiculo() {
		return renderedCadastroNovoVeiculo;
	}

	public void setRenderedCadastroNovoVeiculo(Boolean renderedCadastroNovoVeiculo) {
		this.renderedCadastroNovoVeiculo = renderedCadastroNovoVeiculo;
	}	
	
	public String getTelefoneComercialSindicoProfissional() {
		return telefoneComercialSindicoProfissional;
	}

	public void setTelefoneComercialSindicoProfissional(String telefoneComercialSindicoProfissional) {
		this.telefoneComercialSindicoProfissional = telefoneComercialSindicoProfissional;
	}

	public String getTelefoneCelular1SindicoProfissional() {
		return telefoneCelular1SindicoProfissional;
	}

	public void setTelefoneCelular1SindicoProfissional(String telefoneCelular1SindicoProfissional) {
		this.telefoneCelular1SindicoProfissional = telefoneCelular1SindicoProfissional;
	}

	public String getTelefoneCelular2SindicoProfissional() {
		return telefoneCelular2SindicoProfissional;
	}

	public void setTelefoneCelular2SindicoProfissional(String telefoneCelular2SindicoProfissional) {
		this.telefoneCelular2SindicoProfissional = telefoneCelular2SindicoProfissional;
	}

	public String getTelefoneCelular3SindicoProfissional() {
		return telefoneCelular3SindicoProfissional;
	}

	public void setTelefoneCelular3SindicoProfissional(String telefoneCelular3SindicoProfissional) {
		this.telefoneCelular3SindicoProfissional = telefoneCelular3SindicoProfissional;
	}	

	public SindicoProfissional getSindicoProfissional() {
		return sindicoProfissional;
	}

	public void setSindicoProfissional(SindicoProfissional sindicoProfissional) {
		this.sindicoProfissional = sindicoProfissional;
	}	

	public String getSenhaAtualSindicoProfissional() {
		return senhaAtualSindicoProfissional;
	}

	public void setSenhaAtualSindicoProfissional(String senhaAtualSindicoProfissional) {
		this.senhaAtualSindicoProfissional = senhaAtualSindicoProfissional;
	}

	public String getSenhaSindicoProfissional() {
		return senhaSindicoProfissional;
	}

	public void setSenhaSindicoProfissional(String senhaSindicoProfissional) {
		this.senhaSindicoProfissional = senhaSindicoProfissional;
	}

	public String getConfirmarSenhaSindicoProfissional() {
		return confirmarSenhaSindicoProfissional;
	}

	public void setConfirmarSenhaSindicoProfissional(String confirmarSenhaSindicoProfissional) {
		this.confirmarSenhaSindicoProfissional = confirmarSenhaSindicoProfissional;
	}	
	
	public Contador getContador() {
		return contador;
	}

	public void setContador(Contador contador) {
		this.contador = contador;
	}	

	public String getSenhaAtualContador() {
		return senhaAtualContador;
	}

	public void setSenhaAtualContador(String senhaAtualContador) {
		this.senhaAtualContador = senhaAtualContador;
	}

	public String getSenhaContador() {
		return senhaContador;
	}

	public void setSenhaContador(String senhaContador) {
		this.senhaContador = senhaContador;
	}	

	public String getConfirmarSenhaContador() {
		return confirmarSenhaContador;
	}

	public void setConfirmarSenhaContador(String confirmarSenhaContador) {
		this.confirmarSenhaContador = confirmarSenhaContador;
	}

	public void carregarGaragens(){
		try {
			Usuario usuario = AplicacaoUtil.getUsuarioAutenticado();
			if(this.validaTiposUsuariosGruposUsuarios(usuario.getListaGrupoUsuario(), GrupoUsuarioTipoUsuarioEnum.CONDOMINO.getTipoUsuario(),
					GrupoUsuarioTipoUsuarioEnum.SINDICO.getTipoUsuario())){
				this.popularDMGaragem();
				this.popularSIGaragem();
			}
		} catch (SQLException e) {
			logger.error("erro sqlstate "+e.getSQLState(), e);	
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");				
		} catch (Exception e) {
			logger.error("", e);
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		}			
	}
	
	public void popularSIGaragem(){
		this.listaSIGaragem = new ArrayList<SelectItem>();
		this.listaSIGaragem.add(new SelectItem(null, AplicacaoUtil.i18n("meuPainel.veiculo.semGaragem")));
		for (Garagem garagem : this.listaGaragem) {
			this.listaSIGaragem.add(new SelectItem(garagem.getId(), garagem.getNumero()));
		}		
	}
	
	private void popularDMGaragem() throws SQLException, Exception{
		this.listaGaragem = this.garagemService.buscarPorIdUnidade(this.condomino.getIdUnidade());
		this.listaDMGaragem = new ListDataModel<Garagem>(this.listaGaragem);	
	}
	
	private void popularDMVeiculo() throws NumberFormatException, Exception{
		this.listaDMVeiculo = new ListDataModel<Veiculo>(this.veiculoService.buscarPorIdCondomino(this.condomino.getId()));		
	}
	
	
	private Boolean validaDadosPessoais() {
		Integer quantidadeErros = 0; 
		if(this.condomino.getNome().trim().equals("")){
			ManagedBeanUtil.setMensagemErro("msg.meuPainel.condomino.nomeRequerido");
			quantidadeErros++;			
		}
		if(this.condomino.getNome().length() > 150){
			ManagedBeanUtil.setMensagemErro("msg.meuPainel.condomino.nome");
			quantidadeErros++;			
		}
		// Regra de valica��o tamanho de campos
		if(this.condomino.getEmail().getEmail().equals("")){
			ManagedBeanUtil.setMensagemErro("msg.meuPainel.condomino.emailRequerido");
			quantidadeErros++;		
		}		
		// Regra de valica��o tamanho de campos
		if(this.condomino.getEmail().getEmail().length() > 150){
			ManagedBeanUtil.setMensagemErro("msg.meuPainel.condomino.email");
			quantidadeErros++;		
		}
		// Regra formato de email
		if (!ManagedBeanUtil.validaEmail(this.condomino.getEmail().getEmail())){
			ManagedBeanUtil.setMensagemErro("msg.meuPainel.condomino.formatoEmailInvalido");
			quantidadeErros++;
		}
		// Regra somente n�meros
		if(AplicacaoUtil.validaExpressaoRegular("[^0-9]+", this.telefoneCelularCondomino)){
			ManagedBeanUtil.setMensagemErro("msg.meuPainel.condomino.telefoneCelularSomenteNumero");
			quantidadeErros++;					
		}
		// Regra somente n�meros
		if(AplicacaoUtil.validaExpressaoRegular("[^0-9]+", this.telefoneComercialCondomino)){
			ManagedBeanUtil.setMensagemErro("msg.meuPainel.condomino.telefoneComercialSomenteNumero");
			quantidadeErros++;		
		}
		// Regra somente n�meros
		if(AplicacaoUtil.validaExpressaoRegular("[^0-9]+", this.telefoneResidencialCondomino)){
			ManagedBeanUtil.setMensagemErro("msg.meuPainel.condomino.telefoneResidencialSomenteNumero");
			quantidadeErros++;		
		}
		return quantidadeErros == 0 ? Boolean.TRUE : Boolean.FALSE;
	}
	
	private Boolean validaDadosPessoaisSindicoProfissional() {
		Integer quantidadeErros = 0; 
		if(this.sindicoProfissional.getNome().trim().equals("")){
			ManagedBeanUtil.setMensagemErro("msg.meuPainel.sindicoProfissional.nomeRequerido");
			quantidadeErros++;			
		}
		if(this.sindicoProfissional.getNome().length() > 150){
			ManagedBeanUtil.setMensagemErro("msg.meuPainel.sindicoProfissional.nome");
			quantidadeErros++;			
		}
		// Regra de valica��o tamanho de campos
		if(this.sindicoProfissional.getEmail().getEmail().equals("")){
			ManagedBeanUtil.setMensagemErro("msg.meuPainel.sindicoProfissional.emailRequerido");
			quantidadeErros++;		
		}		
		// Regra de valica��o tamanho de campos
		if(this.sindicoProfissional.getEmail().getEmail().length() > 150){
			ManagedBeanUtil.setMensagemErro("msg.meuPainel.sindicoProfissional.email");
			quantidadeErros++;		
		}
		// Regra formato de email
		if (!ManagedBeanUtil.validaEmail(this.sindicoProfissional.getEmail().getEmail())){
			ManagedBeanUtil.setMensagemErro("msg.meuPainel.sindicoProfissional.formatoEmailInvalido");
			quantidadeErros++;
		}
		// Regra somente n�meros
		if(AplicacaoUtil.validaExpressaoRegular("[^0-9]+", this.telefoneComercialSindicoProfissional)){
			ManagedBeanUtil.setMensagemErro("msg.meuPainel.sindicoProfissional.telefoneComercialSomenteNumero");
			quantidadeErros++;					
		}
		// Regra somente n�meros
		if(AplicacaoUtil.validaExpressaoRegular("[^0-9]+", this.telefoneCelular1SindicoProfissional)){
			ManagedBeanUtil.setMensagemErro("msg.meuPainel.sindicoProfissional.telefoneCelular1SomenteNumero");
			quantidadeErros++;		
		}
		// Regra somente n�meros
		if(AplicacaoUtil.validaExpressaoRegular("[^0-9]+", this.telefoneCelular2SindicoProfissional)){
			ManagedBeanUtil.setMensagemErro("msg.meuPainel.sindicoProfissional.telefoneCelular2SomenteNumero");
			quantidadeErros++;		
		}
		// Regra somente n�meros
		if(AplicacaoUtil.validaExpressaoRegular("[^0-9]+", this.telefoneCelular3SindicoProfissional)){
			ManagedBeanUtil.setMensagemErro("msg.meuPainel.sindicoProfissional.telefoneCelular3SomenteNumero");
			quantidadeErros++;		
		}
		return quantidadeErros == 0 ? Boolean.TRUE : Boolean.FALSE;
	}
	
	private Boolean validaDadosPessoaisFuncionario() {
		Integer quantidadeErros = 0; 
		if(this.funcionario.getNome().trim().equals("")){
			ManagedBeanUtil.setMensagemErro("msg.meuPainel.funcionario.nomeRequerido");
			quantidadeErros++;			
		}
		if(this.funcionario.getNome().length() > 150){
			ManagedBeanUtil.setMensagemErro("msg.meuPainel.funcionario.nome");
			quantidadeErros++;			
		}
		// Regra de valica��o tamanho de campos
		if(this.funcionario.getEmail().getEmail().equals("")){
			ManagedBeanUtil.setMensagemErro("msg.meuPainel.funcionario.emailRequerido");
			quantidadeErros++;		
		}		
		// Regra de valica��o tamanho de campos
		if(this.funcionario.getEmail().getEmail().length() > 150){
			ManagedBeanUtil.setMensagemErro("msg.meuPainel.funcionario.email");
			quantidadeErros++;		
		}
		// Regra formato de email
		if (!ManagedBeanUtil.validaEmail(this.funcionario.getEmail().getEmail())){
			ManagedBeanUtil.setMensagemErro("msg.meuPainel.funcionario.formatoEmailInvalido");
			quantidadeErros++;
		}
		// Regra somente n�meros
		if(AplicacaoUtil.validaExpressaoRegular("[^0-9]+", this.telefoneCelularFuncionario)){
			ManagedBeanUtil.setMensagemErro("msg.meuPainel.funcionario.telefoneCelularSomenteNumero");
			quantidadeErros++;					
		}
		// Regra somente n�meros
		if(AplicacaoUtil.validaExpressaoRegular("[^0-9]+", this.telefoneResidencialFuncionario)){
			ManagedBeanUtil.setMensagemErro("msg.meuPainel.funcionario.telefoneResidencialSomenteNumero");
			quantidadeErros++;		
		}
		return quantidadeErros == 0 ? Boolean.TRUE : Boolean.FALSE;
	}	
	
	private Boolean validaAtualizarSenhaFuncionario() throws NoSuchAlgorithmException {
		Integer quantidadeErros = 0;		
		// Regra campo obrigat�rio
		if(this.senhaAtualFuncionario.trim().equals("")){
			ManagedBeanUtil.setMensagemErro("msg.meuPainel.alterarSenhaFuncionario.senhaAtualRequerida");
			quantidadeErros++;
		}
		// Regra campo obrigat�rio
		if(this.senhaFuncionario.trim().equals("")){
			ManagedBeanUtil.setMensagemErro("msg.meuPainel.alterarSenhaFuncionario.novaSenhaRequerida");	
			quantidadeErros++;
		}
		// Regra campo senha atual deve conferir com a senha da base de dados
		if (!this.funcionario.getSenha().equals(AplicacaoUtil.gerarHashMD5(this.senhaAtualFuncionario))){
			ManagedBeanUtil.setMensagemErro("msg.meuPainel.alterarSenhaFuncionario.senhasAtualIncorreta");
			quantidadeErros++;
		}
		// Regra campo obrigat�rio
		if(this.confirmarSenhaFuncionario.trim().equals("")){
			ManagedBeanUtil.setMensagemErro("msg.meuPainel.alterarSenhaFuncionario.ConfirmarSenhaRequerida");
			quantidadeErros++;
		}
		// Regra para verificar se o usu�rio n�o digitou a nova senha errada e n�o percebeu
		if(!this.senhaFuncionario.equals(this.confirmarSenhaFuncionario)){
			ManagedBeanUtil.setMensagemErro("msg.meuPainel.alterarSenhaFuncionario.senhasNaoCorrespondem");
			quantidadeErros++;
		}
		// Regra valica��o tamanho de campos
		if((this.senhaFuncionario != null && this.senhaFuncionario.length() < 6) || (this.senhaCondomino !=null && this.senhaCondomino.length() > 15)){
			ManagedBeanUtil.setMensagemErro("msg.meuPainel.alterarSenhaFuncionario.novaSenha");
			quantidadeErros++;
		}
		// Regra somente letras e/ou n�meros e valica��o tamanho de campos
		if(this.confirmarSenhaFuncionario.length() < 6 || this.confirmarSenhaFuncionario.length() > 15){
			ManagedBeanUtil.setMensagemErro("msg.meuPainel.alterarSenhaFuncionario.confirmarSenha");
			quantidadeErros++;
		}
		return quantidadeErros == 0 ? Boolean.TRUE : Boolean.FALSE;
	}
	
	private Boolean validaAtualizarSenha() throws NoSuchAlgorithmException {
		Integer quantidadeErros = 0;		
		// Regra campo obrigat�rio
		if(this.senhaAtualCondomino.trim().equals("")){
			ManagedBeanUtil.setMensagemErro("msg.meuPainel.alterarSenha.senhaAtualRequerida");
			quantidadeErros++;
		}
		// Regra campo obrigat�rio
		if(this.senhaCondomino.trim().equals("")){
			ManagedBeanUtil.setMensagemErro("msg.meuPainel.alterarSenha.novaSenhaRequerida");	
			quantidadeErros++;
		}
		// Regra campo senha atual deve conferir com a senha da base de dados
		if (!this.condomino.getSenha().equals(AplicacaoUtil.gerarHashMD5(this.senhaAtualCondomino))){
			ManagedBeanUtil.setMensagemErro("msg.meuPainel.alterarSenha.senhasAtualIncorreta");
			quantidadeErros++;
		}
		// Regra campo obrigat�rio
		if(this.confirmarSenhaCondomino.trim().equals("")){
			ManagedBeanUtil.setMensagemErro("msg.meuPainel.alterarSenha.ConfirmarSenhaRequerida");
			quantidadeErros++;
		}
		// Regra para verificar se o usu�rio n�o digitou a nova senha errada e n�o percebeu
		if(!this.senhaCondomino.equals(this.confirmarSenhaCondomino)){
			ManagedBeanUtil.setMensagemErro("msg.meuPainel.alterarSenha.senhasNaoCorrespondem");
			quantidadeErros++;
		}
		// Regra valica��o tamanho de campos
		if(this.senhaCondomino.length() < 6 || this.senhaCondomino.length() > 15){
			ManagedBeanUtil.setMensagemErro("msg.meuPainel.alterarSenha.novaSenha");
			quantidadeErros++;
		}
		// Regra somente letras e/ou n�meros e valica��o tamanho de campos
		if(this.confirmarSenhaCondomino.length() < 6 || this.confirmarSenhaCondomino.length() > 15){
			ManagedBeanUtil.setMensagemErro("msg.meuPainel.alterarSenha.confirmarSenha");
			quantidadeErros++;
		}
		return quantidadeErros == 0 ? Boolean.TRUE : Boolean.FALSE;
	}
	
	private Boolean validaAtualizarSenhaSindicoProfissional() throws NoSuchAlgorithmException {
		Integer quantidadeErros = 0;		
		// Regra campo obrigat�rio
		if(this.senhaAtualSindicoProfissional.trim().equals("")){
			ManagedBeanUtil.setMensagemErro("msg.meuPainel.alterarSenha.senhaAtualRequerida");
			quantidadeErros++;
		}
		// Regra campo obrigat�rio
		if(this.senhaSindicoProfissional.trim().equals("")){
			ManagedBeanUtil.setMensagemErro("msg.meuPainel.alterarSenha.novaSenhaRequerida");	
			quantidadeErros++;
		}
		// Regra campo senha atual deve conferir com a senha da base de dados
		if (!this.sindicoProfissional.getSenha().equals(AplicacaoUtil.gerarHashMD5(this.senhaAtualSindicoProfissional))){
			ManagedBeanUtil.setMensagemErro("msg.meuPainel.alterarSenha.senhasAtualIncorreta");
			quantidadeErros++;
		}
		// Regra campo obrigat�rio
		if(this.confirmarSenhaSindicoProfissional.trim().equals("")){
			ManagedBeanUtil.setMensagemErro("msg.meuPainel.alterarSenha.ConfirmarSenhaRequerida");
			quantidadeErros++;
		}
		// Regra para verificar se o usu�rio n�o digitou a nova senha errada e n�o percebeu
		if(!this.senhaSindicoProfissional.equals(this.confirmarSenhaSindicoProfissional)){
			ManagedBeanUtil.setMensagemErro("msg.meuPainel.alterarSenha.senhasNaoCorrespondem");
			quantidadeErros++;
		}
		// Regra valica��o tamanho de campos
		if(this.senhaSindicoProfissional.length() < 6 || this.senhaSindicoProfissional.length() > 15){
			ManagedBeanUtil.setMensagemErro("msg.meuPainel.alterarSenha.novaSenha");
			quantidadeErros++;
		}
		// Regra somente letras e/ou n�meros e valica��o tamanho de campos
		if(this.confirmarSenhaSindicoProfissional.length() < 6 || this.confirmarSenhaSindicoProfissional.length() > 15){
			ManagedBeanUtil.setMensagemErro("msg.meuPainel.alterarSenha.confirmarSenha");
			quantidadeErros++;
		}
		return quantidadeErros == 0 ? Boolean.TRUE : Boolean.FALSE;
	}
	
	
	private Boolean validaNovoCondomino() {
		Integer quantidadeErros = 0;
		// Regra campo obrigat�rio
		if(this.novoCondomino.getNome().trim().equals("")){
			ManagedBeanUtil.setMensagemErro("msg.meuPainel.novoCondomino.nomeRequeido");
			quantidadeErros++;
		}
		// Regra tamanho de campos
		if(this.novoCondomino.getNome().length() > 150){
			ManagedBeanUtil.setMensagemErro("msg.meuPainel.novoCondomino.nome");
			quantidadeErros++;
		}
		// Regra campo obrigat�rio
		if(this.novoCondomino.getSexo() == null){
			ManagedBeanUtil.setMensagemErro("msg.meuPainel.novoCondomino.sexoRequerido");	
			quantidadeErros++;
		}
		// Regra campo obrigat�rio
		if (this.novoCondomino.getProprietario() == null){
			ManagedBeanUtil.setMensagemErro("msg.meuPainel.novoCondomino.proprietarioRequerido");
			quantidadeErros++;
		}
		// Regra campo obrigat�rio
		if(this.novoCondomino.getEmail().getEmail().trim().equals("")){
			ManagedBeanUtil.setMensagemErro("msg.meuPainel.novoCondomino.emailRequerido");
			quantidadeErros++;
		}
		// Regra de valida��o tamanho de campos
		if(this.novoCondomino.getEmail().getEmail().length() > 150){
			ManagedBeanUtil.setMensagemErro("msg.meuPainel.novoCondomino.email");
			quantidadeErros++;
		}
		// Regra formato de email
		if (!ManagedBeanUtil.validaEmail(this.novoCondomino.getEmail().getEmail())){
			ManagedBeanUtil.setMensagemErro("msg.meuPainel.novoCondomino.formatoEmailInvalido");
			quantidadeErros++;
		}
		// Regra para verificar se o usu�rio n�o digitou a nova senha errada e n�o percebeu
		if(!this.novoCondomino.getSenha().equals(this.novoCondomino.getConfirmarSenha())){
			ManagedBeanUtil.setMensagemErro("msg.meuPainel.novoCondomino.senhasNaoCorrespondem");
			quantidadeErros++;
		}
		// Regra campo obrigat�rio
		if(this.novoCondomino.getSenha().trim().equals("")){
			ManagedBeanUtil.setMensagemErro("msg.meuPainel.novoCondomino.senhaRequerida");
			quantidadeErros++;
		}
		// Regra campo obrigat�rio
		if(this.novoCondomino.getConfirmarSenha().trim().equals("")){
			ManagedBeanUtil.setMensagemErro("msg.meuPainel.novoCondomino.confirmarSenhaRequerida");
			quantidadeErros++;
		}
		// Regra tamanho de campos
		if(this.novoCondomino.getSenha().length() < 6 || this.novoCondomino.getSenha().length() > 15){
			ManagedBeanUtil.setMensagemErro("msg.meuPainel.novoCondomino.senha");
			quantidadeErros++;
		}
		// Regra tamanho de campos
		if(this.novoCondomino.getConfirmarSenha().length() < 6 || this.novoCondomino.getConfirmarSenha().length() > 15){
			ManagedBeanUtil.setMensagemErro("msg.meuPainel.novoCondomino.confirmarSenha");
			quantidadeErros++;
		}
		// Regra somente n�meros
		if(AplicacaoUtil.validaExpressaoRegular("[^0-9]+", this.telefoneCelularNovoCondomino)){
			ManagedBeanUtil.setMensagemErro("msg.meuPainel.novoCondomino.telefoneCelular");
			quantidadeErros++;
		}
		// Regra somente n�meros
		if(AplicacaoUtil.validaExpressaoRegular("[^0-9]+", this.telefoneComercialNovoCondomino)){
			ManagedBeanUtil.setMensagemErro("msg.meuPainel.novoCondomino.telefoneComercial");
			quantidadeErros++;
		}
		// Regra somente n�meros
		if(AplicacaoUtil.validaExpressaoRegular("[^0-9]+", this.telefoneResidencialNovoCondomino)){
			ManagedBeanUtil.setMensagemErro("msg.meuPainel.novoCondomino.telefoneResidencial");
			quantidadeErros++;
		}
		return quantidadeErros == 0 ? Boolean.TRUE : Boolean.FALSE;
	}
	
	// Regra campo imagem cond�mino		
	private Boolean validaImagemCondomino() throws Exception {
		Integer quantidadeErros = 0;		
		try {
			if (Arrays.equals(this.condomino.getImagem().getDadosArquivo(), ManagedBeanUtil.popularImagemNaoDisponivel())){
				ManagedBeanUtil.setMensagemErro("msg.meuPainel.alterarImagemCondomino.imagemRequerida");
				//ManagedBeanUtil.limpaMensagens();
				quantidadeErros++;
			}
		} catch (FileNotFoundException e) {
			logger.error("", e);
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		} catch (IOException e) {
			logger.error("", e);
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		}	
		return quantidadeErros == 0 ? Boolean.TRUE : Boolean.FALSE;
	}
	
	// Regra campo imagem cond�mino		
		private Boolean validaImagemSindicoProfissional() throws Exception {
			Integer quantidadeErros = 0;		
			try {
				if (Arrays.equals(this.sindicoProfissional.getImagem().getDadosArquivo(), ManagedBeanUtil.popularImagemNaoDisponivel())){
					ManagedBeanUtil.setMensagemErro("msg.meuPainel.alterarImagemCondomino.imagemRequerida");
					//ManagedBeanUtil.limpaMensagens();
					quantidadeErros++;
				}
			} catch (FileNotFoundException e) {
				logger.error("", e);
				ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
			} catch (IOException e) {
				logger.error("", e);
				ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
			}	
			return quantidadeErros == 0 ? Boolean.TRUE : Boolean.FALSE;
		}
	
	// Regra campo imagem funcion�rio		
	private Boolean validaImagemFuncionario() throws Exception {
		Integer quantidadeErros = 0;		
		try {
			if (Arrays.equals(this.funcionario.getImagem().getDadosArquivo(), ManagedBeanUtil.popularImagemNaoDisponivel())){
				ManagedBeanUtil.setMensagemErro("msg.meuPainel.alterarImagemFuncionario.imagemRequerida");				
				quantidadeErros++;
			}
		} catch (FileNotFoundException e) {
			logger.error("", e);
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		} catch (IOException e) {
			logger.error("", e);
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		}	
		return quantidadeErros == 0 ? Boolean.TRUE : Boolean.FALSE;
	}
	
	private Boolean validaDadosNovaGaragem() {
		Integer quantidadeErros = 0; 
		if(this.garagem.getNumero().trim().equals("")){
			ManagedBeanUtil.setMensagemErro("msg.meuPainel.garagem.numeroRequerido");
			quantidadeErros++;			
		}
		if(this.garagem.getNumero().length() > 10){
			ManagedBeanUtil.setMensagemErro("msg.meuPainel.garagem.numero");
			quantidadeErros++;			
		}		
		return quantidadeErros == 0 ? Boolean.TRUE : Boolean.FALSE;
	}	
	
	private Boolean validaDadosNovoVeiculo() {
		Integer quantidadeErros = 0; 
		if(this.veiculo.getNome().trim().equals("")){
			ManagedBeanUtil.setMensagemErro("msg.meuPainel.veiculo.nomeRequerido");
			quantidadeErros++;			
		}
		if(this.veiculo.getPlaca().trim().equals("")){
			ManagedBeanUtil.setMensagemErro("msg.meuPainel.veiculo.placaRequerida");
			quantidadeErros++;			
		}
		if(this.veiculo.getTipo() == 0){
			ManagedBeanUtil.setMensagemErro("msg.meuPainel.veiculo.tipoRequerido");
			quantidadeErros++;			
		}
		return quantidadeErros == 0 ? Boolean.TRUE : Boolean.FALSE;
	}
	
	private Boolean validaDadosContador() {
		Integer quantidadeErros = 0;
		// Regra campo obrigat�rio
		if(this.contador.getNome().trim().equals("")){
			ManagedBeanUtil.setMensagemErro("msg.meuPainel.contador.nomeRequeido");
			quantidadeErros++;
		}
		// Regra tamanho de campos
		if(this.contador.getNome().length() > 150){
			ManagedBeanUtil.setMensagemErro("msg.meuPainel.contador.nome");
			quantidadeErros++;
		}
		// Regra campo obrigat�rio
		if(this.contador.getSexo() == null){
			ManagedBeanUtil.setMensagemErro("msg.meuPainel.contador.sexoRequerido");	
			quantidadeErros++;
		}		
		// Regra campo obrigat�rio
		if(this.contador.getEmail().getEmail().trim().equals("")){
			ManagedBeanUtil.setMensagemErro("msg.meuPainel.contador.emailRequerido");
			quantidadeErros++;
		}
		// Regra de valida��o tamanho de campos
		if(this.contador.getEmail().getEmail().length() > 150){
			ManagedBeanUtil.setMensagemErro("msg.meuPainel.contador.email");
			quantidadeErros++;
		}
		// Regra formato de email
		if (!ManagedBeanUtil.validaEmail(this.contador.getEmail().getEmail())){
			ManagedBeanUtil.setMensagemErro("msg.meuPainel.contador.formatoEmailInvalido");
			quantidadeErros++;
		}
		return quantidadeErros == 0 ? Boolean.TRUE : Boolean.FALSE;
	}
	
	private Boolean validaAtualizarSenhaContador() throws NoSuchAlgorithmException {
		Integer quantidadeErros = 0;		
		// Regra campo obrigat�rio
		if(this.senhaAtualContador.trim().equals("")){
			ManagedBeanUtil.setMensagemErro("msg.meuPainel.alterarSenha.senhaAtualRequerida");
			quantidadeErros++;
		}
		// Regra campo obrigat�rio
		if(this.senhaContador.trim().equals("")){
			ManagedBeanUtil.setMensagemErro("msg.meuPainel.alterarSenha.novaSenhaRequerida");	
			quantidadeErros++;
		}
		// Regra campo senha atual deve conferir com a senha da base de dados
		if (this.senhaAtualContador != null && !this.senhaAtualContador.trim().equals("")){
			 if (!this.contador.getSenha().equals(AplicacaoUtil.gerarHashMD5(this.senhaAtualContador))){
				ManagedBeanUtil.setMensagemErro("msg.meuPainel.alterarSenha.senhasAtualIncorreta");
				quantidadeErros++;
			}
		}
			
		// Regra campo obrigat�rio
		if(this.confirmarSenhaContador.trim().equals("")){
			ManagedBeanUtil.setMensagemErro("msg.meuPainel.alterarSenha.ConfirmarSenhaRequerida");
			quantidadeErros++;
		}
		// Regra para verificar se o usu�rio n�o digitou a nova senha errada e n�o percebeu
		if(!this.senhaContador.equals(this.confirmarSenhaContador)){
			ManagedBeanUtil.setMensagemErro("msg.meuPainel.alterarSenha.senhasNaoCorrespondem");
			quantidadeErros++;
		}
		// Regra valica��o tamanho de campos
		if(this.senhaContador.length() < 6 || this.senhaContador.length() > 15){
			ManagedBeanUtil.setMensagemErro("msg.meuPainel.alterarSenha.novaSenha");
			quantidadeErros++;
		}
		// Regra somente letras e/ou n�meros e valica��o tamanho de campos
		if(this.confirmarSenhaContador.length() < 6 || this.confirmarSenhaContador.length() > 15){
			ManagedBeanUtil.setMensagemErro("msg.meuPainel.alterarSenha.confirmarSenha");
			quantidadeErros++;
		}
		return quantidadeErros == 0 ? Boolean.TRUE : Boolean.FALSE;	
	}
	
	private Boolean validaTiposUsuariosGruposUsuarios(List<GrupoUsuario> listaGrupoUsuarios, Integer... tiposUsuariosGruposUsuarios){
		for (GrupoUsuario grupoUsuario : listaGrupoUsuarios) {
			for (Integer tipoUsuarioGrupoUsuario : tiposUsuariosGruposUsuarios) {
				if(grupoUsuario.getTipoUsuario().equals(tipoUsuarioGrupoUsuario)){
					return Boolean.TRUE;
				}
			}
		}
		return Boolean.FALSE;		
	}
	

}
