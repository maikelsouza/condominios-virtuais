package br.com.condominiosvirtuais.enumeration;

public enum ConfiguracaoAplicacaoEnum {

	
	PROTOCOLO_EMAIL("protocolo_email"),
	
	PROTOCOLO_EMAIL_SECURITY("protocolo_email_security"),
	
	HOST_EMAIL("host_email"),
	
	USUARIO_EMAIL("usuario_email"),
	
	SENHA_EMAIL("senha_email"),
	
	MAIL_SMTP_AUTH("mail.smtp.auth"),
	
	MAIL_SMTP_PORT("mail.smtp.port"),
	
	QTD_ENVIO_EMAIL_LOTE("qtd_envio_email_lote"),
	
	ENDERECO_ARQUIVOS("endereco_arquivos"),
	
	ENDERECO_EMAIL_ADMIN_APLICACAO("endereco_email_admin_aplicacao"),	
	
	ACCESS_KEY_AMAZON("access_key_amazon"),
	
	SECRET_KEY_AMAZON("secret_key_amazon");
	
	
	
	ConfiguracaoAplicacaoEnum(String chave){
		this.chave = chave;		
	}
	
	private String chave = null;

	
	public String getChave() {
		return chave;
	}
	
}
