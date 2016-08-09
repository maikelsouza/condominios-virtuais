	package br.com.condominiosvirtuais.persistence.impl;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import br.com.condominiosvirtuais.entity.Bloco;
import br.com.condominiosvirtuais.entity.BlocoConjuntoBloco;
import br.com.condominiosvirtuais.entity.ConjuntoBloco;
import br.com.condominiosvirtuais.persistence.BlocoConjuntoBlocoDAO;
import br.com.condominiosvirtuais.util.SQLUtil;

public class BlocoConjuntoBlocoDAOImpl implements BlocoConjuntoBlocoDAO, Serializable {	
	
	private static final long serialVersionUID = 1L;

	private static final String BLOCO_CONJUNTO_BLOCO = "BLOCO_CONJUNTO_BLOCO";

	private static final String ID = "ID";	
	
	private static final String ID_BLOCO = "ID_BLOCO";
	
	private static final String ID_CONJUNTO_BLOCO = "ID_CONJUNTO_BLOCO";
	
	@Inject
	private BlocoDAOImpl blocoDAO;

	
	public void salvar(BlocoConjuntoBloco blocoConjuntoBloco, Connection con) throws SQLException, Exception  {		
		StringBuffer query = new StringBuffer();
		query.append("INSERT INTO "); 
		query.append(BLOCO_CONJUNTO_BLOCO);
		query.append("(");
		query.append(ID_BLOCO);
		query.append(",");
		query.append(ID_CONJUNTO_BLOCO);		
		query.append(") ");
		query.append("VALUES(?,?)");		
		PreparedStatement statement = null;				
		try {			
			statement = con.prepareStatement(query.toString());
			statement.setInt(1,blocoConjuntoBloco.getBloco().getId());
			statement.setInt(2,blocoConjuntoBloco.getConjuntoBloco().getId());			
			statement.executeUpdate();
		} catch (SQLException e) {
			con.rollback();
			throw e;
		} catch (Exception e) {
			con.rollback();
			throw e;
		}
	}
	
	public List<BlocoConjuntoBloco> buscarPorIdBloco(Integer idBloco, Connection con) throws SQLException, Exception {		
		StringBuffer query = new StringBuffer();
		query.append("SELECT * FROM ");
		query.append(BLOCO_CONJUNTO_BLOCO);
		query.append(" WHERE ");
		query.append(ID_BLOCO);		
		query.append(" = ?");
		query.append(";");
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;		
		BlocoConjuntoBloco blocoConjuntoBloco = null;
		List<BlocoConjuntoBloco> listaBlocoConjuntoBloco = new ArrayList<BlocoConjuntoBloco>();		
		Bloco bloco = null;
		try {
			preparedStatement = con.prepareStatement(query.toString());
			preparedStatement.setInt(1, idBloco);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()){
				blocoConjuntoBloco = new BlocoConjuntoBloco();
				blocoConjuntoBloco.setId(resultSet.getInt(ID));				
				bloco = blocoDAO.buscarPorId(resultSet.getInt(ID_BLOCO));
				blocoConjuntoBloco.setBloco(bloco);	
				ConjuntoBloco conjuntoBloco = new ConjuntoBloco();
				conjuntoBloco.setId(resultSet.getInt(ID_CONJUNTO_BLOCO));
				blocoConjuntoBloco.setConjuntoBloco(conjuntoBloco);								
				listaBlocoConjuntoBloco.add(blocoConjuntoBloco);
			}
		} catch (SQLException e) {			
			throw e;
		} catch (Exception e) {
			throw e;
		}
		return listaBlocoConjuntoBloco;
	}
	
	public List<BlocoConjuntoBloco> buscarPorIdConjuntoBloco(Integer idConjuntoBloco, Connection con) throws SQLException, Exception {
		StringBuffer query = new StringBuffer();
		query.append("SELECT * FROM ");
		query.append(BLOCO_CONJUNTO_BLOCO);
		query.append(" WHERE ");
		query.append(ID_CONJUNTO_BLOCO);		
		query.append(" = ?");
		query.append(";");
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;		
		BlocoConjuntoBloco blocoConjuntoBloco = null;		
		Bloco bloco = null;
		List<BlocoConjuntoBloco> listaBlocoConjuntoBloco = new ArrayList<BlocoConjuntoBloco>();
		try {
			preparedStatement = con.prepareStatement(query.toString());
			preparedStatement.setInt(1, idConjuntoBloco);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()){
				blocoConjuntoBloco = new BlocoConjuntoBloco();
				blocoConjuntoBloco.setId(resultSet.getInt(ID));
				bloco = this.blocoDAO.buscarPorId(resultSet.getInt(ID_BLOCO));
				blocoConjuntoBloco.setBloco(bloco);
				ConjuntoBloco conjuntoBloco = new ConjuntoBloco();
				conjuntoBloco.setId(resultSet.getInt(ID_CONJUNTO_BLOCO));
				blocoConjuntoBloco.setConjuntoBloco(conjuntoBloco);								
				listaBlocoConjuntoBloco.add(blocoConjuntoBloco);
			}
		} catch (SQLException e) {
			con.rollback();
			throw e;
		} catch (Exception e) {
			con.rollback();
			throw e;
		}
		return listaBlocoConjuntoBloco;
	}

	public void excluir(BlocoConjuntoBloco blocoConjuntoBloco, Connection con) throws SQLException, Exception {	
		StringBuffer query = new StringBuffer();
		query.append("DELETE FROM ");
		query.append(BLOCO_CONJUNTO_BLOCO);
		query.append(" WHERE ");		
		query.append(ID);
		query.append(" = ?");					
		PreparedStatement preparedStatement;
		try {
			preparedStatement = con.prepareStatement(query.toString());			
			SQLUtil.setValorPpreparedStatement(preparedStatement, 1, blocoConjuntoBloco.getId(), java.sql.Types.INTEGER);								
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			con.rollback();
			throw e;
		} catch (Exception e) {
			con.rollback();
			throw e;
		}
	}
	
	public void excluirPorConjuntoBloco(ConjuntoBloco conjuntoBloco, Connection con) throws SQLException, Exception {	
		StringBuffer query = new StringBuffer();
		query.append("DELETE FROM ");
		query.append(BLOCO_CONJUNTO_BLOCO);
		query.append(" WHERE ");		
		query.append(ID_CONJUNTO_BLOCO);
		query.append(" = ?");					
		PreparedStatement preparedStatement;
		try {
			preparedStatement = con.prepareStatement(query.toString());			
			SQLUtil.setValorPpreparedStatement(preparedStatement, 1,conjuntoBloco.getId(), java.sql.Types.INTEGER);								
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			con.rollback();
			throw e;
		} catch (Exception e) {
			con.rollback();
			throw e;
		}
	}	
}
