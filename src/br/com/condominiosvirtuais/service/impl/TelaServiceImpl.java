package br.com.condominiosvirtuais.service.impl;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import br.com.condominiosvirtuais.entity.Tela;
import br.com.condominiosvirtuais.persistence.TelaDAO;
import br.com.condominiosvirtuais.service.TelaService;

public class TelaServiceImpl implements TelaService, Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Inject
	private TelaDAO telaDAO;

	/**
	 * Busca uma lista de telas associadas da um grupo de usuário
	 */
	@Override
	public List<Tela> buscarPorIdGrupoUsuario(Integer idGrupoUsuario) throws SQLException, Exception {
		// Ordenação realizada no código, pois o dado que é persistido no banco é a chave para o I18N
		List<Tela> listTela = this.telaDAO.buscarPorIdGrupoUsuario(idGrupoUsuario);
		Collections.sort(listTela);
		return listTela;
	}
	
	

}
