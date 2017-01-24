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

import br.com.condominiosvirtuais.entity.Chamado;
import br.com.condominiosvirtuais.entity.Condominio;
import br.com.condominiosvirtuais.enumeration.ChamadoStatusEnum;
import br.com.condominiosvirtuais.persistence.ChamadoDAO;
import br.com.condominiosvirtuais.util.SQLUtil;

public class ChamadoDAOImpl implements ChamadoDAO, Serializable {

	private static final long serialVersionUID = 1L;
	
	private static Logger logger = Logger.getLogger(ChamadoDAOImpl.class);
	
	private static final String CHAMADO = "CHAMADO";
	
    private static final String ID = "ID";
	
	private static final String NOME = "NOME";
	
	private static final String DESCRICAO = "DESCRICAO";
	
	private static final String STATUS = "STATUS";
	
	private static final String OUTROS = "OUTROS";
	
	private static final String DATA_ABERTURA = "DATA_ABERTURA";
	
	private static final String DATA_FECHAMENTO = "DATA_FECHAMENTO";
	
	private static final String COMENTARIO = "COMENTARIO";
	
	private static final String ID_TIPO_CHAMADO = "ID_TIPO_CHAMADO";
	
	private static final String ID_CONDOMINIO = "ID_CONDOMINIO";
	
	private static final String ID_USUARIO = "ID_USUARIO";
	
	@Inject
	private TipoChamadoDAOImpl tipoChamadoDAO;
	
	@Inject
	private CondominioDAOImpl condominioDAO;
	@Inject
	private UsuarioDAOImpl usuarioDAO;

	@Override
	public void salvar(Chamado chamado) throws SQLException, Exception {
		Connection con = Conexao.getConexao();
		StringBuffer query = new StringBuffer();
		query.append("INSERT INTO "); 
		query.append(CHAMADO);
		query.append("("); 
		query.append(NOME); 
		query.append(",");
		query.append(DESCRICAO); 
		query.append(",");
		query.append(STATUS); 
		query.append(",");
		query.append(OUTROS); 
		query.append(",");
		query.append(DATA_ABERTURA); 
		query.append(",");
		query.append(DATA_FECHAMENTO); 
		query.append(",");
		query.append(COMENTARIO); 
		query.append(",");
		query.append(ID_TIPO_CHAMADO); 
		query.append(",");
		query.append(ID_USUARIO); 
		query.append(",");
		query.append(ID_CONDOMINIO); 
		query.append(") ");
		query.append("VALUES(?,?,?,?,?,?,?,?,?,?)");
		PreparedStatement statement = null;
		try {			
			statement = con.prepareStatement(query.toString());
			SQLUtil.setValorPpreparedStatement(statement, 1, chamado.getNome(), java.sql.Types.VARCHAR);
			SQLUtil.setValorPpreparedStatement(statement, 2, chamado.getDescricao(), java.sql.Types.VARCHAR);
			SQLUtil.setValorPpreparedStatement(statement, 3, chamado.getStatus(), java.sql.Types.INTEGER);
			SQLUtil.setValorPpreparedStatement(statement, 4, chamado.getOutros(), java.sql.Types.VARCHAR);
			SQLUtil.setValorPpreparedStatement(statement, 5, chamado.getDataAbertura(), java.sql.Types.TIMESTAMP);
			SQLUtil.setValorPpreparedStatement(statement, 6, chamado.getDataFechamento(), java.sql.Types.TIMESTAMP);
			SQLUtil.setValorPpreparedStatement(statement, 7, chamado.getComentario(), java.sql.Types.VARCHAR);
			SQLUtil.setValorPpreparedStatement(statement, 8, chamado.getTipoChamado().getId(), java.sql.Types.INTEGER);
			SQLUtil.setValorPpreparedStatement(statement, 9, chamado.getUsuario().getId(), java.sql.Types.INTEGER);
			SQLUtil.setValorPpreparedStatement(statement, 10, chamado.getCondominio().getId(), java.sql.Types.INTEGER);
			statement.execute();			
		} catch (SQLException e) {
			throw e;
		} catch (Exception e) {			
			throw e;		
		}finally {
			try {
				statement.close();
				con.close();				
			} catch (SQLException e) {
				logger.error("erro sqlstate "+e.getSQLState(), e);			
			}
		}	
		
	}

	@Override
	public void excluir(Integer idChamado) throws SQLException, Exception {
		StringBuffer query = new StringBuffer();
		Connection con = Conexao.getConexao();
		PreparedStatement statement = null;
		try {			
			query.append("DELETE FROM ");
			query.append(CHAMADO);
			query.append(" WHERE ");		
			query.append(ID);
			query.append(" = ?");
			statement = con.prepareStatement(query.toString());			
			SQLUtil.setValorPpreparedStatement(statement, 1, idChamado, java.sql.Types.INTEGER);			
			statement.executeUpdate();
		} catch (SQLException e) {				
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
	public List<Chamado> buscarPorCondominioEStatus(Condominio condominio, Integer status) throws SQLException, Exception {
		StringBuffer query = new StringBuffer();
		query.append("SELECT * FROM ");
		query.append(CHAMADO);
		query.append(" WHERE ");
		query.append(ID_CONDOMINIO);
		query.append(" = ?");
		query.append(" AND ");
		query.append(STATUS);
		query.append(" = ?");
		query.append(" ORDER BY ");
		query.append(DATA_ABERTURA);
		query.append(";");		
		Connection con = Conexao.getConexao();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		List<Chamado> listaChamados = new ArrayList<Chamado>();
		Chamado chamado = null;
		try {
			preparedStatement = con.prepareStatement(query.toString());
			SQLUtil.setValorPpreparedStatement(preparedStatement, 1, condominio.getId(), java.sql.Types.INTEGER);
			SQLUtil.setValorPpreparedStatement(preparedStatement, 2, status, java.sql.Types.INTEGER);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()){
				chamado = new Chamado();
				chamado.setId((Integer) SQLUtil.getValorResultSet(resultSet, ID, java.sql.Types.INTEGER));
				chamado.setNome(String.valueOf(SQLUtil.getValorResultSet(resultSet, NOME, java.sql.Types.VARCHAR)));
				chamado.setDescricao(String.valueOf(SQLUtil.getValorResultSet(resultSet, DESCRICAO, java.sql.Types.VARCHAR)));
				chamado.setStatus((Integer) (SQLUtil.getValorResultSet(resultSet, STATUS, java.sql.Types.INTEGER)));
				chamado.setOutros(String.valueOf(SQLUtil.getValorResultSet(resultSet, OUTROS, java.sql.Types.VARCHAR)));
				chamado.setDataAbertura((Date) SQLUtil.getValorResultSet(resultSet, DATA_ABERTURA, java.sql.Types.TIMESTAMP));
				chamado.setDataFechamento((Date) SQLUtil.getValorResultSet(resultSet, DATA_FECHAMENTO, java.sql.Types.TIMESTAMP));
				chamado.setComentario(String.valueOf(SQLUtil.getValorResultSet(resultSet, COMENTARIO, java.sql.Types.VARCHAR)));
				chamado.setTipoChamado(this.tipoChamadoDAO.buscarPorId((Integer) SQLUtil.getValorResultSet(resultSet, ID_TIPO_CHAMADO, java.sql.Types.INTEGER),con));
				chamado.setCondominio(this.condominioDAO.buscarCondominioPorId((Integer) SQLUtil.getValorResultSet(resultSet, ID_CONDOMINIO, java.sql.Types.INTEGER),con));
				chamado.setUsuario(this.usuarioDAO.buscarPorId((Integer) SQLUtil.getValorResultSet(resultSet, ID_USUARIO, java.sql.Types.INTEGER),con));							
				listaChamados.add(chamado);
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
		return listaChamados;		
	}

	@Override
	public List<Chamado> buscarPorUsuario(Integer idChamado) throws SQLException, Exception {
		StringBuffer query = new StringBuffer();
		query.append("SELECT * FROM ");
		query.append(CHAMADO);
		query.append(" WHERE ");
		query.append(ID_USUARIO);
		query.append(" = ?");		
		query.append(" ORDER BY ");
		query.append(DATA_ABERTURA);
		query.append(";");		
		Connection con = Conexao.getConexao();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		List<Chamado> listaChamados = new ArrayList<Chamado>();
		Chamado chamado = null;
		try {
			preparedStatement = con.prepareStatement(query.toString());
			SQLUtil.setValorPpreparedStatement(preparedStatement, 1, idChamado, java.sql.Types.INTEGER);			
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()){
				chamado = new Chamado();
				chamado.setId((Integer) SQLUtil.getValorResultSet(resultSet, ID, java.sql.Types.INTEGER));
				chamado.setNome(String.valueOf(SQLUtil.getValorResultSet(resultSet, NOME, java.sql.Types.VARCHAR)));
				chamado.setDescricao(String.valueOf(SQLUtil.getValorResultSet(resultSet, DESCRICAO, java.sql.Types.VARCHAR)));
				chamado.setStatus((Integer) (SQLUtil.getValorResultSet(resultSet, STATUS, java.sql.Types.INTEGER)));
				chamado.setOutros(String.valueOf(SQLUtil.getValorResultSet(resultSet, OUTROS, java.sql.Types.VARCHAR)));
				chamado.setComentario(String.valueOf(SQLUtil.getValorResultSet(resultSet, COMENTARIO, java.sql.Types.VARCHAR)));
				chamado.setDataAbertura((Date) SQLUtil.getValorResultSet(resultSet, DATA_ABERTURA, java.sql.Types.TIMESTAMP));
				chamado.setDataFechamento((Date) SQLUtil.getValorResultSet(resultSet, DATA_FECHAMENTO, java.sql.Types.TIMESTAMP));
				chamado.setTipoChamado(this.tipoChamadoDAO.buscarPorId((Integer) SQLUtil.getValorResultSet(resultSet, ID_TIPO_CHAMADO, java.sql.Types.INTEGER),con));
				chamado.setCondominio(this.condominioDAO.buscarCondominioPorId((Integer) SQLUtil.getValorResultSet(resultSet, ID_CONDOMINIO, java.sql.Types.INTEGER),con));
				chamado.setUsuario(this.usuarioDAO.buscarPorId((Integer) SQLUtil.getValorResultSet(resultSet, ID_USUARIO, java.sql.Types.INTEGER),con));							
				listaChamados.add(chamado);
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
		return listaChamados;		
	}

	@Override
	public List<Chamado> buscarPorCondominio(Condominio condominio) throws SQLException, Exception {
		StringBuffer query = new StringBuffer();
		query.append("SELECT * FROM ");
		query.append(CHAMADO);
		query.append(" WHERE ");
		query.append(ID_CONDOMINIO);
		query.append(" = ?");		
		query.append(" ORDER BY ");
		query.append(DATA_ABERTURA);
		query.append(";");		
		Connection con = Conexao.getConexao();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		List<Chamado> listaChamados = new ArrayList<Chamado>();
		Chamado chamado = null;
		try {
			preparedStatement = con.prepareStatement(query.toString());
			SQLUtil.setValorPpreparedStatement(preparedStatement, 1, condominio.getId(), java.sql.Types.INTEGER);			
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()){
				chamado = new Chamado();
				chamado.setId((Integer) SQLUtil.getValorResultSet(resultSet, ID, java.sql.Types.INTEGER));
				chamado.setNome(String.valueOf(SQLUtil.getValorResultSet(resultSet, NOME, java.sql.Types.VARCHAR)));
				chamado.setDescricao(String.valueOf(SQLUtil.getValorResultSet(resultSet, DESCRICAO, java.sql.Types.VARCHAR)));
				chamado.setStatus((Integer) (SQLUtil.getValorResultSet(resultSet, STATUS, java.sql.Types.INTEGER)));
				chamado.setOutros(String.valueOf(SQLUtil.getValorResultSet(resultSet, OUTROS, java.sql.Types.VARCHAR)));
				chamado.setComentario(String.valueOf(SQLUtil.getValorResultSet(resultSet, COMENTARIO, java.sql.Types.VARCHAR)));
				chamado.setDataAbertura((Date) SQLUtil.getValorResultSet(resultSet, DATA_ABERTURA, java.sql.Types.TIMESTAMP));
				chamado.setDataFechamento((Date) SQLUtil.getValorResultSet(resultSet, DATA_FECHAMENTO, java.sql.Types.TIMESTAMP));
				chamado.setTipoChamado(this.tipoChamadoDAO.buscarPorId((Integer) SQLUtil.getValorResultSet(resultSet, ID_TIPO_CHAMADO, java.sql.Types.INTEGER),con));
				chamado.setCondominio(this.condominioDAO.buscarCondominioPorId((Integer) SQLUtil.getValorResultSet(resultSet, ID_CONDOMINIO, java.sql.Types.INTEGER),con));
				chamado.setUsuario(this.usuarioDAO.buscarPorId((Integer) SQLUtil.getValorResultSet(resultSet, ID_USUARIO, java.sql.Types.INTEGER),con));							
				listaChamados.add(chamado);
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
		return listaChamados;		

	}

	@Override
	public void atualizarStatus(Integer idChamado, Integer status) throws SQLException, Exception {
		StringBuffer query = new StringBuffer();
		Connection con = Conexao.getConexao();		
		query.append("UPDATE ");
		query.append(CHAMADO);
		query.append(" SET ");
		query.append(STATUS);
		query.append("= ? ");
		query.append("WHERE ");
		query.append(ID);
		query.append("= ?");
		PreparedStatement statement = null;
		try {			
			statement = con.prepareStatement(query.toString());
			SQLUtil.setValorPpreparedStatement(statement, 1, status, java.sql.Types.INTEGER);
			SQLUtil.setValorPpreparedStatement(statement, 2, idChamado, java.sql.Types.INTEGER);
			statement.executeUpdate();				
		} catch (SQLException e) {							
			throw e;
		}catch (Exception e) {					
			throw e;	
		}finally {
			try {
				statement.close();
				con.close();				
			} catch (SQLException e) {
				logger.error("erro sqlstate "+e.getSQLState(), e);			
			}
		}
	}

	@Override
	public void fecharChamado(Integer idChamado, Date dataFechamento, String comentario) throws SQLException, Exception {
		StringBuffer query = new StringBuffer();
		Connection con = Conexao.getConexao();		
		query.append("UPDATE ");
		query.append(CHAMADO);
		query.append(" SET ");
		query.append(COMENTARIO);
		query.append("= ?, ");
		query.append(STATUS);
		query.append("= ?, ");
		query.append(DATA_FECHAMENTO);
		query.append("= ? ");
		query.append("WHERE ");
		query.append(ID);
		query.append("= ?");
		PreparedStatement statement = null;
		try {			
			statement = con.prepareStatement(query.toString());
			SQLUtil.setValorPpreparedStatement(statement, 1, comentario, java.sql.Types.VARCHAR);
			SQLUtil.setValorPpreparedStatement(statement, 2, ChamadoStatusEnum.FECHADO.getStatus(), java.sql.Types.INTEGER);
			SQLUtil.setValorPpreparedStatement(statement, 3, dataFechamento, java.sql.Types.TIMESTAMP);
			SQLUtil.setValorPpreparedStatement(statement, 4, idChamado, java.sql.Types.INTEGER);
			statement.executeUpdate();				
		} catch (SQLException e) {							
			throw e;
		}catch (Exception e) {					
			throw e;	
		}finally {
			try {
				statement.close();
				con.close();				
			} catch (SQLException e) {
				logger.error("erro sqlstate "+e.getSQLState(), e);			
			}
		}		

		
	}

}
