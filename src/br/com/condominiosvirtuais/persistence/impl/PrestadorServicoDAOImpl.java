package br.com.condominiosvirtuais.persistence.impl;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import br.com.condominiosvirtuais.entity.PrestadorServico;
import br.com.condominiosvirtuais.persistence.PrestadorServicoDAO;
import br.com.condominiosvirtuais.util.SQLUtil;

public class PrestadorServicoDAOImpl implements Serializable, PrestadorServicoDAO {

	private static final long serialVersionUID = 1L;
	
	private static Logger logger = Logger.getLogger(PrestadorServicoDAOImpl.class);
	
	private static final String PRESTADOR_SERVICO = "PRESTADOR_SERVICO";

	private static final String ID = "ID";
	
	private static final String NOME = "NOME";
	
	private static final String TELEFONE = "TELEFONE";
	
	private static final String CNPJ = "CNPJ";	
	
	private static final String EMAIL = "EMAIL";	
	
	private static final String ID_VISITA = "ID_VISITA";
	

	@Override
	public Integer salvar(PrestadorServico prestadorServico, Connection con) throws SQLException, Exception {
		Integer idPrestadorServico;
		StringBuffer query = new StringBuffer();
		query.append("INSERT INTO "); 
		query.append(PRESTADOR_SERVICO);
		query.append("("); 
		query.append(NOME);	
		query.append(",");
		query.append(TELEFONE);
		query.append(",");
		query.append(CNPJ);
		query.append(",");	
		query.append(EMAIL);				
//		query.append(", ");
//		query.append(ID_VISITA);				
		query.append(") ");
		query.append("VALUES(?,?,?,?)");
		PreparedStatement statement = null;		
		try {			
			statement = con.prepareStatement(query.toString(), PreparedStatement.RETURN_GENERATED_KEYS);
			SQLUtil.setValorPpreparedStatement(statement, 1, prestadorServico.getNome(), java.sql.Types.VARCHAR);
			SQLUtil.setValorPpreparedStatement(statement, 2, prestadorServico.getTelefone(), java.sql.Types.BIGINT);	
			SQLUtil.setValorPpreparedStatement(statement, 3, prestadorServico.getCnpj(), java.sql.Types.BIGINT);			
			SQLUtil.setValorPpreparedStatement(statement, 4, prestadorServico.getEmail(), java.sql.Types.VARCHAR);
	//		SQLUtil.setValorPpreparedStatement(statement, 5, prestadorServico.getIdVisita(), java.sql.Types.INTEGER);
			statement.executeUpdate();
			ResultSet rs = statement.getGeneratedKeys();
			rs.next();			
			idPrestadorServico = rs.getInt(1);
		} catch (SQLException e) {
			con.rollback();
			throw e;
		} catch (Exception e) {
			con.rollback();
			throw e;	
		}
		return idPrestadorServico;
	}


	@Override
	public PrestadorServico buscarPorIdVisita(Integer idVisita, Connection con) throws SQLException, Exception {
// TODO: C?digo comentado em 24/08/2016. Apagar em 90 dias 		
//		StringBuffer query = new StringBuffer();
//		query.append("SELECT * FROM ");
//		query.append(PRESTADOR_SERVICO);
//		query.append(" WHERE ");
//		query.append(ID_VISITA);		
//		query.append(" = ?");
//		query.append(";");
//		PreparedStatement preparedStatement = null;
//		ResultSet resultSet = null;		
//		PrestadorServico prestadorServico = new PrestadorServico();		
//		try {
//			preparedStatement = con.prepareStatement(query.toString());
//			SQLUtil.setValorPpreparedStatement(preparedStatement, 1, idVisita, java.sql.Types.INTEGER);	
//			resultSet = preparedStatement.executeQuery();
//			while(resultSet.next()){				
//				prestadorServico.setId((Integer) SQLUtil.getValorResultSet(resultSet, ID, java.sql.Types.INTEGER));
//				prestadorServico.setNome(String.valueOf(SQLUtil.getValorResultSet(resultSet, NOME, java.sql.Types.VARCHAR)));
//				prestadorServico.setTelefone((Long) SQLUtil.getValorResultSet(resultSet, TELEFONE, java.sql.Types.BIGINT));
//				prestadorServico.setCnpj((Long) SQLUtil.getValorResultSet(resultSet, CNPJ, java.sql.Types.BIGINT));
//				prestadorServico.setEmail(String.valueOf(SQLUtil.getValorResultSet(resultSet, EMAIL, java.sql.Types.VARCHAR)));
//				prestadorServico.setIdVisita((Integer) SQLUtil.getValorResultSet(resultSet, ID_VISITA, java.sql.Types.INTEGER));				
//			}
//		} catch (SQLException e) {
//			throw e;
//		} catch (Exception e) {
//			throw e;		
//		}
		return null;
	}


	@Override
	public List<PrestadorServico> buscarPorNome(String nome) throws SQLException, Exception {
		StringBuffer query = new StringBuffer();
		query.append("SELECT * FROM ");
		query.append(PRESTADOR_SERVICO);
		query.append(" WHERE ");
		query.append(" upper(");
		query.append(NOME);
		query.append(") LIKE ?");
		query.append(";");
		Connection con = C3P0DataSource.getInstance().getConnection();
		PreparedStatement preparedStatement = null;		
		List<PrestadorServico> listaPrestadorServico = new ArrayList<PrestadorServico>();
		try {
			preparedStatement = con.prepareStatement(query.toString());
			SQLUtil.setValorPpreparedStatement(preparedStatement, 1, nome+"%", java.sql.Types.VARCHAR);
			ResultSet resultSet = preparedStatement.executeQuery();
			PrestadorServico prestadorServico = null;
			while(resultSet.next()){
				prestadorServico = new PrestadorServico();								
				prestadorServico.setId((Integer) SQLUtil.getValorResultSet(resultSet, ID, java.sql.Types.INTEGER));
				prestadorServico.setCnpj((Long) SQLUtil.getValorResultSet(resultSet, CNPJ, java.sql.Types.BIGINT));
				prestadorServico.setNome(String.valueOf(SQLUtil.getValorResultSet(resultSet, NOME, java.sql.Types.VARCHAR)));
				prestadorServico.setEmail(String.valueOf(SQLUtil.getValorResultSet(resultSet, EMAIL, java.sql.Types.VARCHAR)));
				prestadorServico.setTelefone((Long) SQLUtil.getValorResultSet(resultSet, TELEFONE, java.sql.Types.BIGINT));
				listaPrestadorServico.add(prestadorServico);
			}
		} catch (NumberFormatException e) {
			throw e;
		} catch (SQLException e) {					
			throw e;
		} catch (Exception e) {					
			throw e;	
		}finally{
			try{				
				preparedStatement.close();
				con.close();				
			}catch (SQLException e) {
				logger.error("erro sqlstate "+e.getSQLState(), e);
		    }
		}		
		return listaPrestadorServico;
	}


	@Override
	public void atualizar(PrestadorServico prestadorServico, Connection con) throws SQLException, Exception {
		StringBuffer query = new StringBuffer();
		query.append("UPDATE ");
		query.append(PRESTADOR_SERVICO);
		query.append(" SET ");
		query.append(NOME);
		query.append("= ?, ");
		query.append(TELEFONE);
		query.append("= ?, ");
		query.append(CNPJ);
		query.append("= ?, ");		
		query.append(EMAIL);	
		query.append("= ? ");
//		query.append(ID_VISITA);	
//		query.append("= ? ");
		query.append(" WHERE ");
		query.append(ID);
		query.append("= ?");		
		PreparedStatement statement = null;
		try {
			statement = con.prepareStatement(query.toString());			
			SQLUtil.setValorPpreparedStatement(statement, 1, prestadorServico.getNome(), java.sql.Types.VARCHAR);
			SQLUtil.setValorPpreparedStatement(statement, 2, prestadorServico.getTelefone(), java.sql.Types.BIGINT);
			SQLUtil.setValorPpreparedStatement(statement, 3, prestadorServico.getCnpj(), java.sql.Types.BIGINT);
			SQLUtil.setValorPpreparedStatement(statement, 4, prestadorServico.getEmail(), java.sql.Types.VARCHAR);
			SQLUtil.setValorPpreparedStatement(statement, 5, prestadorServico.getId(), java.sql.Types.INTEGER);
			//SQLUtil.setValorPpreparedStatement(statement, 6, prestadorServico.getIdVisita(), java.sql.Types.INTEGER);
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
	public PrestadorServico buscarPorId(Integer id, Connection con) throws SQLException, Exception {
		StringBuffer query = new StringBuffer();
		query.append("SELECT * FROM ");
		query.append(PRESTADOR_SERVICO);
		query.append(" WHERE ");
		query.append(ID);		
		query.append(" = ?");
		query.append(";");
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;		
		PrestadorServico prestadorServico = new PrestadorServico();		
		try {
			preparedStatement = con.prepareStatement(query.toString());
			SQLUtil.setValorPpreparedStatement(preparedStatement, 1, id, java.sql.Types.INTEGER);	
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()){				
				prestadorServico.setId((Integer) SQLUtil.getValorResultSet(resultSet, ID, java.sql.Types.INTEGER));
				prestadorServico.setNome(String.valueOf(SQLUtil.getValorResultSet(resultSet, NOME, java.sql.Types.VARCHAR)));
				prestadorServico.setTelefone((Long) SQLUtil.getValorResultSet(resultSet, TELEFONE, java.sql.Types.BIGINT));
				prestadorServico.setCnpj((Long) SQLUtil.getValorResultSet(resultSet, CNPJ, java.sql.Types.BIGINT));
				prestadorServico.setEmail(String.valueOf(SQLUtil.getValorResultSet(resultSet, EMAIL, java.sql.Types.VARCHAR)));								
			}
		} catch (SQLException e) {
			throw e;
		} catch (Exception e) {
			throw e;		
		}
		return prestadorServico;
	}
}
