package br.com.condominiosvirtuais.persistence.impl;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.inject.Inject;

import org.apache.log4j.Logger;

import br.com.condominiosvirtuais.entity.Condominio;
import br.com.condominiosvirtuais.entity.GestorCondominio;
import br.com.condominiosvirtuais.entity.SindicoProfissional;
import br.com.condominiosvirtuais.enumeration.TipoGestorCondominioEnum;
import br.com.condominiosvirtuais.exception.BusinessException;
import br.com.condominiosvirtuais.persistence.SindicoProfissionalDAO;
import br.com.condominiosvirtuais.util.SQLUtil;

public class SindicoProfissionalDAOImpl implements SindicoProfissionalDAO, Serializable {

	private static final long serialVersionUID = 1L;
	
	private static Logger logger = Logger.getLogger(SindicoProfissionalDAOImpl.class);
	
	private static final String SINDICO_PROFISSIONAL = "SINDICO_PROFISSIONAL";
	
	private static final String ID = "ID";
	
	private static final String SITE = "SITE";
	
	private static final String TELEFONE_COMERCIAL = "TELEFONE_COMERCIAL";
	
	private static final String TELEFONE_CELULAR1 = "TELEFONE_CELULAR1";
	
	private static final String TELEFONE_CELULAR2 = "TELEFONE_CELULAR2";
	
	private static final String TELEFONE_CELULAR3 = "TELEFONE_CELULAR3";
	
	
	@Inject
	private UsuarioDAOImpl usuarioDAO;
	
	@Inject
	private GestorCondominioDAOImpl gestorCondominioDAO;
	
	

	@Override
	public void salvar(SindicoProfissional sindicoProfissional) throws SQLException, Exception {
		PreparedStatement statement = null;
		Connection con = null;
		GestorCondominio gestorCondominio = null;
		try {
			con = Conexao.getConexao();
			con.setAutoCommit(false);
			this.usuarioDAO.salvarUsuario(sindicoProfissional,con);
			StringBuffer query = new StringBuffer();
			query.append("INSERT INTO "); 
			query.append(SINDICO_PROFISSIONAL); 
			query.append("(");
			query.append(ID); 
			query.append(",");
			query.append(SITE); 
			query.append(",");
			query.append(TELEFONE_COMERCIAL); 
			query.append(",");
			query.append(TELEFONE_CELULAR1); 
			query.append(",");
			query.append(TELEFONE_CELULAR2);
			query.append(",");	
			query.append(TELEFONE_CELULAR3);
			query.append(") ");
			query.append("VALUES(?,?,?,?,?,?)");
			statement = con.prepareStatement(query.toString());
			SQLUtil.setValorPpreparedStatement(statement, 1, sindicoProfissional.getId(), java.sql.Types.INTEGER);
			SQLUtil.setValorPpreparedStatement(statement, 2, sindicoProfissional.getSite(), java.sql.Types.VARCHAR);
			SQLUtil.setValorPpreparedStatement(statement, 3, sindicoProfissional.getTelefoneComercial(), java.sql.Types.BIGINT);
			SQLUtil.setValorPpreparedStatement(statement, 4, sindicoProfissional.getTelefoneCelular1(), java.sql.Types.BIGINT);
			SQLUtil.setValorPpreparedStatement(statement, 5, sindicoProfissional.getTelefoneCelular2(), java.sql.Types.BIGINT);
			SQLUtil.setValorPpreparedStatement(statement, 6, sindicoProfissional.getTelefoneCelular3(), java.sql.Types.BIGINT);
			statement.execute();
			for (Condominio condominio : sindicoProfissional.getListaCondominio()) {
				gestorCondominio = new GestorCondominio();
				gestorCondominio.setIdCondominio(condominio.getId());
				gestorCondominio.setIdUsuario(sindicoProfissional.getId());
				gestorCondominio.setTipoCondomino(TipoGestorCondominioEnum.SINDICO_GERAL.getGestorCondominio());
				this.gestorCondominioDAO.salvarGestorCondominioSindicoProfissional(gestorCondominio, con);
			}
			con.commit();	
		} catch (SQLException e) {
			throw e;
		} catch (BusinessException e) {
			throw e;
		} catch (Exception e) {		
			throw e;			
		}finally{
			try {				
				con.close();	
				statement.close();
			} catch (SQLException e) {
				logger.error("erro sqlstate "+e.getSQLState(), e);		
			}
		}	
		
	}

}
