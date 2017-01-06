package br.com.condominiosvirtuais.service.impl;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

import javax.inject.Inject;

import br.com.condominiosvirtuais.entity.Contador;
import br.com.condominiosvirtuais.persistence.ContadorDAO;
import br.com.condominiosvirtuais.service.CondominioService;
import br.com.condominiosvirtuais.service.ContadorService;

public class ContadorServiceImpl implements Serializable, ContadorService {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private CondominioService condominioService;
	
	@Inject
	private ContadorDAO contadorDAO;

	@Override
	public void salvar(Contador contador) throws SQLException, Exception {
		contador.setListaCondominio(this.condominioService.buscarPorIdEscritorioContabilidade(contador.getIdEscritorioContabilidade()));
		this.contadorDAO.salvar(contador);		
	}

	@Override
	public List<Contador> buscarPorIdEscritorioContabilidadeESituacao(Integer idEscritorioContabilidade, Integer situacao) throws SQLException, Exception {
		return this.contadorDAO.buscarPorIdEscritorioContabilidadeESituacao(idEscritorioContabilidade, situacao);
	}

	@Override
	public void atualizar(Contador contador) throws SQLException, Exception {
		this.contadorDAO.atualizar(contador);		
	}
	
	

}