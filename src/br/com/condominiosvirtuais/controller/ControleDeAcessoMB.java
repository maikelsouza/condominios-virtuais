package br.com.condominiosvirtuais.controller;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.log4j.Logger;

import br.com.condominiosvirtuais.service.TelaService;

@Named @SessionScoped
public class ControleDeAcessoMB implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private static Logger logger = Logger.getLogger(ControleDeAcessoMB.class);
	
	@Inject
	private TelaService telaService;

}
