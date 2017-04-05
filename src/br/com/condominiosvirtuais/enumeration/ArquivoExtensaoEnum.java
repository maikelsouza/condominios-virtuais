package br.com.condominiosvirtuais.enumeration;

public enum ArquivoExtensaoEnum {
	
	PDF("pdf"),
	
	XLS("xls"),
	
	JPG("jpg");
	
	
	private String extensao = null;	
	
	ArquivoExtensaoEnum(String extensao){
		this.extensao = extensao;
	}

	public String getExtensao() {
		return extensao;
	}

}
