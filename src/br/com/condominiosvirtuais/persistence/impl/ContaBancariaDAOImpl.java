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
import br.com.condominiosvirtuais.entity.ContaBancariaCondominio;
import br.com.condominiosvirtuais.persistence.BancoDAO;
import br.com.condominiosvirtuais.persistence.ContaBancariaCondominioDAO;
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
    
    private static final String ID_BANCO = "ID_BANCO";
    
    @Inject
    private ContaBancariaCondominioDAO contaBancariaCondominioDAO;
    
    @Inject
    private BancoDAO bancoDAO;

	@Override
	public void salvar(ContaBancaria contaBancaria) throws SQLException, Exception {
		PreparedStatement statement = null;
		Connection con = null;	
		ContaBancariaCondominio contaBancariaCondominio = new ContaBancariaCondominio();
		try {
			con = Conexao.getConexao();
			con.setAutoCommit(Boolean.FALSE);
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
			query.append(") ");
			query.append("VALUES(?,?,?,?)");
			statement = con.prepareStatement(query.toString(),PreparedStatement.RETURN_GENERATED_KEYS);			
			SQLUtil.setValorPpreparedStatement(statement, 1, contaBancaria.getNumero(),java.sql.Types.VARCHAR);
			SQLUtil.setValorPpreparedStatement(statement, 2, contaBancaria.getAgencia(), java.sql.Types.VARCHAR);
			SQLUtil.setValorPpreparedStatement(statement, 3, contaBancaria.getBanco().getId(), java.sql.Types.INTEGER);
			SQLUtil.setValorPpreparedStatement(statement, 4, contaBancaria.getCarteira(), java.sql.Types.VARCHAR);
			statement.execute();
			ResultSet rs = statement.getGeneratedKeys(); 
			rs.next();
			contaBancariaCondominio.setIdContaBancaria(rs.getInt(1));
			contaBancariaCondominio.setIdCondominio(contaBancaria.getIdCondominio());
			this.contaBancariaCondominioDAO.salvar(contaBancariaCondominio, con);
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
	public List<ContaBancaria> buscarPorIdCondominio(Integer idCondominio) throws SQLException, Exception {
		Connection con = Conexao.getConexao();
		String pontoInterrogacao = "";
		List<ContaBancaria> listaContaBancaria = new ArrayList<ContaBancaria>();
		ContaBancaria contaBancaria = null;
		List<ContaBancariaCondominio> listaContaBancariaCondominio = this.contaBancariaCondominioDAO.buscarPorIdCondominio(idCondominio, con);
		if(listaContaBancariaCondominio.size() > 0){			
			Integer contador = 1;
			pontoInterrogacao = SQLUtil.popularInterrocacoes(listaContaBancariaCondominio.size());
			StringBuffer query = new StringBuffer();
			query.append("SELECT * FROM ");
			query.append(CONTA_BANCARIA);
			query.append(" WHERE ");
			query.append(ID);		
			query.append(" IN (");
			query.append(pontoInterrogacao);
			query.append(" )");
			query.append(";");		
			PreparedStatement preparedStatement = null;
			ResultSet resultSet = null;
			try {
				preparedStatement = con.prepareStatement(query.toString());
				for (ContaBancariaCondominio contaBancariaCondominio : listaContaBancariaCondominio) {
					SQLUtil.setValorPpreparedStatement(preparedStatement, contador++, contaBancariaCondominio.getIdContaBancaria(), java.sql.Types.INTEGER);				
				}
				resultSet = preparedStatement.executeQuery();
				while(resultSet.next()){
					contaBancaria = new ContaBancaria();
					contaBancaria.setId((Integer) SQLUtil.getValorResultSet(resultSet, ID, java.sql.Types.INTEGER));
					contaBancaria.setAgencia(String.valueOf(SQLUtil.getValorResultSet(resultSet, AGENCIA, java.sql.Types.VARCHAR)));
					contaBancaria.setNumero(String.valueOf(SQLUtil.getValorResultSet(resultSet,NUMERO, java.sql.Types.VARCHAR)));
					contaBancaria.setCarteira(String.valueOf(SQLUtil.getValorResultSet(resultSet,CARTEIRA, java.sql.Types.VARCHAR)));
					contaBancaria.setIdCondominio(idCondominio);
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
		}
		return listaContaBancaria;
	}

	@Override
	public void atualizar(ContaBancaria contaBancaria) throws SQLException, Exception {
		Connection con = Conexao.getConexao();		
		con.setAutoCommit(Boolean.FALSE);
		ContaBancariaCondominio contaBancariaCondominio = new ContaBancariaCondominio();
		contaBancariaCondominio.setIdCondominio(contaBancaria.getIdCondominio());
		contaBancariaCondominio.setIdContaBancaria(contaBancaria.getId());
		this.contaBancariaCondominioDAO.atualizarPorIdContaBancaria(contaBancariaCondominio, con);
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
		query.append(ID_BANCO);
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
			SQLUtil.setValorPpreparedStatement(statement, 4, contaBancaria.getBanco().getId(), java.sql.Types.INTEGER);
			SQLUtil.setValorPpreparedStatement(statement, 5, contaBancaria.getId(), java.sql.Types.INTEGER);
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
	public void excluir(ContaBancaria contaBancaria) throws SQLException, Exception {
		Connection con = Conexao.getConexao();		
		PreparedStatement statement = null;		
		try {
			con.setAutoCommit(Boolean.FALSE);
			this.contaBancariaCondominioDAO.excluirPorIdContaBancaria(contaBancaria.getId(), con);
			StringBuffer query = new StringBuffer();
			query.append("DELETE FROM ");
			query.append(CONTA_BANCARIA);
			query.append(" WHERE ");
			query.append(ID);
			query.append("= ?");
			statement = con.prepareStatement(query.toString());
			SQLUtil.setValorPpreparedStatement(statement, 1, contaBancaria.getId(), java.sql.Types.INTEGER);
			statement.executeUpdate();
			con.commit();
		} catch (SQLException e) {
			con.rollback();
			throw e;
		} catch (Exception e) {
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


}
