package br.com.condominiosvirtuais.persistence;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import br.com.condominiosvirtuais.entity.Garagem;
import br.com.condominiosvirtuais.exception.BusinessException;

public interface GaragemDAO {
	
	public abstract void salvar(Garagem garagem) throws SQLException, BusinessException, Exception;
	
	public abstract void excluir(Garagem garagem) throws SQLException, BusinessException, Exception;
	
	public abstract void excluirPorIdUnidade(Integer IdUnidade, Connection con) throws SQLException, Exception;
	
	public abstract void atualizar(Garagem garagem) throws SQLException, BusinessException, Exception;
	
	public abstract List<Garagem> buscarPorIdUnidade(Integer idUnidade) throws SQLException, Exception;
	
	public abstract List<Garagem> buscarPorIdUnidade(Integer idUnidade, Connection con) throws SQLException, Exception;
	
	public abstract Garagem buscarPorId(Integer idGaragem, Connection con) throws SQLException, Exception;
	
	public abstract List<Garagem> buscarPorIdCondominioENumero(Integer idCondominio, String numero) throws SQLException, Exception;

}
