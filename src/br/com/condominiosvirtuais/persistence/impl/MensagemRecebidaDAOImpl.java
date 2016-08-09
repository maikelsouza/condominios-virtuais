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

import br.com.condominiosvirtuais.entity.MensagemEnviada;
import br.com.condominiosvirtuais.entity.MensagemRecebida;
import br.com.condominiosvirtuais.entity.Usuario;
import br.com.condominiosvirtuais.persistence.MensagemEnviadaDAO;
import br.com.condominiosvirtuais.persistence.MensagemRecebidaDAO;
import br.com.condominiosvirtuais.util.SQLUtil;

public class MensagemRecebidaDAOImpl implements MensagemRecebidaDAO, Serializable {
	
	private static final long serialVersionUID = 1L;

	private static Logger logger = Logger.getLogger(MensagemRecebidaDAOImpl.class);
	
	private static final String MENSAGEM_RECEBIDA = "MENSAGEM_RECEBIDA";
	
	private static final String ID = "ID";
	
	private static final String ID_USUARIO_REMETENTE = "ID_USUARIO_REMETENTE";
	
	private static final String ID_USUARIO_DESTINATARIO = "ID_USUARIO_DESTINATARIO";
	
	private static final String VISUALIZADA = "VISUALIZADA";
	
	private static final String DATA = "DATA";
	
	private static final String ASSUNTO = "ASSUNTO";
	
	private static final String TEXTO = "TEXTO";
	
	@Inject
	private UsuarioDAOImpl usuarioDAO = null;
	
	@Inject
	private MensagemEnviadaDAO mensagemEnviadaDAO = null;

	public void salvar(MensagemRecebida mensagemRecebida) throws SQLException, Exception {
		StringBuffer query = new StringBuffer();
		query.append("INSERT INTO "); 
		query.append(MENSAGEM_RECEBIDA);
		query.append("("); 
		query.append(ID_USUARIO_REMETENTE);	
		query.append(",");
		query.append(ID_USUARIO_DESTINATARIO);
		query.append(",");
		query.append(VISUALIZADA);
		query.append(",");	
		query.append(DATA);
		query.append(",");	
		query.append(ASSUNTO);
		query.append(",");		
		query.append(TEXTO);
		query.append(") ");
		query.append("VALUES(?,?,?,?,?,?)");
		PreparedStatement statement = null;		
		Connection con = Conexao.getConexao();
		con.setAutoCommit(Boolean.FALSE);
		try {			
			statement = con.prepareStatement(query.toString());
			SQLUtil.setValorPpreparedStatement(statement, 1, mensagemRecebida.getUsuarioRemetente().getId(), java.sql.Types.INTEGER);
			SQLUtil.setValorPpreparedStatement(statement, 2, mensagemRecebida.getUsuarioDestinatario().getId(), java.sql.Types.INTEGER);	
			SQLUtil.setValorPpreparedStatement(statement, 3, mensagemRecebida.getVisualizada(), java.sql.Types.BOOLEAN);			
			SQLUtil.setValorPpreparedStatement(statement, 4, mensagemRecebida.getData(), java.sql.Types.TIMESTAMP);
			SQLUtil.setValorPpreparedStatement(statement, 5, mensagemRecebida.getAssunto(), java.sql.Types.VARCHAR);
			SQLUtil.setValorPpreparedStatement(statement, 6, mensagemRecebida.getTexto(), java.sql.Types.VARCHAR);
			statement.executeUpdate();
			// Código que constrói e salva uma msg que representa a msg enviada.
			MensagemEnviada mensagemEnviada = new MensagemEnviada();
			mensagemEnviada.setAssunto(mensagemRecebida.getAssunto());
			mensagemEnviada.setTexto(mensagemRecebida.getTexto());
			mensagemEnviada.setData(mensagemRecebida.getData());
			mensagemEnviada.setUsuarioRemetente(mensagemRecebida.getUsuarioRemetente());
			List<Usuario> listaUsuarioDestinatarios = new ArrayList<Usuario>();
			listaUsuarioDestinatarios.add(mensagemRecebida.getUsuarioDestinatario());
			mensagemEnviada.setListaUsuariosDestinatarios(listaUsuarioDestinatarios);
			this.mensagemEnviadaDAO.salvar(mensagemEnviada,con);
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

	public void excluir(MensagemRecebida mensagemRecebida) throws SQLException, Exception {
		StringBuffer query = new StringBuffer();
		Connection con = Conexao.getConexao();
		PreparedStatement statement = null;
		try {			
			query.append("DELETE FROM ");
			query.append(MENSAGEM_RECEBIDA);
			query.append(" WHERE ");		
			query.append(ID);
			query.append(" = ?");
			statement = con.prepareStatement(query.toString());
			SQLUtil.setValorPpreparedStatement(statement, 1, mensagemRecebida.getId(), java.sql.Types.INTEGER);			
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
 
	public List<MensagemRecebida> buscarPorUsuarioDestinatario(Usuario usuario) throws SQLException, Exception {
		StringBuffer query = new StringBuffer();
		query.append("SELECT * FROM ");
		query.append(MENSAGEM_RECEBIDA);
		query.append(" WHERE ");
		query.append(ID_USUARIO_DESTINATARIO);		
		query.append(" = ? ");
		query.append("ORDER BY ");
		query.append(DATA);
		query.append(" DESC");
		query.append(";");
		Connection con = Conexao.getConexao();
		PreparedStatement preparedStatement = null;
		List<MensagemRecebida> listaMensagem = new ArrayList<MensagemRecebida>();
		try {
			preparedStatement = con.prepareStatement(query.toString());
			SQLUtil.setValorPpreparedStatement(preparedStatement, 1, usuario.getId(), java.sql.Types.INTEGER);
			ResultSet resultSet = preparedStatement.executeQuery();
			MensagemRecebida mensagem = null;	
			Usuario usuarioRemetente;
			Usuario usuarioDestinatarioBase;
			while(resultSet.next()){				
				mensagem = new MensagemRecebida();				
				mensagem.setId((Integer) SQLUtil.getValorResultSet(resultSet, ID, java.sql.Types.INTEGER));
				mensagem.setData((Date)(SQLUtil.getValorResultSet(resultSet, DATA, java.sql.Types.TIMESTAMP)));
				mensagem.setAssunto(String.valueOf(SQLUtil.getValorResultSet(resultSet, ASSUNTO, java.sql.Types.VARCHAR)));
				mensagem.setTexto(String.valueOf(SQLUtil.getValorResultSet(resultSet, TEXTO, java.sql.Types.VARCHAR)));
				mensagem.setVisualizada((Boolean) SQLUtil.getValorResultSet(resultSet, VISUALIZADA, java.sql.Types.BOOLEAN));
				usuarioRemetente = new Usuario();
				usuarioRemetente.setId((Integer) SQLUtil.getValorResultSet(resultSet, ID_USUARIO_REMETENTE, java.sql.Types.INTEGER));
				this.usuarioDAO.buscarEPopularUsuarioPeloId(usuarioRemetente, con);
				mensagem.setUsuarioRemetente(usuarioRemetente);
				usuarioDestinatarioBase = new Usuario();
				usuarioDestinatarioBase.setId((Integer) SQLUtil.getValorResultSet(resultSet, ID_USUARIO_DESTINATARIO, java.sql.Types.INTEGER));
				this.usuarioDAO.buscarEPopularUsuarioPeloId(usuarioDestinatarioBase, con);
				mensagem.setUsuarioDestinatario(usuarioDestinatarioBase);			
				listaMensagem.add(mensagem);
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
		return listaMensagem;
	}

	public void atualizar(MensagemRecebida mensagemRecebida) throws SQLException, Exception {
		StringBuffer query = new StringBuffer();
		query.append("UPDATE ");
		query.append(MENSAGEM_RECEBIDA);
		query.append(" SET ");
		query.append(ID_USUARIO_REMETENTE);	
		query.append("= ?, ");
		query.append(ID_USUARIO_DESTINATARIO);
		query.append("= ?, ");
		query.append(VISUALIZADA);
		query.append("= ?, ");		
		query.append(DATA);
		query.append("= ? ");
		query.append(ASSUNTO);
		query.append("= ? ");
		query.append(TEXTO);
		query.append("= ? ");
		query.append(" WHERE ");
		query.append(ID);
		query.append("= ?");
		Connection con = Conexao.getConexao();
		PreparedStatement statement = null;
		try {
			statement = con.prepareStatement(query.toString());
			SQLUtil.setValorPpreparedStatement(statement, 1, mensagemRecebida.getUsuarioRemetente().getId(), java.sql.Types.INTEGER);
			SQLUtil.setValorPpreparedStatement(statement, 2, mensagemRecebida.getUsuarioDestinatario().getId(), java.sql.Types.INTEGER);
			SQLUtil.setValorPpreparedStatement(statement, 3, mensagemRecebida.getVisualizada(), java.sql.Types.BOOLEAN);			
			SQLUtil.setValorPpreparedStatement(statement, 4, mensagemRecebida.getData(), java.sql.Types.TIMESTAMP);
			SQLUtil.setValorPpreparedStatement(statement, 5, mensagemRecebida.getAssunto(), java.sql.Types.VARCHAR);
			SQLUtil.setValorPpreparedStatement(statement, 6, mensagemRecebida.getTexto(), java.sql.Types.VARCHAR);
			SQLUtil.setValorPpreparedStatement(statement, 7, mensagemRecebida.getId(), java.sql.Types.INTEGER);
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
	public void visualizar(MensagemRecebida mensagemRecebida) throws SQLException, Exception {
		StringBuffer query = new StringBuffer();
		query.append("UPDATE ");
		query.append(MENSAGEM_RECEBIDA);
		query.append(" SET ");
		query.append(VISUALIZADA);	
		query.append("= ? ");		
		query.append(" WHERE ");
		query.append(ID);
		query.append("= ?");
		Connection con = Conexao.getConexao();
		PreparedStatement statement = null;
		try {
			statement = con.prepareStatement(query.toString());
			SQLUtil.setValorPpreparedStatement(statement, 1, Boolean.TRUE, java.sql.Types.BOOLEAN);
			SQLUtil.setValorPpreparedStatement(statement, 2, mensagemRecebida.getId(), java.sql.Types.INTEGER);
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
	public void salvarListaMensagemRecebida(List<MensagemRecebida> listaMensagemRecebida) throws SQLException, Exception {
		StringBuffer query = new StringBuffer();
		query.append("INSERT INTO "); 
		query.append(MENSAGEM_RECEBIDA);
		query.append("("); 
		query.append(ID_USUARIO_REMETENTE);	
		query.append(",");
		query.append(ID_USUARIO_DESTINATARIO);
		query.append(",");
		query.append(VISUALIZADA);
		query.append(",");	
		query.append(DATA);
		query.append(",");	
		query.append(ASSUNTO);
		query.append(",");		
		query.append(TEXTO);
		query.append(") ");
		query.append("VALUES(?,?,?,?,?,?)");
		PreparedStatement statement = null;		
		Connection con = Conexao.getConexao();
		con.setAutoCommit(Boolean.FALSE);
		List<Usuario> listaUsuarioDestinatarios = new ArrayList<Usuario>();
		try {			
			statement = con.prepareStatement(query.toString());
			for (MensagemRecebida mensagemRecebida : listaMensagemRecebida) {
				SQLUtil.setValorPpreparedStatement(statement, 1, mensagemRecebida.getUsuarioRemetente().getId(), java.sql.Types.INTEGER);
				SQLUtil.setValorPpreparedStatement(statement, 2, mensagemRecebida.getUsuarioDestinatario().getId(), java.sql.Types.INTEGER);	
				SQLUtil.setValorPpreparedStatement(statement, 3, mensagemRecebida.getVisualizada(), java.sql.Types.BOOLEAN);			
				SQLUtil.setValorPpreparedStatement(statement, 4, mensagemRecebida.getData(), java.sql.Types.TIMESTAMP);
				SQLUtil.setValorPpreparedStatement(statement, 5, mensagemRecebida.getAssunto(), java.sql.Types.VARCHAR);
				SQLUtil.setValorPpreparedStatement(statement, 6, mensagemRecebida.getTexto(), java.sql.Types.VARCHAR);
				// Add usuários pois será usado para persistir a lista de usuaários enviados.
				listaUsuarioDestinatarios.add(mensagemRecebida.getUsuarioDestinatario());
				statement.addBatch();
			}	
			// Código que constrói e salva uma msg que representa a msg enviada.
			MensagemEnviada mensagemEnviada = new MensagemEnviada();
			mensagemEnviada.setAssunto(listaMensagemRecebida.get(0).getAssunto());
			mensagemEnviada.setTexto(listaMensagemRecebida.get(0).getTexto());
			mensagemEnviada.setData(listaMensagemRecebida.get(0).getData());
			mensagemEnviada.setUsuarioRemetente(listaMensagemRecebida.get(0).getUsuarioRemetente());
			mensagemEnviada.setListaUsuariosDestinatarios(listaUsuarioDestinatarios);
			this.mensagemEnviadaDAO.salvar(mensagemEnviada,con);
			statement.executeBatch();
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

	public UsuarioDAOImpl getUsuarioDAO() {
		return usuarioDAO;
	}

	public void setUsuarioDAO(UsuarioDAOImpl usuarioDAO) {
		this.usuarioDAO = usuarioDAO;
	}

	public MensagemEnviadaDAO getMensagemEnviadaDAO() {
		return mensagemEnviadaDAO;
	}

	public void setMensagemEnviadaDAO(MensagemEnviadaDAO mensagemEnviadaDAO) {
		this.mensagemEnviadaDAO = mensagemEnviadaDAO;
	}	
}
