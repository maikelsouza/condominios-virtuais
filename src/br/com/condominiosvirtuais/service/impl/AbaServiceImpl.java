package br.com.condominiosvirtuais.service.impl;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import br.com.condominiosvirtuais.entity.Aba;
import br.com.condominiosvirtuais.persistence.AbaDAO;
import br.com.condominiosvirtuais.service.AbaService;

public class AbaServiceImpl implements AbaService, Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private AbaDAO abaDAO;

	@Override
	public List<Aba> buscarPorIdTela(Integer idTela) throws SQLException, Exception {
		// Ordenação realizada no código, pois o dado que é persistido no banco é a chave para o I18N
		List<Aba> listAba = this.abaDAO.buscarPorIdTela(idTela);
		Collections.sort(listAba);
		return listAba;
	}

}
