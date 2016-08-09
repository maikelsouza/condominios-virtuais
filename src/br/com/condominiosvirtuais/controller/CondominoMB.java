package br.com.condominiosvirtuais.controller;

import java.io.OutputStream;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Instance;
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
import br.com.condominiosvirtuais.entity.EmailUsuario;
import br.com.condominiosvirtuais.entity.Unidade;
import br.com.condominiosvirtuais.enumeration.AtributoSessaoEnum;
import br.com.condominiosvirtuais.enumeration.TipoGrupoUsuarioEnum;
import br.com.condominiosvirtuais.enumeration.UsuarioEnum;
import br.com.condominiosvirtuais.exception.BusinessException;
import br.com.condominiosvirtuais.service.CondominoService;
import br.com.condominiosvirtuais.util.AplicacaoUtil;
import br.com.condominiosvirtuais.util.ManagedBeanUtil;
import br.com.condominiosvirtuais.vo.CondominoVO;

@Named @SessionScoped
public class CondominoMB implements  Serializable{
	
	private static final long serialVersionUID = 1L;

	private static Logger logger = Logger.getLogger(CondominoMB.class);
	
	private Condomino condomino = null;	
	
	private List<SelectItem> listaSICondominios = new ArrayList<SelectItem>();
	
	private List<SelectItem> listaSIBlocos = null;
	
	private List<SelectItem> listaSIUnidades = new ArrayList<SelectItem>();	
	
	private Bloco bloco = null;
	
	private Condominio condominio = null;
	
	private Unidade unidade = null;
	
	private ListDataModel<CondominoVO> listaCondominosVO = null;
	
	@Inject
	private CondominoService condominoService = null;
	
	@Inject
	private Instance<CondominioMB> condominioMB = null;
	
	@Inject
	private Instance<BlocoMB> blocoMB = null;
	
	@Inject
	private Instance<UnidadeMB> unidadeMB = null;
	
	private List<SelectItem> listaSIDias = new ArrayList<SelectItem>();
	
	private List<SelectItem> listaSIMeses = new ArrayList<SelectItem>();
	
	private List<SelectItem> listaSIAnos = new ArrayList<SelectItem>();	
	
	private CondominoVO condominoVO;
	
	private Boolean telaDeCadastro; 
	
	private UIInput componenteNomeCondomino;
	
	private UIInput componenteEmailCondomino;
	
	private UIInput componenteTelefoneResidencialCondomino;
	
	private UIInput componenteTelefoneComercialCondomino;
	
	private UIInput componenteTelefoneCelularCondomino;
	
	
	
	@PostConstruct
	public void iniciarCondominoMB(){		
		this.condomino = new Condomino();
		this.condominio = new Condominio();
		this.bloco = new Bloco();
		this.unidade = new Unidade();	
		this.condominoVO = new CondominoVO();
		ManagedBeanUtil.popularSIDias(listaSIDias);
		ManagedBeanUtil.popularSIMeses(listaSIMeses);
		ManagedBeanUtil.popularSIAnos(listaSIAnos);
	}

	
	public void carregaCondominioCadastroCondominoPrimeiraVez(){		
		if (ManagedBeanUtil.getSession(false).getAttribute(AtributoSessaoEnum.CONDOMINIO.getAtributo()) != null){
			this.condominio = (Condominio) ManagedBeanUtil.getSession(false).getAttribute("condominio");
			this.listaSIBlocos = this.blocoMB.get().buscarListaBlocosPorCondominio(this.condominio);
			ManagedBeanUtil.getSession(false).removeAttribute("condominio");
			this.listaSIUnidades.clear();
		}		
	}
	
	public void getImagem(OutputStream out, Object data)  {
		  try {
			  if(this.condominoVO != null && this.condominoVO.getArquivo() != null){			  
				out.write(this.condominoVO.getArquivo().getDadosArquivo());
			  }else{
				  byte[] sendBuf = ManagedBeanUtil.popularImagemNaoDisponivel();			  
				  out.write(sendBuf);
			  }
			  out.close();
		  } catch (Exception e){
			  logger.error("", e);
			  ManagedBeanUtil.setMensagemErro("msg.erro.executarOperacao");			  
		  }			  
	  }	

	public void popularListaBlocos(){		
		if(this.telaDeCadastro == Boolean.FALSE){
			this.condominio.setId(this.condominoVO.getIdCondominio());
		}		
		this.listaSIBlocos = this.blocoMB.get().buscarListaBlocosPorCondominio(this.condominio);			
		this.listaSIUnidades.clear();
	}
	
	
	public void popularListaUnidades(){		
		if(this.telaDeCadastro == Boolean.FALSE){
			this.bloco.setId(this.condominoVO.getIdBloco());
		}
		this.listaSIUnidades = this.unidadeMB.get().buscarListaSIUnidadesPorBloco(this.bloco);		
	}
	
	public String cancelarCadastroCondominioPrimeiraVez(){		
		return "cancelarCadastroCondominoPrimeiraVez";
	}
	
		
	public void pesquisar(ActionEvent event){
		try {			
			this.popularNomeCondominido();
			this.listaCondominosVO = new ListDataModel<CondominoVO>(this.condominoService.buscarListaCondominosVOPorCondominioBlocoEUnidade(
					this.condominio, this.bloco, this.unidade));
			
			if (this.listaCondominosVO.getRowCount() == 0){
				ManagedBeanUtil.setMensagemInfo("msg.condomino.semCondominos");
			}
		} catch (SQLException e) {
			logger.error("erro sqlstate "+e.getSQLState(), e);	
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");				
		} catch (Exception e) {
			logger.error("", e);
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		}
	}
	
	public String editarCondomino(){
		this.condominoVO = this.listaCondominosVO.getRowData();		
		this.telaDeCadastro = Boolean.FALSE;
		this.popularListaBlocos();
		this.popularListaUnidades();
		return "editar";		        
	}
	
	public String cadastroCondomino(){		
		this.condomino = new Condomino();
		this.telaDeCadastro = Boolean.TRUE;
		return "cadastrar"; 
	}
	
	public String salvarCondomino(){
		try {
			if(!this.condomino.getSenha().equals(this.condomino.getConfirmarSenha())){
				ManagedBeanUtil.setMensagemErro("msg.condomino.senhasNaoCorrespondem");
				return null;
			}
			if (!ManagedBeanUtil.validaEmail(this.condomino.getEmail().getEmail())){
				ManagedBeanUtil.setMensagemErro("msg.condomino.formatoEmailInvalido");
				return null;
			}
			List<Condominio> listaCondominio = new ArrayList<Condominio>();
			listaCondominio.add(this.condominio);
			this.condomino.setListaCondominio(listaCondominio);
			this.condomino.setIdUnidade(this.unidade.getId());			
			this.condomino.setIdGrupoUsuario(TipoGrupoUsuarioEnum.CONDOMINO.getGrupoUsuario());
			this.condomino.getEmail().setPrincipal(Boolean.TRUE);
			this.condomino.setSituacao(UsuarioEnum.ATIVO.getSituacao());
			this.condominoService.salvar(this.condomino);
			this.pesquisar(null);
			ManagedBeanUtil.setMensagemInfo("msg.condomino.salvaSucesso");
			return "salvar";
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
	}
	
	public String salvarCondominoPrimeiraVez(){
		try {
			if(!this.condomino.getSenha().equals(this.condomino.getConfirmarSenha())){
				ManagedBeanUtil.setMensagemErro("msg.condomino.senhasNaoCorrespondem");
				return null;
			}
			if (!ManagedBeanUtil.validaEmail(this.condomino.getEmail().getEmail())){
				ManagedBeanUtil.setMensagemErro("msg.condomino.formatoEmailInvalido");
				return null;
			}
			List<Condominio> listaCondominio = new ArrayList<Condominio>();
			listaCondominio.add(this.condominio);
			this.condomino.setListaCondominio(listaCondominio);
			this.condomino.setIdUnidade(this.unidade.getId());			
			this.condomino.setIdGrupoUsuario(TipoGrupoUsuarioEnum.CONDOMINO.getGrupoUsuario());
			this.condomino.getEmail().setPrincipal(Boolean.TRUE);
			this.condomino.setSituacao(UsuarioEnum.ATIVO.getSituacao());
			this.condominoService.salvar(this.condomino);			
			ManagedBeanUtil.setMensagemInfo("msg.condomino.salvaSucessoPrimeiraVez");
			return "salvarCondominoPrimeiraVez";
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
	}	
	
	public String visualizarCondomino(){
		this.condominoVO = this.listaCondominosVO.getRowData();
		return "visualizar";
	}
	              
	public String voltarVisualizarCondomino(){		
		return "voltar";
	}

	public String atualizarCondomino(){
		try{
			this.condomino = new Condomino();
			this.condomino.setId(this.condominoVO.getIdCondomino());
			this.condomino.setNome(this.condominoVO.getNomeCondomino());
			this.condomino.setSexo(this.condominoVO.getSexoCondomino());
			this.condomino.setDataNascimentoDia(this.condominoVO.getDataNascimentoDiaCondomino());
			this.condomino.setDataNascimentoMes(this.condominoVO.getDataNascimentoMesCondomino());
			this.condomino.setDataNascimentoAno(this.condominoVO.getDataNascimentoAnoCondomino());
			this.condomino.setProprietario(this.condominoVO.getProprietarioCondomino());
			this.condomino.setTelefoneCelular(this.condominoVO.getTelefoneCelularCondomino());
			this.condomino.setTelefoneComercial(this.condominoVO.getTelefoneComercialCondomino());
			this.condomino.setTelefoneResidencial(this.condominoVO.getTelefoneResidencialCondomino());
			this.condomino.setSituacao(this.condominoVO.getSituacaoCondomino());
			this.condomino.setIdUnidade(this.condominoVO.getIdUnidade());
			EmailUsuario emailUsuario = new EmailUsuario();
			emailUsuario.setId(this.condominoVO.getIdEmailCondomino());
			emailUsuario.setEmail(this.condominoVO.getEmailCondomino());		
			emailUsuario.setPrincipal(Boolean.TRUE);
			emailUsuario.setIdUsuario(this.condominoVO.getIdCondomino());
			this.condomino.setEmail(emailUsuario);	
			List<Condominio> listaCondominio = new ArrayList<Condominio>();
			listaCondominio.add(this.condominio);
			this.condomino.setListaCondominio(listaCondominio);						
			this.condomino.setIdGrupoUsuario(this.condominoVO.getIdGrupoUsuario());
			if (!ManagedBeanUtil.validaEmail(this.condomino.getEmail().getEmail())){
				ManagedBeanUtil.setMensagemErro("msg.condomino.formatoEmailInvalido");
				return null;
			}
			this.condominoService.atualizar(this.condomino);		
			this.pesquisar(null);
			ManagedBeanUtil.setMensagemInfo("msg.condomino.atualizadoSucesso");
			return "atualizar";
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
	}
	
	public String excluirCondomino(){
		try{
			this.condomino.setId(this.condominoVO.getId());
			this.condomino.getEmail().setId(this.condominoVO.getIdEmailCondomino());
			this.condomino.setImagem(this.condominoVO.getArquivo());
			this.condominoService.excluir(this.condomino);
			this.pesquisar(null);
			ManagedBeanUtil.setMensagemInfo("msg.condomino.excluirSucesso");
			return "excluir";
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
	}
	
	public void limparFiltroCondomino(ActionEvent event){
		this.listaCondominosVO = new ListDataModel<CondominoVO>();
		this.condominio = new Condominio();
		this.bloco = new Bloco();
		this.unidade = new Unidade();		
		this.condomino = new Condomino();		
	}

	public String cancelarCadastroCondomino(){
		this.listaCondominosVO = new ListDataModel<CondominoVO>();
		this.condominio = new Condominio();
		this.bloco = new Bloco();
		this.unidade = new Unidade();		
		this.condomino = new Condomino();
		return "cancelar";		
	}
	
	public String cancelarCadastroCondominoPrimeiraVez(){
		return "cancelarCadastroCondominoPrimeiraVez";		
	}
	
	public List<CondominoVO> buscarListaCondominosVOPorNomeCondominoECondominio(String nomeCondomino, Condominio condominio){
		List<CondominoVO> listaCondominoVO = null;
		try {
			listaCondominoVO = this.condominoService.buscarListaCondominosVOPorNomeCondominoECondominio(nomeCondomino, condominio);
		} catch (SQLException e) {
			logger.error("erro sqlstate "+e.getSQLState(), e);	
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");			
		} catch (Exception e) {
			logger.error("", e);
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		}
		return listaCondominoVO;
	}
	
	public List<CondominoVO> buscarListaCondominosVOPorCondominio(Condominio condominio){
		List<CondominoVO> listaCondominoVO = null;
		try {
			listaCondominoVO = this.condominoService.buscarListaCondominosVOPorCondominioBlocoEUnidade(condominio,null,null);
		} catch (SQLException e) {
			logger.error("erro sqlstate "+e.getSQLState(), e);	
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		} catch (Exception e) {
			logger.error("", e);
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		}
		return listaCondominoVO; 
	}
	
	public List<CondominoVO> buscarListaCondominosVOPorCondominioEBloco(Condominio condominio, Bloco bloco){
		List<CondominoVO> listaCondominoVO = null;
		try {
			listaCondominoVO = this.condominoService.buscarListaCondominosVOPorCondominioBlocoEUnidade(condominio,bloco,null);
		} catch (SQLException e) {
			logger.error("erro sqlstate "+e.getSQLState(), e);	
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		} catch (Exception e) {
			logger.error("", e);
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		}
		return listaCondominoVO;
	}
	
	public List<SelectItem> getListaSituacoes(){
		List<SelectItem> listaSituacoes = new ArrayList<SelectItem>();
		listaSituacoes.add(new SelectItem(UsuarioEnum.INATIVO.getSituacao(), AplicacaoUtil.i18n("condomino.situacao.itemLabel.0")));
		listaSituacoes.add(new SelectItem(UsuarioEnum.ATIVO.getSituacao(), AplicacaoUtil.i18n("condomino.situacao.itemLabel.1")));
		return listaSituacoes;
	}
	

	public Condomino getCondomino() {		
		return condomino;
	}
	
	
	public void setCondomino(Condomino condomino) {
		this.condomino = condomino;
	}	

	public List<SelectItem> getListaSICondominios() {
		this.listaSICondominios = this.condominioMB.get().buscarListaCondominiosAtivos();
		return listaSICondominios;
	}

	public void setListaSICondominios(List<SelectItem> listaSICondominios) {
		this.listaSICondominios = listaSICondominios;
	}

	public List<SelectItem> getListaSIBlocos() {
		return this.listaSIBlocos;
	}

	public void setListaSIBlocos(List<SelectItem> listaSIBlocos) {
		this.listaSIBlocos = listaSIBlocos;
	}

	public List<SelectItem> getListaSIUnidades() {
		return listaSIUnidades;
	}

	public void setListaSIUnidades(List<SelectItem> listaSIUnidades) {
		this.listaSIUnidades = listaSIUnidades;
	}

	public Bloco getBloco() {
		return bloco;
	}

	public void setBloco(Bloco bloco) {
		this.bloco = bloco;
	}

	public Condominio getCondominio() {
		return condominio;
	}

	public void setCondominio(Condominio condominio) {
		this.condominio = condominio;
	}

	public Unidade getUnidade() {
		return unidade;
	}

	public void setUnidade(Unidade unidade) {
		this.unidade = unidade;
	}	

	public CondominoService getCondominoService() {
		return condominoService;
	}
	
	public void setCondominoService(CondominoService condominoService) {
		this.condominoService = condominoService;
	}


	public ListDataModel<CondominoVO> getListaCondominosVO() {
		return listaCondominosVO;
	}

	public void setListaCondominosVO(ListDataModel<CondominoVO> listaCondominosVO) {
		this.listaCondominosVO = listaCondominosVO;
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
	
	public CondominoVO getCondominoVO() {
		return condominoVO;
	}

	public void setCondominoVO(CondominoVO condominoVO) {
		this.condominoVO = condominoVO;
	}	

	public Boolean getTelaDeCadastro() {
		return telaDeCadastro;
	}

	public void setTelaDeCadastro(Boolean telaDeCadastro) {
		this.telaDeCadastro = telaDeCadastro;
	}
	

	public UIInput getComponenteNomeCondomino() {
		return componenteNomeCondomino;
	}

	public void setComponenteNomeCondomino(UIInput componenteNomeCondomino) {
		this.componenteNomeCondomino = componenteNomeCondomino;
	}

	public UIInput getComponenteEmailCondomino() {
		return componenteEmailCondomino;
	}

	public void setComponenteEmailCondomino(UIInput componenteEmailCondomino) {
		this.componenteEmailCondomino = componenteEmailCondomino;
	}

	public UIInput getComponenteTelefoneResidencialCondomino() {
		return componenteTelefoneResidencialCondomino;
	}

	public void setComponenteTelefoneResidencialCondomino(
			UIInput componenteTelefoneResidencialCondomino) {
		this.componenteTelefoneResidencialCondomino = componenteTelefoneResidencialCondomino;
	}

	public UIInput getComponenteTelefoneComercialCondomino() {
		return componenteTelefoneComercialCondomino;
	}

	public void setComponenteTelefoneComercialCondomino(
			UIInput componenteTelefoneComercialCondomino) {
		this.componenteTelefoneComercialCondomino = componenteTelefoneComercialCondomino;
	}

	public UIInput getComponenteTelefoneCelularCondomino() {
		return componenteTelefoneCelularCondomino;
	}

	public void setComponenteTelefoneCelularCondomino(
			UIInput componenteTelefoneCelularCondomino) {
		this.componenteTelefoneCelularCondomino = componenteTelefoneCelularCondomino;
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


}