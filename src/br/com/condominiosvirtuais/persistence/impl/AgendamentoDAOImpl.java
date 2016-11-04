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

import br.com.condominiosvirtuais.entity.Agendamento;
import br.com.condominiosvirtuais.entity.Bloco;
import br.com.condominiosvirtuais.entity.Condominio;
import br.com.condominiosvirtuais.entity.Condomino;
import br.com.condominiosvirtuais.entity.Unidade;
import br.com.condominiosvirtuais.persistence.AgendamentoDAO;
import br.com.condominiosvirtuais.persistence.BlocoDAO;
import br.com.condominiosvirtuais.persistence.UnidadeDAO;
import br.com.condominiosvirtuais.util.SQLUtil;

public class AgendamentoDAOImpl implements AgendamentoDAO, Serializable {
	
	
	private static final long serialVersionUID = 1L;
	
	@Inject
	private UnidadeDAO unidadeDAO;
	
	@Inject
	private BlocoDAO blocoDAO;
	
	@Inject
	private CondominoDAOImpl condominoDAO;

	private static Logger logger = Logger.getLogger(AgendamentoDAOImpl.class); 
	
	private static final String AGENDAMENTO = "AGENDAMENTO";

	private static final String ID = "ID";
	
	private static final String DATA = "DATA";
	
	private static final String HORA_INICIAL = "HORA_INICIAL";
	
	private static final String HORA_FINAL = "HORA_FINAL";
	
	private static final String OBSERVACAO = "OBSERVACAO";
	
	private static final String MOTIVO_REPROVACAO = "MOTIVO_REPROVACAO";
	
	private static final String SITUACAO = "SITUACAO";
	
	private static final String TIPO = "TIPO";
	
	private static final String ID_CONDOMINO = "ID_CONDOMINO";
	
	
	@Override
	public void salvar(Agendamento agendamento) throws SQLException, Exception {
		StringBuffer query = new StringBuffer();
		query.append("INSERT INTO "); 
		query.append(AGENDAMENTO);
		query.append(" ("); 
		query.append(DATA);	
		query.append(",");
		query.append(HORA_INICIAL);
		query.append(",");
		query.append(HORA_FINAL);
		query.append(",");		
		query.append(OBSERVACAO);
		query.append(",");		
		query.append(MOTIVO_REPROVACAO);
		query.append(", ");
		query.append(TIPO);
		query.append(", ");
		query.append(SITUACAO);
		query.append(",");		
		query.append(ID_CONDOMINO);
		query.append(")");		
		query.append("VALUES(?,?,?,?,?,?,?,?)");
		PreparedStatement statement = null;		
		Connection con = Conexao.getConexao();
		try {
			statement = con.prepareStatement(query.toString());			
			SQLUtil.setValorPpreparedStatement(statement, 1, agendamento.getData(), java.sql.Types.DATE);			
			SQLUtil.setValorPpreparedStatement(statement, 2, agendamento.getHoraInicial(), java.sql.Types.VARCHAR);			
			SQLUtil.setValorPpreparedStatement(statement, 3, agendamento.getHoraFinal(), java.sql.Types.VARCHAR);
			SQLUtil.setValorPpreparedStatement(statement, 4, agendamento.getObservacao(), java.sql.Types.VARCHAR);
			SQLUtil.setValorPpreparedStatement(statement, 5, agendamento.getMotivoReprovacao(), java.sql.Types.VARCHAR);
			SQLUtil.setValorPpreparedStatement(statement, 6, agendamento.getTipo(), java.sql.Types.VARCHAR);
			SQLUtil.setValorPpreparedStatement(statement, 7, agendamento.getSituacao(), java.sql.Types.VARCHAR);
			SQLUtil.setValorPpreparedStatement(statement, 8, agendamento.getCondomino().getId(), java.sql.Types.INTEGER);
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
	public List<Agendamento> buscarPorCondominioESituacao(Condominio condominio, String situacao) throws SQLException, Exception {
		List<Agendamento> listaAgendamento = new ArrayList<Agendamento>();
		String pontoInterrogacao = "";
		List<Integer> listIdsBlocos = null;
		List<Integer> listIdsUnidades = null;
		List<Integer> listIdsCondomino = new ArrayList<Integer>();
		listIdsBlocos = this.blocoDAO.buscarListaIdsBlocosPorIdCondominio(condominio.getId());
		for (Integer idBloco : listIdsBlocos) {
			listIdsUnidades  = this.unidadeDAO.buscarListaIdsUnidadesPorIdBloco(idBloco);
			for (Integer idUnidade : listIdsUnidades) {
				listIdsCondomino.addAll(this.condominoDAO.buscarListaIdsCondominosPorIdUnidade(idUnidade));
			}
		}
	
// TODO: Código comentado em 30/08/2016. Apagar em 180 dias		
//		List<Condomino> listaCondomino = new ArrayList<Condomino>();
//		this.condominioDAO.get().popularCondominio(condominio);
//		for (Bloco bloco : condominio.getListaBlocos()) {
//			for (Unidade unidade : bloco.getListaUnidade()) {
//				for (Condomino condomino : unidade.getListaCondominos()) {
//					listaCondomino.add(condomino);
//				}
//			}					
//		}
		pontoInterrogacao = SQLUtil.popularInterrocacoes(listIdsCondomino.size());
		StringBuffer query = new StringBuffer();
		query.append("SELECT * FROM ");
		query.append(AGENDAMENTO);
		query.append(" WHERE ");
		query.append(ID_CONDOMINO);
		query.append(" in (");
		query.append(pontoInterrogacao);
		query.append(") ");
		query.append("AND ");
		query.append(SITUACAO);		
		query.append("=  ? ");		
		query.append("ORDER BY ");
		query.append(DATA);
		query.append(" DESC, ");
		query.append(HORA_INICIAL);
		query.append(";");		
		Bloco bloco;
		Unidade unidade;		
		Condomino condomino;
		Integer contador = 1;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Agendamento agendamento = null;
		Connection con = Conexao.getConexao();
	    preparedStatement = con.prepareStatement(query.toString());
	    try {
// TODO: Código comentado em 30/08/2016. Apagar em 180 dias	    	
//	    	for (Condomino condominoBase : listaCondomino) {
//				SQLUtil.setValorPpreparedStatement(preparedStatement, contador++, condominoBase.getId(), java.sql.Types.INTEGER);				
//			}	
	    	for (Integer idCondomino : listIdsCondomino) {
	    		SQLUtil.setValorPpreparedStatement(preparedStatement, contador++, idCondomino, java.sql.Types.INTEGER);				
	    	}	
	    	SQLUtil.setValorPpreparedStatement(preparedStatement, contador++, situacao, java.sql.Types.VARCHAR);
			resultSet = preparedStatement.executeQuery();				
			while(resultSet.next()){				
				agendamento = new Agendamento();				
				agendamento.setId((Integer) SQLUtil.getValorResultSet(resultSet, ID, java.sql.Types.INTEGER));
				agendamento.setData((Date)(SQLUtil.getValorResultSet(resultSet, DATA, java.sql.Types.DATE)));
				agendamento.setHoraInicial(String.valueOf(SQLUtil.getValorResultSet(resultSet, HORA_INICIAL, java.sql.Types.VARCHAR)));
				agendamento.setHoraFinal(String.valueOf(SQLUtil.getValorResultSet(resultSet, HORA_FINAL, java.sql.Types.VARCHAR)));			
				agendamento.setTipo(String.valueOf(SQLUtil.getValorResultSet(resultSet, TIPO, java.sql.Types.VARCHAR)));
				agendamento.setSituacao(String.valueOf(SQLUtil.getValorResultSet(resultSet, SITUACAO, java.sql.Types.VARCHAR)));
				agendamento.setObservacao(String.valueOf(SQLUtil.getValorResultSet(resultSet, OBSERVACAO, java.sql.Types.VARCHAR)));			
				agendamento.setMotivoReprovacao(String.valueOf(SQLUtil.getValorResultSet(resultSet, MOTIVO_REPROVACAO, java.sql.Types.VARCHAR)));
				condomino = this.condominoDAO.buscarCondominoPorId((Integer) SQLUtil.getValorResultSet(resultSet, ID_CONDOMINO, java.sql.Types.INTEGER), con);
				unidade = this.unidadeDAO.buscarPorId (condomino.getIdUnidade(), con);
				bloco = this.blocoDAO.buscarPorId(unidade.getIdBloco(), con);
				agendamento.setBloco(bloco);
				agendamento.setUnidade(unidade);
				agendamento.setCondomino(condomino);
				listaAgendamento.add(agendamento);
			}	
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
		return listaAgendamento;
	}


	@Override
	public void reprovar(Agendamento agendamento) throws SQLException, Exception {
		StringBuffer query = new StringBuffer();
		query.append("UPDATE ");
		query.append(AGENDAMENTO);
		query.append(" SET ");		
		query.append(SITUACAO);
		query.append("= ?, ");
		query.append(MOTIVO_REPROVACAO);
		query.append("= ? ");
		query.append(" WHERE ");
		query.append(ID);
		query.append("= ?");
		Connection con = Conexao.getConexao();
		PreparedStatement statement = null;
		try {
			statement = con.prepareStatement(query.toString());
			SQLUtil.setValorPpreparedStatement(statement, 1, agendamento.getSituacao(), java.sql.Types.VARCHAR);
			SQLUtil.setValorPpreparedStatement(statement, 2, agendamento.getMotivoReprovacao(), java.sql.Types.VARCHAR);
			SQLUtil.setValorPpreparedStatement(statement, 3, agendamento.getId(), java.sql.Types.INTEGER);
			statement.executeUpdate();			
		}catch (SQLException e) {					
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
	public void aprovar(Agendamento agendamento) throws SQLException, Exception {
		StringBuffer query = new StringBuffer();
		query.append("UPDATE ");
		query.append(AGENDAMENTO);
		query.append(" SET ");		
		query.append(SITUACAO);
		query.append("= ? ");
		query.append(" WHERE ");
		query.append(ID);
		query.append("= ?");
		Connection con = Conexao.getConexao();
		PreparedStatement statement = null;
		try {
			statement = con.prepareStatement(query.toString());
			SQLUtil.setValorPpreparedStatement(statement, 1, agendamento.getSituacao(), java.sql.Types.VARCHAR);
			SQLUtil.setValorPpreparedStatement(statement, 2, agendamento.getId(), java.sql.Types.INTEGER);
			statement.executeUpdate();			
		}catch (SQLException e) {					
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
	public List<Agendamento> buscarPorCondominio(Condominio condominio) throws SQLException, Exception {
		List<Agendamento> listaAgendamento = new ArrayList<Agendamento>();
		String pontoInterrogacao = "";		
		
		List<Integer> listIdsBlocos = null;
		List<Integer> listIdsUnidades = null;
		List<Integer> listIdsCondomino = new ArrayList<Integer>();
		listIdsBlocos = this.blocoDAO.buscarListaIdsBlocosPorIdCondominio(condominio.getId());
		for (Integer idBloco : listIdsBlocos) {
			listIdsUnidades  = this.unidadeDAO.buscarListaIdsUnidadesPorIdBloco(idBloco);
			for (Integer idUnidade : listIdsUnidades) {
				listIdsCondomino.addAll(this.condominoDAO.buscarListaIdsCondominosPorIdUnidade(idUnidade));
			}
		}
		
		
// TODO: Código comentado em 30/08/2016. Apagar em 180 dias		
//		List<Condomino> listaCondomino = new ArrayList<Condomino>();
//		this.condominioDAO.get().popularCondominio(condominio);
//		for (Bloco bloco : condominio.getListaBlocos()) {
//			for (Unidade unidade : bloco.getListaUnidade()) {
//				for (Condomino condomino : unidade.getListaCondominos()) {
//					listaCondomino.add(condomino);
//				}
//			}					
//		}
		
		pontoInterrogacao = SQLUtil.popularInterrocacoes(listIdsCondomino.size());
		StringBuffer query = new StringBuffer();
		query.append("SELECT * FROM ");
		query.append(AGENDAMENTO);
		query.append(" WHERE ");
		query.append(ID_CONDOMINO);
		query.append(" in (");
		query.append(pontoInterrogacao);
		query.append(") ");
		query.append(" ORDER BY ");
		query.append(DATA);
		query.append(" DESC, ");
		query.append(HORA_INICIAL);
		query.append(";");
		Bloco bloco;
		Unidade unidade;		
		Condomino condomino;
		Integer contador = 1;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Agendamento agendamento = null;
		Connection con = Conexao.getConexao();
	    preparedStatement = con.prepareStatement(query.toString());
	    try {
// TODO: Código comentado em 30/08/2016. Apagar em 180 dias	    	
//	    	for (Condomino condominoBase : listaCondomino) {
//				SQLUtil.setValorPpreparedStatement(preparedStatement, contador++, condominoBase.getId(), java.sql.Types.INTEGER);				
//			}
	    	for (Integer idCondomino : listIdsCondomino) {
	    		SQLUtil.setValorPpreparedStatement(preparedStatement, contador++, idCondomino, java.sql.Types.INTEGER);				
	    	}
			resultSet = preparedStatement.executeQuery();				
			while(resultSet.next()){				
				agendamento = new Agendamento();				
				agendamento.setId((Integer) SQLUtil.getValorResultSet(resultSet, ID, java.sql.Types.INTEGER));
				agendamento.setData((Date)(SQLUtil.getValorResultSet(resultSet, DATA, java.sql.Types.DATE)));
				agendamento.setHoraInicial(String.valueOf(SQLUtil.getValorResultSet(resultSet, HORA_INICIAL, java.sql.Types.VARCHAR)));
				agendamento.setHoraFinal(String.valueOf(SQLUtil.getValorResultSet(resultSet, HORA_FINAL, java.sql.Types.VARCHAR)));			
				agendamento.setTipo(String.valueOf(SQLUtil.getValorResultSet(resultSet, TIPO, java.sql.Types.VARCHAR)));
				agendamento.setSituacao(String.valueOf(SQLUtil.getValorResultSet(resultSet, SITUACAO, java.sql.Types.VARCHAR)));
				agendamento.setObservacao(String.valueOf(SQLUtil.getValorResultSet(resultSet, OBSERVACAO, java.sql.Types.VARCHAR)));			
				agendamento.setMotivoReprovacao(String.valueOf(SQLUtil.getValorResultSet(resultSet, MOTIVO_REPROVACAO, java.sql.Types.VARCHAR)));
				condomino = this.condominoDAO.buscarCondominoPorId((Integer) SQLUtil.getValorResultSet(resultSet, ID_CONDOMINO, java.sql.Types.INTEGER), con);
				unidade = this.unidadeDAO.buscarPorId (condomino.getIdUnidade(), con);
				bloco = this.blocoDAO.buscarPorId(unidade.getIdBloco(), con);
				agendamento.setBloco(bloco);
				agendamento.setUnidade(unidade);
				agendamento.setCondomino(condomino);
				listaAgendamento.add(agendamento);
			}	
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
		return listaAgendamento;
	}
	
	


	@Override
	public List<Agendamento> buscarPorCondomino(Condomino condomino) throws SQLException, Exception {
		List<Agendamento> listaAgendamento = new ArrayList<Agendamento>();
		StringBuffer query = new StringBuffer();
		query.append("SELECT * FROM ");
		query.append(AGENDAMENTO);
		query.append(" WHERE ");
		query.append(ID_CONDOMINO);
		query.append(" = ? " );		
		query.append("ORDER BY ");
		query.append(DATA);
		query.append(" DESC, ");
		query.append(HORA_INICIAL);
		query.append(";");
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Agendamento agendamento = null;
		Bloco bloco;
		Unidade unidade;		
		Connection con = Conexao.getConexao();
	    preparedStatement = con.prepareStatement(query.toString());
	    SQLUtil.setValorPpreparedStatement(preparedStatement, 1, condomino.getId(), java.sql.Types.INTEGER);
	    try {
	    	resultSet = preparedStatement.executeQuery();		
			while(resultSet.next()){				
				agendamento = new Agendamento();				
				agendamento.setId((Integer) SQLUtil.getValorResultSet(resultSet, ID, java.sql.Types.INTEGER));
				agendamento.setData((Date)(SQLUtil.getValorResultSet(resultSet, DATA, java.sql.Types.DATE)));
				agendamento.setHoraInicial(String.valueOf(SQLUtil.getValorResultSet(resultSet, HORA_INICIAL, java.sql.Types.VARCHAR)));
				agendamento.setHoraFinal(String.valueOf(SQLUtil.getValorResultSet(resultSet, HORA_FINAL, java.sql.Types.VARCHAR)));			
				agendamento.setTipo(String.valueOf(SQLUtil.getValorResultSet(resultSet, TIPO, java.sql.Types.VARCHAR)));
				agendamento.setSituacao(String.valueOf(SQLUtil.getValorResultSet(resultSet, SITUACAO, java.sql.Types.VARCHAR)));
				agendamento.setObservacao(String.valueOf(SQLUtil.getValorResultSet(resultSet, OBSERVACAO, java.sql.Types.VARCHAR)));			
				agendamento.setMotivoReprovacao(String.valueOf(SQLUtil.getValorResultSet(resultSet, MOTIVO_REPROVACAO, java.sql.Types.VARCHAR)));				
				unidade = this.unidadeDAO.buscarPorId (condomino.getIdUnidade(), con);
				bloco = this.blocoDAO.buscarPorId(unidade.getIdBloco(), con);
				agendamento.setBloco(bloco);
				agendamento.setUnidade(unidade);
				agendamento.setCondomino(condomino);
				listaAgendamento.add(agendamento);
			}	
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
		return listaAgendamento;
	}

}
