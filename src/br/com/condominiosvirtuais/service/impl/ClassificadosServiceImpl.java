package br.com.condominiosvirtuais.service.impl;

import java.io.Serializable;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import br.com.condominiosvirtuais.entity.Classificados;
import br.com.condominiosvirtuais.entity.Condominio;
import br.com.condominiosvirtuais.entity.Email;
import br.com.condominiosvirtuais.persistence.ClassificadosDAO;
import br.com.condominiosvirtuais.service.ClassificadosService;
import br.com.condominiosvirtuais.service.EmailService;
import br.com.condominiosvirtuais.util.AplicacaoUtil;
import br.com.condominiosvirtuais.util.MensagensEmailUtil;

public class ClassificadosServiceImpl implements ClassificadosService, Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Inject
	private ClassificadosDAO classificadosDAO = null;
	
	@Inject 
	private EmailService emailService = null;
	
	
	@Override
	public List<Classificados> buscarPorCondominio(
			Condominio condominio) throws SQLException, Exception {		
		return this.classificadosDAO.buscarPorCondominio(condominio);
	}

	@Override
	public List<Classificados> buscarPorCondominioETitulo(
			Condominio condominio, String titulo) throws SQLException, Exception {		
		return this.classificadosDAO.buscarPorCondominioETitulo(condominio,titulo);
	}

	@Override
	public void salvar(Classificados classificados) throws SQLException, Exception {
		this.classificadosDAO.salvar(classificados);
	}

	@Override
	public void excluir(Classificados classificados) throws SQLException, Exception {
		this.classificadosDAO.excluir(classificados);		
	}

	@Override
	public void atualizar(Classificados classificados) throws SQLException, Exception {
		this.classificadosDAO.atualizar(classificados);		
	}

	@Override
	public List<Classificados> buscarPorMaiorIgualDataExpira(Date dataExpira) throws SQLException, Exception {
		return this.classificadosDAO.buscarPorMaiorIgualDataExpira(dataExpira);
	}

	@Override
	public List<Classificados> buscarPorDataExpira(Date dataExpira) throws SQLException, Exception {
		return this.classificadosDAO.buscarPorDataExpira(dataExpira);
	}
	
	@Override
	public void enviarEmailDataExpira(Email email, Classificados classificados) throws  Exception {
		//Object[] parametros2 = new Object[1];
		//parametros2[0] = email.getPara().toUpperCase();
		email.setAssunto(AplicacaoUtil.i18n("msg.expiraClassificados.assunto"));
//		Object[] parametros = new Object[1];
//		parametros[0] = email.getPara().toUpperCase();
//		parametros[1] = classificados.getTitulo().toUpperCase();
//		SimpleDateFormat formato = new SimpleDateFormat(AplicacaoUtil.i18n("formatoData"));  
//		parametros[2] = formato.format(classificados.getDataExpira());					
		email.setMensagem(MensagensEmailUtil.expiraClassificados(classificados, email.getPara()));
		this.emailService.salvar(email);
	}



}
