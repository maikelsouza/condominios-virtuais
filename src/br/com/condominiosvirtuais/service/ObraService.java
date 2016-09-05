package br.com.condominiosvirtuais.service;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import br.com.condominiosvirtuais.entity.Obra;

public interface ObraService {
	
	public abstract void salvarObraESalvaResponsavelObra(Obra obra) throws SQLException, Exception;
	
	public abstract void salvarObraEAtualizarResponsavel(Obra obra) throws SQLException, Exception;
	
	public abstract List<Obra> buscarPorCondominioEPeriodoDeAte(Integer idCondominio, Date dataInicioDe, Date dataInicioAte) throws SQLException, Exception;
	
	public abstract void excluir(Integer idObra) throws SQLException, Exception;
	
	public abstract void atualizarObraEAtualizarResponsavelObra(Obra obra) throws SQLException, Exception;
	
	public abstract void atualizarObraESalvarResponsavelObra(Obra obra) throws SQLException, Exception;	

}
