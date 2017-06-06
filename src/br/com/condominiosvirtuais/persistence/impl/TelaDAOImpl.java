package br.com.condominiosvirtuais.persistence.impl;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.inject.Inject;

import org.apache.log4j.Logger;

import br.com.condominiosvirtuais.entity.Tela;
import br.com.condominiosvirtuais.persistence.AbaDAO;
import br.com.condominiosvirtuais.persistence.TelaDAO;
import br.com.condominiosvirtuais.util.SQLUtil;

public class TelaDAOImpl implements TelaDAO, Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private static Logger logger = Logger.getLogger(TelaDAOImpl.class);
	
	@Inject
	private AbaDAO abaDAO;
	
	private static final String TELA = "TELA";
	
	private static final String ID = "ID";
	
	private static final String NOME = "NOME";
	
	private static final String NOME_ARQUIVO = "NOME_ARQUIVO";

	@Override
	public Tela buscarPorId(Integer id) throws SQLException, Exception {
		StringBuffer query = new StringBuffer();
		query.append("SELECT * FROM ");
		query.append(TELA);
		query.append(" WHERE ");
		query.append(ID);
		query.append(" = ?");
		query.append(";");		
		Connection con = Conexao.getConexao();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Tela tela = null;
		try {
			preparedStatement = con.prepareStatement(query.toString());
			SQLUtil.setValorPpreparedStatement(preparedStatement, 1, id, java.sql.Types.INTEGER);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()){
				tela = new Tela();
				tela.setId((Integer) SQLUtil.getValorResultSet(resultSet, ID, java.sql.Types.INTEGER));
				tela.setNome(String.valueOf(SQLUtil.getValorResultSet(resultSet, NOME, java.sql.Types.VARCHAR)));
				tela.setNomeArquivo(String.valueOf(SQLUtil.getValorResultSet(resultSet, NOME_ARQUIVO, java.sql.Types.VARCHAR)));			
				tela.setListaAbas(this.abaDAO.buscarPorIdTela(tela.getId(), con));
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
		return tela;
	}
	
	
	

}
