package br.com.condominiosvirtuais.service.impl;

import java.io.Serializable;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.inject.Inject;

import br.com.condominiosvirtuais.entity.Condominio;
import br.com.condominiosvirtuais.entity.Email;
import br.com.condominiosvirtuais.entity.Enquete;
import br.com.condominiosvirtuais.exception.BusinessException;
import br.com.condominiosvirtuais.persistence.EnqueteDAO;
import br.com.condominiosvirtuais.service.CondominoService;
import br.com.condominiosvirtuais.service.EmailService;
import br.com.condominiosvirtuais.service.EnqueteService;
import br.com.condominiosvirtuais.util.AplicacaoUtil;
import br.com.condominiosvirtuais.vo.CondominoVO;

public class EnqueteServiceImpl implements EnqueteService, Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private EnqueteDAO enqueteDAO;
	
	@Inject
	private CondominoService condominoService;
	
	@Inject 
	private EmailService emailService;
	
	
	@Override
	public void salvar(Enquete enquete) throws SQLException, Exception {
		this.enqueteDAO.salvar(enquete);		
	}

	@Override
	public List<Enquete> buscarPorIdCondominioEPergunta(Integer idCondominio, String pergunta) throws SQLException, Exception {		
		return this.enqueteDAO.buscarPorIdCondominioEPergunta(idCondominio, pergunta);
	}

	@Override
	public List<Enquete> buscarPorIdCondominio(Integer idCondoninio) throws SQLException, Exception {
		return this.enqueteDAO.buscarPorIdCondominio(idCondoninio);
	}

	@Override
	public void excluir(Enquete enquete) throws SQLException, BusinessException,  Exception {
		this.enqueteDAO.excluir(enquete);		
	}

	@Override
	public List<Enquete> buscarPorIdCondominioNaoFinalizou(Integer idCondominio) throws SQLException, Exception {
		return this.enqueteDAO.buscarPorIdCondominioNaoFinalizou(idCondominio);		
	}

	public EnqueteDAO getEnqueteDAO() {
		return enqueteDAO;
	}

	public void setEnqueteDAO(EnqueteDAO enqueteDAO) {
		this.enqueteDAO = enqueteDAO;
	}

	@Override
	public void enviarEmailCondominosEnqueteCriada(Enquete enquete) throws SQLException, Exception {
		List<CondominoVO> listaDeCondominosVOs = null;
		Email email = null;
		Condominio condominio = new Condominio();
		condominio.setId(enquete.getIdCondominio());
		listaDeCondominosVOs = condominoService.buscarListaCondominosVOPorCondominioBlocoEUnidade(condominio,null,null);
		for (CondominoVO condominoVO : listaDeCondominosVOs) {	
			email = new Email();
			email.setPara(condominoVO.getEmailCondomino());
			email.setAssunto(AplicacaoUtil.i18n("msg.cadastroEnquete.assunto"));
			Object[] parametros = new Object[3];
			parametros[0] = condominoVO.getNomeCondomino().toUpperCase();
			parametros[1] = enquete.getPergunta().toUpperCase();
			SimpleDateFormat formato = new SimpleDateFormat(AplicacaoUtil.i18n("formatoData"));  
			parametros[2] = formato.format(enquete.getDataFim());					
			email.setMensagem(AplicacaoUtil.i18n("msg.cadastroEnquete.mensagem",parametros));
			this.emailService.salvar(email);			
		}
		
	}

}
