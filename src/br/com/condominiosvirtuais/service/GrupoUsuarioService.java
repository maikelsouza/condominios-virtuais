package br.com.condominiosvirtuais.service;

import java.sql.SQLException;
import java.util.List;

import br.com.condominiosvirtuais.entity.GrupoUsuario;
import br.com.condominiosvirtuais.exception.BusinessException;

public interface GrupoUsuarioService {
	
	public abstract GrupoUsuario buscarPorIdUsuario(Integer idUsuario) throws SQLException, Exception;
	
	public abstract List<GrupoUsuario> buscarPorIdCondominioESituacao(Integer idCondominio, Boolean situacao) throws SQLException, Exception;
	
	public abstract List<GrupoUsuario> buscarPorIdCondominio(Integer idCondominio) throws SQLException, Exception;
	
	public abstract void salvar(GrupoUsuario grupoUsuario) throws SQLException, Exception;
	
	public abstract void excluir(Integer idGrupoUsuario) throws SQLException, BusinessException, Exception;
	
	public abstract void atualizar(GrupoUsuario grupoUsuario) throws SQLException, Exception;
	
	public abstract List<GrupoUsuario> buscarPorIdCondominioEPadraoETipoUsuarioESituacao(Integer idCondominio, Boolean padrao, Integer tipoUsuario, Boolean situacao) throws SQLException, Exception;
	
	public abstract List<GrupoUsuario> buscarPorIdEscritorioDeContabilidadeEPadraoETipoUsuarioESituacao(Integer idEscritorioDeContabilidade, Boolean padrao, Integer tipoUsuario, Boolean situacao) throws SQLException, Exception;
	
	public abstract GrupoUsuario buscarPorPadraoETipoUsuarioESituacao(Boolean padrao, Integer tipoUsuario, Boolean situacao) throws SQLException, Exception;
	
//	TODO: Código comentado em 27/09/2017. Apagar em 180 dias	
//	public abstract List<GrupoUsuario> buscarPorIdUsuarioESituacao(Integer idUsuario, Boolean situacao) throws SQLException, Exception;

}
