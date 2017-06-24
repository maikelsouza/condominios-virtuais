package br.com.condominiosvirtuais.service.impl;

import java.io.Serializable;

import javax.inject.Inject;

import br.com.condominiosvirtuais.persistence.ComponenteDAO;
import br.com.condominiosvirtuais.service.ComponenteService;

public class ComponenteServiceImpl implements ComponenteService, Serializable{

	private static final long serialVersionUID = 1L;
	
	@Inject
	private ComponenteDAO componenteDAO;

}
