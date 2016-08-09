package br.com.condominiosvirtuais.enumeration;


/* TODO: Enum substituido pelo MimeTypeEnum.
Aguardar 90 dias para apagar esse enum. 
 Data 18/01/2016 */
public enum ArquivoEnum {
	

	PDF("application/pdf"),
	
	JPEG("image/jpeg"),
	
	PNG("image/png"),
	
	DOC("application/msword"),
	
	DOCX ("application/vnd.openxmlformats-officedocument.wordprocessingml.document"),
	
	XLS ("application/vnd.ms-excel"),
	
	XLSX ("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"),
	
	PPT ("application/vnd.ms-powerpoint");
	
	
	
	private String mimeType = null;	
	
	ArquivoEnum(String mimeType){
		this.mimeType = mimeType;
	}

	public String getMimeType() {
		return null;
	}	

}
