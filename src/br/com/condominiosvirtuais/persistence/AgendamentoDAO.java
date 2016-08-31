package br.com.condominiosvirtuais.persistence;

import java.sql.SQLException;
import java.util.List;

import br.com.condominiosvirtuais.entity.Agendamento;
import br.com.condominiosvirtuais.entity.Condominio;
import br.com.condominiosvirtuais.entity.Condomino;

public interface AgendamentoDAO {
	
	public void salvar(Agendamento agendamento) throws SQLException, Exception;
	
	public abstract List<Agendamento> buscarPorCondominioESituacao(Condominio condominio, String Situacao) throws SQLException, Exception;
	
	public abstract List<Agendamento> buscarPorCondominio(Condominio condominio) throws SQLException, Exception;
	
	public abstract List<Agendamento> buscarPorCondomino(Condomino condomino) throws SQLException, Exception;
	
	public abstract void aprovar(Agendamento agendamento) throws SQLException, Exception;
	
	public abstract void reprovar(Agendamento agendamento) throws SQLException, Exception;

}
