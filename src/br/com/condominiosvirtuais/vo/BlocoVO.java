package br.com.condominiosvirtuais.vo;

public class BlocoVO {
	
	private Integer id;
	
	private Integer idCondominio;
	
	private Integer idBloco;	
	
	private String nomeCondominio;
	
	private String nomeBloco;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getIdCondominio() {
		return idCondominio;
	}

	public void setIdCondominio(Integer idCondominio) {
		this.idCondominio = idCondominio;
	}

	public Integer getIdBloco() {
		return idBloco;
	}

	public void setIdBloco(Integer idBloco) {
		this.idBloco = idBloco;
	}

	public String getNomeCondominio() {
		return nomeCondominio;
	}

	public void setNomeCondominio(String nomeCondominio) {
		this.nomeCondominio = nomeCondominio;
	}

	public String getNomeBloco() {
		return nomeBloco;
	}

	public void setNomeBloco(String nomeBloco) {
		this.nomeBloco = nomeBloco;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((idBloco == null) ? 0 : idBloco.hashCode());
		result = prime * result
				+ ((idCondominio == null) ? 0 : idCondominio.hashCode());
		result = prime * result
				+ ((nomeBloco == null) ? 0 : nomeBloco.hashCode());
		result = prime * result
				+ ((nomeCondominio == null) ? 0 : nomeCondominio.hashCode());
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
		BlocoVO other = (BlocoVO) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (idBloco == null) {
			if (other.idBloco != null)
				return false;
		} else if (!idBloco.equals(other.idBloco))
			return false;
		if (idCondominio == null) {
			if (other.idCondominio != null)
				return false;
		} else if (!idCondominio.equals(other.idCondominio))
			return false;
		if (nomeBloco == null) {
			if (other.nomeBloco != null)
				return false;
		} else if (!nomeBloco.equals(other.nomeBloco))
			return false;
		if (nomeCondominio == null) {
			if (other.nomeCondominio != null)
				return false;
		} else if (!nomeCondominio.equals(other.nomeCondominio))
			return false;
		return true;
	}

}
