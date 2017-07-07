package br.com.condominiosvirtuais.service;

import java.sql.SQLException;
import java.util.List;

import br.com.condominiosvirtuais.entity.Tela;

public interface TelaService {
	
	
	public abstract List<Tela> buscarPorIdGrupoUsuario(Integer idGrupoUsuario) throws SQLException, Exception;
	
	public abstract List<Tela> buscarTodas() throws SQLException, Exception;
	
	

}
