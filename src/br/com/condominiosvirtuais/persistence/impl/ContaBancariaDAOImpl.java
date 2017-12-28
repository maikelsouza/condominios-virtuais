package br.com.condominiosvirtuais.persistence.impl;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.apache.log4j.Logger;

import br.com.condominiosvirtuais.entity.ContaBancaria;
import br.com.condominiosvirtuais.exception.BusinessException;
import br.com.condominiosvirtuais.persistence.BancoDAO;
import br.com.condominiosvirtuais.persistence.ContaBancariaDAO;
import br.com.condominiosvirtuais.util.SQLUtil;

public class ContaBancariaDAOImpl implements ContaBancariaDAO, Serializable {
	
	private static final long serialVersionUID = 1L;

	private static Logger logger = Logger.getLogger(ContaBancariaDAOImpl.class); 
	
	private static final String CONTA_BANCARIA = "CONTA_BANCARIA";
	
    private static final String ID = "ID";
    
    private static final String NUMERO = "NUMERO";
    
    private static final String AGENCIA = "AGENCIA";
    
    private static final String CARTEIRA = "CARTEIRA";
    
    private static final String SITUACAO = "SITUACAO";
    
    private static final String TOKEN = "TOKEN";
    
    private static final String ID_BANCO = "ID_BANCO";
    
    private static final String ID_CONDOMINIO = "ID_CONDOMINIO";
    
    // Constraint referente a integridade do boleto e a conta bancária 
 	private static final String FK_BOLETO_ID_CONTA_BANCARIA_CONTA_BANCARIA_ID = "FK_BOLETO_ID_CONTA_BANCARIA_CONTA_BANCARIA_ID";
 	
    // Constraint referente a integridade do pré cadastro boleto e a conta bancária
 	private static final String FK_PRE_CADASTRO_BOLETO_ID_CONTA_BANCARIA_CONTA_BANCARIA_ID = "FK_PRE_CADASTRO_BOLETO_ID_CONTA_BANCARIA_CONTA_BANCARIA_ID";
 	
 	
    
    @Inject
    private BancoDAO bancoDAO;

	@Override
	public void salvar(ContaBancaria contaBancaria) throws SQLException, Exception {
		PreparedStatement statement = null;
		Connection con = null;
		try {
			con = C3P0DataSource.getInstance().getConnection();
			StringBuffer query = new StringBuffer();
			query.append("INSERT INTO "); 
			query.append(CONTA_BANCARIA); 
			query.append("(");
			query.append(NUMERO); 
			query.append(",");
			query.append(AGENCIA); 
			query.append(",");
			query.append(ID_BANCO);
			query.append(",");
			query.append(CARTEIRA);
			query.append(",");
			query.append(SITUACAO);
			query.append(",");
			query.append(TOKEN);
			query.append(",");
			query.append(ID_CONDOMINIO);
			query.append(") ");
			query.append("VALUES(?,?,?,?,?,?,?)");
			statement = con.prepareStatement(query.toString());
			SQLUtil.setValorPpreparedStatement(statement, 1, contaBancaria.getNumero(),java.sql.Types.VARCHAR);
			SQLUtil.setValorPpreparedStatement(statement, 2, contaBancaria.getAgencia(), java.sql.Types.VARCHAR);
			SQLUtil.setValorPpreparedStatement(statement, 3, contaBancaria.getBanco().getId(), java.sql.Types.INTEGER);
			SQLUtil.setValorPpreparedStatement(statement, 4, contaBancaria.getCarteira(), java.sql.Types.VARCHAR);
			SQLUtil.setValorPpreparedStatement(statement, 5, contaBancaria.getSituacao(), java.sql.Types.BOOLEAN);
			SQLUtil.setValorPpreparedStatement(statement, 6, contaBancaria.getToken(), java.sql.Types.VARCHAR);
			SQLUtil.setValorPpreparedStatement(statement, 7, contaBancaria.getIdCondominio(), java.sql.Types.INTEGER);
			statement.execute();
		} catch (SQLException e) {
			throw e;
		} catch (Exception e) {
			throw e;
		}finally{
			try {
				SQLUtil.closeStatement(statement);
				SQLUtil.closeConnection(con);				
			} catch (SQLException e) {
				logger.error("erro sqlstate "+e.getSQLState(), e);
			}
		}			
	}

	@Override
	public List<ContaBancaria> buscarPorIdCondominio(Integer idCondominio) throws SQLException, Exception {
		Connection con = C3P0DataSource.getInstance().getConnection();
		List<ContaBancaria> listaContaBancaria = new ArrayList<ContaBancaria>();
		ContaBancaria contaBancaria = null;
		StringBuffer query = new StringBuffer();
		query.append("SELECT * FROM ");
		query.append(CONTA_BANCARIA);
		query.append(" WHERE ");
		query.append(ID_CONDOMINIO);
		query.append(" = ?");
		query.append(";");		
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			preparedStatement = con.prepareStatement(query.toString());
			SQLUtil.setValorPpreparedStatement(preparedStatement, 1, idCondominio, java.sql.Types.INTEGER);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()){
				contaBancaria = new ContaBancaria();
				contaBancaria.setId((Integer) SQLUtil.getValorResultSet(resultSet, ID, java.sql.Types.INTEGER));
				contaBancaria.setAgencia(String.valueOf(SQLUtil.getValorResultSet(resultSet, AGENCIA, java.sql.Types.VARCHAR)));
				contaBancaria.setNumero(String.valueOf(SQLUtil.getValorResultSet(resultSet,NUMERO, java.sql.Types.VARCHAR)));
				contaBancaria.setToken(String.valueOf(SQLUtil.getValorResultSet(resultSet,TOKEN, java.sql.Types.VARCHAR)));
				contaBancaria.setCarteira(String.valueOf(SQLUtil.getValorResultSet(resultSet,CARTEIRA, java.sql.Types.VARCHAR)));
				contaBancaria.setSituacao((Boolean) SQLUtil.getValorResultSet(resultSet, SITUACAO, java.sql.Types.BOOLEAN));
				contaBancaria.setIdCondominio((Integer) SQLUtil.getValorResultSet(resultSet, ID_CONDOMINIO, java.sql.Types.INTEGER));
				contaBancaria.setBanco(this.bancoDAO.buscarPorId((Integer) SQLUtil.getValorResultSet(resultSet, ID_BANCO, java.sql.Types.INTEGER),con));				
				listaContaBancaria.add(contaBancaria);
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
		return listaContaBancaria;
	}
	
	@Override
	public ContaBancaria buscarPorId(Integer idContaBancaria, Connection con) throws SQLException, Exception {		
		ContaBancaria contaBancaria = null;
		StringBuffer query = new StringBuffer();
		query.append("SELECT * FROM ");
		query.append(CONTA_BANCARIA);
		query.append(" WHERE ");
		query.append(ID);		
		query.append(" = ? ");
		query.append(";");		
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			preparedStatement = con.prepareStatement(query.toString());
			SQLUtil.setValorPpreparedStatement(preparedStatement, 1, idContaBancaria, java.sql.Types.INTEGER);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()){
				contaBancaria = new ContaBancaria();
				contaBancaria.setId((Integer) SQLUtil.getValorResultSet(resultSet, ID, java.sql.Types.INTEGER));
				contaBancaria.setAgencia(String.valueOf(SQLUtil.getValorResultSet(resultSet, AGENCIA, java.sql.Types.VARCHAR)));
				contaBancaria.setNumero(String.valueOf(SQLUtil.getValorResultSet(resultSet,NUMERO, java.sql.Types.VARCHAR)));
				contaBancaria.setCarteira(String.valueOf(SQLUtil.getValorResultSet(resultSet,CARTEIRA, java.sql.Types.VARCHAR)));
				contaBancaria.setSituacao((Boolean)SQLUtil.getValorResultSet(resultSet,SITUACAO, java.sql.Types.BOOLEAN));
				contaBancaria.setToken(String.valueOf(SQLUtil.getValorResultSet(resultSet,TOKEN, java.sql.Types.VARCHAR)));
				contaBancaria.setIdCondominio((Integer) SQLUtil.getValorResultSet(resultSet, ID_CONDOMINIO, java.sql.Types.INTEGER));
				contaBancaria.setBanco(this.bancoDAO.buscarPorId((Integer) SQLUtil.getValorResultSet(resultSet, ID_BANCO, java.sql.Types.INTEGER),con));
			}
		} catch (SQLException e) {
			throw e;
		} catch (Exception e) {
			throw e;	
		}
		return contaBancaria;
	}

	@Override
	public void atualizar(ContaBancaria contaBancaria) throws SQLException, Exception {
		Connection con = C3P0DataSource.getInstance().getConnection();		
		StringBuffer query = new StringBuffer();
		query.append("UPDATE ");
		query.append(CONTA_BANCARIA);
		query.append(" SET ");
		query.append(AGENCIA);
		query.append(" = ?, ");
		query.append(CARTEIRA);
		query.append(" = ?, ");
		query.append(NUMERO);
		query.append(" = ?, ");
		query.append(SITUACAO);
		query.append(" = ?, ");
		query.append(TOKEN);
		query.append(" = ?, ");
		query.append(ID_BANCO);
		query.append(" = ?, ");
		query.append(ID_CONDOMINIO);
		query.append(" = ? ");
		query.append("WHERE ");
		query.append(ID);
		query.append("= ?");
		PreparedStatement statement = null;
		try {			
			statement = con.prepareStatement(query.toString());
			SQLUtil.setValorPpreparedStatement(statement, 1, contaBancaria.getAgencia(), java.sql.Types.VARCHAR);
			SQLUtil.setValorPpreparedStatement(statement, 2, contaBancaria.getCarteira(), java.sql.Types.VARCHAR);
			SQLUtil.setValorPpreparedStatement(statement, 3, contaBancaria.getNumero(), java.sql.Types.VARCHAR);
			SQLUtil.setValorPpreparedStatement(statement, 4, contaBancaria.getSituacao(), java.sql.Types.BOOLEAN);
			SQLUtil.setValorPpreparedStatement(statement, 5, contaBancaria.getToken(), java.sql.Types.VARCHAR);
			SQLUtil.setValorPpreparedStatement(statement, 6, contaBancaria.getBanco().getId(), java.sql.Types.INTEGER);
			SQLUtil.setValorPpreparedStatement(statement, 7, contaBancaria.getIdCondominio(), java.sql.Types.INTEGER);
			SQLUtil.setValorPpreparedStatement(statement, 8, contaBancaria.getId(), java.sql.Types.INTEGER);
			statement.executeUpdate();
		} catch (SQLException e) {		
			throw e;
		}catch (Exception e) {
			throw e;	
		}finally {
			try {
				statement.close();
				con.close();				
			} catch (SQLException e) {
				logger.error("erro sqlstate "+e.getSQLState(), e);			
			}
		}
		
	}

	@Override
	public void excluir(ContaBancaria contaBancaria) throws SQLException, BusinessException, Exception {
		Connection con = C3P0DataSource.getInstance().getConnection();		
		PreparedStatement statement = null;		
		try {
			StringBuffer query = new StringBuffer();
			query.append("DELETE FROM ");
			query.append(CONTA_BANCARIA);
			query.append(" WHERE ");
			query.append(ID);
			query.append("= ?");
			statement = con.prepareStatement(query.toString());
			SQLUtil.setValorPpreparedStatement(statement, 1, contaBancaria.getId(), java.sql.Types.INTEGER);
			statement.executeUpdate();
		} catch (SQLException e) {
			if (e.getMessage().contains(FK_BOLETO_ID_CONTA_BANCARIA_CONTA_BANCARIA_ID)){
				throw new BusinessException("msg.contaBancaria.excluirBoletoAssociado");
			}else if (e.getMessage().contains(FK_PRE_CADASTRO_BOLETO_ID_CONTA_BANCARIA_CONTA_BANCARIA_ID)){
				throw new BusinessException("msg.contaBancaria.excluirPreCadastroboletoAssociado");			
			}else{
				throw e;				
			}
 
		} catch (Exception e) {
			throw e;
		}finally {
			try {
				statement.close();
				con.close();				
			} catch (SQLException e) {
				logger.error("erro sqlstate "+e.getSQLState(), e);			
			}
		}	
	}

	@Override
	public List<ContaBancaria> buscarPorIdCondominioESituacao(Integer idCondominio, Boolean situacao) throws SQLException, Exception {
		Connection con = C3P0DataSource.getInstance().getConnection();
		List<ContaBancaria> listaContaBancaria = new ArrayList<ContaBancaria>();
		ContaBancaria contaBancaria = null;
		StringBuffer query = new StringBuffer();
		query.append("SELECT * FROM ");
		query.append(CONTA_BANCARIA);
		query.append(" WHERE ");
		query.append(ID_CONDOMINIO);
		query.append(" = ?");
		query.append(" AND ");
		query.append(SITUACAO);
		query.append(" = ?");
		query.append(";");		
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			preparedStatement = con.prepareStatement(query.toString());
			SQLUtil.setValorPpreparedStatement(preparedStatement, 1, idCondominio, java.sql.Types.INTEGER);
			SQLUtil.setValorPpreparedStatement(preparedStatement, 2, situacao, java.sql.Types.BOOLEAN);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()){
				contaBancaria = new ContaBancaria();
				contaBancaria.setId((Integer) SQLUtil.getValorResultSet(resultSet, ID, java.sql.Types.INTEGER));
				contaBancaria.setAgencia(String.valueOf(SQLUtil.getValorResultSet(resultSet, AGENCIA, java.sql.Types.VARCHAR)));
				contaBancaria.setNumero(String.valueOf(SQLUtil.getValorResultSet(resultSet,NUMERO, java.sql.Types.VARCHAR)));
				contaBancaria.setCarteira(String.valueOf(SQLUtil.getValorResultSet(resultSet,CARTEIRA, java.sql.Types.VARCHAR)));
				contaBancaria.setToken(String.valueOf(SQLUtil.getValorResultSet(resultSet,TOKEN, java.sql.Types.VARCHAR)));
				contaBancaria.setSituacao((Boolean) SQLUtil.getValorResultSet(resultSet, SITUACAO, java.sql.Types.BOOLEAN));
				contaBancaria.setIdCondominio((Integer) SQLUtil.getValorResultSet(resultSet, ID_CONDOMINIO, java.sql.Types.INTEGER));
				contaBancaria.setBanco(this.bancoDAO.buscarPorId((Integer) SQLUtil.getValorResultSet(resultSet, ID_BANCO, java.sql.Types.INTEGER),con));				
				listaContaBancaria.add(contaBancaria);
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
		return listaContaBancaria;
	}


}
