package br.com.condominiosvirtuais.persistence.impl;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import br.com.condominiosvirtuais.entity.MeioPagamento;
import br.com.condominiosvirtuais.persistence.MeioPagamentoDAO;
import br.com.condominiosvirtuais.util.SQLUtil;

public class MeioPagamentoDAOImpl implements MeioPagamentoDAO, Serializable {

	private static final long serialVersionUID = 1L;
	
	private static Logger logger = Logger.getLogger(MeioPagamentoDAOImpl.class);
	
	private static final String MEIO_PAGAMENTO = "MEIO_PAGAMENTO";
	
	private static final String ID = "ID";
	
	private static final String NOME = "NOME";

	@Override
	public MeioPagamento buscaPorId(Integer id, Connection con) throws SQLException, Exception {
		StringBuffer query = new StringBuffer();
		query.append("SELECT * FROM ");
		query.append(MEIO_PAGAMENTO);
		query.append(" WHERE ");
		query.append(ID);
		query.append("= ? ");
		query.append(";");
		PreparedStatement preparedStatement = null;		
		MeioPagamento meioPagamento = null;		
		try {
			preparedStatement = con.prepareStatement(query.toString());
			SQLUtil.setValorPpreparedStatement(preparedStatement, 1, id, java.sql.Types.INTEGER);	
			ResultSet resultSet = preparedStatement.executeQuery();
			while(resultSet.next()){
				meioPagamento = new MeioPagamento();								
				meioPagamento.setId((Integer) SQLUtil.getValorResultSet(resultSet, ID, java.sql.Types.INTEGER));				
				meioPagamento.setNome(String.valueOf(SQLUtil.getValorResultSet(resultSet, NOME, java.sql.Types.VARCHAR)));
			}
		} catch (SQLException e) {					
			throw e;
		} catch (Exception e) {					
			throw e;	
		}
		return meioPagamento;
	}

	@Override
	public List<MeioPagamento> buscarTodos() throws SQLException, Exception {
		
		StringBuffer query = new StringBuffer();
		query.append("SELECT * FROM ");
		query.append(MEIO_PAGAMENTO);
		query.append(" ORDER BY ");
		query.append(NOME);
		query.append(";");
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;		
		MeioPagamento meioPagamento = null;
		Connection con = C3P0DataSource.getInstance().getConnection();
		List<MeioPagamento> listaMeioPagamento = new ArrayList<MeioPagamento>();
		try {
			preparedStatement = con.prepareStatement(query.toString());						
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()){
				meioPagamento = new MeioPagamento();
				meioPagamento.setId((Integer) SQLUtil.getValorResultSet(resultSet, ID, java.sql.Types.INTEGER));
				meioPagamento.setNome(String.valueOf(SQLUtil.getValorResultSet(resultSet, NOME, java.sql.Types.VARCHAR)));
				listaMeioPagamento.add(meioPagamento);				
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
		return listaMeioPagamento;
	}

}
