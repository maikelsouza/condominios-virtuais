package br.com.condominiosvirtuais.persistence.impl;

import java.io.Serializable;

import org.apache.log4j.Logger;

import br.com.condominiosvirtuais.persistence.BotaoDAO;

public class BotaoDAOImpl implements Serializable, BotaoDAO {

	private static final long serialVersionUID = 1L;
	
	private static Logger logger = Logger.getLogger(BotaoDAOImpl.class);
	
	private static final String BOTAO = "BOTAO";
	
	private static final String ID = "ID";
	
	private static final String NOME = "NOME";
	
	private static final String DESCRICAO = "DESCRICAO";
	
	private static final String ID_BOTAO = "ID_BOTAO";
	
	private static final String ID_TELA = "ID_TELA";
	

	

}
