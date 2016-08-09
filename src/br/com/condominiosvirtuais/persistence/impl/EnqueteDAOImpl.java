package br.com.condominiosvirtuais.persistence.impl;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.apache.log4j.Logger;

import br.com.condominiosvirtuais.entity.AlternativaEnquete;
import br.com.condominiosvirtuais.entity.Enquete;
import br.com.condominiosvirtuais.exception.BusinessException;
import br.com.condominiosvirtuais.persistence.AlternativaEnqueteDAO;
import br.com.condominiosvirtuais.persistence.EnqueteDAO;
import br.com.condominiosvirtuais.persistence.VotoEnqueteDAO;
import br.com.condominiosvirtuais.util.SQLUtil;

public class EnqueteDAOImpl implements EnqueteDAO, Serializable {
	
	private static final long serialVersionUID = 1L;

	private static Logger logger = Logger.getLogger(EnqueteDAOImpl.class); 
	
	private static final String ENQUETE = "ENQUETE";

	private static final String ID = "ID";
	
	private static final String PERGUNTA = "PERGUNTA";
	
	private static final String DATA_FIM = "DATA_FIM";
	
	private static final String DATA_CRIACAO = "DATA_CRIACAO";
	
	private static final String ID_CONDOMINIO = "ID_CONDOMINIO";
	
	// Constraint referente a integridade da enquete e suas alternativas
	private static final String FK_ALTERNATIVA_ENQUETE_ID_ENQUETE_ENQUETE_ID = "FK_ALTERNATIVA_ENQUETE_ID_ENQUETE_ENQUETE_ID";
	
	@Inject
	private AlternativaEnqueteDAO alternativaEnqueteDAO;
	
	@Inject
	private VotoEnqueteDAO votoEnqueteDAO;

	@Override
	public void salvar(Enquete enquete) throws SQLException, Exception {
		PreparedStatement statement = null;
		Connection con = null;		
		try {
			con = Conexao.getConexao();
			con.setAutoCommit(Boolean.FALSE);
			StringBuffer query = new StringBuffer();
			query.append("INSERT INTO "); 
			query.append(ENQUETE); 
			query.append("(");
			query.append(PERGUNTA); 
			query.append(",");
			query.append(DATA_FIM); 
			query.append(",");		
			query.append(DATA_CRIACAO); 
			query.append(",");		
			query.append(ID_CONDOMINIO);
			query.append(") ");
			query.append("VALUES(?,?,?,?)");
			statement = con.prepareStatement(query.toString(), PreparedStatement.RETURN_GENERATED_KEYS);			
			SQLUtil.setValorPpreparedStatement(statement, 1, enquete.getPergunta(), java.sql.Types.VARCHAR);
			SQLUtil.setValorPpreparedStatement(statement, 2, enquete.getDataFim(), java.sql.Types.DATE);
			SQLUtil.setValorPpreparedStatement(statement, 3, enquete.getDataCriacao(), java.sql.Types.DATE);
			SQLUtil.setValorPpreparedStatement(statement, 4, enquete.getIdCondominio(), java.sql.Types.INTEGER);
			statement.executeUpdate();
			ResultSet rs = statement.getGeneratedKeys();
			rs.next();			
			for (AlternativaEnquete alternativaEnquete : enquete.getListaAlternativaEnquetes()) {
				// Salva somente se foi preenchido uma alternativa. (somente as 2 primeiras são obrigatórias)
				if(!alternativaEnquete.getAlternativa().trim().isEmpty()){
					alternativaEnquete.setIdEnquete(rs.getInt(1));
					this.alternativaEnqueteDAO.salvar(alternativaEnquete, con);
				}
			}
			con.commit();
		} catch (SQLException e) {					
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

	@Override
	public List<Enquete> buscarPorIdCondominioEPergunta(Integer idCondominio, String pergunta) throws SQLException, Exception {
		StringBuffer query = new StringBuffer();
		query.append("SELECT * FROM ");
		query.append(ENQUETE);
		query.append(" WHERE ");
		query.append(ID_CONDOMINIO);
		query.append(" = ?");		
		query.append(" AND UPPER(");
		query.append(PERGUNTA);
		query.append(") LIKE ?");		
		query.append(" ORDER BY ");
		query.append(PERGUNTA);		
		query.append(";");		
		Connection con = Conexao.getConexao();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		List<Enquete> listaEnquete = new ArrayList<Enquete>();
		Enquete enquete = null;
		try {
			preparedStatement = con.prepareStatement(query.toString());
			SQLUtil.setValorPpreparedStatement(preparedStatement, 1, idCondominio, java.sql.Types.INTEGER);
			SQLUtil.setValorPpreparedStatement(preparedStatement, 2, "%"+pergunta.toUpperCase()+"%", java.sql.Types.VARCHAR);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()){
				enquete = new Enquete();
				enquete.setId((Integer) SQLUtil.getValorResultSet(resultSet, ID, java.sql.Types.INTEGER));
				enquete.setPergunta(String.valueOf(SQLUtil.getValorResultSet(resultSet, PERGUNTA, java.sql.Types.VARCHAR)));
				enquete.setDataFim((Date) SQLUtil.getValorResultSet(resultSet, DATA_FIM, java.sql.Types.DATE));
				enquete.setDataCriacao((Date) SQLUtil.getValorResultSet(resultSet, DATA_CRIACAO, java.sql.Types.DATE));
				enquete.setIdCondominio((Integer) SQLUtil.getValorResultSet(resultSet, ID_CONDOMINIO, java.sql.Types.INTEGER));
				enquete.setListaAlternativaEnquetes(this.alternativaEnqueteDAO.buscarPorIdEnquete(enquete.getId(), con));
				listaEnquete.add(enquete);
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
		return listaEnquete;
	}

	@Override
	public List<Enquete> buscarPorIdCondominio(Integer idCondominio) throws SQLException, Exception {
		StringBuffer query = new StringBuffer();
		query.append("SELECT * FROM ");
		query.append(ENQUETE);
		query.append(" WHERE ");
		query.append(ID_CONDOMINIO);
		query.append(" = ?");
		query.append(" ORDER BY ");
		query.append(PERGUNTA);
		query.append(";");		
		Connection con = Conexao.getConexao();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		List<Enquete> listaEnquete = new ArrayList<Enquete>();
		Enquete enquete = null;
		try {
			preparedStatement = con.prepareStatement(query.toString());
			SQLUtil.setValorPpreparedStatement(preparedStatement, 1, idCondominio, java.sql.Types.INTEGER);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()){
				enquete = new Enquete();
				enquete.setId((Integer) SQLUtil.getValorResultSet(resultSet, ID, java.sql.Types.INTEGER));
				enquete.setPergunta(String.valueOf(SQLUtil.getValorResultSet(resultSet, PERGUNTA, java.sql.Types.VARCHAR)));
				enquete.setDataFim((Date) SQLUtil.getValorResultSet(resultSet, DATA_FIM, java.sql.Types.DATE));
				enquete.setDataCriacao((Date) SQLUtil.getValorResultSet(resultSet, DATA_CRIACAO, java.sql.Types.DATE));
				enquete.setIdCondominio((Integer) SQLUtil.getValorResultSet(resultSet, ID_CONDOMINIO, java.sql.Types.INTEGER));
				enquete.setListaAlternativaEnquetes(this.alternativaEnqueteDAO.buscarPorIdEnquete(enquete.getId(), con));
				listaEnquete.add(enquete);
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
		return listaEnquete;
	}
	
	@Override
	public List<Enquete> buscarPorIdCondominioNaoFinalizou(Integer idCondominio) throws SQLException, Exception{
		StringBuffer query = new StringBuffer();
		query.append("SELECT * FROM ");
		query.append(ENQUETE);
		query.append(" WHERE ");
		query.append(ID_CONDOMINIO);
		query.append(" = ?");
		query.append(" AND ");
		query.append(DATA_FIM);
		query.append(" >= ?");
		query.append(" ORDER BY ");
		query.append(PERGUNTA);
		query.append(";");		
		Connection con = Conexao.getConexao();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		List<Enquete> listaEnquete = new ArrayList<Enquete>();
		Enquete enquete = null;
		try {
			preparedStatement = con.prepareStatement(query.toString());
			SQLUtil.setValorPpreparedStatement(preparedStatement, 1, idCondominio, java.sql.Types.INTEGER);
			SQLUtil.setValorPpreparedStatement(preparedStatement, 2, new Date(), java.sql.Types.DATE);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()){
				enquete = new Enquete();
				enquete.setId((Integer) SQLUtil.getValorResultSet(resultSet, ID, java.sql.Types.INTEGER));
				enquete.setPergunta(String.valueOf(SQLUtil.getValorResultSet(resultSet, PERGUNTA, java.sql.Types.VARCHAR)));
				enquete.setDataFim((Date) SQLUtil.getValorResultSet(resultSet, DATA_FIM, java.sql.Types.DATE));
				enquete.setDataCriacao((Date) SQLUtil.getValorResultSet(resultSet, DATA_CRIACAO, java.sql.Types.DATE));
				enquete.setIdCondominio((Integer) SQLUtil.getValorResultSet(resultSet, ID_CONDOMINIO, java.sql.Types.INTEGER));
				enquete.setListaAlternativaEnquetes(this.alternativaEnqueteDAO.buscarPorIdEnquete(enquete.getId(), con));
				listaEnquete.add(enquete);
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
		return listaEnquete;
	}

	@Override
	public void excluir(Enquete enquete) throws SQLException, BusinessException, Exception {
		StringBuffer query = new StringBuffer();
		Connection con = Conexao.getConexao();
		PreparedStatement statement = null;
		try {
			con.setAutoCommit(false);		
			for (AlternativaEnquete alternativaEnquete: enquete.getListaAlternativaEnquetes()) {
				this.alternativaEnqueteDAO.excluir(alternativaEnquete, con);			
			}
			this.votoEnqueteDAO.excluirPorIdEnquete(enquete.getId(), con);
			query.append("DELETE FROM ");
			query.append(ENQUETE);
			query.append(" WHERE ");		
			query.append(ID);
			query.append(" = ?");
			statement = con.prepareStatement(query.toString());
			SQLUtil.setValorPpreparedStatement(statement, 1, enquete.getId(), java.sql.Types.INTEGER);
			statement.executeUpdate();			
			con.commit();
		} catch (SQLException e) {
			con.rollback();
			if (e.getMessage().contains(FK_ALTERNATIVA_ENQUETE_ID_ENQUETE_ENQUETE_ID)){
				throw new BusinessException("msg.enquete.excluirAlternativasAssociadas"); 
			}else{
			  throw e;
			}
		}catch (Exception e) {					
					throw e;	
		} finally {
			try {
				statement.close();
				con.close();						
			} catch (SQLException e) {
				logger.error("erro sqlstate "+e.getSQLState(), e);
			}
		}	
	}

	public AlternativaEnqueteDAO getAlternativaEnqueteDAO() {
		return alternativaEnqueteDAO;
	}

	public void setAlternativaEnqueteDAO(AlternativaEnqueteDAO alternativaEnqueteDAO) {
		this.alternativaEnqueteDAO = alternativaEnqueteDAO;
	}

	public VotoEnqueteDAO getVotoEnqueteDAO() {
		return votoEnqueteDAO;
	}

	public void setVotoEnqueteDAO(VotoEnqueteDAO votoEnqueteDAO) {
		this.votoEnqueteDAO = votoEnqueteDAO;
	}

}
