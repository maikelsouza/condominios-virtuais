package br.com.condominiosvirtuais.persistence.impl;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.inject.Produces;
import javax.inject.Inject;

import org.apache.log4j.Logger;

import br.com.condominiosvirtuais.cdi.qualifier.QualifierAmbienteDAO;
import br.com.condominiosvirtuais.cdi.qualifier.QualifierFuncionarioDAO;
import br.com.condominiosvirtuais.entity.BlocoConjuntoBloco;
import br.com.condominiosvirtuais.entity.ConjuntoBloco;
import br.com.condominiosvirtuais.entity.TipoConjuntoBloco;
import br.com.condominiosvirtuais.exception.BusinessException;
import br.com.condominiosvirtuais.persistence.BlocoConjuntoBlocoDAO;
import br.com.condominiosvirtuais.persistence.ConjuntoBlocoDAO;
import br.com.condominiosvirtuais.persistence.TipoConjuntoBlocoDAO;
import br.com.condominiosvirtuais.util.SQLUtil;

public class ConjuntoBlocoDAOImpl implements ConjuntoBlocoDAO, Serializable {
	
	private static final long serialVersionUID = 1L;

	private static Logger logger = Logger.getLogger(ConjuntoBlocoDAOImpl.class);
	
	private static final String CONJUNTO_BLOCO = "CONJUNTO_BLOCO";

	private static final String ID = "ID";	
	
	private static final String  TIPO = "TIPO";
		
	@Inject 
	private BlocoConjuntoBlocoDAO blocoConjuntoBlocoDAO;	
	
	private TipoConjuntoBlocoDAO tipoConjuntoBlocoDAO = null;
	
	@Inject @QualifierFuncionarioDAO
	private TipoConjuntoBlocoDAO tipoConjuntoBlocoDAOFuncionario = null;
	
	
	@Inject @QualifierAmbienteDAO
	private TipoConjuntoBlocoDAO tipoConjuntoBlocoDAOAmbiente = null;	

	@Produces @QualifierFuncionarioDAO
	public ConjuntoBlocoDAOImpl getConjuntoBlocoDAOFuncionario(){
		this.tipoConjuntoBlocoDAO = this.tipoConjuntoBlocoDAOFuncionario; 
		return this;
	}	
	
	@Produces @QualifierAmbienteDAO
	public ConjuntoBlocoDAO getConjuntoBlocoDAOAmbiente(){
		this.tipoConjuntoBlocoDAO = this.tipoConjuntoBlocoDAOAmbiente;
		return this;
	}
	
	public void salvar(ConjuntoBloco conjuntoBloco) throws SQLException, Exception {		
		StringBuffer query = new StringBuffer();
		query.append("INSERT INTO "); 
		query.append(CONJUNTO_BLOCO);
		query.append("("); 
		query.append(TIPO);		
		query.append(") ");
		query.append("VALUES(?)");		
		PreparedStatement statement = null;
		Connection con = C3P0DataSource.getInstance().getConnection();		
		try {
			con.setAutoCommit(false);
			statement = con.prepareStatement(query.toString(), PreparedStatement.RETURN_GENERATED_KEYS);
			statement.setInt(1,conjuntoBloco.getTipo());						
			statement.executeUpdate();
			ResultSet resultSet = statement.getGeneratedKeys(); 
			while(resultSet.next()){
				conjuntoBloco.setId(resultSet.getInt(1));				
			}
			for (int i = 0; i < conjuntoBloco.getListaTipoConjuntoBlocos().size(); i++) {
				TipoConjuntoBloco tipoConjuntoBloco = (TipoConjuntoBloco) conjuntoBloco.getListaTipoConjuntoBlocos().get(i);
				tipoConjuntoBloco.setIdConjuntoBloco(conjuntoBloco.getId());
				this.tipoConjuntoBlocoDAO.salvar(tipoConjuntoBloco, con);				
			}
			for (BlocoConjuntoBloco blocoConjuntoBloco : conjuntoBloco.getListaListaBlocoConjuntoBlocos()) {
				this.blocoConjuntoBlocoDAO.salvar(blocoConjuntoBloco,con);				
			}
			con.commit();
		} catch (SQLException e) {
			throw e;
		} catch (Exception e) {		
			throw e;
		} finally{
			try {
				statement.close();
				con.close();				
			} catch (SQLException e) {
				logger.error("erro sqlstate "+e.getSQLState(), e);
			}
		}	
	}
	
	public void atualizar(ConjuntoBloco conjuntoBloco) throws SQLException, Exception {		
		StringBuffer query = new StringBuffer();
		query.append("UPDATE "); 
		query.append(CONJUNTO_BLOCO);
		query.append(" SET ");		
		query.append(TIPO);
		query.append("= ?" );
		query.append(" WHERE " );
		query.append(ID);
		query.append(" = ?");		
		PreparedStatement statement = null;
		Connection con = C3P0DataSource.getInstance().getConnection();		
		try {
			con.setAutoCommit(false);
			statement = con.prepareStatement(query.toString());				
			SQLUtil.setValorPpreparedStatement(statement, 1, conjuntoBloco.getTipo(), java.sql.Types.INTEGER);
			SQLUtil.setValorPpreparedStatement(statement, 2, conjuntoBloco.getId(), java.sql.Types.INTEGER);
			statement.executeUpdate();
			this.blocoConjuntoBlocoDAO.excluirPorConjuntoBloco(conjuntoBloco, con);
			for (BlocoConjuntoBloco blocoConjuntoBloco : conjuntoBloco.getListaListaBlocoConjuntoBlocos()) {				
				this.blocoConjuntoBlocoDAO.salvar(blocoConjuntoBloco, con);
			}
			for (TipoConjuntoBloco tipoConjuntoBloco : conjuntoBloco.getListaTipoConjuntoBlocos()) {
				this.tipoConjuntoBlocoDAO.atualizar(tipoConjuntoBloco, con);			
			}			
			con.commit();
		} catch (SQLException e) {
			throw e;
		} catch (Exception e) {		
			throw e;
		} finally{
			try {
				statement.close();
				con.close();				
			} catch (SQLException e) {
				logger.error("erro sqlstate "+e.getSQLState(), e);
			}
		}	
	}
	
	public ConjuntoBloco buscarPorIdETipo(Integer idConjuntoBloco, Integer tipo, Connection con) throws SQLException, Exception {
		StringBuffer query = new StringBuffer();
		query.append("SELECT * FROM ");
		query.append(CONJUNTO_BLOCO);
		query.append(" WHERE ");
		query.append(ID);
		query.append(" = ? ");		
		query.append(" AND ");
		query.append(TIPO);
		query.append(" = ? ");
		query.append(";");		
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		ConjuntoBloco conjuntoBloco = null;
		List<BlocoConjuntoBloco> listaBlocoConjuntoBlocos = null;
		List<TipoConjuntoBloco> listaTipoConjuntoBlocos = null;
		try {
			preparedStatement = con.prepareStatement(query.toString());
			SQLUtil.setValorPpreparedStatement(preparedStatement, 1, idConjuntoBloco, java.sql.Types.INTEGER);
			SQLUtil.setValorPpreparedStatement(preparedStatement, 2, tipo, java.sql.Types.INTEGER);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()){
				conjuntoBloco = new ConjuntoBloco();
				conjuntoBloco.setId((Integer) SQLUtil.getValorResultSet(resultSet, ID, java.sql.Types.INTEGER));				
				conjuntoBloco.setTipo((Integer) SQLUtil.getValorResultSet(resultSet, TIPO, java.sql.Types.INTEGER));
				listaBlocoConjuntoBlocos = this.blocoConjuntoBlocoDAO.buscarPorIdConjuntoBloco(idConjuntoBloco, con);
				conjuntoBloco.setListaListaBlocoConjuntoBlocos(listaBlocoConjuntoBlocos);
				listaTipoConjuntoBlocos = this.tipoConjuntoBlocoDAO.buscarPorIdConjuntoBloco(idConjuntoBloco, con);
				conjuntoBloco.setListaTipoConjuntoBlocos(listaTipoConjuntoBlocos);
			}
		} catch (SQLException e) {
			con.rollback();
			throw e;
		} catch (Exception e) {
			con.rollback();
			throw e;
		} 
		return conjuntoBloco;
	}
	
	public ConjuntoBloco buscarPorId(Integer idConjuntoBloco, Connection con) throws SQLException, Exception{
		StringBuffer query = new StringBuffer();
		query.append("SELECT * FROM ");
		query.append(CONJUNTO_BLOCO);
		query.append(" WHERE ");
		query.append(ID);
		query.append(" = ? ");		
		query.append(";");		
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		ConjuntoBloco conjuntoBloco = null;
		List<BlocoConjuntoBloco> listaBlocoConjuntoBlocos = null;
		List<TipoConjuntoBloco> listaTipoConjuntoBlocos = null;
		try {
			preparedStatement = con.prepareStatement(query.toString());
			preparedStatement.setInt(1, idConjuntoBloco);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()){
				conjuntoBloco = new ConjuntoBloco();
				conjuntoBloco.setId(resultSet.getInt(ID));
				conjuntoBloco.setTipo(resultSet.getInt(TIPO));
				listaBlocoConjuntoBlocos = this.blocoConjuntoBlocoDAO.buscarPorIdConjuntoBloco(idConjuntoBloco, con);
				conjuntoBloco.setListaListaBlocoConjuntoBlocos(listaBlocoConjuntoBlocos);
				listaTipoConjuntoBlocos = this.tipoConjuntoBlocoDAO.buscarPorIdConjuntoBloco(idConjuntoBloco, con);
				conjuntoBloco.setListaTipoConjuntoBlocos(listaTipoConjuntoBlocos);
			}
		} catch (SQLException e) {
			throw e;
		} catch (Exception e) {
			throw e;
		} 
		return conjuntoBloco;
	}
	
	public ConjuntoBloco buscarPorId(Integer idConjuntoBloco) throws SQLException, Exception{
		StringBuffer query = new StringBuffer();
		query.append("SELECT * FROM ");
		query.append(CONJUNTO_BLOCO);
		query.append(" WHERE ");
		query.append(ID);
		query.append(" = ? ");		
		query.append(";");		
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		ConjuntoBloco conjuntoBloco = null;
		List<BlocoConjuntoBloco> listaBlocoConjuntoBlocos = null;
		List<TipoConjuntoBloco> listaTipoConjuntoBlocos = null;
		Connection con = C3P0DataSource.getInstance().getConnection();
		try {			
			preparedStatement = con.prepareStatement(query.toString());			
			SQLUtil.setValorPpreparedStatement(preparedStatement, 1, idConjuntoBloco, java.sql.Types.INTEGER);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()){
				conjuntoBloco = new ConjuntoBloco();				
				conjuntoBloco.setId((Integer)SQLUtil.getValorResultSet(resultSet, ID, java.sql.Types.INTEGER));
				conjuntoBloco.setTipo((Integer)SQLUtil.getValorResultSet(resultSet, TIPO, java.sql.Types.INTEGER));
				listaBlocoConjuntoBlocos = this.blocoConjuntoBlocoDAO.buscarPorIdConjuntoBloco(idConjuntoBloco, con);
				conjuntoBloco.setListaListaBlocoConjuntoBlocos(listaBlocoConjuntoBlocos);
				listaTipoConjuntoBlocos = this.tipoConjuntoBlocoDAO.buscarPorIdConjuntoBloco(idConjuntoBloco, con);
				conjuntoBloco.setListaTipoConjuntoBlocos(listaTipoConjuntoBlocos);
			}
		} catch (SQLException e) {
			throw e;
		} catch (Exception e) {		
			throw e;
		} finally{
			try {
				preparedStatement.close();
				con.close();				
			} catch (SQLException e) {
				logger.error("erro sqlstate "+e.getSQLState(), e);
			}
		}	
		return conjuntoBloco;
	}
	
	public void excluir(ConjuntoBloco conjuntoBloco) throws SQLException, BusinessException, Exception {		
		Connection con = C3P0DataSource.getInstance().getConnection();
		PreparedStatement statement = null;				
		try {
			con.setAutoCommit(false);		
			for (TipoConjuntoBloco tipoConjuntoBloco : conjuntoBloco.getListaTipoConjuntoBlocos()) {
				this.tipoConjuntoBlocoDAO.excluir(tipoConjuntoBloco, con);				
			}			
			for (BlocoConjuntoBloco blocoConjuntoBloco: conjuntoBloco.getListaListaBlocoConjuntoBlocos()) {
				this.blocoConjuntoBlocoDAO.excluir(blocoConjuntoBloco, con);	
			}
			StringBuffer query = new StringBuffer();
			query.append("DELETE FROM ");
			query.append(CONJUNTO_BLOCO);
			query.append(" WHERE ");		
			query.append(ID);
			query.append(" = ?");					
			statement = con.prepareStatement(query.toString());			
			SQLUtil.setValorPpreparedStatement(statement, 1, conjuntoBloco.getId(), java.sql.Types.INTEGER);						
			statement.executeUpdate();										
			con.commit();
		} catch (SQLException e) {			
			throw e;
		} catch (BusinessException e) {
			throw e;
		} catch (Exception e) {		
			throw e;
		} finally{
			try {
				SQLUtil.closeStatement(statement);
				SQLUtil.closeConnection(con);
			} catch (SQLException e) {
				logger.error("erro sqlstate "+e.getSQLState(), e);
			}
		}	
	}

	
	public List<ConjuntoBloco> buscarPorTipo(Integer tipo) throws SQLException, Exception {
		StringBuffer query = new StringBuffer();
		query.append("SELECT * FROM ");
		query.append(CONJUNTO_BLOCO);
		query.append(" WHERE ");
		query.append(TIPO);
		query.append(" = ? ");		
		query.append(";");
		Connection con = C3P0DataSource.getInstance().getConnection();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		ConjuntoBloco conjuntoBloco = null;
		List<ConjuntoBloco> listaConjuntoBloco = new ArrayList<ConjuntoBloco>();				
		try {
			preparedStatement = con.prepareStatement(query.toString());
			preparedStatement.setInt(1, tipo);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()){
				conjuntoBloco = new ConjuntoBloco();
				conjuntoBloco.setId(resultSet.getInt(ID));
				conjuntoBloco.setTipo(resultSet.getInt(TIPO));
				List<BlocoConjuntoBloco> listaBlocoConjuntoBloco = this.blocoConjuntoBlocoDAO.buscarPorIdConjuntoBloco(conjuntoBloco.getId(),con);				
				conjuntoBloco.setListaListaBlocoConjuntoBlocos(listaBlocoConjuntoBloco);
				List<TipoConjuntoBloco> listaTipoConjuntoBloco = this.tipoConjuntoBlocoDAO.buscarPorIdConjuntoBloco(conjuntoBloco.getId(),con);
				conjuntoBloco.setListaTipoConjuntoBlocos(listaTipoConjuntoBloco);
				listaConjuntoBloco.add(conjuntoBloco);
			}
		} catch (SQLException e) {
			throw e;
		} catch (Exception e) {		
			throw e;
		} finally{
			try {
				preparedStatement.close();
				con.close();				
			} catch (SQLException e) {
				logger.error("erro sqlstate "+e.getSQLState(), e);
			}
		}	
		return listaConjuntoBloco;	
	}

	public BlocoConjuntoBlocoDAO getBlocoConjuntoBlocoDAO() {
		return blocoConjuntoBlocoDAO;
	}

	public void setBlocoConjuntoBlocoDAO(BlocoConjuntoBlocoDAO blocoConjuntoBlocoDAO) {
		this.blocoConjuntoBlocoDAO = blocoConjuntoBlocoDAO;
	}
	
	 


	
}
