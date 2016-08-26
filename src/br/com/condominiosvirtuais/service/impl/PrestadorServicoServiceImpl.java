package br.com.condominiosvirtuais.service.impl;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

import javax.inject.Inject;

import br.com.condominiosvirtuais.entity.PrestadorServico;
import br.com.condominiosvirtuais.persistence.impl.PrestadorServicoDAOImpl;
import br.com.condominiosvirtuais.service.PrestadorServicoService;

public class PrestadorServicoServiceImpl implements Serializable, PrestadorServicoService {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private PrestadorServicoDAOImpl prestadorServicoDAO;

	@Override
	public List<PrestadorServico> buscarPorNome(String nome) throws SQLException, Exception {
		return this.prestadorServicoDAO.buscarPorNome(nome);
	}

}
