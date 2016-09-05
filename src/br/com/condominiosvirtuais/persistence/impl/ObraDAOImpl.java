package br.com.condominiosvirtuais.persistence.impl;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.apache.log4j.Logger;

import br.com.condominiosvirtuais.entity.Obra;
import br.com.condominiosvirtuais.entity.ObraResponsavelObra;
import br.com.condominiosvirtuais.persistence.ObraDAO;
import br.com.condominiosvirtuais.util.SQLUtil;

public class ObraDAOImpl implements ObraDAO, Serializable {

	private static final long serialVersionUID = 1L;
	
	private static Logger logger = Logger.getLogger(ObraDAOImpl.class);
	
	private static final String OBRA = "OBRA";
	
	private static final String ID = "ID";	
	
	private static final String NOME = "NOME";
	
	private static final String TIPO = "TIPO";
	
	private static final String SITUACAO = "SITUACAO";
	
	private static final String DESCRICAO = "DESCRICAO";
	
	private static final String DATA_INICIO = "DATA_INICIO";
	
	private static final String DATA_FIM = "DATA_FIM";
	
	private static final String ID_CONDOMINIO = "ID_CONDOMINIO";
	
	@Inject
	private ResponsavelObraDAOImpl responsavelObra;
	
	@Inject
	private ObraResponsavelObraDAOImpl obraResponsavelObraDAO;

	@Override
	public void salvarObraESalvaResponsavelObra(Obra obra) throws SQLException, Exception {
		Connection con = Conexao.getConexao();
		Integer idObra;
		Integer idResponsavelObra;
		ObraResponsavelObra obraResponsavelObra = new ObraResponsavelObra();
		con.setAutoCommit(Boolean.FALSE);
		StringBuffer query = new StringBuffer();
		query.append("INSERT INTO "); 
		query.append(OBRA);
		query.append("("); 
		query.append(NOME); 
		query.append(",");		
		query.append(TIPO); 	
		query.append(",");
		query.append(SITUACAO); 
		query.append(",");
		query.append(DESCRICAO);
		query.append(",");		
		query.append(DATA_INICIO);
		query.append(",");
		query.append(DATA_FIM);
		query.append(",");		
		query.append(ID_CONDOMINIO);
		query.append(") ");
		query.append("VALUES(?,?,?,?,?,?,?)");
		PreparedStatement statement = null;
		try {			
			statement = con.prepareStatement(query.toString(), PreparedStatement.RETURN_GENERATED_KEYS);			
			SQLUtil.setValorPpreparedStatement(statement, 1, obra.getNome(), java.sql.Types.VARCHAR);			
			SQLUtil.setValorPpreparedStatement(statement, 2, obra.getTipo(), java.sql.Types.INTEGER);	
			SQLUtil.setValorPpreparedStatement(statement, 3, obra.getSituacao(), java.sql.Types.INTEGER);
			SQLUtil.setValorPpreparedStatement(statement, 4, obra.getDescricao(), java.sql.Types.VARCHAR);
			SQLUtil.setValorPpreparedStatement(statement, 5, obra.getDataInicio(), java.sql.Types.DATE);
			SQLUtil.setValorPpreparedStatement(statement, 6, obra.getDataFim(), java.sql.Types.DATE);
			SQLUtil.setValorPpreparedStatement(statement, 7, obra.getIdCondominio(), java.sql.Types.INTEGER);
			statement.executeUpdate();
			ResultSet rs = statement.getGeneratedKeys();
			rs.next();
			idObra = rs.getInt(1);				
			idResponsavelObra = this.responsavelObra.salvar(obra.getResponsavelObra(), con);
			obraResponsavelObra.setIdObra(idObra);
			obraResponsavelObra.setIdResponsavelObra(idResponsavelObra);
			this.obraResponsavelObraDAO.salvar(obraResponsavelObra, con);
			con.commit();
		}catch (SQLException e) {			
			throw e;			
		} catch (Exception e) {					
			throw e;	
		}finally{
			try{	
				statement.close();
				con.close();
			}catch (SQLException e){
				logger.error("erro sqlstate "+e.getSQLState(), e);
			}
		}
	}

	@Override
	public void salvarObraEAtualizarResponsavel(Obra obra) throws SQLException, Exception {
		Connection con = Conexao.getConexao();
		Integer idObra;
		ObraResponsavelObra obraResponsavelObra = new ObraResponsavelObra();
		con.setAutoCommit(Boolean.FALSE);
		StringBuffer query = new StringBuffer();
		query.append("INSERT INTO "); 
		query.append(OBRA);
		query.append("("); 
		query.append(NOME); 
		query.append(",");		
		query.append(TIPO); 	
		query.append(",");
		query.append(SITUACAO); 
		query.append(",");
		query.append(DESCRICAO);
		query.append(",");		
		query.append(DATA_INICIO);
		query.append(",");
		query.append(DATA_FIM);
		query.append(",");		
		query.append(ID_CONDOMINIO);
		query.append(") ");
		query.append("VALUES(?,?,?,?,?,?,?)");
		PreparedStatement statement = null;
		try {			
			statement = con.prepareStatement(query.toString(), PreparedStatement.RETURN_GENERATED_KEYS);			
			SQLUtil.setValorPpreparedStatement(statement, 1, obra.getNome(), java.sql.Types.VARCHAR);			
			SQLUtil.setValorPpreparedStatement(statement, 2, obra.getTipo(), java.sql.Types.INTEGER);	
			SQLUtil.setValorPpreparedStatement(statement, 3, obra.getSituacao(), java.sql.Types.INTEGER);
			SQLUtil.setValorPpreparedStatement(statement, 4, obra.getDescricao(), java.sql.Types.VARCHAR);
			SQLUtil.setValorPpreparedStatement(statement, 5, obra.getDataInicio(), java.sql.Types.DATE);
			SQLUtil.setValorPpreparedStatement(statement, 6, obra.getDataFim(), java.sql.Types.DATE);
			SQLUtil.setValorPpreparedStatement(statement, 7, obra.getIdCondominio(), java.sql.Types.INTEGER);
			statement.executeUpdate();
			ResultSet rs = statement.getGeneratedKeys();
			rs.next();
			idObra = rs.getInt(1);
			obraResponsavelObra.setIdObra(idObra);
			obraResponsavelObra.setIdResponsavelObra(obra.getResponsavelObra().getId());
			this.obraResponsavelObraDAO.salvar(obraResponsavelObra, con);
			con.commit();
		}catch (SQLException e) {			
			throw e;			
		} catch (Exception e) {					
			throw e;	
		}finally{
			try{	
				statement.close();
				con.close();
			}catch (SQLException e){
				logger.error("erro sqlstate "+e.getSQLState(), e);
			}
		}
		
	}

	@Override
	public List<Obra> buscarPorCondominioEPeriodoDeAte(Integer idCondominio, Date dataInicioDe, Date dataInicioAte)
			throws SQLException, Exception {
		StringBuffer query = new StringBuffer();
		query.append("SELECT * FROM ");
		query.append(OBRA);
		query.append(" WHERE ");
		query.append(ID_CONDOMINIO);		
		query.append(" = ? ");
		query.append(" and ");
		query.append(DATA_INICIO);		
		query.append(" BETWEEN");
		query.append(" ? and ? ");
		query.append(" ORDER BY ");
		query.append(DATA_INICIO);
		query.append(" DESC, ");
		query.append(NOME);
		query.append(";");		
		Connection con = Conexao.getConexao();		
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Obra obra = null;
		List<Obra> listaObra = new ArrayList<Obra>();
		try {
			preparedStatement = con.prepareStatement(query.toString());
			SQLUtil.setValorPpreparedStatement(preparedStatement, 1, idCondominio, java.sql.Types.INTEGER);
			SQLUtil.setValorPpreparedStatement(preparedStatement, 2, dataInicioDe, java.sql.Types.DATE);
			SQLUtil.setValorPpreparedStatement(preparedStatement, 3, dataInicioAte, java.sql.Types.DATE);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()){
				obra = new Obra();
				obra.setId((Integer) SQLUtil.getValorResultSet(resultSet, ID, java.sql.Types.INTEGER));
				obra.setNome(String.valueOf(SQLUtil.getValorResultSet(resultSet, NOME, java.sql.Types.VARCHAR)));
				obra.setSituacao((Integer) SQLUtil.getValorResultSet(resultSet, SITUACAO, java.sql.Types.INTEGER));
				obra.setTipo((Integer) SQLUtil.getValorResultSet(resultSet, TIPO, java.sql.Types.INTEGER));
				obra.setDescricao(String.valueOf(SQLUtil.getValorResultSet(resultSet, DESCRICAO, java.sql.Types.VARCHAR)));
				obra.setDataInicio((Date) SQLUtil.getValorResultSet(resultSet, DATA_INICIO, java.sql.Types.DATE));
				obra.setDataFim((Date) SQLUtil.getValorResultSet(resultSet, DATA_FIM, java.sql.Types.DATE));	
				obra.setIdCondominio((Integer) SQLUtil.getValorResultSet(resultSet, ID_CONDOMINIO, java.sql.Types.INTEGER));
				obra.setResponsavelObra(this.responsavelObra.buscarPorId(this.obraResponsavelObraDAO.buscarPorIdObra(obra.getId(), con).getIdResponsavelObra(), con));
				listaObra.add(obra);
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
		return listaObra;
	}

	@Override
	public void excluir(Integer idObra) throws SQLException, Exception {
		StringBuffer query = new StringBuffer();
		Connection con = Conexao.getConexao();
		con.setAutoCommit(Boolean.FALSE);
		PreparedStatement statement = null;
		this.obraResponsavelObraDAO.excluirPodIdObra(idObra, con);
		try {			
			query.append("DELETE FROM ");
			query.append(OBRA);
			query.append(" WHERE ");		
			query.append(ID);
			query.append(" = ?");			
			statement = con.prepareStatement(query.toString());
			SQLUtil.setValorPpreparedStatement(statement, 1, idObra, java.sql.Types.INTEGER);
			statement.executeUpdate();
			con.commit();
		} catch (SQLException e) {					
			throw e;
		} catch (Exception e) {					
			throw e;	
		}finally{
			try{				
				statement.close();
				con.close();				
			}catch (SQLException e) {
				logger.error("erro sqlstate "+e.getSQLState(), e);
		    }
		}		
		
	}

	@Override
	public void atualizarObraEAtualizarResponsavelObra(Obra obra) throws SQLException, Exception {
		StringBuffer query = new StringBuffer();
		query.append("UPDATE ");
		query.append(OBRA);
		query.append(" SET ");
		query.append(NOME);
		query.append("= ?, ");
		query.append(TIPO);
		query.append("= ?, ");
		query.append(SITUACAO);
		query.append("= ?, ");		
		query.append(DESCRICAO);	
		query.append("= ?, ");
		query.append(DATA_INICIO);	
		query.append("= ?, ");
		query.append(DATA_FIM);	
		query.append("= ?, ");
		query.append(ID_CONDOMINIO);	
		query.append("= ? ");
		query.append(" WHERE ");
		query.append(ID);
		query.append("= ?");		
		PreparedStatement statement = null;		
		Connection con = Conexao.getConexao();
		con.setAutoCommit(Boolean.FALSE);
		try {
			statement = con.prepareStatement(query.toString());			
			SQLUtil.setValorPpreparedStatement(statement, 1, obra.getNome(), java.sql.Types.VARCHAR);
			SQLUtil.setValorPpreparedStatement(statement, 2, obra.getTipo(), java.sql.Types.INTEGER);
			SQLUtil.setValorPpreparedStatement(statement, 3, obra.getSituacao(), java.sql.Types.INTEGER);
			SQLUtil.setValorPpreparedStatement(statement, 4, obra.getDescricao(), java.sql.Types.VARCHAR);
			SQLUtil.setValorPpreparedStatement(statement, 5, obra.getDataInicio(), java.sql.Types.DATE);
			SQLUtil.setValorPpreparedStatement(statement, 6, obra.getDataFim(), java.sql.Types.DATE);
			SQLUtil.setValorPpreparedStatement(statement, 7, obra.getIdCondominio(), java.sql.Types.INTEGER);
			SQLUtil.setValorPpreparedStatement(statement, 8, obra.getId(), java.sql.Types.INTEGER);
			statement.executeUpdate();
			this.responsavelObra.atualizar(obra.getResponsavelObra(), con);
			con.commit();
		} catch (SQLException e) {
			throw e;
		} catch (Exception e) {
			throw e;	
		}finally{
			try{				
				statement.close();
				con.close();				
			}catch (SQLException e) {
				logger.error("erro sqlstate "+e.getSQLState(), e);
		    }
		}
	}

	@Override
	public void atualizarObraESalvarResponsavelObra(Obra obra) throws SQLException, Exception {
		ObraResponsavelObra obraResponsavelObra = new ObraResponsavelObra();
		Integer idResponsavelObra = null;
		StringBuffer query = new StringBuffer();
		query.append("UPDATE ");
		query.append(OBRA);
		query.append(" SET ");
		query.append(NOME);
		query.append("= ?, ");
		query.append(TIPO);
		query.append("= ?, ");
		query.append(SITUACAO);
		query.append("= ?, ");		
		query.append(DESCRICAO);	
		query.append("= ?, ");
		query.append(DATA_INICIO);	
		query.append("= ?, ");
		query.append(DATA_FIM);	
		query.append("= ?, ");
		query.append(ID_CONDOMINIO);	
		query.append("= ? ");
		query.append(" WHERE ");
		query.append(ID);
		query.append("= ?");		
		PreparedStatement statement = null;		
		Connection con = Conexao.getConexao();
		con.setAutoCommit(Boolean.FALSE);
		try {
			statement = con.prepareStatement(query.toString());			
			SQLUtil.setValorPpreparedStatement(statement, 1, obra.getNome(), java.sql.Types.VARCHAR);
			SQLUtil.setValorPpreparedStatement(statement, 2, obra.getTipo(), java.sql.Types.INTEGER);
			SQLUtil.setValorPpreparedStatement(statement, 3, obra.getSituacao(), java.sql.Types.INTEGER);
			SQLUtil.setValorPpreparedStatement(statement, 4, obra.getDescricao(), java.sql.Types.VARCHAR);
			SQLUtil.setValorPpreparedStatement(statement, 5, obra.getDataInicio(), java.sql.Types.DATE);
			SQLUtil.setValorPpreparedStatement(statement, 6, obra.getDataFim(), java.sql.Types.DATE);
			SQLUtil.setValorPpreparedStatement(statement, 7, obra.getIdCondominio(), java.sql.Types.INTEGER);
			SQLUtil.setValorPpreparedStatement(statement, 8, obra.getId(), java.sql.Types.INTEGER);
			statement.executeUpdate();
			idResponsavelObra = this.responsavelObra.salvar(obra.getResponsavelObra(), con);
			obraResponsavelObra.setIdObra(obra.getId());
			obraResponsavelObra.setIdResponsavelObra(idResponsavelObra);
			this.obraResponsavelObraDAO.atualizarPorIdObra(obraResponsavelObra, con);
			con.commit();
		} catch (SQLException e) {
			throw e;
		} catch (Exception e) {
			throw e;	
		}finally{
			try{				
				statement.close();
				con.close();				
			}catch (SQLException e) {
				logger.error("erro sqlstate "+e.getSQLState(), e);
		    }
		}
		
	}

}
