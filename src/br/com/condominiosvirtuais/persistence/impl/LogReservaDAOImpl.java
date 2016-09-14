package br.com.condominiosvirtuais.persistence.impl;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import br.com.condominiosvirtuais.entity.LogReserva;
import br.com.condominiosvirtuais.persistence.LogReservaDAO;
import br.com.condominiosvirtuais.util.SQLUtil;

public class LogReservaDAOImpl implements LogReservaDAO, Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private static Logger logger = Logger.getLogger(LogReservaDAOImpl.class);
	
	private static final String LOG_RESERVA = "LOG_RESERVA";

	private static final String ID = "ID";
	
	private static final String ID_RESERVA = "ID_RESERVA";
	
	private static final String ID_CONDOMINO = "ID_CONDOMINO";	
	
	private static final String ID_AMBIENTE = "ID_AMBIENTE";
	
	private static final String SITUACAO = "SITUACAO";
	
	private static final String MOTIVO_REPROVACAO = "MOTIVO_REPROVACAO";
	
	private static final String MOTIVO_SUSPENSAO = "MOTIVO_SUSPENSAO";
	
	private static final String DATA = "DATA";
	
	private static final String ID_LOG = "ID_LOG";

	@Override
	public void salvar(LogReserva logReserva, Connection con) throws SQLException, Exception {
		StringBuffer query = new StringBuffer();
		query.append("INSERT INTO "); 
		query.append(LOG_RESERVA);
		query.append("("); 
		query.append(ID_RESERVA);	
		query.append(",");
		query.append(ID_CONDOMINO);
		query.append(",");
		query.append(ID_AMBIENTE);
		query.append(",");	
		query.append(SITUACAO);
		query.append(",");
		query.append(MOTIVO_REPROVACAO);
		query.append(",");	
		query.append(MOTIVO_SUSPENSAO);
		query.append(",");	
		query.append(DATA);
		query.append(",");	
		query.append(ID_LOG);
		query.append(") ");
		query.append("VALUES(?,?,?,?,?,?,?,?)");
		PreparedStatement statement = null;		
		try {			
			statement = con.prepareStatement(query.toString());
			SQLUtil.setValorPpreparedStatement(statement, 1, logReserva.getIdReserva(), java.sql.Types.INTEGER);
			SQLUtil.setValorPpreparedStatement(statement, 2, logReserva.getIdCondomino(), java.sql.Types.INTEGER);	
			SQLUtil.setValorPpreparedStatement(statement, 3, logReserva.getIdAmbiente(), java.sql.Types.INTEGER);			
			SQLUtil.setValorPpreparedStatement(statement, 4, logReserva.getSituacao(), java.sql.Types.VARCHAR);			
			SQLUtil.setValorPpreparedStatement(statement, 5, logReserva.getMotivoReprovacao(), java.sql.Types.VARCHAR);			
			SQLUtil.setValorPpreparedStatement(statement, 6, logReserva.getMotivoSuspensao(), java.sql.Types.VARCHAR);
			SQLUtil.setValorPpreparedStatement(statement, 7, logReserva.getData(), java.sql.Types.DATE);
			SQLUtil.setValorPpreparedStatement(statement, 8, logReserva.getIdLog(), java.sql.Types.INTEGER);
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
