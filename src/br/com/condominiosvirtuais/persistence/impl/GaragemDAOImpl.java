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

import br.com.condominiosvirtuais.entity.Bloco;
import br.com.condominiosvirtuais.entity.Condominio;
import br.com.condominiosvirtuais.entity.Garagem;
import br.com.condominiosvirtuais.entity.Unidade;
import br.com.condominiosvirtuais.exception.BusinessException;
import br.com.condominiosvirtuais.persistence.GaragemDAO;
import br.com.condominiosvirtuais.util.SQLUtil;

public class GaragemDAOImpl implements GaragemDAO, Serializable {
	
	private static final long serialVersionUID = 1L;

	private static Logger logger = Logger.getLogger(GaragemDAOImpl.class);	
	
	private static final String GARAGEM = "GARAGEM";

	private static final String ID = "ID";
	
	private static final String TAMANHO = "TAMANHO";
	
	private static final String  NUMERO = "NUMERO";
	
	private static final String  ID_UNIDADE = "ID_UNIDADE";	
	
	@Inject
	private Instance<UnidadeDAOImpl> unidadeDAO;
	
	@Inject
	private Instance<BlocoDAOImpl> blocoDAO;
	
	@Inject
	private Instance<CondominioDAOImpl> condominioDAO;
	
	// Constraint referente a integridade do veículo e garagem, ou seja, um veículo pode estar associado a uma garagem
	private static final String FK_VEICULO_ID_GARAGEM_GARAGEM_ID = "FK_VEICULO_ID_GARAGEM_GARAGEM_ID";
	
	

	
	@Override
	public void salvar(Garagem garagem) throws SQLException, BusinessException, Exception {
		Connection con = Conexao.getConexao();		
		if(this.existeGaragemMesmoNumeroCondominio(garagem, con)){
			throw new BusinessException("msg.garagem.salvarMesmoNumeroCondominio"); 
		}
		StringBuffer query = new StringBuffer();
		query.append("INSERT INTO "); 
		query.append(GARAGEM);
		query.append("("); 
		query.append(TAMANHO);	
		query.append(",");
		query.append(NUMERO);
		query.append(",");
		query.append(ID_UNIDADE);		
		query.append(") ");
		query.append("VALUES(?,?,?)");
		PreparedStatement statement = null;		
		try {			
			statement = con.prepareStatement(query.toString());
			SQLUtil.setValorPpreparedStatement(statement, 1, garagem.getTamanho(), java.sql.Types.INTEGER);			
			SQLUtil.setValorPpreparedStatement(statement, 2, garagem.getNumero(), java.sql.Types.VARCHAR);
			SQLUtil.setValorPpreparedStatement(statement, 3, garagem.getIdUnidade(), java.sql.Types.INTEGER);			
			statement.executeUpdate();
		} catch (SQLException e) {					
			throw e;
		} catch (Exception e) {		
			throw e;	
		} finally{
			try {		
				statement.close();
				con.close();				
			} catch (SQLException e) {
				logger.error("erro sqlstate "+e.getSQLState(), e);	
			}
		}	
	}

	private Boolean existeGaragemMesmoNumeroCondominio(Garagem garagem, Connection con) throws SQLException, Exception  {
		Boolean existeGaragemCondominio = Boolean.FALSE;
		Garagem garagemBase = null;		
		Iterator<Bloco> iteratorBloco = null;
		Iterator<Unidade> iteratorUnidade = null;
		Iterator<Garagem> iteratorGaragem = null;
		Unidade unidade =  this.unidadeDAO.get().buscarPorId(garagem.getIdUnidade(), con);
		Bloco bloco = this.blocoDAO.get().buscarPorId(unidade.getIdBloco(), con);
		Condominio condominio = this.condominioDAO.get().buscarCondominioPorId(bloco.getIdCondominio(), con);
		iteratorBloco = this.blocoDAO.get().buscarListaBlocosPorCondominioENome(condominio, null).iterator();		
		while(!existeGaragemCondominio && iteratorBloco.hasNext()){
			bloco = iteratorBloco.next();
			iteratorUnidade = this.unidadeDAO.get().buscarListaUnidadesPorBloco(bloco).iterator();
			while(!existeGaragemCondominio && iteratorUnidade.hasNext()){
				unidade = iteratorUnidade.next();
				iteratorGaragem = this.buscarPorIdUnidade(unidade.getId(), con).iterator();
				while(!existeGaragemCondominio && iteratorGaragem.hasNext()){
					garagemBase = iteratorGaragem.next();
					if(garagemBase.getNumero().trim().equalsIgnoreCase(garagem.getNumero().trim())){
						existeGaragemCondominio = Boolean.TRUE;
					}
				}
			}
		}		
		return existeGaragemCondominio;
	}

	@Override
	public void excluir(Garagem garagem) throws SQLException, BusinessException, Exception  {
		StringBuffer query = new StringBuffer();
		Connection con = Conexao.getConexao();
		PreparedStatement statement = null;
		try {
			query.append("DELETE FROM ");
			query.append(GARAGEM);
			query.append(" WHERE ");		
			query.append(ID);
			query.append(" = ?");
			statement = con.prepareStatement(query.toString());
			SQLUtil.setValorPpreparedStatement(statement, 1, garagem.getId(), java.sql.Types.INTEGER);			
			statement.executeUpdate();
		} catch (SQLException e) {
			if (e.getMessage().contains(FK_VEICULO_ID_GARAGEM_GARAGEM_ID)){
				throw new BusinessException("msg.garagem.excluirVeiculoAssociado"); 
			}else{
				throw e;				
			}
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
	public void atualizar(Garagem garagem) throws SQLException, BusinessException, Exception {
		Connection con = Conexao.getConexao();
		if(this.existeGaragemMesmoNumeroCondominio(garagem, con)){
			throw new BusinessException("msg.garagem.salvarMesmoNumeroCondominio");
		}
		StringBuffer query = new StringBuffer();
		query.append("UPDATE ");
		query.append(GARAGEM);
		query.append(" SET ");
		query.append(NUMERO);
		query.append("= ?, ");
		query.append(TAMANHO);
		query.append("= ? ");		
		query.append(" WHERE ");
		query.append(ID);
		query.append("= ?");
		PreparedStatement statement = null;
		try {
			statement = con.prepareStatement(query.toString());
			SQLUtil.setValorPpreparedStatement(statement, 1, garagem.getNumero(), java.sql.Types.VARCHAR);
			SQLUtil.setValorPpreparedStatement(statement, 2, garagem.getTamanho(), java.sql.Types.INTEGER);
			SQLUtil.setValorPpreparedStatement(statement, 3, garagem.getId(), java.sql.Types.INTEGER);			
			statement.executeUpdate();			
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
	public void excluirPorIdUnidade(Integer IdUnidade, Connection con) throws SQLException, Exception  {
// TODO: Colocar condição para msg caso exclua uma garagem que tenha algum veículo associado.		
		StringBuffer query = new StringBuffer();		
		PreparedStatement statement = null;
		try {
			query.append("DELETE FROM ");
			query.append(GARAGEM);
			query.append(" WHERE ");		
			query.append(ID_UNIDADE);
			query.append(" = ?");
			statement = con.prepareStatement(query.toString());
			SQLUtil.setValorPpreparedStatement(statement, 1, IdUnidade, java.sql.Types.INTEGER);			
			statement.executeUpdate();	
		} catch (SQLException e) {	
			con.rollback();			
			throw e;			
		}catch (Exception e) {
			con.rollback();					
			throw e;		
		}
	}

	@Override
	public List<Garagem> buscarPorIdUnidade(Integer idUnidade) throws SQLException, Exception {
		StringBuffer query = new StringBuffer();
		query.append("SELECT * FROM ");
		query.append(GARAGEM);
		query.append(" WHERE ");
		query.append(ID_UNIDADE);		
		query.append(" = ?");				
		query.append(" ORDER BY ");
		query.append(NUMERO);
		query.append(";");		
		Connection con = Conexao.getConexao();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;		
		Garagem garagem = null;		
		List<Garagem> listaGaragem = new ArrayList<Garagem>();
		try {
			preparedStatement = con.prepareStatement(query.toString());
			SQLUtil.setValorPpreparedStatement(preparedStatement, 1, idUnidade, java.sql.Types.INTEGER);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()){
				garagem = new Garagem();
				garagem.setId((Integer) SQLUtil.getValorResultSet(resultSet, ID, java.sql.Types.INTEGER));
				garagem.setNumero(String.valueOf(SQLUtil.getValorResultSet(resultSet, NUMERO, java.sql.Types.VARCHAR)));
				garagem.setTamanho((Integer) SQLUtil.getValorResultSet(resultSet, TAMANHO, java.sql.Types.INTEGER));
				garagem.setIdUnidade((Integer) SQLUtil.getValorResultSet(resultSet, ID_UNIDADE, java.sql.Types.INTEGER));								
				listaGaragem.add(garagem);
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
		return listaGaragem;		
	}

	@Override
	public List<Garagem> buscarPorIdUnidade(Integer idUnidade, Connection con)
			throws SQLException, Exception  {
		StringBuffer query = new StringBuffer();
		query.append("SELECT * FROM ");
		query.append(GARAGEM);
		query.append(" WHERE ");
		query.append(ID_UNIDADE);		
		query.append(" = ?");				
		query.append(" ORDER BY ");
		query.append(NUMERO);
		query.append(";");
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;		
		Garagem garagem = null;		
		List<Garagem> listaGaragem = new ArrayList<Garagem>();
		try {
			preparedStatement = con.prepareStatement(query.toString());
			SQLUtil.setValorPpreparedStatement(preparedStatement, 1, idUnidade, java.sql.Types.INTEGER);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()){
				garagem = new Garagem();
				garagem.setId((Integer) SQLUtil.getValorResultSet(resultSet, ID, java.sql.Types.INTEGER));
				garagem.setNumero(String.valueOf(SQLUtil.getValorResultSet(resultSet, NUMERO, java.sql.Types.VARCHAR)));
				garagem.setTamanho((Integer) SQLUtil.getValorResultSet(resultSet, TAMANHO, java.sql.Types.INTEGER));
				garagem.setIdUnidade((Integer) SQLUtil.getValorResultSet(resultSet, ID_UNIDADE, java.sql.Types.INTEGER));								
				listaGaragem.add(garagem);
			}
		} catch (SQLException e) {	
			con.rollback();			
			throw e;			
		}catch (Exception e) {
			con.rollback();					
			throw e;		
		}	
		return listaGaragem;
	}

	@Override
	public Garagem buscarPorId(Integer idGaragem, Connection con)
			throws SQLException, Exception  {
		StringBuffer query = new StringBuffer();
		query.append("SELECT * FROM ");
		query.append(GARAGEM);
		query.append(" WHERE ");
		query.append(ID);		
		query.append(" = ?");
		query.append(";");
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;		
		Garagem garagem = new Garagem();
		try {
			preparedStatement = con.prepareStatement(query.toString());
			SQLUtil.setValorPpreparedStatement(preparedStatement, 1, idGaragem, java.sql.Types.INTEGER);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()){
				garagem = new Garagem();
				garagem.setId((Integer) SQLUtil.getValorResultSet(resultSet, ID, java.sql.Types.INTEGER));
				garagem.setNumero(String.valueOf(SQLUtil.getValorResultSet(resultSet, NUMERO, java.sql.Types.VARCHAR)));
				garagem.setTamanho((Integer) SQLUtil.getValorResultSet(resultSet, TAMANHO, java.sql.Types.INTEGER));
				garagem.setIdUnidade((Integer) SQLUtil.getValorResultSet(resultSet, ID_UNIDADE, java.sql.Types.INTEGER));
			}
		} catch (SQLException e) {	
			con.rollback();			
			throw e;			
		}catch (Exception e) {
			con.rollback();					
			throw e;		
		}
		return garagem;
	}

	@Override
	public List<Garagem> buscarPorIdCondominioENumero(Integer idCondominio, String numero) throws SQLException, Exception  {
		String pontoInterrogacao = "";		
		Condominio condominio = new Condominio();
		condominio.setId(idCondominio);
		List<Unidade> listaUnidade = new ArrayList<Unidade>();
		this.condominioDAO.get().popularCondominio(condominio);
		for (Bloco bloco : condominio.getListaBlocos()) {
			for (int i = 0; i < bloco.getListaUnidade().size(); i++) {
				listaUnidade.add(bloco.getListaUnidade().get(i));				
			}				
		}
		pontoInterrogacao = SQLUtil.popularInterrocacoes(listaUnidade.size());
		StringBuffer query = new StringBuffer();
		query.append("SELECT * FROM ");
		query.append(GARAGEM);
		query.append(" WHERE ");
		query.append(ID_UNIDADE);		
		query.append(" in (");
		query.append(pontoInterrogacao);
		query.append(") ");
		query.append("AND UPPER(");
		query.append(NUMERO);		
		query.append(") LIKE ?");		
		query.append(";");
		Connection con = Conexao.getConexao();
		PreparedStatement preparedStatement = null;		
		Garagem garagem = null;		
		Integer contador = 1;
		List<Garagem> listaGaragem = new ArrayList<Garagem>();
		try {
			preparedStatement = con.prepareStatement(query.toString());
			for (Unidade unidade : listaUnidade) {
				SQLUtil.setValorPpreparedStatement(preparedStatement, contador++, unidade.getId(), java.sql.Types.INTEGER);				
			}
			SQLUtil.setValorPpreparedStatement(preparedStatement, contador, numero.trim() != "" ? "%"+numero.trim().toUpperCase()+"%" : null, java.sql.Types.VARCHAR);
			ResultSet resultSet = preparedStatement.executeQuery();
			while(resultSet.next()){				
				garagem = new Garagem();				
				garagem.setId((Integer) SQLUtil.getValorResultSet(resultSet, ID, java.sql.Types.INTEGER));
				garagem.setNumero(String.valueOf(SQLUtil.getValorResultSet(resultSet, NUMERO, java.sql.Types.VARCHAR)));
				garagem.setIdUnidade((Integer) SQLUtil.getValorResultSet(resultSet, ID_UNIDADE, java.sql.Types.INTEGER));				
				garagem.setTamanho((Integer) SQLUtil.getValorResultSet(resultSet, TAMANHO, java.sql.Types.INTEGER));											
				listaGaragem.add(garagem);
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
		return listaGaragem;
	}

}
