package br.com.condominiosvirtuais.controller;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.log4j.Logger;

import br.com.condominiosvirtuais.entity.Funcionario;
import br.com.condominiosvirtuais.entity.Usuario;
import br.com.condominiosvirtuais.enumeration.UsuarioSituacaoEnum;
import br.com.condominiosvirtuais.enumeration.UsuarioVOTipoUsuarioEnum;
import br.com.condominiosvirtuais.service.CondominoService;
import br.com.condominiosvirtuais.service.FuncionarioService;
import br.com.condominiosvirtuais.vo.CondominoVO;
import br.com.condominiosvirtuais.vo.UsuarioVO;

@Named @SessionScoped 
public class UsuarioMB implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private static Logger logger = Logger.getLogger(UsuarioMB.class);
	
	private Usuario usuario;
	
	@Inject
	private CondominoService condominoService;
	
	@Inject
	private FuncionarioService funcionarioService;	
	
	private List<UsuarioVO> listaUsuariosAssociados;
	
	private List<UsuarioVO> listaUsuariosNaoAssociados;

	
	@PostConstruct
	public void iniciarUsuarioMB() throws SQLException, Exception{
		listaUsuariosAssociados = new ArrayList<UsuarioVO>();
		listaUsuariosNaoAssociados = new ArrayList<UsuarioVO>();
		
		for (CondominoVO condominoVO : this.condominoService.buscarPorIdsEidGrupoUsuarioESituacaoSemImagem(19, 1, 1)) {
			listaUsuariosAssociados.add(this.popularUsuarioVO(condominoVO));
		}		
		for (CondominoVO condominoVO : this.condominoService.buscarPorIdsESituacaoSemImagem(19, 1)) {
			listaUsuariosNaoAssociados.add(this.popularUsuarioVO(condominoVO));
		}		
		for (Funcionario funcionario : this.funcionarioService.buscarPorCondominioESituacaoSemImagem(19,1)) {
			listaUsuariosNaoAssociados.add(this.popularUsuarioVO(funcionario));	
		}		
		for (Funcionario funcionario : this.funcionarioService.buscarPorIdCondominioEIdGrupoUsuarioESituacaoSemImagem(19,1,1)) {
			listaUsuariosAssociados.add(this.popularUsuarioVO(funcionario));	
		}
		
		Collections.sort(this.listaUsuariosAssociados);
		Collections.sort(this.listaUsuariosNaoAssociados);
	}
	
	private UsuarioVO popularUsuarioVO(CondominoVO condominoVO){
		UsuarioVO usuarioVO = new UsuarioVO();
		usuarioVO.setIdUsuario(condominoVO.getId());
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
