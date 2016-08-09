package br.com.condominiosvirtuais.persistence;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import br.com.condominiosvirtuais.entity.Ambiente;
import br.com.condominiosvirtuais.entity.ItemAmbiente;

public interface ItemAmbienteDAO {
	
	public abstract List<ItemAmbiente> buscarPorAmbiente(Ambiente ambiente) throws SQLException, Exception;
	
	public abstract void salvar(ItemAmbiente itemAmbiente) throws SQLException, Exception;
	
	public abstract void excluir(ItemAmbiente itemAmbiente) throws SQLException, Exception;
	
	public abstract void atualizar(ItemAmbiente itemAmbiente) throws SQLException, Exception;
	
	public abstract void excluir(ItemAmbiente itemAmbiente, Connection con) throws SQLException, Exception;

}
