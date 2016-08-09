package br.com.condominiosvirtuais.service;

import java.sql.SQLException;
import java.util.List;

import br.com.condominiosvirtuais.entity.Garagem;
import br.com.condominiosvirtuais.exception.BusinessException;

public interface GaragemService {
	
	public abstract void salvar(Garagem garagem) throws SQLException, Exception;
	
	public abstract void excluir(Garagem garagem) throws SQLException, BusinessException,  Exception;
	
	public abstract void atualizar(Garagem garagem) throws SQLException, BusinessException, Exception;
	
	public abstract List<Garagem> buscarPorIdUnidade(Integer idUnidade) throws SQLException, Exception;
	
	public abstract List<Garagem> buscarPorIdCondominoENumero(Integer idCondominio, String numero) throws SQLException, Exception;

}
