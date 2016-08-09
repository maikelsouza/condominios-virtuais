package br.com.condominiosvirtuais.service.impl;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

import javax.inject.Inject;

import br.com.condominiosvirtuais.entity.Garagem;
import br.com.condominiosvirtuais.exception.BusinessException;
import br.com.condominiosvirtuais.persistence.GaragemDAO;
import br.com.condominiosvirtuais.service.GaragemService;

public class GaragemServiceImpl implements GaragemService, Serializable {	
	
	private static final long serialVersionUID = 1L;
	
	@Inject
	private GaragemDAO garagemDAO;	
		
	@Override
	public void salvar(Garagem garagem) throws SQLException, Exception {
		this.garagemDAO.salvar(garagem);

	}

	@Override
	public void excluir(Garagem garagem) throws SQLException,BusinessException, Exception {
		this.garagemDAO.excluir(garagem);

	}

	@Override
	public void atualizar(Garagem garagem) throws SQLException,BusinessException,  Exception {
		this.garagemDAO.atualizar(garagem);

	}

	@Override
	public List<Garagem> buscarPorIdUnidade(Integer idUnidade) throws SQLException, Exception {		
		return this.garagemDAO.buscarPorIdUnidade(idUnidade);
	}

	@Override
	public List<Garagem> buscarPorIdCondominoENumero(Integer idCondominio, String numero) throws SQLException, Exception {
		return this.garagemDAO.buscarPorIdCondominioENumero(idCondominio, numero);
	}

}
