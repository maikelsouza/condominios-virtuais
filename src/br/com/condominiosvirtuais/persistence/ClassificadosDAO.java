package br.com.condominiosvirtuais.persistence;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import br.com.condominiosvirtuais.entity.Classificados;
import br.com.condominiosvirtuais.entity.Condominio;

public interface ClassificadosDAO {
	
	public abstract List<Classificados> buscarPorCondominio(Condominio condominio) throws SQLException, Exception;
	
	public abstract List<Classificados> buscarPorCondominioETitulo(Condominio condominio, String titulo) throws SQLException, Exception;
	
	public abstract List<Classificados> buscarPorMaiorIgualDataExpira(Date dataExpira) throws SQLException, Exception;
	
	public abstract List<Classificados> buscarPorDataExpira(Date dataExpira) throws SQLException, Exception;
	
	public abstract Classificados  buscarPorIdClassificadosSemImagem(Integer idClassificado,  Connection con) throws SQLException, Exception;
	
	public abstract void salvar(Classificados classificados) throws SQLException, Exception;
	
	public abstract void excluir(Classificados classificados) throws SQLException, Exception;
	
	public abstract void atualizar(Classificados classificados) throws SQLException, Exception;

}
