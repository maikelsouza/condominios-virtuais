package br.com.condominiosvirtuais.persistence.impl;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import br.com.condominiosvirtuais.entity.ConsumoGasDespesas;
import br.com.condominiosvirtuais.persistence.ConsumoGasDespesasDAO;
import br.com.condominiosvirtuais.util.SQLUtil;

public class ConsumoGasDespesasDAOImpl implements ConsumoGasDespesasDAO, Serializable {

	private static final long serialVersionUID = 1L;
	
	private static Logger logger = Logger.getLogger(ConsumoGasDespesasDAOImpl.class);	

	private static final String CONSUMO_GAS_DESPESAS = "CONSUMO_GAS_DESPESAS";

	private static final String ID = "ID";
	
	private static final String CONSUMO_MES_ATUAL = "CONSUMO_MES_ATUAL";
	
	private static final String ID_DESPESAS = "ID_DESPESAS";
	
	
	@Override
	public void excluir(ConsumoGasDespesas consumoGasDespesas, Connection con) throws SQLException, Exception {
		StringBuffer query = new StringBuffer();
		PreparedStatement statement = null;
		try {
			query.append("DELETE FROM "); 
			query.append(CONSUMO_GAS_DESPESAS);
			query.append(" WHERE ");
			query.append(ID);
			query.append(" = ? ");
			statement = con.prepareStatement(query.toString());
			SQLUtil.setValorPpreparedStatement(statement, 1, consumoGasDespesas.getId(), java.sql.Types.INTEGER);			
			statement.executeUpdate();
		} catch (SQLException e) {
			con.rollback();
			throw e;
		} catch (Exception e) {
			con.rollback();
			throw e;		
		}	
	}
	
	

	@Override
	public void salvar(ConsumoGasDespesas consumoGasDespesas) throws SQLException, Exception {
		StringBuffer query = new StringBuffer();
		query.append("INSERT INTO "); 
		query.append(CONSUMO_GAS_DESPESAS);
		query.append("("); 
		query.append(CONSUMO_MES_ATUAL);	
		query.append(",");
		query.append(ID_DESPESAS);		
		query.append(") ");
		query.append("VALUES(?,?)");
		PreparedStatement statement = null;		
		Connection con = Conexao.getConexao();
		try {
			statement = con.prepareStatement(query.toString());
			SQLUtil.setValorPpreparedStatement(statement, 1, consumoGasDespesas.getConsumoMesAtual(), java.sql.Types.DOUBLE);
			SQLUtil.setValorPpreparedStatement(statement, 2, consumoGasDespesas.getIdDespesas(), java.sql.Types.INTEGER);			
			statement.executeUpdate();
		} catch (SQLException e) {			
			throw e;
		} catch (Exception e) {					
			throw e;	
		}finally{
			try{				
				statement.close();
				con.close();				
			}catch (SQLException e) {
				logger.error("erro sqlstate "+e.getSQLState(), e);
		    }
		}	
	}
	
	public void salvar(ConsumoGasDespesas consumoGasDespesas, Connection con) throws SQLException, Exception {
		StringBuffer query = new StringBuffer();
		query.append("INSERT INTO "); 
		query.append(CONSUMO_GAS_DESPESAS);
		query.append("("); 
		query.append(CONSUMO_MES_ATUAL);	
		query.append(",");		
		query.append(ID_DESPESAS);		
		query.append(") ");
		query.append("VALUES(?,?)");
		PreparedStatement statement = null;
		try {
			statement = con.prepareStatement(query.toString());
			SQLUtil.setValorPpreparedStatement(statement, 1, consumoGasDespesas.getConsumoMesAtual(), java.sql.Types.DOUBLE);
			SQLUtil.setValorPpreparedStatement(statement, 2, consumoGasDespesas.getIdDespesas(), java.sql.Types.INTEGER);			
			statement.executeUpdate();
		} catch (SQLException e) {	
			con.rollback();
			throw e;
		} catch (Exception e) {		
			con.rollback();
			throw e;	
		}
	}
	
	@Override
	public void atualizar(ConsumoGasDespesas consumoGasDespesas, Connection con) throws SQLException, Exception {
		StringBuffer query = new StringBuffer();
		query.append("UPDATE "); 
		query.append(CONSUMO_GAS_DESPESAS);
		query.append(" SET ");
		query.append(CONSUMO_MES_ATUAL);
		query.append(" = ?,");
		query.append(ID_DESPESAS);
		query.append(" = ? ");
		query.append(" WHERE "); 
		query.append(ID);	
		query.append("= ?");
		PreparedStatement statement = null;
		try {
			statement = con.prepareStatement(query.toString());
			SQLUtil.setValorPpreparedStatement(statement, 1, consumoGasDespesas.getConsumoMesAtual(), java.sql.Types.DOUBLE);
			SQLUtil.setValorPpreparedStatement(statement, 2, consumoGasDespesas.getIdDespesas(), java.sql.Types.INTEGER);
			SQLUtil.setValorPpreparedStatement(statement, 3, consumoGasDespesas.getId(), java.sql.Types.INTEGER);
			statement.executeUpdate();
		} catch (SQLException e) {	
			con.rollback();
			throw e;
		} catch (Exception e) {		
			con.rollback();
			throw e;	
		}
	}
	
	@Override
	public ConsumoGasDespesas buscarPorIdDespesa(Integer idDespesas) throws SQLException, Exception {
		StringBuffer query = new StringBuffer();
		query.append("SELECT * FROM ");
		query.append(CONSUMO_GAS_DESPESAS);
		query.append(" WHERE ");		
		query.append(ID_DESPESAS);
		query.append(" = ?");		
		query.append(";");		
		Connection con = Conexao.getConexao();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;		
		ConsumoGasDespesas consumoGasDespesas = new ConsumoGasDespesas();
		try {
			preparedStatement = con.prepareStatement(query.toString());			
			SQLUtil.setValorPpreparedStatement(preparedStatement, 1, idDespesas, java.sql.Types.INTEGER);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()){				
				consumoGasDespesas.setId((Integer) SQLUtil.getValorResultSet(resultSet, ID, java.sql.Types.INTEGER));								
				consumoGasDespesas.setConsumoMesAtual((Double) SQLUtil.getValorResultSet(resultSet, CONSUMO_MES_ATUAL, java.sql.Types.DOUBLE));
				consumoGasDespesas.setIdDespesas((Integer) SQLUtil.getValorResultSet(resultSet, ID_DESPESAS, java.sql.Types.INTEGER));
			}
		} catch (SQLException e) {					
			throw e;
		} catch (Exception e) {					
			throw e;	
		}finally{
			try {
				preparedStatement.close();
				con.close();				
			} catch (SQLException e) {
				logger.error("erro sqlstate "+e.getSQLState(), e);
			}
		}	
		return consumoGasDespesas;			
	}

	@Override
	public ConsumoGasDespesas buscarPorIdDespesa(Integer idDespesas, Connection con) throws SQLException, Exception {
		StringBuffer query = new StringBuffer();
		query.append("SELECT * FROM ");
		query.append(CONSUMO_GAS_DESPESAS);
		query.append(" WHERE ");		
		query.append(ID_DESPESAS);
		query.append(" = ?");		
		query.append(";");
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;		
		ConsumoGasDespesas consumoGasDespesas = new ConsumoGasDespesas();
		try {
			preparedStatement = con.prepareStatement(query.toString());			
			SQLUtil.setValorPpreparedStatement(preparedStatement, 1, idDespesas, java.sql.Types.INTEGER);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()){				
				consumoGasDespesas.setId((Integer) SQLUtil.getValorResultSet(resultSet, ID, java.sql.Types.INTEGER));								
				consumoGasDespesas.setConsumoMesAtual((Double) SQLUtil.getValorResultSet(resultSet, CONSUMO_MES_ATUAL, java.sql.Types.DOUBLE));
				consumoGasDespesas.setIdDespesas((Integer) SQLUtil.getValorResultSet(resultSet, ID_DESPESAS, java.sql.Types.INTEGER));
			}
		} catch (SQLException e) {
			con.rollback();
			throw e;
		} catch (Exception e) {
			con.rollback();
			throw e;	
		}			
		return consumoGasDespesas;			
	}

}
