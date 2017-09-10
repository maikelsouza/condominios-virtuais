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

import br.com.condominiosvirtuais.cdi.qualifier.QualifierFuncionarioDAO;
import br.com.condominiosvirtuais.entity.Bloco;
import br.com.condominiosvirtuais.entity.BlocoConjuntoBloco;
import br.com.condominiosvirtuais.entity.Condominio;
import br.com.condominiosvirtuais.entity.ConjuntoBloco;
import br.com.condominiosvirtuais.entity.Funcionario;
import br.com.condominiosvirtuais.entity.TipoConjuntoBloco;
import br.com.condominiosvirtuais.exception.BusinessException;
import br.com.condominiosvirtuais.persistence.ArquivoDAO;
import br.com.condominiosvirtuais.persistence.BlocoConjuntoBlocoDAO;
import br.com.condominiosvirtuais.persistence.ConjuntoBlocoDAO;
import br.com.condominiosvirtuais.persistence.FuncionarioDAO;
import br.com.condominiosvirtuais.util.SQLUtil;

@QualifierFuncionarioDAO
public class FuncionarioDAOImpl implements FuncionarioDAO, Serializable {
	
	private static final long serialVersionUID = 1L;

	private static Logger logger = Logger.getLogger(FuncionarioDAOImpl.class);
	
	private static final String FUNCIONARIO = "FUNCIONARIO";
	
	private static final String ID = "ID";
	
	private static final String FUNCAO = "FUNCAO";
	
	private static final String TELEFONE_CELULAR = "TELEFONE_CELULAR";
	
	private static final String TELEFONE_RESIDENCIAL = "TELEFONE_RESIDENCIAL";	
	
	private static final String ID_CONDOMINIO = "ID_CONDOMINIO";
	
	private static final String ID_CONJUNTO_BLOCO = "ID_CONJUNTO_BLOCO";	
	
	@Inject
	private UsuarioDAOImpl usuarioDAO;
	
	@Inject
	private Instance<ArquivoDAO> arquivoDAO = null;
		
	@Inject
	private BlocoConjuntoBlocoDAO blocoConjuntoBlocoDAO;
	
	@Inject @QualifierFuncionarioDAO
	private Instance<ConjuntoBlocoDAO> conjuntoBlocoDAO = null;	
	
	
	public void salvar(TipoConjuntoBloco tipoConjuntoBloco) throws SQLException, BusinessException, Exception {
		PreparedStatement statement = null;
		Connection con = null;		
		try {
			con = Conexao.getConexao();
			con.setAutoCommit(false);
			Funcionario funcionario = (Funcionario) tipoConjuntoBloco;
			this.usuarioDAO.salvarUsuario(funcionario, con);
			StringBuffer query = new StringBuffer();
			query.append("INSERT INTO "); 
			query.append(FUNCIONARIO); 
			query.append("(");
			query.append(ID); 
			query.append(",");
			query.append(FUNCAO); 
			query.append(",");
			query.append(TELEFONE_CELULAR); 
			query.append(",");
			query.append(TELEFONE_RESIDENCIAL); 
			query.append(",");
			query.append(ID_CONDOMINIO);
			query.append(",");	
			query.append(ID_CONJUNTO_BLOCO);
			query.append(") ");
			query.append("VALUES(?,?,?,?,?,?)");
			statement = con.prepareStatement(query.toString());
			SQLUtil.setValorPpreparedStatement(statement, 1, funcionario.getId(), java.sql.Types.INTEGER);
			SQLUtil.setValorPpreparedStatement(statement, 2, funcionario.getFuncao(), java.sql.Types.VARCHAR);
			SQLUtil.setValorPpreparedStatement(statement, 3, funcionario.getTelefoneCelular(), java.sql.Types.BIGINT);
			SQLUtil.setValorPpreparedStatement(statement, 4, funcionario.getTelefoneResidencial(), java.sql.Types.BIGINT);			
			SQLUtil.setValorPpreparedStatement(statement, 5, funcionario.getIdCondominio(), java.sql.Types.INTEGER);
			SQLUtil.setValorPpreparedStatement(statement, 6, funcionario.getIdConjuntoBloco(), java.sql.Types.INTEGER);
			statement.execute();
			con.commit();	
		} catch (SQLException e) {
			throw e;
		} catch (BusinessException e) {
			throw e;
		} catch (Exception e) {		
			throw e;			
		}finally{
			try {				
				con.close();	
				statement.close();
			} catch (SQLException e) {
				logger.error("erro sqlstate "+e.getSQLState(), e);		
			}
		}	
	}
	
	/**
	 * Método que salva um Funcionário considerando que esse funcionário faz parte de um conjunto de bloco e não de um condomínio.
	 * @throws Exception 
	 */
	public void salvar(TipoConjuntoBloco tipoConjuntoBloco, Connection con) throws SQLException, Exception {
		Funcionario funcionario = (Funcionario) tipoConjuntoBloco;
		this.usuarioDAO.salvarUsuario(funcionario, con);
		StringBuffer query = new StringBuffer();
		query.append("INSERT INTO "); 
		query.append(FUNCIONARIO);
		query.append("(");
		query.append(ID);
		query.append(",");
		query.append(FUNCAO);
		query.append(",");
		query.append(TELEFONE_CELULAR);
		query.append(",");
		query.append(TELEFONE_RESIDENCIAL);
		query.append(",");		
		query.append(ID_CONJUNTO_BLOCO);
		query.append(",");		
		query.append(ID_CONDOMINIO);
		query.append(") ");
		query.append("VALUES(?,?,?,?,?,?)");		
		PreparedStatement statement = null;				
		try {			
			statement = con.prepareStatement(query.toString());
			SQLUtil.setValorPpreparedStatement(statement, 1, funcionario.getId(), java.sql.Types.INTEGER);
			SQLUtil.setValorPpreparedStatement(statement, 2, funcionario.getFuncao(), java.sql.Types.VARCHAR);
			SQLUtil.setValorPpreparedStatement(statement, 3, funcionario.getTelefoneCelular(), java.sql.Types.BIGINT);
			SQLUtil.setValorPpreparedStatement(statement, 4, funcionario.getTelefoneResidencial(), java.sql.Types.BIGINT);
			SQLUtil.setValorPpreparedStatement(statement, 5, funcionario.getIdConjuntoBloco(), java.sql.Types.INTEGER);
			SQLUtil.setValorPpreparedStatement(statement, 6, funcionario.getIdCondominio(), java.sql.Types.INTEGER);
			statement.executeUpdate();
		} catch (SQLException e) {
			con.rollback();		
			throw e;
		} catch (Exception e) {
			con.rollback();		
			throw e;	
		}	
	}

	public List<Funcionario> buscarPorCondominioENomeFuncionario(Condominio condominio, String nomeFuncionario) throws SQLException, Exception {
		StringBuffer query = new StringBuffer();
		query.append("SELECT * FROM ");
		query.append(FUNCIONARIO);
		query.append(" WHERE ");
		query.append(ID_CONDOMINIO);		
		query.append(" = ?");
		query.append(";");		
		Connection con = Conexao.getConexao();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;		
		Funcionario funcionario = null;
		Boolean populouFuncionario = Boolean.FALSE;
		List<Funcionario> listaFuncionarios = new ArrayList<Funcionario>();
		try {
			preparedStatement = con.prepareStatement(query.toString());
			preparedStatement.setInt(1, condominio.getId());
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()){
				funcionario = new Funcionario();				
				funcionario.setId((Integer) SQLUtil.getValorResultSet(resultSet, ID, java.sql.Types.INTEGER));
				funcionario.setFuncao(String.valueOf(SQLUtil.getValorResultSet(resultSet, FUNCAO, java.sql.Types.VARCHAR)));
				funcionario.setTelefoneCelular((Long) SQLUtil.getValorResultSet(resultSet, TELEFONE_CELULAR, java.sql.Types.BIGINT));
				funcionario.setTelefoneResidencial((Long) SQLUtil.getValorResultSet(resultSet, TELEFONE_RESIDENCIAL, java.sql.Types.BIGINT));
				funcionario.setIdCondominio((Integer) SQLUtil.getValorResultSet(resultSet, ID_CONDOMINIO, java.sql.Types.INTEGER));
				funcionario.setIdConjuntoBloco((Integer) SQLUtil.getValorResultSet(resultSet, ID_CONJUNTO_BLOCO, java.sql.Types.INTEGER));
				funcionario.setImagem(this.arquivoDAO.get().buscarPorFuncionarioCondominio(funcionario, con));
				populouFuncionario = this.usuarioDAO.popularUsuarioPeloIdENome(funcionario, nomeFuncionario, con);
				// Condição que contempla o like referente ao nome do funcionário.
				if(populouFuncionario){
					listaFuncionarios.add(funcionario);					
				}
			}
		} catch (SQLException e) {
			throw e;
		} catch (Exception e) {		
			throw e;		
		}finally{
			try {
				preparedStatement.close();
				con.close();
				this.arquivoDAO.destroy(this.arquivoDAO.get());
			} catch (SQLException e) {
				logger.error("erro sqlstate "+e.getSQLState(), e);	
			}
		}
		return listaFuncionarios;		
	}
	
	public void excluir(Funcionario funcionario) throws SQLException, Exception {		
		StringBuffer query = new StringBuffer();
		Connection con = Conexao.getConexao();
		PreparedStatement statement = null;
		try {		
			con.setAutoCommit(false);
			if(funcionario.getImagem() != null){
				this.arquivoDAO.get().setCommit(Boolean.FALSE);
				if(funcionario.getIdCondominio() != null){
					this.arquivoDAO.get().excluirArquivoFuncionarioCondominio(funcionario.getImagem(), con);
				}else{
					this.arquivoDAO.get().excluirArquivoFuncionarioConjuntoBloco(funcionario.getImagem(), con);
				}
			}			
			query.append("DELETE FROM ");
			query.append(FUNCIONARIO);
			query.append(" WHERE ");		
			query.append(ID);
			query.append(" = ?");
			statement = con.prepareStatement(query.toString());
			statement.setInt(1, funcionario.getId());
			statement.executeUpdate();
			this.usuarioDAO.excluirUsuario(funcionario, con);
			con.commit();
			this.arquivoDAO.get().executeListaFile(ArquivoDAO.DELETE);
		}catch (SQLException e) {
			this.arquivoDAO.get().rollback();				
			throw e;
		} catch (Exception e) {
			this.arquivoDAO.get().rollback();		
			throw e;
		}finally{
			try {
				statement.close();
				con.close();		
				this.arquivoDAO.destroy(this.arquivoDAO.get());
			} catch (SQLException e) {
				logger.error("erro sqlstate "+e.getSQLState(), e);	
			}
		}		
	}
	
	public void atualizar(Funcionario funcionario) throws SQLException, Exception {
		StringBuffer query = new StringBuffer();
		query.append("UPDATE ");
		query.append(FUNCIONARIO);
		query.append(" SET ");
		query.append(FUNCAO);
		query.append("= ?, ");
		query.append(TELEFONE_CELULAR);
		query.append("= ?, ");
		query.append(TELEFONE_RESIDENCIAL);
		query.append("= ? ");
		if(funcionario.getIdCondominio() != null){
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
			con.setAutoCommit(false);
			statement = con.prepareStatement(query.toString());			
			SQLUtil.setValorPpreparedStatement(statement, 1, funcionario.getFuncao(), java.sql.Types.VARCHAR);
			SQLUtil.setValorPpreparedStatement(statement, 2, funcionario.getTelefoneCelular(), java.sql.Types.BIGINT);
			SQLUtil.setValorPpreparedStatement(statement, 3, funcionario.getTelefoneResidencial(), java.sql.Types.BIGINT);			
			if(funcionario.getIdCondominio() != null){
				SQLUtil.setValorPpreparedStatement(statement, 4, funcionario.getIdCondominio(), java.sql.Types.INTEGER);
				SQLUtil.setValorPpreparedStatement(statement, 5, funcionario.getId(), java.sql.Types.INTEGER);				
			}else{
				SQLUtil.setValorPpreparedStatement(statement, 4, funcionario.getId(), java.sql.Types.INTEGER);
			}
			statement.executeUpdate();
			this.usuarioDAO.atualizarUsuario(funcionario, con);
			con.commit();
		} catch (SQLException e) {		
			throw e;
		} catch (Exception e) {		
			throw e;	
		} finally {
			try {		
				statement.close();
				con.close();				
			} catch (SQLException e) {
				logger.error("erro sqlstate "+e.getSQLState(), e);		
			}
		}		
	}

	
	public List<Funcionario> buscarPorBlocoENomeFuncionario(Bloco bloco, String nomeFuncionario) throws SQLException, Exception {
		Connection con = null;			
		List<Funcionario> listaFuncionarios = null;
		try {				
			con = Conexao.getConexao();
			List<BlocoConjuntoBloco> listaBlocoConjuntoBloco = this.blocoConjuntoBlocoDAO.buscarPorIdBloco(bloco.getId(),con);
			//this.conjuntoBlocoDAO = new ConjuntoBlocoDAOImpl(this);		
			listaFuncionarios = new ArrayList<Funcionario>();		
			Iterator<BlocoConjuntoBloco> iteratorConjuntoBloco = listaBlocoConjuntoBloco.iterator();			
			while(iteratorConjuntoBloco.hasNext()){
				BlocoConjuntoBloco blocoConjuntoBloco = iteratorConjuntoBloco.next();
				ConjuntoBloco conjuntoBloco = this.getConjuntoBlocoDAO().buscarPorId(blocoConjuntoBloco.getConjuntoBloco().getId(),con);
				for (TipoConjuntoBloco tipoConjuntoBloco : conjuntoBloco.getListaTipoConjuntoBlocos()) {						
					Funcionario funcionario = (Funcionario) tipoConjuntoBloco;
					// Não existe o atributo nome na tabela CONJUNTO_BLOCO, logo o like é feito no fonte, onde temos os funcionários em memória. 
					if(funcionario.getNome().toUpperCase().startsWith(nomeFuncionario.toUpperCase())){						
						listaFuncionarios.add(funcionario);
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
		return listaFuncionarios;
	}


	public List<TipoConjuntoBloco> buscarPorIdConjuntoBloco(Integer idConjuntoBloco, Connection con) throws SQLException, Exception {		
		StringBuffer query = new StringBuffer();
		query.append("SELECT * FROM ");
		query.append(FUNCIONARIO);
		query.append(" WHERE ");
		query.append(ID_CONJUNTO_BLOCO);		
		query.append(" = ?");
		query.append(";");		
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;		
		Funcionario funcionario = null;		
		List<TipoConjuntoBloco> listaTipoConjuntoBloco = new ArrayList<TipoConjuntoBloco>();
		try {
			preparedStatement = con.prepareStatement(query.toString());
			preparedStatement.setInt(1, idConjuntoBloco);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()){				
				funcionario = new Funcionario();				
				funcionario.setId((Integer) SQLUtil.getValorResultSet(resultSet, ID, java.sql.Types.INTEGER));
				funcionario.setFuncao(String.valueOf(SQLUtil.getValorResultSet(resultSet, FUNCAO, java.sql.Types.VARCHAR)));
				funcionario.setTelefoneCelular((Long) SQLUtil.getValorResultSet(resultSet, TELEFONE_CELULAR, java.sql.Types.BIGINT));
				funcionario.setTelefoneResidencial((Long) SQLUtil.getValorResultSet(resultSet, TELEFONE_RESIDENCIAL, java.sql.Types.BIGINT));
				funcionario.setIdCondominio((Integer) SQLUtil.getValorResultSet(resultSet, ID_CONDOMINIO, java.sql.Types.INTEGER));
				funcionario.setIdConjuntoBloco((Integer) SQLUtil.getValorResultSet(resultSet, ID_CONJUNTO_BLOCO, java.sql.Types.INTEGER));
				funcionario.setImagem(this.arquivoDAO.get().buscarPorFuncionarioConjuntoBloco(funcionario, con));
				this.usuarioDAO.buscarEPopularUsuarioPeloId(funcionario, con);	
				listaTipoConjuntoBloco.add(funcionario);
			}
		} catch (SQLException e) {
			con.rollback();		
			throw e;
		} catch (Exception e) {
			con.rollback();		
			throw e;
		}finally{
			this.arquivoDAO.destroy(this.arquivoDAO.get());			
		}		
		return listaTipoConjuntoBloco;		
	}
	
	public List<Funcionario> buscarPorIdConjuntoBloco(Integer idConjuntoBloco) throws SQLException, Exception{
		StringBuffer query = new StringBuffer();
		query.append("SELECT * FROM ");
		query.append(FUNCIONARIO);
		query.append(" WHERE ");
		query.append(ID_CONJUNTO_BLOCO);		
		query.append(" = ?");
		query.append(";");		
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;		
		Funcionario funcionario = null;		
		List<Funcionario> listaFuncionario = new ArrayList<Funcionario>();
		Connection con = Conexao.getConexao();
		try {
			preparedStatement = con.prepareStatement(query.toString());
			preparedStatement.setInt(1, idConjuntoBloco);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()){				
				funcionario = new Funcionario();				
				funcionario.setId((Integer) SQLUtil.getValorResultSet(resultSet, ID, java.sql.Types.INTEGER));
				funcionario.setFuncao(String.valueOf(SQLUtil.getValorResultSet(resultSet, FUNCAO, java.sql.Types.VARCHAR)));
				funcionario.setTelefoneCelular((Long) SQLUtil.getValorResultSet(resultSet, TELEFONE_CELULAR, java.sql.Types.BIGINT));
				funcionario.setTelefoneResidencial((Long) SQLUtil.getValorResultSet(resultSet, TELEFONE_RESIDENCIAL, java.sql.Types.BIGINT));
				funcionario.setIdCondominio((Integer) SQLUtil.getValorResultSet(resultSet, ID_CONDOMINIO, java.sql.Types.INTEGER));
				funcionario.setIdConjuntoBloco((Integer) SQLUtil.getValorResultSet(resultSet, ID_CONJUNTO_BLOCO, java.sql.Types.INTEGER));
				this.usuarioDAO.buscarEPopularUsuarioPeloId(funcionario, con);	
				listaFuncionario.add(funcionario);
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
		return listaFuncionario;		
	}

	public void atualizar(TipoConjuntoBloco tipoConjuntoBloco, Connection con) throws SQLException, Exception {
		Funcionario funcionario = (Funcionario) tipoConjuntoBloco;
		StringBuffer query = new StringBuffer();
		query.append("UPDATE ");
		query.append(FUNCIONARIO);
		query.append(" SET ");
		query.append(FUNCAO);
		query.append("= ?, ");
		query.append(TELEFONE_CELULAR);
		query.append("= ?, ");
		query.append(TELEFONE_RESIDENCIAL);
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
			SQLUtil.setValorPpreparedStatement(statement, 1, funcionario.getFuncao(), java.sql.Types.VARCHAR);
			SQLUtil.setValorPpreparedStatement(statement, 2, funcionario.getTelefoneCelular(), java.sql.Types.BIGINT);
			SQLUtil.setValorPpreparedStatement(statement, 3, funcionario.getTelefoneResidencial(), java.sql.Types.BIGINT);
			SQLUtil.setValorPpreparedStatement(statement, 4, funcionario.getIdCondominio(), java.sql.Types.INTEGER);
			SQLUtil.setValorPpreparedStatement(statement, 5, funcionario.getIdConjuntoBloco(), java.sql.Types.INTEGER);
			SQLUtil.setValorPpreparedStatement(statement, 6, funcionario.getId(), java.sql.Types.INTEGER);			
			statement.executeUpdate();
			this.usuarioDAO.atualizarUsuario(funcionario, con);			
		}  catch (SQLException e) {	
			con.rollback();		
			throw e;
		} catch (Exception e) {
			con.rollback();		
			throw e;
		}	
	}

	
	public void excluir(TipoConjuntoBloco tipoConjuntoBloco, Connection con) throws SQLException, Exception {
		Funcionario funcionario = (Funcionario) tipoConjuntoBloco;		
		StringBuffer query = new StringBuffer();		
		PreparedStatement statement = null;
		try {
			if(funcionario.getImagem() != null){
				this.arquivoDAO.get().excluirArquivoFuncionarioConjuntoBloco(funcionario.getImagem(), con);				
			}
			query.append("DELETE FROM ");
			query.append(FUNCIONARIO);
			query.append(" WHERE ");		
			query.append(ID);
			query.append(" = ?");
			statement = con.prepareStatement(query.toString());
			statement.setInt(1, funcionario.getId());
			statement.executeUpdate();
			this.usuarioDAO.excluirUsuario(funcionario, con);
		} catch (SQLException e) {	
			con.rollback();		
			throw e;
		} catch (Exception e) {
			con.rollback();
			throw e;		
		}finally{
			
		}
		
	}
	
	public Funcionario buscarPorId(Integer id) throws SQLException, Exception {		
		StringBuffer query = new StringBuffer();
		query.append("SELECT * FROM ");
		query.append(FUNCIONARIO);
		query.append(" WHERE ");
		query.append(ID);		
		query.append(" = ?");
		query.append(";");		
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		List<Funcionario> listaFuncionario = new ArrayList<Funcionario>();
		Connection con = Conexao.getConexao();
		Funcionario funcionario = null;
		try {
			preparedStatement = con.prepareStatement(query.toString());
			preparedStatement.setInt(1, id);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()){				
				funcionario = new Funcionario();				
				funcionario.setId((Integer) SQLUtil.getValorResultSet(resultSet, ID, java.sql.Types.INTEGER));
				funcionario.setFuncao(String.valueOf(SQLUtil.getValorResultSet(resultSet, FUNCAO, java.sql.Types.VARCHAR)));
				funcionario.setTelefoneCelular((Long) SQLUtil.getValorResultSet(resultSet, TELEFONE_CELULAR, java.sql.Types.BIGINT));
				funcionario.setTelefoneResidencial((Long) SQLUtil.getValorResultSet(resultSet, TELEFONE_RESIDENCIAL, java.sql.Types.BIGINT));
				funcionario.setIdCondominio((Integer) SQLUtil.getValorResultSet(resultSet, ID_CONDOMINIO, java.sql.Types.INTEGER));
				funcionario.setIdConjuntoBloco((Integer) SQLUtil.getValorResultSet(resultSet, ID_CONJUNTO_BLOCO, java.sql.Types.INTEGER));
				this.usuarioDAO.buscarEPopularUsuarioPeloId(funcionario, con);	
				listaFuncionario.add(funcionario);
				if(funcionario.getIdCondominio() != null){
					funcionario.setImagem(this.arquivoDAO.get().buscarPorFuncionarioCondominio(funcionario, con));					
				}else{
					funcionario.setImagem(this.arquivoDAO.get().buscarPorFuncionarioConjuntoBloco(funcionario, con));
				}
			}
		} catch (SQLException e) {	
			con.rollback();		
			throw e;
		} catch (Exception e) {
			con.rollback();
			throw e;			
		}finally{
			try {
				preparedStatement.close();
				con.close();
				this.arquivoDAO.destroy(this.arquivoDAO.get());
			} catch (SQLException e) {
				logger.error("erro sqlstate "+e.getSQLState(), e);	
			}
		}
		return funcionario;	
	}
	
	public Funcionario buscarPorId(Integer id, 	Connection con ) throws SQLException, Exception {
		StringBuffer query = new StringBuffer();
		query.append("SELECT * FROM ");
		query.append(FUNCIONARIO);
		query.append(" WHERE ");
		query.append(ID);		
		query.append(" = ?");
		query.append(";");		
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		List<Funcionario> listaFuncionario = new ArrayList<Funcionario>();		
		Funcionario funcionario = null;
		try {
			preparedStatement = con.prepareStatement(query.toString());
			preparedStatement.setInt(1, id);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()){				
				funcionario = new Funcionario();				
				funcionario.setId((Integer) SQLUtil.getValorResultSet(resultSet, ID, java.sql.Types.INTEGER));
				funcionario.setFuncao(String.valueOf(SQLUtil.getValorResultSet(resultSet, FUNCAO, java.sql.Types.VARCHAR)));
				funcionario.setTelefoneCelular((Long) SQLUtil.getValorResultSet(resultSet, TELEFONE_CELULAR, java.sql.Types.BIGINT));
				funcionario.setTelefoneResidencial((Long) SQLUtil.getValorResultSet(resultSet, TELEFONE_RESIDENCIAL, java.sql.Types.BIGINT));
				funcionario.setIdCondominio((Integer) SQLUtil.getValorResultSet(resultSet, ID_CONDOMINIO, java.sql.Types.INTEGER));
				funcionario.setIdConjuntoBloco((Integer) SQLUtil.getValorResultSet(resultSet, ID_CONJUNTO_BLOCO, java.sql.Types.INTEGER));
				this.usuarioDAO.buscarEPopularUsuarioPeloId(funcionario, con);	
				listaFuncionario.add(funcionario);
			}
		} catch (SQLException e) {	
			con.rollback();
			throw e;
		} catch (Exception e) {
			con.rollback();
			throw e;			
		}	
		return funcionario;	
	}
	
	@Override
	public List<Funcionario> buscarPorCondominioESituacaoSemImagem(Integer idCondominio, Integer situacao) throws SQLException, Exception {
		StringBuffer query = new StringBuffer();
		query.append("SELECT * FROM ");
		query.append(FUNCIONARIO);
		query.append(" WHERE ");
		query.append(ID_CONDOMINIO);		
		query.append(" = ?");
		query.append(";");		
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;		
		Funcionario funcionario = null;		
		List<Funcionario> listaFuncionario = new ArrayList<Funcionario>();
		Connection con = Conexao.getConexao();
		try {
			preparedStatement = con.prepareStatement(query.toString());
			SQLUtil.setValorPpreparedStatement(preparedStatement, 1, idCondominio, java.sql.Types.INTEGER);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()){				
				funcionario = new Funcionario();				
				funcionario.setId((Integer) SQLUtil.getValorResultSet(resultSet, ID, java.sql.Types.INTEGER));
				funcionario.setFuncao(String.valueOf(SQLUtil.getValorResultSet(resultSet, FUNCAO, java.sql.Types.VARCHAR)));
				funcionario.setTelefoneCelular((Long) SQLUtil.getValorResultSet(resultSet, TELEFONE_CELULAR, java.sql.Types.BIGINT));
				funcionario.setTelefoneResidencial((Long) SQLUtil.getValorResultSet(resultSet, TELEFONE_RESIDENCIAL, java.sql.Types.BIGINT));
				funcionario.setIdCondominio((Integer) SQLUtil.getValorResultSet(resultSet, ID_CONDOMINIO, java.sql.Types.INTEGER));
				funcionario.setIdConjuntoBloco((Integer) SQLUtil.getValorResultSet(resultSet, ID_CONJUNTO_BLOCO, java.sql.Types.INTEGER));
				// Caso não tenha econtrado, ou seja, o funcionário está inativo por exemplo, então não add na lista.
				if(this.usuarioDAO.buscarEPopularUsuarioPeloId(funcionario,situacao,con)){
					listaFuncionario.add(funcionario);
				}
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
		return listaFuncionario;		
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

	public BlocoConjuntoBlocoDAO getBlocoConjuntoBlocoDAO() {
		return blocoConjuntoBlocoDAO;
	}

	public void setBlocoConjuntoBlocoDAO(BlocoConjuntoBlocoDAO blocoConjuntoBlocoDAO) {
		this.blocoConjuntoBlocoDAO = blocoConjuntoBlocoDAO;
	}

	@Override
	public void atualizarSenha(Funcionario funcionario) throws SQLException, Exception {
		this.usuarioDAO.atualizarSenha(funcionario);
	}	


}
