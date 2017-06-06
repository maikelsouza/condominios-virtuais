package br.com.condominiosvirtuais.service.impl;

import java.io.Serializable;

import javax.inject.Inject;

import br.com.condominiosvirtuais.persistence.AbaDAO;
import br.com.condominiosvirtuais.service.AbaService;

public class AbaServiceImpl implements AbaService, Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private AbaDAO abaDAO;

}
