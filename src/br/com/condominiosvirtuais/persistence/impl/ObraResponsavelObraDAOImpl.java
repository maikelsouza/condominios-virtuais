package br.com.condominiosvirtuais.persistence.impl;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import br.com.condominiosvirtuais.entity.ObraResponsavelObra;
import br.com.condominiosvirtuais.persistence.ObraResponsavelObraDAO;
import br.com.condominiosvirtuais.util.SQLUtil;

public class ObraResponsavelObraDAOImpl implements ObraResponsavelObraDAO, Serializable {

	private static final long serialVersionUID = 1L;
	
	private static Logger logger = Logger.getLogger(ObraResponsavelObraDAOImpl.class);
	
	private static final String OBRA_RESPONSAVEL_OBRA = "OBRA_RESPONSAVEL_OBRA";
	
	private static final String ID = "ID";	
	
	private static final String ID_OBRA = "ID_OBRA";
	
	private static final String ID_RESPONSAVEL_OBRA = "ID_RESPONSAVEL_OBRA";

	@Override
	public void salvar(ObraResponsavelObra obraResponsavelObra, Connection con) throws SQLException, Exception {
		StringBuffer query = new StringBuffer();
		query.append("INSERT INTO "); 
		query.append(OBRA_RESPONSAVEL_OBRA);
		query.append("("); 
		query.append(ID_OBRA); 
		query.append(",");		
		query.append(ID_RESPONSAVEL_OBRA);
		query.append(") ");
		query.append("VALUES(?,?)");
		PreparedStatement statement = null;
		try {			
			statement = con.prepareStatement(query.toString());			
			SQLUtil.setValorPpreparedStatement(statement, 1, obraResponsavelObra.getIdObra(), java.sql.Types.INTEGER);			
			SQLUtil.setValorPpreparedStatement(statement, 2, obraResponsavelObra.getIdResponsavelObra(), java.sql.Types.INTEGER);
			statement.executeUpdate();
		}catch (SQLException e) {		
			con.rollback();
			throw e;			
		} catch (Exception e) {
			con.rollback();
			throw e;	
		}
	}

	@Override
	public ObraResponsavelObra buscarPorIdObra(Integer idObra, Connection con) throws SQLException, Exception {
		StringBuffer query = new StringBuffer();
		query.append("SELECT * FROM ");
		query.append(OBRA_RESPONSAVEL_OBRA);
		query.append(" WHERE ");
		query.append(ID_OBRA);		
		query.append(" = ?");
		query.append(";");
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		ObraResponsavelObra obraResponsavelObra = null;
		try {
			preparedStatement = con.prepareStatement(query.toString());
			SQLUtil.setValorPpreparedStatement(preparedStatement, 1, idObra, java.sql.Types.INTEGER);				
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()){
				obraResponsavelObra = new ObraResponsavelObra();
				obraResponsavelObra.setId((Integer) SQLUtil.getValorResultSet(resultSet, ID, java.sql.Types.INTEGER));				
				obraResponsavelObra.setIdObra((Integer) SQLUtil.getValorResultSet(resultSet, ID_OBRA, java.sql.Types.INTEGER));
				obraResponsavelObra.setIdResponsavelObra((Integer) SQLUtil.getValorResultSet(resultSet, ID_RESPONSAVEL_OBRA, java.sql.Types.INTEGER));
			}
		} catch (SQLException e) {			
			throw e;
		} catch (Exception e) {
			throw e;		
		}				
		return obraResponsavelObra;
	}

	@Override
	public void excluirPodIdObra(Integer idObra, Connection con) throws SQLException, Exception {
		StringBuffer query = new StringBuffer();		
		con.setAutoCommit(Boolean.FALSE);
		PreparedStatement statement = null;
		try {			
			query.append("DELETE FROM ");
			query.append(OBRA_RESPONSAVEL_OBRA);
			query.append(" WHERE ");		
			query.append(ID_OBRA);
			query.append(" = ?");			
			statement = con.prepareStatement(query.toString());
			SQLUtil.setValorPpreparedStatement(statement, 1, idObra, java.sql.Types.INTEGER);
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
	public void atualizarPorIdObra(ObraResponsavelObra obraResponsavelObra, Connection con)
			throws SQLException, Exception {
		StringBuffer query = new StringBuffer();
		query.append("UPDATE ");
		query.append(OBRA_RESPONSAVEL_OBRA);
		query.append(" SET ");		
		query.append(ID_OBRA);
		query.append("= ?, ");
		query.append(ID_RESPONSAVEL_OBRA);
		query.append("= ? ");
		query.append(" WHERE ");
		query.append(ID_OBRA);
		query.append("= ?");		
		PreparedStatement statement = null;		
		try {
			statement = con.prepareStatement(query.toString());
			SQLUtil.setValorPpreparedStatement(statement, 1, obraResponsavelObra.getIdObra(), java.sql.Types.INTEGER);
			SQLUtil.setValorPpreparedStatement(statement, 2, obraResponsavelObra.getIdResponsavelObra(), java.sql.Types.INTEGER);
			SQLUtil.setValorPpreparedStatement(statement, 3, obraResponsavelObra.getIdObra(), java.sql.Types.INTEGER);
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
