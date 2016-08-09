package br.com.condominiosvirtuais.persistence.impl;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.apache.log4j.Logger;

import br.com.condominiosvirtuais.entity.ConsumoGasUnidade;
import br.com.condominiosvirtuais.persistence.ConsumoGasUnidadeDAO;
import br.com.condominiosvirtuais.util.SQLUtil;

public class ConsumoGasUnidadeDAOImpl implements ConsumoGasUnidadeDAO, Serializable {

	private static final long serialVersionUID = 1L;
	
	private static Logger logger = Logger.getLogger(ConsumoGasUnidadeDAOImpl.class);	

	private static final String CONSUMO_GAS_UNIDADE = "CONSUMO_GAS_UNIDADE";

	private static final String ID = "ID";
	
	private static final String CONSUMO_MES_ATUAL = "CONSUMO_MES_ATUAL";
	
	private static final String MES_ANO_REFERENCIA = "MES_ANO_REFERENCIA";
	
	private static final String ID_UNIDADE = "ID_UNIDADE";
	
	@Inject
	private UnidadeDAOImpl unidadaeDAO;
	
	@Override
	public List<ConsumoGasUnidade> buscarPorMesAnoReferencia(Date mesAnoReferencia) throws SQLException, Exception {
		StringBuffer query = new StringBuffer();
		query.append("SELECT * FROM ");
		query.append(CONSUMO_GAS_UNIDADE);
		query.append(" WHERE ");		
		query.append(MES_ANO_REFERENCIA);
		query.append(" = ?");		
		query.append(";");		
		Connection con = Conexao.getConexao();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		List<ConsumoGasUnidade> listaConsumoGasUnidade = new ArrayList<ConsumoGasUnidade>();
		ConsumoGasUnidade consumoGasUnidade = null;
		try {
			preparedStatement = con.prepareStatement(query.toString());			
			SQLUtil.setValorPpreparedStatement(preparedStatement, 1, mesAnoReferencia, java.sql.Types.DATE);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()){
				consumoGasUnidade = new ConsumoGasUnidade();
				consumoGasUnidade.setId((Integer) SQLUtil.getValorResultSet(resultSet, ID, java.sql.Types.INTEGER));
				consumoGasUnidade.setMesAnoReferencia((Date) SQLUtil.getValorResultSet(resultSet, MES_ANO_REFERENCIA, java.sql.Types.DATE));				
				consumoGasUnidade.setConsumoMesAtual((Double) SQLUtil.getValorResultSet(resultSet, CONSUMO_MES_ATUAL, java.sql.Types.DOUBLE));
				consumoGasUnidade.setUnidade(this.unidadaeDAO.buscarPorId((Integer) SQLUtil.getValorResultSet(resultSet, ID, java.sql.Types.INTEGER),con));
				listaConsumoGasUnidade.add(consumoGasUnidade);
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
		return listaConsumoGasUnidade;			
	}
	
	
	@Override
	public ConsumoGasUnidade buscarPorMesAnoReferenciaEIdUnidade(Date mesAnoReferencia, Integer idUnidade) throws SQLException, Exception {
		StringBuffer query = new StringBuffer();
		query.append("SELECT * FROM ");
		query.append(CONSUMO_GAS_UNIDADE);
		query.append(" WHERE ");		
		query.append(MES_ANO_REFERENCIA);
		query.append(" = ?");
		query.append(" AND");
		query.append(ID_UNIDADE);
		query.append(" = ?");		
		query.append(";");		
		Connection con = Conexao.getConexao();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;		
		ConsumoGasUnidade consumoGasUnidade = null;
		try {
			preparedStatement = con.prepareStatement(query.toString());			
			SQLUtil.setValorPpreparedStatement(preparedStatement, 1, mesAnoReferencia, java.sql.Types.DATE);
			SQLUtil.setValorPpreparedStatement(preparedStatement, 2, idUnidade, java.sql.Types.DATE);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()){
				consumoGasUnidade = new ConsumoGasUnidade();
				consumoGasUnidade.setId((Integer) SQLUtil.getValorResultSet(resultSet, ID, java.sql.Types.INTEGER));
				consumoGasUnidade.setMesAnoReferencia((Date) SQLUtil.getValorResultSet(resultSet, MES_ANO_REFERENCIA, java.sql.Types.DATE));				
				consumoGasUnidade.setConsumoMesAtual((Double) SQLUtil.getValorResultSet(resultSet, CONSUMO_MES_ATUAL, java.sql.Types.DOUBLE));
				consumoGasUnidade.setUnidade(this.unidadaeDAO.buscarPorId((Integer) SQLUtil.getValorResultSet(resultSet, ID, java.sql.Types.INTEGER),con));				
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
		return consumoGasUnidade;			
	}


	@Override
	public void salvar(ConsumoGasUnidade consumoGasUnidade) throws SQLException, Exception {
		StringBuffer query = new StringBuffer();
		query.append("INSERT INTO "); 
		query.append(CONSUMO_GAS_UNIDADE);
		query.append("("); 
		query.append(CONSUMO_MES_ATUAL);	
		query.append(",");
		query.append(MES_ANO_REFERENCIA);
		query.append(",");
		query.append(ID_UNIDADE);		
		query.append(") ");
		query.append("VALUES(?,?,?)");
		PreparedStatement statement = null;		
		Connection con = Conexao.getConexao();
		try {
			statement = con.prepareStatement(query.toString());
			SQLUtil.setValorPpreparedStatement(statement, 1, consumoGasUnidade.getConsumoMesAtual(), java.sql.Types.DOUBLE);			
			SQLUtil.setValorPpreparedStatement(statement, 2, consumoGasUnidade.getMesAnoReferencia(), java.sql.Types.DATE);
			SQLUtil.setValorPpreparedStatement(statement, 3, consumoGasUnidade.getUnidade().getId(), java.sql.Types.INTEGER);			
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


	@Override
	public void salvar(ConsumoGasUnidade consumoGasUnidade, Connection con) throws SQLException, Exception {
		StringBuffer query = new StringBuffer();
		query.append("INSERT INTO "); 
		query.append(CONSUMO_GAS_UNIDADE);
		query.append("("); 
		query.append(CONSUMO_MES_ATUAL);	
		query.append(",");
		query.append(MES_ANO_REFERENCIA);
		query.append(",");
		query.append(ID_UNIDADE);		
		query.append(") ");
		query.append("VALUES(?,?,?)");
		PreparedStatement statement = null;
		try {
			statement = con.prepareStatement(query.toString());
			SQLUtil.setValorPpreparedStatement(statement, 1, consumoGasUnidade.getConsumoMesAtual(), java.sql.Types.DOUBLE);			
			SQLUtil.setValorPpreparedStatement(statement, 2, consumoGasUnidade.getMesAnoReferencia(), java.sql.Types.DATE);
			SQLUtil.setValorPpreparedStatement(statement, 3, consumoGasUnidade.getUnidade().getId(), java.sql.Types.INTEGER);			
			statement.executeUpdate();
		} catch (SQLException e) {	
			con.rollback();
			throw e;
		} catch (Exception e) {		
			con.rollback();
			throw e;	
		}
	}

}
