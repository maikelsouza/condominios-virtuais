package br.com.condominiosvirtuais.service.impl;

import java.io.Serializable;
import java.sql.SQLException;

import javax.inject.Inject;

import br.com.condominiosvirtuais.entity.TarifaGas;
import br.com.condominiosvirtuais.persistence.impl.TarifaGasDAOImpl;
import br.com.condominiosvirtuais.service.TarifaGasService;

public class TarifaGasServiceImpl implements TarifaGasService, Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Inject
	private TarifaGasDAOImpl tarifaGasDAO;

	@Override
	public TarifaGas buscarPorIdCondominio(Integer idCondominio) throws SQLException, Exception {		
		return this.tarifaGasDAO.buscarPorIdCondominio(idCondominio);
	}

}
