package br.com.condominiosvirtuais.controller;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.Conversation;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIInput;
import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.log4j.Logger;

import br.com.condominiosvirtuais.entity.Bloco;
import br.com.condominiosvirtuais.entity.Condominio;
import br.com.condominiosvirtuais.entity.Condomino;
import br.com.condominiosvirtuais.exception.BusinessException;
import br.com.condominiosvirtuais.service.BlocoService;
import br.com.condominiosvirtuais.service.CondominioService;
import br.com.condominiosvirtuais.service.CondominoService;
import br.com.condominiosvirtuais.util.ManagedBeanUtil;
import br.com.condominiosvirtuais.vo.CondominoVO;

@Named @SessionScoped
public class BlocoMB implements IConversationScopeMB, Serializable{	
	
	private static final long serialVersionUID = 1L;

	private static Logger logger = Logger.getLogger(BlocoMB.class);
	
	@Inject
	private Conversation conversation;
	
	private Condominio condominio = null;
	
	private Bloco bloco = null;
	
	private CondominoVO condominoVO = null;
	
	@Inject
	private CondominioMB condominioMB = new CondominioMB();
	
	@Inject
	private CondominoMB condominoMB = new CondominoMB();
	
	private String nomeSindico = "";
	
	private Integer idSindico = null;	
	
	private String nomeSubSindico = "";
	
	private Integer idSubSindico = null;
	
	private ListDataModel<Bloco> listaBlocos = null;
	
	private String nomeBlocoTipo = null;
	
	private String tipoSequencia = "N";
	
	private String sequenciaInicial = null;
	
	private String sequenciaFinal = null;
	
	private List<String> listaSequencia = null;
	
	private Integer cadastroEmLote = 0;	
	
	@Inject
	private BlocoService blocoService = null;
	
	@Inject
	private CondominoService condominoService = null;
	
	@Inject
	private CondominioService condominioService = null;
	
	private String alfabeto = "ABCDEFGHIJKLMNOPQRSTUVXYZ";
	
	private List<CondominoVO> listaDeCondominosVOs = null;

	private List<CondominoVO> listaDeConselheirosVOs = null;
	
	private List<SelectItem> listaSICondominios = null;	
	
	private Boolean blocoPossuiCondominos;
	
	private UIInput componenteNomeBloco;
	
	public BlocoMB() {		
		this.condominio = new Condominio();
		this.bloco = new Bloco();

	}
	
	public void pesquisar(ActionEvent event){ 	
		try {
			this.listaBlocos = new ListDataModel<Bloco>(this.blocoService.buscarPorCondominioENome(this.condominio, this.bloco.getNome()));
			if (this.listaBlocos.getRowCount() == 0){
				ManagedBeanUtil.setMensagemInfo("msg.bloco.semBlocos");
			}
		} catch (SQLException e) {
			logger.error("erro sqlstate "+e.getSQLState(), e);	
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");				
		} catch (Exception e) {
			logger.error("", e);
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		}
	}
	
	public List<SelectItem> buscarListaBlocosPorCondominio(Condominio condominio){
		List<SelectItem> listaSIBloco = new ArrayList<SelectItem>();		
		List<Bloco> listaBlocos;
		try {
			listaBlocos = this.blocoService.buscarPorCondominioENome(condominio,null);
			for (int i = 0; i < listaBlocos.size(); i++) {
				Bloco bloco = listaBlocos.get(i);
				listaSIBloco.add(new SelectItem(bloco.getId(), bloco.getNome()));
			}		
		} catch (SQLException e) {
			logger.error("erro sqlstate "+e.getSQLState(), e);	
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");				
		} catch (Exception e) {
			logger.error("", e);
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		}
		return listaSIBloco;
	}
	
	public void limparFiltroBloco(ActionEvent event){
		this.limpaFormListaBloco();
		this.setCadastroEmLote(0); 		
	}
	
	public String cadastroBloco(){
		this.condominio = new Condominio();
		this.bloco = new Bloco();
		this.limpaFormCadastroBloco();		
		this.listaSICondominios = this.condominioMB.buscarListaCondominiosAtivos();
		return "cadastrar"; 
	}
	
	public String salvarBloco(){
		// Condição que valida, no caso de um cadastro em lote, se o valor da sequencia inicial é menor que a sequencia final
		try {
			if(this.getCadastroEmLote() == 1){
				List<Bloco> listaBlocos = new ArrayList<Bloco>();
				if(this.getTipoSequencia().equals("A")){				
					Integer quantidadeBlocos = this.alfabeto.indexOf(this.getSequenciaFinal()) - this.alfabeto.indexOf(this.getSequenciaInicial());
					if (this.alfabeto.indexOf(this.getSequenciaInicial()) >= this.alfabeto.indexOf(this.getSequenciaFinal())){
						ManagedBeanUtil.setMensagemErro("msg.sequenciaIniciaDiferenteFinal");
						return null;
					}
					for (int i = 0; i < quantidadeBlocos; i++) {
						Bloco bloco = new Bloco();
						bloco.setIdCondominio(this.condominio.getId());
						bloco.setNome(this.getNomeBlocoTipo() + " " + this.alfabeto.substring(i, i+1));
						listaBlocos.add(bloco);
					}				
						this.blocoService.salvarEmLote(listaBlocos);
				}else{				
					if(Integer.parseInt(this.getSequenciaInicial()) >= Integer.parseInt(this.getSequenciaFinal())){
					  ManagedBeanUtil.setMensagemErro("msg.sequenciaIniciaDiferenteFinal");
					  return null;				  
					}
					for (int i = Integer.parseInt(this.getSequenciaInicial()); i <= Integer.parseInt(this.getSequenciaFinal()); i++) {
						Bloco bloco = new Bloco();
						bloco.setIdCondominio(this.condominio.getId());
						bloco.setNome(this.getNomeBlocoTipo() + " " + i);
						listaBlocos.add(bloco);
					}
					this.blocoService.salvarEmLote(listaBlocos);
				}
			}else{
				this.bloco.setIdCondominio(this.condominio.getId());
				this.bloco.setNome(this.getNomeBlocoTipo());				
				this.blocoService.salvar(this.bloco);			
			}
			this.bloco = new Bloco();
			this.pesquisar(null);
			this.limpaFormCadastroBloco();
			ManagedBeanUtil.setMensagemInfo("msg.bloco.salvoSucesso");
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
	
	public String cancelarCadastroBloco(){
		this.listaBlocos = new ListDataModel<Bloco>();
		this.bloco = new Bloco();
		return "cancelar";
	}	
	
	public String visualizarBloco(){
		this.bloco = this.listaBlocos.getRowData();
		try {
			this.condominio = this.condominioService.buscarPorId(this.bloco.getIdCondominio());
			this.carregarDadosBloco();
		} catch (SQLException e) {
			logger.error("erro sqlstate "+e.getSQLState(), e);	
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
			return null;
		} catch (Exception e) {
			logger.error("", e);
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
			return null;
		}
		return "visualizar";		
	}
	
	public String excluirBloco(){	
		try {
			this.blocoService.excluir(this.bloco);
			this.bloco = new Bloco();
			this.pesquisar(null);
			this.limpaFormCadastroBloco();
			ManagedBeanUtil.setMensagemInfo("msg.bloco.excluirSucesso");
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
		return "excluir";
	}
	
	public String atualizarBloco(){		
		try {
			List<Condomino> listaConselheiro = new ArrayList<Condomino>();
			for (CondominoVO condominoVO : this.listaDeConselheirosVOs) {
				Condomino conselheiro = new Condomino();
				conselheiro.setId(condominoVO.getId());						
				listaConselheiro.add(conselheiro);
			}		
			this.bloco.setListaConselheiros(listaConselheiro);
			// As duas linhas abaixo são para considerar os caso onde o usuário apagou o nome do sindico e o subsindico. 0 é igual a branco.
			this.idSindico = this.nomeSindico.trim().isEmpty() ? 0 : this.idSindico;
			this.idSubSindico = this.nomeSubSindico.trim().isEmpty() ? 0 : this.idSubSindico;
			this.bloco.getSindico().setId(this.idSindico);		
			this.bloco.getSubSindico().setId(this.idSubSindico);
			/*Os ifs abaixo são para garantir que um condômino não possa assumir mais de um papel (Ex.: Síndico e subsíndico),
			 * pois a aplicação não suporta um usuário associado a mais de um grupo de usuário. */
			if(this.bloco.getSubSindico() != null && this.bloco.getSindico().getId() != 0 && this.bloco.getSubSindico().getId() != 0 &&   
					this.bloco.getSindico().getId() == this.bloco.getSubSindico().getId()){
				ManagedBeanUtil.setMensagemErro("msg.bloco.sindicoESubSindicoIgual");
				return null;
			}
			
			for (Condomino conselheiro : this.bloco.getListaConselheiros()) {
				if(conselheiro.getId() == this.bloco.getSindico().getId()){
					ManagedBeanUtil.setMensagemErro("msg.bloco.sindicoEConselheiroIgual");
					return null;
				}
				if(this.bloco.getSubSindico() != null && conselheiro.getId() == this.bloco.getSubSindico().getId()){
					ManagedBeanUtil.setMensagemErro("msg.bloco.subSindicoEConselheiroIgual");
					return null;
				}
			}			
			this.blocoService.atualizar(this.bloco);
			this.nomeSindico = null;
			this.nomeSubSindico = null;
			this.idSindico = 0;
			this.idSubSindico = 0;
			this.bloco = new Bloco();
			this.pesquisar(null);
			this.limpaFormCadastroBloco();
			ManagedBeanUtil.setMensagemInfo("msg.bloco.atualizadoSucesso");
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
		return "atualizar";		
	}
	
	public String editarBloco(){
		this.bloco = this.listaBlocos.getRowData();		
		try {
			this.condominio = this.condominioService.buscarPorId(this.bloco.getIdCondominio());
			this.carregarDadosBloco();
		} catch (SQLException e) {
			logger.error("erro sqlstate "+e.getSQLState(), e);	
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
			return null;
		} catch (Exception e) {
			logger.error("", e);
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
			return null;
		}
		return "editar";
	}
	
	public String voltarVizualizarBloco(){
		return "voltar";
	}		

	public List<SelectItem> getListaCondominios() {		
		this.listaSICondominios = this.condominioMB.buscarListaCondominiosAtivos();
		return this.listaSICondominios;
	}	
	
	public void popularListaSequencia(AjaxBehaviorEvent event){		
		this.listaSequencia = new ArrayList<String>();
		if(this.getTipoSequencia().equals("A")){
			for (int i = 0; i < this.alfabeto.length(); i++) {
				this.listaSequencia.add(alfabeto.substring(i, i+1));				
			}
		}else{
			for (int i = 0; i <= 50; i++) {
				this.listaSequencia.add(String.valueOf(i));
			}
		}
	}
	
	/**
	 * Método que encontra e id o síndico selecionado.	
	 */
	public void popularIdSindico() {
		  this.idSindico = Integer.parseInt(this.nomeSindico);
		  Boolean encontrou = Boolean.FALSE;
		  Integer i = 0;
		  while(!encontrou){		  
			CondominoVO condominoVO = this.listaDeCondominosVOs.get(i++);
			if (condominoVO.getId().equals(this.idSindico)){
				this.nomeSindico = condominoVO .getNomeCondomino();
				encontrou = Boolean.TRUE;
			}
		}	
	}
	
	public List<CondominoVO> buscarListaCondominoVO(Object nomeCondomino){	
		List<CondominoVO> listaCondominoVO = null;
		try {
			listaCondominoVO = this.condominoService.buscarListaCondominosVOPorNomeCondominoECondominio(nomeCondomino.toString(), this.condominio, this.bloco);
		} catch (SQLException e) {
			logger.error("erro sqlstate "+e.getSQLState(), e);	
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");			
		} catch (Exception e) {
			logger.error("", e);
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		}
		return listaCondominoVO;
	}	
	
	/**
	 * Método que encontra e id o subsíndico selecionado.	
	 */
	public void popularIdSubSindico() {
		  this.idSubSindico = Integer.parseInt(this.nomeSubSindico);
		  Boolean encontrou = Boolean.FALSE;
		  Integer i = 0;
		  while(!encontrou){		  
			CondominoVO condominoVO = this.listaDeCondominosVOs.get(i++);
			if (condominoVO.getId().equals(this.idSubSindico)){
				this.nomeSubSindico = condominoVO.getNomeCondomino();
				encontrou = Boolean.TRUE;
			}
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
	
	private void carregarDadosBloco() throws Exception{
		this.bloco = this.listaBlocos.getRowData();				
		Condomino sindico = this.condominoService.buscarSindicoPorBloco(this.bloco);	
		this.listaDeCondominosVOs = condominoMB.buscarListaCondominosVOPorCondominioEBloco(this.condominio,this.bloco);		
		this.blocoPossuiCondominos = this.listaDeCondominosVOs.size() == 0 ? Boolean.FALSE : Boolean.TRUE;
		List<Condomino> listaDeConselheirosLocal = this.condominoService.buscarConselheirosPorBloco(this.bloco);
		this.listaDeConselheirosVOs = new ArrayList<CondominoVO>();		
		for (Condomino conselheiro : listaDeConselheirosLocal) {
			for (int j = 0; j < this.listaDeCondominosVOs.size(); j++) {
				this.condominoVO  = this.listaDeCondominosVOs.get(j);
				if(conselheiro.getId() == this.condominoVO.getId()){					
					this.listaDeConselheirosVOs.add(this.condominoVO);
				}				
			}			
		}		
		if(sindico != null){
			this.idSindico  = sindico.getId();
			this.nomeSindico = sindico.getNome();
		}
		Condomino subSindico = this.condominoService.buscarSubSindicoPorBloco(this.bloco);
		if(subSindico != null){
			this.idSubSindico = subSindico.getId(); 
			this.nomeSubSindico = subSindico.getNome();			
		}
	}
	
	public void limpaFormListaBloco(){
		this.bloco = new Bloco();
		this.listaBlocos = new ListDataModel<Bloco>();
		this.listaSICondominios = new ArrayList<SelectItem>();
		this.condominio = new Condominio();
		this.condominio.setId(0);
		ManagedBeanUtil.cleanSubmittedValues(this.componenteNomeBloco);
		
	}
	
	private void limpaFormCadastroBloco(){
		this.componenteNomeBloco.getChildren().clear();
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

	public ListDataModel<Bloco> getListaBlocos() {
		return listaBlocos;
	}

	public void setListaBlocos(ListDataModel<Bloco> listaBlocos) {
		this.listaBlocos = listaBlocos;
	}
	
	public String getNomeBlocoTipo() {
		return nomeBlocoTipo;
	}

	public void setNomeBlocoTipo(String nomeBlocoTipo) {
		this.nomeBlocoTipo = nomeBlocoTipo;
	}

	public String getTipoSequencia() {
		return tipoSequencia;
	}

	public void setTipoSequencia(String tipoSequencia) {
		this.tipoSequencia = tipoSequencia;
	}

	public String getSequenciaInicial() {
		return sequenciaInicial;
	}

	public void setSequenciaInicial(String sequenciaInicial) {
		this.sequenciaInicial = sequenciaInicial;
	}

	public String getSequenciaFinal() {
		return sequenciaFinal;
	}

	public void setSequenciaFinal(String sequenciaFinal) {
		this.sequenciaFinal = sequenciaFinal;
	}

	public List<String> getListaSequencia() {
		return listaSequencia;
	}

	public void setListaSequencia(List<String> listaSequencia) {
		this.listaSequencia = listaSequencia;
	}

	public Integer getCadastroEmLote() {
		return cadastroEmLote;
	}

	public void setCadastroEmLote(Integer cadastroEmLote) {
		this.cadastroEmLote = cadastroEmLote;
	}

	public List<CondominoVO> getListaDeConselheirosVOs() {
		return listaDeConselheirosVOs;
	}

	public void setListaDeConselheirosVOs(List<CondominoVO> listaDeConselheirosVOs) {
		this.listaDeConselheirosVOs = listaDeConselheirosVOs;
	}

	public List<CondominoVO> getListaDeCondominosVOs() {
		return listaDeCondominosVOs;
	}

	public void setListaDeCondominosVOs(List<CondominoVO> listaDeCondominosVOs) {
		this.listaDeCondominosVOs = listaDeCondominosVOs;
	}

	public String getNomeSubSindico() {		
		return nomeSubSindico;
	}

	public void setNomeSubSindico(String nomeSubSindico) {
		this.nomeSubSindico = nomeSubSindico;
	}

	public Integer getIdSubSindico() {		
		return idSubSindico;
	}

	public void setIdSubSindico(Integer idSubSindico) {		
		this.idSubSindico = idSubSindico;
	}

	public String getNomeSindico() {
		return nomeSindico;
	}

	public void setNomeSindico(String nomeSindico) {
		this.nomeSindico = nomeSindico;
	}

	public Integer getIdSindico() {
		return idSindico;
	}

	public void setIdSindico(Integer idSindico) {
		this.idSindico = idSindico;
	}	

	public CondominioMB getCondominioMB() {
		return condominioMB;
	}

	public void setCondominioMB(CondominioMB condominioMB) {
		this.condominioMB = condominioMB;
	}	
	
	public Conversation getConversation() {
		return conversation;
	}

	public void setConversation(Conversation conversation) {
		this.conversation = conversation;
	}	

	public Boolean getBlocoPossuiCondominos() {
		return blocoPossuiCondominos;
	}

	public void setBlocoPossuiCondominos(Boolean blocoPossuiCondominos) {
		this.blocoPossuiCondominos = blocoPossuiCondominos;
	}

	public BlocoService getBlocoService() {
		return blocoService;
	}

	public void setBlocoService(BlocoService blocoService) {
		this.blocoService = blocoService;
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

	public UIInput getComponenteNomeBloco() {
		return componenteNomeBloco;
	}

	public void setComponenteNomeBloco(UIInput componenteNomeBloco) {
		this.componenteNomeBloco = componenteNomeBloco;
	}
}