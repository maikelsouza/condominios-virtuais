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

import br.com.condominiosvirtuais.entity.Despesa;
import br.com.condominiosvirtuais.persistence.BancoDAO;
import br.com.condominiosvirtuais.persistence.DespesaDAO;
import br.com.condominiosvirtuais.persistence.MeioPagamentoDAO;
import br.com.condominiosvirtuais.util.SQLUtil;

public class DespesaDAOImpl implements DespesaDAO, Serializable {
	
private static final long serialVersionUID = 1L;
	
	private static Logger logger = Logger.getLogger(DespesaDAOImpl.class);
	
	private static final String DESPESA = "DESPESA";
	
	private static final String ID = "ID";
	
	private static final String DESCRICAO = "DESCRICAO";
	
	private static final String OBSERVACAO = "OBSERVACAO";
	
	private static final String NUMERO_DOCUMENTO = "NUMERO_DOCUMENTO";
	
	private static final String VALOR = "VALOR";
	
	private static final String DATA = "DATA";
	
	private static final String ID_CONDOMINIO = "ID_CONDOMINIO";
	
	private static final String ID_BANCO = "ID_BANCO";
	
	private static final String ID_MEIO_PAGAMENTO = "ID_MEIO_PAGAMENTO";
	
	@Inject
	private Instance<MeioPagamentoDAO> meioPagamentoDAO;
	
	@Inject
	private Instance<BancoDAO> bancoDAO;
	
	@Override
	public void salvar(Despesa despesa) throws SQLException, Exception {
		StringBuffer query = new StringBuffer();
		query.append("INSERT INTO "); 
		query.append(DESPESA);
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
		query.append(ID_BANCO);
		query.append(",");
		query.append(ID_MEIO_PAGAMENTO);
		query.append(") ");
		query.append("VALUES(?,?,?,?,?,?,?,?)");
		PreparedStatement statement = null;		
		Connection con = Conexao.getConexao();
		try {
			statement = con.prepareStatement(query.toString());
			SQLUtil.setValorPpreparedStatement(statement, 1, despesa.getDescricao(), java.sql.Types.VARCHAR);			
			SQLUtil.setValorPpreparedStatement(statement, 2, despesa.getValor(), java.sql.Types.DOUBLE);
			SQLUtil.setValorPpreparedStatement(statement, 3, despesa.getData(), java.sql.Types.DATE);
			SQLUtil.setValorPpreparedStatement(statement, 4, despesa.getObservacao(), java.sql.Types.VARCHAR);
			SQLUtil.setValorPpreparedStatement(statement, 5, despesa.getNumeroDocumento(), java.sql.Types.VARCHAR);			
			SQLUtil.setValorPpreparedStatement(statement, 6, despesa.getIdCondominio(), java.sql.Types.INTEGER);
			SQLUtil.setValorPpreparedStatement(statement, 7, despesa.getBanco().getId(), java.sql.Types.INTEGER);
			SQLUtil.setValorPpreparedStatement(statement, 8, despesa.getMeioPagamento().getId(), java.sql.Types.INTEGER);
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
	public List<Despesa> buscarPorDataDeEDataAteEIdCondominio(Date dataDe, Date dataAte, Integer idCondominio) throws SQLException, Exception {
		StringBuffer query = new StringBuffer();
		query.append("SELECT * FROM ");
		query.append(DESPESA);
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
		List<Despesa> listaDespesa = new ArrayList<Despesa>();
		try {
			preparedStatement = con.prepareStatement(query.toString());
			SQLUtil.setValorPpreparedStatement(preparedStatement, 1, dataDe, java.sql.Types.DATE);
			SQLUtil.setValorPpreparedStatement(preparedStatement, 2, dataAte, java.sql.Types.DATE);
			SQLUtil.setValorPpreparedStatement(preparedStatement, 3, idCondominio, java.sql.Types.INTEGER);
			ResultSet resultSet = preparedStatement.executeQuery();
			Despesa despesa = null;
			while(resultSet.next()){
				despesa = new Despesa();								
				despesa.setId((Integer) SQLUtil.getValorResultSet(resultSet, ID, java.sql.Types.INTEGER));				
				despesa.setDescricao(String.valueOf(SQLUtil.getValorResultSet(resultSet, DESCRICAO, java.sql.Types.VARCHAR)));
				despesa.setObservacao(String.valueOf(SQLUtil.getValorResultSet(resultSet, OBSERVACAO, java.sql.Types.VARCHAR)));
				despesa.setValor((Double) (SQLUtil.getValorResultSet(resultSet, VALOR, java.sql.Types.DOUBLE)));
				despesa.setNumeroDocumento(String.valueOf(SQLUtil.getValorResultSet(resultSet, NUMERO_DOCUMENTO, java.sql.Types.VARCHAR)));				
				despesa.setData((Date) SQLUtil.getValorResultSet(resultSet, DATA, java.sql.Types.DATE));
				despesa.setIdCondominio((Integer) SQLUtil.getValorResultSet(resultSet, ID_CONDOMINIO, java.sql.Types.INTEGER));
				despesa.setBanco(this.bancoDAO.get().buscarPorId((Integer) SQLUtil.getValorResultSet(resultSet, ID_BANCO, java.sql.Types.INTEGER), con));
				despesa.setMeioPagamento(this.meioPagamentoDAO.get().buscaPorId((Integer) SQLUtil.getValorResultSet(resultSet, ID_MEIO_PAGAMENTO, java.sql.Types.INTEGER), con));
				listaDespesa.add(despesa);
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
		return listaDespesa;
	}

	@Override
	public void atualizar(Despesa despesa) throws SQLException, Exception {
		StringBuffer query = new StringBuffer();
		query.append("UPDATE ");
		query.append(DESPESA);
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
		query.append("= ?, ");		
		query.append(ID_BANCO);
		query.append("= ? ");		
		query.append("WHERE ");
		query.append(ID);
		query.append("= ?");
		Connection con = Conexao.getConexao();
		PreparedStatement statement = null;
		try {			
			statement = con.prepareStatement(query.toString());			
			SQLUtil.setValorPpreparedStatement(statement,1, despesa.getDescricao(), java.sql.Types.VARCHAR);
			SQLUtil.setValorPpreparedStatement(statement,2, despesa.getValor(), java.sql.Types.DOUBLE);
			SQLUtil.setValorPpreparedStatement(statement,3, despesa.getData(), java.sql.Types.DATE);
			SQLUtil.setValorPpreparedStatement(statement,4, despesa.getObservacao(), java.sql.Types.VARCHAR);
			SQLUtil.setValorPpreparedStatement(statement,5, despesa.getNumeroDocumento(), java.sql.Types.VARCHAR);
			SQLUtil.setValorPpreparedStatement(statement,6, despesa.getIdCondominio(), java.sql.Types.INTEGER);
			SQLUtil.setValorPpreparedStatement(statement,7, despesa.getMeioPagamento().getId(), java.sql.Types.INTEGER);
			SQLUtil.setValorPpreparedStatement(statement,8, despesa.getBanco().getId(), java.sql.Types.INTEGER);
			SQLUtil.setValorPpreparedStatement(statement,9, despesa.getId(), java.sql.Types.INTEGER);
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
	public void excluir(Despesa despesa) throws SQLException, Exception {
		StringBuffer query = new StringBuffer();
		query.append("DELETE FROM ");
		query.append(DESPESA);
		query.append(" WHERE ");
		query.append(ID);
		query.append("= ?");
		PreparedStatement statement = null;
		Connection con = Conexao.getConexao();
		try {
			statement = con.prepareStatement(query.toString());
			SQLUtil.setValorPpreparedStatement(statement, 1, despesa.getId(), java.sql.Types.INTEGER);			
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
