package br.com.condominiosvirtuais.persistence;

import java.sql.SQLException;
import java.util.List;

import br.com.condominiosvirtuais.entity.GrupoUsuario;

public interface GrupoUsuarioDAO {
	
	public abstract GrupoUsuario buscarPorIdUsuario(Integer idUsuario) throws SQLException, Exception;
	
	public abstract void excluir(Integer idGrupoUsuario) throws SQLException, Exception;
	
	public abstract void salvar(GrupoUsuario grupoUsuario) throws SQLException, Exception;
	
	public abstract void atualizar(GrupoUsuario grupoUsuario) throws SQLException, Exception;
	
	public abstract List<GrupoUsuario> buscarPorIdCondominioESituacao(Integer idCondominio, Boolean situacao) throws SQLException, Exception;
	
	public abstract List<GrupoUsuario> buscarPorIdCondominio(Integer idCondominio) throws SQLException, Exception;


}
