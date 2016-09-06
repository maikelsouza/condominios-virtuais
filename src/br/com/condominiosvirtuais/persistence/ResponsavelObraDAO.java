package br.com.condominiosvirtuais.persistence;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import br.com.condominiosvirtuais.entity.ResponsavelObra;

public interface ResponsavelObraDAO {
	
	public abstract Integer salvar(ResponsavelObra responsavelObra, Connection con) throws SQLException, Exception;
	
	public abstract void atualizar(ResponsavelObra responsavelObra, Connection con) throws SQLException, Exception;
	
	public abstract List<ResponsavelObra> buscarPorNome(String nome) throws SQLException, Exception;
	
	public abstract List<ResponsavelObra> buscarPorRazaoSocial(String nome) throws SQLException, Exception;
	
	public abstract ResponsavelObra buscarPorId(Integer id, Connection con) throws SQLException, Exception;

}
