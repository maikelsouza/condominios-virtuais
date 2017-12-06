package br.com.condominiosvirtuais.persistence.impl;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.inject.Instance;
import javax.inject.Inject;

import org.apache.log4j.Logger;

import br.com.condominiosvirtuais.entity.AlternativaEnquete;
import br.com.condominiosvirtuais.entity.Condomino;
import br.com.condominiosvirtuais.entity.VotoEnquete;
import br.com.condominiosvirtuais.exception.BusinessException;
import br.com.condominiosvirtuais.persistence.AlternativaEnqueteDAO;
import br.com.condominiosvirtuais.persistence.VotoEnqueteDAO;
import br.com.condominiosvirtuais.util.AplicacaoUtil;
import br.com.condominiosvirtuais.util.SQLUtil;

public class AlternativaEnqueteDAOImpl implements AlternativaEnqueteDAO, Serializable {
	
	private static final long serialVersionUID = 1L;

	private static Logger logger = Logger.getLogger(AlternativaEnqueteDAOImpl.class); 
	
	private static final String ALTERNATIVA_ENQUETE = "ALTERNATIVA_ENQUETE";

	private static final String ID = "ID";
	
	private static final String ALTERNATIVA = "ALTERNATIVA";
	
	private static final String VOTO = "VOTO";	
	
	private static final String ID_ENQUETE = "ID_ENQUETE";
	
	@Inject
	private Instance<CondominoDAOImpl> condominoDAO = null;
	
	@Inject
	private VotoEnqueteDAO votoEnqueteDAO = null;

	@Override
	public void salvar(AlternativaEnquete alternativaEnquete, Connection con) throws SQLException, Exception {
		StringBuffer query = new StringBuffer();
		query.append("INSERT INTO "); 
		query.append(ALTERNATIVA_ENQUETE); 
		query.append("("); 
		query.append(ALTERNATIVA); 
		query.append(",");
		query.append(VOTO);		
		query.append(",");
		query.append(ID_ENQUETE); 
		query.append(") ");
		query.append("VALUES(?,?,?)");		
		PreparedStatement statement = null;
		try {
			statement = con.prepareStatement(query.toString());		
			SQLUtil.setValorPpreparedStatement(statement, 1, alternativaEnquete.getAlternativa(), java.sql.Types.VARCHAR);
			SQLUtil.setValorPpreparedStatement(statement, 2, alternativaEnquete.getVoto(), java.sql.Types.INTEGER);			
			SQLUtil.setValorPpreparedStatement(statement, 3, alternativaEnquete.getIdEnquete(), java.sql.Types.INTEGER);			
			statement.execute();
		} catch (SQLException e) {			
			con.rollback();
			throw e;
		} catch (Exception e) {
			con.rollback();
			throw e;	
		}		
	}

	@Override
	public List<AlternativaEnquete> buscarPorIdEnquete(Integer idEnquete,
			Connection con) throws SQLException, Exception {
		StringBuffer query = new StringBuffer();
		query.append("SELECT * FROM ");
		query.append(ALTERNATIVA_ENQUETE);
		query.append(" WHERE ");
		query.append(ID_ENQUETE);		
		query.append(" = ?");
		query.append(";");
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;		
		AlternativaEnquete alternativaEnquete = null;
		List<AlternativaEnquete> listaAlternativaEnquete = new ArrayList<AlternativaEnquete>();
		try {
			preparedStatement = con.prepareStatement(query.toString());
			SQLUtil.setValorPpreparedStatement(preparedStatement, 1, idEnquete, java.sql.Types.INTEGER);
			resultSet = preparedStatement.executeQuery();			
			while(resultSet.next()){
				alternativaEnquete = new AlternativaEnquete();
				alternativaEnquete.setId((Integer) SQLUtil.getValorResultSet(resultSet, ID, java.sql.Types.INTEGER));
				alternativaEnquete.setAlternativa(String.valueOf(SQLUtil.getValorResultSet(resultSet, ALTERNATIVA, java.sql.Types.VARCHAR)));
				alternativaEnquete.setVoto((Integer) SQLUtil.getValorResultSet(resultSet, VOTO, java.sql.Types.INTEGER));
				alternativaEnquete.setIdEnquete((Integer) SQLUtil.getValorResultSet(resultSet, ID_ENQUETE, java.sql.Types.INTEGER));		
				listaAlternativaEnquete.add(alternativaEnquete);
			}
		} catch (SQLException e) {			
			con.rollback();
			throw e;
		} catch (Exception e) {
			con.rollback();
			throw e;	
		}	
		return listaAlternativaEnquete;
	}

	@Override
	public void excluir(AlternativaEnquete alternativaEnquete, Connection con)
			throws SQLException, Exception {
		StringBuffer query = new StringBuffer();
		query.append("DELETE FROM ");
		query.append(ALTERNATIVA_ENQUETE);
		query.append(" WHERE ");
		query.append(ID);
		query.append("= ?");
		PreparedStatement statement = null;
		try {
			statement = con.prepareStatement(query.toString());
			SQLUtil.setValorPpreparedStatement(statement, 1, alternativaEnquete.getId(), java.sql.Types.INTEGER);
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
	public List<AlternativaEnquete> buscarPorIdEnquete(Integer idEnquete) throws SQLException, Exception {
		StringBuffer query = new StringBuffer();
		query.append("SELECT * FROM ");
		query.append(ALTERNATIVA_ENQUETE);
		query.append(" WHERE ");
		query.append(ID_ENQUETE);		
		query.append(" = ?");
		query.append(";");
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;		
		AlternativaEnquete alternativaEnquete = null;
		List<AlternativaEnquete> listaAlternativaEnquete = new ArrayList<AlternativaEnquete>();
		Connection con = C3P0DataSource.getInstance().getConnection();
		try {
			preparedStatement = con.prepareStatement(query.toString());
			SQLUtil.setValorPpreparedStatement(preparedStatement, 1, idEnquete, java.sql.Types.INTEGER);
			resultSet = preparedStatement.executeQuery();			
			while(resultSet.next()){
				alternativaEnquete = new AlternativaEnquete();
				alternativaEnquete.setId((Integer) SQLUtil.getValorResultSet(resultSet, ID, java.sql.Types.INTEGER));
				alternativaEnquete.setAlternativa(String.valueOf(SQLUtil.getValorResultSet(resultSet, ALTERNATIVA, java.sql.Types.VARCHAR)));
				alternativaEnquete.setVoto((Integer) SQLUtil.getValorResultSet(resultSet, VOTO, java.sql.Types.INTEGER));
				alternativaEnquete.setIdEnquete((Integer) SQLUtil.getValorResultSet(resultSet, ID_ENQUETE, java.sql.Types.INTEGER));		
				listaAlternativaEnquete.add(alternativaEnquete);
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
		return listaAlternativaEnquete;
	}
	

	public List<AlternativaEnquete> buscarPorIdEnqueteD(Integer idEnquete, Connection con) throws SQLException, Exception {
		StringBuffer query = new StringBuffer();
		query.append("SELECT * FROM ");
		query.append(ALTERNATIVA_ENQUETE);
		query.append(" WHERE ");
		query.append(ID_ENQUETE);		
		query.append(" = ?");
		query.append(";");
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;		
		AlternativaEnquete alternativaEnquete = null;
		List<AlternativaEnquete> listaAlternativaEnquete = new ArrayList<AlternativaEnquete>();		
		try {
			preparedStatement = con.prepareStatement(query.toString());
			SQLUtil.setValorPpreparedStatement(preparedStatement, 1, idEnquete, java.sql.Types.INTEGER);
			resultSet = preparedStatement.executeQuery();			
			while(resultSet.next()){
				alternativaEnquete = new AlternativaEnquete();
				alternativaEnquete.setId((Integer) SQLUtil.getValorResultSet(resultSet, ID, java.sql.Types.INTEGER));
				alternativaEnquete.setAlternativa(String.valueOf(SQLUtil.getValorResultSet(resultSet, ALTERNATIVA, java.sql.Types.VARCHAR)));
				alternativaEnquete.setVoto((Integer) SQLUtil.getValorResultSet(resultSet, VOTO, java.sql.Types.INTEGER));
				alternativaEnquete.setIdEnquete((Integer) SQLUtil.getValorResultSet(resultSet, ID_ENQUETE, java.sql.Types.INTEGER));		
				listaAlternativaEnquete.add(alternativaEnquete);
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
		return listaAlternativaEnquete;
	}

	@Override
	public void votar(AlternativaEnquete alternativaEnquete) throws SQLException, BusinessException, Exception {		
		Connection con = C3P0DataSource.getInstance().getConnection();		
		StringBuffer querySetVariavel = new StringBuffer();
		StringBuffer queryUpdate = new StringBuffer();
		querySetVariavel.append("Set @TOTALVOTOMAISUM = ");
		querySetVariavel.append("(SELECT VOTO FROM ");
		querySetVariavel.append(ALTERNATIVA_ENQUETE);
		querySetVariavel.append(" WHERE ID = ?); ");		
		queryUpdate.append("UPDATE ");
		queryUpdate.append(ALTERNATIVA_ENQUETE);
		queryUpdate.append(" SET ");
		queryUpdate.append(VOTO);
		queryUpdate.append(" = @TOTALVOTOMAISUM + 1");		
		queryUpdate.append(" WHERE ");
		queryUpdate.append(ID);
		queryUpdate.append(" = ?");
		PreparedStatement statement = null;
		try {
			con.setAutoCommit(Boolean.FALSE);
			statement = con.prepareStatement(querySetVariavel.toString());
			SQLUtil.setValorPpreparedStatement(statement, 1, alternativaEnquete.getId(), java.sql.Types.INTEGER);
			statement.execute();
			statement = con.prepareStatement(queryUpdate.toString());
			SQLUtil.setValorPpreparedStatement(statement, 1, alternativaEnquete.getId(), java.sql.Types.INTEGER);
			statement.executeUpdate();
			VotoEnquete votoEnquete = new VotoEnquete();			
			Condomino condomino = this.condominoDAO.get().buscarCondominoPorId(AplicacaoUtil.getUsuarioAutenticado().getId(),con);
		    votoEnquete.setIdEnquete(alternativaEnquete.getIdEnquete());
		    votoEnquete.setIdUnidade(condomino.getIdUnidade());		    
		    this.votoEnqueteDAO.salvar(votoEnquete, con);			
			con.commit();
		} catch (SQLException e) {
			throw e;
		} catch (BusinessException e) {
			throw e;	
		} catch (Exception e) {
			throw e;	
		}finally{
			try {
				statement.close();
				con.close();				
			} catch (SQLException e) {
				logger.error("erro sqlstate "+e.getSQLState(), e);
			}
		}
	}


	public VotoEnqueteDAO getVotoEnqueteDAO() {
		return votoEnqueteDAO;
	}

	public void setVotoEnqueteDAO(VotoEnqueteDAO votoEnqueteDAO) {
		this.votoEnqueteDAO = votoEnqueteDAO;
	}
	
}
