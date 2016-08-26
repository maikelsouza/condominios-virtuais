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

import br.com.condominiosvirtuais.entity.Visitante;
import br.com.condominiosvirtuais.exception.BusinessException;
import br.com.condominiosvirtuais.persistence.VisitanteDAO;
import br.com.condominiosvirtuais.util.SQLUtil;

public class VisitanteDAOImpl implements Serializable, VisitanteDAO {
	
	private static final long serialVersionUID = 1L;
	
	private static Logger logger = Logger.getLogger(VisitanteDAOImpl.class);
	
	private static final String VISITANTE = "VISITANTE";

	private static final String ID = "ID";
	
	private static final String NOME = "NOME";
	
	private static final String RG = "RG";
	
	private static final String CPF = "CPF";
	
	private static final String TELEFONE = "TELEFONE";
	
	private static final String UQ_VISITANTE_RG_CONDOMINIO = "UQ_VISITANTE_RG_CONDOMINIO";
	
	private static final String UQ_VISITANTE_CPF_CONDOMINIO = "UQ_VISITANTE_CPF_CONDOMINIO";
	
	
	
	@Inject
	private VisitaDAOImpl visitaDAO;

	@Override
	public void salvarVisitanteESalvarPrestadorServico(Visitante visitante) throws SQLException, BusinessException, Exception {
		StringBuffer query = new StringBuffer();
		query.append("INSERT INTO "); 
		query.append(VISITANTE);
		query.append("("); 
		query.append(NOME);	
		query.append(",");
		query.append(RG);
		query.append(",");
		query.append(CPF);
		query.append(",");	
		query.append(TELEFONE);
		query.append(") ");
		query.append("VALUES(?,?,?,?)");
		PreparedStatement statement = null;		
		Connection con = Conexao.getConexao();
		con.setAutoCommit(Boolean.FALSE);
		try {			
			statement = con.prepareStatement(query.toString(), PreparedStatement.RETURN_GENERATED_KEYS);
			SQLUtil.setValorPpreparedStatement(statement, 1, visitante.getNome(), java.sql.Types.VARCHAR);
			SQLUtil.setValorPpreparedStatement(statement, 2, visitante.getRg(), java.sql.Types.BIGINT);	
			SQLUtil.setValorPpreparedStatement(statement, 3, visitante.getCpf(), java.sql.Types.BIGINT);			
			SQLUtil.setValorPpreparedStatement(statement, 4, visitante.getTelefone(), java.sql.Types.BIGINT);			
			statement.executeUpdate();
			ResultSet rs = statement.getGeneratedKeys();
			rs.next();
			visitante.getVisita().setIdVisitante(rs.getInt(1));
			this.visitaDAO.salvarVisitaESalvarPrestadorServico(visitante.getVisita(), con);
			con.commit();
		} catch (SQLException e) {					
			if (e.getMessage().contains(UQ_VISITANTE_RG_CONDOMINIO)){
				throw new BusinessException("msg.visitante.rgUnico");
			}else if (e.getMessage().contains(UQ_VISITANTE_CPF_CONDOMINIO)){
				throw new BusinessException("msg.visitante.cpfUnico");
			}else{
				throw e;
			}
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
	public List<Visitante> buscaPorIdsENome(List<Integer> listaIds, String nome) throws SQLException, Exception {		
		String pontoInterrogacao = SQLUtil.popularInterrocacoes(listaIds.size());
		StringBuffer query = new StringBuffer();
		query.append("SELECT * FROM ");
		query.append(VISITANTE);
		query.append(" WHERE ");
		query.append(ID);		
		query.append(" in (");
		query.append(pontoInterrogacao);
		query.append(") AND upper(");
		query.append(NOME);
		query.append(") LIKE ?");
		query.append(";");		
		Connection con = Conexao.getConexao();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;		
		List<Visitante> listaVisitantes = new ArrayList<Visitante>();
		Visitante visitante = null;
		Integer contador = 1;
		try {
			preparedStatement = con.prepareStatement(query.toString());
			for (Integer id : listaIds) {
				SQLUtil.setValorPpreparedStatement(preparedStatement, contador++, id, java.sql.Types.INTEGER);				
			}
			SQLUtil.setValorPpreparedStatement(preparedStatement, contador++, nome.toUpperCase()+"%", java.sql.Types.VARCHAR);	
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()){
				visitante = new Visitante();
				visitante.setId((Integer) SQLUtil.getValorResultSet(resultSet, ID, java.sql.Types.INTEGER));
				visitante.setNome(String.valueOf(SQLUtil.getValorResultSet(resultSet, NOME, java.sql.Types.VARCHAR)));
				visitante.setCpf((Long) SQLUtil.getValorResultSet(resultSet, CPF, java.sql.Types.BIGINT));
				visitante.setRg((Long) SQLUtil.getValorResultSet(resultSet, RG, java.sql.Types.BIGINT));
				visitante.setTelefone((Long) SQLUtil.getValorResultSet(resultSet, TELEFONE, java.sql.Types.BIGINT));
				visitante.setListaVisita(this.visitaDAO.buscarPorIdVisitante(visitante.getId()));
				listaVisitantes.add(visitante);
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
		return listaVisitantes;
	}

	@Override
	public void salvarVisitanteEAtualizarPrestadorServico(Visitante visitante)
			throws SQLException, BusinessException, Exception {
		StringBuffer query = new StringBuffer();
		query.append("INSERT INTO "); 
		query.append(VISITANTE);
		query.append("("); 
		query.append(NOME);	
		query.append(",");
		query.append(RG);
		query.append(",");
		query.append(CPF);
		query.append(",");	
		query.append(TELEFONE);
		query.append(") ");
		query.append("VALUES(?,?,?,?)");
		PreparedStatement statement = null;		
		Connection con = Conexao.getConexao();
		con.setAutoCommit(Boolean.FALSE);
		try {			
			statement = con.prepareStatement(query.toString(), PreparedStatement.RETURN_GENERATED_KEYS);
			SQLUtil.setValorPpreparedStatement(statement, 1, visitante.getNome(), java.sql.Types.VARCHAR);
			SQLUtil.setValorPpreparedStatement(statement, 2, visitante.getRg(), java.sql.Types.BIGINT);	
			SQLUtil.setValorPpreparedStatement(statement, 3, visitante.getCpf(), java.sql.Types.BIGINT);			
			SQLUtil.setValorPpreparedStatement(statement, 4, visitante.getTelefone(), java.sql.Types.BIGINT);			
			statement.executeUpdate();
			ResultSet rs = statement.getGeneratedKeys();
			rs.next();
			visitante.getVisita().setIdVisitante(rs.getInt(1));
			this.visitaDAO.salvarVisitaEAtualizarPrestadorServico(visitante.getVisita(), con);			
			con.commit();
		} catch (SQLException e) {					
			if (e.getMessage().contains(UQ_VISITANTE_RG_CONDOMINIO)){
				throw new BusinessException("msg.visitante.rgUnico");
			}else if (e.getMessage().contains(UQ_VISITANTE_CPF_CONDOMINIO)){
				throw new BusinessException("msg.visitante.cpfUnico");
			}else{
				throw e;
			}
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
	public void atualizarVisitanteESalvarPrestadorServico(Visitante visitante)
			throws SQLException, BusinessException, Exception {
		StringBuffer query = new StringBuffer();
		query.append("UPDATE ");
		query.append(VISITANTE);
		query.append(" SET ");
		query.append(NOME);
		query.append("= ?, ");
		query.append(TELEFONE);
		query.append("= ?, ");
		query.append(RG);
		query.append("= ?, ");		
		query.append(CPF);	
		query.append("= ? ");
		query.append(" WHERE ");
		query.append(ID);
		query.append("= ?");		
		PreparedStatement statement = null;
		Connection con = Conexao.getConexao();
		con.setAutoCommit(Boolean.FALSE);
		try {
			statement = con.prepareStatement(query.toString());			
			SQLUtil.setValorPpreparedStatement(statement, 1, visitante.getNome(), java.sql.Types.VARCHAR);
			SQLUtil.setValorPpreparedStatement(statement, 2, visitante.getTelefone(), java.sql.Types.BIGINT);
			SQLUtil.setValorPpreparedStatement(statement, 3, visitante.getRg(), java.sql.Types.BIGINT);
			SQLUtil.setValorPpreparedStatement(statement, 4, visitante.getCpf(), java.sql.Types.VARCHAR);
			SQLUtil.setValorPpreparedStatement(statement, 5, visitante.getId(), java.sql.Types.INTEGER);			
			statement.executeUpdate();	
			visitante.getVisita().setIdVisitante(visitante.getId());
			this.visitaDAO.salvarVisitaESalvarPrestadorServico(visitante.getVisita(), con);
			con.commit();
		} catch (SQLException e) {					
			if (e.getMessage().contains(UQ_VISITANTE_RG_CONDOMINIO)){
				throw new BusinessException("msg.visitante.rgUnico");
			}else if (e.getMessage().contains(UQ_VISITANTE_CPF_CONDOMINIO)){
				throw new BusinessException("msg.visitante.cpfUnico");
			}else{
				throw e;
			}
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
	public void atualizarVisitanteEAtualizarPrestadorServico(Visitante visitante)
			throws SQLException, BusinessException, Exception {
		StringBuffer query = new StringBuffer();
		query.append("UPDATE ");
		query.append(VISITANTE);
		query.append(" SET ");
		query.append(NOME);
		query.append("= ?, ");
		query.append(TELEFONE);
		query.append("= ?, ");
		query.append(RG);
		query.append("= ?, ");		
		query.append(CPF);	
		query.append("= ? ");
		query.append(" WHERE ");
		query.append(ID);
		query.append("= ?");		
		PreparedStatement statement = null;
		Connection con = Conexao.getConexao();
		con.setAutoCommit(Boolean.FALSE);
		try {
			statement = con.prepareStatement(query.toString());			
			SQLUtil.setValorPpreparedStatement(statement, 1, visitante.getNome(), java.sql.Types.VARCHAR);
			SQLUtil.setValorPpreparedStatement(statement, 2, visitante.getTelefone(), java.sql.Types.BIGINT);
			SQLUtil.setValorPpreparedStatement(statement, 3, visitante.getRg(), java.sql.Types.BIGINT);
			SQLUtil.setValorPpreparedStatement(statement, 4, visitante.getCpf(), java.sql.Types.VARCHAR);
			SQLUtil.setValorPpreparedStatement(statement, 5, visitante.getId(), java.sql.Types.INTEGER);			
			statement.executeUpdate();	
			visitante.getVisita().setIdVisitante(visitante.getId());
			this.visitaDAO.salvarVisitaEAtualizarPrestadorServico(visitante.getVisita(), con);
			con.commit();
		} catch (SQLException e) {					
			if (e.getMessage().contains(UQ_VISITANTE_RG_CONDOMINIO)){
				throw new BusinessException("msg.visitante.rgUnico");
			}else if (e.getMessage().contains(UQ_VISITANTE_CPF_CONDOMINIO)){
				throw new BusinessException("msg.visitante.cpfUnico");
			}else{
				throw e;
			}
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
	public void salvarSomenteVisitante(Visitante visitante) throws SQLException, BusinessException, Exception {
		StringBuffer query = new StringBuffer();
		query.append("INSERT INTO "); 
		query.append(VISITANTE);
		query.append("("); 
		query.append(NOME);	
		query.append(",");
		query.append(RG);
		query.append(",");
		query.append(CPF);
		query.append(",");	
		query.append(TELEFONE);
		query.append(") ");
		query.append("VALUES(?,?,?,?)");
		PreparedStatement statement = null;		
		Connection con = Conexao.getConexao();
		con.setAutoCommit(Boolean.FALSE);
		try {			
			statement = con.prepareStatement(query.toString(), PreparedStatement.RETURN_GENERATED_KEYS);
			SQLUtil.setValorPpreparedStatement(statement, 1, visitante.getNome(), java.sql.Types.VARCHAR);
			SQLUtil.setValorPpreparedStatement(statement, 2, visitante.getRg(), java.sql.Types.BIGINT);	
			SQLUtil.setValorPpreparedStatement(statement, 3, visitante.getCpf(), java.sql.Types.BIGINT);			
			SQLUtil.setValorPpreparedStatement(statement, 4, visitante.getTelefone(), java.sql.Types.BIGINT);			
			statement.executeUpdate();
			ResultSet rs = statement.getGeneratedKeys();
			rs.next();
			visitante.getVisita().setIdVisitante(rs.getInt(1));
			this.visitaDAO.salvarSomenteVisita(visitante.getVisita(), con);			
			con.commit();
		} catch (SQLException e) {					
			if (e.getMessage().contains(UQ_VISITANTE_RG_CONDOMINIO)){
				throw new BusinessException("msg.visitante.rgUnico");
			}else if (e.getMessage().contains(UQ_VISITANTE_CPF_CONDOMINIO)){
				throw new BusinessException("msg.visitante.cpfUnico");
			}else{
				throw e;
			}
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
	public void atualizarSomenteVisitante(Visitante visitante) throws SQLException, BusinessException, Exception {
		StringBuffer query = new StringBuffer();
		query.append("UPDATE ");
		query.append(VISITANTE);
		query.append(" SET ");
		query.append(NOME);
		query.append("= ?, ");
		query.append(TELEFONE);
		query.append("= ?, ");
		query.append(RG);
		query.append("= ?, ");		
		query.append(CPF);	
		query.append("= ? ");
		query.append(" WHERE ");
		query.append(ID);
		query.append("= ?");		
		PreparedStatement statement = null;
		Connection con = Conexao.getConexao();
		con.setAutoCommit(Boolean.FALSE);
		try {
			statement = con.prepareStatement(query.toString());			
			SQLUtil.setValorPpreparedStatement(statement, 1, visitante.getNome(), java.sql.Types.VARCHAR);
			SQLUtil.setValorPpreparedStatement(statement, 2, visitante.getTelefone(), java.sql.Types.BIGINT);
			SQLUtil.setValorPpreparedStatement(statement, 3, visitante.getRg(), java.sql.Types.BIGINT);
			SQLUtil.setValorPpreparedStatement(statement, 4, visitante.getCpf(), java.sql.Types.VARCHAR);
			SQLUtil.setValorPpreparedStatement(statement, 5, visitante.getId(), java.sql.Types.INTEGER);			
			statement.executeUpdate();	
			visitante.getVisita().setIdVisitante(visitante.getId());
			this.visitaDAO.salvarSomenteVisita(visitante.getVisita(), con);
			con.commit();
		} catch (SQLException e) {					
			if (e.getMessage().contains(UQ_VISITANTE_RG_CONDOMINIO)){
				throw new BusinessException("msg.visitante.rgUnico");
			}else if (e.getMessage().contains(UQ_VISITANTE_CPF_CONDOMINIO)){
				throw new BusinessException("msg.visitante.cpfUnico");
			}else{
				throw e;
			}
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
	public Visitante buscaPorId(Integer id) throws SQLException, Exception {		
		StringBuffer query = new StringBuffer();
		query.append("SELECT * FROM ");
		query.append(VISITANTE);
		query.append(" WHERE ");
		query.append(ID);		
		query.append(" = ?");
		query.append(" ORDER BY ");
		query.append(NOME);
		query.append(";");		
		Connection con = Conexao.getConexao();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Visitante visitante = null;		
		try {
			preparedStatement = con.prepareStatement(query.toString());
			SQLUtil.setValorPpreparedStatement(preparedStatement, 1, id, java.sql.Types.INTEGER);				
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()){
				visitante = new Visitante();
				visitante.setId((Integer) SQLUtil.getValorResultSet(resultSet, ID, java.sql.Types.INTEGER));
				visitante.setNome(String.valueOf(SQLUtil.getValorResultSet(resultSet, NOME, java.sql.Types.VARCHAR)));
				visitante.setCpf((Long) SQLUtil.getValorResultSet(resultSet, CPF, java.sql.Types.BIGINT));
				visitante.setRg((Long) SQLUtil.getValorResultSet(resultSet, RG, java.sql.Types.BIGINT));
				visitante.setTelefone((Long) SQLUtil.getValorResultSet(resultSet, TELEFONE, java.sql.Types.BIGINT));
				visitante.setListaVisita(this.visitaDAO.buscarPorIdVisitante(visitante.getId()));			
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
		return visitante;
	}

}