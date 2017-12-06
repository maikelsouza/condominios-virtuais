package br.com.condominiosvirtuais.persistence.impl;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import br.com.condominiosvirtuais.entity.Modulo;
import br.com.condominiosvirtuais.persistence.ModuloDAO;
import br.com.condominiosvirtuais.util.SQLUtil;

public class ModuloDAOImpl implements Serializable, ModuloDAO {

	private static final long serialVersionUID = 1L;
	
	private static Logger logger = Logger.getLogger(ModuloDAOImpl.class);
	
	private static final String MODULO = "MODULO";
	
	private static final String ID = "ID";
	
	private static final String NOME = "NOME";
	
	private static final String DESCRICAO = "DESCRICAO";

	@Override
	public Modulo buscarPorId(Integer id) throws SQLException, Exception {
		StringBuffer query = new StringBuffer();
		query.append("SELECT * FROM ");
		query.append(MODULO);
		query.append(" WHERE ");
		query.append(ID);		
		query.append(" = ? ");
		query.append(";");
		Connection con = C3P0DataSource.getInstance().getConnection();
		PreparedStatement preparedStatement = null;
		Modulo modulo = null;
		try {
			preparedStatement = con.prepareStatement(query.toString());
			SQLUtil.setValorPpreparedStatement(preparedStatement, 1, id, java.sql.Types.INTEGER);
			ResultSet resultSet = preparedStatement.executeQuery();
			while(resultSet.next()){				
				modulo = new Modulo();				
				modulo.setId((Integer) SQLUtil.getValorResultSet(resultSet, ID, java.sql.Types.INTEGER));
				modulo.setNome(String.valueOf(SQLUtil.getValorResultSet(resultSet, NOME, java.sql.Types.VARCHAR)));
				modulo.setDescricao(String.valueOf(SQLUtil.getValorResultSet(resultSet, DESCRICAO, java.sql.Types.VARCHAR)));
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
		return modulo;
	}
	
	@Override
	public List<Modulo>  buscarPorIds(List<Integer> listaIdsModulo) throws SQLException, Exception {
		List<Modulo> listaModulo = new ArrayList<Modulo>();
		Modulo modulo = null;
		if(!listaIdsModulo.isEmpty()){
			StringBuffer query = new StringBuffer();
			query.append("SELECT * FROM ");
			query.append(MODULO);
			query.append(" WHERE ");
			query.append(ID);		
			query.append(" IN( ");
			query.append(SQLUtil.popularInterrocacoes(listaIdsModulo.size()));	
			query.append(" ) ");
			query.append(";");
			Connection con = C3P0DataSource.getInstance().getConnection();
			PreparedStatement preparedStatement = null;
			Integer contador = 1;
			try {
				preparedStatement = con.prepareStatement(query.toString());
				for (Integer id: listaIdsModulo) {
					SQLUtil.setValorPpreparedStatement(preparedStatement, contador++, id, java.sql.Types.INTEGER);
				}
				ResultSet resultSet = preparedStatement.executeQuery();
				while(resultSet.next()){				
					modulo = new Modulo();				
					modulo.setId((Integer) SQLUtil.getValorResultSet(resultSet, ID, java.sql.Types.INTEGER));
					modulo.setNome(String.valueOf(SQLUtil.getValorResultSet(resultSet, NOME, java.sql.Types.VARCHAR)));
					modulo.setDescricao(String.valueOf(SQLUtil.getValorResultSet(resultSet, DESCRICAO, java.sql.Types.VARCHAR)));
					listaModulo.add(modulo);
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
		}	
		return listaModulo;
	}	
	
	

}
