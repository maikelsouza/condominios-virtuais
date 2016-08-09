package br.com.condominiosvirtuais.persistence.impl;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import br.com.condominiosvirtuais.persistence.ConfiguracaoAplicacaoDAO;
import br.com.condominiosvirtuais.util.SQLUtil;

public class ConfiguracaoAplicacaoDAOImpl implements ConfiguracaoAplicacaoDAO, Serializable {
	
	private static final long serialVersionUID = 1L;

	private static Logger logger = Logger.getLogger(ConfiguracaoAplicacaoDAOImpl.class);
	
	private static final String CONFIGURACAO_APLICACAO = "CONFIGURACAO_APLICACAO";

	private static final String CHAVE = "CHAVE";
	
	private static final String  VALOR = "VALOR";


	@Override
	public Map<String, String> buscarTodas() throws SQLException, Exception {
		StringBuffer query = new StringBuffer();
		query.append("SELECT * FROM ");
		query.append(CONFIGURACAO_APLICACAO);		
		query.append(";");
		PreparedStatement preparedStatement = null;
		Connection con = Conexao.getConexao();
		ResultSet resultSet = null;
		Map<String, String> mapConfiguraçaoAplicacao = new HashMap<String, String>();
		try {
			preparedStatement = con.prepareStatement(query.toString());			
			resultSet = preparedStatement.executeQuery();			
			while(resultSet.next()){
				mapConfiguraçaoAplicacao.put(String.valueOf(SQLUtil.getValorResultSet(resultSet, CHAVE, java.sql.Types.VARCHAR))
				, String.valueOf(SQLUtil.getValorResultSet(resultSet, VALOR, java.sql.Types.VARCHAR)));
			}
		}catch (SQLException e) {
			throw e;
		}catch (Exception e) {		
			throw e;
		}finally{
			try {
				preparedStatement.close();
				con.close();				
			} catch (SQLException e) {
				logger.error("erro sqlstate "+e.getSQLState(), e);
			}
		}	
		return mapConfiguraçaoAplicacao;
	}

}
