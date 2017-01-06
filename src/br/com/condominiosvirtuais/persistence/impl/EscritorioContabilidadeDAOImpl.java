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

import br.com.condominiosvirtuais.entity.EscritorioContabilidade;
import br.com.condominiosvirtuais.persistence.CondominioDAO;
import br.com.condominiosvirtuais.persistence.ContadorDAO;
import br.com.condominiosvirtuais.persistence.EscritorioContabilidadeDAO;
import br.com.condominiosvirtuais.util.SQLUtil;

public class EscritorioContabilidadeDAOImpl implements EscritorioContabilidadeDAO, Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private static Logger logger = Logger.getLogger(EscritorioContabilidadeDAOImpl.class);	
	
	private static final String ESCRITORIO_CONTABILIDADE = "ESCRITORIO_CONTABILIDADE";
	
	private static final String ID = "ID";
	
	private static final String NOME = "NOME";
	
	private static final String SITE = "SITE";
	
	private static final String TELEFONE_COMERCIAL = "TELEFONE_COMERCIAL";
	
	private static final String TELEFONE_CELULAR = "TELEFONE_CELULAR";
	
	private static final String CNPJ = "CNPJ";
	
	private static final String SITUACAO = "SITUACAO";
	
	@Inject
	private ContadorDAO contadorDAO = null;
	
	@Inject
	private CondominioDAO condominioDAO = null; 

	@Override
	public void salvar(EscritorioContabilidade escritorioContabilidade) throws SQLException, Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<EscritorioContabilidade> buscarPorSituacao(Integer situacao) throws SQLException, Exception {
		StringBuffer query = new StringBuffer();
		query.append("SELECT * FROM ");
		query.append(ESCRITORIO_CONTABILIDADE);
		query.append(" WHERE ");
		query.append(SITUACAO);
		query.append(" = ?");
		query.append(" ORDER BY ");
		query.append(NOME);
		query.append(";");
		Connection con = Conexao.getConexao();
		PreparedStatement preparedStatement = null;		
		List<EscritorioContabilidade> listaEscritorioContabilidade = new ArrayList<EscritorioContabilidade>();
		try {
			preparedStatement = con.prepareStatement(query.toString());
			SQLUtil.setValorPpreparedStatement(preparedStatement, 1, situacao, java.sql.Types.INTEGER);
			ResultSet resultSet = preparedStatement.executeQuery();
			EscritorioContabilidade escritorioContabilidade = null;
			while(resultSet.next()){
				escritorioContabilidade = new EscritorioContabilidade();								
				escritorioContabilidade.setId((Integer) SQLUtil.getValorResultSet(resultSet, ID, java.sql.Types.INTEGER));				
				escritorioContabilidade.setNome(String.valueOf(SQLUtil.getValorResultSet(resultSet, NOME, java.sql.Types.VARCHAR)));
				escritorioContabilidade.setSite(String.valueOf(SQLUtil.getValorResultSet(resultSet, SITE, java.sql.Types.VARCHAR)));
				escritorioContabilidade.setCnpj((Long) SQLUtil.getValorResultSet(resultSet, CNPJ, java.sql.Types.BIGINT));
				escritorioContabilidade.setTelefoneCelular((Long) SQLUtil.getValorResultSet(resultSet, TELEFONE_CELULAR, java.sql.Types.BIGINT));
				escritorioContabilidade.setTelefoneComercial((Long) SQLUtil.getValorResultSet(resultSet, TELEFONE_COMERCIAL, java.sql.Types.BIGINT));
				escritorioContabilidade.setSituacao((Integer) SQLUtil.getValorResultSet(resultSet, SITUACAO, java.sql.Types.INTEGER));
				escritorioContabilidade.setListaContador(this.contadorDAO.buscarPorIdEscritorioContabilidade(escritorioContabilidade.getId(),con));
				escritorioContabilidade.setListaCondominio(this.condominioDAO.buscarPorIdEscritorioContabilidade(escritorioContabilidade.getId(), con));
				listaEscritorioContabilidade.add(escritorioContabilidade);
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
		return listaEscritorioContabilidade;
	}
	
}
