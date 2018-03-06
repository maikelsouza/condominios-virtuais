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

import br.com.condominiosvirtuais.entity.PreCadastroBoleto;
import br.com.condominiosvirtuais.exception.BusinessException;
import br.com.condominiosvirtuais.persistence.BeneficiarioDAO;
import br.com.condominiosvirtuais.persistence.ContaBancariaDAO;
import br.com.condominiosvirtuais.persistence.PreCadastroBoletoDAO;
import br.com.condominiosvirtuais.util.SQLUtil;

public class PreCadastroBoletoDAOImpl implements PreCadastroBoletoDAO, Serializable {

	private static final long serialVersionUID = 1L;
	
	private static Logger logger = Logger.getLogger(PreCadastroBoletoDAOImpl.class);
	
	private static final String PRE_CADASTRO_BOLETO = "PRE_CADASTRO_BOLETO";
	
	private static final String ID = "ID";	
	
	private static final String DIA_MES_VENCIMENTO = "DIA_MES_VENCIMENTO";
	
	private static final String NOME = "NOME";
	
	private static final String INSTRUCAO1 = "INSTRUCAO1";
	
	private static final String INSTRUCAO2 = "INSTRUCAO2";
	
	private static final String INSTRUCAO3 = "INSTRUCAO3";
	
	private static final String PRINCIPAL = "PRINCIPAL";
	
	private static final String ID_CONDOMINIO = "ID_CONDOMINIO";
	
	private static final String ID_BENEFICIARIO = "ID_BENEFICIARIO";
	
	private static final String ID_CONTA_BANCARIA = "ID_CONTA_BANCARIA";
	
	// Constraint referente a integridade do nome do pré cadastro com o condomínio
	private static final String UQ_PRE_CADASTRO_BOLETO_NOME_ID_CONDOMINIO = "UQ_PRE_CADASTRO_BOLETO_NOME_ID_CONDOMINIO";
	
	@Inject
	private Instance<BeneficiarioDAO> beneficiarioDAO = null;
	
	@Inject
	private Instance<ContaBancariaDAO> contaBancariaDAO = null;
	
	
	
	@Override
	public void salvar(PreCadastroBoleto preCadastroBoleto) throws SQLException, BusinessException, Exception {
		PreparedStatement statement = null;
		Connection con = null;
		con = C3P0DataSource.getInstance().getConnection();
		StringBuffer query = new StringBuffer();
		query.append("INSERT INTO "); 
		query.append(PRE_CADASTRO_BOLETO); 
		query.append("(");
		query.append(DIA_MES_VENCIMENTO);
		query.append(",");		
		query.append(NOME);
		query.append(",");
		query.append(INSTRUCAO1);
		query.append(",");
		query.append(INSTRUCAO2);		
		query.append(",");
		query.append(INSTRUCAO3);		
		query.append(",");
		query.append(PRINCIPAL);		
		query.append(",");
		query.append(ID_CONDOMINIO);
		query.append(",");		
		query.append(ID_BENEFICIARIO);
		query.append(",");
		query.append(ID_CONTA_BANCARIA);
		query.append(") ");
		query.append("VALUES(?,?,?,?,?,?,?,?,?)");
		try {
			statement = con.prepareStatement(query.toString());
			SQLUtil.setValorPpreparedStatement(statement,1, preCadastroBoleto.getDiaMesVencimento(), java.sql.Types.INTEGER);			
			SQLUtil.setValorPpreparedStatement(statement,2, preCadastroBoleto.getNome(), java.sql.Types.VARCHAR);			
			SQLUtil.setValorPpreparedStatement(statement,3, preCadastroBoleto.getInstrucao1(), java.sql.Types.VARCHAR);
			SQLUtil.setValorPpreparedStatement(statement,4, preCadastroBoleto.getInstrucao2(), java.sql.Types.VARCHAR);
			SQLUtil.setValorPpreparedStatement(statement,5, preCadastroBoleto.getInstrucao3(), java.sql.Types.VARCHAR);
			SQLUtil.setValorPpreparedStatement(statement,6, preCadastroBoleto.getPrincipal(), java.sql.Types.BOOLEAN);
			SQLUtil.setValorPpreparedStatement(statement,7, preCadastroBoleto.getIdCondominio(), java.sql.Types.INTEGER);
			SQLUtil.setValorPpreparedStatement(statement,8, preCadastroBoleto.getBeneficiario().getId(), java.sql.Types.INTEGER);			
			SQLUtil.setValorPpreparedStatement(statement,9, preCadastroBoleto.getContaBancaria().getId(), java.sql.Types.INTEGER);
			statement.execute();				
		}catch (SQLException e) {		
			if (e.getMessage().contains(UQ_PRE_CADASTRO_BOLETO_NOME_ID_CONDOMINIO)){
				throw new BusinessException("msg.preCadastroBoleto.nomeIdcondominioUnicos"); 
			}else{			
				throw e;		
			}			
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
	public void excluirPorId(Integer idPreCadastroBoleto) throws SQLException, Exception {
		StringBuffer query = new StringBuffer();
		Connection con = C3P0DataSource.getInstance().getConnection();
		PreparedStatement statement = null;
		try {			
			query.append("DELETE FROM ");
			query.append(PRE_CADASTRO_BOLETO);
			query.append(" WHERE ");		
			query.append(ID);
			query.append(" = ?");
			statement = con.prepareStatement(query.toString());			
			SQLUtil.setValorPpreparedStatement(statement, 1, idPreCadastroBoleto, java.sql.Types.INTEGER);			
			statement.executeUpdate();
		} catch (SQLException e) {				
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
	public void atualizar(PreCadastroBoleto preCadastroBoleto) throws SQLException, BusinessException, Exception {
		StringBuffer query = new StringBuffer();
		query.append("UPDATE ");
		query.append(PRE_CADASTRO_BOLETO);
		query.append(" SET ");		
		query.append(DIA_MES_VENCIMENTO);
		query.append("= ?, ");
		query.append(NOME);
		query.append("= ?, ");
		query.append(INSTRUCAO1);
		query.append("= ?, ");
		query.append(INSTRUCAO2);
		query.append("= ?, ");
		query.append(INSTRUCAO3);
		query.append("= ?, ");
		query.append(PRINCIPAL);
		query.append("= ?, ");
		query.append(ID_CONDOMINIO);
		query.append("= ?, ");
		query.append(ID_BENEFICIARIO);
		query.append("= ?, ");
		query.append(ID_CONTA_BANCARIA);
		query.append("= ? ");
		query.append(" WHERE ");
		query.append(ID);
		query.append("= ?");
		Connection con = C3P0DataSource.getInstance().getConnection();
		PreparedStatement statement = null;
		try {
			statement = con.prepareStatement(query.toString());
			SQLUtil.setValorPpreparedStatement(statement, 1, preCadastroBoleto.getDiaMesVencimento(), java.sql.Types.INTEGER);
			SQLUtil.setValorPpreparedStatement(statement, 2, preCadastroBoleto.getNome(), java.sql.Types.VARCHAR);
			SQLUtil.setValorPpreparedStatement(statement, 3, preCadastroBoleto.getInstrucao1(), java.sql.Types.VARCHAR);
			SQLUtil.setValorPpreparedStatement(statement, 4, preCadastroBoleto.getInstrucao2(), java.sql.Types.VARCHAR);
			SQLUtil.setValorPpreparedStatement(statement, 5, preCadastroBoleto.getInstrucao3(), java.sql.Types.VARCHAR);
			SQLUtil.setValorPpreparedStatement(statement, 6, preCadastroBoleto.getPrincipal(), java.sql.Types.BOOLEAN);
			SQLUtil.setValorPpreparedStatement(statement, 7, preCadastroBoleto.getIdCondominio(), java.sql.Types.INTEGER);
			SQLUtil.setValorPpreparedStatement(statement, 8, preCadastroBoleto.getBeneficiario().getId(), java.sql.Types.INTEGER);
			SQLUtil.setValorPpreparedStatement(statement, 9, preCadastroBoleto.getContaBancaria().getId(), java.sql.Types.INTEGER);
			SQLUtil.setValorPpreparedStatement(statement, 10, preCadastroBoleto.getId(), java.sql.Types.INTEGER);
			statement.executeUpdate();			
		}catch (SQLException e) {		
			if (e.getMessage().contains(UQ_PRE_CADASTRO_BOLETO_NOME_ID_CONDOMINIO)){
				throw new BusinessException("msg.preCadastroBoleto.nomeIdcondominioUnicos"); 
			}else{			
				throw e;		
			}			
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
	public List<PreCadastroBoleto> buscarPorIdCondominio(Integer idCondominio) throws SQLException, Exception {
		StringBuffer query = new StringBuffer();
		query.append("SELECT * FROM ");
		query.append(PRE_CADASTRO_BOLETO);
		query.append(" WHERE ");
		query.append(ID_CONDOMINIO);
		query.append(" = ?");
		query.append(";");		
		Connection con = C3P0DataSource.getInstance().getConnection();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		List<PreCadastroBoleto> listaPreCadastroBoleto = new ArrayList<PreCadastroBoleto>();
		PreCadastroBoleto preCadastroBoleto = null;
		try {
			preparedStatement = con.prepareStatement(query.toString());
			SQLUtil.setValorPpreparedStatement(preparedStatement, 1, idCondominio, java.sql.Types.INTEGER);			
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()){
				preCadastroBoleto = new PreCadastroBoleto();				
				preCadastroBoleto.setId((Integer) SQLUtil.getValorResultSet(resultSet, ID, java.sql.Types.INTEGER));
				preCadastroBoleto.setDiaMesVencimento((Integer) SQLUtil.getValorResultSet(resultSet, DIA_MES_VENCIMENTO, java.sql.Types.INTEGER));
				preCadastroBoleto.setNome(String.valueOf(SQLUtil.getValorResultSet(resultSet, NOME, java.sql.Types.VARCHAR)));				
				preCadastroBoleto.setIdCondominio((Integer) SQLUtil.getValorResultSet(resultSet, ID_CONDOMINIO, java.sql.Types.INTEGER));
				preCadastroBoleto.setInstrucao1(String.valueOf(SQLUtil.getValorResultSet(resultSet, INSTRUCAO1, java.sql.Types.VARCHAR)));
				preCadastroBoleto.setInstrucao2(String.valueOf(SQLUtil.getValorResultSet(resultSet, INSTRUCAO2, java.sql.Types.VARCHAR)));
				preCadastroBoleto.setInstrucao3(String.valueOf(SQLUtil.getValorResultSet(resultSet, INSTRUCAO3, java.sql.Types.VARCHAR)));
				preCadastroBoleto.setPrincipal((Boolean) SQLUtil.getValorResultSet(resultSet, PRINCIPAL, java.sql.Types.BOOLEAN));
				preCadastroBoleto.setBeneficiario(this.beneficiarioDAO.get().buscarPorId((Integer) SQLUtil.getValorResultSet(resultSet, ID_BENEFICIARIO, java.sql.Types.INTEGER), con));				
				preCadastroBoleto.setContaBancaria(this.contaBancariaDAO.get().buscarPorId((Integer) SQLUtil.getValorResultSet(resultSet, ID_CONTA_BANCARIA, java.sql.Types.INTEGER), con));
				listaPreCadastroBoleto.add(preCadastroBoleto);				
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
		return listaPreCadastroBoleto;
	}
	
	@Override
	public List<PreCadastroBoleto> buscarPorIdCondominioEPrincipal(Integer idCondominio, Boolean principal) throws SQLException, Exception {
		StringBuffer query = new StringBuffer();
		query.append("SELECT * FROM ");
		query.append(PRE_CADASTRO_BOLETO);
		query.append(" WHERE ");
		query.append(ID_CONDOMINIO);
		query.append(" = ?");
		query.append(" AND ");
		query.append(PRINCIPAL);
		query.append(" = ?");
		query.append(";");		
		Connection con = C3P0DataSource.getInstance().getConnection();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;		
		PreCadastroBoleto preCadastroBoleto = null;
		List<PreCadastroBoleto> listaPreCadastroBoleto = new ArrayList<PreCadastroBoleto>();
		try {
			preparedStatement = con.prepareStatement(query.toString());
			SQLUtil.setValorPpreparedStatement(preparedStatement, 1, idCondominio, java.sql.Types.INTEGER);			
			SQLUtil.setValorPpreparedStatement(preparedStatement, 2, principal, java.sql.Types.BOOLEAN);			
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()){
				preCadastroBoleto = new PreCadastroBoleto();				
				preCadastroBoleto.setId((Integer) SQLUtil.getValorResultSet(resultSet, ID, java.sql.Types.INTEGER));
				preCadastroBoleto.setDiaMesVencimento((Integer) SQLUtil.getValorResultSet(resultSet, DIA_MES_VENCIMENTO, java.sql.Types.INTEGER));
				preCadastroBoleto.setNome(String.valueOf(SQLUtil.getValorResultSet(resultSet, NOME, java.sql.Types.VARCHAR)));				
				preCadastroBoleto.setIdCondominio((Integer) SQLUtil.getValorResultSet(resultSet, ID_CONDOMINIO, java.sql.Types.INTEGER));
				preCadastroBoleto.setInstrucao1(String.valueOf(SQLUtil.getValorResultSet(resultSet, INSTRUCAO1, java.sql.Types.VARCHAR)));
				preCadastroBoleto.setInstrucao2(String.valueOf(SQLUtil.getValorResultSet(resultSet, INSTRUCAO2, java.sql.Types.VARCHAR)));
				preCadastroBoleto.setInstrucao3(String.valueOf(SQLUtil.getValorResultSet(resultSet, INSTRUCAO3, java.sql.Types.VARCHAR)));
				preCadastroBoleto.setPrincipal((Boolean) SQLUtil.getValorResultSet(resultSet, PRINCIPAL, java.sql.Types.BOOLEAN));
				preCadastroBoleto.setBeneficiario(this.beneficiarioDAO.get().buscarPorId((Integer) SQLUtil.getValorResultSet(resultSet, ID_BENEFICIARIO, java.sql.Types.INTEGER), con));				
				preCadastroBoleto.setContaBancaria(this.contaBancariaDAO.get().buscarPorId((Integer) SQLUtil.getValorResultSet(resultSet, ID_CONTA_BANCARIA, java.sql.Types.INTEGER), con));
				listaPreCadastroBoleto.add(preCadastroBoleto);
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
		return listaPreCadastroBoleto;
	}

	@Override
	public void atualizarPrincipal(Integer id, Boolean principal) throws SQLException, BusinessException, Exception {
		StringBuffer query = new StringBuffer();
		query.append("UPDATE ");
		query.append(PRE_CADASTRO_BOLETO);
		query.append(" SET ");
		query.append(PRINCIPAL);
		query.append("= ? ");		
		query.append(" WHERE ");
		query.append(ID);
		query.append("= ?");
		Connection con = C3P0DataSource.getInstance().getConnection();
		PreparedStatement statement = null;
		try {
			statement = con.prepareStatement(query.toString());
			SQLUtil.setValorPpreparedStatement(statement, 1, principal, java.sql.Types.BOOLEAN);
			SQLUtil.setValorPpreparedStatement(statement, 2, id, java.sql.Types.INTEGER);			
			statement.executeUpdate();			
		}catch (SQLException e) {
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
