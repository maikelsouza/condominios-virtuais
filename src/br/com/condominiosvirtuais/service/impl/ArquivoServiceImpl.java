package br.com.condominiosvirtuais.service.impl;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

import javax.inject.Inject;

import br.com.condominiosvirtuais.entity.Arquivo;
import br.com.condominiosvirtuais.persistence.ArquivoDAO;
import br.com.condominiosvirtuais.service.ArquivoService;

public class ArquivoServiceImpl implements ArquivoService, Serializable {
	
	private static final long serialVersionUID = 1L;
	
	
	@Inject
	private ArquivoDAO arquivoDAO;		

	
	@Override
	public void atualizarArquivoCondomino(Arquivo arquivo) throws FileNotFoundException, IOException, SQLException, Exception {		
		this.arquivoDAO.atualizarArquivoCondomino(arquivo);	
	}

	@Override
	public void salvarArquivoCondomino(Arquivo arquivo) throws FileNotFoundException, IOException, SQLException, Exception  {
		this.arquivoDAO.salvarArquivoCondomino(arquivo);		
	}

	@Override
	public void atualizarArquivoFuncionarioCondominio(Arquivo arquivo) throws FileNotFoundException, IOException, SQLException, Exception  {
		this.arquivoDAO.atualizarArquivoFuncionario(arquivo);		
	}

	@Override
	public void salvarArquivoFuncionarioCondominio(Arquivo arquivo) throws FileNotFoundException, IOException, SQLException, Exception  {
		this.arquivoDAO.salvarArquivoFuncionarioCondominio(arquivo);		
	}

	@Override
	public void salvarArquivoCondominoConjuntoBloco(Arquivo arquivo) throws FileNotFoundException, IOException, SQLException, Exception  {
		this.arquivoDAO.salvarArquivoFuncionarioConjuntoBloco(arquivo);		
	}

	@Override
	public void atualizarArquivoFuncionarioConjuntoBloco(Arquivo arquivo) throws FileNotFoundException, IOException, SQLException, Exception  {
		this.arquivoDAO.atualizarArquivoFuncionarioConjuntoBloco(arquivo);		
	}

	@Override
	public void salvarArquivoFuncionarioConjuntoBloco(Arquivo arquivo) throws FileNotFoundException, IOException, SQLException, Exception  {
		this.arquivoDAO.salvarArquivoFuncionarioConjuntoBloco(arquivo);		
	}

	@Override
	public List<Arquivo> buscarArquivoPorTiposEIdCondominio(List<String> listaTipoArquivos, Integer idCondominio) throws FileNotFoundException, IOException, SQLException, Exception  {
		return this.arquivoDAO.buscarArquivoPorTiposEIdCondominio(listaTipoArquivos, idCondominio);
	}

	@Override
	public void salvarArquivoCondominio(Arquivo arquivo) throws FileNotFoundException, IOException, SQLException, Exception  {
		this.arquivoDAO.salvarArquivoCondominio(arquivo);		
	}

	@Override
	public void excluirArquivoCondominio(Arquivo arquivo) throws SQLException, Exception  {
		this.arquivoDAO.excluirArquivoCondominio(arquivo);		
	}

}
