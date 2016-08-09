package br.com.condominiosvirtuais.service.impl;

import java.io.Serializable;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.faces.model.ListDataModel;
import javax.inject.Inject;

import br.com.condominiosvirtuais.entity.Bloco;
import br.com.condominiosvirtuais.entity.Condominio;
import br.com.condominiosvirtuais.entity.ConsumoGasDespesas;
import br.com.condominiosvirtuais.entity.ConsumoGasUnidade;
import br.com.condominiosvirtuais.entity.Despesas;
import br.com.condominiosvirtuais.entity.TarifaGas;
import br.com.condominiosvirtuais.entity.Unidade;
import br.com.condominiosvirtuais.enumeration.DespesasEnum;
import br.com.condominiosvirtuais.persistence.impl.DespesasDAOImpl;
import br.com.condominiosvirtuais.reports.DespesasUnidade;
import br.com.condominiosvirtuais.service.DespesasService;

public class DespesasServiceImpl implements DespesasService,  Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Inject
	private DespesasDAOImpl despesasDAO;  
	
	@Inject
	private CondominioServiceImpl condominioService;
	
	@Inject
	private TarifaGasServiceImpl tarifaGasService;
	
	@Inject
	private ConsumoGasUnidadeServiceImpl consumoGasUnidadeService;
	
	@Inject
	private ConsumoGasDespesasServiceImpl consumoGasDespesasService;	
	
	@Override
	public void excluir(Despesas despesas) throws SQLException, Exception {
		this.despesasDAO.excluir(despesas);		
	}

	@Override
	public void atualizarDespesasCondominio(Despesas despesas) throws SQLException, Exception {
		this.despesasDAO.atualizarDespesasCondominio(despesas);		
	}
	
	@Override
	public void atualizarDespesasCondomino(Despesas despesas) throws SQLException, Exception {
		this.despesasDAO.atualizarDespesasCondomino(despesas);	
	}

	@Override
	public void salvarDespesasCondominio(Despesas despesas) throws SQLException, Exception {		
		this.despesasDAO.salvarDespesasCondominio(despesas);		
	}

	@Override
	public void salvarDespesasCondomino(Despesas despesas) throws SQLException, Exception {		
		this.despesasDAO.salvarDespesasCondomino(despesas);
		
	}
	
	@Override
	public void salvarDespesasGasCondomino(Despesas despesas, Double consumoAtual) throws SQLException, Exception {
		Calendar mesAnoReferencia = GregorianCalendar.getInstance();
		// Decrementa 1 mês no mês de referência para o cálculo do consumo de gás
		mesAnoReferencia.setTime(despesas.getMesAnoReferencia());
		mesAnoReferencia.add(Calendar.MONTH,-1);
		TarifaGas tarifaGas = null;
		ConsumoGasUnidade consumoGasUnidade = null;
		Double consumoMesAnterior = 0.0;
		Double resultadoConsumoAtualMenosAnterior = 0.0;
		Double valorDoGas = 0.0;		
		tarifaGas = this.tarifaGasService.buscarPorIdCondominio(despesas.getCondominio().getId());
		consumoGasUnidade = this.consumoGasUnidadeService.
				buscarPorMesAnoReferenciaEIdUnidade(mesAnoReferencia.getTime(), despesas.getUnidade().getId());
		// A leitura do mês atual vira do mês anterior, pois foi decrementado o mês/ano Referência.
		consumoMesAnterior = consumoGasUnidade.getConsumoMesAtual();
		resultadoConsumoAtualMenosAnterior = consumoAtual - consumoMesAnterior;
		valorDoGas = resultadoConsumoAtualMenosAnterior * tarifaGas.getFatorConversao() * tarifaGas.getValorCobrado();
		despesas.setValor(valorDoGas);
		this.despesasDAO.salvarDespesasCondomino(despesas);
	}
	
	@Override
	public void salvarDespesasGasCondomino(Despesas despesas) throws SQLException, Exception {
		Calendar mesAnoReferencia = GregorianCalendar.getInstance();
		// Decrementa 1 mês no mês de referência para o cálculo do consumo de gás
		mesAnoReferencia.setTime(despesas.getMesAnoReferencia());
		mesAnoReferencia.add(Calendar.MONTH,-1);
		TarifaGas tarifaGas = null;		
		ConsumoGasDespesas consumoGasDespesasMesAnterior = null;		
		Double consumoMesAnterior = 0.0;
		Double resultadoConsumoAtualMenosAnterior = 0.0;
		Double valorDoGas = 0.0;
		Despesas despesasGasMesAnterior = this.buscarPorIdUnidadeEMesAnoReferenciaETipo(despesas.getUnidade().getId(), 
				mesAnoReferencia.getTime(), despesas.getTipo());
		tarifaGas = this.tarifaGasService.buscarPorIdCondominio(despesas.getCondominio().getId());
		consumoGasDespesasMesAnterior = consumoGasDespesasService.buscarPorIdDespesa(despesasGasMesAnterior.getId());		
		// A leitura do mês atual vira do mês anterior, pois foi decrementado o mês/ano Referência.
		consumoMesAnterior = consumoGasDespesasMesAnterior.getConsumoMesAtual();
		resultadoConsumoAtualMenosAnterior = despesas.getConsumoGasDespesas().getConsumoMesAtual() - consumoMesAnterior;
		valorDoGas = resultadoConsumoAtualMenosAnterior * tarifaGas.getFatorConversao() * tarifaGas.getValorCobrado();
		DecimalFormat formato = new DecimalFormat("#.00");		
		despesas.setValor(Double.valueOf(formato.format(valorDoGas).replace(',','.')));		
		this.despesasDAO.salvarDespesasCondominoGasUnidade(despesas);
		
	}
	
	@Override
	public void atualizarDespesasGasCondomino(Despesas despesas) throws SQLException, Exception {
		Calendar mesAnoReferencia = GregorianCalendar.getInstance();
		// Decrementa 1 mês no mês de referência para o cálculo do consumo de gás
		mesAnoReferencia.setTime(despesas.getMesAnoReferencia());
		mesAnoReferencia.add(Calendar.MONTH,-1);
		TarifaGas tarifaGas = null;		
		ConsumoGasDespesas consumoGasDespesasMesAnterior = null;		
		Double consumoMesAnterior = 0.0;
		Double resultadoConsumoAtualMenosAnterior = 0.0;
		Double valorDoGas = 0.0;
		Despesas despesasGasMesAnterior = this.buscarPorIdUnidadeEMesAnoReferenciaETipo(despesas.getUnidade().getId(), 
				mesAnoReferencia.getTime(), despesas.getTipo());
		tarifaGas = this.tarifaGasService.buscarPorIdCondominio(despesas.getCondominio().getId());
		consumoGasDespesasMesAnterior = consumoGasDespesasService.buscarPorIdDespesa(despesasGasMesAnterior.getId());		
		// A leitura do mês atual vira do mês anterior, pois foi decrementado o mês/ano Referência.
		consumoMesAnterior = consumoGasDespesasMesAnterior.getConsumoMesAtual();
		resultadoConsumoAtualMenosAnterior = despesas.getConsumoGasDespesas().getConsumoMesAtual() - consumoMesAnterior;
		valorDoGas = resultadoConsumoAtualMenosAnterior * tarifaGas.getFatorConversao() * tarifaGas.getValorCobrado();
		DecimalFormat formato = new DecimalFormat("#.00");		
		despesas.setValor(Double.valueOf(formato.format(valorDoGas).replace(',','.')));		
		this.despesasDAO.atualizarDespesasGasCondomino(despesas);
		
	}

	@Override
	public List<Despesas> buscarPorIdCondominioEMesAnoReferencia(Integer idCondominio, Date mesAnoReferencia)
			throws SQLException, Exception {		
		List<Despesas> listaDespesasUnidades = new ArrayList<Despesas>();
		List<Despesas> listaDespesasCondominio = new ArrayList<Despesas>();
		List<Despesas> listaDespesasCondominioEUnidades = new ArrayList<Despesas>();
		Condominio condominio = this.condominioService.buscarPorId(idCondominio);
		this.condominioService.popularBlocoEUnidadeDoCondominio(condominio);
		for (Bloco bloco : condominio.getListaBlocos()) {
			for (Unidade unidade : bloco.getListaUnidade()) {				
				listaDespesasUnidades.addAll(this.despesasDAO.buscarPorIdUnidadeEMesAnoReferencia(unidade.getId(), mesAnoReferencia));
			}			
		}
		listaDespesasCondominio = this.despesasDAO.buscarPorIdCondominioEMesAnoReferencia(idCondominio, mesAnoReferencia);
		listaDespesasCondominioEUnidades.addAll(listaDespesasCondominio);		
		return listaDespesasCondominioEUnidades;
	}
	
	public List<DespesasUnidade> buscarDespesaUnidadePorIdCondominioEMesAnoReferencia(Integer idCondominio, Date mesAnoReferencia)
			throws SQLException, Exception {
		List<DespesasUnidade> listaDespesasUnidade  = new ArrayList<DespesasUnidade>();
		List<Despesas> listaDespesas  = null;
		DespesasUnidade despesasUnidade = null;		
		Condominio condominio = this.condominioService.buscarPorId(idCondominio);
		this.condominioService.popularBlocoEUnidadeDoCondominio(condominio);
		for (Bloco bloco : condominio.getListaBlocos()) {
			for (Unidade unidade : bloco.getListaUnidade()) {		
				despesasUnidade = new DespesasUnidade();
				despesasUnidade.setUnidade(unidade);
				listaDespesas = this.despesasDAO.buscarPorIdUnidadeEMesAnoReferencia(unidade.getId(), mesAnoReferencia);
				if (!listaDespesas.isEmpty()){
					despesasUnidade.setListaDespesas(new ListDataModel<Despesas>(listaDespesas));
					listaDespesasUnidade.add(despesasUnidade);
				}
			}			
		}
		return listaDespesasUnidade;
	}


	@Override
	public List<Despesas> buscarPorIdUnidadeEMesAnoReferencia(Integer idUnidade, Date mesAnoReferencia)	throws SQLException, Exception {		
		return this.despesasDAO.buscarPorIdUnidadeEMesAnoReferencia(idUnidade, mesAnoReferencia);
	}

	@Override
	public void salvarDespesasUnicaCondominos(Despesas despesas) throws SQLException, Exception {
		Condominio condominio = this.condominioService.buscarPorId(despesas.getCondominio().getId());
		this.condominioService.popularBlocoEUnidadeDoCondominio(condominio);
		for (Bloco bloco : condominio.getListaBlocos()) {
			for (Unidade unidade : bloco.getListaUnidade()) {	
				despesas.setUnidade(unidade);
				// Se a despesa 
				if((unidade.getId() != 754) || (despesas.getTipo() != DespesasEnum.RATEIO_SINDICO.getDespesa())){
					this.despesasDAO.salvarDespesasCondomino(despesas);
				}				
			}			
		}		
	}

	@Override
	public void salvarDespesasUnicaRateioPadraoCondominos(List<Despesas> listaDespesas) throws SQLException, Exception {
		Condominio condominio = this.condominioService.buscarPorId(listaDespesas.get(0).getCondominio().getId());
		this.condominioService.popularBlocoEUnidadeDoCondominio(condominio);
		for (Bloco bloco : condominio.getListaBlocos()) {
			Unidade unidade = null;
			Despesas despesas = null;
			for (int i = 0; i < bloco.getListaUnidade().size(); i++) {
				unidade = bloco.getListaUnidade().get(i);
				despesas = listaDespesas.get(i);
				despesas.setUnidade(unidade);
				this.despesasDAO.salvarDespesasCondomino(despesas);
			}					
		}
		
	}

	@Override
	public Despesas buscarPorIdUnidadeEMesAnoReferenciaETipo(Integer idUnidade, Date mesAnoReferencia, Integer tipo) throws SQLException, Exception {		
		return this.despesasDAO.buscarPorIdUnidadeEMesAnoReferenciaETipo(idUnidade, mesAnoReferencia, tipo);
	}


	
	
// TODO: Código comentado em 09/06/2016. Apagar após 90 dias
//	public List<Despesas> buscarPorIdCondominioEMesAnoReferencia(Integer idCondominio, Date mesAnoReferencia)
//			throws SQLException, Exception {		
//		List<Despesas> listaDespesasUnidades = new ArrayList<Despesas>();
//		List<Despesas> listaDespesasCondominio = new ArrayList<Despesas>();
//		List<Despesas> listaDespesasCondominioEUnidades = new ArrayList<Despesas>();
//		Condominio condominio = this.condominioService.buscarPorId(idCondominio);
//		this.condominioService.popularBlocoEUnidadeDoCondominio(condominio);
//		for (Bloco bloco : condominio.getListaBlocos()) {
//			for (Unidade unidade : bloco.getListaUnidade()) {				
//				listaDespesasUnidades.addAll(this.despesasDAO.buscarPorIdUnidadeEMesAnoReferencia(unidade.getId(), mesAnoReferencia));
//			}			
//		}
//		listaDespesasCondominio = this.despesasDAO.buscarPorIdCondominioEMesAnoReferencia(idCondominio, mesAnoReferencia);
//		listaDespesasCondominioEUnidades.addAll(listaDespesasCondominio);
//		//listaDespesasCondominioEUnidades.addAll(listaDespesasUnidades);
//		return listaDespesasCondominioEUnidades;
//	}
	
	
	

}
