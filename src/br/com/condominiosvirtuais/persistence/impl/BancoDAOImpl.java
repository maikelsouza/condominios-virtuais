package br.com.condominiosvirtuais.persistence.impl;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import br.com.condominiosvirtuais.entity.Banco;
import br.com.condominiosvirtuais.persistence.BancoDAO;
import br.com.condominiosvirtuais.util.SQLUtil;

public class BancoDAOImpl implements BancoDAO, Serializable {
	
	private static final long serialVersionUID = 1L;

	private static Logger logger = Logger.getLogger(BancoDAOImpl.class); 
	
	private static final String BANCO = "BANCO";
	
    private static final String ID = "ID";
    
    private static final String NOME = "NOME";
    
    private static final String CODIGO = "CODIGO";

	@Override
	public List<Banco> buscarTodos() throws SQLException, Exception {
		StringBuffer query = new StringBuffer();
		query.append("SELECT * FROM ");
		query.append(BANCO);
		query.append(" ORDER BY ");
		query.append(NOME);
		query.append(";");		
		Connection con = Conexao.getConexao();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		List<Banco> listaBanco = new ArrayList<Banco>();
		Banco banco = null;
		try {
			preparedStatement = con.prepareStatement(query.toString());
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()){
				banco = new Banco();
				banco.setId((Integer) SQLUtil.getValorResultSet(resultSet, ID, java.sql.Types.INTEGER));
				banco.setCodigo(String.valueOf(SQLUtil.getValorResultSet(resultSet, CODIGO, java.sql.Types.VARCHAR)));
				banco.setNome(String.valueOf(SQLUtil.getValorResultSet(resultSet, NOME, java.sql.Types.VARCHAR)));				
				listaBanco.add(banco);
			}
		}catch (SQLException e) {
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
		return listaBanco;
	}

	@Override
	public Banco buscarPorId(Integer idBanco, Connection con) throws SQLException, Exception {
		StringBuffer query = new StringBuffer();
		query.append("SELECT * FROM ");
		query.append(BANCO);
		query.append(" WHERE ");
		query.append(ID);		
		query.append(" = ?");
		query.append(";");
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Banco banco = null;
		try {
			preparedStatement = con.prepareStatement(query.toString());
			SQLUtil.setValorPpreparedStatement(preparedStatement, 1, idBanco, java.sql.Types.INTEGER);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()){
				banco = new Banco();
				banco.setId((Integer) SQLUtil.getValorResultSet(resultSet, ID, java.sql.Types.INTEGER));
				banco.setCodigo(String.valueOf(SQLUtil.getValorResultSet(resultSet, CODIGO, java.sql.Types.VARCHAR)));
				banco.setNome(String.valueOf(SQLUtil.getValorResultSet(resultSet, NOME, java.sql.Types.VARCHAR)));	
			}
		}catch (SQLException e) {
			throw e;
		}catch (Exception e) {		
			throw e;		
		}	
		return banco;
	}

}
