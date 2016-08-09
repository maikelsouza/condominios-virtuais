package br.com.condominiosvirtuais.service.impl;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

import javax.inject.Inject;

import br.com.condominiosvirtuais.entity.Chamado;
import br.com.condominiosvirtuais.entity.Condominio;
import br.com.condominiosvirtuais.entity.Condomino;
import br.com.condominiosvirtuais.entity.Email;
import br.com.condominiosvirtuais.entity.Usuario;
import br.com.condominiosvirtuais.persistence.impl.ChamadoDAOImpl;
import br.com.condominiosvirtuais.service.ChamadoService;
import br.com.condominiosvirtuais.util.AplicacaoUtil;
import br.com.condominiosvirtuais.util.MensagensEmailUtil;

public class ChamadoServiceImpl implements ChamadoService, Serializable {
	
	
	private static final long serialVersionUID = 1L;
	
	@Inject
	private ChamadoDAOImpl chamadoDAOImpl;
	
	@Inject
	private EmailServiceImpl emailService;	

	@Inject
	private CondominoServiceImpl condominoService;
	
	@Inject
	private UsuarioServiceImpl usuarioService;

	@Override
	public void salvar(Chamado chamado) throws SQLException, Exception {
		this.chamadoDAOImpl.salvar(chamado);		
		Email email = new Email();		
		Condomino sindicoGeral = this.condominoService.buscarSindicoGeralPorCondominio(chamado.getCondominio());
		email.setPara(sindicoGeral.getEmail().getEmail());
		email.setAssunto(AplicacaoUtil.i18n("msg.chamado.abertura.assunto"));
// TODO Código comentado em 05/08/20016. Apagar em 90 dias		
//		Object[] parametros = new Object[2];
//		parametros[0] = sindicoGeral.getNome();
//		parametros[1] = chamado.getNome();
//		email.setMensagem(AplicacaoUtil.i18n("msg.aberturaChamado.mensagem", parametros));
		email.setMensagem(MensagensEmailUtil.aberturaChamado(chamado, sindicoGeral.getNome()));
		this.emailService.salvar(email);
	}

	@Override
	public void excluir(Integer idChamado) throws SQLException, Exception {
		this.chamadoDAOImpl.excluir(idChamado);		
	}

	@Override
	public List<Chamado> buscarPorCondominioEStatus(Condominio condominio, Integer status) throws SQLException, Exception {
		return this.chamadoDAOImpl.buscarPorCondominioEStatus(condominio, status);
	}

	@Override
	public List<Chamado> buscarPorUsuario(Integer idChamado) throws SQLException, Exception {
		return this.chamadoDAOImpl.buscarPorUsuario(idChamado);
	}

	@Override
	public List<Chamado> buscarPorCondominio(Condominio condominio) throws SQLException, Exception {
		return this.chamadoDAOImpl.buscarPorCondominio(condominio);
	}

	@Override
	public void atualizarStatus(Integer idChamado, Integer status) throws SQLException, Exception {
		this.chamadoDAOImpl.atualizarStatus(idChamado, status);	
	}

	@Override
	public void fecharChamado(Chamado chamado) throws SQLException, Exception {
		this.chamadoDAOImpl.fecharChamado(chamado.getId(), chamado.getDataFechamento(), chamado.getComentario());	
		Email email = new Email();		
		Usuario usuario = this.usuarioService.buscarPorId(chamado.getUsuario().getId());		
		email.setPara(usuario.getEmail().getEmail());
		email.setAssunto(AplicacaoUtil.i18n("msg.chamado.fechamento.assunto"));
//		TODO Código comentado em 05/08/20016. Apagar em 90 dias	
		//Object[] parametros = new Object[3];
		//parametros[0] = usuario.getNome();
		//parametros[1] = chamado.getNome();
		//parametros[2] = chamado.getComentario();
		if(chamado.getComentario().trim().equals("")){
			email.setMensagem(MensagensEmailUtil.fechamentoChamado(chamado, usuario.getNome()));			
		}else{
			email.setMensagem(MensagensEmailUtil.fechamentoChamadoComObservacao(chamado, usuario.getNome()));
		}
		this.emailService.salvar(email);
	}

}
