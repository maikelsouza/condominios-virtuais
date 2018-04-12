package br.com.condominiosvirtuais.persistence.impl;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.inject.Inject;

import org.apache.log4j.Logger;

import br.com.condominiosvirtuais.entity.ConfiguracaoPadraoContaBancaria;
import br.com.condominiosvirtuais.exception.BusinessException;
import br.com.condominiosvirtuais.persistence.ConfiguracaoPadraoContaBancariaDAO;
import br.com.condominiosvirtuais.persistence.TipoTituloDAO;
import br.com.condominiosvirtuais.util.SQLUtil;

public class ConfiguracaoPadraoContaBancariaDAOImpl implements ConfiguracaoPadraoContaBancariaDAO, Serializable {

	private static final long serialVersionUID = 1L;
	
	private static Logger logger = Logger.getLogger(ConfiguracaoPadraoContaBancariaDAOImpl.class);
	
	private static final String CONFIGURACAO_PADRAO_CONTA_BANCARIA = "CONFIGURACAO_PADRAO_CONTA_BANCARIA";
	
	private static final String ID = "ID";
	
	private static final String MULTA = "MULTA";
	
	private static final String JUROS_AO_DIA = "JUROS_AO_DIA";
	
	private static final String DIAS_SEM_COBRAR_JUROS_APOS_VENCIMENTO = "DIAS_SEM_COBRAR_JUROS_APOS_VENCIMENTO";
	
	private static final String PERMITIR_EMITIR_BOLETO_SEM_VALOR = "PERMITIR_EMITIR_BOLETO_SEM_VALOR";
	
	private static final String DESCONTO = "DESCONTO";
	
	private static final String DIAS_CONCEDER_DESCONTO_ATE_VENCIMENTO = "DIAS_CONCEDER_DESCONTO_ATE_VENCIMENTO";
	
	private static final String INSTRUCAO_PADRAO = "INSTRUCAO_PADRAO";
	
	private static final String ACEITE = "ACEITE";
	
	private static final String ID_CONTA_BANCARIA = "ID_CONTA_BANCARIA";
	
	private static final String ID_TIPO_TITULO = "ID_TIPO_TITULO";
	
	private static final String  FK_CONFIG_PADRAO_CONTA_BANC_ID_CONTA_BANC_CONTA_BANC_ID = "FK_CONFIG_PADRAO_CONTA_BANC_ID_CONTA_BANC_CONTA_BANC_ID";
	
	private static final String  FK_CONFIG_PADRAO_CONTA_BANC_ID_TIPO_TITULO_TIPO_TITULO_ID = "FK_CONFIG_PADRAO_CONTA_BANC_ID_TIPO_TITULO_TIPO_TITULO_ID";

	@Inject
	private TipoTituloDAO tipoTituloDAO; 
	
	@Override
	public void salvar(ConfiguracaoPadraoContaBancaria configuracaoPadraoContaBancaria, Connection con) throws SQLException, BusinessException, Exception {		
		StringBuffer query = new StringBuffer();
		query.append("INSERT INTO "); 
		query.append(CONFIGURACAO_PADRAO_CONTA_BANCARIA);
		query.append("("); 
		query.append(MULTA);	
		query.append(",");
		query.append(JUROS_AO_DIA);
		query.append(",");
		query.append(DIAS_SEM_COBRAR_JUROS_APOS_VENCIMENTO);		
		query.append(",");
		query.append(PERMITIR_EMITIR_BOLETO_SEM_VALOR);		
		query.append(",");
		query.append(DESCONTO);		
		query.append(",");
		query.append(DIAS_CONCEDER_DESCONTO_ATE_VENCIMENTO);		
		query.append(",");
		query.append(INSTRUCAO_PADRAO);		
		query.append(",");
		query.append(ACEITE);		
		query.append(",");
		query.append(ID_CONTA_BANCARIA);		
		query.append(",");
		query.append(ID_TIPO_TITULO);		
		query.append(") ");
		query.append("VALUES(?,?,?,?,?,?,?,?,?,?)");
		PreparedStatement statement = null;		
		try {			
			statement = con.prepareStatement(query.toString());
			SQLUtil.setValorPpreparedStatement(statement, 1, configuracaoPadraoContaBancaria.getMulta(), java.sql.Types.DOUBLE);
			SQLUtil.setValorPpreparedStatement(statement, 2, configuracaoPadraoContaBancaria.getJurosAoDia(), java.sql.Types.DOUBLE);
			SQLUtil.setValorPpreparedStatement(statement, 3, configuracaoPadraoContaBancaria.getDiasSemCobrarJurosAposVencimento(), java.sql.Types.INTEGER);
			SQLUtil.setValorPpreparedStatement(statement, 4, configuracaoPadraoContaBancaria.getPermitirEmitirBoletoSemValor(), java.sql.Types.BOOLEAN);
			SQLUtil.setValorPpreparedStatement(statement, 5, configuracaoPadraoContaBancaria.getDesconto(), java.sql.Types.DOUBLE);
			SQLUtil.setValorPpreparedStatement(statement, 6, configuracaoPadraoContaBancaria.getDiasConcederDescontoAteVencimento(), java.sql.Types.INTEGER);
			SQLUtil.setValorPpreparedStatement(statement, 7, configuracaoPadraoContaBancaria.getInstrucaoPadrao(), java.sql.Types.VARCHAR);
			SQLUtil.setValorPpreparedStatement(statement, 8, configuracaoPadraoContaBancaria.getAceite(), java.sql.Types.BOOLEAN);
			SQLUtil.setValorPpreparedStatement(statement, 9, configuracaoPadraoContaBancaria.getIdContaBancaria(), java.sql.Types.INTEGER);
			SQLUtil.setValorPpreparedStatement(statement, 10, configuracaoPadraoContaBancaria.getTipoTitulo().getId(), java.sql.Types.INTEGER);
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
	public ConfiguracaoPadraoContaBancaria buscarPorIdContaBancaria(Integer idContaBancaria, Connection con)
			throws SQLException, BusinessException, Exception {
		StringBuffer query = new StringBuffer();
		query.append("SELECT * FROM ");
		query.append(CONFIGURACAO_PADRAO_CONTA_BANCARIA);
		query.append(" WHERE ");
		query.append(ID_CONTA_BANCARIA);
		query.append(" = ?");
		query.append(";");		
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		ConfiguracaoPadraoContaBancaria configuracaoPadraoContaBancaria = null;
		try {
			preparedStatement = con.prepareStatement(query.toString());
			SQLUtil.setValorPpreparedStatement(preparedStatement, 1, idContaBancaria, java.sql.Types.INTEGER);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()){
				configuracaoPadraoContaBancaria = new ConfiguracaoPadraoContaBancaria();
				configuracaoPadraoContaBancaria.setId((Integer) SQLUtil.getValorResultSet(resultSet, ID, java.sql.Types.INTEGER));
				configuracaoPadraoContaBancaria.setMulta((Double) SQLUtil.getValorResultSet(resultSet, MULTA, java.sql.Types.DOUBLE));
				configuracaoPadraoContaBancaria.setJurosAoDia((Double) SQLUtil.getValorResultSet(resultSet, JUROS_AO_DIA, java.sql.Types.DOUBLE));
				configuracaoPadraoContaBancaria.setDesconto((Double) SQLUtil.getValorResultSet(resultSet, DESCONTO, java.sql.Types.DOUBLE));
				configuracaoPadraoContaBancaria.setDiasSemCobrarJurosAposVencimento((Integer) SQLUtil.getValorResultSet(resultSet, DIAS_SEM_COBRAR_JUROS_APOS_VENCIMENTO,
						java.sql.Types.INTEGER));
				configuracaoPadraoContaBancaria.setDiasConcederDescontoAteVencimento((Integer) SQLUtil.getValorResultSet(resultSet, DIAS_CONCEDER_DESCONTO_ATE_VENCIMENTO,
						java.sql.Types.INTEGER));
				configuracaoPadraoContaBancaria.setInstrucaoPadrao(String.valueOf(SQLUtil.getValorResultSet(resultSet, INSTRUCAO_PADRAO,java.sql.Types.VARCHAR)));
				configuracaoPadraoContaBancaria.setAceite((Boolean) SQLUtil.getValorResultSet(resultSet, ACEITE, java.sql.Types.BOOLEAN));
				configuracaoPadraoContaBancaria.setPermitirEmitirBoletoSemValor((Boolean) SQLUtil.getValorResultSet(resultSet, PERMITIR_EMITIR_BOLETO_SEM_VALOR, java.sql.Types.BOOLEAN));
				configuracaoPadraoContaBancaria.setIdContaBancaria((Integer) SQLUtil.getValorResultSet(resultSet, ID_CONTA_BANCARIA, java.sql.Types.INTEGER));
				configuracaoPadraoContaBancaria.setTipoTitulo(this.tipoTituloDAO.buscarPorId((Integer) SQLUtil.getValorResultSet(resultSet, ID_TIPO_TITULO, java.sql.Types.INTEGER), con));
			}
		} catch (SQLException e) {
			throw e;
		} catch (Exception e) {
			throw e;
		}
		return configuracaoPadraoContaBancaria;
	}

	@Override
	public void atualizar(ConfiguracaoPadraoContaBancaria configuracaoPadraoContaBancaria, Connection con)
			throws SQLException, BusinessException, Exception {	
		
		StringBuffer query = new StringBuffer();
		query.append("UPDATE ");
		query.append(CONFIGURACAO_PADRAO_CONTA_BANCARIA);
		query.append(" SET ");
		query.append(MULTA);
		query.append("= ?, ");
		query.append(JUROS_AO_DIA); 
		query.append("= ?, ");
		query.append(DIAS_SEM_COBRAR_JUROS_APOS_VENCIMENTO); 
		query.append("= ?, ");
		query.append(PERMITIR_EMITIR_BOLETO_SEM_VALOR);
		query.append("= ?, ");
		query.append(DESCONTO);
		query.append("= ?, ");
		query.append(DIAS_CONCEDER_DESCONTO_ATE_VENCIMENTO);
		query.append("= ?, ");
		query.append(INSTRUCAO_PADRAO);
		query.append("= ?, ");
		query.append(ACEITE);	
		query.append("= ?, ");		
		query.append(ID_CONTA_BANCARIA);	
		query.append("= ?, ");		
		query.append(ID_TIPO_TITULO);	
		query.append("= ? ");		
		query.append("WHERE ");
		query.append(ID);
		query.append("= ?");

		PreparedStatement statement = null;
		try {			
			statement = con.prepareStatement(query.toString());			
			SQLUtil.setValorPpreparedStatement(statement,1, configuracaoPadraoContaBancaria.getMulta(), java.sql.Types.DOUBLE);
			SQLUtil.setValorPpreparedStatement(statement,2, configuracaoPadraoContaBancaria.getJurosAoDia(), java.sql.Types.DOUBLE);
			SQLUtil.setValorPpreparedStatement(statement,3, configuracaoPadraoContaBancaria.getDiasSemCobrarJurosAposVencimento(), java.sql.Types.INTEGER);
			SQLUtil.setValorPpreparedStatement(statement,4, configuracaoPadraoContaBancaria.getPermitirEmitirBoletoSemValor(), java.sql.Types.BOOLEAN);
			SQLUtil.setValorPpreparedStatement(statement,5, configuracaoPadraoContaBancaria.getDesconto(), java.sql.Types.DOUBLE);
			SQLUtil.setValorPpreparedStatement(statement,6, configuracaoPadraoContaBancaria.getDiasConcederDescontoAteVencimento(), java.sql.Types.INTEGER);
			SQLUtil.setValorPpreparedStatement(statement,7, configuracaoPadraoContaBancaria.getInstrucaoPadrao(), java.sql.Types.VARCHAR);
			SQLUtil.setValorPpreparedStatement(statement,8, configuracaoPadraoContaBancaria.getAceite(), java.sql.Types.BOOLEAN);
			SQLUtil.setValorPpreparedStatement(statement,9, configuracaoPadraoContaBancaria.getIdContaBancaria(), java.sql.Types.INTEGER);			
			SQLUtil.setValorPpreparedStatement(statement,10, configuracaoPadraoContaBancaria.getTipoTitulo().getId(), java.sql.Types.INTEGER);			
			SQLUtil.setValorPpreparedStatement(statement,11, configuracaoPadraoContaBancaria.getId(), java.sql.Types.INTEGER);			
			statement.executeUpdate();
			con.commit();
		} catch (SQLException e) {
			throw e;
		}catch (Exception e) {		
			throw e;
		}
		
	}

	@Override
	public void excluir(Integer id, Connection con) throws SQLException, BusinessException, Exception {
		StringBuffer query = new StringBuffer();			
		PreparedStatement statement = null;
		try {			
			query.append("DELETE FROM ");
			query.append(CONFIGURACAO_PADRAO_CONTA_BANCARIA);
			query.append(" WHERE ");		
			query.append(ID);
			query.append(" = ?");
			statement = con.prepareStatement(query.toString());
			SQLUtil.setValorPpreparedStatement(statement, 1, id, java.sql.Types.INTEGER);			
			statement.executeUpdate();
		} catch (SQLException e) {
			con.rollback();
			throw e;
		} catch (Exception e) {
			con.rollback();
			throw e;		
		}
		
	}	
	
}
