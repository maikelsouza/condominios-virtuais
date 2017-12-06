package br.com.condominiosvirtuais.persistence.impl;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import br.com.condominiosvirtuais.entity.Condominio;
import br.com.condominiosvirtuais.entity.TelefonesUteis;
import br.com.condominiosvirtuais.persistence.TelefonesUteisDAO;
import br.com.condominiosvirtuais.util.SQLUtil;

public class TelefonesUteisDAOImpl implements TelefonesUteisDAO, Serializable {
	
	private static final long serialVersionUID = 1L;

	private static Logger logger = Logger.getLogger(TelefonesUteisDAOImpl.class);

	private static final String TELEFONES_UTEIS = "TELEFONES_UTEIS";

	private static final String ID = "ID";
	
	private static final String SITE = "SITE";
	
	private static final String NOME = "NOME";
	
	private static final String TELEFONE1 = "TELEFONE1";
	
	private static final String TELEFONE2 = "TELEFONE2";
	
	private static final String EMAIL = "EMAIL";
	
	private static final String DESCRICAO = "DESCRICAO";
	
	private static final String  ID_CONDOMINIO = "ID_CONDOMINIO";

	@Override
	public List<TelefonesUteis> buscarPorCondominio(Condominio condominio) throws SQLException, Exception {
		StringBuffer query = new StringBuffer();
		query.append("SELECT * FROM ");
		query.append(TELEFONES_UTEIS);
		query.append(" WHERE ");
		query.append(ID_CONDOMINIO);		
		query.append(" = ? ");			
		query.append("ORDER BY ");
		query.append(NOME);	
		Connection con = C3P0DataSource.getInstance().getConnection();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;		
		TelefonesUteis telefonesUteis = null;
		List<TelefonesUteis> listaTelefonesUteis = new ArrayList<TelefonesUteis>();
		try {			
			preparedStatement = con.prepareStatement(query.toString());
			SQLUtil.setValorPpreparedStatement(preparedStatement, 1, condominio.getId(), java.sql.Types.INTEGER);			
			resultSet = preparedStatement.executeQuery();			
			while(resultSet.next()){
				telefonesUteis = new TelefonesUteis();
				telefonesUteis.setId((Integer) SQLUtil.getValorResultSet(resultSet, ID, java.sql.Types.INTEGER));
				telefonesUteis.setNome((String.valueOf(SQLUtil.getValorResultSet(resultSet, NOME, java.sql.Types.VARCHAR))));
				telefonesUteis.setEmail((String.valueOf(SQLUtil.getValorResultSet(resultSet, EMAIL, java.sql.Types.VARCHAR))));
				telefonesUteis.setTelefone1((Long) SQLUtil.getValorResultSet(resultSet, TELEFONE1, java.sql.Types.BIGINT));
				telefonesUteis.setTelefone2((Long) SQLUtil.getValorResultSet(resultSet, TELEFONE2, java.sql.Types.BIGINT));
				telefonesUteis.setSite((String.valueOf(SQLUtil.getValorResultSet(resultSet, SITE, java.sql.Types.VARCHAR))));
				telefonesUteis.setDescricao((String.valueOf(SQLUtil.getValorResultSet(resultSet, DESCRICAO, java.sql.Types.VARCHAR))));				           
				telefonesUteis.setIdCondominio((Integer) SQLUtil.getValorResultSet(resultSet, ID_CONDOMINIO, java.sql.Types.INTEGER));				
				listaTelefonesUteis.add(telefonesUteis);
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
		return listaTelefonesUteis;
	}

	@Override
	public void salvar(TelefonesUteis telefonesUteis) throws SQLException, Exception {
		StringBuffer query = new StringBuffer();
		query.append("INSERT INTO "); 
		query.append(TELEFONES_UTEIS);
		query.append("("); 
		query.append(NOME);
		query.append(",");
		query.append(SITE);
		query.append(",");
		query.append(DESCRICAO);
		query.append(",");
		query.append(ID_CONDOMINIO);
		query.append(",");
		query.append(EMAIL);
		query.append(",");
		query.append(TELEFONE1);
		query.append(",");
		query.append(TELEFONE2);
		query.append(") ");
		query.append("VALUES(?,?,?,?,?,?,?)");
		PreparedStatement statement = null;		
		Connection con = C3P0DataSource.getInstance().getConnection();
		try {			
			statement = con.prepareStatement(query.toString());			
			SQLUtil.setValorPpreparedStatement(statement, 1, telefonesUteis.getNome(), java.sql.Types.VARCHAR);
			SQLUtil.setValorPpreparedStatement(statement, 2, telefonesUteis.getSite(), java.sql.Types.VARCHAR);
			SQLUtil.setValorPpreparedStatement(statement, 3, telefonesUteis.getDescricao(), java.sql.Types.VARCHAR);
			SQLUtil.setValorPpreparedStatement(statement, 4, telefonesUteis.getIdCondominio(), java.sql.Types.INTEGER);
			SQLUtil.setValorPpreparedStatement(statement, 5, telefonesUteis.getEmail(), java.sql.Types.VARCHAR);
			SQLUtil.setValorPpreparedStatement(statement, 6, telefonesUteis.getTelefone1(), java.sql.Types.BIGINT);
			SQLUtil.setValorPpreparedStatement(statement, 7, telefonesUteis.getTelefone2(), java.sql.Types.BIGINT);			
			statement.executeUpdate();
		} catch (SQLException e) {					
			throw e;
		} catch (Exception e) {					
			throw e;	
		}finally{
			try{
				statement.close();
				con.close();
			}catch (SQLException e) {
				logger.error("erro sqlstate "+e.getSQLState(), e);
			}				
		}			
	}

	@Override
	public void excluir(TelefonesUteis linksUteis) throws SQLException, Exception {
		StringBuffer query = new StringBuffer();
		Connection con = C3P0DataSource.getInstance().getConnection();
		PreparedStatement statement = null;
		try {			
			query.append("DELETE FROM ");
			query.append(TELEFONES_UTEIS);
			query.append(" WHERE ");		
			query.append(ID);
			query.append(" = ?");
			statement = con.prepareStatement(query.toString());
			SQLUtil.setValorPpreparedStatement(statement, 1, linksUteis.getId(), java.sql.Types.INTEGER);
			statement.executeUpdate();	
		} catch (SQLException e) {					
			throw e;
		} catch (Exception e) {					
			throw e;	
		}finally{
			try{
				statement.close();
				con.close();
			}catch (SQLException e) {
				logger.error("erro sqlstate "+e.getSQLState(), e);
			}					
		}			
	}

	@Override
	public void atualizar(TelefonesUteis telefonesUteis) throws SQLException, Exception {
		StringBuffer query = new StringBuffer();
		query.append("UPDATE ");
		query.append(TELEFONES_UTEIS);
		query.append(" SET ");
		query.append(NOME);
		query.append("= ?, ");
		query.append(SITE);			
		query.append("= ?, ");
		query.append(DESCRICAO);			
		query.append("= ?, ");
		query.append(ID_CONDOMINIO);			
		query.append("= ?, ");		
		query.append(EMAIL);			
		query.append("= ?, ");		
		query.append(TELEFONE1);			
		query.append("= ?, ");		
		query.append(TELEFONE2);			
		query.append("= ? ");		
		query.append(" WHERE ");
		query.append(ID);
		query.append("= ?");
		Connection con = C3P0DataSource.getInstance().getConnection();
		PreparedStatement statement = null;
		try {
			statement = con.prepareStatement(query.toString());
			SQLUtil.setValorPpreparedStatement(statement, 1, telefonesUteis.getNome(), java.sql.Types.VARCHAR);
			SQLUtil.setValorPpreparedStatement(statement, 2, telefonesUteis.getSite(), java.sql.Types.VARCHAR);
			SQLUtil.setValorPpreparedStatement(statement, 3, telefonesUteis.getDescricao(), java.sql.Types.VARCHAR);
			SQLUtil.setValorPpreparedStatement(statement, 4, telefonesUteis.getIdCondominio(), java.sql.Types.INTEGER);
			SQLUtil.setValorPpreparedStatement(statement, 5, telefonesUteis.getEmail(), java.sql.Types.VARCHAR);
			SQLUtil.setValorPpreparedStatement(statement, 6, telefonesUteis.getTelefone1(), java.sql.Types.BIGINT);
			SQLUtil.setValorPpreparedStatement(statement, 7, telefonesUteis.getTelefone2(), java.sql.Types.BIGINT);
			SQLUtil.setValorPpreparedStatement(statement, 8, telefonesUteis.getId(), java.sql.Types.INTEGER);
			statement.executeUpdate();			
		} catch (SQLException e) {					
			throw e;
		} catch (Exception e) {					
			throw e;	
		}finally{
			try{
				statement.close();
				con.close();
			}catch (SQLException e) {
				logger.error("erro sqlstate "+e.getSQLState(), e);
			}					
		}			
	}	
		
}
