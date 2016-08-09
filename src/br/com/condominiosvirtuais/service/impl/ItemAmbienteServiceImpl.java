package br.com.condominiosvirtuais.service.impl;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

import javax.inject.Inject;

import br.com.condominiosvirtuais.entity.Ambiente;
import br.com.condominiosvirtuais.entity.ItemAmbiente;
import br.com.condominiosvirtuais.persistence.ItemAmbienteDAO;
import br.com.condominiosvirtuais.service.ItemAmbienteService;

public class ItemAmbienteServiceImpl implements ItemAmbienteService, Serializable {

	
	private static final long serialVersionUID = 1L;
	
	@Inject
	private ItemAmbienteDAO itemAmbienteDAO = null;
	
	public void salvar(ItemAmbiente itemAmbiente) throws SQLException, Exception {
		this.itemAmbienteDAO.salvar(itemAmbiente);		
	}
	
	public List<ItemAmbiente> buscarPorAmbiente(Ambiente ambiente) throws SQLException, Exception {		
		return this.itemAmbienteDAO.buscarPorAmbiente(ambiente);
	}

	public void excluir(ItemAmbiente itemAmbiente) throws SQLException, Exception {
		this.itemAmbienteDAO.excluir(itemAmbiente);		
	}

	public void atualizar(ItemAmbiente itemAmbiente) throws SQLException, Exception {
		this.itemAmbienteDAO.atualizar(itemAmbiente);
	}

	public ItemAmbienteDAO getItemAmbienteDAO() {
		return itemAmbienteDAO;
	}

	public void setItemAmbienteDAO(ItemAmbienteDAO itemAmbienteDAO) {
		this.itemAmbienteDAO = itemAmbienteDAO;
	}

}
