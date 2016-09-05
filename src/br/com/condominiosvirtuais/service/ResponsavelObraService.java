package br.com.condominiosvirtuais.service;

import java.sql.SQLException;
import java.util.List;

import br.com.condominiosvirtuais.entity.ResponsavelObra;

public interface ResponsavelObraService {
	
	public abstract List<ResponsavelObra> buscarPorNome(String nome) throws SQLException, Exception;

}
