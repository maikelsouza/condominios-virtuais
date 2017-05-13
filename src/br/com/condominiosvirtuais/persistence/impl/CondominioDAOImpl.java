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
import br.com.condominiosvirtuais.entity.Condomino;
import br.com.condominiosvirtuais.entity.GestorCondominio;
import br.com.condominiosvirtuais.entity.SindicoProfissional;
import br.com.condominiosvirtuais.entity.Unidade;
import br.com.condominiosvirtuais.entity.Usuario;
import br.com.condominiosvirtuais.entity.UsuarioCondominio;
import br.com.condominiosvirtuais.enumeration.CondominioSituacaoEnum;
import br.com.condominiosvirtuais.enumeration.CondominoPagadorEnum;
import br.com.condominiosvirtuais.enumeration.TipoGestorCondominioEnum;
import br.com.condominiosvirtuais.exception.BusinessException;
import br.com.condominiosvirtuais.persistence.ArquivoDAO;
import br.com.condominiosvirtuais.persistence.CondominioDAO;
import br.com.condominiosvirtuais.persistence.SindicoProfissionalDAO;
import br.com.condominiosvirtuais.persistence.UsuarioCondominioDAO;
import br.com.condominiosvirtuais.util.AplicacaoUtil;
import br.com.condominiosvirtuais.util.SQLUtil;

public class CondominioDAOImpl  implements CondominioDAO, Serializable{	
	
	private static final long serialVersionUID = 1L;

	private static Logger logger = Logger.getLogger(CondominioDAOImpl.class);
		
	private static final String CONDOMINIO = "CONDOMINIO";
	
	private static final String ID = "ID";
	
	private static final String NOME = "NOME";
	
	private static final String SITUACAO = "SITUACAO";
	
	private static final String CNPJ = "CNPJ";
	
	private static final String TELEFONE_CELULAR = "TELEFONE_CELULAR";
	
	private static final String TELEFONE_FIXO = "TELEFONE_FIXO";
	
	private static final String CODIGO = "CODIGO";
	
	private static final String ID_ESCRITORIO_CONTABILIDADE = "ID_ESCRITORIO_CONTABILIDADE"; 
	
	// Constraint referente a integridade do condom�nio e bloco
	private static final String FK_BLOCO_ID_CONDOMINIO_CONDOMINIO_ID = "FK_BLOCO_ID_CONDOMINIO_CONDOMINIO_ID";
	
	// Constraint referente a integridade do condom�nio e ambiente
	private static final String FK_AMBIENTE_ID_CONDOMINIO_CONDOMINIO_ID = "FK_AMBIENTE_ID_CONDOMINIO_CONDOMINIO_ID";
	
	// Constraint referente a integridade do c�digo do condom�nio, ou seja, n�o � poss�vel ter mais de um condom�nio com o mesmo c�digo. 
	private static final String UQ_CODIGO = "UQ_CODIGO";
	
	@Inject
	private EnderecoDAOImpl enderecoDAO = null;
	
	@Inject
	private Instance<BlocoDAOImpl> blocoDAO = null;
	
	@Inject
	private Instance<UnidadeDAOImpl> unidadeDAO = null;
	
	@Inject
	private Instance<CondominoDAOImpl> condominoDAO = null;
	
	@Inject
	private Instance<GestorCondominioDAOImpl> gestorCondominioDAO = null;
	
	@Inject
	private Instance<UsuarioCondominioDAO> usuarioCondominioDAO = null;	
	
	@Inject
	private Instance<EmailUsuarioDAOImpl> emailUsuarioDAO = null;
	
	@Inject
	private Instance<ArquivoDAO>  arquivoDAO = null;
	
	@Inject
	private Instance<UsuarioDAOImpl>  usuarioDAO = null;
	
	@Inject
	private SindicoProfissionalDAO sindicoProfissionalDAO;
	
	
		
	
	/**
	 * M�todo que busca uma lista de condom�nios onde o usu�rio autenticado est� associado. 
	 * @return ListaCondominio
	 * @throws Exception 
	 */
	public List<Condominio> buscarListaCondominios() throws SQLException, Exception{
		List<Condominio> listaCondominio = new ArrayList<Condominio>();
		List<UsuarioCondominio> listaUsuarioCondominio = this.usuarioCondominioDAO.get().buscarListaPorUsuario(AplicacaoUtil.getUsuarioAutenticado());  
		if(!listaUsuarioCondominio.isEmpty()){			
			StringBuffer query = new StringBuffer();
			query.append("SELECT * FROM ");
			query.append(CONDOMINIO);
			query.append(" WHERE ");
			query.append(ID);		
			query.append(" in (");
			for (int i = 1; i <= listaUsuarioCondominio.size(); i++) {
				if(i != listaUsuarioCondominio.size()){
					query.append("?,");				
				}
			}		
			query.append("?)");
			query.append(" ORDER BY ");
			query.append(NOME);
			query.append(";");		
			Connection con = Conexao.getConexao();
			PreparedStatement preparedStatement = null;
			ResultSet resultSet = null;
			Condominio condominio = null;
			try {
				Integer quantidadeInterrogacao = 0;
				preparedStatement = con.prepareStatement(query.toString());
				Iterator<UsuarioCondominio>  iteratorListaUsuarioCondominio =  listaUsuarioCondominio.iterator();
				while(iteratorListaUsuarioCondominio.hasNext()){
					UsuarioCondominio usuarioCondominio = iteratorListaUsuarioCondominio.next(); 
					SQLUtil.setValorPpreparedStatement(preparedStatement, ++quantidadeInterrogacao, usuarioCondominio.getIdCondominio(), java.sql.Types.INTEGER);				
				}			
				resultSet = preparedStatement.executeQuery();				
				while(resultSet.next()){
					condominio = new Condominio();
					condominio.setId((Integer) SQLUtil.getValorResultSet(resultSet, ID, java.sql.Types.INTEGER));
					condominio.setNome(String.valueOf(SQLUtil.getValorResultSet(resultSet, NOME, java.sql.Types.VARCHAR)));
					condominio.setSituacao((Integer) SQLUtil.getValorResultSet(resultSet, SITUACAO, java.sql.Types.INTEGER));
					condominio.setCnpj((Long) SQLUtil.getValorResultSet(resultSet, CNPJ, java.sql.Types.BIGINT));
					condominio.setTelefoneCelular((Long) SQLUtil.getValorResultSet(resultSet, TELEFONE_CELULAR, java.sql.Types.BIGINT));
					condominio.setTelefoneFixo((Long) SQLUtil.getValorResultSet(resultSet, TELEFONE_FIXO, java.sql.Types.BIGINT));
					listaCondominio.add(condominio);
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
		}
		return listaCondominio;
	}
	
	/**
	 * M�todo que pesquisa todos os condom�nios, considerando o nome e situa��o {@link CondominioSituacaoEnum}. Esse m�todo contempla somente os condom�nios, <br>
	 * que o usu�rio autenticado tem acesso.  
	 * @param condominio - Parametro que cont�m: situa��o (obrigat�rio) e nome
	 * @return Lista de Condom�nios
	 *  
	 * @author Maikel Joel de Souza
	 * @throws Exception 
	 * @since 25/12/2012
	 */
	public List<Condominio> buscarListaCondominiosPorNomeESituacao(Condominio condominio) throws SQLException, Exception{		
		List<UsuarioCondominio> listaUsuarioCondominio = this.usuarioCondominioDAO.get().buscarListaPorUsuario(AplicacaoUtil.getUsuarioAutenticado());		
		StringBuffer query = new StringBuffer();
		query.append("SELECT * FROM ");
		query.append(CONDOMINIO);		
		// Se atende esse if considera o caso onde todos os par�metros s�o preenchidos
		if(!listaUsuarioCondominio.isEmpty()){
			query.append(" WHERE ");
			query.append(ID);		
			query.append(" in (");
			for (int i = 1; i <= listaUsuarioCondominio.size(); i++) {
				if(i != listaUsuarioCondominio.size()){
					query.append("?,");
				}
			}		
			query.append("?)");			
			// Caso onde foi considerado ambas as situa��es(ativa e inativa), logo n�o precisa de condi��o 'SITUACAO'  
			if(condominio.getSituacao() != 2 ){
				query.append(" AND ");	
				query.append(SITUACAO);			
				query.append(" =  ");	
				query.append(condominio.getSituacao().toString());
				// Caso onde foi considerado o nome do condom�nio
				if(condominio.getNome() != null && !condominio.getNome().isEmpty()){
					query.append(" AND UPPER(");	
					query.append(NOME);
					query.append(")");
					query.append(" LIKE '");
					query.append(condominio.getNome().trim().toUpperCase());
					query.append("%'");
				}
			}else{
				if(condominio.getNome() != null && !condominio.getNome().isEmpty()){
					query.append(" AND UPPER(");	 
					query.append(NOME);
					query.append(")");
					query.append(" LIKE '");
					query.append(condominio.getNome().trim().toUpperCase());
					query.append("%'");
				}
			}
		}else{
			// Caso onde foi considerado ambas as situa��es(ativa e inativa), logo n�o precisa de condi��o 'SITUACAO'  
			if(condominio.getSituacao() != 2 ){	
				query.append(" WHERE ");
				query.append(SITUACAO);			
				query.append(" =  ");	
				query.append(condominio.getSituacao().toString());
				// Caso onde foi considerado o nome do condom�nio
				if(condominio.getNome() != null && !condominio.getNome().isEmpty()){
					query.append(" AND UPPER(");	
					query.append(NOME);
					query.append(")");
					query.append(" LIKE '");
					query.append(condominio.getNome().trim().toUpperCase());
					query.append("%'");
				}
			}else{
				if(condominio.getNome() != null && !condominio.getNome().isEmpty()){
					query.append(" WHERE ");
					query.append(" UPPER(");	
					query.append(NOME);
					query.append(")");
					query.append(" LIKE '");
					query.append(condominio.getNome().trim().toUpperCase());
					query.append("%'");
				}
			}
		}
		query.append(" ORDER BY ");
		query.append(NOME);
		query.append(";");		
		Connection con = Conexao.getConexao();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		List<Condominio> listaCondominio = new ArrayList<Condominio>();
		Condomino subSindicoGeral = null;
		Usuario sindicoGeral = null;
		try {
			Integer quantidadeInterrogacao = 0;
			preparedStatement = con.prepareStatement(query.toString());
			Iterator<UsuarioCondominio>  iteratorListaUsuarioCondominio =  listaUsuarioCondominio.iterator();
			while(iteratorListaUsuarioCondominio.hasNext()){
				UsuarioCondominio usuarioCondominio = iteratorListaUsuarioCondominio.next();
				SQLUtil.setValorPpreparedStatement(preparedStatement, ++quantidadeInterrogacao, usuarioCondominio.getIdCondominio(), java.sql.Types.INTEGER);				
			}
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()){
				condominio = new Condominio();
				condominio.setId((Integer) SQLUtil.getValorResultSet(resultSet, ID, java.sql.Types.INTEGER));
				condominio.setNome(String.valueOf(SQLUtil.getValorResultSet(resultSet, NOME, java.sql.Types.VARCHAR)));
				condominio.setSituacao((Integer) SQLUtil.getValorResultSet(resultSet, SITUACAO, java.sql.Types.INTEGER));
				condominio.setCnpj((Long) SQLUtil.getValorResultSet(resultSet, CNPJ, java.sql.Types.BIGINT));
				condominio.setTelefoneCelular((Long) SQLUtil.getValorResultSet(resultSet, TELEFONE_CELULAR, java.sql.Types.BIGINT));
				condominio.setTelefoneFixo((Long) SQLUtil.getValorResultSet(resultSet, TELEFONE_FIXO, java.sql.Types.BIGINT));
				sindicoGeral = this.usuarioDAO.get().buscarSindicoGeralPorCondominio(condominio,con);
				subSindicoGeral = this.condominoDAO.get().buscarSubSindicoGeralPorCondominio(condominio,con);					
				condominio.setSindicoGeral(sindicoGeral);					
				condominio.setSubSindicoGeral(subSindicoGeral);
				listaCondominio.add(condominio);
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
		return listaCondominio;
	}
	
	public void salvarCondominio(Condominio condominio) throws  SQLException, Exception{
		StringBuffer query = new StringBuffer();
		query.append("INSERT INTO "); 
		query.append(CONDOMINIO);
		query.append("("); 
		query.append(NOME); 
		query.append(",");
		query.append(SITUACAO);
		query.append(",");
		query.append(CNPJ);
		query.append(",");
		query.append(TELEFONE_CELULAR);
		query.append(",");
		query.append(TELEFONE_FIXO);
		query.append(",");
		query.append(CODIGO);
		query.append(") ");
		query.append("VALUES(?,?,?,?,?,?)");
		Connection con = Conexao.getConexao();
		PreparedStatement statement = null;
		UsuarioCondominio usuarioCondominio = null;
		try {
			con.setAutoCommit(false);
			statement = con.prepareStatement(query.toString(),PreparedStatement.RETURN_GENERATED_KEYS);
			SQLUtil.setValorPpreparedStatement(statement, 1, condominio.getNome(), java.sql.Types.VARCHAR);
			SQLUtil.setValorPpreparedStatement(statement, 2, condominio.getSituacao(), java.sql.Types.INTEGER);
			SQLUtil.setValorPpreparedStatement(statement, 3, condominio.getCnpj(), java.sql.Types.BIGINT);
			SQLUtil.setValorPpreparedStatement(statement, 4, condominio.getTelefoneCelular(), java.sql.Types.BIGINT);
			SQLUtil.setValorPpreparedStatement(statement, 5, condominio.getTelefoneFixo(), java.sql.Types.BIGINT);
			SQLUtil.setValorPpreparedStatement(statement, 6, condominio.getCodigo(), java.sql.Types.INTEGER);
			statement.executeUpdate();
			ResultSet rs = statement.getGeneratedKeys();
			rs.next();
			condominio.getEndereco().setIdCondominio(rs.getInt(1));
			condominio.setId(rs.getInt(1));
			this.enderecoDAO.salvarEndereco(condominio.getEndereco(), con);		
			Usuario usuarioAutenticado =  AplicacaoUtil.getUsuarioAutenticado();
			usuarioCondominio = new UsuarioCondominio();
			usuarioCondominio.setIdCondominio(rs.getInt(1));
			usuarioCondominio.setIdUsuario(usuarioAutenticado.getId());
			this.usuarioCondominioDAO.get().salvarUsuarioCondominio(usuarioCondominio, con);
			
			this.arquivoDAO.get().criarDiretorioCondominio(condominio);
			con.commit();
			this.arquivoDAO.destroy(arquivoDAO.get());
		} catch (SQLException e) {	
			if (e.getMessage().contains(UQ_CODIGO))
				logger.error("erro sqlstate "+e.getSQLState(), e);	
			throw e;
		} catch (Exception e) {
			logger.error("exception"+e.getClass(), e);
			throw e;	
		}try {
			statement.close();
			con.close();				
		} catch (SQLException e) {
			logger.error("erro sqlstate "+e.getSQLState(), e);			
		}	
	}
	
	public void excluirCondominio(Condominio condominio) throws SQLException, BusinessException, Exception{
		StringBuffer query = new StringBuffer();
		Connection con = Conexao.getConexao();
		PreparedStatement statement = null;
		try {
			con.setAutoCommit(false);
			this.enderecoDAO.excluirEndereco(condominio.getEndereco(), con);
			query.append("DELETE FROM ");
			query.append(CONDOMINIO);
			query.append(" WHERE ");		
			query.append(ID);
			query.append(" = ?");
			statement = con.prepareStatement(query.toString());
			statement.setInt(1, condominio.getId());
			statement.executeUpdate();			
			this.arquivoDAO.get().excluirDiretorioCondominio(condominio);
			con.commit();
			this.arquivoDAO.destroy(this.arquivoDAO.get());
		} catch (SQLException e) {
			con.rollback();		
			if (e.getMessage().contains(FK_BLOCO_ID_CONDOMINIO_CONDOMINIO_ID)){
				throw new BusinessException("msg.condominio.excluirBlocoAssociado"); 
			}else if (e.getMessage().contains(FK_AMBIENTE_ID_CONDOMINIO_CONDOMINIO_ID)){
				throw new BusinessException("msg.condominio.excluirAmbienteAssociado"); 
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
	
	/**
	 * M�todo que atualiza um condom�nio, considerando os seus gestores (s�ndico, subs�ndico e conselheiros) tamb�m.	 * 
	 * @param condominio - Condom�nio para ser atualizado
	 * 
	 * @author Maikel Joel de Souza
	 * @throws Exception 
	 * @since 30/12/2012
	 */
	public void atualizarCondominio(Condominio condominio) throws SQLException, Exception{		
		StringBuffer query = new StringBuffer();
		query.append("UPDATE ");
		query.append(CONDOMINIO);
		query.append(" SET ");
		query.append(NOME);
		query.append("= ?, ");
		query.append(SITUACAO);
		query.append("= ?, "); 
		query.append(CNPJ);
		query.append("= ?, ");
		query.append(TELEFONE_CELULAR);
		query.append("= ?, ");
		query.append(TELEFONE_FIXO);
		query.append("= ? ");
		query.append("WHERE ");
		query.append(ID);
		query.append("= ?");
		Connection con = Conexao.getConexao();
		PreparedStatement statement = null;
		List<GestorCondominio> listaGestorCondominio = null;
		SindicoProfissional sindicoProfissional = null;
		List<UsuarioCondominio> listaUsuarioCondominio = null;
		Boolean ehGestorDoCondominio = Boolean.FALSE;
		try {
			con.setAutoCommit(Boolean.FALSE);
			statement = con.prepareStatement(query.toString());
			SQLUtil.setValorPpreparedStatement(statement, 1, condominio.getNome(), java.sql.Types.VARCHAR);
			SQLUtil.setValorPpreparedStatement(statement, 2, condominio.getSituacao(), java.sql.Types.INTEGER);
			SQLUtil.setValorPpreparedStatement(statement, 3, condominio.getCnpj(), java.sql.Types.BIGINT);
			SQLUtil.setValorPpreparedStatement(statement, 4, condominio.getTelefoneCelular(), java.sql.Types.BIGINT);
			SQLUtil.setValorPpreparedStatement(statement, 5, condominio.getTelefoneFixo(), java.sql.Types.BIGINT);
			SQLUtil.setValorPpreparedStatement(statement, 6, condominio.getId(), java.sql.Types.INTEGER);
			statement.executeUpdate();
			this.enderecoDAO.atualizarEndereco(condominio.getEndereco(), con);	
			this.gestorCondominioDAO.get().excluirGestorCondominioPorCondominio(condominio, con);
			this.salvarGestoresCondominio(condominio, con);
			listaUsuarioCondominio = this.usuarioCondominioDAO.get().buscarListaPorIdCondominio(condominio.getId(), con);			
			listaGestorCondominio = this.gestorCondominioDAO.get().buscarListaGestoresCondominioPorCondominio(condominio,con);
			// Verificar se tem algum usu�rio do condom�nio que � um s�ndico profissional. Num caso positivo, veja se ele ainda � um gestor desse condom�nio. 
			// Em caso negativo excluir esse usu�rio do condom�nio. Regra necess�ra para garantir que o s�ndico profissional n�o seja mais 
			// gestor e usu�rio desse condom�nio.
			for (UsuarioCondominio usuarioCondominio : listaUsuarioCondominio) {
				sindicoProfissional = this.sindicoProfissionalDAO.buscarPorId(usuarioCondominio.getIdUsuario(), con);
				if(sindicoProfissional != null){
					ehGestorDoCondominio = Boolean.FALSE;
					for (GestorCondominio gestorCondominio : listaGestorCondominio) {
						if(gestorCondominio.getIdUsuario().intValue() == sindicoProfissional.getId().intValue()){
							ehGestorDoCondominio = Boolean.TRUE;
						}						
					}
					// Caso o s�ndico profissional n�o seja um gestor do condom�nio, ent�o esse deve ser exclu�do do condom�nio
					if(ehGestorDoCondominio == Boolean.FALSE){
						this.usuarioCondominioDAO.get().excluirPorUsuario(sindicoProfissional, con);
					}
				}
			}
			con.commit();
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
	
	@Override
	public Condominio buscarCondominioPorId(Integer id) throws SQLException, Exception{
		StringBuffer query = new StringBuffer();
		query.append("SELECT * FROM ");
		query.append(CONDOMINIO);
		query.append(" WHERE ");
		query.append(ID);	
		query.append(" = ?");		
		Connection con = Conexao.getConexao();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Condominio condominio = null;
		Usuario sindicoGeral = null;
		try {
			preparedStatement = con.prepareStatement(query.toString());		
			preparedStatement.setInt(1, id);
			resultSet = preparedStatement.executeQuery();			
			while(resultSet.next()){
				condominio = new Condominio();
				condominio.setId((Integer) SQLUtil.getValorResultSet(resultSet, ID, java.sql.Types.INTEGER));
				condominio.setNome(String.valueOf(SQLUtil.getValorResultSet(resultSet, NOME, java.sql.Types.VARCHAR)));
				condominio.setSituacao((Integer) SQLUtil.getValorResultSet(resultSet, SITUACAO, java.sql.Types.INTEGER));
				condominio.setCnpj((Long) SQLUtil.getValorResultSet(resultSet, CNPJ, java.sql.Types.BIGINT));
				condominio.setTelefoneCelular((Long) SQLUtil.getValorResultSet(resultSet, TELEFONE_CELULAR, java.sql.Types.BIGINT));
				condominio.setTelefoneFixo((Long) SQLUtil.getValorResultSet(resultSet, TELEFONE_FIXO, java.sql.Types.BIGINT));
				sindicoGeral = this.usuarioDAO.get().buscarSindicoGeralPorCondominio(condominio,con);
				condominio.setSindicoGeral(sindicoGeral);					
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
		return condominio;
	}
	
	@Override
	public Condominio buscarCondominioPorId(Integer id, Connection con) throws SQLException, Exception{		
		StringBuffer query = new StringBuffer();
		query.append("SELECT * FROM ");
		query.append(CONDOMINIO);
		query.append(" WHERE ");
		query.append(ID);	
		query.append(" = ?");
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Condominio condominio = null;
		Usuario sindicoGeral = null;
		try {
			preparedStatement = con.prepareStatement(query.toString());
			SQLUtil.setValorPpreparedStatement(preparedStatement,1, id, java.sql.Types.INTEGER);
			resultSet = preparedStatement.executeQuery();	
			while(resultSet.next()){
				condominio = new Condominio();
				condominio.setId((Integer) SQLUtil.getValorResultSet(resultSet, ID, java.sql.Types.INTEGER));
				condominio.setNome(String.valueOf(SQLUtil.getValorResultSet(resultSet, NOME, java.sql.Types.VARCHAR)));
				condominio.setSituacao((Integer) SQLUtil.getValorResultSet(resultSet, SITUACAO, java.sql.Types.INTEGER));
				condominio.setCnpj((Long) SQLUtil.getValorResultSet(resultSet, CNPJ, java.sql.Types.BIGINT));
				condominio.setTelefoneCelular((Long) SQLUtil.getValorResultSet(resultSet, TELEFONE_CELULAR, java.sql.Types.BIGINT));
				condominio.setTelefoneFixo((Long) SQLUtil.getValorResultSet(resultSet, TELEFONE_FIXO, java.sql.Types.BIGINT));
				sindicoGeral = this.condominoDAO.get().buscarSindicoGeralPorCondominio(condominio,con);
				// Caso o sindico geral n�o seja um cond�mino.
				if(sindicoGeral == null){
					sindicoGeral = this.usuarioDAO.get().buscarSindicoGeralPorCondominio(condominio,con);
				}
				this.condominoDAO.get().buscarSindicoGeralPorCondominio(condominio,con);
				sindicoGeral.setEmail(this.emailUsuarioDAO.get().buscarEmailPrincipalPorUsuario(sindicoGeral,con));
				// Condi��o criada para garantir que ao atualizar o condom�nio o s�ndico geral n�o seja null 
				condominio.setSindicoGeral(sindicoGeral == null ? new Condomino() : sindicoGeral);
			}			
		} catch (SQLException e) {
			con.rollback();		
			throw e;
		} catch (Exception e) {
			con.rollback();		
			throw e;
		}
		return condominio;
	}
	
	public Condominio buscarPorIdESituacao(Integer id, Integer situacao, Connection con) throws SQLException, Exception{
		StringBuffer query = new StringBuffer();
		query.append("SELECT * FROM ");
		query.append(CONDOMINIO);
		query.append(" WHERE ");
		query.append(ID);	
		query.append(" = ?");
		query.append(" AND ");	
		query.append(SITUACAO);
		query.append(" = ?");
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Condominio condominio = null;
		try {
			preparedStatement = con.prepareStatement(query.toString());
			SQLUtil.setValorPpreparedStatement(preparedStatement,1, id, java.sql.Types.INTEGER);
			SQLUtil.setValorPpreparedStatement(preparedStatement,2, situacao, java.sql.Types.INTEGER);
			resultSet = preparedStatement.executeQuery();			
			while(resultSet.next()){				
				condominio = new Condominio();				
				condominio.setId((Integer) SQLUtil.getValorResultSet(resultSet, ID, java.sql.Types.INTEGER));
				condominio.setNome(String.valueOf(SQLUtil.getValorResultSet(resultSet, NOME, java.sql.Types.VARCHAR)));
				condominio.setSituacao((Integer) SQLUtil.getValorResultSet(resultSet, SITUACAO, java.sql.Types.INTEGER));
				condominio.setCnpj((Long) SQLUtil.getValorResultSet(resultSet, CNPJ, java.sql.Types.BIGINT));	
				condominio.setTelefoneCelular((Long) SQLUtil.getValorResultSet(resultSet, TELEFONE_CELULAR, java.sql.Types.BIGINT));
				condominio.setTelefoneFixo((Long) SQLUtil.getValorResultSet(resultSet, TELEFONE_FIXO, java.sql.Types.BIGINT));
			}			
		} catch (SQLException e) {
			con.rollback();			
			throw e;
		} catch (Exception e) {
			con.rollback();			
			throw e;
		}	
		return condominio;
	}
	
	public Condominio buscarPorCondomino(Condomino condomino) throws SQLException, Exception{
		Condominio condominio = null;
		Connection con = Conexao.getConexao();
		try {
			Unidade unidade = this.unidadeDAO.get().buscarPorId(condomino.getIdUnidade(), con);
			Bloco bloco = this.blocoDAO.get().buscarPorId(unidade.getIdBloco(), con);
			condominio = this.buscarCondominioPorId(bloco.getIdCondominio(), con);
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
		return condominio;
	}

	
	public void popularCondominioPorNomeCondominos(String nomeCondominos, Condominio condominio) throws SQLException, Exception{		
		List<Bloco> listaDeBlocos = null;
		List<Unidade> listaDeUnidades = null;
		List<Condomino> listaDeCondominos = null;
		listaDeBlocos = this.blocoDAO.get().buscarListaBlocosPorCondominioENome(condominio,null);		
		condominio.setListaBlocos(listaDeBlocos);
		for (Bloco bloco : listaDeBlocos) {
			listaDeUnidades = this.unidadeDAO.get().buscarListaUnidadesPorBloco(bloco);
			bloco.setListaUnidade(listaDeUnidades);
			for (Unidade unidade : listaDeUnidades) {
				listaDeCondominos = this.condominoDAO.get().buscarListaCondominosPeloNomeEPorUnidade(nomeCondominos, unidade);		
				unidade.setListaCondominos(listaDeCondominos);
			}
		}
	}
	
	
	public void popularBlocoEUnidadeDoCondominio(Condominio condominio) throws SQLException, Exception{		
		List<Bloco> listaDeBlocos = null;
		List<Unidade> listaDeUnidades = null;		
		listaDeBlocos = this.blocoDAO.get().buscarListaBlocosPorCondominioENome(condominio,null);		
		condominio.setListaBlocos(listaDeBlocos);
		for (Bloco bloco : listaDeBlocos) {
			listaDeUnidades = this.unidadeDAO.get().buscarListaUnidadesPorBloco(bloco);
			bloco.setListaUnidade(listaDeUnidades);			
		}
	}
	
	@Override
	public Boolean existeCondominioPorCodigo(Integer codigo) throws SQLException, Exception {
		Boolean existeCondominio = Boolean.FALSE;
		StringBuffer query = new StringBuffer();
		query.append("SELECT * FROM ");
		query.append(CONDOMINIO);
		query.append(" WHERE ");
		query.append(CODIGO);	
		query.append(" = ?");		
		Connection con = Conexao.getConexao();
		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = con.prepareStatement(query.toString());		
			SQLUtil.setValorPpreparedStatement(preparedStatement,1, codigo, java.sql.Types.INTEGER);
			existeCondominio = preparedStatement.executeQuery().next();		
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
		return existeCondominio;
	}
	
	@Override
	public Condominio buscarPorCodigo(Integer codigo) throws SQLException,
			Exception {
		StringBuffer query = new StringBuffer();
		query.append("SELECT * FROM ");
		query.append(CONDOMINIO);
		query.append(" WHERE ");
		query.append(CODIGO);	
		query.append(" = ?");		
		Connection con = Conexao.getConexao();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Condominio condominio = null;
		try {
			preparedStatement = con.prepareStatement(query.toString());		
			SQLUtil.setValorPpreparedStatement(preparedStatement,1, codigo, java.sql.Types.INTEGER);
			resultSet = preparedStatement.executeQuery();			
			while(resultSet.next()){
				condominio = new Condominio();
				condominio.setId((Integer) SQLUtil.getValorResultSet(resultSet, ID, java.sql.Types.INTEGER));
				condominio.setNome(String.valueOf(SQLUtil.getValorResultSet(resultSet, NOME, java.sql.Types.VARCHAR)));
				condominio.setSituacao((Integer) SQLUtil.getValorResultSet(resultSet, SITUACAO, java.sql.Types.INTEGER));
				condominio.setCnpj((Long) SQLUtil.getValorResultSet(resultSet, CNPJ, java.sql.Types.BIGINT));
				condominio.setTelefoneCelular((Long) SQLUtil.getValorResultSet(resultSet, TELEFONE_CELULAR, java.sql.Types.BIGINT));
				condominio.setTelefoneFixo((Long) SQLUtil.getValorResultSet(resultSet, TELEFONE_FIXO, java.sql.Types.BIGINT));
				condominio.setCodigo((Integer) SQLUtil.getValorResultSet(resultSet, CODIGO, java.sql.Types.INTEGER));			
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
		return condominio;
	}
	
	/**
	 * M�todo que popula um condom�nio, ou seja, recebe um objeto condom�nio e "seta" nele todos os blocos, unidades e cond�ninos desse condom�nio.
	 * 
	 * @param condominio - Ao final do m�todo esse objeto estar� todo populado.
	 * @throws Exception 
	 */
	// FIXME Rever esse m�todo, pois est� muito lento
	public void popularCondominio(Condominio condominio) throws SQLException, Exception{		
		List<Bloco> listaDeBlocos = null;
		List<Unidade> listaDeUnidades = null;
		List<Condomino> listaDeCondominos = null;
		listaDeBlocos = this.blocoDAO.get().buscarListaBlocosPorCondominioENome(condominio,null);		
		condominio.setListaBlocos(listaDeBlocos);
		for (Bloco bloco : listaDeBlocos) {
			listaDeUnidades = this.unidadeDAO.get().buscarListaUnidadesPorBloco(bloco);
			bloco.setListaUnidade(listaDeUnidades);
			for (Unidade unidade : listaDeUnidades) {
				listaDeCondominos = this.condominoDAO.get().buscarListaCondominosPorUnidade(unidade);		
				unidade.setListaCondominos(listaDeCondominos);
			}
		}
	}
	
	/**
	 * M�todo que popula um condom�nio, ou seja, recebe um objeto condom�nio e "seta" nele todos os blocos, unidades e cond�ninos desse condom�nio. </br>
	 * sendo que os cond�minos est�o sem imagem e est�o com o status de pagador igual a sim {@link CondominoPagadorEnum}
	 * 
	 * @param condominio - Ao final do m�todo esse objeto estar� todo populado.
	 * @throws Exception 
	 */
	public void popularCondominioComCondominoSemImagemEPagador(Condominio condominio) throws SQLException, Exception{	
		List<Bloco> listaDeBlocos = null;
		List<Unidade> listaDeUnidades = null;
		List<Condomino> listaDeCondominos = null;
	
		listaDeBlocos = this.blocoDAO.get().buscarListaBlocosPorCondominioENome(condominio,null);		
		condominio.setListaBlocos(listaDeBlocos);
		for (Bloco bloco : listaDeBlocos) {
			listaDeUnidades = this.unidadeDAO.get().buscarListaUnidadesPorBloco(bloco);
			bloco.setListaUnidade(listaDeUnidades);
			for (Unidade unidade : listaDeUnidades) {
				listaDeCondominos = this.condominoDAO.get().buscarPorIdUnidadeEPagadorSemImagem(unidade.getId(), CondominoPagadorEnum.SIM.getPagador());		
				unidade.setListaCondominos(listaDeCondominos);
			}
		}
	}	
		
	private void salvarGestoresCondominio(Condominio condominio, Connection con)throws SQLException, Exception {
		// Persiste o S�ndico-Geral. Sempre deve existir um s�ndico-geral
		GestorCondominio sindicoGeralGestorCondominio = new GestorCondominio();
		sindicoGeralGestorCondominio.setIdCondominio(condominio.getId());
		sindicoGeralGestorCondominio.setIdUsuario(condominio.getSindicoGeral().getId());
		sindicoGeralGestorCondominio.setTipoCondomino(TipoGestorCondominioEnum.SINDICO_GERAL.getGestorCondominio());
		this.gestorCondominioDAO.get().salvarGestorCondominio(sindicoGeralGestorCondominio, con);	
		// Persiste o SubS�ndico-Geral - Caso possua subS�ndicoGeral
		if(condominio.getSubSindicoGeral() != null && condominio.getSubSindicoGeral().getId() != -1){
			GestorCondominio subsindicoGeralGestorCondominio = new GestorCondominio();
			subsindicoGeralGestorCondominio.setIdCondominio(condominio.getId());
			subsindicoGeralGestorCondominio.setIdUsuario(condominio.getSubSindicoGeral().getId());
			subsindicoGeralGestorCondominio.setTipoCondomino(TipoGestorCondominioEnum.SUBSINDICO_GERAL.getGestorCondominio());
			this.gestorCondominioDAO.get().salvarGestorCondominio(subsindicoGeralGestorCondominio, con);
		}
		GestorCondominio conselheiroGeralGestorCondominio = null;
		// Persiste os Conselheiros - Caso possua mais conselheiros
		for (Condomino conselheiro : condominio.getListaConselheiros()) {
			conselheiroGeralGestorCondominio = new GestorCondominio();
			conselheiroGeralGestorCondominio.setIdCondominio(condominio.getId());
			conselheiroGeralGestorCondominio.setIdUsuario(conselheiro.getId());
			conselheiroGeralGestorCondominio.setTipoCondomino(TipoGestorCondominioEnum.CONSELHEIRO_CONDOMINIO.getGestorCondominio());
			this.gestorCondominioDAO.get().salvarGestorCondominio(conselheiroGeralGestorCondominio, con);
		}
	}

	@Override
	public List<Condominio> buscarPorSituacaoESemGestores(Integer situacao) throws SQLException, Exception {
		StringBuffer query = new StringBuffer();
		query.append("SELECT * FROM ");
		query.append(CONDOMINIO);
		query.append(" WHERE ");
		query.append(SITUACAO);	
		query.append(" = ?");
		query.append(" ORDER BY ");
		query.append(NOME);
		Connection con = Conexao.getConexao();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Condominio condominio = null;
		List<Condominio> listaDeCondominios = new ArrayList<Condominio>();
		List<GestorCondominio> listaGestorCondominio = null;
		try {
			preparedStatement = con.prepareStatement(query.toString());		
			SQLUtil.setValorPpreparedStatement(preparedStatement,1, situacao, java.sql.Types.INTEGER);
			resultSet = preparedStatement.executeQuery();			
			while(resultSet.next()){
				condominio = new Condominio();
				condominio.setId((Integer) SQLUtil.getValorResultSet(resultSet, ID, java.sql.Types.INTEGER));
				condominio.setNome(String.valueOf(SQLUtil.getValorResultSet(resultSet, NOME, java.sql.Types.VARCHAR)));
				condominio.setSituacao((Integer) SQLUtil.getValorResultSet(resultSet, SITUACAO, java.sql.Types.INTEGER));
				condominio.setCnpj((Long) SQLUtil.getValorResultSet(resultSet, CNPJ, java.sql.Types.BIGINT));
				condominio.setTelefoneCelular((Long) SQLUtil.getValorResultSet(resultSet, TELEFONE_CELULAR, java.sql.Types.BIGINT));
				condominio.setTelefoneFixo((Long) SQLUtil.getValorResultSet(resultSet, TELEFONE_FIXO, java.sql.Types.BIGINT));
				condominio.setCodigo((Integer) SQLUtil.getValorResultSet(resultSet, CODIGO, java.sql.Types.INTEGER));
				listaGestorCondominio = this.gestorCondominioDAO.get().buscarListaGestoresCondominioPorCondominio(condominio, con);
				if (listaGestorCondominio.isEmpty()){
					listaDeCondominios.add(condominio);					
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
		return listaDeCondominios;
	}

	@Override
	public List<Condominio> buscarPorSituacao(Integer situacao) throws SQLException, Exception {
		StringBuffer query = new StringBuffer();
		query.append("SELECT * FROM ");
		query.append(CONDOMINIO);
		query.append(" WHERE ");
		query.append(SITUACAO);	
		query.append(" = ?");
		query.append(" ORDER BY ");
		query.append(NOME);
		Connection con = Conexao.getConexao();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Condominio condominio = null;
		List<Condominio> listaDeCondominios = new ArrayList<Condominio>();
		try {
			preparedStatement = con.prepareStatement(query.toString());		
			SQLUtil.setValorPpreparedStatement(preparedStatement,1, situacao, java.sql.Types.INTEGER);
			resultSet = preparedStatement.executeQuery();			
			while(resultSet.next()){
				condominio = new Condominio();
				condominio.setId((Integer) SQLUtil.getValorResultSet(resultSet, ID, java.sql.Types.INTEGER));
				condominio.setNome(String.valueOf(SQLUtil.getValorResultSet(resultSet, NOME, java.sql.Types.VARCHAR)));
				condominio.setSituacao((Integer) SQLUtil.getValorResultSet(resultSet, SITUACAO, java.sql.Types.INTEGER));
				condominio.setCnpj((Long) SQLUtil.getValorResultSet(resultSet, CNPJ, java.sql.Types.BIGINT));
				condominio.setTelefoneCelular((Long) SQLUtil.getValorResultSet(resultSet, TELEFONE_CELULAR, java.sql.Types.BIGINT));
				condominio.setTelefoneFixo((Long) SQLUtil.getValorResultSet(resultSet, TELEFONE_FIXO, java.sql.Types.BIGINT));
				condominio.setCodigo((Integer) SQLUtil.getValorResultSet(resultSet, CODIGO, java.sql.Types.INTEGER));
				listaDeCondominios.add(condominio);					
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
		return listaDeCondominios;
	}

	@Override
	public List<Condominio> buscarPorIdEscritorioContabilidade(Integer idEscritorioContabilidade, Connection con) throws SQLException, Exception {
		StringBuffer query = new StringBuffer();
		query.append("SELECT * FROM ");
		query.append(CONDOMINIO);
		query.append(" WHERE ");
		query.append(ID_ESCRITORIO_CONTABILIDADE);	
		query.append(" = ?");
		query.append(" ORDER BY ");
		query.append(NOME);		
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Condominio condominio = null;
		List<Condominio> listaDeCondominios = new ArrayList<Condominio>();
		try {
			preparedStatement = con.prepareStatement(query.toString());		
			SQLUtil.setValorPpreparedStatement(preparedStatement,1, idEscritorioContabilidade, java.sql.Types.INTEGER);
			resultSet = preparedStatement.executeQuery();			
			while(resultSet.next()){
				condominio = new Condominio();
				condominio.setId((Integer) SQLUtil.getValorResultSet(resultSet, ID, java.sql.Types.INTEGER));
				condominio.setNome(String.valueOf(SQLUtil.getValorResultSet(resultSet, NOME, java.sql.Types.VARCHAR)));
				condominio.setSituacao((Integer) SQLUtil.getValorResultSet(resultSet, SITUACAO, java.sql.Types.INTEGER));
				condominio.setCnpj((Long) SQLUtil.getValorResultSet(resultSet, CNPJ, java.sql.Types.BIGINT));
				condominio.setTelefoneCelular((Long) SQLUtil.getValorResultSet(resultSet, TELEFONE_CELULAR, java.sql.Types.BIGINT));
				condominio.setTelefoneFixo((Long) SQLUtil.getValorResultSet(resultSet, TELEFONE_FIXO, java.sql.Types.BIGINT));
				condominio.setCodigo((Integer) SQLUtil.getValorResultSet(resultSet, CODIGO, java.sql.Types.INTEGER));
				listaDeCondominios.add(condominio);					
			}
		} catch (SQLException e) {			
			throw e;
		} catch (Exception e) {			
			throw e;
		}	
		return listaDeCondominios;
	}
	
	@Override
	public List<Condominio> buscarPorIdEscritorioContabilidade(Integer idEscritorioContabilidade) throws SQLException, Exception {
		StringBuffer query = new StringBuffer();
		query.append("SELECT * FROM ");
		query.append(CONDOMINIO);
		query.append(" WHERE ");
		query.append(ID_ESCRITORIO_CONTABILIDADE);	
		query.append(" = ?");
		query.append(" ORDER BY ");
		query.append(NOME);		
		PreparedStatement preparedStatement = null;
		Connection con = Conexao.getConexao();
		ResultSet resultSet = null;
		Condominio condominio = null;
		List<Condominio> listaDeCondominios = new ArrayList<Condominio>();
		try {
			preparedStatement = con.prepareStatement(query.toString());		
			SQLUtil.setValorPpreparedStatement(preparedStatement,1, idEscritorioContabilidade, java.sql.Types.INTEGER);
			resultSet = preparedStatement.executeQuery();			
			while(resultSet.next()){
				condominio = new Condominio();
				condominio.setId((Integer) SQLUtil.getValorResultSet(resultSet, ID, java.sql.Types.INTEGER));
				condominio.setNome(String.valueOf(SQLUtil.getValorResultSet(resultSet, NOME, java.sql.Types.VARCHAR)));
				condominio.setSituacao((Integer) SQLUtil.getValorResultSet(resultSet, SITUACAO, java.sql.Types.INTEGER));
				condominio.setCnpj((Long) SQLUtil.getValorResultSet(resultSet, CNPJ, java.sql.Types.BIGINT));
				condominio.setTelefoneCelular((Long) SQLUtil.getValorResultSet(resultSet, TELEFONE_CELULAR, java.sql.Types.BIGINT));
				condominio.setTelefoneFixo((Long) SQLUtil.getValorResultSet(resultSet, TELEFONE_FIXO, java.sql.Types.BIGINT));
				condominio.setCodigo((Integer) SQLUtil.getValorResultSet(resultSet, CODIGO, java.sql.Types.INTEGER));
				listaDeCondominios.add(condominio);					
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
		return listaDeCondominios;
	}

	
}
