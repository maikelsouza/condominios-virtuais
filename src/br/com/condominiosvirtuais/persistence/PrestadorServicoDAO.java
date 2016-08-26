package br.com.condominiosvirtuais.persistence;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import br.com.condominiosvirtuais.entity.PrestadorServico;

public interface PrestadorServicoDAO {
	
	public abstract Integer salvar(PrestadorServico prestadorServico, Connection con) throws SQLException, Exception;
	
	public abstract void atualizar(PrestadorServico prestadorServico, Connection con) throws SQLException, Exception;
	
	public abstract PrestadorServico buscarPorIdVisita(Integer idVisita, Connection con) throws SQLException, Exception;
	
	public abstract PrestadorServico buscarPorId(Integer id, Connection con) throws SQLException, Exception;
	
	public abstract List<PrestadorServico> buscarPorNome(String nome) throws SQLException, Exception;

}
