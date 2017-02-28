
package br.com.condominiosvirtuais.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Entidade que representa um Condomínio.
 * @author Maikel Joel de Souza
 * @since 20/02/2013 
 */
public class Condominio implements Serializable{

	private static final long serialVersionUID = 1L;

	private Integer id;
	
	private String nome;
	
	private Integer situacao;
	
	private Long cnpj;
	
	private Usuario sindicoGeral;
	
	private Condomino subSindicoGeral;
	
	private List<Condomino> listaConselheiros;
	
	private List<Bloco> listaBlocos;
	
	private List<Ambiente> listaAmbientes;
	
	private Endereco endereco;
	
	private Long telefoneFixo;
	
	private Long telefoneCelular;
	
	private Integer idEscritorioContabilidade;
	
	// Atributo criado para guardar um código, que deverá ser único e gerado randomicamente. Inicialmente gerado para que o usuário possa fazer o seu cadastro.
	private Integer codigo;
		
	public Condominio() {
		this.endereco = new Endereco();
		this.sindicoGeral = new Usuario();
		this.subSindicoGeral = new Condomino();
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

	public Integer getSituacao() {
		return situacao;
	}

	public void setSituacao(Integer situacao) {
		this.situacao = situacao;
	}

	public Long getCnpj() {
		return cnpj;
	}

	public void setCnpj(Long cnpj) {
		this.cnpj = cnpj;
	}

	public Usuario getSindicoGeral() {
		return sindicoGeral;
	}

	public void setSindicoGeral(Usuario sindicoGeral) {
		this.sindicoGeral = sindicoGeral;
	}

	public Condomino getSubSindicoGeral() {
		return subSindicoGeral;
	}

	public void setSubSindicoGeral(Condomino subSindicoGeral) {
		this.subSindicoGeral = subSindicoGeral;
	}

	public List<Condomino> getListaConselheiros() {
		return listaConselheiros;
	}

	public void setListaConselheiros(List<Condomino> listaConselheiros) {
		this.listaConselheiros = listaConselheiros;
	}

	public List<Bloco> getListaBlocos() {
		return listaBlocos;
	}

	public void setListaBlocos(List<Bloco> listaBlocos) {
		this.listaBlocos = listaBlocos;
	}

	public List<Ambiente> getListaAmbientes() {
		return listaAmbientes;
	}

	public void setListaAmbientes(List<Ambiente> listaAmbientes) {
		this.listaAmbientes = listaAmbientes;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}	

	public Long getTelefoneFixo() {
		return telefoneFixo;
	}

	public void setTelefoneFixo(Long telefoneFixo) {
		this.telefoneFixo = telefoneFixo;
	}

	public Long getTelefoneCelular() {
		return telefoneCelular;
	}

	public void setTelefoneCelular(Long telefoneCelular) {
		this.telefoneCelular = telefoneCelular;
	}	
	
	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}	

	public Integer getIdEscritorioContabilidade() {
		return idEscritorioContabilidade;
	}

	public void setIdEscritorioContabilidade(Integer idEscritorioContabilidade) {
		this.idEscritorioContabilidade = idEscritorioContabilidade;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cnpj == null) ? 0 : cnpj.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
		result = prime * result + ((idEscritorioContabilidade == null) ? 0 : idEscritorioContabilidade.hashCode());
		result = prime * result
				+ ((situacao == null) ? 0 : situacao.hashCode());
		result = prime * result
				+ ((telefoneCelular == null) ? 0 : telefoneCelular.hashCode());
		result = prime * result
				+ ((telefoneFixo == null) ? 0 : telefoneFixo.hashCode());
		result = prime * result
				+ ((codigo == null) ? 0 : codigo.hashCode());
		result = prime * result
				+ ((idEscritorioContabilidade == null) ? 0 : idEscritorioContabilidade.hashCode());
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
		Condominio other = (Condominio) obj;
		if (cnpj == null) {
			if (other.cnpj != null)
				return false;
		} else if (!cnpj.equals(other.cnpj))
			return false;
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
		if (situacao == null) {
			if (other.situacao != null)
				return false;
		} else if (!situacao.equals(other.situacao))
			return false;
		if (telefoneCelular == null) {
			if (other.telefoneCelular != null)
				return false;
		} else if (!telefoneCelular.equals(other.telefoneCelular))
			return false;
		if (telefoneFixo == null) {
			if (other.telefoneFixo != null)
				return false;
		} else if (!telefoneFixo.equals(other.telefoneFixo))
			return false;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		if (idEscritorioContabilidade == null) {
			if (other.idEscritorioContabilidade != null)
				return false;
		} else if (!idEscritorioContabilidade.equals(other.idEscritorioContabilidade))
			return false;
		return true;
	}	
}
