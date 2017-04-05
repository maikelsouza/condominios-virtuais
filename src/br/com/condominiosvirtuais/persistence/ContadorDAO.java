package br.com.condominiosvirtuais.persistence;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import br.com.condominiosvirtuais.entity.Contador;
import br.com.condominiosvirtuais.exception.BusinessException;

public interface ContadorDAO {

	public abstract void salvar(Contador contador) throws SQLException, BusinessException, Exception;
	
	public abstract Contador buscarPorId(Integer id) throws SQLException, Exception;
	
	public abstract List<Contador> buscarPorIdEscritorioContabilidade(Integer idEscritorioContabilidade, Connection con) throws SQLException, Exception;
	
	public abstract List<Contador> buscarPorIdEscritorioContabilidadeESituacao(Integer idEscritorioContabilidade, Integer situacao) throws SQLException, Exception;
	
	public abstract void atualizar(Contador contador) throws SQLException, Exception;
	
}
