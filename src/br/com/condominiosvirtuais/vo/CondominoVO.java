package br.com.condominiosvirtuais.vo;

import java.util.Calendar;

import br.com.condominiosvirtuais.entity.Arquivo;

public class CondominoVO {
	
	private Integer id;
	
	private Integer idCondominio;
	
	private Integer idBloco;	
	
	private Integer idUnidade;
	
	private Integer idCondomino;
	
	private String nomeCondominio;
	
	private String nomeBloco;
	
	private Integer numeroUnidade;
	
	private String nomeCondomino;
	
	private String emailCondomino;
	
	private Integer IdEmailCondomino;
	
	private Integer pagadorCondomino;
	
	private Integer sexoCondomino;
	
	private Integer dataNascimentoDiaCondomino;
	
	private Integer dataNascimentoMesCondomino;
	
	private Integer dataNascimentoAnoCondomino;
	
	private Integer situacaoCondomino;
	
	private Integer idGrupoUsuario;
	
	private Long dataNascimentoCondomino;
	
	private Boolean proprietarioCondomino;
	
	private Long telefoneResidencialCondomino;
	
	private Long telefoneCelularCondomino;	
	
	private Long telefoneComercialCondomino;
	
	private Arquivo arquivo;
	
	private Long cpfCondomino;
	
		
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

	public Integer getIdUnidade() {
		return idUnidade;
	}

	public void setIdUnidade(Integer idUnidade) {
		this.idUnidade = idUnidade;
	}	

	public Integer getIdCondomino() {
		return idCondomino;
	}

	public void setIdCondomino(Integer idCondomino) {
		this.idCondomino = idCondomino;
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

	public Integer getNumeroUnidade() {
		return numeroUnidade;
	}

	public void setNumeroUnidade(Integer numeroUnidade) {
		this.numeroUnidade = numeroUnidade;
	}

	public String getNomeCondomino() {
		return nomeCondomino;
	}

	public void setNomeCondomino(String nomeCondomino) {
		this.nomeCondomino = nomeCondomino;
	}	

	public Integer getDataNascimentoDiaCondomino() {
		return dataNascimentoDiaCondomino;
	}

	public void setDataNascimentoDiaCondomino(Integer dataNascimentoDiaCondomino) {
		this.dataNascimentoDiaCondomino = dataNascimentoDiaCondomino;
	}

	public Integer getDataNascimentoMesCondomino() {
		return dataNascimentoMesCondomino;
	}

	public void setDataNascimentoMesCondomino(Integer dataNascimentoMesCondomino) {
		this.dataNascimentoMesCondomino = dataNascimentoMesCondomino;
	}

	public Integer getDataNascimentoAnoCondomino() {
		return dataNascimentoAnoCondomino;
	}

	public void setDataNascimentoAnoCondomino(Integer dataNascimentoAnoCondomino) {
		this.dataNascimentoAnoCondomino = dataNascimentoAnoCondomino;
	}

	public Integer getSexoCondomino() {
		return sexoCondomino;
	}	

	public Integer getSituacaoCondomino() {
		return situacaoCondomino;
	}

	public void setSituacaoCondomino(Integer situacaoCondomino) {
		this.situacaoCondomino = situacaoCondomino;
	}	

	public Integer getIdGrupoUsuario() {
		return idGrupoUsuario;
	}

	public void setIdGrupoUsuario(Integer idGrupoUsuario) {
		this.idGrupoUsuario = idGrupoUsuario;
	}

	public void setSexoCondomino(Integer sexoCondomino) {
		this.sexoCondomino = sexoCondomino;
	}
	
	public Long getDataNascimentoCondomino() {
		/* Essa validação (diferente null e zero), se faz necessária, pois no caso de uma inserção/atualização o zero é para garantir a data null
		 e o null é a recuperação da informação do banco. */
		if(this.getDataNascimentoAnoCondomino() != null && this.getDataNascimentoAnoCondomino() != 0
				&& this.getDataNascimentoMesCondomino() != null && 
				this.getDataNascimentoDiaCondomino() != null && this.getDataNascimentoDiaCondomino() != 0){
			Calendar calendar = Calendar.getInstance();			
			calendar.set(this.getDataNascimentoAnoCondomino(), this.getDataNascimentoMesCondomino(), this.getDataNascimentoDiaCondomino(),0,0);			
			dataNascimentoCondomino = calendar.getTimeInMillis();
		}
		return dataNascimentoCondomino;
	}

	public void setDataNascimentoCondomino(Long dataNascimentoCondomino) {
		if(dataNascimentoCondomino != null){
			Calendar calendar = Calendar.getInstance();		
			calendar.setTimeInMillis(dataNascimentoCondomino);			
			this.setDataNascimentoDiaCondomino(calendar.get(Calendar.DAY_OF_MONTH));
			this.setDataNascimentoMesCondomino(calendar.get(Calendar.MONTH));
			this.setDataNascimentoAnoCondomino(calendar.get(Calendar.YEAR));			
		}
		this.dataNascimentoCondomino = dataNascimentoCondomino;			
	}

	public Boolean getProprietarioCondomino() {
		return proprietarioCondomino;
	}

	public void setProprietarioCondomino(Boolean proprietarioCondomino) {
		this.proprietarioCondomino = proprietarioCondomino;
	}

	public Long getTelefoneResidencialCondomino() {
		return telefoneResidencialCondomino;
	}

	public void setTelefoneResidencialCondomino(Long telefoneResidencialCondomino) {
		this.telefoneResidencialCondomino = telefoneResidencialCondomino;
	}

	public Long getTelefoneCelularCondomino() {
		return telefoneCelularCondomino;
	}

	public void setTelefoneCelularCondomino(Long telefoneCelularCondomino) {
		this.telefoneCelularCondomino = telefoneCelularCondomino;
	}

	public Long getTelefoneComercialCondomino() {
		return telefoneComercialCondomino;
	}

	public void setTelefoneComercialCondomino(Long telefoneComercialCondomino) {
		this.telefoneComercialCondomino = telefoneComercialCondomino;
	}	

	public String getEmailCondomino() {
		return emailCondomino;
	}

	public void setEmailCondomino(String emailCondomino) {
		this.emailCondomino = emailCondomino;
	}

	public Integer getIdEmailCondomino() {
		return IdEmailCondomino;
	}

	public void setIdEmailCondomino(Integer idEmailCondomino) {
		IdEmailCondomino = idEmailCondomino;
	}		

	public Integer getPagadorCondomino() {
		return pagadorCondomino;
	}

	public void setPagadorCondomino(Integer pagadorCondomino) {
		this.pagadorCondomino = pagadorCondomino;
	}

	public Arquivo getArquivo() {
		return arquivo;
	}

	public void setArquivo(Arquivo arquivo) {
		this.arquivo = arquivo;
	}	

	public Long getCpfCondomino() {
		return cpfCondomino;
	}

	public void setCpfCondomino(Long cpfCondomino) {
		this.cpfCondomino = cpfCondomino;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((IdEmailCondomino == null) ? 0 : IdEmailCondomino.hashCode());
		result = prime
				* result
				+ ((dataNascimentoAnoCondomino == null) ? 0
						: dataNascimentoAnoCondomino.hashCode());
		result = prime
				* result
				+ ((dataNascimentoCondomino == null) ? 0
						: dataNascimentoCondomino.hashCode());
		result = prime
				* result
				+ ((dataNascimentoDiaCondomino == null) ? 0
						: dataNascimentoDiaCondomino.hashCode());
		result = prime
				* result
				+ ((dataNascimentoMesCondomino == null) ? 0
						: dataNascimentoMesCondomino.hashCode());
		result = prime * result
				+ ((emailCondomino == null) ? 0 : emailCondomino.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((idBloco == null) ? 0 : idBloco.hashCode());
		result = prime * result
				+ ((idCondominio == null) ? 0 : idCondominio.hashCode());
		result = prime * result
				+ ((idCondomino == null) ? 0 : idCondomino.hashCode());
		result = prime * result
				+ ((idGrupoUsuario == null) ? 0 : idGrupoUsuario.hashCode());
		result = prime * result
				+ ((idUnidade == null) ? 0 : idUnidade.hashCode());
		result = prime * result
				+ ((nomeBloco == null) ? 0 : nomeBloco.hashCode());
		result = prime * result
				+ ((nomeCondominio == null) ? 0 : nomeCondominio.hashCode());
		result = prime * result
				+ ((nomeCondomino == null) ? 0 : nomeCondomino.hashCode());
		result = prime * result
				+ ((numeroUnidade == null) ? 0 : numeroUnidade.hashCode());
		result = prime
				* result
				+ ((proprietarioCondomino == null) ? 0 : proprietarioCondomino
						.hashCode());
		result = prime * result
				+ ((sexoCondomino == null) ? 0 : sexoCondomino.hashCode());
		result = prime
				* result
				+ ((situacaoCondomino == null) ? 0 : situacaoCondomino
						.hashCode());
		result = prime
				* result
				+ ((telefoneCelularCondomino == null) ? 0
						: telefoneCelularCondomino.hashCode());
		result = prime
				* result
				+ ((telefoneComercialCondomino == null) ? 0
						: telefoneComercialCondomino.hashCode());
		result = prime
				* result
				+ ((telefoneResidencialCondomino == null) ? 0
						: telefoneResidencialCondomino.hashCode());
		
		result = prime
				* result
				+ ((cpfCondomino == null) ? 0
						: cpfCondomino.hashCode());
		result = prime
				* result
				+ ((pagadorCondomino == null) ? 0
						: pagadorCondomino.hashCode());
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
		CondominoVO other = (CondominoVO) obj;
		if (IdEmailCondomino == null) {
			if (other.IdEmailCondomino != null)
				return false;
		} else if (!IdEmailCondomino.equals(other.IdEmailCondomino))
			return false;
		if (dataNascimentoAnoCondomino == null) {
			if (other.dataNascimentoAnoCondomino != null)
				return false;
		} else if (!dataNascimentoAnoCondomino
				.equals(other.dataNascimentoAnoCondomino))
			return false;
		if (dataNascimentoCondomino == null) {
			if (other.dataNascimentoCondomino != null)
				return false;
		} else if (!dataNascimentoCondomino
				.equals(other.dataNascimentoCondomino))
			return false;
		if (dataNascimentoDiaCondomino == null) {
			if (other.dataNascimentoDiaCondomino != null)
				return false;
		} else if (!dataNascimentoDiaCondomino
				.equals(other.dataNascimentoDiaCondomino))
			return false;
		if (dataNascimentoMesCondomino == null) {
			if (other.dataNascimentoMesCondomino != null)
				return false;
		} else if (!dataNascimentoMesCondomino
				.equals(other.dataNascimentoMesCondomino))
			return false;
		if (emailCondomino == null) {
			if (other.emailCondomino != null)
				return false;
		} else if (!emailCondomino.equals(other.emailCondomino))
			return false;
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
		if (idCondomino == null) {
			if (other.idCondomino != null)
				return false;
		} else if (!idCondomino.equals(other.idCondomino))
			return false;
		if (idGrupoUsuario == null) {
			if (other.idGrupoUsuario != null)
				return false;
		} else if (!idGrupoUsuario.equals(other.idGrupoUsuario))
			return false;
		if (idUnidade == null) {
			if (other.idUnidade != null)
				return false;
		} else if (!idUnidade.equals(other.idUnidade))
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
		if (nomeCondomino == null) {
			if (other.nomeCondomino != null)
				return false;
		} else if (!nomeCondomino.equals(other.nomeCondomino))
			return false;
		if (numeroUnidade == null) {
			if (other.numeroUnidade != null)
				return false;
		} else if (!numeroUnidade.equals(other.numeroUnidade))
			return false;
		if (proprietarioCondomino == null) {
			if (other.proprietarioCondomino != null)
				return false;
		} else if (!proprietarioCondomino.equals(other.proprietarioCondomino))
			return false;
		if (sexoCondomino == null) {
			if (other.sexoCondomino != null)
				return false;
		} else if (!sexoCondomino.equals(other.sexoCondomino))
			return false;
		if (situacaoCondomino == null) {
			if (other.situacaoCondomino != null)
				return false;
		} else if (!situacaoCondomino.equals(other.situacaoCondomino))
			return false;
		if (telefoneCelularCondomino == null) {
			if (other.telefoneCelularCondomino != null)
				return false;
		} else if (!telefoneCelularCondomino
				.equals(other.telefoneCelularCondomino))
			return false;
		if (telefoneComercialCondomino == null) {
			if (other.telefoneComercialCondomino != null)
				return false;
		} else if (!telefoneComercialCondomino
				.equals(other.telefoneComercialCondomino))
			return false;
		if (telefoneResidencialCondomino == null) {
			if (other.telefoneResidencialCondomino != null)
				return false;
		} else if (!telefoneResidencialCondomino
				.equals(other.telefoneResidencialCondomino))
			return false;
		if (pagadorCondomino == null) {
			if (other.pagadorCondomino != null)
				return false;
		} else if (!pagadorCondomino
				.equals(other.pagadorCondomino))
			return false;
		if (cpfCondomino == null) {
			if (other.cpfCondomino != null)
				return false;
		} else if (!cpfCondomino
				.equals(other.cpfCondomino))
			return false;
		return true;
	}	
	
}
