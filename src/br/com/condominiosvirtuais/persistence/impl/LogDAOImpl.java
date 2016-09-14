package br.com.condominiosvirtuais.persistence.impl;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.enterprise.inject.Instance;
import javax.inject.Inject;

import org.apache.log4j.Logger;

import br.com.condominiosvirtuais.entity.Log;
import br.com.condominiosvirtuais.persistence.LogDAO;
import br.com.condominiosvirtuais.util.SQLUtil;

public class LogDAOImpl implements LogDAO, Serializable {

	private static final long serialVersionUID = 1L;
	
	private static Logger logger = Logger.getLogger(LogDAOImpl.class);
	
	private static final String LOG = "LOG";

	private static final String ID = "ID";
	
	private static final String ACAO = "ACAO";
	
	private static final String DATA = "DATA";	
	
	private static final String ID_USUARIO = "ID_USUARIO";
	
	@Inject
	private Instance<LogReservaDAOImpl> LogReservaDAO;

	@Override
	public void salvarReserva(Log log, Connection con) throws SQLException, Exception {
		StringBuffer query = new StringBuffer();
		query.append("INSERT INTO "); 
		query.append(LOG);
		query.append("("); 
		query.append(ACAO);
		query.append(",");	
		query.append(DATA);
		query.append(",");	
		query.append(ID_USUARIO);
		query.append(") ");
		query.append("VALUES(?,?,?)");
		PreparedStatement statement = null;
		ResultSet rs = null;
		try {			
			statement = con.prepareStatement(query.toString(), PreparedStatement.RETURN_GENERATED_KEYS);
			SQLUtil.setValorPpreparedStatement(statement, 1, log.getAcao(), java.sql.Types.VARCHAR);
			SQLUtil.setValorPpreparedStatement(statement, 2, log.getData(), java.sql.Types.TIMESTAMP);	
			SQLUtil.setValorPpreparedStatement(statement, 3, log.getIdUsuario(), java.sql.Types.INTEGER);	
			statement.executeUpdate();
			rs =  statement.getGeneratedKeys();
			rs.next();
			log.getLogReserva().setIdLog(rs.getInt(1));
			this.LogReservaDAO.get().salvar(log.getLogReserva(), con);
		} catch (SQLException e) {
			con.rollback();
			throw e;
		} catch (Exception e) {
			con.rollback();
			throw e;	
		}		
	}
	

}
