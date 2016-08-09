package br.com.condominiosvirtuais.service.impl;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

import javax.inject.Inject;

import br.com.condominiosvirtuais.entity.MensagemEnviada;
import br.com.condominiosvirtuais.entity.Usuario;
import br.com.condominiosvirtuais.persistence.MensagemEnviadaDAO;
import br.com.condominiosvirtuais.service.MensagemEnviadaService;

public class MensagemEnviadaServiceImpl implements MensagemEnviadaService, Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Inject
	private MensagemEnviadaDAO mensagemEnviadaDAO = null;
	
	@Override
	public void excluir(MensagemEnviada mensagemEnviada) throws SQLException, Exception {
		this.mensagemEnviadaDAO.excluir(mensagemEnviada);
	}

	@Override
	public List<MensagemEnviada> buscarPorUsuarioRemetente(Usuario usuario) throws SQLException, Exception {
		return this.mensagemEnviadaDAO.buscarPorUsuarioRemetente(usuario);
	}

	public MensagemEnviadaDAO getMensagemEnviadaDAO() {
		return mensagemEnviadaDAO;
	}

	public void setMensagemEnviadaDAO(MensagemEnviadaDAO mensagemEnviadaDAO) {
		this.mensagemEnviadaDAO = mensagemEnviadaDAO;
	}

}
