package br.com.condominiosvirtuais.persistence.impl;

import java.io.Serializable;

import org.apache.log4j.Logger;

import br.com.condominiosvirtuais.persistence.ModuloDAO;

public class ModuloDAOImpl implements Serializable, ModuloDAO {

	private static final long serialVersionUID = 1L;
	
	private static Logger logger = Logger.getLogger(ModuloDAOImpl.class);
	
	private static final String MODULO = "MODULO";
	
	private static final String ID = "ID";
	
	private static final String NOME = "NOME";
	
	private static final String DESCRICAO = "DESCRICAO";
	
	
	

}
