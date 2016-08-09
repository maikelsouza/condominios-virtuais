package br.com.condominiosvirtuais.service;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import br.com.condominiosvirtuais.entity.Despesas;
import br.com.condominiosvirtuais.reports.DespesasUnidade;

public interface DespesasService {
	
	
	public abstract List<Despesas> buscarPorIdCondominioEMesAnoReferencia(Integer idCondominio, Date mesAnoReferencia) throws SQLException, Exception;
	
	public abstract List<Despesas> buscarPorIdUnidadeEMesAnoReferencia(Integer idUnidade, Date mesAnoReferencia) throws SQLException, Exception;
	
	public abstract void salvarDespesasCondominio(Despesas despesas) throws SQLException, Exception;
	
	public abstract void salvarDespesasCondomino(Despesas despesas) throws SQLException, Exception;
	
	public abstract void salvarDespesasGasCondomino(Despesas despesas, Double leituraAtual) throws SQLException, Exception;
	
	public void salvarDespesasGasCondomino(Despesas despesas) throws SQLException, Exception;
	
	public abstract void salvarDespesasUnicaCondominos(Despesas despesas) throws SQLException, Exception;	
	
	public abstract void salvarDespesasUnicaRateioPadraoCondominos(List<Despesas> listaDespesas) throws SQLException, Exception;
	
	public abstract void excluir(Despesas despesas) throws SQLException, Exception;
	
	public abstract void atualizarDespesasCondominio(Despesas despesas) throws SQLException, Exception;
	
	public abstract List<DespesasUnidade> buscarDespesaUnidadePorIdCondominioEMesAnoReferencia(Integer idCondominio, Date mesAnoReferencia) throws SQLException, Exception;
	
	public abstract Despesas buscarPorIdUnidadeEMesAnoReferenciaETipo(Integer idUnidade, Date mesAnoReferencia, Integer tipo) throws SQLException, Exception;	
	
	// TODO: Refer o nome desse método, pois esse atualiza a despesa da unidade e não do condômino
	public abstract void atualizarDespesasGasCondomino(Despesas despesas) throws SQLException, Exception;
	
	public abstract void atualizarDespesasCondomino(Despesas despesas) throws SQLException, Exception;

}
