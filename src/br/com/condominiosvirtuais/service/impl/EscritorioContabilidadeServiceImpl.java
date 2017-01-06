package br.com.condominiosvirtuais.service.impl;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

import javax.inject.Inject;

import br.com.condominiosvirtuais.entity.EscritorioContabilidade;
import br.com.condominiosvirtuais.persistence.EscritorioContabilidadeDAO;
import br.com.condominiosvirtuais.service.EscritorioContabilidadeService;

public class EscritorioContabilidadeServiceImpl implements Serializable, EscritorioContabilidadeService {
	
	private static final long serialVersionUID = 1L;
	
	@Inject
	private EscritorioContabilidadeDAO escritorioContabilidadeDAO;

	@Override
	public List<EscritorioContabilidade> buscarPorSituacao(Integer situacao) throws SQLException, Exception {
		return this.escritorioContabilidadeDAO.buscarPorSituacao(situacao);
	}

}
