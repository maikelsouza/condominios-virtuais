package br.com.condominiosvirtuais.service.impl;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import br.com.condominiosvirtuais.entity.Bloco;
import br.com.condominiosvirtuais.entity.Condominio;
import br.com.condominiosvirtuais.entity.Unidade;
import br.com.condominiosvirtuais.exception.BusinessException;
import br.com.condominiosvirtuais.persistence.impl.UnidadeDAOImpl;
import br.com.condominiosvirtuais.service.UnidadeService;
import br.com.condominiosvirtuais.vo.UnidadeVO;

public class UnidadeServiceImpl implements UnidadeService, Serializable {
	
	private static final long serialVersionUID = 1L;

	@Inject
	private UnidadeDAOImpl unidadeDAO = null;
	
	@Inject
	private BlocoServiceImpl blocoBusiness = null;
	
	public List<Unidade> buscarListaUnidadesPorBloco(Bloco bloco) throws SQLException, Exception{
		return this.unidadeDAO.buscarListaUnidadesPorBloco(bloco);
	}
	
	public void excluirUnidade(Unidade unidade) throws SQLException, BusinessException, Exception{
		this.unidadeDAO.excluirUnidade(unidade);
	}
	
	public void salvarUnidade(Unidade unidade) throws SQLException, BusinessException,Exception{
		this.unidadeDAO.salvarUnidade(unidade);
	}
	
	public void salvarUnidadeEmLote(List<Unidade> listaUnidades) throws SQLException, BusinessException, Exception{
		this.unidadeDAO.salvarUnidadeEmLote(listaUnidades);
	}
	
	public void atualizarUnidade(Unidade unidade) throws SQLException, BusinessException, Exception{
		this.unidadeDAO.atualizarUnidade(unidade);
	}
	
	/**
	 * M�todo que busca uma lista de unidades por condom�nio, tipo e bloco, sendo esse �ltimo n�o obrigat�rio. Caso o id do bloco seja 0 <br>
	 * o m�todo retornar� todas as unidades do condom�nio e tipo selecionados.
	 * 
	 * @param condominio - Condom�nio a ser pesquisado (Obrigat�rio)
	 * @param tipo - Tipo (apartamento, loja e ambos)  a ser pesquisado (Obrigat�rio)
	 * @param bloco - Bloco a ser pesquisado (Opcional))
	 * @return Lista de UnidadesVO
	 * @throws Exception 
	 */
	public List<UnidadeVO> buscarListaUnidadesPorCondominioTipoEBloco(Condominio condominio, Integer tipo, Bloco bloco) throws SQLException, Exception{
		List<UnidadeVO> listaDeUnidadeVO = new ArrayList<UnidadeVO>();
		List<Unidade> listaDeUnidades = null;
		UnidadeVO unidadeVO = null;
		// Situa��o onde o bloco n�o foi selecionado, ou seja, id = 0
		if(bloco.getId() == 0){
			List<Bloco> listaDeBlocos = null;			
			// Recupera-se os blocos de um condom�nio
			listaDeBlocos = this.blocoBusiness.buscarPorCondominioENome(condominio,null);
			for (Bloco blocoLocal : listaDeBlocos) {
				listaDeUnidades = this.unidadeDAO.buscarListaUnidadesPorBlocoETipo(blocoLocal, tipo);
				for (Unidade unidade : listaDeUnidades) {
					unidadeVO = new UnidadeVO();
					unidadeVO.setIdCondominio(condominio.getId());
					unidadeVO.setIdBloco(blocoLocal.getId());
					unidadeVO.setId(unidade.getId());
					unidadeVO.setNomeCondominio(condominio.getNome());
					unidadeVO.setNomeBloco(blocoLocal.getNome());
					unidadeVO.setNumeroUnidade(unidade.getNumero());
					unidadeVO.setTipoUnidade(unidade.getTipo());
					listaDeUnidadeVO.add(unidadeVO);
				}				
			}
		}else{
			listaDeUnidades = this.unidadeDAO.buscarListaUnidadesPorBlocoETipo(bloco, tipo);
			for (Unidade unidade : listaDeUnidades) {
				unidadeVO = new UnidadeVO(); 
				unidadeVO.setIdCondominio(condominio.getId());
				unidadeVO.setIdBloco(bloco.getId());
				unidadeVO.setId(unidade.getId());
				unidadeVO.setNomeCondominio(condominio.getNome());
				unidadeVO.setNomeBloco(bloco.getNome());
				unidadeVO.setNumeroUnidade(unidade.getNumero());
				unidadeVO.setTipoUnidade(unidade.getTipo());
				listaDeUnidadeVO.add(unidadeVO);
			}		
		}
		return listaDeUnidadeVO;
	}

	public Unidade buscarPorId(Integer idUnidade) throws SQLException, Exception {	
		return 	this.unidadeDAO.buscarPorId(idUnidade);
	}

	public UnidadeDAOImpl getUnidadeDAO() {
		return unidadeDAO;
	}

	public void setUnidadeDAO(UnidadeDAOImpl unidadeDAO) {
		this.unidadeDAO = unidadeDAO;
	}

	public BlocoServiceImpl getBlocoBusiness() {
		return blocoBusiness;
	}

	public void setBlocoBusiness(BlocoServiceImpl blocoBusiness) {
		this.blocoBusiness = blocoBusiness;
	}

	@Override
	public List<Integer> buscarListaIdsUnidadesPorIdBloco(Integer idBloco) throws SQLException, Exception {		
		return this.unidadeDAO.buscarListaIdsUnidadesPorIdBloco(idBloco);
	}

}
