 package br.com.condominiosvirtuais.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Instance;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.richfaces.event.FileUploadEvent;
import org.richfaces.model.UploadedFile;

import br.com.condominiosvirtuais.entity.Arquivo;
import br.com.condominiosvirtuais.entity.Condominio;
import br.com.condominiosvirtuais.enumeration.MimeTypeEnum;
import br.com.condominiosvirtuais.service.ArquivoService;
import br.com.condominiosvirtuais.util.ManagedBeanUtil;

@Named @SessionScoped
public class ArquivoMB implements  Serializable{
	
	private static final long serialVersionUID = 1L;

	private static Logger logger = Logger.getLogger(ArquivoMB.class);
	
	private Condominio condominio = null;
	
	private DataModel<Arquivo> listaDeDocumentos;
	
	private ListDataModel<Arquivo> listaDeImagens;
	
	private List<SelectItem> listaCondominios = null;	
	
	private ArrayList<Arquivo> arquivos = new ArrayList<Arquivo>();
	
	@Inject
	private ArquivoService arquivoService;	
	
	@Inject
	private Instance<CondominioMB> condominioMB = null;
	
	@PostConstruct
	public void iniciarArquivoMB(){
		this.condominio = new Condominio();
	}
	
	
	 public void paintImagemListagem(OutputStream stream, Object object){	 
		try {		
			@SuppressWarnings("unchecked")
			List<Arquivo> listArquivo = (List <Arquivo>) this.listaDeImagens.getWrappedData();
			Arquivo arquivo =  listArquivo.get((Integer) object);		
			stream.write(arquivo.getDadosArquivo());
			stream.close();
		} catch (IOException e) {
			logger.error("", e);
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		} catch (Exception e) {
			logger.error("", e);
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		}
	 }
	 
	 public void paintImagemAnexo(OutputStream stream, Object object){			
		 try {
			 Arquivo arquivo  = this.arquivos.get(((Integer) object));
			 stream.write(arquivo.getDadosArquivo());
			 stream.close();
		} catch (IOException e) {
			logger.error("", e);
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		} catch (Exception e) {
			logger.error("", e);
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		}
	 }

	public void listener(FileUploadEvent event) {
		 UploadedFile item = event.getUploadedFile();
		 Arquivo arquivo =  new Arquivo();	 
		 arquivo.setIdCondominio(this.condominio.getId());
		 arquivo.setDadosArquivo(item.getData());
		 arquivo.setNome(event.getUploadedFile().getName());
		 arquivo.setMimeType(event.getUploadedFile().getContentType());
		 this.arquivos.clear();
	     this.arquivos.add(arquivo);	
	} 
	
	public void pesquisarDocumentos(ActionEvent event){
		try {
			List<String> listaTipoArquivos = new ArrayList<String>();
			listaTipoArquivos.add(MimeTypeEnum.PDF.getMimeType());
			listaTipoArquivos.add(MimeTypeEnum.DOC.getMimeType());
			listaTipoArquivos.add(MimeTypeEnum.DOCX.getMimeType());
			listaTipoArquivos.add(MimeTypeEnum.XLS.getMimeType());
			listaTipoArquivos.add(MimeTypeEnum.XLSX.getMimeType());
			listaTipoArquivos.add(MimeTypeEnum.PPT.getMimeType());
			listaTipoArquivos.add(MimeTypeEnum.PPTX.getMimeType());
			listaTipoArquivos.add(MimeTypeEnum.TXT.getMimeType());
			this.listaDeDocumentos = new ListDataModel<Arquivo>(this.arquivoService.buscarArquivoPorTiposEIdCondominio(listaTipoArquivos,this.condominio.getId()));
			if (this.listaDeDocumentos.getRowCount() == 0){
				ManagedBeanUtil.setMensagemInfo("msg.arquivo.documento.semDocumento");
			}
		} catch (FileNotFoundException e) {		
			logger.error("", e);
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		} catch (IOException e) {
			logger.error("", e);
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		} catch (SQLException e) {
			logger.error("erro sqlstate "+e.getSQLState(), e);
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		} catch (Exception e) {
			logger.error("", e);
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		}
	}	
	
	public void pesquisarImagens(ActionEvent event){
		try {
			List<String> listaTipoArquivos = new ArrayList<String>();
			listaTipoArquivos.add(MimeTypeEnum.JPEG.getMimeType());		
			listaTipoArquivos.add(MimeTypeEnum.PNG.getMimeType());		
			this.listaDeImagens =  new ListDataModel<Arquivo>(this.arquivoService.buscarArquivoPorTiposEIdCondominio(listaTipoArquivos,this.condominio.getId()));
			if (this.listaDeImagens.getRowCount() == 0){
				ManagedBeanUtil.setMensagemInfo("msg.arquivo.imagem.semImagem");
			}                                    
		} catch (FileNotFoundException e) {		
			logger.error("", e);
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		} catch (IOException e) {
			logger.error("", e);
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		} catch (SQLException e) {
			logger.error("erro sqlstate "+e.getSQLState(), e);
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		} catch (Exception e) {
			logger.error("", e);
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		}
	}
	
	public void downloadDocumento(ActionEvent actionEvent){		 
		 Arquivo arquivo = this.listaDeDocumentos.getRowData();
         HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
         try {                                 
              response.setContentType(arquivo.getMimeType());
              response.setHeader("Content-Disposition", "attachment; filename="+arquivo.getNome());
              OutputStream output = response.getOutputStream();
              output.write(arquivo.getDadosArquivo());
              response.flushBuffer();
              FacesContext.getCurrentInstance().responseComplete();
         } catch (IOException e) {
        	 logger.error("", e);
        	 ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao"); 			
         } catch (Exception e) {
        	 logger.error("", e);
        	 ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
         }
	 }
	 
	public String abrirTelaAnexarDocumento(){	
		this.arquivos.clear();
		return "anexarDocumento";
	}
	
	public String abrirTelaAnexarImagem(){
		this.arquivos.clear();
		return "anexarImagem";
	}
	 
	public void excluirDocumento(ActionEvent event){
		try {
			Arquivo arquivo = this.listaDeDocumentos.getRowData();			
			this.arquivoService.excluirArquivoCondominio(arquivo);
			this.pesquisarDocumentos(null);
			ManagedBeanUtil.setMensagemInfo("msg.arquivo.documento.excluirSucesso");	
		} catch (SQLException e) {
			logger.error("erro sqlstate "+e.getSQLState(), e);
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		} catch (Exception e) {
			logger.error("", e);
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		}
	}
	
	public String excluirImagem(){	
		try {
			Arquivo arquivo = this.listaDeImagens.getRowData();			
			this.arquivoService.excluirArquivoCondominio(arquivo);
			this.pesquisarImagens(null);
			ManagedBeanUtil.setMensagemInfo("msg.arquivo.imagem.excluirSucesso");
		} catch (SQLException e) {
			logger.error("erro sqlstate "+e.getSQLState(), e);
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		} catch (Exception e) {
			logger.error("", e);
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		}
		return null;
		
	}
	
	public String anexarDocumento(){
		 try {			
			 if (this.arquivos.size() == 0){
				 ManagedBeanUtil.setMensagemErro("msg.arquivo.documento.naoAdicionado");
				 return null;	 
			 }else{
				 this.arquivoService.salvarArquivoCondominio(this.arquivos.get(0));
				 this.pesquisarDocumentos(null);
			 }			 
		 } catch (FileNotFoundException e) {		
			logger.error("", e);
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		 } catch (IOException e) {
			logger.error("", e);
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		 } catch (SQLException e) {
			 logger.error("erro sqlstate "+e.getSQLState(), e);
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		 } catch (Exception e) {
			logger.error("", e);
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		 }
		 return "listarDocumento";
	}
	 
	public String anexarImagem(){
		 try {
			 if (this.arquivos.size() == 0){
				 ManagedBeanUtil.setMensagemErro("msg.arquivo.imagem.naoAdicionado");
				 return null;
			 }else{
				 this.arquivoService.salvarArquivoCondominio(this.arquivos.get(0));
				 this.pesquisarImagens(null);				 
			 }
		 } catch (FileNotFoundException e) {		
			logger.error("", e);
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		} catch (IOException e) {
			logger.error("", e);
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		} catch (SQLException e) {
			logger.error("erro sqlstate "+e.getSQLState(), e);
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		} catch (Exception e) {
			logger.error("", e);
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		}
		 return "listarImagem";
	}
	
//	private Boolean validaMimeTypeDocumento(){
//		Boolean mimeTypePermitido = Boolean.FALSE;
//		String mimeType = this.arquivos.get(0).getMimeType(); 
//		if(mimeType.equals(MimeTypeEnum.DOC.getMimeType()) || mimeType.equals(MimeTypeEnum.DOCX.getMimeType())
//				|| mimeType.equals(MimeTypeEnum.PDF.getMimeType())){
//			mimeTypePermitido = Boolean.TRUE;			
//		}
//		return mimeTypePermitido;
//	}
	
	public DataModel<Arquivo> getListaDeDocumentos() {
		return listaDeDocumentos;
	}

	public void setListaDeDocumentos(DataModel<Arquivo> listaDeDocumentos) {
		this.listaDeDocumentos = listaDeDocumentos;
	}

	public ArrayList<Arquivo> getArquivos() {
		return arquivos;
	}

	public void setArquivos(ArrayList<Arquivo> arquivos) {
		this.arquivos = arquivos;
	}

	public Condominio getCondominio() {
		return condominio;
	}

	public void setCondominio(Condominio condominio) {
		this.condominio = condominio;
	}

	public List<SelectItem> getListaCondominios() {
		this.listaCondominios = this.condominioMB.get().buscarListaCondominiosAtivos();	
		return listaCondominios;
	}

	public void setListaCondominios(List<SelectItem> listaCondominios) {
		this.listaCondominios = listaCondominios;
	}

	public ListDataModel<Arquivo> getListaDeImagens() {
		return listaDeImagens;
	}

	public void setListaDeImagens(ListDataModel<Arquivo> listaDeImagens) {
		this.listaDeImagens = listaDeImagens;
	}
}
