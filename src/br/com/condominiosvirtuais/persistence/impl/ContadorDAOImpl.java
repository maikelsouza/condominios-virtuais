package br.com.condominiosvirtuais.persistence.impl;

import java.io.Serializable;

import org.apache.log4j.Logger;

import br.com.condominiosvirtuais.persistence.ContadorDAO;

public class ContadorDAOImpl implements ContadorDAO, Serializable {

	private static final long serialVersionUID = 1L;
	
	private static Logger logger = Logger.getLogger(ContadorDAOImpl.class);

}
