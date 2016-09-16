package br.com.condominiosvirtuais.service;

import java.sql.SQLException;

import br.com.condominiosvirtuais.entity.SindicoProfissional;

public interface SindicoProfissionalService {
	
	
	public abstract void salvar(SindicoProfissional sindicoProfissional) throws SQLException, Exception;

}
