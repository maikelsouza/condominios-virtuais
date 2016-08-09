package br.com.condominiosvirtuais.persistence;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import br.com.condominiosvirtuais.entity.Arquivo;
import br.com.condominiosvirtuais.entity.Classificados;
import br.com.condominiosvirtuais.entity.Condominio;
import br.com.condominiosvirtuais.entity.Condomino;
import br.com.condominiosvirtuais.entity.Funcionario;

public interface ArquivoDAO {
	
	public abstract void salvarArquivoCondominio(Arquivo arquivo, Connection con ) throws FileNotFoundException, IOException, SQLException, Exception;
	
	public abstract void salvarArquivoCondomino(Arquivo arquivo, Connection con ) throws FileNotFoundException, IOException, SQLException, Exception;
	
	public abstract void salvarArquivoCondomino(Arquivo arquivo) throws FileNotFoundException, IOException, SQLException, Exception;
	
	//public abstract void salvarArquivoAmbienteCondominio(Arquivo arquivo, Connection con ) throws FileNotFoundException, IOException, SQLException, Exception;
	
	//public abstract void excluirArquivoCondominio(Arquivo arquivo, Connection con ) throws SQLException, Exception;
	
	public abstract void excluirArquivoCondominio(Arquivo arquivo) throws SQLException, Exception;
	
	public abstract void excluirArquivoCondomino(Arquivo arquivo, Connection con ) throws  SQLException, Exception;
	
	public abstract void excluirArquivoFuncionarioCondominio(Arquivo arquivo, Connection con ) throws SQLException, Exception;

	//public abstract void excluirArquivoAmbienteCondominio(Arquivo arquivo, Connection con ) throws SQLException, Exception;	
		
	public abstract void criarDiretorioCondominio(Condominio condominio);
	
	public abstract void excluirDiretorioCondominio(Condominio condominio);
	
	public abstract Arquivo buscarPorCondomino(Condomino condomino, Connection con) throws FileNotFoundException, IOException, SQLException, Exception;
	
	public abstract void atualizarArquivoCondomino(Arquivo arquivo) throws FileNotFoundException, IOException, SQLException, Exception;	
	
	public abstract void salvarArquivoCondominio(Arquivo arquivo) throws FileNotFoundException, IOException, SQLException, Exception;		
	
	public abstract void atualizarArquivoFuncionario(Arquivo arquivo) throws FileNotFoundException, IOException, SQLException, Exception;
	
	public abstract void salvarArquivoFuncionarioCondominio(Arquivo arquivo) throws FileNotFoundException, IOException, SQLException, Exception;
	
	public abstract Arquivo buscarPorFuncionarioCondominio(Funcionario funcionario, Connection con) throws FileNotFoundException, IOException, SQLException, Exception;
	
	public abstract Arquivo buscarPorFuncionarioConjuntoBloco(Funcionario funcionario, Connection con) throws FileNotFoundException, IOException, SQLException, Exception;	
	
	public abstract void atualizarArquivoFuncionarioCondominio(Arquivo arquivo) throws FileNotFoundException, IOException, SQLException, Exception;

	public abstract void excluirArquivoFuncionarioConjuntoBloco(Arquivo arquivo, Connection con ) throws SQLException, Exception;
	
	public abstract Arquivo buscarPorClassificados(Classificados classificados, Connection con) throws FileNotFoundException, IOException, SQLException, Exception;
	
	public abstract void excluirArquivoClassificados(Arquivo arquivo, Connection con) throws SQLException, Exception;
	
	public abstract void salvarArquivoClassificados(Arquivo arquivo, Connection con ) throws SQLException, Exception;
	
	public abstract void atualizarArquivoClassificados(Arquivo arquivo, Connection con) throws FileNotFoundException, IOException, SQLException, Exception;	
	
	//public abstract void salvarArquivoFuncionarioCondominio(Arquivo arquivo, Connection con ) throws FileNotFoundException, IOException, SQLException, Exception;	
	
	public abstract void salvarArquivoFuncionarioConjuntoBloco(Arquivo arquivo) throws FileNotFoundException, IOException, SQLException, Exception;
	
	public abstract List<Arquivo> buscarArquivoPorTiposEIdCondominio(List<String> listaTipoArquivos, Integer idCondominio) throws FileNotFoundException, IOException, SQLException, Exception;
	
	public abstract void atualizarArquivoFuncionarioConjuntoBloco(Arquivo arquivo) throws FileNotFoundException, IOException, SQLException, Exception;
	
	public abstract void executeListaFile(Integer tipo) throws Exception;
	
	public abstract Boolean getCommit();

	public abstract void setCommit(Boolean commit);
	
	public abstract void rollback();
	
	public static final Integer DELETE = 1;
}
