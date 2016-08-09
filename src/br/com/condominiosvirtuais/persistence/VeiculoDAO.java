package br.com.condominiosvirtuais.persistence;

import java.sql.SQLException;
import java.util.List;

import br.com.condominiosvirtuais.entity.Veiculo;
import br.com.condominiosvirtuais.exception.BusinessException;

public interface VeiculoDAO {
	
	public abstract void salvar(Veiculo veiculo) throws SQLException, BusinessException,  Exception;
	
	public abstract void excluir(Veiculo veiculo) throws SQLException, Exception;
	
	public abstract void atualizar(Veiculo veiculo) throws SQLException, BusinessException, Exception;
	
	public abstract List<Veiculo> buscarPorIdCondomino(Integer idCondomino) throws NumberFormatException, SQLException, Exception;
	
	public abstract List<Veiculo> buscarPorIdCondominoOuNomeOuPlacaOuNumeroGaragem(Veiculo veiculo) throws NumberFormatException, SQLException, Exception;
	
	public abstract List<Veiculo> buscarPorIdCondominioENome(Integer idCondomino, String nome) throws SQLException, Exception;;
	
	public abstract List<Veiculo> buscarPorIdCondominioEPlaca(Integer idCondomino, String placa) throws SQLException, Exception;;
	
	public abstract List<Veiculo> buscarPorIdCondominio(Integer idCondominio) throws SQLException, Exception;	

}
