package br.com.condominiosvirtuais.persistence;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import br.com.condominiosvirtuais.entity.Condominio;
import br.com.condominiosvirtuais.entity.SindicoProfissional;

public interface CondominioDAO {
	
	public abstract Condominio buscarPorCodigo(Integer Codigo) throws SQLException, Exception;
	
	public abstract void popularBlocoEUnidadeDoCondominio(Condominio condominio) throws SQLException, Exception;
	
	public abstract List<Condominio> buscarPorSituacaoESemGestores(Integer situacao) throws SQLException, Exception;
	
	public abstract List<Condominio> buscarPorSituacao(Integer situacao) throws SQLException, Exception;
	
	public abstract Condominio buscarCondominioPorId(Integer id) throws SQLException, Exception;
	
    public abstract Condominio buscarCondominioPorId(Integer id, Connection con) throws SQLException, Exception;
    
 // TODO: Código comentado em 24/10/2016. Apagar em 90 dias   
  //  public abstract List<Condominio> buscarPorSindicoProfissional(SindicoProfissional sindicoProfissional) throws SQLException, Exception;
    
    

}
