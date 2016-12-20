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
	public void salvar(Receita receita) throws SQLException, Exception {
		this.receitaDAO.salvar(receita);
		
	}

	@Override
	public List<Receita> buscarPorDataDeEDataAteEIdCondominio(Date dataDe, Date dataAte, Integer idCondominio) throws SQLException, Exception {		
		return this.receitaDAO.buscarPorDataDeEDataAteEIdCondominio(dataDe, dataAte, idCondominio);
	}

	@Override
	public void atualizar(Receita receita) throws SQLException, Exception {
		this.receitaDAO.atualizar(receita);		
	}

	@Override
	public void excluir(Receita receita) throws SQLException, Exception {
		this.receitaDAO.excluir(receita);		
	}
	
	

}
