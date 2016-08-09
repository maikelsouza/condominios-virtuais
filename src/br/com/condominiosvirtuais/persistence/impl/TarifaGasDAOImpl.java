package br.com.condominiosvirtuais.persistence.impl;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.inject.Inject;

import org.apache.log4j.Logger;

import br.com.condominiosvirtuais.entity.TarifaGas;
import br.com.condominiosvirtuais.persistence.TarifaGasDAO;
import br.com.condominiosvirtuais.util.SQLUtil;

public class TarifaGasDAOImpl implements TarifaGasDAO, Serializable {

	private static final long serialVersionUID = 1L;

	private static Logger logger = Logger.getLogger(TarifaGasDAOImpl.class);
	
	private static final String TARIFA_GAS = "TARIFA_GAS";

	private static final String ID = "ID";
	
	private static final String  FATOR_CONVERSAO = "FATOR_CONVERSAO";
	
	private static final String  VALOR_COBRADO = "VALOR_COBRADO";
	
	private static final String  ID_CONDOMINIO = "ID_CONDOMINIO";
	
	@Inject
	private CondominioDAOImpl condominioDAO;

	@Override
	public TarifaGas buscarPorIdCondominio(Integer idCondominio) throws SQLException, Exception {		
		StringBuffer query = new StringBuffer();
		query.append("SELECT * FROM ");
		query.append(TARIFA_GAS);
		query.append(" WHERE ");		
		query.append(ID_CONDOMINIO);
		query.append(" = ?");		
		query.append(";");		
		Connection con = Conexao.getConexao();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;		
		TarifaGas tarifaGas = null;
		try {
			preparedStatement = con.prepareStatement(query.toString());			
			SQLUtil.setValorPpreparedStatement(preparedStatement, 1, idCondominio, java.sql.Types.INTEGER);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()){
				tarifaGas = new TarifaGas();
				tarifaGas.setId((Integer) SQLUtil.getValorResultSet(resultSet, ID, java.sql.Types.INTEGER));
				tarifaGas.setFatorConversao((Double) SQLUtil.getValorResultSet(resultSet, FATOR_CONVERSAO, java.sql.Types.DOUBLE));		
				tarifaGas.setValorCobrado((Double) SQLUtil.getValorResultSet(resultSet, VALOR_COBRADO, java.sql.Types.DOUBLE));
				tarifaGas.setCondominio(this.condominioDAO.buscarCondominioPorId((Integer) SQLUtil.getValorResultSet(resultSet, ID, java.sql.Types.INTEGER), con));				
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
		return tarifaGas;	
	}
}
