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

import br.com.condominiosvirtuais.entity.Bloco;
import br.com.condominiosvirtuais.entity.Unidade;
import br.com.condominiosvirtuais.exception.BusinessException;
import br.com.condominiosvirtuais.persistence.GaragemDAO;
import br.com.condominiosvirtuais.persistence.UnidadeDAO;
import br.com.condominiosvirtuais.util.SQLUtil;

public class UnidadeDAOImpl implements UnidadeDAO, Serializable{
	
	private static final long serialVersionUID = 1L;

	private static Logger logger = Logger.getLogger(UnidadeDAOImpl.class);
	
	private static final String UNIDADE = "UNIDADE";
	
	private static final String ID = "ID";
	
	private static final String TIPO = "TIPO";	
	
	private static final String NUMERO = "NUMERO";	
	
	private static final String ID_BLOCO = "ID_BLOCO";
	
	// Constraint referente a integridade da unidade e condônino.
	private static final String FK_CONDOMINO_ID_UNIDADE_UNIDADE_ID = "FK_CONDOMINO_ID_UNIDADE_UNIDADE_ID";
	
	// Constraint referente a integridade de um número da unidade com seu bloco, ou seja, não é possível repetir um número para o mesmo bloco.
	private static final String UQ_UNIDADE_NUMERO_ID_BLOCO = "UQ_UNIDADE_NUMERO_ID_BLOCO";
	
	@Inject
	private Instance<GaragemDAO> garagemDAO;
	
	
		
	public List<Unidade> buscarListaUnidadesPorBlocoETipo(Bloco bloco, Integer tipo) throws SQLException, Exception{		
		StringBuffer query = new StringBuffer();
		query.append("SELECT * FROM ");
		query.append(UNIDADE);
		query.append(" WHERE ");
		query.append(ID_BLOCO);
		query.append(" = ? ");
		// Caso onde foi considerado ambos os tipos(apartamento e loja), logo não precisa da condição 'tipo'  
		if(tipo != 2 ){
			query.append("AND ");	
			query.append(TIPO);			
			query.append(" = ? ");	
		}
		query.append(" ORDER BY ");
		query.append(NUMERO);
		query.append(";");		
		Connection con = C3P0DataSource.getInstance().getConnection();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		List<Unidade> listaUnidade = new ArrayList<Unidade>();
		Unidade unidade = null;
		try {
			preparedStatement = con.prepareStatement(query.toString());
			preparedStatement.setInt(1, bloco.getId());
			if(tipo != 2 ){
				preparedStatement.setInt(2, tipo);
			}
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()){
				unidade = new Unidade();
				unidade.setId(resultSet.getInt(ID));
				unidade.setTipo(resultSet.getInt(TIPO));	
				unidade.setNumero(resultSet.getInt(NUMERO));
				listaUnidade.add(unidade);
			}
		} catch (SQLException e) {		
			throw e;
		}catch (Exception e) {
			throw e;	
		}finally{
			try {
				preparedStatement.close();
				con.close();				
			} catch (SQLException e) {
				logger.error("erro sqlstate "+e.getSQLState(), e);
			}
		}	
		return listaUnidade;
	}
	

	
	public List<Unidade> buscarListaUnidadesPorBloco(Bloco bloco) throws SQLException, Exception{		
		StringBuffer query = new StringBuffer();
		query.append("SELECT * FROM ");
		query.append(UNIDADE);
		query.append(" WHERE ");
		query.append(ID_BLOCO);
		query.append(" = ? ");
		query.append(" ORDER BY ");
		query.append(NUMERO);
		query.append(";");		
		Connection con = C3P0DataSource.getInstance().getConnection();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		List<Unidade> listaUnidade = new ArrayList<Unidade>();
		Unidade unidade = null;
		try {
			preparedStatement = con.prepareStatement(query.toString());
			SQLUtil.setValorPpreparedStatement(preparedStatement, 1, bloco.getId(), java.sql.Types.INTEGER);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()){
				unidade = new Unidade();
				unidade.setId((Integer) SQLUtil.getValorResultSet(resultSet, ID, java.sql.Types.INTEGER));
				unidade.setTipo((Integer) SQLUtil.getValorResultSet(resultSet, TIPO, java.sql.Types.INTEGER));
				unidade.setNumero((Integer) SQLUtil.getValorResultSet(resultSet, NUMERO, java.sql.Types.INTEGER));
				unidade.setIdBloco((Integer) SQLUtil.getValorResultSet(resultSet, ID_BLOCO, java.sql.Types.INTEGER));
				listaUnidade.add(unidade);
			}
		} catch (SQLException e) {
			throw e;
		}catch (Exception e) {		
			throw e;		
		}finally{
			try {
				SQLUtil.closeStatement(preparedStatement);
				SQLUtil.closeConnection(con);				
			} catch (SQLException e) {
				logger.error("erro sqlstate "+e.getSQLState(), e);				
			}
		}	
		return listaUnidade;
	}
	
	public List<Integer> buscarListaIdsUnidadesPorIdBloco(Integer idBloco) throws SQLException, Exception{		
		StringBuffer query = new StringBuffer();
		query.append("SELECT * FROM ");
		query.append(UNIDADE);
		query.append(" WHERE ");
		query.append(ID_BLOCO);
		query.append(" = ? ");			
		Connection con = C3P0DataSource.getInstance().getConnection();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		List<Integer> listaIds = new ArrayList<Integer>();
		Integer id = null;
		try {
			preparedStatement = con.prepareStatement(query.toString());			
			SQLUtil.setValorPpreparedStatement(preparedStatement, 1, idBloco, java.sql.Types.INTEGER);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()){
				id = (Integer) SQLUtil.getValorResultSet(resultSet, ID, java.sql.Types.INTEGER);
				listaIds.add(id);
			}
		} catch (SQLException e) {
			throw e;
		}catch (Exception e) {		
			throw e;		
		}finally{
			try {
				preparedStatement.close();
				con.close();				
			} catch (SQLException e) {
				logger.error("erro sqlstate "+e.getSQLState(), e);				
			}
		}	
		return listaIds;
	}
	
	public void salvarUnidade(Unidade unidade) throws SQLException, BusinessException, Exception{
		StringBuffer query = new StringBuffer();
		query.append("INSERT INTO "); 
		query.append(UNIDADE);
		query.append("("); 
		query.append(TIPO); 
		query.append(",");
		query.append(NUMERO); 
		query.append(",");
		query.append(ID_BLOCO); 
		query.append(") ");
		query.append("VALUES(?,?,?)");
		Connection con = C3P0DataSource.getInstance().getConnection();
		PreparedStatement statement = null;
		try {			
			statement = con.prepareStatement(query.toString());
			statement.setInt(1,unidade.getTipo());
			statement.setInt(2,unidade.getNumero());
			statement.setInt(3,unidade.getIdBloco());
			statement.execute();			
		} catch (SQLException e) {
			if (e.getMessage().contains(UQ_UNIDADE_NUMERO_ID_BLOCO)){
				throw new BusinessException("msg.unidade.NumeroRepetidoBloco"); 
			}else{			
				throw e;		
			}
		}catch (Exception e) {
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
	
	public void salvarUnidadeEmLote(List<Unidade> listaUnidades) throws SQLException, BusinessException, Exception{
		PreparedStatement statement = null;
		Connection con = C3P0DataSource.getInstance().getConnection();
		try {			
			StringBuffer query = new StringBuffer();
			query.append("INSERT INTO "); 
			query.append(UNIDADE);
			query.append("("); 
			query.append(TIPO); 
			query.append(",");
			query.append(UNIDADE); 
			query.append(",");
			query.append(ID_BLOCO); 
			query.append(") ");
			query.append("VALUES(?,?,?)");			 
			statement = con.prepareStatement(query.toString());
			for (Unidade unidade: listaUnidades) {
				statement.setInt(1,unidade.getTipo());
				statement.setInt(2,unidade.getNumero());
				statement.setInt(3,unidade.getIdBloco());
				statement.addBatch();	
			}			
			statement.executeBatch();			
		} catch (SQLException e) {			
			if (e.getMessage().contains(UQ_UNIDADE_NUMERO_ID_BLOCO)){
				throw new BusinessException("msg.unidade.NumeroRepetidoBloco"); 
			}else{			
				throw e;	
			}
		}catch (Exception e) {					
			throw new Exception("msg.erro.executarOperacao",e);
		}finally{
			try {
				statement.close();
				con.close();				
			} catch (SQLException e) {
				logger.error("erro sqlstate "+e.getSQLState(), e);				
		    }
		}	
	}

	public void atualizarUnidade(Unidade unidade) throws SQLException, BusinessException, Exception{
		StringBuffer query = new StringBuffer();
		query.append("UPDATE ");
		query.append(UNIDADE);
		query.append(" SET ");
		query.append(NUMERO);
		query.append("= ?, ");
		query.append(TIPO);
		query.append("= ?, ");
		query.append(ID_BLOCO);
		query.append("= ? ");
		query.append("WHERE ");
		query.append(ID);
		query.append("= ?");
		Connection con =C3P0DataSource.getInstance().getConnection();
		PreparedStatement statement = null;
		try {			
			statement = con.prepareStatement(query.toString());
			statement.setInt(1,unidade.getNumero());
			statement.setInt(2,unidade.getTipo());
			statement.setInt(3,unidade.getIdBloco());
			statement.setInt(4,unidade.getId());
			statement.executeUpdate();
		} catch (SQLException e) {
			if (e.getMessage().contains(UQ_UNIDADE_NUMERO_ID_BLOCO)){
				throw new BusinessException("msg.unidade.NumeroRepetidoBloco"); 
			}else{			
				throw e;				
			}
		}catch (Exception e) {					
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
	
	public void excluirUnidade(Unidade unidade) throws SQLException, BusinessException, Exception {
		StringBuffer query = new StringBuffer();
		Connection con = C3P0DataSource.getInstance().getConnection();
		PreparedStatement statement = null;
		con.setAutoCommit(Boolean.FALSE);
		try {			
			this.garagemDAO.get().excluirPorIdUnidade(unidade.getId(), con);
			query.append("DELETE FROM ");
			query.append(UNIDADE);
			query.append(" WHERE ");		
			query.append(ID);
			query.append(" = ?");
			statement = con.prepareStatement(query.toString());
			statement.setInt(1, unidade.getId());
			statement.executeUpdate();		
			con.commit();
		} catch (SQLException e) {				
			if (e.getMessage().contains(FK_CONDOMINO_ID_UNIDADE_UNIDADE_ID)){
				throw new BusinessException("msg.unidade.excluirCondominoAssociado"); 
			}else{			
				throw e;	
			}	
		}catch (Exception e) {			
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
	
	public Unidade buscarPorId(Integer id, Connection con) throws SQLException, Exception{		
		StringBuffer query = new StringBuffer();
		query.append("SELECT * FROM ");
		query.append(UNIDADE);
		query.append(" WHERE ");
		query.append(ID);
		query.append(" = ? ");		
		query.append(";");
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;		
		Unidade unidade = null;		
		try {
			preparedStatement = con.prepareStatement(query.toString());
			SQLUtil.setValorPpreparedStatement(preparedStatement, 1, id, java.sql.Types.INTEGER);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()){			
				unidade = new Unidade();				
				unidade.setId((Integer) SQLUtil.getValorResultSet(resultSet, ID, java.sql.Types.INTEGER));
				unidade.setTipo((Integer) SQLUtil.getValorResultSet(resultSet, TIPO, java.sql.Types.INTEGER));	
				unidade.setNumero((Integer) SQLUtil.getValorResultSet(resultSet, NUMERO, java.sql.Types.INTEGER));	
				unidade.setIdBloco((Integer) SQLUtil.getValorResultSet(resultSet, ID_BLOCO, java.sql.Types.INTEGER));	
			}
		} catch (SQLException e) {	
			con.rollback();
			throw e;				
		} catch (Exception e){
			con.rollback();
			throw e;
		}
		return unidade;
	}
	
	public Unidade buscarPorId(Integer id) throws SQLException, Exception {
		StringBuffer query = new StringBuffer();
		query.append("SELECT * FROM ");
		query.append(UNIDADE);
		query.append(" WHERE ");
		query.append(ID);
		query.append(" = ? ");		
		query.append(";");
		Connection con = C3P0DataSource.getInstance().getConnection();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Unidade unidade = null;
		try {
			preparedStatement =  con.prepareStatement(query.toString());
			SQLUtil.setValorPpreparedStatement(preparedStatement, 1, id, java.sql.Types.INTEGER);		
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()){
				unidade = new Unidade();
				unidade.setId((Integer) SQLUtil.getValorResultSet(resultSet, ID, java.sql.Types.INTEGER));
				unidade.setTipo((Integer) SQLUtil.getValorResultSet(resultSet, TIPO, java.sql.Types.INTEGER));	
				unidade.setNumero((Integer) SQLUtil.getValorResultSet(resultSet, NUMERO, java.sql.Types.INTEGER));	
				unidade.setIdBloco((Integer) SQLUtil.getValorResultSet(resultSet, ID_BLOCO, java.sql.Types.INTEGER));
			}
		}catch (SQLException e) {		
			throw e;
		}catch (Exception e){		
			throw e;
		}finally{
			try {
				preparedStatement.close();
				con.close();
			} catch (SQLException e) {				
				logger.error("erro sqlstate "+e.getSQLState(), e);	
			}			
		}
		return unidade;
	}	
}
