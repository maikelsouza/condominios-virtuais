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

import br.com.condominiosvirtuais.entity.Despesas;
import br.com.condominiosvirtuais.persistence.DespesasDAO;
import br.com.condominiosvirtuais.util.SQLUtil;

public class DespesasDAOImpl implements DespesasDAO, Serializable {

	private static final long serialVersionUID = 1L;
	
	private static Logger logger = Logger.getLogger(DespesasDAOImpl.class);
	
	private static final String DESPESAS = "DESPESAS";
	
	private static final String ID = "ID";	
	
	private static final String DESCRICAO = "DESCRICAO";
	
	private static final String VALOR = "VALOR";
	
	private static final String TIPO = "TIPO";
	
	private static final String MES_ANO_REFERENCIA = "MES_ANO_REFERENCIA";
	
	private static final String ID_CONDOMINIO = "ID_CONDOMINIO";
	
	private static final String ID_UNIDADE = "ID_UNIDADE";
	
	@Inject
	private CondominioDAOImpl condominioDAO;
	
	@Inject
	private UnidadeDAOImpl unidadeDAO;	
	
	@Inject
	private ConsumoGasDespesasDAOImpl consumoGasDespesasDAO;
		

	@Override
	public void excluir(Despesas despesas) throws SQLException, Exception {
		StringBuffer query = new StringBuffer();
		query.append("DELETE FROM ");
		query.append(DESPESAS);
		query.append(" WHERE ");
		query.append(ID);
		query.append("= ?");
		PreparedStatement statement = null;
		Connection con = C3P0DataSource.getInstance().getConnection();
		try {
			statement = con.prepareStatement(query.toString());
			this.consumoGasDespesasDAO.excluir(despesas.getConsumoGasDespesas(), con);
			SQLUtil.setValorPpreparedStatement(statement, 1, despesas.getId(), java.sql.Types.INTEGER);			
			statement.executeUpdate();
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
	public void atualizarDespesasGasCondominio(Despesas despesas) throws SQLException, Exception {
		StringBuffer query = new StringBuffer();
		query.append("UPDATE ");
		query.append(DESPESAS);
		query.append(" SET ");
		query.append(DESCRICAO);
		query.append("= ?, ");
		query.append(TIPO);
		query.append("= ?, ");
		query.append(VALOR);
		query.append("= ?, ");
		query.append(MES_ANO_REFERENCIA);
		query.append("= ?, ");		
		query.append(ID_UNIDADE);
		query.append("= ? ");		
		query.append(" WHERE ");
		query.append(ID);
		query.append("= ?");
		PreparedStatement statement = null;
		Connection con = C3P0DataSource.getInstance().getConnection();
		try {
			statement = con.prepareStatement(query.toString());
			SQLUtil.setValorPpreparedStatement(statement, 1, despesas.getDescricao(), java.sql.Types.VARCHAR);
			SQLUtil.setValorPpreparedStatement(statement, 2, despesas.getTipo(), java.sql.Types.INTEGER);
			SQLUtil.setValorPpreparedStatement(statement, 3, despesas.getValor(), java.sql.Types.DOUBLE);
			SQLUtil.setValorPpreparedStatement(statement, 4, despesas.getMesAnoReferencia(), java.sql.Types.DATE);
			SQLUtil.setValorPpreparedStatement(statement, 5, despesas.getUnidade().getId(), java.sql.Types.INTEGER);
			SQLUtil.setValorPpreparedStatement(statement, 6, despesas.getId(), java.sql.Types.INTEGER);
			this.consumoGasDespesasDAO.atualizar(despesas.getConsumoGasDespesas(), con);
			statement.executeUpdate();	
			
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
	public void atualizarDespesasGasCondomino(Despesas despesas) throws SQLException, Exception {
		StringBuffer query = new StringBuffer();
		query.append("UPDATE ");
		query.append(DESPESAS);
		query.append(" SET ");
		query.append(DESCRICAO);
		query.append("= ?, ");
		query.append(TIPO);
		query.append("= ?, ");
		query.append(VALOR);
		query.append("= ?, ");
		query.append(MES_ANO_REFERENCIA);
		query.append("= ?, ");		
		query.append(ID_UNIDADE);
		query.append("= ? ");		
		query.append(" WHERE ");
		query.append(ID);
		query.append("= ?");
		PreparedStatement statement = null;
		Connection con = C3P0DataSource.getInstance().getConnection();
		try {
			statement = con.prepareStatement(query.toString());
			SQLUtil.setValorPpreparedStatement(statement, 1, despesas.getDescricao(), java.sql.Types.VARCHAR);
			SQLUtil.setValorPpreparedStatement(statement, 2, despesas.getTipo(), java.sql.Types.INTEGER);
			SQLUtil.setValorPpreparedStatement(statement, 3, despesas.getValor(), java.sql.Types.DOUBLE);
			SQLUtil.setValorPpreparedStatement(statement, 4, despesas.getMesAnoReferencia(), java.sql.Types.DATE);
			SQLUtil.setValorPpreparedStatement(statement, 5, despesas.getUnidade().getId(), java.sql.Types.INTEGER);
			SQLUtil.setValorPpreparedStatement(statement, 6, despesas.getId(), java.sql.Types.INTEGER);
			this.consumoGasDespesasDAO.atualizar(despesas.getConsumoGasDespesas(), con);
			statement.executeUpdate();	
			
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
	public void atualizarDespesasCondominio(Despesas despesas) throws SQLException, Exception {
		StringBuffer query = new StringBuffer();
		query.append("UPDATE ");
		query.append(DESPESAS);
		query.append(" SET ");
		query.append(DESCRICAO);
		query.append("= ?, ");
		query.append(TIPO);
		query.append("= ?, ");
		query.append(VALOR);
		query.append("= ?, ");
		query.append(MES_ANO_REFERENCIA);
		query.append("= ?, ");		
		query.append(ID_CONDOMINIO);
		query.append("= ? ");		
		query.append(" WHERE ");
		query.append(ID);
		query.append("= ?");
		PreparedStatement statement = null;
		Connection con = C3P0DataSource.getInstance().getConnection();
		try {
			statement = con.prepareStatement(query.toString());
			SQLUtil.setValorPpreparedStatement(statement, 1, despesas.getDescricao(), java.sql.Types.VARCHAR);
			SQLUtil.setValorPpreparedStatement(statement, 2, despesas.getTipo(), java.sql.Types.INTEGER);
			SQLUtil.setValorPpreparedStatement(statement, 3, despesas.getValor(), java.sql.Types.DOUBLE);
			SQLUtil.setValorPpreparedStatement(statement, 4, despesas.getMesAnoReferencia(), java.sql.Types.DATE);
			SQLUtil.setValorPpreparedStatement(statement, 5, despesas.getCondominio().getId(), java.sql.Types.INTEGER);
			SQLUtil.setValorPpreparedStatement(statement, 6, despesas.getId(), java.sql.Types.INTEGER);
			statement.executeUpdate();			
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
	public void atualizarDespesasCondomino(Despesas despesas) throws SQLException, Exception {
		StringBuffer query = new StringBuffer();
		query.append("UPDATE ");
		query.append(DESPESAS);
		query.append(" SET ");
		query.append(DESCRICAO);
		query.append("= ?, ");
		query.append(TIPO);
		query.append("= ?, ");
		query.append(VALOR);
		query.append("= ?, ");
		query.append(MES_ANO_REFERENCIA);
		query.append("= ?, ");		
		query.append(ID_UNIDADE);
		query.append("= ? ");		
		query.append(" WHERE ");
		query.append(ID);
		query.append("= ?");
		PreparedStatement statement = null;
		Connection con = C3P0DataSource.getInstance().getConnection();
		try {
			statement = con.prepareStatement(query.toString());
			SQLUtil.setValorPpreparedStatement(statement, 1, despesas.getDescricao(), java.sql.Types.VARCHAR);
			SQLUtil.setValorPpreparedStatement(statement, 2, despesas.getTipo(), java.sql.Types.INTEGER);
			SQLUtil.setValorPpreparedStatement(statement, 3, despesas.getValor(), java.sql.Types.DOUBLE);
			SQLUtil.setValorPpreparedStatement(statement, 4, despesas.getMesAnoReferencia(), java.sql.Types.DATE);
			SQLUtil.setValorPpreparedStatement(statement, 5, despesas.getUnidade().getId(), java.sql.Types.INTEGER);
			SQLUtil.setValorPpreparedStatement(statement, 6, despesas.getId(), java.sql.Types.INTEGER);
			statement.executeUpdate();			
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
	public void salvarDespesasCondomino(Despesas despesas) throws SQLException, Exception {
		StringBuffer query = new StringBuffer();
		query.append("INSERT INTO "); 
		query.append(DESPESAS);
		query.append("("); 
		query.append(DESCRICAO);	
		query.append(",");
		query.append(VALOR);
		query.append(",");
		query.append(MES_ANO_REFERENCIA);
		query.append(",");	
		query.append(TIPO);
		query.append(",");	
		query.append(ID_UNIDADE);
		query.append(") ");
		query.append("VALUES(?,?,?,?,?)");
		PreparedStatement statement = null;		
		Connection con = C3P0DataSource.getInstance().getConnection();
		try {
			statement = con.prepareStatement(query.toString());
			SQLUtil.setValorPpreparedStatement(statement, 1, despesas.getDescricao(), java.sql.Types.VARCHAR);			
			SQLUtil.setValorPpreparedStatement(statement, 2, despesas.getValor(), java.sql.Types.DOUBLE);
			SQLUtil.setValorPpreparedStatement(statement, 3, despesas.getMesAnoReferencia(), java.sql.Types.DATE);
			SQLUtil.setValorPpreparedStatement(statement, 4, despesas.getTipo(), java.sql.Types.INTEGER);
			SQLUtil.setValorPpreparedStatement(statement, 5, despesas.getUnidade().getId(), java.sql.Types.INTEGER);			
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
	public void salvarDespesasCondominoGasUnidade(Despesas despesas) throws SQLException, Exception {
		StringBuffer query = new StringBuffer();
		query.append("INSERT INTO "); 
		query.append(DESPESAS);
		query.append("("); 
		query.append(DESCRICAO);	
		query.append(",");
		query.append(VALOR);
		query.append(",");
		query.append(MES_ANO_REFERENCIA);
		query.append(",");	
		query.append(TIPO);
		query.append(",");	
		query.append(ID_UNIDADE);
		query.append(") ");
		query.append("VALUES(?,?,?,?,?)");
		PreparedStatement statement = null;		
		Connection con = C3P0DataSource.getInstance().getConnection();		
		try {
			statement = con.prepareStatement(query.toString(),PreparedStatement.RETURN_GENERATED_KEYS);
			SQLUtil.setValorPpreparedStatement(statement, 1, despesas.getDescricao(), java.sql.Types.VARCHAR);			
			SQLUtil.setValorPpreparedStatement(statement, 2, despesas.getValor(), java.sql.Types.DOUBLE);
			SQLUtil.setValorPpreparedStatement(statement, 3, despesas.getMesAnoReferencia(), java.sql.Types.DATE);
			SQLUtil.setValorPpreparedStatement(statement, 4, despesas.getTipo(), java.sql.Types.INTEGER);
			SQLUtil.setValorPpreparedStatement(statement, 5, despesas.getUnidade().getId(), java.sql.Types.INTEGER);
			statement.executeUpdate();			
			ResultSet rs = statement.getGeneratedKeys();
			rs.next();
			despesas.getConsumoGasDespesas().setIdDespesas(rs.getInt(1));
			this.consumoGasDespesasDAO.salvar(despesas.getConsumoGasDespesas(), con);				
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
	public void salvarDespesasCondominio(Despesas despesas) throws SQLException, Exception {
		StringBuffer query = new StringBuffer();
		query.append("INSERT INTO "); 
		query.append(DESPESAS);
		query.append("("); 
		query.append(DESCRICAO);	
		query.append(",");
		query.append(VALOR);
		query.append(",");
		query.append(MES_ANO_REFERENCIA);
		query.append(",");	
		query.append(TIPO);
		query.append(",");	
		query.append(ID_CONDOMINIO);
		query.append(") ");
		query.append("VALUES(?,?,?,?,?)");
		PreparedStatement statement = null;		
		Connection con = C3P0DataSource.getInstance().getConnection();
		try {
			statement = con.prepareStatement(query.toString());
			SQLUtil.setValorPpreparedStatement(statement, 1, despesas.getDescricao(), java.sql.Types.VARCHAR);			
			SQLUtil.setValorPpreparedStatement(statement, 2, despesas.getValor(), java.sql.Types.DOUBLE);
			SQLUtil.setValorPpreparedStatement(statement, 3, despesas.getMesAnoReferencia(), java.sql.Types.DATE);
			SQLUtil.setValorPpreparedStatement(statement, 4, despesas.getTipo(), java.sql.Types.INTEGER);
			SQLUtil.setValorPpreparedStatement(statement, 5, despesas.getCondominio().getId(), java.sql.Types.INTEGER);			
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
	public List<Despesas> buscarPorIdCondominioEMesAnoReferencia(Integer idCondominio, Date mesAnoReferencia)
			throws SQLException, Exception {
		StringBuffer query = new StringBuffer();
		query.append("SELECT * FROM ");
		query.append(DESPESAS);
		query.append(" WHERE ");
		query.append(ID_CONDOMINIO);
		query.append(" = ?");
		query.append(" AND ");
		query.append(MES_ANO_REFERENCIA);
		query.append(" = ?");		
		query.append(";");		
		Connection con = C3P0DataSource.getInstance().getConnection();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		List<Despesas> listaDespesas = new ArrayList<Despesas>();
		Despesas despesas = null;
		try {
			preparedStatement = con.prepareStatement(query.toString());
			SQLUtil.setValorPpreparedStatement(preparedStatement, 1, idCondominio, java.sql.Types.INTEGER);
			SQLUtil.setValorPpreparedStatement(preparedStatement, 2, mesAnoReferencia, java.sql.Types.DATE);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()){
				despesas = new Despesas();
				despesas.setId((Integer) SQLUtil.getValorResultSet(resultSet, ID, java.sql.Types.INTEGER));
				despesas.setDescricao(String.valueOf(SQLUtil.getValorResultSet(resultSet, DESCRICAO, java.sql.Types.VARCHAR)));
				despesas.setValor((Double) SQLUtil.getValorResultSet(resultSet, VALOR, java.sql.Types.DOUBLE));
				despesas.setTipo((Integer) SQLUtil.getValorResultSet(resultSet, TIPO, java.sql.Types.INTEGER));
				despesas.setMesAnoReferencia((Date) SQLUtil.getValorResultSet(resultSet, MES_ANO_REFERENCIA, java.sql.Types.DATE));
				despesas.setCondominio(this.condominioDAO.buscarCondominioPorId((Integer) SQLUtil.getValorResultSet(resultSet, ID_CONDOMINIO, java.sql.Types.INTEGER),con));
				despesas.setConsumoGasDespesas(this.consumoGasDespesasDAO.buscarPorIdDespesa(despesas.getId(), con));				
				listaDespesas.add(despesas);
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
		return listaDespesas;		
	}

	@Override
	public List<Despesas> buscarPorIdUnidadeEMesAnoReferencia(Integer idUnidade, Date mesAnoReferencia)
			throws SQLException, Exception {
		StringBuffer query = new StringBuffer();
		query.append("SELECT * FROM ");
		query.append(DESPESAS);
		query.append(" WHERE ");
		query.append(ID_UNIDADE);
		query.append(" = ?");
		query.append(" AND ");
		query.append(MES_ANO_REFERENCIA);
		query.append(" = ?");		
		query.append(";");		
		Connection con = C3P0DataSource.getInstance().getConnection();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		List<Despesas> listaDespesas = new ArrayList<Despesas>();
		Despesas despesas = null;
		try {
			preparedStatement = con.prepareStatement(query.toString());
			SQLUtil.setValorPpreparedStatement(preparedStatement, 1, idUnidade, java.sql.Types.INTEGER);
			SQLUtil.setValorPpreparedStatement(preparedStatement, 2, mesAnoReferencia, java.sql.Types.DATE);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()){
				despesas = new Despesas();
				despesas.setId((Integer) SQLUtil.getValorResultSet(resultSet, ID, java.sql.Types.INTEGER));
				despesas.setDescricao(String.valueOf(SQLUtil.getValorResultSet(resultSet, DESCRICAO, java.sql.Types.VARCHAR)));
				despesas.setValor((Double) SQLUtil.getValorResultSet(resultSet, VALOR, java.sql.Types.DOUBLE));
				despesas.setTipo((Integer) SQLUtil.getValorResultSet(resultSet, TIPO, java.sql.Types.INTEGER));
				despesas.setMesAnoReferencia((Date) SQLUtil.getValorResultSet(resultSet, MES_ANO_REFERENCIA, java.sql.Types.DATE));
				despesas.setUnidade(this.unidadeDAO.buscarPorId((Integer) SQLUtil.getValorResultSet(resultSet, ID_UNIDADE, java.sql.Types.INTEGER), con));
				despesas.setConsumoGasDespesas(this.consumoGasDespesasDAO.buscarPorIdDespesa(despesas.getId(), con));
				listaDespesas.add(despesas);
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
		return listaDespesas;	
	}

	@Override
	public Despesas buscarPorIdUnidadeEMesAnoReferenciaETipo(Integer idUnidade, Date mesAnoReferencia,
			Integer tipo) throws SQLException, Exception {
		StringBuffer query = new StringBuffer();
		query.append("SELECT * FROM ");
		query.append(DESPESAS);
		query.append(" WHERE ");
		query.append(ID_UNIDADE);
		query.append(" = ?");
		query.append(" AND ");
		query.append(MES_ANO_REFERENCIA);
		query.append(" = ?");	
		query.append(" AND ");
		query.append(TIPO);
		query.append(" = ?");	
		query.append(";");		
		Connection con = C3P0DataSource.getInstance().getConnection();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;		
		Despesas despesas = null;
		try {
			preparedStatement = con.prepareStatement(query.toString());
			SQLUtil.setValorPpreparedStatement(preparedStatement, 1, idUnidade, java.sql.Types.INTEGER);
			SQLUtil.setValorPpreparedStatement(preparedStatement, 2, mesAnoReferencia, java.sql.Types.DATE);
			SQLUtil.setValorPpreparedStatement(preparedStatement, 3, tipo, java.sql.Types.INTEGER);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()){
				despesas = new Despesas();
				despesas.setId((Integer) SQLUtil.getValorResultSet(resultSet, ID, java.sql.Types.INTEGER));
				despesas.setDescricao(String.valueOf(SQLUtil.getValorResultSet(resultSet, DESCRICAO, java.sql.Types.VARCHAR)));
				despesas.setValor((Double) SQLUtil.getValorResultSet(resultSet, VALOR, java.sql.Types.DOUBLE));
				despesas.setTipo((Integer) SQLUtil.getValorResultSet(resultSet, TIPO, java.sql.Types.INTEGER));
				despesas.setMesAnoReferencia((Date) SQLUtil.getValorResultSet(resultSet, MES_ANO_REFERENCIA, java.sql.Types.DATE));
				despesas.setUnidade(this.unidadeDAO.buscarPorId((Integer) SQLUtil.getValorResultSet(resultSet, ID_UNIDADE, java.sql.Types.INTEGER), con));
				despesas.setConsumoGasDespesas(this.consumoGasDespesasDAO.buscarPorIdDespesa(despesas.getId(), con));
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
		return despesas;	
	}

}
