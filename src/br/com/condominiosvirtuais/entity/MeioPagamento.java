package br.com.condominiosvirtuais.entity;

import br.com.condominiosvirtuais.util.AplicacaoUtil;

public class MeioPagamento  {
	
	private Integer id;
	
	private String nome;
	
	private String nomeI18n;

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

	public String getNomeI18n() {
		nomeI18n = AplicacaoUtil.i18n(this.getNome());
		return  nomeI18n;
	}

	public void setNomeI18n(String nomeI18n) {
		this.nomeI18n = nomeI18n;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result + ((nomeI18n == null) ? 0 : nomeI18n.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MeioPagamento other = (MeioPagamento) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		if (nomeI18n == null) {
			if (other.nomeI18n != null)
				return false;
		} else if (!nomeI18n.equals(other.nomeI18n))
			return false;
		return true;
	}
	

	
	
	

}
