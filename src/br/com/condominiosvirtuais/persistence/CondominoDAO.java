package br.com.condominiosvirtuais.persistence;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import br.com.condominiosvirtuais.entity.Condominio;
import br.com.condominiosvirtuais.entity.Condomino;
import br.com.condominiosvirtuais.entity.Usuario;

public interface CondominoDAO {
	
	public abstract List<Integer> buscarListaIdsCondominosPorIdUnidade(Integer idUnidade) throws SQLException, Exception;
	
	public abstract Usuario buscarSindicoGeralPorCondominio(Condominio condominio, Connection con) throws SQLException, Exception;
	
	public abstract List<Condomino> buscarPorIdUnidadeEPagadorSemImagem(Integer idUnidade, Integer pagador) throws SQLException, Exception;
	
	public abstract Condomino buscarCondominoPorIdSemImagem(Integer idCondomino, Connection con) throws SQLException, Exception;
	
	public abstract List<Condomino>buscarPorIdUnidadeESituacaoSemImagem(Integer idUnidade,  Integer situacao) throws SQLException, Exception;
	
	public abstract List<Condomino>buscarPorIdUnidadeEidGrupoUsuarioESituacaoSemImagem(Integer idUnidade, Integer idGrupoUsuario, Integer situacao) throws SQLException, Exception;

}
