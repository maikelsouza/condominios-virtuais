package br.com.condominiosvirtuais.persistence.impl;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.apache.log4j.Logger;

import br.com.condominiosvirtuais.entity.Bloco;
import br.com.condominiosvirtuais.entity.Condominio;
import br.com.condominiosvirtuais.entity.GestorCondominio;
import br.com.condominiosvirtuais.entity.Usuario;
import br.com.condominiosvirtuais.enumeration.TipoGestorCondominioEnum;
import br.com.condominiosvirtuais.enumeration.TipoGrupoUsuarioEnum;
import br.com.condominiosvirtuais.persistence.GestorCondominioDAO;
import br.com.condominiosvirtuais.util.SQLUtil;

public class GestorCondominioDAOImpl implements GestorCondominioDAO, Serializable{
	
	private static final long serialVersionUID = 1L;

	private static Logger logger = Logger.getLogger(GestorCondominioDAOImpl.class);
	
	private static final String GESTOR_CONDOMINIO = "GESTOR_CONDOMINIO";

	private static final String ID = "ID";
	
	private static final String  ID_CONDOMINIO = "ID_CONDOMINIO";
	
	private static final String  ID_BLOCO = "ID_BLOCO";
	
	private static final String  ID_USUARIO = "ID_USUARIO";
	
	private static final String  TIPO_CONDOMINO = "TIPO_CONDOMINO";
	
	@Inject
	private UsuarioDAOImpl usuarioDAO = null;
	
	
	public List<GestorCondominio> buscarListaGestoresCondominioPorCondominio(Condominio condominio) throws SQLException, Exception{		
		StringBuffer query = new StringBuffer();
		query.append("SELECT * FROM ");
		query.append(GESTOR_CONDOMINIO);
		query.append(" WHERE ");
		query.append(ID_CONDOMINIO);
		query.append(" = ? ");		
		query.append(";");
		Connection con = Conexao.getConexao();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		List<GestorCondominio> listaGestorCondominio = new ArrayList<GestorCondominio>();
		GestorCondominio gestorCondominio = null;
		try {
			preparedStatement = con.prepareStatement(query.toString());
			preparedStatement.setInt(1, condominio.getId());
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()){
				gestorCondominio = new GestorCondominio();
				gestorCondominio.setId(resultSet.getInt(ID));
				gestorCondominio.setIdCondominio(resultSet.getInt(ID_CONDOMINIO));
				gestorCondominio.setIdUsuario(resultSet.getInt(ID_USUARIO));
				gestorCondominio.setIdBloco(resultSet.getInt(ID_BLOCO));
				gestorCondominio.setTipoCondomino(resultSet.getInt(TIPO_CONDOMINO));
				listaGestorCondominio.add(gestorCondominio);
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
		return listaGestorCondominio;
	}
								  							  
	public List<GestorCondominio> buscarListaGestoresCondominioPorCondominio(Condominio condominio, Connection con) throws SQLException, Exception{		
		StringBuffer query = new StringBuffer();
		query.append("SELECT * FROM ");
		query.append(GESTOR_CONDOMINIO);
		query.append(" WHERE ");
		query.append(ID_CONDOMINIO);
		query.append(" = ? ");		
		query.append(";");		
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		List<GestorCondominio> listaGestorCondominio = new ArrayList<GestorCondominio>();
		GestorCondominio gestorCondominio = null;
		try {
			preparedStatement = con.prepareStatement(query.toString());
			preparedStatement.setInt(1, condominio.getId());
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()){
				gestorCondominio = new GestorCondominio();
				gestorCondominio.setId(resultSet.getInt(ID));
				gestorCondominio.setIdCondominio(resultSet.getInt(ID_CONDOMINIO));
				gestorCondominio.setIdUsuario(resultSet.getInt(ID_USUARIO));
				gestorCondominio.setIdBloco(resultSet.getInt(ID_BLOCO));
				gestorCondominio.setTipoCondomino(resultSet.getInt(TIPO_CONDOMINO));
				listaGestorCondominio.add(gestorCondominio);
			}
		} catch (SQLException e) {
			con.rollback();
			throw e;
		} catch (Exception e) {
			con.rollback();
			throw e;
		}
		return listaGestorCondominio;
	}
	
	public void salvarGestorCondominio(GestorCondominio gestorCondominio, Connection con) throws SQLException, Exception{
		// Código que atualiza o grupo de usuários do usuário que virou um gestor do condomínio. 		
		Usuario usuario = this.usuarioDAO.buscarPorId(gestorCondominio.getIdUsuario());
		if(gestorCondominio.getTipoCondomino() == TipoGestorCondominioEnum.SINDICO_GERAL.getGestorCondominio()){
			usuario.setIdGrupoUsuario(TipoGrupoUsuarioEnum.SINDICO.getGrupoUsuario());
		}else{
			usuario.setIdGrupoUsuario(TipoGrupoUsuarioEnum.CONDOMINO.getGrupoUsuario());	
		}
		this.usuarioDAO.atualizarUsuario(usuario, con);
		StringBuffer query = new StringBuffer();
		query.append("INSERT INTO "); 
		query.append(GESTOR_CONDOMINIO);
		query.append("("); 
		query.append(ID_USUARIO); 
		query.append(",");
		query.append(TIPO_CONDOMINO); 
		if(gestorCondominio.getIdCondominio() != null){
			query.append(",");
			query.append(ID_CONDOMINIO);
		}
		if(gestorCondominio.getIdBloco() != null){
			query.append(",");			
			query.append(ID_BLOCO); 
		}
		query.append(") ");
		query.append("VALUES(?,?");
		if(gestorCondominio.getIdCondominio() != null){
			query.append(",?");
		}
		if(gestorCondominio.getIdBloco() != null){
			query.append(",?");
		}
		query.append(")");
		PreparedStatement statement = null;
		try {			
			statement = con.prepareStatement(query.toString());
			statement.setInt(1,gestorCondominio.getIdUsuario());
			statement.setInt(2,gestorCondominio.getTipoCondomino());
			if(gestorCondominio.getIdCondominio() != null && gestorCondominio.getIdBloco() != null){
				statement.setInt(3,gestorCondominio.getIdCondominio());				
				statement.setInt(4,gestorCondominio.getIdBloco());				
			}else if (gestorCondominio.getIdCondominio() == null && gestorCondominio.getIdBloco() != null){
				statement.setInt(3,gestorCondominio.getIdBloco());
			}else if(gestorCondominio.getIdCondominio() != null && gestorCondominio.getIdBloco() == null){
				statement.setInt(3,gestorCondominio.getIdCondominio());
			}			
			statement.executeUpdate();			
		} catch (SQLException e) {
			con.rollback();
			throw e;
		} catch (Exception e) {
			con.rollback();
			throw e;
		}
	}
	
	public void salvarGestorCondominioBloco(GestorCondominio gestorCondominio, Connection con) throws SQLException, Exception{
		StringBuffer query = new StringBuffer();
		query.append("INSERT INTO "); 
		query.append(GESTOR_CONDOMINIO);
		query.append("("); 
		query.append(ID_USUARIO); 
		query.append(",");
		query.append(TIPO_CONDOMINO); 
		if(gestorCondominio.getIdCondominio() != null){
			query.append(",");
			query.append(ID_CONDOMINIO);
		}
		if(gestorCondominio.getIdBloco() != null){
			query.append(",");			
			query.append(ID_BLOCO); 
		}
		query.append(") ");
		query.append("VALUES(?,?");
		if(gestorCondominio.getIdCondominio() != null){
			query.append(",?");
		}
		if(gestorCondominio.getIdBloco() != null){
			query.append(",?");
		}
		query.append(")");
		PreparedStatement statement = null;
		try {			
			statement = con.prepareStatement(query.toString());
			SQLUtil.setValorPpreparedStatement(statement, 1, gestorCondominio.getIdUsuario(), java.sql.Types.INTEGER);
			SQLUtil.setValorPpreparedStatement(statement, 2, gestorCondominio.getTipoCondomino(), java.sql.Types.INTEGER);			
			if(gestorCondominio.getIdCondominio() != null && gestorCondominio.getIdBloco() != null){
				SQLUtil.setValorPpreparedStatement(statement, 3, gestorCondominio.getIdCondominio(), java.sql.Types.INTEGER);
				SQLUtil.setValorPpreparedStatement(statement, 4, gestorCondominio.getIdBloco(), java.sql.Types.INTEGER);		
			}else if (gestorCondominio.getIdCondominio() == null && gestorCondominio.getIdBloco() != null){
				SQLUtil.setValorPpreparedStatement(statement, 3, gestorCondominio.getIdBloco(), java.sql.Types.INTEGER);
			}else if(gestorCondominio.getIdCondominio() != null && gestorCondominio.getIdBloco() == null){
				SQLUtil.setValorPpreparedStatement(statement, 4, gestorCondominio.getIdCondominio(), java.sql.Types.INTEGER);
			}			
			statement.executeUpdate();			
		} catch (SQLException e) {
			con.rollback();
			throw e;
		} catch (Exception e) {
			con.rollback();
			throw e;
		}
	}
	
	public void atualizarGestorCondominioPorCondominio(GestorCondominio gestorCondominio, Connection con) throws SQLException, Exception{
		StringBuffer query = new StringBuffer();
		query.append("UPDATE ");
		query.append(GESTOR_CONDOMINIO);
		query.append(" SET ");
		query.append(ID_USUARIO);
		query.append("= ?, ");
		query.append(TIPO_CONDOMINO);
		query.append("= ?, ");
		query.append(ID_BLOCO);
		query.append("= ? ");
		query.append("WHERE ");
		query.append(ID_CONDOMINIO);
		query.append("= ?");	
		PreparedStatement statement = null;
		try {			
			statement = con.prepareStatement(query.toString());
			statement.setInt(1,gestorCondominio.getIdUsuario());
			statement.setInt(2,gestorCondominio.getTipoCondomino());
			statement.setInt(3,gestorCondominio.getIdBloco());
			statement.setInt(4,gestorCondominio.getIdCondominio());
			statement.executeUpdate();
		} catch (SQLException e) {
			con.rollback();
			throw e;
		} catch (Exception e) {
			con.rollback();
			throw e;
		}	
	}
	
	public void excluirGestorCondominioPorCondominio(Condominio condominio, Connection con) throws SQLException, Exception{
		StringBuffer query = new StringBuffer();	
		PreparedStatement statement = null;		
		// Código que atualiza os usuarios, que não serão mais gestores, setando o seu grupo para condônino.
		List<GestorCondominio> listaGestores = this.buscarListaGestoresCondominioPorCondominio(condominio);
		Usuario usuario = null;
		for (GestorCondominio gestorCondominio : listaGestores) {
			usuario = this.usuarioDAO.buscarPorId(gestorCondominio.getIdUsuario());
			usuario.setIdGrupoUsuario(TipoGrupoUsuarioEnum.CONDOMINO.getGrupoUsuario());
			this.usuarioDAO.atualizarUsuario(usuario, con);		
		}
		try {			
			query.append("DELETE FROM ");
			query.append(GESTOR_CONDOMINIO);
			query.append(" WHERE ");		
			query.append(ID_CONDOMINIO);
			query.append(" = ?");
			statement = con.prepareStatement(query.toString());
			statement.setInt(1, condominio.getId());
			statement.executeUpdate();			
		} catch (SQLException e) {
			con.rollback();
			throw e;
		} catch (Exception e) {
			con.rollback();
			throw e;
		}
		
	}
	
	public void excluirGestorCondominioPorBloco(Bloco bloco, Connection con) throws SQLException, Exception{
		StringBuffer query = new StringBuffer();	
		PreparedStatement statement = null;
		try {			
			query.append("DELETE FROM ");
			query.append(GESTOR_CONDOMINIO);
			query.append(" WHERE ");		
			query.append(ID_BLOCO);
			query.append(" = ?");
			statement = con.prepareStatement(query.toString());
			statement.setInt(1, bloco.getId());
			statement.executeUpdate();			
		} catch (SQLException e) {
			con.rollback();
			throw e;
		} catch (Exception e) {
			con.rollback();
			throw e;
		}
		
	}
	
	public List<GestorCondominio> buscarListaGestoresCondominioPorBloco(Bloco bloco, Connection con) throws SQLException, Exception{		
		StringBuffer query = new StringBuffer();
		query.append("SELECT * FROM ");
		query.append(GESTOR_CONDOMINIO);
		query.append(" WHERE ");
		query.append(ID_BLOCO);
		query.append(" = ? ");		
		query.append(";");
		//Connection con = Conexao.getConexao();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		List<GestorCondominio> listaGestorCondominio = new ArrayList<GestorCondominio>();
		GestorCondominio gestorCondominio = null;
		try {
			preparedStatement = con.prepareStatement(query.toString());
			preparedStatement.setInt(1, bloco.getId());
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()){
				gestorCondominio = new GestorCondominio();
				gestorCondominio.setId(resultSet.getInt(ID));
				gestorCondominio.setIdCondominio(resultSet.getInt(ID_CONDOMINIO));
				gestorCondominio.setIdUsuario(resultSet.getInt(ID_USUARIO));
				gestorCondominio.setIdBloco(resultSet.getInt(ID_BLOCO));
				gestorCondominio.setTipoCondomino(resultSet.getInt(TIPO_CONDOMINO));
				listaGestorCondominio.add(gestorCondominio);
			}
		} catch (SQLException e) {
			con.rollback();
			throw e;
		} catch (Exception e) {
			con.rollback();
			throw e;
		}
		return listaGestorCondominio;
	}
	
	public List<GestorCondominio> buscarListaGestoresCondominioPorBloco(Bloco bloco) throws SQLException, Exception{		
		StringBuffer query = new StringBuffer();
		query.append("SELECT * FROM ");
		query.append(GESTOR_CONDOMINIO);
		query.append(" WHERE ");
		query.append(ID_BLOCO);
		query.append(" = ? ");		
		query.append(";");
		Connection con = Conexao.getConexao();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		List<GestorCondominio> listaGestorCondominio = new ArrayList<GestorCondominio>();
		GestorCondominio gestorCondominio = null;
		try {
			preparedStatement = con.prepareStatement(query.toString());
			preparedStatement.setInt(1, bloco.getId());
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()){
				gestorCondominio = new GestorCondominio();
				gestorCondominio.setId(resultSet.getInt(ID));
				gestorCondominio.setIdCondominio(resultSet.getInt(ID_CONDOMINIO));
				gestorCondominio.setIdUsuario(resultSet.getInt(ID_USUARIO));
				gestorCondominio.setIdBloco(resultSet.getInt(ID_BLOCO));
				gestorCondominio.setTipoCondomino(resultSet.getInt(TIPO_CONDOMINO));
				listaGestorCondominio.add(gestorCondominio);
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
		return listaGestorCondominio;
	}
	
	public void salvarGestorCondominioSindicoProfissional(GestorCondominio gestorCondominio, Connection con) throws SQLException, Exception {
		StringBuffer query = new StringBuffer();
		query.append("INSERT INTO "); 
		query.append(GESTOR_CONDOMINIO);
		query.append("("); 
		query.append(ID_USUARIO); 
		query.append(",");
		query.append(TIPO_CONDOMINO); 
		query.append(",");
		query.append(ID_CONDOMINIO);
		query.append(") ");
		query.append("VALUES(?,?,?)");
		PreparedStatement statement = null;
		try {			
			statement = con.prepareStatement(query.toString());
			SQLUtil.setValorPpreparedStatement(statement, 1, gestorCondominio.getIdUsuario(), java.sql.Types.INTEGER);
			SQLUtil.setValorPpreparedStatement(statement, 2, gestorCondominio.getTipoCondomino(), java.sql.Types.INTEGER);
			SQLUtil.setValorPpreparedStatement(statement, 3, gestorCondominio.getIdCondominio(), java.sql.Types.INTEGER);
			statement.executeUpdate();			
		} catch (SQLException e) {
			con.rollback();
			throw e;
		} catch (Exception e) {
			con.rollback();
			throw e;
		}		
	}
}
