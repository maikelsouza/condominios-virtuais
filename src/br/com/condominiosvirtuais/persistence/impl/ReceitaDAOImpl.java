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

import br.com.condominiosvirtuais.entity.Receita;
import br.com.condominiosvirtuais.persistence.ReceitaDAO;
import br.com.condominiosvirtuais.util.SQLUtil;

public class ReceitaDAOImpl implements ReceitaDAO, Serializable {

	private static final long serialVersionUID = 1L;
	
	private static Logger logger = Logger.getLogger(ReceitaDAOImpl.class);
	
	private static final String RECEITA = "RECEITA";
	
	private static final String ID = "ID";
	
	private static final String NOME = "NOME";
	
	private static final String VALOR = "VALOR";
	
	private static final String MES_ANO_REFERENCIA = "MES_ANO_REFERENCIA";
	
	private static final String ID_CONDOMINIO = "ID_CONDOMINIO";
	
	
	@Override
	public void salvarReceita(Receita receita) throws SQLException, Exception {
		StringBuffer query = new StringBuffer();
		query.append("INSERT INTO "); 
		query.append(RECEITA);
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
			SQLUtil.setValorPpreparedStatement(statement, 1, receita.getNome(), java.sql.Types.VARCHAR);			
			SQLUtil.setValorPpreparedStatement(statement, 2, receita.getValor(), java.sql.Types.DOUBLE);
			SQLUtil.setValorPpreparedStatement(statement, 3, receita.getMesAnoReferencia(), java.sql.Types.DATE);			
			SQLUtil.setValorPpreparedStatement(statement, 4, receita.getIdCondominio(), java.sql.Types.INTEGER);			
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
	public List<Receita> pesquisarPorMesAnoReferenciaEIdCondominio(Date mesAnoReferencia, Integer idCondominio) throws SQLException, Exception {
		StringBuffer query = new StringBuffer();
		query.append("SELECT * FROM ");
		query.append(RECEITA);
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
		List<Receita> listaReceita = new ArrayList<Receita>();
		try {
			preparedStatement = con.prepareStatement(query.toString());
			SQLUtil.setValorPpreparedStatement(preparedStatement, 1, mesAnoReferencia, java.sql.Types.DATE);
			SQLUtil.setValorPpreparedStatement(preparedStatement, 2, idCondominio, java.sql.Types.INTEGER);
			ResultSet resultSet = preparedStatement.executeQuery();
			Receita receita = null;
			while(resultSet.next()){
				receita = new Receita();								
				receita.setId((Integer) SQLUtil.getValorResultSet(resultSet, ID, java.sql.Types.INTEGER));				
				receita.setNome(String.valueOf(SQLUtil.getValorResultSet(resultSet, NOME, java.sql.Types.VARCHAR)));
				receita.setValor((Double) (SQLUtil.getValorResultSet(resultSet, VALOR, java.sql.Types.DOUBLE)));
				receita.setMesAnoReferencia((Date) SQLUtil.getValorResultSet(resultSet, MES_ANO_REFERENCIA, java.sql.Types.DATE));
				receita.setIdCondominio((Integer) SQLUtil.getValorResultSet(resultSet, ID_CONDOMINIO, java.sql.Types.INTEGER));
				listaReceita.add(receita);
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
		return listaReceita;
	}
	
	

}
