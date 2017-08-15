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

import br.com.condominiosvirtuais.entity.Componente;
import br.com.condominiosvirtuais.persistence.ComponenteDAO;
import br.com.condominiosvirtuais.persistence.TelaComponenteDAO;
import br.com.condominiosvirtuais.util.SQLUtil;

public class ComponenteDAOImpl implements ComponenteDAO, Serializable {
	
	private static final long serialVersionUID = 1L;

	private static Logger logger = Logger.getLogger(ComponenteDAOImpl.class);
	
	private static final String COMPONENTE = "COMPONENTE";
	
	private static final String ID = "ID";	
	
	private static final String NOME = "NOME";	
	
	private static final String DESCRICAO = "DESCRICAO";
	
	private static final String ID_COMPONENTE = "ID_COMPONENTE";
	
	private static final String ID_TELA = "ID_TELA";
	
	private static final String ID_ABA = "ID_ABA";
	
	private static final String TIPO = "TIPO";
	
	@Inject
	private TelaComponenteDAO telaComponenteDAO; 
	

	@Override
	public List<Componente> buscarPorIdTela(Integer idTela) throws SQLException, Exception {
		Connection con = Conexao.getConexao();
		List<Componente> listaComponente = new ArrayList<Componente>();
			StringBuffer query = new StringBuffer();
			query.append("SELECT * FROM ");
			query.append(COMPONENTE);
			query.append(" WHERE ");
			query.append(ID_TELA);
			query.append(" = ? ");
			query.append(";");		
			PreparedStatement preparedStatement = null;
			ResultSet resultSet = null;
			Componente componente = null;
			try {
				preparedStatement = con.prepareStatement(query.toString());
				SQLUtil.setValorPpreparedStatement(preparedStatement, 1, idTela, java.sql.Types.INTEGER);
				resultSet = preparedStatement.executeQuery();
				while(resultSet.next()){
					componente = new Componente();
					componente.setId((Integer) SQLUtil.getValorResultSet(resultSet, ID, java.sql.Types.INTEGER));
					componente.setNome(String.valueOf(SQLUtil.getValorResultSet(resultSet, NOME, java.sql.Types.VARCHAR)));
					componente.setIdTela((Integer) SQLUtil.getValorResultSet(resultSet, ID_TELA, java.sql.Types.INTEGER));
					componente.setIdAba((Integer)SQLUtil.getValorResultSet(resultSet, ID_ABA, java.sql.Types.INTEGER));
					componente.setTipo((Integer)SQLUtil.getValorResultSet(resultSet, TIPO, java.sql.Types.INTEGER));
					componente.setDescricao(String.valueOf(SQLUtil.getValorResultSet(resultSet, DESCRICAO, java.sql.Types.VARCHAR)));
					componente.setIdComponente(String.valueOf(SQLUtil.getValorResultSet(resultSet, ID_COMPONENTE, java.sql.Types.VARCHAR)));
					listaComponente.add(componente);
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
		return listaComponente;
	}
	
	@Override
	public List<Componente> buscarPorIdTela(Integer idTela, Connection con) throws SQLException, Exception {
		List<Componente> listaComponente = new ArrayList<Componente>();
		StringBuffer query = new StringBuffer();
		query.append("SELECT * FROM ");
		query.append(COMPONENTE);
		query.append(" WHERE ");
		query.append(ID_TELA);
		query.append(" = ? ");
		query.append(";");		
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Componente componente = null;
		try {
			preparedStatement = con.prepareStatement(query.toString());
			SQLUtil.setValorPpreparedStatement(preparedStatement, 1, idTela, java.sql.Types.INTEGER);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()){
				componente = new Componente();
				componente.setId((Integer) SQLUtil.getValorResultSet(resultSet, ID, java.sql.Types.INTEGER));
				componente.setNome(String.valueOf(SQLUtil.getValorResultSet(resultSet, NOME, java.sql.Types.VARCHAR)));
				componente.setIdTela((Integer) SQLUtil.getValorResultSet(resultSet, ID_TELA, java.sql.Types.INTEGER));
				componente.setIdAba((Integer)SQLUtil.getValorResultSet(resultSet, ID_ABA, java.sql.Types.INTEGER));
				componente.setTipo((Integer)SQLUtil.getValorResultSet(resultSet, TIPO, java.sql.Types.INTEGER));
				componente.setDescricao(String.valueOf(SQLUtil.getValorResultSet(resultSet, DESCRICAO, java.sql.Types.VARCHAR)));
				componente.setIdComponente(String.valueOf(SQLUtil.getValorResultSet(resultSet, ID_COMPONENTE, java.sql.Types.VARCHAR)));
				listaComponente.add(componente);
			}
		}catch (SQLException e) {
			con.rollback();
			throw e;
		}catch (Exception e) {
			con.rollback();
			throw e;			
		}	
		return listaComponente;
	}
	

}
