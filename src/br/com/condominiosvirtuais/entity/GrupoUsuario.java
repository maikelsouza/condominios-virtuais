package br.com.condominiosvirtuais.entity;

import java.io.Serializable;
import java.util.List;

import br.com.condominiosvirtuais.enumeration.GrupoUsuarioPadraoEnum;
import br.com.condominiosvirtuais.enumeration.GrupoUsuarioSituacaoEnum;
import br.com.condominiosvirtuais.enumeration.TipoGrupoUsuarioEnum;
import br.com.condominiosvirtuais.util.AplicacaoUtil;
import br.com.condominiosvirtuais.vo.TelaVO;

/**
 * Entidade que representa um grupo de usu�rio. Exemplo: Administrador, S�ndico, Cond�mino e etc.
 * @author Maikel Joel de Souza
 * @since 23/02/2013
 */
public class GrupoUsuario implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private Integer id;
	
	private String nome;	
	
	private String descricao;
	
	private Boolean situacao;
	
	private Boolean padrao;
	
	private Integer tipoUsuario;
	
	// Lista de telas que est�o cadastradas na aplica��o. Entidade que representa uma tela
	// TODO: Rever para remover essa lista e usar somente a listaTelaAcesso
	private List<Tela> listaTela;
	
	// Lista de telasVO que est�o que um determinado grupo que tema acesso. VO que representa mais que uma tela. Exemplo: A pr�pria tela, o m�dulo a qual </br>
	// ela pertence, a permiss�o que deve ter. 
	private List<TelaVO> listaTelaAcesso;
	
	private Integer idCondominio;
	
	private Integer idSindicoProfissional;
	
	private Integer idEscritorioContabilidade;
	
	// Identificador do grupo de usu�rio ADMINISTRADOR (1), ADMINISTRADORA (2) e etc 
	private TipoGrupoUsuarioEnum tipoGrupoUsuario; 
	

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		if(this.nome != null){
			// Condi��o criada para garantir que s� ir� recuperar os grupos fixos do sistema, onde ter�o seus nomes i18n, os outros s�o cadastrados pelo usu�rio
			if(this.getPadrao().booleanValue() == GrupoUsuarioPadraoEnum.SIM.getPadrao() && 
					this.getSituacao().booleanValue() == GrupoUsuarioSituacaoEnum.ATIVO.getSituacao().booleanValue()
					&& this.getIdCondominio() == null && this.getIdEscritorioContabilidade() == null && this.getIdSindicoProfissional() == null) {
				return AplicacaoUtil.i18n(this.nome);
			}else{
				return this.nome;
			}
		}else{
			return this.nome;
		}
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

// TODO: C�digo comentado em 18/09/2017. Apagar em 180 dias
//	public TipoGrupoUsuarioEnum getTipoGrupoUsuario() {
//		return tipoGrupoUsuario;
//	}
//
//	public void setTipoGrupoUsuario(TipoGrupoUsuarioEnum tipoGrupoUsuario) {
//		this.tipoGrupoUsuario = tipoGrupoUsuario;
//	}
	
	
// TODO: C�digo comentado em 04/10/2017. Apagar em 180 dias	
//	public Boolean temAcesso(String pagina){
//		List<String> listaTelas = null;
//		Boolean temAcesso = Boolean.FALSE;		
//		switch(this.getId()){
//			case 1 :					
//				listaTelas = TipoGrupoUsuarioEnum.ADMINISTRADOR.getGrupoUsuariosTelasAcesso().get(this.getId()); 
//				temAcesso = this.contemTela(pagina, listaTelas);
//				break;
//			case 2 :					
//				listaTelas = TipoGrupoUsuarioEnum.ADMINISTRADORA.getGrupoUsuariosTelasAcesso().get(this.getId()); 
//				temAcesso = this.contemTela(pagina, listaTelas);
//				break;
//			case 3 :					
//				listaTelas = TipoGrupoUsuarioEnum.SINDICO_PROFISSIONAL.getGrupoUsuariosTelasAcesso().get(this.getId()); 
//				temAcesso = this.contemTela(pagina, listaTelas);
//				break;
//			case 4 :					
//				listaTelas = TipoGrupoUsuarioEnum.SINDICO.getGrupoUsuariosTelasAcesso().get(this.getId()); 
//				temAcesso = this.contemTela(pagina, listaTelas);
//				break;
//			case 5:					
//				listaTelas = TipoGrupoUsuarioEnum.CONDOMINO.getGrupoUsuariosTelasAcesso().get(this.getId()); 
//				temAcesso = this.contemTela(pagina, listaTelas);
//				break;
//			case 6 :					
//				TipoGrupoUsuarioEnum.FUNCIONARIO.inicializar();
//				listaTelas = TipoGrupoUsuarioEnum.FUNCIONARIO.getGrupoUsuariosTelasAcesso().get(this.getId()); 
//				temAcesso = this.contemTela(pagina, listaTelas);
//				break;
//			case 7 :					
//				listaTelas = TipoGrupoUsuarioEnum.ESCRITORIO_CONTABILIDADE.getGrupoUsuariosTelasAcesso().get(this.getId()); 
//				temAcesso = this.contemTela(pagina, listaTelas);	
//				
//			}
//		
//		return temAcesso;
//		
//	}
	
	public String getDescricaoLimitado(){
		if(this.descricao != null){
			// Condi��o criada para garantir que s� ir� recuperar os grupos fixos do sistema, onde ter�o seus nomes i18n, os outros s�o cadastrados pelo usu�rio
			if(this.getPadrao().booleanValue() == GrupoUsuarioPadraoEnum.SIM.getPadrao() && 
					this.getSituacao().booleanValue() == GrupoUsuarioSituacaoEnum.ATIVO.getSituacao().booleanValue()
					&& this.getIdCondominio() == null && this.getIdEscritorioContabilidade() == null && this.getIdSindicoProfissional() == null) {
				return AplicacaoUtil.i18n(descricao);
			}else{
				Integer limite = 80;
				return this.descricao.length() > limite ? this.descricao.substring(0,limite) + "..." : this.descricao;
			}
		}else{
			return this.descricao;
		}
	}
	
	public String getDescricao() {
		if(this.descricao != null){
			// Condi��o criada para garantir que s� ir� recuperar os grupos fixos do sistema, onde ter�o seus nomes i18n, os outros s�o cadastrados pelo usu�rio
			if(this.getPadrao().booleanValue() == GrupoUsuarioPadraoEnum.SIM.getPadrao() && 
					this.getSituacao().booleanValue() == GrupoUsuarioSituacaoEnum.ATIVO.getSituacao().booleanValue()
					&& this.getIdCondominio() == null && this.getIdEscritorioContabilidade() == null && this.getIdSindicoProfissional() == null) {
				return AplicacaoUtil.i18n(descricao);
			}else{	
				return this.descricao;
			}
		}else{
			return this.descricao;
		}
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}	
	
	public Boolean getSituacao() {
		return situacao;
	}
	
	public String getSituacaoI18n() {
		return this.situacao == GrupoUsuarioSituacaoEnum.ATIVO.getSituacao() ? AplicacaoUtil.i18n("grupoUsuario.situacao.1") : AplicacaoUtil.i18n("grupoUsuario.situacao.0");
	}
	
	public String getPadraoI18n() {
		return this.padrao == GrupoUsuarioPadraoEnum.SIM.getPadrao() ? AplicacaoUtil.i18n("grupoUsuario.padrao.1") : AplicacaoUtil.i18n("grupoUsuario.padrao.0");
	}
	
	public String getTipoUsuarioI18n() {
		if(this.tipoUsuario == null){
			return "";	
		}else{
			return AplicacaoUtil.i18n("grupoUsuario.tipoUsuario."+this.tipoUsuario);
		}
	}

	public void setSituacao(Boolean situacao) {
		this.situacao = situacao;
	}	

	public List<Tela> getListaTela() {
		return listaTela;
	}

	public void setListaTela(List<Tela> listaTela) {
		this.listaTela = listaTela;
	}

	public Integer getIdCondominio() {
		return idCondominio;
	}

	public void setIdCondominio(Integer idCondominio) {
		this.idCondominio = idCondominio;
	}	

	public Integer getIdSindicoProfissional() {
		return idSindicoProfissional;
	}

	public void setIdSindicoProfissional(Integer idSindicoProfissional) {
		this.idSindicoProfissional = idSindicoProfissional;
	}

	public Integer getIdEscritorioContabilidade() {
		return idEscritorioContabilidade;
	}

	public void setIdEscritorioContabilidade(Integer idEscritorioContabilidade) {
		this.idEscritorioContabilidade = idEscritorioContabilidade;
	}	

	public List<TelaVO> getListaTelaAcesso() {
		return listaTelaAcesso;
	}

	public void setListaTelaAcesso(List<TelaVO> listaTelaAcesso) {
		this.listaTelaAcesso = listaTelaAcesso;
	}	
	
	public Boolean getPadrao() {
		return padrao;
	}

	public void setPadrao(Boolean padrao) {
		this.padrao = padrao;
	}

	public Integer getTipoUsuario() {
		return tipoUsuario;
	}

	public void setTipoUsuario(Integer tipoUsuario) {
		this.tipoUsuario = tipoUsuario;
	}

	public Boolean existeTela(String caminhoPagina){
		// Todos os usu�rios que conseguiram se autenticar tem acesso a tela principal
		if (caminhoPagina.equals("/pages/formPrincipal.xhtml") || caminhoPagina.equals("/security/formLogin.xhtml")){
			return Boolean.TRUE;
		}
		
		String pagina = caminhoPagina.substring(7, caminhoPagina.length());
		for (TelaVO telaVO : listaTelaAcesso) {
			if(telaVO.getNomeArquivoTela().equals(pagina)){
				return Boolean.TRUE;		
			}
		}
		return Boolean.FALSE;
	}
	
	public Boolean existeComponente(String nomeComponente){
		for (TelaVO telaVO : listaTelaAcesso) {
			for (Componente componente : telaVO.getListaComponentesTela()) {
				if (componente.getNome().equals(nomeComponente)){
					return Boolean.TRUE;
				}
			}
		}
		return Boolean.FALSE;
	}
	
	public Boolean existeAba(String nomeAba){
		for (TelaVO telaVO : listaTelaAcesso) {
			for (Aba aba : telaVO.getListaAbasTela()) {
				if (aba.getNome().equals(nomeAba)){
					return Boolean.TRUE;
				}
			}
		}
		return Boolean.FALSE;
	}

// TODO: C�digo comentado em 04/10/2017. Apagar em 180 dias	
//	private Boolean contemTela(String tela, List<String> listaTelas){
//		Boolean contem = Boolean.FALSE;		
//		Iterator<String> iteratorListaTelas = listaTelas.iterator();
//		while(iteratorListaTelas.hasNext() && !contem) {
//			String telaLista = iteratorListaTelas.next();			
//			if(telaLista.equals(tela)){
//				contem = Boolean.TRUE;
//			}			
//		}
//		return contem;
//	}	
}
