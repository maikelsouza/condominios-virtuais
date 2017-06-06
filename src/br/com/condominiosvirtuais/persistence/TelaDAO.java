package br.com.condominiosvirtuais.persistence;

import java.sql.SQLException;

import br.com.condominiosvirtuais.entity.Tela;

public interface TelaDAO {

	public abstract Tela buscarPorId(Integer id) throws SQLException, Exception;
	
}
