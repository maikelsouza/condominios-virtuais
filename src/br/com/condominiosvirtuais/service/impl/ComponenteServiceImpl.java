package br.com.condominiosvirtuais.service.impl;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import br.com.condominiosvirtuais.entity.Componente;
import br.com.condominiosvirtuais.persistence.ComponenteDAO;
import br.com.condominiosvirtuais.service.ComponenteService;

public class ComponenteServiceImpl implements ComponenteService, Serializable{

	private static final long serialVersionUID = 1L;
	
	@Inject
	private ComponenteDAO componenteDAO;

	@Override
	public List<Componente> buscarPorIdTela(Integer idTela) throws SQLException, Exception {	
		// Ordenação realizada no código, pois o dado que é persistido no banco é a chave para o I18N
		List<Componente> listComponente = this.componenteDAO.buscarPorIdTela(idTela);
		Collections.sort(listComponente);
		return listComponente;
	}

}
