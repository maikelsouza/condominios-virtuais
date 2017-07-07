package br.com.condominiosvirtuais.vo;

import java.util.List;

import br.com.condominiosvirtuais.entity.Aba;
import br.com.condominiosvirtuais.entity.Componente;

public class TelaVO implements Comparable<TelaVO>{
	
	private Integer id;
	
	private Integer idTela;
	
	private String nomeI18nTela;
	
	private String nomeTela;
	
	private String nomeI18nModulo;
	
	private String descricaoI18nTela;
	
	private String descricaoTela;
	
	private List<Aba> listaAbasTela;
	
	private List<Componente> listaComponentesTela;
	
	private Boolean checada;
	
	private String acao;

	public Integer getId() {		
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getIdTela() {
		return idTela;
	}

	public void setIdTela(Integer idTela) {
		this.idTela = idTela;
	}

	public String getNomeI18nTela() {
		return nomeI18nTela;
	}

	public void setNomeI18nTela(String nomeI18nTela) {
		this.nomeI18nTela = nomeI18nTela;
	}

	public String getDescricaoI18nTela() {
		return descricaoI18nTela;
	}

	public void setDescricaoI18nTela(String descricaoI18nTela) {
		this.descricaoI18nTela = descricaoI18nTela;
	}
	

	public String getNomeI18nModulo() {
		return nomeI18nModulo;
	}

	public void setNomeI18nModulo(String nomeI18nModulo) {
		this.nomeI18nModulo = nomeI18nModulo;
	}

	public List<Aba> getListaAbasTela() {
		return listaAbasTela;
	}

	public void setListaAbasTela(List<Aba> listaAbasTela) {
		this.listaAbasTela = listaAbasTela;
	}

	public List<Componente> getListaComponentesTela() {
		return listaComponentesTela;
	}

	public void setListaComponentesTela(List<Componente> listaComponentesTela) {
		this.listaComponentesTela = listaComponentesTela;
	}	

	public Boolean getChecada() {
		return checada;
	}

	public void setChecada(Boolean checada) {
		this.checada = checada;
	}	

	public String getNomeTela() {
		return nomeTela;
	}

	public void setNomeTela(String nomeTela) {
		this.nomeTela = nomeTela;
	}

	public String getDescricaoTela() {
		return descricaoTela;
	}

	public void setDescricaoTela(String descricaoTela) {
		this.descricaoTela = descricaoTela;
	}	

	public String getAcao() {
		return acao;
	}

	public void setAcao(String acao) {
		this.acao = acao;
	}

	@Override
	public int compareTo(TelaVO outraTelaVO) {
		int resultado = this.getNomeI18nModulo().compareTo(outraTelaVO.getNomeI18nModulo());
	    if (resultado == 0) {
	        resultado = this.getNomeI18nTela().compareTo(outraTelaVO.getNomeI18nTela());
	    }
	    return resultado;
	}
	
	
}
