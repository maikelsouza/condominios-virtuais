package br.com.condominiosvirtuais.service;

import java.sql.SQLException;
import java.util.List;

import br.com.condominiosvirtuais.entity.Contador;

public interface ContadorService {
	
	public abstract void salvar(Contador contador) throws SQLException, Exception;
	
	public abstract List<Contador> buscarPorIdEscritorioContabilidadeESituacao(Integer idEscritorioContabilidade, Integer situacao) throws SQLException, Exception;

}
