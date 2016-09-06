package br.com.condominiosvirtuais.service.impl;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

import javax.inject.Inject;

import br.com.condominiosvirtuais.entity.ResponsavelObra;
import br.com.condominiosvirtuais.enumeration.ResponsavelObraTipoPessoaEnum;
import br.com.condominiosvirtuais.persistence.impl.ResponsavelObraDAOImpl;
import br.com.condominiosvirtuais.service.ResponsavelObraService;

public class ResponsavelObraServiceImpl implements ResponsavelObraService, Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private ResponsavelObraDAOImpl responsavelObraDAO;

	@Override
	public List<ResponsavelObra> buscarPorNomeETipoPessoa(String nome, Integer tipoPessoa) throws SQLException, Exception {
		if(tipoPessoa == ResponsavelObraTipoPessoaEnum.PESSOA_FISICA.getTipoPessoa()){
			return this.responsavelObraDAO.buscarPorNome(nome);			
		}else{
			return this.responsavelObraDAO.buscarPorRazaoSocial(nome);
		}
	}

}
