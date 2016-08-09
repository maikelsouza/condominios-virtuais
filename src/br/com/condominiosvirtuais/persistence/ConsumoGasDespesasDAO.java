package br.com.condominiosvirtuais.persistence;

import java.sql.Connection;
import java.sql.SQLException;

import br.com.condominiosvirtuais.entity.ConsumoGasDespesas;

public interface ConsumoGasDespesasDAO {
	
	public abstract void salvar(ConsumoGasDespesas consumoGasDespesas) throws SQLException, Exception;
	
	public abstract void salvar(ConsumoGasDespesas consumoGasDespesas, Connection con) throws SQLException, Exception;
	
	public abstract ConsumoGasDespesas buscarPorIdDespesa(Integer idDespesas) throws SQLException, Exception;
	
	public abstract ConsumoGasDespesas buscarPorIdDespesa(Integer idDespesas, Connection con) throws SQLException, Exception;
	
	public abstract void atualizar(ConsumoGasDespesas consumoGasDespesas, Connection con) throws SQLException, Exception;
	
	public abstract void excluir(ConsumoGasDespesas consumoGasDespesas, Connection con) throws SQLException, Exception;

}
