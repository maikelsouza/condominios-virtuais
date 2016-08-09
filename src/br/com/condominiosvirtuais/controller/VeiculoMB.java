package br.com.condominiosvirtuais.controller;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UISelectOne;
import javax.faces.event.ActionEvent;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.log4j.Logger;

import br.com.condominiosvirtuais.entity.Condominio;
import br.com.condominiosvirtuais.entity.Condomino;
import br.com.condominiosvirtuais.entity.Garagem;
import br.com.condominiosvirtuais.entity.Veiculo;
import br.com.condominiosvirtuais.service.CondominoService;
import br.com.condominiosvirtuais.service.GaragemService;
import br.com.condominiosvirtuais.service.VeiculoService;
import br.com.condominiosvirtuais.util.ManagedBeanUtil;
import br.com.condominiosvirtuais.vo.CondominoVO;

@Named @SessionScoped
public class VeiculoMB implements IConversationScopeMB, Serializable {
	
/**
 * TODO: Foi removido os campos onde faziam o fitro por nome do condômino e etc. 
 * Falta remover os código. Data 30/07/2015. Remover em 180 dias.	
 */
	private static final long serialVersionUID = 1L;

	private static Logger logger = Logger.getLogger(VeiculoMB.class);
	
	private Veiculo veiculo;
	
	private List<SelectItem> listaSICondominios = null;	
	
	private ListDataModel<Veiculo> listaDMVeiculo = null;
	
	@Inject
	private Conversation conversation;
	
	@Inject
	private VeiculoService veiculoService;
	
	@Inject
	private CondominoService condominoService;
	
	@Inject
	private GaragemService garagemService;
	
	@Inject
	private CondominoMB condominoMB = null;
	
	@Inject
	private CondominioMB condominioMB = null;
	
	private List<CondominoVO> listaDeCondominosVOs = null;
	
	private Condominio condominio;
	
	private Condomino condomino;
	
	private Integer idCondomino;
	
	private String nomeCondomino;
	
	private Boolean buscarTodos = Boolean.TRUE;
	
	private List<Garagem> listaGaragem = null;
	
	private UISelectOne componenteItensCondominio;
	
	
	@PostConstruct
	public void iniciarVeiculoMB(){
		this.veiculo = new Veiculo();		
		this.condominio = new Condominio();		
		this.condomino = new Condomino();
		this.listaGaragem = new ArrayList<Garagem>();
		this.listaSICondominios = this.condominioMB.buscarListaCondominiosAtivos();
		if(this.listaSICondominios.size() != 0){
			// Necessário setar o primeiro condomínio para não dar problema na consulta autocomplete
			this.condominio.setId((Integer) this.listaSICondominios.get(0).getValue());			
		}
		this.populaCondominoVO();
	}
	
	private void populaIdGaragem(){
		Boolean encontrouGaragem = Boolean.FALSE;
		Integer indice = 0;
		this.veiculo.getGaragem().setId(null);
		Garagem garagem = null;
		while(this.listaGaragem.size() > indice && !encontrouGaragem){
			garagem = this.listaGaragem.get(indice);
			if (garagem.getNumero().equals(this.veiculo.getGaragem().getNumero())){
				this.veiculo.getGaragem().setId(garagem.getId());
				encontrouGaragem = Boolean.TRUE;
			}
			indice++;
		}
	}

	public String pesquisar(){
		try{			
			if(this.buscarTodos){
				this.listaDMVeiculo = new ListDataModel<Veiculo>(this.veiculoService.buscarPorIdCondominio(this.condominio.getId()));			
			}else{
				if(this.validaDadosVeiculo()){
					Condomino condomino = new Condomino();
					condomino.setId(this.idCondomino);
					this.veiculo.setCondomino(condomino);
					this.populaIdGaragem();
					this.listaDMVeiculo = new ListDataModel<Veiculo>(this.veiculoService.buscarPorIdCondominoOuNomeOuPlacaOuNumeroGaragem(this.veiculo));
				}
			}
			if (this.listaDMVeiculo != null && this.listaDMVeiculo.getRowCount() == 0){
				ManagedBeanUtil.setMensagemInfo("msg.veiculo.semVeiculos");
			}			
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
		return null;
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
	
	public List<Veiculo> buscarListaVeiculoPorNome(Object nomeVeiculo){		
		List<Veiculo> listaVeiculo = null;
		try {
			listaVeiculo = this.veiculoService.buscarPorIdCondominioENome(this.condominio.getId(), nomeVeiculo.toString());
		} catch (SQLException e) {
			logger.error("erro sqlstate "+e.getSQLState(), e);	
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");				
		} catch (Exception e) {
			logger.error("", e);
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		}
		return listaVeiculo;
	}
	
	public List<Veiculo> buscarListaVeiculoPorPlaca(Object placaVeiculo){		
		List<Veiculo> listaVeiculo = null;
		try {
			listaVeiculo = this.veiculoService.buscarPorIdCondominioEPlaca(this.condominio.getId(), placaVeiculo.toString());
		} catch (SQLException e) {
			logger.error("erro sqlstate "+e.getSQLState(), e);	
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");				
		} catch (Exception e) {
			logger.error("", e);
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		}	
		return listaVeiculo;
	}
	
	public List<Garagem> buscarListaGaragemPorNumero(Object numeroGaragem){
	//	List<Garagem> listaGaragem = null;
		try {
			listaGaragem = this.garagemService.buscarPorIdCondominoENumero(this.condominio.getId(), numeroGaragem.toString());
		} catch (SQLException e) {
			logger.error("erro sqlstate "+e.getSQLState(), e);	
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");				
		} catch (Exception e) {
			logger.error("", e);
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		}
		return listaGaragem;
	}
	
	/**
	 * Método que encontra e id do condomino selecionado.	
	 */
	public void popularIdCondominoVO() {
		  this.idCondomino = Integer.parseInt(this.nomeCondomino);
		  Boolean encontrou = Boolean.FALSE;
		  Integer i = 0;
		  while(!encontrou){		  
			CondominoVO condominoVO = this.listaDeCondominosVOs.get(i++);
			if (condominoVO.getIdCondomino().equals(this.idCondomino)){
				this.nomeCondomino = condominoVO.getNomeCondomino();
				encontrou = Boolean.TRUE;
			}
		}	
	}
	
	public void populaCondominoVO(){
		this.listaDeCondominosVOs = condominoMB.buscarListaCondominosVOPorCondominio(this.condominio);
	}
	
	public void limparFiltroVeiculo(ActionEvent event){
		this.veiculo = new Veiculo();
		this.buscarTodos = Boolean.TRUE;
		this.nomeCondomino = "";
		this.idCondomino = null;
		this.listaDMVeiculo = new ListDataModel<Veiculo>();
		this.condominio = new Condominio();
		this.condominio.setId((Integer) this.listaSICondominios.get(0).getValue());
		ManagedBeanUtil.cleanSubmittedValues(this.componenteItensCondominio);
	}
	
	@Override
	public void abreSessao() {
		ManagedBeanUtil.abreSessao(conversation);
	}

	@Override
	public void fechaSessao() {
		ManagedBeanUtil.fechaSessao(conversation);		
	}	
	
	private Boolean validaDadosVeiculo() {
		Integer quantidadeErros = 0;		
		if((this.veiculo.getCondomino().getNome() == null || this.veiculo.getCondomino().getNome().trim().isEmpty())
				&& this.veiculo.getNome().trim().isEmpty()
				&& this.veiculo.getPlaca().trim().isEmpty() 
				&& this.idCondomino == 0
				&& (this.veiculo.getGaragem() == null || this.veiculo.getGaragem().getNumero().trim().isEmpty())){
			ManagedBeanUtil.setMensagemErro("msg.veiculo.camposConsultaRequerido");
			quantidadeErros++;			
		}		
		return quantidadeErros == 0 ? Boolean.TRUE : Boolean.FALSE;
	}
	
	public Veiculo getVeiculo() {
		return veiculo;
	}

	public void setVeiculo(Veiculo veiculo) {
		this.veiculo = veiculo;
	}

	public List<SelectItem> getListaSICondominios() {
		return listaSICondominios;
	}

	public void setListaSICondominios(List<SelectItem> listaSICondominios) {
		this.listaSICondominios = listaSICondominios;
	}

	public Condominio getCondominio() {
		return condominio;
	}

	public void setCondominio(Condominio condominio) {
		this.condominio = condominio;
	}

	public Condomino getCondomino() {
		return condomino;
	}

	public void setCondomino(Condomino condomino) {
		this.condomino = condomino;
	}

	public ListDataModel<Veiculo> getListaDMVeiculo() {
		return listaDMVeiculo;
	}

	public void setListaDMVeiculo(ListDataModel<Veiculo> listaDMVeiculo) {
		this.listaDMVeiculo = listaDMVeiculo;
	}

	public List<CondominoVO> getListaDeCondominosVOs() {
		return listaDeCondominosVOs;
	}

	public void setListaDeCondominosVOs(List<CondominoVO> listaDeCondominosVOs) {
		this.listaDeCondominosVOs = listaDeCondominosVOs;
	}

	public Integer getIdCondomino() {
		return idCondomino;
	}

	public void setIdCondomino(Integer idCondomino) {
		this.idCondomino = idCondomino;
	}	

	public VeiculoService getVeiculoService() {
		return veiculoService;
	}

	public void setVeiculoService(VeiculoService veiculoService) {
		this.veiculoService = veiculoService;
	}

	public CondominoService getCondominoService() {
		return condominoService;
	}

	public void setCondominoService(CondominoService condominoService) {
		this.condominoService = condominoService;
	}

	public GaragemService getGaragemService() {
		return garagemService;
	}

	public void setGaragemService(GaragemService garagemService) {
		this.garagemService = garagemService;
	}

	public CondominoMB getCondominoMB() {
		return condominoMB;
	}

	public void setCondominoMB(CondominoMB condominoMB) {
		this.condominoMB = condominoMB;
	}

	public CondominioMB getCondominioMB() {
		return condominioMB;
	}

	public void setCondominioMB(CondominioMB condominioMB) {
		this.condominioMB = condominioMB;
	}

	public String getNomeCondomino() {
		return nomeCondomino;
	}

	public void setNomeCondomino(String nomeCondomino) {
		this.nomeCondomino = nomeCondomino;
	}

	public Boolean getBuscarTodos() {
		return buscarTodos;
	}

	public void setBuscarTodos(Boolean buscarTodos) {
		this.buscarTodos = buscarTodos;
	}

	public Conversation getConversation() {
		return conversation;
	}

	public void setConversation(Conversation conversation) {
		this.conversation = conversation;
	}

	public UISelectOne getComponenteItensCondominio() {
		return componenteItensCondominio;
	}

	public void setComponenteItensCondominio(UISelectOne componenteItensCondominio) {
		this.componenteItensCondominio = componenteItensCondominio;
	}
	
}
