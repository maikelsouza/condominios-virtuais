package br.com.condominiosvirtuais.persistence.impl;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.apache.log4j.Logger;

import br.com.condominiosvirtuais.entity.Visita;
import br.com.condominiosvirtuais.entity.VisitaPrestadorServico;
import br.com.condominiosvirtuais.persistence.VisitaDAO;
import br.com.condominiosvirtuais.util.SQLUtil;

public class VisitaDAOImpl implements Serializable, VisitaDAO{

	private static final long serialVersionUID = 1L;
	
	private static Logger logger = Logger.getLogger(VisitaDAOImpl.class);
	
	private static final String VISITA = "VISITA";
	
	private static final String ID = "ID";
	
	private static final String DATA_INICIO = "DATA_INICIO";
	
	private static final String DATA_FIM = "DATA_FIM";
	
	private static final String PRESTAR_SERVICO = "PRESTAR_SERVICO";	
	
	private static final String ID_CONDOMINIO = "ID_CONDOMINIO";
	
	private static final String ID_BLOCO = "ID_BLOCO";
	
	private static final String ID_UNIDADE = "ID_UNIDADE";
	
	private static final String ID_CONDOMINO = "ID_CONDOMINO";
	
	private static final String ID_VISITANTE = "ID_VISITANTE";
	
	@Inject
	private PrestadorServicoDAOImpl prestadorServicoDAO;	
	
	@Inject
	private VisitaPrestadorServicoDAOImpl visitaPrestadorServicoDAO;
	
	@Override
	public void salvarVisitaESalvarPrestadorServico(Visita visita, Connection con) throws SQLException, Exception {		
		VisitaPrestadorServico visitaPrestadorServico = new VisitaPrestadorServico();
		Integer idPrestadorServico = null;
		StringBuffer query = new StringBuffer();
		query.append("INSERT INTO "); 
		query.append(VISITA);
		query.append("("); 
		query.append(DATA_INICIO);	
		query.append(",");
		query.append(DATA_FIM);
		query.append(",");
		query.append(PRESTAR_SERVICO);
		query.append(",");	
		query.append(ID_CONDOMINIO);
		query.append(",");	
		query.append(ID_BLOCO);
		query.append(",");	
		query.append(ID_UNIDADE);
		query.append(",");	
		query.append(ID_CONDOMINO);
		query.append(",");	
		query.append(ID_VISITANTE);
		query.append(") ");
		query.append("VALUES(?,?,?,?,?,?,?,?)");
		PreparedStatement statement = null;		
		try {			
			statement = con.prepareStatement(query.toString(), PreparedStatement.RETURN_GENERATED_KEYS);
			SQLUtil.setValorPpreparedStatement(statement, 1, visita.getDataInicio(), java.sql.Types.TIMESTAMP);
			SQLUtil.setValorPpreparedStatement(statement, 2, visita.getDataFim(), java.sql.Types.TIMESTAMP);	
			SQLUtil.setValorPpreparedStatement(statement, 3, visita.getPrestarServico(), java.sql.Types.BOOLEAN);			
			SQLUtil.setValorPpreparedStatement(statement, 4, visita.getIdCondominio(), java.sql.Types.INTEGER);			
			SQLUtil.setValorPpreparedStatement(statement, 5, visita.getIdBloco(), java.sql.Types.INTEGER);			
			SQLUtil.setValorPpreparedStatement(statement, 6, visita.getIdUnidade(), java.sql.Types.INTEGER);			
			SQLUtil.setValorPpreparedStatement(statement, 7, visita.getIdCondomino(), java.sql.Types.INTEGER);			
			SQLUtil.setValorPpreparedStatement(statement, 8, visita.getIdVisitante(), java.sql.Types.INTEGER);			
			statement.executeUpdate();	
			ResultSet rs = statement.getGeneratedKeys();
			rs.next();
			idPrestadorServico = this.prestadorServicoDAO.salvar(visita.getPrestadorServico(),con);
			visitaPrestadorServico.setIdVisita(rs.getInt(1));
			visitaPrestadorServico.setIdPrestadorServico(idPrestadorServico);
			visitaPrestadorServicoDAO.salvar(visitaPrestadorServico, con);
		} catch (SQLException e) {
			con.rollback();
			throw e;
		} catch (Exception e) {
			con.rollback();
			throw e;	
		}
	}

	@Override
	public List<Visita> buscarPorIdCondominio(Integer idCondominio) throws SQLException, Exception {
		StringBuffer query = new StringBuffer();
		query.append("SELECT * FROM ");
		query.append(VISITA);
		query.append(" WHERE ");
		query.append(ID_CONDOMINIO);		
		query.append(" = ?");
		query.append(";");		
		Connection con = Conexao.getConexao();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;		
		List<Visita> listaVisitas = new ArrayList<Visita>();
		Visita visita = null;
		try {
			preparedStatement = con.prepareStatement(query.toString());
			SQLUtil.setValorPpreparedStatement(preparedStatement, 1, idCondominio, java.sql.Types.INTEGER);	
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()){
				visita = new Visita();
				visita.setId((Integer) SQLUtil.getValorResultSet(resultSet, ID, java.sql.Types.INTEGER));
				visita.setDataInicio((Date) SQLUtil.getValorResultSet(resultSet, DATA_INICIO, java.sql.Types.TIMESTAMP));
				visita.setDataFim((Date) SQLUtil.getValorResultSet(resultSet, DATA_FIM, java.sql.Types.TIMESTAMP));
				visita.setPrestarServico((Boolean) SQLUtil.getValorResultSet(resultSet, PRESTAR_SERVICO, java.sql.Types.BOOLEAN));
				visita.setIdCondominio((Integer) SQLUtil.getValorResultSet(resultSet, ID_CONDOMINIO, java.sql.Types.INTEGER));
				visita.setIdBloco((Integer) SQLUtil.getValorResultSet(resultSet, ID_BLOCO, java.sql.Types.INTEGER));
				visita.setIdUnidade((Integer) SQLUtil.getValorResultSet(resultSet, ID_UNIDADE, java.sql.Types.INTEGER));
				visita.setIdCondomino((Integer) SQLUtil.getValorResultSet(resultSet, ID_CONDOMINO, java.sql.Types.INTEGER));
				visita.setIdVisitante((Integer) SQLUtil.getValorResultSet(resultSet, ID_VISITANTE, java.sql.Types.INTEGER));				
				listaVisitas.add(visita);
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
		return listaVisitas;		
	}
	
	@Override
	public List<Visita> buscarPorIdVisitante(Integer idVisitante) throws SQLException, Exception {
		StringBuffer query = new StringBuffer();
		query.append("SELECT * FROM ");
		query.append(VISITA);
		query.append(" WHERE ");
		query.append(ID_VISITANTE);		
		query.append(" = ?");
		query.append(";");		
		Connection con = Conexao.getConexao();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;		
		List<Visita> listaVisitas = new ArrayList<Visita>();
		Visita visita = null;
		VisitaPrestadorServico visitaPrestadorServico = null;
		Integer idPrestadorServico = null;
		try {
			preparedStatement = con.prepareStatement(query.toString());
			SQLUtil.setValorPpreparedStatement(preparedStatement, 1, idVisitante, java.sql.Types.INTEGER);	
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()){
				visita = new Visita();
				visita.setId((Integer) SQLUtil.getValorResultSet(resultSet, ID, java.sql.Types.INTEGER));
				visita.setDataInicio((Date) SQLUtil.getValorResultSet(resultSet, DATA_INICIO, java.sql.Types.TIMESTAMP));
				visita.setDataFim((Date) SQLUtil.getValorResultSet(resultSet, DATA_FIM, java.sql.Types.TIMESTAMP));
				visita.setPrestarServico((Boolean) SQLUtil.getValorResultSet(resultSet, PRESTAR_SERVICO, java.sql.Types.BOOLEAN));
				visita.setIdCondominio((Integer) SQLUtil.getValorResultSet(resultSet, ID_CONDOMINIO, java.sql.Types.INTEGER));
				visita.setIdBloco((Integer) SQLUtil.getValorResultSet(resultSet, ID_BLOCO, java.sql.Types.INTEGER));
				visita.setIdUnidade((Integer) SQLUtil.getValorResultSet(resultSet, ID_UNIDADE, java.sql.Types.INTEGER));
				visita.setIdCondomino((Integer) SQLUtil.getValorResultSet(resultSet, ID_CONDOMINO, java.sql.Types.INTEGER));
				visita.setIdVisitante((Integer) SQLUtil.getValorResultSet(resultSet, ID_VISITANTE, java.sql.Types.INTEGER));
				visitaPrestadorServico = this.visitaPrestadorServicoDAO.buscarPodIdVisita(visita.getId(), con);
				idPrestadorServico = visitaPrestadorServico != null ? visitaPrestadorServico.getIdPrestadorServico() : null;
				visita.setPrestadorServico(this.prestadorServicoDAO.buscarPorId(idPrestadorServico, con));
				listaVisitas.add(visita);
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
		return listaVisitas;		
	}

	@Override
	public void salvarVisitaEAtualizarPrestadorServico(Visita visita, Connection con) throws SQLException, Exception {
		VisitaPrestadorServico visitaPrestadorServico = new VisitaPrestadorServico();
		Integer idPrestadorServico = null;
		StringBuffer query = new StringBuffer();
		query.append("INSERT INTO "); 
		query.append(VISITA);
		query.append("("); 
		query.append(DATA_INICIO);	
		query.append(",");
		query.append(DATA_FIM);
		query.append(",");
		query.append(PRESTAR_SERVICO);
		query.append(",");	
		query.append(ID_CONDOMINIO);
		query.append(",");	
		query.append(ID_BLOCO);
		query.append(",");	
		query.append(ID_UNIDADE);
		query.append(",");	
		query.append(ID_CONDOMINO);
		query.append(",");	
		query.append(ID_VISITANTE);
		query.append(") ");
		query.append("VALUES(?,?,?,?,?,?,?,?)");
		PreparedStatement statement = null;		
		try {			
			statement = con.prepareStatement(query.toString(), PreparedStatement.RETURN_GENERATED_KEYS);
			SQLUtil.setValorPpreparedStatement(statement, 1, visita.getDataInicio(), java.sql.Types.TIMESTAMP);
			SQLUtil.setValorPpreparedStatement(statement, 2, visita.getDataFim(), java.sql.Types.TIMESTAMP);	
			SQLUtil.setValorPpreparedStatement(statement, 3, visita.getPrestarServico(), java.sql.Types.BOOLEAN);			
			SQLUtil.setValorPpreparedStatement(statement, 4, visita.getIdCondominio(), java.sql.Types.INTEGER);			
			SQLUtil.setValorPpreparedStatement(statement, 5, visita.getIdBloco(), java.sql.Types.INTEGER);			
			SQLUtil.setValorPpreparedStatement(statement, 6, visita.getIdUnidade(), java.sql.Types.INTEGER);			
			SQLUtil.setValorPpreparedStatement(statement, 7, visita.getIdCondomino(), java.sql.Types.INTEGER);			
			SQLUtil.setValorPpreparedStatement(statement, 8, visita.getIdVisitante(), java.sql.Types.INTEGER);			
			statement.executeUpdate();
			ResultSet rs = statement.getGeneratedKeys();
			rs.next();
			this.prestadorServicoDAO.atualizar(visita.getPrestadorServico(),con);
			visitaPrestadorServico.setIdVisita(rs.getInt(1));
			visitaPrestadorServico.setIdPrestadorServico(visita.getPrestadorServico().getId());
			this.visitaPrestadorServicoDAO.salvar(visitaPrestadorServico, con);
		} catch (SQLException e) {
			con.rollback();
			throw e;
		} catch (Exception e) {
			con.rollback();
			throw e;	
		}
		
	}

	@Override
	public void salvarSomenteVisita(Visita visita, Connection con) throws SQLException, Exception {		
		StringBuffer query = new StringBuffer();
		query.append("INSERT INTO "); 
		query.append(VISITA);
		query.append("("); 
		query.append(DATA_INICIO);	
		query.append(",");
		query.append(DATA_FIM);
		query.append(",");
		query.append(PRESTAR_SERVICO);
		query.append(",");	
		query.append(ID_CONDOMINIO);
		query.append(",");	
		query.append(ID_BLOCO);
		query.append(",");	
		query.append(ID_UNIDADE);
		query.append(",");	
		query.append(ID_CONDOMINO);
		query.append(",");	
		query.append(ID_VISITANTE);
		query.append(") ");
		query.append("VALUES(?,?,?,?,?,?,?,?)");
		PreparedStatement statement = null;		
		try {			
			statement = con.prepareStatement(query.toString(), PreparedStatement.RETURN_GENERATED_KEYS);
			SQLUtil.setValorPpreparedStatement(statement, 1, visita.getDataInicio(), java.sql.Types.TIMESTAMP);
			SQLUtil.setValorPpreparedStatement(statement, 2, visita.getDataFim(), java.sql.Types.TIMESTAMP);	
			SQLUtil.setValorPpreparedStatement(statement, 3, visita.getPrestarServico(), java.sql.Types.BOOLEAN);			
			SQLUtil.setValorPpreparedStatement(statement, 4, visita.getIdCondominio(), java.sql.Types.INTEGER);			
			SQLUtil.setValorPpreparedStatement(statement, 5, visita.getIdBloco(), java.sql.Types.INTEGER);			
			SQLUtil.setValorPpreparedStatement(statement, 6, visita.getIdUnidade(), java.sql.Types.INTEGER);			
			SQLUtil.setValorPpreparedStatement(statement, 7, visita.getIdCondomino(), java.sql.Types.INTEGER);			
			SQLUtil.setValorPpreparedStatement(statement, 8, visita.getIdVisitante(), java.sql.Types.INTEGER);			
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
	public List<Integer> buscarIdsVisitantesAgrupadosOndePeriodoEIdCondominio(Date de, Date ate, Integer idCondominio)
			throws SQLException, Exception {
		StringBuffer query = new StringBuffer();
		query.append("SELECT ");
		query.append(ID_VISITANTE);
		query.append(" FROM ");
		query.append(VISITA);
		query.append(" WHERE ");
		query.append(DATA_INICIO);
		query.append(" BETWEEN ? AND ?");
		query.append(" AND ");
		query.append(ID_CONDOMINIO);		
		query.append(" = ?");
		query.append(" GROUP BY ");
		query.append(ID_VISITANTE);
		query.append(";");		
		Connection con = Conexao.getConexao();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;		
		List<Integer> listaIdsVisitantes = new ArrayList<Integer>();
		Integer idVisitante = null;
		Calendar calendarAte = Calendar.getInstance();
		ate.setTime(ate.getTime());
		calendarAte.set(Calendar.HOUR_OF_DAY, 23);
		calendarAte.set(Calendar.MINUTE, 59);
		calendarAte.set(Calendar.SECOND, 59);
		try {
			preparedStatement = con.prepareStatement(query.toString());
			SQLUtil.setValorPpreparedStatement(preparedStatement, 1, de, java.sql.Types.TIMESTAMP);
			SQLUtil.setValorPpreparedStatement(preparedStatement, 2, calendarAte.getTime(), java.sql.Types.TIMESTAMP);
			SQLUtil.setValorPpreparedStatement(preparedStatement, 3, idCondominio, java.sql.Types.INTEGER);			
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()){
				idVisitante = (Integer) SQLUtil.getValorResultSet(resultSet, ID_VISITANTE, java.sql.Types.INTEGER);
				listaIdsVisitantes.add(idVisitante);
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
		return listaIdsVisitantes;
	}

	@Override
	public List<Visita> buscarPorPeriodoEIdVisitante(Date de, Date ate, Integer idVisitante) throws SQLException, Exception {		
		StringBuffer query = new StringBuffer();
		query.append("SELECT ");
		query.append(" * ");
		query.append(" FROM ");
		query.append(VISITA);
		query.append(" WHERE ");
		query.append(DATA_INICIO);
		query.append(" BETWEEN ? AND ?");
		query.append(" AND ");
		query.append(ID_VISITANTE);		
		query.append(" = ?");
		query.append(" ORDER BY ");
		query.append(DATA_INICIO);
		query.append(";");		
		Connection con = Conexao.getConexao();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;		
		List<Visita> listaVisitas = new ArrayList<Visita>();
		Visita visita = null;
		Calendar calendarAte = Calendar.getInstance();
		VisitaPrestadorServico visitaPrestadorServico = new VisitaPrestadorServico();
		Integer idPrestadorServico = null;
		ate.setTime(ate.getTime());		
		calendarAte.set(Calendar.HOUR_OF_DAY, 23);
		calendarAte.set(Calendar.MINUTE, 59);
		calendarAte.set(Calendar.SECOND, 59);
		try {
			preparedStatement = con.prepareStatement(query.toString());
			SQLUtil.setValorPpreparedStatement(preparedStatement, 1, de, java.sql.Types.TIMESTAMP);
			SQLUtil.setValorPpreparedStatement(preparedStatement, 2, calendarAte.getTime(), java.sql.Types.TIMESTAMP);
			SQLUtil.setValorPpreparedStatement(preparedStatement, 3, idVisitante, java.sql.Types.INTEGER);			
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()){
				visita = new Visita();
				visita.setId((Integer) SQLUtil.getValorResultSet(resultSet, ID, java.sql.Types.INTEGER));
				visita.setDataInicio((Date) SQLUtil.getValorResultSet(resultSet, DATA_INICIO, java.sql.Types.TIMESTAMP));
				visita.setDataFim((Date) SQLUtil.getValorResultSet(resultSet, DATA_FIM, java.sql.Types.TIMESTAMP));
				visita.setPrestarServico((Boolean) SQLUtil.getValorResultSet(resultSet, PRESTAR_SERVICO, java.sql.Types.BOOLEAN));
				visita.setIdCondominio((Integer) SQLUtil.getValorResultSet(resultSet, ID_CONDOMINIO, java.sql.Types.INTEGER));
				visita.setIdBloco((Integer) SQLUtil.getValorResultSet(resultSet, ID_BLOCO, java.sql.Types.INTEGER));
				visita.setIdUnidade((Integer) SQLUtil.getValorResultSet(resultSet, ID_UNIDADE, java.sql.Types.INTEGER));
				visita.setIdCondomino((Integer) SQLUtil.getValorResultSet(resultSet, ID_CONDOMINO, java.sql.Types.INTEGER));
				visita.setIdVisitante((Integer) SQLUtil.getValorResultSet(resultSet, ID_VISITANTE, java.sql.Types.INTEGER));
				visitaPrestadorServico = this.visitaPrestadorServicoDAO.buscarPodIdVisita(visita.getId(), con);
				idPrestadorServico = visitaPrestadorServico != null ? visitaPrestadorServico.getIdPrestadorServico() : null;
				visita.setPrestadorServico(this.prestadorServicoDAO.buscarPorId(idPrestadorServico, con));
				listaVisitas.add(visita);
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
		return listaVisitas;
	}
}
