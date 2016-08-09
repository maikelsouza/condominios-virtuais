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

import br.com.condominiosvirtuais.entity.Bloco;
import br.com.condominiosvirtuais.entity.Condominio;
import br.com.condominiosvirtuais.entity.Condomino;
import br.com.condominiosvirtuais.entity.Garagem;
import br.com.condominiosvirtuais.entity.Unidade;
import br.com.condominiosvirtuais.entity.Veiculo;
import br.com.condominiosvirtuais.exception.BusinessException;
import br.com.condominiosvirtuais.persistence.BlocoDAO;
import br.com.condominiosvirtuais.persistence.GaragemDAO;
import br.com.condominiosvirtuais.persistence.UnidadeDAO;
import br.com.condominiosvirtuais.persistence.VeiculoDAO;
import br.com.condominiosvirtuais.util.SQLUtil;

public class VeiculoDAOImpl implements VeiculoDAO, Serializable {
	
	private static final long serialVersionUID = 1L;

	private static Logger logger = Logger.getLogger(VeiculoDAOImpl.class); 
	
	private static final String VEICULO = "VEICULO";
	
	private static final String ID = "ID";
	
	private static final String NOME = "NOME";
	
	private static final String PLACA = "PLACA";
	
	private static final String TAMANHO = "TAMANHO";
	
	private static final String TIPO = "TIPO";
	
	private static final String ID_CONDOMINO = "ID_CONDOMINO";
	
	private static final String ID_GARAGEM = "ID_GARAGEM";
	
	// Constraint referente a singularidade de um veículo, ou seja, não é possível cadastar o mesmo veículo.
	private static final String UQ_VEICULO_PLACA = "UQ_VEICULO_PLACA";	
	
	@Inject
	private GaragemDAO garagemDAO;
	
	@Inject
	private CondominoDAOImpl condominoDAO;
	
	@Inject
	private CondominioDAOImpl condominioDAO;
	
	@Inject
	private UnidadeDAO unidadeDAO;
	
	@Inject
	private BlocoDAO blocoDAO;

	@Override
	public void salvar(Veiculo veiculo) throws SQLException, BusinessException, Exception {
		Connection con = Conexao.getConexao();
		StringBuffer query = new StringBuffer();
		query.append("INSERT INTO "); 
		query.append(VEICULO);
		query.append("("); 
		query.append(NOME);	
		query.append(",");
		query.append(PLACA);
		query.append(",");
		query.append(TAMANHO);		
		query.append(",");
		query.append(TIPO);		
		query.append(",");
		query.append(ID_CONDOMINO);		
		query.append(",");
		query.append(ID_GARAGEM);		
		query.append(") ");
		query.append("VALUES(?,?,?,?,?,?)");
		PreparedStatement statement = null;		
		try {			
			statement = con.prepareStatement(query.toString());
			SQLUtil.setValorPpreparedStatement(statement, 1, veiculo.getNome(), java.sql.Types.VARCHAR);	
			// UpperCase e trim para que a constraint Unique funcione sem "furos"
			SQLUtil.setValorPpreparedStatement(statement, 2, veiculo.getPlaca().toUpperCase().trim(), java.sql.Types.VARCHAR);
			SQLUtil.setValorPpreparedStatement(statement, 3, veiculo.getTamanho(), java.sql.Types.INTEGER);
			SQLUtil.setValorPpreparedStatement(statement, 4, veiculo.getTipo(), java.sql.Types.INTEGER);
			SQLUtil.setValorPpreparedStatement(statement, 5, veiculo.getCondomino().getId(), java.sql.Types.INTEGER);
			SQLUtil.setValorPpreparedStatement(statement, 6, veiculo.getGaragem().getId(), java.sql.Types.INTEGER);
			statement.executeUpdate();
		}catch (SQLException e) {			
			if (e.getMessage().contains(UQ_VEICULO_PLACA)){
				throw new BusinessException("msg.meuPainel.veiculo.placaDuplicada"); 
			}else{
				throw e;
			}
		} catch (Exception e) {					
			throw e;	
		}finally{
			try{	
				statement.close();
				con.close();
			}catch (SQLException e){
				logger.error("erro sqlstate "+e.getSQLState(), e);
			}
		}			
	}

	@Override
	public void excluir(Veiculo veiculo) throws SQLException, Exception {
		StringBuffer query = new StringBuffer();
		Connection con = Conexao.getConexao();
		PreparedStatement statement = null;
		try {
			query.append("DELETE FROM ");
			query.append(VEICULO);
			query.append(" WHERE ");		
			query.append(ID);
			query.append(" = ?");
			statement = con.prepareStatement(query.toString());
			SQLUtil.setValorPpreparedStatement(statement, 1, veiculo.getId(), java.sql.Types.INTEGER);			
			statement.executeUpdate();	
		}catch (SQLException e) {			
			throw e;
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
	public void atualizar(Veiculo veiculo) throws SQLException, BusinessException, Exception {
		Connection con = Conexao.getConexao();	
		StringBuffer query = new StringBuffer();
		query.append("UPDATE ");
		query.append(VEICULO);
		query.append(" SET ");
		query.append(NOME);	
		query.append(" = ?,");
		query.append(PLACA);
		query.append(" = ?,");
		query.append(TAMANHO);		
		query.append(" = ?,");
		query.append(TIPO);		
		query.append(" = ?,");
		query.append(ID_CONDOMINO);		
		query.append(" = ?,");
		query.append(ID_GARAGEM);
		query.append(" = ?");
		query.append(" WHERE ");
		query.append(ID);
		query.append("= ?");
		PreparedStatement statement = null;
		try {
			statement = con.prepareStatement(query.toString());
			SQLUtil.setValorPpreparedStatement(statement, 1, veiculo.getNome(), java.sql.Types.VARCHAR);
			// UpperCase e trim para que a constraint Unique funcione sem "furos"
			SQLUtil.setValorPpreparedStatement(statement, 2, veiculo.getPlaca().toUpperCase().trim(), java.sql.Types.VARCHAR);
			SQLUtil.setValorPpreparedStatement(statement, 3, veiculo.getTamanho(), java.sql.Types.INTEGER);
			SQLUtil.setValorPpreparedStatement(statement, 4, veiculo.getTipo(), java.sql.Types.INTEGER);			
			SQLUtil.setValorPpreparedStatement(statement, 5, veiculo.getCondomino().getId(), java.sql.Types.INTEGER);			
			SQLUtil.setValorPpreparedStatement(statement, 6, veiculo.getGaragem().getId(), java.sql.Types.INTEGER);			
			SQLUtil.setValorPpreparedStatement(statement, 7, veiculo.getId(), java.sql.Types.INTEGER);			
			statement.executeUpdate();			
		} catch (SQLException e) {			
			if (e.getMessage().contains(UQ_VEICULO_PLACA)){
				throw new BusinessException("msg.meuPainel.veiculo.placaDuplicada"); 
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
	public List<Veiculo> buscarPorIdCondomino(Integer idCondomino) throws NumberFormatException, SQLException, Exception {
		StringBuffer query = new StringBuffer();
		query.append("SELECT * FROM ");
		query.append(VEICULO);
		query.append(" WHERE ");
		query.append(ID_CONDOMINO);		
		query.append(" = ?");		
		query.append(";");
		Connection con = Conexao.getConexao();
		PreparedStatement preparedStatement = null;
		Veiculo veiculo = null;
		Garagem garagem = null;
		Condomino condomino = null;
		List<Veiculo> listaVeiculo = new ArrayList<Veiculo>();
		try {
			preparedStatement = con.prepareStatement(query.toString());
			SQLUtil.setValorPpreparedStatement(preparedStatement, 1, idCondomino, java.sql.Types.INTEGER);
			ResultSet resultSet = preparedStatement.executeQuery();
			while(resultSet.next()){				
				veiculo = new Veiculo();				
				veiculo.setId((Integer) SQLUtil.getValorResultSet(resultSet, ID, java.sql.Types.INTEGER));
				veiculo.setNome(String.valueOf(SQLUtil.getValorResultSet(resultSet, NOME, java.sql.Types.VARCHAR)));
				veiculo.setPlaca(String.valueOf(SQLUtil.getValorResultSet(resultSet, PLACA, java.sql.Types.VARCHAR)));
				veiculo.setTipo((Integer) SQLUtil.getValorResultSet(resultSet, TIPO, java.sql.Types.INTEGER));
				veiculo.setTamanho((Integer) SQLUtil.getValorResultSet(resultSet, TAMANHO, java.sql.Types.INTEGER));
				condomino = this.condominoDAO.buscarCondominoPorId((Integer) SQLUtil.getValorResultSet(resultSet, ID_CONDOMINO, java.sql.Types.INTEGER), con);
				veiculo.setCondomino(condomino);
				garagem = this.garagemDAO.buscarPorId((Integer) SQLUtil.getValorResultSet(resultSet, ID_GARAGEM, java.sql.Types.INTEGER), con);
				veiculo.setGaragem(garagem);							
				listaVeiculo.add(veiculo);
			}
		} catch (NumberFormatException e){
			throw e;
		} catch (SQLException e) {			
			throw e;
		} catch (Exception e) {					
			throw e;	
		}finally{
			try{				
				preparedStatement.close();
				con.close();				
			}catch (SQLException e) {
				logger.error("erro sqlstate "+e.getSQLState(), e);
		    }
		}
		return listaVeiculo;
	}

	@Override
	public List<Veiculo> buscarPorIdCondominoOuNomeOuPlacaOuNumeroGaragem(Veiculo veiculo) throws NumberFormatException, SQLException, Exception {
		StringBuffer query = new StringBuffer();
		query.append("SELECT * FROM ");
		query.append(VEICULO);
		query.append(" WHERE ");
		query.append(ID_CONDOMINO);		
		query.append(" = ?");		
		query.append(" OR UPPER(");		
		query.append(NOME);		
		query.append(") LIKE ?");
		query.append(" OR ");
		query.append(PLACA);
		query.append(" LIKE ?");
		query.append(" OR ");	
		query.append(ID_GARAGEM);
		query.append(" = ?");
		query.append(";");
		Connection con = Conexao.getConexao();
		PreparedStatement preparedStatement = null;
		Veiculo veiculoBase = null;
		Garagem garagem = null;
		Condomino condomino = null;
		List<Veiculo> listaVeiculo = new ArrayList<Veiculo>();
		try {
			preparedStatement = con.prepareStatement(query.toString());
			SQLUtil.setValorPpreparedStatement(preparedStatement, 1, veiculo.getCondomino().getId(), java.sql.Types.INTEGER);
			SQLUtil.setValorPpreparedStatement(preparedStatement, 2, veiculo.getNome().trim() != "" ? "%"+veiculo.getNome().trim().toUpperCase()+"%" : null, java.sql.Types.VARCHAR);
			SQLUtil.setValorPpreparedStatement(preparedStatement, 3, veiculo.getPlaca().trim() != "" ? "%"+veiculo.getPlaca().trim().toUpperCase()+"%" : null, java.sql.Types.VARCHAR);
			SQLUtil.setValorPpreparedStatement(preparedStatement, 4, veiculo.getGaragem().getId(), java.sql.Types.INTEGER);
			ResultSet resultSet = preparedStatement.executeQuery();
			while(resultSet.next()){				
				veiculoBase = new Veiculo();				
				veiculoBase.setId((Integer) SQLUtil.getValorResultSet(resultSet, ID, java.sql.Types.INTEGER));
				veiculoBase.setNome(String.valueOf(SQLUtil.getValorResultSet(resultSet, NOME, java.sql.Types.VARCHAR)));
				veiculoBase.setPlaca(String.valueOf(SQLUtil.getValorResultSet(resultSet, PLACA, java.sql.Types.VARCHAR)));
				veiculoBase.setTipo((Integer) SQLUtil.getValorResultSet(resultSet, TIPO, java.sql.Types.INTEGER));
				veiculoBase.setTamanho((Integer) SQLUtil.getValorResultSet(resultSet, TAMANHO, java.sql.Types.INTEGER));
				condomino = this.condominoDAO.buscarCondominoPorId((Integer) SQLUtil.getValorResultSet(resultSet, ID_CONDOMINO, java.sql.Types.INTEGER), con);
				veiculoBase.setCondomino(condomino);
				garagem = this.garagemDAO.buscarPorId((Integer) SQLUtil.getValorResultSet(resultSet, ID_GARAGEM, java.sql.Types.INTEGER), con);
				veiculoBase.setGaragem(garagem);							
				listaVeiculo.add(veiculoBase);
			}	
		} catch (NumberFormatException e){
			throw e;
		} catch (SQLException e) {			
			throw e;
		} catch (Exception e) {					
			throw e;	
		}finally{
			try{				
				preparedStatement.close();
				con.close();				
			}catch (SQLException e) {
				logger.error("erro sqlstate "+e.getSQLState(), e);
		    }
		}
		return listaVeiculo;

		
	}

	@Override
	public List<Veiculo> buscarPorIdCondominioENome(Integer idCondominio,
			String nome) throws SQLException, Exception {
		String pontoInterrogacao = "";
		Condominio condominio = new Condominio();
		condominio.setId(idCondominio);
		List<Condomino> listaCondomino = new ArrayList<Condomino>();
		this.condominioDAO.popularCondominio(condominio);
		for (Bloco bloco : condominio.getListaBlocos()) {
			for (Unidade unidade : bloco.getListaUnidade()) {
				for (int i = 0; i < unidade.getListaCondominos().size(); i++) {
					listaCondomino.add(unidade.getListaCondominos().get(i));
					pontoInterrogacao+="?,";
				}				
			}
		}
		// Caso possua condôminos, será necessário remover a última vírgula do array de ponto de interrogação. 
		if (!pontoInterrogacao.isEmpty()){
			String virgula = pontoInterrogacao.substring(pontoInterrogacao.length()-1,pontoInterrogacao.length());
			if(virgula.equals(",")){
				pontoInterrogacao =  pontoInterrogacao.substring(0,pontoInterrogacao.length()-1);
			}
		}
		StringBuffer query = new StringBuffer();
		query.append("SELECT * FROM ");
		query.append(VEICULO);
		query.append(" WHERE ");
		query.append(ID_CONDOMINO);		
		query.append(" in (");
		query.append(pontoInterrogacao);
		query.append(") ");
		query.append("AND UPPER(");
		query.append(NOME);		
		query.append(") LIKE ?");	
		query.append(" ORDER BY ");
		query.append(NOME);
		query.append(";");
		Connection con = Conexao.getConexao();
		PreparedStatement preparedStatement = null;
		Veiculo veiculo = null;
		Garagem garagem = null;
		Condomino condomino = null;
		Integer contador = 1;
		List<Veiculo> listaVeiculo = new ArrayList<Veiculo>();
		try {
			preparedStatement = con.prepareStatement(query.toString());
			for (Condomino condominoBase : listaCondomino) {
				SQLUtil.setValorPpreparedStatement(preparedStatement, contador++, condominoBase.getId(), java.sql.Types.INTEGER);				
			}
			SQLUtil.setValorPpreparedStatement(preparedStatement, contador, nome.trim() != "" ? "%"+nome.trim().toUpperCase()+"%" : null, java.sql.Types.VARCHAR);
			ResultSet resultSet = preparedStatement.executeQuery();
			while(resultSet.next()){				
				veiculo = new Veiculo();				
				veiculo.setId((Integer) SQLUtil.getValorResultSet(resultSet, ID, java.sql.Types.INTEGER));
				veiculo.setNome(String.valueOf(SQLUtil.getValorResultSet(resultSet, NOME, java.sql.Types.VARCHAR)));
				veiculo.setPlaca(String.valueOf(SQLUtil.getValorResultSet(resultSet, PLACA, java.sql.Types.VARCHAR)));
				veiculo.setTipo((Integer) SQLUtil.getValorResultSet(resultSet, TIPO, java.sql.Types.INTEGER));
				veiculo.setTamanho((Integer) SQLUtil.getValorResultSet(resultSet, TAMANHO, java.sql.Types.INTEGER));
				condomino = this.condominoDAO.buscarCondominoPorId((Integer) SQLUtil.getValorResultSet(resultSet, ID_CONDOMINO, java.sql.Types.INTEGER), con);
				veiculo.setCondomino(condomino);
				garagem = this.garagemDAO.buscarPorId((Integer) SQLUtil.getValorResultSet(resultSet, ID_GARAGEM, java.sql.Types.INTEGER), con);
				veiculo.setGaragem(garagem);							
				listaVeiculo.add(veiculo);
			}	
		} catch (SQLException e) {			
			throw e;
		} catch (Exception e) {					
			throw e;	
		}finally{
			try{				
				preparedStatement.close();
				con.close();				
			}catch (SQLException e) {
				logger.error("erro sqlstate "+e.getSQLState(), e);
		    }
		}
		return listaVeiculo;

	}

	@Override
	public List<Veiculo> buscarPorIdCondominioEPlaca(Integer idCondominio,
			String placa) throws SQLException, Exception {
		String pontoInterrogacao = "";
		Condominio condominio = new Condominio();
		condominio.setId(idCondominio);
		List<Condomino> listaCondomino = new ArrayList<Condomino>();
		this.condominioDAO.popularCondominio(condominio);
		for (Bloco bloco : condominio.getListaBlocos()) {
			for (Unidade unidade : bloco.getListaUnidade()) {
				for (int i = 0; i < unidade.getListaCondominos().size(); i++) {
					listaCondomino.add(unidade.getListaCondominos().get(i));
					pontoInterrogacao+="?,";
				}				
			}
		}
		// Caso possua condôminos, será necessário remover a última vírgula do array de ponto de interrogação. 
		if (!pontoInterrogacao.isEmpty()){
			String virgula = pontoInterrogacao.substring(pontoInterrogacao.length()-1,pontoInterrogacao.length());
			if(virgula.equals(",")){
				pontoInterrogacao =  pontoInterrogacao.substring(0,pontoInterrogacao.length()-1);
			}
		}
		StringBuffer query = new StringBuffer();
		query.append("SELECT * FROM ");
		query.append(VEICULO);
		query.append(" WHERE ");
		query.append(ID_CONDOMINO);		
		query.append(" in (");
		query.append(pontoInterrogacao);
		query.append(") ");
		query.append("AND ");
		query.append(PLACA);		
		query.append(" LIKE ?");
		query.append(" ORDER BY ");
		query.append(PLACA);
		query.append(";");
		Connection con = Conexao.getConexao();
		PreparedStatement preparedStatement = null;
		Veiculo veiculo = null;
		Garagem garagem = null;
		Condomino condomino = null;
		Integer contador = 1;
		List<Veiculo> listaVeiculo = new ArrayList<Veiculo>();
		try {
			preparedStatement = con.prepareStatement(query.toString());
			for (Condomino condominoBase : listaCondomino) {
				SQLUtil.setValorPpreparedStatement(preparedStatement, contador++, condominoBase.getId(), java.sql.Types.INTEGER);				
			}
			SQLUtil.setValorPpreparedStatement(preparedStatement, contador, placa.trim() != "" ? "%"+placa.trim().toUpperCase()+"%" : null, java.sql.Types.VARCHAR);
			ResultSet resultSet = preparedStatement.executeQuery();
			while(resultSet.next()){				
				veiculo = new Veiculo();				
				veiculo.setId((Integer) SQLUtil.getValorResultSet(resultSet, ID, java.sql.Types.INTEGER));
				veiculo.setNome(String.valueOf(SQLUtil.getValorResultSet(resultSet, NOME, java.sql.Types.VARCHAR)));
				veiculo.setPlaca(String.valueOf(SQLUtil.getValorResultSet(resultSet, PLACA, java.sql.Types.VARCHAR)));
				veiculo.setTipo((Integer) SQLUtil.getValorResultSet(resultSet, TIPO, java.sql.Types.INTEGER));
				veiculo.setTamanho((Integer) SQLUtil.getValorResultSet(resultSet, TAMANHO, java.sql.Types.INTEGER));
				condomino = this.condominoDAO.buscarCondominoPorId((Integer) SQLUtil.getValorResultSet(resultSet, ID_CONDOMINO, java.sql.Types.INTEGER), con);
				veiculo.setCondomino(condomino);
				garagem = this.garagemDAO.buscarPorId((Integer) SQLUtil.getValorResultSet(resultSet, ID_GARAGEM, java.sql.Types.INTEGER), con);
				veiculo.setGaragem(garagem);							
				listaVeiculo.add(veiculo);
			}	
		} catch (SQLException e) {			
			throw e;
		} catch (Exception e) {					
			throw e;	
		}finally{
			try{				
				preparedStatement.close();
				con.close();				
			}catch (SQLException e) {
				logger.error("erro sqlstate "+e.getSQLState(), e);
		    }
		}
		return listaVeiculo;

	}

	@Override
	public List<Veiculo> buscarPorIdCondominio(Integer idCondominio) throws SQLException, Exception {
		String pontoInterrogacao = "";
		Condominio condominio = new Condominio();
		condominio.setId(idCondominio);
		List<Condomino> listaCondomino = new ArrayList<Condomino>();
		this.condominioDAO.popularCondominio(condominio);
		for (Bloco bloco : condominio.getListaBlocos()) {
			for (Unidade unidade : bloco.getListaUnidade()) {
				for (int i = 0; i < unidade.getListaCondominos().size(); i++) {
					listaCondomino.add(unidade.getListaCondominos().get(i));
						pontoInterrogacao+="?,";
				}
			}				
		}
		// Caso possua condôminos, será necessário remover a última vírgula do array de ponto de interrogação. 
		if (!pontoInterrogacao.isEmpty()){
			String virgula = pontoInterrogacao.substring(pontoInterrogacao.length()-1,pontoInterrogacao.length());
			if(virgula.equals(",")){
				pontoInterrogacao =  pontoInterrogacao.substring(0,pontoInterrogacao.length()-1);
			}
		}
		StringBuffer query = new StringBuffer();
		query.append("SELECT * FROM ");
		query.append(VEICULO);
		query.append(" WHERE ");
		query.append(ID_CONDOMINO);		
		query.append(" in (");
		query.append(pontoInterrogacao);
		query.append(") ");
		query.append(";");
		Connection con = Conexao.getConexao();
		PreparedStatement preparedStatement = null;
		Veiculo veiculo = null;
		Garagem garagem = null;
		Bloco bloco = null;
		Unidade unidade = null;
		Condomino condomino = null;
		
		Integer contador = 1;
		List<Veiculo> listaVeiculo = new ArrayList<Veiculo>();
		try {
			preparedStatement = con.prepareStatement(query.toString());
			for (Condomino condominoBase : listaCondomino) {
				SQLUtil.setValorPpreparedStatement(preparedStatement, contador++, condominoBase.getId(), java.sql.Types.INTEGER);				
			}			
			ResultSet resultSet = preparedStatement.executeQuery();
			while(resultSet.next()){				
				veiculo = new Veiculo();				
				veiculo.setId((Integer) SQLUtil.getValorResultSet(resultSet, ID, java.sql.Types.INTEGER));
				veiculo.setNome(String.valueOf(SQLUtil.getValorResultSet(resultSet, NOME, java.sql.Types.VARCHAR)));
				veiculo.setPlaca(String.valueOf(SQLUtil.getValorResultSet(resultSet, PLACA, java.sql.Types.VARCHAR)));
				veiculo.setTipo((Integer) SQLUtil.getValorResultSet(resultSet, TIPO, java.sql.Types.INTEGER));
				veiculo.setTamanho((Integer) SQLUtil.getValorResultSet(resultSet, TAMANHO, java.sql.Types.INTEGER));
				condomino = this.condominoDAO.buscarCondominoPorId((Integer) SQLUtil.getValorResultSet(resultSet, ID_CONDOMINO, java.sql.Types.INTEGER), con);
				unidade = this.unidadeDAO.buscarPorId(condomino.getIdUnidade(), con);
				bloco = this.blocoDAO.buscarPorId(unidade.getIdBloco(), con);
				veiculo.setBloco(bloco);
				veiculo.setUnidade(unidade);
				veiculo.setCondomino(condomino);
				garagem = this.garagemDAO.buscarPorId((Integer) SQLUtil.getValorResultSet(resultSet, ID_GARAGEM, java.sql.Types.INTEGER), con);
				veiculo.setGaragem(garagem);							
				listaVeiculo.add(veiculo);
			}	
		} catch (SQLException e) {			
			throw e;
		} catch (Exception e) {					
			throw e;	
		}finally{
			try{				
				preparedStatement.close();
				con.close();				
			}catch (SQLException e) {
				logger.error("erro sqlstate "+e.getSQLState(), e);
		    }
		}	
		Collections.sort(listaVeiculo);
		return listaVeiculo;
	}

	public GaragemDAO getGaragemDAO() {
		return garagemDAO;
	}

	public void setGaragemDAO(GaragemDAO garagemDAO) {
		this.garagemDAO = garagemDAO;
	}

	public CondominoDAOImpl getCondominoDAO() {
		return condominoDAO;
	}

	public void setCondominoDAO(CondominoDAOImpl condominoDAO) {
		this.condominoDAO = condominoDAO;
	}

	public CondominioDAOImpl getCondominioDAO() {
		return condominioDAO;
	}

	public void setCondominioDAO(CondominioDAOImpl condominioDAO) {
		this.condominioDAO = condominioDAO;
	}	
}
