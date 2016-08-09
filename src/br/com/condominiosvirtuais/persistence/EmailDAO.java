package br.com.condominiosvirtuais.persistence;

import java.sql.SQLException;
import java.util.List;

import br.com.condominiosvirtuais.entity.Email;

public interface EmailDAO {
	
	public abstract void salvar(Email email) throws SQLException, Exception;
	
	public abstract void excluir(Email email) throws SQLException, Exception;
	
	public abstract List<Email> busca(Integer limite) throws SQLException, Exception;

	
}
