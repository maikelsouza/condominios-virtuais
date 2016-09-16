package br.com.condominiosvirtuais.persistence;

import java.sql.SQLException;

import br.com.condominiosvirtuais.entity.SindicoProfissional;

public interface SindicoProfissionalDAO {
	             
	public abstract void salvar(SindicoProfissional sindicoProfissional) throws SQLException, Exception;

}
