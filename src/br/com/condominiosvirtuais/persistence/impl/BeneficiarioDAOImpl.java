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

import br.com.condominiosvirtuais.entity.Beneficiario;
import br.com.condominiosvirtuais.exception.BusinessException;
import br.com.condominiosvirtuais.persistence.BeneficiarioDAO;
import br.com.condominiosvirtuais.persistence.EnderecoDAO;
import br.com.condominiosvirtuais.util.SQLUtil;

public class BeneficiarioDAOImpl implements Serializable, BeneficiarioDAO {

	private static final long serialVersionUID = 1L;
	
	private static Logger logger = Logger.getLogger(BeneficiarioDAOImpl.class); 
	
	private static final String BENEFICIARIO = "BENEFICIARIO";
	
	private static final String ID = "ID";
    
    private static final String NOME = "NOME";
    
    private static final String CPRF = "CPRF";
    
    private static final String SITUACAO = "SITUACAO";
    
    private static final String ID_CONDOMINIO = "ID_CONDOMINIO";    
    
    private static final String FK_PRE_CADASTRO_BOLETO_ID_BENEFICIARIO_BENEFICIARIO_ID = "FK_PRE_CADASTRO_BOLETO_ID_BENEFICIARIO_BENEFICIARIO_ID";
    
    private static final String FK_BOLETO_ID_BENEFICIARIO_BENEFICIARIO_ID = "FK_BOLETO_ID_BENEFICIARIO_BENEFICIARIO_ID";
    
    
    
    @Inject
    private EnderecoDAO enderecoDAO;

	@Override
	public void salvar(Beneficiario beneficiario) throws SQLException, BusinessException, Exception {
		PreparedStatement statement = null;
		Connection con = null;	 
		try {
			con = Conexao.getConexao();
			con.setAutoCommit(Boolean.FALSE);
			StringBuffer query = new StringBuffer();
			query.append("INSERT INTO "); 
			query.append(BENEFICIARIO); 
			query.append("(");
			query.append(NOME); 
			query.append(",");
			query.append(CPRF); 
			query.append(",");
			query.append(SITUACAO); 
			query.append(",");
			query.append(ID_CONDOMINIO); 
			query.append(") ");
			query.append("VALUES(?,?,?,?)");
			statement = con.prepareStatement(query.toString(),PreparedStatement.RETURN_GENERATED_KEYS);			
			SQLUtil.setValorPpreparedStatement(statement, 1, beneficiario.getNome(),java.sql.Types.VARCHAR);
			SQLUtil.setValorPpreparedStatement(statement, 2, beneficiario.getCprf(), java.sql.Types.BIGINT);
			SQLUtil.setValorPpreparedStatement(statement, 3, beneficiario.getSituacao(), java.sql.Types.BOOLEAN);
			SQLUtil.setValorPpreparedStatement(statement, 4, beneficiario.getIdCondominio(), java.sql.Types.INTEGER);
			statement.execute();
			ResultSet rs = statement.getGeneratedKeys(); 
			rs.next();
			beneficiario.getEndereco().setIdBeneficiario(rs.getInt(1));			
			this.enderecoDAO.salvarEndereco(beneficiario.getEndereco(), con);
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
	public List<Beneficiario> buscarPorIdCondominio(Integer idCondominio) throws SQLException, BusinessException, Exception {
		Connection con = Conexao.getConexao();		
		List<Beneficiario> listaBeneficiario = new ArrayList<Beneficiario>();
		Beneficiario beneficiario = null;
		StringBuffer query = new StringBuffer();
		query.append("SELECT * FROM ");
		query.append(BENEFICIARIO);
		query.append(" WHERE ");
		query.append(ID_CONDOMINIO);		
		query.append(" = ? ");
		query.append(";");		
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			preparedStatement = con.prepareStatement(query.toString());
			SQLUtil.setValorPpreparedStatement(preparedStatement, 1, idCondominio, java.sql.Types.INTEGER);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()){
				beneficiario = new Beneficiario();
				beneficiario.setId((Integer) SQLUtil.getValorResultSet(resultSet, ID, java.sql.Types.INTEGER));
				beneficiario.setNome(String.valueOf(SQLUtil.getValorResultSet(resultSet, NOME, java.sql.Types.VARCHAR)));
				beneficiario.setCprf((Long) (SQLUtil.getValorResultSet(resultSet, CPRF, java.sql.Types.BIGINT)));
				beneficiario.setSituacao((Boolean) (SQLUtil.getValorResultSet(resultSet, SITUACAO, java.sql.Types.BOOLEAN)));
				beneficiario.setIdCondominio((Integer) SQLUtil.getValorResultSet(resultSet, ID_CONDOMINIO, java.sql.Types.INTEGER));
				beneficiario.setEndereco(this.enderecoDAO.buscarEnderecoPorIdBeneficiario(beneficiario.getId(), con));								
				listaBeneficiario.add(beneficiario);
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
		return listaBeneficiario;
	}
	
	@Override
	public Beneficiario buscarPorId(Integer idBeneficiario, Connection con) throws SQLException, BusinessException, Exception {
		Beneficiario beneficiario = null;
		StringBuffer query = new StringBuffer();
		query.append("SELECT * FROM ");
		query.append(BENEFICIARIO);
		query.append(" WHERE ");
		query.append(ID);		
		query.append(" = ? ");
		query.append(";");		
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			preparedStatement = con.prepareStatement(query.toString());
			SQLUtil.setValorPpreparedStatement(preparedStatement, 1, idBeneficiario, java.sql.Types.INTEGER);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()){		
				beneficiario = new Beneficiario();
				beneficiario.setId((Integer) SQLUtil.getValorResultSet(resultSet, ID, java.sql.Types.INTEGER));
				beneficiario.setNome(String.valueOf(SQLUtil.getValorResultSet(resultSet, NOME, java.sql.Types.VARCHAR)));
				beneficiario.setCprf((Long) (SQLUtil.getValorResultSet(resultSet, CPRF, java.sql.Types.BIGINT)));
				beneficiario.setSituacao((Boolean) (SQLUtil.getValorResultSet(resultSet, SITUACAO, java.sql.Types.BOOLEAN)));
				beneficiario.setIdCondominio((Integer) SQLUtil.getValorResultSet(resultSet, ID_CONDOMINIO, java.sql.Types.INTEGER));
				beneficiario.setEndereco(this.enderecoDAO.buscarEnderecoPorIdBeneficiario(beneficiario.getId(), con));	
			}
		} catch (SQLException e) {			
			throw e;
		} catch (Exception e) {
			throw e;
		}				
		return beneficiario;
	}

	@Override
	public void atualizar(Beneficiario beneficiario) throws SQLException, BusinessException, Exception {
		Connection con = Conexao.getConexao();	
		con.setAutoCommit(Boolean.FALSE);
		this.enderecoDAO.atualizarEnderecoPorId(beneficiario.getEndereco(), con);
		StringBuffer query = new StringBuffer();
		query.append("UPDATE ");
		query.append(BENEFICIARIO);
		query.append(" SET ");
		query.append(NOME);
		query.append(" = ?, ");
		query.append(CPRF);
		query.append(" = ?, ");
		query.append(SITUACAO);
		query.append(" = ?, ");
		query.append(ID_CONDOMINIO);
		query.append(" = ? ");		
		query.append("WHERE ");
		query.append(ID);
		query.append("= ?");
		PreparedStatement statement = null;
		try {			
			statement = con.prepareStatement(query.toString());
			SQLUtil.setValorPpreparedStatement(statement, 1, beneficiario.getNome(), java.sql.Types.VARCHAR);
			SQLUtil.setValorPpreparedStatement(statement, 2, beneficiario.getCprf(), java.sql.Types.BIGINT);
			SQLUtil.setValorPpreparedStatement(statement, 3, beneficiario.getSituacao(), java.sql.Types.BOOLEAN);
			SQLUtil.setValorPpreparedStatement(statement, 4, beneficiario.getIdCondominio(), java.sql.Types.INTEGER);
			SQLUtil.setValorPpreparedStatement(statement, 5, beneficiario.getId(), java.sql.Types.INTEGER);			
			statement.executeUpdate();
			con.commit();
		} catch (SQLException e) {							
			con.rollback();
			throw e;
		}catch (Exception e) {
			con.rollback();
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
	public void excluir(Beneficiario beneficiario) throws SQLException, BusinessException, Exception {
		StringBuffer query = new StringBuffer();
		Connection con = Conexao.getConexao();
		con.setAutoCommit(Boolean.FALSE);
		PreparedStatement statement = null;
		try {
			this.enderecoDAO.excluirEndereco(beneficiario.getEndereco(), con);
			query.append("DELETE FROM ");
			query.append(BENEFICIARIO);
			query.append(" WHERE ");		
			query.append(ID);
			query.append(" = ?");
			statement = con.prepareStatement(query.toString());
			SQLUtil.setValorPpreparedStatement(statement, 1, beneficiario.getId(), java.sql.Types.INTEGER);
			statement.executeUpdate();	
			con.commit();
		} catch (SQLException e) {
			if (e.getMessage().contains(FK_PRE_CADASTRO_BOLETO_ID_BENEFICIARIO_BENEFICIARIO_ID)){
				throw new BusinessException("msg.beneficiario.excluirPreCadastroBoletoAssociado");				
			}else if (e.getMessage().contains(FK_BOLETO_ID_BENEFICIARIO_BENEFICIARIO_ID)){
				throw new BusinessException("msg.beneficiario.excluirBoletoAssociado");		
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

	@Override
	public List<Beneficiario> buscarPorIdCondominioESituacao(Integer idCondominio, Boolean situacao) throws SQLException, BusinessException, Exception {
		Connection con = Conexao.getConexao();		
		List<Beneficiario> listaBeneficiario = new ArrayList<Beneficiario>();
		Beneficiario beneficiario = null;
		StringBuffer query = new StringBuffer();
		query.append("SELECT * FROM ");
		query.append(BENEFICIARIO);
		query.append(" WHERE ");
		query.append(ID_CONDOMINIO);		
		query.append(" = ? ");
		query.append(" AND ");
		query.append(SITUACAO);
		query.append(" = ? ");
		query.append(";");		
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			preparedStatement = con.prepareStatement(query.toString());
			SQLUtil.setValorPpreparedStatement(preparedStatement, 1, idCondominio, java.sql.Types.INTEGER);
			SQLUtil.setValorPpreparedStatement(preparedStatement, 2, situacao, java.sql.Types.BOOLEAN);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()){
				beneficiario = new Beneficiario();
				beneficiario.setId((Integer) SQLUtil.getValorResultSet(resultSet, ID, java.sql.Types.INTEGER));
				beneficiario.setNome(String.valueOf(SQLUtil.getValorResultSet(resultSet, NOME, java.sql.Types.VARCHAR)));
				beneficiario.setCprf((Long) (SQLUtil.getValorResultSet(resultSet, CPRF, java.sql.Types.BIGINT)));
				beneficiario.setSituacao((Boolean) (SQLUtil.getValorResultSet(resultSet, SITUACAO, java.sql.Types.BOOLEAN)));
				beneficiario.setIdCondominio((Integer) SQLUtil.getValorResultSet(resultSet, ID_CONDOMINIO, java.sql.Types.INTEGER));
				beneficiario.setEndereco(this.enderecoDAO.buscarEnderecoPorIdBeneficiario(beneficiario.getId(), con));								
				listaBeneficiario.add(beneficiario);
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
		return listaBeneficiario;
	}  
    
    


}
