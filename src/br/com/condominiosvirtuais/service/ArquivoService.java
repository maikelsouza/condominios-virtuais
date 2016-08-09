package br.com.condominiosvirtuais.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import br.com.condominiosvirtuais.entity.Arquivo;

public interface ArquivoService {
	
	public abstract void atualizarArquivoCondomino(Arquivo arquivo) throws FileNotFoundException, IOException, SQLException, Exception;
	
	public abstract void salvarArquivoCondomino(Arquivo arquivo) throws FileNotFoundException, IOException, SQLException, Exception;
	
	public abstract void atualizarArquivoFuncionarioCondominio(Arquivo arquivo) throws FileNotFoundException, IOException, SQLException, Exception;
	
	public abstract void salvarArquivoCondominoConjuntoBloco(Arquivo arquivo) throws FileNotFoundException, IOException, SQLException, Exception;
	
	public abstract void salvarArquivoCondominio(Arquivo arquivo) throws FileNotFoundException, IOException, SQLException, Exception;
	
	public abstract void atualizarArquivoFuncionarioConjuntoBloco(Arquivo arquivo) throws FileNotFoundException, IOException, SQLException, Exception;
	
	public abstract void salvarArquivoFuncionarioCondominio(Arquivo arquivo) throws FileNotFoundException, IOException, SQLException, Exception;
	
	public abstract void salvarArquivoFuncionarioConjuntoBloco(Arquivo arquivo) throws FileNotFoundException, IOException, SQLException, Exception;
	
	public abstract List<Arquivo> buscarArquivoPorTiposEIdCondominio(List<String> listaTipoArquivos, Integer idCondominio) throws FileNotFoundException, IOException, SQLException, Exception;
	
	public abstract void excluirArquivoCondominio(Arquivo arquivo) throws SQLException, Exception;
	
}
