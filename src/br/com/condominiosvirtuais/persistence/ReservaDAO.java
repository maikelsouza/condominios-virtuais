package br.com.condominiosvirtuais.persistence;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import br.com.condominiosvirtuais.entity.Condominio;
import br.com.condominiosvirtuais.entity.Condomino;
import br.com.condominiosvirtuais.entity.Reserva;
import br.com.condominiosvirtuais.exception.BusinessException;

public interface ReservaDAO {
	
	public abstract void salvar(Reserva reserva) throws SQLException, BusinessException, Exception;	
		
	public abstract void excluir(Reserva reserva) throws SQLException, Exception;
	
	public abstract List<Reserva> buscarPorCondomino(Condomino condomino) throws NumberFormatException, SQLException, Exception;
	
	public abstract void atualizar(Reserva reserva) throws SQLException, BusinessException, Exception;

	public abstract List<Reserva> buscarPorCondominioETipo(Condominio condominio, String tipo) throws SQLException, Exception;
	
	public abstract List<Reserva> buscarPorCondominio(Condominio condominio) throws SQLException, Exception;
	
	public abstract List<Reserva> buscarPorIdAmbienteEMaiorIgualDataEPendeteOUAprovado(Integer idAmbiente, Date data) throws NumberFormatException, SQLException, Exception;
	
	public abstract void aprovar(Reserva reserva) throws SQLException, Exception;
	
	public abstract void reprovar(Reserva reserva) throws SQLException, Exception;
}
