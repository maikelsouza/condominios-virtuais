package br.com.condominiosvirtuais.persistence.impl;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import br.com.condominiosvirtuais.entity.Despesa;
import br.com.condominiosvirtuais.persistence.DespesaDAO;
import br.com.condominiosvirtuais.util.SQLUtil;

public class DespesaDAOImpl implements DespesaDAO, Serializable {
	
private static final long serialVersionUID = 1L;
	
	private static Logger logger = Logger.getLogger(DespesaDAOImpl.class);
	
	private static final String DESPESA = "DESPESA";
	
	private static final String ID = "ID";
	
	private static final String NOME = "NOME";
	
	private static final String VALOR = "VALOR";
	
	private static final String MES_ANO_REFERENCIA = "MES_ANO_REFERENCIA";
	
	private static final String ID_CONDOMINIO = "ID_CONDOMINIO";
	
	
	@Override
	public void salvarDespesa(Despesa despesa) throws SQLException, Exception {
		StringBuffer query = new StringBuffer();
		query.append("INSERT INTO "); 
		query.append(DESPESA);
		query.append("(");
		query.append(NOME);
		query.append(",");
		query.append(VALOR);
		query.append(",");
		query.append(MES_ANO_REFERENCIA);
		query.append(",");	
		query.append(ID_CONDOMINIO);
		query.append(") ");
		query.append("VALUES(?,?,?,?)");
		PreparedStatement statement = null;		
		Connection con = Conexao.getConexao();
		try {
			statement = con.prepareStatement(query.toString());
			SQLUtil.setValorPpreparedStatement(statement, 1, despesa.getNome(), java.sql.Types.VARCHAR);			
			SQLUtil.setValorPpreparedStatement(statement, 2, despesa.getValor(), java.sql.Types.DOUBLE);
			SQLUtil.setValorPpreparedStatement(statement, 3, despesa.getMesAnoReferencia(), java.sql.Types.DATE);			
			SQLUtil.setValorPpreparedStatement(statement, 4, despesa.getIdCondominio(), java.sql.Types.INTEGER);			
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
	public List<Despesa> pesquisarPorMesAnoReferenciaEIdCondominio(Date mesAnoReferencia, Integer idCondominio) throws SQLException, Exception {
		StringBuffer query = new StringBuffer();
		query.append("SELECT * FROM ");
		query.append(DESPESA);
		query.append(" WHERE ");
		query.append(MES_ANO_REFERENCIA);
		query.append(" = ?");
		query.append(" AND ");
		query.append(ID_CONDOMINIO);
		query.append(" = ?");
		query.append(" ORDER BY ");
		query.append(NOME);
		query.append(";");
		Connection con = Conexao.getConexao();
		PreparedStatement preparedStatement = null;		
		List<Despesa> listaDespesa = new ArrayList<Despesa>();
		try {
			preparedStatement = con.prepareStatement(query.toString());
			SQLUtil.setValorPpreparedStatement(preparedStatement, 1, mesAnoReferencia, java.sql.Types.DATE);
			SQLUtil.setValorPpreparedStatement(preparedStatement, 2, idCondominio, java.sql.Types.INTEGER);
			ResultSet resultSet = preparedStatement.executeQuery();
			Despesa despesa = null;
			while(resultSet.next()){
				despesa = new Despesa();								
				despesa.setId((Integer) SQLUtil.getValorResultSet(resultSet, ID, java.sql.Types.INTEGER));				
				despesa.setNome(String.valueOf(SQLUtil.getValorResultSet(resultSet, NOME, java.sql.Types.VARCHAR)));
				despesa.setValor((Double) (SQLUtil.getValorResultSet(resultSet, VALOR, java.sql.Types.DOUBLE)));
				despesa.setMesAnoReferencia((Date) SQLUtil.getValorResultSet(resultSet, MES_ANO_REFERENCIA, java.sql.Types.DATE));
				despesa.setIdCondominio((Integer) SQLUtil.getValorResultSet(resultSet, ID_CONDOMINIO, java.sql.Types.INTEGER));
				listaDespesa.add(despesa);
			}
		} catch (NumberFormatException e) {
			throw e;
		} catch (SQLException e) {					
			throw e;
		} catch (Exception e) {					
			throw e;	
		}finally{
			try{				
				preparedStatement.close();
				con.close();				
			}catch (SQLException e) {
				logger.error("erro sqlstate "+e.getSQLState(), e);
		    }
		}
		return listaDespesa;
	}
	

}
