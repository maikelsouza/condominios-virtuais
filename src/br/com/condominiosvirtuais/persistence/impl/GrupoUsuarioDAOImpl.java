package br.com.condominiosvirtuais.persistence.impl;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import br.com.condominiosvirtuais.entity.GrupoUsuario;
import br.com.condominiosvirtuais.persistence.GrupoUsuarioDAO;

public class GrupoUsuarioDAOImpl implements GrupoUsuarioDAO, Serializable {
	
	private static final long serialVersionUID = 1L;

	private static Logger logger = Logger.getLogger(GrupoUsuarioDAOImpl.class);

	private static final String  GRUPO_USUARIO = " GRUPO_USUARIO";
	
	private static final String ID = "ID";	
	
	private static final String NOME = "NOME";
	
		
	public GrupoUsuario buscarPorIdUsuario(Integer idUsuario) throws SQLException , Exception{
		StringBuffer query = new StringBuffer();
		query.append("SELECT * FROM ");
		query.append(GRUPO_USUARIO);
		query.append(" WHERE ");
		query.append(ID);
		query.append(" = ?");		
		Connection con = Conexao.getConexao();
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		GrupoUsuario grupoUsuario = null;		
		try {
			statement = con.prepareStatement(query.toString());
			statement.setInt(1, idUsuario);
			resultSet = statement.executeQuery();
			while(resultSet.next()){
				grupoUsuario = new GrupoUsuario();
				grupoUsuario.setId(resultSet.getInt(ID));
				grupoUsuario.setNome(resultSet.getString(NOME));								
			}
		}catch (SQLException e) {
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
		return grupoUsuario;
	}
}
