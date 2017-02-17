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

import br.com.condominiosvirtuais.entity.Boleto;
import br.com.condominiosvirtuais.persistence.BeneficiarioDAO;
import br.com.condominiosvirtuais.persistence.BoletoDAO;
import br.com.condominiosvirtuais.persistence.CondominoDAO;
import br.com.condominiosvirtuais.persistence.ContaBancariaDAO;
import br.com.condominiosvirtuais.util.SQLUtil;

public class BoletoDAOImpl implements BoletoDAO, Serializable {

	private static final long serialVersionUID = 1L;
	
	private static Logger logger = Logger.getLogger(BoletoDAOImpl.class);
	
	@Inject
	private BeneficiarioDAO beneficiarioDAO;
	
	@Inject
	private ContaBancariaDAO contaBancariaDAO;
	
	@Inject
	private CondominoDAO condominoDAO;
	
	private static final String BOLETO = "BOLETO";
	
	private static final String ID = "ID";
	
	private static final String EMISSAO = "EMISSAO";
	
	private static final String VENCIMENTO = "VENCIMENTO";
	
	private static final String DOCUMENTO = "DOCUMENTO";
	
	private static final String NUMERO = "NUMERO";
	
	private static final String TITULO = "TITULO";
	
	private static final String VALOR = "VALOR";
	
	private static final String INSTRUCAO1 = "INSTRUCAO1";
	
	private static final String INSTRUCAO2 = "INSTRUCAO2";
	
	private static final String INSTRUCAO3 = "INSTRUCAO3";
	
	private static final String ID_CONDOMINIO = "ID_CONDOMINIO";
	
	private static final String ID_PAGADOR = "ID_PAGADOR";
	
	private static final String ID_BENEFICIARIO = "ID_BENEFICIARIO";
	
	private static final String ID_CONTA_BANCARIA = "ID_CONTA_BANCARIA";

	@Override
	public void salvar(Boleto boleto) throws SQLException, Exception {
		PreparedStatement statement = null;
		Connection con = null;
		con = Conexao.getConexao();
		StringBuffer query = new StringBuffer();
		query.append("INSERT INTO "); 
		query.append(BOLETO); 
		query.append("(");
		query.append(ID); 
		query.append(",");
		query.append(EMISSAO);
		query.append(",");
		query.append(VENCIMENTO);
		query.append(",");
		query.append(DOCUMENTO); 
		query.append(",");
		query.append(NUMERO);
		query.append(",");
		query.append(TITULO);
		query.append(",");
		query.append(VALOR);
		query.append(",");
		query.append(INSTRUCAO1);
		query.append(",");
		query.append(INSTRUCAO2);
		query.append(",");
		query.append(INSTRUCAO3);
		query.append(",");
		query.append(ID_CONDOMINIO);
		query.append(",");
		query.append(ID_PAGADOR);
		query.append(",");
		query.append(ID_BENEFICIARIO);
		query.append(",");
		query.append(ID_CONTA_BANCARIA);
		query.append(") ");
		query.append("VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
		try {
			statement = con.prepareStatement(query.toString());
			SQLUtil.setValorPpreparedStatement(statement,1, boleto.getId(), java.sql.Types.INTEGER);
			SQLUtil.setValorPpreparedStatement(statement,2, boleto.getEmissao(), java.sql.Types.DATE);
			SQLUtil.setValorPpreparedStatement(statement,3, boleto.getVencimento(), java.sql.Types.DATE);
			SQLUtil.setValorPpreparedStatement(statement,4, boleto.getDocumento(), java.sql.Types.VARCHAR);
			SQLUtil.setValorPpreparedStatement(statement,5, boleto.getNumero(), java.sql.Types.VARCHAR);
			SQLUtil.setValorPpreparedStatement(statement,6, boleto.getTitulo(), java.sql.Types.VARCHAR);			
			SQLUtil.setValorPpreparedStatement(statement,7, boleto.getValor(), java.sql.Types.VARCHAR);			
			SQLUtil.setValorPpreparedStatement(statement,8, boleto.getInstrucao1(), java.sql.Types.VARCHAR);			
			SQLUtil.setValorPpreparedStatement(statement,9, boleto.getInstrucao2(), java.sql.Types.VARCHAR);			
			SQLUtil.setValorPpreparedStatement(statement,10, boleto.getInstrucao3(), java.sql.Types.VARCHAR);			
			SQLUtil.setValorPpreparedStatement(statement,11, boleto.getIdCondominio(), java.sql.Types.INTEGER);			
			SQLUtil.setValorPpreparedStatement(statement,12, boleto.getPagador().getId(), java.sql.Types.INTEGER);			
			SQLUtil.setValorPpreparedStatement(statement,13, boleto.getBeneficiario().getId(), java.sql.Types.INTEGER);			
			SQLUtil.setValorPpreparedStatement(statement,14, boleto.getContaBancaria().getId(), java.sql.Types.INTEGER);			
			statement.execute();				
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

	@Override
	public void excluirPorId(Integer idBoleto) throws SQLException, Exception {
		StringBuffer query = new StringBuffer();
		Connection con = Conexao.getConexao();
		PreparedStatement statement = null;
		try {			
			query.append("DELETE FROM ");
			query.append(BOLETO);
			query.append(" WHERE ");		
			query.append(ID);
			query.append(" = ?");
			statement = con.prepareStatement(query.toString());			
			SQLUtil.setValorPpreparedStatement(statement, 1, idBoleto, java.sql.Types.INTEGER);			
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
	public List<Boleto> buscarPorIdCondominioEDataVencimento(Integer idCondominio, Date dataVencimentoDe, Date dataVencimentoAte) throws SQLException, Exception {
		StringBuffer query = new StringBuffer();
		query.append("SELECT * FROM ");
		query.append(BOLETO);
		query.append(" WHERE ");
		query.append(ID_CONDOMINIO);
		query.append(" = ?");
		query.append(" AND ");
		query.append(VENCIMENTO);
		query.append(" BETWEEN ");
		query.append("? AND ?");		
		query.append(" ORDER BY ");
		query.append(VENCIMENTO);
		query.append(";");		
		Connection con = Conexao.getConexao();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		List<Boleto> listaBoleto = new ArrayList<Boleto>();
		Boleto boleto = null;
		try {
			preparedStatement = con.prepareStatement(query.toString());
			SQLUtil.setValorPpreparedStatement(preparedStatement, 1, idCondominio, java.sql.Types.INTEGER);
			SQLUtil.setValorPpreparedStatement(preparedStatement, 2, dataVencimentoDe, java.sql.Types.DATE);
			SQLUtil.setValorPpreparedStatement(preparedStatement, 3, dataVencimentoAte, java.sql.Types.DATE);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()){
				boleto = new Boleto();
				boleto.setId((Integer) SQLUtil.getValorResultSet(resultSet, ID, java.sql.Types.INTEGER));
				boleto.setEmissao((Date) SQLUtil.getValorResultSet(resultSet, EMISSAO, java.sql.Types.DATE));
				boleto.setVencimento((Date) SQLUtil.getValorResultSet(resultSet, VENCIMENTO, java.sql.Types.DATE));
				boleto.setDocumento(String.valueOf(SQLUtil.getValorResultSet(resultSet, DOCUMENTO, java.sql.Types.VARCHAR)));
				boleto.setNumero(String.valueOf(SQLUtil.getValorResultSet(resultSet, NUMERO, java.sql.Types.VARCHAR)));
				boleto.setTitulo(String.valueOf(SQLUtil.getValorResultSet(resultSet, TITULO, java.sql.Types.VARCHAR)));
				boleto.setValor((Long) SQLUtil.getValorResultSet(resultSet, VALOR, java.sql.Types.BIGINT));
				boleto.setInstrucao1(String.valueOf(SQLUtil.getValorResultSet(resultSet, INSTRUCAO1, java.sql.Types.VARCHAR)));
				boleto.setInstrucao2(String.valueOf(SQLUtil.getValorResultSet(resultSet, INSTRUCAO2, java.sql.Types.VARCHAR)));
				boleto.setInstrucao3(String.valueOf(SQLUtil.getValorResultSet(resultSet, INSTRUCAO3, java.sql.Types.VARCHAR)));
				boleto.setIdCondominio((Integer) SQLUtil.getValorResultSet(resultSet, ID_CONDOMINIO, java.sql.Types.INTEGER));
				boleto.setBeneficiario(this.beneficiarioDAO.buscarPorId((Integer) SQLUtil.getValorResultSet(resultSet, ID_BENEFICIARIO, java.sql.Types.INTEGER), con));
				boleto.setContaBancaria(this.contaBancariaDAO.buscarPorId((Integer) SQLUtil.getValorResultSet(resultSet, ID_CONTA_BANCARIA, java.sql.Types.INTEGER), con));
				
				
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
		return listaBoleto;
	}
	
	


	
	
	


}
