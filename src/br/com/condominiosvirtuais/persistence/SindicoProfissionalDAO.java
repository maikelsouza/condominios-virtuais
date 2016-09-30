package br.com.condominiosvirtuais.persistence;

import java.sql.SQLException;
import java.util.List;

import br.com.condominiosvirtuais.entity.SindicoProfissional;

public interface SindicoProfissionalDAO {
	             
	public abstract void salvar(SindicoProfissional sindicoProfissional) throws SQLException, Exception;
	
	public abstract List<SindicoProfissional> buscarPorSituacao(Integer situacao) throws SQLException, Exception;

}
