package br.com.condominiosvirtuais.service.impl;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

import javax.inject.Inject;

import br.com.condominiosvirtuais.entity.Bloco;
import br.com.condominiosvirtuais.entity.Condominio;
import br.com.condominiosvirtuais.exception.BusinessException;
import br.com.condominiosvirtuais.persistence.impl.BlocoDAOImpl;
import br.com.condominiosvirtuais.service.BlocoService;

public class BlocoServiceImpl implements BlocoService, Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Inject
	private BlocoDAOImpl blocoDAO = null;	

	
	public List<Bloco> buscarPorCondominioENome(Condominio condominio, String nomeBloco) throws SQLException, Exception{
		return this.blocoDAO.buscarListaBlocosPorCondominioENome(condominio,nomeBloco);
	}	
		
	public void salvar(Bloco bloco) throws SQLException, BusinessException, Exception{
		this.blocoDAO.salvarBloco(bloco);
	}
	
	public void salvarEmLote(List<Bloco> listaBlocos) throws SQLException, Exception{
		this.blocoDAO.salvarBlocoEmLote(listaBlocos);
	}	
	
	public void atualizar(Bloco bloco) throws SQLException, BusinessException, Exception{
		this.blocoDAO.atualizarBloco(bloco);
	}
	
	public void excluir(Bloco bloco) throws SQLException, BusinessException, Exception{
		this.blocoDAO.excluirBloco(bloco);
	}
	
	public void popularPorNomeCondominos(String nomeCondominos,	Bloco bloco) throws SQLException, Exception {
		this.blocoDAO.popularBlocoPorNomeCondominos(nomeCondominos, bloco);		
	}
	
	public Bloco buscarPorId(Integer idBloco) throws SQLException, Exception {
		return this.blocoDAO.buscarPorId(idBloco);		
	}

	@Override
	public void popularBloco(Bloco bloco) throws SQLException, Exception {
		this.blocoDAO.popularBloco(bloco);		
	}

	@Override
	public List<Integer> buscarListaIdsBlocosPorIdCondominio(Integer idCondominio) throws SQLException, Exception {
		return blocoDAO.buscarListaIdsBlocosPorIdCondominio(idCondominio);
	}

}
