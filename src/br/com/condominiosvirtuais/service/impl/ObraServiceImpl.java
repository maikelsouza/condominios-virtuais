package br.com.condominiosvirtuais.service.impl;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import br.com.condominiosvirtuais.entity.Obra;
import br.com.condominiosvirtuais.persistence.impl.ObraDAOImpl;
import br.com.condominiosvirtuais.service.ObraService;

public class ObraServiceImpl implements ObraService, Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private ObraDAOImpl obraDao;

	@Override
	public void salvarObraESalvaResponsavelObra(Obra obra) throws SQLException, Exception {
		this.obraDao.salvarObraESalvaResponsavelObra(obra);	
	}

	@Override
	public void salvarObraEAtualizarResponsavel(Obra obra) throws SQLException, Exception {
		this.obraDao.salvarObraEAtualizarResponsavel(obra);
	}

	@Override
	public List<Obra> buscarPorCondominioEPeriodoDeAte(Integer idCondominio, Date dataInicioDe, Date dataInicioAte) throws SQLException, Exception {		
		return this.obraDao.buscarPorCondominioEPeriodoDeAte(idCondominio, dataInicioDe, dataInicioAte);
	}

	@Override
	public void excluir(Integer idObra) throws SQLException, Exception {
		this.obraDao.excluir(idObra);		
	}

	@Override
	public void atualizarObraEAtualizarResponsavelObra(Obra obra) throws SQLException, Exception {
		this.obraDao.atualizarObraEAtualizarResponsavelObra(obra);		
	}

	@Override
	public void atualizarObraESalvarResponsavelObra(Obra obra) throws SQLException, Exception {
		this.obraDao.atualizarObraESalvarResponsavelObra(obra);		
	}

	

}
