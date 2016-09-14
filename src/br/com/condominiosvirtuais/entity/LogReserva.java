package br.com.condominiosvirtuais.entity;

import java.util.Date;

public class LogReserva {
	
	private Integer id;
	
	private Integer idReserva;
	
	private Integer idCondomino;
	
	private Integer idAmbiente;
	
	private String situacao;
	
	private String MotivoReprovacao;

	private String MotivoSuspensao;
	
	private Date data;
	
	private Integer idLog;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getIdReserva() {
		return idReserva;
	}

	public void setIdReserva(Integer idReserva) {
		this.idReserva = idReserva;
	}

	public Integer getIdCondomino() {
		return idCondomino;
	}

	public void setIdCondomino(Integer idCondomino) {
		this.idCondomino = idCondomino;
	}

	public Integer getIdAmbiente() {
		return idAmbiente;
	}

	public void setIdAmbiente(Integer idAmbiente) {
		this.idAmbiente = idAmbiente;
	}

	public String getSituacao() {
		return situacao;
	}

	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}

	public String getMotivoReprovacao() {
		return MotivoReprovacao;
	}

	public void setMotivoReprovacao(String motivoReprovacao) {
		MotivoReprovacao = motivoReprovacao;
	}

	public String getMotivoSuspensao() {
		return MotivoSuspensao;
	}

	public void setMotivoSuspensao(String motivoSuspensao) {
		MotivoSuspensao = motivoSuspensao;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public Integer getIdLog() {
		return idLog;
	}

	public void setIdLog(Integer idLog) {
		this.idLog = idLog;
	}
	
}
