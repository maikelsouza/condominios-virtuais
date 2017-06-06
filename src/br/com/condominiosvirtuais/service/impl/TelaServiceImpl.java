package br.com.condominiosvirtuais.service.impl;

import java.io.Serializable;

import javax.inject.Inject;

import br.com.condominiosvirtuais.persistence.TelaDAO;
import br.com.condominiosvirtuais.service.TelaService;

public class TelaServiceImpl implements TelaService, Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Inject
	private TelaDAO telaDAO;
	
	

}
