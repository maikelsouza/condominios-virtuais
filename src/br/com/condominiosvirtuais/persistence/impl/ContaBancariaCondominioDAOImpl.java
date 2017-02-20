package br.com.condominiosvirtuais.persistence.impl;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import br.com.condominiosvirtuais.entity.ContaBancariaCondominio;
import br.com.condominiosvirtuais.persistence.ContaBancariaCondominioDAO;
import br.com.condominiosvirtuais.util.SQLUtil;

public class ContaBancariaCondominioDAOImpl implements ContaBancariaCondominioDAO, Serializable {	

	private static final long serialVersionUID = 1L;

	private static Logger logger = Logger.getLogger(ContaBancariaCondominioDAOImpl.class); 
	
	private static final String CONTA_BANCARIA_CONDOMINIO = "CONTA_BANCARIA_CONDOMINIO";
	
    private static final String ID = "ID";
    
    private static final String ID_CONTA_BANCARIA = "ID_CONTA_BANCARIA";
    
    private static final String ID_CONDOMINIO = "ID_CONDOMINIO";

	@Override
	public void salvar(ContaBancariaCondominio contaBancariaCondominio,Connection con) throws SQLException, Exception {
		PreparedStatement statement = null;				
		try {			
			StringBuffer query = new StringBuffer();
			query.append("INSERT INTO "); 
			query.append(CONTA_BANCARIA_CONDOMINIO); 
			query.append("(");
			query.append(ID_CONTA_BANCARIA); 
			query.append(",");
			query.append(ID_CONDOMINIO);
			query.append(") ");
			query.append("VALUES(?,?)");
			statement = con.prepareStatement(query.toString());			
			SQLUtil.setValorPpreparedStatement(statement, 1, contaBancariaCondominio.getIdContaBancaria(), java.sql.Types.INTEGER);
			SQLUtil.setValorPpreparedStatement(statement, 2, contaBancariaCondominio.getIdCondominio(),java.sql.Types.INTEGER);
			statement.execute();
		} catch (SQLException e) {
			throw e;
		} catch (Exception e) {
			throw e;		
		}	
		
	}

	@Override
	public List<ContaBancariaCondominio> buscarPorIdCondominio(Integer idCondominio, Connection con)
			throws SQLException, Exception {
		StringBuffer query = new StringBuffer();
		query.append("SELECT * FROM ");
		query.append(CONTA_BANCARIA_CONDOMINIO);
		query.append(" WHERE ");
		query.append(ID_CONDOMINIO);
		query.append(" = ?");
		query.append(";");
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		ContaBancariaCondominio contaBancariaCondominio = null;		
		List<ContaBancariaCondominio> listaContaBancariaCondominio = new ArrayList<ContaBancariaCondominio>();
		try {
			preparedStatement = con.prepareStatement(query.toString());
			SQLUtil.setValorPpreparedStatement(preparedStatement, 1, idCondominio, java.sql.Types.INTEGER);			
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()){
				contaBancariaCondominio = new ContaBancariaCondominio();
				contaBancariaCondominio.setId((Integer) SQLUtil.getValorResultSet(resultSet, ID, java.sql.Types.INTEGER));
				contaBancariaCondominio.setIdCondominio((Integer) SQLUtil.getValorResultSet(resultSet, ID_CONDOMINIO, java.sql.Types.INTEGER));
				contaBancariaCondominio.setIdContaBancaria((Integer) SQLUtil.getValorResultSet(resultSet, ID_CONTA_BANCARIA, java.sql.Types.INTEGER));
				listaContaBancariaCondominio.add(contaBancariaCondominio);
			}
		} catch (SQLException e) {
			throw e;
		} catch (Exception e) {
			throw e;
		}				
		return listaContaBancariaCondominio;
	}

	@Override
	public void atualizarPorIdContaBancaria(ContaBancariaCondominio contaBancariaCondominio, Connection con)
			throws SQLException, Exception {
		StringBuffer query = new StringBuffer();		
		query.append("UPDATE ");
		query.append(CONTA_BANCARIA_CONDOMINIO);
		query.append(" SET ");
		query.append(ID_CONDOMINIO);
		query.append(" = ? ");
		query.append("WHERE ");
		query.append(ID_CONTA_BANCARIA);
		query.append("= ?");
		PreparedStatement statement = null;
		try {			
			statement = con.prepareStatement(query.toString());
			SQLUtil.setValorPpreparedStatement(statement, 1, contaBancariaCondominio.getIdCondominio(), java.sql.Types.VARCHAR);
			SQLUtil.setValorPpreparedStatement(statement, 2, contaBancariaCondominio.getIdContaBancaria(), java.sql.Types.INTEGER);
			statement.executeUpdate();				
		} catch (SQLException e) {							
			throw e;
		}catch (Exception e) {					
			throw e;	
		}	
		
	}

	@Override
	public void excluirPorIdContaBancaria(Integer idContaBancaria, Connection con) throws SQLException, Exception {
		StringBuffer query = new StringBuffer();
		query.append("DELETE FROM ");
		query.append(CONTA_BANCARIA_CONDOMINIO);
		query.append(" WHERE ");
		query.append(ID_CONTA_BANCARIA);
		query.append("= ?");
		PreparedStatement statement = null;		
		try {
			statement = con.prepareStatement(query.toString());
			SQLUtil.setValorPpreparedStatement(statement, 1, idContaBancaria, java.sql.Types.INTEGER);			
			statement.executeUpdate();
		} catch (SQLException e) {
			throw e;
		} catch (Exception e) {
			throw e;
		}	
	} 
    

}
