package br.com.condominiosvirtuais.persistence.impl;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import br.com.condominiosvirtuais.entity.ResponsavelObra;
import br.com.condominiosvirtuais.persistence.ResponsavelObraDAO;
import br.com.condominiosvirtuais.util.SQLUtil;

public class ResponsavelObraDAOImpl implements ResponsavelObraDAO, Serializable {

	private static final long serialVersionUID = 1L;

	private static Logger logger = Logger.getLogger(ResponsavelObraDAOImpl.class);
	
	private static final String RESPONSAVEL_OBRA = "RESPONSAVEL_OBRA";
	
	private static final String ID = "ID";	
	
	private static final String NOME = "NOME";
	
	private static final String TELEFONE = "TELEFONE";
	
	private static final String CNPJ = "CNPJ";
	
	private static final String EMAIL = "EMAIL";
	
	private static final String SITE = "SITE";
	
	private static final String RAZAO_SOCIAL = "RAZAO_SOCIAL";
	
	private static final String TIPO_PESSOA = "TIPO_PESSOA";
	
	private static final String CPF = "CPF";
	

	@Override
	public Integer salvar(ResponsavelObra responsavelObra, Connection con) throws SQLException, Exception {
		Integer idResponsavelObra;
		StringBuffer query = new StringBuffer();
		query.append("INSERT INTO "); 
		query.append(RESPONSAVEL_OBRA);
		query.append("("); 
		query.append(NOME); 
		query.append(",");		
		query.append(TELEFONE); 	
		query.append(",");
		query.append(CNPJ); 
		query.append(",");
		query.append(EMAIL);
		query.append(",");
		query.append(SITE);
		query.append(",");
		query.append(RAZAO_SOCIAL);
		query.append(",");
		query.append(TIPO_PESSOA);
		query.append(",");
		query.append(CPF);
		query.append(") ");
		query.append("VALUES(?,?,?,?,?,?,?,?)");
		PreparedStatement statement = null;
		try {			
			statement = con.prepareStatement(query.toString(), PreparedStatement.RETURN_GENERATED_KEYS);
			SQLUtil.setValorPpreparedStatement(statement, 1, responsavelObra.getNome(), java.sql.Types.VARCHAR);			
			SQLUtil.setValorPpreparedStatement(statement, 2, responsavelObra.getTelefone(), java.sql.Types.BIGINT);	
			SQLUtil.setValorPpreparedStatement(statement, 3, responsavelObra.getCNPJ(), java.sql.Types.BIGINT);
			SQLUtil.setValorPpreparedStatement(statement, 4, responsavelObra.getEmail(), java.sql.Types.VARCHAR);
			SQLUtil.setValorPpreparedStatement(statement, 5, responsavelObra.getSite(), java.sql.Types.VARCHAR);
			SQLUtil.setValorPpreparedStatement(statement, 6, responsavelObra.getRazaoSocial(), java.sql.Types.VARCHAR);
			SQLUtil.setValorPpreparedStatement(statement, 7, responsavelObra.getTipoPessoa(), java.sql.Types.INTEGER);
			SQLUtil.setValorPpreparedStatement(statement, 8, responsavelObra.getCPF(), java.sql.Types.INTEGER);
			statement.executeUpdate();	
			ResultSet rs = statement.getGeneratedKeys();
			rs.next();
			idResponsavelObra = rs.getInt(1);
		} catch (NumberFormatException e) {
			con.rollback();
			throw e;
		}catch (SQLException e) {
			con.rollback();
			throw e;
		} catch (Exception e) {
			con.rollback();
			throw e; 
		}
		return idResponsavelObra;
	}

	@Override
	public void atualizar(ResponsavelObra responsavelObra, Connection con) throws SQLException, Exception {
		StringBuffer query = new StringBuffer();
		query.append("UPDATE ");
		query.append(RESPONSAVEL_OBRA);
		query.append(" SET ");
		query.append(NOME);
		query.append("= ?, ");
		query.append(TELEFONE);
		query.append("= ?, ");
		query.append(CNPJ);
		query.append("= ?, ");		
		query.append(EMAIL);	
		query.append("= ?, ");
		query.append(SITE);	
		query.append("= ?, ");
		query.append(RAZAO_SOCIAL);	
		query.append("= ?, ");
		query.append(TIPO_PESSOA);	
		query.append("= ?, ");
		query.append(CPF);	
		query.append("= ? ");
		query.append(" WHERE ");
		query.append(ID);
		query.append("= ?");		
		PreparedStatement statement = null;		
		
		try {
			statement = con.prepareStatement(query.toString());			
			SQLUtil.setValorPpreparedStatement(statement, 1, responsavelObra.getNome(), java.sql.Types.VARCHAR);
			SQLUtil.setValorPpreparedStatement(statement, 2, responsavelObra.getTelefone(), java.sql.Types.BIGINT);
			SQLUtil.setValorPpreparedStatement(statement, 3, responsavelObra.getCNPJ(), java.sql.Types.BIGINT);
			SQLUtil.setValorPpreparedStatement(statement, 4, responsavelObra.getEmail(), java.sql.Types.VARCHAR);
			SQLUtil.setValorPpreparedStatement(statement, 5, responsavelObra.getSite(), java.sql.Types.VARCHAR);
			SQLUtil.setValorPpreparedStatement(statement, 6, responsavelObra.getRazaoSocial(), java.sql.Types.VARCHAR);
			SQLUtil.setValorPpreparedStatement(statement, 7, responsavelObra.getTipoPessoa(), java.sql.Types.INTEGER);
			SQLUtil.setValorPpreparedStatement(statement, 8, responsavelObra.getCPF(), java.sql.Types.BIGINT);
			SQLUtil.setValorPpreparedStatement(statement, 9, responsavelObra.getId(), java.sql.Types.INTEGER);
			statement.executeUpdate();
		} catch (SQLException e) {
			con.rollback();
			throw e;
		} catch (Exception e) {
			con.rollback();
			throw e;	
		}		
	}

	@Override
	public List<ResponsavelObra> buscarPorNome(String nome) throws SQLException, Exception {
		StringBuffer query = new StringBuffer();
		query.append("SELECT * FROM ");
		query.append(RESPONSAVEL_OBRA);
		query.append(" WHERE ");
		query.append(NOME);		
		query.append(" like ?");
		query.append(" ORDER BY ");
		query.append(NOME);
		query.append(";");		
		Connection con = Conexao.getConexao();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		ResponsavelObra responsavelObra = null;
		List<ResponsavelObra> listaResponsavelObra = new ArrayList<ResponsavelObra>();
		try {
			preparedStatement = con.prepareStatement(query.toString());
			SQLUtil.setValorPpreparedStatement(preparedStatement, 1, nome+"%", java.sql.Types.VARCHAR);				
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()){
				responsavelObra = new ResponsavelObra();
				responsavelObra.setId((Integer) SQLUtil.getValorResultSet(resultSet, ID, java.sql.Types.INTEGER));
				responsavelObra.setNome(String.valueOf(SQLUtil.getValorResultSet(resultSet, NOME, java.sql.Types.VARCHAR)));
				responsavelObra.setCNPJ((Long) SQLUtil.getValorResultSet(resultSet, CNPJ, java.sql.Types.BIGINT));
				responsavelObra.setEmail(String.valueOf(SQLUtil.getValorResultSet(resultSet, EMAIL, java.sql.Types.VARCHAR)));
				responsavelObra.setSite(String.valueOf(SQLUtil.getValorResultSet(resultSet, SITE, java.sql.Types.VARCHAR)));
				responsavelObra.setTelefone((Long) SQLUtil.getValorResultSet(resultSet, TELEFONE, java.sql.Types.BIGINT));
				responsavelObra.setCPF((Long) SQLUtil.getValorResultSet(resultSet, CPF, java.sql.Types.BIGINT));
				responsavelObra.setRazaoSocial(String.valueOf(SQLUtil.getValorResultSet(resultSet, RAZAO_SOCIAL, java.sql.Types.VARCHAR)));
				responsavelObra.setTipoPessoa((Integer) SQLUtil.getValorResultSet(resultSet, TIPO_PESSOA, java.sql.Types.INTEGER));
				listaResponsavelObra.add(responsavelObra);		
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
		return listaResponsavelObra;
	}

	@Override
	public ResponsavelObra buscarPorId(Integer id, Connection con) throws SQLException, Exception {
		StringBuffer query = new StringBuffer();
		query.append("SELECT * FROM ");
		query.append(RESPONSAVEL_OBRA);
		query.append(" WHERE ");
		query.append(ID);		
		query.append(" = ? ");
		query.append(";");
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		ResponsavelObra responsavelObra = null;		
		try {
			preparedStatement = con.prepareStatement(query.toString());
			SQLUtil.setValorPpreparedStatement(preparedStatement, 1, id, java.sql.Types.INTEGER);				
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()){
				responsavelObra = new ResponsavelObra();
				responsavelObra.setId((Integer) SQLUtil.getValorResultSet(resultSet, ID, java.sql.Types.INTEGER));
				responsavelObra.setNome(String.valueOf(SQLUtil.getValorResultSet(resultSet, NOME, java.sql.Types.VARCHAR)));
				responsavelObra.setCNPJ((Long) SQLUtil.getValorResultSet(resultSet, CNPJ, java.sql.Types.BIGINT));
				responsavelObra.setEmail(String.valueOf(SQLUtil.getValorResultSet(resultSet, EMAIL, java.sql.Types.VARCHAR)));
				responsavelObra.setSite(String.valueOf(SQLUtil.getValorResultSet(resultSet, SITE, java.sql.Types.VARCHAR)));
				responsavelObra.setTelefone((Long) SQLUtil.getValorResultSet(resultSet, TELEFONE, java.sql.Types.BIGINT));
				responsavelObra.setTipoPessoa((Integer) SQLUtil.getValorResultSet(resultSet, TIPO_PESSOA, java.sql.Types.INTEGER));
				responsavelObra.setCPF((Long) SQLUtil.getValorResultSet(resultSet, CPF, java.sql.Types.BIGINT));
				responsavelObra.setRazaoSocial(String.valueOf(SQLUtil.getValorResultSet(resultSet, RAZAO_SOCIAL, java.sql.Types.VARCHAR)));
			}
		} catch (SQLException e) {
			con.rollback();
			throw e;
		} catch (Exception e) {
			con.rollback();
			throw e;	
		
		}				
		return responsavelObra;		
	}	
	
}
