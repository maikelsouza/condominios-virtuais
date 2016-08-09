package br.com.condominiosvirtuais.service.impl;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

import javax.inject.Inject;

import br.com.condominiosvirtuais.entity.Veiculo;
import br.com.condominiosvirtuais.persistence.VeiculoDAO;
import br.com.condominiosvirtuais.service.VeiculoService;

public class VeiculoServiceImpl implements VeiculoService, Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Inject
	private VeiculoDAO veiculoDAO;
	
	
	@Override
	public void salvar(Veiculo veiculo) throws SQLException, Exception {
		this.veiculoDAO.salvar(veiculo);
	}

	@Override
	public void excluir(Veiculo veiculo) throws SQLException, Exception {
		this.veiculoDAO.excluir(veiculo);
	}

	@Override
	public void atualizar(Veiculo veiculo) throws SQLException, Exception {
		this.veiculoDAO.atualizar(veiculo);
	}

	@Override
	public List<Veiculo> buscarPorIdCondomino(Integer idCondomino) throws NumberFormatException, SQLException, Exception {
		return this.veiculoDAO.buscarPorIdCondomino(idCondomino);
	}

	@Override
	public List<Veiculo> buscarPorIdCondominoOuNomeOuPlacaOuNumeroGaragem(Veiculo veiculo) throws NumberFormatException, SQLException, Exception {
		return this.veiculoDAO.buscarPorIdCondominoOuNomeOuPlacaOuNumeroGaragem(veiculo);
	}

	@Override
	public List<Veiculo> buscarPorIdCondominioENome(Integer idCondominio,
			String nome) throws SQLException, Exception {
		return this.veiculoDAO.buscarPorIdCondominioENome(idCondominio, nome);
	}

	@Override
	public List<Veiculo> buscarPorIdCondominioEPlaca(Integer idCondominio,
			String placa) throws SQLException, Exception {
		return this.veiculoDAO.buscarPorIdCondominioEPlaca(idCondominio, placa);
	}

	@Override
	public List<Veiculo> buscarPorIdCondominio(Integer idCondominio) throws SQLException, Exception {		
		return this.veiculoDAO.buscarPorIdCondominio(idCondominio);
	}

	public VeiculoDAO getVeiculoDAO() {
		return veiculoDAO;
	}

	public void setVeiculoDAO(VeiculoDAO veiculoDAO) {
		this.veiculoDAO = veiculoDAO;
	}

}
