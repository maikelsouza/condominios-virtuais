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

import br.com.condominiosvirtuais.entity.Receita;
import br.com.condominiosvirtuais.persistence.MeioPagamentoDAO;
import br.com.condominiosvirtuais.persistence.ReceitaDAO;
import br.com.condominiosvirtuais.util.SQLUtil;

public class ReceitaDAOImpl implements ReceitaDAO, Serializable {

	private static final long serialVersionUID = 1L;
	
	private static Logger logger = Logger.getLogger(ReceitaDAOImpl.class);
	
	private static final String RECEITA = "RECEITA";
	
	private static final String ID = "ID";
	
	private static final String DESCRICAO = "DESCRICAO";
	
	private static final String OBSERVACAO = "OBSERVACAO";
	
	private static final String NUMERO_DOCUMENTO = "NUMERO_DOCUMENTO";
	
	private static final String VALOR = "VALOR";
	
	private static final String DATA = "DATA";
	
	private static final String ID_CONDOMINIO = "ID_CONDOMINIO";
	
	private static final String ID_MEIO_PAGAMENTO = "ID_MEIO_PAGAMENTO";
	
	@Inject
	private Instance<MeioPagamentoDAO> meioPagamentoDAO;
	
	
	@Override
	public void salvar(Receita receita) throws SQLException, Exception {
		StringBuffer query = new StringBuffer();
		query.append("INSERT INTO "); 
		query.append(RECEITA);
		query.append("(");
		query.append(DESCRICAO);
		query.append(",");
		query.append(VALOR);
		query.append(",");
		query.append(DATA);
		query.append(",");	
		query.append(OBSERVACAO);
		query.append(",");	
		query.append(NUMERO_DOCUMENTO);
		query.append(",");	
		query.append(ID_CONDOMINIO);
		query.append(",");
		query.append(ID_MEIO_PAGAMENTO);
		query.append(") ");
		query.append("VALUES(?,?,?,?,?,?,?)");
		PreparedStatement statement = null;		
		Connection con = Conexao.getConexao();
		try {
			statement = con.prepareStatement(query.toString());
			SQLUtil.setValorPpreparedStatement(statement, 1, receita.getDescricao(), java.sql.Types.VARCHAR);			
			SQLUtil.setValorPpreparedStatement(statement, 2, receita.getValor(), java.sql.Types.DOUBLE);
			SQLUtil.setValorPpreparedStatement(statement, 3, receita.getData(), java.sql.Types.DATE);
			SQLUtil.setValorPpreparedStatement(statement, 4, receita.getObservacao(), java.sql.Types.VARCHAR);
			SQLUtil.setValorPpreparedStatement(statement, 5, receita.getNumeroDocumento(), java.sql.Types.VARCHAR);			
			SQLUtil.setValorPpreparedStatement(statement, 6, receita.getIdCondominio(), java.sql.Types.INTEGER);
			SQLUtil.setValorPpreparedStatement(statement, 7, receita.getMeioPagamento().getId(), java.sql.Types.INTEGER);
			statement.executeUpdate();
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
	public List<Receita> buscarPorDataDeEDataAteEIdCondominio(Date dataDe, Date dataAte, Integer idCondominio) throws SQLException, Exception {
		StringBuffer query = new StringBuffer();
		query.append("SELECT * FROM ");
		query.append(RECEITA);
		query.append(" WHERE ");
		query.append(DATA);
		query.append(" BETWEEN ");
		query.append("? AND ?");		
		query.append(" AND ");
		query.append(ID_CONDOMINIO);
		query.append(" = ?");
		query.append(" ORDER BY ");
		query.append(DESCRICAO);
		query.append(";");
		Connection con = Conexao.getConexao();
		PreparedStatement preparedStatement = null;		
		List<Receita> listaReceita = new ArrayList<Receita>();
		try {
			preparedStatement = con.prepareStatement(query.toString());
			SQLUtil.setValorPpreparedStatement(preparedStatement, 1, dataDe, java.sql.Types.DATE);
			SQLUtil.setValorPpreparedStatement(preparedStatement, 2, dataAte, java.sql.Types.DATE);
			SQLUtil.setValorPpreparedStatement(preparedStatement, 3, idCondominio, java.sql.Types.INTEGER);
			ResultSet resultSet = preparedStatement.executeQuery();
			Receita receita = null;
			while(resultSet.next()){
				receita = new Receita();								
				receita.setId((Integer) SQLUtil.getValorResultSet(resultSet, ID, java.sql.Types.INTEGER));				
				receita.setDescricao(String.valueOf(SQLUtil.getValorResultSet(resultSet, DESCRICAO, java.sql.Types.VARCHAR)));
				receita.setObservacao(String.valueOf(SQLUtil.getValorResultSet(resultSet, OBSERVACAO, java.sql.Types.VARCHAR)));
				receita.setValor((Double) (SQLUtil.getValorResultSet(resultSet, VALOR, java.sql.Types.DOUBLE)));
				receita.setNumeroDocumento(String.valueOf(SQLUtil.getValorResultSet(resultSet, NUMERO_DOCUMENTO, java.sql.Types.VARCHAR)));				
				receita.setData((Date) SQLUtil.getValorResultSet(resultSet, DATA, java.sql.Types.DATE));
				receita.setIdCondominio((Integer) SQLUtil.getValorResultSet(resultSet, ID_CONDOMINIO, java.sql.Types.INTEGER));
				receita.setMeioPagamento(this.meioPagamentoDAO.get().buscaPorId((Integer) SQLUtil.getValorResultSet(resultSet, ID_MEIO_PAGAMENTO, java.sql.Types.INTEGER), con));
				listaReceita.add(receita);
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
		return listaReceita;
	}

	@Override
	public void atualizar(Receita receita) throws SQLException, Exception {
		StringBuffer query = new StringBuffer();
		query.append("UPDATE ");
		query.append(RECEITA);
		query.append(" SET ");
		query.append(DESCRICAO);
		query.append("= ?, ");
		query.append(VALOR); 
		query.append("= ?, ");
		query.append(DATA); 
		query.append("= ?, ");
		query.append(OBSERVACAO);
		query.append("= ?, ");
		query.append(NUMERO_DOCUMENTO);	
		query.append("= ?, ");		
		query.append(ID_CONDOMINIO);
		query.append("= ?, ");
		query.append(ID_MEIO_PAGAMENTO);
		query.append("= ? ");		
		query.append("WHERE ");
		query.append(ID);
		query.append("= ?");
		Connection con = Conexao.getConexao();
		PreparedStatement statement = null;
		try {			
			statement = con.prepareStatement(query.toString());			
			SQLUtil.setValorPpreparedStatement(statement,1, receita.getDescricao(), java.sql.Types.VARCHAR);
			SQLUtil.setValorPpreparedStatement(statement,2, receita.getValor(), java.sql.Types.DOUBLE);
			SQLUtil.setValorPpreparedStatement(statement,3, receita.getData(), java.sql.Types.DATE);
			SQLUtil.setValorPpreparedStatement(statement,4, receita.getObservacao(), java.sql.Types.VARCHAR);
			SQLUtil.setValorPpreparedStatement(statement,5, receita.getNumeroDocumento(), java.sql.Types.VARCHAR);
			SQLUtil.setValorPpreparedStatement(statement,6, receita.getIdCondominio(), java.sql.Types.INTEGER);
			SQLUtil.setValorPpreparedStatement(statement,7, receita.getMeioPagamento().getId(), java.sql.Types.INTEGER);
			SQLUtil.setValorPpreparedStatement(statement,8, receita.getId(), java.sql.Types.INTEGER);
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
	public void excluir(Receita receita) throws SQLException, Exception {
		StringBuffer query = new StringBuffer();
		query.append("DELETE FROM ");
		query.append(RECEITA);
		query.append(" WHERE ");
		query.append(ID);
		query.append("= ?");
		PreparedStatement statement = null;
		Connection con = Conexao.getConexao();
		try {
			statement = con.prepareStatement(query.toString());
			SQLUtil.setValorPpreparedStatement(statement, 1, receita.getId(), java.sql.Types.INTEGER);			
			statement.executeUpdate();
		} catch (SQLException e) {
			throw e;
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
	

}
