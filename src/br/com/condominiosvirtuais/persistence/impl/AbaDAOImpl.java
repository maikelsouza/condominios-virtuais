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

import br.com.condominiosvirtuais.entity.Aba;
import br.com.condominiosvirtuais.entity.GrupoUsuarioTelaAba;
import br.com.condominiosvirtuais.persistence.AbaDAO;
import br.com.condominiosvirtuais.persistence.GrupoUsuarioTelaAbaDAO;
import br.com.condominiosvirtuais.util.SQLUtil;

public class AbaDAOImpl implements AbaDAO, Serializable {

	private static final long serialVersionUID = 1L;

	private static Logger logger = Logger.getLogger(AbaDAOImpl.class);
	
	private static final String ABA = "ABA";
	
	private static final String ID = "ID";
	
	private static final String NOME = "NOME";
	
	private static final String DESCRICAO = "DESCRICAO";
	
	private static final String ID_TELA = "ID_TELA";
	
	private static final String ID_ABA = "ID_ABA";
	
	@Inject
	private GrupoUsuarioTelaAbaDAO grupoUsuarioTelaAbaDAO; 
	
	
	@Override
	public List<Aba> buscarPoridGrupoUsuarioEIdTela(Integer idGrupoUsuario, Integer idTela, Connection con) throws SQLException, Exception {
		// Busca a lista de abas associadas a uma tela
		List<GrupoUsuarioTelaAba> listaGrupoUsuarioTelaAba = this.grupoUsuarioTelaAbaDAO.buscarPorIdGrupoUsuarioEIdTela(idGrupoUsuario, idTela, con);
		Integer contador = 1;
		List<Aba> listaAba = new ArrayList<Aba>();
		if(!listaGrupoUsuarioTelaAba.isEmpty()){
			StringBuffer query = new StringBuffer();
			query.append("SELECT * FROM ");
			query.append(ABA);
			query.append(" WHERE ");
			query.append(ID);
			query.append(" IN (");
			query.append(SQLUtil.popularInterrocacoes(listaGrupoUsuarioTelaAba.size()));
			query.append(");");		
			PreparedStatement preparedStatement = null;
			ResultSet resultSet = null;
			Aba aba = null;
			try {
				preparedStatement = con.prepareStatement(query.toString());
				for (GrupoUsuarioTelaAba grupoUsuarioTelaAba : listaGrupoUsuarioTelaAba) {
					SQLUtil.setValorPpreparedStatement(preparedStatement, contador++, grupoUsuarioTelaAba.getIdAba(), java.sql.Types.INTEGER);
				}
				resultSet = preparedStatement.executeQuery();
				while(resultSet.next()){
					aba = new Aba();
					aba.setId((Integer) SQLUtil.getValorResultSet(resultSet, ID, java.sql.Types.INTEGER));
					aba.setNome(String.valueOf(SQLUtil.getValorResultSet(resultSet, NOME, java.sql.Types.VARCHAR)));
					aba.setIdTela((Integer) SQLUtil.getValorResultSet(resultSet, ID_TELA, java.sql.Types.INTEGER));
					aba.setIdAba(String.valueOf(SQLUtil.getValorResultSet(resultSet, ID_ABA, java.sql.Types.VARCHAR)));
					aba.setDescricao(String.valueOf(SQLUtil.getValorResultSet(resultSet, DESCRICAO, java.sql.Types.VARCHAR)));
					listaAba.add(aba);
				}
			}catch (SQLException e) {
				con.rollback();
				throw e;
			}catch (Exception e) {
				con.rollback();
				throw e;
			}
		}
		return listaAba;
	}
	
	@Override
	public List<Aba> buscarPorIdTela(Integer idTela) throws SQLException, Exception {
		Connection con = Conexao.getConexao();
		List<Aba> listaAba = new ArrayList<Aba>();		
		StringBuffer query = new StringBuffer();
		query.append("SELECT * FROM ");
		query.append(ABA);
		query.append(" WHERE ");
		query.append(ID_TELA);
		query.append(" = ?;");		
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Aba aba = null;
		try {
			preparedStatement = con.prepareStatement(query.toString());
			SQLUtil.setValorPpreparedStatement(preparedStatement, 1, idTela, java.sql.Types.INTEGER);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()){
				aba = new Aba();
				aba.setId((Integer) SQLUtil.getValorResultSet(resultSet, ID, java.sql.Types.INTEGER));
				aba.setNome(String.valueOf(SQLUtil.getValorResultSet(resultSet, NOME, java.sql.Types.VARCHAR)));
				aba.setIdTela((Integer) SQLUtil.getValorResultSet(resultSet, ID_TELA, java.sql.Types.INTEGER));
				aba.setIdAba(String.valueOf(SQLUtil.getValorResultSet(resultSet, ID_ABA, java.sql.Types.VARCHAR)));
				aba.setDescricao(String.valueOf(SQLUtil.getValorResultSet(resultSet, DESCRICAO, java.sql.Types.VARCHAR)));
				listaAba.add(aba);
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
		return listaAba;
	}
	
	@Override
	public List<Aba> buscarPorIdTela(Integer idTela, Connection con) throws SQLException, Exception {
		List<Aba> listaAba = new ArrayList<Aba>();
		StringBuffer query = new StringBuffer();
		query.append("SELECT * FROM ");
		query.append(ABA);
		query.append(" WHERE ");
		query.append(ID_TELA);
		query.append(" = ?;");
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Aba aba = null;
		try {
			preparedStatement = con.prepareStatement(query.toString());
			SQLUtil.setValorPpreparedStatement(preparedStatement, 1, idTela, java.sql.Types.INTEGER);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()){
				aba = new Aba();
				aba.setId((Integer) SQLUtil.getValorResultSet(resultSet, ID, java.sql.Types.INTEGER));
				aba.setNome(String.valueOf(SQLUtil.getValorResultSet(resultSet, NOME, java.sql.Types.VARCHAR)));
				aba.setIdTela((Integer) SQLUtil.getValorResultSet(resultSet, ID_TELA, java.sql.Types.INTEGER));
				aba.setIdAba(String.valueOf(SQLUtil.getValorResultSet(resultSet, ID_ABA, java.sql.Types.VARCHAR)));
				aba.setDescricao(String.valueOf(SQLUtil.getValorResultSet(resultSet, DESCRICAO, java.sql.Types.VARCHAR)));
				listaAba.add(aba);
			}
		}catch (SQLException e) {
			con.rollback();
			throw e;
		}catch (Exception e) {
			con.rollback();
			throw e;
		}
		return listaAba;
	}	

}
