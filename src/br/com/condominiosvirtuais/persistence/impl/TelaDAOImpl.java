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

import br.com.condominiosvirtuais.entity.Aba;
import br.com.condominiosvirtuais.entity.GrupoUsuarioTela;
import br.com.condominiosvirtuais.entity.Tela;
import br.com.condominiosvirtuais.persistence.AbaDAO;
import br.com.condominiosvirtuais.persistence.ComponenteDAO;
import br.com.condominiosvirtuais.persistence.GrupoUsuarioTelaDAO;
import br.com.condominiosvirtuais.persistence.TelaDAO;
import br.com.condominiosvirtuais.util.SQLUtil;

public class TelaDAOImpl implements TelaDAO, Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private static Logger logger = Logger.getLogger(TelaDAOImpl.class);
	
	@Inject
	private AbaDAO abaDAO;
	
	@Inject
	private ComponenteDAO componenteDAO; 
	
	@Inject
	private GrupoUsuarioTelaDAO grupoUsuarioTelaDAO;
	
	private static final String TELA = "TELA";
	
	private static final String ID = "ID";
	
	private static final String NOME = "NOME";
	
	private static final String DESCRICAO = "DESCRICAO";
	
	private static final String NOME_ARQUIVO = "NOME_ARQUIVO";
	
	private static final String ID_MODULO = "ID_MODULO";
	
	

	@Override
	public Tela buscarPorId(Integer id) throws SQLException, Exception {
		
	
		return null;
	}

	@Override
	public List<Tela> buscarPorIdGrupoUsuario(Integer idGrupoUsuario) throws SQLException, Exception {
		List<Tela> listaTela = new ArrayList<Tela>();
		Connection con = Conexao.getConexao();
		PreparedStatement preparedStatement = null;
		try {
			// Busca a lista de telas associadas a um grupo de usuário
			List<GrupoUsuarioTela> listaGrupoUsuarioTela = this.grupoUsuarioTelaDAO.buscarPorIdGrupoUsuario(idGrupoUsuario,con);
			if(!listaGrupoUsuarioTela.isEmpty()){
				List<Aba> listaAbas = null;
				StringBuffer query = new StringBuffer();
				query.append("SELECT * FROM ");
				query.append(TELA);
				query.append(" WHERE ");
				query.append(ID);
				query.append(" IN ( ");
				query.append(SQLUtil.popularInterrocacoes(listaGrupoUsuarioTela.size()));			
				query.append(" );");
				ResultSet resultSet = null;
				Integer contador = 1;
				Tela tela = null;
				preparedStatement = con.prepareStatement(query.toString());
				for (GrupoUsuarioTela grupoUsuarioTela: listaGrupoUsuarioTela) {
					SQLUtil.setValorPpreparedStatement(preparedStatement, contador++, grupoUsuarioTela.getIdTela(), java.sql.Types.INTEGER);
				}
				resultSet = preparedStatement.executeQuery();	
				while(resultSet.next()){
					tela = new Tela();				
					tela.setId((Integer) SQLUtil.getValorResultSet(resultSet, ID, java.sql.Types.INTEGER));
					tela.setNome(String.valueOf(SQLUtil.getValorResultSet(resultSet, NOME, java.sql.Types.VARCHAR)));
					tela.setNomeArquivo(String.valueOf(SQLUtil.getValorResultSet(resultSet, NOME_ARQUIVO, java.sql.Types.VARCHAR)));
					tela.setDescricao(String.valueOf(SQLUtil.getValorResultSet(resultSet, DESCRICAO, java.sql.Types.VARCHAR)));
					tela.setIdModulo((Integer) SQLUtil.getValorResultSet(resultSet, ID_MODULO, java.sql.Types.INTEGER));
					listaAbas = this.abaDAO.buscarPoridGrupoUsuarioEIdTela(idGrupoUsuario, tela.getId(), con);
					tela.setListaAbas(listaAbas);
					tela.setListaComponente(componenteDAO.buscarPorIdTela(tela.getId(),con));
					listaTela.add(tela);
				}				
			}
		}catch (SQLException e) {
			throw e;
		}catch (Exception e) {		
			throw e;
		}finally{
			try {
				if(preparedStatement != null){
					preparedStatement.close();
				}
				con.close();				
			} catch (SQLException e) {
				logger.error("erro sqlstate "+e.getSQLState(), e);
			}
		}	
		return listaTela;
	}

	@Override
	public List<Tela> buscarTodas() throws SQLException, Exception {
		StringBuffer query = new StringBuffer();
		query.append("SELECT * FROM ");
		query.append(TELA);
		query.append(";");		
		Connection con = Conexao.getConexao();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Tela tela = null;
		List<Tela> listaTela = new ArrayList<Tela>();
		try {
			preparedStatement = con.prepareStatement(query.toString());
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()){
				tela = new Tela();
				tela.setId((Integer) SQLUtil.getValorResultSet(resultSet, ID, java.sql.Types.INTEGER));
				tela.setNome(String.valueOf(SQLUtil.getValorResultSet(resultSet, NOME, java.sql.Types.VARCHAR)));
				tela.setNomeArquivo(String.valueOf(SQLUtil.getValorResultSet(resultSet, NOME_ARQUIVO, java.sql.Types.VARCHAR)));
				tela.setDescricao(String.valueOf(SQLUtil.getValorResultSet(resultSet, DESCRICAO, java.sql.Types.VARCHAR)));
				tela.setIdModulo((Integer) SQLUtil.getValorResultSet(resultSet, ID_MODULO, java.sql.Types.INTEGER));
				tela.setListaAbas(this.abaDAO.buscarPorIdTela(tela.getId(), con));
				listaTela.add(tela);
			}
		}catch (SQLException e) {
			throw e;
		}catch (Exception e) {		
			throw e;
		}finally{
			try {
				preparedStatement.close();
				con.close();				
			} catch (SQLException e) {
				logger.error("erro sqlstate "+e.getSQLState(), e);
			}
		}	
		return listaTela;
	}
	
	
	

}
