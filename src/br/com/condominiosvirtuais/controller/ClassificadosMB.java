package br.com.condominiosvirtuais.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIInput;
import javax.faces.event.ActionEvent;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.log4j.Logger;
import org.richfaces.event.FileUploadEvent;
import org.richfaces.model.UploadedFile;

import br.com.condominiosvirtuais.entity.Arquivo;
import br.com.condominiosvirtuais.entity.Classificados;
import br.com.condominiosvirtuais.entity.Condominio;
import br.com.condominiosvirtuais.service.ClassificadosService;
import br.com.condominiosvirtuais.util.AplicacaoUtil;
import br.com.condominiosvirtuais.util.ManagedBeanUtil;

@Named @SessionScoped
public class ClassificadosMB implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private static Logger logger = Logger.getLogger(ClassificadosMB.class);
	
	@Inject
	private ClassificadosService classificadosService;
	
	private Classificados classificados;
	
	private Condominio condominio;
	
	@Inject
	private CondominioMB condominioMB = null;
	
	private ListDataModel<Classificados> listaClassificados;
	
	private List<SelectItem> listaCondominios = null;	
	
	private ArrayList<Arquivo> arquivos = new ArrayList<Arquivo>();	
	
	private UIInput componenteTituloClassificados;
	
	private UIInput componenteDescricaoClassificados;
	
	private UIInput componenteTelefone1Classificados;
	
	private UIInput componenteTelefone2Classificados;
	
	private UIInput componenteValorClassificados;
	
	private UIInput componenteDataExpiraClassificados;
	
		
	@PostConstruct
	public void iniciarClassificadosMB(){
		this.classificados = new Classificados();
		this.condominio = new Condominio();
		// Configura da data de expiração do anúncio para um mês após a data atual
		this.configurarDataExpira();		
		this.listaCondominios = this.condominioMB.buscarListaCondominiosAtivos();
	}
	
	public void pesquisar(ActionEvent actionEvent){
		// TODO: Descobrir porque repete as imagens as vezes
		//System.out.println(this.condominio.getId());
		try {
			if(this.classificados.getTitulo() != null && this.classificados.getTitulo().trim() != ""){
				this.listaClassificados = new ListDataModel<Classificados>(this.classificadosService.buscarPorCondominioETitulo(this.condominio,  this.classificados.getTitulo()));
			}else{
				this.listaClassificados = new ListDataModel<Classificados>(this.classificadosService.buscarPorCondominio(this.condominio));
			}
			if (this.listaClassificados.getRowCount() == 0){
				ManagedBeanUtil.setMensagemInfo("msg.classifcados.semClassificados");
			}	
		} catch (SQLException e) {
			logger.error("erro sqlstate "+e.getSQLState(), e);	
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");				
		} catch (Exception e) {
			logger.error("", e);
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		}		
	}	
	
	public String abrirTelaCadastrarClassificados(){		 
		this.classificados = new Classificados();
		this.configurarDataExpira();
		this.arquivos.clear();
		return "cadastrar";
	}
	
	public String editarClassificados(){
		this.classificados = this.listaClassificados.getRowData();
		return "editar";
	}
	
	public String atualizarClassificados(){
		String retorno = null;
		try {
			if(this.validaDadosClassificados()){
				//this.classificados.setImagem(!this.arquivos.isEmpty() ? this.arquivos.get(0) : null);
				this.classificadosService.atualizar(this.classificados);
				this.arquivos.add(this.classificados.getImagem());
				this.classificados = new Classificados();
				this.pesquisar(null);
				ManagedBeanUtil.setMensagemInfo("msg.classificados.atualizarSucesso");
			}	
			retorno = "atualizar";
		} catch (SQLException e) {
			logger.error("erro sqlstate "+e.getSQLState(), e);	
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		} catch (Exception e) {
			logger.error("", e);
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		}
		return retorno;
	}
		 
	public void paintListagemClassificados(OutputStream stream, Object object) {		
		try {			
			if (this.condominio.getId() != 0){				
				@SuppressWarnings("unchecked")
				List<Classificados> listClassificados = (List <Classificados>) this.listaClassificados.getWrappedData();				
				Classificados classificados = listClassificados.get((Integer) object);				
				stream.write(classificados.getImagem() != null ? classificados.getImagem().getDadosArquivo() : ManagedBeanUtil.popularImagemNaoDisponivel());
				stream.flush();
				stream.close();			}			
		} catch (IOException e) {
			logger.error("", e);	
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		} catch (Exception e) {
			logger.error("", e);
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		}
	}
	
	public void paintEditarClassificados(OutputStream stream, Object object)  {
		try {
			stream.write(classificados.getImagem() != null ? classificados.getImagem().getDadosArquivo() : ManagedBeanUtil.popularImagemNaoDisponivel());
			stream.close();
		} catch (IOException e) {
			logger.error("", e);	
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		} catch (Exception e) {
			logger.error("", e);
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		}
	}
	
	public void paintCadastroClassificados(OutputStream stream, Object object)  {
		 try {
			 Arquivo arquivo = this.arquivos.get((Integer) object);
			 this.classificados.setImagem(arquivo);	     
			stream.write(this.classificados.getImagem() != null ? this.classificados.getImagem().getDadosArquivo() : ManagedBeanUtil.popularImagemNaoDisponivel());
			stream.close();
		} catch (IOException e) {
			logger.error("", e);	
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		} catch (Exception e) {
			logger.error("", e);
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		}
	}
	 
	public void listenerCadastro(FileUploadEvent event) {
		 UploadedFile item = event.getUploadedFile();
		 Arquivo arquivo =  new Arquivo();
		 arquivo.setDadosArquivo(item.getData());
		 arquivo.setNome(event.getUploadedFile().getName());
		 arquivo.setMimeType(event.getUploadedFile().getContentType());
		 this.arquivos.clear();
	     this.arquivos.add(arquivo);	
	}	
	
	public void listenerEditar(FileUploadEvent event)  {
		 UploadedFile item = event.getUploadedFile();
		 // Situação onde no momento do cadastro não foi inserido uma imagem.
		 if(this.classificados.getImagem() == null){
			 Arquivo imagem = new Arquivo();
			 imagem.setDadosArquivo(item.getData());
			 imagem.setNome(event.getUploadedFile().getName());
			 imagem.setMimeType(event.getUploadedFile().getContentType());
			 imagem.setIdClassificados(this.classificados.getId());
			 this.classificados.setImagem(imagem);
		 }else{
			 this.classificados.getImagem().setDadosArquivo(item.getData());
			 this.classificados.getImagem().setNome(event.getUploadedFile().getName());
			 this.classificados.getImagem().setMimeType(event.getUploadedFile().getContentType());
		 }
	}	
	
	public long getTimeStamp() {
	     return System.currentTimeMillis();
	 }
	
	public String salvarClassificados(){
		String retorno = null;
		try {
			if(this.validaDadosClassificados()){
				this.classificados.setImagem(!this.arquivos.isEmpty() ? this.arquivos.get(0) : null);
				this.classificados.setIdUsuario(AplicacaoUtil.getUsuarioAutenticado().getId());
				this.classificados.setIdCondominio(this.condominio.getId());
				this.classificadosService.salvar(this.classificados);
				this.classificados = new Classificados();
				this.arquivos.clear();
				this.pesquisar(null);
				ManagedBeanUtil.setMensagemInfo("msg.classificados.salvoSucesso");
				retorno = "salvar";
			}
		} catch (SQLException e) {
			logger.error("erro sqlstate "+e.getSQLState(), e);	
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		} catch (Exception e) {
			logger.error("", e);
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		}
		return retorno;
	}
	
	public String cancelarCadastrosClassificados(){
		this.classificados = new Classificados();
		this.pesquisar(null);
		return "cancelar";
	}
	
	public String excluirClassificados(){
		try {
			this.classificadosService.excluir(this.classificados);
			this.classificados = new Classificados();
			this.pesquisar(null);
			ManagedBeanUtil.setMensagemInfo("msg.classificados.excluirSucesso");
		} catch (SQLException e) {
			logger.error("erro sqlstate "+e.getSQLState(), e);	
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		} catch (Exception e) {
			logger.error("", e);
			ManagedBeanUtil.setMensagemErro(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "msg.erro.executarOperacao");
		}
		return "excluir";
		        
	}
	
	public Boolean classificadosPertenceAoUsuarioLogado(){
		Boolean pertenceUsuarioLogado = Boolean.FALSE;
		if (this.listaClassificados.getRowData().getIdUsuario().intValue() == AplicacaoUtil.getUsuarioAutenticado().getId().intValue()){			
			pertenceUsuarioLogado = Boolean.TRUE;
		}
		return pertenceUsuarioLogado;
	}
	

	private Boolean validaDadosClassificados() {
		Integer quantidadeErros = 0; 
		if(this.condominio.getId() == null){
			ManagedBeanUtil.setMensagemErro("msg.classificados.condominioRequerido");
			quantidadeErros++;			
		}
		if(this.classificados.getDataExpira() == null){
			ManagedBeanUtil.setMensagemErro("msg.classificados.dataExpiraRequerida");
			quantidadeErros++;			
		}
		if(this.classificados.getTitulo().trim().equals("")){
			ManagedBeanUtil.setMensagemErro("msg.classificados.tituloRequerido");
			quantidadeErros++;			
		}
		return quantidadeErros == 0 ? Boolean.TRUE : Boolean.FALSE;
	}
	
	private void configurarDataExpira(){
		// Configura da data de expiração do anúncio para um mês após a data atual
		Calendar dataExpira = Calendar.getInstance();
		dataExpira.setTime(new Date());
		dataExpira.add(Calendar.MONTH, 1);
		this.classificados.setDataExpira(dataExpira.getTime());
	}

	public Classificados getClassificados() {
		return classificados;
	}

	public void setClassificados(Classificados classificados) {
		this.classificados = classificados;
	}

	public Condominio getCondominio() {
		return condominio;
	}

	public void setCondominio(Condominio condominio) {
		this.condominio = condominio;
	}
	
	public ClassificadosService getClassificadosService() {
		return classificadosService;
	}

	public void setClassificadosService(ClassificadosService classificadosService) {
		this.classificadosService = classificadosService;
	}

	public CondominioMB getCondominioMB() {
		return condominioMB;
	}

	public void setCondominioMB(CondominioMB condominioMB) {
		this.condominioMB = condominioMB;
	}

	public ListDataModel<Classificados> getListaClassificados() {
		return listaClassificados;
	}

	public ArrayList<Arquivo> getArquivos() {
		return arquivos;
	}

	public void setArquivos(ArrayList<Arquivo> arquivos) {
		this.arquivos = arquivos;
	}

	public void setListaClassificados(
			ListDataModel<Classificados> listaClassificados) {
		this.listaClassificados = listaClassificados;
	}	

	public UIInput getComponenteTituloClassificados() {
		return componenteTituloClassificados;
	}

	public void setComponenteTituloClassificados(
			UIInput componenteTituloClassificados) {
		this.componenteTituloClassificados = componenteTituloClassificados;
	}

	public UIInput getComponenteDescricaoClassificados() {
		return componenteDescricaoClassificados;
	}

	public void setComponenteDescricaoClassificados(
			UIInput componenteDescricaoClassificados) {
		this.componenteDescricaoClassificados = componenteDescricaoClassificados;
	}

	public UIInput getComponenteTelefone1Classificados() {
		return componenteTelefone1Classificados;
	}

	public void setComponenteTelefone1Classificados(
			UIInput componenteTelefone1Classificados) {
		this.componenteTelefone1Classificados = componenteTelefone1Classificados;
	}

	public UIInput getComponenteTelefone2Classificados() {
		return componenteTelefone2Classificados;
	}

	public void setComponenteTelefone2Classificados(
			UIInput componenteTelefone2Classificados) {
		this.componenteTelefone2Classificados = componenteTelefone2Classificados;
	}

	public UIInput getComponenteValorClassificados() {
		return componenteValorClassificados;
	}

	public void setComponenteValorClassificados(UIInput componenteValorClassificados) {
		this.componenteValorClassificados = componenteValorClassificados;
	}

	public UIInput getComponenteDataExpiraClassificados() {
		return componenteDataExpiraClassificados;
	}

	public void setComponenteDataExpiraClassificados(
			UIInput componenteDataExpiraClassificados) {
		this.componenteDataExpiraClassificados = componenteDataExpiraClassificados;
	}

	public List<SelectItem> getListaCondominios() {
		return listaCondominios;
	}

	public void setListaCondominios(List<SelectItem> listaCondominios) {
		this.listaCondominios = listaCondominios;
	}
	
}
