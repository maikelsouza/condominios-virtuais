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
    
    private static final String ID_CONDOMINIO = "ID_CONDOMINIO";
    
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
			query.append(ID_CONDOMINIO); 
			query.append(") ");
			query.append("VALUES(?,?,?)");
			statement = con.prepareStatement(query.toString(),PreparedStatement.RETURN_GENERATED_KEYS);			
			SQLUtil.setValorPpreparedStatement(statement, 1, beneficiario.getNome(),java.sql.Types.VARCHAR);
			SQLUtil.setValorPpreparedStatement(statement, 2, beneficiario.getCprf(), java.sql.Types.BIGINT);
			SQLUtil.setValorPpreparedStatement(statement, 3, beneficiario.getIdCondominio(), java.sql.Types.INTEGER);
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
