package br.com.condominiosvirtuais.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import br.com.condominiosvirtuais.entity.TelaComponente;
import br.com.condominiosvirtuais.util.SQLUtil;

public class TelaComponenteDAOImpl implements TelaComponenteDAO {
	
	private static Logger logger = Logger.getLogger(TelaComponenteDAOImpl.class);
	
	private static final String TELA_COMPONENTE = "TELA_COMPONENTE";
	
    private static final String ID = "ID";
	
	private static final String ID_TELA = "ID_TELA";
	
	private static final String ID_COMPONENTE = "ID_COMPONENTE";
	
	private static final String ACAO = "ACAO";

	@Override
	public List<TelaComponente> buscarPorIdTela(Integer idTela, Connection con) throws SQLException, Exception {
		StringBuffer query = new StringBuffer();
		query.append("SELECT * FROM ");
		query.append(TELA_COMPONENTE);
		query.append(" WHERE ");
		query.append(ID_TELA);
		query.append(" = ?");
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		TelaComponente telaComponente = null;	
		List<TelaComponente> listaTelaComponente = new ArrayList<TelaComponente>();
		try {
			statement = con.prepareStatement(query.toString());
			SQLUtil.setValorPpreparedStatement(statement, 1,idTela, java.sql.Types.INTEGER);
			resultSet = statement.executeQuery();
			while(resultSet.next()){
				telaComponente = new TelaComponente();
				telaComponente.setId((Integer) SQLUtil.getValorResultSet(resultSet, ID, java.sql.Types.INTEGER));
				telaComponente.setIdTela((Integer) SQLUtil.getValorResultSet(resultSet, ID_TELA, java.sql.Types.INTEGER));
				telaComponente.setIdComponente((Integer) SQLUtil.getValorResultSet(resultSet, ID_COMPONENTE, java.sql.Types.INTEGER));
				telaComponente.setAcao(String.valueOf(SQLUtil.getValorResultSet(resultSet, ACAO, java.sql.Types.VARCHAR)));
				listaTelaComponente.add(telaComponente);
			}
		}catch (SQLException e) {
			con.rollback();
			throw e;
		}catch (Exception e) {		
			con.rollback();
			throw e;
		}
		return listaTelaComponente;
	}
	

}
