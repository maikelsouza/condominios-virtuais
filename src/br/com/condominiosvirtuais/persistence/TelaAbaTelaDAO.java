package br.com.condominiosvirtuais.persistence;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import br.com.condominiosvirtuais.entity.TelaAbaTela;

public interface TelaAbaTelaDAO {
	
	public abstract void salvar(TelaAbaTela telaAbaTela, Connection con) throws SQLException, Exception;
	
	public abstract void excluirPorIdTela(Integer idTela, Connection con) throws SQLException, Exception;
	
	public abstract List<TelaAbaTela> buscarPorIdTela(Integer idTela, Connection con) throws SQLException, Exception;
	
	
}
