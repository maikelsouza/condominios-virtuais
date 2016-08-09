package br.com.condominiosvirtuais.enumeration;

public enum MimeTypeEnum {
	
	PDF("application/pdf"),
	
	DOC("application/msword"),
	
	DOCX("application/vnd.openxmlformats-officedocument.wordprocessingml.document"),
	
	JPEG("image/jpeg"),
	
	PNG("image/png"),
	
	XLS ("application/vnd.ms-excel"),
	
	XLSX ("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"),
	
	PPT ("application/vnd.ms-powerpoint"),
	
	PPTX ("application/vnd.openxmlformats-officedocument.presentationml.presentation"),	
	
	TXT ("text/plain");
	
	
	
	private String mimeType = null;	
	
	MimeTypeEnum(String mimeType){
		this.mimeType = mimeType;
	}

	public String getMimeType() {
		return mimeType;
	}	

}
