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
import br.com.condominiosvirtuais.entity.Usuario;
import br.com.condominiosvirtuais.entity.UsuariosRecebidosMensagem;
import br.com.condominiosvirtuais.persistence.MensagemEnviadaDAO;
import br.com.condominiosvirtuais.persistence.UsuarioDAO;
import br.com.condominiosvirtuais.persistence.UsuariosRecebidosMensagemDAO;
import br.com.condominiosvirtuais.util.SQLUtil;

public class MensagemEnviadaDAOImpl implements MensagemEnviadaDAO, Serializable {
	
	private static final long serialVersionUID = 1L;

	private static Logger logger = Logger.getLogger(MensagemEnviadaDAOImpl.class);
	
	private static final String MENSAGEM_ENVIADA = "MENSAGEM_ENVIADA";
	
	private static final String ID = "ID";
	
	private static final String ID_USUARIO_REMETENTE = "ID_USUARIO_REMETENTE";
	
	private static final String DATA = "DATA";
	
	private static final String ASSUNTO = "ASSUNTO";
	
	private static final String TEXTO = "TEXTO";
	
	@Inject
	private UsuariosRecebidosMensagemDAO usuariosRecebidosMensagemDAO = null;
	
	@Inject
	private UsuarioDAO usuarioDAO = null;

	public void salvar(MensagemEnviada mensagemEnviada, Connection con) throws SQLException, Exception {
		StringBuffer query = new StringBuffer();
		query.append("INSERT INTO "); 
		query.append(MENSAGEM_ENVIADA);
		query.append("("); 
		query.append(ID_USUARIO_REMETENTE);	
		query.append(",");	
		query.append(DATA);
		query.append(",");	
		query.append(ASSUNTO);
		query.append(",");		
		query.append(TEXTO);
		query.append(") ");
		query.append("VALUES(?,?,?,?)");
		PreparedStatement statement = null;		
		try {			
			statement = con.prepareStatement(query.toString(), PreparedStatement.RETURN_GENERATED_KEYS);
			SQLUtil.setValorPpreparedStatement(statement, 1, mensagemEnviada.getUsuarioRemetente().getId(), java.sql.Types.INTEGER);							
			SQLUtil.setValorPpreparedStatement(statement, 2, mensagemEnviada.getData(), java.sql.Types.TIMESTAMP);
			SQLUtil.setValorPpreparedStatement(statement, 3, mensagemEnviada.getAssunto(), java.sql.Types.VARCHAR);
			SQLUtil.setValorPpreparedStatement(statement, 4, mensagemEnviada.getTexto(), java.sql.Types.VARCHAR);
			statement.executeUpdate();
			ResultSet rs = statement.getGeneratedKeys(); 
			rs.next();
			mensagemEnviada.setId(rs.getInt(1));
			UsuariosRecebidosMensagem usuariosRecebidosMensagem = null;
			for (Usuario usuario : mensagemEnviada.getListaUsuariosDestinatarios()) {
				usuariosRecebidosMensagem = new UsuariosRecebidosMensagem();
				usuariosRecebidosMensagem.setIdMensagemEnviada(mensagemEnviada.getId());
				usuariosRecebidosMensagem.setIdUsuario(usuario.getId());
				this.usuariosRecebidosMensagemDAO.salvar(usuariosRecebidosMensagem,con);			
			}
		} catch (SQLException e) {					
			con.rollback();
			throw e;
		} catch (Exception e) {
			con.rollback();
			throw e;	
		}			
	}

	public void excluir(MensagemEnviada mensagemEnviada) throws SQLException, Exception {
		StringBuffer query = new StringBuffer();
		Connection con = Conexao.getConexao();
		PreparedStatement statement = null;
		con.setAutoCommit(Boolean.FALSE);
		UsuariosRecebidosMensagem usuariosRecebidosMensagem = new UsuariosRecebidosMensagem();
		usuariosRecebidosMensagem.setIdMensagemEnviada(mensagemEnviada.getId());
		this.usuariosRecebidosMensagemDAO.excluir(usuariosRecebidosMensagem, con);
		try {			
			query.append("DELETE FROM ");
			query.append(MENSAGEM_ENVIADA);
			query.append(" WHERE ");		
			query.append(ID);
			query.append(" = ?");
			statement = con.prepareStatement(query.toString());
			SQLUtil.setValorPpreparedStatement(statement, 1, mensagemEnviada.getId(), java.sql.Types.INTEGER);			
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
 
	public List<MensagemEnviada> buscarPorUsuarioRemetente(Usuario usuario) throws SQLException, Exception {
		StringBuffer query = new StringBuffer();
		query.append("SELECT * FROM ");
		query.append(MENSAGEM_ENVIADA);
		query.append(" WHERE ");
		query.append(ID_USUARIO_REMETENTE);		
		query.append(" = ? ");		
		query.append("ORDER BY ");
		query.append(DATA);
		query.append(" DESC");
		query.append(";");
		Connection con = Conexao.getConexao();
		PreparedStatement preparedStatement = null;
		List<MensagemEnviada> listaMensagem = new ArrayList<MensagemEnviada>();
		try {
			preparedStatement = con.prepareStatement(query.toString());
			SQLUtil.setValorPpreparedStatement(preparedStatement, 1, usuario.getId(), java.sql.Types.INTEGER);
			ResultSet resultSet = preparedStatement.executeQuery();
			MensagemEnviada mensagemEnviada = null;	
			List<Usuario> listaUsuariosDestinatarios = null;
			List<UsuariosRecebidosMensagem> listaUsuarioRecebidoMensagem = null;
			Usuario usuarioRemetente;
			Usuario usuarioDestinatario;
			while(resultSet.next()){				
				mensagemEnviada = new MensagemEnviada();	
				listaUsuariosDestinatarios = new ArrayList<Usuario>();
				mensagemEnviada.setId((Integer) SQLUtil.getValorResultSet(resultSet, ID, java.sql.Types.INTEGER));
				mensagemEnviada.setData((Date)(SQLUtil.getValorResultSet(resultSet, DATA, java.sql.Types.TIMESTAMP)));
				mensagemEnviada.setAssunto(String.valueOf(SQLUtil.getValorResultSet(resultSet, ASSUNTO, java.sql.Types.VARCHAR)));
				mensagemEnviada.setTexto(String.valueOf(SQLUtil.getValorResultSet(resultSet, TEXTO, java.sql.Types.VARCHAR)));
				usuarioRemetente = new Usuario();
				usuarioRemetente.setId((Integer) SQLUtil.getValorResultSet(resultSet, ID_USUARIO_REMETENTE, java.sql.Types.INTEGER));
				this.usuarioDAO.buscarEPopularUsuarioPeloId(usuarioRemetente, con);
				mensagemEnviada.setUsuarioRemetente(usuarioRemetente);
				listaUsuarioRecebidoMensagem = this.usuariosRecebidosMensagemDAO.buscar(mensagemEnviada.getId(), con);
				for (UsuariosRecebidosMensagem usuariosRecebidosMensagem : listaUsuarioRecebidoMensagem) {
					usuarioDestinatario = new Usuario();
					usuarioDestinatario.setId(usuariosRecebidosMensagem.getIdUsuario());
					this.usuarioDAO.buscarEPopularUsuarioPeloId(usuarioDestinatario, con);	
					listaUsuariosDestinatarios.add(usuarioDestinatario);
				}
				mensagemEnviada.setListaUsuariosDestinatarios(listaUsuariosDestinatarios);			
				listaMensagem.add(mensagemEnviada);
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

	public UsuariosRecebidosMensagemDAO getUsuariosRecebidosMensagemDAO() {
		return usuariosRecebidosMensagemDAO;
	}

	public void setUsuariosRecebidosMensagemDAO(
			UsuariosRecebidosMensagemDAO usuariosRecebidosMensagemDAO) {
		this.usuariosRecebidosMensagemDAO = usuariosRecebidosMensagemDAO;
	}

	public UsuarioDAO getUsuarioDAO() {
		return usuarioDAO;
	}

	public void setUsuarioDAO(UsuarioDAO usuarioDAO) {
		this.usuarioDAO = usuarioDAO;
	}		
}
