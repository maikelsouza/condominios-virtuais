package br.com.condominiosvirtuais.service;

import java.sql.SQLException;

import br.com.condominiosvirtuais.entity.Endereco;

public interface EnderecoService {
	
	public abstract Endereco buscaPorIdCondominio(Integer idCondominio) throws SQLException, Exception;

}
