package br.com.condominiosvirtuais.service.impl;

import java.io.Serializable;
import java.sql.SQLException;
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
// TODO: Código comentado em 04/08/2016. Apagar em 90 dias		
//		Object[] parametros = new Object[7];
//		parametros[0] = nomeSindico;
//		parametros[1] = agendamento.getBloco().getNome();
//		parametros[2] = String.valueOf(agendamento.getUnidade().getNumero());
//		parametros[3] = AplicacaoUtil.formatarData(AplicacaoUtil.i18n("formatoData"), agendamento.getData());
//		parametros[4] = agendamento.getHoraInicial();
//		parametros[5] = agendamento.getHoraFinal();
//		parametros[6] = agendamento.getObservacao();
		if (agendamento.getObservacao().isEmpty()){
		//	email.setMensagem(AplicacaoUtil.i18n("msg.agendamentoMudança.solicitacao.mensagem", parametros));
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
// 		TODO: Código comentado em 04/08/2016. Apagar em 90 dias
//		Object[] parametros = new Object[4];
//		parametros[0] = agendamento.getCondomino().getNome();		
//		parametros[1] = AplicacaoUtil.formatarData(AplicacaoUtil.i18n("formatoData"), agendamento.getData());
//		parametros[2] = agendamento.getHoraInicial();
//		parametros[3] = agendamento.getHoraFinal();
		email.setMensagem(MensagensEmailUtil.solicitacaoDeMudancaAprovada(agendamento));		
		this.emailService.salvar(email);
		
	}

	@Override
	public void reprovar(Agendamento agendamento) throws SQLException, Exception {
		this.agendamentoDAO.reprovar(agendamento);
		Email email = new Email();			
		email.setPara(agendamento.getCondomino().getEmail().getEmail());
		email.setAssunto(AplicacaoUtil.i18n("msg.solicitacaoMudanca.reprovada.assunto"));
// 		TODO: Código comentado em 04/08/2016. Apagar em 90 dias		
//		Object[] parametros = new Object[5];
//		parametros[0] = agendamento.getCondomino().getNome();		
//		parametros[1] = AplicacaoUtil.formatarData(AplicacaoUtil.i18n("formatoData"), agendamento.getData());
//		parametros[2] = agendamento.getHoraInicial();
//		parametros[3] = agendamento.getHoraFinal();
//		parametros[4] = agendamento.getMotivoReprovacao();
//		email.setMensagem(AplicacaoUtil.i18n("msg.agendamentoMudança.reprovar.mensagem", parametros));
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
	

}
