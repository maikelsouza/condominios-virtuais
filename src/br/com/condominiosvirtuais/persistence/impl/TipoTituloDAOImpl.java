package br.com.condominiosvirtuais.persistence.impl;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import br.com.condominiosvirtuais.entity.TipoTitulo;
import br.com.condominiosvirtuais.persistence.TipoTituloDAO;
import br.com.condominiosvirtuais.util.SQLUtil;

public class TipoTituloDAOImpl implements TipoTituloDAO, Serializable {

	private static final long serialVersionUID = 1L;
	
	private static Logger logger = Logger.getLogger(TipoTituloDAOImpl.class);
	
	private static final String TIPO_TITULO = "TIPO_TITULO";
	
    private static final String ID = "ID";
	
	private static final String SIGLA = "SIGLA";
	
	private static final String NOME = "NOME";
	
	private static final String SITUACAO = "SITUACAO";

	@Override
	public List<TipoTitulo> buscarPorSituacao(Boolean situacao) throws SQLException, Exception {
		StringBuffer query = new StringBuffer();
		query.append("SELECT * FROM ");
		query.append(TIPO_TITULO);
		query.append(" WHERE ");
		query.append(SITUACAO);		
		query.append(" = ?");
		query.append(";");
		Connection con = C3P0DataSource.getInstance().getConnection();
		PreparedStatement preparedStatement = null;
		TipoTitulo tipoTitulo = null;		
		List<TipoTitulo> listaTipoTitulo = new ArrayList<TipoTitulo>();
		try {
			preparedStatement = con.prepareStatement(query.toString());
			SQLUtil.setValorPpreparedStatement(preparedStatement, 1, situacao, java.sql.Types.BOOLEAN);			
			ResultSet resultSet = preparedStatement.executeQuery();
			while(resultSet.next()){				
				tipoTitulo = new TipoTitulo();				
				tipoTitulo.setId((Integer) SQLUtil.getValorResultSet(resultSet, ID, java.sql.Types.INTEGER));
				tipoTitulo.setNome(String.valueOf(SQLUtil.getValorResultSet(resultSet, NOME, java.sql.Types.VARCHAR)));
				tipoTitulo.setSigla(String.valueOf(SQLUtil.getValorResultSet(resultSet, SIGLA, java.sql.Types.VARCHAR)));				
				tipoTitulo.setSituacao((Boolean) SQLUtil.getValorResultSet(resultSet, SITUACAO, java.sql.Types.BOOLEAN));
				listaTipoTitulo.add(tipoTitulo);
			}		
		} catch (SQLException e) {			
			throw e;
		} catch (Exception e) {					
			throw e;	
		}finally{
			try{				
				SQLUtil.closeStatement(preparedStatement);
				SQLUtil.closeConnection(con);					
			}catch (SQLException e) {
				logger.error("erro sqlstate "+e.getSQLState(), e);
		    }
		}
		return listaTipoTitulo;		
	}

}
