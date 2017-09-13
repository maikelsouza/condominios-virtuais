package br.com.condominiosvirtuais.persistence.impl;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.inject.Instance;
import javax.inject.Inject;

import org.apache.log4j.Logger;

import br.com.condominiosvirtuais.entity.Arquivo;
import br.com.condominiosvirtuais.entity.Bloco;
import br.com.condominiosvirtuais.entity.Condominio;
import br.com.condominiosvirtuais.entity.Condomino;
import br.com.condominiosvirtuais.entity.GestorCondominio;
import br.com.condominiosvirtuais.entity.Unidade;
import br.com.condominiosvirtuais.enumeration.TipoGestorCondominioEnum;
import br.com.condominiosvirtuais.exception.BusinessException;
import br.com.condominiosvirtuais.persistence.ArquivoDAO;
import br.com.condominiosvirtuais.persistence.CondominoDAO;
import br.com.condominiosvirtuais.util.SQLUtil;

public class CondominoDAOImpl implements CondominoDAO, Serializable {
	
	private static final long serialVersionUID = 1L;

	private static Logger logger = Logger.getLogger(CondominoDAOImpl.class);
	
	private static final String CONDOMINO = "CONDOMINO";
	
	private static final String ID = "ID";
	
	private static final String PROPRIETARIO = "PROPRIETARIO";		
	
	private static final String TELEFONE_CELULAR = "TELEFONE_CELULAR";
	
	private static final String TELEFONE_RESIDENCIAL = "TELEFONE_RESIDENCIAL";
	
	private static final String TELEFONE_COMERCIAL = "TELEFONE_COMERCIAL";
	
	private static final String PAGADOR = "PAGADOR";
	
	private static final String ID_UNIDADE = "ID_UNIDADE";
	
	@Inject
	private UsuarioDAOImpl usuarioDAO  = null;
	
	@Inject
	private GestorCondominioDAOImpl gestorCondominioDAO = null;
	
	@Inject
	private Instance<ArquivoDAO> arquivoDAO = null;	
	
	// Constraint referente a integridade entre um gestor condomínio e condômino, ou seja, não será possível excluir um 
	// condômino caso ele seja um gestor do condomínio.
	private static final String FK_GESTOR_CONDOMINIO_ID_CONDOMINO_CONDOMINO_ID = "FK_GESTOR_CONDOMINIO_ID_CONDOMINO_CONDOMINO_ID";
		
	// Constraint referente a integridade entre uma reserva e um condônino, ou seja, não será possível excluir um condômino
	// caso esse possua uma reserva.
	private static final String FK_RESERVA_ID_CONDOMINO_CONDOMINO_ID = "FK_RESERVA_ID_CONDOMINO_CONDOMINO_ID";
	
	// Constraint referente a integridade entre uma mensagem enviada e um condônino, ou seja, caso esse condômino tenha recebido uma msg o remetente agora
	// possui ele na sua lista de destinatário, logo não é possível excluir esse usuário caso a msg não seja excluída
	private static final String FK_USU_RECEBI_MENSAGEM_ID_USUARIO_USUARIO_ID = "FK_USU_RECEBI_MENSAGEM_ID_USUARIO_USUARIO_ID";
	
	
	
	
	
	public List<Condomino> buscarListaCondominos() throws SQLException, Exception{
		StringBuffer query = new StringBuffer();
		query.append("SELECT * FROM ");
		query.append(CONDOMINO);
		query.append(";");		
		Connection con = Conexao.getConexao();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		List<Condomino> listaCondomino = new ArrayList<Condomino>();
		Condomino condomino = null;
		try {
			preparedStatement = con.prepareStatement(query.toString());
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()){
				condomino = new Condomino();
				condomino.setId((Integer) SQLUtil.getValorResultSet(resultSet, ID, java.sql.Types.INTEGER));
				condomino.setProprietario((Boolean) SQLUtil.getValorResultSet(resultSet, PROPRIETARIO, java.sql.Types.BOOLEAN));
				condomino.setTelefoneCelular((Long) SQLUtil.getValorResultSet(resultSet, TELEFONE_CELULAR, java.sql.Types.BIGINT));
				condomino.setTelefoneComercial((Long) SQLUtil.getValorResultSet(resultSet, TELEFONE_COMERCIAL, java.sql.Types.BIGINT));
				condomino.setTelefoneResidencial((Long) SQLUtil.getValorResultSet(resultSet, TELEFONE_COMERCIAL, java.sql.Types.BIGINT));
				condomino.setIdUnidade((Integer) SQLUtil.getValorResultSet(resultSet, ID_UNIDADE, java.sql.Types.INTEGER));
				listaCondomino.add(condomino);
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
		return listaCondomino;
	}
	
	public List<Condomino> buscarListaCondominosPorUnidade(Unidade unidade) throws SQLException, Exception{
		StringBuffer query = new StringBuffer();
		query.append("SELECT * FROM ");
		query.append(CONDOMINO);
		query.append(" WHERE ");
		query.append(ID_UNIDADE);
		query.append(" = ?");
		query.append(";");		
		Connection con = Conexao.getConexao();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		List<Condomino> listaCondomino = new ArrayList<Condomino>();
		Condomino condomino = null;
		Arquivo arquivo = null;
		try {
			preparedStatement = con.prepareStatement(query.toString());
			SQLUtil.setValorPpreparedStatement(preparedStatement,1, unidade.getId(), java.sql.Types.INTEGER);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()){
				condomino = new Condomino();				
				condomino.setId((Integer) SQLUtil.getValorResultSet(resultSet, ID, java.sql.Types.INTEGER));
				condomino.setProprietario((Boolean) SQLUtil.getValorResultSet(resultSet, PROPRIETARIO, java.sql.Types.BOOLEAN));
				condomino.setTelefoneCelular((Long) SQLUtil.getValorResultSet(resultSet, TELEFONE_CELULAR, java.sql.Types.BIGINT));
				condomino.setTelefoneComercial((Long) SQLUtil.getValorResultSet(resultSet, TELEFONE_COMERCIAL, java.sql.Types.BIGINT));
				condomino.setTelefoneResidencial((Long) SQLUtil.getValorResultSet(resultSet, TELEFONE_RESIDENCIAL, java.sql.Types.BIGINT));
				condomino.setIdUnidade((Integer) SQLUtil.getValorResultSet(resultSet, ID_UNIDADE, java.sql.Types.INTEGER));
				condomino.setPagador((Integer) SQLUtil.getValorResultSet(resultSet, PAGADOR, java.sql.Types.INTEGER));
				this.usuarioDAO.buscarEPopularUsuarioPeloId(condomino, con);
				arquivo = this.arquivoDAO.get().buscarPorCondomino(condomino, con);
				condomino.setImagem(arquivo);
				listaCondomino.add(condomino);
			}
		}catch (SQLException e) {
			throw e;
		}catch (Exception e) {		
			throw e;
		}finally{
			try {
				preparedStatement.close();
				con.close();
				this.arquivoDAO.destroy(this.arquivoDAO.get());
			} catch (SQLException e) {
				logger.error("erro sqlstate "+e.getSQLState(), e);
			}
		}	
		return listaCondomino;
	}
	
	public List<Integer> buscarListaIdsCondominosPorIdUnidade(Integer idUnidade) throws SQLException, Exception{
		StringBuffer query = new StringBuffer();
		query.append("SELECT * FROM ");
		query.append(CONDOMINO);
		query.append(" WHERE ");
		query.append(ID_UNIDADE);
		query.append(" = ?");
		query.append(";");		
		Connection con = Conexao.getConexao();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;	
		List<Integer> listaIds = new ArrayList<Integer>();
		Integer id = null;
		try {
			preparedStatement = con.prepareStatement(query.toString());
			SQLUtil.setValorPpreparedStatement(preparedStatement,1, idUnidade, java.sql.Types.INTEGER);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()){
				id = (Integer) SQLUtil.getValorResultSet(resultSet, ID, java.sql.Types.INTEGER);
				listaIds.add(id);
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
		return listaIds;
	}
	
	public void salvarCondomino(Condomino condomino) throws SQLException, Exception{
		PreparedStatement statement = null;
		Connection con = null;
		con = Conexao.getConexao();
		con.setAutoCommit(false);
		this.usuarioDAO.salvarUsuario(condomino, con);
		StringBuffer query = new StringBuffer();
		query.append("INSERT INTO "); 
		query.append(CONDOMINO); 
		query.append("(");
		query.append(ID); 
		query.append(",");
		query.append(PROPRIETARIO); 
		query.append(",");
		query.append(TELEFONE_CELULAR); 
		query.append(",");
		query.append(TELEFONE_RESIDENCIAL); 
		query.append(",");
		query.append(TELEFONE_COMERCIAL);
		query.append(",");
		query.append(ID_UNIDADE);
		query.append(",");
		query.append(PAGADOR);
		query.append(") ");
		query.append("VALUES(?,?,?,?,?,?,?)");
		try {
			statement = con.prepareStatement(query.toString());
			SQLUtil.setValorPpreparedStatement(statement,1, condomino.getId(), java.sql.Types.INTEGER);
			SQLUtil.setValorPpreparedStatement(statement,2, condomino.getProprietario(), java.sql.Types.BOOLEAN);
			SQLUtil.setValorPpreparedStatement(statement,3, condomino.getTelefoneCelular(), java.sql.Types.BIGINT);
			SQLUtil.setValorPpreparedStatement(statement,4, condomino.getTelefoneResidencial(), java.sql.Types.BIGINT);
			SQLUtil.setValorPpreparedStatement(statement,5, condomino.getTelefoneComercial(), java.sql.Types.BIGINT);
			SQLUtil.setValorPpreparedStatement(statement,6, condomino.getIdUnidade(), java.sql.Types.INTEGER);			
			SQLUtil.setValorPpreparedStatement(statement,7, condomino.getPagador(), java.sql.Types.INTEGER);			
			statement.execute();			
			con.commit();
		} catch (SQLException e) {
			throw e;
		} catch (Exception e) {		
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
	
	public List<Condomino> buscarListaCondominosPeloNomeEPorUnidade(String nomeCondomino, Unidade unidade) throws SQLException, Exception{
		StringBuffer query = new StringBuffer();
		query.append("SELECT * FROM ");
		query.append(CONDOMINO);
		query.append(" WHERE ");
		query.append(ID_UNIDADE);		
		query.append(" = ?");
		query.append(";");		
		Connection con = Conexao.getConexao();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		List<Condomino> listaCondomino = new ArrayList<Condomino>();
		Condomino condomino = null;
		Arquivo arquivo = null;
		try {
			preparedStatement = con.prepareStatement(query.toString());
			preparedStatement.setInt(1, unidade.getId());
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()){
				condomino = new Condomino();				
				condomino.setId((Integer) SQLUtil.getValorResultSet(resultSet, ID, java.sql.Types.INTEGER));
				condomino.setProprietario((Boolean) SQLUtil.getValorResultSet(resultSet, PROPRIETARIO, java.sql.Types.BOOLEAN));
				condomino.setTelefoneCelular((Long) SQLUtil.getValorResultSet(resultSet, TELEFONE_CELULAR, java.sql.Types.BIGINT));
				condomino.setTelefoneComercial((Long) SQLUtil.getValorResultSet(resultSet, TELEFONE_COMERCIAL, java.sql.Types.BIGINT));
				condomino.setTelefoneResidencial((Long) SQLUtil.getValorResultSet(resultSet, TELEFONE_COMERCIAL, java.sql.Types.BIGINT));
				condomino.setIdUnidade((Integer) SQLUtil.getValorResultSet(resultSet, ID_UNIDADE, java.sql.Types.INTEGER));
				// Condição que garante que só adicona um condômino na lista caso tenha encontrado um usuário que atenda as condições da pesquisa.
				if(this.usuarioDAO.popularUsuarioPeloIdENome(condomino, nomeCondomino, con)){
					listaCondomino.add(condomino);					
				}
				arquivo = this.arquivoDAO.get().buscarPorCondomino(condomino, con);
				condomino.setImagem(arquivo);
			}
		}catch (SQLException e) {
			throw e;
		}catch (Exception e) {
			throw e;
		}finally{
			try {
				preparedStatement.close();
				con.close();
				this.arquivoDAO.destroy(this.arquivoDAO.get());
			} catch (SQLException e) {
				logger.error("erro sqlstate "+e.getSQLState(), e);
			}
		}	
		return listaCondomino;
	}
	
	public void atualizarCondomino(Condomino condomino) throws SQLException, Exception{
		StringBuffer query = new StringBuffer();
		query.append("UPDATE ");
		query.append(CONDOMINO);
		query.append(" SET ");
		query.append(PROPRIETARIO);
		query.append("= ?, ");
		query.append(TELEFONE_CELULAR); 
		query.append("= ?, ");
		query.append(TELEFONE_RESIDENCIAL); 
		query.append("= ?, ");
		query.append(TELEFONE_COMERCIAL);
		query.append("= ?, ");
		query.append(ID_UNIDADE);	
		query.append("= ? ");		
		query.append("WHERE ");
		query.append(ID);
		query.append("= ?");
		Connection con = Conexao.getConexao();
		con.setAutoCommit(false);
		this.usuarioDAO.atualizarUsuario(condomino, con);
		PreparedStatement statement = null;
		try {			
			statement = con.prepareStatement(query.toString());			
			SQLUtil.setValorPpreparedStatement(statement,1, condomino.getProprietario(), java.sql.Types.BOOLEAN);
			SQLUtil.setValorPpreparedStatement(statement,2, condomino.getTelefoneCelular(), java.sql.Types.BIGINT);
			SQLUtil.setValorPpreparedStatement(statement,3, condomino.getTelefoneResidencial(), java.sql.Types.BIGINT);
			SQLUtil.setValorPpreparedStatement(statement,4, condomino.getTelefoneComercial(), java.sql.Types.BIGINT);
			SQLUtil.setValorPpreparedStatement(statement,5, condomino.getIdUnidade(), java.sql.Types.INTEGER);
			SQLUtil.setValorPpreparedStatement(statement,6, condomino.getId(), java.sql.Types.INTEGER);			
			statement.executeUpdate();
			con.commit();
		} catch (SQLException e) {
			throw e;
		}catch (Exception e) {		
			throw e;
		}finally {
			try {		
				statement.close();
				con.close();				
			} catch (SQLException e) {
				logger.error("erro sqlstate "+e.getSQLState(), e);
			}
		}
	}
	
	public void excluirCondomino(Condomino condomino) throws SQLException, BusinessException, Exception{
		StringBuffer query = new StringBuffer();
		Connection con = Conexao.getConexao();
		con.setAutoCommit(false);
		PreparedStatement statement = null;
		try {
			if (condomino.getImagem() != null){
				this.arquivoDAO.get().setCommit(Boolean.FALSE);
				this.arquivoDAO.get().excluirArquivoCondomino(condomino.getImagem(), con);				
			}
			query.append("DELETE FROM ");
			query.append(CONDOMINO);
			query.append(" WHERE ");		
			query.append(ID);
			query.append(" = ?");
			statement = con.prepareStatement(query.toString());
			statement.setInt(1, condomino.getId());
			statement.executeUpdate();
			this.usuarioDAO.excluirUsuario(condomino, con);
			con.commit();			
			this.arquivoDAO.get().executeListaFile(ArquivoDAO.DELETE);
		} catch (SQLException e) {
			if (e.getMessage().contains(FK_GESTOR_CONDOMINIO_ID_CONDOMINO_CONDOMINO_ID)){
				throw new BusinessException("msg.condomino.gestorCondominioAssociado");				
			}else if (e.getMessage().contains(FK_RESERVA_ID_CONDOMINO_CONDOMINO_ID)){
				throw new BusinessException("msg.condomino.reservaAssociada");		
			}else if (e.getMessage().contains(FK_USU_RECEBI_MENSAGEM_ID_USUARIO_USUARIO_ID)){
				throw new BusinessException("msg.condomino.destinatarioAssociado");		
			}else{		
				throw e;		
			}
		} catch (Exception e) {
			throw e;
		}finally {
			try {		
				statement.close();
				con.close();		
				this.arquivoDAO.destroy(this.arquivoDAO.get());
			} catch (SQLException e) {
				logger.error("erro sqlstate "+e.getSQLState(), e);			
			}
		}	
	}
	
	public Condomino buscarCondominoPorId(Integer idCondomino) throws SQLException, Exception{
		StringBuffer query = new StringBuffer();
		query.append("SELECT * FROM ");
		query.append(CONDOMINO);
		query.append(" WHERE ");
		query.append(ID);		
		query.append(" = ?");
		query.append(";");		
		Connection con = Conexao.getConexao();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;		
		Condomino condomino = null;
		try {
			preparedStatement = con.prepareStatement(query.toString());			
			SQLUtil.setValorPpreparedStatement(preparedStatement, 1,idCondomino, java.sql.Types.INTEGER);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()){
				condomino = new Condomino();				
				condomino.setId((Integer) SQLUtil.getValorResultSet(resultSet, ID, java.sql.Types.INTEGER));
				condomino.setProprietario((Boolean) SQLUtil.getValorResultSet(resultSet, PROPRIETARIO, java.sql.Types.BOOLEAN));
				condomino.setTelefoneCelular((Long) SQLUtil.getValorResultSet(resultSet, TELEFONE_CELULAR, java.sql.Types.BIGINT));
				condomino.setTelefoneComercial((Long) SQLUtil.getValorResultSet(resultSet, TELEFONE_COMERCIAL, java.sql.Types.BIGINT));
				condomino.setTelefoneResidencial((Long) SQLUtil.getValorResultSet(resultSet, TELEFONE_RESIDENCIAL, java.sql.Types.BIGINT));
				condomino.setIdUnidade((Integer) SQLUtil.getValorResultSet(resultSet, ID_UNIDADE, java.sql.Types.INTEGER));
				this.usuarioDAO.buscarEPopularUsuarioPeloId(condomino, con);
				condomino.setImagem(this.arquivoDAO.get().buscarPorCondomino(condomino, con));				
			}
		}catch (SQLException e) {
			throw e;
		}catch (Exception e) {		
			throw e;
		}finally{
			try {
				preparedStatement.close();
				con.close();				
				this.arquivoDAO.destroy(this.arquivoDAO.get());
			} catch (SQLException e) {
				logger.error("erro sqlstate "+e.getSQLState(), e);	
			}
		}	
		return condomino;		
	}
	
	public Condomino buscarCondominoPorId(Integer idCondomino, Connection con) throws SQLException, Exception{
		StringBuffer query = new StringBuffer();
		query.append("SELECT * FROM ");
		query.append(CONDOMINO);
		query.append(" WHERE ");
		query.append(ID);		
		query.append(" = ?");
		query.append(";");
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;		
		Condomino condomino = null;
		try {
			preparedStatement = con.prepareStatement(query.toString());
			preparedStatement.setInt(1, idCondomino);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()){
				condomino = new Condomino();				
				condomino.setId((Integer) SQLUtil.getValorResultSet(resultSet, ID, java.sql.Types.INTEGER));
				condomino.setProprietario((Boolean) SQLUtil.getValorResultSet(resultSet, PROPRIETARIO, java.sql.Types.BOOLEAN));
				condomino.setTelefoneCelular((Long) SQLUtil.getValorResultSet(resultSet, TELEFONE_CELULAR, java.sql.Types.BIGINT));
				condomino.setTelefoneComercial((Long) SQLUtil.getValorResultSet(resultSet, TELEFONE_COMERCIAL, java.sql.Types.BIGINT));
				condomino.setTelefoneResidencial((Long) SQLUtil.getValorResultSet(resultSet, TELEFONE_RESIDENCIAL, java.sql.Types.BIGINT));
				condomino.setIdUnidade((Integer) SQLUtil.getValorResultSet(resultSet, ID_UNIDADE, java.sql.Types.INTEGER));
				this.usuarioDAO.buscarEPopularUsuarioPeloId(condomino, con);
				condomino.setImagem(this.arquivoDAO.get().buscarPorCondomino(condomino, con));
			}
		}catch (SQLException e) {
			con.rollback();
			throw e;
		}catch (Exception e) {
			con.rollback();
			throw e;			
		}finally{
			this.arquivoDAO.destroy(this.arquivoDAO.get());
		}
		return condomino;		
	}
	
	public Condomino buscarCondominoPorIdSemImagem(Integer idCondomino, Connection con) throws SQLException, Exception{
		StringBuffer query = new StringBuffer();
		query.append("SELECT * FROM ");
		query.append(CONDOMINO);
		query.append(" WHERE ");
		query.append(ID);		
		query.append(" = ?");
		query.append(";");
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;		
		Condomino condomino = null;
		try {
			preparedStatement = con.prepareStatement(query.toString());
			preparedStatement.setInt(1, idCondomino);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()){
				condomino = new Condomino();				
				condomino.setId((Integer) SQLUtil.getValorResultSet(resultSet, ID, java.sql.Types.INTEGER));
				condomino.setProprietario((Boolean) SQLUtil.getValorResultSet(resultSet, PROPRIETARIO, java.sql.Types.BOOLEAN));
				condomino.setTelefoneCelular((Long) SQLUtil.getValorResultSet(resultSet, TELEFONE_CELULAR, java.sql.Types.BIGINT));
				condomino.setTelefoneComercial((Long) SQLUtil.getValorResultSet(resultSet, TELEFONE_COMERCIAL, java.sql.Types.BIGINT));
				condomino.setTelefoneResidencial((Long) SQLUtil.getValorResultSet(resultSet, TELEFONE_RESIDENCIAL, java.sql.Types.BIGINT));
				condomino.setIdUnidade((Integer) SQLUtil.getValorResultSet(resultSet, ID_UNIDADE, java.sql.Types.INTEGER));
				this.usuarioDAO.buscarEPopularUsuarioPeloId(condomino, con);				
			}
		}catch (SQLException e) {
			con.rollback();
			throw e;
		}catch (Exception e) {
			con.rollback();		
			throw e;			
		}							
		return condomino;		
	}
	
	public List<Condomino>buscarConselheirosPorCondominio(Condominio condominio) throws SQLException, Exception{
		List<Condomino> listaDeCondomino = new ArrayList<Condomino>();
		List<GestorCondominio> listaDeGestorCondominio = this.gestorCondominioDAO.buscarListaGestoresCondominioPorCondominio(condominio);
		for (GestorCondominio gestorCondominio : listaDeGestorCondominio) {
			if(gestorCondominio.getTipoCondomino() == TipoGestorCondominioEnum.CONSELHEIRO_CONDOMINIO.getGestorCondominio()){
				listaDeCondomino.add(this.buscarCondominoPorId(gestorCondominio.getIdUsuario()));				
			}
		}				
		return listaDeCondomino;
	}
	
	public Condomino buscarSindicoGeralPorCondominio(Condominio condominio) throws SQLException, Exception{
		Condomino condomino = null;
		Boolean encontrou = Boolean.FALSE;
		List<GestorCondominio> listaDeGestorCondominio = this.gestorCondominioDAO.buscarListaGestoresCondominioPorCondominio(condominio);
		Integer indice = 0;
		while (encontrou == Boolean.FALSE && indice < listaDeGestorCondominio.size()){
			GestorCondominio gestorCondominio = listaDeGestorCondominio.get(indice++);
			if(gestorCondominio.getTipoCondomino() == TipoGestorCondominioEnum.SINDICO_GERAL.getGestorCondominio()){
				condomino = this.buscarCondominoPorId(gestorCondominio.getIdUsuario());
				encontrou = Boolean.TRUE;
			}			
		}		
		return condomino;
	}
	
	public Condomino buscarSindicoGeralPorCondominio(Condominio condominio, Connection con) throws SQLException, Exception{
		Condomino condomino = null;
		Boolean encontrou = Boolean.FALSE;
		List<GestorCondominio> listaDeGestorCondominio = this.gestorCondominioDAO.buscarListaGestoresCondominioPorCondominio(condominio,con);
		Integer indice = 0;
		while (encontrou == Boolean.FALSE && indice < listaDeGestorCondominio.size()){
			GestorCondominio gestorCondominio = listaDeGestorCondominio.get(indice++);
			if(gestorCondominio.getTipoCondomino() == TipoGestorCondominioEnum.SINDICO_GERAL.getGestorCondominio()){
				condomino = this.buscarCondominoPorId(gestorCondominio.getIdUsuario(),con);
				encontrou = Boolean.TRUE;
			}			
		}		
		return condomino;
	}
	
	public Condomino buscarSubSindicoGeralPorCondominio(Condominio condominio, Connection con) throws SQLException, Exception{
		Condomino condomino = null;
		Boolean encontrou = Boolean.FALSE;
		List<GestorCondominio> listaDeGestorCondominio = this.gestorCondominioDAO.buscarListaGestoresCondominioPorCondominio(condominio,con);
		Integer indice = 0;
		while (encontrou == Boolean.FALSE && indice < listaDeGestorCondominio.size()){
			GestorCondominio gestorCondominio = listaDeGestorCondominio.get(indice++);
			if(gestorCondominio.getTipoCondomino() == TipoGestorCondominioEnum.SUBSINDICO_GERAL.getGestorCondominio()){
				condomino = this.buscarCondominoPorId(gestorCondominio.getIdUsuario());
				encontrou = Boolean.TRUE;
			}			
		}		
		return condomino;
	}			

	public Condomino buscarSubSindicoGeralPorCondominio(Condominio condominio) throws SQLException, Exception{
		Condomino condomino = null;
		Boolean encontrou = Boolean.FALSE;
		List<GestorCondominio> listaDeGestorCondominio = this.gestorCondominioDAO.buscarListaGestoresCondominioPorCondominio(condominio);
		Integer indice = 0;
		while (encontrou == Boolean.FALSE && indice < listaDeGestorCondominio.size()){
			GestorCondominio gestorCondominio = listaDeGestorCondominio.get(indice++);
			if(gestorCondominio.getTipoCondomino() == TipoGestorCondominioEnum.SUBSINDICO_GERAL.getGestorCondominio()){
				condomino = this.buscarCondominoPorId(gestorCondominio.getIdUsuario());
				encontrou = Boolean.TRUE;
			}			
		}		
		return condomino;
	}
	
	public List<Condomino>buscarConselheirosPorBloco(Bloco bloco, Connection con) throws SQLException, Exception{
		List<Condomino> listaDeCondomino = new ArrayList<Condomino>();
		List<GestorCondominio> listaDeGestorBloco = this.gestorCondominioDAO.buscarListaGestoresCondominioPorBloco(bloco,con);
		for (GestorCondominio gestorCondominio : listaDeGestorBloco) {
			if(gestorCondominio.getTipoCondomino() == TipoGestorCondominioEnum.CONSELHEIRO_BLOCO.getGestorCondominio()){
				listaDeCondomino.add(this.buscarCondominoPorId(gestorCondominio.getIdUsuario()));				
			}
		}				
		return listaDeCondomino;
	}
	
	public List<Condomino>buscarConselheirosPorBloco(Bloco bloco) throws SQLException, Exception{
		List<Condomino> listaDeCondomino = new ArrayList<Condomino>();
		List<GestorCondominio> listaDeGestorBloco = this.gestorCondominioDAO.buscarListaGestoresCondominioPorBloco(bloco);
		for (GestorCondominio gestorCondominio : listaDeGestorBloco) {
			if(gestorCondominio.getTipoCondomino() == TipoGestorCondominioEnum.CONSELHEIRO_BLOCO.getGestorCondominio()){
				listaDeCondomino.add(this.buscarCondominoPorId(gestorCondominio.getIdUsuario()));				
			}
		}				
		return listaDeCondomino;
	}
	
	public Condomino buscarSindicoPorBloco(Bloco bloco, Connection con) throws SQLException, Exception{
		Condomino condomino = null;
		Boolean encontrou = Boolean.FALSE;
		List<GestorCondominio> listaDeGestorBloco = this.gestorCondominioDAO.buscarListaGestoresCondominioPorBloco(bloco, con);
		Integer indice = 0;
		while (encontrou == Boolean.FALSE && indice < listaDeGestorBloco.size()){
			GestorCondominio gestorCondominio = listaDeGestorBloco.get(indice++);
			if(gestorCondominio.getTipoCondomino() == TipoGestorCondominioEnum.SINDICO.getGestorCondominio()){
				condomino = this.buscarCondominoPorId(gestorCondominio.getIdUsuario());
				encontrou = Boolean.TRUE;
			}			
		}		
		return condomino;
	}
	
	public Condomino buscarSindicoPorBloco(Bloco bloco) throws SQLException, Exception{
		Condomino condomino = null;
		Boolean encontrou = Boolean.FALSE;
		List<GestorCondominio> listaDeGestorBloco = this.gestorCondominioDAO.buscarListaGestoresCondominioPorBloco(bloco);
		Integer indice = 0;
		while (encontrou == Boolean.FALSE && indice < listaDeGestorBloco.size()){
			GestorCondominio gestorCondominio = listaDeGestorBloco.get(indice++);
			if(gestorCondominio.getTipoCondomino() == TipoGestorCondominioEnum.SINDICO.getGestorCondominio()){
				condomino = this.buscarCondominoPorId(gestorCondominio.getIdUsuario());
				encontrou = Boolean.TRUE;
			}			
		}		
		return condomino;
	}
	
	
	public Condomino buscarSubSindicoPorBloco(Bloco bloco, Connection con) throws SQLException, Exception{
		Condomino condomino = null;
		Boolean encontrou = Boolean.FALSE;
		List<GestorCondominio> listaDeGestorBloco = this.gestorCondominioDAO.buscarListaGestoresCondominioPorBloco(bloco,con);
		Integer indice = 0;
		while (encontrou == Boolean.FALSE && indice < listaDeGestorBloco.size()){
			GestorCondominio gestorCondominio = listaDeGestorBloco.get(indice++);
			if(gestorCondominio.getTipoCondomino() == TipoGestorCondominioEnum.SUBSINDICO.getGestorCondominio()){
				condomino = this.buscarCondominoPorId(gestorCondominio.getIdUsuario());
				encontrou = Boolean.TRUE;
			}			
		}		
		return condomino;
	}
	
	public Condomino buscarSubSindicoPorBloco(Bloco bloco) throws SQLException, Exception {
		Condomino condomino = null;
		Boolean encontrou = Boolean.FALSE;
		List<GestorCondominio> listaDeGestorBloco = this.gestorCondominioDAO.buscarListaGestoresCondominioPorBloco(bloco);
		Integer indice = 0;
		while (encontrou == Boolean.FALSE && indice < listaDeGestorBloco.size()){
			GestorCondominio gestorCondominio = listaDeGestorBloco.get(indice++);
			if(gestorCondominio.getTipoCondomino() == TipoGestorCondominioEnum.SUBSINDICO.getGestorCondominio()){
				condomino = this.buscarCondominoPorId(gestorCondominio.getIdUsuario());
				encontrou = Boolean.TRUE;
			}			
		}		
		return condomino;
	}
	
	@Override
	public List<Condomino>buscarPorIdUnidadeEPagadorSemImagem(Integer idUnidade, Integer pagador) throws SQLException, Exception {
		StringBuffer query = new StringBuffer();
		query.append("SELECT * FROM ");
		query.append(CONDOMINO);
		query.append(" WHERE ");
		query.append(ID_UNIDADE);		
		query.append(" = ? ");
		query.append(" AND ");		
		query.append(PAGADOR);
		query.append(" = ? ");
		query.append(";");
		PreparedStatement preparedStatement = null;
		List<Condomino> listaCondominos = new ArrayList<Condomino>();
		ResultSet resultSet = null;		
		Condomino condomino = null;
		Connection con = Conexao.getConexao();
		try {
			preparedStatement = con.prepareStatement(query.toString());
			SQLUtil.setValorPpreparedStatement(preparedStatement, 1,idUnidade, java.sql.Types.INTEGER);
			SQLUtil.setValorPpreparedStatement(preparedStatement, 2,pagador, java.sql.Types.INTEGER);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()){
				condomino = new Condomino();				
				condomino.setId((Integer) SQLUtil.getValorResultSet(resultSet, ID, java.sql.Types.INTEGER));
				condomino.setProprietario((Boolean) SQLUtil.getValorResultSet(resultSet, PROPRIETARIO, java.sql.Types.BOOLEAN));
				condomino.setTelefoneCelular((Long) SQLUtil.getValorResultSet(resultSet, TELEFONE_CELULAR, java.sql.Types.BIGINT));
				condomino.setTelefoneComercial((Long) SQLUtil.getValorResultSet(resultSet, TELEFONE_COMERCIAL, java.sql.Types.BIGINT));
				condomino.setTelefoneResidencial((Long) SQLUtil.getValorResultSet(resultSet, TELEFONE_RESIDENCIAL, java.sql.Types.BIGINT));
				condomino.setIdUnidade((Integer) SQLUtil.getValorResultSet(resultSet, ID_UNIDADE, java.sql.Types.INTEGER));
				condomino.setPagador((Integer) SQLUtil.getValorResultSet(resultSet, PAGADOR, java.sql.Types.INTEGER));
				this.usuarioDAO.buscarEPopularUsuarioPeloId(condomino, con);	
				listaCondominos.add(condomino);
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
		return listaCondominos;		
		
	}
	
	@Override
	public List<Condomino>buscarPorIdUnidadeESituacaoSemImagem(Integer idUnidade,  Integer situacao) throws SQLException, Exception {
		StringBuffer query = new StringBuffer();
		query.append("SELECT * FROM ");
		query.append(CONDOMINO);
		query.append(" WHERE ");
		query.append(ID_UNIDADE);		
		query.append(" = ? ");
		query.append(";");
		PreparedStatement preparedStatement = null;
		List<Condomino> listaCondominos = new ArrayList<Condomino>();
		ResultSet resultSet = null;		
		Condomino condomino = null;
		Connection con = Conexao.getConexao();
		try {
			preparedStatement = con.prepareStatement(query.toString());
			SQLUtil.setValorPpreparedStatement(preparedStatement, 1,idUnidade, java.sql.Types.INTEGER);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()){
				condomino = new Condomino();				
				condomino.setId((Integer) SQLUtil.getValorResultSet(resultSet, ID, java.sql.Types.INTEGER));
				condomino.setProprietario((Boolean) SQLUtil.getValorResultSet(resultSet, PROPRIETARIO, java.sql.Types.BOOLEAN));
				condomino.setTelefoneCelular((Long) SQLUtil.getValorResultSet(resultSet, TELEFONE_CELULAR, java.sql.Types.BIGINT));
				condomino.setTelefoneComercial((Long) SQLUtil.getValorResultSet(resultSet, TELEFONE_COMERCIAL, java.sql.Types.BIGINT));
				condomino.setTelefoneResidencial((Long) SQLUtil.getValorResultSet(resultSet, TELEFONE_RESIDENCIAL, java.sql.Types.BIGINT));
				condomino.setIdUnidade((Integer) SQLUtil.getValorResultSet(resultSet, ID_UNIDADE, java.sql.Types.INTEGER));
				condomino.setPagador((Integer) SQLUtil.getValorResultSet(resultSet, PAGADOR, java.sql.Types.INTEGER));
				// Caso não tenha econtrado, ou seja, o usuário está inativo, então não add na lista. 
				if(this.usuarioDAO.buscarPorEPopularPeloIdESituacaoSemImagem(condomino, situacao, con)){
					listaCondominos.add(condomino);
				}
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
		return listaCondominos;		
		
	}
	
	@Override
	public List<Condomino>buscarPorIdUnidadeEidGrupoUsuarioESituacaoSemImagem(Integer idUnidade, Integer idGrupoUsuario, Integer situacao) throws SQLException, Exception {
		StringBuffer query = new StringBuffer();
		query.append("SELECT * FROM ");
		query.append(CONDOMINO);
		query.append(" WHERE ");
		query.append(ID_UNIDADE);		
		query.append(" = ? ");
		query.append(";");
		PreparedStatement preparedStatement = null;
		List<Condomino> listaCondominos = new ArrayList<Condomino>();
		ResultSet resultSet = null;		
		Condomino condomino = null;
		Connection con = Conexao.getConexao();
		try {
			preparedStatement = con.prepareStatement(query.toString());
			SQLUtil.setValorPpreparedStatement(preparedStatement, 1,idUnidade, java.sql.Types.INTEGER);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()){
				condomino = new Condomino();				
				condomino.setId((Integer) SQLUtil.getValorResultSet(resultSet, ID, java.sql.Types.INTEGER));
				condomino.setProprietario((Boolean) SQLUtil.getValorResultSet(resultSet, PROPRIETARIO, java.sql.Types.BOOLEAN));
				condomino.setTelefoneCelular((Long) SQLUtil.getValorResultSet(resultSet, TELEFONE_CELULAR, java.sql.Types.BIGINT));
				condomino.setTelefoneComercial((Long) SQLUtil.getValorResultSet(resultSet, TELEFONE_COMERCIAL, java.sql.Types.BIGINT));
				condomino.setTelefoneResidencial((Long) SQLUtil.getValorResultSet(resultSet, TELEFONE_RESIDENCIAL, java.sql.Types.BIGINT));
				condomino.setIdUnidade((Integer) SQLUtil.getValorResultSet(resultSet, ID_UNIDADE, java.sql.Types.INTEGER));
				condomino.setPagador((Integer) SQLUtil.getValorResultSet(resultSet, PAGADOR, java.sql.Types.INTEGER));
				// Caso não tenha econtrado, ou seja, o usuário está inativo, então não add na lista. 
				if(this.usuarioDAO.buscarPorEPopularPeloIdEGrupoUsuarioESituacaoSemImagem(condomino,idGrupoUsuario, situacao, con)){
					listaCondominos.add(condomino);
				}
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
		return listaCondominos;		
		
	}
	
	public UsuarioDAOImpl getUsuarioDAO() {
		return usuarioDAO;
	}

	public void setUsuarioDAO(UsuarioDAOImpl usuarioDAO) {
		this.usuarioDAO = usuarioDAO;
	}

	public GestorCondominioDAOImpl getGestorCondominioDAO() {
		return gestorCondominioDAO;
	}

	public void setGestorCondominioDAO(GestorCondominioDAOImpl gestorCondominioDAO) {
		this.gestorCondominioDAO = gestorCondominioDAO;
	}

	

}
