package br.com.condominiosvirtuais.service;

import java.sql.SQLException;
import java.util.List;

import br.com.condominiosvirtuais.entity.SindicoProfissional;

public interface SindicoProfissionalService {
	
	
	public abstract void salvar(SindicoProfissional sindicoProfissional) throws SQLException, Exception;
	
	public abstract List<SindicoProfissional> buscarPorSituacao(Integer situacao) throws SQLException, Exception;
	
	public abstract void atualizar(SindicoProfissional sindicoProfissional) throws SQLException, Exception;
	
}
