package br.com.condominiosvirtuais.persistence.impl;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.enterprise.inject.Instance;
import javax.inject.Inject;

import org.apache.log4j.Logger;

import br.com.condominiosvirtuais.entity.Classificados;
import br.com.condominiosvirtuais.entity.Condominio;
import br.com.condominiosvirtuais.persistence.ArquivoDAO;
import br.com.condominiosvirtuais.persistence.ClassificadosDAO;
import br.com.condominiosvirtuais.util.SQLUtil;

public class ClassificadosDAOImpl implements ClassificadosDAO, Serializable {
	
	private static final long serialVersionUID = 1L;

	private static Logger logger = Logger.getLogger(ClassificadosDAOImpl.class);
	
	private static final String CLASSIFICADOS = "CLASSIFICADOS";

	private static final String ID = "ID";
	
	private static final String TITULO = "TITULO";
	
	private static final String DESCRICAO = "DESCRICAO";
	
	private static final String  VALOR = "VALOR";
	
	private static final String TELEFONE1 = "TELEFONE1";
	
	private static final String TELEFONE2 = "TELEFONE2";
	
	private static final String DATA_EXPIRA = "DATA_EXPIRA";
	
	private static final String ID_CONDOMINIO = "ID_CONDOMINIO";
	
	private static final String ID_USUARIO = "ID_USUARIO";
	
	@Inject
	private Instance<ArquivoDAO> arquivoDAO;	

	
	@Override
	public List<Classificados> buscarPorCondominio(Condominio condominio) throws SQLException, Exception {		
		StringBuffer query = new StringBuffer();
		query.append("SELECT * FROM ");
		query.append(CLASSIFICADOS);
		query.append(" WHERE ");
		query.append(ID_CONDOMINIO);		
		query.append(" = ?");		
		query.append(" ORDER BY ");
		query.append(TITULO);
		query.append(";");		
		Connection con = Conexao.getConexao();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;		
		Classificados classificados = null;
		List<Classificados> listaClassificados = new ArrayList<Classificados>();
		try {
			preparedStatement = con.prepareStatement(query.toString());
			SQLUtil.setValorPpreparedStatement(preparedStatement, 1, condominio.getId(), java.sql.Types.INTEGER);			
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()){
				classificados = new Classificados();
				classificados.setId((Integer) SQLUtil.getValorResultSet(resultSet, ID, java.sql.Types.INTEGER));
				classificados.setTitulo(String.valueOf(SQLUtil.getValorResultSet(resultSet, TITULO, java.sql.Types.VARCHAR)));
				classificados.setDescricao(String.valueOf(SQLUtil.getValorResultSet(resultSet, DESCRICAO, java.sql.Types.VARCHAR)));
				classificados.setTelefone1((Long) SQLUtil.getValorResultSet(resultSet, TELEFONE1, java.sql.Types.BIGINT));
				classificados.setTelefone2((Long) SQLUtil.getValorResultSet(resultSet, TELEFONE2, java.sql.Types.BIGINT));
				classificados.setValor((Double) SQLUtil.getValorResultSet(resultSet, VALOR, java.sql.Types.DOUBLE));
				classificados.setDataExpira((Date) SQLUtil.getValorResultSet(resultSet, DATA_EXPIRA, java.sql.Types.DATE));
				classificados.setIdCondominio((Integer) SQLUtil.getValorResultSet(resultSet, ID_CONDOMINIO, java.sql.Types.INTEGER));
				classificados.setIdUsuario((Integer) SQLUtil.getValorResultSet(resultSet, ID_USUARIO, java.sql.Types.INTEGER));
				classificados.setImagem(this.arquivoDAO.get().buscarPorClassificados(classificados, con));	
				listaClassificados.add(classificados);
			}
		} catch (SQLException e) {					
			throw e;
		} catch (Exception e) {					
			throw e;	
		}finally{
			try {
				this.arquivoDAO.destroy(this.arquivoDAO.get());
				preparedStatement.close();
				con.close();				
			} catch (SQLException e) {
				logger.error("erro sqlstate "+e.getSQLState(), e);
			}
		}
		return listaClassificados;
	}

	@Override
	public List<Classificados> buscarPorCondominioETitulo(
			Condominio condominio, String titulo) throws SQLException, Exception {		
		StringBuffer query = new StringBuffer();
		query.append("SELECT * FROM ");
		query.append(CLASSIFICADOS);
		query.append(" WHERE ");
		query.append(ID_CONDOMINIO);		
		query.append(" = ?");		
		query.append(" AND UPPER(");
		query.append(TITULO);
		query.append(") LIKE ?");		
		query.append(" ORDER BY ");
		query.append(TITULO);
		query.append(";");		
		Connection con = Conexao.getConexao();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;		
		Classificados classificados = null;		
		List<Classificados> listaClassificados = new ArrayList<Classificados>();
		try {
			preparedStatement = con.prepareStatement(query.toString());
			SQLUtil.setValorPpreparedStatement(preparedStatement, 1, condominio.getId(), java.sql.Types.INTEGER);			
			SQLUtil.setValorPpreparedStatement(preparedStatement, 2, "%"+titulo.toUpperCase()+"%", java.sql.Types.VARCHAR);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()){
				classificados = new Classificados();
				classificados.setId((Integer) SQLUtil.getValorResultSet(resultSet, ID, java.sql.Types.INTEGER));
				classificados.setTitulo(String.valueOf(SQLUtil.getValorResultSet(resultSet, TITULO, java.sql.Types.VARCHAR)));
				classificados.setDescricao(String.valueOf(SQLUtil.getValorResultSet(resultSet, DESCRICAO, java.sql.Types.VARCHAR)));
				classificados.setTelefone1((Long) SQLUtil.getValorResultSet(resultSet, TELEFONE1, java.sql.Types.BIGINT));
				classificados.setTelefone2((Long) SQLUtil.getValorResultSet(resultSet, TELEFONE2, java.sql.Types.BIGINT));
				classificados.setValor((Double) SQLUtil.getValorResultSet(resultSet, VALOR, java.sql.Types.DOUBLE));
				classificados.setDataExpira((Date) SQLUtil.getValorResultSet(resultSet, DATA_EXPIRA, java.sql.Types.DATE));
				classificados.setIdCondominio((Integer) SQLUtil.getValorResultSet(resultSet, ID_CONDOMINIO, java.sql.Types.INTEGER));
				classificados.setIdUsuario((Integer) SQLUtil.getValorResultSet(resultSet, ID_USUARIO, java.sql.Types.INTEGER));
				classificados.setImagem(this.arquivoDAO.get().buscarPorClassificados(classificados, con));	
				listaClassificados.add(classificados);
			}
		} catch (SQLException e) {					
			throw e;
		} catch (Exception e) {					
			throw e;	
		}finally{
			try {
				preparedStatement.close();
				con.close();				
				this.arquivoDAO.destroy(this.arquivoDAO.get());
			} catch (SQLException e) {
				logger.error("erro sqlstate "+e.getSQLState(), e);
			}
		}
		return listaClassificados;
	}

	@Override
	public void salvar(Classificados classificados) throws SQLException, Exception {		
		StringBuffer query = new StringBuffer();
		query.append("INSERT INTO "); 
		query.append(CLASSIFICADOS);
		query.append("("); 
		query.append(TITULO); 
		query.append(",");
		query.append(DESCRICAO); 
		query.append(", ");
		query.append(VALOR); 
		query.append(", ");
		query.append(TELEFONE1); 
		query.append(", ");
		query.append(TELEFONE2); 
		query.append(", ");
		query.append(DATA_EXPIRA); 
		query.append(", ");
		query.append(ID_CONDOMINIO); 
		query.append(", ");
		query.append(ID_USUARIO); 
		query.append(") ");		
		query.append("VALUES(?,?,?,?,?,?,?,?)");
		Connection con = Conexao.getConexao();
		PreparedStatement statement = null;		
		try {
			con.setAutoCommit(false);
			statement = con.prepareStatement(query.toString(), PreparedStatement.RETURN_GENERATED_KEYS);
			SQLUtil.setValorPpreparedStatement(statement, 1, classificados.getTitulo(), java.sql.Types.VARCHAR);
			SQLUtil.setValorPpreparedStatement(statement, 2, classificados.getDescricao(), java.sql.Types.VARCHAR);
			SQLUtil.setValorPpreparedStatement(statement, 3, classificados.getValor(), java.sql.Types.DOUBLE);
			SQLUtil.setValorPpreparedStatement(statement, 4, classificados.getTelefone1(), java.sql.Types.BIGINT);
			SQLUtil.setValorPpreparedStatement(statement, 5, classificados.getTelefone2(), java.sql.Types.BIGINT);
			SQLUtil.setValorPpreparedStatement(statement, 6, classificados.getDataExpira(), java.sql.Types.DATE);
			SQLUtil.setValorPpreparedStatement(statement, 7, classificados.getIdCondominio(), java.sql.Types.INTEGER);
			SQLUtil.setValorPpreparedStatement(statement, 8, classificados.getIdUsuario(), java.sql.Types.INTEGER);
			statement.executeUpdate();
			ResultSet rs = statement.getGeneratedKeys();
			rs.next();
			classificados.setId(rs.getInt(1));
			if(classificados.getImagem() != null){				
				classificados.getImagem().setIdClassificados(classificados.getId());				
				this.arquivoDAO.get().salvarArquivoClassificados(classificados.getImagem(), con);				
			}
			con.commit();
		} catch (SQLException e) {					
			throw e;
		} catch (Exception e) {					
			throw e;	
		}finally{ 
			try {
				statement.close();
				con.close();
				this.arquivoDAO.destroy(this.arquivoDAO.get());
			} catch (SQLException e) {
				logger.error("erro sqlstate "+e.getSQLState(), e);
			}
		}		
	}

	@Override
	public void excluir(Classificados classificados) throws SQLException, Exception {
		StringBuffer query = new StringBuffer();
		Connection con = Conexao.getConexao();
		PreparedStatement statement = null;
		try {
			query.append("DELETE FROM ");
			query.append(CLASSIFICADOS);
			query.append(" WHERE ");		
			query.append(ID);
			query.append(" = ?");
			statement = con.prepareStatement(query.toString());
			SQLUtil.setValorPpreparedStatement(statement, 1, classificados.getId(), java.sql.Types.INTEGER);
			if(classificados.getImagem() != null){
				classificados.getImagem().setIdClassificados(classificados.getId());								
				this.arquivoDAO.get().excluirArquivoClassificados(classificados.getImagem(), con);
			}
			statement.executeUpdate();	
		} catch (SQLException e) {					
			throw e;
		} catch (Exception e) {					
			throw e;			
		}finally{
			try {
				statement.close();
				con.close();			
				this.arquivoDAO.destroy(this.arquivoDAO.get());
			} catch (SQLException e) {
				logger.error("erro sqlstate "+e.getSQLState(), e);
			}
		}		
	}

	@Override
	public void atualizar(Classificados classificados) throws SQLException, Exception {
		StringBuffer query = new StringBuffer();
		query.append("UPDATE ");
		query.append(CLASSIFICADOS);
		query.append(" SET ");
		query.append(TITULO); 
		query.append(" = ?, ");
		query.append(DESCRICAO); 
		query.append(" = ?, ");
		query.append(VALOR); 
		query.append(" = ?, ");
		query.append(TELEFONE1); 
		query.append(" = ?, ");
		query.append(TELEFONE2); 
		query.append(" = ?, ");
		query.append(DATA_EXPIRA); 
		query.append(" = ?, ");
		query.append(ID_CONDOMINIO); 
		query.append(" = ?, ");
		query.append(ID_USUARIO); 
		query.append(" = ? ");		
		query.append("WHERE ");
		query.append(ID);
		query.append("= ?");
		Connection con = Conexao.getConexao();
		PreparedStatement statement = null;
		try {
			con.setAutoCommit(false);
			statement = con.prepareStatement(query.toString());
			SQLUtil.setValorPpreparedStatement(statement, 1, classificados.getTitulo(), java.sql.Types.VARCHAR);
			SQLUtil.setValorPpreparedStatement(statement, 2, classificados.getDescricao(), java.sql.Types.VARCHAR);
			SQLUtil.setValorPpreparedStatement(statement, 3, classificados.getValor(), java.sql.Types.DOUBLE);
			SQLUtil.setValorPpreparedStatement(statement, 4, classificados.getTelefone1(), java.sql.Types.BIGINT);
			SQLUtil.setValorPpreparedStatement(statement, 5, classificados.getTelefone2(), java.sql.Types.BIGINT);
			SQLUtil.setValorPpreparedStatement(statement, 6, classificados.getDataExpira(), java.sql.Types.DATE);
			SQLUtil.setValorPpreparedStatement(statement, 7, classificados.getIdCondominio(), java.sql.Types.INTEGER);
			SQLUtil.setValorPpreparedStatement(statement, 8, classificados.getIdUsuario(), java.sql.Types.INTEGER);
			SQLUtil.setValorPpreparedStatement(statement, 9, classificados.getId(), java.sql.Types.INTEGER);
			if (classificados.getImagem() != null){
				if(classificados.getImagem().getId() != null){				
					classificados.getImagem().setIdClassificados(classificados.getId());
					this.arquivoDAO.get().atualizarArquivoClassificados(classificados.getImagem(), con);			
				}else{
					this.arquivoDAO.get().salvarArquivoClassificados(classificados.getImagem(), con);
				}				
			}
			statement.executeUpdate();		
			con.commit();
		} catch (SQLException e) {					
			throw e;
		} catch (Exception e) {					
			throw e;	
		} finally{
			try {
				statement.close();
				con.close();
				this.arquivoDAO.destroy(this.arquivoDAO.get());
			} catch (SQLException e) {
				logger.error("erro sqlstate "+e.getSQLState(), e);
			}
		}	
	}

	@Override
	public Classificados buscarPorIdClassificadosSemImagem(Integer idClassificado,
			Connection con) throws SQLException, Exception {	
		StringBuffer query = new StringBuffer();
		query.append("SELECT * FROM ");
		query.append(CLASSIFICADOS);
		query.append(" WHERE ");
		query.append(ID);		
		query.append(" = ?");
		query.append(";");
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;		
		Classificados classificados = null;		
		try {
			preparedStatement = con.prepareStatement(query.toString());
			SQLUtil.setValorPpreparedStatement(preparedStatement, 1, idClassificado, java.sql.Types.INTEGER);			
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()){
				classificados = new Classificados();
				classificados.setId((Integer) SQLUtil.getValorResultSet(resultSet, ID, java.sql.Types.INTEGER));
				classificados.setTitulo(String.valueOf(SQLUtil.getValorResultSet(resultSet, TITULO, java.sql.Types.VARCHAR)));
				classificados.setDescricao(String.valueOf(SQLUtil.getValorResultSet(resultSet, DESCRICAO, java.sql.Types.VARCHAR)));
				classificados.setTelefone1((Long) SQLUtil.getValorResultSet(resultSet, TELEFONE1, java.sql.Types.BIGINT));
				classificados.setTelefone2((Long) SQLUtil.getValorResultSet(resultSet, TELEFONE2, java.sql.Types.BIGINT));
				classificados.setValor((Double) SQLUtil.getValorResultSet(resultSet, VALOR, java.sql.Types.DOUBLE));
				classificados.setDataExpira((Date) SQLUtil.getValorResultSet(resultSet, DATA_EXPIRA, java.sql.Types.DATE));
				classificados.setIdCondominio((Integer) SQLUtil.getValorResultSet(resultSet, ID_CONDOMINIO, java.sql.Types.INTEGER));
				classificados.setIdUsuario((Integer) SQLUtil.getValorResultSet(resultSet, ID_USUARIO, java.sql.Types.INTEGER));				
			}
		} catch (SQLException e) {					
			con.rollback();
			throw e;
		} catch (Exception e) {
			con.rollback();
			throw e;	
		}	
		return classificados;
	}

	@Override
	public List<Classificados> buscarPorMaiorIgualDataExpira(Date dataExpira) throws SQLException, Exception {		
		StringBuffer query = new StringBuffer();
		query.append("SELECT * FROM ");
		query.append(CLASSIFICADOS);
		query.append(" WHERE ");
		query.append(DATA_EXPIRA);		
		query.append(" <= ?");		
		query.append(";");		
		Connection con = Conexao.getConexao();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;		
		Classificados classificados = null;		
		List<Classificados> listaClassificados = new ArrayList<Classificados>();
		try {
			preparedStatement = con.prepareStatement(query.toString());
			SQLUtil.setValorPpreparedStatement(preparedStatement, 1, dataExpira, java.sql.Types.DATE);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()){
				classificados = new Classificados();
				classificados.setId((Integer) SQLUtil.getValorResultSet(resultSet, ID, java.sql.Types.INTEGER));
				classificados.setTitulo(String.valueOf(SQLUtil.getValorResultSet(resultSet, TITULO, java.sql.Types.VARCHAR)));
				classificados.setDescricao(String.valueOf(SQLUtil.getValorResultSet(resultSet, DESCRICAO, java.sql.Types.VARCHAR)));
				classificados.setTelefone1((Long) SQLUtil.getValorResultSet(resultSet, TELEFONE1, java.sql.Types.BIGINT));
				classificados.setTelefone2((Long) SQLUtil.getValorResultSet(resultSet, TELEFONE2, java.sql.Types.BIGINT));
				classificados.setValor((Double) SQLUtil.getValorResultSet(resultSet, VALOR, java.sql.Types.DOUBLE));
				classificados.setDataExpira((Date) SQLUtil.getValorResultSet(resultSet, DATA_EXPIRA, java.sql.Types.DATE));
				classificados.setIdCondominio((Integer) SQLUtil.getValorResultSet(resultSet, ID_CONDOMINIO, java.sql.Types.INTEGER));
				classificados.setIdUsuario((Integer) SQLUtil.getValorResultSet(resultSet, ID_USUARIO, java.sql.Types.INTEGER));				
				classificados.setImagem(this.arquivoDAO.get().buscarPorClassificados(classificados, con));	
				listaClassificados.add(classificados);
			}
		} catch (SQLException e) {					
			throw e;
		} catch (Exception e) {
			throw e;
		}finally{
			try {
				preparedStatement.close();
				con.close();				
				this.arquivoDAO.destroy(this.arquivoDAO.get());
			} catch (SQLException e) {
				logger.error("erro sqlstate "+e.getSQLState(), e);
			}
		}
		return listaClassificados;		
	}	
	
	@Override
	public List<Classificados> buscarPorDataExpira(Date dataExpira) throws SQLException, Exception {
		StringBuffer query = new StringBuffer();
		query.append("SELECT * FROM ");
		query.append(CLASSIFICADOS);
		query.append(" WHERE ");
		query.append(DATA_EXPIRA);		
		query.append(" = ?");		
		query.append(";");		
		Connection con = Conexao.getConexao();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;		
		Classificados classificados = null;		
		List<Classificados> listaClassificados = new ArrayList<Classificados>();
		try {
			preparedStatement = con.prepareStatement(query.toString());
			SQLUtil.setValorPpreparedStatement(preparedStatement, 1, dataExpira, java.sql.Types.DATE);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()){
				classificados = new Classificados();
				classificados.setId((Integer) SQLUtil.getValorResultSet(resultSet, ID, java.sql.Types.INTEGER));
				classificados.setTitulo(String.valueOf(SQLUtil.getValorResultSet(resultSet, TITULO, java.sql.Types.VARCHAR)));
				classificados.setDescricao(String.valueOf(SQLUtil.getValorResultSet(resultSet, DESCRICAO, java.sql.Types.VARCHAR)));
				classificados.setTelefone1((Long) SQLUtil.getValorResultSet(resultSet, TELEFONE1, java.sql.Types.BIGINT));
				classificados.setTelefone2((Long) SQLUtil.getValorResultSet(resultSet, TELEFONE2, java.sql.Types.BIGINT));
				classificados.setValor((Double) SQLUtil.getValorResultSet(resultSet, VALOR, java.sql.Types.DOUBLE));
				classificados.setDataExpira((Date) SQLUtil.getValorResultSet(resultSet, DATA_EXPIRA, java.sql.Types.DATE));
				classificados.setIdCondominio((Integer) SQLUtil.getValorResultSet(resultSet, ID_CONDOMINIO, java.sql.Types.INTEGER));
				classificados.setIdUsuario((Integer) SQLUtil.getValorResultSet(resultSet, ID_USUARIO, java.sql.Types.INTEGER));
				classificados.setImagem(this.arquivoDAO.get().buscarPorClassificados(classificados, con));	
				listaClassificados.add(classificados);
			}
		} catch (SQLException e) {					
			throw e;
		} catch (Exception e) {
			throw e;
		}finally{
			try {
				preparedStatement.close();
				con.close();
				this.arquivoDAO.destroy(this.arquivoDAO.get());
			} catch (SQLException e) {
				logger.error("erro sqlstate "+e.getSQLState(), e);
			}
		}
		return listaClassificados;		
	}	
}