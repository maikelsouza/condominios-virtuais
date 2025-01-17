package br.com.condominiosvirtuais.persistence;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import br.com.condominiosvirtuais.entity.Agendamento;
import br.com.condominiosvirtuais.entity.Condominio;
import br.com.condominiosvirtuais.entity.Condomino;

public interface AgendamentoDAO {
	
	public void salvar(Agendamento agendamento) throws SQLException, Exception;
	
	public abstract List<Agendamento> buscarPorCondominioESituacao(Condominio condominio, String Situacao) throws SQLException, Exception;
	
	public abstract List<Agendamento> buscarPorCondominioESituacaoETipo(Condominio condominio, String situacao, String tipo) throws SQLException, Exception;
	
	public abstract List<Agendamento> buscarPorCondominioETipo(Condominio condominio, String tipo) throws SQLException, Exception;	 
	
	public abstract List<Agendamento> buscarPorCondominio(Condominio condominio) throws SQLException, Exception;
	
	public abstract List<Agendamento> buscarPorCondomino(Condomino condomino) throws SQLException, Exception;
	
	public abstract void aprovar(Agendamento agendamento) throws SQLException, Exception;
	
	public abstract void reprovar(Agendamento agendamento) throws SQLException, Exception;
	
	public abstract List<Agendamento> buscarPorCondominioEData(Condominio condominio, Date data) throws SQLException, Exception;

}
