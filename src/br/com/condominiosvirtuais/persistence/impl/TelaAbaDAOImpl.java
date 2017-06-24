package br.com.condominiosvirtuais.persistence.impl;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import br.com.condominiosvirtuais.entity.TelaAba;
import br.com.condominiosvirtuais.persistence.TelaAbaDAO;
import br.com.condominiosvirtuais.util.SQLUtil;

public class TelaAbaDAOImpl implements TelaAbaDAO, Serializable {

	private static final long serialVersionUID = 1L;
	
	private static Logger logger = Logger.getLogger(TelaAbaDAOImpl.class);
	
	private static final String  TELA_ABA = " TELA_ABA";
	
	private static final String ID = "ID";	
	
	private static final String ID_TELA = "ID_TELA";
	
	private static final String ID_ABA = "ID_ABA";
	
	private static final String ACAO = "ACAO";
	
	

	@Override
	public void salvar(TelaAba telaAba, Connection con) throws SQLException, Exception {
		StringBuffer query = new StringBuffer();
		query.append("INSERT INTO "); 
		query.append(TELA_ABA);
		query.append("("); 
		query.append(ID_TELA); 
		query.append(",");		
		query.append(ID_ABA); 	
		query.append(",");
		query.append(ACAO); 
		query.append(") ");
		query.append("VALUES(?,?,?)");
		PreparedStatement statement = null;
		try {			
			statement = con.prepareStatement(query.toString());
			SQLUtil.setValorPpreparedStatement(statement, 1, telaAba.getIdTela(), java.sql.Types.INTEGER);			
			SQLUtil.setValorPpreparedStatement(statement, 2, telaAba.getIdAba(), java.sql.Types.INTEGER);
			SQLUtil.setValorPpreparedStatement(statement, 3, telaAba.getAcao(), java.sql.Types.VARCHAR);
			statement.executeUpdate();
		}catch (SQLException e) {
			con.rollback();
			throw e;
		} catch (Exception e) {
			con.rollback();
			throw e; 
		}		
		
	}	

	@Override
	public List<TelaAba> buscarPorIdTela(Integer idTela, Connection con) throws SQLException, Exception {
		StringBuffer query = new StringBuffer();
		query.append("SELECT * FROM ");
		query.append(TELA_ABA);
		query.append(" WHERE ");
		query.append(ID_TELA);
		query.append(" = ?");
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		TelaAba telaAba = null;	
		List<TelaAba> listaTelaAba = new ArrayList<TelaAba>();
		try {
			statement = con.prepareStatement(query.toString());
			SQLUtil.setValorPpreparedStatement(statement, 1,idTela, java.sql.Types.INTEGER);
			resultSet = statement.executeQuery();
			while(resultSet.next()){
				telaAba = new TelaAba();
				telaAba.setId((Integer) SQLUtil.getValorResultSet(resultSet, ID, java.sql.Types.INTEGER));
				telaAba.setIdAba((Integer) SQLUtil.getValorResultSet(resultSet, ID_ABA, java.sql.Types.INTEGER));
				telaAba.setIdTela((Integer) SQLUtil.getValorResultSet(resultSet, ID_TELA, java.sql.Types.INTEGER));
				telaAba.setAcao(String.valueOf(SQLUtil.getValorResultSet(resultSet, ACAO, java.sql.Types.VARCHAR)));
				listaTelaAba.add(telaAba);
			}
		}catch (SQLException e) {
			con.rollback();
			throw e;
		}catch (Exception e) {		
			con.rollback();
			throw e;
		}
		return listaTelaAba;
	}

	@Override
	public void excluirPorIdTela(Integer idTela, Connection con) throws SQLException, Exception {
		StringBuffer query = new StringBuffer();
		PreparedStatement statement = null;
		try {
			query.append("DELETE FROM ");
			query.append(TELA_ABA);
			query.append(" WHERE ");		
			query.append(ID_TELA);
			query.append(" = ?");
			statement = con.prepareStatement(query.toString());
			SQLUtil.setValorPpreparedStatement(statement, 1, idTela, java.sql.Types.INTEGER);
			statement.executeUpdate();
		} catch (SQLException e) {
			con.rollback();
			throw e;
		}catch (Exception e) {
			con.rollback();
			throw e;
		}
	}

}
