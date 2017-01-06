package br.com.condominiosvirtuais.persistence;

import java.sql.SQLException;
import java.util.List;

import br.com.condominiosvirtuais.entity.EscritorioContabilidade;

public interface EscritorioContabilidadeDAO {
	
	public abstract void salvar(EscritorioContabilidade escritorioContabilidade) throws SQLException, Exception;
	
	public abstract List<EscritorioContabilidade> buscarPorSituacao(Integer situacao) throws SQLException, Exception;
	
}
