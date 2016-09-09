package br.com.condominiosvirtuais.service.impl;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import br.com.condominiosvirtuais.entity.Condominio;
import br.com.condominiosvirtuais.entity.Condomino;
import br.com.condominiosvirtuais.entity.Email;
import br.com.condominiosvirtuais.entity.Reserva;
import br.com.condominiosvirtuais.exception.BusinessException;
import br.com.condominiosvirtuais.persistence.ReservaDAO;
import br.com.condominiosvirtuais.service.EmailService;
import br.com.condominiosvirtuais.service.ReservaService;
import br.com.condominiosvirtuais.util.AplicacaoUtil;
import br.com.condominiosvirtuais.util.MensagensEmailUtil;

public class ReservaServiceImpl implements ReservaService, Serializable {
	
	private static final long serialVersionUID = 1L;

	@Inject	
	private ReservaDAO reservaDAO = null;
	
	@Inject 
	private EmailService emailService = null;
	
	public void salvar(Reserva reserva) throws SQLException, BusinessException, Exception {		
		this.reservaDAO.salvar(reserva);		
	}
	
	public void excluir(Reserva reserva) throws SQLException, Exception {
		this.reservaDAO.excluir(reserva);		
	}
	
	public List<Reserva> buscarPorCondomino(Condomino condomino) throws NumberFormatException, SQLException, Exception {	
		return this.reservaDAO.buscarPorCondomino(condomino);
	}
	
	public void atualizar(Reserva reserva) throws SQLException, BusinessException, Exception {
		this.reservaDAO.atualizar(reserva);	
	}

	public List<Reserva> buscarPorCondominioETipo(Condominio condominio, String tipo) throws SQLException, Exception {		
		return this.reservaDAO.buscarPorCondominioETipo(condominio, tipo);
	}

	public List<Reserva> buscarPorIdAmbienteEMaiorIgualDataEPendeteOUAprovado(Integer idAmbiente, Date data) throws NumberFormatException, SQLException, Exception {
		return this.reservaDAO.buscarPorIdAmbienteEMaiorIgualDataEPendeteOUAprovado(idAmbiente, data);
	}

	public void aprovar(Reserva reserva) throws SQLException, Exception {
		this.reservaDAO.aprovar(reserva);
		Email email = new Email();			
		email.setPara(reserva.getCondomino().getEmail().getEmail());
		email.setAssunto(AplicacaoUtil.i18n("msg.solicitacaoReserva.aprovada.assunto"));
// TODO: Código comentado em 05/08/2016. Apagar em 90 dias		
//		Object[] parametros = new Object[3];
//		parametros[0] = reserva.getCondomino().getNome();
//		parametros[1] = reserva.getAmbiente().getNome().toUpperCase();
//		parametros[2] = AplicacaoUtil.formatarData(AplicacaoUtil.i18n("formatoData"), reserva.getData());
		email.setMensagem(MensagensEmailUtil.solicitacaoDeReservaAprovada(reserva));
		this.emailService.salvar(email);
	}

	public void reprovar(Reserva reserva) throws SQLException, Exception {
		this.reservaDAO.reprovar(reserva);
		Email email = new Email();			
		email.setPara(reserva.getCondomino().getEmail().getEmail());
		email.setAssunto(AplicacaoUtil.i18n("msg.solicitacaoReserva.reprovada.assunto"));
		// TODO: Código comentado em 05/08/2016. Apagar em 90 dias		
//		Object[] parametros = new Object[4];
//		parametros[0] = reserva.getCondomino().getNome();
//		parametros[1] = reserva.getAmbiente().getNome().toUpperCase();
//		parametros[2] = AplicacaoUtil.formatarData(AplicacaoUtil.i18n("formatoData"), reserva.getData());
//		parametros[3] = reserva.getMotivoReprovacao();
		email.setMensagem(MensagensEmailUtil.solicitacaoDeReservaReprovada(reserva));
		this.emailService.salvar(email);		
	}

	public void solicitar(Reserva reserva, String emailSindico, String nomeSindico) throws SQLException, Exception {		
		this.salvar(reserva);
		Email email = new Email();		
		email.setPara(emailSindico);
		email.setAssunto(AplicacaoUtil.i18n("msg.solicitacaoReserva.assunto"));
// TODO: Código comentado em 05/08/2016. Apagar em 90 dias		
//		Object[] parametros = new Object[3];
//		parametros[0] = nomeSindico;
//		parametros[1] = reserva.getAmbiente().getNome().toUpperCase();		   
//		parametros[2] = AplicacaoUtil.formatarData(AplicacaoUtil.i18n("formatoData"), reserva.getData());
		email.setMensagem(MensagensEmailUtil.solicitacaoDeReserva(reserva, nomeSindico));
		this.emailService.salvar(email);
	}

	@Override
	public List<Reserva> buscarPorCondominio(Condominio condominio)
			throws SQLException, Exception {
		return this.reservaDAO.buscarPorCondominio(condominio);
	}

	@Override
	public void suspender(Reserva reserva) throws SQLException, Exception {
		this.reservaDAO.suspender(reserva);	
		Email email = new Email();			
		email.setPara(reserva.getCondomino().getEmail().getEmail());
		email.setAssunto(AplicacaoUtil.i18n("msg.solicitacaoReserva.suspensa.assunto"));
		email.setMensagem(MensagensEmailUtil.solicitacaoDeReservaSuspensa(reserva));
		this.emailService.salvar(email);		
	}

	@Override
	public List<Reserva> buscarPorCondominioESituacoesEAteData(Condominio condominio, List<String> listaSituacao, Date data) throws SQLException, Exception {		
		return this.reservaDAO.buscarPorCondominioESituacoesEAteData(condominio, listaSituacao, data);
	}

}
