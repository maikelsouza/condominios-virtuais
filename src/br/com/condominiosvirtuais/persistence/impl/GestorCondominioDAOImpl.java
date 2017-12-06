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

import br.com.condominiosvirtuais.entity.Bloco;
import br.com.condominiosvirtuais.entity.Condominio;
import br.com.condominiosvirtuais.entity.GestorCondominio;
import br.com.condominiosvirtuais.entity.GrupoUsuario;
import br.com.condominiosvirtuais.entity.Usuario;
import br.com.condominiosvirtuais.entity.UsuarioGrupoUsuario;
import br.com.condominiosvirtuais.enumeration.GrupoUsuarioPadraoEnum;
import br.com.condominiosvirtuais.enumeration.GrupoUsuarioSituacaoEnum;
import br.com.condominiosvirtuais.enumeration.GrupoUsuarioTipoUsuarioEnum;
import br.com.condominiosvirtuais.enumeration.TipoGestorCondominioEnum;
import br.com.condominiosvirtuais.persistence.GestorCondominioDAO;
import br.com.condominiosvirtuais.persistence.UsuarioGrupoUsuarioDAO;
import br.com.condominiosvirtuais.service.GrupoUsuarioService;
import br.com.condominiosvirtuais.util.SQLUtil;

public class GestorCondominioDAOImpl implements GestorCondominioDAO, Serializable{
	
	private static final long serialVersionUID = 1L;

	private static Logger logger = Logger.getLogger(GestorCondominioDAOImpl.class);
	
	private static final String GESTOR_CONDOMINIO = "GESTOR_CONDOMINIO";

	private static final String ID = "ID";
	
	private static final String  ID_CONDOMINIO = "ID_CONDOMINIO";
	
	private static final String  ID_BLOCO = "ID_BLOCO";
	
	private static final String  ID_USUARIO = "ID_USUARIO";
	
	private static final String  TIPO_CONDOMINO = "TIPO_CONDOMINO";
	
	@Inject
	private UsuarioDAOImpl usuarioDAO = null;	
	
	@Inject
	private GrupoUsuarioService grupoUsuarioService = null;
	
	@Inject
	private UsuarioGrupoUsuarioDAO usuarioGrupoUsuarioDAO;
	
	
	public List<GestorCondominio> buscarListaGestoresCondominioPorCondominio(Condominio condominio) throws SQLException, Exception{		
		StringBuffer query = new StringBuffer();
		query.append("SELECT * FROM ");
		query.append(GESTOR_CONDOMINIO);
		query.append(" WHERE ");
		query.append(ID_CONDOMINIO);
		query.append(" = ? ");		
		query.append(";");
		Connection con = C3P0DataSource.getInstance().getConnection();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		List<GestorCondominio> listaGestorCondominio = new ArrayList<GestorCondominio>();
		GestorCondominio gestorCondominio = null;
		try {
			preparedStatement = con.prepareStatement(query.toString());
			preparedStatement.setInt(1, condominio.getId());
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()){
				gestorCondominio = new GestorCondominio();
				gestorCondominio.setId(resultSet.getInt(ID));
				gestorCondominio.setIdCondominio(resultSet.getInt(ID_CONDOMINIO));
				gestorCondominio.setIdUsuario(resultSet.getInt(ID_USUARIO));
				gestorCondominio.setIdBloco(resultSet.getInt(ID_BLOCO));
				gestorCondominio.setTipoCondomino(resultSet.getInt(TIPO_CONDOMINO));
				listaGestorCondominio.add(gestorCondominio);
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
		return listaGestorCondominio;
	}
								  							  
	public List<GestorCondominio> buscarListaGestoresCondominioPorCondominio(Condominio condominio, Connection con) throws SQLException, Exception{		
		StringBuffer query = new StringBuffer();
		query.append("SELECT * FROM ");
		query.append(GESTOR_CONDOMINIO);
		query.append(" WHERE ");
		query.append(ID_CONDOMINIO);
		query.append(" = ? ");		
		query.append(";");		
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		List<GestorCondominio> listaGestorCondominio = new ArrayList<GestorCondominio>();
		GestorCondominio gestorCondominio = null;
		try {
			preparedStatement = con.prepareStatement(query.toString());
			SQLUtil.setValorPpreparedStatement(preparedStatement, 1, condominio.getId(), java.sql.Types.INTEGER);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()){
				gestorCondominio = new GestorCondominio();
				gestorCondominio.setId((Integer) SQLUtil.getValorResultSet(resultSet, ID, java.sql.Types.INTEGER));
				gestorCondominio.setIdCondominio((Integer) SQLUtil.getValorResultSet(resultSet, ID_CONDOMINIO, java.sql.Types.INTEGER));
				gestorCondominio.setIdUsuario((Integer) SQLUtil.getValorResultSet(resultSet, ID_USUARIO, java.sql.Types.INTEGER));
				gestorCondominio.setIdBloco((Integer) SQLUtil.getValorResultSet(resultSet, ID_BLOCO, java.sql.Types.INTEGER));
				gestorCondominio.setTipoCondomino((Integer) SQLUtil.getValorResultSet(resultSet, TIPO_CONDOMINO, java.sql.Types.INTEGER));
				listaGestorCondominio.add(gestorCondominio);
			}
		} catch (SQLException e) {
			con.rollback();
			throw e;
		} catch (Exception e) {
			con.rollback();
			throw e;
		}
		return listaGestorCondominio;
	}
	
	private void removeSindicoProfissionalEAdicionaCondomino(Integer idCondominio, Usuario usuario) throws SQLException, Exception{
		List<GrupoUsuario> listaGrupoUsuarioRemover = new ArrayList<>();
		List<GrupoUsuario> listaGrupoUsuario = new ArrayList<GrupoUsuario>();
		// Recupera os grupos que são do tipo usuário síndico profissional (para remover) e os grupos do tipo condômino (esses serão removidos, 
		// pois serão adicionados posteriormente - Para garantir que não duplique)
		for (GrupoUsuario grupoUsuario : usuario.getListaGrupoUsuario()) {
			if (grupoUsuario.getTipoUsuario().intValue() == GrupoUsuarioTipoUsuarioEnum.SINDICO_PROFISSIONAL.getTipoUsuario().intValue()
					|| grupoUsuario.getTipoUsuario().intValue() == GrupoUsuarioTipoUsuarioEnum.CONDOMINO.getTipoUsuario().intValue()){
				listaGrupoUsuarioRemover.add(grupoUsuario);
			}
		}
		usuario.getListaGrupoUsuario().removeAll(listaGrupoUsuarioRemover);
		// Verifica se tem grupos que são ativos, padrão de um condômino 
		listaGrupoUsuario.addAll(this.grupoUsuarioService.buscarPorIdCondominioEPadraoETipoUsuarioESituacao(idCondominio,
				GrupoUsuarioPadraoEnum.SIM.getPadrao(), GrupoUsuarioTipoUsuarioEnum.CONDOMINO.getTipoUsuario(),GrupoUsuarioSituacaoEnum.ATIVO.getSituacao()));
		if(listaGrupoUsuario.isEmpty()){
			listaGrupoUsuario.add(this.grupoUsuarioService.buscarPorPadraoETipoUsuarioESituacao(GrupoUsuarioPadraoEnum.SIM.getPadrao(), GrupoUsuarioTipoUsuarioEnum.CONDOMINO.getTipoUsuario(),
					GrupoUsuarioSituacaoEnum.ATIVO.getSituacao()));			
		}
		usuario.getListaGrupoUsuario().addAll(listaGrupoUsuario);		
	}
	
	private void removeSindicoEAdicionaCondomino(Integer idCondominio, Usuario usuario) throws SQLException, Exception{
		List<GrupoUsuario> listaGrupoUsuarioRemover = new ArrayList<>();
		List<GrupoUsuario> listaGrupoUsuario = new ArrayList<GrupoUsuario>();
		// Recupera os grupos que são do tipo usuário síndico (para remover) e os grupos do tipo condômino (esses serão removidos, 
		// pois serão adicionados posteriormente - Para garantir que não duplique)
		for (GrupoUsuario grupoUsuario : usuario.getListaGrupoUsuario()) {
			if (grupoUsuario.getTipoUsuario().intValue() == GrupoUsuarioTipoUsuarioEnum.SINDICO.getTipoUsuario().intValue()
					|| grupoUsuario.getTipoUsuario().intValue() == GrupoUsuarioTipoUsuarioEnum.CONDOMINO.getTipoUsuario().intValue()){
				listaGrupoUsuarioRemover.add(grupoUsuario);
			}
		}
		usuario.getListaGrupoUsuario().removeAll(listaGrupoUsuarioRemover);
		// Verifica se tem grupos que são ativos, padrão de um condômino 
		listaGrupoUsuario.addAll(this.grupoUsuarioService.buscarPorIdCondominioEPadraoETipoUsuarioESituacao(idCondominio,
				GrupoUsuarioPadraoEnum.SIM.getPadrao(), GrupoUsuarioTipoUsuarioEnum.CONDOMINO.getTipoUsuario(),GrupoUsuarioSituacaoEnum.ATIVO.getSituacao()));
		if(listaGrupoUsuario.isEmpty()){
			listaGrupoUsuario.add(this.grupoUsuarioService.buscarPorPadraoETipoUsuarioESituacao(GrupoUsuarioPadraoEnum.SIM.getPadrao(), GrupoUsuarioTipoUsuarioEnum.CONDOMINO.getTipoUsuario(),
					GrupoUsuarioSituacaoEnum.ATIVO.getSituacao()));			
		}
		usuario.getListaGrupoUsuario().addAll(listaGrupoUsuario);		
	}
	
	private void removeCondominoEAdicionaSindico(Integer idCondominio, Usuario usuario, Connection con) throws SQLException, Exception{
		List<GrupoUsuario> listaGrupoUsuarioRemover = new ArrayList<>();
		List<GrupoUsuario> listaGrupoUsuario = new ArrayList<GrupoUsuario>();		
		// Recupera os grupos que são do tipo usuário síndico (para remover) e os grupos do tipo condômino (esses serão removidos, 
		// pois serão adicionados posteriormente - Para garantir que não duplique)
		for (GrupoUsuario grupoUsuario : usuario.getListaGrupoUsuario()) {
			if (grupoUsuario.getTipoUsuario().intValue() == GrupoUsuarioTipoUsuarioEnum.SINDICO.getTipoUsuario().intValue()
					|| grupoUsuario.getTipoUsuario().intValue() == GrupoUsuarioTipoUsuarioEnum.CONDOMINO.getTipoUsuario().intValue()){
				usuarioGrupoUsuarioDAO.excluirPorIdGrupoUsuarioEIdUsuario(grupoUsuario.getId(), usuario.getId(), con);
				listaGrupoUsuarioRemover.add(grupoUsuario);
			}
		}
		usuario.getListaGrupoUsuario().removeAll(listaGrupoUsuarioRemover);
		// Verifica se tem grupos que são ativos, padrão de um condômino 
		listaGrupoUsuario.addAll(this.grupoUsuarioService.buscarPorIdCondominioEPadraoETipoUsuarioESituacao(idCondominio,
				GrupoUsuarioPadraoEnum.SIM.getPadrao(), GrupoUsuarioTipoUsuarioEnum.SINDICO.getTipoUsuario(),GrupoUsuarioSituacaoEnum.ATIVO.getSituacao()));
		if(listaGrupoUsuario.isEmpty()){
			listaGrupoUsuario.add(this.grupoUsuarioService.buscarPorPadraoETipoUsuarioESituacao(GrupoUsuarioPadraoEnum.SIM.getPadrao(), GrupoUsuarioTipoUsuarioEnum.SINDICO.getTipoUsuario(),
					GrupoUsuarioSituacaoEnum.ATIVO.getSituacao()));			
		}
		usuario.getListaGrupoUsuario().addAll(listaGrupoUsuario);
		for (GrupoUsuario grupoUsuario : listaGrupoUsuario) {
			UsuarioGrupoUsuario usuarioGrupoUsuario = new UsuarioGrupoUsuario();
			usuarioGrupoUsuario.setIdGrupoUsuario(grupoUsuario.getId());
			usuarioGrupoUsuario.setIdUsuario(usuario.getId());
			this.usuarioGrupoUsuarioDAO.salvar(usuarioGrupoUsuario, con);
		}
	}
	
	private Boolean existeTipoUsuarioGrupoUsuario(Usuario usuario, Integer tipoUsuario) throws SQLException, Exception{
		for (GrupoUsuario grupoUsuario : usuario.getListaGrupoUsuario()) {
			if (grupoUsuario.getTipoUsuario().intValue() == tipoUsuario.intValue()){
				return Boolean.TRUE;
			}
		}		
		return Boolean.FALSE;		
	}
	
	public void salvarGestorCondominio(GestorCondominio gestorCondominio, Connection con) throws SQLException, Exception{
		Usuario usuario = this.usuarioDAO.buscarPorId(gestorCondominio.getIdUsuario());
		// Código que atualiza o grupo de usuários do usuário que virou um gestor do condomínio. Ou ele é condômino ou síndico. 
		//O Síndico Profissional não modifica o seu grupo
		if(gestorCondominio.getTipoCondomino() != TipoGestorCondominioEnum.SINDICO_GERAL.getGestorCondominio()){
			this.removeSindicoEAdicionaCondomino(gestorCondominio.getIdCondominio(), usuario);
			// Remove o grupo que é do tipo síndico e add um grupo que seja condômino (caso não tenha)
		}else if (gestorCondominio.getTipoCondomino() == TipoGestorCondominioEnum.SINDICO_GERAL.getGestorCondominio() && 
				!this.existeTipoUsuarioGrupoUsuario(usuario, GrupoUsuarioTipoUsuarioEnum.SINDICO_PROFISSIONAL.getTipoUsuario())){
			this.removeCondominoEAdicionaSindico(gestorCondominio.getIdCondominio(), usuario, con);
		}
		this.usuarioDAO.atualizarUsuario(usuario, con);
		StringBuffer query = new StringBuffer();
		query.append("INSERT INTO "); 
		query.append(GESTOR_CONDOMINIO);
		query.append("("); 
		query.append(ID_USUARIO); 
		query.append(",");
		query.append(TIPO_CONDOMINO); 
		if(gestorCondominio.getIdCondominio() != null){
			query.append(",");
			query.append(ID_CONDOMINIO);
		}
		if(gestorCondominio.getIdBloco() != null){
			query.append(",");			
			query.append(ID_BLOCO); 
		}
		query.append(") ");
		query.append("VALUES(?,?");
		if(gestorCondominio.getIdCondominio() != null){
			query.append(",?");
		}
		if(gestorCondominio.getIdBloco() != null){
			query.append(",?");
		}
		query.append(")");
		PreparedStatement statement = null;
		try {			
			statement = con.prepareStatement(query.toString());
			statement.setInt(1,gestorCondominio.getIdUsuario());
			statement.setInt(2,gestorCondominio.getTipoCondomino());
			if(gestorCondominio.getIdCondominio() != null && gestorCondominio.getIdBloco() != null){
				statement.setInt(3,gestorCondominio.getIdCondominio());				
				statement.setInt(4,gestorCondominio.getIdBloco());				
			}else if (gestorCondominio.getIdCondominio() == null && gestorCondominio.getIdBloco() != null){
				statement.setInt(3,gestorCondominio.getIdBloco());
			}else if(gestorCondominio.getIdCondominio() != null && gestorCondominio.getIdBloco() == null){
				statement.setInt(3,gestorCondominio.getIdCondominio());
			}			
			statement.executeUpdate();			
		} catch (SQLException e) {
			con.rollback();
			throw e;
		} catch (Exception e) {
			con.rollback();
			throw e;
		}
	}
	
	public void salvarGestorCondominioBloco(GestorCondominio gestorCondominio, Connection con) throws SQLException, Exception{
		StringBuffer query = new StringBuffer();
		query.append("INSERT INTO "); 
		query.append(GESTOR_CONDOMINIO);
		query.append("("); 
		query.append(ID_USUARIO); 
		query.append(",");
		query.append(TIPO_CONDOMINO); 
		if(gestorCondominio.getIdCondominio() != null){
			query.append(",");
			query.append(ID_CONDOMINIO);
		}
		if(gestorCondominio.getIdBloco() != null){
			query.append(",");			
			query.append(ID_BLOCO); 
		}
		query.append(") ");
		query.append("VALUES(?,?");
		if(gestorCondominio.getIdCondominio() != null){
			query.append(",?");
		}
		if(gestorCondominio.getIdBloco() != null){
			query.append(",?");
		}
		query.append(")");
		PreparedStatement statement = null;
		try {			
			statement = con.prepareStatement(query.toString());
			SQLUtil.setValorPpreparedStatement(statement, 1, gestorCondominio.getIdUsuario(), java.sql.Types.INTEGER);
			SQLUtil.setValorPpreparedStatement(statement, 2, gestorCondominio.getTipoCondomino(), java.sql.Types.INTEGER);			
			if(gestorCondominio.getIdCondominio() != null && gestorCondominio.getIdBloco() != null){
				SQLUtil.setValorPpreparedStatement(statement, 3, gestorCondominio.getIdCondominio(), java.sql.Types.INTEGER);
				SQLUtil.setValorPpreparedStatement(statement, 4, gestorCondominio.getIdBloco(), java.sql.Types.INTEGER);		
			}else if (gestorCondominio.getIdCondominio() == null && gestorCondominio.getIdBloco() != null){
				SQLUtil.setValorPpreparedStatement(statement, 3, gestorCondominio.getIdBloco(), java.sql.Types.INTEGER);
			}else if(gestorCondominio.getIdCondominio() != null && gestorCondominio.getIdBloco() == null){
				SQLUtil.setValorPpreparedStatement(statement, 4, gestorCondominio.getIdCondominio(), java.sql.Types.INTEGER);
			}			
			statement.executeUpdate();			
		} catch (SQLException e) {
			con.rollback();
			throw e;
		} catch (Exception e) {
			con.rollback();
			throw e;
		}
	}
	
	public void atualizarGestorCondominioPorCondominio(GestorCondominio gestorCondominio, Connection con) throws SQLException, Exception{
		Condominio condominio = new Condominio();
		condominio.setId(gestorCondominio.getIdCondominio());
		Usuario sindicoGeral = this.usuarioDAO.buscarSindicoGeralPorCondominio(condominio, con);
		// Modifica o grupo do usuário para condômino somente se esse não é um Síndico Profissional. Caso seja ele somente deve deixar de ser gestor do condomínio
		if(!this.existeTipoUsuarioGrupoUsuario(sindicoGeral, GrupoUsuarioTipoUsuarioEnum.SINDICO_PROFISSIONAL.getTipoUsuario())){
			// Remove o grupo que é do tipo síndico profissional e add um grupo que seja condômino (caso não tenha)
			this.removeSindicoProfissionalEAdicionaCondomino(gestorCondominio.getIdCondominio(), sindicoGeral);
			this.usuarioDAO.atualizarUsuario(sindicoGeral,con);
		}
		StringBuffer query = new StringBuffer();
		query.append("UPDATE ");
		query.append(GESTOR_CONDOMINIO);
		query.append(" SET ");
		query.append(ID_USUARIO);
		query.append("= ?, ");
		query.append(TIPO_CONDOMINO);
		query.append("= ?, ");
		query.append(ID_BLOCO);
		query.append("= ? ");
		query.append("WHERE ");
		query.append(ID_CONDOMINIO);
		query.append("= ?");	
		PreparedStatement statement = null;
		try {			
			statement = con.prepareStatement(query.toString());
			SQLUtil.setValorPpreparedStatement(statement,1,gestorCondominio.getIdUsuario(), java.sql.Types.INTEGER);
			SQLUtil.setValorPpreparedStatement(statement,2,gestorCondominio.getTipoCondomino(), java.sql.Types.INTEGER);
			SQLUtil.setValorPpreparedStatement(statement,3,gestorCondominio.getIdBloco(), java.sql.Types.INTEGER);
			SQLUtil.setValorPpreparedStatement(statement,4,gestorCondominio.getIdCondominio(), java.sql.Types.INTEGER);
			statement.executeUpdate();
		} catch (SQLException e) {
			con.rollback();
			throw e;
		} catch (Exception e) {
			con.rollback();
			throw e;
		}	
	}	
	
	
	public void excluirGestorCondominioPorCondominio(Condominio condominio, Connection con) throws SQLException, Exception{
		StringBuffer query = new StringBuffer();	
		PreparedStatement statement = null;		
		// Código que atualiza os usuarios, que não serão mais gestores, setando o seu grupo para condônino.
		List<GestorCondominio> listaGestores = this.buscarListaGestoresCondominioPorCondominio(condominio);
		// UsuarioGrupoUsuario que representa o condômino que está persistido no banco, antes do usuário modificar 
		UsuarioGrupoUsuario usuarioGrupoUsuarioAtualCondomino = null;
		// UsuarioGrupoUsuario que representa o síndico que está persistido no banco, antes do usuário modificar
		UsuarioGrupoUsuario usuarioGrupoUsuarioAtualSindico = null;
		// Usuario que representa o síndico que está persistido no banco, antes do usuário modificar
		Usuario sindicoAtual = null;		
		// Usuario que representa o condômino que está persistido no banco, antes do usuário modificar
		Usuario condominoAtual = null;		
		for (GestorCondominio gestorCondominio : listaGestores) {
			sindicoAtual = this.usuarioDAO.buscarPorId(gestorCondominio.getIdUsuario());
			// Caso o usuário seja um síndico profissional ele não muda o grupo, apenas saí da gestão do condomínio.
			if(!this.existeTipoUsuarioGrupoUsuario(sindicoAtual, GrupoUsuarioTipoUsuarioEnum.SINDICO_PROFISSIONAL.getTipoUsuario())){
				this.removeSindicoProfissionalEAdicionaCondomino(gestorCondominio.getIdCondominio(), sindicoAtual);
			}			
			if (this.existeTipoUsuarioGrupoUsuario(sindicoAtual, GrupoUsuarioTipoUsuarioEnum.SINDICO.getTipoUsuario())){				
				for (GrupoUsuario grupoUsuario : sindicoAtual.getListaGrupoUsuario()) {
					if(grupoUsuario.getTipoUsuario() == GrupoUsuarioTipoUsuarioEnum.SINDICO.getTipoUsuario()){
						usuarioGrupoUsuarioAtualSindico = this.usuarioGrupoUsuarioDAO.buscarPorIdGrupoUsuarioEIdUsuario(grupoUsuario.getId(), sindicoAtual.getId(), con);
					}
				}
				
				condominoAtual = this.usuarioDAO.buscarPorId(condominio.getSindicoGeral().getId());
				for (GrupoUsuario grupoUsuario : condominoAtual.getListaGrupoUsuario()) {
					if(grupoUsuario.getTipoUsuario() == GrupoUsuarioTipoUsuarioEnum.CONDOMINO.getTipoUsuario()){
						usuarioGrupoUsuarioAtualCondomino = this.usuarioGrupoUsuarioDAO.buscarPorIdGrupoUsuarioEIdUsuario(grupoUsuario.getId(), condominoAtual.getId(), con);
					}
				}	
				
				// Caso não encontre nenhum novo usuário (condômino) associado a um grupo de usuário, ou seja, não houve a atualização do síndico, então exclui o 
				// síndico atual, pois esse será incluso num outro momento dessa ação.
				if(usuarioGrupoUsuarioAtualCondomino == null){
					// Condição que considera uma atulização do condôminio, sem modificar o síndico
					if(sindicoAtual.getId().intValue() == condominoAtual.getId().intValue()){
						this.usuarioGrupoUsuarioDAO.excluirPorId(usuarioGrupoUsuarioAtualSindico.getId(), con);
					}else{ // Caso onde atualiza o condomínio e o novo síndico não possuia grupo, então atualiza o grupo de usuários do síndico anterior, e o novo
						   // síndico será inserido na tabela USUARIO_GRUPO_USUARIO mais à frete
						this.popularUsuarioGrupoUsuarioCondomino(usuarioGrupoUsuarioAtualSindico, condominio.getId());
						this.usuarioGrupoUsuarioDAO.atualizar(usuarioGrupoUsuarioAtualSindico, con);						
					}
				}else{
					// Modifica os grupos de seus usuários, ou seja, o síndico virá condômino e o condômino será removido, pois será inserido um novo síndico mais à frente
					Integer idGrupoUsuarioCondominoAtual = usuarioGrupoUsuarioAtualCondomino.getIdGrupoUsuario();								
					usuarioGrupoUsuarioAtualSindico.setIdGrupoUsuario(idGrupoUsuarioCondominoAtual);
					this.usuarioGrupoUsuarioDAO.excluirPorId(usuarioGrupoUsuarioAtualCondomino.getId(), con);
					this.usuarioGrupoUsuarioDAO.atualizar(usuarioGrupoUsuarioAtualSindico, con);
				}

			}
			this.usuarioDAO.atualizarUsuario(sindicoAtual, con);		
		}
		try {			
			query.append("DELETE FROM ");
			query.append(GESTOR_CONDOMINIO);
			query.append(" WHERE ");		
			query.append(ID_CONDOMINIO);
			query.append(" = ?");
			statement = con.prepareStatement(query.toString());
			SQLUtil.setValorPpreparedStatement(statement, 1, condominio.getId(), java.sql.Types.INTEGER);
			statement.executeUpdate();			
		} catch (SQLException e) {
			con.rollback();
			throw e;
		} catch (Exception e) {
			con.rollback();
			throw e;
		}
		
	}
	
	public void excluirGestorCondominioPorBloco(Bloco bloco, Connection con) throws SQLException, Exception{
		StringBuffer query = new StringBuffer();	
		PreparedStatement statement = null;
		try {			
			query.append("DELETE FROM ");
			query.append(GESTOR_CONDOMINIO);
			query.append(" WHERE ");		
			query.append(ID_BLOCO);
			query.append(" = ?");
			statement = con.prepareStatement(query.toString());
			statement.setInt(1, bloco.getId());
			statement.executeUpdate();			
		} catch (SQLException e) {
			con.rollback();
			throw e;
		} catch (Exception e) {
			con.rollback();
			throw e;
		}
		
	}
	
	public List<GestorCondominio> buscarListaGestoresCondominioPorBloco(Bloco bloco, Connection con) throws SQLException, Exception{		
		StringBuffer query = new StringBuffer();
		query.append("SELECT * FROM ");
		query.append(GESTOR_CONDOMINIO);
		query.append(" WHERE ");
		query.append(ID_BLOCO);
		query.append(" = ? ");		
		query.append(";");		
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		List<GestorCondominio> listaGestorCondominio = new ArrayList<GestorCondominio>();
		GestorCondominio gestorCondominio = null;
		try {
			preparedStatement = con.prepareStatement(query.toString());
			preparedStatement.setInt(1, bloco.getId());
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()){
				gestorCondominio = new GestorCondominio();
				gestorCondominio.setId(resultSet.getInt(ID));
				gestorCondominio.setIdCondominio(resultSet.getInt(ID_CONDOMINIO));
				gestorCondominio.setIdUsuario(resultSet.getInt(ID_USUARIO));
				gestorCondominio.setIdBloco(resultSet.getInt(ID_BLOCO));
				gestorCondominio.setTipoCondomino(resultSet.getInt(TIPO_CONDOMINO));
				listaGestorCondominio.add(gestorCondominio);
			}
		} catch (SQLException e) {
			con.rollback();
			throw e;
		} catch (Exception e) {
			con.rollback();
			throw e;
		}
		return listaGestorCondominio;
	}
	
	public List<GestorCondominio> buscarListaGestoresCondominioPorBloco(Bloco bloco) throws SQLException, Exception{		
		StringBuffer query = new StringBuffer();
		query.append("SELECT * FROM ");
		query.append(GESTOR_CONDOMINIO);
		query.append(" WHERE ");
		query.append(ID_BLOCO);
		query.append(" = ? ");		
		query.append(";");
		Connection con = C3P0DataSource.getInstance().getConnection();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		List<GestorCondominio> listaGestorCondominio = new ArrayList<GestorCondominio>();
		GestorCondominio gestorCondominio = null;
		try {
			preparedStatement = con.prepareStatement(query.toString());
			preparedStatement.setInt(1, bloco.getId());
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()){
				gestorCondominio = new GestorCondominio();
				gestorCondominio.setId(resultSet.getInt(ID));
				gestorCondominio.setIdCondominio(resultSet.getInt(ID_CONDOMINIO));
				gestorCondominio.setIdUsuario(resultSet.getInt(ID_USUARIO));
				gestorCondominio.setIdBloco(resultSet.getInt(ID_BLOCO));
				gestorCondominio.setTipoCondomino(resultSet.getInt(TIPO_CONDOMINO));
				listaGestorCondominio.add(gestorCondominio);
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
		return listaGestorCondominio;
	}
	
	public void salvarGestorCondominioSindicoProfissional(GestorCondominio gestorCondominio, Connection con) throws SQLException, Exception {
		StringBuffer query = new StringBuffer();
		query.append("INSERT INTO "); 
		query.append(GESTOR_CONDOMINIO);
		query.append("("); 
		query.append(ID_USUARIO); 
		query.append(",");
		query.append(TIPO_CONDOMINO); 
		query.append(",");
		query.append(ID_CONDOMINIO);
		query.append(") ");
		query.append("VALUES(?,?,?)");
		PreparedStatement statement = null;
		try {			
			statement = con.prepareStatement(query.toString());
			SQLUtil.setValorPpreparedStatement(statement, 1, gestorCondominio.getIdUsuario(), java.sql.Types.INTEGER);
			SQLUtil.setValorPpreparedStatement(statement, 2, gestorCondominio.getTipoCondomino(), java.sql.Types.INTEGER);
			SQLUtil.setValorPpreparedStatement(statement, 3, gestorCondominio.getIdCondominio(), java.sql.Types.INTEGER);
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
	public void excluirPorIdUsuario(Integer idUsuario, Connection con) throws SQLException, Exception {
		StringBuffer query = new StringBuffer();
		PreparedStatement statement = null;
		try {			
			query.append("DELETE FROM ");
			query.append(GESTOR_CONDOMINIO);
			query.append(" WHERE ");		
			query.append(ID_USUARIO);
			query.append(" = ?");			
			statement = con.prepareStatement(query.toString());
			SQLUtil.setValorPpreparedStatement(statement, 1, idUsuario, java.sql.Types.INTEGER);
			statement.executeUpdate();			
		} catch (SQLException e) {
			con.rollback();
			throw e;			
		} catch (Exception e) {
			con.rollback();
			throw e;	
		}
	}	
	

	private void popularUsuarioGrupoUsuarioCondomino(UsuarioGrupoUsuario usuarioGrupoUsuario, Integer idCondominio) throws SQLException, Exception{
		List<GrupoUsuario> listaGrupoUsuario = new ArrayList<GrupoUsuario>();
		listaGrupoUsuario.addAll(this.grupoUsuarioService.buscarPorIdCondominioEPadraoETipoUsuarioESituacao(idCondominio,
				GrupoUsuarioPadraoEnum.SIM.getPadrao(), GrupoUsuarioTipoUsuarioEnum.CONDOMINO.getTipoUsuario(),GrupoUsuarioSituacaoEnum.ATIVO.getSituacao()));
		if(listaGrupoUsuario.isEmpty()){
			listaGrupoUsuario.add(this.grupoUsuarioService.buscarPorPadraoETipoUsuarioESituacao(GrupoUsuarioPadraoEnum.SIM.getPadrao(), 
					GrupoUsuarioTipoUsuarioEnum.CONDOMINO.getTipoUsuario(),
					GrupoUsuarioSituacaoEnum.ATIVO.getSituacao()));			
		}
		// Pega o primeiro grupo, pois esse método está sendo chamado por um usuário que não tem grupo associado, logo para não ficar sem pega o primeiro.
		usuarioGrupoUsuario.setIdGrupoUsuario(listaGrupoUsuario.get(0).getId());			
	}
}
