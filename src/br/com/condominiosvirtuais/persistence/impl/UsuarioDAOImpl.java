package br.com.condominiosvirtuais.persistence.impl;

import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.enterprise.inject.Instance;
import javax.inject.Inject;

import org.apache.log4j.Logger;

import br.com.condominiosvirtuais.entity.Condominio;
import br.com.condominiosvirtuais.entity.GestorCondominio;
import br.com.condominiosvirtuais.entity.Usuario;
import br.com.condominiosvirtuais.entity.UsuarioCondominio;
import br.com.condominiosvirtuais.enumeration.TipoGestorCondominioEnum;
import br.com.condominiosvirtuais.exception.BusinessException;
import br.com.condominiosvirtuais.persistence.UsuarioCondominioDAO;
import br.com.condominiosvirtuais.persistence.UsuarioDAO;
import br.com.condominiosvirtuais.util.AplicacaoUtil;
import br.com.condominiosvirtuais.util.SQLUtil;

public class UsuarioDAOImpl implements UsuarioDAO, Serializable{
	
	private static final long serialVersionUID = 1L;

	private static Logger logger = Logger.getLogger(UsuarioDAOImpl.class);
	
	private static final String USUARIO = "USUARIO";
	
	private static final String ID = "ID";
	
	private static final String NOME = "NOME";
	
	private static final String SENHA = "SENHA";
	
	private static final String SEXO = "SEXO";	
	
	private static final String DATA_NASCIMENTO = "DATA_NASCIMENTO";
	
	private static final String SITUACAO = "SITUACAO";	
	
	private static final String ULTIMO_LOGIN = "ULTIMO_LOGIN";
	
	private static final String ULTIMO_LOGOUT = "ULTIMO_LOGOUT";
	
	private static final String ID_GRUPO_USUARIO = "ID_GRUPO_USUARIO";
	
	@Inject
	private Instance<UsuarioCondominioDAO> usuarioCondominioDAO = null;
	
	@Inject
	private Instance<CondominioDAOImpl> condominioDAO = null;
	
	@Inject
	private Instance<EmailUsuarioDAOImpl> emailUsuarioDAO = null;
	
	@Inject
	private Instance<GestorCondominioDAOImpl> gestorCondominioDAO = null;
	
	
	// Constraint referente a integridade entre uma mensagem e um usuário, ou seja, não será possível excluir um usuário
	// caso esse tenha sido o destinatário de uma ou mais mensagens.
	private static final String FK_MENSAGEM_RECEBIDA_ID_USUARIO_DESTINATARIO_USUARIO_ID = "FK_MENSAGEM_RECEBIDA_ID_USUARIO_DESTINATARIO_USUARIO_ID";
	
	// Constraint referente a integridade entre uma mensagem e um usuário, ou seja, não será possível excluir um usuário
	// caso esse tenha sido o remetente de uma ou mais mensagens.
	private static final String FK_ID_MENSAGEM_REM_USUARIO = "FK_MENSAGEM_RECEBIDA_ID_USUARIO_REMETENTE_USUARIO_ID";
	
	
	
	
	public List<Usuario> buscarListaUsuarios() throws SQLException, Exception{
		StringBuffer query = new StringBuffer();
		query.append("SELECT * FROM ");
		query.append(USUARIO);
		query.append(";");		
		Connection con = Conexao.getConexao();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		List<Usuario> listaUsuario = new ArrayList<Usuario>();
		Usuario usuario = null;
		try {
			preparedStatement = con.prepareStatement(query.toString());
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()){
				usuario = new Usuario();
				usuario.setId((Integer) SQLUtil.getValorResultSet(resultSet, ID, java.sql.Types.INTEGER));
				usuario.setNome(String.valueOf(SQLUtil.getValorResultSet(resultSet, NOME, java.sql.Types.VARCHAR)));
				usuario.setSexo((Integer) SQLUtil.getValorResultSet(resultSet, SEXO, java.sql.Types.INTEGER));
				usuario.setDataNascimento((Date) SQLUtil.getValorResultSet(resultSet, DATA_NASCIMENTO, java.sql.Types.DATE));
				usuario.setSituacao((Integer) SQLUtil.getValorResultSet(resultSet, SITUACAO, java.sql.Types.INTEGER));
				listaUsuario.add(usuario);
			}
		}catch (SQLException e) {			
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
		return listaUsuario;
	}
	
	public void salvarUsuario(Usuario usuario, Connection con) throws NoSuchAlgorithmException, NumberFormatException, 
	SQLException, Exception {		
		StringBuffer query = new StringBuffer();
		query.append("INSERT INTO "); 
		query.append(USUARIO);
		query.append("("); 
		query.append(NOME); 
		query.append(",");		
		query.append(SEXO); 	
		query.append(",");
		query.append(SENHA); 
		query.append(",");
		query.append(DATA_NASCIMENTO);
		query.append(",");
		query.append(SITUACAO);
		query.append(",");
		query.append(ID_GRUPO_USUARIO);
		query.append(") ");
		query.append("VALUES(?,?,?,?,?,?)");
		PreparedStatement statement = null;
		try {			
			statement = con.prepareStatement(query.toString(), PreparedStatement.RETURN_GENERATED_KEYS);
			SQLUtil.setValorPpreparedStatement(statement, 1, usuario.getNome(), java.sql.Types.VARCHAR);			
			SQLUtil.setValorPpreparedStatement(statement, 2, usuario.getSexo(), java.sql.Types.INTEGER);	
			SQLUtil.setValorPpreparedStatement(statement, 3,  AplicacaoUtil.gerarHashMD5(usuario.getSenha()), java.sql.Types.VARCHAR);
			SQLUtil.setValorPpreparedStatement(statement, 4, usuario.getDataNascimento(), java.sql.Types.DATE);
			SQLUtil.setValorPpreparedStatement(statement, 5, usuario.getSituacao(), java.sql.Types.INTEGER);
			SQLUtil.setValorPpreparedStatement(statement, 6, usuario.getIdGrupoUsuario(), java.sql.Types.INTEGER );
			statement.executeUpdate();
			ResultSet rs = statement.getGeneratedKeys();
			rs.next();
			usuario.setId(rs.getInt(1));
			// Código que associa o usuário que está sendo salvo a um ou mais condomínios que esse terá acesso.
			for (Condominio condominio : usuario.getListaCondominio()) {
				UsuarioCondominio usuarioCondominio = new UsuarioCondominio();
				usuarioCondominio.setIdUsuario(usuario.getId());
				usuarioCondominio.setIdCondominio(condominio.getId());
				this.usuarioCondominioDAO.get().salvarUsuarioCondominio(usuarioCondominio, con);
			}
			usuario.getEmail().setIdUsuario(usuario.getId());
			this.emailUsuarioDAO.get().salvar(usuario.getEmail(), con);			
		} catch (NumberFormatException e) {
			con.rollback();
			throw e;
		} catch (NoSuchAlgorithmException e) {
			con.rollback();
			throw e;
		}catch (SQLException e) {
			con.rollback();
			throw e;
		} catch (Exception e) {
			con.rollback();
			throw e; 
		}		
	}
	
	public void buscarEPopularUsuarioPeloId(Usuario usuario, Connection con ) throws SQLException, Exception{
		List<UsuarioCondominio> listaUsuarioCondominio = null; 		
		List<Condominio> listaCondominio = null;
		StringBuffer query = new StringBuffer();
		query.append("SELECT * FROM ");
		query.append(USUARIO);
		query.append(" WHERE ");
		query.append(ID);
		query.append(" = ?");		
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			preparedStatement = con.prepareStatement(query.toString());
			preparedStatement.setInt(1,usuario.getId());
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()){
				listaCondominio = new ArrayList<Condominio>();				
				usuario.setNome(String.valueOf(SQLUtil.getValorResultSet(resultSet, NOME, java.sql.Types.VARCHAR)));
				usuario.setSexo((Integer) SQLUtil.getValorResultSet(resultSet, SEXO, java.sql.Types.INTEGER));
				usuario.setDataNascimento((Date) SQLUtil.getValorResultSet(resultSet, DATA_NASCIMENTO, java.sql.Types.DATE));
				usuario.setSituacao((Integer) SQLUtil.getValorResultSet(resultSet, SITUACAO, java.sql.Types.INTEGER));
				usuario.setIdGrupoUsuario((Integer) SQLUtil.getValorResultSet(resultSet, ID_GRUPO_USUARIO, java.sql.Types.INTEGER));
				usuario.setSenha(String.valueOf(SQLUtil.getValorResultSet(resultSet, SENHA, java.sql.Types.VARCHAR)));	
				usuario.setEmail(this.emailUsuarioDAO.get().buscarEmailPrincipalPorUsuario(usuario,con));
				listaUsuarioCondominio = this.usuarioCondominioDAO.get().buscarListaPorUsuario(usuario,con);
				for (UsuarioCondominio usuarioCondominio : listaUsuarioCondominio) {
					Condominio condominio = new Condominio();
					condominio.setId(usuarioCondominio.getIdCondominio());				
					listaCondominio.add(condominio);			
				}
				usuario.setListaCondominio(listaCondominio);
			}
		}catch (SQLException e) {
			con.rollback();
			throw e;
		} catch (Exception e) {
			con.rollback();
			throw e; 
		}		
	}
	
	/**
	 * Método que popula um objeto do tipo Usuário conforme as seguintes condições: <br>
	 * Caso encontre o registro (pesquisando pelo ID) e o nome atenda o requisito do LIKE <br>
	 * 
	 * @param Usuario - Objeto que será populado
	 * @param nomeUsuario - String que contém o nome que será pesquisado no LIKE 
	 * @param con - Conexão
	 * @return Boolean - True caso encontrou o objeto e False caso não tenha encontrado.
	 * @throws SQLException, Exception 
	 */
	public Boolean popularUsuarioPeloIdENome(Usuario usuario, String nomeUsuario, Connection con ) throws SQLException, Exception{
		List<UsuarioCondominio> listaUsuarioCondominio = null;
		List<Condominio> listaCondominio = null;
		Boolean populou = Boolean.FALSE;
		StringBuffer query = new StringBuffer();
		query.append("SELECT * FROM ");
		query.append(USUARIO);
		query.append(" WHERE ");
		query.append(ID);
		query.append(" = ?");
		query.append(" AND upper(");
		query.append(NOME);
		query.append(") LIKE ?");
		query.append(";");		
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;		
		try {
			preparedStatement = con.prepareStatement(query.toString());
			preparedStatement.setInt(1,usuario.getId());
			preparedStatement.setString(2,"%"+nomeUsuario.toUpperCase()+"%");
			resultSet = preparedStatement.executeQuery();			
			while(resultSet.next()){
				listaCondominio = new ArrayList<Condominio>();
				populou = Boolean.TRUE;				
				usuario.setNome(String.valueOf(SQLUtil.getValorResultSet(resultSet, NOME, java.sql.Types.VARCHAR)));
				usuario.setSexo((Integer) SQLUtil.getValorResultSet(resultSet, SEXO, java.sql.Types.INTEGER));				
				usuario.setDataNascimento((Date) SQLUtil.getValorResultSet(resultSet, DATA_NASCIMENTO, java.sql.Types.DATE));
				usuario.setSituacao((Integer) SQLUtil.getValorResultSet(resultSet, SITUACAO, java.sql.Types.INTEGER));
				usuario.setIdGrupoUsuario((Integer) SQLUtil.getValorResultSet(resultSet, ID_GRUPO_USUARIO, java.sql.Types.INTEGER));
				usuario.setEmail(this.emailUsuarioDAO.get().buscarEmailPrincipalPorUsuario(usuario));
				listaUsuarioCondominio = this.usuarioCondominioDAO.get().buscarListaPorUsuario(usuario);
				for (UsuarioCondominio usuarioCondominio : listaUsuarioCondominio) {
					Condominio condominio = new Condominio();
					condominio.setId(usuarioCondominio.getIdCondominio());				
					listaCondominio.add(condominio);			
				}
				usuario.setListaCondominio(listaCondominio);
			}
		}catch (SQLException e) {
			con.rollback();
			throw e;
		} catch (Exception e) {
			con.rollback();
			throw e; 
		}		
		return populou;
	}
	
	public void atualizarUsuario(Usuario usuario, Connection con) throws SQLException, Exception{
		this.usuarioCondominioDAO.get().excluirPorUsuario(usuario, con);		
		for (Condominio condominio : usuario.getListaCondominio()) {
			UsuarioCondominio usuarioCondominio = new UsuarioCondominio();
			usuarioCondominio.setIdUsuario(usuario.getId());
			usuarioCondominio.setIdCondominio(condominio.getId());
			this.usuarioCondominioDAO.get().salvarUsuarioCondominio(usuarioCondominio, con);			
		}
		this.emailUsuarioDAO.get().atualizar(usuario.getEmail(), con);
		StringBuffer query = new StringBuffer();
		query.append("UPDATE ");
		query.append(USUARIO);
		query.append(" SET ");
		query.append(NOME); 
		query.append(" = ?,");		
		query.append(SEXO); 
		query.append(" = ?,");
		query.append(DATA_NASCIMENTO); 
		query.append(" = ?, ");
		query.append(SITUACAO); 
		query.append(" = ?, ");
		query.append(ID_GRUPO_USUARIO); 
		query.append(" = ? ");		
		query.append("WHERE ");
		query.append(ID);
		query.append(" = ? ");		
		PreparedStatement statement = null;
		try {			
			statement = con.prepareStatement(query.toString());
			SQLUtil.setValorPpreparedStatement(statement, 1, usuario.getNome(), java.sql.Types.VARCHAR);
			SQLUtil.setValorPpreparedStatement(statement, 2, usuario.getSexo(), java.sql.Types.INTEGER);
			SQLUtil.setValorPpreparedStatement(statement, 3, usuario.getDataNascimento(), java.sql.Types.DATE);
			SQLUtil.setValorPpreparedStatement(statement, 4, usuario.getSituacao(), java.sql.Types.INTEGER);
			SQLUtil.setValorPpreparedStatement(statement, 5, usuario.getIdGrupoUsuario(), java.sql.Types.INTEGER);
			SQLUtil.setValorPpreparedStatement(statement, 6, usuario.getId(), java.sql.Types.INTEGER);		
			statement.executeUpdate();					
		} catch (SQLException e) {
			con.rollback();
			throw e;
		} catch (Exception e) {
			con.rollback();
			throw e; 
		}		
	}
	
	public void atualizarUsuario(Usuario usuario) throws SQLException, Exception{
		Connection con = Conexao.getConexao();
		con.setAutoCommit(false);
		this.usuarioCondominioDAO.get().excluirPorUsuario(usuario, con);		
		for (Condominio condominio : usuario.getListaCondominio()) {
			UsuarioCondominio usuarioCondominio = new UsuarioCondominio();
			usuarioCondominio.setIdUsuario(usuario.getId());
			usuarioCondominio.setIdCondominio(condominio.getId());
			this.usuarioCondominioDAO.get().salvarUsuarioCondominio(usuarioCondominio, con);			
		}
		StringBuffer query = new StringBuffer();
		query.append("UPDATE ");
		query.append(USUARIO);
		query.append(" SET ");
		query.append(NOME); 
		query.append(" = ?,");		
		query.append(SEXO); 
		query.append(" = ?,");		
		query.append(DATA_NASCIMENTO); 
		query.append(" = ?, ");		
		query.append(SITUACAO); 
		query.append(" = ? ");		
		query.append("WHERE ");
		query.append(ID);
		query.append(" = ? ");		
		PreparedStatement statement = null;
		try {			
			statement = con.prepareStatement(query.toString());
			SQLUtil.setValorPpreparedStatement(statement, 1, usuario.getNome(), java.sql.Types.VARCHAR);
			SQLUtil.setValorPpreparedStatement(statement, 2, usuario.getSexo(), java.sql.Types.INTEGER);
			SQLUtil.setValorPpreparedStatement(statement, 3, usuario.getImagem(), java.sql.Types.BINARY);
			SQLUtil.setValorPpreparedStatement(statement, 4, usuario.getDataNascimento(), java.sql.Types.DATE);
			SQLUtil.setValorPpreparedStatement(statement, 5, usuario.getSituacao(), java.sql.Types.INTEGER);
			SQLUtil.setValorPpreparedStatement(statement, 6, usuario.getId(), java.sql.Types.INTEGER);						
			statement.executeUpdate();	
			con.commit();
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
	
	public void excluirUsuario(Usuario usuario, Connection con) throws SQLException, BusinessException, Exception{
		StringBuffer query = new StringBuffer();		
		PreparedStatement statement = null;
		try {			
			this.usuarioCondominioDAO.get().excluirPorUsuario(usuario, con);
			this.emailUsuarioDAO.get().excluir(usuario.getEmail(), con);
			query.append("DELETE FROM ");
			query.append(USUARIO);
			query.append(" WHERE ");		
			query.append(ID);
			query.append(" = ?");
			statement = con.prepareStatement(query.toString());
			statement.setInt(1, usuario.getId());
			statement.executeUpdate();			
		} catch (SQLException e){
			con.rollback();
			if (e.getMessage().contains(FK_MENSAGEM_RECEBIDA_ID_USUARIO_DESTINATARIO_USUARIO_ID)){
				throw new BusinessException("msg.usuario.mensagemDesAssociada");
			}else if (e.getMessage().contains(FK_ID_MENSAGEM_REM_USUARIO)){
				throw new BusinessException("msg.usuario.mensagemRemAssociada");
			}else{
				throw e;
			}
		} catch (Exception e) {
			con.rollback();
			throw e;
		}
	}
	
	public Usuario buscarPorId(Integer idUsuario) throws SQLException, Exception{
		StringBuffer query = new StringBuffer();
		query.append("SELECT * FROM ");
		query.append(USUARIO);
		query.append(" WHERE ");
		query.append(ID);
		query.append(" = ?");		
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Connection con = Conexao.getConexao();
		Usuario usuario = null;
		List<UsuarioCondominio> listaUsuarioCondominio = null;
		List<Condominio> listaCondominio = null;
		Condominio condominio = null;
		try {
			preparedStatement = con.prepareStatement(query.toString());
			preparedStatement.setInt(1,idUsuario);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()){
				usuario = new Usuario();
				listaCondominio = new ArrayList<Condominio>();
				usuario.setId((Integer) SQLUtil.getValorResultSet(resultSet, ID, java.sql.Types.INTEGER));
				usuario.setNome(String.valueOf(SQLUtil.getValorResultSet(resultSet, NOME, java.sql.Types.VARCHAR)));
				usuario.setSexo((Integer) SQLUtil.getValorResultSet(resultSet, SEXO, java.sql.Types.INTEGER));
				usuario.setSenha(String.valueOf(SQLUtil.getValorResultSet(resultSet, SENHA, java.sql.Types.VARCHAR)));			
				usuario.setDataNascimento((Date) SQLUtil.getValorResultSet(resultSet, DATA_NASCIMENTO, java.sql.Types.DATE));
				usuario.setSituacao((Integer) SQLUtil.getValorResultSet(resultSet, SITUACAO, java.sql.Types.INTEGER));
				usuario.setIdGrupoUsuario((Integer) SQLUtil.getValorResultSet(resultSet, ID_GRUPO_USUARIO, java.sql.Types.INTEGER));					
				usuario.setEmail(this.emailUsuarioDAO.get().buscarEmailPrincipalPorUsuario(usuario));
				listaUsuarioCondominio = this.usuarioCondominioDAO.get().buscarListaPorUsuario(usuario);	
				for (UsuarioCondominio usuarioCondominio : listaUsuarioCondominio) {
					condominio = this.condominioDAO.get().buscarCondominioPorId(usuarioCondominio.getIdCondominio());
				}
				listaCondominio.add(condominio);				
				usuario.setListaCondominio(listaCondominio);				
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
		return usuario;
	}
	
	public Usuario buscarPorId(Integer idUsuario, Connection con) throws SQLException, Exception{
		StringBuffer query = new StringBuffer();
		query.append("SELECT * FROM ");
		query.append(USUARIO);
		query.append(" WHERE ");
		query.append(ID);
		query.append(" = ?");		
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;		
		Usuario usuario = null;
		List<UsuarioCondominio> listaUsuarioCondominio = null;
		List<Condominio> listaCondominio = null;
		Condominio condominio = null;
		try {
			preparedStatement = con.prepareStatement(query.toString());
			preparedStatement.setInt(1,idUsuario);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()){
				usuario = new Usuario();
				listaCondominio = new ArrayList<Condominio>();
				usuario.setId((Integer) SQLUtil.getValorResultSet(resultSet, ID, java.sql.Types.INTEGER));
				usuario.setNome(String.valueOf(SQLUtil.getValorResultSet(resultSet, NOME, java.sql.Types.VARCHAR)));
				usuario.setSexo((Integer) SQLUtil.getValorResultSet(resultSet, SEXO, java.sql.Types.INTEGER));
				usuario.setSenha(String.valueOf(SQLUtil.getValorResultSet(resultSet, SENHA, java.sql.Types.VARCHAR)));			
				usuario.setDataNascimento((Date) SQLUtil.getValorResultSet(resultSet, DATA_NASCIMENTO, java.sql.Types.DATE));
				usuario.setSituacao((Integer) SQLUtil.getValorResultSet(resultSet, SITUACAO, java.sql.Types.INTEGER));
				usuario.setIdGrupoUsuario((Integer) SQLUtil.getValorResultSet(resultSet, ID_GRUPO_USUARIO, java.sql.Types.INTEGER));					
				usuario.setEmail(this.emailUsuarioDAO.get().buscarEmailPrincipalPorUsuario(usuario));
				listaUsuarioCondominio = this.usuarioCondominioDAO.get().buscarListaPorUsuario(usuario);	
				for (UsuarioCondominio usuarioCondominio : listaUsuarioCondominio) {
					condominio = this.condominioDAO.get().buscarCondominioPorId(usuarioCondominio.getIdCondominio());
				}
				listaCondominio.add(condominio);				
				usuario.setListaCondominio(listaCondominio);				
			}
		} catch (SQLException e) {
			con.rollback();
			throw e;
		} catch (Exception e) {
			con.rollback();
			throw e;
		}
		return usuario;
	}
	
	public void atualizarSenha(Usuario usuario) throws SQLException, Exception{
		StringBuffer query = new StringBuffer();
		query.append("UPDATE ");
		query.append(USUARIO);
		query.append(" SET ");
		query.append(SENHA); 
		query.append(" = ? ");
		query.append("WHERE ");
		query.append(ID);
		query.append(" = ? ");		
		PreparedStatement statement = null;
		Connection con = Conexao.getConexao();
		try {			
			statement = con.prepareStatement(query.toString());
			SQLUtil.setValorPpreparedStatement(statement, 1, AplicacaoUtil.gerarHashMD5(usuario.getSenha()), java.sql.Types.VARCHAR);
			SQLUtil.setValorPpreparedStatement(statement, 2, usuario.getId(), java.sql.Types.INTEGER);						
			statement.executeUpdate();
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
	
	@Override
	public void salvarDataHoraLogin(Usuario usuario) throws SQLException, Exception {
		StringBuffer query = new StringBuffer();
		query.append("UPDATE ");
		query.append(USUARIO);
		query.append(" SET ");
		query.append(ULTIMO_LOGIN); 
		query.append(" = ? ");
		query.append("WHERE ");
		query.append(ID);
		query.append(" = ? ");		
		PreparedStatement statement = null;
		Connection con = Conexao.getConexao();
		try {			
			statement = con.prepareStatement(query.toString());
			SQLUtil.setValorPpreparedStatement(statement, 1, usuario.getUltimoLogin(), java.sql.Types.TIMESTAMP);
			SQLUtil.setValorPpreparedStatement(statement, 2, usuario.getId(), java.sql.Types.INTEGER);						
			statement.executeUpdate();
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

	@Override
	public void salvarDataHoraLogout(Usuario usuario) throws SQLException, Exception {
		StringBuffer query = new StringBuffer();
		query.append("UPDATE ");
		query.append(USUARIO);
		query.append(" SET ");
		query.append(ULTIMO_LOGOUT); 
		query.append(" = ? ");
		query.append("WHERE ");
		query.append(ID);
		query.append(" = ? ");		
		PreparedStatement statement = null;
		Connection con = Conexao.getConexao();
		try {			
			statement = con.prepareStatement(query.toString());
			SQLUtil.setValorPpreparedStatement(statement, 1, usuario.getUltimoLogout(), java.sql.Types.TIMESTAMP);
			SQLUtil.setValorPpreparedStatement(statement, 2, usuario.getId(), java.sql.Types.INTEGER);						
			statement.executeUpdate();
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
	
	public Usuario buscarSindicoGeralPorCondominio(Condominio condominio, Connection con) throws SQLException, Exception{
		Usuario usuario = null;
		Boolean encontrou = Boolean.FALSE;                   
		List<GestorCondominio> listaDeGestorCondominio = this.gestorCondominioDAO.get().buscarListaGestoresCondominioPorCondominio(condominio,con);
		Integer indice = 0;
		while (encontrou == Boolean.FALSE && indice < listaDeGestorCondominio.size()){
			GestorCondominio gestorCondominio = listaDeGestorCondominio.get(indice++);
			if(gestorCondominio.getTipoCondomino() == TipoGestorCondominioEnum.SINDICO_GERAL.getGestorCondominio()){
				usuario = new Usuario();
				usuario.setId(gestorCondominio.getIdUsuario());
				this.buscarEPopularUsuarioPeloId(usuario,con);
				encontrou = Boolean.TRUE;
			}			
		}		
		return usuario;
	}
	
	public Usuario buscarSindicoGeralPorCondominio(Condominio condominio) throws SQLException, Exception{
		Usuario usuario = null;
		Boolean encontrou = Boolean.FALSE;   
		Connection con = Conexao.getConexao();
		List<GestorCondominio> listaDeGestorCondominio = this.gestorCondominioDAO.get().buscarListaGestoresCondominioPorCondominio(condominio,con);
		Integer indice = 0;
		while (encontrou == Boolean.FALSE && indice < listaDeGestorCondominio.size()){
			GestorCondominio gestorCondominio = listaDeGestorCondominio.get(indice++);
			if(gestorCondominio.getTipoCondomino() == TipoGestorCondominioEnum.SINDICO_GERAL.getGestorCondominio()){
				usuario = new Usuario();
				usuario.setId(gestorCondominio.getIdUsuario());
				this.buscarEPopularUsuarioPeloId(usuario,con);
				encontrou = Boolean.TRUE;
			}			
		}		
		return usuario;
	}


	public Instance<EmailUsuarioDAOImpl> getEmailUsuarioDAO() {
		return emailUsuarioDAO;
	}

	public void setEmailUsuarioDAO(Instance<EmailUsuarioDAOImpl> emailUsuarioDAO) {
		this.emailUsuarioDAO = emailUsuarioDAO;
	}

	
	
}
