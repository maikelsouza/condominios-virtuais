package br.com.condominiosvirtuais.persistence;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import br.com.condominiosvirtuais.entity.TelaAba;

public interface TelaAbaDAO {
	
	public abstract void salvar(TelaAba telaAba, Connection con) throws SQLException, Exception;
	
	public abstract void excluirPorIdTela(Integer idTela, Connection con) throws SQLException, Exception;
	
	public abstract List<TelaAba> buscarPorIdTela(Integer idTela, Connection con) throws SQLException, Exception;
	
	
}
