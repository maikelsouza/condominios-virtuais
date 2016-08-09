package br.com.condominiosvirtuais.service;

import java.sql.SQLException;
import java.util.List;

import br.com.condominiosvirtuais.entity.Veiculo;

public interface VeiculoService {
	
	public abstract void salvar(Veiculo veiculo) throws SQLException, Exception;
	
	public abstract void excluir(Veiculo veiculo) throws SQLException, Exception;
	
	public abstract void atualizar(Veiculo veiculo) throws SQLException, Exception;	
	
	public abstract List<Veiculo> buscarPorIdCondomino(Integer idCondomino) throws NumberFormatException, SQLException, Exception;
	
	public abstract List<Veiculo> buscarPorIdCondominoOuNomeOuPlacaOuNumeroGaragem(Veiculo veiculo) throws NumberFormatException, SQLException, Exception;
	
	public abstract List<Veiculo> buscarPorIdCondominioENome(Integer idCondominio, String nome) throws SQLException, Exception;
	
	public abstract List<Veiculo> buscarPorIdCondominioEPlaca(Integer idCondominio, String placa) throws SQLException, Exception;
	
	public abstract List<Veiculo> buscarPorIdCondominio(Integer idCondominio) throws SQLException, Exception;
	

}
