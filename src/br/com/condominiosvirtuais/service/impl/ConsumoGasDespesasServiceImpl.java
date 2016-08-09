package br.com.condominiosvirtuais.service.impl;

import java.io.Serializable;
import java.sql.SQLException;

import javax.inject.Inject;

import br.com.condominiosvirtuais.entity.ConsumoGasDespesas;
import br.com.condominiosvirtuais.persistence.impl.ConsumoGasDespesasDAOImpl;
import br.com.condominiosvirtuais.service.ConsumoGasDespesasService;

public class ConsumoGasDespesasServiceImpl implements ConsumoGasDespesasService, Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Inject
	private ConsumoGasDespesasDAOImpl consumoGasDespesasDAO;

	@Override
	public ConsumoGasDespesas buscarPorIdDespesa(Integer idDeDespesas) throws SQLException, Exception {
		return consumoGasDespesasDAO.buscarPorIdDespesa(idDeDespesas);
	}	

}
