package br.com.condominiosvirtuais.service.impl;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import br.com.condominiosvirtuais.entity.Agendamento;
import br.com.condominiosvirtuais.entity.Condominio;
import br.com.condominiosvirtuais.entity.Condomino;
import br.com.condominiosvirtuais.entity.Email;
import br.com.condominiosvirtuais.persistence.AgendamentoDAO;
import br.com.condominiosvirtuais.service.AgendamentoService;
import br.com.condominiosvirtuais.service.EmailService;
import br.com.condominiosvirtuais.util.AplicacaoUtil;
import br.com.condominiosvirtuais.util.MensagensEmailUtil;

public class AgendamentoServiceImpl implements Serializable, AgendamentoService {

	
	private static final long serialVersionUID = 1L;
	
	@Inject
	private AgendamentoDAO agendamentoDAO;
	
	@Inject 
	private EmailService emailService; 

	@Override
	public void solicitarAgendamento(Agendamento agendamento, String nomeSindico, String emailSindico) throws SQLException, Exception {		
		this.agendamentoDAO.salvar(agendamento);
		Email email = new Email();			
		email.setPara(emailSindico);
		email.setAssunto(AplicacaoUtil.i18n("msg.solicitacaoMudança.assunto"));
		if (agendamento.getObservacao().isEmpty()){
			email.setMensagem(MensagensEmailUtil.solicitacaoDeMudanca(agendamento, nomeSindico));
		}else{
			email.setMensagem(MensagensEmailUtil.solicitacaoDeMudancaComObservacao(agendamento, nomeSindico));
		}
		this.emailService.salvar(email);
		
	}

	@Override
	public List<Agendamento> buscarPorCondominioESituacao(Condominio condominio, String situacao) throws SQLException, Exception {		
		return this.agendamentoDAO.buscarPorCondominioESituacao(condominio, situacao);
	}

	@Override
	public void aprovar(Agendamento agendamento) throws SQLException, Exception {
		this.agendamentoDAO.aprovar(agendamento);
		Email email = new Email();			
		email.setPara(agendamento.getCondomino().getEmail().getEmail());
		email.setAssunto(AplicacaoUtil.i18n("msg.solicitacaoMudanca.aprovada.assunto"));
		email.setMensagem(MensagensEmailUtil.solicitacaoDeMudancaAprovada(agendamento));		
		this.emailService.salvar(email);
		
	}

	@Override
	public void reprovar(Agendamento agendamento) throws SQLException, Exception {
		this.agendamentoDAO.reprovar(agendamento);
		Email email = new Email();			
		email.setPara(agendamento.getCondomino().getEmail().getEmail());
		email.setAssunto(AplicacaoUtil.i18n("msg.solicitacaoMudanca.reprovada.assunto"));
		email.setMensagem(MensagensEmailUtil.solicitacaoDeMudancaReprovada(agendamento));
		this.emailService.salvar(email);
		
	}

	@Override
	public List<Agendamento> buscarPorCondominio(Condominio condominio) throws SQLException, Exception {		
		return this.agendamentoDAO.buscarPorCondominio(condominio);
	}

	@Override
	public List<Agendamento> buscarPorCondomino(Condomino condomino) throws SQLException, Exception {		
		return this.agendamentoDAO.buscarPorCondomino(condomino);
	}

	@Override
	public List<Agendamento> buscarPorCondominioESituacaoETipo(Condominio condominio, String situacao, String tipo) throws SQLException, Exception {
		return this.agendamentoDAO.buscarPorCondominioESituacaoETipo(condominio, situacao, tipo);
	}

	@Override
	public List<Agendamento> buscarPorCondominioETipo(Condominio condominio, String tipo) throws SQLException, Exception {
		return this.agendamentoDAO.buscarPorCondominioETipo(condominio, tipo);
	}

	@Override
	public List<Agendamento> buscarPorCondominioEData(Condominio condominio, Date data) throws SQLException, Exception {		
		return this.agendamentoDAO.buscarPorCondominioEData(condominio, data);
	}

}
