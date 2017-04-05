package br.com.condominiosvirtuais.service.impl;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

import javax.inject.Inject;

import br.com.condominiosvirtuais.entity.Contador;
import br.com.condominiosvirtuais.entity.Email;
import br.com.condominiosvirtuais.exception.BusinessException;
import br.com.condominiosvirtuais.persistence.ContadorDAO;
import br.com.condominiosvirtuais.service.CondominioService;
import br.com.condominiosvirtuais.service.ContadorService;
import br.com.condominiosvirtuais.service.EmailService;
import br.com.condominiosvirtuais.util.AplicacaoUtil;
import br.com.condominiosvirtuais.util.MensagensEmailUtil;

public class ContadorServiceImpl implements Serializable, ContadorService {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private CondominioService condominioService;
	
	@Inject
	private ContadorDAO contadorDAO;
	
	@Inject
	private EmailService emailService;

	@Override
	public void salvar(Contador contador) throws SQLException, BusinessException, Exception {
		contador.setListaCondominio(this.condominioService.buscarPorIdEscritorioContabilidade(contador.getIdEscritorioContabilidade()));
		this.contadorDAO.salvar(contador);	
		Email email = new Email();			
		email.setPara(contador.getEmail().getEmail());
		email.setAssunto(AplicacaoUtil.i18n("msg.cadastroContador.assunto"));	
		email.setMensagem(MensagensEmailUtil.confirmacaoDeCadastroContador(contador));
		this.emailService.salvar(email);
	}

	@Override
	public List<Contador> buscarPorIdEscritorioContabilidadeESituacao(Integer idEscritorioContabilidade, Integer situacao) throws SQLException, Exception {
		return this.contadorDAO.buscarPorIdEscritorioContabilidadeESituacao(idEscritorioContabilidade, situacao);
	}

	@Override
	public void atualizar(Contador contador) throws SQLException, Exception {
		this.contadorDAO.atualizar(contador);		
	}

	@Override
	public Contador buscarPorId(Integer id) throws SQLException, Exception {
		return this.contadorDAO.buscarPorId(id);
	}
	
	

}