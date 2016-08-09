package br.com.condominiosvirtuais.service.impl;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import br.com.condominiosvirtuais.entity.ConsumoGasUnidade;
import br.com.condominiosvirtuais.persistence.impl.ConsumoGasUnidadeDAOImpl;
import br.com.condominiosvirtuais.service.ConsumoGasUnidadeService;

public class ConsumoGasUnidadeServiceImpl implements ConsumoGasUnidadeService, Serializable {
	
	
	private static final long serialVersionUID = 1L;
	
	@Inject
	private ConsumoGasUnidadeDAOImpl consumoGasUnidadeDAO;

	@Override
	public List<ConsumoGasUnidade> buscarPorMesAnoReferencia(Date mesAnoReferencia) throws SQLException, Exception {
		return this.consumoGasUnidadeDAO.buscarPorMesAnoReferencia(mesAnoReferencia);
	}

	@Override
	public void salvar(ConsumoGasUnidade consumoGasUnidade) throws SQLException, Exception {
		this.consumoGasUnidadeDAO.salvar(consumoGasUnidade);		
	}

	@Override
	public ConsumoGasUnidade buscarPorMesAnoReferenciaEIdUnidade(Date mesAnoReferencia, Integer idUnidade) throws SQLException, Exception {		
		return this.consumoGasUnidadeDAO.buscarPorMesAnoReferenciaEIdUnidade(mesAnoReferencia, idUnidade);
	}

}
