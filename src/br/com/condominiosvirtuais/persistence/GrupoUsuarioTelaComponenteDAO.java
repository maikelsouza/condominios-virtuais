package br.com.condominiosvirtuais.persistence;

import java.sql.Connection;
import java.sql.SQLException;

import br.com.condominiosvirtuais.entity.GrupoUsuarioTelaComponente;

public interface GrupoUsuarioTelaComponenteDAO {
	
	public abstract void excluirPorIdGrupoUsuario(Integer idGrupoUsuario, Connection con) throws SQLException, Exception;
	
	public abstract void salvar(GrupoUsuarioTelaComponente grupoUsuarioTelaComponente, Connection con) throws SQLException, Exception;

}
