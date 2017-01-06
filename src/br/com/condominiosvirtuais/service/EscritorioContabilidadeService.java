package br.com.condominiosvirtuais.service;

import java.sql.SQLException;
import java.util.List;

import br.com.condominiosvirtuais.entity.EscritorioContabilidade;

public interface EscritorioContabilidadeService {
	
	public abstract List<EscritorioContabilidade> buscarPorSituacao(Integer situacao) throws SQLException, Exception;

}
