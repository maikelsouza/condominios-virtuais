package br.com.condominiosvirtuais.persistence.impl;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import br.com.condominiosvirtuais.entity.TipoChamado;
import br.com.condominiosvirtuais.persistence.TipoChamadoDAO;
import br.com.condominiosvirtuais.util.SQLUtil;

public class TipoChamadoDAOImpl implements TipoChamadoDAO, Serializable{
	
	private static final long serialVersionUID = 1L;

	private static Logger logger = Logger.getLogger(TipoChamadoDAOImpl.class);
	
	private static final String TIPO_CHAMADO = "TIPO_CHAMADO";
	
    private static final String ID = "ID";
	
	private static final String NOME = "NOME";
	
	private static final String POSICAO = "POSICAO";
	
	// String que contém a chave que representa o tipo de opção outros
	private static final String OPCAO_TIPO_CHAMADO_OUTROS = "tipoChamado.outros";

	@Override
	public TipoChamado buscarPorId(Integer idTipoChamado, Connection con) throws SQLException, Exception {
		StringBuffer query = new StringBuffer();
		query.append("SELECT * FROM ");
		query.append(TIPO_CHAMADO);
		query.append(" WHERE ");
		query.append(ID);				
		query.append(" = ?;");
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;		
		TipoChamado tipoChamado = null;
		try {
			preparedStatement = con.prepareStatement(query.toString());
			SQLUtil.setValorPpreparedStatement(preparedStatement, 1, idTipoChamado, java.sql.Types.INTEGER);			
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()){
				tipoChamado = new TipoChamado();
				tipoChamado.setId((Integer) SQLUtil.getValorResultSet(resultSet, ID, java.sql.Types.INTEGER));
				tipoChamado.setNome(String.valueOf(SQLUtil.getValorResultSet(resultSet, NOME, java.sql.Types.VARCHAR)));				
			}
		} catch (SQLException e) {
			con.rollback();
			throw e;
		} catch (Exception e) {
			con.rollback();
			throw e;	
		}			
		return tipoChamado;
	}
	
	@Override
	public List<TipoChamado> buscarTodos() throws SQLException, Exception {
		StringBuffer query = new StringBuffer();
		query.append("SELECT * FROM ");
		query.append(TIPO_CHAMADO);
		query.append(" ORDER BY ");
		query.append(POSICAO);				
		query.append(";");
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;		
		TipoChamado tipoChamado = null;
		Connection con = C3P0DataSource.getInstance().getConnection();
		List<TipoChamado> listaTipoChamado = new ArrayList<TipoChamado>();
		try {
			preparedStatement = con.prepareStatement(query.toString());						
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()){
				tipoChamado = new TipoChamado();
				tipoChamado.setId((Integer) SQLUtil.getValorResultSet(resultSet, ID, java.sql.Types.INTEGER));
				tipoChamado.setNome(String.valueOf(SQLUtil.getValorResultSet(resultSet, NOME, java.sql.Types.VARCHAR)));
				listaTipoChamado.add(tipoChamado);				
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
		return listaTipoChamado;
	}

	@Override
	public Integer maxId(Connection con) throws SQLException, Exception {
		StringBuffer query = new StringBuffer();
		query.append("SELECT ");
		query.append("MAX(");
		query.append(ID);
		query.append(") as ");
		query.append(ID);
		query.append(" FROM ");
		query.append(TIPO_CHAMADO);		
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Integer maxId = 0;
		try {
			preparedStatement = con.prepareStatement(query.toString());						
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()){
				maxId = (Integer) SQLUtil.getValorResultSet(resultSet, ID, java.sql.Types.INTEGER);
			}
		} catch (SQLException e) {
			con.rollback();
			throw e;
		} catch (Exception e) {
			con.rollback();
			throw e;	
		}
		return maxId;
	}



}
