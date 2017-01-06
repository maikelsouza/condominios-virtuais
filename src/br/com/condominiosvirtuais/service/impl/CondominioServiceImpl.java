package br.com.condominiosvirtuais.service.impl;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;
import java.util.Random;

import javax.inject.Inject;

import br.com.condominiosvirtuais.entity.Condominio;
import br.com.condominiosvirtuais.entity.Condomino;
import br.com.condominiosvirtuais.entity.Endereco;
import br.com.condominiosvirtuais.persistence.impl.CondominioDAOImpl;
import br.com.condominiosvirtuais.service.CondominioService;
import br.com.condominiosvirtuais.service.EnderecoService;

public class CondominioServiceImpl implements CondominioService, Serializable{
	
	private static final long serialVersionUID = 1L;

	@Inject
	private EnderecoService enderecoService = null;
	
	@Inject
	private CondominioDAOImpl condominioDAO = null;
		
	
	@Override
	public void associarEndereco(Condominio condominio) throws SQLException, Exception{		
		Endereco endereco = this.enderecoService.buscaPorIdCondominio(condominio.getId());
		condominio.setEndereco(endereco);
	}	
	
	/**
	 * Método que popula os bloco, unidades e condôninos de um condomínio. 
	 * 
	 * @param condominio
	 * @throws Exception 
	 */
	@Override
	public void popularCondominos(Condominio condominio) throws SQLException, Exception{
		this.condominioDAO.popularCondominio(condominio);
	}

	@Override
	public void salvar(Condominio condominio) throws SQLException, Exception {
		Random rn = new Random();
		int codigo = rn.nextInt(10000);
		while (this.condominioDAO.buscarPorCodigo(codigo) != null){
			codigo = rn.nextInt(10000);
		}
		condominio.setCodigo(codigo);
		this.condominioDAO.salvarCondominio(condominio);
	}

	@Override
	public void excluir(Condominio condominio) throws SQLException, Exception {
		this.condominioDAO.excluirCondominio(condominio);		
	}

	@Override
	public void atualizar(Condominio condominio) throws SQLException, Exception {
		this.condominioDAO.atualizarCondominio(condominio);				
	}
	
	@Override
	public List<Condominio> buscarTodos() throws SQLException, Exception {
		return this.condominioDAO.buscarListaCondominios();	
	}

	@Override
	public List<Condominio> buscarPorNomeESituacao(Condominio condominio) throws SQLException, Exception{
		return this.condominioDAO.buscarListaCondominiosPorNomeESituacao(condominio);
	}

	@Override
	public Condominio buscarPorId(Integer idCondominio) throws SQLException, Exception {
		return this.condominioDAO.buscarCondominioPorId(idCondominio);
	}

	@Override
	public void popularPorNomeCondominos(String nomeCondominos,	Condominio condominio) throws SQLException, Exception {
		this.condominioDAO.popularCondominioPorNomeCondominos(nomeCondominos, condominio);		
	}

	@Override
	public Condominio buscarPorCondomino(Condomino condomino) throws SQLException, Exception {		
		return this.condominioDAO.buscarPorCondomino(condomino);
	}
	
// TODO: Código comentado em 24/10/2016. Apagar em 90 dias	
//	@Override
//	public List<Condominio> buscarPorSindicoProfissional(SindicoProfissional sindicoProfissional) throws SQLException, Exception {		
//		return this.condominioDAO.buscarPorSindicoProfissional(sindicoProfissional);
//	}
	
	@Override
	public Condominio buscarPorCodigo(Integer codigo) throws SQLException, Exception {
		return  this.condominioDAO.buscarPorCodigo(codigo);		
	}	

	public EnderecoService getEnderecoService() {
		return enderecoService;
	}

	public void setEnderecoService(EnderecoService enderecoService) {
		this.enderecoService = enderecoService;
	}

	public CondominioDAOImpl getCondominioDAO() {
		return condominioDAO;
	}

	public void setCondominioDAO(CondominioDAOImpl condominioDAO) {
		this.condominioDAO = condominioDAO;
	}

	@Override
	public void popularBlocoEUnidadeDoCondominio(Condominio condominio) throws SQLException, Exception {
		this.condominioDAO.popularBlocoEUnidadeDoCondominio(condominio);		
	}

	@Override
	public List<Condominio> buscarPorSituacaoESemGestores(Integer situacao) throws SQLException, Exception {
		return this.condominioDAO.buscarPorSituacaoESemGestores(situacao);
	}

	@Override
	public List<Condominio> buscarPorSituacao(Integer situacao) throws SQLException, Exception {
		return this.condominioDAO.buscarPorSituacao(situacao);
	}

	@Override
	public List<Condominio> buscarPorIdEscritorioContabilidade(Integer idEscritorioContabilidade)
			throws SQLException, Exception {		
		return this.condominioDAO.buscarPorIdEscritorioContabilidade(idEscritorioContabilidade);
	}	

}
