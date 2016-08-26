package br.com.condominiosvirtuais.persistence.impl;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import br.com.condominiosvirtuais.entity.PrestadorServico;
import br.com.condominiosvirtuais.entity.VisitaPrestadorServico;
import br.com.condominiosvirtuais.persistence.VisitaPrestadorServicoDAO;
import br.com.condominiosvirtuais.util.SQLUtil;

public class VisitaPrestadorServicoDAOImpl implements Serializable, VisitaPrestadorServicoDAO {
	
private static final long serialVersionUID = 1L;
	
	private static Logger logger = Logger.getLogger(VisitaPrestadorServicoDAOImpl.class);
	
	private static final String VISITA_PRESTADOR_SERVICO = "VISITA_PRESTADOR_SERVICO";

	private static final String ID = "ID";	
	
	private static final String ID_VISITA = "ID_VISITA";

	private static final String ID_PRESTADOR_SERVICO = "ID_PRESTADOR_SERVICO";
	
	
	
	@Override
	public void salvar(VisitaPrestadorServico visitaPrestadorServico , Connection con) throws SQLException, Exception {
		StringBuffer query = new StringBuffer();
		query.append("INSERT INTO "); 
		query.append(VISITA_PRESTADOR_SERVICO);
		query.append("("); 
		query.append(ID_VISITA);	
		query.append(",");
		query.append(ID_PRESTADOR_SERVICO);				
		query.append(") ");
		query.append("VALUES(?,?)");
		PreparedStatement statement = null;		
		try {			
			statement = con.prepareStatement(query.toString());
			SQLUtil.setValorPpreparedStatement(statement, 1, visitaPrestadorServico.getIdVisita(), java.sql.Types.INTEGER);
			SQLUtil.setValorPpreparedStatement(statement, 2, visitaPrestadorServico.getIdPrestadorServico(), java.sql.Types.INTEGER);
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
	public VisitaPrestadorServico buscarPodIdVisita(Integer idVisita, Connection con) throws SQLException, Exception {
		StringBuffer query = new StringBuffer();
		query.append("SELECT * FROM ");
		query.append(VISITA_PRESTADOR_SERVICO);
		query.append(" WHERE ");
		query.append(ID_VISITA);		
		query.append(" = ?");
		query.append(";");
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;		
		VisitaPrestadorServico visitaPrestadorServico = null;		
		try {
			preparedStatement = con.prepareStatement(query.toString());
			SQLUtil.setValorPpreparedStatement(preparedStatement, 1, idVisita, java.sql.Types.INTEGER);	
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()){
				visitaPrestadorServico = new VisitaPrestadorServico();
				visitaPrestadorServico.setId((Integer) SQLUtil.getValorResultSet(resultSet, ID, java.sql.Types.INTEGER));				
				visitaPrestadorServico.setIdVisita((Integer) SQLUtil.getValorResultSet(resultSet, ID_VISITA, java.sql.Types.INTEGER));
				visitaPrestadorServico.setIdPrestadorServico((Integer) SQLUtil.getValorResultSet(resultSet, ID_PRESTADOR_SERVICO, java.sql.Types.INTEGER));				
			}
		} catch (SQLException e) {
			throw e;
		} catch (Exception e) {
			throw e;		
		}
		return visitaPrestadorServico;
	}
}
