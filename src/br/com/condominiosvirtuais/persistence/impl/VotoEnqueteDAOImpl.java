package br.com.condominiosvirtuais.persistence.impl;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import br.com.condominiosvirtuais.entity.VotoEnquete;
import br.com.condominiosvirtuais.exception.BusinessException;
import br.com.condominiosvirtuais.persistence.VotoEnqueteDAO;
import br.com.condominiosvirtuais.util.SQLUtil;

public class VotoEnqueteDAOImpl implements VotoEnqueteDAO, Serializable {
	
	private static final long serialVersionUID = 1L;

	private static final String VOTO_ENQUETE = "VOTO_ENQUETE";
	
	private static final String ID_ENQUETE = "ID_ENQUETE";
	
	private static final String ID_UNIDADE = "ID_UNIDADE";

	// Constraint referente a integridade de uma enquete e uma unidade, ou seja, um voto por unidade.
	private static final String UQ_VOTO_ENQUETE_ID_UNIDADE_ID_ENQUETE = "UQ_VOTO_ENQUETE_ID_UNIDADE_ID_ENQUETE";	
	

	@Override
	public void salvar(VotoEnquete votoEnquete, Connection con) throws SQLException, BusinessException, Exception {		
		PreparedStatement statement = null;
		try {					
			StringBuffer query = new StringBuffer();
			query.append("INSERT INTO "); 
			query.append(VOTO_ENQUETE); 
			query.append("(");
			query.append(ID_ENQUETE); 
			query.append(",");
			query.append(ID_UNIDADE);
			query.append(") ");
			query.append("VALUES(?,?)");
			statement = con.prepareStatement(query.toString());			
			SQLUtil.setValorPpreparedStatement(statement, 1, votoEnquete.getIdEnquete(), java.sql.Types.INTEGER);
			SQLUtil.setValorPpreparedStatement(statement, 2, votoEnquete.getIdUnidade(), java.sql.Types.INTEGER);	
			statement.execute();
		} catch (SQLException e) {
			con.rollback();
			if (e.getMessage().contains(UQ_VOTO_ENQUETE_ID_UNIDADE_ID_ENQUETE)){
				throw new BusinessException("msg.enquete.votoEnqueteUnidadeDuplicado"); 
			}else{
				throw e;
			}
		} catch (Exception e){
			con.rollback();
			throw e;
		}
	}
	
	@Override
	public void excluirPorIdEnquete(Integer idEnquete, Connection con)
			throws SQLException, Exception {
		StringBuffer query = new StringBuffer();
		query.append("DELETE FROM ");
		query.append(VOTO_ENQUETE);
		query.append(" WHERE ");
		query.append(ID_ENQUETE);
		query.append("= ?");
		PreparedStatement statement = null;
		try {
			statement = con.prepareStatement(query.toString());
			SQLUtil.setValorPpreparedStatement(statement, 1, idEnquete, java.sql.Types.INTEGER);
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
