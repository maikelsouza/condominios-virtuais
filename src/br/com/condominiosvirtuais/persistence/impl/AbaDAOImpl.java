package br.com.condominiosvirtuais.persistence.impl;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import br.com.condominiosvirtuais.entity.Aba;
import br.com.condominiosvirtuais.persistence.AbaDAO;
import br.com.condominiosvirtuais.util.SQLUtil;

public class AbaDAOImpl implements AbaDAO, Serializable {

	private static final long serialVersionUID = 1L;

	private static Logger logger = Logger.getLogger(AbaDAOImpl.class);
	
	private static final String ABA = "ABA";
	
	private static final String ID = "ID";
	
	private static final String NOME = "NOME";
	
	private static final String ID_TELA = "ID_TELA";
	
	private static final String ID_ABA = "ID_ABA";
	
	
	@Override
	public List<Aba> buscarPorIdTela(Integer idTela, Connection con) throws SQLException, Exception {
		StringBuffer query = new StringBuffer();
		query.append("SELECT * FROM ");
		query.append(ABA);
		query.append(" WHERE ");
		query.append(ID_TELA);
		query.append(" = ?");
		query.append(" ORDER BY ");
		query.append(NOME);
		query.append(";");		
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		List<Aba> listaAba = new ArrayList<Aba>();
		Aba aba = null;
		try {
			preparedStatement = con.prepareStatement(query.toString());
			SQLUtil.setValorPpreparedStatement(preparedStatement, 1, idTela, java.sql.Types.INTEGER);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()){
				aba = new Aba();
				aba.setId((Integer) SQLUtil.getValorResultSet(resultSet, ID, java.sql.Types.INTEGER));
				aba.setNome(String.valueOf(SQLUtil.getValorResultSet(resultSet, NOME, java.sql.Types.VARCHAR)));
				aba.setIdTela((Integer) SQLUtil.getValorResultSet(resultSet, ID_TELA, java.sql.Types.INTEGER));
				aba.setIdAba(String.valueOf(SQLUtil.getValorResultSet(resultSet, ID_ABA, java.sql.Types.VARCHAR)));
				listaAba.add(aba);
			}
		}catch (SQLException e) {
			con.rollback();
			throw e;
		}catch (Exception e) {
			con.rollback();
			throw e;
		}	
		return listaAba;
	}	

}
