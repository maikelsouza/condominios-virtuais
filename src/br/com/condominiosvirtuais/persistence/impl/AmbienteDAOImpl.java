package br.com.condominiosvirtuais.persistence.impl;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
import br.com.condominiosvirtuais.entity.ItemAmbiente;
import br.com.condominiosvirtuais.entity.TipoConjuntoBloco;
import br.com.condominiosvirtuais.entity.Unidade;
import br.com.condominiosvirtuais.enumeration.CondominioSituacaoEnum;
import br.com.condominiosvirtuais.enumeration.TipoConjuntoBlocoEnum;
import br.com.condominiosvirtuais.exception.BusinessException;
import br.com.condominiosvirtuais.persistence.AmbienteDAO;
import br.com.condominiosvirtuais.persistence.BlocoConjuntoBlocoDAO;
import br.com.condominiosvirtuais.persistence.ConjuntoBlocoDAO;
import br.com.condominiosvirtuais.persistence.ItemAmbienteDAO;
import br.com.condominiosvirtuais.util.SQLUtil;

@QualifierAmbienteDAO
public class AmbienteDAOImpl implements AmbienteDAO, Serializable {
	
	private static final long serialVersionUID = 1L;

	private static Logger logger = Logger.getLogger(AmbienteDAOImpl.class); 

	private static final String AMBIENTE = "AMBIENTE";

	private static final String ID = "ID";
	
	private static final String NOME = "NOME";
	
	private static final String  ID_CONDOMINIO = "ID_CONDOMINIO";
	
	private static final String  ID_CONJUNTO_BLOCO = "ID_CONJUNTO_BLOCO";	
	
	private static final String  FK_RESERVA_ID_AMBIENTE_AMBIENTE_ID = "FK_RESERVA_ID_AMBIENTE_AMBIENTE_ID";
	
	
	@Inject
	private ItemAmbienteDAO itemAmbienteDAO;	
	
	@Inject
	private BlocoConjuntoBlocoDAO blocoConjuntoBlocoDAO;
	
	@Inject @QualifierAmbienteDAO
	private Instance<ConjuntoBlocoDAO> conjuntoBlocoDAO;
	
	@Inject
	private UnidadeDAOImpl unidadeDAO;
	
	@Inject
	private BlocoDAOImpl blocoDAO;
	
	@Inject
	private CondominioDAOImpl condominioDAO;

	public void excluir(Ambiente ambiente) throws SQLException, BusinessException, Exception {	
		StringBuffer query = new StringBuffer();
		Connection con = Conexao.getConexao();
		PreparedStatement statement = null;
		try {
			con.setAutoCommit(Boolean.FALSE);
			
			for (ItemAmbiente itemAmbiente : ambiente.getListaItensAmbiente()) {
				this.itemAmbienteDAO.excluir(itemAmbiente,con);
			}
			query.append("DELETE FROM ");
			query.append(AMBIENTE);
			query.append(" WHERE ");		
			query.append(ID);
			query.append(" = ?");
			statement = con.prepareStatement(query.toString());
			statement.setInt(1, ambiente.getId());
			statement.executeUpdate();	
			con.commit();
		} catch (SQLException e) {
			if (e.getMessage().contains(FK_RESERVA_ID_AMBIENTE_AMBIENTE_ID)){
				throw new BusinessException("msg.ambienteCondominio.reservaAssociada");
			}
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

	public void atualizar(Ambiente ambiente) throws SQLException,Exception {
		StringBuffer query = new StringBuffer();
		query.append("UPDATE ");
		query.append(AMBIENTE);
		query.append(" SET ");
		query.append(NOME);
		query.append("= ? ");
		if(ambiente.getIdCondominio() != null){
			query.append(",");
			query.append(ID_CONDOMINIO);			
			query.append("= ? ");
		}
		query.append(" WHERE ");
		query.append(ID);
		query.append("= ?");
		Connection con = Conexao.getConexao();
		PreparedStatement statement = null;
		try {
			statement = con.prepareStatement(query.toString());
			statement.setString(1, ambiente.getNome());	
			if(ambiente.getIdCondominio() != null){
				statement.setInt(2,ambiente.getIdCondominio());
				statement.setInt(3,ambiente.getId());
			}else{
				statement.setInt(2,ambiente.getId());			
			}
			statement.executeUpdate();			
		} catch (SQLException e) {			
			throw e;
		} catch (Exception e) {
			throw e;	
		}finally{
			try{
				statement.close();
				con.close();				
			} catch (SQLException e) {
				logger.error("erro sqlstate "+e.getSQLState(), e);
			}
		}	
	}

	public Ambiente buscarPorId(Integer idAmbiente) throws SQLException, Exception {
		StringBuffer query = new StringBuffer();
		query.append("SELECT * FROM ");
		query.append(AMBIENTE);
		query.append(" WHERE ");
		query.append(ID);		
		query.append(" = ?");
		query.append(";");		
		Connection con = Conexao.getConexao();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;		
		Ambiente ambiente = null;
		try {
			preparedStatement = con.prepareStatement(query.toString());
			preparedStatement.setInt(1, idAmbiente);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()){
				ambiente = new Ambiente();
				ambiente.setId(resultSet.getInt(ID));
				ambiente.setNome(resultSet.getString(NOME));	
				ambiente.setIdCondominio(resultSet.getInt(ID_CONDOMINIO));				
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
		return ambiente;
	}

	public List<Ambiente> buscarPorCondominioENomeAmbiente(Condominio condominio, String nomeAmbiente) throws SQLException, Exception {
		StringBuffer query = new StringBuffer();
		query.append("SELECT * FROM ");
		query.append(AMBIENTE);
		query.append(" WHERE ");
		query.append(ID_CONDOMINIO);		
		query.append(" = ?");
		if(nomeAmbiente != null && !nomeAmbiente.isEmpty()){
			query.append(" AND UPPER(");	
			query.append(NOME);
			query.append(")");
			query.append(" LIKE '");
			query.append(nomeAmbiente.trim().toUpperCase());
			query.append("%'");
		}
		query.append(";");		
		Connection con = Conexao.getConexao();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;		
		Ambiente ambiente = null;
		List<Ambiente> listaAmbientes = new ArrayList<Ambiente>();
		try {			
			preparedStatement = con.prepareStatement(query.toString());
			SQLUtil.setValorPpreparedStatement(preparedStatement, 1, condominio.getId(), java.sql.Types.INTEGER);			
			resultSet = preparedStatement.executeQuery();			
			while(resultSet.next()){
				ambiente = new Ambiente();
				ambiente.setId((Integer) SQLUtil.getValorResultSet(resultSet, ID, java.sql.Types.INTEGER));
				ambiente.setNome(String.valueOf(SQLUtil.getValorResultSet(resultSet, NOME, java.sql.Types.VARCHAR)));
				ambiente.setIdCondominio((Integer)(SQLUtil.getValorResultSet(resultSet, ID_CONDOMINIO, java.sql.Types.INTEGER)));				
				ambiente.setListaItensAmbiente(this.itemAmbienteDAO.buscarPorAmbiente(ambiente));
				listaAmbientes.add(ambiente);
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
		return listaAmbientes;
	}
	
	public List<Ambiente> buscarPorCondominio(Condominio condominio, Connection con) throws SQLException, Exception {	
		StringBuffer query = new StringBuffer();
		query.append("SELECT * FROM ");
		query.append(AMBIENTE);
		query.append(" WHERE ");
		query.append(ID_CONDOMINIO);		
		query.append(" = ?");
		query.append(";");
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;		
		Ambiente ambiente = null;
		List<Ambiente> listaAmbientes = new ArrayList<Ambiente>();
		try {			
			preparedStatement = con.prepareStatement(query.toString());
			preparedStatement.setInt(1, condominio.getId());
			resultSet = preparedStatement.executeQuery();			
			while(resultSet.next()){
				ambiente = new Ambiente();
				ambiente.setId(resultSet.getInt(ID));
				ambiente.setNome(resultSet.getString(NOME));	
				ambiente.setIdCondominio(resultSet.getInt(ID_CONDOMINIO));
				ambiente.setListaItensAmbiente(this.itemAmbienteDAO.buscarPorAmbiente(ambiente));
				listaAmbientes.add(ambiente);
			}
		} catch (SQLException e) {
			throw e;
		} catch (Exception e) {
			throw e;	
		}	
		return listaAmbientes;
	}
	
	public List<Ambiente> buscarPorCondominio(Condominio condominio) throws SQLException, Exception {	
		StringBuffer query = new StringBuffer();
		query.append("SELECT * FROM ");
		query.append(AMBIENTE);
		query.append(" WHERE ");
		query.append(ID_CONDOMINIO);		
		query.append(" = ?");
		query.append(";");
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;		
		Ambiente ambiente = null;
		List<Ambiente> listaAmbientes = new ArrayList<Ambiente>();
		Connection con = Conexao.getConexao();
		try {			
			preparedStatement = con.prepareStatement(query.toString());
			SQLUtil.setValorPpreparedStatement(preparedStatement,1, condominio.getId(), java.sql.Types.INTEGER);			
			resultSet = preparedStatement.executeQuery();			
			while(resultSet.next()){
				ambiente = new Ambiente();
				ambiente.setId((Integer) SQLUtil.getValorResultSet(resultSet, ID, java.sql.Types.INTEGER));
				ambiente.setNome(String.valueOf(SQLUtil.getValorResultSet(resultSet, NOME, java.sql.Types.VARCHAR)));
				ambiente.setIdCondominio((Integer) SQLUtil.getValorResultSet(resultSet, ID_CONDOMINIO, java.sql.Types.INTEGER));
				ambiente.setListaItensAmbiente(this.itemAmbienteDAO.buscarPorAmbiente(ambiente));
				listaAmbientes.add(ambiente);
			}
		} catch (SQLException e) {			
			con.rollback();
			throw e;
		} catch (Exception e) {
			con.rollback();
			throw e;	
		}finally{			
			try {
				con.close();
			} catch (SQLException e) {
				logger.error("erro sqlstate "+e.getSQLState(), e);
			}
		}		
		return listaAmbientes;
	}



	public List<Ambiente> buscarPorBlocoENomeAmbiente(Bloco bloco, String nomeAmbiente) throws SQLException, Exception {		
		Connection con = null;			
		List<Ambiente> listaAmbientes = null;		
		try {				
			con = Conexao.getConexao();
			List<BlocoConjuntoBloco> listaBlocoConjuntoBloco = this.blocoConjuntoBlocoDAO.buscarPorIdBloco(bloco.getId(),con);
			//this.conjuntoBlocoDAO = new ConjuntoBlocoDAOImpl(this);		
			listaAmbientes = new ArrayList<Ambiente>();		
			Iterator<BlocoConjuntoBloco> iteratorConjuntoBloco = listaBlocoConjuntoBloco.iterator();
			/* A modelagem do cadastro de ambiente (bloco, blocoConjuntoBloco, conjuntoBloco e ambiente) contempla a possibilidade de um ambiente
			 * fazer parte de um ou mais conjunto de blocos, porém a aplicação permite que um ambiente faça parte de um conjunto de bloco.
			 * Caso essa regra mude, o algoritmo abaixo, deve ser modificado. && listaAmbientes.isEmpty() 
			 */
			while(iteratorConjuntoBloco.hasNext() ){
				BlocoConjuntoBloco blocoConjuntoBloco = iteratorConjuntoBloco.next();
				ConjuntoBloco conjuntoBloco = this.getConjuntoBlocoDAO().buscarPorId(blocoConjuntoBloco.getConjuntoBloco().getId(),con);
				for (TipoConjuntoBloco tipoConjuntoBloco : conjuntoBloco.getListaTipoConjuntoBlocos()) {
					Ambiente ambiente = (Ambiente) tipoConjuntoBloco;
					// Não existe o atributo nome na tabela CONJUNTO_BLOCO, logo o like é feito no fonte, onde temos os ambientes em memória. 
					if(ambiente.getNome().toUpperCase().startsWith(nomeAmbiente.toUpperCase())){
						ambiente.setListaItensAmbiente(this.itemAmbienteDAO.buscarPorAmbiente(ambiente));
						listaAmbientes.add(ambiente);
					}
				}
			}
		} catch (SQLException e) {
			throw e;
		} catch (Exception e) {
			throw e;	
		}finally{			
			try {
				con.close();
			} catch (SQLException e) {
				logger.error("erro sqlstate "+e.getSQLState(), e);
			}
		}	
		return listaAmbientes;
	}

	public List<TipoConjuntoBloco> buscarPorIdConjuntoBloco(Integer idConjuntoBloco, Connection con) throws SQLException, Exception {		
		StringBuffer query = new StringBuffer();
		query.append("SELECT * FROM ");
		query.append(AMBIENTE);
		query.append(" WHERE ");
		query.append(ID_CONJUNTO_BLOCO);		
		query.append(" = ?");		
		query.append(";");		
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;		
		Ambiente ambiente = null;		
		List<TipoConjuntoBloco> listaTipoConjuntoBloco = new ArrayList<TipoConjuntoBloco>();
		try {
			preparedStatement = con.prepareStatement(query.toString());
			preparedStatement.setInt(1, idConjuntoBloco);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()){			
				ambiente = new Ambiente();				
				ambiente.setId((Integer) SQLUtil.getValorResultSet(resultSet, ID, java.sql.Types.INTEGER));				
				ambiente.setNome(String.valueOf(SQLUtil.getValorResultSet(resultSet, NOME, java.sql.Types.VARCHAR)));				
				ambiente.setIdCondominio((Integer) SQLUtil.getValorResultSet(resultSet, ID_CONDOMINIO, java.sql.Types.INTEGER));
				ambiente.setIdConjuntoBloco((Integer) SQLUtil.getValorResultSet(resultSet, ID_CONJUNTO_BLOCO, java.sql.Types.INTEGER));
				ambiente.setListaItensAmbiente(this.itemAmbienteDAO.buscarPorAmbiente(ambiente));
				listaTipoConjuntoBloco.add(ambiente);
			}
		} catch (SQLException e) {
			con.rollback();		
		}catch (Exception e) {
			con.rollback();
			throw e;
		}	
		return listaTipoConjuntoBloco;		
	}
	
	
	public List<Ambiente> buscarPorIdConjuntoBloco(Integer idConjuntoBloco) throws SQLException, Exception{		
		StringBuffer query = new StringBuffer();
		query.append("SELECT * FROM ");
		query.append(AMBIENTE);
		query.append(" WHERE ");
		query.append(ID_CONJUNTO_BLOCO);		
		query.append(" = ?");
		query.append(";");		
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;		
		Ambiente ambiente = null;		
		List<Ambiente> listaAmbiente = new ArrayList<Ambiente>();
		Connection con = Conexao.getConexao();
		try {
			preparedStatement = con.prepareStatement(query.toString());
			preparedStatement.setInt(1, idConjuntoBloco);
			resultSet = preparedStatement.executeQuery();			
			while(resultSet.next()){			
				ambiente = new Ambiente();				
				ambiente.setId((Integer) SQLUtil.getValorResultSet(resultSet, ID, java.sql.Types.INTEGER));
				ambiente.setNome(String.valueOf(SQLUtil.getValorResultSet(resultSet, NOME, java.sql.Types.VARCHAR)));				
				ambiente.setIdCondominio((Integer) SQLUtil.getValorResultSet(resultSet, ID_CONDOMINIO, java.sql.Types.INTEGER));
				ambiente.setIdConjuntoBloco((Integer) SQLUtil.getValorResultSet(resultSet, ID_CONJUNTO_BLOCO, java.sql.Types.INTEGER));	
				ambiente.setListaItensAmbiente(this.itemAmbienteDAO.buscarPorAmbiente(ambiente));
				listaAmbiente.add(ambiente);
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
		return listaAmbiente;
	}
		

	public void salvar(TipoConjuntoBloco tipoConjuntoBloco) throws SQLException, Exception {
		Ambiente ambiente = (Ambiente) tipoConjuntoBloco;
		StringBuffer query = new StringBuffer();
		query.append("INSERT INTO "); 
		query.append(AMBIENTE);
		query.append("("); 
		query.append(NOME);
		query.append(",");
		query.append(ID_CONDOMINIO);
		query.append(",");
		query.append(ID_CONJUNTO_BLOCO);
		query.append(") ");
		query.append("VALUES(?,?,?)");
		PreparedStatement statement = null;		
		Connection con = Conexao.getConexao();
		try {			
			statement = con.prepareStatement(query.toString());			
			SQLUtil.setValorPpreparedStatement(statement, 1, ambiente.getNome(), java.sql.Types.VARCHAR);
			SQLUtil.setValorPpreparedStatement(statement, 2, ambiente.getIdCondominio(), java.sql.Types.INTEGER);
			SQLUtil.setValorPpreparedStatement(statement, 3, ambiente.getIdConjuntoBloco(), java.sql.Types.INTEGER);
			statement.executeUpdate();
		} catch (SQLException e) {
			throw e;
		} catch (Exception e) {
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

	public void salvar(TipoConjuntoBloco tipoConjuntoBloco, Connection con)	throws SQLException, Exception {
		Ambiente ambiente = (Ambiente) tipoConjuntoBloco;
		StringBuffer query = new StringBuffer();
		query.append("INSERT INTO "); 
		query.append(AMBIENTE);
		query.append("("); 
		query.append(NOME);
		query.append(",");
		query.append(ID_CONJUNTO_BLOCO);
		query.append(",");
		query.append(ID_CONDOMINIO);
		query.append(") ");
		query.append("VALUES(?,?,?)");		
		PreparedStatement statement = null;				
		try {			
			statement = con.prepareStatement(query.toString());			
			SQLUtil.setValorPpreparedStatement(statement, 1, ambiente.getNome(), java.sql.Types.VARCHAR);
			SQLUtil.setValorPpreparedStatement(statement, 2, ambiente.getIdConjuntoBloco(), java.sql.Types.INTEGER);
			SQLUtil.setValorPpreparedStatement(statement, 3, ambiente.getIdCondominio(), java.sql.Types.INTEGER);
			statement.executeUpdate();
		} catch (SQLException e) {
			con.rollback();
			throw e;
		} catch (Exception e) {
			con.rollback();
			throw e;	
		}	
	}


	public void atualizar(TipoConjuntoBloco tipoConjuntoBloco, Connection con) throws SQLException, Exception {
		Ambiente ambiente = (Ambiente) tipoConjuntoBloco;		
		StringBuffer query = new StringBuffer();
		query.append("UPDATE ");
		query.append(AMBIENTE);
		query.append(" SET ");
		query.append(NOME);
		query.append("= ?, ");
		query.append(ID_CONDOMINIO);			
		query.append("= ?, ");		
		query.append(ID_CONJUNTO_BLOCO);			
		query.append("= ? ");		
		query.append(" WHERE ");
		query.append(ID);
		query.append("= ?");		
		PreparedStatement statement = null;
		try {		
			statement = con.prepareStatement(query.toString());			
			SQLUtil.setValorPpreparedStatement(statement, 1, ambiente.getNome(), java.sql.Types.VARCHAR);		
			SQLUtil.setValorPpreparedStatement(statement, 2, ambiente.getIdCondominio(), java.sql.Types.INTEGER);
			SQLUtil.setValorPpreparedStatement(statement, 3, ambiente.getIdConjuntoBloco(), java.sql.Types.INTEGER);
			SQLUtil.setValorPpreparedStatement(statement, 4, ambiente.getId(), java.sql.Types.INTEGER);			
			statement.executeUpdate();		
		} catch (SQLException e) {
			con.rollback();
			throw e;
		} catch (Exception e) {
			con.rollback();
			throw e;	
		}	
		
	}

	
	public void excluir(TipoConjuntoBloco tipoConjuntoBloco, Connection con) throws SQLException, BusinessException, Exception {
		Ambiente ambiente = (Ambiente) tipoConjuntoBloco;		
		StringBuffer query = new StringBuffer();		
		PreparedStatement statement = null;
		try {			
			for (ItemAmbiente itemAmbiente : ambiente.getListaItensAmbiente()) {
				this.itemAmbienteDAO.excluir(itemAmbiente,con);
			}
			query.append("DELETE FROM ");
			query.append(AMBIENTE);
			query.append(" WHERE ");		
			query.append(ID);
			query.append(" = ?");
			statement = con.prepareStatement(query.toString());			
			SQLUtil.setValorPpreparedStatement(statement, 1, ambiente.getId(), java.sql.Types.INTEGER);			
			statement.executeUpdate();			
		} catch (SQLException e) {
			con.rollback();
			if (e.getMessage().contains(FK_RESERVA_ID_AMBIENTE_AMBIENTE_ID)){
				throw new BusinessException("msg.ambienteCondominio.reservaAssociada");
			}	
			throw e;
		} catch (Exception e) {
			con.rollback();
			throw e;
				
		}	
		
	}
	
	/**
	 * Método que busca todos os ambientes associados ao condomínio e a um conjunto de blocos, onde o condônino tem acesso.
	 * @throws Exception 
	 */
	public List<Ambiente> buscarPorCondomino(Condomino condomino) throws SQLException, Exception {	
		// Esse método faz a união dos ambientes associados ao condomínio e a un(s) conjunto de blocos o condômino tem acesso. 
		Connection con = Conexao.getConexao();
		ResultSet resultSet = null;		
		Ambiente ambienteCondominio = null;	
		Ambiente ambienteConjuntoBloco = null;
		PreparedStatement preparedStatement = null;
		List<Ambiente> listaAmbiente = null;
		try {
			// -- Início do código que descobre quais ambientes associados ao condomínio que o condômino tem acesso --
			Unidade unidade = this.unidadeDAO.buscarPorId(condomino.getIdUnidade(), con);
			Bloco bloco = this.blocoDAO.buscarPorId(unidade.getIdBloco(),con);	
			Condominio condominio = this.condominioDAO.buscarPorIdESituacao(bloco.getIdCondominio(),CondominioSituacaoEnum.ATIVO.getSituacao(),con);
			StringBuffer query = new StringBuffer();
			query.append("SELECT * FROM ");
			query.append(AMBIENTE);
			query.append(" WHERE ");
			query.append(ID_CONDOMINIO);		
			query.append(" = ?");
			query.append(";");
			listaAmbiente = new ArrayList<Ambiente>();
			preparedStatement = con.prepareStatement(query.toString());
			SQLUtil.setValorPpreparedStatement(preparedStatement, 1, condominio.getId(), java.sql.Types.INTEGER);
			resultSet = preparedStatement.executeQuery();			
			// Nesse momento os ambientes que estão associados a um condomínio estão sendo criados e populados
			while(resultSet.next()){				
				ambienteCondominio = new Ambiente();				
				ambienteCondominio.setId((Integer) SQLUtil.getValorResultSet(resultSet, ID, java.sql.Types.INTEGER));
				ambienteCondominio.setNome(String.valueOf(SQLUtil.getValorResultSet(resultSet, NOME, java.sql.Types.VARCHAR)));				
				ambienteCondominio.setIdCondominio((Integer) SQLUtil.getValorResultSet(resultSet, ID_CONDOMINIO, java.sql.Types.INTEGER));
				ambienteCondominio.setIdConjuntoBloco((Integer) SQLUtil.getValorResultSet(resultSet, ID_CONJUNTO_BLOCO, java.sql.Types.INTEGER));	
				ambienteCondominio.setListaItensAmbiente(this.itemAmbienteDAO.buscarPorAmbiente(ambienteCondominio));
				listaAmbiente.add(ambienteCondominio);
			}
			// -- Fim do código que descobre quais ambientes associados ao condomínio que o condômino tem acesso --
			
			// -- Início do código que descobre quais ambientes associados a um conjunto de blocos que o condômino tem acesso --
			List<BlocoConjuntoBloco> listaBlocoConjuntoBloco = this.blocoConjuntoBlocoDAO.buscarPorIdBloco(bloco.getId(), con);			
			for (BlocoConjuntoBloco blocoConjuntoBloco : listaBlocoConjuntoBloco) {
				ConjuntoBloco conjuntoBloco = this.getConjuntoBlocoDAO().buscarPorIdETipo(blocoConjuntoBloco.getConjuntoBloco().getId(), TipoConjuntoBlocoEnum.AMBIENTE.getConjuntoBloco(), con);				
				// Condição necessária para garantir que somente o conjunto blodo do tipo ambiente será considerado na query. 
				if(conjuntoBloco != null){							
					query = new StringBuffer();
					query.append("SELECT * FROM ");
					query.append(AMBIENTE);
					query.append(" WHERE ");
					query.append(ID_CONJUNTO_BLOCO);		
					query.append(" = ?");
					query.append(";");
					preparedStatement = con.prepareStatement(query.toString());
					SQLUtil.setValorPpreparedStatement(preparedStatement, 1, conjuntoBloco.getId(), java.sql.Types.INTEGER);
					resultSet = preparedStatement.executeQuery();				
					while(resultSet.next()){				
						ambienteConjuntoBloco = new Ambiente();				
						ambienteConjuntoBloco.setId((Integer) SQLUtil.getValorResultSet(resultSet, ID, java.sql.Types.INTEGER));
						ambienteConjuntoBloco.setNome(String.valueOf(SQLUtil.getValorResultSet(resultSet, NOME, java.sql.Types.VARCHAR)));				
						ambienteConjuntoBloco.setIdCondominio((Integer) SQLUtil.getValorResultSet(resultSet, ID_CONDOMINIO, java.sql.Types.INTEGER));
						ambienteConjuntoBloco.setIdConjuntoBloco((Integer) SQLUtil.getValorResultSet(resultSet, ID_CONJUNTO_BLOCO, java.sql.Types.INTEGER));	
						ambienteConjuntoBloco.setListaItensAmbiente(this.itemAmbienteDAO.buscarPorAmbiente(ambienteCondominio));
						listaAmbiente.add(ambienteConjuntoBloco);
					}
						// -- Fim do código que descobre quais ambientes associados a um conjunto de blocos que o condômino tem acesso --									
				}
			}
		}catch (SQLException e) {
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
		return listaAmbiente;		
	}

	@Override
	public Ambiente buscarPorId(Integer idAmbiente, Connection con) throws SQLException, Exception {
		StringBuffer query = new StringBuffer();
		query.append("SELECT * FROM ");
		query.append(AMBIENTE);
		query.append(" WHERE ");
		query.append(ID);		
		query.append(" = ?");
		query.append(";");
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;		
		Ambiente ambiente = null;
		try {
			preparedStatement = con.prepareStatement(query.toString());
			preparedStatement.setInt(1, idAmbiente);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()){
				ambiente = new Ambiente();
				ambiente.setId(resultSet.getInt(ID));
				ambiente.setNome(resultSet.getString(NOME));	
				ambiente.setIdCondominio(resultSet.getInt(ID_CONDOMINIO));				
			}
		}catch (SQLException e) {
			con.rollback();
			throw e;
		} catch (Exception e) {
			con.rollback();
			throw e;
		}	
		return ambiente;		
	}
	
	private ConjuntoBlocoDAO getConjuntoBlocoDAO(){
		ConjuntoBlocoDAO conjuntoBlocoDAO = null;
		if(this.conjuntoBlocoDAO.isAmbiguous()){
			conjuntoBlocoDAO = this.conjuntoBlocoDAO.iterator().next();
		}else{
			conjuntoBlocoDAO = this.conjuntoBlocoDAO.get();
		}			
		return conjuntoBlocoDAO;
	}

}
