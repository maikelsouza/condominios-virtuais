package br.com.condominiosvirtuais.entity;

/**
 * Entidade que representa um arquivo (.pdf, .jpeg e etc) que é salvo na aplicação. <br>
 * Nesses casos, o arquivo propriamente dito, é persistido no disco (endereço na tabela {@link ConfiguracaoAplicacao}) e as <br>
 * informações (nome, tipo e etc) sobre esse arquivo são armazenados no BD.
 * 
 * @author Maikel Joel de Souza
 * @since 13/09/2013
 */
public class Arquivo {
	
	private Integer id;
	
	private Integer idCondominio;
	
	private Integer idUsuario;

// TODO: Código comentado em 01/10/2014. Apagar em 180 dias	
//	private Integer idAmbiente;
	
	private Integer idClassificados;
	
	private String nome;
	
	private String mimeType;
	
	private byte[] dadosArquivo;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getIdCondominio() {
		return idCondominio;
	}

	public void setIdCondominio(Integer idCondominio) {
		this.idCondominio = idCondominio;
	}

	public Integer getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Integer idUsuario) {
		this.idUsuario = idUsuario;
	}

// TODO: Código comentado em 01/10/2014. Apagar em 180 dias	
//	public Integer getIdAmbiente() {
//		return idAmbiente;
//	}
//
//	public void setIdAmbiente(Integer idAmbiente) {
//		this.idAmbiente = idAmbiente;
//	}	

	public Integer getIdClassificados() {
		return idClassificados;
	}

	public void setIdClassificados(Integer idClassificados) {
		this.idClassificados = idClassificados;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getMimeType() {
		return mimeType;
	}

	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}

	public byte[] getDadosArquivo() {
		return dadosArquivo;
	}

	public void setDadosArquivo(byte[] dadosArquivo) {
		this.dadosArquivo = dadosArquivo;
	}

}
