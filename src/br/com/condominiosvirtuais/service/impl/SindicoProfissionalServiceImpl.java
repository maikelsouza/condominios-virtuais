package br.com.condominiosvirtuais.service.impl;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

import javax.inject.Inject;

import br.com.condominiosvirtuais.entity.SindicoProfissional;
import br.com.condominiosvirtuais.persistence.SindicoProfissionalDAO;
import br.com.condominiosvirtuais.persistence.UsuarioDAO;
import br.com.condominiosvirtuais.service.SindicoProfissionalService;

public class SindicoProfissionalServiceImpl implements SindicoProfissionalService, Serializable {

	private static final long serialVersionUID = 1L;
	
	
	@Inject
	private SindicoProfissionalDAO sindicoProfissionalDAO;
	
	@Inject
	private UsuarioDAO usuarioDAO;

	@Override
	public void salvar(SindicoProfissional sindicoProfissional) throws SQLException, Exception {
		this.sindicoProfissionalDAO.salvar(sindicoProfissional);		
	}

	@Override
	public List<SindicoProfissional> buscarPorSituacao(Integer situacao) throws SQLException, Exception {		
		return this.sindicoProfissionalDAO.buscarPorSituacao(situacao);
	}

	@Override
	public void atualizar(SindicoProfissional sindicoProfissional) throws SQLException, Exception {
		this.sindicoProfissionalDAO.atualizar(sindicoProfissional);
	}

	@Override
	public SindicoProfissional buscarPorId(Integer idSindicoProfissinal) throws SQLException, Exception {
		return this.sindicoProfissionalDAO.buscarPorId(idSindicoProfissinal);
	}

	@Override
	public void atualizarSenha(SindicoProfissional sindicoProfissional) throws SQLException, Exception {
		this.usuarioDAO.atualizarSenha(sindicoProfissional);
		
	}

}
