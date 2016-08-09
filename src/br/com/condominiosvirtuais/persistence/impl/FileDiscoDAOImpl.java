package br.com.condominiosvirtuais.persistence.impl;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.sql.SQLException;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import br.com.condominiosvirtuais.cdi.qualifier.QualifierFileDiscoDAO;
import br.com.condominiosvirtuais.entity.Arquivo;
import br.com.condominiosvirtuais.entity.Condominio;
import br.com.condominiosvirtuais.enumeration.ConfiguracaoAplicacaoEnum;
import br.com.condominiosvirtuais.persistence.ConfiguracaoAplicacaoDAO;
import br.com.condominiosvirtuais.persistence.FileDAO;

@QualifierFileDiscoDAO
public class FileDiscoDAOImpl implements FileDAO, Serializable{
	

	private static final long serialVersionUID = 1L;

	private String enderecoArquivos;
	
	private Boolean commit;
	
	//private static final String BARRA = "/";
	
	@Inject
	private ConfiguracaoAplicacaoDAO configuracaoAplicacaoDAO;
	
	
	@Override @PostConstruct
	public void configuraEnderecoFile() throws SQLException, Exception {
		this.enderecoArquivos = this.configuracaoAplicacaoDAO.buscarTodas().get(ConfiguracaoAplicacaoEnum.ENDERECO_ARQUIVOS.getChave());
		FileDAO.listaFile.clear();
	}


	@Override
	public void criarFile(Arquivo arquivo, Integer idCondomino) throws Exception {
		String endereco = this.enderecoArquivos+File.separator+idCondomino+File.separator+arquivo.getId();		
		File diretorio = new File(endereco);	
		// Cria o diretório com o nome igual ao id do registro no Banco de dados (TABELA ARQUIVO)
		diretorio.mkdir();	
		endereco += File.separator+arquivo.getNome();
		FileOutputStream outPut = new FileOutputStream(endereco);
		outPut.write(arquivo.getDadosArquivo());
		outPut.flush();
		outPut.close();
	}
	
	@Override
	public void excluirFile(Arquivo arquivo) throws Exception {
		String enderecoArquivo = this.enderecoArquivos+File.separator+arquivo.getIdCondominio()+File.separator+arquivo.getId()+File.separator+arquivo.getNome();
		File file = new File(enderecoArquivo);
		if(this.commit == Boolean.FALSE){
			FileDAO.listaFile.add(file);
		}else{
			// Exclui o arquivo
			file.delete();				
		}
		String enderecoDiretorio = this.enderecoArquivos+File.separator+arquivo.getIdCondominio()+File.separator+arquivo.getId();
		File diretorio = new File(enderecoDiretorio);
		if(this.commit == Boolean.FALSE){
			FileDAO.listaFile.add(diretorio);
		}else{
			// Exclui o diretório
			diretorio.delete();				
		}
	}
	
	
	@Override
	public void criarDiretorioCondominio(Condominio condominio) {		
		String endereco = this.enderecoArquivos+File.separator+condominio.getId();
		File diretorio = new File(endereco);
		System.out.println("CRIA " + diretorio.mkdir());		
	}

	@Override
	public void excluirDiretorioCondominio(Condominio condominio) {		
		String endereco = this.enderecoArquivos+File.separator+condominio.getId();
		File diretorio = new File(endereco);
		diretorio.delete();		
	}

	@Override
	public byte[] buscaDadosFile(Arquivo arquivo, Integer idCondominio) throws IOException {
		byte[] dadosArquivo = null;
		File file = new File(this.enderecoArquivos+File.separator+idCondominio+File.separator+arquivo.getId()+File.separator+arquivo.getNome());
		dadosArquivo = new byte[(int) file.length()];
		InputStream ios = new FileInputStream(file);
		DataInputStream dis = new DataInputStream (ios);			      
		dis.readFully(dadosArquivo);			        
		dis.close();
		ios.close();
		return dadosArquivo;
	}


	@Override
	public void atualizarFile(Arquivo arquivo) throws Exception {
		String enderecoDiretorio = this.enderecoArquivos+File.separator+arquivo.getIdCondominio()+File.separator+arquivo.getId();
		File fileDiretorio = new File(enderecoDiretorio);
		File[] listaArquivos = fileDiretorio.listFiles();
		listaArquivos[0].delete();			
		String enderecoArquivo = this.enderecoArquivos+File.separator+arquivo.getIdCondominio()+File.separator+arquivo.getId()+File.separator+arquivo.getNome();
		File file = new File(enderecoArquivo);			
		FileOutputStream outPut = new FileOutputStream(file);
		outPut.write(arquivo.getDadosArquivo());
		outPut.flush();
		outPut.close();
		
	}	

}
