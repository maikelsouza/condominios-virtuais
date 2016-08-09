package br.com.condominiosvirtuais.service.impl;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

import javax.inject.Inject;

import br.com.condominiosvirtuais.cdi.qualifier.QualifierAmbienteDAO;
import br.com.condominiosvirtuais.entity.Ambiente;
import br.com.condominiosvirtuais.entity.Bloco;
import br.com.condominiosvirtuais.entity.Condominio;
import br.com.condominiosvirtuais.entity.Condomino;
import br.com.condominiosvirtuais.exception.BusinessException;
import br.com.condominiosvirtuais.persistence.AmbienteDAO;
import br.com.condominiosvirtuais.service.AmbienteService;

public class AmbienteServiceImpl implements AmbienteService, Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject @QualifierAmbienteDAO
	private AmbienteDAO ambienteDAO = null;	
		
	public void salvar(Ambiente ambiente) throws SQLException, Exception {
		this.ambienteDAO.salvar(ambiente);	
	}
	
	public void excluir(Ambiente ambiente) throws SQLException, BusinessException, Exception {
		this.ambienteDAO.excluir(ambiente);		
	}
	
	public void atualizar(Ambiente ambiente) throws SQLException, Exception {
		this.ambienteDAO.atualizar(ambiente);		
	}
	
	public Ambiente buscarPorId(Integer idAmbiente) throws SQLException, Exception {
		return this.ambienteDAO.buscarPorId(idAmbiente);
	}

	public List<Ambiente> buscarPorCondominioENomeAmbiente(Condominio condominio, String nomeAmbiente) throws SQLException, Exception {		
		return this.ambienteDAO.buscarPorCondominioENomeAmbiente(condominio, nomeAmbiente);
	}
	
	public List<Ambiente> buscarPorBlocoENomeAmbiente(Bloco bloco, String nomeAmbiente) throws SQLException, Exception {
		return this.ambienteDAO.buscarPorBlocoENomeAmbiente(bloco, nomeAmbiente);		
	}	
	
	public List<Ambiente> buscarPorIdConjuntoBloco(Integer idConjuntoBloco) throws SQLException, Exception {		
		return this.ambienteDAO.buscarPorIdConjuntoBloco(idConjuntoBloco);	
	}

	public List<Ambiente> buscarPorCondomino(Condomino condomino) throws SQLException, Exception {
		return this.ambienteDAO.buscarPorCondomino(condomino);
	}

}
