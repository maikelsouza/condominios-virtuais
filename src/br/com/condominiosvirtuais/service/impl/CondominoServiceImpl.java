package br.com.condominiosvirtuais.service.impl;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;

import br.com.condominiosvirtuais.entity.Bloco;
import br.com.condominiosvirtuais.entity.Condominio;
import br.com.condominiosvirtuais.entity.Condomino;
import br.com.condominiosvirtuais.entity.Email;
import br.com.condominiosvirtuais.entity.Unidade;
import br.com.condominiosvirtuais.enumeration.CondominoPagadorEnum;
import br.com.condominiosvirtuais.exception.BusinessException;
import br.com.condominiosvirtuais.persistence.impl.CondominoDAOImpl;
import br.com.condominiosvirtuais.service.BlocoService;
import br.com.condominiosvirtuais.service.CondominioService;
import br.com.condominiosvirtuais.service.CondominoService;
import br.com.condominiosvirtuais.service.EmailService;
import br.com.condominiosvirtuais.util.AplicacaoUtil;
import br.com.condominiosvirtuais.util.MensagensEmailUtil;
import br.com.condominiosvirtuais.vo.CondominoVO;

public class CondominoServiceImpl implements CondominoService, Serializable{	
	
	private static final long serialVersionUID = 1L;

	@Inject
	private CondominioService condominioService = null;	
	
	@Inject 
	private EmailService emailService = null;
	
	@Inject
	private BlocoService blocoService = null;
	
	@Inject
	private CondominoDAOImpl condominoDAO;
	
	
	public List<Condomino> buscarPorUnidade(Unidade unidade) throws SQLException, Exception{
		return  this.condominoDAO.buscarListaCondominosPorUnidade(unidade);		
	}
	

	public void salvar(Condomino condomino) throws SQLException, Exception{
		Boolean encontrou = Boolean.FALSE;
		condomino.setPagador(CondominoPagadorEnum.SIM.getPagador());
		Unidade unidade = new Unidade();
		unidade.setId(condomino.getIdUnidade());
		List<Condomino> listaCondomino = this.condominoDAO.buscarListaCondominosPorUnidade(unidade);
		Iterator<Condomino> iteratorCondomino = listaCondomino.iterator();
		while (iteratorCondomino.hasNext() && !encontrou) {
			Condomino condominoLocal = iteratorCondomino.next();
			// Condição necessária para garantir que não tenha mais de um pagador por unidade.
			if(condominoLocal.getPagador() == CondominoPagadorEnum.SIM.getPagador()){
				encontrou = Boolean.TRUE;
				condomino.setPagador(CondominoPagadorEnum.NAO.getPagador());
			}
			
		}		
		this.condominoDAO.salvarCondomino(condomino);
		Email email = new Email();			
		email.setPara(condomino.getEmail().getEmail());
		email.setAssunto(AplicacaoUtil.i18n("msg.cadastroCondomino.assunto"));	
		email.setMensagem(MensagensEmailUtil.confirmacaoDeCadastro(condomino));
		this.emailService.salvar(email);
	}
	
	public void atualizar(Condomino condomino) throws SQLException, Exception{
		this.condominoDAO.atualizarCondomino(condomino);
	}
		
	public List<CondominoVO> buscarListaCondominosVOPorIdCondominioEPagadorSemImagem(Integer idCondominio) throws SQLException, Exception{
		Condominio condominioLocal = new Condominio();
		CondominoVO condominoVO = null;		
		condominioLocal.setId(idCondominio);
		this.condominioService.popularCondominioComCondominoSemImagemEPagador(condominioLocal);
		List<CondominoVO> listaCondominoVO = new ArrayList<CondominoVO>();
		for (Bloco bloco : condominioLocal.getListaBlocos()) {
			for (Unidade unidade : bloco.getListaUnidade()) {
				for (Condomino condomino : unidade.getListaCondominos()) {
					condominoVO = new CondominoVO();
					condominoVO.setId(condomino.getId());
					condominoVO.setNomeBloco(bloco.getNome());
					condominoVO.setNumeroUnidade(unidade.getNumero());
					condominoVO.setNomeCondomino(condomino.getNome());
					condominoVO.setPagadorCondomino(condomino.getPagador());
					condominoVO.setIdCondomino(condomino.getId());
					listaCondominoVO.add(condominoVO);
				}
			}
		} 
		return listaCondominoVO;		
	}
	
	public List<CondominoVO> buscarListaCondominosVOPorNomeCondominoECondominio(String nomeCondomino, Condominio condominio) throws SQLException, Exception{
		Condominio condominioLocal = new Condominio();
		CondominoVO condominoVO = null;		
		condominioLocal.setId(condominio.getId());
		this.condominioService.popularPorNomeCondominos(nomeCondomino, condominioLocal);
		List<CondominoVO> listaCondominoVO = new ArrayList<CondominoVO>();
		for (Bloco bloco : condominioLocal.getListaBlocos()) {
			for (Unidade unidade : bloco.getListaUnidade()) {
				for (Condomino condomino : unidade.getListaCondominos()) {
					condominoVO = new CondominoVO();
					condominoVO.setId(condomino.getId());
					condominoVO.setNomeBloco(bloco.getNome());
					condominoVO.setNumeroUnidade(unidade.getNumero());
					condominoVO.setNomeCondomino(condomino.getNome());					
					listaCondominoVO.add(condominoVO);
				}
			}
		} 
		return listaCondominoVO;		
	}
	
	public List<CondominoVO> buscarListaCondominosVOPorNomeCondominoECondominio(String nomeCondomino, Condominio condominio, Bloco bloco) throws SQLException, Exception {
		Bloco blocoLocal = new Bloco();
		CondominoVO condominoVO = null;		
		blocoLocal.setId(bloco.getId());
		this.blocoService.popularPorNomeCondominos(nomeCondomino, blocoLocal);
		List<CondominoVO> listaCondominoVO = new ArrayList<CondominoVO>();
		for (Unidade unidade : blocoLocal.getListaUnidade()) {
			for (Condomino condomino : unidade.getListaCondominos()) {
				condominoVO = new CondominoVO();
				condominoVO.setId(condomino.getId());
				condominoVO.setNomeBloco(bloco.getNome());
				condominoVO.setNumeroUnidade(unidade.getNumero());
				condominoVO.setNomeCondomino(condomino.getNome());					
				listaCondominoVO.add(condominoVO);
			}
		}		 
		return listaCondominoVO;
	}

	
	/**
	 * Método que busca uma lista de condôminos por condomínio, bloco e unidade. Caso o id do bloco seja 0, <br>
	 * o método irá retornar todos os condôminos de um determinado condomínio. Caso o id do bloco seja diferente de 0, <br>
	 * o método irá retornar todos os condôminos de um determinado bloco e caso o id do bloco seja diferente 0 e o id da unidade também, <b>
	 * o método irá retornar todos os condôminos de um determinado bloco e uma determinada unidade.
	 * 
	 * @param condominio - Condomínio a ser pesquisado. (Obrigatório) 
	 * @param bloco - Bloco a ser pesquisado. (Opcional)
	 * @param unidade - Unidade a ser pesquisada (Opcional)
	 * @return lista de condominosVO
	 * @throws Exception 
	 */
	public List<CondominoVO> buscarListaCondominosVOPorCondominioBlocoEUnidade(Condominio condominio, Bloco bloco, Unidade unidade) throws SQLException, Exception{
		Condominio condominioLocal = new Condominio();
		condominioLocal.setId(condominio.getId());
		condominioLocal.setNome(condominio.getNome());
		this.condominioService.popularCondominos(condominioLocal);
		List<CondominoVO> listaCondominoVO = new ArrayList<CondominoVO>();		
		CondominoVO  condominoVO = null;
		// Situação onde não foi selecionado um bloco específico, logo também não foi selecionado uma unidade, portanto retorná todos os condôminos desse condomínio. (true - Não foi selecinado) 
		if(bloco == null || bloco.getId() == 0){
			for (Bloco blocoLocal : condominioLocal.getListaBlocos()) {
				for (Unidade unidadeLocal : blocoLocal.getListaUnidade()) {
					for (Condomino condomino : unidadeLocal.getListaCondominos()) {
						condominoVO = new CondominoVO();
						this.popularCondominoVO(condominioLocal, condominoVO,
								blocoLocal, unidadeLocal, condomino);
						listaCondominoVO.add(condominoVO);
					}
				}
			}
		// Situação onde foi selecionado um bloco específico, logo o algoritmo irá verificar qual bloco e se foi selecionado uma unidade específica.			
		}else{
			for (Bloco blocoLocal : condominioLocal.getListaBlocos()) {
				// Condição que garante que somente os condôminos de um determinado bloco serão retornados.
				if(blocoLocal.getId() == bloco.getId()){
					for (Unidade unidadeLocal : blocoLocal.getListaUnidade()) {
						// Situação onde foi selecionado uma unidade específica. (true - Foi selecinado) 
						if(unidade != null && unidade.getId() != 0){
							// Condição que garante que somente os condôminos de um determinado bloco e unidade serão retornados.
							if(unidadeLocal.getId() == unidade.getId()){
								for (Condomino condomino : unidadeLocal.getListaCondominos()) {
									condominoVO = new CondominoVO();
									this.popularCondominoVO(condominioLocal, condominoVO,
											blocoLocal, unidadeLocal, condomino);					
									listaCondominoVO.add(condominoVO);
								}						
							}
						// Situação onde não foi selecionado uma unidade específica.	
						}else{
							for (Condomino condomino : unidadeLocal.getListaCondominos()) {
								condominoVO = new CondominoVO();
								this.popularCondominoVO(condominioLocal, condominoVO,
										blocoLocal, unidadeLocal, condomino);					
								listaCondominoVO.add(condominoVO);
							}
						}
					}				
				}
			} 			
		}
		return listaCondominoVO;		
	}
	
	public void excluir(Condomino condomino) throws SQLException, BusinessException, Exception{
		this.condominoDAO.excluirCondomino(condomino);
	}

	@Override
	public List<Condomino> buscarConselheirosPorCondominio(Condominio condominio) throws SQLException, Exception {
		return this.condominoDAO.buscarConselheirosPorCondominio(condominio);		
	}
	
	@Override
	public Condomino buscarSindicoGeralPorCondominio(Condominio condominio) throws SQLException, Exception {
		return this.condominoDAO.buscarSindicoGeralPorCondominio(condominio);		
	}
	
	@Override
	public Condomino buscarSubSindicoGeralPorCondominio(Condominio condominio) throws SQLException, Exception {
		return this.condominoDAO.buscarSubSindicoGeralPorCondominio(condominio);		
	}

	public Condomino buscarSindicoPorBloco(Bloco bloco) throws SQLException, Exception {		
		return this.condominoDAO.buscarSindicoPorBloco(bloco);
	}

	public Condomino buscarSubSindicoPorBloco(Bloco bloco) throws SQLException, Exception {		
		return this.condominoDAO.buscarSubSindicoPorBloco(bloco);
	}

	public List<Condomino> buscarConselheirosPorBloco(Bloco bloco) throws SQLException, Exception {
		return this.condominoDAO.buscarConselheirosPorBloco(bloco);
	}
	
	public Condomino buscarPorId(Integer id) throws SQLException, Exception {
		return this.condominoDAO.buscarCondominoPorId(id);
	}
	
	public CondominioService getCondominioService() {
		return condominioService;
	}

	public void setCondominioService(CondominioService condominioService) {
		this.condominioService = condominioService;
	}

	private void popularCondominoVO(Condominio condominioLocal,
			CondominoVO condominoVO, Bloco blocoLocal, Unidade unidadeLocal,
			Condomino condomino) {
		condominoVO.setId(condomino.getId());
		condominoVO.setIdCondominio(condominioLocal.getId());
		condominoVO.setNomeCondominio(condominioLocal.getNome());
		condominoVO.setIdBloco(blocoLocal.getId());
		condominoVO.setNomeBloco(blocoLocal.getNome());
		condominoVO.setIdUnidade(unidadeLocal.getId());
		condominoVO.setNumeroUnidade(unidadeLocal.getNumero());
		condominoVO.setIdCondomino(condomino.getId());
		condominoVO.setNomeCondomino(condomino.getNome());	
		condominoVO.setDataNascimentoCondomino(condomino.getDataNascimento() != null ? condomino.getDataNascimento().getTime() : null);
		condominoVO.setSexoCondomino(condomino.getSexo());
		condominoVO.setArquivo(condomino.getImagem());
		condominoVO.setEmailCondomino(condomino.getEmail().getEmail());
		condominoVO.setIdEmailCondomino(condomino.getEmail().getId());
		condominoVO.setProprietarioCondomino(condomino.getProprietario());
		condominoVO.setCpfCondomino(condomino.getCpf());
		condominoVO.setTelefoneCelularCondomino(condomino.getTelefoneCelular());
		condominoVO.setTelefoneComercialCondomino(condomino.getTelefoneComercial());
		condominoVO.setTelefoneResidencialCondomino(condomino.getTelefoneResidencial());
		condominoVO.setSituacaoCondomino(condomino.getSituacao());
		condominoVO.setIdGrupoUsuario(condomino.getIdGrupoUsuario());		
	}
	
	
}
