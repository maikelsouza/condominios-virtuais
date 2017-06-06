package br.com.condominiosvirtuais.persistence;

import java.sql.SQLException;
import java.util.List;

import br.com.condominiosvirtuais.entity.GrupoUsuarioTela;

public interface GrupoUsuarioTelaDAO {
	
	public abstract List<GrupoUsuarioTela> buscarPorIdGrupoUsuario(Integer idGrupoUsuario) throws SQLException, Exception;
	
	public abstract void salvar(GrupoUsuarioTela grupoUsuarioTela) throws SQLException, Exception;
	
	public abstract void excluir(Integer idGrupoUsuarioTela) throws SQLException, Exception;

}
