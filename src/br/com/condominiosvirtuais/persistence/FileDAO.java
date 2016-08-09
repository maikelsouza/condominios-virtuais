package br.com.condominiosvirtuais.persistence;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.condominiosvirtuais.entity.Arquivo;
import br.com.condominiosvirtuais.entity.Condominio;

/**
 * Interface criada para representar a manipulação de arquivos, seja ele no ambiente local, s3 amazon e etc.
 * Obs.: Para cada ambiente uma classe deverá ser criada.
 */
public interface FileDAO {
	
	
	public List<File> listaFile = new ArrayList<File>(); 
	
	public abstract  void configuraEnderecoFile() throws SQLException, Exception;		
	
	public abstract void criarFile(Arquivo arquivo, Integer idCondomino) throws Exception;
	
	public abstract void excluirFile(Arquivo arquivo) throws Exception;
	
	public abstract void criarDiretorioCondominio(Condominio condominio); 
	
	public abstract void excluirDiretorioCondominio(Condominio condominio);
	
	public abstract byte[] buscaDadosFile(Arquivo arquivo, Integer idCondominio) throws FileNotFoundException, IOException;
	
	public void atualizarFile(Arquivo arquivo) throws Exception;;
	

}
