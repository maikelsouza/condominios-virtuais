package br.com.condominiosvirtuais.persistence.impl;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.condominiosvirtuais.entity.UsuariosRecebidosMensagem;
import br.com.condominiosvirtuais.persistence.UsuariosRecebidosMensagemDAO;
import br.com.condominiosvirtuais.util.SQLUtil;

public class UsuariosRecebidosMensagemDAOImpl implements UsuariosRecebidosMensagemDAO, Serializable {
	
	private static final long serialVersionUID = 1L;

	private static final String USUARIOS_RECEBIDOS_MENSAGEM = "USUARIOS_RECEBIDOS_MENSAGEM";
	
	private static final String ID = "ID";
	
	private static final String ID_USUARIO = "ID_USUARIO";
	
	private static final String ID_MENSAGEM_ENVIADA = "ID_MENSAGEM_ENVIADA";

	@Override
	public void salvar(UsuariosRecebidosMensagem usuariosRecebidosMensagem, Connection con) throws SQLException, Exception {		
		StringBuffer query = new StringBuffer();
		query.append("INSERT INTO "); 
		query.append(USUARIOS_RECEBIDOS_MENSAGEM); 
		query.append("(");
		query.append(ID_USUARIO);
		query.append(",");
		query.append(ID_MENSAGEM_ENVIADA);
		query.append(") ");
		query.append("VALUES(?,?)");
		PreparedStatement statement = null;		
		try {
			statement = con.prepareStatement(query.toString());
			SQLUtil.setValorPpreparedStatement(statement, 1, usuariosRecebidosMensagem.getIdUsuario(), java.sql.Types.INTEGER);
			SQLUtil.setValorPpreparedStatement(statement, 2, usuariosRecebidosMensagem.getIdMensagemEnviada(), java.sql.Types.INTEGER);
			statement.executeUpdate();
		} catch (SQLException e) {
			con.rollback();
			throw e;
		} catch (Exception e) {
			con.rollback();
			throw e;
		}				
	}

	@Override
	public void excluir(UsuariosRecebidosMensagem usuariosRecebidosMensagem, Connection con) throws SQLException, Exception {
		StringBuffer query = new StringBuffer();
		PreparedStatement statement = null;
		try {			
			query.append("DELETE FROM ");
			query.append(USUARIOS_RECEBIDOS_MENSAGEM);
			query.append(" WHERE ");		
			query.append(ID_MENSAGEM_ENVIADA);
			query.append(" = ?");
			statement = con.prepareStatement(query.toString());
			SQLUtil.setValorPpreparedStatement(statement, 1,usuariosRecebidosMensagem.getIdMensagemEnviada(), java.sql.Types.INTEGER);			
			statement.executeUpdate();
		} catch (SQLException e) {
			con.rollback();
			throw e;
		} catch (Exception e) {
			con.rollback();
			throw e;
		}				
		
	}

	@Override
	public List<UsuariosRecebidosMensagem> buscar(Integer idMensagemEnviada, Connection con) throws SQLException, Exception {
		StringBuffer query = new StringBuffer();
		List<UsuariosRecebidosMensagem> listaUsuariosRecebidoMensagem = null;
		try {
			query.append("SELECT * FROM ");
			query.append(USUARIOS_RECEBIDOS_MENSAGEM);
			query.append(" WHERE ");
			query.append(ID_MENSAGEM_ENVIADA);		
			query.append(" = ?");
			query.append(";");
			PreparedStatement preparedStatement = null;
			preparedStatement = con.prepareStatement(query.toString());
			SQLUtil.setValorPpreparedStatement(preparedStatement, 1, idMensagemEnviada, java.sql.Types.INTEGER);
			ResultSet resultSet = preparedStatement.executeQuery();
			listaUsuariosRecebidoMensagem = new ArrayList<UsuariosRecebidosMensagem>();		
			UsuariosRecebidosMensagem usuariosRecebidosMensagem = null;
			while(resultSet.next()){				
				usuariosRecebidosMensagem = new UsuariosRecebidosMensagem();	
				usuariosRecebidosMensagem.setId((Integer) SQLUtil.getValorResultSet(resultSet, ID, java.sql.Types.INTEGER));
				usuariosRecebidosMensagem.setIdMensagemEnviada((Integer) SQLUtil.getValorResultSet(resultSet, ID_MENSAGEM_ENVIADA, java.sql.Types.INTEGER));
				usuariosRecebidosMensagem.setIdUsuario((Integer) SQLUtil.getValorResultSet(resultSet, ID_USUARIO, java.sql.Types.INTEGER));						
				listaUsuariosRecebidoMensagem.add(usuariosRecebidosMensagem);
			}
		} catch (SQLException e) {
			con.rollback();
			throw e;
		} catch (Exception e) {
			con.rollback();
			throw e;
		}		
		return listaUsuariosRecebidoMensagem;
	}
}
