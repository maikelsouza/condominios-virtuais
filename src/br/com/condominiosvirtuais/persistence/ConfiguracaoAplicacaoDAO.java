package br.com.condominiosvirtuais.persistence;

import java.sql.SQLException;
import java.util.Map;

public interface ConfiguracaoAplicacaoDAO {
	
	public abstract Map<String, String> buscarTodas() throws SQLException, Exception;	

}
