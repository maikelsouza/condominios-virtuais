package br.com.condominiosvirtuais.persistence;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import br.com.condominiosvirtuais.entity.Tela;

public interface TelaDAO {

	public abstract Tela buscarPorId(Integer id, Connection con) throws SQLException, Exception;
	
	public abstract List<Tela> buscarPorIdGrupoUsuario(Integer idGrupoUsuario) throws SQLException, Exception;
	
	public abstract List<Tela> buscarTodas() throws SQLException, Exception;
	
}
