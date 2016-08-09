package br.com.condominiosvirtuais.persistence.impl;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import br.com.condominiosvirtuais.cdi.qualifier.QualifierFileAmazonS3DAO;
import br.com.condominiosvirtuais.entity.Arquivo;
import br.com.condominiosvirtuais.entity.Condominio;
import br.com.condominiosvirtuais.enumeration.ConfiguracaoAplicacaoEnum;
import br.com.condominiosvirtuais.persistence.ConfiguracaoAplicacaoDAO;
import br.com.condominiosvirtuais.persistence.FileDAO;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ListObjectsRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectSummary;

@QualifierFileAmazonS3DAO
public class FileAmazonS3DAOImpl implements FileDAO {

	private AWSCredentials credentials = null; 
	
	private AmazonS3 conn = null;
	
	private static final String BUCKET = "condominiosvirtuais";
	
	private static final String BARRA = "/";
	
	@Inject
	private ConfiguracaoAplicacaoDAO configuracaoAplicacaoDAO;

	@Override @PostConstruct
	public void configuraEnderecoFile() throws SQLException, Exception {
		this.credentials = new BasicAWSCredentials(this.configuracaoAplicacaoDAO.buscarTodas().get(ConfiguracaoAplicacaoEnum.ACCESS_KEY_AMAZON.getChave()),
				this.configuracaoAplicacaoDAO.buscarTodas().get(ConfiguracaoAplicacaoEnum.SECRET_KEY_AMAZON.getChave()));
		this.conn = new AmazonS3Client(this.credentials);
		FileDAO.listaFile.clear();
	}

	@Override
	public void criarFile(Arquivo arquivo, Integer idCondominio) throws Exception {
		ByteArrayInputStream input = new ByteArrayInputStream(arquivo.getDadosArquivo());
		conn.putObject(BUCKET,idCondominio+BARRA+arquivo.getId()+BARRA+arquivo.getNome(), input, new ObjectMetadata());
	}

	@Override
	public void excluirFile(Arquivo arquivo) throws Exception {
		DeleteObjectRequest deleteObjectRequest = new DeleteObjectRequest(BUCKET,arquivo.getIdCondominio()+BARRA+arquivo.getId()+BARRA+arquivo.getNome());
		this.conn.deleteObject(deleteObjectRequest);

	}

	@Override
	public void criarDiretorioCondominio(Condominio condominio) {
		ObjectMetadata metadata = new ObjectMetadata();		
        metadata.setContentLength(0);
        InputStream emptyContent = new ByteArrayInputStream(new byte[0]);
        PutObjectRequest putObjectRequest = new PutObjectRequest(BUCKET, condominio.getId()+BARRA,emptyContent, metadata);
        this.conn.putObject(putObjectRequest);
     }

	@Override
	public void excluirDiretorioCondominio(Condominio condominio) {
		
		DeleteObjectRequest deleteObjectRequest = new DeleteObjectRequest(BUCKET, condominio.getId()+BARRA);     
        this.conn.deleteObject(deleteObjectRequest);
	}

	@Override
	public byte[] buscaDadosFile(Arquivo arquivo, Integer idCondominio) throws FileNotFoundException, IOException {
		GetObjectRequest getObjectRequest = new GetObjectRequest(BUCKET,idCondominio+BARRA+arquivo.getId()+BARRA+arquivo.getNome());
		byte[] dadosArquivo = null;
	    S3Object s3Object = this.conn.getObject(getObjectRequest);
	    InputStream stream = s3Object.getObjectContent();
	    DataInputStream dis = new DataInputStream (stream);
	    dadosArquivo = new byte[(int) s3Object.getObjectMetadata().getContentLength()];
	    dis.readFully(dadosArquivo);			
	    dis.close();
	    stream.close();
	   return dadosArquivo;
	}
	
	@Override
	public void atualizarFile(Arquivo arquivo) throws Exception{
		// O método atualizar foi criado, pois num caso de adotar a opção de chamar o método excluir e depois criar, oarquivo passado como parâmetro seria o novo, 
		// logo ao tentar excluir ele não iria encontrar o aquivo antigo.  
		ListObjectsRequest listObjectsRequest = new ListObjectsRequest().withBucketName(BUCKET).withPrefix(arquivo.getIdCondominio()+BARRA+arquivo.getId());
		S3ObjectSummary s3ObjectSummary = this.conn.listObjects(listObjectsRequest).getObjectSummaries().get(0);
		DeleteObjectRequest deleteObjectRequest = new DeleteObjectRequest(BUCKET,s3ObjectSummary.getKey());
		this.conn.deleteObject(deleteObjectRequest);
		this.criarFile(arquivo, arquivo.getIdCondominio());		
	}

}
