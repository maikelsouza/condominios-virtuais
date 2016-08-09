package br.com.condominiosvirtuais.service;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import br.com.condominiosvirtuais.entity.ConsumoGasUnidade;

public interface ConsumoGasUnidadeService {
	
	
	public abstract List<ConsumoGasUnidade> buscarPorMesAnoReferencia(Date mesAnoReferencia) throws SQLException, Exception;
	
	public abstract void salvar(ConsumoGasUnidade consumoGasUnidade) throws SQLException, Exception;
	
	public abstract ConsumoGasUnidade buscarPorMesAnoReferenciaEIdUnidade(Date mesAnoReferencia, Integer idUnidade) throws SQLException, Exception;

}
