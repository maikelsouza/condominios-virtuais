package br.com.condominiosvirtuais.service;

import java.sql.SQLException;
import java.util.List;

import br.com.condominiosvirtuais.entity.Enquete;
import br.com.condominiosvirtuais.exception.BusinessException;

public interface EnqueteService {
	
	public abstract void salvar(Enquete enquete) throws SQLException, Exception;
	
	public abstract void excluir(Enquete enquete) throws SQLException, BusinessException, Exception;
	
	public abstract List<Enquete> buscarPorIdCondominioEPergunta(Integer idCondominio,  String pergunta) throws SQLException, Exception;
	
	public abstract List<Enquete> buscarPorIdCondominio(Integer idCondominio) throws SQLException, Exception;
	
	public abstract List<Enquete> buscarPorIdCondominioNaoFinalizou(Integer idCondominio) throws SQLException, Exception;
	
	public abstract void enviarEmailCondominosEnqueteCriada(Enquete enquete) throws SQLException, Exception;

}
