package br.com.condominiosvirtuais.vo;

import br.com.condominiosvirtuais.enumeration.UsuarioVOTipoUsuarioEnum;

public class UsuarioVO implements Comparable<UsuarioVO> {
	
	private Integer id;
	
	private Integer idUsuario;
	
	private String nomeUsuario; 
	
	private Integer tipoUsuario;
	
	private String nomeBloco;
	
	private Integer numeroUnidade;
	
		
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Integer idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getNomeUsuario() {
		return nomeUsuario;
	}

	public void setNomeUsuario(String nomeUsuario) {
		this.nomeUsuario = nomeUsuario;
	}

	public Integer getTipoUsuario() {
		return tipoUsuario;
	}

	public void setTipoUsuario(Integer tipoUsuario) {
		this.tipoUsuario = tipoUsuario;
	}	

	public String getNomeBloco() {
		return nomeBloco;
	}

	public void setNomeBloco(String nomeBloco) {
		this.nomeBloco = nomeBloco;
	}

	public Integer getNumeroUnidade() {
		return numeroUnidade;
	}

	public void setNumeroUnidade(Integer numeroUnidade) {
		this.numeroUnidade = numeroUnidade;
	}
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((idUsuario == null) ? 0 : idUsuario.hashCode());
		result = prime * result + ((nomeBloco == null) ? 0 : nomeBloco.hashCode());
		result = prime * result + ((nomeUsuario == null) ? 0 : nomeUsuario.hashCode());
		result = prime * result + ((numeroUnidade == null) ? 0 : numeroUnidade.hashCode());
		result = prime * result + ((tipoUsuario == null) ? 0 : tipoUsuario.hashCode());
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
		UsuarioVO other = (UsuarioVO) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (idUsuario == null) {
			if (other.idUsuario != null)
				return false;
		} else if (!idUsuario.equals(other.idUsuario))
			return false;
		if (nomeBloco == null) {
			if (other.nomeBloco != null)
				return false;
		} else if (!nomeBloco.equals(other.nomeBloco))
			return false;
		if (nomeUsuario == null) {
			if (other.nomeUsuario != null)
				return false;
		} else if (!nomeUsuario.equals(other.nomeUsuario))
			return false;
		if (numeroUnidade == null) {
			if (other.numeroUnidade != null)
				return false;
		} else if (!numeroUnidade.equals(other.numeroUnidade))
			return false;
		if (tipoUsuario == null) {
			if (other.tipoUsuario != null)
				return false;
		} else if (!tipoUsuario.equals(other.tipoUsuario))
			return false;
		return true;
	}

		
	@Override
	public int compareTo(UsuarioVO usuarioVO) {		
		int resultado = this.getTipoUsuario().compareTo(usuarioVO.getTipoUsuario());
		if (this.getTipoUsuario() == UsuarioVOTipoUsuarioEnum.CONDOMINO.getTipoUsuario()){
			if(resultado == 0){
				resultado = this.getNomeBloco().compareTo(usuarioVO.getNomeBloco());
			}
			if(resultado == 0){
				resultado = this.getNumeroUnidade().compareTo(usuarioVO.getNumeroUnidade());
			}
		}
		if(resultado == 0){
			resultado = this.getNomeUsuario().compareTo(usuarioVO.getNomeUsuario());
		}
		return resultado;
	}

}
