package br.com.condominiosvirtuais.service.impl;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import br.com.condominiosvirtuais.entity.Despesa;
import br.com.condominiosvirtuais.persistence.DespesaDAO;
import br.com.condominiosvirtuais.service.DespesaService;

public class DespesaServiceImpl implements DespesaService, Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private DespesaDAO despesaDAO;

	@Override
	public void salvar(Despesa despesa) throws SQLException, Exception {
		this.despesaDAO.salvar(despesa);		
	}
	
	
	@Override
	public List<Despesa> buscarPorDataDeEDataAteEIdCondominio(Date dataDe, Date dataAte, Integer idCondominio) throws SQLException, Exception {		
		return despesaDAO.buscarPorDataDeEDataAteEIdCondominio(dataDe, dataAte, idCondominio);
	}


	@Override
	public void atualizar(Despesa despesa) throws SQLException, Exception {
		this.despesaDAO.atualizar(despesa);		
	}


	@Override
	public void excluir(Despesa despesa) throws SQLException, Exception {
		this.despesaDAO.excluir(despesa);		
	}

}
