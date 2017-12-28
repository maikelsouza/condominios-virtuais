package br.com.condominiosvirtuais.persistence.impl;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import br.com.condominiosvirtuais.entity.ConfiguracaoPadraoContaBancaria;
import br.com.condominiosvirtuais.exception.BusinessException;
import br.com.condominiosvirtuais.persistence.ConfiguracaoPadraoContaBancariaDAO;
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
			SQLUtil.setValorPpreparedStatement(statement, 10, configuracaoPadraoContaBancaria.getIdTipoTitulo(), java.sql.Types.INTEGER);
			statement.executeUpdate();
		}catch (SQLException e) {
			con.rollback();
			throw e;
		} catch (Exception e) {
			con.rollback();
			throw e;	
		}
	}	
	
}
