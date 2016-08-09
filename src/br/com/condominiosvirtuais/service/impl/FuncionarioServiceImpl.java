package br.com.condominiosvirtuais.service.impl;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

import javax.inject.Inject;

import br.com.condominiosvirtuais.cdi.qualifier.QualifierFuncionarioDAO;
import br.com.condominiosvirtuais.entity.Bloco;
import br.com.condominiosvirtuais.entity.Condominio;
import br.com.condominiosvirtuais.entity.Email;
import br.com.condominiosvirtuais.entity.Funcionario;
import br.com.condominiosvirtuais.persistence.FuncionarioDAO;
import br.com.condominiosvirtuais.service.EmailService;
import br.com.condominiosvirtuais.service.FuncionarioService;
import br.com.condominiosvirtuais.util.AplicacaoUtil;

public class FuncionarioServiceImpl implements FuncionarioService, Serializable {

	
	private static final long serialVersionUID = 1L;

	@Inject @QualifierFuncionarioDAO
	private FuncionarioDAO funcionarioDAO = null;
	 
	@Inject 
	private EmailService emailService = null;
	

	public void salvar(Funcionario funcionario) throws SQLException, Exception {
		this.funcionarioDAO.salvar(funcionario);		
		Email email = new Email();			
		email.setPara(funcionario.getEmail().getEmail());
		email.setAssunto(AplicacaoUtil.i18n("msg.cadastroFuncionario.assunto"));
		Object[] parametros = new Object[3];
		parametros[0] = funcionario.getNome().toUpperCase();
		parametros[1] = funcionario.getEmail().getEmail();
		parametros[2] = funcionario.getSenha();
		email.setMensagem(AplicacaoUtil.i18n("msg.cadastroFuncionario.mensagem", parametros));
		this.emailService.salvar(email);
	}
	
	public List<Funcionario> buscarPorCondominioENomeFuncionario(Condominio condominio, String nomeFuncionario) throws SQLException, Exception {		
		return this.funcionarioDAO.buscarPorCondominioENomeFuncionario(condominio,nomeFuncionario);
	}
	
	public void excluir(Funcionario funcionario) throws SQLException, Exception {
		this.funcionarioDAO.excluir(funcionario);		
	}
	
	public void atualizar(Funcionario funcionario) throws SQLException, Exception {
		this.funcionarioDAO.atualizar(funcionario);		
	}
	
	public List<Funcionario> buscarPorBlocoENomeFuncionario(Bloco bloco, String nome) throws SQLException, Exception {		
		return this.funcionarioDAO.buscarPorBlocoENomeFuncionario(bloco,nome);
	}
	
	public List<Funcionario> buscarPorIdConjuntoBloco(Integer idConjuntoBloco) throws SQLException, Exception {		
		return this.funcionarioDAO.buscarPorIdConjuntoBloco(idConjuntoBloco);
	}

	public Funcionario buscarPorId(Integer id) throws SQLException, Exception {		
		return this.funcionarioDAO.buscarPorId(id);
	}

	public FuncionarioDAO getFuncionarioDAO() {
		return funcionarioDAO;
	}

	public void setFuncionarioDAO(FuncionarioDAO funcionarioDAO) {
		this.funcionarioDAO = funcionarioDAO;
	}

	public EmailService getEmailService() {
		return emailService;
	}

	public void setEmailService(EmailService emailService) {
		this.emailService = emailService;
	}

	@Override
	public void atualizarSenha(Funcionario funcionario) throws SQLException,
			Exception {
		this.funcionarioDAO.atualizarSenha(funcionario);	
		
	}

}
