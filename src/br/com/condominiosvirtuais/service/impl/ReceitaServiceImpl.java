package br.com.condominiosvirtuais.service.impl;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import br.com.condominiosvirtuais.entity.Receita;
import br.com.condominiosvirtuais.persistence.ReceitaDAO;
import br.com.condominiosvirtuais.service.ReceitaService;

public class ReceitaServiceImpl implements ReceitaService, Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private ReceitaDAO receitaDAO;

	@Override
	public void salvarReceita(Receita receita) throws SQLException, Exception {
		this.receitaDAO.salvarReceita(receita);
		
	}

	@Override
	public List<Receita> pesquisarPorMesAnoReferenciaEIdCondominio(Date mesAnoReferencia, Integer idCondominio) throws SQLException, Exception {
		return this.receitaDAO.pesquisarPorMesAnoReferenciaEIdCondominio(mesAnoReferencia, idCondominio);
	}

}
