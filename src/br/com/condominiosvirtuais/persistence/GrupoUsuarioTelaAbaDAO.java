package br.com.condominiosvirtuais.persistence;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import br.com.condominiosvirtuais.entity.GrupoUsuarioTelaAba;

public interface GrupoUsuarioTelaAbaDAO {
	
	public abstract void salvar(GrupoUsuarioTelaAba grupoUsuarioTelaAba, Connection con) throws SQLException, Exception;
	
	public abstract void excluirPorIdTela(Integer idTela, Connection con) throws SQLException, Exception;
	
	public abstract List<GrupoUsuarioTelaAba> buscarPorIdGrupoUsuarioEIdTela(Integer idGrupoUsuario, Integer idTela, Connection con) throws SQLException, Exception;
	
	public abstract List<GrupoUsuarioTelaAba> buscarPorIdTela(Integer idTela, Connection con) throws SQLException, Exception;
	
	
}
