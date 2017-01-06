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

import br.com.condominiosvirtuais.entity.Contador;
import br.com.condominiosvirtuais.persistence.ContadorDAO;
import br.com.condominiosvirtuais.persistence.UsuarioDAO;
import br.com.condominiosvirtuais.util.SQLUtil;

public class ContadorDAOImpl implements ContadorDAO, Serializable {

	private static final long serialVersionUID = 1L;
	
	private static Logger logger = Logger.getLogger(ContadorDAOImpl.class);	
	
	private static final String CONTADOR = "CONTADOR";
	
	private static final String ID = "ID";
	
	private static final String ID_ESCRITORIO_CONTABILIDADE = "ID_ESCRITORIO_CONTABILIDADE";
	
	@Inject
	private UsuarioDAO usuarioDAO;
	
		
		
	@Override
	public void salvar(Contador contador) throws SQLException,  Exception {
		PreparedStatement statement = null;
		Connection con = null;		
		try {
			con = Conexao.getConexao();
			con.setAutoCommit(Boolean.FALSE);			
			this.usuarioDAO.salvarUsuario(contador, con);
			StringBuffer query = new StringBuffer();
			query.append("INSERT INTO "); 
			query.append(CONTADOR); 
			query.append("(");
			query.append(ID); 
			query.append(",");
			query.append(ID_ESCRITORIO_CONTABILIDADE); 
			query.append(") ");
			query.append("VALUES(?,?)");
			statement = con.prepareStatement(query.toString());
			SQLUtil.setValorPpreparedStatement(statement, 1, contador.getId(), java.sql.Types.INTEGER);						
			SQLUtil.setValorPpreparedStatement(statement, 2, contador.getIdEscritorioContabilidade(), java.sql.Types.INTEGER);
			statement.execute();
			con.commit();	
		} catch (SQLException e) {
			throw e;		
		} catch (Exception e) {		
			throw e;			
		}finally{
			try {				
				con.close();	
				statement.close();
			} catch (SQLException e) {
				logger.error("erro sqlstate "+e.getSQLState(), e);		
			}
		}	
	}

	@Override
	public List<Contador> buscarPorIdEscritorioContabilidade(Integer idEscritorioContabilidade, Connection con) throws SQLException, Exception {
		StringBuffer query = new StringBuffer();
		query.append("SELECT * FROM ");
		query.append(CONTADOR);
		query.append(" WHERE ");
		query.append(ID_ESCRITORIO_CONTABILIDADE);
		query.append(" = ?");			
		PreparedStatement preparedStatement = null;		
		List<Contador> listaContador = new ArrayList<Contador>();
		try {
			preparedStatement = con.prepareStatement(query.toString());
			SQLUtil.setValorPpreparedStatement(preparedStatement, 1, idEscritorioContabilidade, java.sql.Types.INTEGER);
			ResultSet resultSet = preparedStatement.executeQuery();
			Contador contador = null;
			while(resultSet.next()){
				contador = new Contador();
				contador.setId((Integer) SQLUtil.getValorResultSet(resultSet, ID, java.sql.Types.INTEGER));
				contador.setIdEscritorioContabilidade((Integer) SQLUtil.getValorResultSet(resultSet, ID_ESCRITORIO_CONTABILIDADE, java.sql.Types.INTEGER));
				this.usuarioDAO.buscarEPopularUsuarioPeloId(contador, con);
				listaContador.add(contador);
			}
		} catch (NumberFormatException e) {
			throw e;
		} catch (SQLException e) {					
			throw e;
		} catch (Exception e) {					
			throw e;
		}
		return listaContador;
	}

	@Override
	public List<Contador> buscarPorIdEscritorioContabilidadeESituacao(Integer idEscritorioContabilidade, Integer situacao) throws SQLException, Exception {
		StringBuffer query = new StringBuffer();
		query.append("SELECT * FROM ");
		query.append(CONTADOR);
		query.append(" WHERE ");
		query.append(ID_ESCRITORIO_CONTABILIDADE);
		query.append(" = ?");		
		PreparedStatement preparedStatement = null;		
		Connection con = Conexao.getConexao();
		List<Contador> listaContador = new ArrayList<Contador>();
		try {
			preparedStatement = con.prepareStatement(query.toString());
			SQLUtil.setValorPpreparedStatement(preparedStatement, 1, idEscritorioContabilidade, java.sql.Types.INTEGER);
			ResultSet resultSet = preparedStatement.executeQuery();
			Contador contador = null;
			while(resultSet.next()){
				contador = new Contador();
				contador.setId((Integer) SQLUtil.getValorResultSet(resultSet, ID, java.sql.Types.INTEGER));
				contador.setIdEscritorioContabilidade((Integer) SQLUtil.getValorResultSet(resultSet, ID_ESCRITORIO_CONTABILIDADE, java.sql.Types.INTEGER));
				contador.setSituacao(situacao);
				if(this.usuarioDAO.buscarPorIdESituacaoEPopularUsuarioPeloId(contador, con)){
					listaContador.add(contador);				
				}
				
			}
		} catch (NumberFormatException e) {
			throw e;
		} catch (SQLException e) {					
			throw e;
		} catch (Exception e) {					
			throw e;
		}finally{
			try {				
				con.close();	
				preparedStatement.close();
			} catch (SQLException e) {
				logger.error("erro sqlstate "+e.getSQLState(), e);		
			}
		}	
		return listaContador;
	}

	@Override
	public void atualizar(Contador contador) throws SQLException, Exception {
		// TODO Auto-generated method stub
		
	}
	

}
