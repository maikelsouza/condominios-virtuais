package br.com.condominiosvirtuais.controller;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.faces.model.ListDataModel;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.log4j.Logger;

import br.com.condominiosvirtuais.entity.Aba;
import br.com.condominiosvirtuais.entity.Componente;
import br.com.condominiosvirtuais.entity.GrupoUsuario;
import br.com.condominiosvirtuais.entity.Tela;
import br.com.condominiosvirtuais.enumeration.AtributoSessaoEnum;
import br.com.condominiosvirtuais.persistence.ModuloDAO;
import br.com.condominiosvirtuais.util.ManagedBeanUtil;
import br.com.condominiosvirtuais.vo.AbaVO;
import br.com.condominiosvirtuais.vo.TelaVO;

@Named @SessionScoped
public class TelaMB implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private static Logger logger = Logger.getLogger(TelaMB.class);
	
	private Tela tela;
	
	private TelaVO telaVO;
	
	private GrupoUsuario grupoUsuario;
	
	private ListDataModel<TelaVO> listaTelaVO;
	
	private ListDataModel<AbaVO> listaAbaVO;
	
	private List<AbaVO> listaTemporariaAbaVO;
	
	private Boolean checadoTodos =  Boolean.FALSE;
	
	@Inject
	private ModuloDAO moduloDAO;
		
	
	public void inicializaVisualizarGrupoUsuarioTelaMB() throws SQLException, Exception{
		this.grupoUsuario = (GrupoUsuario) ManagedBeanUtil.getSession(Boolean.FALSE).getAttribute(AtributoSessaoEnum.GRUPO_USUARIO.getAtributo());
		this.populaTelaVO();
	}
	
//	public void inicializaTelaAbaMB() throws SQLException, Exception{
//		
//		this.telaVO = (TelaVO) ManagedBeanUtil.getSession(Boolean.FALSE).getAttribute(AtributoSessaoEnum.TELA_VO.getAtributo());		
//		this.populaAbaVO();		
//	}
	
	public String visualizarTelaAba(){		
		try {
			this.telaVO = (TelaVO) this.listaTelaVO.getRowData();
			ManagedBeanUtil.getSession(Boolean.TRUE).setAttribute(AtributoSessaoEnum.TELA_VO.getAtributo(),this.telaVO);
			if(this.telaVO.getListaAbasTela().isEmpty()){
				ManagedBeanUtil.setMensagemInfo("msg.tela.semAbas");
			}
		} catch (Exception e) {
			logger.error("", e);
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		}
		return "visualizar";
	}
	
	public String visualizarTelaComponente(){		
		try {
			this.telaVO = (TelaVO) this.listaTelaVO.getRowData();
			ManagedBeanUtil.getSession(Boolean.TRUE).setAttribute(AtributoSessaoEnum.TELA_VO.getAtributo(),this.telaVO);
			if(this.telaVO.getListaComponentesTela().isEmpty()){
				ManagedBeanUtil.setMensagemInfo("msg.tela.semComponentes");
			}
		} catch (Exception e) {
			logger.error("", e);
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		}
		return "visualizar";
	}
	
	public String salvarListaTelaAba(){
		this.telaVO.setChecada(Boolean.FALSE);
		Iterator<AbaVO> iteratorAbaVO = listaAbaVO.iterator();
		List<AbaVO> listaAbaVO = new ArrayList<AbaVO>();
		while (iteratorAbaVO.hasNext()) {
		AbaVO abaVO = iteratorAbaVO.next();
			if(abaVO.getChecada() != null && abaVO.getChecada()){
				this.telaVO.setChecada(Boolean.TRUE);
			}
			listaAbaVO.add(abaVO);
		//}
	}
//	if(!listaAbaVO.isEmpty()){
//		
//	}
		
		
//		Iterator<AbaVO> iteratorAbaVO = listaAbaVO.iterator();
//		Iterator<AbaVO> iteratorAbaVOTemporaria = listaTemporariaAbaVO.iterator();
//		List<AbaVO> listaAbaVO = new ArrayList<AbaVO>();
//		while (iteratorAbaVOTemporaria.hasNext()) {
//			AbaVO abaVO = iteratorAbaVOTemporaria.next();
//			if(abaVO.getChecada() != null && abaVO.getChecada()){				
//				listaAbaVO.add(abaVO);
//			}
//		}
//		if(!listaAbaVO.isEmpty()){
//			this.telaVO.setListaAbasVOTela(listaAbaVO);
//		}
		
		this.telaVO.setListaAbasVOTela(listaAbaVO);
		ManagedBeanUtil.getSession(Boolean.FALSE).setAttribute(AtributoSessaoEnum.TELA_VO.getAtributo(),this.telaVO);
		return "salvar";
	}
	
	public String voltarListaTelaAba(){
		// While necessário para retornar ao estado anterior os checks das abas.
		Iterator<AbaVO> iteratorAbaVOTemporaria = listaTemporariaAbaVO.iterator();
		Iterator<AbaVO> iteratorAbaVO = null;
		Boolean encontrou = null;
		while (iteratorAbaVOTemporaria.hasNext()) {
			AbaVO abaVOTemporaria = iteratorAbaVOTemporaria.next();
			encontrou = Boolean.FALSE;
			iteratorAbaVO = listaAbaVO.iterator();
			while (iteratorAbaVO.hasNext() && !encontrou) {
				AbaVO abaVO = iteratorAbaVO.next();
				if(abaVOTemporaria.getIdAba().equals(abaVO.getIdAba())){
					abaVO.setChecada(abaVOTemporaria.getChecada());
					encontrou = Boolean.TRUE;	
				}
			}
		}
		this.ab();
		return "voltar";
	}
	
	public void ab(){
		Iterator<AbaVO> iteratorAbaVO = listaAbaVO.iterator();
		Integer contarChecadas = 0;
		while (iteratorAbaVO.hasNext()) {
		AbaVO abaVO = iteratorAbaVO.next();
			if(abaVO.getChecada() != null && abaVO.getChecada()){
				contarChecadas++;
			}
		}
		if(contarChecadas == listaAbaVO.getRowCount()){
			this.checadoTodos = Boolean.TRUE;
		}else{
			this.checadoTodos = Boolean.FALSE;			
		}
	}
	
	public void checarTodosCheckbox2(){
		this.telaVO = (TelaVO) ManagedBeanUtil.getSession(Boolean.FALSE).getAttribute(AtributoSessaoEnum.TELA_VO.getAtributo());
		Iterator<AbaVO> iteratorAbaVO = this.telaVO.getListaAbasVOTela().iterator();
		while (iteratorAbaVO.hasNext()) {
			AbaVO abaVO = iteratorAbaVO.next();
			abaVO.setChecada(this.checadoTodos);
		}		
	}
	
	public void checarTodosCheckbox(){
		Iterator<AbaVO> iteratorAbaVO = listaAbaVO.iterator();
		while (iteratorAbaVO.hasNext()) {
			AbaVO abaVO = iteratorAbaVO.next();
			abaVO.setChecada(this.checadoTodos);
		}		
	}
	
	private void populaAbaVO() throws SQLException, Exception{
		AbaVO abaVO = null;
		List<AbaVO> listaAbaVO = new ArrayList<AbaVO>();		
		if (this.listaAbaVO == null){
			for (Aba aba : this.telaVO.getListaAbasTela()) {
				abaVO = new AbaVO();
				abaVO.setIdAba(aba.getId());
				abaVO.setNomeI18nAba(aba.getNomeI18n());
				abaVO.setDescricaoI18nAba(aba.getDescricaoI18n());
				listaAbaVO.add(abaVO);
			}
			Collections.sort(listaAbaVO);
			this.listaAbaVO = new ListDataModel<AbaVO>(listaAbaVO);
		}
	}
	
	public void populaAbaVOTemporaria() throws SQLException, Exception{
		this.telaVO = (TelaVO) ManagedBeanUtil.getSession(Boolean.FALSE).getAttribute(AtributoSessaoEnum.TELA_VO.getAtributo());		
		this.populaAbaVO();	
		this.listaTemporariaAbaVO = new ArrayList<>();
		AbaVO abaVOTemporaria = null;
		for (AbaVO abaVO : this.telaVO.getListaAbasVOTela()) {
			abaVOTemporaria = new AbaVO();
			abaVOTemporaria.setIdAba(abaVO.getIdAba());
			abaVOTemporaria.setChecada(abaVO.getChecada());
			listaTemporariaAbaVO.add(abaVOTemporaria);
		}	
	}
	
	private void populaTelaVO() throws SQLException, Exception{
		TelaVO telaVO = null;
		List<TelaVO> listaTelaVO = new ArrayList<TelaVO>();
		for (Tela tela : this.grupoUsuario.getListaTela()) {
			telaVO = new TelaVO();
			telaVO.setIdTela(tela.getId());
			telaVO.setDescricaoI18nTela(tela.getDescricaoI18n());
			telaVO.setNomeI18nTela(tela.getNomeI18n());
			telaVO.setNomeI18nModulo(this.moduloDAO.buscarPorId(tela.getIdModulo()).getNomeI18n());
			telaVO.setListaAbasTela(new ArrayList<Aba>(tela.getListaAbas()));
			telaVO.setListaComponentesTela(new ArrayList<Componente>(tela.getListaComponentes()));
			listaTelaVO.add(telaVO);
		}
		Collections.sort(listaTelaVO);
		this.listaTelaVO = new ListDataModel<TelaVO>(listaTelaVO);
	}
	
	public String voltarVisualizarGrupoUsuarioTela(){		
		return "voltar";
	}
	
	public String cancelarVisualizarGrupoUsuarioTela(){
		return "cancelar";
	}

	public Tela getTela() {
		return tela;
	}

	public void setTela(Tela tela) {
		this.tela = tela;
	}

	public GrupoUsuario getGrupoUsuario() {
		return grupoUsuario;
	}

	public void setGrupoUsuario(GrupoUsuario grupoUsuario) {
		this.grupoUsuario = grupoUsuario;
	}

	public ListDataModel<TelaVO> getListaTelaVO() {
		return listaTelaVO;
	}

	public void setListaTelaVO(ListDataModel<TelaVO> listaTelaVO) {
		this.listaTelaVO = listaTelaVO;
	}

	public ListDataModel<AbaVO> getListaAbaVO() {
		return listaAbaVO;
	}

	public void setListaAbaVO(ListDataModel<AbaVO> listaAbaVO) {
		this.listaAbaVO = listaAbaVO;
	}

	public TelaVO getTelaVO() {
		return telaVO;
	}

	public void setTelaVO(TelaVO telaVO) {
		this.telaVO = telaVO;
	}

	public Boolean getChecadoTodos() {
		return checadoTodos;
	}

	public void setChecadoTodos(Boolean checadoTodos) {
		this.checadoTodos = checadoTodos;
	}		
	
	
	

}
