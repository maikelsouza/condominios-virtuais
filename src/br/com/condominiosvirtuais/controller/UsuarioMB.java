package br.com.condominiosvirtuais.controller;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.log4j.Logger;

import br.com.condominiosvirtuais.entity.Funcionario;
import br.com.condominiosvirtuais.entity.GrupoUsuario;
import br.com.condominiosvirtuais.entity.Usuario;
import br.com.condominiosvirtuais.enumeration.AtributoSessaoEnum;
import br.com.condominiosvirtuais.enumeration.UsuarioSituacaoEnum;
import br.com.condominiosvirtuais.enumeration.UsuarioVOTipoUsuarioEnum;
import br.com.condominiosvirtuais.service.CondominoService;
import br.com.condominiosvirtuais.service.FuncionarioService;
import br.com.condominiosvirtuais.service.UsuarioService;
import br.com.condominiosvirtuais.util.ManagedBeanUtil;
import br.com.condominiosvirtuais.vo.CondominoVO;
import br.com.condominiosvirtuais.vo.UsuarioVO;

@Named @SessionScoped 
public class UsuarioMB implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private static Logger logger = Logger.getLogger(UsuarioMB.class);
	
	private Usuario usuario;
	
	private GrupoUsuario grupoUsuario;
	
	@Inject
	private CondominoService condominoService;
	
	@Inject
	private FuncionarioService funcionarioService;	
	
	@Inject
	private UsuarioService usuarioService;
	
	private List<UsuarioVO> listaUsuariosAssociados;
	
	private List<UsuarioVO> listaOriginalUsuariosAssociados;
	
	private List<UsuarioVO> listaUsuariosNaoAssociados;

	
	
	
	public void buscarUsuariosAssociadosOuNao(){
		listaUsuariosAssociados = new ArrayList<UsuarioVO>();
		listaUsuariosNaoAssociados = new ArrayList<UsuarioVO>();
		listaOriginalUsuariosAssociados = new ArrayList<UsuarioVO>();
		this.grupoUsuario = (GrupoUsuario) ManagedBeanUtil.getSession(Boolean.FALSE).getAttribute(AtributoSessaoEnum.GRUPO_USUARIO.getAtributo());
		
		try{
			for (CondominoVO condominoVO : this.condominoService.buscarPorIdsEidGrupoUsuarioESituacaoSemImagem(grupoUsuario.getIdCondominio(), 
					grupoUsuario.getId(), UsuarioSituacaoEnum.ATIVO.getSituacao())) {
				listaUsuariosAssociados.add(this.popularUsuarioVO(condominoVO));
				listaOriginalUsuariosAssociados.add(this.popularUsuarioVO(condominoVO));
			}		
			for (CondominoVO condominoVO : this.condominoService.buscarPorIdsESituacaoSemImagem(grupoUsuario.getIdCondominio(), 
					UsuarioSituacaoEnum.ATIVO.getSituacao())) {
				listaUsuariosNaoAssociados.add(this.popularUsuarioVO(condominoVO));
			}		
			for (Funcionario funcionario : this.funcionarioService.buscarPorCondominioESituacaoSemImagem(grupoUsuario.getIdCondominio(),
					UsuarioSituacaoEnum.ATIVO.getSituacao())) {
				listaUsuariosNaoAssociados.add(this.popularUsuarioVO(funcionario));	
			}		
			for (Funcionario funcionario : this.funcionarioService.buscarPorIdCondominioEIdGrupoUsuarioESituacaoSemImagem(grupoUsuario.getIdCondominio(), 
					grupoUsuario.getId(),UsuarioSituacaoEnum.ATIVO.getSituacao())) {
				listaUsuariosAssociados.add(this.popularUsuarioVO(funcionario));	
				listaOriginalUsuariosAssociados.add(this.popularUsuarioVO(funcionario));
			}
			//listaUsuariossAssociados = new ArrayList<UsuarioVO>(listaUsuariosAssociados); 
			
			Collections.sort(this.listaUsuariosAssociados);
			Collections.sort(this.listaUsuariosNaoAssociados);
		} catch (SQLException e) {
			logger.error("erro sqlstate "+e.getSQLState(), e);	
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");				
		} catch (Exception e) {
			logger.error("", e);
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		}
		
		
	}
	
	private UsuarioVO popularUsuarioVO(CondominoVO condominoVO){
		UsuarioVO usuarioVO = new UsuarioVO();
		usuarioVO.setIdUsuario(condominoVO.getIdCondomino());
		usuarioVO.setNomeUsuario(condominoVO.getNomeCondomino().substring(0, condominoVO.getNomeCondomino().length() > 20 ? 20 : condominoVO.getNomeCondomino().length()));
		usuarioVO.setNumeroUnidade(condominoVO.getNumeroUnidade());
		usuarioVO.setNomeBloco(condominoVO.getNomeBloco());
		usuarioVO.setTipoUsuario(UsuarioVOTipoUsuarioEnum.CONDOMINO.getTipoUsuario());
		return usuarioVO;
	}
	
	private UsuarioVO popularUsuarioVO(Funcionario funcionario){
		UsuarioVO usuarioVO = new UsuarioVO();
		usuarioVO.setIdUsuario(funcionario.getId());
		usuarioVO.setNomeUsuario(funcionario.getNome().substring(0, funcionario.getNome().length() > 20 ? 20 : funcionario.getNome().length()));
		usuarioVO.setTipoUsuario(UsuarioVOTipoUsuarioEnum.FUNCIONARIO.getTipoUsuario());
		return usuarioVO;
	}
	
	public String associarUsuario(){	
		try {
			List<Usuario> listaUsuariosAssociados = new ArrayList<Usuario>();
			List<Usuario> listaOriginalUsuariosAssociados = new ArrayList<Usuario>();
			//if(!this.listaUsuariosAssociados.isEmpty()){
				for (UsuarioVO usuarioVO : this.listaUsuariosAssociados) {
					listaUsuariosAssociados.add(this.popularUsuario(usuarioVO));
				}		
				for (UsuarioVO usuarioVO : this.listaOriginalUsuariosAssociados) {
					listaOriginalUsuariosAssociados.add(this.popularUsuario(usuarioVO));
				}		
				this.usuarioService.associar(listaUsuariosAssociados,listaOriginalUsuariosAssociados, this.grupoUsuario.getId());
				ManagedBeanUtil.setMensagemInfo("msg.usuario.associadoSucesso");
//			}else if (!this.listaOriginalUsuariosAssociados.isEmpty()){
//				for (UsuarioVO usuarioVO : this.listaOriginalUsuariosAssociados) {
//					listaUsuarios.add(this.popularUsuario(usuarioVO));
//				}		
//				this.usuarioService.desassociar(listaUsuarios, this.grupoUsuario.getId());
//				ManagedBeanUtil.setMensagemInfo("msg.usuario.desassociadoSucesso");				
//			}
			
		} catch (SQLException e) {
			logger.error("erro sqlstate "+e.getSQLState(), e);	
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");				
		} catch (Exception e) {
			logger.error("", e);
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		}
		return "associar";
	}
	
	private Usuario popularUsuario(UsuarioVO usuarioVO){
		Usuario usuario = new Usuario();
		usuario.setId(usuarioVO.getIdUsuario());
		return usuario;
	}
	
	public String cancelarAssociarUsuario(){
		return "cancelar";
	}
	
	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<UsuarioVO> getListaUsuariosAssociados() {
		return listaUsuariosAssociados;
	}

	public void setListaUsuariosAssociados(List<UsuarioVO> listaUsuariosAssociados) {
		this.listaUsuariosAssociados = listaUsuariosAssociados;
	}

	public List<UsuarioVO> getListaUsuariosNaoAssociados() {
		return listaUsuariosNaoAssociados;
	}

	public void setListaUsuariosNaoAssociados(List<UsuarioVO> listaUsuariosNaoAssociados) {
		this.listaUsuariosNaoAssociados = listaUsuariosNaoAssociados;
	}
	
	
	

}
