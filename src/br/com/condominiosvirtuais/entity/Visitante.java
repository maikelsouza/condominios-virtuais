package br.com.condominiosvirtuais.entity;

import java.util.List;

public class Visitante {
	
	private Integer id;
	
	private String nome;
	
	private Long rg;
	
	private Long cpf;
	
	private Long telefone;	
	
	private List<Visita> listaVisita;
	
	private Visita visita;
	
	
	public Visitante(){		
		this.visita = new Visita();
	}
	
	
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

	public Long getRg() {
		return rg;
	}

	public void setRg(Long rg) {
		this.rg = rg;
	}

	public Long getCpf() {
		return cpf;
	}

	public void setCpf(Long cpf) {
		this.cpf = cpf;
	}

	public Long getTelefone() {
		return telefone;
	}

	public void setTelefone(Long telefone) {
		this.telefone = telefone;
	}

	public List<Visita> getListaVisita() {
		return listaVisita;
	}

	public void setListaVisita(List<Visita> listaVisita) {
		this.listaVisita = listaVisita;
	}

	public Visita getVisita() {
		return visita;
	}

	public void setVisita(Visita visita) {
		this.visita = visita;
	}
	
}
