package br.com.condominiosvirtuais.persistence;

import java.sql.Connection;
import java.sql.SQLException;

import br.com.condominiosvirtuais.entity.ObraResponsavelObra;

public interface ObraResponsavelObraDAO {
	
	public abstract void excluirPodIdObra(Integer idObra, Connection con) throws SQLException, Exception;
	
	public abstract void salvar(ObraResponsavelObra obraResponsavelObra, Connection con) throws SQLException, Exception;
	
	public abstract void atualizarPorIdObra(ObraResponsavelObra obraResponsavelObra, Connection con) throws SQLException, Exception;
	
	public abstract ObraResponsavelObra buscarPorIdObra(Integer idObra, Connection con) throws SQLException, Exception;

}
