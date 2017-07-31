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

import br.com.condominiosvirtuais.entity.GrupoUsuario;
import br.com.condominiosvirtuais.entity.GrupoUsuarioTela;
import br.com.condominiosvirtuais.entity.GrupoUsuarioTelaAba;
import br.com.condominiosvirtuais.entity.Tela;
import br.com.condominiosvirtuais.exception.BusinessException;
import br.com.condominiosvirtuais.persistence.GrupoUsuarioDAO;
import br.com.condominiosvirtuais.persistence.GrupoUsuarioTelaAbaDAO;
import br.com.condominiosvirtuais.persistence.GrupoUsuarioTelaDAO;
import br.com.condominiosvirtuais.persistence.TelaDAO;
import br.com.condominiosvirtuais.util.SQLUtil;
import br.com.condominiosvirtuais.vo.AbaVO;
import br.com.condominiosvirtuais.vo.TelaVO;

public class GrupoUsuarioDAOImpl implements GrupoUsuarioDAO, Serializable {
	
	private static final long serialVersionUID = 1L;

	private static Logger logger = Logger.getLogger(GrupoUsuarioDAOImpl.class);
	
	@Inject
	private GrupoUsuarioTelaDAO grupoUsuarioTelaDAO;
	
	@Inject
	private GrupoUsuarioTelaAbaDAO grupoUsuarioTelaAbaDAO;
	
	@Inject
	private TelaDAO telaDAO;

	private static final String  GRUPO_USUARIO = " GRUPO_USUARIO";
	
	private static final String ID = "ID";	
	
	private static final String NOME = "NOME";
	
	private static final String DESCRICAO = "DESCRICAO";
	
	private static final String SITUACAO = "SITUACAO";
	
	private static final String ID_CONDOMINIO = "ID_CONDOMINIO";
	
	private static final String ID_SINDICO_PROFISSIONAL = "ID_SINDICO_PROFISSIONAL";
	
	private static final String ID_ESCRITORIO_CONTABILIDADE = "ID_ESCRITORIO_CONTABILIDADE";
	
	private static final String FK_GRUPO_USUARIO_TELA_ID_GRUPO_USUARIO_GRUPO_USUARIO_ID = "FK_GRUPO_USUARIO_TELA_ID_GRUPO_USUARIO_GRUPO_USUARIO_ID";
	
	private static final String FK_GRUPO_USUARIO_ID_CONDOMINIO_CONDOMINIO_ID = "FK_GRUPO_USUARIO_ID_CONDOMINIO_CONDOMINIO_ID";
	
	private static final String FK_GRUPO_USUARIO_ID_SINDICO_PROFISSIONAL_SINDICO_PROFISSIONAL_ID = "FK_GRUPO_USUARIO_ID_SINDICO_PROFISSIONAL_SINDICO_PROFISSIONAL_ID";
	
	private static final String FK_GRUPO_USUARIO_ID_ESCRITORIO_CONTABILI_ESCRITORIO_CONTABILI_ID = "FK_GRUPO_USUARIO_ID_ESCRITORIO_CONTABILI_ESCRITORIO_CONTABILI_ID";
	

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
				grupoUsuario.setSituacao((Boolean) SQLUtil.getValorResultSet(resultSet, SITUACAO, java.sql.Types.BOOLEAN));
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

	@Override
	public void salvar(GrupoUsuario grupoUsuario) throws SQLException,BusinessException, Exception {
		GrupoUsuarioTela grupoUsuarioTela = null;
		GrupoUsuarioTelaAba grupoUsuarioTelaAba = null;
		StringBuffer query = new StringBuffer();
		query.append("INSERT INTO "); 
		query.append(GRUPO_USUARIO);
		query.append("("); 
		query.append(NOME);
		query.append(",");
		query.append(DESCRICAO);
		query.append(",");
		query.append(SITUACAO);
		query.append(",");
		query.append(ID_CONDOMINIO);
		query.append(",");
		query.append(ID_SINDICO_PROFISSIONAL);
		query.append(",");
		query.append(ID_ESCRITORIO_CONTABILIDADE);
		query.append(") ");
		query.append("VALUES(?,?,?,?,?,?)");
		PreparedStatement statement = null;		
		Connection con = Conexao.getConexao();
		con.setAutoCommit(Boolean.FALSE);
		Integer idGrupoUsuario = 0;
		try {		
			statement = con.prepareStatement(query.toString(),PreparedStatement.RETURN_GENERATED_KEYS);			
			SQLUtil.setValorPpreparedStatement(statement, 1, grupoUsuario.getNome(), java.sql.Types.VARCHAR);
			SQLUtil.setValorPpreparedStatement(statement, 2, grupoUsuario.getDescricao(), java.sql.Types.VARCHAR);
			SQLUtil.setValorPpreparedStatement(statement, 3, grupoUsuario.getSituacao(), java.sql.Types.BOOLEAN);
			SQLUtil.setValorPpreparedStatement(statement, 4, grupoUsuario.getIdCondominio(), java.sql.Types.INTEGER);
			SQLUtil.setValorPpreparedStatement(statement, 5, grupoUsuario.getIdSindicoProfissional(), java.sql.Types.INTEGER);
			SQLUtil.setValorPpreparedStatement(statement, 6, grupoUsuario.getIdEscritorioContabilidade(), java.sql.Types.INTEGER);
			statement.executeUpdate();
			ResultSet rs = statement.getGeneratedKeys();
			rs.next();
			idGrupoUsuario = rs.getInt(1);
			for (TelaVO telaVO : grupoUsuario.getListaTelaAcesso()) {
				grupoUsuarioTela = new GrupoUsuarioTela();
				grupoUsuarioTela.setIdGrupoUsuario(idGrupoUsuario);
				grupoUsuarioTela.setIdTela(telaVO.getIdTela());
				grupoUsuarioTela.setAcao(telaVO.getAcao());
				this.grupoUsuarioTelaDAO.salvar(grupoUsuarioTela, con);
				for (AbaVO abaVO : telaVO.getListaAbasVOTela()) {
					grupoUsuarioTelaAba = new GrupoUsuarioTelaAba();
					grupoUsuarioTelaAba.setIdAba(abaVO.getIdAba());
					grupoUsuarioTelaAba.setIdGrupoUsuario(idGrupoUsuario);
					grupoUsuarioTelaAba.setIdTela(telaVO.getIdTela());
					grupoUsuarioTelaAba.setAcao(abaVO.getAcao());
					this.grupoUsuarioTelaAbaDAO.salvar(grupoUsuarioTelaAba, con);
				}
			}
			con.commit();
		} catch (SQLException e) {					
			if (e.getMessage().contains(FK_GRUPO_USUARIO_ID_CONDOMINIO_CONDOMINIO_ID)){
				throw new BusinessException("msg.grupoUsuario.salvarIdCondominioAssociado");
			}else if(e.getMessage().contains(FK_GRUPO_USUARIO_ID_SINDICO_PROFISSIONAL_SINDICO_PROFISSIONAL_ID)){
				throw new BusinessException("msg.grupoUsuario.salvarIdSindicoProfissionalAssociado");
			}else if(e.getMessage().contains(FK_GRUPO_USUARIO_ID_ESCRITORIO_CONTABILI_ESCRITORIO_CONTABILI_ID)){
				throw new BusinessException("msg.grupoUsuario.salvarIdEscritorioContabilidadeAssociado");
			}else{
				throw e;
			}
		} catch (Exception e) {					
			throw e;	
		}finally{
			try{
				statement.close();
				con.close();
			}catch (SQLException e) {
				logger.error("erro sqlstate "+e.getSQLState(), e);
			}				
		}			

		
	}


	@Override
	public void atualizar(GrupoUsuario grupoUsuario) throws SQLException, Exception {
		// TODO Auto-generated method stub
		
	}


	@Override
	public List<GrupoUsuario> buscarPorIdCondominioESituacao(Integer idCondominio, Boolean situacao) throws SQLException, Exception {
		StringBuffer query = new StringBuffer();
		query.append("SELECT * FROM ");
		query.append(GRUPO_USUARIO);
		query.append(" WHERE ");
		query.append(ID_CONDOMINIO);
		query.append(" = ?");		
		query.append(" AND ");
		query.append(SITUACAO);
		query.append(" = ?");
		query.append(" ORDER BY ");
		query.append(NOME);
		Connection con = Conexao.getConexao();
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		List<GrupoUsuario> listaGrupoUsuario = new ArrayList<GrupoUsuario>();
		GrupoUsuario grupoUsuario = null;		
		try {
			statement = con.prepareStatement(query.toString());
			SQLUtil.setValorPpreparedStatement(statement, 1,idCondominio, java.sql.Types.INTEGER);
			SQLUtil.setValorPpreparedStatement(statement, 2,situacao, java.sql.Types.BOOLEAN);
			resultSet = statement.executeQuery();
			while(resultSet.next()){
				grupoUsuario = new GrupoUsuario();
				grupoUsuario.setId((Integer) SQLUtil.getValorResultSet(resultSet, ID, java.sql.Types.INTEGER));
				grupoUsuario.setNome(String.valueOf(SQLUtil.getValorResultSet(resultSet, NOME, java.sql.Types.VARCHAR)));
				grupoUsuario.setDescricao(String.valueOf(SQLUtil.getValorResultSet(resultSet, DESCRICAO, java.sql.Types.VARCHAR)));
				grupoUsuario.setSituacao((Boolean) SQLUtil.getValorResultSet(resultSet, SITUACAO, java.sql.Types.BOOLEAN));
				grupoUsuario.setIdCondominio((Integer) SQLUtil.getValorResultSet(resultSet, ID_CONDOMINIO, java.sql.Types.INTEGER));
				grupoUsuario.setIdSindicoProfissional((Integer) SQLUtil.getValorResultSet(resultSet, ID_SINDICO_PROFISSIONAL, java.sql.Types.INTEGER));
				grupoUsuario.setIdEscritorioContabilidade((Integer) SQLUtil.getValorResultSet(resultSet, ID_ESCRITORIO_CONTABILIDADE, java.sql.Types.INTEGER));
				listaGrupoUsuario.add(grupoUsuario);
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
		return listaGrupoUsuario;
	}


	@Override
	public List<GrupoUsuario> buscarPorIdCondominio(Integer idCondominio) throws SQLException, Exception {
		StringBuffer query = new StringBuffer();
		query.append("SELECT * FROM ");
		query.append(GRUPO_USUARIO);
		query.append(" WHERE ");
		query.append(ID_CONDOMINIO);
		query.append(" = ?");
		query.append(" ORDER BY ");
		query.append(NOME);
		Connection con = Conexao.getConexao();
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		List<GrupoUsuario> listaGrupoUsuario = new ArrayList<GrupoUsuario>();
		GrupoUsuario grupoUsuario = null;		
		try {
			statement = con.prepareStatement(query.toString());
			SQLUtil.setValorPpreparedStatement(statement, 1,idCondominio, java.sql.Types.INTEGER);
			resultSet = statement.executeQuery();
			while(resultSet.next()){
				grupoUsuario = new GrupoUsuario();
				grupoUsuario.setId((Integer) SQLUtil.getValorResultSet(resultSet, ID, java.sql.Types.INTEGER));
				grupoUsuario.setNome(String.valueOf(SQLUtil.getValorResultSet(resultSet, NOME, java.sql.Types.VARCHAR)));
				grupoUsuario.setDescricao(String.valueOf(SQLUtil.getValorResultSet(resultSet, DESCRICAO, java.sql.Types.VARCHAR)));
				grupoUsuario.setSituacao((Boolean) SQLUtil.getValorResultSet(resultSet, SITUACAO, java.sql.Types.BOOLEAN));
				grupoUsuario.setIdCondominio((Integer) SQLUtil.getValorResultSet(resultSet, ID_CONDOMINIO, java.sql.Types.INTEGER));
				grupoUsuario.setIdSindicoProfissional((Integer) SQLUtil.getValorResultSet(resultSet, ID_SINDICO_PROFISSIONAL, java.sql.Types.INTEGER));
				grupoUsuario.setIdEscritorioContabilidade((Integer) SQLUtil.getValorResultSet(resultSet, ID_ESCRITORIO_CONTABILIDADE, java.sql.Types.INTEGER));
				listaGrupoUsuario.add(grupoUsuario);
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
		return listaGrupoUsuario;

	}


	@Override
	public GrupoUsuario buscarPorId(Integer idGrupoUsuario, Connection con) throws SQLException, Exception {
		StringBuffer query = new StringBuffer();
		query.append("SELECT * FROM ");
		query.append(GRUPO_USUARIO);
		query.append(" WHERE ");
		query.append(ID);
		query.append(" = ?");
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		GrupoUsuario grupoUsuario = null;
		List<TelaVO> listaTelaVO = null;
		Tela tela = null;
		try {
			statement = con.prepareStatement(query.toString());
			SQLUtil.setValorPpreparedStatement(statement, 1,idGrupoUsuario, java.sql.Types.INTEGER);
			resultSet = statement.executeQuery();
			while(resultSet.next()){
				grupoUsuario = new GrupoUsuario();				
				grupoUsuario.setId((Integer) SQLUtil.getValorResultSet(resultSet, ID, java.sql.Types.INTEGER));
				grupoUsuario.setNome(String.valueOf(SQLUtil.getValorResultSet(resultSet, NOME, java.sql.Types.VARCHAR)));
				grupoUsuario.setDescricao(String.valueOf(SQLUtil.getValorResultSet(resultSet, DESCRICAO, java.sql.Types.VARCHAR)));
				grupoUsuario.setSituacao((Boolean) SQLUtil.getValorResultSet(resultSet, SITUACAO, java.sql.Types.BOOLEAN));
				grupoUsuario.setIdCondominio((Integer) SQLUtil.getValorResultSet(resultSet, ID_CONDOMINIO, java.sql.Types.INTEGER));
				grupoUsuario.setIdSindicoProfissional((Integer) SQLUtil.getValorResultSet(resultSet, ID_SINDICO_PROFISSIONAL, java.sql.Types.INTEGER));
				grupoUsuario.setIdEscritorioContabilidade((Integer) SQLUtil.getValorResultSet(resultSet, ID_ESCRITORIO_CONTABILIDADE, java.sql.Types.INTEGER));
				List<GrupoUsuarioTela> listaGrupoUsuarioTela = this.grupoUsuarioTelaDAO.buscarPorIdGrupoUsuario(grupoUsuario.getId(), con);
				listaTelaVO = new ArrayList<TelaVO>();
				for (GrupoUsuarioTela grupoUsuarioTela : listaGrupoUsuarioTela) {
					tela = telaDAO.buscarPorId(grupoUsuarioTela.getIdTela(),con);
					listaTelaVO.add(popularTelaVO(tela));
				}
				grupoUsuario.setListaTelaAcesso(listaTelaVO);
				
			}
		}catch (SQLException e) {
			con.rollback();
			throw e;			
		}catch (Exception e) {
			con.rollback();
			throw e;
		}			
		return grupoUsuario;
	}
	
	private TelaVO popularTelaVO(Tela tela){
		TelaVO telaVO = new TelaVO();
		telaVO.setIdTela(tela.getId());
		telaVO.setNomeArquivoTela(tela.getNomeArquivo());		
		return telaVO;
	}
}
