package br.com.condominiosvirtuais.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.richfaces.event.FileUploadEvent;
import org.richfaces.model.UploadedFile;

import br.com.condominiosvirtuais.util.ManagedBeanUtil;

public class FileUploadMB implements Serializable{		
	
	private static final long serialVersionUID = 1L;
	
	private static Logger logger = Logger.getLogger(FileUploadMB.class);

	private ArrayList<byte[]> arquivos = new ArrayList<byte[]>();
	
	private String qtd = "1";
		
	 public void listener(FileUploadEvent event){
		 UploadedFile item = event.getUploadedFile();		
	     this.arquivos.add(item.getData());	
	 }
	 
	 public void paint(OutputStream stream, Object object){		 
	    try {
			stream.write(this.arquivos.get((Integer) object));
			stream.close();		
		} catch (IOException e) {
			logger.error("", e);	
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		} catch (Exception e) {
			logger.error("", e);
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		}		
	 }
	 
	 public String limparArquivos() {
	     this.arquivos.clear();
	     return null;
	 }
	 
	 public long getTimeStamp() {
	     return System.currentTimeMillis();
	 }
	 
	 public int getSize() {
        if (getArquivos().size() > 0) {
            return getArquivos().size();
        } else {
            return 0;
        }
	 }	 

	 public ArrayList<byte[]> getArquivos() {
		 return arquivos;
	 }
	
	 public void setArquivos(ArrayList<byte[]> arquivos) {
		 this.arquivos = arquivos;
	 }

	public String getQtd() {
		return qtd;
	}

	public void setQtd(String qtd) {
		this.qtd = qtd;
	} 

}
