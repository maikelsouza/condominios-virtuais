package br.com.condominiosvirtuais.service.impl;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

import javax.inject.Inject;

import br.com.condominiosvirtuais.cdi.qualifier.QualifierAmbienteDAO;
import br.com.condominiosvirtuais.cdi.qualifier.QualifierFuncionarioDAO;
import br.com.condominiosvirtuais.entity.ConjuntoBloco;
import br.com.condominiosvirtuais.enumeration.TipoConjuntoBlocoEnum;
import br.com.condominiosvirtuais.exception.BusinessException;
import br.com.condominiosvirtuais.persistence.ConjuntoBlocoDAO;
import br.com.condominiosvirtuais.service.ConjuntoBlocoService;

public class ConjuntoBlocoServiceImpl implements ConjuntoBlocoService, Serializable {
	
	private static final long serialVersionUID = 1L;

	
	@Inject @QualifierFuncionarioDAO
	private ConjuntoBlocoDAO conjuntoBlocoDAOFuncionario = null;
	
	@Inject  @QualifierAmbienteDAO
	private ConjuntoBlocoDAO conjuntoBlocoDAOAmbiente = null;
	
	private  ConjuntoBlocoDAO conjuntoBlocoDAO = null;
	
	
	// TODO: Solução encontrada para setar o tipo de conjunto bloco. Rever solução mais elegante
	public void configuraTipoConjuntoBloco(Integer tipoConjuntoBloco){
		if(tipoConjuntoBloco == TipoConjuntoBlocoEnum.AMBIENTE.getConjuntoBloco()){
			conjuntoBlocoDAO = conjuntoBlocoDAOAmbiente;				
		}else if(tipoConjuntoBloco == TipoConjuntoBlocoEnum.FUNCIONARIO.getConjuntoBloco()){
			conjuntoBlocoDAO = conjuntoBlocoDAOFuncionario; 
		}
	}
	

	public void salvar(ConjuntoBloco conjuntoBloco) throws SQLException, Exception {
		conjuntoBlocoDAO.salvar(conjuntoBloco);		
	}

	public ConjuntoBloco buscarPorId(Integer id) throws SQLException, Exception {		
		return conjuntoBlocoDAO.buscarPorId(id);
	}

	public void excluir(ConjuntoBloco conjuntoBloco) throws SQLException, BusinessException, Exception {
		conjuntoBlocoDAO.excluir(conjuntoBloco);		
	}

	public void atualizar(ConjuntoBloco conjuntoBloco) throws SQLException, Exception {
		conjuntoBlocoDAO.atualizar(conjuntoBloco);		
	}
	
	public List<ConjuntoBloco> buscarPorTipo(Integer tipo) throws SQLException, Exception {		
		return conjuntoBlocoDAO.buscarPorTipo(tipo);
	}

}
