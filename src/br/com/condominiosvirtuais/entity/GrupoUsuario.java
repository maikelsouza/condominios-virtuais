package br.com.condominiosvirtuais.entity;

import java.util.Iterator;
import java.util.List;

import br.com.condominiosvirtuais.enumeration.TipoGrupoUsuarioEnum;

/**
 * Entidade que representa um grupo de usuário. Exemplo: Administrador, Síndico, Condômino e etc.
 * @author Maikel Joel de Souza
 * @since 23/02/2013
 */
public class GrupoUsuario {
	
	private Integer id;
	
	private String nome;	
	
	// Identificador do grupo de usuário ADMINISTRADOR (1), ADMINISTRADORA (2) e etc 
	private TipoGrupoUsuarioEnum tipoGrupoUsuario; 
	

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public TipoGrupoUsuarioEnum getTipoGrupoUsuario() {
		return tipoGrupoUsuario;
	}

	public void setTipoGrupoUsuario(TipoGrupoUsuarioEnum tipoGrupoUsuario) {
		this.tipoGrupoUsuario = tipoGrupoUsuario;
	}
	
	public Boolean temAcesso(String pagina){
		List<String> listaTelas = null;
		Boolean temAcesso = Boolean.FALSE;		
		switch(this.getId()){
			case 1 :					
				listaTelas = TipoGrupoUsuarioEnum.ADMINISTRADOR.getGrupoUsuariosTelasAcesso().get(this.getId()); 
				temAcesso = this.contemTela(pagina, listaTelas);
				break;
			case 2 :					
				listaTelas = TipoGrupoUsuarioEnum.ADMINISTRADORA.getGrupoUsuariosTelasAcesso().get(this.getId()); 
				temAcesso = this.contemTela(pagina, listaTelas);
				break;
			case 3 :					
				listaTelas = TipoGrupoUsuarioEnum.SINDICO_PROFISSIONAL.getGrupoUsuariosTelasAcesso().get(this.getId()); 
				temAcesso = this.contemTela(pagina, listaTelas);
				break;
			case 4 :					
				listaTelas = TipoGrupoUsuarioEnum.SINDICO.getGrupoUsuariosTelasAcesso().get(this.getId()); 
				temAcesso = this.contemTela(pagina, listaTelas);
				break;
			case 5:					
				listaTelas = TipoGrupoUsuarioEnum.CONDOMINO.getGrupoUsuariosTelasAcesso().get(this.getId()); 
				temAcesso = this.contemTela(pagina, listaTelas);
				break;
			case 6 :					
				TipoGrupoUsuarioEnum.FUNCIONARIO.inicializar();
				listaTelas = TipoGrupoUsuarioEnum.FUNCIONARIO.getGrupoUsuariosTelasAcesso().get(this.getId()); 
				temAcesso = this.contemTela(pagina, listaTelas);
				break;
			case 7 :					
				listaTelas = TipoGrupoUsuarioEnum.ESCRITORIO_CONTABILIDADE.getGrupoUsuariosTelasAcesso().get(this.getId()); 
				temAcesso = this.contemTela(pagina, listaTelas);	
				
			}
		
		return temAcesso;
		
	}
	
	private Boolean contemTela(String tela, List<String> listaTelas){
		Boolean contem = Boolean.FALSE;		
		Iterator<String> iteratorListaTelas = listaTelas.iterator();
		while(iteratorListaTelas.hasNext() && !contem) {
			String telaLista = iteratorListaTelas.next();			
			if(telaLista.equals(tela)){
				contem = Boolean.TRUE;
			}			
		}
		return contem;
	}	
}
