package br.com.condominiosvirtuais.persistence.impl;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.log4j.Logger;

import br.com.condominiosvirtuais.cdi.qualifier.QualifierFileDiscoDAO;
import br.com.condominiosvirtuais.cdi.qualifier.QualifierFuncionarioDAO;
import br.com.condominiosvirtuais.entity.Arquivo;
import br.com.condominiosvirtuais.entity.Bloco;
import br.com.condominiosvirtuais.entity.BlocoConjuntoBloco;
import br.com.condominiosvirtuais.entity.Classificados;
import br.com.condominiosvirtuais.entity.Condominio;
import br.com.condominiosvirtuais.entity.Condomino;
import br.com.condominiosvirtuais.entity.ConjuntoBloco;
import br.com.condominiosvirtuais.entity.Funcionario;
import br.com.condominiosvirtuais.entity.Unidade;
import br.com.condominiosvirtuais.enumeration.ConfiguracaoAplicacaoEnum;
import br.com.condominiosvirtuais.persistence.ArquivoDAO;
import br.com.condominiosvirtuais.persistence.BlocoConjuntoBlocoDAO;
import br.com.condominiosvirtuais.persistence.ClassificadosDAO;
import br.com.condominiosvirtuais.persistence.ConfiguracaoAplicacaoDAO;
import br.com.condominiosvirtuais.persistence.ConjuntoBlocoDAO;
import br.com.condominiosvirtuais.persistence.FileDAO;
import br.com.condominiosvirtuais.persistence.FuncionarioDAO;
import br.com.condominiosvirtuais.util.SQLUtil;

public class ArquivoDAOImpl implements ArquivoDAO, Serializable {
	
	private static final long serialVersionUID = 1L;

	private static Logger logger = Logger.getLogger(ArquivoDAOImpl.class); 
	
	private static final String ARQUIVO = "ARQUIVO";
	
    private static final String ID = "ID";
	
	private static final String ID_USUARIO = "ID_USUARIO";
	
	private static final String ID_CONDOMINIO = "ID_CONDOMINIO";

// TODO: Código comentado em 01/10/2014. Apagar em 180 dias	
	//private static final String ID_AMBIENTE = "ID_AMBIENTE";
	
	private static final String ID_CLASSIFICADOS = "ID_CLASSIFICADOS";
	
	private static final String NOME = "NOME";
	
	private static final String MIME_TYPE = "MIME_TYPE";	
	
	private String enderecoArquivos;
	
	private Boolean commit;
	
	@Inject
	private CondominioDAOImpl condominioDAO;	
	
	@Inject
	private BlocoDAOImpl blocoDAO;
	
	@Inject
	private UnidadeDAOImpl unidadeDAO;
	
	@Inject
	private CondominoDAOImpl condominoDAO;
	

	@Inject @QualifierFuncionarioDAO
	private FuncionarioDAO funcionarioDAO;

// TODO: Código comentado em 18/09/2014. Apagar em 180 dias	
//	@Inject @QualifierAmbienteDAO
//	private AmbienteDAO ambienteDAO;
	
	@Inject
	private ConfiguracaoAplicacaoDAO configuracaoAplicacaoDAO;
	
	@Inject @QualifierFuncionarioDAO
	private ConjuntoBlocoDAO conjuntoBlocoDAO;
	
	@Inject
	private BlocoConjuntoBlocoDAO blocoConjuntoBlocoDAO;
	
	@Inject
	private ClassificadosDAO classificadosDAO;
	
	private List<Arquivo> listaFile = new ArrayList<Arquivo>(); 
	
	@Inject @QualifierFileDiscoDAO
	private FileDAO fileDAO;
	
		
	@PostConstruct
	public void configurEnderecoArquivo() throws SQLException, Exception{
		this.enderecoArquivos = this.configuracaoAplicacaoDAO.buscarTodas().get(ConfiguracaoAplicacaoEnum.ENDERECO_ARQUIVOS.getChave());
		this.commit = Boolean.TRUE;
	}
	
	public ArquivoDAOImpl(String ende){
		this.enderecoArquivos = ende;
	}
	
	public ArquivoDAOImpl(){}

	@Override
	public void salvarArquivoCondominio(Arquivo arquivo, Connection con) throws FileNotFoundException, IOException, SQLException, Exception {
		PreparedStatement statement = null;	 		
		try {
			StringBuffer query = new StringBuffer();
			query.append("INSERT INTO "); 
			query.append(ARQUIVO); 
			query.append("(");
			query.append(ID_USUARIO); 
			query.append(",");
			query.append(ID_CONDOMINIO);
// TODO: Código comentado em 01/10/2014. Apagar em 180 dias			
//			query.append(",");
//			query.append(ID_AMBIENTE); 
			query.append(",");			
			query.append(ID_CLASSIFICADOS); 
			query.append(",");			
			query.append(NOME); 
			query.append(",");			
			query.append(MIME_TYPE);
			query.append(") ");
			query.append("VALUES(?,?,?,?,?)");
			statement = con.prepareStatement(query.toString(),PreparedStatement.RETURN_GENERATED_KEYS);
			SQLUtil.setValorPpreparedStatement(statement, 1, arquivo.getIdUsuario(), java.sql.Types.INTEGER);
			SQLUtil.setValorPpreparedStatement(statement, 2, arquivo.getIdCondominio(), java.sql.Types.INTEGER);
// TODO: Código comentado em 01/10/2014. Apagar em 180 dias			
//			SQLUtil.setValorPpreparedStatement(statement, 3, arquivo.getIdAmbiente(), java.sql.Types.INTEGER);
			SQLUtil.setValorPpreparedStatement(statement, 3, arquivo.getIdClassificados(), java.sql.Types.INTEGER);
			SQLUtil.setValorPpreparedStatement(statement, 4, arquivo.getNome(), java.sql.Types.VARCHAR);
			SQLUtil.setValorPpreparedStatement(statement, 5, arquivo.getMimeType(), java.sql.Types.VARCHAR);			
			statement.executeUpdate();
			ResultSet rs = statement.getGeneratedKeys();
			arquivo.setId(rs.getInt(1));
			this.fileDAO.criarFile(arquivo, arquivo.getIdCondominio());	

// TODO: Código comentado em 18/09/2014. Apagar em 180 dias			
//			String endereco = this.enderecoArquivos+File.separator+arquivo.getIdCondominio()+File.separator+arquivo.getId();			
//			File diretorio = new File(endereco);	
//			// Cria o diretório com o nome igual ao id do registro no Banco de dados (TABELA ARQUIVO)
//			diretorio.mkdir();	
//			endereco += File.separator+arquivo.getNome();
//			FileOutputStream outPut = new FileOutputStream(endereco);
//			outPut.write(arquivo.getDadosArquivo());
//			outPut.flush();
//			outPut.close();				  
		} catch (FileNotFoundException e) {		
			con.rollback();
			throw e;
		} catch (IOException e) {
			con.rollback();
			throw e;
		} catch (SQLException e) {
			con.rollback();
			throw e;
		} catch (Exception e) {
			con.rollback();			
			throw e;
		}	
	}	

	@Override
	public void salvarArquivoCondomino(Arquivo arquivo, Connection con) throws FileNotFoundException, IOException, SQLException, Exception {
		Condominio condominio = null;
		PreparedStatement statement = null;	 		
		try {			
			condominio = this.recuperaCondominio(arquivo, con);
			StringBuffer query = new StringBuffer();
			query.append("INSERT INTO "); 
			query.append(ARQUIVO); 
			query.append("(");
			query.append(ID_USUARIO); 
			query.append(",");
			query.append(ID_CONDOMINIO);
// TODO: Código comentado em 01/10/2014. Apagar em 180 dias			
//			query.append(",");
//			query.append(ID_AMBIENTE); 
			query.append(",");
			query.append(ID_CLASSIFICADOS); 
			query.append(",");
			query.append(NOME); 
			query.append(",");			
			query.append(MIME_TYPE);
			query.append(") ");
			query.append("VALUES(?,?,?,?,?)");
			statement = con.prepareStatement(query.toString(),PreparedStatement.RETURN_GENERATED_KEYS);
			SQLUtil.setValorPpreparedStatement(statement, 1, arquivo.getIdUsuario(), java.sql.Types.INTEGER);
			SQLUtil.setValorPpreparedStatement(statement, 2, arquivo.getIdCondominio(), java.sql.Types.INTEGER);
// TODO: Código comentado em 01/10/2014. Apagar em 180 dias			
//			SQLUtil.setValorPpreparedStatement(statement, 3, arquivo.getIdAmbiente(), java.sql.Types.INTEGER);
			SQLUtil.setValorPpreparedStatement(statement, 3, arquivo.getIdClassificados(), java.sql.Types.INTEGER);
			SQLUtil.setValorPpreparedStatement(statement, 4, arquivo.getNome(), java.sql.Types.VARCHAR);
			SQLUtil.setValorPpreparedStatement(statement, 5, arquivo.getMimeType(), java.sql.Types.VARCHAR);			
			statement.executeUpdate();
			ResultSet rs = statement.getGeneratedKeys(); 
			rs.next();
			arquivo.setId(rs.getInt(1));
			this.fileDAO.criarFile(arquivo, condominio.getId());

// TODO: Código comentado em 18/09/2014. Apagar em 180 dias				
//			String endereco = this.enderecoArquivos+File.separator+condominio.getId()+File.separator+arquivo.getId();
//			File diretorio = new File(endereco);	
//			// Cria o diretório com o nome igual ao id do registro no Banco de dados (TABELA ARQUIVO)
//			diretorio.mkdir();	
//			endereco += File.separator+arquivo.getNome();
//			FileOutputStream outPut = new FileOutputStream(endereco);
//			outPut.write(arquivo.getDadosArquivo());
//			outPut.flush();
//			outPut.close();				  
		} catch (FileNotFoundException e) {
			con.rollback();
			throw e;
		} catch (IOException e) {
			con.rollback();
			throw e;
		} catch (SQLException e) {
			con.rollback();
			throw e;	
		} catch (Exception e) {
			con.rollback();			
			throw e;
		}	
	}	
	
	@Override
	public void salvarArquivoCondomino(Arquivo arquivo) throws FileNotFoundException, IOException, SQLException, Exception {
		Connection con = Conexao.getConexao();
		PreparedStatement statement = null;	 		
		try {
			StringBuffer query = new StringBuffer();
			query.append("INSERT INTO "); 
			query.append(ARQUIVO); 
			query.append("(");
			query.append(ID_USUARIO); 
			query.append(",");
			query.append(ID_CONDOMINIO);
// TODO: Código comentado em 01/10/2014. Apagar em 180 dias			
//			query.append(",");
//			query.append(ID_AMBIENTE); 
			query.append(",");			
			query.append(ID_CLASSIFICADOS); 
			query.append(",");			
			query.append(NOME); 
			query.append(",");			
			query.append(MIME_TYPE);
			query.append(") ");
			query.append("VALUES(?,?,?,?,?)");
			statement = con.prepareStatement(query.toString(), PreparedStatement.RETURN_GENERATED_KEYS);
			SQLUtil.setValorPpreparedStatement(statement, 1, arquivo.getIdUsuario(), java.sql.Types.INTEGER);
			SQLUtil.setValorPpreparedStatement(statement, 2, arquivo.getIdCondominio(), java.sql.Types.INTEGER);
// TODO: Código comentado em 01/10/2014. Apagar em 180 dias			
//			SQLUtil.setValorPpreparedStatement(statement, 3, arquivo.getIdAmbiente(), java.sql.Types.INTEGER);
			SQLUtil.setValorPpreparedStatement(statement, 3, arquivo.getIdClassificados(), java.sql.Types.INTEGER);
			SQLUtil.setValorPpreparedStatement(statement, 4, arquivo.getNome(), java.sql.Types.VARCHAR);
			SQLUtil.setValorPpreparedStatement(statement, 5, arquivo.getMimeType(), java.sql.Types.VARCHAR);			
			statement.executeUpdate();
			ResultSet rs = statement.getGeneratedKeys();
			rs.next();
			arquivo.setId(rs.getInt(1));
			Condominio condominio = this.recuperaCondominio(arquivo, con);			
			this.fileDAO.criarFile(arquivo, condominio.getId());
			
// TODO: Código comentado em 18/09/2014. Apagar em 180 dias			
//	AWSCredentials credentials = new BasicAWSCredentials("AKIAIOU42DA2ZRERDIMQ", "jQTeFOmgWD837DMCswKNSabEDwnS21nf78ZC9Ba2");
//AWSCredentials awsCredentials = new AWSCredentials("maikel.souza@gmail.com", "mkl862");
//	AmazonS3 conn = new AmazonS3Client(credentials);
//	ByteArrayInputStream input = new ByteArrayInputStream("Hello World!".getBytes());
//	conn.putObject("condominiosvirtuais", "hello.txt", input, new ObjectMetadata());
/*			ByteArrayInputStream input = new ByteArrayInputStream(arquivo.getDadosArquivo());
			//conn.putObject("condominiosvirtuais",arquivo.getNome(), input, new ObjectMetadata());
			Bucket bucket = null;
			 // Create metadata for your folder & set content-length to 0
			        ObjectMetadata metadata = new ObjectMetadata();
			        metadata.setContentLength(0);
			        // Create empty content
			        InputStream emptyContent = new ByteArrayInputStream(new byte[0]);
			        // Create a PutObjectRequest passing the foldername suffixed by /
			        PutObjectRequest putObjectRequest =
			                new PutObjectRequest("condominiosvirtuais", "0"+"/",
			                        emptyContent, metadata);
			        // Send request to S3 to create folder
			        conn.putObject(putObjectRequest);
		//	AWSCredentials credentials = new ProfileCredentialsProvider().getCredentials();
		//	AmazonS3 amazonS3 = new AmazonS3Client(credentials);
			//DefaultAWSCredentialsProviderChain credentialProviderChain = new DefaultAWSCredentialsProviderChain();
			//TransferManager tx = new TransferManager(credentials);
		//	File file = File.createTempFile(arquivo.getNome(), ".jpg");
		//	file.createNewFile()
//		    Upload myUpload = tx.upload("condominiosvirtuais", arquivo.getNome(),file);
//		    if (myUpload.isDone())
//		    	System.out.println("feito");
//		    FileOutputStream outPut = new FileOutputStream(file);
//		    Writer writer = new OutputStreamWriter(new FileOutputStream(file));
		    //writer.write(arquivo.getDadosArquivo());
//			outPut.write(arquivo.getDadosArquivo());
//			outPut.flush();
//			outPut.close();			
//			//myUpload.
		//	myUpload.waitForCompletion();			 
//			// After the upload is complete, call shutdownNow to release the resources.
		//	tx.shutdownNow();
//			String endereco = this.enderecoArquivos+File.separator+condominio.getId()+File.separator+arquivo.getId();
//			File diretorio = new File(endereco);	
//			// Cria o diretório com o nome igual ao id do registro no Banco de dados (TABELA ARQUIVO)
//			diretorio.mkdir();	
//			endereco += File.separator+arquivo.getNome();
//			FileOutputStream outPut = new FileOutputStream(endereco);
//			outPut.write(arquivo.getDadosArquivo());
//			outPut.flush();
//			outPut.close();
 * 				  
 */
		} catch (FileNotFoundException e) {
			throw e;
		} catch (IOException e) {
			throw e;
		} catch (SQLException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
		}	
		finally{
			try {
				statement.close();
				con.close();				
			} catch (SQLException e) {
				logger.error("erro sqlstate "+e.getSQLState(), e);
			}	
		}	
	}	
	
// TODO: Código comentado em 17/09/2014. Apagar em 180 dias			
//	@Override
//	public void salvarArquivoAmbienteCondominio(Arquivo arquivo, Connection con) throws FileNotFoundException, IOException, SQLException, Exception {		
//		PreparedStatement statement = null;	 		
//		try {
//			Ambiente ambiente = this.ambienteDAO.buscarPorId(arquivo.getIdAmbiente(),con);		
//			Condominio condominio = this.condominioDAO.buscarCondominioPorId(ambiente.getIdCondominio(), con);
//			StringBuffer query = new StringBuffer();
//			query.append("INSERT INTO "); 
//			query.append(ARQUIVO); 
//			query.append("(");
//			query.append(ID_USUARIO); 
//			query.append(",");
//			query.append(ID_CONDOMINIO); 
//			query.append(",");
//			query.append(ID_AMBIENTE); 
//			query.append(",");			
//			query.append(ID_CLASSIFICADOS); 
//			query.append(",");			
//			query.append(NOME); 
//			query.append(",");			
//			query.append(MIME_TYPE);
//			query.append(") ");
//			query.append("VALUES(?,?,?,?,?,?)");
//			statement = con.prepareStatement(query.toString(), PreparedStatement.RETURN_GENERATED_KEYS);
//			SQLUtil.setValorPpreparedStatement(statement, 1, arquivo.getIdUsuario(), java.sql.Types.INTEGER);
//			SQLUtil.setValorPpreparedStatement(statement, 2, arquivo.getIdCondominio(), java.sql.Types.INTEGER);
//			SQLUtil.setValorPpreparedStatement(statement, 3, arquivo.getIdAmbiente(), java.sql.Types.INTEGER);
//			SQLUtil.setValorPpreparedStatement(statement, 4, arquivo.getIdClassificados(), java.sql.Types.INTEGER);
//			SQLUtil.setValorPpreparedStatement(statement, 5, arquivo.getNome(), java.sql.Types.VARCHAR);
//			SQLUtil.setValorPpreparedStatement(statement, 6, arquivo.getMimeType(), java.sql.Types.VARCHAR);			
//			statement.executeUpdate();
//			ResultSet rs = statement.getGeneratedKeys();
//			rs.next();
//			arquivo.setId(rs.getInt(1));
//			String endereco = this.enderecoArquivos+File.separator+condominio.getId()+File.separator+arquivo.getId();
//			File diretorio = new File(endereco);	
//			// Cria o diretório com o nome igual ao id do registro no Banco de dados (TABELA ARQUIVO)
//			diretorio.mkdir();	
//			endereco += File.separator+arquivo.getNome();
//			FileOutputStream outPut = new FileOutputStream(endereco);
//			outPut.write(arquivo.getDadosArquivo());
//			outPut.flush();
//			outPut.close();				  
//		} catch (FileNotFoundException e) {			
//			con.rollback();
//			throw e;
//		} catch (IOException e) {
//			con.rollback();
//			throw e;
//		} catch (SQLException e) {
//			con.rollback();
//			throw e;
//		}catch (Exception e) {
//			con.rollback();
//			throw e;
//		}	
//		
//	}

// TODO: Código comentado em 17/09/2014. Apagar em 180 dias	
//	@Override
//	public void salvarArquivoFuncionarioCondominio(Arquivo arquivo, Connection con) throws FileNotFoundException, IOException, SQLException, Exception {		
//		Funcionario funcionario = this.funcionarioDAO.buscarPorId(arquivo.getIdUsuario(),con);
//		Condominio condominio = null;
//		PreparedStatement statement = null;	 		
//		try {
//			condominio = this.condominioDAO.buscarCondominioPorId(funcionario.getIdCondominio(), con);
//			StringBuffer query = new StringBuffer();
//			query.append("INSERT INTO "); 
//			query.append(ARQUIVO); 
//			query.append("(");
//			query.append(ID_USUARIO); 
//			query.append(",");
//			query.append(ID_CONDOMINIO); 
//			query.append(",");
//			query.append(ID_AMBIENTE); 
//			query.append(",");			
//			query.append(ID_CLASSIFICADOS); 
//			query.append(",");			
//			query.append(NOME); 
//			query.append(",");			
//			query.append(MIME_TYPE);
//			query.append(") ");
//			query.append("VALUES(?,?,?,?,?,?)");
//			statement = con.prepareStatement(query.toString());
//			SQLUtil.setValorPpreparedStatement(statement, 1, arquivo.getIdUsuario(), java.sql.Types.INTEGER);
//			SQLUtil.setValorPpreparedStatement(statement, 2, arquivo.getIdCondominio(), java.sql.Types.INTEGER);
//			SQLUtil.setValorPpreparedStatement(statement, 3, arquivo.getIdAmbiente(), java.sql.Types.INTEGER);
//			SQLUtil.setValorPpreparedStatement(statement, 4, arquivo.getIdClassificados(), java.sql.Types.INTEGER);
//			SQLUtil.setValorPpreparedStatement(statement, 5, arquivo.getNome(), java.sql.Types.VARCHAR);
//			SQLUtil.setValorPpreparedStatement(statement, 6, arquivo.getMimeType(), java.sql.Types.VARCHAR);			
//			statement.executeUpdate();
//			ResultSet rs = statement.getGeneratedKeys();
//			rs.next();
//			arquivo.setId(rs.getInt(1));
//			String endereco = this.enderecoArquivos+File.separator+condominio.getId()+File.separator+arquivo.getId();
//			File diretorio = new File(endereco);	
//			// Cria o diretório com o nome igual ao id do registro no Banco de dados (TABELA ARQUIVO)
//			diretorio.mkdir();	
//			endereco += File.separator+arquivo.getNome();
//			FileOutputStream outPut = new FileOutputStream(endereco);
//			outPut.write(arquivo.getDadosArquivo());
//			outPut.flush();
//			outPut.close();				  
//		} catch (FileNotFoundException e) {			
//			con.rollback();
//			throw e;
//		} catch (IOException e) {
//			con.rollback();
//			throw e;
//		} catch (SQLException e) {
//			con.rollback();
//			throw e;
//		} catch (Exception e) {
//			con.rollback();
//			throw e;
//		}	
//		
//	}

	// TODO: Código comentado em 17/09/2014. Apagar em 180 dias		
//	@Override
//	public void excluirArquivoCondominio(Arquivo arquivo, Connection con) throws SQLException, Exception {
//		PreparedStatement statement = null;	 		
//		try {
//			StringBuffer query = new StringBuffer();
//			query.append("DELETE FROM ");
//			query.append(ARQUIVO);
//			query.append(" WHERE ");		
//			query.append(ID);
//			query.append(" = ?");
//			statement = con.prepareStatement(query.toString());
//			SQLUtil.setValorPpreparedStatement(statement, 1, arquivo.getId(), java.sql.Types.INTEGER);
//			statement.executeUpdate();
//			String enderecoArquivo = this.enderecoArquivos+File.separator+arquivo.getIdCondominio()+File.separator+arquivo.getId()+File.separator+arquivo.getNome();
//			File file = new File(enderecoArquivo);
//			// Exclui o arquivo
//			file.delete();
//			String enderecoDiretorio = this.enderecoArquivos+File.separator+arquivo.getIdCondominio()+File.separator+arquivo.getId();
//			File diretorio = new File(enderecoDiretorio);	
//			// Exclui o diretório
//			diretorio.delete();	
//		} catch (SQLException e) {
//			con.rollback();		
//			throw e;
//		} catch (Exception e) {
//			con.rollback();
//			throw e;
//		}			
//	}

	@Override
	public void excluirArquivoCondomino(Arquivo arquivo, Connection con) throws SQLException, Exception {
		Condominio condominio = recuperaCondominio(arquivo, con);
		PreparedStatement statement = null;	 		
		try {
			StringBuffer query = new StringBuffer();
			query.append("DELETE FROM ");
			query.append(ARQUIVO);
			query.append(" WHERE ");		
			query.append(ID);
			query.append(" = ?");
			statement = con.prepareStatement(query.toString());
			SQLUtil.setValorPpreparedStatement(statement, 1, arquivo.getId(), java.sql.Types.INTEGER);
			statement.executeUpdate();
			arquivo.setIdCondominio(condominio.getId());
			if(this.commit == Boolean.FALSE){
				this.listaFile.add(arquivo);
			}else{
				// Exclui o arquivo
				this.fileDAO.excluirFile(arquivo);			
			}
		}  catch (SQLException e) {
			con.rollback();
			throw e;
		}catch (Exception e) {
			con.rollback();
			throw e;
		}	
	}

	@Override
	public void excluirArquivoFuncionarioCondominio(Arquivo arquivo, Connection con) throws SQLException, Exception {	
		Funcionario funcionario = this.funcionarioDAO.buscarPorId(arquivo.getIdUsuario(),con);
		Condominio condominio = null;
		PreparedStatement statement = null;	 		
		try {
			condominio = this.condominioDAO.buscarCondominioPorId(funcionario.getIdCondominio(), con);
			StringBuffer query = new StringBuffer();
			query.append("DELETE FROM ");
			query.append(ARQUIVO);
			query.append(" WHERE ");		
			query.append(ID);
			query.append(" = ?");
			statement = con.prepareStatement(query.toString());
			SQLUtil.setValorPpreparedStatement(statement, 1, arquivo.getId(), java.sql.Types.INTEGER);
			statement.executeUpdate();
			arquivo.setIdCondominio(condominio.getId());		
			if(this.commit == Boolean.FALSE){
				this.listaFile.add(arquivo);
			}else{
				// Exclui o arquivo
				this.fileDAO.excluirFile(arquivo);			
			}
// TODO: Código comentado em 17/09/2014. Apagar em 180 dias				
//			String enderecoArquivo = this.enderecoArquivos+File.separator+condominio.getId()+File.separator+arquivo.getId()+File.separator+arquivo.getNome();
//			File file = new File(enderecoArquivo);
//			if(this.commit == Boolean.FALSE){
//				this.listaFile.add(file);
//			}else{
//				// Exclui o arquivo
//				file.delete();				
//			}			
//			String enderecoDiretorio = this.enderecoArquivos+File.separator+condominio.getId()+File.separator+arquivo.getId();
//			File diretorio = new File(enderecoDiretorio);	
//			if(this.commit == Boolean.FALSE){
//				this.listaFile.add(diretorio);
//			}else{
//				// Exclui o diretório
//				diretorio.delete();				
//			}	
		} catch (SQLException e) {
			con.rollback();
			throw e;
		}catch (Exception e) {
			con.rollback();
			throw e;
		}			
	}

// TODO: Código comentado em 17/09/2014. Apagar em 180 dias	
//	@Override
//	public void excluirArquivoAmbienteCondominio(Arquivo arquivo, Connection con) throws SQLException, Exception {
//		Ambiente ambiente = null;		
//		Condominio condominio = null;
//		PreparedStatement statement = null;	 		
//		try {
//			ambiente = this.ambienteDAO.buscarPorId(arquivo.getIdAmbiente(), con);
//			condominio = this.condominioDAO.buscarCondominioPorId(ambiente.getIdCondominio(), con);
//			StringBuffer query = new StringBuffer();
//			query.append("DELETE FROM ");
//			query.append(ARQUIVO);
//			query.append(" WHERE ");		
//			query.append(ID);
//			query.append(" = ?");
//			statement = con.prepareStatement(query.toString());
//			SQLUtil.setValorPpreparedStatement(statement, 1, arquivo.getId(), java.sql.Types.INTEGER);
//			statement.executeUpdate();
//			String enderecoArquivo = this.enderecoArquivos+File.separator+condominio.getId()+File.separator+arquivo.getId()+File.separator+arquivo.getNome();
//			File file = new File(enderecoArquivo);
//			if(this.commit == Boolean.FALSE){
//				this.listaFile.add(file);
//			}else{
//				// Exclui o arquivo
//				file.delete();				
//			}			
//			String enderecoDiretorio = this.enderecoArquivos+File.separator+condominio.getId()+File.separator+arquivo.getId();
//			File diretorio = new File(enderecoDiretorio);	
//			if(this.commit == Boolean.FALSE){
//				this.listaFile.add(diretorio);
//			}else{
//				// Exclui o diretório
//				diretorio.delete();				
//			}	
//		} catch (SQLException e) {
//			con.rollback();
//			throw e;
//		}catch (Exception e) {
//			con.rollback();
//			throw e;
//		}	
//	}

// TODO: Código comentado em 17/09/2014. Apagar em 180 dias		
//	@Override
//	public void criarDiretorioCondominio(Condominio condominio) {		
//		String endereco = this.enderecoArquivos+File.separator+condominio.getId();
//		File diretorio = new File(endereco);
//		diretorio.mkdir();		
//	}
//
//	@Override
//	public void excluirDiretorioCondominio(Condominio condominio) {		
//		String endereco = this.enderecoArquivos+File.separator+condominio.getId();
//		File diretorio = new File(endereco);
//		diretorio.delete();		
//	}
//	
	public Arquivo buscarPorCondomino(Condomino condomino, Connection con) throws FileNotFoundException, IOException, SQLException, Exception{			
		StringBuffer query = new StringBuffer();
		query.append("SELECT * FROM ");
		query.append(ARQUIVO );
		query.append(" WHERE ");
		query.append(ID_USUARIO);
		query.append(" = ?");
		query.append(";");
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;	
		Arquivo arquivo = null;
		Condominio condominio = null;
		try {
			preparedStatement = con.prepareStatement(query.toString());
			SQLUtil.setValorPpreparedStatement(preparedStatement,1, condomino.getId(), java.sql.Types.INTEGER);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()){
				arquivo = new Arquivo();				
				arquivo.setId((Integer) SQLUtil.getValorResultSet(resultSet, ID, java.sql.Types.INTEGER));
				arquivo.setIdCondominio((Integer) SQLUtil.getValorResultSet(resultSet, ID_CONDOMINIO, java.sql.Types.INTEGER));
				arquivo.setIdUsuario((Integer) SQLUtil.getValorResultSet(resultSet, ID_USUARIO, java.sql.Types.INTEGER));
				arquivo.setIdClassificados((Integer) SQLUtil.getValorResultSet(resultSet, ID_CLASSIFICADOS, java.sql.Types.INTEGER));
				arquivo.setMimeType(String.valueOf(SQLUtil.getValorResultSet(resultSet, MIME_TYPE, java.sql.Types.VARCHAR)));
				arquivo.setNome(String.valueOf(SQLUtil.getValorResultSet(resultSet, NOME, java.sql.Types.VARCHAR)));
				condominio = this.recuperaCondominio(arquivo, con);		
				arquivo.setDadosArquivo(this.fileDAO.buscaDadosFile(arquivo, condominio.getId()));
			}    
		} catch (FileNotFoundException e) {			
			con.rollback();
			throw e;
		} catch (IOException e) {
			con.rollback();
			throw e;
		} catch (SQLException e) {
			con.rollback();		
			throw e;
		}	catch (Exception e) {
			con.rollback();
			throw e;
		}	
		return arquivo;			
	}
	
	
	@Override
	public void executeListaFile(Integer tipo) throws Exception {
		switch (tipo) {
		case 1: // Delete			
			for (Arquivo arquivo : this.listaFile) {				
				this.fileDAO.excluirFile(arquivo);			
			}							
			break;		
		}
		this.setCommit(Boolean.TRUE);
	}
	
	@Override
	public void rollback() {
		this.listaFile.clear();		
	}
	
	public Boolean getCommit() {
		return commit;
	}

	public void setCommit(Boolean commit) {
		this.commit = commit;
	}

	
	@Override
	public void atualizarArquivoCondomino(Arquivo arquivo) throws FileNotFoundException, IOException, SQLException, Exception {
		
		StringBuffer query = new StringBuffer();
		query.append("UPDATE ");
		query.append(ARQUIVO);
		query.append(" SET ");
		query.append(NOME);
		query.append("= ?, ");
		query.append(ID_USUARIO);
		query.append("= ?, "); 
		query.append(ID_CONDOMINIO);
		query.append("= ?, ");
// TODO: Código comentado em 01/10/2014. Apagar em 180 dias		
//		query.append(ID_AMBIENTE);		
//		query.append("= ?, ");
		query.append(ID_CLASSIFICADOS);		
		query.append("= ?, ");
		query.append(MIME_TYPE);
		query.append("= ? ");
		query.append("WHERE ");
		query.append(ID);
		query.append("= ?");
		Connection con = Conexao.getConexao();
		PreparedStatement statement = null;
		try {			
			statement = con.prepareStatement(query.toString());
			SQLUtil.setValorPpreparedStatement(statement, 1, arquivo.getNome(), java.sql.Types.VARCHAR);			
			SQLUtil.setValorPpreparedStatement(statement, 2, arquivo.getIdUsuario(), java.sql.Types.INTEGER);
			SQLUtil.setValorPpreparedStatement(statement, 3, arquivo.getIdCondominio(), java.sql.Types.INTEGER);
// TODO: Código comentado em 01/10/2014. Apagar em 180 dias			
//			SQLUtil.setValorPpreparedStatement(statement, 4, arquivo.getIdAmbiente(), java.sql.Types.INTEGER);
			SQLUtil.setValorPpreparedStatement(statement, 4, arquivo.getIdClassificados(), java.sql.Types.INTEGER);
			SQLUtil.setValorPpreparedStatement(statement, 5, arquivo.getMimeType(), java.sql.Types.VARCHAR);
			SQLUtil.setValorPpreparedStatement(statement, 6, arquivo.getId(), java.sql.Types.INTEGER);
			Condominio condominio = this.recuperaCondominio(arquivo, con);	
			statement.executeUpdate();
			arquivo.setIdCondominio(condominio.getId());
			this.fileDAO.atualizarFile(arquivo);
			
// TODO: Código comentado em 17/09/2014. Apagar em 180 dias				
//			String enderecoDiretorio = this.enderecoArquivos+File.separator+condominio.getId()+File.separator+arquivo.getId();
//			File fileDiretorio = new File(enderecoDiretorio);
//			File[] listaArquivos = fileDiretorio.listFiles();
//			listaArquivos[0].delete();			
//			String enderecoArquivo = this.enderecoArquivos+File.separator+condominio.getId()+File.separator+arquivo.getId()+File.separator+arquivo.getNome();
//			File file = new File(enderecoArquivo);			
//			FileOutputStream outPut = new FileOutputStream(file);
//			outPut.write(arquivo.getDadosArquivo());
//			outPut.flush();
//			outPut.close();		
		} catch (FileNotFoundException e) {
			throw e;
		} catch (IOException e) {
			throw e;
		} catch (SQLException e) {
			throw e;
		}catch (Exception e) {		
			throw e;
		}finally{
			try {
				statement.close();
				con.close();				
			} catch (SQLException e) {
				logger.error("erro sqlstate "+e.getSQLState(), e);
			}	
		}	
	}	

	@Override
	public void salvarArquivoCondominio(Arquivo arquivo) throws FileNotFoundException, IOException, SQLException, Exception{
		Connection con = Conexao.getConexao();
		PreparedStatement statement = null;	 		
		try {
			StringBuffer query = new StringBuffer();
			query.append("INSERT INTO "); 
			query.append(ARQUIVO); 
			query.append("(");
			query.append(ID_USUARIO); 
			query.append(",");
			query.append(ID_CONDOMINIO); 
			query.append(",");
// TODO: Código comentado em 01/10/2014. Apagar em 180 dias			
//			query.append(ID_AMBIENTE); 
//			query.append(",");			
			query.append(ID_CLASSIFICADOS); 
			query.append(",");			
			query.append(NOME); 
			query.append(",");			
			query.append(MIME_TYPE);
			query.append(") ");
			query.append("VALUES(?,?,?,?,?)");
			statement = con.prepareStatement(query.toString(), PreparedStatement.RETURN_GENERATED_KEYS);
			SQLUtil.setValorPpreparedStatement(statement, 1, arquivo.getIdUsuario(), java.sql.Types.INTEGER);
			SQLUtil.setValorPpreparedStatement(statement, 2, arquivo.getIdCondominio(), java.sql.Types.INTEGER);
// TODO: Código comentado em 01/10/2014. Apagar em 180 dias			
//			SQLUtil.setValorPpreparedStatement(statement, 3, arquivo.getIdAmbiente(), java.sql.Types.INTEGER);
			SQLUtil.setValorPpreparedStatement(statement, 3, arquivo.getIdClassificados(), java.sql.Types.INTEGER);
			SQLUtil.setValorPpreparedStatement(statement, 4, arquivo.getNome(), java.sql.Types.VARCHAR);
			SQLUtil.setValorPpreparedStatement(statement, 5, arquivo.getMimeType(), java.sql.Types.VARCHAR);			
			statement.executeUpdate();
			ResultSet rs = statement.getGeneratedKeys(); 
			rs.next();			
			arquivo.setId(rs.getInt(1));
			this.fileDAO.criarFile(arquivo, arquivo.getIdCondominio());

// TODO: Código comentado em 18/09/2014. Apagar em 180 dias						
//			String endereco = this.enderecoArquivos+File.separator+arquivo.getIdCondominio()+File.separator+arquivo.getId();
//			File diretorio = new File(endereco);	
//			// Cria o diretório com o nome igual ao id do registro no Banco de dados (TABELA ARQUIVO)
//			diretorio.mkdir();	
//			endereco += File.separator+arquivo.getNome();
//			FileOutputStream outPut = new FileOutputStream(endereco);
//			outPut.write(arquivo.getDadosArquivo());
//			outPut.flush();
//			outPut.close();				  
		} catch (FileNotFoundException e) {					
			throw e;
		} catch (IOException e) {
			throw e;
		} catch (SQLException e) {
			throw e;
		}catch (Exception e) {
			throw e;
		}finally{
			try {
				statement.close();
				con.close();				
			} catch (SQLException e) {
				logger.error("erro sqlstate "+e.getSQLState(), e);
			}	
		}		
	}

	@Override
	public void atualizarArquivoFuncionario(Arquivo arquivo) throws FileNotFoundException, IOException, SQLException, Exception {
		StringBuffer query = new StringBuffer();
		query.append("UPDATE ");
		query.append(ARQUIVO);
		query.append(" SET ");
		query.append(NOME);
		query.append("= ?, ");
		query.append(ID_USUARIO);
		query.append("= ?, "); 
		query.append(ID_CONDOMINIO);
		query.append("= ?, ");
// TODO: Código comentado em 01/10/2014. Apagar em 180 dias		
//		query.append(ID_AMBIENTE);		
//		query.append("= ?, ");
		query.append(ID_CLASSIFICADOS);		
		query.append("= ?, ");
		query.append(MIME_TYPE);
		query.append("= ? ");
		query.append("WHERE ");
		query.append(ID);
		query.append("= ?");
		Connection con = Conexao.getConexao();
		PreparedStatement statement = null;
		try {			
			statement = con.prepareStatement(query.toString());
			SQLUtil.setValorPpreparedStatement(statement, 1, arquivo.getNome(), java.sql.Types.VARCHAR);			
			SQLUtil.setValorPpreparedStatement(statement, 2, arquivo.getIdUsuario(), java.sql.Types.INTEGER);
			SQLUtil.setValorPpreparedStatement(statement, 3, arquivo.getIdCondominio(), java.sql.Types.INTEGER);
// TODO: Código comentado em 01/10/2014. Apagar em 180 dias			
//			SQLUtil.setValorPpreparedStatement(statement, 4, arquivo.getIdAmbiente(), java.sql.Types.INTEGER);
			SQLUtil.setValorPpreparedStatement(statement, 4, arquivo.getIdClassificados(), java.sql.Types.INTEGER);
			SQLUtil.setValorPpreparedStatement(statement, 5, arquivo.getMimeType(), java.sql.Types.VARCHAR);
			SQLUtil.setValorPpreparedStatement(statement, 6, arquivo.getId(), java.sql.Types.INTEGER);
			Funcionario funcionario = this.funcionarioDAO.buscarPorId(arquivo.getIdUsuario(),con);
			Condominio condominio = this.condominioDAO.buscarCondominioPorId(funcionario.getIdCondominio(), con);
			statement.executeUpdate();
			arquivo.setIdCondominio(condominio.getId());
			this.fileDAO.atualizarFile(arquivo);
		
// TODO: Código comentado em 18/09/2014. Apagar em 180 dias					
//			String enderecoDiretorio = this.enderecoArquivos+File.separator+condominio.getId()+File.separator+arquivo.getId();
//			File fileDiretorio = new File(enderecoDiretorio);
//			File[] listaArquivos = fileDiretorio.listFiles();
//			listaArquivos[0].delete();			
//			String enderecoArquivo = this.enderecoArquivos+File.separator+condominio.getId()+File.separator+arquivo.getId()+File.separator+arquivo.getNome();
//			File file = new File(enderecoArquivo);			
//			FileOutputStream outPut = new FileOutputStream(file);
//			outPut.write(arquivo.getDadosArquivo());
//			outPut.flush();
//			outPut.close();		
		} catch (FileNotFoundException e) {					
			throw e;
		} catch (IOException e) {
			throw e;
		} catch (SQLException e) {
			throw e;
		}catch (Exception e) {
			throw e;
		}finally{
			try {
				statement.close();
				con.close();				
			} catch (SQLException e) {
				logger.error("erro sqlstate "+e.getSQLState(), e);
			}	
		}			
	}

	@Override
	public void salvarArquivoFuncionarioCondominio(Arquivo arquivo) throws FileNotFoundException, IOException, SQLException, Exception {
		Connection con = Conexao.getConexao();
		PreparedStatement statement = null;	 		
		try {
			StringBuffer query = new StringBuffer();
			query.append("INSERT INTO "); 
			query.append(ARQUIVO); 
			query.append("(");
			query.append(ID_USUARIO); 
			query.append(",");
			query.append(ID_CONDOMINIO); 
			query.append(",");
// TODO: Código comentado em 01/10/2014. Apagar em 180 dias			
//			query.append(ID_AMBIENTE); 
//			query.append(",");			
			query.append(ID_CLASSIFICADOS); 
			query.append(",");			
			query.append(NOME); 
			query.append(",");			
			query.append(MIME_TYPE);
			query.append(") ");
			query.append("VALUES(?,?,?,?,?)");
			statement = con.prepareStatement(query.toString(), PreparedStatement.RETURN_GENERATED_KEYS);
			SQLUtil.setValorPpreparedStatement(statement, 1, arquivo.getIdUsuario(), java.sql.Types.INTEGER);
			SQLUtil.setValorPpreparedStatement(statement, 2, arquivo.getIdCondominio(), java.sql.Types.INTEGER);
// TODO: Código comentado em 01/10/2014. Apagar em 180 dias			
//			SQLUtil.setValorPpreparedStatement(statement, 3, arquivo.getIdAmbiente(), java.sql.Types.INTEGER);
			SQLUtil.setValorPpreparedStatement(statement, 3, arquivo.getIdClassificados(), java.sql.Types.INTEGER);
			SQLUtil.setValorPpreparedStatement(statement, 4, arquivo.getNome(), java.sql.Types.VARCHAR);
			SQLUtil.setValorPpreparedStatement(statement, 5, arquivo.getMimeType(), java.sql.Types.VARCHAR);			
			statement.executeUpdate();
			ResultSet rs = statement.getGeneratedKeys(); 
			rs.next();			
			arquivo.setId(rs.getInt(1));
			Funcionario funcionario = this.funcionarioDAO.buscarPorId(arquivo.getIdUsuario(),con);
			Condominio condominio = this.condominioDAO.buscarCondominioPorId(funcionario.getIdCondominio(), con);
			this.fileDAO.criarFile(arquivo, condominio.getId());
			
// TODO: Código comentado em 18/09/2014. Apagar em 180 dias					
//			String endereco = this.enderecoArquivos+File.separator+condominio.getId()+File.separator+arquivo.getId();
//			File diretorio = new File(endereco);	
//			// Cria o diretório com o nome igual ao id do registro no Banco de dados (TABELA ARQUIVO)
//			diretorio.mkdir();	
//			endereco += File.separator+arquivo.getNome();
//			FileOutputStream outPut = new FileOutputStream(endereco);
//			outPut.write(arquivo.getDadosArquivo());
//			outPut.flush();
//			outPut.close();				  
		} catch (FileNotFoundException e) {					
			throw e;
		} catch (IOException e) {
			throw e;
		} catch (SQLException e) {
			throw e;
		}catch (Exception e) {
			throw e;
		}finally{
			try {
				statement.close();
				con.close();				
			} catch (SQLException e) {
				logger.error("erro sqlstate "+e.getSQLState(), e);
			}	
		}		
	}

	@Override
	public Arquivo buscarPorFuncionarioCondominio(Funcionario funcionario, Connection con) throws FileNotFoundException, IOException, SQLException, Exception {
		StringBuffer query = new StringBuffer();
		query.append("SELECT * FROM ");
		query.append(ARQUIVO );
		query.append(" WHERE ");
		query.append(ID_USUARIO);
		query.append(" = ?");
		query.append(";");
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;	
		Arquivo arquivo = null;
		//File file = null;
		Condominio condominio = null;
		//byte[] dadosArquivo = null;		
		try {
			preparedStatement = con.prepareStatement(query.toString());
			SQLUtil.setValorPpreparedStatement(preparedStatement,1, funcionario.getId(), java.sql.Types.INTEGER);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()){
				arquivo = new Arquivo();
				arquivo.setId((Integer) SQLUtil.getValorResultSet(resultSet, ID, java.sql.Types.INTEGER));
// TODO: Código comentado em 01/10/2014. Apagar em 180 dias				
//				arquivo.setIdAmbiente((Integer) SQLUtil.getValorResultSet(resultSet, ID_AMBIENTE, java.sql.Types.INTEGER));
				arquivo.setIdCondominio((Integer) SQLUtil.getValorResultSet(resultSet, ID_CONDOMINIO, java.sql.Types.INTEGER));
				arquivo.setIdUsuario((Integer) SQLUtil.getValorResultSet(resultSet, ID_USUARIO, java.sql.Types.INTEGER));
				arquivo.setIdClassificados((Integer) SQLUtil.getValorResultSet(resultSet, ID_CLASSIFICADOS, java.sql.Types.INTEGER));
				arquivo.setMimeType(String.valueOf(SQLUtil.getValorResultSet(resultSet, MIME_TYPE, java.sql.Types.VARCHAR)));
				arquivo.setNome(String.valueOf(SQLUtil.getValorResultSet(resultSet, NOME, java.sql.Types.VARCHAR)));				
				condominio = this.condominioDAO.buscarCondominioPorId(funcionario.getIdCondominio(), con);				
				arquivo.setDadosArquivo(this.fileDAO.buscaDadosFile(arquivo, condominio.getId()));

// TODO: Código comentado em 18/09/2014. Apagar em 180 dias			
//				file = new File(this.enderecoArquivos+File.separator+condominio.getId()+File.separator+arquivo.getId()+File.separator+arquivo.getNome());
//				dadosArquivo = new byte[(int) file.length()];
//				InputStream ios = new FileInputStream(file);
//				DataInputStream dis = new DataInputStream (ios);			      
//				dis.readFully (dadosArquivo);			        
//				dis.close();
//				ios.close();
			}    
		} catch (FileNotFoundException e) {			
			con.rollback();
			throw e;
		} catch (IOException e) {
			con.rollback();
			throw e;
		} catch (SQLException e) {
			con.rollback();
			throw e;
		} catch (Exception e) {
			con.rollback();
			throw e;
		}
		return arquivo;
	}	
	
	public Arquivo buscarPorFuncionarioConjuntoBloco(Funcionario funcionario, Connection con) throws FileNotFoundException, IOException, SQLException, Exception {
		StringBuffer query = new StringBuffer();
		query.append("SELECT * FROM ");
		query.append(ARQUIVO );
		query.append(" WHERE ");
		query.append(ID_USUARIO);
		query.append(" = ?");
		query.append(";");
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;	
		Arquivo arquivo = null;
		//File file = null;
		Condominio condominio = null;
		//byte[] dadosArquivo = null;		
		try {
			preparedStatement = con.prepareStatement(query.toString());
			SQLUtil.setValorPpreparedStatement(preparedStatement,1, funcionario.getId(), java.sql.Types.INTEGER);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()){
				arquivo = new Arquivo();
				arquivo.setId((Integer) SQLUtil.getValorResultSet(resultSet, ID, java.sql.Types.INTEGER));
// TODO: Código comentado em 01/10/2014. Apagar em 180 dias				
//				arquivo.setIdAmbiente((Integer) SQLUtil.getValorResultSet(resultSet, ID_AMBIENTE, java.sql.Types.INTEGER));
				arquivo.setIdCondominio((Integer) SQLUtil.getValorResultSet(resultSet, ID_CONDOMINIO, java.sql.Types.INTEGER));
				arquivo.setIdUsuario((Integer) SQLUtil.getValorResultSet(resultSet, ID_USUARIO, java.sql.Types.INTEGER));
				arquivo.setIdClassificados((Integer) SQLUtil.getValorResultSet(resultSet, ID_CLASSIFICADOS, java.sql.Types.INTEGER));
				arquivo.setMimeType(String.valueOf(SQLUtil.getValorResultSet(resultSet, MIME_TYPE, java.sql.Types.VARCHAR)));
				arquivo.setNome(String.valueOf(SQLUtil.getValorResultSet(resultSet, NOME, java.sql.Types.VARCHAR)));
				List<BlocoConjuntoBloco> listaBlocoConjuntoBloco = this.blocoConjuntoBlocoDAO.buscarPorIdConjuntoBloco(funcionario.getIdConjuntoBloco(), con);	
				condominio = this.condominioDAO.buscarCondominioPorId(listaBlocoConjuntoBloco.get(0).getBloco().getIdCondominio(), con);
				arquivo.setDadosArquivo(this.fileDAO.buscaDadosFile(arquivo, condominio.getId()));
				
// TODO: Código comentado em 18/09/2014. Apagar em 180 dias				
//				file = new File(this.enderecoArquivos+File.separator+condominio.getId()+File.separator+arquivo.getId()+File.separator+arquivo.getNome());
//				dadosArquivo = new byte[(int) file.length()];
//				InputStream ios = new FileInputStream(file);
//				DataInputStream dis = new DataInputStream (ios);			      
//				dis.readFully (dadosArquivo);			        
//				dis.close();
//				ios.close();
//				arquivo.setDadosArquivo(dadosArquivo);
			}    
		} catch (FileNotFoundException e) {			
			con.rollback();
			throw e;
		} catch (IOException e) {
			con.rollback();
			throw e;
		} catch (SQLException e) {
			con.rollback();
			throw e;
		} catch (Exception e) {
			con.rollback();
			throw e;
		}
		return arquivo;
	}

	@Override
	public void salvarArquivoFuncionarioConjuntoBloco(Arquivo arquivo) throws FileNotFoundException, IOException, SQLException, Exception {
		Connection con = Conexao.getConexao();
		PreparedStatement statement = null;	 		
		try {
			StringBuffer query = new StringBuffer();
			query.append("INSERT INTO "); 
			query.append(ARQUIVO); 
			query.append("(");
			query.append(ID_USUARIO); 
			query.append(",");
			query.append(ID_CONDOMINIO);
// TODO: Código comentado em 01/10/2014. Apagar em 180 dias			
//			query.append(",");
//			query.append(ID_AMBIENTE); 
			query.append(",");			
			query.append(ID_CLASSIFICADOS); 
			query.append(",");			
			query.append(NOME); 
			query.append(",");			
			query.append(MIME_TYPE);
			query.append(") ");
			query.append("VALUES(?,?,?,?,?)");
			statement = con.prepareStatement(query.toString(),PreparedStatement.RETURN_GENERATED_KEYS);
			SQLUtil.setValorPpreparedStatement(statement, 1, arquivo.getIdUsuario(), java.sql.Types.INTEGER);
			SQLUtil.setValorPpreparedStatement(statement, 2, arquivo.getIdCondominio(), java.sql.Types.INTEGER);
// TODO: Código comentado em 01/10/2014. Apagar em 180 dias			
//			SQLUtil.setValorPpreparedStatement(statement, 3, arquivo.getIdAmbiente(), java.sql.Types.INTEGER);
			SQLUtil.setValorPpreparedStatement(statement, 3, arquivo.getIdClassificados(), java.sql.Types.INTEGER);
			SQLUtil.setValorPpreparedStatement(statement, 4, arquivo.getNome(), java.sql.Types.VARCHAR);
			SQLUtil.setValorPpreparedStatement(statement, 5, arquivo.getMimeType(), java.sql.Types.VARCHAR);			
			statement.executeUpdate();
			ResultSet rs = statement.getGeneratedKeys(); 
			rs.next();			
			arquivo.setId(rs.getInt(1));
			Funcionario funcionario = this.funcionarioDAO.buscarPorId(arquivo.getIdUsuario(),con);
			Condominio condominio = this.recuperaCondominioPorIdConjuntoBloco(funcionario.getIdConjuntoBloco(), con);
			this.fileDAO.criarFile(arquivo, condominio.getId());
			
			// TODO: Código comentado em 18/09/2014. Apagar em 180 dias					
//			String endereco = this.enderecoArquivos+File.separator+condominio.getId()+File.separator+arquivo.getId();
//			File diretorio = new File(endereco);	
//			// Cria o diretório com o nome igual ao id do registro no Banco de dados (TABELA ARQUIVO)
//			diretorio.mkdir();	
//			endereco += File.separator+arquivo.getNome();
//			FileOutputStream outPut = new FileOutputStream(endereco);
//			outPut.write(arquivo.getDadosArquivo());
//			outPut.flush();
//			outPut.close();				  
		} catch (FileNotFoundException e) {					
			throw e;
		} catch (IOException e) {
			throw e;
		} catch (SQLException e) {
			throw e;
		}catch (Exception e) {
			throw e;
		}finally{
			try {
				statement.close();
				con.close();				
			} catch (SQLException e) {
				logger.error("erro sqlstate "+e.getSQLState(), e);
			}	
		}		

	}

	@Override
	public void atualizarArquivoFuncionarioCondominio(Arquivo arquivo) throws FileNotFoundException, IOException, SQLException, Exception {
		StringBuffer query = new StringBuffer();
		query.append("UPDATE ");
		query.append(ARQUIVO);
		query.append(" SET ");
		query.append(NOME);
		query.append("= ?, ");
		query.append(ID_USUARIO);
		query.append("= ?, "); 
		query.append(ID_CONDOMINIO);
		query.append("= ?, ");
// TODO: Código comentado em 01/10/2014. Apagar em 180 dias		
//		query.append(ID_AMBIENTE);		
//		query.append("= ?, ");
		query.append(ID_CLASSIFICADOS);		
		query.append("= ?, ");
		query.append(MIME_TYPE);
		query.append("= ? ");
		query.append("WHERE ");
		query.append(ID);
		query.append("= ?");
		Connection con = Conexao.getConexao();
		PreparedStatement statement = null;
		try {			
			statement = con.prepareStatement(query.toString());
			SQLUtil.setValorPpreparedStatement(statement, 1, arquivo.getNome(), java.sql.Types.VARCHAR);			
			SQLUtil.setValorPpreparedStatement(statement, 2, arquivo.getIdUsuario(), java.sql.Types.INTEGER);
			SQLUtil.setValorPpreparedStatement(statement, 3, arquivo.getIdCondominio(), java.sql.Types.INTEGER);
// TODO: Código comentado em 01/10/2014. Apagar em 180 dias			
//			SQLUtil.setValorPpreparedStatement(statement, 4, arquivo.getIdAmbiente(), java.sql.Types.INTEGER);
			SQLUtil.setValorPpreparedStatement(statement, 4, arquivo.getIdClassificados(), java.sql.Types.INTEGER);
			SQLUtil.setValorPpreparedStatement(statement, 5, arquivo.getMimeType(), java.sql.Types.VARCHAR);
			SQLUtil.setValorPpreparedStatement(statement, 6, arquivo.getId(), java.sql.Types.INTEGER);
			Funcionario funcionario = this.funcionarioDAO.buscarPorId(arquivo.getIdUsuario(),con);
			Condominio condominio = this.condominioDAO.buscarCondominioPorId(funcionario.getIdCondominio(), con);
			statement.executeUpdate();
			arquivo.setIdCondominio(condominio.getId());
			this.fileDAO.atualizarFile(arquivo);
			
//			String enderecoDiretorio = this.enderecoArquivos+File.separator+condominio.getId()+File.separator+arquivo.getId();
//			File fileDiretorio = new File(enderecoDiretorio);
//			File[] listaArquivos = fileDiretorio.listFiles();
//			listaArquivos[0].delete();			
//			String enderecoArquivo = this.enderecoArquivos+File.separator+condominio.getId()+File.separator+arquivo.getId()+File.separator+arquivo.getNome();
//			File file = new File(enderecoArquivo);			
//			FileOutputStream outPut = new FileOutputStream(file);
//			outPut.write(arquivo.getDadosArquivo());
//			outPut.flush();
//			outPut.close();		
		} catch (FileNotFoundException e) {					
			throw e;
		} catch (IOException e) {
			throw e;
		} catch (SQLException e) {
			throw e;
		}catch (Exception e) {
			throw e;
		}finally{
			try {
				statement.close();
				con.close();				
			} catch (SQLException e) {
				logger.error("erro sqlstate "+e.getSQLState(), e);
			}	
		}	
	}

	@Override
	public void atualizarArquivoFuncionarioConjuntoBloco(Arquivo arquivo) throws FileNotFoundException, IOException, SQLException, Exception {
		StringBuffer query = new StringBuffer();
		query.append("UPDATE ");
		query.append(ARQUIVO);
		query.append(" SET ");
		query.append(NOME);
		query.append("= ?, ");
		query.append(ID_USUARIO);
		query.append("= ?, "); 
		query.append(ID_CONDOMINIO);
		query.append("= ?, ");
// TODO: Código comentado em 01/10/2014. Apagar em 180 dias		
//		query.append(ID_AMBIENTE);		
//		query.append("= ?, ");
		query.append(ID_CLASSIFICADOS);		
		query.append("= ?, ");
		query.append(MIME_TYPE);
		query.append("= ? ");
		query.append("WHERE ");
		query.append(ID);
		query.append("= ?");
		Connection con = Conexao.getConexao();
		PreparedStatement statement = null;
		try {			
			statement = con.prepareStatement(query.toString());
			SQLUtil.setValorPpreparedStatement(statement, 1, arquivo.getNome(), java.sql.Types.VARCHAR);			
			SQLUtil.setValorPpreparedStatement(statement, 2, arquivo.getIdUsuario(), java.sql.Types.INTEGER);
			SQLUtil.setValorPpreparedStatement(statement, 3, arquivo.getIdCondominio(), java.sql.Types.INTEGER);
// TODO: Código comentado em 01/10/2014. Apagar em 180 dias			
//			SQLUtil.setValorPpreparedStatement(statement, 4, arquivo.getIdAmbiente(), java.sql.Types.INTEGER);
			SQLUtil.setValorPpreparedStatement(statement, 4, arquivo.getIdClassificados(), java.sql.Types.INTEGER);
			SQLUtil.setValorPpreparedStatement(statement, 5, arquivo.getMimeType(), java.sql.Types.VARCHAR);
			SQLUtil.setValorPpreparedStatement(statement, 6, arquivo.getId(), java.sql.Types.INTEGER);
			Funcionario funcionario = this.funcionarioDAO.buscarPorId(arquivo.getIdUsuario(),con);
			Condominio condominio = this.recuperaCondominioPorIdConjuntoBloco(funcionario.getIdConjuntoBloco(), con);
			statement.executeUpdate();
			arquivo.setIdCondominio(condominio.getId());
			this.fileDAO.atualizarFile(arquivo);
			
// TODO: Código comentado em 18/09/2014. Apagar em 180 dias			
//			String enderecoDiretorio = this.enderecoArquivos+File.separator+condominio.getId()+File.separator+arquivo.getId();
//			File fileDiretorio = new File(enderecoDiretorio);
//			File[] listaArquivos = fileDiretorio.listFiles();
//			listaArquivos[0].delete();			
//			String enderecoArquivo = this.enderecoArquivos+File.separator+condominio.getId()+File.separator+arquivo.getId()+File.separator+arquivo.getNome();
//			File file = new File(enderecoArquivo);			
//			FileOutputStream outPut = new FileOutputStream(file);
//			outPut.write(arquivo.getDadosArquivo());
//			outPut.flush();
//			outPut.close();		
		} catch (FileNotFoundException e) {					
			throw e;
		} catch (IOException e) {
			throw e;
		} catch (SQLException e) {
			throw e;
		}catch (Exception e) {
			throw e;
		}finally{
			try {
				statement.close();
				con.close();				
			} catch (SQLException e) {
				logger.error("erro sqlstate "+e.getSQLState(), e);
			}	
		}
	}
	
	@Override
	public void excluirArquivoFuncionarioConjuntoBloco(Arquivo arquivo, Connection con) throws SQLException, Exception {
		Funcionario funcionario = this.funcionarioDAO.buscarPorId(arquivo.getIdUsuario(),con);
		Condominio condominio = null;
		PreparedStatement statement = null;	 		
		try {
			condominio = this.recuperaCondominioPorIdConjuntoBloco(funcionario.getIdConjuntoBloco(), con);
			StringBuffer query = new StringBuffer();
			query.append("DELETE FROM ");
			query.append(ARQUIVO);
			query.append(" WHERE ");		
			query.append(ID);
			query.append(" = ?");
			statement = con.prepareStatement(query.toString());
			SQLUtil.setValorPpreparedStatement(statement, 1, arquivo.getId(), java.sql.Types.INTEGER);
			statement.executeUpdate();
			arquivo.setIdCondominio(condominio.getId());
			if(this.commit == Boolean.FALSE){
				this.listaFile.add(arquivo);
			}else{
				// Exclui o arquivo
				this.fileDAO.excluirFile(arquivo);			
			}

// TODO: Código comentado em 17/09/2014. Apagar em 180 dias					
//			String enderecoArquivo = this.enderecoArquivos+File.separator+condominio.getId()+File.separator+arquivo.getId()+File.separator+arquivo.getNome();
//			File file = new File(enderecoArquivo);
//			if(this.commit == Boolean.FALSE){
//				this.listaFile.add(file);
//			}else{
//				// Exclui o arquivo
//				file.delete();				
//			}	
//			String enderecoDiretorio = this.enderecoArquivos+File.separator+condominio.getId()+File.separator+arquivo.getId();
//			File diretorio = new File(enderecoDiretorio);	
//			if(this.commit == Boolean.FALSE){
//				this.listaFile.add(diretorio);
//			}else{
//				// Exclui o diretório
//				diretorio.delete();				
//			}	
		} catch (SQLException e) {
			con.rollback();
			throw e;
		} catch (Exception e) {
			con.rollback();
			throw e;
		}
	}
	
	@Override
	public List<Arquivo> buscarArquivoPorTiposEIdCondominio(List<String> listaTipoArquivos, Integer idCondominio) throws FileNotFoundException, IOException, SQLException, Exception {
		PreparedStatement statement = null;
		List<Arquivo> listaArquivo = new ArrayList<Arquivo>();
		Connection con = Conexao.getConexao();		
		try {
			StringBuffer query = new StringBuffer();
			query.append("SELECT * FROM ");
			query.append(ARQUIVO );
			query.append(" WHERE ");
			query.append(ID_CONDOMINIO);
			query.append(" = ?");
			query.append(" AND ");			
			query.append(MIME_TYPE);
			query.append(" in (?");
			for (int i = 0; i < listaTipoArquivos.size()-1; i++) {
				query.append(",?");
			}
			query.append(")");
			query.append(" ORDER BY ");
			query.append(NOME);
			ResultSet resultSet = null;	
			Arquivo arquivo = null;
	//		byte[] dadosArquivo = null;	
	//		File file = null;			
			statement = con.prepareStatement(query.toString());
			SQLUtil.setValorPpreparedStatement(statement,1, idCondominio, java.sql.Types.INTEGER);
			for (int i = 2; i <= listaTipoArquivos.size()+1; i++) {
				SQLUtil.setValorPpreparedStatement(statement,i, listaTipoArquivos.get(i-2), java.sql.Types.VARCHAR);
			}		
			resultSet = statement.executeQuery();
			while(resultSet.next()){
				arquivo = new Arquivo();				
				arquivo.setId((Integer) SQLUtil.getValorResultSet(resultSet, ID, java.sql.Types.INTEGER));
//				arquivo.setIdAmbiente((Integer) SQLUtil.getValorResultSet(resultSet, ID_AMBIENTE, java.sql.Types.INTEGER));
				arquivo.setIdCondominio((Integer) SQLUtil.getValorResultSet(resultSet, ID_CONDOMINIO, java.sql.Types.INTEGER));
				arquivo.setIdUsuario((Integer) SQLUtil.getValorResultSet(resultSet, ID_USUARIO, java.sql.Types.INTEGER));
				arquivo.setIdClassificados((Integer) SQLUtil.getValorResultSet(resultSet, ID_CLASSIFICADOS, java.sql.Types.INTEGER));
				arquivo.setMimeType(String.valueOf(SQLUtil.getValorResultSet(resultSet, MIME_TYPE, java.sql.Types.VARCHAR)));
				arquivo.setNome(String.valueOf(SQLUtil.getValorResultSet(resultSet, NOME, java.sql.Types.VARCHAR)));
				arquivo.setDadosArquivo(this.fileDAO.buscaDadosFile(arquivo, arquivo.getIdCondominio()));
				listaArquivo.add(arquivo);
			
// TODO: Código comentado em 18/09/2014. Apagar em 180 dias					
//				file = new File(this.enderecoArquivos+File.separator+arquivo.getIdCondominio()+File.separator+arquivo.getId()+File.separator+arquivo.getNome());
//				dadosArquivo = new byte[(int) file.length()];
//				InputStream ios = new FileInputStream(file);
//				DataInputStream dis = new DataInputStream (ios);			      
//				dis.readFully (dadosArquivo);			        
//				dis.close();
//				ios.close();
//				arquivo.setDadosArquivo(dadosArquivo);
			}  
		} catch (FileNotFoundException e) {					
			throw e;
		} catch (IOException e) {
			throw e;
		} catch (SQLException e) {
			throw e;
		}catch (Exception e) {
			throw e;
		}finally{
			try {
				statement.close();
				con.close();				
			} catch (SQLException e) {
				logger.error("erro sqlstate "+e.getSQLState(), e);
			}	
		}		

		return listaArquivo;
	}
	
	@Override
	public Arquivo buscarPorClassificados(Classificados classificados, Connection con) throws FileNotFoundException, IOException, SQLException, Exception {
		StringBuffer query = new StringBuffer();
		query.append("SELECT * FROM ");
		query.append(ARQUIVO );
		query.append(" WHERE ");
		query.append(ID_CLASSIFICADOS);
		query.append(" = ?");
		query.append(";");
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;	
		Arquivo arquivo = null;	
//		File file = null;
//		byte[] dadosArquivo = null;		
		try {
			preparedStatement = con.prepareStatement(query.toString());
			SQLUtil.setValorPpreparedStatement(preparedStatement,1, classificados.getId(), java.sql.Types.INTEGER);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()){
				arquivo = new Arquivo();
				arquivo.setId((Integer) SQLUtil.getValorResultSet(resultSet, ID, java.sql.Types.INTEGER));
// TODO: Código comentado em 01/10/2014. Apagar em 180 dias				
//				arquivo.setIdAmbiente((Integer) SQLUtil.getValorResultSet(resultSet, ID_AMBIENTE, java.sql.Types.INTEGER));
				arquivo.setIdCondominio((Integer) SQLUtil.getValorResultSet(resultSet, ID_CONDOMINIO, java.sql.Types.INTEGER));
				arquivo.setIdUsuario((Integer) SQLUtil.getValorResultSet(resultSet, ID_USUARIO, java.sql.Types.INTEGER));
				arquivo.setIdClassificados((Integer) SQLUtil.getValorResultSet(resultSet, ID_CLASSIFICADOS, java.sql.Types.INTEGER));
				arquivo.setMimeType(String.valueOf(SQLUtil.getValorResultSet(resultSet, MIME_TYPE, java.sql.Types.VARCHAR)));
				arquivo.setNome(String.valueOf(SQLUtil.getValorResultSet(resultSet, NOME, java.sql.Types.VARCHAR)));
				arquivo.setDadosArquivo(this.fileDAO.buscaDadosFile(arquivo, classificados.getIdCondominio()));
				
// TODO: Código comentado em 18/09/2014. Apagar em 180 dias		
//				file = new File(this.enderecoArquivos+File.separator+classificados.getIdCondominio()+File.separator+arquivo.getId()+File.separator+arquivo.getNome());
//				dadosArquivo = new byte[(int) file.length()];
//				InputStream ios = new FileInputStream(file);
//				DataInputStream dis = new DataInputStream (ios);			      
//				dis.readFully (dadosArquivo);			        
//				dis.close();
//				ios.close();
			}    
		} catch (FileNotFoundException e) {			
			con.rollback();
			throw e;
		} catch (IOException e) {
			con.rollback();
			throw e;
		} catch (SQLException e) {
			con.rollback();
			throw e;
		} catch (Exception e) {
			con.rollback();
			throw e;
		}
		return arquivo;			
	}	
	
	@Override
	public void excluirArquivoCondominio(Arquivo arquivo) throws  SQLException, Exception {
		Connection con = Conexao.getConexao();
		PreparedStatement statement = null;	 		
		try {
			StringBuffer query = new StringBuffer();
			query.append("DELETE FROM ");
			query.append(ARQUIVO);
			query.append(" WHERE ");		
			query.append(ID);
			query.append(" = ?");
			statement = con.prepareStatement(query.toString());
			SQLUtil.setValorPpreparedStatement(statement, 1, arquivo.getId(), java.sql.Types.INTEGER);
			statement.executeUpdate();
			if(this.commit == Boolean.FALSE){
				this.listaFile.add(arquivo);
			}else{
				// Exclui o arquivo
				this.fileDAO.excluirFile(arquivo);			
			}
// TODO: Código comentado em 17/09/2014. Apagar em 180 dias			
//			String enderecoArquivo = this.enderecoArquivos+File.separator+arquivo.getIdCondominio()+File.separator+arquivo.getId()+File.separator+arquivo.getNome();
//			File file = new File(enderecoArquivo);
//			if(this.commit == Boolean.FALSE){
//				this.listaFile.add(file);
//			}else{
//				// Exclui o arquivo
//				file.delete();				
//			}	
//			String enderecoDiretorio = this.enderecoArquivos+File.separator+arquivo.getIdCondominio()+File.separator+arquivo.getId();
//			File diretorio = new File(enderecoDiretorio);	
//			if(this.commit == Boolean.FALSE){
//				this.listaFile.add(diretorio);
//			}else{
//				// Exclui o diretório
//				diretorio.delete();				
//			}	
		} catch (SQLException e) {			
			throw e;
		}catch (Exception e) {
			throw e;
		}finally{
			try {
				statement.close();
				con.close();				
			} catch (SQLException e) {
				logger.error("erro sqlstate "+e.getSQLState(), e);
			}	
		}		
	}

	@Override
	public void excluirArquivoClassificados(Arquivo arquivo, Connection con) throws SQLException, Exception {
		PreparedStatement statement = null;	 		
		try {
			StringBuffer query = new StringBuffer();
			query.append("DELETE FROM ");
			query.append(ARQUIVO);
			query.append(" WHERE ");		
			query.append(ID);
			query.append(" = ?");
			statement = con.prepareStatement(query.toString());
			SQLUtil.setValorPpreparedStatement(statement, 1, arquivo.getId(), java.sql.Types.INTEGER);
			statement.executeUpdate();
			Condominio condominio = this.recuperaCondominioPorIdClassificados(arquivo.getIdClassificados(), con);
			arquivo.setIdCondominio(condominio.getId());
			if(this.commit == Boolean.FALSE){
				this.listaFile.add(arquivo);
			}else{
				// Exclui o arquivo
				this.fileDAO.excluirFile(arquivo);			
			}
// TODO: Código comentado em 17/09/2014. Apagar em 180 dias		
//			String enderecoArquivo = this.enderecoArquivos+File.separator+condominio.getId()+File.separator+arquivo.getId()+File.separator+arquivo.getNome();
//			File file = new File(enderecoArquivo);
//			if(this.commit == Boolean.FALSE){
//				this.listaFile.add(file);
//			}else{
//				// Exclui o arquivo
//				file.delete();				
//			}
//			String enderecoDiretorio = this.enderecoArquivos+File.separator+condominio.getId()+File.separator+arquivo.getId();
//			File diretorio = new File(enderecoDiretorio);	
//			if(this.commit == Boolean.FALSE){
//				this.listaFile.add(diretorio);
//			}else{
//				// Exclui o diretório
//				diretorio.delete();				
//			}	
		} catch (SQLException e) {
			con.rollback();			
			throw e;
		} catch (Exception e) {
			con.rollback();
			throw e;
		}
		
	}

	@Override
	public void salvarArquivoClassificados(Arquivo arquivo, Connection con) throws  SQLException, Exception  {
		PreparedStatement statement = null;	 		
		try {
			StringBuffer query = new StringBuffer();
			query.append("INSERT INTO "); 
			query.append(ARQUIVO); 
			query.append("(");
			query.append(ID_USUARIO); 
			query.append(",");
			query.append(ID_CONDOMINIO); 
			query.append(",");
// TODO: Código comentado em 01/10/2014. Apagar em 180 dias			
//			query.append(ID_AMBIENTE); 
//			query.append(",");			
			query.append(ID_CLASSIFICADOS); 
			query.append(",");			
			query.append(NOME); 
			query.append(",");			
			query.append(MIME_TYPE);
			query.append(") ");
			query.append("VALUES(?,?,?,?,?)");
			statement = con.prepareStatement(query.toString(), PreparedStatement.RETURN_GENERATED_KEYS);
			SQLUtil.setValorPpreparedStatement(statement, 1, arquivo.getIdUsuario(), java.sql.Types.INTEGER);
			SQLUtil.setValorPpreparedStatement(statement, 2, arquivo.getIdCondominio(), java.sql.Types.INTEGER);
// TODO: Código comentado em 01/10/2014. Apagar em 180 dias			
//			SQLUtil.setValorPpreparedStatement(statement, 3, arquivo.getIdAmbiente(), java.sql.Types.INTEGER);
			SQLUtil.setValorPpreparedStatement(statement, 3, arquivo.getIdClassificados(), java.sql.Types.INTEGER);
			SQLUtil.setValorPpreparedStatement(statement, 4, arquivo.getNome(), java.sql.Types.VARCHAR);
			SQLUtil.setValorPpreparedStatement(statement, 5, arquivo.getMimeType(), java.sql.Types.VARCHAR);
			statement.executeUpdate();
			ResultSet rs = statement.getGeneratedKeys();			
			rs.next();			
			arquivo.setId(rs.getInt(1));
			Condominio condominio = this.recuperaCondominioPorIdClassificados(arquivo.getIdClassificados(), con);
			this.fileDAO.criarFile(arquivo, condominio.getId());
		
// TODO: Código comentado em 18/09/2014. Apagar em 180 dias				
//			String endereco = this.enderecoArquivos+File.separator+condominio.getId()+File.separator+arquivo.getId();
//			File diretorio = new File(endereco);	
//			// Cria o diretório com o nome igual ao id do registro no Banco de dados (TABELA ARQUIVO)
//			diretorio.mkdir();	
//			endereco += File.separator+arquivo.getNome();
//			FileOutputStream outPut = new FileOutputStream(endereco);
//			outPut.write(arquivo.getDadosArquivo());
//			outPut.flush();
//			outPut.close();				  
		} catch (SQLException e) {
			con.rollback();
			throw e;
		} catch (Exception e) {
			con.rollback();
			throw e;
		}
	}

	@Override
	public void atualizarArquivoClassificados(Arquivo arquivo, Connection con) throws FileNotFoundException, IOException, SQLException, Exception  {
		StringBuffer query = new StringBuffer();
		query.append("UPDATE ");
		query.append(ARQUIVO);
		query.append(" SET ");
		query.append(NOME);
		query.append(" = ?, ");
		query.append(ID_USUARIO);
		query.append(" = ?, "); 
		query.append(ID_CONDOMINIO);
		query.append(" = ?, ");
// TODO: Código comentado em 01/10/2014. Apagar em 180 dias	
//		query.append(ID_AMBIENTE);		
//		query.append(" = ?, ");
		query.append(ID_CLASSIFICADOS);		
		query.append(" = ?, ");
		query.append(MIME_TYPE);
		query.append(" = ? ");
		query.append("WHERE ");
		query.append(ID);
		query.append(" = ?");
		PreparedStatement statement = null;
		try {			
			statement = con.prepareStatement(query.toString());
			SQLUtil.setValorPpreparedStatement(statement, 1, arquivo.getNome(), java.sql.Types.VARCHAR);			
			SQLUtil.setValorPpreparedStatement(statement, 2, arquivo.getIdUsuario(), java.sql.Types.INTEGER);
			SQLUtil.setValorPpreparedStatement(statement, 3, arquivo.getIdCondominio(), java.sql.Types.INTEGER);
// TODO: Código comentado em 01/10/2014. Apagar em 180 dias			
//			SQLUtil.setValorPpreparedStatement(statement, 4, arquivo.getIdAmbiente(), java.sql.Types.INTEGER);
			SQLUtil.setValorPpreparedStatement(statement, 4, arquivo.getIdClassificados(), java.sql.Types.INTEGER);
			SQLUtil.setValorPpreparedStatement(statement, 5, arquivo.getMimeType(), java.sql.Types.VARCHAR);
			SQLUtil.setValorPpreparedStatement(statement, 6, arquivo.getId(), java.sql.Types.INTEGER);
			statement.executeUpdate();
			Condominio condominio = this.recuperaCondominioPorIdClassificados(arquivo.getIdClassificados(), con);			
			arquivo.setIdCondominio(condominio.getId());
			this.fileDAO.atualizarFile(arquivo);	
			
			
//			String enderecoDiretorio = this.enderecoArquivos+File.separator+condominio.getId()+File.separator+arquivo.getId();
//			File fileDiretorio = new File(enderecoDiretorio);
//			File[] listaArquivos = fileDiretorio.listFiles();			
//			listaArquivos[0].delete();
//			String enderecoArquivo = this.enderecoArquivos+File.separator+condominio.getId()+File.separator+arquivo.getId()+File.separator+arquivo.getNome();
//			File file = new File(enderecoArquivo);			
//			FileOutputStream outPut = new FileOutputStream(file);
//			outPut.write(arquivo.getDadosArquivo());
//			outPut.flush();
//			outPut.close();		
		} catch (FileNotFoundException e) {			
			con.rollback();
			throw e;
		} catch (IOException e) {
			con.rollback();
			throw e;
		} catch (SQLException e) {
			con.rollback();
			throw e;
		} catch (Exception e) {
			con.rollback();
			throw e;
		}
	}
	
	private Condominio recuperaCondominioPorIdClassificados(Integer idClassificados, Connection con)
			throws  SQLException, Exception  {		
		Classificados classificados = this.classificadosDAO.buscarPorIdClassificadosSemImagem(idClassificados, con);
		Condominio condominio = this.condominioDAO.buscarCondominioPorId(classificados.getIdCondominio(), con);
		return condominio;
	}	
	
	public String getEnderecoArquivos() {
		return enderecoArquivos;
	}

	public void setEnderecoArquivos(String enderecoArquivos) {
		this.enderecoArquivos = enderecoArquivos;
	}

	private Condominio recuperaCondominio(Arquivo arquivo, Connection con) throws  SQLException, Exception {
		// Necessário chamar um método que busca um condômino sem imagem, para evitar o loop infinito.
		Condomino condomino = this.condominoDAO.buscarCondominoPorIdSemImagem(arquivo.getIdUsuario(),con);		
		Unidade unidade = this.unidadeDAO.buscarPorId(condomino.getIdUnidade(), con);
		Bloco bloco = this.blocoDAO.buscarPorId(unidade.getIdBloco(), con);		
		Condominio condominio = new Condominio();
		condominio.setId(bloco.getIdCondominio());
		return condominio;
	}
	
	private Condominio recuperaCondominioPorIdConjuntoBloco(Integer idConjuntoBloco, Connection con) throws SQLException, Exception {		
		ConjuntoBloco conjuntoBloco = this.conjuntoBlocoDAO.buscarPorId(idConjuntoBloco, con);
		List<BlocoConjuntoBloco> listaBlocoConjuntoBloco = this.blocoConjuntoBlocoDAO.buscarPorIdConjuntoBloco(conjuntoBloco.getId(), con);	
		Condominio condominio = this.condominioDAO.buscarCondominioPorId(listaBlocoConjuntoBloco.get(0).getBloco().getIdCondominio(), con);
		return condominio;
	}

	@Override
	public void criarDiretorioCondominio(Condominio condominio) {
		this.fileDAO.criarDiretorioCondominio(condominio);		
	}

	@Override
	public void excluirDiretorioCondominio(Condominio condominio) {
		this.fileDAO.excluirDiretorioCondominio(condominio);		
	}
	
}