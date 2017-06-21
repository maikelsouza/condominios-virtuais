package br.com.condominiosvirtuais.persistence.impl;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import br.com.condominiosvirtuais.entity.GrupoUsuarioTela;
import br.com.condominiosvirtuais.entity.TelaAbaTela;
import br.com.condominiosvirtuais.persistence.TelaAbaTelaDAO;
import br.com.condominiosvirtuais.util.SQLUtil;

public class TelaAbaTelaDAOImpl implements TelaAbaTelaDAO, Serializable {

	private static final long serialVersionUID = 1L;
	
	private static Logger logger = Logger.getLogger(TelaAbaTelaDAOImpl.class);
	
	private static final String  TELA_ABA_TELA = " TELA_ABA_TELA";
	
	private static final String ID = "ID";	
	
	private static final String ID_TELA = "ID_TELA";
	
	private static final String ID_ABA_TELA = "ID_ABA_TELA";
	
	private static final String ACAO = "ACAO";
	
	

	@Override
	public void salvar(TelaAbaTela telaAbaTela, Connection con) throws SQLException, Exception {
		StringBuffer query = new StringBuffer();
		query.append("INSERT INTO "); 
		query.append(TELA_ABA_TELA);
		query.append("("); 
		query.append(ID_TELA); 
		query.append(",");		
		query.append(ID_ABA_TELA); 	
		query.append(",");
		query.append(ACAO); 
		query.append(") ");
		query.append("VALUES(?,?,?)");
		PreparedStatement statement = null;
		try {			
			statement = con.prepareStatement(query.toString());
			SQLUtil.setValorPpreparedStatement(statement, 1, telaAbaTela.getIdTela(), java.sql.Types.INTEGER);			
			SQLUtil.setValorPpreparedStatement(statement, 2, telaAbaTela.getIdAbaTela(), java.sql.Types.INTEGER);
			SQLUtil.setValorPpreparedStatement(statement, 3, telaAbaTela.getAcao(), java.sql.Types.VARCHAR);
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
	public List<TelaAbaTela> buscarPorIdTela(Integer idTela, Connection con) throws SQLException, Exception {
		StringBuffer query = new StringBuffer();
		query.append("SELECT * FROM ");
		query.append(TELA_ABA_TELA);
		query.append(" WHERE ");
		query.append(ID_TELA);
		query.append(" = ?");
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		TelaAbaTela telaAbaTela = null;	
		List<TelaAbaTela> listaTelaAbaTela = new ArrayList<TelaAbaTela>();
		try {
			statement = con.prepareStatement(query.toString());
			SQLUtil.setValorPpreparedStatement(statement, 1,idTela, java.sql.Types.INTEGER);
			resultSet = statement.executeQuery();
			while(resultSet.next()){
				telaAbaTela = new TelaAbaTela();
				telaAbaTela.setId((Integer) SQLUtil.getValorResultSet(resultSet, ID, java.sql.Types.INTEGER));
				telaAbaTela.setIdAbaTela((Integer) SQLUtil.getValorResultSet(resultSet, ID_ABA_TELA, java.sql.Types.INTEGER));
				telaAbaTela.setIdTela((Integer) SQLUtil.getValorResultSet(resultSet, ID_TELA, java.sql.Types.INTEGER));
				telaAbaTela.setAcao(String.valueOf(SQLUtil.getValorResultSet(resultSet, ACAO, java.sql.Types.VARCHAR)));
				listaTelaAbaTela.add(telaAbaTela);
			}
		}catch (SQLException e) {
			con.rollback();
			throw e;
		}catch (Exception e) {		
			con.rollback();
			throw e;
		}
		return listaTelaAbaTela;
	}

	@Override
	public void excluirPorIdTela(Integer idTela, Connection con) throws SQLException, Exception {
		StringBuffer query = new StringBuffer();
		PreparedStatement statement = null;
		try {
			query.append("DELETE FROM ");
			query.append(TELA_ABA_TELA);
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
