package br.com.condominiosvirtuais.persistence;

import java.sql.Connection;
import java.sql.SQLException;

import br.com.condominiosvirtuais.entity.VisitaPrestadorServico;

public interface VisitaPrestadorServicoDAO {
	
	public abstract void salvar(VisitaPrestadorServico visitaPrestadorServico , Connection con) throws SQLException, Exception;
	
	public abstract VisitaPrestadorServico buscarPodIdVisita(Integer idVisita, Connection con) throws SQLException, Exception;

}
