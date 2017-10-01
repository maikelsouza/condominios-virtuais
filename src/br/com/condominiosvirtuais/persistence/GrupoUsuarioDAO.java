package br.com.condominiosvirtuais.persistence;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import br.com.condominiosvirtuais.entity.GrupoUsuario;

public interface GrupoUsuarioDAO {
	
	public abstract GrupoUsuario buscarPorIdUsuario(Integer idUsuario) throws SQLException, Exception;
	
	public abstract void excluir(Integer idGrupoUsuario) throws SQLException, Exception;
	
	public abstract void salvar(GrupoUsuario grupoUsuario) throws SQLException, Exception;
	
	public abstract void atualizar(GrupoUsuario grupoUsuario) throws SQLException, Exception;
	
	public abstract List<GrupoUsuario> buscarPorIdCondominioESituacao(Integer idCondominio, Boolean situacao) throws SQLException, Exception;
	
	public abstract List<GrupoUsuario> buscarPorIdCondominio(Integer idCondominio) throws SQLException, Exception;
	
	public abstract List<GrupoUsuario> buscarPorIdCondominioEPadraoETipoUsuarioESituacao(Integer idCondominio, Boolean padrao, Integer tipoUsuario, Boolean situacao) throws SQLException, Exception;
	
	public abstract GrupoUsuario buscarPorPadraoETipoUsuarioESituacao(Boolean padrao, Integer tipoUsuario, Boolean situacao) throws SQLException, Exception;
	
	public abstract List<GrupoUsuario> buscarPorIdEscritorioDeContabilidadeEPadraoETipoUsuarioESituacao(Integer idContador, Boolean padrao,
			Integer tipoUsuario, Boolean situacao) throws SQLException, Exception;
	
	public abstract List<GrupoUsuario> buscarPorIdSindicoProfissionalEPadraoETipoUsuarioESituacao(Integer idSindicoProfissional, Boolean padrao,
			Integer tipoUsuario, Boolean situacao, Connection con) throws SQLException, Exception;
	
	public abstract GrupoUsuario buscarPorId(Integer idCondominio, Connection con, Boolean situacao) throws SQLException, Exception;
	
//TODO: Código comentado em 27/09/2017. Apagar em 180 dias	
//	public abstract List<GrupoUsuario> buscarPorIdUsuarioESituacao(Integer idUsuario, Boolean situacao) throws SQLException, Exception;
	
	public abstract List<GrupoUsuario> buscarGruposFixosTipoUsuarioSindicoCondominoEFuncionarioEPadraoESituacao(List<Integer> listaTipoUsuarioSindicoCondominoEFuncionario, Boolean padrao,
			Boolean situacao) throws SQLException, Exception;
	
	public abstract List<GrupoUsuario> buscarGruposFixosTipoUsuarioSindicoCondominoEFuncionarioEPadrao(List<Integer> listaTipoUsuarioSindicoCondominoEFuncionario, Boolean padrao) throws SQLException, Exception;


}
