package br.com.condominiosvirtuais.service.impl;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

import javax.inject.Inject;

import br.com.condominiosvirtuais.entity.GrupoUsuario;
import br.com.condominiosvirtuais.exception.BusinessException;
import br.com.condominiosvirtuais.persistence.GrupoUsuarioDAO;
import br.com.condominiosvirtuais.service.GrupoUsuarioService;

public class GrupoUsuarioServiceImpl implements GrupoUsuarioService, Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Inject
	private GrupoUsuarioDAO grupoUsuarioDAO = null;
	
	public GrupoUsuario buscarPorIdUsuario(Integer idUsuario) throws SQLException, Exception {		
		return this.grupoUsuarioDAO.buscarPorIdUsuario(idUsuario);
	}

	public GrupoUsuarioDAO getGrupoUsuarioDAO() {
		return grupoUsuarioDAO;
	}

	public void setGrupoUsuarioDAO(GrupoUsuarioDAO grupoUsuarioDAO) {
		this.grupoUsuarioDAO = grupoUsuarioDAO;
	}

	@Override
	public List<GrupoUsuario> buscarPorIdCondominioESituacao(Integer idCondominio, Boolean situacao)
			throws SQLException, Exception {
		return this.grupoUsuarioDAO.buscarPorIdCondominioESituacao(idCondominio, situacao);
	}

	@Override
	public List<GrupoUsuario> buscarPorIdCondominio(Integer idCondominio) throws SQLException, Exception {
		return this.grupoUsuarioDAO.buscarPorIdCondominio(idCondominio);
	}

	@Override
	public void salvar(GrupoUsuario grupoUsuario) throws SQLException, Exception {
		this.grupoUsuarioDAO.salvar(grupoUsuario);		
	}

	@Override
	public void excluir(Integer idGrupoUsuario) throws SQLException, BusinessException, Exception {
		this.grupoUsuarioDAO.excluir(idGrupoUsuario);		
	}

	@Override
	public void atualizar(GrupoUsuario grupoUsuario) throws SQLException, Exception {
		this.grupoUsuarioDAO.atualizar(grupoUsuario);		
	}

	@Override
	public List<GrupoUsuario> buscarPorIdCondominioEPadraoETipoUsuarioESituacao(Integer idCondominio, Boolean padrao,
			Integer tipoUsuario, Boolean situacao) throws SQLException, Exception {
		return this.grupoUsuarioDAO.buscarPorIdCondominioEPadraoETipoUsuarioESituacao(idCondominio, padrao, tipoUsuario, situacao);
	}

	@Override
	public GrupoUsuario buscarPorPadraoETipoUsuarioESituacao(Boolean padrao, Integer tipoUsuario, Boolean situacao)
			throws SQLException, Exception {
		return this.grupoUsuarioDAO.buscarPorPadraoETipoUsuarioESituacao(padrao, tipoUsuario, situacao);
	}

	@Override
	public List<GrupoUsuario> buscarPorIdEscritorioDeContabilidadeEPadraoETipoUsuarioESituacao(
			Integer idEscritorioDeContabilidade, Boolean padrao, Integer tipoUsuario, Boolean situacao)
			throws SQLException, Exception {
		return this.grupoUsuarioDAO.buscarPorIdEscritorioDeContabilidadeEPadraoETipoUsuarioESituacao(idEscritorioDeContabilidade, padrao, tipoUsuario, situacao);
	}

// TODO: Código comentado em 27/09/2017. Apagar em 180 dias	
//	@Override
//	public List<GrupoUsuario> buscarPorIdUsuarioESituacao(Integer idUsuario, Boolean situacao)
//			throws SQLException, Exception {
//		return this.grupoUsuarioDAO.buscarPorIdUsuarioESituacao(idUsuario, situacao);
//	}	

}
