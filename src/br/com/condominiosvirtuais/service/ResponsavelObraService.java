package br.com.condominiosvirtuais.service;

import java.sql.SQLException;
import java.util.List;

import br.com.condominiosvirtuais.entity.ResponsavelObra;

public interface ResponsavelObraService {
	
	public abstract List<ResponsavelObra> buscarPorNomeETipoPessoa(String nome, Integer tipoPessoa) throws SQLException, Exception;

}
