package br.com.condominiosvirtuais.persistence.impl;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.inject.Instance;
import javax.inject.Inject;

import org.apache.log4j.Logger;

import br.com.condominiosvirtuais.entity.Bloco;
import br.com.condominiosvirtuais.entity.Condominio;
import br.com.condominiosvirtuais.entity.Condomino;
import br.com.condominiosvirtuais.entity.GestorCondominio;
import br.com.condominiosvirtuais.entity.Unidade;
import br.com.condominiosvirtuais.enumeration.TipoGestorCondominioEnum;
import br.com.condominiosvirtuais.exception.BusinessException;
import br.com.condominiosvirtuais.persistence.BlocoDAO;
import br.com.condominiosvirtuais.util.SQLUtil;

public class BlocoDAOImpl implements BlocoDAO, Serializable{
	
	private static final long serialVersionUID = 1L;

	private static Logger logger = Logger.getLogger(BlocoDAOImpl.class);
	
	private static final String BLOCO = "BLOCO";
	
	private static final String ID = "ID";
	
	private static final String NOME = "NOME";
	
	private static final String ID_CONDOMINIO = "ID_CONDOMINIO";	
	
	// Constraint referente a integridade do bloco e unidade
	private static final String FK_UNIDADE_ID_BLOCO_BLOCO_ID = "FK_UNIDADE_ID_BLOCO_BLOCO_ID";
	
	// Constraint referente a integridade do bloco e ambiente (considerando a tabela AMBIENTE_BLOCO)
	private static final String FK_ID_AMBIENTE_BLOCO_BLOCO = "fk_id_ambiente_bloco_bloco";	
	
	// Constraint referente a integridade do bloco conjunto bloco e bloco (considerando a tabela BLOCO_CONJUNTO_BLOCO)
	private static final String FK_BLOCO_CONJUNTO_BLOCO_ID_BLOCO_BLOCO_ID = "FK_BLOCO_CONJUNTO_BLOCO_ID_BLOCO_BLOCO_ID";
	
	// Constraint referente a integridade do gestor condomínio e bloco (considerando a tabela GESTOR_CONDOMINIO)
	private static final String FK_GESTOR_CONDOMINIO_ID_BLOCO_BLOCO_ID = "FK_GESTOR_CONDOMINIO_ID_BLOCO_BLOCO_ID";
	
	// Constraint referente a integridade do nome bloco e o condomínio, ou seja, não é possível repetir um bloco com o mesmo nome para o mesmo condomínio
	private static final String UQ_BLOCO_NOME_ID_CONDOMINIO = "UQ_BLOCO_NOME_ID_CONDOMINIO";	
	
	@Inject
	private Instance<UnidadeDAOImpl> unidadeDAO = null;
	
	@Inject
	private Instance<CondominoDAOImpl> condominoDAO = null;
	
	@Inject
	private Instance<GestorCondominioDAOImpl> gestorCondominioDAO = null;	
	
	
	/**
	 * Método que pesquisa todos os blocos de um condomínio e seu nome.
	 * @param condominio - Parâmetro que contém o id do condomínio pesquisado
	 * @param nomeBloco - Parâmetro que contém o nome do bloco - 
	 * Obs.: Esse parâmetro pode ser null e caso seja preenchido, não precisa coincidir todos o nome do bloco, somente o início.
	 * @return Lista de Blocos
	 *  
	 * @author Maikel Joel de Souza
	 * @throws Exception 
	 * @since 12/01/2013 
	 */
	public List<Bloco> buscarListaBlocosPorCondominioENome(Condominio condominio, String nomeBloco) throws SQLException, Exception{			
		StringBuffer query = new StringBuffer();
		query.append("SELECT * FROM ");
		query.append(BLOCO);
		query.append(" WHERE ");
		query.append(ID_CONDOMINIO);
		query.append(" = ? ");
		// Caso onde foi considerado o nome do condomínio
		if(nomeBloco != null && ! nomeBloco.isEmpty()){
			query.append(" AND UPPER(");	
			query.append(NOME);
			query.append(")");
			query.append(" LIKE '");
			query.append(nomeBloco.trim().toUpperCase());
			query.append("%'");
		}		
		query.append(" ORDER BY ");
		query.append(NOME);
		query.append(";");		
		Connection con = Conexao.getConexao();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		List<Bloco> listaBloco = new ArrayList<Bloco>();
		Bloco bloco = null;
		Condomino sindico = null;
		Condomino subSindico = null;
		try {
			preparedStatement = con.prepareStatement(query.toString());
			SQLUtil.setValorPpreparedStatement(preparedStatement, 1, condominio.getId(), java.sql.Types.INTEGER);		
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()){
				bloco = new Bloco();
				bloco.setId((Integer) SQLUtil.getValorResultSet(resultSet, ID, java.sql.Types.INTEGER));
				bloco.setNome(String.valueOf(SQLUtil.getValorResultSet(resultSet, NOME, java.sql.Types.VARCHAR)));
				bloco.setIdCondominio((Integer) SQLUtil.getValorResultSet(resultSet, ID_CONDOMINIO, java.sql.Types.INTEGER));				
				sindico = this.condominoDAO.get().buscarSindicoPorBloco(bloco, con);
				// Condição criada para garantir que ao atualizar o bloco o síndico não seja null 
				bloco.setSindico(sindico == null ? new Condomino() : sindico);
				subSindico = this.condominoDAO.get().buscarSubSindicoPorBloco(bloco,con);
				// Condição criada para garantir que ao atualizar o bloco o subsíndico não seja null 
				bloco.setSubSindico(subSindico == null ? new Condomino() : subSindico);
				listaBloco.add(bloco);
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
		return listaBloco;
	}
	
	public void salvarBloco(Bloco bloco) throws SQLException, BusinessException, Exception{
		Connection con = Conexao.getConexao();
		StringBuffer query = new StringBuffer();
		query.append("INSERT INTO "); 
		query.append(BLOCO);
		query.append("("); 
		query.append(NOME); 
		query.append(",");
		query.append(ID_CONDOMINIO); 
		query.append(") ");
		query.append("VALUES(?,?)");
		PreparedStatement statement = null;
		try {			
			statement = con.prepareStatement(query.toString());
			SQLUtil.setValorPpreparedStatement(statement, 1, bloco.getNome().toUpperCase().trim(), java.sql.Types.VARCHAR);
			SQLUtil.setValorPpreparedStatement(statement, 2, bloco.getIdCondominio(), java.sql.Types.INTEGER);			
			statement.execute();			
		} catch (SQLException e) {
			if (e.getMessage().contains(UQ_BLOCO_NOME_ID_CONDOMINIO)){
				throw new BusinessException("msg.bloco.salvarBlocoMesmoNome"); 
			}else{					
				throw new BusinessException("msg.erro.executarOperacao",e);				
			}
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
	
	public void salvarBlocoEmLote(List<Bloco> listaBlocos) throws SQLException, Exception{
		PreparedStatement statement = null;
		Connection con = Conexao.getConexao();		
		try {			
			StringBuffer query = new StringBuffer();
			query.append("INSERT INTO "); 
			query.append(BLOCO);
			query.append("("); 
			query.append(NOME); 
			query.append(",");
			query.append(ID_CONDOMINIO); 
			query.append(") ");
			query.append("VALUES(?,?)");			 
			statement = con.prepareStatement(query.toString());
			for (Bloco bloco : listaBlocos) {				
				SQLUtil.setValorPpreparedStatement(statement, 1, bloco.getNome().toUpperCase().trim(), java.sql.Types.VARCHAR);
				SQLUtil.setValorPpreparedStatement(statement, 2, bloco.getIdCondominio(), java.sql.Types.INTEGER);
				statement.addBatch();	
			}			
			statement.executeBatch();			
		} catch (SQLException e) {				
			throw e;
		}catch (Exception e) {					
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
	
	public void atualizarBloco(Bloco bloco) throws SQLException, BusinessException, Exception{
		StringBuffer query = new StringBuffer();
		Connection con = Conexao.getConexao();		
		query.append("UPDATE ");
		query.append(BLOCO);
		query.append(" SET ");
		query.append(NOME);
		query.append("= ?, ");			
		query.append(ID_CONDOMINIO);
		query.append("= ? "); 
		query.append("WHERE ");
		query.append(ID);
		query.append("= ?");
		PreparedStatement statement = null;
		try {
			con.setAutoCommit(false);
			statement = con.prepareStatement(query.toString());
			SQLUtil.setValorPpreparedStatement(statement, 1, bloco.getNome().toUpperCase().trim(), java.sql.Types.VARCHAR);
			SQLUtil.setValorPpreparedStatement(statement, 2, bloco.getIdCondominio(), java.sql.Types.INTEGER);
			SQLUtil.setValorPpreparedStatement(statement, 3, bloco.getId(), java.sql.Types.INTEGER);			
			statement.executeUpdate();				
			if(!this.gestorCondominioDAO.get().buscarListaGestoresCondominioPorBloco(bloco,con).isEmpty()){
				this.gestorCondominioDAO.get().excluirGestorCondominioPorBloco(bloco, con);				
			}
			this.salvarGestoresBloco(bloco, con);
			con.commit();
		} catch (SQLException e) {
			if (e.getMessage().contains(UQ_BLOCO_NOME_ID_CONDOMINIO)){
				throw new BusinessException("msg.bloco.atualizarBlocoMesmoNome");
			}else{				
				throw e;				
			}
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
	
	public void excluirBloco(Bloco bloco ) throws SQLException, BusinessException, Exception{
		StringBuffer query = new StringBuffer();
		Connection con = Conexao.getConexao();
		PreparedStatement statement = null;
		try {			
			query.append("DELETE FROM ");
			query.append(BLOCO);
			query.append(" WHERE ");		
			query.append(ID);
			query.append(" = ?");
			statement = con.prepareStatement(query.toString());
			statement.setInt(1, bloco.getId());
			statement.executeUpdate();			
		} catch (SQLException e) {				
			if (e.getMessage().contains(FK_UNIDADE_ID_BLOCO_BLOCO_ID)){
				throw new BusinessException("msg.bloco.excluirUnidadeAssociada"); 
			}else if (e.getMessage().contains(FK_ID_AMBIENTE_BLOCO_BLOCO)){
				throw new BusinessException("msg.bloco.excluirAmbienteAssociado"); 
			}else if (e.getMessage().contains(FK_BLOCO_CONJUNTO_BLOCO_ID_BLOCO_BLOCO_ID)){
				throw new BusinessException("msg.bloco.excluirConjuntoBlocoAssociado"); 
			}else if (e.getMessage().contains(FK_GESTOR_CONDOMINIO_ID_BLOCO_BLOCO_ID)){
				throw new BusinessException("msg.bloco.excluirGestorCondominioAssociado"); 
			}else{
				throw e;
			}
		}finally{
			try {
				statement.close();
				con.close();				
			} catch (SQLException e) {
				logger.error("erro sqlstate "+e.getSQLState(), e);				
			}
		}	
	}	
	
	
	/**
	 * Método que popula um bloco, ou seja, recebe um objeto bloco e "seta" nele todas as unidades e condôninos desse bloco conforme o nome passado por parâmetro.
	 * 
	 * @param nomeCondominos - Deve conter o nome ou fragmento do nome do condômino que será procurado.
	 * @param bloco - Ao final do método esse objeto estará todo populado.
	 * @throws Exception 
	 */
	public void popularBlocoPorNomeCondominos(String nomeCondominos, Bloco bloco) throws SQLException, Exception{
		List<Unidade> listaDeUnidades = null;
		List<Condomino> listaDeCondominos = null;		
		listaDeUnidades = this.unidadeDAO.get().buscarListaUnidadesPorBloco(bloco);
		bloco.setListaUnidade(listaDeUnidades);
		for (Unidade unidade : listaDeUnidades) {
			listaDeCondominos = this.condominoDAO.get().buscarListaCondominosPeloNomeEPorUnidade(nomeCondominos, unidade);		
			unidade.setListaCondominos(listaDeCondominos);
		}		
	}
	
	public Bloco buscarPorId(Integer idBloco) throws SQLException, Exception{
		StringBuffer query = new StringBuffer();
		query.append("SELECT * FROM ");
		query.append(BLOCO);
		query.append(" WHERE ");
		query.append(ID);
		query.append(" = ?");
		Connection con = Conexao.getConexao();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Bloco bloco = null;
		try {
			preparedStatement = con.prepareStatement(query.toString());			
			SQLUtil.setValorPpreparedStatement(preparedStatement, 1,idBloco, java.sql.Types.INTEGER);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()){
				bloco = new Bloco();
				bloco.setId((Integer) SQLUtil.getValorResultSet(resultSet, ID, java.sql.Types.INTEGER));				
				bloco.setNome(String.valueOf(SQLUtil.getValorResultSet(resultSet, NOME, java.sql.Types.VARCHAR)));	
				bloco.setIdCondominio((Integer) SQLUtil.getValorResultSet(resultSet, ID_CONDOMINIO, java.sql.Types.INTEGER));
			}
		} catch (SQLException e) {			
			throw e;
		} catch (Exception e){			
			throw e;
		}finally{
			try {
				preparedStatement.close();
				con.close();				
			} catch (SQLException e) {
				logger.error("erro sqlstate "+e.getSQLState(), e);				
			}
		}		
		return bloco;	
	}
	
	public Bloco buscarPorId(Integer idBloco, Connection con) throws SQLException, Exception{
		StringBuffer query = new StringBuffer();
		query.append("SELECT * FROM ");
		query.append(BLOCO);
		query.append(" WHERE ");
		query.append(ID);
		query.append(" = ?");		
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Bloco bloco = null;
		try {			
			preparedStatement = con.prepareStatement(query.toString());			
			SQLUtil.setValorPpreparedStatement(preparedStatement, 1, idBloco, java.sql.Types.INTEGER);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()){				
				bloco = new Bloco();				
				bloco.setId((Integer) SQLUtil.getValorResultSet(resultSet, ID, java.sql.Types.INTEGER));
				bloco.setNome(String.valueOf(SQLUtil.getValorResultSet(resultSet, NOME, java.sql.Types.VARCHAR)));	
				bloco.setIdCondominio((Integer) SQLUtil.getValorResultSet(resultSet, ID_CONDOMINIO, java.sql.Types.INTEGER));
			}
		} catch (SQLException e) {
			con.rollback();				
			throw e;
		} catch (Exception e){
			con.rollback();			
			throw e;
		}	
		return bloco;
	}
	
	public List<Bloco> buscarPorIdCondominio(Integer idCondominio, Connection con) throws SQLException, Exception{
		StringBuffer query = new StringBuffer();
		query.append("SELECT * FROM ");
		query.append(BLOCO);
		query.append(" WHERE ");
		query.append(ID_CONDOMINIO);
		query.append(" = ?");		
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Bloco bloco = null;
		List<Bloco> listaBloco = new ArrayList<Bloco>();
		try {			
			preparedStatement = con.prepareStatement(query.toString());			
			SQLUtil.setValorPpreparedStatement(preparedStatement, 1, idCondominio, java.sql.Types.INTEGER);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()){				
				bloco = new Bloco();				
				bloco.setId((Integer) SQLUtil.getValorResultSet(resultSet, ID, java.sql.Types.INTEGER));
				bloco.setNome(String.valueOf(SQLUtil.getValorResultSet(resultSet, NOME, java.sql.Types.VARCHAR)));	
				bloco.setIdCondominio((Integer) SQLUtil.getValorResultSet(resultSet, ID_CONDOMINIO, java.sql.Types.INTEGER));
				listaBloco.add(bloco);
			}
		} catch (SQLException e) {
			con.rollback();				
			throw e;
		} catch (Exception e){
			con.rollback();			
			throw e;
		}	
		return listaBloco;
	}
	
	/**
	 * Método que popula um bloco, ou seja, recebe um objeto bloco e "seta" nele todas as, unidades e condôninos desse bloco.
	 * 
	 * @param condominio - Ao final do método esse objeto estará todo populado.
	 * @throws Exception 
	 */
	public void popularBloco(Bloco bloco) throws SQLException, Exception{		
		List<Unidade> listaDeUnidades = null;
		List<Condomino> listaDeCondominos = null;
		listaDeUnidades = this.unidadeDAO.get().buscarListaUnidadesPorBloco(bloco);
		bloco.setListaUnidade(listaDeUnidades);
		for (Unidade unidade : listaDeUnidades) {
			listaDeCondominos = this.condominoDAO.get().buscarListaCondominosPorUnidade(unidade);		
			unidade.setListaCondominos(listaDeCondominos);
		}
		
	}	
	
	private void salvarGestoresBloco(Bloco bloco, Connection con) throws SQLException, Exception {
		// Persiste o Síndico do bloco - Caso o bloco possua Síndico
		if(bloco.getSindico().getId() != null && bloco.getSindico().getId() != 0){
			GestorCondominio sindicoGestorCondominio = new GestorCondominio();
			sindicoGestorCondominio.setIdBloco(bloco.getId());
			sindicoGestorCondominio.setIdCondomino(bloco.getSindico().getId());
			sindicoGestorCondominio.setTipoCondomino(TipoGestorCondominioEnum.SINDICO.getGestorCondominio());
			this.gestorCondominioDAO.get().salvarGestorCondominioBloco(sindicoGestorCondominio, con);			
		}
		// Persiste o SubSíndico - Caso possua subSíndico
		if(bloco.getSubSindico().getId() != null && bloco.getSubSindico().getId() != 0){
			GestorCondominio subsindicoGestorCondominio = new GestorCondominio();
			subsindicoGestorCondominio.setIdBloco(bloco.getId());
			subsindicoGestorCondominio.setIdCondomino(bloco.getSubSindico().getId());
			subsindicoGestorCondominio.setTipoCondomino(TipoGestorCondominioEnum.SUBSINDICO.getGestorCondominio());
			this.gestorCondominioDAO.get().salvarGestorCondominioBloco(subsindicoGestorCondominio, con);
		}
		GestorCondominio conselheiroGestorCondominio = null;
		// Persiste os Conselheiros - Caso possua mais conselheiros
		for (Condomino conselheiro : bloco.getListaConselheiros()) {
			conselheiroGestorCondominio = new GestorCondominio();
			conselheiroGestorCondominio.setIdBloco(bloco.getId());
			conselheiroGestorCondominio.setIdCondomino(conselheiro.getId());
			conselheiroGestorCondominio.setTipoCondomino(TipoGestorCondominioEnum.CONSELHEIRO_BLOCO.getGestorCondominio());
			this.gestorCondominioDAO.get().salvarGestorCondominioBloco(conselheiroGestorCondominio, con);
		}
	}


}
