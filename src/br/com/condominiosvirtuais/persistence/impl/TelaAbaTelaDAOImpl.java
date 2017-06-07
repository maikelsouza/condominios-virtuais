package br.com.condominiosvirtuais.persistence.impl;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;

import br.com.condominiosvirtuais.entity.GrupoUsuarioTela;
import br.com.condominiosvirtuais.entity.TelaAbaTela;
import br.com.condominiosvirtuais.persistence.TelaAbaTelaDAO;
import br.com.condominiosvirtuais.util.SQLUtil;

public class TelaAbaTelaDAOImpl implements TelaAbaTelaDAO, Serializable {

	private static final long serialVersionUID = 1L;
	
	private static Logger logger = Logger.getLogger(TelaAbaTelaDAOImpl.class);
	
	private static final String  TELA_ABA_TELA = " TELA_ABA_TELA";
	
	private static final String ID = "ID";	
	
	private static final String ID_TELA = "ID_TELA";
	
	private static final String ID_ABA_TELA = "ID_ABA_TELA";

	@Override
	public void salvar(TelaAbaTela telaAbaTela, Connection con) throws SQLException, Exception {
		// TODO Auto-generated method stub
		
	}	

	@Override
	public List<TelaAbaTela> buscarPorIdTela(Integer idTela, Connection con) throws SQLException, Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void excluirPorIdTela(Integer idTela, Connection con) throws SQLException, Exception {
		StringBuffer query = new StringBuffer();
		PreparedStatement statement = null;
		try {
			query.append("DELETE FROM ");
			query.append(TELA_ABA_TELA);
			query.append(" WHERE ");		
			query.append(ID_TELA);
			query.append(" = ?");
			statement = con.prepareStatement(query.toString());
			SQLUtil.setValorPpreparedStatement(statement, 1, idTela, java.sql.Types.INTEGER);
			statement.executeUpdate();
		} catch (SQLException e) {
			con.rollback();
			throw e;
		}catch (Exception e) {
			con.rollback();
			throw e;
		}
	}

}
