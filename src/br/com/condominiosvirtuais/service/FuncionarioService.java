package br.com.condominiosvirtuais.service;

import java.sql.SQLException;
import java.util.List;

import br.com.condominiosvirtuais.entity.Bloco;
import br.com.condominiosvirtuais.entity.Condominio;
import br.com.condominiosvirtuais.entity.Funcionario;

public interface FuncionarioService {
	
	public abstract void salvar(Funcionario funcionario) throws SQLException, Exception;
	
	public abstract List<Funcionario> buscarPorCondominioENomeFuncionario(Condominio condominio, String nomeFuncionario) throws SQLException, Exception;
	
	public abstract void excluir(Funcionario funcionario) throws SQLException, Exception;
	
	public abstract void atualizar(Funcionario funcionario) throws SQLException, Exception;
	
	public abstract void atualizarSenha(Funcionario funcionario) throws SQLException, Exception;
	
	public abstract List<Funcionario> buscarPorBlocoENomeFuncionario(Bloco bloco, String nomeFuncionario) throws SQLException, Exception;
	
	public abstract List<Funcionario> buscarPorIdConjuntoBloco(Integer idConjuntoBloco) throws SQLException, Exception;
	
	public abstract Funcionario buscarPorId(Integer id) throws SQLException, Exception;
	
	public abstract List<Funcionario> buscarPorCondominioESituacaoSemImagem(Integer idCondominio, Integer situacao) throws SQLException, Exception;
	
	public abstract List<Funcionario> buscarPorIdCondominioEIdGrupoUsuarioESituacaoSemImagem(Integer idCondominio, Integer idGrupoUsuario, Integer situacao) throws SQLException, Exception;
	
	

}
