package br.com.condominiosvirtuais.persistence;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import br.com.condominiosvirtuais.entity.Despesas;

public interface DespesasDAO {	
	
	public abstract void salvarDespesasCondomino(Despesas despesas) throws SQLException, Exception;
	
	public abstract void salvarDespesasCondominio(Despesas despesas) throws SQLException, Exception;
	
	public abstract void excluir(Despesas despesas) throws SQLException, Exception;
	
	public abstract void atualizarDespesasCondominio(Despesas despesas) throws SQLException, Exception;
	
	public abstract List<Despesas> buscarPorIdCondominioEMesAnoReferencia(Integer idCondominio, Date mesAnoReferencia) throws SQLException, Exception;
	
	public abstract List<Despesas> buscarPorIdUnidadeEMesAnoReferencia(Integer idUnidade, Date mesAnoReferencia) throws SQLException, Exception;
	
	public abstract Despesas buscarPorIdUnidadeEMesAnoReferenciaETipo(Integer idUnidade, Date mesAnoReferencia, Integer tipo) throws SQLException, Exception;
	
	public abstract void salvarDespesasCondominoGasUnidade(Despesas despesas) throws SQLException, Exception;
	
	public abstract void atualizarDespesasGasCondominio(Despesas despesas) throws SQLException, Exception;
	
	public abstract void atualizarDespesasCondomino(Despesas despesas) throws SQLException, Exception;
	
	// TODO: Refer o nome desse método, pois esse atualiza a despesa da unidade e não do condômino
	public abstract void atualizarDespesasGasCondomino(Despesas despesas) throws SQLException, Exception;

	
}
