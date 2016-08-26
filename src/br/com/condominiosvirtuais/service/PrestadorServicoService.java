package br.com.condominiosvirtuais.service;

import java.sql.SQLException;
import java.util.List;

import br.com.condominiosvirtuais.entity.PrestadorServico;

public interface PrestadorServicoService {
	
	
	public abstract List<PrestadorServico> buscarPorNome(String nome) throws SQLException, Exception;

}
