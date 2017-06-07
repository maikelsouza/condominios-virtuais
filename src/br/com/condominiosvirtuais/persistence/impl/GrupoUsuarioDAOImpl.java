package br.com.condominiosvirtuais.persistence.impl;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.inject.Inject;

import org.apache.log4j.Logger;

import br.com.condominiosvirtuais.entity.GrupoUsuario;
import br.com.condominiosvirtuais.exception.BusinessException;
import br.com.condominiosvirtuais.persistence.GrupoUsuarioDAO;
import br.com.condominiosvirtuais.persistence.GrupoUsuarioTelaDAO;
import br.com.condominiosvirtuais.util.SQLUtil;

public class GrupoUsuarioDAOImpl implements GrupoUsuarioDAO, Serializable {
	
	private static final long serialVersionUID = 1L;

	private static Logger logger = Logger.getLogger(GrupoUsuarioDAOImpl.class);
	
	@Inject
	private GrupoUsuarioTelaDAO grupoUsuarioTelaDAO;

	private static final String  GRUPO_USUARIO = " GRUPO_USUARIO";
	
	private static final String ID = "ID";	
	
	private static final String NOME = "NOME";
	
	private static final String DESCRICAO = "DESCRICAO";
	
	private static final String ATIVO = "ATIVO";
	
	private static final String ID_CONDOMINIO = "ID_CONDOMINIO";
	
	private static final String ID_SINDICO_PROFISSIONAL = "ID_SINDICO_PROFISSIONAL";
	
	private static final String ID_ESCRITORIO_CONTABILIDADE = "ID_ESCRITORIO_CONTABILIDADE";
	
	private static final String FK_GRUPO_USUARIO_TELA_ID_GRUPO_USUARIO_GRUPO_USUARIO_ID = "FK_GRUPO_USUARIO_TELA_ID_GRUPO_USUARIO_GRUPO_USUARIO_ID";
	

	
		
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
			SQLUtil.setValorPpreparedStatement(statement, 1,idUsuario, java.sql.Types.INTEGER);
			resultSet = statement.executeQuery();
			while(resultSet.next()){
				grupoUsuario = new GrupoUsuario();
				grupoUsuario.setId((Integer) SQLUtil.getValorResultSet(resultSet, ID, java.sql.Types.INTEGER));
				grupoUsuario.setNome(String.valueOf(SQLUtil.getValorResultSet(resultSet, NOME, java.sql.Types.VARCHAR)));
				grupoUsuario.setDescricao(String.valueOf(SQLUtil.getValorResultSet(resultSet, DESCRICAO, java.sql.Types.VARCHAR)));
				grupoUsuario.setAtivo((Boolean) SQLUtil.getValorResultSet(resultSet, ATIVO, java.sql.Types.BOOLEAN));
				grupoUsuario.setIdCondominio((Integer) SQLUtil.getValorResultSet(resultSet, ID_CONDOMINIO, java.sql.Types.INTEGER));
				grupoUsuario.setIdSindicoProfissional((Integer) SQLUtil.getValorResultSet(resultSet, ID_SINDICO_PROFISSIONAL, java.sql.Types.INTEGER));
				grupoUsuario.setIdEscritorioContabilidade((Integer) SQLUtil.getValorResultSet(resultSet, ID_ESCRITORIO_CONTABILIDADE, java.sql.Types.INTEGER));
										
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




	@Override
	public void excluir(Integer idGrupoUsuario) throws SQLException,BusinessException, Exception {
		Connection con = Conexao.getConexao();
		StringBuffer query = new StringBuffer();
		con.setAutoCommit(Boolean.FALSE);
		PreparedStatement statement = null;
		try {			
			this.grupoUsuarioTelaDAO.excluirPorIdGrupoUsuario(idGrupoUsuario, con);
			query.append("DELETE FROM ");
			query.append(GRUPO_USUARIO);
			query.append(" WHERE ");		
			query.append(ID);
			query.append(" = ?");
			statement = con.prepareStatement(query.toString());
			SQLUtil.setValorPpreparedStatement(statement, 1, idGrupoUsuario, java.sql.Types.INTEGER);
			statement.executeUpdate();	
			con.commit();
		} catch (SQLException e) {
			if (e.getMessage().contains(FK_GRUPO_USUARIO_TELA_ID_GRUPO_USUARIO_GRUPO_USUARIO_ID)){
				throw new BusinessException("msg.grupoUsuario.excluirTelaAssociadas");
			}else{		
				throw e;		
			}
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
}
