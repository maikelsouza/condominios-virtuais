package br.com.condominiosvirtuais.persistence.impl;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;

import br.com.condominiosvirtuais.entity.TelaAbaTela;
import br.com.condominiosvirtuais.persistence.TelaAbaTelaDAO;

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
	public void excluir(Integer id, Connection con) throws SQLException, Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<TelaAbaTela> buscarPorIdTela(Integer idTela, Connection con) throws SQLException, Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
