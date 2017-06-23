package br.com.condominiosvirtuais.persistence.impl;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import org.apache.log4j.Logger;

import br.com.condominiosvirtuais.entity.Aba;
import br.com.condominiosvirtuais.entity.GrupoUsuarioTela;
import br.com.condominiosvirtuais.entity.Tela;
import br.com.condominiosvirtuais.persistence.AbaDAO;
import br.com.condominiosvirtuais.persistence.GrupoUsuarioTelaDAO;
import br.com.condominiosvirtuais.persistence.TelaDAO;
import br.com.condominiosvirtuais.util.SQLUtil;

public class TelaDAOImpl implements TelaDAO, Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private static Logger logger = Logger.getLogger(TelaDAOImpl.class);
	
	@Inject
	private AbaDAO abaDAO;
	
	@Inject
	private GrupoUsuarioTelaDAO grupoUsuarioTelaDAO;
	
	private static final String TELA = "TELA";
	
	private static final String ID = "ID";
	
	private static final String NOME = "NOME";
	
	private static final String DESCRICAO = "DESCRICAO";
	
	private static final String NOME_ARQUIVO = "NOME_ARQUIVO";

	@Override
	public Tela buscarPorId(Integer id) throws SQLException, Exception {
		StringBuffer query = new StringBuffer();
		query.append("SELECT * FROM ");
		query.append(TELA);
		query.append(" WHERE ");
		query.append(ID);
		query.append(" = ?");
		query.append(";");		
		Connection con = Conexao.getConexao();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Tela tela = null;
		try {
			preparedStatement = con.prepareStatement(query.toString());
			SQLUtil.setValorPpreparedStatement(preparedStatement, 1, id, java.sql.Types.INTEGER);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()){
				tela = new Tela();
				tela.setId((Integer) SQLUtil.getValorResultSet(resultSet, ID, java.sql.Types.INTEGER));
				tela.setNome(String.valueOf(SQLUtil.getValorResultSet(resultSet, NOME, java.sql.Types.VARCHAR)));
				tela.setNomeArquivo(String.valueOf(SQLUtil.getValorResultSet(resultSet, NOME_ARQUIVO, java.sql.Types.VARCHAR)));
				tela.setDescricao(String.valueOf(SQLUtil.getValorResultSet(resultSet, DESCRICAO, java.sql.Types.VARCHAR)));
				tela.setListaAbas(this.abaDAO.buscarPorIdTela(tela.getId(), con));
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
		return tela;
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
					listaAbas = this.abaDAO.buscarPorIdTela(tela.getId(), con);
					// Ordenação realizada no código, pois o dado que é persistido no banco é a chave para o I18N
					Collections.sort(listaAbas);
					tela.setListaAbas(listaAbas);
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
	
	
	

}
