package br.com.condominiosvirtuais.persistence.impl;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.enterprise.inject.Instance;
import javax.inject.Inject;

import org.apache.log4j.Logger;

import br.com.condominiosvirtuais.cdi.qualifier.QualifierAmbienteDAO;
import br.com.condominiosvirtuais.entity.Ambiente;
import br.com.condominiosvirtuais.entity.Bloco;
import br.com.condominiosvirtuais.entity.BlocoConjuntoBloco;
import br.com.condominiosvirtuais.entity.Condominio;
import br.com.condominiosvirtuais.entity.Condomino;
import br.com.condominiosvirtuais.entity.ConjuntoBloco;
import br.com.condominiosvirtuais.entity.Reserva;
import br.com.condominiosvirtuais.entity.TipoConjuntoBloco;
import br.com.condominiosvirtuais.enumeration.ReservaEnum;
import br.com.condominiosvirtuais.exception.BusinessException;
import br.com.condominiosvirtuais.persistence.AmbienteDAO;
import br.com.condominiosvirtuais.persistence.BlocoConjuntoBlocoDAO;
import br.com.condominiosvirtuais.persistence.ConjuntoBlocoDAO;
import br.com.condominiosvirtuais.persistence.ReservaDAO;
import br.com.condominiosvirtuais.util.SQLUtil;

public class ReservaDAOImpl implements ReservaDAO, Serializable {
	
	private static final long serialVersionUID = 1L;

	private static Logger logger = Logger.getLogger(ReservaDAOImpl.class);
	
	private static final String RESERVA = "RESERVA";

	private static final String ID = "ID";
	
	private static final String DATA = "DATA";
	
	private static final String SITUACAO = "SITUACAO";
	
	private static final String MOTIVO_REPROVACAO = "MOTIVO_REPROVACAO";
	
	private static final String ID_CONDOMINO = "ID_CONDOMINO";
	
	private static final String ID_AMBIENTE = "ID_AMBIENTE";
	
	@Inject @QualifierAmbienteDAO
	private Instance<AmbienteDAO> ambienteDAO = null;
	
	@Inject
	private Instance<CondominoDAOImpl> condominoDAO = null;
	
	@Inject
	private Instance<BlocoDAOImpl> blocoDAO = null;
	
	@Inject @QualifierAmbienteDAO
	private ConjuntoBlocoDAO conjuntoBlocoDAO = null;
	
	@Inject 
	private BlocoConjuntoBlocoDAO blocoConjuntoBlocoDAO = null;
	
	
	public void salvar(Reserva reserva) throws SQLException, BusinessException, Exception {
		StringBuffer query = new StringBuffer();
		query.append("INSERT INTO "); 
		query.append(RESERVA);
		query.append("("); 
		query.append(DATA);	
		query.append(",");
		query.append(ID_CONDOMINO);
		query.append(",");
		query.append(SITUACAO);
		query.append(",");		
		query.append(ID_AMBIENTE);
		query.append(",");		
		query.append(MOTIVO_REPROVACAO);
		query.append(") ");
		query.append("VALUES(?,?,?,?,?)");
		PreparedStatement statement = null;		
		Connection con = Conexao.getConexao();
		try {
			statement = con.prepareStatement(query.toString());
			// Condição criada para garantir a validação caso apresente um bug no calendário, que já faz essa validação 
			if (this.existeReservaMesmaDataEAmbienteEAprovadaOUPendente(con, reserva)){
				throw new BusinessException("msg.reserva.salvarReservaMesmaDataEAmbiente");
			}
			SQLUtil.setValorPpreparedStatement(statement, 1, reserva.getData(), java.sql.Types.DATE);			
			SQLUtil.setValorPpreparedStatement(statement, 2, reserva.getCondomino().getId(), java.sql.Types.INTEGER);
			SQLUtil.setValorPpreparedStatement(statement, 3, reserva.getSituacao(), java.sql.Types.VARCHAR);
			SQLUtil.setValorPpreparedStatement(statement, 4, reserva.getAmbiente().getId(), java.sql.Types.INTEGER);
			SQLUtil.setValorPpreparedStatement(statement, 5, reserva.getMotivoReprovacao(), java.sql.Types.VARCHAR);
			statement.executeUpdate();
		} catch (SQLException e) {			
			throw e;	
		} catch (BusinessException e) {			
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
	
	public void excluir(Reserva reserva) throws SQLException, Exception {
		StringBuffer query = new StringBuffer();
		Connection con = Conexao.getConexao();
		PreparedStatement statement = null;
		try {			
			query.append("DELETE FROM ");
			query.append(RESERVA);
			query.append(" WHERE ");		
			query.append(ID);
			query.append(" = ?");
			statement = con.prepareStatement(query.toString());
			statement.setInt(1, reserva.getId());
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

	public List<Reserva> buscarPorCondomino(Condomino condomino) throws NumberFormatException, SQLException, Exception {
		StringBuffer query = new StringBuffer();
		query.append("SELECT * FROM ");
		query.append(RESERVA);
		query.append(" WHERE ");
		query.append(ID_CONDOMINO);		
		query.append(" = ?");
		query.append(";");
		Connection con = Conexao.getConexao();
		PreparedStatement preparedStatement = null;
		List<Reserva> listaReserva = new ArrayList<Reserva>();
		try {
			preparedStatement = con.prepareStatement(query.toString());
			SQLUtil.setValorPpreparedStatement(preparedStatement, 1, condomino.getId(), java.sql.Types.INTEGER);
			ResultSet resultSet = preparedStatement.executeQuery();
			Reserva reserva = null;	
			Ambiente ambiente = null;
			while(resultSet.next()){				
				reserva = new Reserva();				
				reserva.setId((Integer) SQLUtil.getValorResultSet(resultSet, ID, java.sql.Types.INTEGER));
				reserva.setData((Date)(SQLUtil.getValorResultSet(resultSet, DATA, java.sql.Types.DATE)));
				reserva.setSituacao(String.valueOf(SQLUtil.getValorResultSet(resultSet, SITUACAO, java.sql.Types.VARCHAR)));
				reserva.setMotivoReprovacao(String.valueOf(SQLUtil.getValorResultSet(resultSet, MOTIVO_REPROVACAO, java.sql.Types.VARCHAR)));
				reserva.setCondomino(condomino);
				ambiente = this.ambienteDAO.get().buscarPorId((Integer) SQLUtil.getValorResultSet(resultSet, ID_AMBIENTE, java.sql.Types.INTEGER));
				reserva.setAmbiente(ambiente);
				listaReserva.add(reserva);
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
		return listaReserva;
	}

	public void atualizar(Reserva reserva) throws SQLException, BusinessException, Exception {
		StringBuffer query = new StringBuffer();
		query.append("UPDATE ");
		query.append(RESERVA);
		query.append(" SET ");
		query.append(DATA);
		query.append("= ?, ");
		query.append(SITUACAO);
		query.append("= ?, ");
		query.append(ID_CONDOMINO);
		query.append("= ?, ");		
		query.append(ID_AMBIENTE);
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
			// Condição criada para garantir a validação caso apresente um bug no calendário, que já faz essa validação
			if (this.existeReservaMesmaDataEAmbienteEAprovadaOUPendente(con, reserva)){
				throw new BusinessException("msg.reserva.salvarReservaMesmaDataEAmbiente");
			}
			SQLUtil.setValorPpreparedStatement(statement, 1, reserva.getData(), java.sql.Types.DATE);
			SQLUtil.setValorPpreparedStatement(statement, 2, reserva.getSituacao(), java.sql.Types.VARCHAR);
			SQLUtil.setValorPpreparedStatement(statement, 3, reserva.getCondomino().getId(), java.sql.Types.INTEGER);
			SQLUtil.setValorPpreparedStatement(statement, 4, reserva.getAmbiente().getId(), java.sql.Types.INTEGER);
			SQLUtil.setValorPpreparedStatement(statement, 5, reserva.getMotivoReprovacao(), java.sql.Types.VARCHAR);
			SQLUtil.setValorPpreparedStatement(statement, 6, reserva.getId(), java.sql.Types.INTEGER);
			statement.executeUpdate();			
		}catch (SQLException e) {					
			throw e;		
		} catch (BusinessException e) {			
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
	
	public void aprovar(Reserva reserva) throws SQLException, Exception {
		StringBuffer query = new StringBuffer();
		query.append("UPDATE ");
		query.append(RESERVA);
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
			SQLUtil.setValorPpreparedStatement(statement, 1, reserva.getSituacao(), java.sql.Types.VARCHAR);
			SQLUtil.setValorPpreparedStatement(statement, 2, reserva.getId(), java.sql.Types.INTEGER);
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
	
	public void reprovar(Reserva reserva) throws SQLException, Exception {
		StringBuffer query = new StringBuffer();
		query.append("UPDATE ");
		query.append(RESERVA);
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
			SQLUtil.setValorPpreparedStatement(statement, 1, reserva.getSituacao(), java.sql.Types.VARCHAR);
			SQLUtil.setValorPpreparedStatement(statement, 2, reserva.getMotivoReprovacao(), java.sql.Types.VARCHAR);
			SQLUtil.setValorPpreparedStatement(statement, 3, reserva.getId(), java.sql.Types.INTEGER);
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


	public List<Reserva> buscarPorCondominioETipo(Condominio condominio, String tipo) throws SQLException, Exception {
// TODO: Passar conexão para os métodos 	
		List<Reserva> listaReserva = new ArrayList<Reserva>();
		//this.conjuntoBlocoDAO = new ConjuntoBlocoDAOImpl(new AmbienteDAOImpl());
		Connection con = Conexao.getConexao();
		List<Ambiente> listaAmbiente = this.ambienteDAO.get().buscarPorCondominioENomeAmbiente(condominio,null);
		StringBuffer query = new StringBuffer();
		query.append("SELECT * FROM ");
		query.append(RESERVA);
		query.append(" WHERE ");
		query.append(ID_AMBIENTE);		
		query.append(" = ? ");
		query.append("AND ");
		query.append(SITUACAO);
		query.append("= ? ");
		query.append(";");		
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Reserva reservaCondominio = null;	
		Reserva reservaConjuntoBloco = null;
		try {			
			for (Ambiente ambienteCondominio : listaAmbiente) {
				preparedStatement = con.prepareStatement(query.toString());
				SQLUtil.setValorPpreparedStatement(preparedStatement, 1, ambienteCondominio.getId(), java.sql.Types.INTEGER);
				SQLUtil.setValorPpreparedStatement(preparedStatement, 2, tipo, java.sql.Types.VARCHAR);
				resultSet = preparedStatement.executeQuery();				
				while(resultSet.next()){				
					reservaCondominio = new Reserva();				
					reservaCondominio.setId((Integer) SQLUtil.getValorResultSet(resultSet, ID, java.sql.Types.INTEGER));
					reservaCondominio.setData((Date)(SQLUtil.getValorResultSet(resultSet, DATA, java.sql.Types.DATE)));
					reservaCondominio.setSituacao(String.valueOf(SQLUtil.getValorResultSet(resultSet, SITUACAO, java.sql.Types.VARCHAR)));
					reservaCondominio.setMotivoReprovacao(String.valueOf(SQLUtil.getValorResultSet(resultSet, MOTIVO_REPROVACAO, java.sql.Types.VARCHAR)));
					Condomino condomino = this.condominoDAO.get().buscarCondominoPorId((Integer) SQLUtil.getValorResultSet(resultSet, ID_CONDOMINO, java.sql.Types.INTEGER));
					reservaCondominio.setCondomino(condomino);				
					reservaCondominio.setAmbiente(ambienteCondominio);
					listaReserva.add(reservaCondominio);
				}			
			}
			List<Bloco> listaBloco = this.blocoDAO.get().buscarListaBlocosPorCondominioENome(condominio,null);
			List<BlocoConjuntoBloco> listaBlocoConjuntoBlocos = null;		
			Set<Integer> listaIdConjuntoBloco = new HashSet<Integer>();
			for (Bloco bloco : listaBloco) {
				listaBlocoConjuntoBlocos =  this.blocoConjuntoBlocoDAO.buscarPorIdBloco(bloco.getId(), con);
				for (BlocoConjuntoBloco blocoConjuntoBloco : listaBlocoConjuntoBlocos) {
					listaIdConjuntoBloco.add(blocoConjuntoBloco.getConjuntoBloco().getId());
				}				
			}
			for (Integer idConjuntoBloco : listaIdConjuntoBloco) {					
				ConjuntoBloco conjuntoBloco = this.conjuntoBlocoDAO.buscarPorId(idConjuntoBloco, con);					
				for (TipoConjuntoBloco tipoConjuntoBloco : conjuntoBloco.getListaTipoConjuntoBlocos()) {
					Ambiente ambienteConjuntoBloco = (Ambiente) tipoConjuntoBloco;
					preparedStatement = con.prepareStatement(query.toString());
					SQLUtil.setValorPpreparedStatement(preparedStatement, 1, ambienteConjuntoBloco.getId(), java.sql.Types.INTEGER);
					SQLUtil.setValorPpreparedStatement(preparedStatement, 2, tipo, java.sql.Types.VARCHAR);
					resultSet = preparedStatement.executeQuery();
					while(resultSet.next()){				
						reservaConjuntoBloco = new Reserva();				
						reservaConjuntoBloco.setId((Integer) SQLUtil.getValorResultSet(resultSet, ID, java.sql.Types.INTEGER));
						reservaConjuntoBloco.setData((Date)(SQLUtil.getValorResultSet(resultSet, DATA, java.sql.Types.DATE)));
						reservaConjuntoBloco.setSituacao(String.valueOf(SQLUtil.getValorResultSet(resultSet, SITUACAO, java.sql.Types.VARCHAR)));
						reservaConjuntoBloco.setMotivoReprovacao(String.valueOf(SQLUtil.getValorResultSet(resultSet, MOTIVO_REPROVACAO, java.sql.Types.VARCHAR)));
						Condomino condomino = this.condominoDAO.get().buscarCondominoPorId((Integer) SQLUtil.getValorResultSet(resultSet, ID_CONDOMINO, java.sql.Types.INTEGER));
						reservaConjuntoBloco.setCondomino(condomino);				
						reservaConjuntoBloco.setAmbiente(ambienteConjuntoBloco);
						listaReserva.add(reservaConjuntoBloco);
					}
				}							
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
		return listaReserva;
	}
	
	public List<Reserva> buscarPorCondominio(Condominio condominio) throws SQLException, Exception {
		// TODO: Passar conexão para os métodos 	
			List<Reserva> listaReserva = new ArrayList<Reserva>();			
			Connection con = Conexao.getConexao();
			List<Ambiente> listaAmbiente = this.ambienteDAO.get().buscarPorCondominioENomeAmbiente(condominio,null);
			StringBuffer query = new StringBuffer();
			query.append("SELECT * FROM ");
			query.append(RESERVA);
			query.append(" WHERE ");
			query.append(ID_AMBIENTE);		
			query.append(" = ? ");
			query.append(";");		
			PreparedStatement preparedStatement = null;
			ResultSet resultSet = null;
			Reserva reservaCondominio = null;	
			Reserva reservaConjuntoBloco = null;
			try {			
				for (Ambiente ambienteCondominio : listaAmbiente) {
					preparedStatement = con.prepareStatement(query.toString());
					SQLUtil.setValorPpreparedStatement(preparedStatement, 1, ambienteCondominio.getId(), java.sql.Types.INTEGER);
					resultSet = preparedStatement.executeQuery();				
					while(resultSet.next()){				
						reservaCondominio = new Reserva();				
						reservaCondominio.setId((Integer) SQLUtil.getValorResultSet(resultSet, ID, java.sql.Types.INTEGER));
						reservaCondominio.setData((Date)(SQLUtil.getValorResultSet(resultSet, DATA, java.sql.Types.DATE)));
						reservaCondominio.setSituacao(String.valueOf(SQLUtil.getValorResultSet(resultSet, SITUACAO, java.sql.Types.VARCHAR)));
						reservaCondominio.setMotivoReprovacao(String.valueOf(SQLUtil.getValorResultSet(resultSet, MOTIVO_REPROVACAO, java.sql.Types.VARCHAR)));
						Condomino condomino = this.condominoDAO.get().buscarCondominoPorId((Integer) SQLUtil.getValorResultSet(resultSet, ID_CONDOMINO, java.sql.Types.INTEGER));
						reservaCondominio.setCondomino(condomino);				
						reservaCondominio.setAmbiente(ambienteCondominio);
						listaReserva.add(reservaCondominio);
					}			
				}
				List<Bloco> listaBloco = this.blocoDAO.get().buscarListaBlocosPorCondominioENome(condominio,null);
				List<BlocoConjuntoBloco> listaBlocoConjuntoBlocos = null;		
				Set<Integer> listaIdConjuntoBloco = new HashSet<Integer>();
				for (Bloco bloco : listaBloco) {
					listaBlocoConjuntoBlocos =  this.blocoConjuntoBlocoDAO.buscarPorIdBloco(bloco.getId(), con);
					for (BlocoConjuntoBloco blocoConjuntoBloco : listaBlocoConjuntoBlocos) {
						listaIdConjuntoBloco.add(blocoConjuntoBloco.getConjuntoBloco().getId());
					}				
				}
				for (Integer idConjuntoBloco : listaIdConjuntoBloco) {					
					ConjuntoBloco conjuntoBloco = this.conjuntoBlocoDAO.buscarPorId(idConjuntoBloco, con);					
					for (TipoConjuntoBloco tipoConjuntoBloco : conjuntoBloco.getListaTipoConjuntoBlocos()) {
						Ambiente ambienteConjuntoBloco = (Ambiente) tipoConjuntoBloco;
						preparedStatement = con.prepareStatement(query.toString());
						SQLUtil.setValorPpreparedStatement(preparedStatement, 1, ambienteConjuntoBloco.getId(), java.sql.Types.INTEGER);							
						resultSet = preparedStatement.executeQuery();
						while(resultSet.next()){				
							reservaConjuntoBloco = new Reserva();				
							reservaConjuntoBloco.setId((Integer) SQLUtil.getValorResultSet(resultSet, ID, java.sql.Types.INTEGER));
							reservaConjuntoBloco.setData((Date)(SQLUtil.getValorResultSet(resultSet, DATA, java.sql.Types.DATE)));
							reservaConjuntoBloco.setSituacao(String.valueOf(SQLUtil.getValorResultSet(resultSet, SITUACAO, java.sql.Types.VARCHAR)));
							reservaConjuntoBloco.setMotivoReprovacao(String.valueOf(SQLUtil.getValorResultSet(resultSet, MOTIVO_REPROVACAO, java.sql.Types.VARCHAR)));
							Condomino condomino = this.condominoDAO.get().buscarCondominoPorId((Integer) SQLUtil.getValorResultSet(resultSet, ID_CONDOMINO, java.sql.Types.INTEGER));
							reservaConjuntoBloco.setCondomino(condomino);				
							reservaConjuntoBloco.setAmbiente(ambienteConjuntoBloco);
							listaReserva.add(reservaConjuntoBloco);
						}
					}							
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
			return listaReserva;
		}


	@Override
	public List<Reserva> buscarPorIdAmbienteEMaiorIgualDataEPendeteOUAprovado(Integer idAmbiente, Date data) throws NumberFormatException, SQLException, Exception {
		StringBuffer query = new StringBuffer();
		query.append("SELECT * FROM ");
		query.append(RESERVA);
		query.append(" WHERE ");
		query.append(ID_AMBIENTE);		
		query.append(" = ? ");
		query.append("AND ");
		query.append(DATA);
		query.append(" >= ? ");		
		query.append("AND (");
		query.append(SITUACAO);
		query.append(" = ? ");
		query.append("OR ");
		query.append(SITUACAO);
		query.append(" = ? )");
		query.append(";");		
		Connection con = Conexao.getConexao();
		PreparedStatement preparedStatement = null;
		List<Reserva> listaReserva = new ArrayList<Reserva>();
		try {
			preparedStatement = con.prepareStatement(query.toString());
			SQLUtil.setValorPpreparedStatement(preparedStatement, 1, idAmbiente, java.sql.Types.INTEGER);
			SQLUtil.setValorPpreparedStatement(preparedStatement, 2, data, java.sql.Types.DATE);
			SQLUtil.setValorPpreparedStatement(preparedStatement, 3, ReservaEnum.APROVADA.getSituacao(), java.sql.Types.VARCHAR);
			SQLUtil.setValorPpreparedStatement(preparedStatement, 4, ReservaEnum.PENDENTE.getSituacao(), java.sql.Types.VARCHAR);
			ResultSet resultSet = preparedStatement.executeQuery();
			Reserva reserva = null;			
			while(resultSet.next()){				
				reserva = new Reserva();				
				reserva.setId((Integer) SQLUtil.getValorResultSet(resultSet, ID, java.sql.Types.INTEGER));
				reserva.setData((Date)(SQLUtil.getValorResultSet(resultSet, DATA, java.sql.Types.DATE)));
				reserva.setSituacao(String.valueOf(SQLUtil.getValorResultSet(resultSet, SITUACAO, java.sql.Types.VARCHAR)));
				reserva.setMotivoReprovacao(String.valueOf(SQLUtil.getValorResultSet(resultSet, MOTIVO_REPROVACAO, java.sql.Types.VARCHAR)));
				listaReserva.add(reserva);
			}
		} catch (NumberFormatException e) {
			throw e;
		} catch (SQLException e) {
			throw e;
		} catch (Exception e){
			throw e;
		}finally{
			try{				
				preparedStatement.close();
				con.close();				
			}catch (SQLException e) {
				logger.error("erro sqlstate "+e.getSQLState(), e);
		    }
		}
		return listaReserva;
	}
	
	/**
	 * Método que verifica se existe alguma reserva com status de pendente ou aprovada com a mesma data </br>
	 * para o mesmo ambiente.
	 * No caso da reserva ter sido reprovada, então essa poderá ser realizada solicitada novamente. 
	 * @param con
	 * @param Reserva - Objeto que será responsável para verificar se existe ou não uma, com as configurações citadas acimas, já cadastrada.
	 * @return True caso exista e false caso não exista.
	 * @throws SQLException
	 */
	private Boolean existeReservaMesmaDataEAmbienteEAprovadaOUPendente(Connection con, Reserva reserva) throws SQLException{		
		StringBuffer query = new StringBuffer();
		query.append("SELECT * FROM ");
		query.append(RESERVA);
		query.append(" WHERE ");
		query.append(ID_AMBIENTE);		
		query.append(" = ? ");
		query.append("AND ");
		query.append(DATA);		
		query.append(" = ? ");
		query.append("AND ");
		query.append(SITUACAO);
		query.append(" IN (?,?)");
		PreparedStatement preparedStatement = null;
		preparedStatement = con.prepareStatement(query.toString());
		SQLUtil.setValorPpreparedStatement(preparedStatement, 1, reserva.getAmbiente().getId(), java.sql.Types.INTEGER);
		SQLUtil.setValorPpreparedStatement(preparedStatement, 2, reserva.getData(), java.sql.Types.DATE);
		SQLUtil.setValorPpreparedStatement(preparedStatement, 3, ReservaEnum.APROVADA.getSituacao(), java.sql.Types.VARCHAR);
		SQLUtil.setValorPpreparedStatement(preparedStatement, 4, ReservaEnum.PENDENTE.getSituacao(), java.sql.Types.VARCHAR);
		ResultSet resultSet = preparedStatement.executeQuery();
		return resultSet.next();
	}
	
}