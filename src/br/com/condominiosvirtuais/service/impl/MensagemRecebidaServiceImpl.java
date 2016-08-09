package br.com.condominiosvirtuais.service.impl;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import br.com.condominiosvirtuais.entity.Bloco;
import br.com.condominiosvirtuais.entity.Condomino;
import br.com.condominiosvirtuais.entity.Email;
import br.com.condominiosvirtuais.entity.MensagemRecebida;
import br.com.condominiosvirtuais.entity.Unidade;
import br.com.condominiosvirtuais.entity.Usuario;
import br.com.condominiosvirtuais.persistence.MensagemRecebidaDAO;
import br.com.condominiosvirtuais.service.BlocoService;
import br.com.condominiosvirtuais.service.CondominoService;
import br.com.condominiosvirtuais.service.EmailService;
import br.com.condominiosvirtuais.service.MensagemRecebidaService;
import br.com.condominiosvirtuais.service.UnidadeService;
import br.com.condominiosvirtuais.util.AplicacaoUtil;
import br.com.condominiosvirtuais.util.MensagensEmailUtil;
import br.com.condominiosvirtuais.vo.MensagemRecebidaVO;

public class MensagemRecebidaServiceImpl implements MensagemRecebidaService, Serializable {
	
	private static final long serialVersionUID = 1L;

	@Inject
	private MensagemRecebidaDAO mensagemRecebidaDAO = null;
	
	@Inject 
	private EmailService emailService = null;
	
	@Inject
	private CondominoService condominoService = null;
	
	@Inject
	private BlocoService blocoService = null;
	
	@Inject
	private UnidadeService unidadeService = null;
	
	public void salvar(MensagemRecebida mensagemRecebida) throws SQLException, Exception {
		this.mensagemRecebidaDAO.salvar(mensagemRecebida);		
	}

	public void excluir(MensagemRecebida mensagemRecebida) throws SQLException, Exception {
		this.mensagemRecebidaDAO.excluir(mensagemRecebida);		
	}

	public List<MensagemRecebidaVO> buscarPorUsuarioDestinatario(Usuario usuarioDestinatario) throws SQLException, Exception {
		List<MensagemRecebidaVO> listaMensagemRecebidaVO = new ArrayList<MensagemRecebidaVO>();		
		List<MensagemRecebida>  listaMensagemRecebida = this.mensagemRecebidaDAO.buscarPorUsuarioDestinatario(usuarioDestinatario);
		MensagemRecebidaVO mensagemRecebidaVO = null;
		Condomino condomino = null;
		Unidade unidade = null;
		Bloco bloco = null;
		for (MensagemRecebida mensagemRecebida : listaMensagemRecebida) {
			mensagemRecebidaVO = new MensagemRecebidaVO();
			mensagemRecebidaVO.setId(mensagemRecebida.getId());
			mensagemRecebidaVO.setData(mensagemRecebida.getData());
			mensagemRecebidaVO.setAssunto(mensagemRecebida.getAssunto());
			mensagemRecebidaVO.setTexto(mensagemRecebida.getTexto());
			mensagemRecebidaVO.setVisualizada(mensagemRecebida.getVisualizada());
			mensagemRecebidaVO.setUsuarioDestinatario(mensagemRecebida.getUsuarioDestinatario());
			mensagemRecebidaVO.setUsuarioRemetente(mensagemRecebida.getUsuarioRemetente());
			condomino = this.condominoService.buscarPorId(mensagemRecebida.getUsuarioRemetente().getId());
			if(condomino != null){
				unidade = this.unidadeService.buscarPorId(condomino.getIdUnidade());
				bloco = this.blocoService.buscarPorId(unidade.getIdBloco());
				mensagemRecebidaVO.setUnidadeRemetente(unidade);
				mensagemRecebidaVO.setBlocoRemetente(bloco);
			}
			listaMensagemRecebidaVO.add(mensagemRecebidaVO);
		}
		return listaMensagemRecebidaVO;
	}

	public void atualizar(MensagemRecebida mensagemRecebida) throws SQLException, Exception {
		this.mensagemRecebidaDAO.atualizar(mensagemRecebida);		
	}

	public void enviarListaMensagemRecebida(List<MensagemRecebida> listaMensagemRecebida) throws SQLException, Exception {
		this.salvarListaMensagemRecebida(listaMensagemRecebida);
		for (MensagemRecebida mensagemRecebida : listaMensagemRecebida) {
			Email email = new Email();			
			email.setPara(mensagemRecebida.getUsuarioDestinatario().getEmail().getEmail());
			email.setAssunto(AplicacaoUtil.i18n("msg.mensagemRecebida.assunto"));
			// TODO: Código comentado em 05/08/2016. Apagar em 90 dias
			//Object[] parametros = new Object[1];
			//parametros[0] = mensagemRecebida.getUsuarioDestinatario().getNome().toUpperCase();
			//email.setMensagem(AplicacaoUtil.i18n("msg.mensagemRecebida.texto",parametros));
			email.setMensagem(MensagensEmailUtil.mensagemRecebida(mensagemRecebida));
			this.emailService.salvar(email);
		}
	}

	@Override
	public void visualizar(MensagemRecebida mensagemRecebida) throws SQLException, Exception {
		this.mensagemRecebidaDAO.visualizar(mensagemRecebida);		
	}

	@Override
	public void salvarListaMensagemRecebida(List<MensagemRecebida> listaMensagemRecebida) throws SQLException, Exception {
		this.mensagemRecebidaDAO.salvarListaMensagemRecebida(listaMensagemRecebida);
	}

	@Override
	public void enviar(MensagemRecebida mensagemRecebida) throws SQLException,Exception {
		this.salvar(mensagemRecebida);
		Email email = new Email();			
		email.setPara(mensagemRecebida.getUsuarioDestinatario().getEmail().getEmail());
		email.setAssunto(AplicacaoUtil.i18n("msg.mensagemRecebida.assunto"));
		Object[] parametros = new Object[1];
		parametros[0] = mensagemRecebida.getUsuarioDestinatario().getNome().toUpperCase();
		email.setMensagem(AplicacaoUtil.i18n("msg.mensagemRecebida.texto",parametros));
		this.emailService.salvar(email);	
	}

	public MensagemRecebidaDAO getMensagemRecebidaDAO() {
		return mensagemRecebidaDAO;
	}

	public void setMensagemRecebidaDAO(MensagemRecebidaDAO mensagemRecebidaDAO) {
		this.mensagemRecebidaDAO = mensagemRecebidaDAO;
	}

	public EmailService getEmailService() {
		return emailService;
	}

	public void setEmailService(EmailService emailService) {
		this.emailService = emailService;
	}

	public CondominoService getCondominoService() {
		return condominoService;
	}

	public void setCondominoService(CondominoService condominoService) {
		this.condominoService = condominoService;
	}

	public BlocoService getBlocoService() {
		return blocoService;
	}

	public void setBlocoService(BlocoService blocoService) {
		this.blocoService = blocoService;
	}

	public UnidadeService getUnidadeService() {
		return unidadeService;
	}

	public void setUnidadeService(UnidadeService unidadeService) {
		this.unidadeService = unidadeService;
	}

}
